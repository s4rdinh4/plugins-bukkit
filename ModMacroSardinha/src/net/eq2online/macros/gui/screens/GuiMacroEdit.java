/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bty;
/*     */ import bug;
/*     */ import bxf;
/*     */ import byj;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.GuiControl;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.core.MacroPlaybackType;
/*     */ import net.eq2online.macros.core.MacroTemplate;
/*     */ import net.eq2online.macros.core.MacroType;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.controls.GuiButtonTab;
/*     */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*     */ import net.eq2online.macros.gui.controls.GuiColourCodeSelector;
/*     */ import net.eq2online.macros.gui.controls.GuiTextFieldEx;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiTextFieldWithHighlight;
/*     */ import net.eq2online.macros.gui.shared.GuiControlEx;
/*     */ import net.eq2online.macros.input.InputHandler;
/*     */ import net.eq2online.macros.interfaces.IHighlighter;
/*     */ import net.eq2online.macros.scripting.api.IMacroEvent;
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
/*     */ public class GuiMacroEdit
/*     */   extends GuiScreenWithHeader
/*     */ {
/*  45 */   protected int key = -1;
/*     */ 
/*     */   
/*     */   protected boolean showOptions;
/*     */ 
/*     */   
/*     */   protected boolean simpleGui;
/*     */ 
/*     */   
/*     */   protected GuiCheckBox chkControl;
/*     */ 
/*     */   
/*     */   protected GuiCheckBox chkAlt;
/*     */ 
/*     */   
/*     */   protected GuiCheckBox chkShift;
/*     */ 
/*     */   
/*     */   protected GuiCheckBox chkInhibit;
/*     */ 
/*     */   
/*     */   protected GuiCheckBox chkGlobal;
/*     */ 
/*     */   
/*     */   protected GuiCheckBox chkAlways;
/*     */ 
/*     */   
/*     */   protected boolean displayColourCodeHelper;
/*     */   
/*     */   protected GuiColourCodeSelector colourCodeHelper;
/*     */   
/*  76 */   protected char colourCodeKeyChar = Character.MIN_VALUE;
/*     */   
/*     */   public boolean requireControl;
/*     */   
/*     */   public boolean requireAlt;
/*     */   
/*     */   public boolean requireShift;
/*     */   
/*     */   public boolean inhibitParamLoad;
/*     */   public boolean global;
/*     */   public boolean alwaysOverride;
/*     */   protected GuiControl btnEditFile;
/*     */   protected GuiControl btnMode;
/*     */   protected GuiControl btnCancel;
/*     */   protected GuiControl btnSave;
/*     */   protected GuiControl btnSwap;
/*     */   protected GuiControl btnCommands;
/*     */   protected GuiButtonTab btnTabNormal;
/*     */   protected GuiButtonTab btnTabKeystate;
/*     */   protected GuiButtonTab btnTabConditional;
/*  96 */   protected MacroPlaybackType playbackType = MacroPlaybackType.OneShot;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   protected MacroType macroType = MacroType.Key;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   protected int eventId = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   protected GuiTextFieldEx txtKeyDownMacro;
/*     */ 
/*     */ 
/*     */   
/*     */   protected GuiTextFieldEx txtKeyHeldMacro;
/*     */ 
/*     */   
/*     */   protected GuiTextFieldEx txtKeyUpMacro;
/*     */ 
/*     */   
/*     */   protected GuiTextFieldEx txtRepeatDelay;
/*     */ 
/*     */   
/*     */   protected GuiTextFieldEx txtCondition;
/*     */ 
/*     */   
/*     */   protected Macros macros;
/*     */ 
/*     */   
/*     */   protected MacroTemplate template;
/*     */ 
/*     */   
/*     */   protected bxf parentScreen;
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiMacroEdit(int keyCode, bxf parentScreen) {
/* 137 */     super(0, 0);
/*     */     
/* 139 */     this.key = keyCode;
/* 140 */     this.macros = MacroModCore.getMacroManager();
/* 141 */     this.template = this.macros.getMacroTemplate(keyCode);
/* 142 */     this.showOptions = MacroModSettings.defaultToOptions;
/* 143 */     this.simpleGui = MacroModSettings.simpleGui;
/* 144 */     this.playbackType = this.template.getPlaybackType();
/* 145 */     this.macroType = this.template.getMacroType();
/* 146 */     this.requireControl = this.template.requireControl;
/* 147 */     this.requireAlt = this.template.requireAlt;
/* 148 */     this.requireShift = this.template.requireShift;
/* 149 */     this.inhibitParamLoad = this.template.inhibitParamLoad;
/* 150 */     this.global = this.template.global;
/* 151 */     this.alwaysOverride = this.template.alwaysOverride;
/* 152 */     this.screenCentreBanner = false;
/*     */     
/* 154 */     bty fontRenderer = AbstractionLayer.getFontRenderer();
/*     */     
/* 156 */     if (MacroModSettings.enableHighlightTextFields) {
/*     */       
/* 158 */       this.txtKeyDownMacro = (GuiTextFieldEx)new GuiTextFieldWithHighlight(0, fontRenderer, 0, 0, 1000, 20, this.template.getKeyDownMacro());
/* 159 */       this.txtKeyHeldMacro = (GuiTextFieldEx)new GuiTextFieldWithHighlight(1, fontRenderer, 0, 0, 1000, 20, this.template.getKeyHeldMacro());
/* 160 */       this.txtRepeatDelay = (GuiTextFieldEx)new GuiTextFieldWithHighlight(2, fontRenderer, 0, 0, 1000, 20, this.template.repeatRate, 4);
/* 161 */       this.txtKeyUpMacro = (GuiTextFieldEx)new GuiTextFieldWithHighlight(3, fontRenderer, 0, 0, 1000, 20, this.template.getKeyUpMacro());
/* 162 */       this.txtCondition = (GuiTextFieldEx)new GuiTextFieldWithHighlight(4, fontRenderer, 0, 0, 1000, 20, this.template.getMacroCondition());
/*     */     }
/*     */     else {
/*     */       
/* 166 */       this.txtKeyDownMacro = new GuiTextFieldEx(0, fontRenderer, 0, 0, 1000, 20, this.template.getKeyDownMacro());
/* 167 */       this.txtKeyHeldMacro = new GuiTextFieldEx(1, fontRenderer, 0, 0, 1000, 20, this.template.getKeyHeldMacro());
/* 168 */       this.txtRepeatDelay = new GuiTextFieldEx(2, fontRenderer, 0, 0, 1000, 20, this.template.repeatRate, 4);
/* 169 */       this.txtKeyUpMacro = new GuiTextFieldEx(3, fontRenderer, 0, 0, 1000, 20, this.template.getKeyUpMacro());
/* 170 */       this.txtCondition = new GuiTextFieldEx(4, fontRenderer, 0, 0, 1000, 20, this.template.getMacroCondition());
/*     */     } 
/*     */     
/* 173 */     this.parentScreen = parentScreen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/* 183 */     Keyboard.enableRepeatEvents(true);
/*     */     
/* 185 */     List<GuiControl> controlList = getControlList();
/* 186 */     controlList.clear();
/*     */     
/* 188 */     this.colourCodeHelper = new GuiColourCodeSelector(46, 46, 200, 200, 2);
/*     */     
/* 190 */     this.btnEditFile = new GuiControl(3, this.l - 82, 63, 78, 20, LocalisationProvider.getLocalisedString("macro.action.editfile"), 5);
/* 191 */     this.btnCancel = new GuiControl(1, this.l - 82, this.m - 46, 78, 20, LocalisationProvider.getLocalisedString("gui.cancel"), 1);
/* 192 */     this.btnSave = new GuiControl(0, this.l - 82, this.m - 24, 78, 20, LocalisationProvider.getLocalisedString("gui.save"), 7);
/* 193 */     this.btnMode = new GuiControl(2, this.l - 82, 89, 78, 20, LocalisationProvider.getLocalisedString("macro.option.title"), 4);
/* 194 */     this.btnCommands = new GuiControl(51, this.l - 82, 111, 78, 20, LocalisationProvider.getLocalisedString("macro.option.cmdref"), 6);
/* 195 */     this.btnSwap = new GuiControl(50, this.l - 110, this.m - 34, 20, 20, "", 10);
/*     */     
/* 197 */     if (this.simpleGui) {
/*     */ 
/*     */       
/* 200 */       this.chkAlways = new GuiCheckBox(9, this.l - 82, this.m - 98, 78, 14, LocalisationProvider.getLocalisedString("macro.option.always.simple"), this.alwaysOverride, GuiCheckBox.DisplayStyle.LayoutButton);
/* 201 */       this.chkGlobal = new GuiCheckBox(8, this.l - 82, this.m - 82, 78, 14, LocalisationProvider.getLocalisedString("macro.option.global.simple"), this.global, GuiCheckBox.DisplayStyle.LayoutButton);
/* 202 */       this.chkInhibit = new GuiCheckBox(7, this.l - 82, this.m - 66, 78, 14, LocalisationProvider.getLocalisedString("macro.option.inhibit.simple"), this.inhibitParamLoad, GuiCheckBox.DisplayStyle.LayoutButton);
/* 203 */       this.chkControl = new GuiCheckBox(4, this.l - 82, this.m - 50, 78, 14, LocalisationProvider.getLocalisedString("macro.keyname.control"), this.requireControl, GuiCheckBox.DisplayStyle.LayoutButton);
/* 204 */       this.chkAlt = new GuiCheckBox(5, this.l - 82, this.m - 34, 78, 14, LocalisationProvider.getLocalisedString("macro.keyname.alt"), this.requireAlt, GuiCheckBox.DisplayStyle.LayoutButton);
/* 205 */       this.chkShift = new GuiCheckBox(6, this.l - 82, this.m - 18, 78, 14, LocalisationProvider.getLocalisedString("macro.keyname.shift"), this.requireShift, GuiCheckBox.DisplayStyle.LayoutButton);
/*     */       
/* 207 */       this.btnTabNormal = new GuiButtonTab(100, 2, 22, 100, 16, LocalisationProvider.getLocalisedString("macro.mode.normal"));
/* 208 */       this.btnTabKeystate = new GuiButtonTab(101, 104, 22, 100, 16, LocalisationProvider.getLocalisedString("macro.mode.keystate"));
/* 209 */       this.btnTabConditional = new GuiButtonTab(102, 206, 22, 100, 16, LocalisationProvider.getLocalisedString("macro.mode.conditional"));
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 214 */       this.chkControl = new GuiCheckBox(4, 14, this.m - 165, LocalisationProvider.getLocalisedString("macro.keyname.control"), this.requireControl);
/* 215 */       this.chkAlt = new GuiCheckBox(5, 94, this.m - 165, LocalisationProvider.getLocalisedString("macro.keyname.alt"), this.requireAlt);
/* 216 */       this.chkShift = new GuiCheckBox(6, 174, this.m - 165, LocalisationProvider.getLocalisedString("macro.keyname.shift"), this.requireShift);
/* 217 */       this.chkGlobal = new GuiCheckBox(8, 14, this.m - 133, LocalisationProvider.getLocalisedString("macro.option.global"), this.global);
/* 218 */       this.chkInhibit = new GuiCheckBox(7, 14, this.m - 68, LocalisationProvider.getLocalisedString("macro.option.inhibit"), this.inhibitParamLoad);
/* 219 */       this.chkAlways = new GuiCheckBox(9, 14, this.m - 101, LocalisationProvider.getLocalisedString("macro.option.always"), this.alwaysOverride);
/*     */       
/* 221 */       this.btnTabNormal = new GuiButtonTab(100, 2, 22, 100, 20, LocalisationProvider.getLocalisedString("macro.mode.normal"));
/* 222 */       this.btnTabKeystate = new GuiButtonTab(101, 104, 22, 100, 20, LocalisationProvider.getLocalisedString("macro.mode.keystate"));
/* 223 */       this.btnTabConditional = new GuiButtonTab(102, 206, 22, 100, 20, LocalisationProvider.getLocalisedString("macro.mode.conditional"));
/*     */       
/* 225 */       addControl(this.btnEditFile);
/* 226 */       addControl(this.btnCancel);
/* 227 */       addControl(this.btnSave);
/* 228 */       addControl(this.btnMode);
/* 229 */       addControl(this.btnCommands);
/*     */     } 
/*     */     
/* 232 */     addControl(this.btnSwap);
/*     */     
/* 234 */     addControl((GuiControl)this.btnTabNormal);
/* 235 */     addControl((GuiControl)this.btnTabKeystate);
/* 236 */     addControl((GuiControl)this.btnTabConditional);
/*     */     
/* 238 */     addControl((GuiControl)this.chkInhibit);
/* 239 */     addControl((GuiControl)this.chkGlobal);
/* 240 */     addControl((GuiControl)this.chkControl);
/* 241 */     addControl((GuiControl)this.chkAlt);
/* 242 */     addControl((GuiControl)this.chkShift);
/* 243 */     addControl((GuiControl)this.chkAlways);
/*     */     
/* 245 */     this.txtKeyDownMacro.setSize(this.l - 156, 16);
/* 246 */     this.txtKeyHeldMacro.setSize(this.l - 226 - this.q.a(LocalisationProvider.getLocalisedString("macro.prompt.repeatrate")), 16);
/* 247 */     this.txtRepeatDelay.setSize(60, 16);
/* 248 */     this.txtKeyUpMacro.setSize(this.l - 156, 16);
/* 249 */     this.txtCondition.setSize(this.l - 156, 16);
/*     */     
/* 251 */     this.txtKeyDownMacro.scrollToEnd();
/* 252 */     this.txtKeyHeldMacro.scrollToEnd();
/* 253 */     this.txtKeyUpMacro.scrollToEnd();
/* 254 */     this.txtCondition.scrollToEnd();
/*     */     
/* 256 */     this.txtKeyDownMacro.b(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void m() {
/* 265 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/* 274 */     this.updateCounter++;
/*     */     
/* 276 */     this.txtKeyDownMacro.a();
/* 277 */     this.txtKeyHeldMacro.a();
/* 278 */     this.txtRepeatDelay.a();
/* 279 */     this.txtKeyUpMacro.a();
/* 280 */     this.txtCondition.a();
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
/* 291 */     if (guibutton != null) {
/*     */       
/* 293 */       if (guibutton.k == 0)
/*     */       {
/* 295 */         save();
/*     */       }
/*     */       
/* 298 */       if (guibutton.k == 0 || guibutton.k == 1) {
/*     */         
/* 300 */         if (this.parentScreen == null) this.parentScreen = (bxf)new GuiMacroBind(false); 
/* 301 */         AbstractionLayer.displayGuiScreen(this.parentScreen);
/*     */         
/*     */         return;
/*     */       } 
/* 305 */       if (guibutton.k == 2)
/*     */       {
/* 307 */         this.showOptions = !this.showOptions;
/*     */       }
/*     */       
/* 310 */       if (guibutton.k == 3) {
/*     */         
/* 312 */         String suggest = null;
/* 313 */         Matcher filePattern = Pattern.compile("<([A-Za-z0-9\\x20_\\-\\.]+\\.txt)>").matcher(this.txtKeyDownMacro.b());
/*     */         
/* 315 */         if (filePattern.find())
/*     */         {
/* 317 */           suggest = filePattern.group(1);
/*     */         }
/*     */         
/* 320 */         AbstractionLayer.displayGuiScreen((bxf)new GuiEditTextFile(this, suggest, ScriptContext.MAIN));
/*     */         
/*     */         return;
/*     */       } 
/* 324 */       if (this.macroType == MacroType.Key) {
/*     */         
/* 326 */         if (guibutton.k == 4) this.requireControl = ((GuiCheckBox)guibutton).checked; 
/* 327 */         if (guibutton.k == 5) this.requireAlt = ((GuiCheckBox)guibutton).checked; 
/* 328 */         if (guibutton.k == 6) this.requireShift = ((GuiCheckBox)guibutton).checked; 
/* 329 */         if (guibutton.k == 9) this.alwaysOverride = ((GuiCheckBox)guibutton).checked;
/*     */       
/*     */       } 
/* 332 */       if (this.macroType == MacroType.Key || this.macroType == MacroType.Control)
/*     */       {
/* 334 */         if (guibutton.k == 7) this.inhibitParamLoad = ((GuiCheckBox)guibutton).checked;
/*     */       
/*     */       }
/* 337 */       if (guibutton.k == 8) this.global = ((GuiCheckBox)guibutton).checked;
/*     */       
/* 339 */       if (guibutton.k == 100) this.playbackType = MacroPlaybackType.OneShot; 
/* 340 */       if (guibutton.k == 101) this.playbackType = MacroPlaybackType.KeyState; 
/* 341 */       if (guibutton.k == 102) this.playbackType = MacroPlaybackType.Conditional;
/*     */       
/* 343 */       if (guibutton.k > 99 && guibutton.k < 103) {
/*     */         
/* 345 */         this.txtKeyDownMacro.b(true);
/* 346 */         this.txtKeyHeldMacro.b(false);
/* 347 */         this.txtRepeatDelay.b(false);
/* 348 */         this.txtKeyUpMacro.b(false);
/* 349 */         this.txtCondition.b(false);
/*     */       } 
/*     */       
/* 352 */       if (guibutton.k == 50 && this.playbackType == MacroPlaybackType.Conditional) {
/*     */         
/* 354 */         String trueText = this.txtKeyDownMacro.b();
/* 355 */         String falseText = this.txtKeyUpMacro.b();
/*     */         
/* 357 */         this.txtKeyDownMacro.a(falseText);
/* 358 */         this.txtKeyUpMacro.a(trueText);
/*     */         
/* 360 */         this.txtKeyDownMacro.scrollToEnd();
/* 361 */         this.txtKeyUpMacro.scrollToEnd();
/*     */       } 
/*     */       
/* 364 */       if (guibutton.k == 51)
/*     */       {
/* 366 */         AbstractionLayer.displayGuiScreen((bxf)new GuiCommandReference((bxf)this));
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
/*     */   public void l() throws IOException {
/* 379 */     if (Keyboard.getEventKey() == MacroModSettings.getColourCodeHelperKey() && this.colourCodeHelper != null && !this.txtRepeatDelay.m()) {
/*     */       
/* 381 */       if (Keyboard.getEventKeyState()) {
/*     */         
/* 383 */         this.displayColourCodeHelper = true;
/* 384 */         this.colourCodeHelper.colour = -1;
/* 385 */         this.colourCodeKeyChar = Keyboard.getEventCharacter();
/*     */       }
/*     */       else {
/*     */         
/* 389 */         this.displayColourCodeHelper = false;
/*     */         
/* 391 */         if (this.colourCodeHelper.colour > -1)
/*     */         {
/* 393 */           String appendCode = String.format(MacroModSettings.colourCodeFormat, new Object[] { Character.valueOf("0123456789abcdef".charAt(this.colourCodeHelper.colour)) });
/*     */           
/* 395 */           this.txtKeyDownMacro.insertText(appendCode);
/* 396 */           this.txtKeyHeldMacro.insertText(appendCode);
/* 397 */           this.txtKeyUpMacro.insertText(appendCode);
/*     */         }
/*     */         else
/*     */         {
/* 401 */           a(this.colourCodeKeyChar, Keyboard.getEventKey());
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 407 */       super.l();
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
/*     */   protected void a(char keyChar, int keyCode) {
/* 419 */     if (this.macros.getKeyboardLayout().keyPressed(keyChar, keyCode))
/*     */       return; 
/* 421 */     if (keyCode == 1) {
/*     */       
/* 423 */       if (this.parentScreen == null) this.parentScreen = MacroModSettings.alwaysGoBack ? (bxf)new GuiMacroBind(false) : ((AbstractionLayer.getWorld() != null) ? null : (bxf)new byj(null, this.j.t)); 
/* 424 */       AbstractionLayer.displayGuiScreen(this.parentScreen);
/*     */       
/*     */       return;
/*     */     } 
/* 428 */     if (keyCode == 59) {
/*     */       
/* 430 */       AbstractionLayer.displayGuiScreen((bxf)new GuiCommandReference((bxf)this));
/*     */       
/*     */       return;
/*     */     } 
/* 434 */     if (this.displayColourCodeHelper) {
/*     */       
/* 436 */       this.colourCodeHelper.keyTyped(keyChar, keyCode);
/*     */       
/*     */       return;
/*     */     } 
/* 440 */     if (keyCode == 28 || keyCode == 156) {
/*     */ 
/*     */       
/* 443 */       save();
/* 444 */       if (this.parentScreen == null) this.parentScreen = MacroModSettings.alwaysGoBack ? (bxf)new GuiMacroBind(false) : ((AbstractionLayer.getWorld() != null) ? null : (bxf)new byj(null, this.j.t)); 
/* 445 */       AbstractionLayer.displayGuiScreen(this.parentScreen);
/*     */       
/*     */       return;
/*     */     } 
/* 449 */     this.txtKeyDownMacro.a(keyChar, keyCode);
/* 450 */     this.txtKeyHeldMacro.a(keyChar, keyCode);
/* 451 */     this.txtRepeatDelay.a(keyChar, keyCode);
/* 452 */     this.txtKeyUpMacro.a(keyChar, keyCode);
/* 453 */     this.txtCondition.a(keyChar, keyCode);
/*     */     
/* 455 */     if (keyCode == 15) {
/*     */       
/* 457 */       boolean sh = (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
/*     */       
/* 459 */       if (this.playbackType == MacroPlaybackType.KeyState) {
/*     */         
/* 461 */         if (this.txtKeyDownMacro.m()) { setTextBoxFocusedState(sh ? 3 : 1); }
/* 462 */         else if (this.txtKeyHeldMacro.m()) { setTextBoxFocusedState(sh ? 0 : 2); }
/* 463 */         else if (this.txtRepeatDelay.m()) { setTextBoxFocusedState(sh ? 1 : 3); }
/* 464 */         else if (this.txtKeyUpMacro.m()) { setTextBoxFocusedState(sh ? 2 : 0); }
/*     */       
/* 466 */       } else if (this.playbackType == MacroPlaybackType.Conditional) {
/*     */         
/* 468 */         if (this.txtCondition.m()) { setTextBoxFocusedState(sh ? 3 : 0); }
/* 469 */         else if (this.txtKeyDownMacro.m()) { setTextBoxFocusedState(sh ? 4 : 3); }
/* 470 */         else if (this.txtKeyUpMacro.m()) { setTextBoxFocusedState(sh ? 0 : 4); }
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setTextBoxFocusedState(int textBoxIndex) {
/* 477 */     this.txtKeyDownMacro.b((textBoxIndex == 0));
/* 478 */     this.txtKeyHeldMacro.b((textBoxIndex == 1));
/* 479 */     this.txtRepeatDelay.b((textBoxIndex == 2));
/* 480 */     this.txtKeyUpMacro.b((textBoxIndex == 3));
/* 481 */     this.txtCondition.b((textBoxIndex == 4));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void save() {
/* 489 */     this.macros.updateMacroTemplate(this.key, this.template, this.playbackType, this.txtRepeatDelay.b(), this.txtKeyDownMacro.b(), this.txtKeyHeldMacro.b(), this.txtKeyUpMacro.b(), this.txtCondition.b(), this.requireControl, this.requireAlt, this.requireShift, this.inhibitParamLoad, this.global, this.alwaysOverride);
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
/* 502 */     if (AbstractionLayer.getWorld() == null)
/*     */     {
/* 504 */       c();
/*     */     }
/*     */     
/* 507 */     int foreColour = -22016;
/* 508 */     int backColour = -1342177280;
/* 509 */     int backColour2 = -1607257293;
/* 510 */     this.screenBannerColour = 4259648;
/* 511 */     this.screenBackgroundSpaceBottom = 16;
/* 512 */     this.screenDrawBackground = false;
/*     */     
/* 514 */     int helperPosition = 0;
/* 515 */     int textBoxPosition = getTextBoxPosition();
/* 516 */     this.btnTabKeystate.l = (this.macroType == MacroType.Key);
/*     */     
/* 518 */     drawBackground(textBoxPosition, foreColour, backColour, backColour2);
/*     */     
/* 520 */     int top = textBoxPosition - 152;
/*     */     
/* 522 */     if (this.playbackType == MacroPlaybackType.OneShot) {
/*     */       
/* 524 */       helperPosition = drawOneShotInterface(textBoxPosition);
/*     */     }
/* 526 */     else if (this.playbackType == MacroPlaybackType.KeyState) {
/*     */       
/* 528 */       if (this.macroType == MacroType.Event)
/*     */       {
/*     */         
/* 531 */         this.playbackType = MacroPlaybackType.OneShot;
/*     */       }
/*     */       else
/*     */       {
/* 535 */         top += 40;
/* 536 */         helperPosition = drawKeyStateInterface(textBoxPosition);
/*     */       }
/*     */     
/* 539 */     } else if (this.playbackType == MacroPlaybackType.Conditional) {
/*     */       
/* 541 */       top += 50;
/* 542 */       helperPosition = drawConditionalInterface(textBoxPosition);
/*     */     } 
/*     */     
/* 545 */     drawPrompt(textBoxPosition);
/* 546 */     drawHelp(6, this.m - 176, foreColour);
/* 547 */     drawLabels(6, top, foreColour);
/* 548 */     setupControls(18, top + 9);
/*     */     
/* 550 */     super.a(mouseX, mouseY, f);
/*     */ 
/*     */     
/* 553 */     drawColourCodeHelper(helperPosition);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawLabels(int left, int top, int foreColour) {
/* 561 */     if (this.showOptions && !MacroModSettings.simpleGui) {
/*     */       
/* 563 */       c(this.q, LocalisationProvider.getLocalisedString("macro.help.line12"), left, top, foreColour);
/*     */       
/* 565 */       if (this.playbackType != MacroPlaybackType.Conditional) {
/*     */         
/* 567 */         c(this.q, LocalisationProvider.getLocalisedString("macro.help.global"), left, top + 32, foreColour);
/* 568 */         c(this.q, LocalisationProvider.getLocalisedString("macro.help.always"), left, top + 64, foreColour);
/*     */         
/* 570 */         if (this.playbackType != MacroPlaybackType.KeyState)
/*     */         {
/* 572 */           c(this.q, LocalisationProvider.getLocalisedString("macro.help.inhibit"), left, top + 96, foreColour);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setupControls(int left, int top) {
/* 582 */     boolean showCheckBoxes = (this.simpleGui || this.showOptions);
/*     */     
/* 584 */     this.chkGlobal.checked = this.global;
/*     */     
/* 586 */     if (!this.simpleGui) {
/*     */       
/* 588 */       int spacing = (this.playbackType == MacroPlaybackType.Conditional) ? 16 : 32;
/* 589 */       int spc2 = (this.playbackType == MacroPlaybackType.Conditional) ? 4 : 0;
/*     */       
/* 591 */       this.chkControl.setPosition(left, top);
/* 592 */       this.chkAlt.setPosition(left + 80, top);
/* 593 */       this.chkShift.setPosition(left + 160, top); top += spacing + spc2;
/* 594 */       this.chkGlobal.setPosition(left, top); top += spacing;
/* 595 */       this.chkAlways.setPosition(left, top); top += spacing;
/* 596 */       this.chkInhibit.setPosition(left, top);
/*     */     } 
/*     */ 
/*     */     
/* 600 */     this.chkInhibit.setVisible((this.playbackType != MacroPlaybackType.KeyState && showCheckBoxes));
/* 601 */     this.chkAlways.setVisible(showCheckBoxes);
/* 602 */     this.chkGlobal.setVisible(showCheckBoxes);
/* 603 */     this.chkControl.setVisible(showCheckBoxes);
/* 604 */     this.chkAlt.setVisible(showCheckBoxes);
/* 605 */     this.chkShift.setVisible(showCheckBoxes);
/* 606 */     this.btnSwap.setVisible((this.playbackType == MacroPlaybackType.Conditional));
/*     */ 
/*     */     
/* 609 */     this.chkInhibit.setEnabled((this.macroType == MacroType.Key || this.macroType == MacroType.Control));
/* 610 */     this.chkAlways.setEnabled((this.macroType == MacroType.Key && !InputHandler.fallbackMode));
/* 611 */     this.chkControl.setEnabled((this.macroType == MacroType.Key));
/* 612 */     this.chkAlt.setEnabled((this.macroType == MacroType.Key));
/* 613 */     this.chkShift.setEnabled((this.macroType == MacroType.Key));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getTextBoxPosition() {
/* 622 */     if (this.playbackType == MacroPlaybackType.KeyState) return this.m - 62; 
/* 623 */     if (this.playbackType == MacroPlaybackType.Conditional) return this.m - 72;
/*     */     
/* 625 */     return this.m - 22;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawColourCodeHelper(int helperPosition) {
/* 633 */     if (this.displayColourCodeHelper) {
/*     */       
/* 635 */       int top = (this.playbackType == MacroPlaybackType.KeyState) ? 60 : 20;
/* 636 */       if (this.txtKeyHeldMacro.m()) top = 40; 
/* 637 */       if (this.txtKeyUpMacro.m()) top = 20;
/*     */       
/* 639 */       this.colourCodeHelper.yPosition = this.m - top;
/* 640 */       this.colourCodeHelper.xPosition = Math.min(this.l - this.colourCodeHelper.width - 4, helperPosition + 4);
/* 641 */       this.colourCodeHelper.drawColourCodeSelector();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawPrompt(int textBoxPosition) {
/* 650 */     c(this.q, LocalisationProvider.getLocalisedString("macro.prompt.bind", new Object[] { getBindingName() }), 6, textBoxPosition - 12, 15658496);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int drawOneShotInterface(int textBoxPosition) {
/* 659 */     this.btnTabNormal.active = true;
/* 660 */     this.btnTabKeystate.active = false;
/* 661 */     this.btnTabConditional.active = false;
/*     */     
/* 663 */     this.txtKeyDownMacro.b(true);
/* 664 */     this.txtKeyHeldMacro.b(false);
/* 665 */     this.txtRepeatDelay.b(false);
/* 666 */     this.txtKeyUpMacro.b(false);
/*     */ 
/*     */     
/* 669 */     this.txtKeyDownMacro.drawTextBox(6, textBoxPosition, this.l - 96, 16, (IHighlighter)this.playbackType);
/* 670 */     return this.txtKeyDownMacro.getCursorLocation();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int drawKeyStateInterface(int textBoxPosition) {
/* 680 */     int helperPosition = 0;
/*     */     
/* 682 */     this.btnTabNormal.active = false;
/* 683 */     this.btnTabKeystate.active = true;
/* 684 */     this.btnTabConditional.active = false;
/*     */     
/* 686 */     int labelWidth = this.q.a(LocalisationProvider.getLocalisedString("macro.prompt.repeatrate"));
/*     */ 
/*     */     
/* 689 */     c(this.q, LocalisationProvider.getLocalisedString("macro.prompt.keydown"), 6, textBoxPosition + 4, 15658496);
/* 690 */     this.txtKeyDownMacro.drawTextBox(66, textBoxPosition, this.l - 156, 16, (IHighlighter)this.playbackType);
/* 691 */     if (this.txtKeyDownMacro.m()) helperPosition = this.txtKeyDownMacro.getCursorLocation();
/*     */ 
/*     */     
/* 694 */     c(this.q, LocalisationProvider.getLocalisedString("macro.prompt.keyheld"), 6, textBoxPosition + 24, 15658496);
/* 695 */     this.txtKeyHeldMacro.drawTextBox(66, textBoxPosition + 20, this.l - 226 - labelWidth, 16, (IHighlighter)this.playbackType);
/* 696 */     if (this.txtKeyHeldMacro.m()) helperPosition = this.txtKeyHeldMacro.getCursorLocation();
/*     */ 
/*     */     
/* 699 */     c(this.q, LocalisationProvider.getLocalisedString("macro.prompt.repeatrate"), this.l - 153 - labelWidth, textBoxPosition + 24, 15658496);
/* 700 */     this.txtRepeatDelay.drawTextBox(this.l - 150, textBoxPosition + 20, 60, 16, (IHighlighter)this.playbackType);
/*     */ 
/*     */     
/* 703 */     c(this.q, LocalisationProvider.getLocalisedString("macro.prompt.keyup"), 6, textBoxPosition + 44, 15658496);
/* 704 */     this.txtKeyUpMacro.drawTextBox(66, textBoxPosition + 40, this.l - 156, 16, (IHighlighter)this.playbackType);
/* 705 */     if (this.txtKeyUpMacro.m()) helperPosition = this.txtKeyUpMacro.getCursorLocation(); 
/* 706 */     return helperPosition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int drawConditionalInterface(int textBoxPosition) {
/* 716 */     int helperPosition = 0;
/*     */     
/* 718 */     this.btnTabNormal.active = false;
/* 719 */     this.btnTabKeystate.active = false;
/* 720 */     this.btnTabConditional.active = true;
/*     */ 
/*     */     
/* 723 */     c(this.q, LocalisationProvider.getLocalisedString("macro.prompt.condition"), 6, textBoxPosition + 4, 15658496);
/* 724 */     this.txtCondition.drawTextBox(86, textBoxPosition, this.l - 276, 16, (IHighlighter)this.playbackType);
/* 725 */     if (this.txtCondition.m()) helperPosition = this.txtCondition.getCursorLocation();
/*     */ 
/*     */     
/* 728 */     c(this.q, LocalisationProvider.getLocalisedString("macro.prompt.condtrue"), 36, textBoxPosition + 34, 15658496);
/* 729 */     this.txtKeyDownMacro.drawTextBox(86, textBoxPosition + 30, this.l - 200, 16, (IHighlighter)this.playbackType);
/* 730 */     if (this.txtKeyDownMacro.m()) helperPosition = this.txtKeyDownMacro.getCursorLocation();
/*     */ 
/*     */     
/* 733 */     c(this.q, LocalisationProvider.getLocalisedString("macro.prompt.condfalse"), 36, textBoxPosition + 54, 15658496);
/* 734 */     this.txtKeyUpMacro.drawTextBox(86, textBoxPosition + 50, this.l - 200, 16, (IHighlighter)this.playbackType);
/* 735 */     if (this.txtKeyUpMacro.m()) helperPosition = this.txtKeyUpMacro.getCursorLocation();
/*     */     
/* 737 */     GuiControlEx.drawLine(this.l - 186, textBoxPosition + 8, this.l - 166, textBoxPosition + 8, 1, -22016);
/* 738 */     GuiControlEx.drawLine(this.l - 166, textBoxPosition + 8, this.l - 166, textBoxPosition + 23, 1, -22016);
/* 739 */     GuiControlEx.drawLine(15, textBoxPosition + 23, this.l - 166, textBoxPosition + 23, 1, -22016);
/* 740 */     GuiControlEx.drawLine(15, textBoxPosition + 23, 15, textBoxPosition + 59, 1, -22016);
/* 741 */     GuiControlEx.drawArrow(15, textBoxPosition + 38, 32, textBoxPosition + 38, 0, 1, 5, -22016);
/* 742 */     GuiControlEx.drawArrow(15, textBoxPosition + 58, 32, textBoxPosition + 58, 0, 1, 5, -22016);
/*     */     
/* 744 */     return helperPosition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawBackground(int textBoxPosition, int foreColour, int backColour, int backColour2) {
/* 755 */     if (this.simpleGui) {
/*     */       
/* 757 */       a(this.l - 84, this.m - 100, this.l - 2, this.m - 2, backColour);
/*     */       
/* 759 */       this.btnTabNormal.setYPosition(textBoxPosition - 34);
/* 760 */       this.btnTabKeystate.setYPosition(textBoxPosition - 34);
/* 761 */       this.btnTabConditional.setYPosition(textBoxPosition - 34);
/*     */       
/* 763 */       this.screenDrawHeader = false;
/*     */     }
/*     */     else {
/*     */       
/* 767 */       this.screenDrawHeader = true;
/* 768 */       this.screenTitle = LocalisationProvider.getLocalisedString("macro.edit.title");
/* 769 */       this.screenBanner = LocalisationProvider.getLocalisedString("macro.currentconfig", new Object[] { this.global ? ("§e" + LocalisationProvider.getLocalisedString("macro.config.global")) : this.macros.getActiveConfigName() });
/*     */ 
/*     */       
/* 772 */       a(this.l - 84, 44, this.l - 2, 61, backColour2);
/* 773 */       c(this.q, LocalisationProvider.getLocalisedString("macro.action.title"), this.l - 80, 49, 16776960);
/* 774 */       a(this.l - 84, 61, this.l - 2, this.m - 2, backColour);
/*     */ 
/*     */       
/* 777 */       a(2, 44, this.l - 86, 61, backColour2);
/* 778 */       c(this.q, LocalisationProvider.getLocalisedString(this.showOptions ? "macro.option.title" : "macro.help.title"), 6, 49, 16776960);
/* 779 */       a(2, 61, this.l - 86, textBoxPosition - 18, backColour);
/*     */       
/* 781 */       this.btnMode.j = LocalisationProvider.getLocalisedString(this.showOptions ? "macro.help.title" : "macro.option.title");
/* 782 */       this.btnMode.setIconIndex(this.showOptions ? 6 : 4);
/* 783 */       this.btnTabNormal.setYPosition(22);
/* 784 */       this.btnTabKeystate.setYPosition(22);
/* 785 */       this.btnTabConditional.setYPosition(22);
/*     */     } 
/*     */ 
/*     */     
/* 789 */     a(2, textBoxPosition - 16, this.l - 86, this.m - 2, backColour);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getBindingName() {
/* 799 */     String modifiers = "";
/*     */     
/* 801 */     if (this.macroType == MacroType.Key) {
/*     */       
/* 803 */       modifiers = this.requireControl ? "<CTRL> " : "";
/* 804 */       modifiers = modifiers + (this.requireAlt ? "<ALT> " : "");
/* 805 */       modifiers = modifiers + (this.requireShift ? "<SHIFT> " : "");
/*     */     } 
/*     */     
/* 808 */     return modifiers + "<" + this.macroType.getName(this.key) + ">";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawHelp(int left, int top, int foreColour) {
/* 817 */     if (this.simpleGui || this.showOptions)
/*     */       return; 
/* 819 */     this.rowPos = top;
/* 820 */     String prefix = "macro.help.", prefix2 = prefix;
/* 821 */     int start = 1, end = 11;
/*     */     
/* 823 */     int eventId = this.template.getID();
/*     */     
/* 825 */     if (this.playbackType == MacroPlaybackType.OneShot) {
/*     */       
/* 827 */       drawSpacedString(LocalisationProvider.getLocalisedString("macro.help.line1"), left, foreColour);
/* 828 */       drawSpacedString(LocalisationProvider.getLocalisedString("macro.help.line2"), left, foreColour);
/*     */       
/* 830 */       if (this.macroType == MacroType.Event) {
/*     */         
/* 832 */         prefix = prefix + "event.";
/* 833 */         prefix2 = prefix2 + "event." + eventId + ".";
/* 834 */         end = 5;
/*     */       } 
/*     */       
/* 837 */       start = 3;
/*     */     }
/* 839 */     else if (this.playbackType == MacroPlaybackType.KeyState) {
/*     */       
/* 841 */       prefix2 = prefix = "macro.help2.";
/* 842 */       end = 5;
/*     */     }
/* 844 */     else if (this.playbackType == MacroPlaybackType.Conditional) {
/*     */       
/* 846 */       prefix2 = prefix = "macro.help3.";
/* 847 */       end = 7;
/*     */     } 
/*     */     
/* 850 */     for (int line = start; line <= end; line++)
/*     */     {
/* 852 */       drawSpacedString(LocalisationProvider.getLocalisedString(((line > 5) ? prefix2 : prefix) + "line" + line), left, foreColour);
/*     */     }
/*     */     
/* 855 */     if (this.macroType == MacroType.Event && this.playbackType == MacroPlaybackType.OneShot) {
/*     */       
/* 857 */       IMacroEvent event = this.macros.getEventManager().getEvent(eventId);
/* 858 */       if (event != null)
/*     */       {
/* 860 */         for (int i = 0; i < 6; i++)
/*     */         {
/* 862 */           drawSpacedString(event.getHelpLine(eventId, i), left, foreColour);
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
/*     */   protected void mouseClickedEx(int mouseX, int mouseY, int button) {
/* 874 */     if (button == 0) {
/*     */       
/* 876 */       if (this.displayColourCodeHelper && this.colourCodeHelper.mouseClicked(mouseX, mouseY) && this.colourCodeHelper.colour > -1) {
/*     */         
/* 878 */         this.displayColourCodeHelper = false;
/*     */         
/* 880 */         String appendCode = String.format(MacroModSettings.colourCodeFormat, new Object[] { Character.valueOf("0123456789abcdef".charAt(this.colourCodeHelper.colour)) });
/*     */         
/* 882 */         this.txtKeyDownMacro.insertText(appendCode);
/* 883 */         this.txtKeyHeldMacro.insertText(appendCode);
/* 884 */         this.txtKeyUpMacro.insertText(appendCode);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 889 */       this.txtKeyDownMacro.a(mouseX, mouseY, button);
/*     */       
/* 891 */       if (this.playbackType == MacroPlaybackType.KeyState) {
/*     */         
/* 893 */         this.txtKeyHeldMacro.a(mouseX, mouseY, button);
/* 894 */         this.txtRepeatDelay.a(mouseX, mouseY, button);
/* 895 */         this.txtKeyUpMacro.a(mouseX, mouseY, button);
/*     */       }
/* 897 */       else if (this.playbackType == MacroPlaybackType.Conditional) {
/*     */         
/* 899 */         this.txtKeyUpMacro.a(mouseX, mouseY, button);
/* 900 */         this.txtCondition.a(mouseX, mouseY, button);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void openTextEditor(GuiTextFieldEx guiTextFieldMacroEdit) {
/* 907 */     AbstractionLayer.displayGuiScreen((bxf)new GuiEditTextString(this, guiTextFieldMacroEdit.b(), this.macroType.getName(this.key), ScriptContext.MAIN));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onHeaderClick() {
/* 913 */     this.global = !this.global;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\screens\GuiMacroEdit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */