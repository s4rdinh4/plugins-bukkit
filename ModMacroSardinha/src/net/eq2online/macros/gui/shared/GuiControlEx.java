/*     */ package net.eq2online.macros.gui.shared;
/*     */ 
/*     */ import bsu;
/*     */ import bty;
/*     */ import civ;
/*     */ import ckx;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.nio.DoubleBuffer;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.GuiControl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GuiControlEx
/*     */   extends GuiControl
/*     */ {
/*     */   public int updateCounter;
/*     */   protected bsu mc;
/*     */   protected boolean actionPerformed;
/*     */   protected boolean doubleClicked;
/*     */   protected DoubleBuffer doubleBuffer;
/*     */   
/*     */   public enum KeyHandledState
/*     */   {
/*  31 */     None,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  36 */     Handled,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  41 */     ActionPerformed;
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
/*     */   public final void a(bsu minecraft, int mouseX, int mouseY) {
/*  79 */     drawControl(minecraft, mouseX, mouseY);
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
/*     */   protected abstract void drawControl(bsu parambsu, int paramInt1, int paramInt2);
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
/*     */   public GuiControlEx(bsu minecraft, int controlId, int xPos, int yPos, int controlWidth, int controlHeight, String displayText) {
/* 104 */     super(controlId, xPos, yPos, controlWidth, controlHeight, displayText);
/* 105 */     this.mc = minecraft;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActionPerformed() {
/* 116 */     return this.actionPerformed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDoubleClicked(boolean resetDoubleClicked) {
/* 126 */     boolean result = this.doubleClicked;
/* 127 */     if (resetDoubleClicked) this.doubleClicked = false; 
/* 128 */     return result;
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
/*     */   public static void drawLine(int x1, int y1, int x2, int y2, int width, int colour) {
/* 143 */     drawArrow(x1, y1, x2, y2, 0, width, colour, false, 0);
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
/*     */   public static void drawNativeLine(float x1, float y1, float x2, float y2, float width, int colour) {
/* 159 */     float f = (colour >> 24 & 0xFF) / 255.0F;
/* 160 */     float f1 = (colour >> 16 & 0xFF) / 255.0F;
/* 161 */     float f2 = (colour >> 8 & 0xFF) / 255.0F;
/* 162 */     float f3 = (colour & 0xFF) / 255.0F;
/*     */     
/* 164 */     GL.glEnableBlend();
/* 165 */     GL.glDisableTexture2D();
/* 166 */     GL.glBlendFunc(770, 771);
/* 167 */     GL.glColor4f(f1, f2, f3, f);
/* 168 */     GL.glLineWidth(width);
/*     */     
/* 170 */     ckx tessellator = AbstractionLayer.getTessellator();
/* 171 */     civ worldRender = tessellator.c();
/* 172 */     worldRender.a(1);
/* 173 */     worldRender.b(x1, y1, 0.0D);
/* 174 */     worldRender.b(x2, y2, 0.0D);
/* 175 */     tessellator.b();
/*     */     
/* 177 */     GL.glEnableTexture2D();
/* 178 */     GL.glDisableBlend();
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
/*     */   public static void drawArrow(int x1, int y1, int x2, int y2, int z, int width, int arrowHeadSize, int colour) {
/* 194 */     drawArrow(x1, y1, x2, y2, z, width, colour, true, arrowHeadSize);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawArrow(int x1, int y1, int x2, int y2, int z, int width, int colour, boolean arrowHead, int arrowHeadSize) {
/* 213 */     int length = (int)Math.sqrt(Math.pow((x2 - x1), 2.0D) + Math.pow((y2 - y1), 2.0D));
/* 214 */     float angle = (float)Math.toDegrees(Math.atan2((y2 - y1), (x2 - x1)));
/*     */ 
/*     */     
/* 217 */     GL.glPushMatrix();
/* 218 */     GL.glTranslatef(x1, y1, 0.0F);
/* 219 */     GL.glRotatef(angle, 0.0F, 0.0F, 1.0F);
/*     */ 
/*     */     
/* 222 */     x1 = 0;
/* 223 */     x2 = length - (arrowHead ? arrowHeadSize : 0);
/* 224 */     y1 = (int)(width * -0.5D);
/* 225 */     y2 = y1 + width;
/*     */ 
/*     */     
/* 228 */     float f = (colour >> 24 & 0xFF) / 255.0F;
/* 229 */     float f1 = (colour >> 16 & 0xFF) / 255.0F;
/* 230 */     float f2 = (colour >> 8 & 0xFF) / 255.0F;
/* 231 */     float f3 = (colour & 0xFF) / 255.0F;
/*     */     
/* 233 */     GL.glEnableBlend();
/* 234 */     GL.glDisableTexture2D();
/* 235 */     GL.glBlendFunc(770, 771);
/* 236 */     GL.glColor4f(f1, f2, f3, f);
/*     */ 
/*     */     
/* 239 */     ckx tessellator = AbstractionLayer.getTessellator();
/* 240 */     civ worldRender = tessellator.c();
/* 241 */     worldRender.b();
/* 242 */     worldRender.b(x1, y2, z);
/* 243 */     worldRender.b(x2, y2, z);
/* 244 */     worldRender.b(x2, y1, z);
/* 245 */     worldRender.b(x1, y1, z);
/* 246 */     tessellator.b();
/*     */ 
/*     */     
/* 249 */     if (arrowHead && arrowHeadSize > 0) {
/*     */       
/* 251 */       worldRender.a(4);
/* 252 */       worldRender.b(x2, (0 - arrowHeadSize / 2), z);
/* 253 */       worldRender.b(x2, (arrowHeadSize / 2), z);
/* 254 */       worldRender.b(length, 0.0D, z);
/* 255 */       tessellator.b();
/*     */     } 
/*     */     
/* 258 */     GL.glEnableTexture2D();
/* 259 */     GL.glDisableBlend();
/*     */     
/* 261 */     GL.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTexMapSize(int textureSize) {
/* 272 */     texMapScale = 1.0F / textureSize;
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
/*     */ 
/*     */   
/*     */   public void drawTexturedModalRectRot(int x, int y, int x2, int y2, int u, int v, int u2, int v2) {
/* 290 */     ckx tessellator = AbstractionLayer.getTessellator();
/* 291 */     civ worldRender = tessellator.c();
/* 292 */     worldRender.b();
/* 293 */     worldRender.a(x2, y2, this.e, (u * texMapScale), (v2 * texMapScale));
/* 294 */     worldRender.a(x2, y, this.e, (u2 * texMapScale), (v2 * texMapScale));
/* 295 */     worldRender.a(x, y, this.e, (u2 * texMapScale), (v * texMapScale));
/* 296 */     worldRender.a(x, y2, this.e, (u * texMapScale), (v * texMapScale));
/* 297 */     tessellator.b();
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
/*     */   public void drawTexturedModalRectRot(int x, int y, int u, int v, int width, int height) {
/* 313 */     ckx tessellator = AbstractionLayer.getTessellator();
/* 314 */     civ worldRender = tessellator.c();
/* 315 */     worldRender.b();
/* 316 */     worldRender.a((x + height), (y + width), this.e, (u * texMapScale), ((v + height) * texMapScale));
/* 317 */     worldRender.a((x + height), y, this.e, ((u + width) * texMapScale), ((v + height) * texMapScale));
/* 318 */     worldRender.a(x, y, this.e, ((u + width) * texMapScale), (v * texMapScale));
/* 319 */     worldRender.a(x, (y + width), this.e, (u * texMapScale), (v * texMapScale));
/* 320 */     tessellator.b();
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
/*     */ 
/*     */   
/*     */   public void drawTessellatedModalRectV(int x, int y, int x2, int y2, int u, int v, int u2, int v2) {
/* 338 */     int tileSize = (v2 - v) / 2;
/* 339 */     int vMidTop = v + tileSize;
/* 340 */     int vMidBtm = vMidTop + 1;
/*     */     
/* 342 */     drawTexturedModalRect(x, y, x2, y + tileSize, u, v, u2, vMidTop);
/* 343 */     drawTexturedModalRect(x, y + tileSize, x2, y2 - tileSize + 1, u, vMidTop, u2, vMidBtm);
/* 344 */     drawTexturedModalRect(x, y2 - tileSize + 1, x2, y2, u, vMidBtm, u2, v2);
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
/*     */ 
/*     */   
/*     */   public void drawTessellatedModalRectH(int x, int y, int x2, int y2, int u, int v, int u2, int v2) {
/* 362 */     int tileSize = (u2 - u) / 2;
/* 363 */     int uMidLeft = u + tileSize;
/* 364 */     int uMidRight = uMidLeft + 1;
/*     */     
/* 366 */     drawTexturedModalRect(x, y, x + tileSize, y2, u, v, uMidLeft, v2);
/* 367 */     drawTexturedModalRect(x + tileSize, y, x2 - tileSize + 1, y2, uMidLeft, v, uMidRight, v2);
/* 368 */     drawTexturedModalRect(x2 - tileSize + 1, y, x2, y2, uMidRight, v, u2, v2);
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
/*     */ 
/*     */   
/*     */   public void drawTessellatedModalBorderRect(int x, int y, int x2, int y2, int u, int v, int u2, int v2) {
/* 386 */     drawTessellatedModalBorderRect(x, y, x2, y2, u, v, u2, v2, Math.min((x2 - x) / 2 - 1, (y2 - y) / 2 - 1));
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTessellatedModalBorderRect(int x, int y, int x2, int y2, int u, int v, int u2, int v2, int borderSize) {
/* 406 */     int tileSize = Math.min((u2 - u) / 2 - 1, (v2 - v) / 2 - 1);
/*     */     
/* 408 */     int ul = u + tileSize, ur = u2 - tileSize, vt = v + tileSize, vb = v2 - tileSize;
/* 409 */     int xl = x + borderSize, xr = x2 - borderSize, yt = y + borderSize, yb = y2 - borderSize;
/*     */     
/* 411 */     drawTexturedModalRect(x, y, xl, yt, u, v, ul, vt);
/* 412 */     drawTexturedModalRect(xl, y, xr, yt, ul, v, ur, vt);
/* 413 */     drawTexturedModalRect(xr, y, x2, yt, ur, v, u2, vt);
/* 414 */     drawTexturedModalRect(x, yb, xl, y2, u, vb, ul, v2);
/* 415 */     drawTexturedModalRect(xl, yb, xr, y2, ul, vb, ur, v2);
/* 416 */     drawTexturedModalRect(xr, yb, x2, y2, ur, vb, u2, v2);
/* 417 */     drawTexturedModalRect(x, yt, xl, yb, u, vt, ul, vb);
/* 418 */     drawTexturedModalRect(xr, yt, x2, yb, ur, vt, u2, vb);
/* 419 */     drawTexturedModalRect(xl, yt, xr, yb, ul, vt, ur, vb);
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
/*     */   public static void drawStringWithEllipsis(bty fontrenderer, String s, int x, int y, int width, int colour) {
/* 434 */     if (fontrenderer.a(s) <= width) {
/*     */       
/* 436 */       fontrenderer.a(s, x, y, colour);
/*     */     }
/* 438 */     else if (width < 8) {
/*     */       
/* 440 */       fontrenderer.a("..", x, y, colour);
/*     */     }
/*     */     else {
/*     */       
/* 444 */       String trimmedText = s;
/*     */       
/* 446 */       while (fontrenderer.a(trimmedText) > width - 8 && trimmedText.length() > 0) {
/* 447 */         trimmedText = trimmedText.substring(0, trimmedText.length() - 1);
/*     */       }
/* 449 */       fontrenderer.a(trimmedText + "...", x, y, colour);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawCrossHair(int x, int y, int size, int width, int colour) {
/* 459 */     float alpha = (colour >> 24 & 0xFF) / 255.0F;
/* 460 */     float red = (colour >> 16 & 0xFF) / 255.0F;
/* 461 */     float green = (colour >> 8 & 0xFF) / 255.0F;
/* 462 */     float blue = (colour & 0xFF) / 255.0F;
/*     */     
/* 464 */     GL.glLineWidth((GuiScreenEx.guiScaleFactor * width));
/* 465 */     GL.glBlendFunc(770, 771);
/* 466 */     GL.glEnableBlend();
/* 467 */     GL.glDisableTexture2D();
/* 468 */     GL.glDisableLighting();
/* 469 */     GL.glColor4f(red, green, blue, alpha);
/* 470 */     GL.glEnableColorLogic();
/* 471 */     GL.glLogicOp(5387);
/*     */ 
/*     */     
/* 474 */     ckx tessellator = AbstractionLayer.getTessellator();
/* 475 */     civ worldRender = tessellator.c();
/*     */     
/* 477 */     worldRender.a(1);
/* 478 */     worldRender.b((x - size), y, 0.0D);
/* 479 */     worldRender.b((x + size), y, 0.0D);
/* 480 */     tessellator.b();
/*     */     
/* 482 */     worldRender.a(1);
/* 483 */     worldRender.b(x, (y - size), 0.0D);
/* 484 */     worldRender.b(x, (y + size), 0.0D);
/* 485 */     tessellator.b();
/*     */     
/* 487 */     GL.glDisableColorLogic();
/* 488 */     GL.glEnableTexture2D();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawRotText(bty fontRenderer, String text, int xPosition, int yPosition, int colour, boolean colourOrOp) {
/* 493 */     if (colourOrOp) {
/*     */       
/* 495 */       GL.glEnableColorLogic();
/* 496 */       GL.glLogicOp(5387);
/*     */     } 
/*     */     
/* 499 */     int textWidth = fontRenderer.a(text) / 2;
/*     */     
/* 501 */     GL.glPushMatrix();
/* 502 */     GL.glTranslatef(xPosition, yPosition, 0.0F);
/* 503 */     GL.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
/* 504 */     GL.glTranslatef(-textWidth, -4.0F, 0.0F);
/*     */     
/* 506 */     fontRenderer.a(text, 0, 0, colour);
/*     */     
/* 508 */     GL.glPopMatrix();
/*     */     
/* 510 */     if (colourOrOp) {
/*     */       
/* 512 */       GL.glDisableColorLogic();
/* 513 */       GL.glEnableTexture2D();
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
/*     */ 
/*     */   
/*     */   protected void drawTooltip(bty fontRenderer, String tooltipText, int mouseX, int mouseY, int screenWidth, int screenHeight, int colour, int backgroundColour) {
/* 531 */     int textSize = fontRenderer.a(tooltipText);
/* 532 */     mouseX = Math.max(0, Math.min(screenWidth - textSize - 6, mouseX - 6));
/* 533 */     mouseY = Math.max(0, Math.min(screenHeight - 16, mouseY - 18));
/*     */     
/* 535 */     a(mouseX, mouseY, mouseX + textSize + 6, mouseY + 16, backgroundColour);
/* 536 */     c(fontRenderer, tooltipText, mouseX + 3, mouseY + 4, colour);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\shared\GuiControlEx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */