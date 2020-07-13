/*     */ package net.eq2online.macros.gui.list;
/*     */ 
/*     */ import bsu;
/*     */ import bty;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import oa;
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
/*     */ public class ListObjectEditInPlace
/*     */   extends ListObjectEditable
/*     */ {
/*     */   protected boolean editing = false;
/*  26 */   protected String oldText = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   protected String emptyText = "";
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
/*     */   public ListObjectEditInPlace(int id, int iconID, String text, oa itemTexture, String emptyText) {
/*  44 */     super(id, iconID, text, itemTexture);
/*     */     
/*  46 */     this.emptyText = emptyText;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanEditInPlace() {
/*  52 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEditingInPlace() {
/*  58 */     return this.editing;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void beginEditInPlace() {
/*  64 */     this.oldText = this.text;
/*  65 */     this.editing = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void endEditInPlace() {
/*  71 */     this.text = this.oldText;
/*  72 */     this.editing = false;
/*  73 */     this.emptyText = "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean editInPlaceKeyTyped(char keyChar, int keyCode) {
/*  79 */     if (keyCode == 28) {
/*     */       
/*  81 */       this.oldText = this.text;
/*  82 */       this.data = this.text;
/*  83 */       return false;
/*     */     } 
/*     */     
/*  86 */     if (keyCode == 1 || keyCode == 200 || keyCode == 208) {
/*     */       
/*  88 */       this.text = this.oldText;
/*  89 */       return false;
/*     */     } 
/*     */     
/*  92 */     if (keyCode == 14 && this.text.length() > 0)
/*     */     {
/*  94 */       this.text = this.text.substring(0, this.text.length() - 1);
/*     */     }
/*     */     
/*  97 */     if (AbstractionLayer.getChatAllowedCharacters().indexOf(keyChar) >= 0 && this.text.length() < 255)
/*     */     {
/*  99 */       this.text += keyChar;
/*     */     }
/*     */     
/* 102 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean editInPlaceMousePressed(boolean iconEnabled, bsu minecraft, int mouseX, int mouseY, int xPosition, int yPosition, int width, int height) {
/* 108 */     return !this.editing;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void editInPlaceDraw(boolean iconEnabled, bsu minecraft, int mouseX, int mouseY, int xPosition, int yPosition, int width, int height, int updateCounter) {
/* 114 */     a(xPosition - 1, yPosition - 1, xPosition + width + 1, yPosition + height + 1, -6250336);
/* 115 */     a(xPosition, yPosition, xPosition + width, yPosition + height, -16777216);
/*     */     
/* 117 */     if (this.text == "" && this.emptyText != "") {
/* 118 */       c(AbstractionLayer.getFontRenderer(), this.emptyText, xPosition + 4, yPosition + (height - 8) / 2, 5263440);
/*     */     }
/* 120 */     drawStringRightAligned(AbstractionLayer.getFontRenderer(), this.text + ((updateCounter / 6 % 2 == 0) ? "_" : ""), xPosition + 4, yPosition + (height - 8) / 2, width, 14737632);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDraggable() {
/* 126 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawStringRightAligned(bty fontrenderer, String s, int x, int y, int width, int colour) {
/* 134 */     Boolean cursorOffset = Boolean.valueOf(false);
/*     */     
/* 136 */     if (!s.endsWith("_")) {
/*     */       
/* 138 */       s = s + "_";
/* 139 */       cursorOffset = Boolean.valueOf(true);
/*     */     } 
/*     */     
/* 142 */     if (fontrenderer.a(s) <= width - 4) {
/*     */       
/* 144 */       if (cursorOffset.booleanValue()) s = s.substring(0, s.length() - 1); 
/* 145 */       fontrenderer.a(s, x, y, colour);
/*     */     }
/*     */     else {
/*     */       
/* 149 */       String trimmedText = s;
/* 150 */       int w = fontrenderer.a(trimmedText);
/*     */       
/* 152 */       while (w > width - 4 && trimmedText.length() > 0) {
/*     */         
/* 154 */         trimmedText = trimmedText.substring(1);
/* 155 */         w = fontrenderer.a(trimmedText);
/*     */       } 
/*     */       
/* 158 */       if (cursorOffset.booleanValue()) trimmedText = trimmedText.substring(0, trimmedText.length() - 1); 
/* 159 */       fontrenderer.a(trimmedText, x, y, colour);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\list\ListObjectEditInPlace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */