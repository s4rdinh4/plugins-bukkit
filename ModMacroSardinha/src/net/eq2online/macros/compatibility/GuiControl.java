/*     */ package net.eq2online.macros.compatibility;
/*     */ 
/*     */ import bsu;
/*     */ import bty;
/*     */ import bug;
/*     */ import civ;
/*     */ import ckx;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import com.mumfrey.liteloader.util.render.Icon;
/*     */ import net.eq2online.macros.res.ResourceLocations;
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
/*     */ public class GuiControl
/*     */   extends bug
/*     */ {
/*  29 */   protected static float texMapScale = 0.00390625F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   public int iconIndex = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int iconU;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int iconV;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiControl(int id, int xPosition, int yPosition, String displayText) {
/*  51 */     super(id, xPosition, yPosition, displayText);
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
/*     */   public GuiControl(int id, int xPosition, int yPosition, String displayText, int iconIndex) {
/*  65 */     super(id, xPosition, yPosition, displayText);
/*     */     
/*  67 */     setIconIndex(iconIndex);
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
/*     */   public GuiControl(int id, int xPosition, int yPosition, int controlWidth, int controlHeight, String displayText) {
/*  82 */     super(id, xPosition, yPosition, controlWidth, controlHeight, displayText);
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
/*     */   public GuiControl(int id, int xPosition, int yPosition, int controlWidth, int controlHeight, String displayText, int iconIndex) {
/*  98 */     super(id, xPosition, yPosition, controlWidth, controlHeight, displayText);
/*     */     
/* 100 */     setIconIndex(iconIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIconIndex(int iconIndex) {
/* 109 */     this.iconIndex = iconIndex;
/*     */     
/* 111 */     if (iconIndex > -1) {
/*     */       
/* 113 */       this.iconU = 80 + iconIndex % 4 * 12;
/* 114 */       this.iconV = iconIndex / 4 * 12;
/*     */     }
/*     */     else {
/*     */       
/* 118 */       this.iconU = 0;
/* 119 */       this.iconV = 0;
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
/*     */   protected void drawIcon(oa texture, Icon icon, int x, int y, int x2, int y2) {
/* 132 */     AbstractionLayer.bindTexture(texture);
/* 133 */     drawTexturedModalRectF(x, y, x2, y2, icon.getMinU(), icon.getMinV(), icon.getMaxU(), icon.getMaxV());
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
/*     */   public void drawTexturedModalRect(int x, int y, int x2, int y2, int u, int v, int u2, int v2) {
/* 151 */     ckx tessellator = AbstractionLayer.getTessellator();
/* 152 */     civ worldRender = tessellator.c();
/* 153 */     worldRender.b();
/* 154 */     worldRender.a(x, y2, this.e, (u * texMapScale), (v2 * texMapScale));
/* 155 */     worldRender.a(x2, y2, this.e, (u2 * texMapScale), (v2 * texMapScale));
/* 156 */     worldRender.a(x2, y, this.e, (u2 * texMapScale), (v * texMapScale));
/* 157 */     worldRender.a(x, y, this.e, (u * texMapScale), (v * texMapScale));
/* 158 */     tessellator.b();
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
/*     */   public void drawTexturedModalRectF(int x, int y, int x2, int y2, float u, float v, float u2, float v2) {
/* 175 */     ckx tessellator = AbstractionLayer.getTessellator();
/* 176 */     civ worldRender = tessellator.c();
/* 177 */     worldRender.b();
/* 178 */     worldRender.a(x, y2, this.e, u, v2);
/* 179 */     worldRender.a(x2, y2, this.e, u2, v2);
/* 180 */     worldRender.a(x2, y, this.e, u2, v);
/* 181 */     worldRender.a(x, y, this.e, u, v);
/* 182 */     tessellator.b();
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
/*     */   public void drawTexturedModalRect(int x, int y, int u, int v, int width, int height, float texMapScale) {
/* 199 */     ckx tessellator = AbstractionLayer.getTessellator();
/* 200 */     civ worldRender = tessellator.c();
/* 201 */     worldRender.b();
/* 202 */     worldRender.a((x + 0), (y + height), this.e, ((u + 0) * texMapScale), ((v + height) * texMapScale));
/* 203 */     worldRender.a((x + width), (y + height), this.e, ((u + width) * texMapScale), ((v + height) * texMapScale));
/* 204 */     worldRender.a((x + width), (y + 0), this.e, ((u + width) * texMapScale), ((v + 0) * texMapScale));
/* 205 */     worldRender.a((x + 0), (y + 0), this.e, ((u + 0) * texMapScale), ((v + 0) * texMapScale));
/* 206 */     tessellator.b();
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getID() {
/* 211 */     return this.k;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getWidth() {
/* 216 */     return this.f;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getHeight() {
/* 221 */     return this.g;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void getHeight(int newHeight) {
/* 226 */     this.g = newHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getXPosition() {
/* 231 */     return this.h;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setXPosition(int newXPosition) {
/* 236 */     this.h = newXPosition;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getYPosition() {
/* 241 */     return this.i;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setYPosition(int newYPosition) {
/* 246 */     this.i = newYPosition;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setPosition(int newXPosition, int newYPosition) {
/* 251 */     this.h = newXPosition;
/* 252 */     this.i = newYPosition;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isEnabled() {
/* 257 */     return this.l;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setEnabled(boolean newEnabled) {
/* 262 */     this.l = newEnabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isVisible() {
/* 267 */     return this.m;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setVisible(boolean newVisible) {
/* 272 */     this.m = newVisible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(bsu minecraft, int mouseX, int mouseY) {
/* 281 */     if (!this.m) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 286 */     bty fontrenderer = minecraft.k;
/*     */     
/* 288 */     AbstractionLayer.bindTexture(ResourceLocations.MAIN);
/* 289 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 291 */     boolean flag = (mouseX >= this.h && mouseY >= this.i && mouseX < this.h + this.f && mouseY < this.i + this.g);
/* 292 */     int hoverState = a(flag);
/* 293 */     drawTexturedModalRect(this.h, this.i, 0, 64 + hoverState * 20, this.f / 2, this.g, 0.0078125F);
/* 294 */     drawTexturedModalRect(this.h + this.f / 2, this.i, 128 - this.f / 2, 64 + hoverState * 20, this.f / 2, this.g, 0.0078125F);
/* 295 */     b(minecraft, mouseX, mouseY);
/*     */     
/* 297 */     int innerWidth = this.f;
/* 298 */     GL.glPushMatrix();
/*     */     
/* 300 */     if (this.iconIndex > -1) {
/*     */       
/* 302 */       GL.glEnableBlend();
/* 303 */       drawTexturedModalRect(this.h + 5, this.i + 4, this.iconU, this.iconV, 12, 12, 0.0078125F);
/* 304 */       GL.glDisableBlend();
/* 305 */       GL.glTranslatef(16.0F, 0.0F, 0.0F);
/* 306 */       innerWidth -= 16;
/*     */     } 
/*     */     
/* 309 */     if (!this.l) {
/*     */       
/* 311 */       a(fontrenderer, this.j, this.h + innerWidth / 2, this.i + (this.g - 8) / 2, -6250336);
/*     */     }
/* 313 */     else if (flag) {
/*     */       
/* 315 */       a(fontrenderer, this.j, this.h + innerWidth / 2, this.i + (this.g - 8) / 2, 11206655);
/*     */     }
/*     */     else {
/*     */       
/* 319 */       a(fontrenderer, this.j, this.h + innerWidth / 2, this.i + (this.g - 8) / 2, 14737632);
/*     */     } 
/*     */     
/* 322 */     GL.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean rightClicked(bsu minecraft, int mouseX, int mouseY, boolean buttonState) {
/* 327 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMouseOver(bsu minecraft, int mouseX, int mouseY) {
/* 332 */     return (this.l && this.m && mouseX >= this.h && mouseY >= this.i && mouseX < this.h + this.f && mouseY < this.i + this.g);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\compatibility\GuiControl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */