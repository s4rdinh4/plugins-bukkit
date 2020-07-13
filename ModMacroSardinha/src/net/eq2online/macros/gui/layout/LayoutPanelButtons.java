/*     */ package net.eq2online.macros.gui.layout;
/*     */ 
/*     */ import bsu;
/*     */ import bxf;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroType;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiLayout;
/*     */ import net.eq2online.macros.gui.designable.LayoutManager;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxConfirm;
/*     */ import net.eq2online.macros.gui.screens.GuiDesignerBase;
/*     */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*     */ import net.eq2online.macros.interfaces.ILayoutPanelContainer;
/*     */ import net.eq2online.macros.interfaces.ILayoutWidget;
/*     */ import net.eq2online.macros.interfaces.ISettingsProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LayoutPanelButtons
/*     */   extends LayoutPanel<DesignableGuiControl>
/*     */ {
/*     */   private DesignableGuiLayout layout;
/*     */   private Rectangle bounds;
/*     */   private boolean moved;
/*     */   private DesignableGuiControl rightClickedWidget;
/*     */   private GuiDesignerBase parentScreen;
/*  39 */   private int selectedHandle = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LayoutPanelButtons(bsu minecraft, int controlId, GuiDesignerBase parent, Macros macros, DesignableGuiLayout layout) {
/*  50 */     super(minecraft, controlId, MacroType.Control);
/*  51 */     connect((ILayoutPanelContainer)parent);
/*     */     
/*  53 */     this.parentScreen = parent;
/*  54 */     this.layout = layout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() {
/*  63 */     super.release();
/*     */     
/*  65 */     LayoutManager.saveSettings((ISettingsProvider)this.macros);
/*  66 */     if (this.layout != null) this.layout.beginPlacingControl(null);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawControl(bsu minecraft, int mouseX, int mouseY) {
/*  75 */     if (mode == LayoutPanelEditMode.EditAll || mode == LayoutPanelEditMode.EditButtonsOnly || mode == LayoutPanelEditMode.Reserve) {
/*     */       
/*  77 */       this.hoverWidget = null;
/*  78 */       this.hoverWidgetTime = panelUpdateCounter;
/*     */     }
/*     */     else {
/*     */       
/*  82 */       calcHover(mouseX, mouseY);
/*     */     } 
/*     */ 
/*     */     
/*  86 */     if (this.layout != null) {
/*     */       
/*  88 */       this.layout.drawWidgets(this.bounds, mouseX, mouseY, mode, this.selectedWidgetIndex);
/*  89 */       b(minecraft, mouseX, mouseY);
/*     */     } 
/*     */ 
/*     */     
/*  93 */     drawHover(mouseX, mouseY, AbstractionLayer.getFontRenderer());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSizeAndPosition(int left, int top, int controlWidth, int controlHeight) {
/* 107 */     super.setSizeAndPosition(left, top, controlWidth, controlHeight);
/* 108 */     this.bounds = new Rectangle(getXPosition(), getYPosition(), getWidth(), getHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point getWidgetPosition(ILayoutWidget widget) {
/* 118 */     Point position = widget.getWidgetPosition(this.layout);
/* 119 */     position.translate(-getXPosition(), -getYPosition());
/* 120 */     return position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ILayoutWidget getWidgetAt(int mouseX, int mouseY) {
/* 130 */     return getWidgetAtAdjustedPosition(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ILayoutWidget getWidgetAtAdjustedPosition(int mouseX, int mouseY) {
/* 137 */     if (mouseX < getXPosition() || mouseY < getYPosition() || mouseX > getXPosition() + getWidth() || mouseY > getYPosition() + getWidth()) {
/* 138 */       return null;
/*     */     }
/* 140 */     return (this.layout == null) ? null : (ILayoutWidget)this.layout.getControlAt(this.bounds, mouseX, mouseY, (mode == LayoutPanelEditMode.EditAll || mode == LayoutPanelEditMode.EditButtonsOnly) ? this.selectedWidgetIndex : -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ILayoutWidget createWidget(int widgetId) {
/* 147 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean keyPressed(char keyChar, int keyCode) {
/* 156 */     if (keyCode == 1) this.layout.beginPlacingControl(null);
/*     */     
/* 158 */     if (super.keyPressed(keyChar, keyCode)) return true;
/*     */     
/* 160 */     if ((mode == LayoutPanelEditMode.EditAll || mode == LayoutPanelEditMode.EditButtonsOnly) && keyCode == 211 && this.selectedWidgetIndex > -1) {
/*     */       
/* 162 */       inhibitModeSwitch = true;
/* 163 */       AbstractionLayer.displayGuiScreen((bxf)new GuiDialogBoxConfirm((GuiScreenEx)this.parentScreen, LocalisationProvider.getLocalisedString("control.delete.title"), LocalisationProvider.getLocalisedString("control.delete.line1"), this.layout.getControl(this.selectedWidgetIndex).getName() + " ?", Integer.valueOf(this.selectedWidgetIndex)));
/*     */     } 
/*     */     
/* 166 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteWidget(int widgetIndex) {
/* 174 */     if (this.layout != null)
/*     */     {
/* 176 */       this.layout.removeControl(widgetIndex);
/*     */     }
/*     */     
/* 179 */     widgetIndex = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dialogClosed() {
/* 184 */     inhibitModeSwitch = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean c(bsu minecraft, int mouseX, int mouseY) {
/* 193 */     DesignableGuiControl clickedWidget = (DesignableGuiControl)getWidgetAtAdjustedPosition(mouseX, mouseY);
/*     */     
/* 195 */     if (clickedWidget != null) {
/*     */       DesignableGuiControl control; Point gridPos;
/* 197 */       switch (mode) {
/*     */         
/*     */         case None:
/*     */         case EditAll:
/*     */         case EditButtonsOnly:
/* 202 */           control = clickedWidget;
/*     */           
/* 204 */           gridPos = this.layout.getDragPoint(this.bounds, control);
/*     */           
/* 206 */           clickedWidget.mousePressed(minecraft, mouseX - gridPos.x, mouseY - gridPos.y);
/* 207 */           this.selectedWidgetIndex = clickedWidget.getWidgetId();
/* 208 */           this.selectedWidget = clickedWidget;
/* 209 */           this.draggingWidget = clickedWidget;
/* 210 */           this.mouseDownAtX = mouseX;
/* 211 */           this.mouseDownAtY = mouseY;
/* 212 */           this.selectedHandle = this.layout.getControlHandleAt(this.bounds, mouseX, mouseY, control);
/* 213 */           this.moved = false;
/*     */           
/* 215 */           return true;
/*     */         
/*     */         case Copy:
/*     */         case Move:
/* 219 */           if (clickedWidget.getWidgetIsBound() && clickedWidget.getWidgetIsBindable())
/*     */           {
/* 221 */             this.selectedWidget = clickedWidget;
/*     */           }
/*     */           
/* 224 */           return true;
/*     */         
/*     */         case Delete:
/* 227 */           this.selectedWidget = clickedWidget;
/* 228 */           return true;
/*     */       } 
/*     */     
/* 231 */     } else if (this.layout != null && this.layout.isPlacingControl()) {
/*     */       
/* 233 */       Point clickedPos = this.layout.getRowAndColumnAt(this.bounds, mouseX, mouseY);
/* 234 */       if (clickedPos.x != -1 && clickedPos.y != -1) return true;
/*     */ 
/*     */       
/* 237 */       this.layout.beginPlacingControl(null);
/*     */     } 
/*     */     
/* 240 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean rightClicked(bsu minecraft, int mouseX, int mouseY, boolean state) {
/* 249 */     if (this.layout != null) this.layout.beginPlacingControl(null);
/*     */     
/* 251 */     DesignableGuiControl clickedWidget = (DesignableGuiControl)getWidgetAtAdjustedPosition(mouseX, mouseY);
/*     */     
/* 253 */     if (state) {
/*     */       
/* 255 */       this.rightClickedWidget = clickedWidget;
/* 256 */       return false;
/*     */     } 
/* 258 */     if (this.rightClickedWidget != null && this.rightClickedWidget == clickedWidget)
/*     */     {
/* 260 */       AbstractionLayer.displayGuiScreen((bxf)this.rightClickedWidget.getPropertiesDialog((GuiScreenEx)this.parentScreen));
/*     */     }
/*     */     
/* 263 */     this.rightClickedWidget = null;
/* 264 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleWidgetClick(int widgetId, boolean bindable) {
/* 273 */     if (!this.moved && this.layout != null) {
/*     */       
/* 275 */       this.layout.beginPlacingControl(null);
/*     */       
/* 277 */       DesignableGuiControl control = DesignableGuiControl.getControl(widgetId);
/*     */       
/* 279 */       if (control != null)
/*     */       {
/* 281 */         super.handleWidgetClick(widgetId, control.getWidgetIsBindable());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleWidgetDrag(bsu minecraft, int mouseX, int mouseY) {
/* 292 */     if (this.layout != null) {
/*     */       
/* 294 */       this.layout.beginPlacingControl(null);
/*     */       
/* 296 */       DesignableGuiControl control = this.draggingWidget;
/*     */       
/* 298 */       if (this.selectedHandle < 0) {
/*     */         
/* 300 */         this.moved |= this.layout.setWidgetPosition(this.bounds, mouseX, mouseY, control);
/*     */       }
/*     */       else {
/*     */         
/* 304 */         this.moved = true;
/*     */         
/* 306 */         this.layout.setControlSpan(this.bounds, mouseX, mouseY, control, this.selectedHandle);
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
/*     */   protected void handleMouseRelease(int mouseX, int mouseY, DesignableGuiControl selectedWidget, ILayoutWidget landedWidget) {
/* 318 */     if (this.layout != null) {
/*     */       
/* 320 */       DesignableGuiControl control = this.layout.placeControl(this.bounds, mouseX, mouseY);
/*     */       
/* 322 */       if (control != null) {
/* 323 */         AbstractionLayer.displayGuiScreen((bxf)control.getPropertiesDialog((GuiScreenEx)this.parentScreen));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleMacroDelete(int widgetId) {
/* 333 */     if (this.layout != null && this.selectedWidget != null && !this.selectedWidget.getWidgetIsBound()) {
/*     */       
/* 335 */       inhibitModeSwitch = true;
/* 336 */       AbstractionLayer.displayGuiScreen((bxf)new GuiDialogBoxConfirm((GuiScreenEx)this.parentScreen, LocalisationProvider.getLocalisedString("control.delete.title"), LocalisationProvider.getLocalisedString("control.delete.line1"), this.layout.getControl(widgetId).getName() + " ?", Integer.valueOf(widgetId)));
/* 337 */       return true;
/*     */     } 
/*     */     
/* 340 */     return super.handleMacroDelete(widgetId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addControl(String controlType) {
/* 350 */     if (this.layout != null)
/*     */     {
/* 352 */       this.layout.beginPlacingControl(controlType);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public DesignableGuiLayout getLayout() {
/* 358 */     return this.layout;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLayout(DesignableGuiLayout layout) {
/* 363 */     this.layout = layout;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\layout\LayoutPanelButtons.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */