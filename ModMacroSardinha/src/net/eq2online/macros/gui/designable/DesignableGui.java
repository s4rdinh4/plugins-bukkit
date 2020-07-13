/*     */ package net.eq2online.macros.gui.designable;
/*     */ 
/*     */ import bub;
/*     */ import civ;
/*     */ import ckx;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Rectangle;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DesignableGui
/*     */   extends bub
/*     */ {
/*     */   protected void drawRect(Rectangle rect, int colour) {
/*  21 */     drawRect(rect, colour, 0, 0, 0, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawRect(Rectangle rect, int colour, int inset) {
/*  31 */     drawRect(rect, colour, inset, inset, inset, inset);
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
/*     */   protected void drawRect(Rectangle rect, int colour, int xOffset, int yOffset, int widthOffset, int heightOffset) {
/*  44 */     a(rect.x + xOffset, rect.y + yOffset, rect.x + rect.width - widthOffset, rect.y + rect.height - heightOffset, colour);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawTexturedModalIcon(int x, int y, int width, int height, int u, int v, int u2, int v2, float texMapScale) {
/*  49 */     x += (width - u2 - u) / 2;
/*  50 */     y += (height - v2 - v) / 2;
/*     */     
/*  52 */     drawTexturedModalRect(x, y, x + u2 - u, y + v2 - v, u, v, u2, v2, texMapScale);
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
/*     */   protected void drawTexturedModalRect(int x, int y, int x2, int y2, int u, int v, int u2, int v2, float texMapScale) {
/*  69 */     float z = this.e;
/*     */     
/*  71 */     ckx tessellator = AbstractionLayer.getTessellator();
/*  72 */     civ worldRender = tessellator.c();
/*  73 */     worldRender.b();
/*  74 */     worldRender.a(x, y2, z, (u * texMapScale), (v2 * texMapScale));
/*  75 */     worldRender.a(x2, y2, z, (u2 * texMapScale), (v2 * texMapScale));
/*  76 */     worldRender.a(x2, y, z, (u2 * texMapScale), (v * texMapScale));
/*  77 */     worldRender.a(x, y, z, (u * texMapScale), (v * texMapScale));
/*  78 */     tessellator.b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawRectOutline(Rectangle boundingBox, int colour, int width) {
/*  86 */     float alpha = (colour >> 24 & 0xFF) / 255.0F;
/*  87 */     float red = (colour >> 16 & 0xFF) / 255.0F;
/*  88 */     float green = (colour >> 8 & 0xFF) / 255.0F;
/*  89 */     float blue = (colour & 0xFF) / 255.0F;
/*     */     
/*  91 */     GL.glLineWidth((GuiScreenEx.guiScaleFactor * width));
/*  92 */     GL.glBlendFunc(770, 771);
/*  93 */     GL.glDisableBlend();
/*  94 */     GL.glDisableTexture2D();
/*  95 */     GL.glDisableLighting();
/*  96 */     GL.glColor4f(red, green, blue, alpha);
/*     */ 
/*     */     
/*  99 */     ckx tessellator = AbstractionLayer.getTessellator();
/* 100 */     civ worldRender = tessellator.c();
/* 101 */     worldRender.a(2);
/* 102 */     worldRender.b(boundingBox.x, boundingBox.y, 0.0D);
/* 103 */     worldRender.b((boundingBox.x + boundingBox.width), boundingBox.y, 0.0D);
/* 104 */     worldRender.b((boundingBox.x + boundingBox.width), (boundingBox.y + boundingBox.height), 0.0D);
/* 105 */     worldRender.b(boundingBox.x, (boundingBox.y + boundingBox.height), 0.0D);
/* 106 */     tessellator.b();
/*     */     
/* 108 */     GL.glEnableTexture2D();
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
/*     */   protected void drawLine(float x1, float y1, float x2, float y2, float width, int colour) {
/* 124 */     float alpha = (colour >> 24 & 0xFF) / 255.0F;
/* 125 */     float red = (colour >> 16 & 0xFF) / 255.0F;
/* 126 */     float green = (colour >> 8 & 0xFF) / 255.0F;
/* 127 */     float blue = (colour & 0xFF) / 255.0F;
/*     */     
/* 129 */     GL.glDisableBlend();
/* 130 */     GL.glDisableTexture2D();
/* 131 */     GL.glBlendFunc(770, 771);
/* 132 */     GL.glColor4f(red, green, blue, alpha);
/* 133 */     GL.glLineWidth(width);
/*     */     
/* 135 */     ckx tessellator = AbstractionLayer.getTessellator();
/* 136 */     civ worldRender = tessellator.c();
/* 137 */     worldRender.a(1);
/* 138 */     worldRender.b(x1, y1, 0.0D);
/* 139 */     worldRender.b(x2, y2, 0.0D);
/* 140 */     tessellator.b();
/*     */     
/* 142 */     GL.glEnableTexture2D();
/* 143 */     GL.glDisableBlend();
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
/*     */   protected void drawDoubleEndedArrowH(float x1, float x2, float y, float width, float arrowHeadSize, int colour) {
/* 159 */     float alpha = (colour >> 24 & 0xFF) / 255.0F;
/* 160 */     float red = (colour >> 16 & 0xFF) / 255.0F;
/* 161 */     float green = (colour >> 8 & 0xFF) / 255.0F;
/* 162 */     float blue = (colour & 0xFF) / 255.0F;
/*     */     
/* 164 */     GL.glDisableBlend();
/* 165 */     GL.glDisableTexture2D();
/* 166 */     GL.glBlendFunc(770, 771);
/* 167 */     GL.glColor4f(red, green, blue, alpha);
/* 168 */     GL.glLineWidth(width);
/*     */     
/* 170 */     float halfArrowHeadSize = arrowHeadSize * 0.5F;
/*     */     
/* 172 */     ckx tessellator = AbstractionLayer.getTessellator();
/* 173 */     civ worldRender = tessellator.c();
/*     */     
/* 175 */     worldRender.a(1);
/* 176 */     worldRender.b((x1 + arrowHeadSize), y, 0.0D);
/* 177 */     worldRender.b((x2 - arrowHeadSize), y, 0.0D);
/* 178 */     tessellator.b();
/*     */     
/* 180 */     worldRender.a(4);
/* 181 */     worldRender.b(x1, y, 0.0D);
/* 182 */     worldRender.b((x1 + arrowHeadSize), (y + halfArrowHeadSize), 0.0D);
/* 183 */     worldRender.b((x1 + arrowHeadSize), (y - halfArrowHeadSize), 0.0D);
/* 184 */     tessellator.b();
/*     */     
/* 186 */     worldRender.a(4);
/* 187 */     worldRender.b(x2, y, 0.0D);
/* 188 */     worldRender.b((x2 - arrowHeadSize), (y - halfArrowHeadSize), 0.0D);
/* 189 */     worldRender.b((x2 - arrowHeadSize), (y + halfArrowHeadSize), 0.0D);
/* 190 */     tessellator.b();
/*     */     
/* 192 */     GL.glEnableTexture2D();
/* 193 */     GL.glDisableBlend();
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
/*     */   protected void drawDoubleEndedArrowV(float x, float y1, float y2, float width, float arrowHeadSize, int colour) {
/* 210 */     float alpha = (colour >> 24 & 0xFF) / 255.0F;
/* 211 */     float red = (colour >> 16 & 0xFF) / 255.0F;
/* 212 */     float green = (colour >> 8 & 0xFF) / 255.0F;
/* 213 */     float blue = (colour & 0xFF) / 255.0F;
/*     */     
/* 215 */     GL.glDisableBlend();
/* 216 */     GL.glDisableTexture2D();
/* 217 */     GL.glBlendFunc(770, 771);
/* 218 */     GL.glColor4f(red, green, blue, alpha);
/* 219 */     GL.glLineWidth(width);
/*     */     
/* 221 */     float halfArrowHeadSize = arrowHeadSize * 0.5F;
/*     */     
/* 223 */     ckx tessellator = AbstractionLayer.getTessellator();
/* 224 */     civ worldRender = tessellator.c();
/*     */     
/* 226 */     worldRender.a(1);
/* 227 */     worldRender.b(x, (y1 + arrowHeadSize), 0.0D);
/* 228 */     worldRender.b(x, (y2 - arrowHeadSize), 0.0D);
/* 229 */     tessellator.b();
/*     */     
/* 231 */     worldRender.a(4);
/* 232 */     worldRender.b(x, y1, 0.0D);
/* 233 */     worldRender.b((x - halfArrowHeadSize), (y1 + arrowHeadSize), 0.0D);
/* 234 */     worldRender.b((x + halfArrowHeadSize), (y1 + arrowHeadSize), 0.0D);
/* 235 */     tessellator.b();
/*     */     
/* 237 */     worldRender.a(4);
/* 238 */     worldRender.b(x, y2, 0.0D);
/* 239 */     worldRender.b((x + halfArrowHeadSize), (y2 - arrowHeadSize), 0.0D);
/* 240 */     worldRender.b((x - halfArrowHeadSize), (y2 - arrowHeadSize), 0.0D);
/* 241 */     tessellator.b();
/*     */     
/* 243 */     GL.glEnableTexture2D();
/* 244 */     GL.glDisableBlend();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\designable\DesignableGui.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */