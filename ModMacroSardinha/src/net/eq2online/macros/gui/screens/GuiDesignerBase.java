/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bsu;
/*     */ import bxf;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.GuiControl;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.controls.GuiDropDownMenu;
/*     */ import net.eq2online.macros.gui.controls.GuiMiniToolbarButton;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiLayout;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxSetGridSize;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiLayoutPatch;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxConfirm;
/*     */ import net.eq2online.macros.gui.layout.LayoutPanel;
/*     */ import net.eq2online.macros.gui.layout.LayoutPanelButtons;
/*     */ import net.eq2online.macros.gui.layout.LayoutPanelEditMode;
/*     */ import net.eq2online.macros.gui.shared.GuiDialogBox;
/*     */ import net.eq2online.macros.interfaces.ILayoutPanel;
/*     */ import net.eq2online.macros.interfaces.ILayoutPanelContainer;
/*     */ import net.eq2online.macros.interfaces.ILayoutWidget;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GuiDesignerBase
/*     */   extends GuiScreenWithHeader
/*     */   implements ILayoutPanelContainer
/*     */ {
/*     */   protected Macros macros;
/*     */   protected String prompt;
/*     */   protected static int page;
/*  54 */   protected int buttonPanelPage = 0;
/*     */   
/*     */   protected GuiMiniToolbarButton btnGui;
/*     */   
/*     */   protected GuiMiniToolbarButton btnIcons;
/*     */   protected GuiMiniToolbarButton btnEditFile;
/*     */   protected GuiMiniToolbarButton btnOptions;
/*  61 */   protected ArrayList<GuiMiniToolbarButton> miniButtons = new ArrayList<GuiMiniToolbarButton>(); protected GuiMiniToolbarButton btnCopy; protected GuiMiniToolbarButton btnMove; protected GuiMiniToolbarButton btnDelete; protected GuiMiniToolbarButton btnEdit;
/*     */   protected GuiMiniToolbarButton btnButtons;
/*  63 */   protected GuiDropDownMenu buttonPanelMenu = new GuiDropDownMenu(false);
/*     */ 
/*     */ 
/*     */   
/*     */   protected bxf lastScreen;
/*     */ 
/*     */ 
/*     */   
/*     */   protected LayoutPanelButtons buttonsLayout;
/*     */ 
/*     */   
/*     */   protected DesignableGuiLayout layout;
/*     */ 
/*     */   
/*  77 */   protected ILayoutWidget capturedWidget = null;
/*     */   
/*     */   protected int promptBarStart;
/*     */   
/*     */   protected int promptBarEnd;
/*     */   protected boolean screenDrawToolButtons;
/*     */   protected boolean screenDrawActionButtons;
/*     */   
/*     */   public GuiDesignerBase(int pages, int initialPage) {
/*  86 */     super(pages, initialPage);
/*     */     
/*  88 */     this.screenDrawMenuButton = true;
/*  89 */     this.screenCentreBanner = false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  94 */     setTitles(page);
/*     */     
/*  96 */     this.macros = MacroModCore.getMacroManager();
/*  97 */     this.updateCounter = 0;
/*     */     
/*  99 */     this.buttonsLayout = new LayoutPanelButtons(bsu.z(), 11, this, this.macros, this.layout);
/* 100 */     this.buttonsLayout.dialogClosed();
/*     */     
/* 102 */     for (String controlType : DesignableGuiControl.getAvailableControlTypes())
/*     */     {
/* 104 */       this.buttonPanelMenu.addItem("new_" + controlType, LocalisationProvider.getLocalisedString("layout.editor.new." + controlType));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     this.buttonPanelMenu.addSeparator()
/* 112 */       .addItem("grid", LocalisationProvider.getLocalisedString("layout.editor.grid"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/* 121 */     List<GuiControl> controlList = getControlList();
/* 122 */     controlList.clear();
/*     */     
/* 124 */     Keyboard.enableRepeatEvents(true);
/*     */     
/* 126 */     this.miniButtons.clear();
/*     */     
/* 128 */     if (this.screenDrawToolButtons) {
/*     */       
/* 130 */       this.miniButtons.add(this.btnGui = new GuiMiniToolbarButton(this.j, 8, 104, 64));
/* 131 */       this.miniButtons.add(this.btnIcons = new GuiMiniToolbarButton(this.j, 0, 104, 32));
/* 132 */       this.miniButtons.add(this.btnEditFile = new GuiMiniToolbarButton(this.j, 1, 104, 16));
/* 133 */       this.miniButtons.add(this.btnOptions = new GuiMiniToolbarButton(this.j, 2, 104, 0));
/*     */     } 
/*     */     
/* 136 */     if (this.screenDrawActionButtons) {
/*     */       
/* 138 */       this.miniButtons.add(this.btnCopy = new GuiMiniToolbarButton(this.j, 3, 128, 0));
/* 139 */       this.miniButtons.add(this.btnMove = new GuiMiniToolbarButton(this.j, 4, 128, 16));
/*     */     } 
/*     */     
/* 142 */     this.miniButtons.add(this.btnDelete = new GuiMiniToolbarButton(this.j, 5, 128, 32));
/* 143 */     this.miniButtons.add(this.btnEdit = new GuiMiniToolbarButton(this.j, 6, 128, 48));
/* 144 */     this.miniButtons.add(this.btnButtons = new GuiMiniToolbarButton(this.j, 7, 128, 64));
/*     */     
/* 146 */     if (drawComplexGuiElements()) {
/*     */       
/* 148 */       this.buttonsLayout.connect(this);
/* 149 */       controlList.add(this.buttonsLayout);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void m() {
/* 159 */     this.macros.save();
/* 160 */     Keyboard.enableRepeatEvents(false);
/* 161 */     this.buttonsLayout.release();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/* 171 */     LayoutPanel.panelUpdateCounter = ++this.updateCounter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleWidgetClick(ILayoutPanel<? extends ILayoutWidget> source, int keyCode, boolean bindable) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ILayoutWidget getCapturedWidget() {
/* 184 */     return this.capturedWidget;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void captureWidgetAt(int mouseX, int mouseY) {
/* 190 */     this.capturedWidget = null;
/* 191 */     this.capturedWidget = this.buttonsLayout.getWidgetAt(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDialogClosed(GuiDialogBox dialog) {
/* 201 */     super.onDialogClosed(dialog);
/*     */     
/* 203 */     if (dialog instanceof GuiDialogBoxConfirm) {
/*     */       
/* 205 */       GuiDialogBoxConfirm<Integer> confirmDelete = (GuiDialogBoxConfirm<Integer>)dialog;
/* 206 */       if (dialog.dialogResult == GuiDialogBox.DialogResult.OK)
/*     */       {
/* 208 */         this.buttonsLayout.deleteWidget(((Integer)confirmDelete.getMetaData()).intValue());
/*     */       }
/*     */       
/* 211 */       this.buttonsLayout.dialogClosed();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float partialTick) {
/* 221 */     if (AbstractionLayer.getWorld() == null) {
/* 222 */       c();
/*     */     }
/* 224 */     int backColour = -1342177280;
/* 225 */     this.screenBannerColour = 4259648;
/* 226 */     this.screenBackgroundSpaceBottom = 16;
/*     */     
/* 228 */     GL.glDisableLighting();
/* 229 */     GL.glDisableDepthTest();
/*     */ 
/*     */     
/* 232 */     if (drawComplexGuiElements())
/*     */     {
/* 234 */       drawLayouts();
/*     */     }
/*     */     
/* 237 */     this.screenEnableGuiAnimation = MacroModSettings.enableGuiAnimation;
/* 238 */     this.screenDrawBackground = this.screenDrawHeader = drawComplexGuiElements();
/* 239 */     this.screenBanner = LocalisationProvider.getLocalisedString("macro.currentconfig", new Object[] { this.macros.getActiveConfigName() });
/*     */     
/* 241 */     int overMiniButton = 0;
/*     */     
/* 243 */     this.promptBarStart = 2;
/* 244 */     this.promptBarEnd = this.l - 2;
/*     */     
/* 246 */     if (drawComplexGuiElements()) {
/*     */       
/* 248 */       overMiniButton = drawMiniButtons(mouseX, mouseY, (page == this.buttonPanelPage));
/*     */ 
/*     */     
/*     */     }
/* 252 */     else if (this.btnOptions != null) {
/*     */       
/* 254 */       if (this.btnOptions.drawControlAt(this.j, mouseX, mouseY, this.promptBarEnd - 18, this.m - 14, -1118720, -1342177280)) overMiniButton = 8; 
/* 255 */       this.promptBarEnd -= 20;
/*     */     } 
/*     */ 
/*     */     
/* 259 */     a(this.promptBarStart, this.m - 14, this.promptBarEnd, this.m - 2, backColour);
/* 260 */     if (this.prompt != null) {
/* 261 */       c(this.q, this.prompt, this.promptBarStart + 2, this.m - 12, 15658496);
/*     */     }
/* 263 */     super.a(mouseX, mouseY, partialTick);
/*     */     
/* 265 */     if (drawComplexGuiElements()) {
/*     */       
/* 267 */       this.buttonPanelMenu.drawControlAt(this.promptBarStart - 20, this.m - 14, mouseX, mouseY);
/*     */       
/* 269 */       if (this.buttonPanelMenu.isDropDownVisible())
/*     */       {
/* 271 */         a(this.promptBarStart - 20, this.m - 15, this.promptBarStart - 2, this.m - 13, -16777216);
/*     */       }
/*     */       
/* 274 */       drawMiniButtonToolTips(mouseX, mouseY, overMiniButton);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean drawComplexGuiElements() {
/* 283 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postRender(int currentPage, int mouseX, int mouseY, float partialTick) {
/* 292 */     super.postRender(currentPage, mouseX, mouseY, partialTick);
/*     */     
/* 294 */     this.buttonsLayout.postRender(this.j, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawLayouts() {
/* 299 */     this.buttonsLayout.setSizeAndPosition(0, 22, this.l, this.m - 38);
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
/*     */   public int drawMiniButtons(int mouseX, int mouseY, boolean drawMenuButton) {
/* 311 */     int overMiniButton = 0;
/*     */     
/* 313 */     if (this.btnCopy != null) {
/*     */       
/* 315 */       if (this.btnCopy.drawControlAt(this.j, mouseX, mouseY, this.promptBarStart, this.m - 14, -16716288, -1342177280, (LayoutPanel.getGlobalMode() == LayoutPanelEditMode.Copy))) overMiniButton = 1; 
/* 316 */       this.promptBarStart += 20;
/*     */     } 
/*     */     
/* 319 */     if (this.btnMove != null) {
/*     */       
/* 321 */       if (this.btnMove.drawControlAt(this.j, mouseX, mouseY, this.promptBarStart, this.m - 14, -16716050, -1342177280, (LayoutPanel.getGlobalMode() == LayoutPanelEditMode.Move))) overMiniButton = 2; 
/* 322 */       this.promptBarStart += 20;
/*     */     } 
/*     */     
/* 325 */     if (this.btnDelete != null) {
/*     */       
/* 327 */       if (this.btnDelete.drawControlAt(this.j, mouseX, mouseY, this.promptBarStart, this.m - 14, -1179648, -1342177280, (LayoutPanel.getGlobalMode() == LayoutPanelEditMode.Delete))) overMiniButton = 3; 
/* 328 */       this.promptBarStart += 20;
/*     */     } 
/*     */     
/* 331 */     if (LayoutPanel.isEditable() || drawMenuButton) {
/*     */       
/* 333 */       LayoutPanelEditMode mode = LayoutPanel.getGlobalMode();
/* 334 */       if (this.btnEdit.drawControlAt(this.j, mouseX, mouseY, this.promptBarStart, this.m - 14, -1118720, -1342177280, (mode == LayoutPanelEditMode.EditAll || mode == LayoutPanelEditMode.EditButtonsOnly))) overMiniButton = 4;  this.promptBarStart += 20;
/*     */     } 
/*     */     
/* 337 */     if (this.btnButtons != null && drawMenuButton) {
/*     */       
/* 339 */       if (this.btnButtons.drawControlAt(this.j, mouseX, mouseY, this.promptBarStart, this.m - 14, -1118482, -1342177280, this.buttonPanelMenu.isDropDownVisible())) overMiniButton = 5; 
/* 340 */       this.promptBarStart += 20;
/*     */     } 
/*     */     
/* 343 */     if (this.btnOptions != null) {
/*     */       
/* 345 */       if (this.btnOptions.drawControlAt(this.j, mouseX, mouseY, this.promptBarEnd - 18, this.m - 14, -1118720, -1342177280)) overMiniButton = 8; 
/* 346 */       this.promptBarEnd -= 20;
/*     */     } 
/*     */     
/* 349 */     if (this.btnEditFile != null) {
/*     */       
/* 351 */       if (this.btnEditFile.drawControlAt(this.j, mouseX, mouseY, this.promptBarEnd - 18, this.m - 14, -1118720, -1342177280)) overMiniButton = 7; 
/* 352 */       this.promptBarEnd -= 20;
/*     */     } 
/*     */     
/* 355 */     if (this.btnIcons != null) {
/*     */       
/* 357 */       if (this.btnIcons.drawControlAt(this.j, mouseX, mouseY, this.promptBarEnd - 18, this.m - 14, -1118720, -1342177280)) overMiniButton = 6; 
/* 358 */       this.promptBarEnd -= 20;
/*     */     } 
/*     */     
/* 361 */     if (this.btnGui != null) {
/*     */       
/* 363 */       if (this.btnGui.drawControlAt(this.j, mouseX, mouseY, this.promptBarEnd - 18, this.m - 14, -1118720, -1342177280)) overMiniButton = 9; 
/* 364 */       this.promptBarEnd -= 20;
/*     */     } 
/*     */     
/* 367 */     return overMiniButton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawMiniButtonToolTips(int mouseX, int mouseY, int overMiniButton) {
/* 377 */     if (overMiniButton == 1) drawTooltip(LocalisationProvider.getLocalisedString("gui.copy"), mouseX, mouseY, -1118482, -1342177280); 
/* 378 */     if (overMiniButton == 2) drawTooltip(LocalisationProvider.getLocalisedString("gui.move"), mouseX, mouseY, -1118482, -1342177280); 
/* 379 */     if (overMiniButton == 3) drawTooltip(LocalisationProvider.getLocalisedString("gui.delete"), mouseX, mouseY, -1118482, -1342177280); 
/* 380 */     if (overMiniButton == 4) drawTooltip(LocalisationProvider.getLocalisedString("gui.edit"), mouseX, mouseY, -1118482, -1342177280); 
/* 381 */     if (overMiniButton == 5) drawTooltip(LocalisationProvider.getLocalisedString("gui.buttonopts"), mouseX, mouseY, -1118482, -1342177280);
/*     */     
/* 383 */     if (overMiniButton == 6) drawTooltip(LocalisationProvider.getLocalisedString("tooltip.icons"), mouseX, mouseY, -1118482, -1342177280); 
/* 384 */     if (overMiniButton == 7) drawTooltip(LocalisationProvider.getLocalisedString("tooltip.editfile"), mouseX, mouseY, -1118482, -1342177280); 
/* 385 */     if (overMiniButton == 8) drawTooltip(LocalisationProvider.getLocalisedString("tooltip.options"), mouseX, mouseY, -1118482, -1342177280); 
/* 386 */     if (overMiniButton == 9) drawTooltip(LocalisationProvider.getLocalisedString("tooltip.guiedit"), mouseX, mouseY, -1118482, -1342177280);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawPages(int currentPage, int mouseX, int mouseY, float partialTick) {
/* 395 */     page = currentPage;
/* 396 */     setTitles(currentPage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setTitles(int currentPage) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) {
/* 414 */     if (getPage() == this.buttonPanelPage && this.buttonsLayout.keyPressed(keyChar, keyCode))
/*     */       return; 
/* 416 */     if (keyCode == 1) {
/*     */       
/* 418 */       onCloseClick();
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(int mouseX, int mouseY, int button) throws IOException {
/* 429 */     boolean wasVisible = this.buttonPanelMenu.isDropDownVisible();
/*     */     
/* 431 */     String menuItem = this.buttonPanelMenu.mousePressed(mouseX, mouseY);
/* 432 */     if (menuItem != null) {
/*     */       
/* 434 */       if (menuItem.startsWith("new_"))
/*     */       {
/* 436 */         this.buttonsLayout.addControl(menuItem.substring(4));
/*     */       }
/*     */       
/* 439 */       if (menuItem.equals("grid") && this.layout != null)
/*     */       {
/* 441 */         AbstractionLayer.displayGuiScreen((bxf)new GuiDialogBoxSetGridSize(this, this.layout));
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 447 */     for (GuiMiniToolbarButton miniButton : this.miniButtons) {
/*     */       
/* 449 */       if (miniButton.c(this.j, mouseX, mouseY)) {
/*     */         
/* 451 */         onMiniButtonClicked(miniButton);
/*     */         
/* 453 */         if (miniButton.k == this.btnButtons.k && !wasVisible) {
/* 454 */           this.buttonPanelMenu.showDropDown();
/*     */         }
/*     */       } 
/*     */     } 
/* 458 */     super.a(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMiniButtonClicked(GuiMiniToolbarButton button) {
/* 468 */     if (this.btnGui != null && button.k == this.btnGui.k) {
/* 469 */       AbstractionLayer.displayGuiScreen((bxf)new GuiLayoutPatch((bxf)this));
/*     */     }
/* 471 */     if (this.btnIcons != null && button.k == this.btnIcons.k && AbstractionLayer.getWorld() != null) {
/* 472 */       AbstractionLayer.displayGuiScreen((bxf)new GuiEditThumbnails((bxf)this));
/*     */     }
/* 474 */     if (this.btnEditFile != null && button.k == this.btnEditFile.k) {
/* 475 */       AbstractionLayer.displayGuiScreen((bxf)new GuiEditTextFile(this, ScriptContext.MAIN));
/*     */     }
/* 477 */     if (this.btnOptions != null && button.k == this.btnOptions.k) {
/* 478 */       AbstractionLayer.displayGuiScreen((bxf)new GuiMacroConfig(this, false));
/*     */     }
/* 480 */     if (this.btnCopy != null && button.k == this.btnCopy.k) {
/* 481 */       LayoutPanel.setGlobalMode(LayoutPanelEditMode.Copy);
/*     */     }
/* 483 */     if (this.btnMove != null && button.k == this.btnMove.k) {
/* 484 */       LayoutPanel.setGlobalMode(LayoutPanelEditMode.Move);
/*     */     }
/* 486 */     if (this.btnDelete != null && button.k == this.btnDelete.k) {
/* 487 */       LayoutPanel.setGlobalMode(LayoutPanelEditMode.Delete);
/*     */     }
/* 489 */     if (this.btnEdit != null && button.k == this.btnEdit.k)
/*     */     {
/* 491 */       if (LayoutPanel.isEditable()) {
/* 492 */         LayoutPanel.setGlobalMode(LayoutPanelEditMode.EditAll);
/*     */       } else {
/* 494 */         LayoutPanel.setGlobalMode(LayoutPanelEditMode.EditButtonsOnly);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCloseClick() {
/* 504 */     this.macros.save();
/* 505 */     AbstractionLayer.displayGuiScreen(this.lastScreen);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\screens\GuiDesignerBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */