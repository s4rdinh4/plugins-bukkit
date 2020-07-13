/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bsu;
/*     */ import bty;
/*     */ import bug;
/*     */ import com.mumfrey.liteloader.modconfig.ConfigPanel;
/*     */ import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.GuiControl;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.core.SpamFilter;
/*     */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*     */ import net.eq2online.macros.gui.controls.GuiDropDownList;
/*     */ import net.eq2online.macros.gui.controls.GuiTextFieldEx;
/*     */ import net.eq2online.macros.gui.layout.LayoutPanel;
/*     */ import net.eq2online.macros.gui.shared.GuiDialogBox;
/*     */ import net.eq2online.macros.interfaces.annotations.DropdownStyle;
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
/*     */ public class GuiMacroConfigPanel
/*     */   implements ConfigPanel
/*     */ {
/*     */   public class ConfigOption
/*     */   {
/*     */     public int displayHeight;
/*     */     public String displayText;
/*     */     public int displayColour;
/*     */     public String binding;
/*     */     public int xPosition;
/*     */     public GuiCheckBox checkBox;
/*     */     public GuiTextFieldEx textField;
/*     */     public GuiControl button;
/*     */     public GuiDropDownList.GuiDropDownListControl dropDown;
/*     */     public Enum<?> defaultEnumValue;
/*     */     public int defaultIntValue;
/*     */     private int yPosition;
/*     */     private boolean isNumeric;
/*  65 */     private bty innerFontRenderer = AbstractionLayer.getFontRenderer();
/*     */ 
/*     */     
/*     */     public ConfigOption(int height) {
/*  69 */       this.displayHeight = height;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ConfigOption(String text, int xPosition, int height, int displayColour) {
/*  78 */       this.displayText = LocalisationProvider.getLocalisedString(text);
/*  79 */       this.xPosition = xPosition;
/*  80 */       this.displayHeight = height;
/*  81 */       this.displayColour = displayColour;
/*     */     }
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
/*     */     public ConfigOption(int id, int xPosition, int height, String displayText, String binding) {
/*  94 */       Boolean settingValue = GuiMacroConfigPanel.<Boolean>getSetting(binding);
/*     */       
/*  96 */       this.binding = binding;
/*  97 */       this.displayHeight = height;
/*  98 */       this.checkBox = new GuiCheckBox(id, xPosition, 0, LocalisationProvider.getLocalisedString(displayText), settingValue.booleanValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public ConfigOption(int id, int xPosition, int width, int height, int displayHeight, String displayText) {
/* 103 */       this.displayHeight = displayHeight;
/* 104 */       this.button = new GuiControl(id, xPosition, 0, width, height, LocalisationProvider.getLocalisedString(displayText));
/*     */     }
/*     */ 
/*     */     
/*     */     public ConfigOption(int id, bty fontrenderer, int xPosition, int width, int height, int displayHeight, String binding) {
/* 109 */       this.binding = binding;
/* 110 */       this.displayHeight = displayHeight;
/* 111 */       this.textField = new GuiTextFieldEx(id, fontrenderer, xPosition, 0, width, height, GuiMacroConfigPanel.<T>getSetting(binding).toString());
/*     */     }
/*     */ 
/*     */     
/*     */     public ConfigOption(int id, bty fontrenderer, int xPosition, int width, int height, int displayHeight, String binding, int digits) {
/* 116 */       this.defaultIntValue = ((Integer)GuiMacroConfigPanel.<Integer>getSetting(binding)).intValue();
/*     */       
/* 118 */       this.binding = binding;
/* 119 */       this.isNumeric = true;
/* 120 */       this.displayHeight = displayHeight;
/* 121 */       this.textField = new GuiTextFieldEx(id, fontrenderer, xPosition, 0, width, height, this.defaultIntValue, digits);
/*     */     }
/*     */ 
/*     */     
/*     */     public ConfigOption(bsu minecraft, int controlId, int xPosition, int controlWidth, int controlHeight, int itemHeight, int displayHeight, Enum<?> defaultValue, String binding) {
/* 126 */       this.defaultEnumValue = defaultValue;
/*     */       
/* 128 */       this.binding = binding;
/* 129 */       this.displayHeight = displayHeight;
/* 130 */       this.dropDown = new GuiDropDownList.GuiDropDownListControl(minecraft, controlId, xPosition, 0, controlWidth, controlHeight, itemHeight, "");
/*     */ 
/*     */       
/*     */       try {
/* 134 */         Class<? extends Enum<?>> enumClass = (Class)defaultValue.getDeclaringClass();
/*     */         
/* 136 */         Method values = enumClass.getDeclaredMethod("values", new Class[0]);
/* 137 */         Enum[] arrayOfEnum = (Enum[])values.invoke(null, new Object[0]);
/*     */         
/* 139 */         for (Enum<?> enumValue : arrayOfEnum)
/*     */         {
/* 141 */           DropdownStyle dropDownStyle = enumValue.getClass().getField(enumValue.name()).<DropdownStyle>getAnnotation(DropdownStyle.class);
/*     */           
/* 143 */           if (dropDownStyle == null || !dropDownStyle.hideInDropdown())
/*     */           {
/* 145 */             this.dropDown.addItem(enumValue.name(), (dropDownStyle != null) ? dropDownStyle.dropDownText() : enumValue.toString());
/*     */           }
/*     */         }
/*     */       
/* 149 */       } catch (Exception ex) {
/*     */         
/* 151 */         ex.printStackTrace();
/*     */       } 
/*     */       
/* 154 */       this.dropDown.selectItemByTag(GuiMacroConfigPanel.<T>getSetting(this.binding).toString());
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateCursorCounter() {
/* 159 */       if (this.textField != null)
/*     */       {
/* 161 */         this.textField.a();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void apply() {
/* 167 */       if (this.binding != null) {
/*     */         
/* 169 */         if (this.checkBox != null) GuiMacroConfigPanel.setSetting(this.binding, Boolean.valueOf(this.checkBox.checked)); 
/* 170 */         if (this.dropDown != null) GuiMacroConfigPanel.setSetting(this.binding, GuiMacroConfigPanel.this.getEnumValue(this.dropDown.getSelectedItemTag(), this.defaultEnumValue)); 
/* 171 */         if (this.textField != null) GuiMacroConfigPanel.setSetting(this.binding, this.isNumeric ? Integer.valueOf(this.textField.getValueInt(this.defaultIntValue)) : this.textField.b());
/*     */       
/*     */       } 
/*     */     }
/*     */     
/*     */     public int draw(bsu minecraft, int mouseX, int mouseY, int yPosition) {
/* 177 */       if (this.displayText != null) {
/*     */         
/* 179 */         this.innerFontRenderer.a(this.displayText, this.xPosition, yPosition, this.displayColour);
/*     */       }
/* 181 */       else if (this.checkBox != null) {
/*     */         
/* 183 */         this.checkBox.drawCheckboxAt(minecraft, mouseX, mouseY, yPosition);
/*     */       }
/* 185 */       else if (this.button != null) {
/*     */         
/* 187 */         this.button.setYPosition(yPosition);
/* 188 */         this.button.a(minecraft, mouseX, mouseY);
/*     */       }
/* 190 */       else if (this.textField != null) {
/*     */         
/* 192 */         this.textField.drawTextBoxAt(yPosition);
/*     */       } 
/*     */       
/* 195 */       this.yPosition = yPosition;
/* 196 */       return yPosition + this.displayHeight;
/*     */     }
/*     */ 
/*     */     
/*     */     public void postRender(bsu minecraft, int mouseX, int mouseY) {
/* 201 */       if (this.dropDown != null) {
/*     */         
/* 203 */         GuiDialogBox.lastScreenHeight = GuiMacroConfigPanel.this.getContentHeight() - 32;
/* 204 */         this.dropDown.drawControlAt(minecraft, mouseX, mouseY, this.yPosition);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 210 */       if (this.textField != null) {
/* 211 */         this.textField.a(mouseX, mouseY, mouseButton);
/*     */       }
/* 213 */       if (this.button != null)
/*     */       {
/* 215 */         if (this.button.c(GuiMacroConfigPanel.this.minecraft, mouseX, mouseY)) {
/* 216 */           GuiMacroConfigPanel.this.actionPerformed(this);
/*     */         }
/*     */       }
/* 219 */       if (this.checkBox != null) {
/* 220 */         this.checkBox.c(GuiMacroConfigPanel.this.minecraft, mouseX, mouseY);
/*     */       }
/* 222 */       if (this.dropDown != null) {
/* 223 */         this.dropDown.c(GuiMacroConfigPanel.this.minecraft, mouseX, mouseY);
/*     */       }
/*     */     }
/*     */     
/*     */     public boolean textboxKeyTyped(char keyChar, int keyCode) {
/* 228 */       if (this.textField != null) {
/*     */         
/* 230 */         this.textField.a(keyChar, keyCode);
/* 231 */         return this.textField.m();
/*     */       } 
/*     */       
/* 234 */       return false;
/*     */     }
/*     */   }
/*     */   
/* 238 */   private ArrayList<ConfigOption> options = new ArrayList<ConfigOption>();
/*     */   
/* 240 */   private LinkedList<GuiTextFieldEx> textFields = new LinkedList<GuiTextFieldEx>();
/*     */   
/*     */   private int totalHeight;
/*     */   
/*     */   bsu minecraft;
/*     */   
/*     */   private boolean ateKeyPress;
/*     */   
/*     */   private GuiMacroConfig parentScreen;
/*     */   
/*     */   private boolean saveChanges = true;
/*     */ 
/*     */   
/*     */   public GuiMacroConfigPanel() {
/* 254 */     this(null);
/*     */   }
/*     */ 
/*     */   
/*     */   GuiMacroConfigPanel(GuiMacroConfig parentScreen) {
/* 259 */     this.minecraft = bsu.z();
/* 260 */     this.parentScreen = parentScreen;
/*     */     
/* 262 */     int displayColour = -22016;
/* 263 */     int controlId = 22;
/*     */     
/* 265 */     bsu minecraft = bsu.z();
/* 266 */     bty fontRenderer = minecraft.k;
/*     */     
/* 268 */     int left0 = 6;
/* 269 */     int left1 = 10;
/* 270 */     int left2 = 12;
/* 271 */     int left3 = 18;
/* 272 */     int left4 = 132;
/*     */     
/* 274 */     addOptionLabel("options.option.general", left0, 12, displayColour);
/*     */ 
/*     */     
/* 277 */     addOptionCheckbox(controlId++, left1, 20, "options.option.enableoverridechat", "enableOverrideChat");
/*     */     
/* 279 */     addOptionCheckbox(controlId++, left1, 20, "options.option.simpleoverride", "simpleOverridePopup");
/* 280 */     addOptionCheckbox(controlId++, left1, 20, "options.option.runstatus", "enableStatus");
/* 281 */     addOptionCheckbox(controlId++, left1, 20, "options.option.alwaysgoback", "alwaysGoBack");
/* 282 */     addOptionSpacer(12);
/*     */     
/* 284 */     addOptionLabel("options.option.gui", left0, 12, displayColour);
/* 285 */     addOptionCheckbox(controlId++, left1, 20, "options.option.simple", "simpleGui");
/* 286 */     addOptionCheckbox(controlId++, left1, 20, "options.option.animation", "enableGuiAnimation");
/* 287 */     addOptionCheckbox(controlId++, left1, 20, "options.option.rememberpage", "rememberBindPage");
/* 288 */     addOptionCheckbox(controlId++, left1, 20, "options.option.optionsfirst", "defaultToOptions");
/* 289 */     addOptionCheckbox(controlId++, left1, 20, "options.option.largebuttons", "drawLargeEditorButtons");
/* 290 */     addOptionCheckbox(controlId++, left1, 20, "options.option.autoscale", "autoScaleKeyboard");
/* 291 */     addOptionSpacer(12);
/*     */     
/* 293 */     addOptionLabel("options.option.extra", left0, 12, displayColour);
/*     */     
/* 295 */     addOptionCheckbox(controlId++, left1, 20, "options.option.showslots", "showSlotInfo");
/* 296 */     addOptionCheckbox(controlId++, left1, 20, "options.option.buttonsonchat", "enableButtonsOnChatGui");
/* 297 */     addOptionCheckbox(controlId++, left1, 20, "options.option.filterablechat", "showFilterableChat");
/* 298 */     addOptionSpacer(12);
/*     */     
/* 300 */     addOptionLabel("options.option.spamfilter", left0, 12, displayColour);
/*     */     
/* 302 */     addOptionCheckbox(controlId++, left1, 20, "options.option.spamfilter.enable", "spamFilterEnabled");
/* 303 */     addOptionCheckbox(controlId++, left1, 28, "options.option.spamfilter.ignorecommands", "spamFilterIgnoreCommands");
/* 304 */     addOptionLabel("options.option.spamfilter.style", left1, -4, 16777215);
/* 305 */     addOptionDropdown(minecraft, controlId++, left4, 80, 16, 16, 26, (Enum<?>)SpamFilter.FilterStyle.Queue, "spamFilterStyle");
/* 306 */     addOptionLabel("options.option.spamfilter.queuesize", left1, -4, 16777215);
/* 307 */     addOptionTextBox(controlId++, fontRenderer, left4, 80, 16, 20, "spamFilterQueueSize", 3);
/* 308 */     addOptionSpacer(12);
/*     */     
/* 310 */     addOptionLabel("options.option.discovery", left0, 16, displayColour);
/*     */     
/* 312 */     addOptionLabel("options.option.commandhomelist", left2, 12, 16777215);
/* 313 */     addOptionTextbox(controlId++, fontRenderer, left2, 200, 16, 18, "cmdHomeList");
/* 314 */     addOptionCheckbox(controlId++, left1, 28, "options.option.waitforhomecount", "requireHomeCount");
/* 315 */     addOptionLabel("options.option.commandtownlist", left2, 12, 16777215);
/* 316 */     addOptionTextbox(controlId++, fontRenderer, left2, 200, 16, 28, "cmdTownList");
/* 317 */     addOptionLabel("options.option.commandwarplist", left2, 12, 16777215);
/* 318 */     addOptionTextbox(controlId++, fontRenderer, left2, 200, 16, 24, "cmdWarps");
/* 319 */     addOptionLabel("options.option.commandwarplist2", left2, 12, 16777215);
/* 320 */     addOptionTextbox(controlId++, fontRenderer, left2, 200, 16, 20, "cmdWarpMorePages");
/* 321 */     addOptionLabel("options.option.commandwarplist2help", left3, 12, 16777215);
/* 322 */     addOptionSpacer(12);
/*     */     
/* 324 */     addOptionLabel("options.option.configs", left0, 16, displayColour);
/*     */     
/* 326 */     addOptionLabel("options.option.configs.use", left2, 12, 16777215);
/* 327 */     addOptionCheckbox(controlId++, left1, 18, "options.option.configs.friends", "configsForFriends");
/* 328 */     addOptionCheckbox(controlId++, left1, 18, "options.option.configs.homes", "configsForHomes");
/* 329 */     addOptionCheckbox(controlId++, left1, 18, "options.option.configs.towns", "configsForTowns");
/* 330 */     addOptionCheckbox(controlId++, left1, 18, "options.option.configs.warps", "configsForWarps");
/* 331 */     addOptionCheckbox(controlId++, left1, 18, "options.option.configs.places", "configsForPlaces");
/* 332 */     addOptionCheckbox(controlId++, left1, 18, "options.option.configs.presets", "configsForPresets");
/* 333 */     addOptionSpacer(14);
/*     */     
/* 335 */     addOptionLabel("options.option.advanced", left0, 16, displayColour);
/* 336 */     if (this.parentScreen != null) addOptionButton(4, left1, 120, 20, 24, "options.option.reserved"); 
/* 337 */     addOptionCheckbox(controlId++, left1, 18, "options.option.compatibilitymode", "compatibilityMode");
/* 338 */     addOptionCheckbox(controlId++, left1, 18, "options.option.disabledeepinject", "disableDeepInjection");
/* 339 */     addOptionCheckbox(controlId++, left1, 18, "options.option.keyboardedit", "keyboardLayoutEditable");
/* 340 */     addOptionCheckbox(controlId++, left1, 18, "options.option.chathistory", "chatHistory");
/* 341 */     addOptionCheckbox(controlId++, left1, 18, "options.option.debug", "enableDebug");
/* 342 */     addOptionCheckbox(controlId++, left1, 18, "options.option.debug.env", "debugEnvironment");
/* 343 */     addOptionCheckbox(controlId++, left1, 18, "options.option.debug.env.keys", "debugEnvKeys");
/* 344 */     addOptionCheckbox(controlId++, left1, 18, "options.option.textboxhighlight", "enableHighlightTextFields");
/* 345 */     addOptionCheckbox(controlId++, left1, 18, "options.option.debounce", "templateDebounceEnabled");
/* 346 */     addOptionSpacer(8);
/*     */     
/* 348 */     for (ConfigOption option : this.options) {
/*     */       
/* 350 */       this.totalHeight += option.displayHeight;
/*     */       
/* 352 */       if (option.textField != null) {
/* 353 */         this.textFields.add(option.textField);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private ConfigOption addOptionSpacer(int height) {
/* 359 */     ConfigOption newOption = new ConfigOption(height);
/* 360 */     this.options.add(newOption);
/* 361 */     return newOption;
/*     */   }
/*     */ 
/*     */   
/*     */   private ConfigOption addOptionLabel(String text, int xPosition, int height, int displayColour) {
/* 366 */     ConfigOption newOption = new ConfigOption(text, xPosition, height, displayColour);
/* 367 */     this.options.add(newOption);
/* 368 */     return newOption;
/*     */   }
/*     */ 
/*     */   
/*     */   private ConfigOption addOptionCheckbox(int id, int xPosition, int height, String displayText, String binding) {
/* 373 */     ConfigOption newOption = new ConfigOption(id, xPosition, height, displayText, binding);
/* 374 */     this.options.add(newOption);
/* 375 */     return newOption;
/*     */   }
/*     */ 
/*     */   
/*     */   private ConfigOption addOptionTextbox(int id, bty fontrenderer, int xPosition, int width, int height, int displayHeight, String binding) {
/* 380 */     ConfigOption newOption = new ConfigOption(id, fontrenderer, xPosition, width, height, displayHeight, binding);
/* 381 */     this.options.add(newOption);
/* 382 */     return newOption;
/*     */   }
/*     */ 
/*     */   
/*     */   private ConfigOption addOptionTextBox(int id, bty fontrenderer, int xPosition, int width, int height, int displayHeight, String binding, int digits) {
/* 387 */     ConfigOption newOption = new ConfigOption(id, fontrenderer, xPosition, width, height, displayHeight, binding, digits);
/* 388 */     this.options.add(newOption);
/* 389 */     return newOption;
/*     */   }
/*     */ 
/*     */   
/*     */   private ConfigOption addOptionButton(int id, int xPosition, int width, int height, int displayHeight, String displayText) {
/* 394 */     ConfigOption newOption = new ConfigOption(id, xPosition, width, height, displayHeight, displayText);
/* 395 */     this.options.add(newOption);
/* 396 */     return newOption;
/*     */   }
/*     */ 
/*     */   
/*     */   private ConfigOption addOptionDropdown(bsu minecraft, int controlId, int xPosition, int controlWidth, int controlHeight, int itemHeight, int displayHeight, Enum<?> defaultValue, String binding) {
/* 401 */     ConfigOption newOption = new ConfigOption(minecraft, controlId, xPosition, controlWidth, controlHeight, itemHeight, displayHeight, defaultValue, binding);
/* 402 */     this.options.add(newOption);
/* 403 */     return newOption;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPanelTitle() {
/* 409 */     return "Macro / Keybind Mod Settings";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getContentHeight() {
/* 415 */     return this.totalHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPanelShown(ConfigPanelHost host) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPanelResize(ConfigPanelHost host) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPanelHidden() {
/* 431 */     if (this.saveChanges)
/*     */     {
/* 433 */       for (ConfigOption option : this.options) {
/* 434 */         option.apply();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick(ConfigPanelHost host) {
/* 441 */     for (ConfigOption option : this.options) {
/* 442 */       option.updateCursorCounter();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawPanel(ConfigPanelHost host, int mouseX, int mouseY, float partialTicks) {
/* 448 */     int yPos = 4;
/*     */     
/* 450 */     for (ConfigOption option : this.options)
/*     */     {
/* 452 */       yPos = option.draw(this.minecraft, mouseX, mouseY, yPos);
/*     */     }
/*     */     
/* 455 */     for (int id = this.options.size(); id > 0; id--)
/*     */     {
/* 457 */       ((ConfigOption)this.options.get(id - 1)).postRender(this.minecraft, mouseX, mouseY);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mousePressed(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton) {
/* 464 */     for (ConfigOption option : this.options)
/*     */     {
/* 466 */       option.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void actionPerformed(ConfigOption configOption) {
/* 472 */     if (configOption.button != null && this.parentScreen != null)
/*     */     {
/* 474 */       this.parentScreen.a((bug)configOption.button);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseMoved(ConfigPanelHost host, int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(ConfigPanelHost host, char keyChar, int keyCode) {
/* 491 */     this.ateKeyPress = false;
/*     */     
/* 493 */     if (keyCode == 1 && this.parentScreen == null) {
/*     */       
/* 495 */       this.saveChanges = false;
/* 496 */       host.close();
/*     */     } 
/*     */     
/* 499 */     if (keyCode == 15) {
/*     */       
/* 501 */       selectNextField();
/* 502 */       this.ateKeyPress = true;
/*     */       
/*     */       return;
/*     */     } 
/* 506 */     for (ConfigOption option : this.options)
/*     */     {
/* 508 */       this.ateKeyPress |= option.textboxKeyTyped(keyChar, keyCode);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean ateKeyPress() {
/* 514 */     return this.ateKeyPress;
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectNextField() {
/* 519 */     GuiTextFieldEx firstTextField = this.textFields.getFirst();
/* 520 */     boolean found = false;
/* 521 */     boolean assigned = false;
/*     */     
/* 523 */     for (GuiTextFieldEx textField : this.textFields) {
/*     */       
/* 525 */       if (textField.m()) {
/*     */         
/* 527 */         textField.b(false);
/* 528 */         found = true; continue;
/*     */       } 
/* 530 */       if (found) {
/*     */         
/* 532 */         textField.b(!assigned);
/* 533 */         assigned = true;
/*     */         
/*     */         continue;
/*     */       } 
/* 537 */       textField.b(false);
/*     */     } 
/*     */ 
/*     */     
/* 541 */     if (!found || !assigned)
/*     */     {
/* 543 */       firstTextField.b(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Enum> T getEnumValue(String stringValue, T defaultValue) {
/*     */     try {
/* 552 */       Class<? extends T> enumClass = defaultValue.getDeclaringClass();
/* 553 */       return Enum.valueOf(enumClass, stringValue);
/*     */     }
/* 555 */     catch (IllegalArgumentException ex) {
/*     */       
/* 557 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static <T> T getSetting(String settingName) {
/* 564 */     if (settingName.equals("cmdHomeList")) {
/* 565 */       return (T)(MacroModCore.getMacroManager().getAutoDiscoveryAgent()).cmdHomeList;
/*     */     }
/* 567 */     if (settingName.equals("cmdTownList")) {
/* 568 */       return (T)(MacroModCore.getMacroManager().getAutoDiscoveryAgent()).cmdTownList;
/*     */     }
/* 570 */     if (settingName.equals("cmdWarps")) {
/* 571 */       return (T)(MacroModCore.getMacroManager().getAutoDiscoveryAgent()).cmdWarps;
/*     */     }
/* 573 */     if (settingName.equals("cmdWarpMorePages")) {
/* 574 */       return (T)(MacroModCore.getMacroManager().getAutoDiscoveryAgent()).cmdWarpMorePages;
/*     */     }
/* 576 */     if (settingName.equals("requireHomeCount")) {
/* 577 */       return (T)Boolean.valueOf((MacroModCore.getMacroManager().getAutoDiscoveryAgent()).requireHomeCount);
/*     */     }
/* 579 */     if (settingName.equals("keyboardLayoutEditable")) {
/* 580 */       return (T)Boolean.valueOf(LayoutPanel.isEditable());
/*     */     }
/* 582 */     return (T)MacroModSettings.getSetting(settingName);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static <T> void setSetting(String settingName, T settingValue) {
/* 587 */     if (settingName.equals("cmdHomeList")) {
/*     */       
/* 589 */       (MacroModCore.getMacroManager().getAutoDiscoveryAgent()).cmdHomeList = (String)settingValue;
/*     */       
/*     */       return;
/*     */     } 
/* 593 */     if (settingName.equals("cmdTownList")) {
/*     */       
/* 595 */       (MacroModCore.getMacroManager().getAutoDiscoveryAgent()).cmdTownList = (String)settingValue;
/*     */       
/*     */       return;
/*     */     } 
/* 599 */     if (settingName.equals("cmdWarps")) {
/*     */       
/* 601 */       (MacroModCore.getMacroManager().getAutoDiscoveryAgent()).cmdWarps = (String)settingValue;
/*     */       
/*     */       return;
/*     */     } 
/* 605 */     if (settingName.equals("cmdWarpMorePages")) {
/*     */       
/* 607 */       (MacroModCore.getMacroManager().getAutoDiscoveryAgent()).cmdWarpMorePages = (String)settingValue;
/*     */       
/*     */       return;
/*     */     } 
/* 611 */     if (settingName.equals("requireHomeCount")) {
/*     */       
/* 613 */       (MacroModCore.getMacroManager().getAutoDiscoveryAgent()).requireHomeCount = ((Boolean)settingValue).booleanValue();
/*     */       
/*     */       return;
/*     */     } 
/* 617 */     if (settingName.equals("keyboardLayoutEditable")) {
/*     */       
/* 619 */       LayoutPanel.setEditable(((Boolean)settingValue).booleanValue());
/*     */       
/*     */       return;
/*     */     } 
/* 623 */     MacroModSettings.setSetting(settingName, settingValue);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\screens\GuiMacroConfigPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */