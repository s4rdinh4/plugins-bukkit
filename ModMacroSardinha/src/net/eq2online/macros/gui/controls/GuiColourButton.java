/*     */ package net.eq2online.macros.gui.controls;
/*     */ 
/*     */ import bsu;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.GuiControl;
/*     */ import net.eq2online.macros.gui.shared.GuiDialogBox;
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
/*     */ public class GuiColourButton
/*     */   extends GuiControl
/*     */ {
/*  21 */   private int colour = -16777216;
/*     */   
/*     */   private GuiColourPicker picker;
/*     */   
/*     */   private boolean pickerClicked = false;
/*     */ 
/*     */   
/*     */   public GuiColourButton(int id, int xPosition, int yPosition, String displayText, int initialColour) {
/*  29 */     super(id, xPosition, yPosition, displayText);
/*  30 */     this.colour = initialColour;
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiColourButton(int id, int xPosition, int yPosition, int controlWidth, int controlHeight, String displayText, int initialColour) {
/*  35 */     super(id, xPosition, yPosition, controlWidth, controlHeight, displayText);
/*  36 */     this.colour = initialColour;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColour() {
/*  41 */     return this.colour;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(bsu minecraft, int mouseX, int mouseY) {
/*  47 */     if (this.m) {
/*     */       
/*  49 */       boolean mouseOver = (mouseX >= this.h && mouseY >= this.i && mouseX < this.h + this.f && mouseY < this.i + this.g);
/*  50 */       int borderColour = mouseOver ? -1 : -6250336;
/*     */       
/*  52 */       a(this.h, this.i, this.h + this.f, this.i + this.g, borderColour);
/*     */       
/*  54 */       int v = Math.min(Math.max((int)(this.g / this.f * 1024.0F), 256), 1024);
/*     */       
/*  56 */       AbstractionLayer.bindTexture(ResourceLocations.COLOURPICKER_CHECKER);
/*  57 */       GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  58 */       drawTexturedModalRect(this.h + 1, this.i + 1, this.h + this.f - 1, this.i + this.g - 1, 0, 0, 1024, v);
/*     */       
/*  60 */       a(this.h + 1, this.i + 1, this.h + this.f - 1, this.i + this.g - 1, this.colour);
/*     */       
/*  62 */       b(minecraft, mouseX, mouseY);
/*     */       
/*  64 */       if (this.j != null && this.j.length() > 0) {
/*     */         
/*  66 */         GL.glEnableColorLogic();
/*  67 */         GL.glLogicOp(5387);
/*  68 */         a(minecraft.k, this.j, this.h + this.f / 2, this.i + (this.g - 8) / 2, -16777216);
/*  69 */         GL.glDisableColorLogic();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawPicker(bsu minecraft, int mouseX, int mouseY) {
/*  76 */     if (this.m && this.picker != null) {
/*     */       
/*  78 */       this.picker.a(minecraft, mouseX, mouseY);
/*     */       
/*  80 */       if (this.picker.getDialogResult() == GuiDialogBox.DialogResult.OK) {
/*     */         
/*  82 */         closePicker(true);
/*     */       }
/*  84 */       else if (this.picker.getDialogResult() == GuiDialogBox.DialogResult.Cancel) {
/*     */         
/*  86 */         closePicker(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closePicker(boolean getColour) {
/*  96 */     if (getColour) this.colour = this.picker.getColour(); 
/*  97 */     this.picker = null;
/*  98 */     this.pickerClicked = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY) {
/* 107 */     if (this.pickerClicked && this.picker != null) {
/*     */       
/* 109 */       this.picker.a(mouseX, mouseY);
/* 110 */       this.pickerClicked = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean c(bsu minecraft, int mouseX, int mouseY) {
/* 120 */     boolean pressed = super.c(minecraft, mouseX, mouseY);
/*     */     
/* 122 */     if (this.picker == null) {
/*     */       
/* 124 */       if (pressed) {
/*     */         
/* 126 */         int xPos = Math.min(this.h + this.f, GuiDialogBox.lastScreenWidth - 233);
/* 127 */         int yPos = Math.min(this.i, GuiDialogBox.lastScreenHeight - 175);
/*     */         
/* 129 */         this.picker = new GuiColourPicker(minecraft, 1, xPos, yPos, this.colour, "Choose colour");
/* 130 */         this.pickerClicked = false;
/*     */       } 
/*     */       
/* 133 */       return pressed;
/*     */     } 
/*     */     
/* 136 */     this.pickerClicked = this.picker.c(minecraft, mouseX, mouseY);
/*     */     
/* 138 */     if (pressed && !this.pickerClicked)
/*     */     {
/* 140 */       closePicker(true);
/*     */     }
/*     */     
/* 143 */     return this.pickerClicked;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean textBoxKeyTyped(char keyChar, int keyCode) {
/* 148 */     return (this.picker != null) ? this.picker.textBoxKeyTyped(keyChar, keyCode) : false;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\GuiColourButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */