/*     */ package net.eq2online.macros.gui.designable;
/*     */ 
/*     */ import bty;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Rectangle;
/*     */ import java.util.LinkedList;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxControlProperties;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxTextAreaProperties;
/*     */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*     */ import net.eq2online.util.Colour;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DesignableGuiControlTextArea
/*     */   extends DesignableGuiControl
/*     */ {
/*     */   public class TextAreaMessage
/*     */   {
/*     */     public String message;
/*     */     public int updateCounter;
/*     */     
/*     */     public TextAreaMessage(String message) {
/*  28 */       this.message = message;
/*  29 */       this.updateCounter = 0;
/*     */     }
/*     */   }
/*     */   
/*  33 */   protected int messageLifeSpan = 200;
/*     */   
/*  35 */   protected LinkedList<TextAreaMessage> messages = new LinkedList<TextAreaMessage>();
/*     */   
/*  37 */   protected Object messageListLock = new Object();
/*     */   
/*  39 */   protected int lastWidth = 100;
/*     */   
/*  41 */   protected int rowHeight = 9;
/*     */   
/*     */   protected int yPos;
/*     */   
/*  45 */   protected int alpha = 0;
/*     */ 
/*     */   
/*     */   protected DesignableGuiControlTextArea(int id) {
/*  49 */     super(id);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initProperties() {
/*  55 */     setProperty("colour", "FF00FF00");
/*  56 */     setProperty("lifespan", 200);
/*  57 */     setProperty("visible", true);
/*     */ 
/*     */     
/*  60 */     this.backColour &= 0xFFFFFF;
/*  61 */     this.padding = 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getControlType() {
/*  67 */     return "textarea";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getWidgetIsBindable() {
/*  73 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDialogBoxControlProperties getPropertiesDialog(GuiScreenEx parentScreen) {
/*  82 */     setProperty("lifespan", this.messageLifeSpan);
/*  83 */     return (GuiDialogBoxControlProperties)new GuiDialogBoxTextAreaProperties(parentScreen, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPropertyWithValidation(String property, String stringValue, int intValue, boolean boolValue) {
/*  89 */     if (property.equals("lifespan")) setProperty(property, Math.min(Math.max(intValue, 10), 1200)); 
/*  90 */     if (property.equals("colour"))
/*     */     {
/*  92 */       setProperty(property, Colour.sanitiseColour(stringValue, -16711936));
/*     */     }
/*     */     
/*  95 */     super.setPropertyWithValidation(property, stringValue, intValue, boolValue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void update() {
/* 101 */     this.foreColour = Colour.parseColour(getProperty("colour", "00FF00"), -16711936);
/* 102 */     this.messageLifeSpan = Math.min(Math.max(getProperty("lifespan", 200), 10), 1200);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTick() {
/* 108 */     synchronized (this.messageListLock) {
/*     */       
/* 110 */       for (TextAreaMessage message : this.messages)
/*     */       {
/* 112 */         message.updateCounter++;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void draw(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY) {
/* 120 */     if (!isVisible())
/*     */       return; 
/* 122 */     this.lastWidth = boundingBox.width;
/*     */     
/* 124 */     GL.glPushMatrix();
/* 125 */     GL.glTranslatef(boundingBox.x, (boundingBox.y + boundingBox.height), 0.0F);
/*     */     
/* 127 */     GL.glDisableBlend();
/* 128 */     GL.glDisableLighting();
/* 129 */     GL.glDisableDepthTest();
/*     */     
/* 131 */     a(0, this.yPos + this.rowHeight - this.padding, boundingBox.width, 0, this.alpha << 24 | this.backColour);
/*     */     
/* 133 */     this.yPos = -this.rowHeight - this.padding;
/* 134 */     this.alpha = 0;
/*     */     
/* 136 */     synchronized (this.messageListLock) {
/*     */       
/* 138 */       for (TextAreaMessage message : this.messages) {
/*     */         
/* 140 */         if (message.updateCounter < this.messageLifeSpan) {
/*     */           
/* 142 */           if (this.yPos < -boundingBox.height + this.padding)
/*     */             break; 
/* 144 */           float fade = Math.min(Math.max((1.0F - message.updateCounter / this.messageLifeSpan) * 10.0F, 0.0F), 1.0F);
/* 145 */           this.alpha = Math.max(this.alpha, (int)(128.0F * fade * fade));
/*     */           
/* 147 */           if (this.alpha < 40)
/* 148 */             break;  GL.glDisableBlend();
/* 149 */           this.fontRenderer.a(message.message, this.padding, this.yPos, this.foreColour);
/* 150 */           this.yPos -= this.rowHeight;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 155 */     GL.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawWidget(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY, int drawColour) {
/* 161 */     int xMid = boundingBox.x + boundingBox.width / 2;
/* 162 */     int yMid = boundingBox.y + boundingBox.height / 2;
/*     */     
/* 164 */     drawDoubleEndedArrowH(boundingBox.x, (boundingBox.x + boundingBox.width), yMid, GuiScreenEx.guiScaleFactor, 6.0F, this.foreColour);
/* 165 */     drawDoubleEndedArrowV(xMid, boundingBox.y, (boundingBox.y + boundingBox.height), GuiScreenEx.guiScaleFactor, 6.0F, this.foreColour);
/*     */     
/* 167 */     bty fontRenderer = AbstractionLayer.getFontRenderer();
/* 168 */     int nameSize = fontRenderer.a(getName()) / 2;
/*     */     
/* 170 */     a(xMid - nameSize - this.padding, yMid - 4 - this.padding, xMid + nameSize + this.padding, yMid + 4 + this.padding, 0xFF000000 | this.backColour);
/*     */     
/* 172 */     fontRenderer.a(getName(), xMid - nameSize, yMid - 4, this.foreColour);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMessage(String text) {
/* 178 */     synchronized (this.messageListLock) {
/*     */       
/* 180 */       for (String textLine : this.fontRenderer.c(text, this.lastWidth)) {
/*     */         
/* 182 */         TextAreaMessage newLine = new TextAreaMessage(textLine);
/* 183 */         this.messages.add(0, newLine);
/*     */       } 
/*     */       
/* 186 */       for (; this.messages.size() > 100; this.messages.removeLast());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\designable\DesignableGuiControlTextArea.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */