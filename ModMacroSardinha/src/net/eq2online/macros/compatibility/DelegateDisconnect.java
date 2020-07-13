/*    */ package net.eq2online.macros.compatibility;
/*    */ 
/*    */ import bug;
/*    */ import bwy;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DelegateDisconnect
/*    */   extends bwy
/*    */ {
/*    */   public void a(int mouseX, int mouseY, float partialTick) {
/* 22 */     super.a(mouseX, mouseY, partialTick);
/*    */     
/*    */     try {
/* 25 */       a(new bug(1, 0, 0, ""));
/*    */     }
/* 27 */     catch (IOException iOException) {}
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\compatibility\DelegateDisconnect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */