/*    */ package net.eq2online.macros.gui.screens;
/*    */ 
/*    */ import bvx;
/*    */ import bxf;
/*    */ import java.io.IOException;
/*    */ import net.eq2online.macros.core.MacroModCore;
/*    */ import net.eq2online.macros.event.BuiltinEvents;
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
/*    */ 
/*    */ 
/*    */ public class GuiChatFilterable
/*    */   extends bvx
/*    */ {
/*    */   private boolean canType = false;
/*    */   
/*    */   public GuiChatFilterable() {}
/*    */   
/*    */   public GuiChatFilterable(String initialText) {
/* 31 */     super(initialText);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void e() {
/* 37 */     super.e();
/* 38 */     this.canType = true;
/* 39 */     this.a.f(1024);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void a(char keyChar, int keyCode) throws IOException {
/* 45 */     if (!this.canType)
/*    */       return; 
/* 47 */     if (keyCode == 28 || keyCode == 156) {
/*    */       
/* 49 */       String text = this.a.b().trim();
/*    */       
/* 51 */       if (text.length() > 0) {
/*    */         
/* 53 */         this.j.q.d().a(text);
/* 54 */         MacroModCore.sendEvent(BuiltinEvents.onFilterableChat.getName(), 100, new String[] { text.replace("|", "\\|") });
/*    */       } 
/*    */       
/* 57 */       this.j.a((bxf)null);
/*    */     }
/*    */     else {
/*    */       
/* 61 */       super.a(keyChar, keyCode);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\screens\GuiChatFilterable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */