/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bug;
/*     */ import bxf;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import cxy;
/*     */ import cye;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.designable.LayoutManager;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiLayoutPatch;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxAddConfig;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxConfirm;
/*     */ import net.eq2online.macros.gui.layout.LayoutPanel;
/*     */ import net.eq2online.macros.gui.layout.LayoutPanelEditMode;
/*     */ import net.eq2online.macros.gui.layout.LayoutPanelEvents;
/*     */ import net.eq2online.macros.gui.layout.LayoutPanelKeys;
/*     */ import net.eq2online.macros.gui.shared.GuiDialogBox;
/*     */ import net.eq2online.macros.input.InputHandler;
/*     */ import net.eq2online.macros.interfaces.ILayoutPanel;
/*     */ import net.eq2online.macros.interfaces.ILayoutWidget;
/*     */ import net.eq2online.macros.interfaces.IListObject;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
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
/*     */ public class GuiMacroBind
/*     */   extends GuiDesignerBase
/*     */ {
/*  44 */   protected int mouseOverButtonTime = 0;
/*     */   
/*  46 */   protected int overrideKeyDownTime = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   protected LayoutPanelEvents eventLayout;
/*     */ 
/*     */   
/*     */   protected LayoutPanelKeys keyboardLayout;
/*     */ 
/*     */   
/*     */   protected GuiListBox configs;
/*     */ 
/*     */   
/*     */   protected boolean waitingFeedback;
/*     */ 
/*     */   
/*     */   public static GuiEditTextFile minimisedEditor;
/*     */ 
/*     */   
/*     */   private static String customScreenName;
/*     */ 
/*     */   
/*     */   private static Class<? extends bxf> customScreenClass;
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiMacroBind(boolean resetPage) {
/*  73 */     this(resetPage, (bxf)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiMacroBind(boolean resetPage, bxf lastScreen) {
/*  78 */     super(3, (resetPage && !MacroModSettings.rememberBindPage) ? 0 : page);
/*     */     
/*  80 */     if (resetPage && !MacroModSettings.rememberBindPage) {
/*  81 */       page = 0;
/*     */     }
/*  83 */     this.screenDrawMenuButton = true;
/*  84 */     this.screenCentreBanner = false;
/*  85 */     this.screenDrawToolButtons = true;
/*  86 */     this.screenDrawActionButtons = true;
/*  87 */     this.buttonPanelPage = 2;
/*     */     
/*  89 */     init();
/*     */     
/*  91 */     this.lastScreen = lastScreen;
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiMacroBind(int initialPage, bxf lastScreen) {
/*  96 */     super(3, initialPage);
/*     */     
/*  98 */     this.screenDrawMenuButton = true;
/*  99 */     this.screenCentreBanner = false;
/* 100 */     this.screenDrawToolButtons = true;
/* 101 */     this.screenDrawActionButtons = true;
/* 102 */     this.buttonPanelPage = 2;
/*     */     
/* 104 */     init();
/*     */     
/* 106 */     this.lastScreen = lastScreen;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerCustomScreen(String screenName, Class<? extends bxf> screenClass) {
/* 111 */     customScreenName = screenName;
/* 112 */     customScreenClass = screenClass;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init() {
/* 118 */     this.layout = LayoutManager.getBoundLayout("playback", false);
/*     */     
/* 120 */     super.init();
/*     */     
/* 122 */     this.keyboardLayout = this.macros.getKeyboardLayout();
/* 123 */     this.eventLayout = this.macros.getEventLayout();
/*     */     
/* 125 */     this.screenMenu.addItem("keys", "§o" + LocalisationProvider.getLocalisedString("macro.trigger.type.keys") + "§r")
/* 126 */       .addItem("events", "§o" + LocalisationProvider.getLocalisedString("macro.trigger.type.events") + "§r")
/* 127 */       .addItem("buttons", "§o" + LocalisationProvider.getLocalisedString("macro.trigger.type.control") + "§r")
/* 128 */       .addSeparator()
/* 129 */       .addItem("options", LocalisationProvider.getLocalisedString("tooltip.options"), 26, 0)
/* 130 */       .addItem("perms", LocalisationProvider.getLocalisedString("tooltip.perms"), 32, 20)
/* 131 */       .addSeparator()
/* 132 */       .addItem("editgui", LocalisationProvider.getLocalisedString("tooltip.guiedit"), 26, 16);
/*     */     
/* 134 */     if (customScreenName != null) {
/*     */       
/* 136 */       this.screenMenu.addSeparator();
/* 137 */       this.screenMenu.addItem("custom", customScreenName);
/*     */     } 
/*     */     
/* 140 */     this.macros.detectThirdPartyModKeys();
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
/*     */   public void captureWidgetAt(int mouseX, int mouseY) {
/* 153 */     this.capturedWidget = null;
/*     */     
/* 155 */     this.capturedWidget = this.keyboardLayout.getWidgetAt(mouseX, mouseY);
/* 156 */     if (this.capturedWidget != null)
/*     */       return; 
/* 158 */     this.capturedWidget = this.eventLayout.getWidgetAt(mouseX, mouseY);
/* 159 */     if (this.capturedWidget != null)
/*     */       return; 
/* 161 */     this.capturedWidget = this.buttonsLayout.getWidgetAt(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/* 171 */     this.layout = LayoutManager.getBoundLayout("playback", false);
/* 172 */     this.buttonsLayout.setLayout(this.layout);
/*     */     
/* 174 */     super.b();
/*     */ 
/*     */     
/* 177 */     if (!MacroModSettings.simpleGui) {
/*     */       
/* 179 */       this.keyboardLayout.connect(this);
/* 180 */       this.n.add(this.keyboardLayout);
/*     */       
/* 182 */       this.eventLayout.connect(this);
/* 183 */       this.n.add(this.eventLayout);
/*     */       
/* 185 */       if (this.configs == null)
/*     */       {
/* 187 */         this.configs = new GuiListBox(this.j, 20, 202, 21, this.l - 224, this.m - 40, 16, false, false, false);
/* 188 */         this.configs.backColour = -16777202;
/* 189 */         this.configs.m = false;
/* 190 */         GuiMacroConfig.refreshConfigs(this.macros, this.configs);
/*     */       }
/*     */       else
/*     */       {
/* 194 */         this.configs.setSize(this.l - 224, this.m - 40);
/* 195 */         this.configs.m = false;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 200 */       beginTweening(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onHeaderClick() {
/* 207 */     if (MacroModSettings.configNameLinksToSettings)
/* 208 */     { AbstractionLayer.displayGuiScreen((bxf)new GuiMacroConfig(this, false)); }
/*     */     
/* 210 */     else if (this.configs != null) { this.configs.m = !this.configs.m; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void m() {
/* 219 */     this.keyboardLayout.release();
/* 220 */     this.eventLayout.release();
/*     */     
/* 222 */     super.m();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/* 231 */     if (minimisedEditor != null) {
/*     */       
/* 233 */       AbstractionLayer.displayGuiScreen((bxf)minimisedEditor);
/* 234 */       minimisedEditor = null;
/*     */       
/*     */       return;
/*     */     } 
/* 238 */     if (this.configs != null) {
/* 239 */       this.configs.updateCounter++;
/*     */     }
/* 241 */     super.e();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleWidgetClick(ILayoutPanel<? extends ILayoutWidget> source, int keyCode, boolean bindable) {
/* 251 */     if (bindable) {
/*     */       
/* 253 */       this.macros.save();
/* 254 */       this.keyboardLayout.release();
/* 255 */       this.eventLayout.release();
/* 256 */       AbstractionLayer.displayGuiScreen((bxf)new GuiMacroEdit(keyCode, (bxf)this));
/*     */     }
/* 258 */     else if (source == this.buttonsLayout) {
/*     */       
/* 260 */       LayoutPanel.setGlobalMode(LayoutPanelEditMode.EditButtonsOnly);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(int mouseX, int mouseY, int button) throws IOException {
/* 267 */     boolean configsHide = false;
/*     */     
/* 269 */     if (this.configs != null && this.configs.m) {
/*     */       
/* 271 */       if (button == 0 && this.configs.c(this.j, mouseX, mouseY)) {
/*     */         
/* 273 */         this.j.U().a((cye)cxy.a(ResourceLocations.BUTTON_PRESS, 1.0F));
/* 274 */         a((bug)this.configs);
/*     */         
/*     */         return;
/*     */       } 
/* 278 */       if (button == 0)
/*     */       {
/* 280 */         configsHide = true;
/*     */       }
/*     */     } 
/*     */     
/* 284 */     super.a(mouseX, mouseY, button);
/*     */     
/* 286 */     if (this.configs != null && configsHide) {
/* 287 */       this.configs.m = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) {
/* 298 */     if (this.updateCounter < 2)
/*     */       return; 
/* 300 */     int currentPage = getPage();
/*     */     
/* 302 */     if (this.configs != null && this.configs.m) {
/*     */       
/* 304 */       if (keyCode == 200) { this.configs.up(); a((bug)this.configs); return; }
/* 305 */        if (keyCode == 208) { this.configs.down(); a((bug)this.configs); return; }
/* 306 */        if (keyCode == 28) { this.configs.m = false; return; }
/* 307 */        if (keyCode == 156) { this.configs.m = false; return; }
/* 308 */        if (keyCode == 1) { this.configs.m = false;
/*     */         return; }
/*     */     
/*     */     } else {
/* 312 */       if (currentPage == 0 && this.keyboardLayout.keyPressed(keyChar, keyCode))
/* 313 */         return;  if (currentPage == 1 && this.eventLayout.keyPressed(keyChar, keyCode))
/*     */         return; 
/*     */     } 
/* 316 */     super.a(keyChar, keyCode);
/*     */     
/* 318 */     if (keyCode != 1) {
/*     */       
/* 320 */       if (InputHandler.isKeyDown(InputHandler.getOverrideKeyCode()) && !MacroModSettings.simpleGui) {
/*     */         
/* 322 */         if (keyCode == 209 || keyCode == 205) {
/*     */           
/* 324 */           this.overrideKeyDownTime = 0;
/* 325 */           onPageUpClick();
/*     */         } 
/*     */         
/* 328 */         if (keyCode == 201 || keyCode == 203) {
/*     */           
/* 330 */           this.overrideKeyDownTime = 0;
/* 331 */           onPageDownClick();
/*     */         } 
/*     */         
/* 334 */         if (keyCode == 208 || keyCode == 200) {
/*     */           
/* 336 */           this.overrideKeyDownTime = 0;
/* 337 */           if (this.configs != null) this.configs.m = !this.configs.m;
/*     */         
/*     */         } 
/* 340 */         if (keyCode == 45) {
/*     */           
/* 342 */           this.overrideKeyDownTime = 0;
/* 343 */           LayoutPanel.setGlobalMode(LayoutPanelEditMode.Move);
/*     */         } 
/*     */         
/* 346 */         if (keyCode == 46) {
/*     */           
/* 348 */           this.overrideKeyDownTime = 0;
/* 349 */           LayoutPanel.setGlobalMode(LayoutPanelEditMode.Copy);
/*     */         } 
/*     */         
/* 352 */         if (keyCode == 32 || keyCode == 211) {
/*     */           
/* 354 */           this.overrideKeyDownTime = 0;
/* 355 */           LayoutPanel.setGlobalMode(LayoutPanelEditMode.Delete);
/*     */         } 
/*     */         
/* 358 */         if (keyCode == 20) {
/*     */           
/* 360 */           this.overrideKeyDownTime = 0;
/* 361 */           AbstractionLayer.displayGuiScreen((bxf)new GuiEditTextFile(this, ScriptContext.MAIN));
/*     */         } 
/*     */         
/* 364 */         if (keyCode == 31) {
/*     */           
/* 366 */           this.overrideKeyDownTime = 0;
/* 367 */           AbstractionLayer.displayGuiScreen((bxf)new GuiMacroConfig(this, false));
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 373 */       if (currentPage == 0 && keyCode > 0 && keyCode != InputHandler.getOverrideKeyCode()) {
/*     */         
/* 375 */         if (keyCode == InputHandler.getSneakKeyCode())
/*     */           return; 
/* 377 */         if (keyCode == InputHandler.keybindActivate.i() && InputHandler.isKeyDown(InputHandler.getSneakKeyCode())) {
/*     */           
/* 379 */           AbstractionLayer.displayGuiScreen((bxf)new GuiMacroConfig(this, false));
/*     */           
/*     */           return;
/*     */         } 
/* 383 */         handleWidgetClick((ILayoutPanel<? extends ILayoutWidget>)null, keyCode, true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(bug guibutton) {
/* 391 */     if (this.configs != null && guibutton.k == this.configs.k) {
/*     */       
/* 393 */       IListObject selectedItem = this.configs.getSelectedItem();
/*     */       
/* 395 */       if (selectedItem.getId() == -2) {
/*     */         
/* 397 */         this.waitingFeedback = true;
/* 398 */         displayDialog((GuiDialogBox)new GuiDialogBoxAddConfig(this));
/*     */         
/*     */         return;
/*     */       } 
/* 402 */       if (selectedItem.getCustomAction(true).equals("delete")) {
/*     */         
/* 404 */         this.waitingFeedback = true;
/* 405 */         displayDialog((GuiDialogBox)new GuiDialogBoxConfirm(this, LocalisationProvider.getLocalisedString("gui.delete"), LocalisationProvider.getLocalisedString("param.action.confirmdelete"), selectedItem.getData().toString()));
/*     */         
/*     */         return;
/*     */       } 
/* 409 */       this.macros.setActiveConfig((String)selectedItem.getData());
/*     */       
/* 411 */       if (this.configs.isDoubleClicked(true))
/*     */       {
/* 413 */         this.configs.m = false;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDialogClosed(GuiDialogBox dialog) {
/* 424 */     if (this.waitingFeedback && this.configs != null) {
/*     */       
/* 426 */       this.waitingFeedback = false;
/*     */       
/* 428 */       if (dialog.dialogResult == GuiDialogBox.DialogResult.OK) {
/*     */         
/* 430 */         if (dialog instanceof GuiDialogBoxAddConfig) {
/*     */           
/* 432 */           GuiDialogBoxAddConfig addConfigDialog = (GuiDialogBoxAddConfig)dialog;
/*     */           
/* 434 */           this.macros.addConfig(addConfigDialog.newConfigName, addConfigDialog.copySettings);
/* 435 */           this.macros.setActiveConfig(addConfigDialog.newConfigName);
/*     */         } 
/*     */         
/* 438 */         if (dialog instanceof GuiDialogBoxConfirm) {
/*     */           
/* 440 */           this.macros.setActiveConfig("");
/* 441 */           this.macros.deleteConfig(this.configs.getSelectedItem().getData().toString());
/*     */         } 
/*     */       } 
/*     */       
/* 445 */       GuiMacroConfig.refreshConfigs(this.macros, this.configs);
/* 446 */       AbstractionLayer.displayGuiScreen((bxf)this);
/*     */     }
/*     */     else {
/*     */       
/* 450 */       super.onDialogClosed(dialog);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseWheelScrolled(int mouseWheelDelta) {
/* 457 */     if (this.configs != null && this.configs.m) {
/*     */       
/* 459 */       mouseWheelDelta /= 120;
/*     */       
/* 461 */       while (mouseWheelDelta > 0) {
/*     */         
/* 463 */         this.configs.up();
/* 464 */         a((bug)this.configs);
/* 465 */         mouseWheelDelta--;
/*     */       } 
/*     */       
/* 468 */       while (mouseWheelDelta < 0) {
/*     */         
/* 470 */         this.configs.down();
/* 471 */         a((bug)this.configs);
/* 472 */         mouseWheelDelta++;
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
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float f) {
/* 487 */     GL.glEnableAlphaTest();
/* 488 */     GL.glAlphaFunc(516, 0.1F);
/*     */     
/* 490 */     super.a(mouseX, mouseY, f);
/*     */     
/* 492 */     if (this.configs != null && this.configs.m) {
/*     */       
/* 494 */       a(201, 1, this.l - 21, this.configs.getHeight() + 22, -1);
/* 495 */       drawTitle(this.screenBanner, this.screenCentreBanner, 202, 2, this.l - (this.screenDrawMinButton ? 44 : 22), this.screenBannerColour, -16777216);
/* 496 */       this.configs.a(this.j, mouseX, mouseY);
/*     */     } 
/*     */ 
/*     */     
/* 500 */     int overButton = 0;
/*     */ 
/*     */     
/* 503 */     if (this.keyboardLayout.isDragging() || this.eventLayout.isDragging() || this.buttonsLayout.isDragging())
/*     */     {
/* 505 */       overButton = getButtonOverAt(mouseX, mouseY);
/*     */     }
/*     */ 
/*     */     
/* 509 */     if (overButton > 0) {
/*     */       
/* 511 */       if (this.mouseOverButtonTime == 0) {
/*     */         
/* 513 */         this.mouseOverButtonTime = this.updateCounter;
/*     */ 
/*     */       
/*     */       }
/* 517 */       else if (this.updateCounter - this.mouseOverButtonTime > 10) {
/*     */ 
/*     */         
/* 520 */         this.mouseOverButtonTime = this.updateCounter + 10;
/*     */         
/* 522 */         if (overButton == 1) {
/* 523 */           onPageDownClick();
/*     */         } else {
/* 525 */           onPageUpClick();
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 531 */       this.mouseOverButtonTime = 0;
/*     */     } 
/*     */     
/* 534 */     if (InputHandler.isKeyDown(InputHandler.getOverrideKeyCode()) && !MacroModSettings.simpleGui) {
/*     */       
/* 536 */       if (this.overrideKeyDownTime == 0)
/*     */       {
/* 538 */         this.overrideKeyDownTime = this.updateCounter;
/*     */ 
/*     */       
/*     */       }
/* 542 */       else if (this.updateCounter - this.overrideKeyDownTime > 20)
/*     */       {
/* 544 */         drawTooltip("LEFT", 37, 30, -16711936, -1157627904);
/* 545 */         drawTooltip("RIGHT", 195, 30, -16711936, -1157627904);
/* 546 */         if (this.configs == null || !this.configs.m) drawTooltip("DOWN", this.l - 60, 30, -16711936, -1157627904); 
/* 547 */         drawTooltip("C", 12, this.m - 6, -16711936, -1157627904);
/* 548 */         drawTooltip("X", 32, this.m - 6, -16711936, -1157627904);
/* 549 */         drawTooltip("D", 52, this.m - 6, -16711936, -1157627904);
/* 550 */         drawTooltip("T", this.l - 32, this.m - 6, -16711936, -1157627904);
/* 551 */         drawTooltip("S", this.l - 12, this.m - 6, -16711936, -1157627904);
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 557 */       this.overrideKeyDownTime = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean drawComplexGuiElements() {
/* 567 */     return !MacroModSettings.simpleGui;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawLayouts() {
/* 576 */     this.keyboardLayout.setSizeAndPosition(0, 22, this.l, this.m - 38);
/* 577 */     this.eventLayout.setSizeAndPosition(this.l, 22, this.l, this.m - 38);
/* 578 */     this.buttonsLayout.setSizeAndPosition(this.l * 2 + 2, 22, this.l - 4, this.m - 38);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setTitles(int currentPage) {
/* 584 */     if (currentPage == 0) {
/*     */       
/* 586 */       this.screenTitle = LocalisationProvider.getLocalisedString("macro.bind.title") + ": " + LocalisationProvider.getLocalisedString("macro.trigger.type.keys");
/* 587 */       this.prompt = LocalisationProvider.getLocalisedString("macro.prompt." + (MacroModSettings.simpleGui ? "edit.simple" : "edit"));
/*     */     }
/* 589 */     else if (currentPage == 1) {
/*     */       
/* 591 */       this.screenTitle = LocalisationProvider.getLocalisedString("macro.bind.title") + ": " + LocalisationProvider.getLocalisedString("macro.trigger.type.events");
/* 592 */       this.prompt = LocalisationProvider.getLocalisedString("macro.prompt.edit.event");
/*     */     }
/* 594 */     else if (currentPage == 2) {
/*     */       
/* 596 */       this.screenTitle = LocalisationProvider.getLocalisedString("macro.bind.title") + ": " + LocalisationProvider.getLocalisedString("macro.trigger.type.control");
/* 597 */       this.prompt = LocalisationProvider.getLocalisedString("macro.prompt.edit.control");
/*     */     }
/*     */     else {
/*     */       
/* 601 */       this.screenTitle = LocalisationProvider.getLocalisedString("macro.bind.title");
/* 602 */       this.prompt = LocalisationProvider.getLocalisedString("macro.prompt.edit.unknown");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postRender(int currentPage, int mouseX, int mouseY, float partialTick) {
/* 612 */     super.postRender(currentPage, mouseX, mouseY, partialTick);
/*     */     
/* 614 */     this.keyboardLayout.postRender(this.j, mouseX, mouseY);
/* 615 */     this.eventLayout.postRender(this.j, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMenuItemClicked(String menuItem) {
/* 624 */     if (menuItem.equals("keys")) {
/* 625 */       beginTweening(0);
/*     */     }
/* 627 */     if (menuItem.equals("events")) {
/* 628 */       beginTweening(1);
/*     */     }
/* 630 */     if (menuItem.equals("buttons")) {
/* 631 */       beginTweening(2);
/*     */     }
/* 633 */     if (menuItem.equals("options")) {
/* 634 */       AbstractionLayer.displayGuiScreen((bxf)new GuiMacroConfig(this, false));
/*     */     }
/* 636 */     if (menuItem.equals("perms")) {
/* 637 */       AbstractionLayer.displayGuiScreen((bxf)new GuiPermissions((bxf)this));
/*     */     }
/* 639 */     if (menuItem.equals("editgui")) {
/* 640 */       AbstractionLayer.displayGuiScreen((bxf)new GuiLayoutPatch((bxf)this));
/*     */     }
/* 642 */     if (menuItem.equals("custom") && customScreenClass != null)
/*     */       
/*     */       try {
/*     */         
/* 646 */         Constructor<? extends bxf> constructor = customScreenClass.getDeclaredConstructor(new Class[] { GuiMacroBind.class });
/* 647 */         AbstractionLayer.displayGuiScreen(constructor.newInstance(new Object[] { this }));
/*     */       }
/* 649 */       catch (Exception ex) {
/*     */         
/* 651 */         ex.printStackTrace();
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\screens\GuiMacroBind.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */