/*     */ package net.eq2online.macros.gui.layout;
/*     */ 
/*     */ import bsu;
/*     */ import bty;
/*     */ import com.mumfrey.liteloader.gl.GLClippingPlanes;
/*     */ import java.awt.Point;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroPlaybackType;
/*     */ import net.eq2online.macros.core.MacroTemplate;
/*     */ import net.eq2online.macros.core.MacroType;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.shared.GuiControlEx;
/*     */ import net.eq2online.macros.interfaces.ILayoutPanel;
/*     */ import net.eq2online.macros.interfaces.ILayoutPanelContainer;
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
/*     */ public abstract class LayoutPanel<TWidget extends ILayoutWidget>
/*     */   extends GuiControlEx
/*     */   implements ILayoutPanel<TWidget>
/*     */ {
/*     */   public static int panelUpdateCounter;
/*     */   private static boolean editable = false;
/*  36 */   protected static LayoutPanelEditMode mode = LayoutPanelEditMode.None;
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean inhibitModeSwitch;
/*     */ 
/*     */   
/*  43 */   protected ILayoutWidget[] widgets = new ILayoutWidget[10000];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TWidget selectedWidget;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TWidget draggingWidget;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TWidget hoverWidget;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int mouseDownAtX;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int mouseDownAtY;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int hoverWidgetTime;
/*     */ 
/*     */ 
/*     */   
/*     */   protected String hoverModifier;
/*     */ 
/*     */ 
/*     */   
/*     */   protected MacroTemplate hoverTemplate;
/*     */ 
/*     */ 
/*     */   
/*  83 */   protected int selectedWidgetIndex = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean centreAlign = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   protected int widgetWidth = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   protected int widgetHeight = 14;
/*     */ 
/*     */   
/*     */   protected ILayoutPanelContainer parent;
/*     */ 
/*     */   
/*     */   protected Macros macros;
/*     */ 
/*     */   
/*     */   protected final MacroType macroType;
/*     */ 
/*     */   
/*     */   protected LayoutPanel(bsu minecraft, int controlId, MacroType type) {
/* 111 */     super(minecraft, controlId, 0, 0, 0, 0, "");
/*     */     
/* 113 */     this.macroType = type;
/* 114 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isEditable() {
/* 119 */     return editable;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setEditable(boolean editable) {
/* 124 */     LayoutPanel.editable = editable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LayoutPanelEditMode getGlobalMode() {
/* 133 */     return mode;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setGlobalMode(LayoutPanelEditMode newMode) {
/* 138 */     if (newMode == LayoutPanelEditMode.Reserve || newMode == LayoutPanelEditMode.None)
/*     */     {
/* 140 */       mode = newMode;
/*     */     }
/* 142 */     if (newMode == LayoutPanelEditMode.Copy) {
/*     */       
/* 144 */       mode = (mode == LayoutPanelEditMode.Copy) ? LayoutPanelEditMode.None : LayoutPanelEditMode.Copy;
/*     */     }
/* 146 */     else if (newMode == LayoutPanelEditMode.Move) {
/*     */       
/* 148 */       mode = (mode == LayoutPanelEditMode.Move) ? LayoutPanelEditMode.None : LayoutPanelEditMode.Move;
/*     */     }
/* 150 */     else if (newMode == LayoutPanelEditMode.Delete) {
/*     */       
/* 152 */       mode = (mode == LayoutPanelEditMode.Delete) ? LayoutPanelEditMode.None : LayoutPanelEditMode.Delete;
/*     */     }
/* 154 */     else if ((isEditable() && newMode == LayoutPanelEditMode.EditAll) || newMode == LayoutPanelEditMode.EditButtonsOnly) {
/*     */       
/* 156 */       mode = (mode == newMode) ? LayoutPanelEditMode.None : newMode;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void init();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect(ILayoutPanelContainer parent) {
/* 171 */     if (!inhibitModeSwitch) setGlobalMode(LayoutPanelEditMode.None);
/*     */     
/* 173 */     this.macros = MacroModCore.getMacroManager();
/* 174 */     this.parent = parent;
/* 175 */     this.selectedWidgetIndex = -1;
/* 176 */     this.selectedWidget = null;
/* 177 */     this.draggingWidget = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() {
/* 186 */     if (!inhibitModeSwitch) setGlobalMode(LayoutPanelEditMode.None); 
/* 187 */     this.parent = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSizeAndPosition(int left, int top, int controlWidth, int controlHeight) {
/* 196 */     setXPosition(left);
/* 197 */     setYPosition(top);
/* 198 */     a(controlWidth);
/* 199 */     getHeight(controlHeight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDragging() {
/* 208 */     return (this.selectedWidget != null && (mode == LayoutPanelEditMode.Copy || mode == LayoutPanelEditMode.Move));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point getWidgetPosition(ILayoutWidget widget) {
/* 218 */     return widget.getWidgetPosition(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract ILayoutWidget getWidgetAt(int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ILayoutWidget getWidgetAtAdjustedPosition(int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ILayoutWidget getWidget(int widgetId, boolean create) {
/* 244 */     if (!this.macroType.supportsId(widgetId)) return null;
/*     */     
/* 246 */     if (this.widgets[widgetId] == null && create) {
/* 247 */       this.widgets[widgetId] = createWidget(widgetId);
/*     */     }
/* 249 */     return this.widgets[widgetId];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ILayoutWidget createWidget(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean keyPressed(char keyChar, int keyCode) {
/* 266 */     if (keyCode == 1)
/*     */     {
/* 268 */       if (mode != LayoutPanelEditMode.None) {
/*     */         
/* 270 */         mode = LayoutPanelEditMode.None;
/* 271 */         return true;
/*     */       } 
/*     */     }
/*     */     
/* 275 */     return false;
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
/* 288 */     if (this.draggingWidget != null && mouseHasMoved(mouseX, mouseY))
/*     */     {
/* 290 */       handleWidgetDrag(minecraft, mouseX, mouseY);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean mouseHasMoved(int mouseX, int mouseY) {
/* 296 */     if (this.mouseDownAtX != mouseX || this.mouseDownAtY != mouseY) {
/*     */       
/* 298 */       this.mouseDownAtX = mouseX;
/* 299 */       this.mouseDownAtY = mouseY;
/* 300 */       return true;
/*     */     } 
/*     */     
/* 303 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleWidgetDrag(bsu minecraft, int mouseX, int mouseY) {
/* 313 */     this.draggingWidget.mouseDragged(minecraft, mouseX - getXPosition(), mouseY - getYPosition());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY) {
/* 322 */     if (this.parent != null) this.parent.captureWidgetAt(mouseX, mouseY); 
/* 323 */     handleMouseRelease(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleMouseRelease(int mouseX, int mouseY) {
/* 329 */     if (this.draggingWidget != null) {
/*     */       
/* 331 */       this.selectedWidgetIndex = this.draggingWidget.getWidgetId();
/* 332 */       this.draggingWidget.mouseReleased(mouseX - getXPosition(), mouseY - getYPosition());
/* 333 */       this.draggingWidget = null;
/*     */     } 
/*     */     
/* 336 */     ILayoutWidget landedWidget = (this.parent != null) ? this.parent.getCapturedWidget() : getWidgetAtAdjustedPosition(mouseX, mouseY);
/*     */     
/* 338 */     if (this.selectedWidget != null) {
/*     */       
/* 340 */       int widgetId = this.selectedWidget.getWidgetId();
/*     */       
/* 342 */       switch (mode) {
/*     */ 
/*     */         
/*     */         case OneShot:
/* 346 */           if (landedWidget == this.selectedWidget)
/*     */           {
/* 348 */             handleWidgetClick(widgetId, true);
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case KeyState:
/* 354 */           if (landedWidget != null) {
/*     */             
/* 356 */             if (landedWidget == this.selectedWidget) {
/*     */               
/* 358 */               handleWidgetClick(widgetId, true);
/*     */               
/*     */               break;
/*     */             } 
/* 362 */             handleMacroCopy(landedWidget, widgetId);
/*     */           } 
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case Conditional:
/* 369 */           if (landedWidget != null) {
/*     */             
/* 371 */             if (landedWidget == this.selectedWidget) {
/*     */               
/* 373 */               handleWidgetClick(widgetId, true);
/*     */               
/*     */               break;
/*     */             } 
/* 377 */             handleMacroMove(landedWidget, widgetId);
/*     */           } 
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case null:
/* 384 */           if (landedWidget == this.selectedWidget)
/*     */           {
/* 386 */             handleMacroDelete(widgetId);
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case null:
/* 392 */           if (landedWidget == this.selectedWidget)
/*     */           {
/* 394 */             landedWidget.toggleReservedState();
/*     */           }
/*     */           break;
/*     */       } 
/*     */       
/* 399 */       handleMouseRelease(mouseX, mouseY, this.selectedWidget, landedWidget);
/*     */       
/* 401 */       this.selectedWidget = null;
/*     */     }
/*     */     else {
/*     */       
/* 405 */       handleMouseRelease(mouseX, mouseY, this.selectedWidget, landedWidget);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleMacroCopy(ILayoutWidget landedWidget, int widgetId) {
/* 415 */     if (landedWidget.getWidgetIsBindable()) {
/*     */       
/* 417 */       this.macros.copyMacroTemplate(widgetId, landedWidget.getWidgetId());
/* 418 */       this.macros.save();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleMacroMove(ILayoutWidget landedWidget, int widgetId) {
/* 428 */     if (landedWidget.getWidgetIsBindable()) {
/*     */       
/* 430 */       this.macros.moveMacroTemplate(widgetId, landedWidget.getWidgetId());
/* 431 */       this.macros.save();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleMacroDelete(int widgetId) {
/* 440 */     this.macros.deleteMacroTemplate(widgetId);
/* 441 */     this.macros.save();
/*     */     
/* 443 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleMouseRelease(int mouseX, int mouseY, TWidget selectedWidget, ILayoutWidget landedWidget) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleWidgetClick(int widgetId, boolean bindable) {
/* 462 */     if (this.parent != null)
/*     */     {
/* 464 */       this.parent.handleWidgetClick(this, widgetId, bindable);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean c(bsu minecraft, int mouseX, int mouseY) {
/* 475 */     ILayoutWidget iLayoutWidget = getWidgetAtAdjustedPosition(mouseX, mouseY);
/*     */     
/* 477 */     if (iLayoutWidget != null)
/*     */     {
/* 479 */       switch (mode) {
/*     */         
/*     */         case KeyState:
/*     */         case Conditional:
/*     */         case null:
/* 484 */           if (!iLayoutWidget.getWidgetIsBound() || !iLayoutWidget.getWidgetIsBindable()) {
/*     */             break;
/*     */           }
/*     */         case OneShot:
/*     */         case null:
/* 489 */           this.selectedWidget = (TWidget)iLayoutWidget;
/* 490 */           return true;
/*     */         
/*     */         case null:
/*     */         case null:
/* 494 */           if (iLayoutWidget.mousePressed(minecraft, mouseX - getXPosition(), mouseY - getYPosition())) {
/*     */             
/* 496 */             this.selectedWidgetIndex = iLayoutWidget.getWidgetId();
/* 497 */             this.draggingWidget = (TWidget)iLayoutWidget;
/* 498 */             this.mouseDownAtX = mouseX;
/* 499 */             this.mouseDownAtY = mouseY;
/* 500 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     
/*     */     }
/* 506 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postRender(bsu minecraft, int mouseX, int mouseY) {
/* 517 */     if (this.selectedWidget != null && (mode == LayoutPanelEditMode.Copy || mode == LayoutPanelEditMode.Move)) {
/*     */       
/* 519 */       bty fontRenderer = AbstractionLayer.getFontRenderer();
/*     */       
/* 521 */       Point sourcePos = getWidgetPosition((ILayoutWidget)this.selectedWidget);
/*     */       
/* 523 */       if (!this.centreAlign) {
/* 524 */         sourcePos.x += this.selectedWidget.getWidgetWidth(this) / 2;
/*     */       }
/*     */       
/* 527 */       int colour = (mode == LayoutPanelEditMode.Copy) ? -16711936 : -16711681;
/*     */ 
/*     */       
/* 530 */       String label = (mode == LayoutPanelEditMode.Copy) ? LocalisationProvider.getLocalisedString("gui.copy") : LocalisationProvider.getLocalisedString("gui.move");
/* 531 */       label = label + " " + this.selectedWidget.getWidgetDisplayText();
/*     */       
/* 533 */       drawArrow(sourcePos.x + getXPosition(), sourcePos.y + getYPosition() + 7, mouseX, mouseY, 600, 2, 6, colour);
/* 534 */       a(fontRenderer, label, mouseX, mouseY - 16, colour);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawWidgets(int mouseX, int mouseY) {
/* 545 */     GLClippingPlanes.glEnableClipping(getXPosition(), getXPosition() + getWidth(), -1, -1);
/*     */ 
/*     */     
/* 548 */     for (int i = this.macroType.getMinId(); i <= this.macroType.getAbsoluteMaxId(); i++) {
/*     */       
/* 550 */       if (this.widgets[i] != null)
/*     */       {
/* 552 */         this.widgets[i].drawWidget(this, null, mouseX - getXPosition(), mouseY - getYPosition(), mode, (this.widgets[i] == this.selectedWidget || (mode == LayoutPanelEditMode.EditAll && i == this.selectedWidgetIndex)), false);
/*     */       }
/*     */     } 
/*     */     
/* 556 */     GLClippingPlanes.glDisableClipping();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void calcHover(int mouseX, int mouseY) {
/* 566 */     ILayoutWidget iLayoutWidget = getWidgetAtAdjustedPosition(mouseX, mouseY);
/*     */     
/* 568 */     if (iLayoutWidget == null) {
/*     */       
/* 570 */       this.hoverWidget = null;
/* 571 */       this.hoverWidgetTime = panelUpdateCounter;
/*     */     }
/* 573 */     else if (iLayoutWidget != this.hoverWidget) {
/*     */       
/* 575 */       this.hoverWidget = (TWidget)iLayoutWidget;
/* 576 */       this.hoverTemplate = this.macros.getMacroTemplate(this.hoverWidget.getWidgetId(), false);
/* 577 */       this.hoverModifier = this.hoverTemplate.getModifiers();
/* 578 */       this.hoverWidgetTime = panelUpdateCounter;
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
/*     */   protected void drawHover(int mouseX, int mouseY, bty fontRenderer) {
/* 590 */     if (this.hoverWidget != null && panelUpdateCounter - this.hoverWidgetTime > 6) {
/*     */       
/* 592 */       boolean overlaid = this.macros.isKeyOverlaid(this.hoverWidget.getWidgetId());
/* 593 */       boolean denied = this.hoverWidget.getWidgetIsDenied();
/*     */       
/* 595 */       if (overlaid) {
/*     */         
/* 597 */         drawOverlayHoverTip(fontRenderer, mouseX, mouseY, denied);
/*     */       }
/*     */       else {
/*     */         
/* 601 */         drawHoverTip(fontRenderer, this.hoverTemplate, mouseX, mouseY, -1342177280, true, denied);
/*     */       } 
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
/*     */   protected void drawOverlayHoverTip(bty fontRenderer, int mouseX, int mouseY, boolean denied) {
/* 614 */     String overlayText = LocalisationProvider.getLocalisedString("macro.hover.overlay", new Object[] { this.macros.getOverlayConfigName("§c") });
/* 615 */     int textWidth = fontRenderer.a(overlayText) + 10;
/* 616 */     int textLeft = Math.min(mouseX - 10, getWidth() - textWidth);
/*     */     
/* 618 */     a(textLeft, mouseY - 16, mouseX + textWidth - 10, mouseY, -1342177212);
/* 619 */     c(fontRenderer, overlayText, textLeft + 3, mouseY - 12, -15658497);
/*     */     
/* 621 */     MacroTemplate overlayTemplate = this.macros.getMacroTemplateWithOverlay(this.hoverWidget.getWidgetId());
/*     */     
/* 623 */     drawHoverTip(fontRenderer, this.hoverTemplate, mouseX, mouseY - 18, -1342177280, true, denied);
/* 624 */     drawHoverTip(fontRenderer, overlayTemplate, mouseX, mouseY, -1342177212, false, denied);
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
/*     */   protected void drawHoverTip(bty fontRenderer, MacroTemplate template, int mouseX, int mouseY, int backColour, boolean direction, boolean denied) {
/* 639 */     int textWidth = Math.min(200, getHoverWidth(fontRenderer, template) + 10);
/* 640 */     int textHeight = this.hoverWidget.getWidgetIsDenied() ? 16 : 6;
/* 641 */     int textLeft = Math.max(getXPosition(), Math.min(mouseX - 10, getXPosition() + getWidth() - textWidth)) + 3;
/* 642 */     int yPos = Math.min(getHeight() - textHeight - 2, mouseY + 4);
/*     */     
/* 644 */     if (this.hoverWidget.getWidgetIsBound()) {
/*     */       
/* 646 */       textHeight += (template.getPlaybackType() == MacroPlaybackType.OneShot) ? 10 : 30;
/* 647 */       textHeight += (this.hoverModifier.length() > 0) ? 12 : 0;
/* 648 */       if (direction) yPos -= textHeight;
/*     */       
/* 650 */       a(textLeft - 3, yPos - 4, mouseX + textWidth - 10, yPos + textHeight - 4, backColour);
/*     */       
/* 652 */       switch (template.getPlaybackType()) {
/*     */         
/*     */         case OneShot:
/* 655 */           drawStringWithEllipsis(fontRenderer, template.getKeyDownMacroHoverText(), textLeft, yPos, textWidth - 4, -1711276033); yPos += 10;
/*     */           break;
/*     */         
/*     */         case KeyState:
/* 659 */           drawStringWithEllipsis(fontRenderer, template.getKeyDownMacroHoverText(), textLeft, yPos, textWidth - 4, -1711276033); yPos += 10;
/* 660 */           drawStringWithEllipsis(fontRenderer, template.getKeyHeldMacroHoverText(), textLeft, yPos, textWidth - 4, -1711298048); yPos += 10;
/* 661 */           drawStringWithEllipsis(fontRenderer, template.getKeyUpMacroHoverText(), textLeft, yPos, textWidth - 4, -1711306752); yPos += 10;
/*     */           break;
/*     */         
/*     */         case Conditional:
/* 665 */           drawStringWithEllipsis(fontRenderer, template.getConditionHovertext(), textLeft, yPos, textWidth - 4, -1711276033); yPos += 10;
/* 666 */           drawStringWithEllipsis(fontRenderer, template.getKeyDownMacroHoverText(), textLeft, yPos, textWidth - 4, -1721303194); yPos += 10;
/* 667 */           drawStringWithEllipsis(fontRenderer, template.getKeyUpMacroHoverText(), textLeft, yPos, textWidth - 4, -1711315456); yPos += 10;
/*     */           break;
/*     */       } 
/*     */       
/* 671 */       if (this.hoverModifier.length() > 0)
/*     */       {
/* 673 */         yPos += 2;
/* 674 */         c(fontRenderer, this.hoverModifier, textLeft, yPos, -1727987968);
/* 675 */         yPos += 12;
/*     */       }
/*     */     
/* 678 */     } else if (denied) {
/*     */       
/* 680 */       a(textLeft - 3, yPos - 4, mouseX + textWidth - 10, yPos + textHeight - 4, backColour);
/*     */     } 
/*     */     
/* 683 */     if (denied)
/*     */     {
/* 685 */       c(fontRenderer, this.hoverWidget.getWidgetDeniedText(), textLeft, yPos, -1711341568);
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
/*     */   protected int getHoverWidth(bty fontRenderer, MacroTemplate template) {
/* 698 */     int deniedWidth = this.hoverWidget.getWidgetIsDenied() ? fontRenderer.a(this.hoverWidget.getWidgetDeniedText()) : 0;
/*     */     
/* 700 */     if (template == null) return deniedWidth;
/*     */     
/* 702 */     if (template.getPlaybackType() == MacroPlaybackType.OneShot)
/*     */     {
/* 704 */       return Math.max(Math.max(fontRenderer
/* 705 */             .a(template.getKeyDownMacro()), fontRenderer
/* 706 */             .a(this.hoverModifier)), deniedWidth);
/*     */     }
/*     */ 
/*     */     
/* 710 */     if (template.getPlaybackType() == MacroPlaybackType.KeyState)
/*     */     {
/* 712 */       return Math.max(Math.max(Math.max(Math.max(fontRenderer
/* 713 */                 .a(template.getKeyDownMacroHoverText()), fontRenderer
/* 714 */                 .a(template.getKeyHeldMacroHoverText())), fontRenderer
/* 715 */               .a(template.getKeyUpMacroHoverText())), fontRenderer
/* 716 */             .a(this.hoverModifier)), deniedWidth);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 722 */     return Math.max(Math.max(Math.max(Math.max(fontRenderer
/* 723 */               .a(template.getKeyDownMacroHoverText()), fontRenderer
/* 724 */               .a(template.getKeyUpMacroHoverText())), fontRenderer
/* 725 */             .a(template.getConditionHovertext())), fontRenderer
/* 726 */           .a(this.hoverModifier)), deniedWidth);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\layout\LayoutPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */