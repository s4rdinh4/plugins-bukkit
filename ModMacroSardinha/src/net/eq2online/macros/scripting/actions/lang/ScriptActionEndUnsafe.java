/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionEndUnsafe
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionEndUnsafe(ScriptContext context) {
/* 11 */     super(context, "endunsafe");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 17 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isStackPopOperator() {
/* 23 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matchesConditionalOperator(IScriptAction action) {
/* 29 */     return action instanceof ScriptActionUnsafe;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionEndUnsafe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */