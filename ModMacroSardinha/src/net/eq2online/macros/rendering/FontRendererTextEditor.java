/*     */ package net.eq2online.macros.rendering;
/*     */ 
/*     */ import bst;
/*     */ import bsu;
/*     */ import bty;
/*     */ import civ;
/*     */ import ckx;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import cuj;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.nio.IntBuffer;
/*     */ import javax.imageio.ImageIO;
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
/*     */ public class FontRendererTextEditor
/*     */   extends bty
/*     */ {
/*  29 */   private static int LIST_SEL = 256;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   private static int LIST_SELCOL = 257;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  39 */   private static int LIST_ERRORCOL = 258;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   private static int LIST_NORMALCOL = 259;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   private static int LIST_CCCOL = 260;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private static int LIST_HICOL = 261;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   private static int LIST_HICOL2 = 262;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private static int LIST_PARAMCOL = 263;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   private static int LIST_QUOTECOL = 264;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int fixedWidth;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IntBuffer buffer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int fontDisplayLists;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String highLightSymbols;
/*     */ 
/*     */ 
/*     */   
/*     */   public int fontTextureName;
/*     */ 
/*     */ 
/*     */   
/*     */   private oa fontTexture;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontRendererTextEditor(oa fontTexture, int fixedWidth) {
/* 103 */     super(AbstractionLayer.getGameSettings(), fontTexture, AbstractionLayer.getTextureManager(), false); BufferedImage bufferedimage; this.fixedWidth = 7; this.highLightSymbols = "+=-/&%$#|*@'{}[]()";
/*     */     this.fontTextureName = 0;
/* 105 */     this.fixedWidth = fixedWidth;
/*     */ 
/*     */     
/* 108 */     int[] charWidth = new int[256];
/* 109 */     this.fontTexture = fontTexture;
/* 110 */     this.fontTextureName = 0;
/*     */ 
/*     */     
/* 113 */     this.buffer = bst.f(1024);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 119 */       bufferedimage = ImageIO.read(bsu.z().O().a(this.fontTexture).b());
/*     */     }
/* 121 */     catch (IOException ioexception) {
/*     */       
/* 123 */       throw new RuntimeException(ioexception);
/*     */     } 
/*     */     
/* 126 */     this.fontTextureName = cuj.a();
/* 127 */     cuj.a(this.fontTextureName, bufferedimage, false, false);
/* 128 */     this.fontDisplayLists = bst.a(264);
/* 129 */     ckx tessellator = AbstractionLayer.getTessellator();
/* 130 */     civ worldRender = tessellator.c();
/*     */ 
/*     */     
/* 133 */     for (int glyphIndex = 0; glyphIndex < 256; glyphIndex++) {
/*     */ 
/*     */       
/* 136 */       charWidth[glyphIndex] = fixedWidth;
/*     */ 
/*     */       
/* 139 */       GL.glNewList(this.fontDisplayLists + glyphIndex, 4864);
/*     */ 
/*     */       
/* 142 */       int glyphUCoordinate = glyphIndex % 16 * 8;
/* 143 */       int glyphVCoordinate = glyphIndex / 16 * 8;
/*     */ 
/*     */       
/* 146 */       float f = 7.99F, f1 = 0.0F, f2 = 0.0F;
/*     */ 
/*     */       
/* 149 */       worldRender.b();
/* 150 */       worldRender.a(0.0D, (0.0F + f), 0.0D, (glyphUCoordinate / 128.0F + f1), ((glyphVCoordinate + f) / 128.0F + f2));
/* 151 */       worldRender.a((0.0F + f), (0.0F + f), 0.0D, ((glyphUCoordinate + f) / 128.0F + f1), ((glyphVCoordinate + f) / 128.0F + f2));
/* 152 */       worldRender.a((0.0F + f), 0.0D, 0.0D, ((glyphUCoordinate + f) / 128.0F + f1), (glyphVCoordinate / 128.0F + f2));
/* 153 */       worldRender.a(0.0D, 0.0D, 0.0D, (glyphUCoordinate / 128.0F + f1), (glyphVCoordinate / 128.0F + f2));
/* 154 */       tessellator.b();
/*     */ 
/*     */       
/* 157 */       GL.glTranslatef(charWidth[glyphIndex], 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */       
/* 161 */       GL.glEndList();
/*     */     } 
/*     */     
/* 164 */     GL.glNewList(this.fontDisplayLists + LIST_SEL, 4864);
/*     */     
/* 166 */     GL.glColor4f(0.35F, 0.35F, 0.35F, 1.0F);
/* 167 */     GL.glDisableTexture2D();
/* 168 */     worldRender.b();
/* 169 */     worldRender.b(-1.0D, 9.0D, 0.0D);
/* 170 */     worldRender.b((0.0F + fixedWidth), 9.0D, 0.0D);
/* 171 */     worldRender.b((0.0F + fixedWidth), -1.0D, 0.0D);
/* 172 */     worldRender.b(-1.0D, -1.0D, 0.0D);
/* 173 */     tessellator.b();
/* 174 */     GL.glEnableTexture2D();
/* 175 */     GL.glColor3f(0.6F, 0.6F, 0.6F);
/*     */     
/* 177 */     GL.glEndList();
/*     */     
/* 179 */     GL.glNewList(this.fontDisplayLists + LIST_SELCOL, 4864); GL.glColor3f(0.5F, 0.5F, 1.0F); GL.glEndList();
/* 180 */     GL.glNewList(this.fontDisplayLists + LIST_ERRORCOL, 4864); GL.glColor3f(0.4F, 0.2F, 0.0F); GL.glEndList();
/* 181 */     GL.glNewList(this.fontDisplayLists + LIST_CCCOL, 4864); GL.glColor3f(0.0F, 0.5F, 0.0F); GL.glEndList();
/* 182 */     GL.glNewList(this.fontDisplayLists + LIST_HICOL, 4864); GL.glColor3f(0.5F, 0.0F, 0.0F); GL.glEndList();
/* 183 */     GL.glNewList(this.fontDisplayLists + LIST_HICOL2, 4864); GL.glColor3f(0.0F, 0.0F, 0.5F); GL.glEndList();
/* 184 */     GL.glNewList(this.fontDisplayLists + LIST_PARAMCOL, 4864); GL.glColor3f(0.5F, 0.0F, 0.5F); GL.glEndList();
/* 185 */     GL.glNewList(this.fontDisplayLists + LIST_QUOTECOL, 4864); GL.glColor3f(0.0F, 0.3F, 0.3F); GL.glEndList();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int a(String displayText, int xPos, int yPos, int colour) {
/* 191 */     renderFixedWidthString(displayText, xPos, yPos, colour, false, 0, 32768);
/* 192 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderFixedWidthString(String displayText, int xPos, int yPos, int colour, boolean doHighlight, int startChar, int displayChars) {
/* 199 */     if (displayText == null) {
/*     */       return;
/*     */     }
/* 202 */     AbstractionLayer.bindTexture(this.fontTextureName);
/*     */ 
/*     */     
/* 205 */     float red = (colour >> 16 & 0xFF) / 255.0F;
/* 206 */     float green = (colour >> 8 & 0xFF) / 255.0F;
/* 207 */     float blue = (colour & 0xFF) / 255.0F;
/* 208 */     float alpha = (colour >> 24 & 0xFF) / 255.0F;
/*     */ 
/*     */     
/* 211 */     if (alpha == 0.0F) alpha = 1.0F;
/*     */ 
/*     */     
/* 214 */     GL.glNewList(this.fontDisplayLists + LIST_NORMALCOL, 4864);
/* 215 */     GL.glColor3f(red, green, blue);
/* 216 */     GL.glEndList();
/*     */ 
/*     */     
/* 219 */     GL.glColor4f(red, green, blue, alpha);
/*     */ 
/*     */     
/* 222 */     this.buffer.clear();
/*     */ 
/*     */     
/* 225 */     GL.glPushMatrix();
/* 226 */     GL.glTranslatef(xPos, yPos, 0.0F);
/*     */     
/* 228 */     boolean highlight = false, quote = false, unquote = false, param = false;
/* 229 */     boolean resetColour = false;
/* 230 */     int realPos = 0;
/* 231 */     int previousCodePoint = -1;
/*     */ 
/*     */     
/* 234 */     for (int charIndex = 0; charIndex < displayText.length(); charIndex++) {
/*     */       
/* 236 */       if (charIndex < displayText.length()) {
/*     */         
/* 238 */         int codePoint = displayText.charAt(charIndex);
/*     */         
/* 240 */         if (codePoint == 65535) {
/*     */           
/* 242 */           highlight = true;
/*     */           continue;
/*     */         } 
/* 245 */         if (codePoint == 65534) {
/*     */           
/* 247 */           highlight = false;
/*     */           
/*     */           continue;
/*     */         } 
/* 251 */         if (codePoint == 65531) {
/*     */           
/* 253 */           param = true;
/*     */           continue;
/*     */         } 
/* 256 */         if (codePoint == 65530) {
/*     */           
/* 258 */           param = false;
/*     */           
/*     */           continue;
/*     */         } 
/* 262 */         if (codePoint == 65533) {
/*     */           
/* 264 */           if (doHighlight) addToBufferAndFlip(this.fontDisplayLists + LIST_HICOL);
/*     */           
/*     */           continue;
/*     */         } 
/* 268 */         if (codePoint == 65532) {
/*     */           
/* 270 */           if (doHighlight) addToBufferAndFlip(this.fontDisplayLists + LIST_NORMALCOL);
/*     */           
/*     */           continue;
/*     */         } 
/* 274 */         if (doHighlight && codePoint == 34 && previousCodePoint != 92) {
/*     */           
/* 276 */           quote = !quote;
/* 277 */           unquote = !quote;
/*     */         } 
/*     */         
/* 280 */         if (realPos >= startChar && realPos < startChar + displayChars) {
/*     */           
/* 282 */           int validCodePointIndex = AbstractionLayer.getChatAllowedCharacters().indexOf(codePoint);
/*     */           
/* 284 */           if (highlight)
/*     */           {
/* 286 */             addToBufferAndFlip(this.fontDisplayLists + LIST_SEL);
/*     */           }
/*     */           
/* 289 */           if (this.highLightSymbols.indexOf((char)codePoint) > -1 && doHighlight && !param) {
/*     */             
/* 291 */             addToBufferAndFlip(this.fontDisplayLists + LIST_HICOL2);
/* 292 */             resetColour = true;
/*     */           }
/* 294 */           else if ((quote || unquote) && !param) {
/*     */             
/* 296 */             addToBufferAndFlip(this.fontDisplayLists + LIST_QUOTECOL);
/* 297 */             resetColour = true;
/*     */           }
/* 299 */           else if (param) {
/*     */             
/* 301 */             addToBufferAndFlip(this.fontDisplayLists + LIST_PARAMCOL);
/* 302 */             resetColour = true;
/*     */           } 
/*     */           
/* 305 */           if (validCodePointIndex >= 0) {
/*     */ 
/*     */             
/* 308 */             addToBufferAndFlip(this.fontDisplayLists + validCodePointIndex + 32);
/*     */           }
/* 310 */           else if (codePoint >= 0) {
/*     */ 
/*     */             
/* 313 */             if (codePoint == 8 || codePoint == 0 || codePoint > 255) codePoint = 63;
/*     */ 
/*     */             
/* 316 */             addToBufferAndFlip(this.fontDisplayLists + ((codePoint == 167) ? LIST_CCCOL : LIST_ERRORCOL));
/* 317 */             addToBufferAndFlip(this.fontDisplayLists + codePoint);
/* 318 */             resetColour = true;
/*     */             
/* 320 */             if (codePoint == 167 && charIndex + 1 < displayText.length() && "0123456789abcdefklmnor".indexOf(displayText.charAt(charIndex + 1)) > -1) {
/*     */               continue;
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 326 */           if (highlight || resetColour || unquote) {
/*     */             
/* 328 */             addToBufferAndFlip(this.fontDisplayLists + LIST_NORMALCOL);
/* 329 */             resetColour = false;
/* 330 */             unquote = false;
/*     */           } 
/*     */         } 
/*     */         
/* 334 */         realPos++;
/* 335 */         previousCodePoint = codePoint;
/*     */       } 
/*     */       continue;
/*     */     } 
/* 339 */     this.buffer.flip();
/* 340 */     GL.glCallLists(this.buffer);
/* 341 */     GL.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   private void addToBufferAndFlip(int value) {
/* 346 */     this.buffer.put(value);
/* 347 */     if (this.buffer.remaining() == 0) {
/*     */       
/* 349 */       this.buffer.flip();
/* 350 */       GL.glCallLists(this.buffer);
/* 351 */       this.buffer.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFixedStringWidth(String stringToMeasure) {
/* 362 */     if (stringToMeasure == null) {
/* 363 */       return 0;
/*     */     }
/* 365 */     return stringToMeasure.length() * this.fixedWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFixedCharWidth() {
/* 375 */     return this.fixedWidth;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\rendering\FontRendererTextEditor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */