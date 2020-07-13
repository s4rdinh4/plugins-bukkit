/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IExpressionEvaluator;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionIf
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionIf(ScriptContext context) {
/* 15 */     super(context, "if");
/*    */   }
/*    */ 
/*    */   
/*    */   protected ScriptActionIf(ScriptContext context, String actionName) {
/* 20 */     super(context, actionName);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getExpectedPopCommands() {
/* 26 */     return "§e" + toString().toUpperCase() + " §cexpects §dELSEIF§c, §dELSE§c or §dENDIF";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isConditionalOperator() {
/* 32 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean executeConditional(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 38 */     String condition = (params.length > 0) ? params[0] : "flag";
/*    */     
/* 40 */     IExpressionEvaluator evaluator = provider.getExpressionEvaluator(macro, ScriptCore.parseVars(provider, macro, condition, true));
/* 41 */     return (evaluator.evaluate() != 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionIf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */