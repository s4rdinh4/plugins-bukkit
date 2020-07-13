/*    */ package net.eq2online.macros.scripting.exceptions;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptExceptionStackOverflow
/*    */   extends ScriptException
/*    */ {
/*    */   private static final long serialVersionUID = 8589588416236445843L;
/*    */   
/*    */   public ScriptExceptionStackOverflow() {
/* 12 */     super("Stack overflow in script");
/*    */   }
/*    */ 
/*    */   
/*    */   public ScriptExceptionStackOverflow(String arg0) {
/* 17 */     super(arg0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ScriptExceptionStackOverflow(Throwable arg0) {
/* 22 */     super(arg0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ScriptExceptionStackOverflow(String arg0, Throwable arg1) {
/* 27 */     super(arg0, arg1);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\exceptions\ScriptExceptionStackOverflow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */