/*     */ package net.eq2online.macros.core;
/*     */ 
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.scripting.Variable;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionContext;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionStackEntry;
/*     */ import net.eq2online.macros.scripting.api.IReturnValue;
/*     */ import net.eq2online.macros.scripting.api.IReturnValueArray;
/*     */ import net.eq2online.macros.scripting.api.IScriptAction;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.IScriptParser;
/*     */ import net.eq2online.macros.scripting.exceptions.ScriptExceptionVoidResult;
/*     */ import net.eq2online.macros.scripting.parser.DeniedAction;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MacroAction
/*     */   implements IMacroAction
/*     */ {
/*     */   protected IMacroActionProcessor actionProcessor;
/*     */   protected IScriptAction action;
/*     */   protected String[] params;
/*     */   protected String rawParams;
/*     */   protected Object state;
/*  56 */   protected String outVar = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MacroAction(IMacroActionProcessor actionProcessor) {
/*  65 */     this.actionProcessor = actionProcessor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MacroAction(IMacroActionProcessor actionProcessor, IScriptAction action, String rawParams, String[] params, String outVar) {
/*  76 */     this(actionProcessor);
/*  77 */     this.action = action;
/*  78 */     this.rawParams = rawParams;
/*  79 */     this.params = params;
/*  80 */     this.outVar = outVar;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMacroActionProcessor getActionProcessor() {
/*  86 */     return this.actionProcessor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasOutVar() {
/*  92 */     return (this.outVar != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOutVarName() {
/*  98 */     return (this.outVar != null) ? this.outVar : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canExecuteNow(IMacroActionContext context, IMacro macro) {
/* 107 */     return (this.action
/* 108 */       .isStackPopOperator() || this.action
/* 109 */       .isStackPushOperator() || this.action
/* 110 */       .isConditionalOperator() || this.action
/* 111 */       .canExecuteNow(context.getProvider(), macro, this, this.rawParams, this.params));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refreshPermissions(IScriptParser parser) {
/* 121 */     if (this.action != null && !this.action.checkExecutePermission()) {
/*     */       
/* 123 */       Log.info("Disabling disallowed script action {0}", new Object[] { this.action.toString().toUpperCase() });
/* 124 */       this.rawParams = this.action.toString();
/* 125 */       this.action = (IScriptAction)new DeniedAction(parser.getContext());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute(IMacroActionContext context, IMacro macro, boolean stop, boolean allowLatent) {
/* 135 */     IMacroActionStackEntry topmost = this.actionProcessor.getTopStackEntry();
/*     */ 
/*     */     
/* 138 */     IScriptActionProvider provider = context.getProvider();
/* 139 */     if (this.action.isStackPopOperator()) {
/*     */       
/* 141 */       if (!allowLatent) return true;
/*     */       
/* 143 */       if (topmost != null)
/*     */       {
/* 145 */         if (topmost.isStackPushOperator()) {
/*     */           
/* 147 */           if (topmost.canBePoppedBy(this))
/*     */           {
/*     */             
/* 150 */             ScriptAction.onActionSkipped();
/*     */             
/* 152 */             topmost.executeStackPop(this.actionProcessor, context, macro, this);
/* 153 */             return this.actionProcessor.popStack();
/*     */           }
/*     */         
/* 156 */         } else if (topmost.isConditionalOperator()) {
/*     */           
/* 158 */           boolean isElse = topmost.isConditionalElseOperator(this);
/*     */           
/* 160 */           if (isElse) {
/*     */             
/* 162 */             if (!topmost.getElseFlag())
/*     */             {
/*     */               
/* 165 */               ScriptAction.onActionSkipped();
/*     */               
/* 167 */               this.action.executeConditionalElse(provider, macro, this, this.rawParams, this.params, topmost);
/*     */             }
/*     */           
/* 170 */           } else if (topmost.matchesConditionalOperator(this)) {
/*     */ 
/*     */             
/* 173 */             ScriptAction.onActionSkipped();
/*     */             
/* 175 */             topmost.executeStackPop(this.actionProcessor, context, macro, this);
/* 176 */             this.actionProcessor.popStack();
/*     */           }
/*     */         
/*     */         } 
/*     */       }
/* 181 */     } else if (this.action.isStackPushOperator()) {
/*     */       
/* 183 */       if (!allowLatent) return true;
/*     */ 
/*     */       
/* 186 */       ScriptAction.onActionSkipped();
/*     */       
/* 188 */       if (this.action.executeStackPush(provider, macro, this, this.rawParams, this.params) && this.actionProcessor.getConditionalExecutionState())
/*     */       {
/* 190 */         this.actionProcessor.pushStack(this, true);
/*     */       }
/*     */       else
/*     */       {
/* 194 */         this.actionProcessor.pushStack(-1, this, false);
/*     */       }
/*     */     
/* 197 */     } else if (this.action.isConditionalOperator()) {
/*     */       
/* 199 */       if (!allowLatent) return true;
/*     */ 
/*     */       
/* 202 */       ScriptAction.onActionSkipped();
/*     */       
/* 204 */       boolean conditional = this.action.executeConditional(provider, macro, this, this.rawParams, this.params);
/* 205 */       this.actionProcessor.pushStack(-1, this, conditional);
/*     */     }
/* 207 */     else if (this.actionProcessor.getConditionalExecutionState()) {
/*     */ 
/*     */       
/* 210 */       ScriptAction.onActionExecuted();
/*     */ 
/*     */       
/* 213 */       IReturnValue returnValue = this.action.execute(provider, macro, this, this.rawParams, this.params);
/*     */       
/* 215 */       if (returnValue != null) {
/*     */         
/* 217 */         handleReturnValue(provider, macro, returnValue);
/*     */       }
/* 219 */       else if (this.outVar != null) {
/*     */         
/* 221 */         throw new ScriptExceptionVoidResult(this.action.toString().toUpperCase());
/*     */       } 
/*     */       
/* 224 */       provider.updateVariableProviders(false);
/*     */ 
/*     */       
/* 227 */       this.state = null;
/*     */     } 
/*     */     
/* 230 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleReturnValue(IScriptActionProvider provider, IMacro macro, IReturnValue returnValue) throws ScriptExceptionVoidResult {
/* 241 */     if (returnValue.getRemoteMessage() != null)
/*     */     {
/* 243 */       provider.actionSendChatMessage(macro, this, returnValue.getRemoteMessage());
/*     */     }
/*     */     
/* 246 */     if (returnValue.getLocalMessage() != null)
/*     */     {
/* 248 */       provider.actionAddChatMessage(returnValue.getLocalMessage());
/*     */     }
/*     */     
/* 251 */     if (this.outVar != null) {
/*     */       
/* 253 */       if (returnValue.isVoid())
/*     */       {
/* 255 */         throw new ScriptExceptionVoidResult(this.action.toString().toUpperCase());
/*     */       }
/*     */       
/* 258 */       String parsedOutVar = ScriptCore.parseVars(provider, macro, this.outVar, false);
/* 259 */       String varName = Variable.getValidVariableOrArraySpecifier(parsedOutVar);
/* 260 */       if (Variable.couldBeArraySpecifier(parsedOutVar) && varName != null) {
/*     */         
/* 262 */         if (returnValue instanceof IReturnValueArray)
/*     */         {
/* 264 */           IReturnValueArray array = (IReturnValueArray)returnValue;
/*     */           
/* 266 */           if (!array.shouldAppend())
/*     */           {
/* 268 */             provider.clearArray(macro, varName);
/*     */           }
/*     */           
/* 271 */           for (String stringValue : array.getStrings())
/*     */           {
/* 273 */             provider.pushValueToArray(macro, varName, stringValue);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 278 */           provider.pushValueToArray(macro, varName, returnValue.getString());
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 283 */         provider.setVariable(macro, parsedOutVar, returnValue);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStopped(IMacroActionProcessor processor, IMacroActionContext context, IMacro macro) {
/* 294 */     if (this.action != null)
/*     */     {
/* 296 */       this.action.onStopped(context.getProvider(), macro, this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean executeStackPop(IMacroActionProcessor processor, IMacroActionContext context, IMacro macro, IMacroAction popAction) {
/* 306 */     return this.action.executeStackPop(context.getProvider(), macro, this, this.rawParams, this.params, popAction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBreak(IMacroActionProcessor processor, IScriptActionProvider provider, IMacro macro, IMacroAction breakAction) {
/* 315 */     return this.action.canBreak(processor, provider, macro, this, breakAction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 324 */     return (this.action != null) ? this.action.toString() : "ERROR";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setState(Object state) {
/* 333 */     this.state = state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IScriptAction getAction() {
/* 342 */     return this.action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getState() {
/* 352 */     return (T)this.state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getParams() {
/* 361 */     return this.params;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRawParams() {
/* 370 */     return this.rawParams;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClocked() {
/* 379 */     return (this.action != null) ? this.action.isClocked() : true;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\MacroAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */