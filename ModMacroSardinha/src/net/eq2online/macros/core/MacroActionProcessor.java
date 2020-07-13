/*     */ package net.eq2online.macros.core;
/*     */ 
/*     */ import bsu;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionContext;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionStackEntry;
/*     */ import net.eq2online.macros.scripting.api.IScriptAction;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.IScriptParser;
/*     */ import net.eq2online.macros.scripting.exceptions.ScriptException;
/*     */ import net.eq2online.macros.scripting.exceptions.ScriptExceptionInvalidContextSwitch;
/*     */ import net.eq2online.macros.scripting.exceptions.ScriptExceptionInvalidModeSwitch;
/*     */ import net.eq2online.macros.scripting.exceptions.ScriptExceptionStackOverflow;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MacroActionProcessor
/*     */   implements IMacroActionProcessor
/*     */ {
/*     */   private IScriptParser parser;
/*  38 */   private int pointer = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean suspendProcessing = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   private ArrayList<IMacroAction> actions = new ArrayList<IMacroAction>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   private LinkedList<IMacroActionStackEntry> stack = new LinkedList<IMacroActionStackEntry>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IMacroAction pendingAction;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   private static Pattern scriptPattern = Pattern.compile("\\x24\\x24\\{(.*?)\\}\\x24\\x24");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private static Object executionLock = new Object();
/*     */   
/*     */   private boolean safeMode = true;
/*     */   
/*  72 */   private int maxExecutionTime = 100;
/*     */   
/*  74 */   private int maxActionsPerTick = 0;
/*     */   
/*  76 */   private int originalMaxActions = 0;
/*     */ 
/*     */   
/*     */   public static MacroActionProcessor compile(IScriptParser parser, String macro, int maxActionsPerTick, int maxExecutionTime) {
/*  80 */     return new MacroActionProcessor(parser, macro, maxActionsPerTick, maxExecutionTime);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MacroActionProcessor(IScriptParser parser, String macro, int maxActionsPerTick, int maxExecutionTime) {
/*  88 */     this.parser = parser;
/*  89 */     this.maxActionsPerTick = maxActionsPerTick;
/*  90 */     this.originalMaxActions = maxActionsPerTick;
/*  91 */     this.maxExecutionTime = maxExecutionTime;
/*     */     
/*  93 */     Matcher scriptPatternMatcher = scriptPattern.matcher(macro);
/*     */     
/*  95 */     while (scriptPatternMatcher.find()) {
/*     */       
/*  97 */       String script = scriptPatternMatcher.group(1);
/*  98 */       this.actions.add(new MacroActionChat(this, macro.substring(0, scriptPatternMatcher.start())));
/*  99 */       this.actions.addAll(this.parser.parseScript(this, script));
/* 100 */       macro = macro.substring(scriptPatternMatcher.end());
/* 101 */       scriptPatternMatcher.reset(macro);
/*     */     } 
/*     */     
/* 104 */     if (macro.length() > 0)
/*     */     {
/* 106 */       this.actions.add(new MacroActionChat(this, macro));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refreshPermissions() {
/* 123 */     for (IMacroAction action : this.actions)
/*     */     {
/* 125 */       action.refreshPermissions(this.parser);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void beginUnsafeBlock(IScriptActionProvider scriptActionProvider, IMacro macro, IMacroAction instance, int maxActions) {
/* 132 */     if (!this.safeMode) throw new ScriptExceptionInvalidModeSwitch();
/*     */     
/* 134 */     this.safeMode = false;
/* 135 */     this.maxActionsPerTick = maxActions;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void endUnsafeBlock(IScriptActionProvider iScriptActionProvider, IMacro macro, IMacroAction instance) {
/* 141 */     if (this.safeMode) throw new ScriptExceptionInvalidModeSwitch();
/*     */     
/* 143 */     this.safeMode = true;
/* 144 */     this.maxActionsPerTick = this.originalMaxActions;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnsafe() {
/* 150 */     return !this.safeMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute(IMacro macro, IMacroActionContext context, boolean stop, boolean allowLatent, boolean clock) throws ScriptException {
/* 159 */     if (macro.isDead()) return false;
/*     */     
/* 161 */     if (allowLatent) {
/*     */       
/* 163 */       int processedInstructionCount = 0;
/* 164 */       long systemTime = bsu.I();
/*     */       
/* 166 */       this.suspendProcessing = false;
/*     */       
/* 168 */       while (this.pointer < this.actions.size()) {
/*     */         
/* 170 */         processedInstructionCount++;
/* 171 */         IMacroAction currentAction = this.actions.get(this.pointer);
/* 172 */         boolean actionIsAllowed = true;
/*     */         
/* 174 */         if ((clock || !currentAction.isClocked()) && (!actionIsAllowed || !getConditionalExecutionState() || canExecuteNow(macro, context, currentAction))) {
/*     */           
/* 176 */           boolean continueExecuting = false;
/*     */           
/* 178 */           if (actionIsAllowed) {
/*     */             
/* 180 */             synchronized (executionLock) {
/*     */               
/* 182 */               if (MacroModSettings.scriptTrace) Log.info("TRACE[{0}] PTR={1} ST={2} [{3}]", new Object[] { Integer.valueOf(macro.getID()), Integer.valueOf(this.pointer), getConditionalExecutionState() ? "RUN" : "   ", currentAction.toString() }); 
/* 183 */               continueExecuting = currentAction.execute(context, macro, (stop && this.actions.size() - this.pointer < 2), true);
/*     */             } 
/*     */             
/* 186 */             this.suspendProcessing |= (this.maxActionsPerTick > 0 && processedInstructionCount >= this.maxActionsPerTick) ? 1 : 0;
/* 187 */             this.suspendProcessing |= (this.maxExecutionTime > 0 && bsu.I() - systemTime > this.maxExecutionTime) ? 1 : 0;
/*     */           } 
/*     */           
/* 190 */           if (continueExecuting) {
/*     */             
/* 192 */             advancePointer();
/* 193 */             if (this.suspendProcessing) return true;
/*     */           
/*     */           } 
/* 196 */           if (macro.isDead()) return false;
/*     */           
/*     */           continue;
/*     */         } 
/* 200 */         return true;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 205 */       if (this.stack.size() > 0)
/*     */       {
/* 207 */         String reason = "missing statement";
/* 208 */         IMacroActionStackEntry topStackEntry = getTopStackEntry();
/* 209 */         if (topStackEntry.isStackPushOperator() || topStackEntry.isConditionalOperator()) {
/*     */           
/* 211 */           IScriptAction action = topStackEntry.getAction().getAction();
/* 212 */           if (action instanceof ScriptAction)
/*     */           {
/* 214 */             reason = ((ScriptAction)action).getExpectedPopCommands();
/*     */           }
/*     */         } 
/*     */         
/* 218 */         context.getProvider().actionAddChatMessage("§c" + LocalisationProvider.getLocalisedString("script.error.missingpop", new Object[] { reason }));
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 223 */       if (this.pointer > 0)
/*     */       {
/* 225 */         throw new ScriptExceptionInvalidContextSwitch();
/*     */       }
/*     */       
/* 228 */       for (IMacroAction scriptAction : this.actions) {
/*     */         
/* 230 */         if (canExecuteNow(macro, context, scriptAction)) {
/*     */           
/* 232 */           scriptAction.execute(context, macro, false, false);
/* 233 */           if (macro.isDead()) return false;
/*     */         
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 239 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canExecuteNow(IMacro macro, IMacroActionContext context, IMacroAction currentAction) {
/* 250 */     boolean canExecuteNow = (currentAction.canExecuteNow(context, macro) || macro.isSynchronous());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 257 */     this.pendingAction = canExecuteNow ? null : currentAction;
/* 258 */     return canExecuteNow;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void advancePointer() {
/* 266 */     this.pointer++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushStack(IMacroAction action, boolean conditional) throws ScriptExceptionStackOverflow {
/* 275 */     pushStack(this.pointer, action, conditional);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushStack(int ptr, IMacroAction action, boolean conditional) throws ScriptExceptionStackOverflow {
/* 284 */     if (this.stack.size() > 32)
/*     */     {
/* 286 */       throw new ScriptExceptionStackOverflow();
/*     */     }
/*     */     
/* 289 */     this.stack.addFirst(new MacroActionStackEntry(ptr, action, conditional));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean popStack() {
/* 298 */     IMacroActionStackEntry popped = null;
/*     */     
/* 300 */     if (this.stack.size() > 0) {
/*     */       
/* 302 */       popped = this.stack.removeFirst();
/* 303 */       int stackPointer = popped.getStackPointer();
/*     */       
/* 305 */       if (stackPointer > -1) {
/*     */         
/* 307 */         this.pointer = stackPointer;
/* 308 */         if (this.safeMode) this.suspendProcessing = true;
/*     */       
/*     */       } else {
/*     */         
/* 312 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 316 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMacroActionStackEntry getTopStackEntry() {
/* 325 */     return (this.stack.size() > 0) ? this.stack.getFirst() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getConditionalExecutionState() {
/* 334 */     if (this.stack.size() == 0) return true;
/*     */     
/* 336 */     for (IMacroActionStackEntry stackEntry : this.stack) {
/*     */       
/* 338 */       if (!stackEntry.getConditionalFlag()) return false;
/*     */     
/*     */     } 
/* 341 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakLoop(IScriptActionProvider provider, IMacro macro, IMacroAction breakAction) {
/* 347 */     if (this.stack.size() == 0)
/*     */       return; 
/* 349 */     for (IMacroActionStackEntry stackEntry : this.stack) {
/*     */       
/* 351 */       if (stackEntry.getAction().canBreak(this, provider, macro, breakAction)) {
/*     */         
/* 353 */         stackEntry.setConditionalFlag(false);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onStopped(IMacro macro, IMacroActionContext context) {
/* 361 */     if (this.pendingAction != null) {
/*     */       
/* 363 */       this.pendingAction.onStopped(this, context, macro);
/* 364 */       this.pendingAction = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\MacroActionProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */