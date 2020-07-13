/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bxf;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import com.mumfrey.liteloader.gl.GLClippingPlanes;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.GuiControl;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.gui.controls.GuiScrollBar;
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
/*     */ public class GuiEditThumbnails
/*     */   extends GuiScreenWithHeader
/*     */ {
/*     */   private bxf parentScreen;
/*  31 */   private int iconSpacing = 3;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   private int totalHeight = 320;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private GuiScrollBar scrollBar;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   private static int lastScrollBarValue = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiEditThumbnails(bxf oldScreen) {
/*  56 */     super(0, 0);
/*     */     
/*  58 */     this.parentScreen = oldScreen;
/*     */     
/*  60 */     this.screenTitle = LocalisationProvider.getLocalisedString("macro.icons.title");
/*  61 */     this.screenBanner = LocalisationProvider.getLocalisedString("macro.icons.help");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/*  71 */     int columnWidth = (this.l - 44) / 2;
/*  72 */     int iconsPerColumn = columnWidth / (16 + this.iconSpacing);
/*     */     
/*  74 */     if (iconsPerColumn > 0) {
/*     */       
/*  76 */       this.totalHeight = (256 / iconsPerColumn + 2) * (16 + this.iconSpacing) + 10;
/*     */     }
/*     */     else {
/*     */       
/*  80 */       this.totalHeight = this.m;
/*     */     } 
/*     */     
/*  83 */     this.scrollBar = new GuiScrollBar(this.j, 0, this.l - 24, 43, 20, this.m - 50, 0, Math.max(0, this.totalHeight - this.m - 50), GuiScrollBar.ScrollBarOrientation.Vertical);
/*  84 */     this.scrollBar.setValue(lastScrollBarValue);
/*  85 */     addControl((GuiControl)this.scrollBar);
/*     */     
/*  87 */     super.b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float f) {
/*  93 */     super.a(mouseX, mouseY, f);
/*     */     
/*  95 */     GL.glDisableLighting();
/*  96 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/*  98 */     int columnWidth = (this.l - 44) / 2;
/*     */     
/* 100 */     c(this.q, LocalisationProvider.getLocalisedString("macro.icons.town"), 14, 29, -1);
/* 101 */     c(this.q, LocalisationProvider.getLocalisedString("macro.icons.homes"), 14 + columnWidth + 10, 29, -1);
/*     */     
/* 103 */     GLClippingPlanes.glEnableVerticalClipping(44, this.m - 7);
/*     */     
/* 105 */     mouseY += this.scrollBar.getValue();
/* 106 */     lastScrollBarValue = this.scrollBar.getValue();
/*     */     
/* 108 */     GL.glPushMatrix();
/* 109 */     GL.glTranslatef(0.0F, -this.scrollBar.getValue(), 0.0F);
/*     */     
/* 111 */     int mouseOverIndex1 = drawIcons(ResourceLocations.DYNAMIC_TOWNS, 10, 44, columnWidth, mouseX, mouseY);
/* 112 */     int mouseOverIndex2 = drawIcons(ResourceLocations.DYNAMIC_HOMES, 10 + columnWidth + 10, 44, columnWidth, mouseX, mouseY);
/*     */     
/* 114 */     GLClippingPlanes.glDisableClipping();
/*     */ 
/*     */     
/* 117 */     if (mouseOverIndex1 > -1 && mouseY > 44) {
/* 118 */       drawTooltip("Icon " + (mouseOverIndex1 + 1), mouseX, mouseY, 16777215, -805306368);
/*     */     }
/* 120 */     if (mouseOverIndex2 > -1 && mouseY > 44) {
/* 121 */       drawTooltip("Icon " + (mouseOverIndex2 + 1), mouseX, mouseY, 16777215, -805306368);
/*     */     }
/* 123 */     GL.glPopMatrix();
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
/*     */   protected int drawIcons(oa texture, int left, int top, int columnWidth, int mouseX, int mouseY) {
/* 138 */     AbstractionLayer.bindTexture(texture);
/*     */     
/* 140 */     int xPosition = left, yPosition = top;
/* 141 */     int mouseOverIndex = -1;
/*     */     
/* 143 */     for (int iconIndex = 0; iconIndex < 256; iconIndex++) {
/*     */       
/* 145 */       int u = iconIndex % 16 * 16;
/* 146 */       int v = iconIndex / 16 * 16;
/*     */       
/* 148 */       if (mouseX > xPosition - this.iconSpacing && mouseY > yPosition - this.iconSpacing && mouseX < xPosition + 16 && mouseY < yPosition + 16) {
/*     */         
/* 150 */         a(xPosition - 2, yPosition - 2, xPosition + 16 + 2, yPosition + 16 + 2, -1);
/* 151 */         mouseOverIndex = iconIndex;
/*     */       } 
/*     */       
/* 154 */       drawTexturedModalRect(xPosition, yPosition, xPosition + 16, yPosition + 16, u, v, u + 16, v + 16);
/*     */       
/* 156 */       xPosition += 16 + this.iconSpacing;
/*     */       
/* 158 */       if (xPosition > left + columnWidth - 20) {
/*     */         
/* 160 */         xPosition = left;
/* 161 */         yPosition += 17 + this.iconSpacing;
/*     */       } 
/*     */     } 
/*     */     
/* 165 */     return mouseOverIndex;
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
/*     */   protected int getMouseOverIndex(int left, int top, int columnWidth, int mouseX, int mouseY) {
/* 180 */     int xPosition = left, yPosition = top;
/*     */     
/* 182 */     for (int iconIndex = 0; iconIndex < 256; iconIndex++) {
/*     */       
/* 184 */       if (mouseX > xPosition - this.iconSpacing && mouseY > yPosition - this.iconSpacing && mouseX < xPosition + 16 && mouseY < yPosition + 16) {
/* 185 */         return iconIndex;
/*     */       }
/* 187 */       xPosition += 16 + this.iconSpacing;
/*     */       
/* 189 */       if (xPosition > left + columnWidth - 20) {
/*     */         
/* 191 */         xPosition = left;
/* 192 */         yPosition += 17 + this.iconSpacing;
/*     */       } 
/*     */     } 
/*     */     
/* 196 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClickedEx(int mouseX, int mouseY, int button) {
/* 202 */     if (button == 0 && mouseY > 44) {
/*     */       
/* 204 */       mouseY += this.scrollBar.getValue();
/*     */       
/* 206 */       int columnWidth = (this.l - 44) / 2;
/* 207 */       int mouseOverTownsIndex = getMouseOverIndex(10, 44, columnWidth, mouseX, mouseY);
/*     */       
/* 209 */       if (mouseOverTownsIndex > -1) {
/*     */         
/* 211 */         MacroModCore.getInstance().beginThumbnailCapture(this, ResourceLocations.TOWNS, mouseOverTownsIndex);
/*     */       }
/*     */       else {
/*     */         
/* 215 */         int mouseOverHomesIndex = getMouseOverIndex(10 + columnWidth + 10, 44, columnWidth, mouseX, mouseY);
/*     */         
/* 217 */         if (mouseOverHomesIndex > -1)
/*     */         {
/* 219 */           MacroModCore.getInstance().beginThumbnailCapture(this, ResourceLocations.HOMES, mouseOverHomesIndex);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseWheelScrolled(int mouseWheelDelta) {
/* 231 */     mouseWheelDelta /= 3;
/* 232 */     this.scrollBar.setValue(this.scrollBar.getValue() - mouseWheelDelta);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) {
/* 238 */     if (keyCode == 1)
/*     */     {
/* 240 */       AbstractionLayer.displayGuiScreen(this.parentScreen);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCloseClick() {
/* 250 */     AbstractionLayer.displayGuiScreen(this.parentScreen);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\screens\GuiEditThumbnails.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */