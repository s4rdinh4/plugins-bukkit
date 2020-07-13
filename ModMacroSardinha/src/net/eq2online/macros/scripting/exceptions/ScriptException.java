/*    */ package net.eq2online.macros.scripting.exceptions;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -1410715644061148289L;
/*    */   
/*    */   public ScriptException() {}
/*    */   
/*    */   public ScriptException(String arg0) {
/* 16 */     super(arg0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ScriptException(Throwable arg0) {
/* 21 */     super(arg0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ScriptException(String arg0, Throwable arg1) {
/* 26 */     super(arg0, arg1);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\exceptions\ScriptException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */