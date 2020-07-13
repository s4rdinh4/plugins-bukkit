/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bty;
/*     */ import bug;
/*     */ import bxf;
/*     */ import cio;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.GuiControl;
/*     */ import net.eq2online.macros.compatibility.IconTiled;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.core.params.MacroParam;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.controls.GuiListBoxIconic;
/*     */ import net.eq2online.macros.gui.controls.GuiTextFieldEx;
/*     */ import net.eq2online.macros.gui.list.ListObjectGeneric;
/*     */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*     */ import net.eq2online.macros.interfaces.IListObject;
/*     */ import net.eq2online.macros.struct.Place;
/*     */ import oa;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import uv;
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
/*     */ public class GuiEditListEntry
/*     */   extends GuiScreenEx
/*     */ {
/*     */   private GuiMacroParam parentScreen;
/*     */   private MacroParam param;
/*     */   private GuiTextFieldEx textFieldItemName;
/*     */   private GuiTextFieldEx textFieldDisplayName;
/*     */   private GuiTextFieldEx textFieldXCoord;
/*     */   private GuiTextFieldEx textFieldYCoord;
/*     */   private GuiTextFieldEx textFieldZCoord;
/*     */   private GuiListBox listBoxChooseIcon;
/*     */   private GuiControl btnOk;
/*     */   private GuiControl btnCancel;
/*     */   private GuiControl btnAutoPopulate;
/*     */   private GuiControl btnUseCurrentLocation;
/*     */   protected IListObject editingObject;
/*     */   public String displayText;
/*     */   public boolean enableIconChoice = true;
/*     */   public boolean enableDisplayName = false;
/*     */   public boolean enableCoords = false;
/*  87 */   public int windowHeight = 158;
/*     */   
/*  89 */   protected int startIconIndex = 0; protected int endIconIndex = 255;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String numericCharacters = "-0123456789";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiEditListEntry(GuiMacroParam guiscreen, MacroParam param, IListObject editingObject) {
/* 104 */     this.parentScreen = guiscreen;
/* 105 */     this.param = param;
/* 106 */     this.editingObject = editingObject;
/*     */     
/* 108 */     this.param.setupEditEntryWindow(this, (editingObject != null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/* 117 */     Keyboard.enableRepeatEvents(true);
/*     */     
/* 119 */     oa texture = this.param.getIconTexture();
/* 120 */     Boolean populateList = Boolean.valueOf(false);
/*     */     
/* 122 */     if (this.textFieldItemName == null) {
/*     */       
/* 124 */       this.textFieldItemName = new GuiTextFieldEx(0, this.q, 188, 20, 196, 16, "");
/* 125 */       this.textFieldDisplayName = new GuiTextFieldEx(1, this.q, 188, this.windowHeight - 46, 196, 16, "");
/*     */     } 
/*     */     
/* 128 */     this.textFieldItemName.b(true);
/* 129 */     this.textFieldItemName.f(100);
/*     */     
/* 131 */     if (this.textFieldXCoord == null && this.enableCoords) {
/*     */       
/* 133 */       this.textFieldXCoord = new GuiTextFieldEx(2, this.q, 208, 50, 40, 16, "");
/* 134 */       this.textFieldYCoord = new GuiTextFieldEx(3, this.q, 208, 70, 40, 16, "");
/* 135 */       this.textFieldZCoord = new GuiTextFieldEx(4, this.q, 208, 90, 40, 16, "");
/* 136 */       setCoordsFromPlayerLocation();
/*     */     } 
/*     */     
/* 139 */     if (this.listBoxChooseIcon == null && this.enableIconChoice) {
/*     */       
/* 141 */       this.listBoxChooseIcon = (GuiListBox)new GuiListBoxIconic(this.j, 2, 188, 54, 196, 80, 20, true, false, false);
/* 142 */       populateList = Boolean.valueOf(true);
/*     */     } 
/*     */     
/* 145 */     this.param.setupEditEntryTextbox(this.textFieldItemName);
/*     */     
/* 147 */     if (this.param.isAutoPopulateSupported() && MacroModSettings.enableAutoDiscovery)
/*     */     {
/* 149 */       this.btnAutoPopulate = new GuiControl(3, 324, this.windowHeight - 22, 60, 20, LocalisationProvider.getLocalisedString("gui.auto"));
/*     */     }
/*     */     
/* 152 */     if (this.enableCoords)
/*     */     {
/* 154 */       this.btnUseCurrentLocation = new GuiControl(4, 255, 68, 129, 20, LocalisationProvider.getLocalisedString("entry.usemylocation"));
/*     */     }
/*     */ 
/*     */     
/* 158 */     if (this.editingObject != null) {
/*     */       
/* 160 */       texture = this.editingObject.getIconTexture();
/* 161 */       this.textFieldItemName.a(this.editingObject.getText());
/* 162 */       this.textFieldDisplayName.a(this.editingObject.getDisplayName());
/*     */       
/* 164 */       if (this.editingObject.getDisplayName().equals(this.editingObject.getText())) {
/* 165 */         this.textFieldDisplayName.a("");
/*     */       }
/* 167 */       this.btnAutoPopulate = null;
/*     */       
/* 169 */       if (this.editingObject instanceof net.eq2online.macros.gui.list.ListObjectPlace) {
/*     */         
/* 171 */         this.textFieldXCoord.a("" + ((Place)this.editingObject.getData()).x);
/* 172 */         this.textFieldYCoord.a("" + ((Place)this.editingObject.getData()).y);
/* 173 */         this.textFieldZCoord.a("" + ((Place)this.editingObject.getData()).z);
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     if (populateList.booleanValue() && this.listBoxChooseIcon != null)
/*     */     {
/* 179 */       for (int icon = this.startIconIndex; icon <= this.endIconIndex; icon++) {
/* 180 */         this.listBoxChooseIcon.addItem((IListObject)new ListObjectGeneric(icon, LocalisationProvider.getLocalisedString("gui.icon") + " " + (icon - this.startIconIndex + 1), null, true, texture, icon));
/*     */       }
/*     */     }
/* 183 */     if (this.editingObject != null && this.listBoxChooseIcon != null) {
/*     */       
/* 185 */       this.listBoxChooseIcon.selectId(((IconTiled)this.editingObject.getIcon()).getIconID());
/* 186 */       this.listBoxChooseIcon.scrollToCentre();
/*     */     } 
/*     */     
/* 189 */     this.btnOk = new GuiControl(0, 186, this.windowHeight - 22, 60, 20, (this.editingObject == null) ? LocalisationProvider.getLocalisedString("gui.add") : LocalisationProvider.getLocalisedString("gui.save"));
/* 190 */     this.btnCancel = new GuiControl(1, 250, this.windowHeight - 22, 60, 20, LocalisationProvider.getLocalisedString("gui.cancel"));
/*     */     
/* 192 */     clearControlList();
/* 193 */     addControl(this.btnOk);
/* 194 */     addControl(this.btnCancel);
/*     */     
/* 196 */     if (this.btnAutoPopulate != null) addControl(this.btnAutoPopulate); 
/* 197 */     if (this.listBoxChooseIcon != null) addControl((GuiControl)this.listBoxChooseIcon); 
/* 198 */     if (this.btnUseCurrentLocation != null) addControl(this.btnUseCurrentLocation);
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void m() {
/* 204 */     Keyboard.enableRepeatEvents(false);
/*     */     
/* 206 */     super.m();
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
/* 217 */     if (guibutton.k == 0 && this.textFieldItemName.b().length() >= this.textFieldItemName.minStringLength) {
/*     */       
/* 219 */       if (this.editingObject == null) {
/*     */         
/* 221 */         if (this.param.addItem(this, this.textFieldItemName.b(), this.textFieldDisplayName.b(), (this.listBoxChooseIcon != null) ? this.listBoxChooseIcon.getSelectedItem().getId() : -1, null)) {
/*     */           return;
/*     */         }
/*     */       } else {
/*     */         
/* 226 */         this.param.editItem(this, this.textFieldItemName.b(), this.textFieldDisplayName.b(), (this.listBoxChooseIcon != null) ? this.listBoxChooseIcon.getSelectedItem().getId() : -1, this.editingObject);
/*     */       } 
/*     */       
/* 229 */       this.j.a((bxf)this.parentScreen);
/*     */     }
/* 231 */     else if (guibutton.k == 1) {
/*     */       
/* 233 */       AbstractionLayer.displayGuiScreen((bxf)this.parentScreen);
/*     */     }
/* 235 */     else if (guibutton.k == 3) {
/*     */       
/* 237 */       this.parentScreen.autoPopulate();
/*     */     }
/* 239 */     else if (guibutton.k == 4) {
/*     */       
/* 241 */       setCoordsFromPlayerLocation();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setCoordsFromPlayerLocation() {
/* 247 */     if (this.textFieldYCoord != null) {
/*     */       
/* 249 */       cio thePlayer = AbstractionLayer.getPlayer();
/*     */       
/* 251 */       if (thePlayer != null) {
/*     */         
/* 253 */         int posX = uv.c(thePlayer.s);
/* 254 */         int posY = uv.c((thePlayer.S()).b);
/* 255 */         int posZ = uv.c(thePlayer.u);
/*     */         
/* 257 */         this.textFieldXCoord.a(String.format("%d", new Object[] { Integer.valueOf(posX) }));
/* 258 */         this.textFieldYCoord.a(String.format("%d", new Object[] { Integer.valueOf(posY) }));
/* 259 */         this.textFieldZCoord.a(String.format("%d", new Object[] { Integer.valueOf(posZ) }));
/*     */       }
/*     */       else {
/*     */         
/* 263 */         this.textFieldXCoord.a("0");
/* 264 */         this.textFieldYCoord.a("0");
/* 265 */         this.textFieldZCoord.a("0");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/* 276 */     super.e();
/*     */     
/* 278 */     this.textFieldItemName.a();
/* 279 */     this.textFieldDisplayName.a();
/*     */     
/* 281 */     if (this.textFieldXCoord != null) this.textFieldXCoord.a(); 
/* 282 */     if (this.textFieldYCoord != null) this.textFieldYCoord.a(); 
/* 283 */     if (this.textFieldZCoord != null) this.textFieldZCoord.a();
/*     */     
/* 285 */     if (this.listBoxChooseIcon != null) {
/* 286 */       this.listBoxChooseIcon.updateCounter = this.updateCounter;
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
/*     */   public void a(int mouseX, int mouseY, float f) {
/* 299 */     bty fontRenderer = AbstractionLayer.getFontRenderer();
/*     */ 
/*     */     
/* 302 */     this.parentScreen.drawScreenWithEnabledState(mouseX, mouseY, f, false);
/*     */ 
/*     */     
/* 305 */     a(184, 2, 388, this.windowHeight, -2146562560);
/*     */ 
/*     */     
/* 308 */     c(fontRenderer, this.displayText, 188, 8, -1);
/* 309 */     if (this.listBoxChooseIcon != null) {
/* 310 */       c(fontRenderer, LocalisationProvider.getLocalisedString("entry.chooseicon"), 188, 42, -1);
/*     */     }
/*     */     
/* 313 */     this.textFieldItemName.g();
/*     */     
/* 315 */     if (this.enableDisplayName) {
/*     */       
/* 317 */       c(fontRenderer, LocalisationProvider.getLocalisedString("entry.displayname"), 188, this.windowHeight - 58, -1);
/* 318 */       this.textFieldDisplayName.g();
/*     */     } 
/*     */     
/* 321 */     if (this.textFieldXCoord != null) { this.textFieldXCoord.g(); c(fontRenderer, "X:", 193, 54, -1); }
/* 322 */      if (this.textFieldYCoord != null) { this.textFieldYCoord.g(); c(fontRenderer, "Y:", 193, 74, -1); }
/* 323 */      if (this.textFieldZCoord != null) { this.textFieldZCoord.g(); c(fontRenderer, "Z:", 193, 95, -1); }
/*     */ 
/*     */ 
/*     */     
/* 327 */     super.a(mouseX, mouseY, f);
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
/*     */   protected void a(int mouseX, int mouseY, int button) throws IOException {
/* 340 */     this.textFieldItemName.a(mouseX, mouseY, button);
/*     */     
/* 342 */     if (this.enableDisplayName) {
/* 343 */       this.textFieldDisplayName.a(mouseX, mouseY, button);
/*     */     }
/* 345 */     if (this.textFieldXCoord != null) this.textFieldXCoord.a(mouseX, mouseY, button); 
/* 346 */     if (this.textFieldYCoord != null) this.textFieldYCoord.a(mouseX, mouseY, button); 
/* 347 */     if (this.textFieldZCoord != null) this.textFieldZCoord.a(mouseX, mouseY, button);
/*     */     
/* 349 */     super.a(mouseX, mouseY, button);
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
/*     */   protected void a(char keyChar, int keyCode) {
/* 361 */     if (keyCode == 1) {
/*     */       
/* 363 */       AbstractionLayer.displayGuiScreen((bxf)this.parentScreen);
/*     */     }
/* 365 */     else if (keyCode == 28 || keyCode == 156) {
/*     */       
/* 367 */       a((bug)this.btnOk);
/*     */     }
/* 369 */     else if (keyCode == 200) {
/*     */       
/* 371 */       if (this.listBoxChooseIcon != null)
/*     */       {
/* 373 */         this.textFieldItemName.b(false);
/* 374 */         this.textFieldDisplayName.b(false);
/* 375 */         this.listBoxChooseIcon.up();
/*     */       }
/*     */     
/* 378 */     } else if (keyCode == 208) {
/*     */       
/* 380 */       if (this.listBoxChooseIcon != null)
/*     */       {
/* 382 */         this.textFieldItemName.b(false);
/* 383 */         this.textFieldDisplayName.b(false);
/* 384 */         this.listBoxChooseIcon.down();
/*     */       }
/*     */     
/* 387 */     } else if (keyCode == 205 && this.listBoxChooseIcon != null && !this.textFieldItemName.m() && !this.textFieldDisplayName.m()) {
/*     */       
/* 389 */       this.listBoxChooseIcon.right();
/*     */     }
/* 391 */     else if (keyCode == 203 && this.listBoxChooseIcon != null && !this.textFieldItemName.m() && !this.textFieldDisplayName.m()) {
/*     */       
/* 393 */       this.listBoxChooseIcon.left();
/*     */     }
/* 395 */     else if (keyCode == 15) {
/*     */       
/* 397 */       selectNextField();
/*     */     }
/* 399 */     else if (this.textFieldItemName.m()) {
/*     */       
/* 401 */       this.textFieldItemName.a(keyChar, keyCode);
/*     */       
/* 403 */       if (this.editingObject == null && this.enableDisplayName)
/*     */       {
/* 405 */         this.textFieldDisplayName.a(this.textFieldItemName.b());
/*     */       }
/*     */     }
/* 408 */     else if (this.enableDisplayName && this.textFieldDisplayName.m()) {
/*     */       
/* 410 */       this.textFieldDisplayName.a(keyChar, keyCode);
/*     */     }
/* 412 */     else if (this.textFieldXCoord != null && (keyCode == 14 || "-0123456789".indexOf(keyChar) >= 0)) {
/*     */       
/* 414 */       if (this.textFieldXCoord.m()) {
/* 415 */         this.textFieldXCoord.a(keyChar, keyCode);
/* 416 */       } else if (this.textFieldYCoord.m()) {
/* 417 */         this.textFieldYCoord.a(keyChar, keyCode);
/* 418 */       } else if (this.textFieldZCoord.m()) {
/* 419 */         this.textFieldZCoord.a(keyChar, keyCode);
/*     */       } 
/* 421 */     } else if (keyCode == 203) {
/*     */       
/* 423 */       if (this.listBoxChooseIcon != null) {
/* 424 */         this.listBoxChooseIcon.left();
/*     */       }
/* 426 */     } else if (keyCode == 205) {
/*     */       
/* 428 */       if (this.listBoxChooseIcon != null) {
/* 429 */         this.listBoxChooseIcon.right();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void selectNextField() {
/* 435 */     if (this.textFieldXCoord == null) {
/*     */       
/* 437 */       if (this.enableDisplayName)
/*     */       {
/* 439 */         if (this.textFieldItemName.m())
/*     */         {
/* 441 */           this.textFieldItemName.b(false); this.textFieldDisplayName.b(true);
/*     */         }
/*     */         else
/*     */         {
/* 445 */           this.textFieldItemName.b(true); this.textFieldDisplayName.b(false);
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     }
/* 451 */     else if (this.textFieldItemName.m()) {
/*     */       
/* 453 */       this.textFieldItemName.b(false); this.textFieldXCoord.b(true); this.textFieldYCoord.b(false); this.textFieldZCoord.b(false);
/*     */     }
/* 455 */     else if (this.textFieldXCoord.m()) {
/*     */       
/* 457 */       this.textFieldItemName.b(false); this.textFieldXCoord.b(false); this.textFieldYCoord.b(true); this.textFieldZCoord.b(false);
/*     */     }
/* 459 */     else if (this.textFieldYCoord.m()) {
/*     */       
/* 461 */       this.textFieldItemName.b(false); this.textFieldXCoord.b(false); this.textFieldYCoord.b(false); this.textFieldZCoord.b(true);
/*     */     }
/*     */     else {
/*     */       
/* 465 */       this.textFieldItemName.b(true); this.textFieldXCoord.b(false); this.textFieldYCoord.b(false); this.textFieldZCoord.b(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getXCoordText() {
/* 472 */     return this.textFieldXCoord.b();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getYCoordText() {
/* 477 */     return this.textFieldYCoord.b();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getZCoordText() {
/* 482 */     return this.textFieldZCoord.b();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\screens\GuiEditListEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */