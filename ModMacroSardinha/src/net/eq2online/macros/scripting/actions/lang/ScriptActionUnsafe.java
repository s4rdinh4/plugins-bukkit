/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionUnsafe
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionUnsafe(ScriptContext context) {
/* 14 */     super(context, "unsafe");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 20 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isConditionalOperator() {
/* 26 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getExpectedPopCommands() {
/* 32 */     return "§eUNSAFE §cexpects §dENDUNSAFE";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean executeConditional(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 38 */     int maxActions = 100;
/*    */     
/* 40 */     if (params.length > 0)
/*    */     {
/* 42 */       maxActions = Math.min(Math.max(ScriptCore.tryParseInt(params[0], 100), 0), 10000);
/*    */     }
/*    */     
/* 45 */     provider.actionBeginUnsafeBlock(macro, instance, maxActions);
/* 46 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean executeStackPop(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params, IMacroAction popAction) {
/* 52 */     provider.actionEndUnsafeBlock(macro, instance);
/*    */     
/* 54 */     return super.executeStackPop(provider, macro, instance, rawParams, params, popAction);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionUnsafe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */