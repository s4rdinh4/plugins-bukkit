/*     */ package net.eq2online.macros.scripting.actions.lang;
/*     */ 
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*     */ import net.eq2online.macros.scripting.api.IScriptAction;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
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
/*     */ public class ScriptActionDo
/*     */   extends ScriptAction
/*     */ {
/*     */   protected class LoopState
/*     */   {
/*     */     int loopCounter;
/*     */     int totalLoops;
/*     */     boolean unlimited;
/*     */     boolean halted;
/*  30 */     int direction = 1;
/*     */     
/*  32 */     int offset = 0;
/*     */     
/*     */     String variableName;
/*     */ 
/*     */     
/*     */     LoopState(int totalLoops) {
/*  38 */       this.loopCounter = 0;
/*  39 */       this.totalLoops = totalLoops;
/*     */     }
/*     */ 
/*     */     
/*     */     LoopState(String variableName, int from, int to) {
/*  44 */       this.variableName = variableName;
/*  45 */       this.loopCounter = 0;
/*  46 */       this.offset = from;
/*     */       
/*  48 */       if (from == to) {
/*     */         
/*  50 */         this.totalLoops = 0;
/*     */       }
/*  52 */       else if (from > to) {
/*     */         
/*  54 */         this.totalLoops = from - to;
/*  55 */         this.direction = -1;
/*     */       }
/*     */       else {
/*     */         
/*  59 */         this.totalLoops = to - from;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     LoopState() {
/*  65 */       this.unlimited = true;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean ContinueLooping() {
/*  70 */       return !this.halted;
/*     */     }
/*     */ 
/*     */     
/*     */     void Increment() {
/*  75 */       this.loopCounter++;
/*     */       
/*  77 */       if (!this.unlimited)
/*     */       {
/*  79 */         this.halted |= (this.loopCounter > this.totalLoops) ? 1 : 0;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int GetOffsetCounter() {
/*  85 */       return this.offset + this.loopCounter * this.direction;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ScriptActionDo(ScriptContext context) {
/*  91 */     super(context, "do");
/*     */   }
/*     */ 
/*     */   
/*     */   protected ScriptActionDo(ScriptContext context, String actionName) {
/*  96 */     super(context, actionName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStackPushOperator() {
/* 102 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExpectedPopCommands() {
/* 108 */     return "§eDO §cexpects §dLOOP§c, §dUNTIL§c or §dWHILE";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean executeStackPush(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 114 */     if (params.length > 0) {
/*     */       
/* 116 */       if (instance.getState() == null)
/*     */       {
/*     */         
/* 119 */         int loopCount = ScriptCore.tryParseInt(ScriptCore.parseVars(provider, macro, params[0], false), 0);
/* 120 */         instance.setState(new LoopState(loopCount));
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 125 */     else if (instance.getState() == null) {
/*     */       
/* 127 */       instance.setState(new LoopState());
/*     */     } 
/*     */ 
/*     */     
/* 131 */     LoopState state = (LoopState)instance.getState();
/* 132 */     state.Increment();
/*     */     
/* 134 */     return state.ContinueLooping();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean executeStackPop(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params, IMacroAction popAction) {
/* 143 */     if (popAction.getAction() instanceof ScriptActionWhile || popAction.getAction() instanceof ScriptActionUntil) {
/*     */       
/* 145 */       LoopState state = (LoopState)instance.getState();
/*     */       
/* 147 */       if (state == null || !state.halted) {
/*     */         
/* 149 */         boolean conditionalResult = (provider.getExpressionEvaluator(macro, ScriptCore.parseVars(provider, macro, popAction.getRawParams(), true)).evaluate() != 0);
/*     */         
/* 151 */         if (popAction.getAction() instanceof ScriptActionWhile) {
/* 152 */           conditionalResult = !conditionalResult;
/*     */         }
/* 154 */         if (conditionalResult) {
/*     */           
/* 156 */           if (state == null) {
/*     */             
/* 158 */             state = new LoopState(0);
/* 159 */             instance.setState(state);
/*     */           } 
/*     */           
/* 162 */           state.halted = true;
/* 163 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 168 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBreak(IMacroActionProcessor processor, IScriptActionProvider provider, IMacro macro, IMacroAction instance, IMacroAction breakAction) {
/* 174 */     LoopState state = (LoopState)instance.getState();
/* 175 */     if (state != null) {
/*     */       
/* 177 */       state.halted = true;
/* 178 */       return true;
/*     */     } 
/*     */     
/* 181 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBePoppedBy(IScriptAction action) {
/* 187 */     return action instanceof ScriptActionLoop;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionDo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */