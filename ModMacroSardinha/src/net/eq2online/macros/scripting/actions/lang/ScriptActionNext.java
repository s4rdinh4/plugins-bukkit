/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionNext
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionNext(ScriptContext context) {
/* 10 */     super(context, "next");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isStackPopOperator() {
/* 19 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionNext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */