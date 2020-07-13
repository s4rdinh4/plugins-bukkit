/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionEndif
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionEndif(ScriptContext context) {
/* 11 */     super(context, "endif");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isStackPopOperator() {
/* 17 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matchesConditionalOperator(IScriptAction action) {
/* 23 */     return action instanceof ScriptActionIf;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionEndif.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */