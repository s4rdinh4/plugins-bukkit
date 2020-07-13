/*    */ package net.eq2online.macros.scripting.exceptions;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptExceptionInvalidContextSwitch
/*    */   extends ScriptException
/*    */ {
/*    */   private static final long serialVersionUID = 2725499246595774741L;
/*    */   
/*    */   public ScriptExceptionInvalidContextSwitch() {
/* 12 */     super("Invalid context switch in script");
/*    */   }
/*    */ 
/*    */   
/*    */   public ScriptExceptionInvalidContextSwitch(String arg0) {
/* 17 */     super(arg0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ScriptExceptionInvalidContextSwitch(Throwable arg0) {
/* 22 */     super(arg0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ScriptExceptionInvalidContextSwitch(String arg0, Throwable arg1) {
/* 27 */     super(arg0, arg1);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\exceptions\ScriptExceptionInvalidContextSwitch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */