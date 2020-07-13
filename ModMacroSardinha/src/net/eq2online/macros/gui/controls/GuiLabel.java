/*    */ package net.eq2online.macros.gui.controls;
/*    */ 
/*    */ import bsu;
/*    */ import bty;
/*    */ import net.eq2online.macros.compatibility.GuiControl;
/*    */ 
/*    */ public class GuiLabel
/*    */   extends GuiControl
/*    */ {
/*    */   public int drawColour;
/*    */   
/*    */   public GuiLabel(int id, int xPosition, int yPosition, String displayText, int drawColour) {
/* 13 */     super(id, xPosition, yPosition, displayText);
/* 14 */     this.drawColour = drawColour;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(bsu minecraft, int mouseX, int mouseY) {
/* 23 */     if (this.m) {
/*    */       
/* 25 */       bty fontrenderer = minecraft.k;
/* 26 */       c(fontrenderer, this.j, getXPosition(), getYPosition(), this.drawColour);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean c(bsu minecraft, int mouseX, int mouseY) {
/* 36 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\GuiLabel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */