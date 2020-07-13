/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bty;
/*     */ import bxf;
/*     */ import byj;
/*     */ import civ;
/*     */ import ckx;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.gui.controls.GuiDropDownMenu;
/*     */ import net.eq2online.macros.gui.shared.GuiControlEx;
/*     */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GuiScreenWithHeader
/*     */   extends GuiScreenEx
/*     */ {
/*  27 */   protected String screenTitle = "screen.title";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  32 */   protected String screenBanner = "screen.banner";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   protected int screenBackColour = -1342177280;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   protected int screenTitleColour = -256;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   protected int screenBannerColour = -22016;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean screenDrawHeader = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean screenDrawMinButton = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean screenDrawMenuButton = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean screenDrawBackground = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean screenDrawControls = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean screenCentreBanner = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   protected int screenBackgroundSpaceBottom = 2;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean screenEnableGuiAnimation = true;
/*     */ 
/*     */ 
/*     */   
/*     */   protected String screenTooltip;
/*     */ 
/*     */ 
/*     */   
/*  94 */   private int page = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   private int pages = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   private int targetPage = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   private long tweenBeginTime = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   private float tweenBeginPartialTime = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   protected GuiDropDownMenu screenMenu = new GuiDropDownMenu(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiScreenWithHeader(int pages, int initialPage) {
/* 129 */     this.pages = pages;
/* 130 */     this.page = this.targetPage = Math.min(Math.max(initialPage, 0), pages);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPage() {
/* 140 */     return this.page;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreenWithEnabledState(int mouseX, int mouseY, float partialTick, boolean enabled) {
/* 149 */     a(mouseX, mouseY, partialTick);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float partialTick) {
/* 156 */     GL.glEnableAlphaTest();
/* 157 */     GL.glAlphaFunc(516, 0.1F);
/*     */ 
/*     */     
/* 160 */     if (this.screenDrawHeader) {
/*     */       
/* 162 */       int left = 2;
/*     */       
/* 164 */       if (this.screenDrawMenuButton) {
/*     */         
/* 166 */         if (this.screenMenu.isDropDownVisible()) {
/* 167 */           a(1, 1, 21, 21, -1118482);
/*     */         }
/* 169 */         a(left, 2, left + 18, 20, this.screenMenu.isDropDownVisible() ? (0xFF000000 | this.screenBackColour) : this.screenBackColour);
/*     */         
/* 171 */         AbstractionLayer.bindTexture(ResourceLocations.MAIN);
/*     */         
/* 173 */         GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 174 */         drawTexturedModalRect(left + 3, 7, left + 15, 15, 104, 80, 128, 96);
/*     */         
/* 176 */         left += 20;
/*     */       } 
/*     */       
/* 179 */       if (this.pages > 0) {
/*     */ 
/*     */         
/* 182 */         a(left, 2, left + 18, 20, this.screenBackColour);
/* 183 */         a(left + 20, 2, left + 158, 20, this.screenBackColour);
/* 184 */         a(left + 160, 2, left + 178, 20, this.screenBackColour);
/*     */         
/* 186 */         AbstractionLayer.bindTexture(ResourceLocations.FIXEDWIDTHFONT);
/*     */ 
/*     */         
/* 189 */         float arrowColour = (this.targetPage > 0) ? 1.0F : 0.3F;
/* 190 */         GL.glColor4f(arrowColour, arrowColour, 0.0F, 1.0F);
/* 191 */         drawTexturedModalRect(left + 5, 7, left + 13, 15, 16, 16, 32, 32);
/*     */ 
/*     */         
/* 194 */         arrowColour = (this.targetPage < this.pages - 1) ? 1.0F : 0.4F;
/* 195 */         GL.glColor4f(arrowColour, arrowColour, 0.0F, 1.0F);
/* 196 */         drawTexturedModalRect(left + 165, 7, left + 173, 15, 0, 16, 16, 32);
/*     */       }
/*     */       else {
/*     */         
/* 200 */         a(left, 2, left + 178, 20, this.screenBackColour);
/*     */       } 
/*     */ 
/*     */       
/* 204 */       a(this.l - 20, 2, this.l - 2, 20, this.screenBackColour);
/*     */ 
/*     */       
/* 207 */       a(this.q, this.screenTitle, left + 88, 7, this.screenTitleColour);
/*     */ 
/*     */       
/* 210 */       drawTexturedModalRect(ResourceLocations.MAIN, this.l - 17, 5, this.l - 5, 17, 104, 104, 128, 128);
/*     */       
/* 212 */       if (this.screenDrawMinButton) {
/*     */         
/* 214 */         a(this.l - 42, 2, this.l - 22, 20, this.screenBackColour);
/*     */         
/* 216 */         AbstractionLayer.bindTexture(ResourceLocations.FIXEDWIDTHFONT);
/*     */ 
/*     */         
/* 219 */         GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 220 */         drawTexturedModalRect(this.l - 36, 7, this.l - 28, 15, 240, 16, 256, 32);
/*     */       } 
/*     */       
/* 223 */       drawTitle(this.screenBanner, this.screenCentreBanner, left + 180, 2, this.l - (this.screenDrawMinButton ? 44 : 22), this.screenBannerColour, this.screenBackColour);
/*     */     } 
/*     */     
/* 226 */     int offsetMouseX = mouseX;
/*     */ 
/*     */     
/* 229 */     if (this.pages > 0) {
/*     */       
/* 231 */       GL.glPushMatrix();
/*     */       
/* 233 */       if (this.targetPage != this.page && !this.screenEnableGuiAnimation)
/*     */       {
/* 235 */         this.page = this.targetPage;
/*     */       }
/*     */       
/* 238 */       if (this.targetPage == this.page) {
/*     */         
/* 240 */         this.tweenBeginPartialTime = partialTick;
/* 241 */         GL.glTranslatef((this.l * this.page * -1), 0.0F, 0.0F);
/* 242 */         offsetMouseX = mouseX + this.l * this.page;
/*     */       }
/*     */       else {
/*     */         
/* 246 */         float tweenPct = (this.updateCounter + partialTick - (float)this.tweenBeginTime + this.tweenBeginPartialTime) * 0.05F;
/*     */         
/* 248 */         if (tweenPct >= 0.5F) {
/* 249 */           this.page = this.targetPage;
/*     */         }
/* 251 */         tweenPct = (float)Math.sin(tweenPct * Math.PI);
/*     */         
/* 253 */         float pageOffset = (this.l * this.page * -1);
/* 254 */         float targetPageOffset = ((this.l * this.targetPage * -1) - pageOffset) * tweenPct;
/*     */         
/* 256 */         GL.glTranslatef(pageOffset + targetPageOffset, 0.0F, 0.0F);
/* 257 */         offsetMouseX = mouseX - (int)(pageOffset + targetPageOffset);
/*     */       } 
/*     */       
/* 260 */       if (this.screenDrawBackground)
/*     */       {
/* 262 */         for (int pg = 0; pg < this.pages; pg++)
/*     */         {
/* 264 */           int offset = this.l * pg;
/* 265 */           a(2 + offset, 22, this.l - 2 + offset, this.m - this.screenBackgroundSpaceBottom, this.screenBackColour);
/*     */         }
/*     */       
/*     */       }
/* 269 */     } else if (this.screenDrawBackground) {
/*     */       
/* 271 */       a(2, 22, this.l - 2, this.m - this.screenBackgroundSpaceBottom, this.screenBackColour);
/*     */     } 
/*     */ 
/*     */     
/* 275 */     drawPages(this.targetPage, offsetMouseX, mouseY, partialTick);
/*     */ 
/*     */     
/* 278 */     if (this.screenDrawControls)
/*     */     {
/* 280 */       super.a(offsetMouseX, mouseY, partialTick);
/*     */     }
/*     */ 
/*     */     
/* 284 */     postRender(this.targetPage, offsetMouseX, mouseY, partialTick);
/*     */ 
/*     */     
/* 287 */     if (this.screenTooltip != null)
/*     */     {
/* 289 */       drawTooltip(this.screenTooltip, mouseX, mouseY, 16777215, -805306368);
/*     */     }
/*     */ 
/*     */     
/* 293 */     if (this.pages > 0)
/*     */     {
/* 295 */       GL.glPopMatrix();
/*     */     }
/*     */     
/* 298 */     if (this.screenDrawMenuButton) {
/*     */       
/* 300 */       this.screenMenu.drawControlAt(2, 20, mouseX, mouseY);
/* 301 */       if (this.screenMenu.isDropDownVisible()) {
/* 302 */         a(2, 19, 20, 22, -16777216);
/*     */       }
/*     */     } 
/* 305 */     GL.glAlphaFunc(516, 0.1F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void beginTweening(int targetPage) {
/* 314 */     if (targetPage >= 0 && targetPage < this.pages && targetPage != this.targetPage) {
/*     */       
/* 316 */       this.targetPage = targetPage;
/* 317 */       this.tweenBeginTime = this.updateCounter;
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
/*     */   protected void drawPages(int currentPage, int mouseX, int mouseY, float partialTick) {}
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
/*     */   protected void postRender(int currentPage, int mouseX, int mouseY, float partialTick) {}
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
/*     */   public static void drawTitle(String banner, boolean centre, int left, int top, int right, int bannerColour, int backColour) {
/* 360 */     int bottom = top + 18;
/*     */     
/* 362 */     float f = (backColour >> 24 & 0xFF) / 255.0F;
/* 363 */     float f1 = (backColour >> 16 & 0xFF) / 255.0F;
/* 364 */     float f2 = (backColour >> 8 & 0xFF) / 255.0F;
/* 365 */     float f3 = (backColour & 0xFF) / 255.0F;
/* 366 */     ckx tessellator = AbstractionLayer.getTessellator();
/* 367 */     civ worldRender = tessellator.c();
/* 368 */     GL.glEnableBlend();
/* 369 */     GL.glDisableTexture2D();
/* 370 */     GL.glBlendFunc(770, 771);
/* 371 */     GL.glColor4f(f1, f2, f3, f);
/* 372 */     worldRender.b();
/* 373 */     worldRender.b(right, top, 0.0D);
/* 374 */     worldRender.b(left, top, 0.0D);
/* 375 */     worldRender.b(left, bottom, 0.0D);
/* 376 */     worldRender.b(right, bottom, 0.0D);
/* 377 */     tessellator.b();
/* 378 */     GL.glEnableTexture2D();
/*     */     
/* 380 */     bty fontRenderer = AbstractionLayer.getFontRenderer();
/*     */     
/* 382 */     if (centre) {
/*     */       
/* 384 */       fontRenderer.a(banner, ((right - left) / 2 + left - fontRenderer.a(banner) / 2), (top + 5), bannerColour);
/*     */     }
/*     */     else {
/*     */       
/* 388 */       GuiControlEx.drawStringWithEllipsis(fontRenderer, banner, left + 4, top + 5, right - left - 8, bannerColour);
/*     */     } 
/*     */     
/* 391 */     GL.glDisableBlend();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(int mouseX, int mouseY, int button) throws IOException {
/* 397 */     if (this.screenDrawHeader && button == 0) {
/*     */       
/* 399 */       int left = 2;
/* 400 */       boolean wasVisible = this.screenMenu.isDropDownVisible();
/*     */       
/* 402 */       if (this.screenDrawMenuButton) {
/*     */         
/* 404 */         String menuItem = this.screenMenu.mousePressed(mouseX, mouseY);
/* 405 */         if (menuItem != null) {
/*     */           
/* 407 */           onMenuItemClicked(menuItem);
/*     */           
/*     */           return;
/*     */         } 
/* 411 */         left += 20;
/*     */       } 
/*     */       
/* 414 */       if (mouseX > 2 && mouseY > 2 && mouseY < 21)
/*     */       {
/* 416 */         if (mouseX > left + 180) {
/*     */           
/* 418 */           if (mouseX > this.l - 22)
/*     */           {
/* 420 */             onCloseClick();
/*     */           }
/* 422 */           else if (mouseX > this.l - 44 && this.screenDrawMinButton)
/*     */           {
/* 424 */             onMinimiseClick();
/*     */           }
/*     */           else
/*     */           {
/* 428 */             onHeaderClick();
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 433 */           if (this.screenDrawMenuButton && mouseX < left && mouseX > 2)
/*     */           {
/* 435 */             if (!wasVisible) onMenuButtonClick();
/*     */           
/*     */           }
/* 438 */           if (mouseX > left)
/*     */           {
/* 440 */             if (this.pages > 0) {
/*     */               
/* 442 */               if (mouseX < left + 20)
/*     */               {
/* 444 */                 onPageDownClick();
/*     */               }
/* 446 */               else if (mouseX > left + 160)
/*     */               {
/* 448 */                 onPageUpClick();
/*     */               }
/*     */               else
/*     */               {
/* 452 */                 onTitleClick();
/*     */               }
/*     */             
/*     */             } else {
/*     */               
/* 457 */               onTitleClick();
/*     */             } 
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 464 */     mouseX += this.l * this.page;
/*     */     
/* 466 */     mouseClickedEx(mouseX, mouseY, button);
/* 467 */     super.a(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getButtonOverAt(int mouseX, int mouseY) {
/* 478 */     int overButton = 0;
/* 479 */     int left = this.screenDrawMenuButton ? 20 : 2;
/*     */     
/* 481 */     if (mouseX < 3) {
/*     */       
/* 483 */       overButton = 1;
/*     */     }
/* 485 */     else if (mouseX > this.l - 3) {
/*     */       
/* 487 */       overButton = 2;
/*     */     }
/* 489 */     else if (mouseY < 20) {
/*     */       
/* 491 */       if (mouseX > left && mouseX < left + 20) {
/* 492 */         overButton = 1;
/* 493 */       } else if (mouseX > left + 160 && mouseX < left + 182) {
/* 494 */         overButton = 2;
/*     */       } 
/*     */     } 
/* 497 */     return overButton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClickedEx(int mouseX, int mouseY, int button) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void b(int mouseX, int mouseY, int button) {
/* 517 */     mouseX += this.l * this.page;
/* 518 */     super.b(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onPageUpClick() {
/* 523 */     if (this.page < this.pages)
/*     */     {
/* 525 */       beginTweening(this.targetPage + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onPageDownClick() {
/* 531 */     if (this.page > 0)
/*     */     {
/* 533 */       beginTweening(this.targetPage - 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onHeaderClick() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTitleClick() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMinimiseClick() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMenuButtonClick() {
/* 554 */     if (this.screenDrawMenuButton)
/*     */     {
/* 556 */       this.screenMenu.showDropDown();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMenuItemClicked(String menuItem) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCloseClick() {
/* 567 */     AbstractionLayer.displayGuiScreen((AbstractionLayer.getWorld() != null) ? null : (bxf)new byj(null, this.j.t));
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\screens\GuiScreenWithHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */