/*     */ package net.eq2online.macros.gui.controls;
/*     */ 
/*     */ import bsu;
/*     */ import bty;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.GuiControl;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiCheckBox
/*     */   extends GuiControl
/*     */ {
/*     */   public enum DisplayStyle
/*     */   {
/*  27 */     Button,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  32 */     CheckBox,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  37 */     LayoutButton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   public DisplayStyle style = DisplayStyle.CheckBox;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checked;
/*     */ 
/*     */   
/*  50 */   public int mouseOverColour = 16777120;
/*     */   
/*  52 */   public int textColour = 14737632;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiCheckBox(int id, int xPosition, int yPosition, String displayText, boolean checked) {
/*  65 */     super(id, xPosition, yPosition, displayText);
/*     */     
/*  67 */     a(AbstractionLayer.getFontRenderer().a(displayText) + 20);
/*  68 */     this.checked = checked;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiCheckBox(int id, int xPosition, int yPosition, String displayText, boolean checked, DisplayStyle style) {
/*  82 */     super(id, xPosition, yPosition, displayText);
/*     */     
/*  84 */     a(AbstractionLayer.getFontRenderer().a(displayText) + 20);
/*  85 */     this.checked = checked;
/*  86 */     this.style = style;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiCheckBox(int id, int xPosition, int yPosition, int width, int height, String displayText, boolean checked) {
/* 102 */     super(id, xPosition, yPosition, width, height, displayText);
/*     */     
/* 104 */     this.checked = checked;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiCheckBox(int id, int xPosition, int yPosition, int width, int height, String displayText, boolean checked, DisplayStyle style) {
/* 120 */     super(id, xPosition, yPosition, width, height, displayText);
/*     */     
/* 122 */     this.checked = checked;
/* 123 */     this.style = style;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawCheckboxAt(bsu minecraft, int mouseX, int mouseY, int yPos) {
/* 128 */     setYPosition(yPos);
/* 129 */     a(minecraft, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(bsu minecraft, int mouseX, int mouseY) {
/* 143 */     if (!isVisible())
/*     */       return; 
/* 145 */     bty fontrenderer = AbstractionLayer.getFontRenderer();
/*     */     
/* 147 */     boolean mouseOver = (mouseX >= getXPosition() && mouseY >= getYPosition() && mouseX < getXPosition() + getWidth() && mouseY < getYPosition() + getHeight());
/*     */     
/* 149 */     if (this.style == DisplayStyle.Button) {
/*     */       
/* 151 */       super.a(minecraft, mouseX, mouseY);
/*     */     }
/* 153 */     else if (this.style == DisplayStyle.CheckBox) {
/*     */       
/* 155 */       AbstractionLayer.bindTexture(ResourceLocations.MAIN);
/* 156 */       GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 158 */       int u = this.checked ? 24 : 0;
/* 159 */       int y = getYPosition() + (getHeight() - 12) / 2;
/*     */       
/* 161 */       drawTexturedModalRect(getXPosition(), y, getXPosition() + 12, y + 12, u, 104, u + 24, 128);
/* 162 */       b(minecraft, mouseX, mouseY);
/*     */       
/* 164 */       c(fontrenderer, this.j, getXPosition() + 16, getYPosition() + (getHeight() - 8) / 2, isEnabled() ? (mouseOver ? this.mouseOverColour : this.textColour) : -6250336);
/*     */     }
/*     */     else {
/*     */       
/* 168 */       a(getXPosition(), getYPosition(), getXPosition() + getWidth(), getYPosition() + getHeight(), this.checked ? -256 : -8355712);
/* 169 */       a(getXPosition() + 1, getYPosition() + 1, getXPosition() + getWidth() - 1, getYPosition() + getHeight() - 1, mouseOver ? -13421773 : -16777216);
/* 170 */       a(fontrenderer, this.j, getXPosition() + getWidth() / 2, getYPosition() + (getHeight() - 8) / 2, isEnabled() ? (mouseOver ? this.mouseOverColour : (this.checked ? -256 : this.textColour)) : -6250336);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean c(bsu minecraft, int mouseX, int mouseY) {
/* 183 */     if (super.c(minecraft, mouseX, mouseY)) {
/*     */       
/* 185 */       this.checked = !this.checked;
/* 186 */       return true;
/*     */     } 
/*     */     
/* 189 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\GuiCheckBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */