/*    */ package net.eq2online.macros.scripting.actions.option;
/*    */ 
/*    */ import btr;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ 
/*    */ public class ScriptActionChatOpacity
/*    */   extends ScriptActionGamma<btr>
/*    */ {
/*    */   public ScriptActionChatOpacity(ScriptContext context) {
/* 11 */     super(context, "chatopacity", btr.s, 10.0F, 100.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\option\ScriptActionChatOpacity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */