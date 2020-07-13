/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IMacroActionStackEntry;
/*    */ import net.eq2online.macros.scripting.api.IScriptAction;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionElse
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionElse(ScriptContext context) {
/* 15 */     super(context, "else");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isStackPopOperator() {
/* 21 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isConditionalElseOperator(IScriptAction action) {
/* 27 */     return action instanceof ScriptActionIf;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void executeConditionalElse(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params, IMacroActionStackEntry top) {
/* 36 */     top.setConditionalFlag(!top.getIfFlag());
/* 37 */     top.setElseFlag(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionElse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */