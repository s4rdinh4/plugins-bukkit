/*     */ package net.eq2online.macros.gui.designable;
/*     */ 
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Rectangle;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxButtonProperties;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxControlProperties;
/*     */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*     */ import net.eq2online.util.Colour;
/*     */ import vb;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DesignableGuiControlButton
/*     */   extends DesignableGuiControl
/*     */ {
/*     */   protected DesignableGuiControlButton(int id) {
/*  18 */     super(id);
/*  19 */     this.padding = 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initProperties() {
/*  25 */     setProperty("text", "Button Text");
/*  26 */     setProperty("hide", false);
/*  27 */     setProperty("sticky", false);
/*  28 */     setProperty("colour", "FF00FF00");
/*  29 */     setProperty("background", "B0000000");
/*  30 */     setProperty("visible", true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getControlType() {
/*  36 */     return "button";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getWidgetIsBindable() {
/*  42 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVisible() {
/*  51 */     return ((!getProperty("hide", false) || MacroModCore.getMacroManager().isMacroBound(this.id, true)) && getProperty("visible", true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCloseGuiOnClick() {
/*  60 */     return !getProperty("sticky", false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPropertyWithValidation(String property, String stringValue, int intValue, boolean boolValue) {
/*  66 */     if (property.equals("text")) setProperty(property, stringValue); 
/*  67 */     if (property.equals("hide") || property.equals("sticky")) setProperty(property, boolValue); 
/*  68 */     if (property.equals("colour") || property.equals("background"))
/*     */     {
/*  70 */       setProperty(property, Colour.sanitiseColour(stringValue, property.equals("colour") ? -16711936 : -1442840576));
/*     */     }
/*     */     
/*  73 */     super.setPropertyWithValidation(property, stringValue, intValue, boolValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getText() {
/*  78 */     return getProperty("text", "Button Text");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void update() {
/*  89 */     this.foreColour = Colour.parseColour(getProperty("colour", "FF00FF00"), -16711936);
/*  90 */     this.backColour = Colour.parseColour(getProperty("background", "B0000000"), -1442840576);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTick() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void draw(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY) {
/* 101 */     int foreColour = boundingBox.contains(mouseX, mouseY) ? -3342388 : this.foreColour;
/*     */     
/* 103 */     if (drawButton(boundingBox, foreColour, this.backColour, true)) {
/* 104 */       drawRectOutline(boundingBox, foreColour, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawWidget(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY, int drawColour) {
/* 110 */     drawButton(boundingBox, drawColour, 0, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean drawButton(Rectangle boundingBox, int foreColour, int backColour, boolean playback) {
/* 119 */     if (playback && !isVisible()) return false;
/*     */     
/* 121 */     this.e = -this.zIndex;
/* 122 */     if (backColour != 0) {
/* 123 */       a(boundingBox.x, boundingBox.y, boundingBox.x + boundingBox.width, boundingBox.y + boundingBox.height, backColour);
/*     */     }
/* 125 */     GL.glPushMatrix();
/*     */     
/* 127 */     String[] text = MacroModCore.convertAmpCodes(getText()).split("\\\\n");
/*     */     
/* 129 */     float textWidth = 0.0F;
/* 130 */     float textHeight = 8.0F;
/*     */     
/* 132 */     for (int row = 0; row < text.length; row++) {
/*     */       
/* 134 */       textWidth = Math.max(textWidth, (this.fontRenderer.a(vb.a(text[row])) + 4));
/* 135 */       if (row > 0) textHeight += 10.0F;
/*     */     
/*     */     } 
/* 138 */     if (textWidth > (boundingBox.width - this.padding * 2)) {
/*     */       
/* 140 */       float scaleFactor = boundingBox.width / textWidth;
/* 141 */       GL.glTranslatef((boundingBox.x + this.padding), boundingBox.y + (boundingBox.height - textHeight * scaleFactor) / 2.0F, 0.0F);
/* 142 */       GL.glScalef(scaleFactor, scaleFactor, 1.0F);
/*     */     }
/*     */     else {
/*     */       
/* 146 */       GL.glTranslatef(boundingBox.x + (boundingBox.width - textWidth) / 2.0F, boundingBox.y + (boundingBox.height - textHeight) / 2.0F, 0.0F);
/*     */     } 
/*     */     
/* 149 */     int offset = 0;
/*     */     
/* 151 */     for (int i = 0; i < text.length; i++) {
/*     */       
/* 153 */       this.fontRenderer.a(text[i], 0, offset, foreColour);
/* 154 */       offset += 10;
/*     */     } 
/*     */     
/* 157 */     GL.glPopMatrix();
/*     */     
/* 159 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDialogBoxControlProperties getPropertiesDialog(GuiScreenEx parentScreen) {
/* 168 */     return (GuiDialogBoxControlProperties)new GuiDialogBoxButtonProperties(parentScreen, this);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\designable\DesignableGuiControlButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */