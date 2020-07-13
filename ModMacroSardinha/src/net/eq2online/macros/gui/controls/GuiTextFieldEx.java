/*     */ package net.eq2online.macros.gui.controls;
/*     */ 
/*     */ import bty;
/*     */ import bul;
/*     */ import com.mumfrey.liteloader.client.overlays.IGuiTextField;
/*     */ import net.eq2online.macros.interfaces.IHighlighter;
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
/*     */ public class GuiTextFieldEx
/*     */   extends bul
/*     */ {
/*     */   protected int xPos;
/*     */   protected int yPos;
/*     */   protected int i;
/*     */   protected int j;
/*     */   public String allowedCharacters;
/*  28 */   public int minStringLength = 0;
/*     */   
/*     */   protected bty fontRenderer;
/*     */   
/*  32 */   protected int cursorPos = 0;
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
/*     */   public GuiTextFieldEx(int id, bty fontrenderer, int xPos, int yPos, int width, int height, String initialText, String allowedCharacters, int maxStringLength) {
/*  47 */     super(id, fontrenderer, xPos, yPos, width, height);
/*  48 */     this.allowedCharacters = allowedCharacters;
/*  49 */     f(maxStringLength);
/*  50 */     a(initialText);
/*     */     
/*  52 */     this.i = width;
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
/*     */   public GuiTextFieldEx(int id, bty fontrenderer, int xPos, int yPos, int width, int height, String initialText) {
/*  68 */     super(id, fontrenderer, xPos, yPos, width, height);
/*  69 */     f(65536);
/*  70 */     a(initialText);
/*     */     
/*  72 */     this.i = width;
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
/*     */   public GuiTextFieldEx(int id, bty fontrenderer, int xPos, int yPos, int width, int height, int initialValue, int digits) {
/*  88 */     super(id, fontrenderer, xPos, yPos, width, height);
/*  89 */     f(digits);
/*  90 */     a(String.valueOf(initialValue));
/*  91 */     this.allowedCharacters = "0123456789";
/*  92 */     this.i = width;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(char keyChar, int keyCode) {
/*  98 */     if (this.allowedCharacters == null || this.allowedCharacters.indexOf(keyChar) >= 0 || keyCode == 203 || keyCode == 205 || keyCode == 199 || keyCode == 207 || keyCode == 211 || keyCode == 14 || keyChar == '\003' || keyChar == '\026' || keyChar == '\030')
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 104 */       return super.a(keyChar, keyCode);
/*     */     }
/*     */     
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void f() {
/*     */     try {
/* 118 */       super.f();
/*     */     }
/* 120 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSizeAndPosition(int xPos, int yPos, int width, int height) {
/* 125 */     setPosition(xPos, yPos);
/* 126 */     setSize(width, height);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSize(int width, int height) {
/* 131 */     ((IGuiTextField)this).setInternalWidth(width);
/* 132 */     ((IGuiTextField)this).setHeight(height);
/*     */     
/* 134 */     this.i = width;
/* 135 */     this.j = height;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPosition(int xPos, int yPos) {
/* 140 */     ((IGuiTextField)this).setXPosition(xPos);
/* 141 */     ((IGuiTextField)this).setYPosition(yPos);
/*     */     
/* 143 */     this.xPos = xPos;
/* 144 */     this.yPos = yPos;
/*     */   }
/*     */ 
/*     */   
/*     */   public void scrollToEnd() {
/* 149 */     e(0);
/* 150 */     e(b().length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e(int pos) {
/* 159 */     super.e(pos);
/* 160 */     super.e(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void syncPrivateMembers() {
/* 168 */     this.xPos = ((IGuiTextField)this).getXPosition();
/* 169 */     this.yPos = ((IGuiTextField)this).getYPosition();
/* 170 */     this.i = ((IGuiTextField)this).getInternalWidth();
/* 171 */     this.j = ((IGuiTextField)this).getHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCursorLocation() {
/* 176 */     return this.cursorPos;
/*     */   }
/*     */ 
/*     */   
/*     */   public void insertText(String appendCode) {
/* 181 */     if (m())
/*     */     {
/* 183 */       b(appendCode);
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
/*     */   public void drawTextBox(int xPos, int yPos, int width, int height, IHighlighter highlighter) {
/* 199 */     setSizeAndPosition(xPos, yPos, width, height);
/*     */     
/* 201 */     drawHighlightTextBox(xPos, yPos, width, height, highlighter);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawHighlightTextBox(int xPos2, int yPos2, int width2, int height2, IHighlighter highlighter) {
/* 206 */     g();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTextBoxAt(int yPos) {
/*     */     try {
/* 213 */       ((IGuiTextField)this).setYPosition(yPos);
/* 214 */       this.yPos = yPos;
/* 215 */       g();
/*     */     }
/* 217 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int drawString(bty fontrenderer, String s, int x, int y, int width, int colour) {
/* 225 */     Boolean cursorOffset = Boolean.valueOf(false);
/*     */     
/* 227 */     if (!s.endsWith("_")) {
/*     */       
/* 229 */       s = s + "_";
/* 230 */       cursorOffset = Boolean.valueOf(true);
/*     */     } 
/*     */     
/* 233 */     int stringWidth = fontrenderer.a(s);
/*     */     
/* 235 */     if (stringWidth <= width - 4) {
/*     */       
/* 237 */       if (cursorOffset.booleanValue()) s = s.substring(0, s.length() - 1); 
/* 238 */       fontrenderer.a(s, x, y, colour);
/* 239 */       width = stringWidth;
/*     */     }
/*     */     else {
/*     */       
/* 243 */       String trimmedText = s;
/* 244 */       int w = fontrenderer.a(trimmedText);
/*     */       
/* 246 */       while (w > width - 4 && trimmedText.length() > 0) {
/*     */         
/* 248 */         trimmedText = trimmedText.substring(1);
/* 249 */         w = fontrenderer.a(trimmedText);
/*     */       } 
/*     */       
/* 252 */       String stub = s.substring(0, s.length() - trimmedText.length());
/* 253 */       int lastColourCodePos = stub.lastIndexOf('§');
/*     */       
/* 255 */       if (lastColourCodePos >= 0)
/*     */       {
/* 257 */         trimmedText = s.substring(lastColourCodePos, lastColourCodePos + 2) + trimmedText;
/*     */       }
/*     */       
/* 260 */       if (cursorOffset.booleanValue()) trimmedText = trimmedText.substring(0, trimmedText.length() - 1); 
/* 261 */       fontrenderer.a(trimmedText, x, y, colour);
/*     */     } 
/*     */ 
/*     */     
/* 265 */     return width;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValueInt(int defaultValue) {
/*     */     try {
/* 272 */       return Integer.parseInt(b());
/*     */     } catch (Exception ex) {
/* 274 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(String text) {
/* 280 */     String filteredText = filterAllowedCharacters(text);
/* 281 */     super.b(filteredText);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String filterAllowedCharacters(String text) {
/* 290 */     if (this.allowedCharacters == null) return text;
/*     */     
/* 292 */     StringBuilder filteredString = new StringBuilder();
/* 293 */     char[] charArray = text.toCharArray();
/* 294 */     int stringLength = charArray.length;
/*     */     
/* 296 */     for (int i = 0; i < stringLength; i++) {
/*     */       
/* 298 */       char charAt = charArray[i];
/*     */       
/* 300 */       if (this.allowedCharacters.indexOf(charAt) > -1) {
/* 301 */         filteredString.append(charAt);
/*     */       }
/*     */     } 
/* 304 */     return filteredString.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\GuiTextFieldEx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */