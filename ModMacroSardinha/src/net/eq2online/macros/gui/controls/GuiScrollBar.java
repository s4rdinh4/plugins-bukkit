/*     */ package net.eq2online.macros.gui.controls;
/*     */ 
/*     */ import bsu;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.gui.shared.GuiControlEx;
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
/*     */ public class GuiScrollBar
/*     */   extends GuiControlEx
/*     */ {
/*     */   protected ScrollBarOrientation orientation;
/*     */   protected int min;
/*     */   protected int max;
/*     */   protected int value;
/*     */   
/*     */   public enum ScrollBarOrientation
/*     */   {
/*  27 */     Vertical,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  32 */     Horizontal;
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
/*  58 */   protected int buttonPos = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   protected int scrollButtonSize = 20;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int traySize;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int dragOffset;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   protected int mouseDownState = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   protected int mouseDownTime = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiScrollBar(bsu minecraft, int controlId, int xPos, int yPos, int controlWidth, int controlHeight, int minValue, int maxValue, ScrollBarOrientation orientation) {
/*  99 */     super(minecraft, controlId, xPos, yPos, controlWidth, controlHeight, "");
/*     */     
/* 101 */     this.orientation = orientation;
/*     */     
/* 103 */     this.value = this.min = minValue;
/* 104 */     this.max = Math.max(this.min, maxValue);
/*     */     
/* 106 */     this.traySize = getLargeDimension() - getSmallDimension() * 2 - this.scrollButtonSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getLargeDimension() {
/* 117 */     return (this.orientation == ScrollBarOrientation.Vertical) ? getHeight() : getWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getSmallDimension() {
/* 128 */     return (this.orientation == ScrollBarOrientation.Vertical) ? getWidth() : getHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValue() {
/* 138 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(int value) {
/* 148 */     this.value = value;
/* 149 */     updateValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMin(int value) {
/* 159 */     this.min = value;
/* 160 */     this.max = Math.max(this.min, this.max);
/* 161 */     updateValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMax(int value) {
/* 171 */     this.max = value;
/* 172 */     this.min = Math.min(this.max, this.min);
/* 173 */     updateValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSize(int controlWidth, int controlHeight) {
/* 184 */     a(controlWidth);
/* 185 */     getHeight(controlHeight);
/* 186 */     this.traySize = getLargeDimension() - getSmallDimension() * 2 - this.scrollButtonSize;
/* 187 */     updateValue();
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
/*     */   public void setSizeAndPosition(int left, int top, int controlWidth, int controlHeight) {
/* 200 */     setPosition(left, top);
/* 201 */     setSize(controlWidth, controlHeight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateValue() {
/* 210 */     if (this.value < this.min) this.value = this.min; 
/* 211 */     if (this.value > this.max) this.value = this.max;
/*     */     
/* 213 */     this.buttonPos = (int)((this.value - this.min) / (this.max - this.min) * this.traySize);
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
/*     */   protected void drawControl(bsu minecraft, int mouseX, int mouseY) {
/* 226 */     if (!isVisible())
/*     */       return; 
/* 228 */     float opacity = isEnabled() ? 1.0F : 0.3F;
/*     */     
/* 230 */     AbstractionLayer.bindTexture(ResourceLocations.MAIN);
/* 231 */     GL.glColor4f(opacity, opacity, opacity, opacity);
/* 232 */     setTexMapSize(128);
/*     */ 
/*     */     
/* 235 */     boolean mouseOverUpButton = (this.mouseDownState == 3 || mouseIsOverButton(mouseX, mouseY, 3));
/* 236 */     boolean mouseOverDownButton = (this.mouseDownState == 2 || mouseIsOverButton(mouseX, mouseY, 2));
/* 237 */     boolean mouseOverButton = (this.mouseDownState == 1 || mouseIsOverButton(mouseX, mouseY, 1));
/*     */ 
/*     */     
/* 240 */     int upButtonHoverState = 64 + a(mouseOverUpButton) * this.scrollButtonSize;
/* 241 */     int downButtonHoverState = 64 + a(mouseOverDownButton) * this.scrollButtonSize;
/* 242 */     int dragbuttonHoverState = 64 + a(mouseOverButton) * this.scrollButtonSize;
/*     */ 
/*     */     
/* 245 */     b(minecraft, mouseX, mouseY);
/*     */     
/* 247 */     if (this.orientation == ScrollBarOrientation.Vertical) {
/* 248 */       drawVerticalScrollBar(upButtonHoverState, downButtonHoverState, dragbuttonHoverState);
/*     */     } else {
/* 250 */       drawHorizontalScrollBar(upButtonHoverState, downButtonHoverState, dragbuttonHoverState);
/*     */     } 
/* 252 */     setTexMapSize(256);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawHorizontalScrollBar(int upButtonHoverState, int downButtonHoverState, int dragbuttonHoverState) {
/* 263 */     drawTexturedModalRectRot(getXPosition(), getYPosition() + getHeight() - 2, getXPosition() + getHeight(), getYPosition() + getHeight(), 0, upButtonHoverState, 2, upButtonHoverState + 20);
/* 264 */     drawTexturedModalRectRot(getXPosition(), getYPosition(), getXPosition() + getHeight(), getYPosition() + getHeight() - 2, 128 - getHeight() + 2, upButtonHoverState, 128, upButtonHoverState + 20);
/* 265 */     drawTexturedModalRectRot(getXPosition() + getWidth() - getHeight(), getYPosition() + getHeight() - 2, getXPosition() + getWidth(), getYPosition() + getHeight(), 0, downButtonHoverState, 2, downButtonHoverState + 20);
/* 266 */     drawTexturedModalRectRot(getXPosition() + getWidth() - getHeight(), getYPosition(), getXPosition() + getWidth(), getYPosition() + getHeight() - 2, 128 - getHeight() + 2, downButtonHoverState, 128, downButtonHoverState + 20);
/*     */ 
/*     */     
/* 269 */     drawTessellatedModalRectH(getXPosition() + getHeight(), getYPosition(), getXPosition() + getWidth() - getHeight(), getYPosition() + getHeight() - 1, 64, 46, 82, 64);
/*     */ 
/*     */     
/* 272 */     drawTexturedModalRectRot(getXPosition() + 1, getYPosition() + 1, getXPosition() + getHeight() - 4, getYPosition() + getHeight() - 2, 100, 48, 118, 64);
/* 273 */     drawTexturedModalRectRot(getXPosition() + getWidth() - getHeight() + 1, getYPosition() + 1, getXPosition() + getWidth() - 3, getYPosition() + getHeight() - 2, 82, 48, 100, 64);
/*     */ 
/*     */     
/* 276 */     drawTexturedModalRectRot(getXPosition() + getHeight() + this.buttonPos, getYPosition() + getHeight() - 3, 0, dragbuttonHoverState, 3, this.scrollButtonSize);
/* 277 */     drawTexturedModalRectRot(getXPosition() + getHeight() + this.buttonPos, getYPosition(), 128 - getHeight() + 3, dragbuttonHoverState, getHeight() - 3, this.scrollButtonSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawVerticalScrollBar(int upButtonHoverState, int downButtonHoverState, int dragbuttonHoverState) {
/* 288 */     drawTexturedModalRect(getXPosition(), getYPosition(), getXPosition() + 2, getYPosition() + getWidth(), 0, upButtonHoverState, 2, upButtonHoverState + 20);
/* 289 */     drawTexturedModalRect(getXPosition() + 2, getYPosition(), getXPosition() + getWidth(), getYPosition() + getWidth(), 128 - getWidth() + 2, upButtonHoverState, 128, upButtonHoverState + 20);
/* 290 */     drawTexturedModalRect(getXPosition(), getYPosition() + getHeight() - getWidth(), getXPosition() + 2, getYPosition() + getHeight(), 0, downButtonHoverState, 2, downButtonHoverState + 20);
/* 291 */     drawTexturedModalRect(getXPosition() + 2, getYPosition() + getHeight() - getWidth(), getXPosition() + getWidth(), getYPosition() + getHeight(), 128 - getWidth() + 2, downButtonHoverState, 128, downButtonHoverState + 20);
/*     */ 
/*     */     
/* 294 */     drawTessellatedModalRectV(getXPosition() + 1, getYPosition() + getWidth(), getXPosition() + getWidth() - 1, getYPosition() + getHeight() - getWidth(), 64, 48, 82, 64);
/*     */ 
/*     */     
/* 297 */     drawTexturedModalRect(getXPosition() + 1, getYPosition() + 1, getXPosition() + getWidth() - 2, getYPosition() + getWidth() - 4, 100, 48, 118, 64);
/* 298 */     drawTexturedModalRect(getXPosition() + 1, getYPosition() + getHeight() - getWidth() + 1, getXPosition() + getWidth() - 2, getYPosition() + getHeight() - 3, 82, 48, 100, 64);
/*     */ 
/*     */     
/* 301 */     drawTexturedModalRect(getXPosition(), getYPosition() + getWidth() + this.buttonPos, 0, dragbuttonHoverState, 3, this.scrollButtonSize, 0.0078125F);
/* 302 */     drawTexturedModalRect(getXPosition() + 3, getYPosition() + getWidth() + this.buttonPos, 128 - getWidth() + 3, dragbuttonHoverState, getWidth() - 3, this.scrollButtonSize, 0.0078125F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean mouseIsOverButton(int mouseX, int mouseY, int button) {
/* 307 */     mouseX -= getXPosition();
/* 308 */     mouseY -= getYPosition();
/*     */     
/* 310 */     if (mouseX < 0 || mouseY < 0 || mouseX > getWidth() || mouseY > getHeight()) return false;
/*     */     
/* 312 */     int largeDimension = getLargeDimension();
/* 313 */     int smallDimension = getSmallDimension();
/* 314 */     int buttonWidth = smallDimension;
/* 315 */     int buttonHeight = smallDimension;
/* 316 */     int buttonX = 0;
/* 317 */     int buttonY = 0;
/*     */     
/* 319 */     if (button == 2) {
/*     */       
/* 321 */       if (this.orientation == ScrollBarOrientation.Vertical) buttonY = largeDimension - smallDimension; 
/* 322 */       if (this.orientation == ScrollBarOrientation.Horizontal) buttonX = largeDimension - smallDimension;
/*     */     
/* 324 */     } else if (button == 1) {
/*     */       
/* 326 */       if (this.orientation == ScrollBarOrientation.Vertical) { buttonY = smallDimension + this.buttonPos; buttonHeight = this.scrollButtonSize; }
/* 327 */        if (this.orientation == ScrollBarOrientation.Horizontal) { buttonX = smallDimension + this.buttonPos; buttonWidth = this.scrollButtonSize; }
/*     */     
/*     */     } 
/* 330 */     return (mouseX >= buttonX && mouseY >= buttonY && mouseX < buttonX + buttonWidth && mouseY < buttonY + buttonHeight);
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
/*     */   protected void b(bsu minecraft, int mouseX, int mouseY) {
/* 343 */     if (!isVisible())
/*     */       return; 
/* 345 */     if (this.mouseDownState > 0) {
/*     */       
/* 347 */       int mouseDownTicks = this.updateCounter - this.mouseDownTime;
/*     */       
/* 349 */       if (this.mouseDownState == 1) {
/*     */         
/* 351 */         int mPos = (this.orientation == ScrollBarOrientation.Vertical) ? (mouseY - getYPosition()) : (mouseX - getXPosition());
/* 352 */         this.value = (int)((mPos - this.dragOffset - getSmallDimension()) / this.traySize * (this.max - this.min)) + this.min;
/*     */       }
/* 354 */       else if (this.mouseDownState == 2 && mouseDownTicks > 6) {
/*     */         
/* 356 */         this.value++;
/*     */       }
/* 358 */       else if (this.mouseDownState == 3 && mouseDownTicks > 6) {
/*     */         
/* 360 */         this.value--;
/*     */       } 
/*     */       
/* 363 */       updateValue();
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
/*     */   public void a(int mouseX, int mouseY) {
/* 376 */     this.mouseDownState = 0;
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
/*     */   public boolean c(bsu minecraft, int mouseX, int mouseY) {
/* 389 */     this.actionPerformed = false;
/* 390 */     boolean returnValue = false;
/*     */     
/* 392 */     if (super.c(minecraft, mouseX, mouseY)) {
/*     */ 
/*     */       
/* 395 */       mouseX -= getXPosition(); mouseY -= getYPosition();
/*     */       
/* 397 */       if (this.orientation == ScrollBarOrientation.Horizontal) {
/*     */         
/* 399 */         int mouseT = mouseY;
/* 400 */         mouseY = mouseX;
/* 401 */         mouseX = mouseT;
/*     */       } 
/*     */ 
/*     */       
/* 405 */       this.mouseDownTime = this.updateCounter;
/*     */       
/* 407 */       int largeDimension = getLargeDimension();
/* 408 */       int smallDimension = getSmallDimension();
/*     */       
/* 410 */       if (mouseY < smallDimension) {
/*     */         
/* 412 */         this.mouseDownState = 3;
/* 413 */         this.value--;
/* 414 */         this.actionPerformed = true;
/*     */ 
/*     */       
/*     */       }
/* 418 */       else if (mouseY > largeDimension - smallDimension) {
/*     */         
/* 420 */         this.mouseDownState = 2;
/* 421 */         this.value++;
/* 422 */         this.actionPerformed = true;
/*     */       }
/* 424 */       else if (mouseY > this.buttonPos + smallDimension && mouseY < this.buttonPos + smallDimension + this.scrollButtonSize) {
/*     */         
/* 426 */         this.mouseDownState = 1;
/* 427 */         this.dragOffset = mouseY - this.buttonPos - smallDimension;
/* 428 */         returnValue = true;
/*     */       }
/* 430 */       else if (mouseY < this.buttonPos + smallDimension) {
/*     */         
/* 432 */         this.value -= 5;
/* 433 */         this.actionPerformed = true;
/*     */       }
/* 435 */       else if (mouseY > this.buttonPos + smallDimension + this.scrollButtonSize) {
/*     */         
/* 437 */         this.value += 5;
/* 438 */         this.actionPerformed = true;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 443 */       updateValue();
/*     */     } 
/*     */     
/* 446 */     return (this.actionPerformed || returnValue);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\GuiScrollBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */