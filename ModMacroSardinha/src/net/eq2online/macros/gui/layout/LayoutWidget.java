/*     */ package net.eq2online.macros.gui.layout;
/*     */ 
/*     */ import bsu;
/*     */ import bty;
/*     */ import bub;
/*     */ import civ;
/*     */ import ckx;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import com.mumfrey.liteloader.util.render.Icon;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.interfaces.ILayoutWidget;
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
/*     */ public class LayoutWidget
/*     */   extends bub
/*     */   implements ILayoutWidget<LayoutPanelStandard>
/*     */ {
/*     */   protected bty fontRenderer;
/*     */   protected int widgetId;
/*     */   protected String name;
/*     */   protected String displayText;
/*     */   protected int width;
/*     */   protected int height;
/*     */   protected int xPosition;
/*     */   protected int yPosition;
/*     */   protected int drawX;
/*     */   private int dragOffsetX;
/*     */   private int dragOffsetY;
/*     */   protected boolean centreAlign = true;
/*  84 */   public static int COLOR_UNBOUND = -12566464;
/*  85 */   public static int COLOR_BOUND = -256;
/*  86 */   public static int COLOR_SPECIAL = -7864320;
/*  87 */   public static int COLOR_BOUNDSPECIAL = -22016;
/*  88 */   public static int COLOR_BOUNDGLOBAL = -16711936;
/*  89 */   public static int COLOR_SELECTED = -1;
/*  90 */   public static int COLOR_DENIED = -65536;
/*     */ 
/*     */   
/*     */   public LayoutWidget(bty fontRenderer, String name, int width, int height, boolean centre) {
/*  94 */     this.fontRenderer = fontRenderer;
/*  95 */     this.name = name;
/*  96 */     this.displayText = name;
/*  97 */     this.width = width;
/*  98 */     this.height = height;
/*  99 */     this.centreAlign = centre;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setWidgetPosition(LayoutPanelStandard parent, int x, int y) {
/* 108 */     this.xPosition = x;
/* 109 */     this.yPosition = y;
/* 110 */     this.drawX = this.centreAlign ? (this.xPosition - this.width / 2) : this.xPosition;
/*     */     
/* 112 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWidgetPositionSnapped(LayoutPanelStandard parent, int x, int y) {
/* 121 */     setWidgetPosition(parent, x - x % 2, y - y % 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point getWidgetPosition(LayoutPanelStandard parent) {
/* 130 */     return new Point(this.xPosition, this.yPosition);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidgetId() {
/* 139 */     return this.widgetId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidgetZIndex() {
/* 145 */     return this.widgetId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidgetWidth(LayoutPanelStandard parent) {
/* 154 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getWidgetIsBound() {
/* 163 */     return MacroModCore.getMacroManager().isMacroBound(this.widgetId, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getWidgetIsBindable() {
/* 172 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getWidgetIsDenied() {
/* 181 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWidgetDisplayText() {
/* 190 */     return this.displayText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWidgetDeniedText() {
/* 199 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void toggleReservedState() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawWidget(LayoutPanelStandard parent, Rectangle boundingBox, int mouseX, int mouseY, LayoutPanelEditMode mode, boolean selected, boolean denied) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseDragged(bsu minecraft, int mouseX, int mouseY) {
/* 226 */     setWidgetPositionSnapped((LayoutPanelStandard)null, mouseX + this.dragOffsetX, mouseY + this.dragOffsetY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY) {
/* 235 */     setWidgetPositionSnapped((LayoutPanelStandard)null, mouseX + this.dragOffsetX, mouseY + this.dragOffsetY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mousePressed(bsu minecraft, int mouseX, int mouseY) {
/* 244 */     if (mouseOver(null, mouseX, mouseY, false)) {
/*     */       
/* 246 */       this.dragOffsetX = this.xPosition - mouseX;
/* 247 */       this.dragOffsetY = this.yPosition - mouseY;
/* 248 */       return true;
/*     */     } 
/*     */     
/* 251 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseOver(Rectangle boundingBox, int mouseX, int mouseY, boolean selected) {
/* 260 */     return (mouseX > this.drawX && mouseX < this.drawX + this.width && mouseY > this.yPosition && mouseY < this.yPosition + this.height);
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
/*     */   protected void drawTexturedModalRect(int x, int y, int x2, int y2, int u, int v, int u2, int v2) {
/* 277 */     float z = this.e;
/*     */     
/* 279 */     float texMapScale = 0.00390625F;
/* 280 */     ckx tessellator = AbstractionLayer.getTessellator();
/* 281 */     civ worldRender = tessellator.c();
/* 282 */     worldRender.b();
/* 283 */     worldRender.a(x, y2, z, (u * texMapScale), (v2 * texMapScale));
/* 284 */     worldRender.a(x2, y2, z, (u2 * texMapScale), (v2 * texMapScale));
/* 285 */     worldRender.a(x2, y, z, (u2 * texMapScale), (v * texMapScale));
/* 286 */     worldRender.a(x, y, z, (u * texMapScale), (v * texMapScale));
/* 287 */     tessellator.b();
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
/*     */   protected void drawTriangle(int corner, int x1, int y1, int x2, int y2, int colour) {
/* 302 */     corner %= 4;
/*     */     
/* 304 */     if (x1 < x2) {
/*     */       
/* 306 */       int xTemp = x1;
/* 307 */       x1 = x2;
/* 308 */       x2 = xTemp;
/*     */     } 
/*     */     
/* 311 */     if (y1 < y2) {
/*     */       
/* 313 */       int yTemp = y1;
/* 314 */       y1 = y2;
/* 315 */       y2 = yTemp;
/*     */     } 
/*     */     
/* 318 */     float alpha = (colour >> 24 & 0xFF) / 255.0F;
/* 319 */     float red = (colour >> 16 & 0xFF) / 255.0F;
/* 320 */     float green = (colour >> 8 & 0xFF) / 255.0F;
/* 321 */     float blue = (colour & 0xFF) / 255.0F;
/*     */     
/* 323 */     GL.glEnableBlend();
/* 324 */     GL.glDisableTexture2D();
/* 325 */     GL.glBlendFunc(770, 771);
/* 326 */     GL.glColor4f(red, green, blue, alpha);
/*     */     
/* 328 */     ckx tessellator = ckx.a();
/* 329 */     civ worldRender = tessellator.c();
/* 330 */     worldRender.a(4);
/* 331 */     if (corner != 3) worldRender.b(x1, y2, 0.0D); 
/* 332 */     if (corner != 0) worldRender.b(x2, y2, 0.0D); 
/* 333 */     if (corner != 1) worldRender.b(x2, y1, 0.0D); 
/* 334 */     if (corner != 2) worldRender.b(x1, y1, 0.0D); 
/* 335 */     tessellator.b();
/*     */     
/* 337 */     GL.glEnableTexture2D();
/* 338 */     GL.glDisableBlend();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedModelRectFromIcon(int x, int y, Icon icon, int width, int height) {
/* 346 */     ckx tessellator = ckx.a();
/* 347 */     civ worldRender = tessellator.c();
/* 348 */     worldRender.b();
/* 349 */     worldRender.a((x + 0), (y + height), this.e, icon.getMinU(), icon.getMaxV());
/* 350 */     worldRender.a((x + width), (y + height), this.e, icon.getMaxU(), icon.getMaxV());
/* 351 */     worldRender.a((x + width), (y + 0), this.e, icon.getMaxU(), icon.getMinV());
/* 352 */     worldRender.a((x + 0), (y + 0), this.e, icon.getMinU(), icon.getMinV());
/* 353 */     tessellator.b();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\layout\LayoutWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */