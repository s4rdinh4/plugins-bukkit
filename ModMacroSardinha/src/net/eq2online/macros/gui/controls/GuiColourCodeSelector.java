/*     */ package net.eq2online.macros.gui.controls;
/*     */ 
/*     */ import bub;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiColourCodeSelector
/*     */   extends bub
/*     */ {
/*  13 */   protected static int[] COLOURS = new int[] { -16777216, -16777025, -16728320, -16728129, -4259840, -4259649, -4210944, -4210753, -12566464, -12566273, -12517568, -12517377, -49088, -48897, -192, -1 };
/*     */ 
/*     */   
/*     */   public int width;
/*     */ 
/*     */   
/*     */   public int height;
/*     */ 
/*     */   
/*     */   public int xPosition;
/*     */ 
/*     */   
/*     */   public int yPosition;
/*     */   
/*     */   public int spacing;
/*     */   
/*  29 */   public int colour = -1;
/*     */   
/*     */   public boolean labels = true;
/*     */   
/*  33 */   public static String COLOURCODES = "0123456789abcdef";
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
/*     */   public GuiColourCodeSelector(int width, int height, int xPosition, int yPosition, int spacing) {
/*  45 */     this.width = width;
/*  46 */     this.height = height;
/*  47 */     this.xPosition = xPosition;
/*  48 */     this.yPosition = yPosition;
/*  49 */     this.spacing = spacing;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawColourCodeSelector() {
/*  57 */     int cellWidth = (this.width - this.spacing * 3) / 4;
/*  58 */     int cellHeight = (this.height - this.spacing * 3) / 4;
/*     */     
/*  60 */     a(this.xPosition - this.spacing, this.yPosition - this.height - this.spacing, this.xPosition + this.width + this.spacing, this.yPosition + this.spacing, -16777216);
/*     */     
/*  62 */     for (int colourIndex = 0; colourIndex < 16; colourIndex++) {
/*     */ 
/*     */       
/*  65 */       int x = this.xPosition + colourIndex % 4 * (cellWidth + this.spacing);
/*  66 */       int y = this.yPosition - this.height + colourIndex / 4 * (cellHeight + this.spacing);
/*     */       
/*  68 */       if (colourIndex == this.colour)
/*     */       {
/*  70 */         a(x - this.spacing, y - this.spacing, x + cellWidth + this.spacing, y + cellHeight + this.spacing, -1);
/*     */       }
/*     */       
/*  73 */       a(x, y, x + cellWidth, y + cellHeight, COLOURS[colourIndex]);
/*     */       
/*  75 */       if (this.labels) {
/*  76 */         AbstractionLayer.getFontRenderer().a(COLOURCODES.substring(colourIndex, colourIndex + 1), x + 2, y + 2, -12566464);
/*     */       }
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
/*     */   public boolean mouseClicked(int mouseX, int mouseY) {
/*  89 */     int cellWidth = (this.width - this.spacing * 3) / 4;
/*  90 */     int cellHeight = (this.height - this.spacing * 3) / 4;
/*     */     
/*  92 */     for (int colourIndex = 0; colourIndex < 16; colourIndex++) {
/*     */ 
/*     */       
/*  95 */       int x = this.xPosition + colourIndex % 4 * (cellWidth + this.spacing);
/*  96 */       int y = this.yPosition - this.height + colourIndex / 4 * (cellHeight + this.spacing);
/*     */       
/*  98 */       if (mouseX >= x && mouseX < x + cellWidth && mouseY >= y && mouseY < y + cellHeight) {
/*     */         
/* 100 */         this.colour = colourIndex;
/* 101 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyTyped(char keyChar, int keyCode) {
/* 116 */     if (keyCode > 1 && keyCode < 11) this.colour = keyCode - 1; 
/* 117 */     if (keyCode == 11) this.colour = 0; 
/* 118 */     if (keyCode == 30) this.colour = 10; 
/* 119 */     if (keyCode == 48) this.colour = 11; 
/* 120 */     if (keyCode == 46) this.colour = 12; 
/* 121 */     if (keyCode == 32) this.colour = 13; 
/* 122 */     if (keyCode == 18) this.colour = 14; 
/* 123 */     if (keyCode == 33) this.colour = 15;
/*     */     
/* 125 */     if (this.colour == -1) {
/*     */       
/* 127 */       if (keyCode == 200) this.colour = 12; 
/* 128 */       if (keyCode == 208) this.colour = 0; 
/* 129 */       if (keyCode == 203) this.colour = 3; 
/* 130 */       if (keyCode == 205) this.colour = 1;
/*     */     
/*     */     } else {
/*     */       
/* 134 */       if (keyCode == 200) this.colour = (this.colour + 12) % 16; 
/* 135 */       if (keyCode == 208) this.colour = (this.colour + 4) % 16; 
/* 136 */       if (keyCode == 203) this.colour = (this.colour + 15) % 16; 
/* 137 */       if (keyCode == 205) this.colour = (this.colour + 1) % 16; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\GuiColourCodeSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */