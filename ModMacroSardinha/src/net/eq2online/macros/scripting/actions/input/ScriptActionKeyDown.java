/*    */ package net.eq2online.macros.scripting.actions.input;
/*    */ 
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionKeyDown
/*    */   extends ScriptActionKeyUp
/*    */ {
/*    */   public ScriptActionKeyDown(ScriptContext context) {
/*  9 */     super(context, "keydown");
/* 10 */     this.keyState = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\input\ScriptActionKeyDown.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */