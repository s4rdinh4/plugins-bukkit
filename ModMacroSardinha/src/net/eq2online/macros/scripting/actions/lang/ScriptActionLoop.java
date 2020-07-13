/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionLoop
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionLoop(ScriptContext context) {
/* 10 */     super(context, "loop");
/*    */   }
/*    */ 
/*    */   
/*    */   protected ScriptActionLoop(ScriptContext context, String actionName) {
/* 15 */     super(context, actionName);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isStackPopOperator() {
/* 21 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */