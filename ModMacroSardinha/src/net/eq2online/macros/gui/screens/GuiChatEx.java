/*    */ package net.eq2online.macros.gui.screens;
/*    */ 
/*    */ import bvx;
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
/*    */ 
/*    */ public class GuiChatEx
/*    */   extends bvx
/*    */ {
/*    */   protected boolean enableKeys = false;
/*    */   
/*    */   public GuiChatEx(String buffer) {
/* 24 */     super(buffer);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void a(char keyChar, int keyCode) throws IOException {
/* 33 */     if (!this.enableKeys)
/*    */       return; 
/* 35 */     super.a(keyChar, keyCode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void e() {
/* 44 */     super.e();
/*    */     
/* 46 */     this.enableKeys = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\screens\GuiChatEx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */