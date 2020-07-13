/*     */ package net.eq2online.macros.gui.shared;
/*     */ 
/*     */ import bsu;
/*     */ import bug;
/*     */ import bxf;
/*     */ import civ;
/*     */ import ckx;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import cqh;
/*     */ import cxy;
/*     */ import cye;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.GuiControl;
/*     */ import net.eq2online.macros.gui.controls.GuiTextEditor;
/*     */ import net.eq2online.macros.gui.screens.GuiEditText;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import oa;
/*     */ import org.lwjgl.input.Mouse;
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
/*     */ public abstract class GuiScreenEx
/*     */   extends bxf
/*     */ {
/*  37 */   public static int guiScaleFactor = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   protected static cqh itemRenderer = new cqh(bsu.z().N(), bsu.z().af().a().a());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   protected static float texMapScale = 0.00390625F;
/*     */ 
/*     */ 
/*     */   
/*     */   protected bug h;
/*     */ 
/*     */ 
/*     */   
/*     */   private GuiControl rightClickedButton;
/*     */ 
/*     */ 
/*     */   
/*  59 */   protected int updateCounter = 0;
/*     */ 
/*     */   
/*     */   protected boolean generateMouseDragEvents = false;
/*     */   
/*     */   protected int rowPos;
/*     */   
/*  66 */   protected int rowSpacing = 12;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/*  74 */     this.updateCounter++;
/*     */     
/*  76 */     for (bug control : getLegacyControlList()) {
/*     */       
/*  78 */       if (control instanceof GuiControlEx) ((GuiControlEx)control).updateCounter++; 
/*  79 */       if (control instanceof GuiTextEditor) ((GuiTextEditor)control).onUpdate();
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<bug> getLegacyControlList() {
/*  86 */     return this.n;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final List<GuiControl> getControlList() {
/*  92 */     return this.n;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void clearControlList() {
/*  97 */     getControlList().clear();
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void addControl(GuiControl control) {
/* 102 */     getControlList().add(control);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void removeControl(GuiControl control) {
/* 107 */     getControlList().remove(control);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreenWithEnabledState(int mouseX, int mouseY, float f, boolean enabled) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawTooltip(String tooltipText, int mouseX, int mouseY, int colour, int backgroundColour) {
/* 126 */     int textSize = this.q.a(tooltipText);
/* 127 */     mouseX = Math.min(this.l - textSize - 6, mouseX - 6);
/* 128 */     mouseY = Math.max(0, mouseY - 18);
/*     */     
/* 130 */     a(mouseX, mouseY, mouseX + textSize + 6, mouseY + 16, backgroundColour);
/* 131 */     c(this.q, tooltipText, mouseX + 3, mouseY + 4, colour);
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
/*     */   protected void drawFunkyTooltip(String tooltipText, int mouseX, int mouseY, int fontColour) {
/* 144 */     drawFunkyTooltip(tooltipText, mouseX, mouseY, fontColour, -267386864, 1347420415);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawFunkyTooltip(String tooltipText, int mouseX, int mouseY, int fontColour, int colour1, int colour2) {
/* 149 */     drawFunkyTooltip(tooltipText.split("\\n"), mouseX, mouseY, fontColour, colour1, colour2);
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
/*     */   protected void drawFunkyTooltip(String[] tooltipText, int mouseX, int mouseY, int fontColour, int colour1, int colour2) {
/* 164 */     int xPos = mouseX + 12;
/* 165 */     int yPos = mouseY - 12;
/* 166 */     int textSize = 0;
/*     */     
/* 168 */     for (String toolTipLine : tooltipText)
/*     */     {
/* 170 */       textSize = Math.max(textSize, this.q.a(toolTipLine));
/*     */     }
/*     */     
/* 173 */     int height = 8 * tooltipText.length + 2 * (tooltipText.length - 1);
/*     */     
/* 175 */     this.e = 300.0F;
/* 176 */     itemRenderer.a = 300.0F;
/* 177 */     a(xPos - 3, yPos - 4, xPos + textSize + 3, yPos - 3, colour1, colour1);
/* 178 */     a(xPos - 3, yPos + height + 3, xPos + textSize + 3, yPos + height + 4, colour1, colour1);
/* 179 */     a(xPos - 3, yPos - 3, xPos + textSize + 3, yPos + height + 3, colour1, colour1);
/* 180 */     a(xPos - 4, yPos - 3, xPos - 3, yPos + height + 3, colour1, colour1);
/* 181 */     a(xPos + textSize + 3, yPos - 3, xPos + textSize + 4, yPos + height + 3, colour1, colour1);
/* 182 */     int colour3 = (colour2 & 0xFEFEFE) >> 1 | colour2 & 0xFF000000;
/* 183 */     a(xPos - 3, yPos - 3 + 1, xPos - 3 + 1, yPos + height + 3 - 1, colour2, colour3);
/* 184 */     a(xPos + textSize + 2, yPos - 3 + 1, xPos + textSize + 3, yPos + height + 3 - 1, colour2, colour3);
/* 185 */     a(xPos - 3, yPos - 3, xPos + textSize + 3, yPos - 3 + 1, colour2, colour2);
/* 186 */     a(xPos - 3, yPos + height + 2, xPos + textSize + 3, yPos + height + 3, colour3, colour3);
/*     */     
/* 188 */     for (String toolTipLine : tooltipText) {
/*     */       
/* 190 */       this.q.a(toolTipLine, xPos, yPos, fontColour);
/* 191 */       yPos += 10;
/*     */     } 
/*     */     
/* 194 */     this.e = 0.0F;
/* 195 */     itemRenderer.a = 0.0F;
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
/*     */   protected void drawHorizontalProgressBar(float value, float maxValue, int x, int y, int width, int height) {
/* 212 */     value = Math.min(Math.max(value, 0.0F), maxValue);
/*     */     
/* 214 */     GL.glDepthFunc(518);
/* 215 */     GL.glEnableTexture2D();
/* 216 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 217 */     setTexMapSize(128);
/*     */     
/* 219 */     AbstractionLayer.bindTexture(ResourceLocations.EXT);
/* 220 */     drawTexturedModalRect(x, y, x + 4, y + height, 0, 120, 4, 128);
/* 221 */     drawTexturedModalRect(x + width - 4, y, x + width, y + height, 124, 120, 128, 128);
/* 222 */     drawTexturedModalRect(x + 4, y, x + width - 4, y + height, 4, 120, 124, 128);
/*     */     
/* 224 */     if (value > 0.0F && maxValue != 0.0F) {
/*     */       
/* 226 */       int endWidth = 4;
/* 227 */       int barWidth = (int)(value / maxValue * width);
/* 228 */       if (barWidth < 8) endWidth = barWidth / 2;
/*     */       
/* 230 */       drawTexturedModalRect(x, y, x + endWidth, y + height, 0, 112, 4, 120);
/* 231 */       drawTexturedModalRect(x + barWidth - endWidth, y, x + barWidth, y + height, 124, 112, 128, 120);
/* 232 */       setTexMapSize(256);
/*     */       
/* 234 */       drawTexturedModalRect(x + endWidth, y, x + barWidth - endWidth, y + height, 8, 224, Math.min(barWidth - endWidth * 2, 248), 240);
/*     */     } 
/*     */     
/* 237 */     setTexMapSize(256);
/* 238 */     GL.glDepthFunc(515);
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
/* 249 */     texMapScale = 1.0F / textureSize;
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
/*     */   public void drawTexturedModalRect(oa texture, int x, int y, int x2, int y2, int u, int v, int u2, int v2) {
/* 267 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 268 */     AbstractionLayer.bindTexture(texture);
/*     */     
/* 270 */     drawTexturedModalRect(x, y, x2, y2, u, v, u2, v2);
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
/* 288 */     ckx tessellator = AbstractionLayer.getTessellator();
/* 289 */     civ worldRender = tessellator.c();
/* 290 */     worldRender.b();
/* 291 */     worldRender.a(x, y2, this.e, (u * texMapScale), (v2 * texMapScale));
/* 292 */     worldRender.a(x2, y2, this.e, (u2 * texMapScale), (v2 * texMapScale));
/* 293 */     worldRender.a(x2, y, this.e, (u2 * texMapScale), (v * texMapScale));
/* 294 */     worldRender.a(x, y, this.e, (u * texMapScale), (v * texMapScale));
/* 295 */     tessellator.b();
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
/*     */   public void drawTessellatedModalRectV(oa texture, int textureSize, int x, int y, int x2, int y2, int u, int v, int u2, int v2) {
/* 315 */     int tileSize = (v2 - v) / 2;
/* 316 */     int vMidTop = v + tileSize;
/* 317 */     int vMidBtm = vMidTop + 1;
/*     */     
/* 319 */     setTexMapSize(textureSize);
/* 320 */     drawTexturedModalRect(texture, x, y, x2, y + tileSize, u, v, u2, vMidTop);
/* 321 */     drawTexturedModalRect(texture, x, y + tileSize, x2, y2 - tileSize + 1, u, vMidTop, u2, vMidBtm);
/* 322 */     drawTexturedModalRect(texture, x, y2 - tileSize + 1, x2, y2, u, vMidBtm, u2, v2);
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
/*     */   public void drawTessellatedModalRectH(oa texture, int textureSize, int x, int y, int x2, int y2, int u, int v, int u2, int v2) {
/* 342 */     int tileSize = (u2 - u) / 2;
/* 343 */     int uMidLeft = u + tileSize;
/* 344 */     int uMidRight = uMidLeft + 1;
/*     */     
/* 346 */     setTexMapSize(textureSize);
/* 347 */     drawTexturedModalRect(texture, x, y, x + tileSize, y2, u, v, uMidLeft, v2);
/* 348 */     drawTexturedModalRect(texture, x + tileSize, y, x2 - tileSize + 1, y2, uMidLeft, v, uMidRight, v2);
/* 349 */     drawTexturedModalRect(texture, x2 - tileSize + 1, y, x2, y2, uMidRight, v, u2, v2);
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
/*     */   public void drawTessellatedModalBorderRect(oa texture, int textureSize, int x, int y, int x2, int y2, int u, int v, int u2, int v2) {
/* 369 */     drawTessellatedModalBorderRect(texture, textureSize, x, y, x2, y2, u, v, u2, v2, Math.min((x2 - x) / 2 - 1, (y2 - y) / 2 - 1));
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
/*     */   public void drawTessellatedModalBorderRect(oa texture, int textureSize, int x, int y, int x2, int y2, int u, int v, int u2, int v2, int borderSize) {
/* 391 */     int tileSize = Math.min((u2 - u) / 2 - 1, (v2 - v) / 2 - 1);
/*     */     
/* 393 */     int ul = u + tileSize, ur = u2 - tileSize, vt = v + tileSize, vb = v2 - tileSize;
/* 394 */     int xl = x + borderSize, xr = x2 - borderSize, yt = y + borderSize, yb = y2 - borderSize;
/*     */     
/* 396 */     setTexMapSize(textureSize);
/*     */     
/* 398 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 399 */     AbstractionLayer.bindTexture(texture);
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
/* 416 */     drawTexturedModalRect(x, y, xl, yt, u, v, ul, vt);
/* 417 */     drawTexturedModalRect(xl, y, xr, yt, ul, v, ur, vt);
/* 418 */     drawTexturedModalRect(xr, y, x2, yt, ur, v, u2, vt);
/* 419 */     drawTexturedModalRect(x, yb, xl, y2, u, vb, ul, v2);
/* 420 */     drawTexturedModalRect(xl, yb, xr, y2, ul, vb, ur, v2);
/* 421 */     drawTexturedModalRect(xr, yb, x2, y2, ur, vb, u2, v2);
/* 422 */     drawTexturedModalRect(x, yt, xl, yb, u, vt, ul, vb);
/* 423 */     drawTexturedModalRect(xr, yt, x2, yb, ur, vt, u2, vb);
/* 424 */     drawTexturedModalRect(xl, yt, xr, yb, ul, vt, ur, vb);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void p() throws IOException {
/* 433 */     super.p();
/*     */     
/* 435 */     int mouseWheelDelta = Mouse.getDWheel();
/*     */     
/* 437 */     if (mouseWheelDelta != 0) {
/* 438 */       mouseWheelScrolled(mouseWheelDelta);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void k() throws IOException {
/* 444 */     super.k();
/*     */     
/* 446 */     if (this.generateMouseDragEvents && Mouse.getEventButton() == -1) {
/*     */       
/* 448 */       int mouseX = Mouse.getEventX() * this.l / this.j.d;
/* 449 */       int mouseY = this.m - Mouse.getEventY() * this.m / this.j.e - 1;
/*     */       
/* 451 */       a(mouseX, mouseY, -1, 0L);
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
/*     */   protected void mouseWheelScrolled(int mouseWheelDelta) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(int mouseX, int mouseY, int button) throws IOException {
/* 475 */     for (int controlIndex = 0; controlIndex < getControlList().size(); controlIndex++) {
/*     */       
/* 477 */       bug guibutton = (bug)getControlList().get(controlIndex);
/*     */ 
/*     */       
/* 480 */       if (button == 0 && guibutton.c(this.j, mouseX, mouseY)) {
/*     */         
/* 482 */         this.h = guibutton;
/*     */         
/* 484 */         if (!(guibutton instanceof GuiControlEx) || ((GuiControlEx)guibutton).isActionPerformed()) {
/*     */           
/* 486 */           this.j.U().a((cye)cxy.a(ResourceLocations.BUTTON_PRESS, 1.0F));
/* 487 */           a(guibutton);
/*     */ 
/*     */           
/* 490 */           if (this.j.m != this) {
/* 491 */             guibutton.a(mouseX, mouseY);
/*     */           }
/*     */         } 
/* 494 */       } else if (button == 1 && guibutton instanceof GuiControl && ((GuiControl)guibutton).isMouseOver(this.j, mouseX, mouseY)) {
/*     */         
/* 496 */         this.rightClickedButton = (GuiControl)guibutton;
/* 497 */         this.rightClickedButton.rightClicked(this.j, mouseX, mouseY, true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void b(int mouseX, int mouseY, int button) {
/* 508 */     if (this.h != null && button == 0) {
/*     */       
/* 510 */       this.h.a(mouseX, mouseY);
/* 511 */       this.h = null;
/*     */     } 
/*     */     
/* 514 */     if (this.rightClickedButton != null && button == 1) {
/*     */       
/* 516 */       if (this.rightClickedButton.isMouseOver(this.j, mouseX, mouseY)) {
/* 517 */         this.rightClickedButton.rightClicked(this.j, mouseX, mouseY, false);
/*     */       }
/* 519 */       this.rightClickedButton = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFinishEditingTextFile(GuiEditText editor, File file) {
/* 526 */     AbstractionLayer.displayGuiScreen(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void displayDialog(GuiDialogBox dialog) {
/* 531 */     AbstractionLayer.displayGuiScreen(dialog);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDialogClosed(GuiDialogBox dialog) {
/* 537 */     AbstractionLayer.displayGuiScreen(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawTexturedModalRectEx(int x, int y, int x2, int y2, int u, int v, int u2, int v2, int zLevel) {
/* 546 */     float texMapScale = 0.015625F;
/*     */     
/* 548 */     ckx tessellator = AbstractionLayer.getTessellator();
/* 549 */     civ worldRender = tessellator.c();
/* 550 */     worldRender.b();
/* 551 */     worldRender.a(x, y2, zLevel, (u * texMapScale), (v2 * texMapScale));
/* 552 */     worldRender.a(x2, y2, zLevel, (u2 * texMapScale), (v2 * texMapScale));
/* 553 */     worldRender.a(x2, y, zLevel, (u2 * texMapScale), (v * texMapScale));
/* 554 */     worldRender.a(x, y, zLevel, (u * texMapScale), (v * texMapScale));
/* 555 */     tessellator.b();
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
/*     */   protected void drawSpacedString(String text, int xPos, int foreColour) {
/* 568 */     int additionalSpacing = 0;
/*     */     
/* 570 */     if (text != null) {
/*     */       
/* 572 */       while (text.endsWith("¬")) {
/*     */         
/* 574 */         additionalSpacing += 5;
/* 575 */         text = text.substring(0, text.length() - 1);
/*     */       } 
/*     */       
/* 578 */       this.q.a(text, xPos, this.rowPos, foreColour);
/*     */     } 
/*     */     
/* 581 */     this.rowPos += this.rowSpacing + additionalSpacing;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\shared\GuiScreenEx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */