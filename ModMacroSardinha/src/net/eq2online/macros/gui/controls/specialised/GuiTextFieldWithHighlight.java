/*     */ package net.eq2online.macros.gui.controls.specialised;
/*     */ 
/*     */ import bty;
/*     */ import bub;
/*     */ import civ;
/*     */ import ckx;
/*     */ import com.mumfrey.liteloader.client.overlays.IGuiTextField;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import net.eq2online.macros.gui.controls.GuiTextFieldEx;
/*     */ import net.eq2online.macros.interfaces.IHighlighter;
/*     */ import vb;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiTextFieldWithHighlight
/*     */   extends GuiTextFieldEx
/*     */ {
/*     */   private IHighlighter lastHighlighter;
/*     */   private String lastText;
/*     */   private String highlight;
/*     */   protected int m;
/*     */   protected int initialXPosition;
/*     */   protected int initialYPosition;
/*     */   protected int initialWidth;
/*     */   protected int initialHeight;
/*     */   
/*     */   public GuiTextFieldWithHighlight(int id, bty fontrenderer, int xPos, int yPos, int width, int height, String initialText) {
/*  28 */     super(id, fontrenderer, xPos, yPos, width, height, initialText.replace('§', '&'));
/*  29 */     this.fontRenderer = fontrenderer;
/*  30 */     this.initialXPosition = xPos;
/*  31 */     this.initialYPosition = yPos;
/*  32 */     this.initialWidth = width;
/*  33 */     this.initialHeight = height;
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiTextFieldWithHighlight(int id, bty fontrenderer, int xPos, int yPos, int width, int height, int initialValue, int digits) {
/*  38 */     super(id, fontrenderer, xPos, yPos, width, height, initialValue, digits);
/*  39 */     this.fontRenderer = fontrenderer;
/*  40 */     this.initialXPosition = xPos;
/*  41 */     this.initialYPosition = yPos;
/*  42 */     this.initialWidth = width;
/*  43 */     this.initialHeight = height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a() {
/*  52 */     this.m++;
/*  53 */     super.a();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b(boolean newFocused) {
/*  62 */     if (newFocused && !m())
/*     */     {
/*  64 */       this.m = 0;
/*     */     }
/*     */     
/*  67 */     super.b(newFocused);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHighlighter(IHighlighter highlighter) {
/*  72 */     this.lastHighlighter = highlighter;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(String text) {
/*  78 */     super.a(text.replace('§', '&'));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void g() {
/*  87 */     drawHighlightTextBox(this.initialXPosition, this.initialYPosition, this.initialWidth, this.initialHeight, this.lastHighlighter);
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
/*     */   protected void drawHighlightTextBox(int xPos, int yPos, int width, int height, IHighlighter highlighter) {
/* 100 */     if (j()) {
/*     */       
/* 102 */       a(xPos - 1, yPos - 1, xPos + width + 1, yPos + height + 1, -6250336);
/* 103 */       a(xPos, yPos, xPos + width, yPos + height, -16777216);
/*     */     } 
/*     */     
/* 106 */     boolean isEnabled = ((IGuiTextField)this).isEnabled();
/* 107 */     int scrollPos = ((IGuiTextField)this).getLineScrollOffset();
/* 108 */     int selectionStart = i();
/* 109 */     int selectionEnd = o();
/* 110 */     int enabledColour = ((IGuiTextField)this).getTextColor();
/* 111 */     int disabledColour = ((IGuiTextField)this).getDisabledTextColour();
/*     */     
/* 113 */     String text = vb.a(b());
/*     */     
/* 115 */     if (text != this.lastText || highlighter != this.lastHighlighter) {
/*     */       
/* 117 */       this.lastText = text;
/* 118 */       this.lastHighlighter = highlighter;
/* 119 */       this.highlight = highlighter.generateHighlightMask(text);
/*     */     } 
/*     */     
/* 122 */     int fontColour = isEnabled ? enabledColour : disabledColour;
/* 123 */     int adjustedSelectionPos = selectionStart - scrollPos;
/* 124 */     int adjustedSelectionEnd = selectionEnd - scrollPos;
/*     */     
/* 126 */     String trimmedText = this.fontRenderer.a(text.substring(scrollPos), p());
/* 127 */     String trimmedHighlight = this.highlight.substring(scrollPos);
/*     */     
/* 129 */     boolean cursorVisible = (adjustedSelectionPos >= 0 && adjustedSelectionPos <= trimmedText.length());
/* 130 */     boolean drawCursor = (m() && this.m / 6 % 2 == 0 && cursorVisible);
/*     */     
/* 132 */     int textLeft = xPos + 4;
/* 133 */     int textTop = yPos + (height - 8) / 2;
/* 134 */     int currentDrawPosition = textLeft;
/*     */     
/* 136 */     if (adjustedSelectionEnd > trimmedText.length())
/*     */     {
/* 138 */       adjustedSelectionEnd = trimmedText.length();
/*     */     }
/*     */     
/* 141 */     if (trimmedText.length() > 0) {
/*     */       
/* 143 */       String beforeCursor = cursorVisible ? trimmedText.substring(0, adjustedSelectionPos) : trimmedText;
/* 144 */       currentDrawPosition = drawMaskedStringWithShadow(beforeCursor, trimmedHighlight, currentDrawPosition, textTop, fontColour);
/*     */     } 
/*     */     
/* 147 */     boolean adjustSelection = (selectionStart < text.length() || text.length() >= h());
/* 148 */     int cursorLocation = currentDrawPosition;
/* 149 */     this.cursorPos = cursorLocation;
/*     */     
/* 151 */     if (!cursorVisible) {
/*     */       
/* 153 */       cursorLocation = (adjustedSelectionPos <= 0) ? textLeft : (textLeft + width);
/*     */     }
/* 155 */     else if (adjustSelection) {
/*     */       
/* 157 */       cursorLocation--;
/* 158 */       currentDrawPosition--;
/*     */     } 
/*     */     
/* 161 */     if (trimmedText.length() > 0 && cursorVisible && adjustedSelectionPos < trimmedText.length() && adjustedSelectionPos >= 0)
/*     */     {
/* 163 */       currentDrawPosition = drawMaskedStringWithShadow(trimmedText.substring(adjustedSelectionPos), trimmedHighlight.substring(adjustedSelectionPos), currentDrawPosition, textTop, fontColour);
/*     */     }
/*     */     
/* 166 */     if (drawCursor)
/*     */     {
/* 168 */       if (adjustSelection) {
/*     */         
/* 170 */         bub.a(cursorLocation, textTop - 1, cursorLocation + 1, textTop + 1 + this.fontRenderer.a, -3092272);
/*     */       }
/*     */       else {
/*     */         
/* 174 */         this.fontRenderer.a("_", cursorLocation, textTop, fontColour);
/*     */       } 
/*     */     }
/*     */     
/* 178 */     if (adjustedSelectionPos > trimmedText.length())
/*     */     {
/* 180 */       adjustedSelectionPos = trimmedText.length();
/*     */     }
/*     */     
/* 183 */     if (adjustedSelectionEnd != adjustedSelectionPos) {
/*     */       
/* 185 */       int selectionWidth = textLeft + this.fontRenderer.a(trimmedText.substring(0, adjustedSelectionEnd));
/* 186 */       drawInverseRect(cursorLocation, textTop - 1, selectionWidth - 1, textTop + 1 + this.fontRenderer.a);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public int drawMaskedStringWithShadow(String text, String highlight, int xPosition, int yPosition, int colour) {
/* 202 */     StringBuilder highlightedText = new StringBuilder();
/*     */     
/* 204 */     char lastColour = 'z';
/*     */     
/* 206 */     for (int i = 0; i < text.length() && i < highlight.length(); i++) {
/*     */       
/* 208 */       char col = highlight.charAt(i);
/* 209 */       char c = text.charAt(i);
/*     */       
/* 211 */       if (col != lastColour) {
/*     */         
/* 213 */         lastColour = col;
/* 214 */         highlightedText.append('§').append(col);
/*     */       } 
/*     */       
/* 217 */       highlightedText.append(c);
/*     */     } 
/*     */     
/* 220 */     return this.fontRenderer.a(highlightedText.toString(), xPosition, yPosition, colour);
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
/*     */   private void drawInverseRect(int x1, int y1, int x2, int y2) {
/* 233 */     if (x1 < x2) {
/*     */       
/* 235 */       int i = x1;
/* 236 */       x1 = x2;
/* 237 */       x2 = i;
/*     */     } 
/*     */     
/* 240 */     if (y1 < y2) {
/*     */       
/* 242 */       int j = y1;
/* 243 */       y1 = y2;
/* 244 */       y2 = j;
/*     */     } 
/*     */     
/* 247 */     ckx tessellator = ckx.a();
/* 248 */     civ worldRender = tessellator.c();
/* 249 */     GL.glColor4f(0.0F, 0.0F, 255.0F, 255.0F);
/* 250 */     GL.glDisableTexture2D();
/* 251 */     GL.glEnableColorLogic();
/* 252 */     GL.glLogicOp(5387);
/* 253 */     worldRender.b();
/* 254 */     worldRender.b(x1, y2, 0.0D);
/* 255 */     worldRender.b(x2, y2, 0.0D);
/* 256 */     worldRender.b(x2, y1, 0.0D);
/* 257 */     worldRender.b(x1, y1, 0.0D);
/* 258 */     tessellator.b();
/* 259 */     GL.glDisableColorLogic();
/* 260 */     GL.glEnableTexture2D();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\specialised\GuiTextFieldWithHighlight.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */