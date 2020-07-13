/*    */ package net.eq2online.macros.core;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacroActionContext;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MacroActionContext
/*    */   implements IMacroActionContext
/*    */ {
/*    */   private final ScriptContext scriptContext;
/*    */   private final IScriptActionProvider scriptActionProvider;
/*    */   private final IVariableProvider contextVariableProvider;
/*    */   
/*    */   public MacroActionContext(ScriptContext scriptContext, IScriptActionProvider scriptActionProvider, IVariableProvider contextVariableProvider) {
/* 18 */     this.scriptContext = scriptContext;
/* 19 */     this.scriptActionProvider = scriptActionProvider;
/* 20 */     this.contextVariableProvider = contextVariableProvider;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ScriptContext getScriptContext() {
/* 26 */     return this.scriptContext;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IScriptActionProvider getProvider() {
/* 32 */     return this.scriptActionProvider;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IVariableProvider getVariableProvider() {
/* 38 */     return this.contextVariableProvider;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\MacroActionContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */