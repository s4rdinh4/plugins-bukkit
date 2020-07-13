/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IExpressionEvaluator;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IMacroActionStackEntry;
/*    */ import net.eq2online.macros.scripting.api.IScriptAction;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionElseIf
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionElseIf(ScriptContext context) {
/* 17 */     super(context, "elseif");
/*    */   }
/*    */ 
/*    */   
/*    */   protected ScriptActionElseIf(ScriptContext context, String actionName) {
/* 22 */     super(context, actionName);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isStackPopOperator() {
/* 31 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isConditionalElseOperator(IScriptAction action) {
/* 40 */     return action instanceof ScriptActionIf;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void executeConditionalElse(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params, IMacroActionStackEntry top) {
/* 49 */     if (top.getIfFlag()) {
/*    */       
/* 51 */       top.setConditionalFlag(false);
/*    */       
/*    */       return;
/*    */     } 
/* 55 */     if (params.length > 0) {
/*    */       
/* 57 */       IExpressionEvaluator evaluator = provider.getExpressionEvaluator(macro, ScriptCore.parseVars(provider, macro, params[0], true));
/* 58 */       top.setConditionalFlag((evaluator.evaluate() != 0));
/*    */       
/* 60 */       top.setIfFlag(top.getIfFlag() | top.getConditionalFlag());
/*    */       
/*    */       return;
/*    */     } 
/* 64 */     top.setConditionalFlag(!top.getConditionalFlag());
/* 65 */     top.setElseFlag(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionElseIf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */