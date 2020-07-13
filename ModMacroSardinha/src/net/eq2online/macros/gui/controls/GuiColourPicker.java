/*     */ package net.eq2online.macros.gui.controls;
/*     */ 
/*     */ import bsu;
/*     */ import bty;
/*     */ import bul;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Color;
/*     */ import java.awt.Rectangle;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.GuiControl;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.gui.shared.GuiControlEx;
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
/*     */ public class GuiColourPicker
/*     */   extends GuiControlEx
/*     */ {
/*     */   private static final int H = 0;
/*     */   private static final int S = 1;
/*     */   private static final int B = 2;
/*     */   private float[] hsb;
/*     */   private int rgb;
/*     */   private int opacity;
/*     */   private bul txtRed;
/*     */   private bul txtGreen;
/*     */   private bul txtBlue;
/*     */   private bul txtAlpha;
/*     */   private GuiControl btnOk;
/*     */   private GuiControl btnCancel;
/*     */   private boolean draggingHS;
/*     */   private boolean draggingB;
/*     */   private boolean draggingA;
/*     */   private Rectangle rectHSArea;
/*     */   private Rectangle rectBArea;
/*     */   private Rectangle rectAArea;
/*  71 */   private GuiDialogBox.DialogResult result = GuiDialogBox.DialogResult.None;
/*     */   
/*     */   private bty fontRenderer;
/*     */ 
/*     */   
/*     */   public GuiColourPicker(bsu minecraft, int controlId, int xPos, int yPos, int initialColour, String displayText) {
/*  77 */     super(minecraft, controlId, xPos, yPos, 231, 173, displayText);
/*     */     
/*  79 */     Color colour = new Color(initialColour);
/*  80 */     this.hsb = Color.RGBtoHSB(colour.getRed(), colour.getGreen(), colour.getBlue(), null);
/*  81 */     this.opacity = initialColour & 0xFF000000;
/*  82 */     if (this.opacity == 16777216) this.opacity = 0;
/*     */     
/*  84 */     this.fontRenderer = minecraft.k;
/*  85 */     this.txtRed = new bul(0, this.fontRenderer, this.h + 188, this.i + 10, 32, 16);
/*  86 */     this.txtGreen = new bul(1, this.fontRenderer, this.h + 188, this.i + 30, 32, 16);
/*  87 */     this.txtBlue = new bul(2, this.fontRenderer, this.h + 188, this.i + 50, 32, 16);
/*  88 */     this.txtAlpha = new bul(3, this.fontRenderer, this.h + 188, this.i + 70, 32, 16);
/*     */     
/*  90 */     this.txtRed.f(3);
/*  91 */     this.txtGreen.f(3);
/*  92 */     this.txtBlue.f(3);
/*  93 */     this.txtAlpha.f(3);
/*     */     
/*  95 */     this.rectHSArea = new Rectangle(this.h + 10, this.i + 10, 128, 128);
/*  96 */     this.rectBArea = new Rectangle(this.h + 143, this.i + 10, 15, 128);
/*  97 */     this.rectAArea = new Rectangle(this.h + 163, this.i + 10, 15, 128);
/*     */     
/*  99 */     this.btnOk = new GuiControl(0, this.h + 9, this.i + 145, 55, 20, LocalisationProvider.getLocalisedString("gui.ok"), 7);
/* 100 */     this.btnCancel = new GuiControl(1, this.h + 70, this.i + 145, 65, 20, LocalisationProvider.getLocalisedString("gui.cancel"), 1);
/*     */     
/* 102 */     updateColour();
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiDialogBox.DialogResult getDialogResult() {
/* 107 */     return this.result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColour() {
/* 112 */     int opacity = (this.opacity == 0) ? 16777216 : this.opacity;
/* 113 */     int rgb = opacity | 0xFFFFFF & Color.HSBtoRGB(this.hsb[0], this.hsb[1], this.hsb[2]);
/* 114 */     return rgb;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawControl(bsu minecraft, int mouseX, int mouseY) {
/* 120 */     b(minecraft, mouseX, mouseY);
/*     */ 
/*     */     
/* 123 */     int hPos = this.h + 10 + (int)(128.0F * this.hsb[0]);
/* 124 */     int sPos = this.i + 10 + 128 - (int)(128.0F * this.hsb[1]);
/* 125 */     int bPos = this.i + 10 + 128 - (int)(128.0F * this.hsb[2]);
/* 126 */     int aPos = this.i + 10 + (256 - (this.opacity >> 24 & 0xFF)) / 2;
/*     */ 
/*     */     
/* 129 */     int brightness = Color.HSBtoRGB(this.hsb[0], this.hsb[1], 1.0F) | 0xFF000000;
/*     */ 
/*     */     
/* 132 */     a(this.h, this.i, this.h + this.f, this.i + this.g, -1442840576);
/* 133 */     a(this.h + 9, this.i + 9, this.h + 139, this.i + 139, -6250336);
/* 134 */     a(this.h + 142, this.i + 9, this.h + 159, this.i + 139, -6250336);
/* 135 */     a(this.h + 162, this.i + 9, this.h + 179, this.i + 139, -6250336);
/* 136 */     a(this.h + 187, this.i + 105, this.h + 221, this.i + 139, -6250336);
/*     */ 
/*     */     
/* 139 */     AbstractionLayer.bindTexture(ResourceLocations.COLOURPICKER_PICKER);
/* 140 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 141 */     drawTexturedModalRect(this.h + 10, this.i + 10, this.h + 138, this.i + 138, 0, 0, 256, 256);
/* 142 */     drawCrossHair(hPos, sPos, 5, 1, -16777216);
/*     */ 
/*     */     
/* 145 */     a(this.h + 143, this.i + 10, this.h + 158, this.i + 138, brightness, -16777216);
/* 146 */     drawRotText(this.fontRenderer, "Luminosity", this.h + 150, this.i + 74, -16777216, false);
/* 147 */     a(this.h + 142, bPos - 1, this.h + 159, bPos + 1, -1);
/*     */ 
/*     */     
/* 150 */     a(this.h + 163, this.i + 10, this.h + 178, this.i + 138, -1, -16777216);
/* 151 */     drawRotText(this.fontRenderer, "Opacity", this.h + 170, this.i + 74, -16777216, false);
/* 152 */     a(this.h + 162, aPos - 1, this.h + 179, aPos + 1, -1);
/*     */ 
/*     */     
/* 155 */     AbstractionLayer.bindTexture(ResourceLocations.COLOURPICKER_CHECKER);
/* 156 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 157 */     drawTexturedModalRect(this.h + 188, this.i + 106, this.h + 220, this.i + 138, 0, 0, 1024, 1024);
/* 158 */     a(this.h + 188, this.i + 106, this.h + 220, this.i + 138, this.rgb);
/*     */ 
/*     */     
/* 161 */     this.txtRed.g();
/* 162 */     this.txtGreen.g();
/* 163 */     this.txtBlue.g();
/* 164 */     this.txtAlpha.g();
/*     */     
/* 166 */     this.btnOk.a(minecraft, mouseX, mouseY);
/* 167 */     this.btnCancel.a(minecraft, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateCursorCounter() {
/* 172 */     this.txtRed.a();
/* 173 */     this.txtGreen.a();
/* 174 */     this.txtBlue.a();
/* 175 */     this.txtAlpha.a();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateColour() {
/* 180 */     this.rgb = this.opacity | 0xFFFFFF & Color.HSBtoRGB(this.hsb[0], this.hsb[1], this.hsb[2]);
/* 181 */     this.txtRed.a(String.valueOf(this.rgb >> 16 & 0xFF));
/* 182 */     this.txtGreen.a(String.valueOf(this.rgb >> 8 & 0xFF));
/* 183 */     this.txtBlue.a(String.valueOf(this.rgb & 0xFF));
/* 184 */     this.txtAlpha.a(String.valueOf(this.opacity >> 24 & 0xFF));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateColourFromTextEntry() {
/* 189 */     int currentRed = this.rgb >> 16 & 0xFF;
/* 190 */     int currentGreen = this.rgb >> 8 & 0xFF;
/* 191 */     int currentBlue = this.rgb & 0xFF;
/* 192 */     int currentOpacity = this.opacity >> 24 & 0xFF;
/*     */     
/* 194 */     currentRed = (int)clamp(tryParseInt(this.txtRed.b(), currentRed), 0.0F, 255.0F);
/* 195 */     currentGreen = (int)clamp(tryParseInt(this.txtGreen.b(), currentGreen), 0.0F, 255.0F);
/* 196 */     currentBlue = (int)clamp(tryParseInt(this.txtBlue.b(), currentBlue), 0.0F, 255.0F);
/* 197 */     currentOpacity = (int)clamp(tryParseInt(this.txtAlpha.b(), currentOpacity), 0.0F, 255.0F);
/*     */     
/* 199 */     this.hsb = Color.RGBtoHSB(currentRed, currentGreen, currentBlue, null);
/* 200 */     this.opacity = currentOpacity << 24 & 0xFF000000;
/* 201 */     updateColour();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int tryParseInt(String text, int defaultValue) {
/*     */     try {
/* 208 */       return Integer.parseInt(text);
/*     */     } catch (Exception ex) {
/* 210 */       return "".equals(text) ? 0 : defaultValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void b(bsu minecraft, int mouseX, int mouseY) {
/* 219 */     super.b(minecraft, mouseX, mouseY);
/*     */     
/* 221 */     if (this.draggingHS) {
/*     */       
/* 223 */       this.hsb[0] = clamp((mouseX - this.h - 10), 0.0F, 128.0F) / 128.0F;
/* 224 */       this.hsb[1] = (128.0F - clamp((mouseY - this.i - 10), 0.0F, 128.0F)) / 128.0F;
/* 225 */       updateColour();
/*     */     } 
/*     */     
/* 228 */     if (this.draggingB) {
/*     */       
/* 230 */       this.hsb[2] = (128.0F - clamp((mouseY - this.i - 10), 0.0F, 128.0F)) / 128.0F;
/* 231 */       updateColour();
/*     */     } 
/*     */     
/* 234 */     if (this.draggingA) {
/*     */       
/* 236 */       this.opacity = (mouseY - this.i < 11) ? -16777216 : (128 - (int)clamp((mouseY - this.i - 10), 0.0F, 128.0F) << 25 & 0xFF000000);
/* 237 */       updateColour();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean c(bsu minecraft, int mouseX, int mouseY) {
/* 247 */     if (super.c(minecraft, mouseX, mouseY)) {
/*     */ 
/*     */       
/* 250 */       if (this.btnOk.c(minecraft, mouseX, mouseY)) {
/* 251 */         this.result = GuiDialogBox.DialogResult.OK;
/*     */       }
/* 253 */       if (this.btnCancel.c(minecraft, mouseX, mouseY)) {
/* 254 */         this.result = GuiDialogBox.DialogResult.Cancel;
/*     */       }
/* 256 */       if (this.rectHSArea.contains(mouseX, mouseY)) {
/* 257 */         this.draggingHS = true;
/*     */       }
/* 259 */       if (this.rectBArea.contains(mouseX, mouseY)) {
/* 260 */         this.draggingB = true;
/*     */       }
/* 262 */       if (this.rectAArea.contains(mouseX, mouseY)) {
/* 263 */         this.draggingA = true;
/*     */       }
/* 265 */       this.txtRed.a(mouseX, mouseY, 0);
/* 266 */       this.txtGreen.a(mouseX, mouseY, 0);
/* 267 */       this.txtBlue.a(mouseX, mouseY, 0);
/* 268 */       this.txtAlpha.a(mouseX, mouseY, 0);
/*     */       
/* 270 */       return true;
/*     */     } 
/* 272 */     if (this.l)
/*     */     {
/* 274 */       this.result = GuiDialogBox.DialogResult.Cancel;
/*     */     }
/*     */     
/* 277 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY) {
/* 286 */     this.draggingHS = false;
/* 287 */     this.draggingB = false;
/* 288 */     this.draggingA = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean textBoxKeyTyped(char keyChar, int keyCode) {
/* 293 */     this.txtRed.a(keyChar, keyCode);
/* 294 */     this.txtGreen.a(keyChar, keyCode);
/* 295 */     this.txtBlue.a(keyChar, keyCode);
/* 296 */     this.txtAlpha.a(keyChar, keyCode);
/* 297 */     updateColourFromTextEntry();
/*     */     
/* 299 */     if (keyCode == 15)
/*     */     {
/* 301 */       if (this.txtRed.m()) {
/*     */         
/* 303 */         this.txtRed.b(false);
/* 304 */         this.txtGreen.b(true);
/* 305 */         this.txtBlue.b(false);
/* 306 */         this.txtAlpha.b(false);
/*     */       }
/* 308 */       else if (this.txtGreen.m()) {
/*     */         
/* 310 */         this.txtRed.b(false);
/* 311 */         this.txtGreen.b(false);
/* 312 */         this.txtBlue.b(true);
/* 313 */         this.txtAlpha.b(false);
/*     */       }
/* 315 */       else if (this.txtBlue.m()) {
/*     */         
/* 317 */         this.txtRed.b(false);
/* 318 */         this.txtGreen.b(false);
/* 319 */         this.txtBlue.b(false);
/* 320 */         this.txtAlpha.b(true);
/*     */       }
/*     */       else {
/*     */         
/* 324 */         this.txtRed.b(true);
/* 325 */         this.txtGreen.b(false);
/* 326 */         this.txtBlue.b(false);
/* 327 */         this.txtAlpha.b(false);
/*     */       } 
/*     */     }
/*     */     
/* 331 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float clamp(float value, float min, float max) {
/* 336 */     return Math.min(Math.max(value, min), max);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\GuiColourPicker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */