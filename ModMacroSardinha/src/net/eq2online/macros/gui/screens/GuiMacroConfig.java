/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bsu;
/*     */ import bug;
/*     */ import bxf;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import com.mumfrey.liteloader.gl.GLClippingPlanes;
/*     */ import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.GuiControl;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.controls.GuiScrollBar;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxAddConfig;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxConfirm;
/*     */ import net.eq2online.macros.gui.layout.LayoutPanel;
/*     */ import net.eq2online.macros.gui.layout.LayoutPanelEditMode;
/*     */ import net.eq2online.macros.gui.layout.LayoutPanelKeys;
/*     */ import net.eq2online.macros.gui.list.ListObjectEditable;
/*     */ import net.eq2online.macros.gui.list.ListObjectGeneric;
/*     */ import net.eq2online.macros.gui.shared.GuiControlEx;
/*     */ import net.eq2online.macros.gui.shared.GuiDialogBox;
/*     */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*     */ import net.eq2online.macros.input.InputHandler;
/*     */ import net.eq2online.macros.interfaces.IListObject;
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
/*     */ public class GuiMacroConfig
/*     */   extends GuiScreenEx
/*     */   implements ConfigPanelHost
/*     */ {
/*     */   private GuiScreenEx parentScreen;
/*     */   private Macros macros;
/*     */   private GuiListBox configs;
/*     */   private GuiCheckBox chkAutoSwitch;
/*     */   private boolean enableAutoConfigSwitch;
/*     */   private GuiControl btnOk;
/*     */   private GuiControl btnCancel;
/*     */   private GuiScrollBar scrollBar;
/*     */   private GuiMacroConfigPanel configPanel;
/*     */   private boolean noConfigSwitch = false;
/*     */   private boolean editingReservedKeys = false;
/*     */   private int lastMouseX;
/*     */   private int lastMouseY;
/*  94 */   private int suspendInput = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiMacroConfig(GuiScreenEx parentScreen, boolean noConfigSwitch) {
/* 104 */     this.e = 999.0F;
/*     */     
/* 106 */     this.noConfigSwitch = noConfigSwitch;
/* 107 */     this.parentScreen = parentScreen;
/* 108 */     this.macros = MacroModCore.getMacroManager();
/*     */     
/* 110 */     this.enableAutoConfigSwitch = MacroModSettings.enableAutoConfigSwitch;
/*     */     
/* 112 */     this.configPanel = new GuiMacroConfigPanel(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void submit() {
/* 120 */     this.configPanel.onPanelHidden();
/*     */     
/* 122 */     MacroModSettings.enableAutoConfigSwitch = this.chkAutoSwitch.checked;
/*     */     
/* 124 */     this.macros.save();
/* 125 */     MacroModCore.getInstance().notifyChangeConfiguration();
/*     */     
/* 127 */     AbstractionLayer.displayGuiScreen((bxf)this.parentScreen);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/* 136 */     this.suspendInput = 4;
/*     */     
/* 138 */     if (this.editingReservedKeys) {
/*     */       
/* 140 */       LayoutPanelKeys kb = this.macros.getKeyboardLayout();
/* 141 */       kb.connect(null);
/* 142 */       LayoutPanel.setGlobalMode(LayoutPanelEditMode.Reserve);
/* 143 */       clearControlList();
/* 144 */       addControl((GuiControl)kb);
/*     */     }
/*     */     else {
/*     */       
/* 148 */       if (this.configs == null) {
/*     */         
/* 150 */         this.configs = new GuiListBox(this.j, 1, 4, 40, 174, this.m - 94, 16, false, false, false);
/* 151 */         this.chkAutoSwitch = new GuiCheckBox(21, 10, this.m - 50, LocalisationProvider.getLocalisedString("options.option.enableautoswitch"), this.enableAutoConfigSwitch);
/* 152 */         this.scrollBar = new GuiScrollBar(this.j, 0, this.l - 24, 40, 20, this.m - 70, 0, 1000, GuiScrollBar.ScrollBarOrientation.Vertical);
/*     */         
/* 154 */         refreshConfigs(this.macros, this.configs);
/*     */         
/* 156 */         this.configs.l = !this.noConfigSwitch;
/*     */       }
/*     */       else {
/*     */         
/* 160 */         this.configs.setSize(174, this.m - 94);
/* 161 */         this.chkAutoSwitch.setYPosition(this.m - 50);
/* 162 */         this.scrollBar.setSizeAndPosition(this.l - 24, 40, 20, this.m - 70);
/*     */       } 
/*     */ 
/*     */       
/* 166 */       this.btnOk = new GuiControl(2, this.l - 64, this.m - 24, 60, 20, LocalisationProvider.getLocalisedString("gui.ok"));
/* 167 */       this.btnCancel = new GuiControl(3, this.l - 128, this.m - 24, 60, 20, LocalisationProvider.getLocalisedString("gui.cancel"));
/*     */ 
/*     */       
/* 170 */       addControl((GuiControl)this.configs);
/* 171 */       addControl((GuiControl)this.chkAutoSwitch);
/* 172 */       addControl((GuiControl)this.scrollBar);
/* 173 */       addControl(this.btnCancel);
/*     */       
/* 175 */       this.configPanel.onPanelResize(this);
/* 176 */       this.scrollBar.setMax(Math.max(0, this.configPanel.getContentHeight() - getHeight()));
/*     */     } 
/*     */     
/* 179 */     addControl(this.btnOk);
/*     */     
/* 181 */     super.b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void refreshConfigs(Macros macros, GuiListBox configs) {
/* 189 */     int id = 0;
/*     */     
/* 191 */     configs.clear();
/* 192 */     configs.addItem((IListObject)new ListObjectGeneric(-1, LocalisationProvider.getLocalisedString("options.defaultconfig"), ""));
/*     */     
/* 194 */     for (String config : macros.getConfigNames())
/*     */     {
/* 196 */       configs.addItem((IListObject)new ListObjectEditable(id++, config, config, false, true));
/*     */     }
/*     */     
/* 199 */     configs.selectData(macros.getActiveConfig());
/* 200 */     configs.scrollToCentre();
/* 201 */     configs.addItem((IListObject)new ListObjectGeneric(-2, LocalisationProvider.getLocalisedString("options.addconfig")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/* 210 */     if (this.suspendInput > 0) this.suspendInput--;
/*     */     
/* 212 */     this.configPanel.onTick(this);
/*     */     
/* 214 */     super.e();
/*     */     
/* 216 */     LayoutPanel.panelUpdateCounter = this.updateCounter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(bug guibutton) {
/* 227 */     if (this.editingReservedKeys) {
/*     */       
/* 229 */       if (guibutton.k == this.btnOk.k || guibutton.k == this.btnCancel.k)
/*     */       {
/* 231 */         this.editingReservedKeys = false;
/* 232 */         this.macros.getKeyboardLayout().release();
/* 233 */         b();
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 238 */       if (guibutton.k == this.configs.k) {
/*     */         
/* 240 */         IListObject selectedItem = this.configs.getSelectedItem();
/*     */         
/* 242 */         if (selectedItem.getId() == -2) {
/*     */ 
/*     */           
/* 245 */           displayDialog((GuiDialogBox)new GuiDialogBoxAddConfig(this));
/*     */           
/*     */           return;
/*     */         } 
/* 249 */         if (selectedItem.getCustomAction(true).equals("delete")) {
/*     */           
/* 251 */           displayDialog((GuiDialogBox)new GuiDialogBoxConfirm(this, LocalisationProvider.getLocalisedString("gui.delete"), LocalisationProvider.getLocalisedString("param.action.confirmdelete"), selectedItem.getData().toString()));
/*     */           
/*     */           return;
/*     */         } 
/* 255 */         this.macros.setActiveConfig((String)selectedItem.getData());
/*     */         
/* 257 */         if (this.configs.isDoubleClicked(true))
/*     */         {
/* 259 */           submit();
/*     */         }
/*     */       } 
/*     */       
/* 263 */       if (guibutton.k == this.btnOk.k) {
/* 264 */         submit();
/*     */       }
/* 266 */       if (guibutton.k == this.btnCancel.k) {
/* 267 */         AbstractionLayer.displayGuiScreen((bxf)this.parentScreen);
/*     */       }
/* 269 */       if (guibutton.k == 4) {
/*     */         
/* 271 */         this.editingReservedKeys = true;
/* 272 */         b();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) {
/* 283 */     if (this.editingReservedKeys) {
/*     */       
/* 285 */       if (keyCode == 1 || keyCode == 28 || keyCode == 156 || (keyCode == InputHandler.keybindActivate.i() && InputHandler.isKeyDown(InputHandler.getSneakKeyCode())))
/*     */       {
/* 287 */         a((bug)this.btnOk);
/*     */       
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 293 */       if (keyCode == 1) {
/*     */         
/* 295 */         AbstractionLayer.displayGuiScreen((bxf)this.parentScreen);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 300 */       if (keyCode == 28 || keyCode == 156 || (keyCode == InputHandler.keybindActivate.i() && InputHandler.isKeyDown(InputHandler.getSneakKeyCode()))) {
/*     */         
/* 302 */         submit();
/*     */         
/*     */         return;
/*     */       } 
/* 306 */       this.configPanel.keyPressed(this, keyChar, keyCode);
/*     */       
/* 308 */       if (!this.configPanel.ateKeyPress()) {
/*     */         
/* 310 */         if (keyCode == 200) { this.configs.up(); a((bug)this.configs); }
/* 311 */          if (keyCode == 208) { this.configs.down(); a((bug)this.configs); }
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseWheelScrolled(int mouseWheelDelta) {
/* 319 */     mouseWheelDelta /= 120;
/*     */     
/* 321 */     if (this.lastMouseY > 40 && this.lastMouseY < this.m - 54)
/*     */     {
/* 323 */       if (this.lastMouseX < 180) {
/*     */         
/* 325 */         while (mouseWheelDelta > 0) {
/*     */           
/* 327 */           this.configs.up();
/* 328 */           a((bug)this.configs);
/* 329 */           mouseWheelDelta--;
/*     */         } 
/*     */         
/* 332 */         while (mouseWheelDelta < 0)
/*     */         {
/* 334 */           this.configs.down();
/* 335 */           a((bug)this.configs);
/* 336 */           mouseWheelDelta++;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 341 */         while (mouseWheelDelta > 0) {
/*     */           
/* 343 */           this.scrollBar.setValue(this.scrollBar.getValue() - 30);
/* 344 */           mouseWheelDelta--;
/*     */         } 
/*     */         
/* 347 */         while (mouseWheelDelta < 0) {
/*     */           
/* 349 */           this.scrollBar.setValue(this.scrollBar.getValue() + 30);
/* 350 */           mouseWheelDelta++;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(int mouseX, int mouseY, int button) throws IOException {
/* 359 */     if (this.suspendInput > 0)
/*     */       return; 
/* 361 */     if (!this.editingReservedKeys && mouseX > 182 && mouseY > 40 && mouseY < this.m - 40)
/*     */     {
/* 363 */       this.configPanel.mousePressed(this, mouseX - 182, mouseY - getScrollPos(), button);
/*     */     }
/*     */     
/* 366 */     if (this.suspendInput > 0)
/*     */       return; 
/* 368 */     if (mouseX > this.l - 20 && mouseY < 20)
/*     */     {
/* 370 */       a((bug)this.btnCancel);
/*     */     }
/*     */     
/* 373 */     super.a(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float f) {
/* 379 */     this.lastMouseX = mouseX;
/* 380 */     this.lastMouseY = mouseY;
/*     */     
/* 382 */     if (AbstractionLayer.getWorld() == null) {
/* 383 */       c();
/*     */     }
/* 385 */     drawScreenWithEnabledState(mouseX, mouseY, f, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreenWithEnabledState(int mouseX, int mouseY, float partialTicks, boolean enabled) {
/* 391 */     this.configs.setEnabled((!this.noConfigSwitch && enabled));
/*     */     
/* 393 */     bsu minecraft = bsu.z();
/*     */     
/* 395 */     if (enabled) drawBackground();
/*     */ 
/*     */     
/* 398 */     GuiControlEx.drawStringWithEllipsis(this.q, LocalisationProvider.getLocalisedString("macro.currentconfig", new Object[] { this.macros.getActiveConfigName() }), 186, 7, this.l - 210, 4259648);
/*     */ 
/*     */     
/* 401 */     a(this.q, LocalisationProvider.getLocalisedString("options.title"), 90, 7, 16776960);
/* 402 */     drawTexturedModalRect(ResourceLocations.MAIN, this.l - 17, 5, this.l - 5, 17, 104, 104, 128, 128);
/*     */     
/* 404 */     if (this.editingReservedKeys) {
/*     */       
/* 406 */       this.btnOk.setXPosition(4); this.btnOk.setYPosition(this.m - 24);
/* 407 */       this.btnOk.a(minecraft, mouseX, mouseY);
/*     */       
/* 409 */       GLClippingPlanes.glEnableVerticalClipping(22, this.m - 30);
/* 410 */       this.macros.getKeyboardLayout().setSizeAndPosition(0, 22, this.l, this.m - 50);
/* 411 */       this.macros.getKeyboardLayout().a(minecraft, mouseX, mouseY);
/* 412 */       GLClippingPlanes.glDisableClipping();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 417 */     c(this.q, LocalisationProvider.getLocalisedString("options.selectconfig"), 8, 26, 16776960);
/* 418 */     c(this.q, LocalisationProvider.getLocalisedString("options.option.title"), 188, 26, 16776960);
/*     */     
/* 420 */     this.btnOk.a(minecraft, mouseX, mouseY);
/* 421 */     this.btnCancel.a(minecraft, mouseX, mouseY);
/* 422 */     this.scrollBar.a(minecraft, mouseX, mouseY);
/* 423 */     this.configs.a(minecraft, mouseX, mouseY);
/* 424 */     this.chkAutoSwitch.a(minecraft, mouseX, mouseY);
/*     */ 
/*     */     
/* 427 */     GLClippingPlanes.glEnableVerticalClipping(40, this.m - 30);
/*     */     
/* 429 */     int yPos = getScrollPos();
/* 430 */     GL.glPushMatrix();
/* 431 */     GL.glTranslatef(182.0F, yPos, 0.0F);
/*     */     
/* 433 */     this.configPanel.drawPanel(this, mouseX - 182, mouseY - yPos, partialTicks);
/*     */     
/* 435 */     GL.glPopMatrix();
/*     */     
/* 437 */     GLClippingPlanes.glDisableClipping();
/*     */     
/* 439 */     if (!enabled) drawBackground();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getScrollPos() {
/* 447 */     return 44 - this.scrollBar.getValue();
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
/*     */   private void drawBackground() {
/* 459 */     int backColour = -1342177280, backColour2 = -1607257293;
/*     */     
/* 461 */     a(2, 2, 180, 20, backColour);
/* 462 */     a(182, 2, this.l - 22, 20, backColour);
/* 463 */     a(this.l - 20, 2, this.l - 2, 20, backColour);
/*     */     
/* 465 */     if (this.editingReservedKeys) {
/*     */       
/* 467 */       a(2, 22, this.l - 2, this.m - 28, backColour);
/*     */     }
/*     */     else {
/*     */       
/* 471 */       a(2, 22, 180, 38, backColour2);
/* 472 */       a(2, 38, 180, this.m - 28, backColour);
/* 473 */       a(182, 22, this.l - 2, 38, backColour2);
/* 474 */       a(182, 38, this.l - 2, this.m - 28, backColour);
/*     */     } 
/*     */     
/* 477 */     a(2, this.m - 26, this.l - 2, this.m - 2, backColour);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDialogClosed(GuiDialogBox dialog) {
/* 486 */     if (dialog.dialogResult == GuiDialogBox.DialogResult.OK) {
/*     */       
/* 488 */       if (dialog instanceof GuiDialogBoxAddConfig) {
/*     */         
/* 490 */         GuiDialogBoxAddConfig addConfigDialog = (GuiDialogBoxAddConfig)dialog;
/*     */         
/* 492 */         this.macros.addConfig(addConfigDialog.newConfigName, addConfigDialog.copySettings);
/* 493 */         this.macros.setActiveConfig(addConfigDialog.newConfigName);
/*     */       } 
/*     */       
/* 496 */       if (dialog instanceof GuiDialogBoxConfirm) {
/*     */         
/* 498 */         this.macros.setActiveConfig("");
/* 499 */         this.macros.deleteConfig(this.configs.getSelectedItem().getData().toString());
/*     */       } 
/*     */     } 
/*     */     
/* 503 */     refreshConfigs(this.macros, this.configs);
/*     */     
/* 505 */     super.onDialogClosed(dialog);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <ModClass extends com.mumfrey.liteloader.LiteMod> ModClass getMod() {
/* 511 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 522 */     return this.l;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 528 */     return this.m - 70;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\screens\GuiMacroConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */