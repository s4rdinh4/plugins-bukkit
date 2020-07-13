/*    */ package net.eq2online.macros.gui.controls;
/*    */ 
/*    */ import bsu;
/*    */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*    */ import net.eq2online.macros.compatibility.GuiControl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiButtonTab
/*    */   extends GuiControl
/*    */ {
/* 17 */   public int tabColour = -1342177280, activeTabColour = -1607257293, enabledTextColour = -1, disabledTextColour = -256;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 22 */   public int selectedHeight = 2;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean active;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GuiButtonTab(int id, int xPosition, int yPosition, int controlWidth, int controlHeight, String displayText) {
/* 41 */     super(id, xPosition, yPosition, controlWidth, controlHeight, displayText);
/*    */   }
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
/*    */   public GuiButtonTab(int id, int xPosition, int yPosition, String displayText) {
/* 54 */     super(id, xPosition, yPosition, displayText);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(bsu minecraft, int mouseX, int mouseY) {
/* 60 */     if (!isVisible())
/*    */       return; 
/* 62 */     b(minecraft, mouseX, mouseY);
/*    */     
/* 64 */     a(getXPosition(), getYPosition(), getXPosition() + getWidth(), getYPosition() + getHeight() + (this.active ? this.selectedHeight : 0), this.active ? this.activeTabColour : this.tabColour);
/* 65 */     a(AbstractionLayer.getFontRenderer(), this.j, getXPosition() + getWidth() / 2, getYPosition() + (getHeight() - 8) / 2, this.l ? (this.active ? this.enabledTextColour : this.disabledTextColour) : -10461088);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\GuiButtonTab.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */