/*     */ package net.eq2online.macros.core.params;
/*     */ 
/*     */ import bsu;
/*     */ import bty;
/*     */ import bub;
/*     */ import bxf;
/*     */ import cxy;
/*     */ import cye;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.ArrayList;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.ChatAllowedCharacters;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.Macro;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroParamProvider;
/*     */ import net.eq2online.macros.core.MacroParams;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderFile;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderFriend;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderHome;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderItem;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderList;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderNamed;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderPlace;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderPreset;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderResourcePack;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderShader;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderStandard;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderTown;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderUser;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderWarp;
/*     */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.controls.GuiTextFieldEx;
/*     */ import net.eq2online.macros.gui.screens.GuiEditListEntry;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroParam;
/*     */ import net.eq2online.macros.gui.shared.GuiControlEx;
/*     */ import net.eq2online.macros.interfaces.IListObject;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import oa;
/*     */ 
/*     */ public abstract class MacroParam
/*     */ {
/*     */   public enum Type {
/*  46 */     NamedParam((String)MacroParamProviderNamed.class),
/*     */ 
/*     */     
/*  49 */     Normal((String)MacroParamProviderStandard.class),
/*     */ 
/*     */     
/*  52 */     Item((String)MacroParamProviderItem.class),
/*     */ 
/*     */     
/*  55 */     Friend((String)MacroParamProviderFriend.class),
/*     */ 
/*     */     
/*  58 */     OnlineUser((String)MacroParamProviderUser.class),
/*     */ 
/*     */     
/*  61 */     Town((String)MacroParamProviderTown.class),
/*     */ 
/*     */     
/*  64 */     Warp((String)MacroParamProviderWarp.class),
/*     */ 
/*     */     
/*  67 */     Home((String)MacroParamProviderHome.class),
/*     */ 
/*     */     
/*  70 */     Place((String)MacroParamProviderPlace.class),
/*     */ 
/*     */     
/*  73 */     File((String)MacroParamProviderFile.class),
/*     */ 
/*     */     
/*  76 */     Preset((String)MacroParamProviderPreset.class),
/*     */ 
/*     */     
/*  79 */     TexturePack((String)MacroParamProviderResourcePack.class),
/*     */ 
/*     */     
/*  82 */     ShaderGroup((String)MacroParamProviderShader.class),
/*     */ 
/*     */     
/*  85 */     List((String)MacroParamProviderList.class);
/*     */     
/*     */     private Class<? extends MacroParamProvider> providerClass;
/*     */ 
/*     */     
/*     */     Type(Class<? extends MacroParamProvider> providerClass) {
/*  91 */       this.providerClass = providerClass;
/*     */     }
/*     */ 
/*     */     
/*     */     public MacroParamProvider getProvider() throws Exception {
/*  96 */       Constructor<? extends MacroParamProvider> ctor = this.providerClass.getDeclaredConstructor(new Class[] { Type.class });
/*  97 */       return ctor.newInstance(new Object[] { this });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   public static String escapeCharacter = "\\x5C";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   public static String nonPrintingEscapeCharacter = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   public static String paramPrefix = "\\x24\\x24";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   public static String paramSequence = "(?<![" + escapeCharacter + nonPrintingEscapeCharacter + "])" + paramPrefix;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final MacroParams params;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final MacroParamProvider provider;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final IMacroParamTarget target;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Type type;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 144 */   protected int position = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String parameterValue;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Boolean enableTextField;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 159 */   protected int maxParameterLength = 100;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String promptMessage;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String promptPrefix;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean itemListBoxInitialised = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 179 */   protected GuiListBox itemListBox = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GuiTextFieldEx txtParam;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GuiCheckBox chkReplaceAll;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GuiMacroParam parentScreen;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean replaceFirstOccurrenceOnly = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MacroParam(Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider provider) {
/* 206 */     this.type = type;
/* 207 */     this.target = target;
/* 208 */     this.params = params;
/* 209 */     this.provider = provider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFound() {
/* 219 */     return (this.position > -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getType() {
/* 229 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isType(Type type) {
/* 234 */     return (this.type == type);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPosition() {
/* 239 */     return this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   public IMacroParamTarget getTarget() {
/* 244 */     return this.target;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getParameterValue() {
/* 249 */     return this.parameterValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getParameterValueEscaped() {
/* 254 */     return Macro.escapeReplacement(this.parameterValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParameterValue(String newValue) {
/* 259 */     this.parameterValue = newValue;
/*     */     
/* 261 */     if (this.txtParam != null)
/*     */     {
/* 263 */       this.txtParam.a(newValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiListBox getListBox(int width, int height) {
/* 269 */     if (!this.itemListBoxInitialised) {
/*     */       
/* 271 */       this.itemListBoxInitialised = true;
/* 272 */       initListBox(width, height);
/*     */     } 
/*     */     
/* 275 */     if (this.itemListBox != null)
/*     */     {
/* 277 */       setupListBox(width, height);
/*     */     }
/*     */     
/* 280 */     return this.itemListBox;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initListBox(int width, int height) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setupListBox(int width, int height) {
/* 290 */     if (this.itemListBox != null)
/*     */     {
/* 292 */       this.itemListBox.setSize(180, height - (this.enableTextField.booleanValue() ? 40 : 4));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void initControls(int width, int height) {
/* 298 */     if (this.enableTextField.booleanValue()) {
/*     */       
/* 300 */       this.promptMessage = (this.target.getPromptMessage() == null) ? getPromptMessage() : this.target.getPromptMessage();
/* 301 */       this.promptPrefix = getPromptPrefix();
/*     */       
/* 303 */       bty fontRenderer = AbstractionLayer.getFontRenderer();
/* 304 */       int promptPrefixLength = fontRenderer.a(this.promptPrefix);
/* 305 */       if (promptPrefixLength > 0) promptPrefixLength += 6;
/*     */       
/* 307 */       if (this.txtParam == null) {
/*     */         
/* 309 */         this.txtParam = new GuiTextFieldEx(0, fontRenderer, promptPrefixLength + 4, height - 20, width - promptPrefixLength - 8, 16, this.parameterValue, getAllowedCharacters(), this.maxParameterLength);
/* 310 */         this.txtParam.b(true);
/* 311 */         this.txtParam.e(this.parameterValue.length() + 1);
/* 312 */         this.txtParam.i(0);
/*     */       }
/*     */       else {
/*     */         
/* 316 */         this.txtParam.setSizeAndPosition(promptPrefixLength + 4, height - 20, width - promptPrefixLength - 8, 16);
/*     */       } 
/*     */     } 
/*     */     
/* 320 */     if (isFirstOccurrenceSupported()) {
/*     */       
/* 322 */       this.chkReplaceAll = new GuiCheckBox(0, 0, 0, LocalisationProvider.getLocalisedString("param.option.replaceall"), !shouldReplaceFirstOccurrenceOnly());
/* 323 */       this.chkReplaceAll.setPosition(width - 4 - this.chkReplaceAll.getWidth(), height - 39);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 329 */     if (this.txtParam != null)
/*     */     {
/* 331 */       this.txtParam.a();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getAllowedCharacters() {
/* 337 */     return ChatAllowedCharacters.allowedCharacters;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(int mouseX, int mouseY, int button) {
/* 342 */     if (this.enableTextField.booleanValue()) {
/*     */       
/* 344 */       this.txtParam.a(mouseX, mouseY, button);
/* 345 */       this.txtParam.b(true);
/*     */     } 
/*     */     
/* 348 */     if (this.chkReplaceAll != null && button == 0) {
/*     */       
/* 350 */       bsu minecraft = bsu.z();
/*     */       
/* 352 */       if (this.chkReplaceAll.c(minecraft, mouseX, mouseY)) {
/*     */         
/* 354 */         minecraft.U().a((cye)cxy.a(ResourceLocations.BUTTON_PRESS, 1.0F));
/* 355 */         setReplaceFirstOccurrenceOnly(!this.chkReplaceAll.checked);
/* 356 */         this.chkReplaceAll.checked = !shouldReplaceFirstOccurrenceOnly();
/* 357 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 361 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiControlEx.KeyHandledState keyTyped(char keyChar, int keyCode) {
/* 367 */     if (this.itemListBox != null && keyCode != 203 && keyCode != 205)
/*     */     {
/* 369 */       return this.itemListBox.listBoxKeyTyped(keyChar, keyCode);
/*     */     }
/*     */     
/* 372 */     return GuiControlEx.KeyHandledState.None;
/*     */   }
/*     */ 
/*     */   
/*     */   public void textFieldKeyTyped(char keyChar, int keyCode) {
/* 377 */     if (this.enableTextField.booleanValue()) {
/*     */       
/* 379 */       if (keyChar == '|')
/*     */         return; 
/* 381 */       this.txtParam.a(keyChar, keyCode);
/* 382 */       this.parameterValue = this.txtParam.b();
/*     */       
/* 384 */       onTextFieldTextChanged();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTextFieldTextChanged() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawControls(bsu minecraft, int mouseX, int mouseY, float partialTick, boolean enabled, bty fontRenderer, int width, int height, int updateCounter) {
/* 395 */     if (this.enableTextField.booleanValue() && this.txtParam != null) {
/*     */       
/* 397 */       bub.a(2, height - 36, width - 2, height - 2, -1342177280);
/* 398 */       fontRenderer.a(this.promptMessage, 6, height - 33, enabled ? 16777045 : 1143087650);
/* 399 */       fontRenderer.a(this.promptPrefix, 6, height - 16, enabled ? 16777045 : 1143087650);
/*     */       
/* 401 */       this.txtParam.g();
/*     */     } 
/*     */     
/* 404 */     if (this.chkReplaceAll != null)
/*     */     {
/* 406 */       this.chkReplaceAll.a(minecraft, mouseX, mouseY);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void sortList() {
/* 412 */     if (this.itemListBox != null) {
/* 413 */       this.itemListBox.sort();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setParent(GuiMacroParam parentScreen) {
/* 418 */     this.parentScreen = parentScreen;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAutoPopulateSupported() {
/* 423 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void autoPopulateCancelled() {
/* 428 */     AbstractionLayer.displayGuiScreen((bxf)this.parentScreen);
/*     */   }
/*     */ 
/*     */   
/*     */   public void autoPopulateComplete(ArrayList<String> items) {
/* 433 */     if (this.parentScreen != null)
/*     */     {
/* 435 */       this.parentScreen.onAutoPopulateComplete(this, items);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void autoPopulate() {
/* 441 */     if (this.parentScreen != null)
/*     */     {
/* 443 */       AbstractionLayer.displayGuiScreen((bxf)this.parentScreen);
/*     */     }
/*     */     
/* 446 */     MacroModCore.getMacroManager().getAutoDiscoveryAgent().activate(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean apply() {
/* 452 */     this.params.replaceParam(this);
/* 453 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract boolean replace();
/*     */   
/*     */   public void setReplaceFirstOccurrenceOnly(boolean firstOnly) {
/* 460 */     if (isFirstOccurrenceSupported())
/*     */     {
/* 462 */       this.replaceFirstOccurrenceOnly = firstOnly;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldReplaceFirstOccurrenceOnly() {
/* 468 */     return this.replaceFirstOccurrenceOnly;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFirstOccurrenceSupported() {
/* 473 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addItem(GuiEditListEntry gui, String newItem, String displayName, int iconID, Object newItemData) {
/* 478 */     IListObject newListEntry = this.itemListBox.createObject(newItem, iconID, newItemData);
/* 479 */     newListEntry.setDisplayName(displayName);
/* 480 */     this.itemListBox.addItemAt(newListEntry, this.itemListBox.getItemCount() - 1);
/* 481 */     this.itemListBox.selectItem(newListEntry);
/* 482 */     this.itemListBox.save();
/* 483 */     setParameterValue(newItem);
/*     */     
/* 485 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void editItem(GuiEditListEntry gui, String editedText, String displayName, int editedIconID, IListObject editedObject) {}
/*     */ 
/*     */   
/*     */   public void deleteSelectedItem(boolean response) {
/* 494 */     if (this.itemListBox != null)
/*     */     {
/* 496 */       if (response) {
/*     */         
/* 498 */         this.itemListBox.removeSelectedItem();
/* 499 */         this.itemListBox.save();
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 504 */         setParameterValue((String)this.itemListBox.getSelectedItem().getData());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void importAllFrom(GuiListBox sourceListBox) {
/* 511 */     if (this.itemListBox != null) {
/*     */       
/* 513 */       while (sourceListBox.getItemCount() > 0)
/*     */       {
/* 515 */         this.itemListBox.addItemAt(sourceListBox.removeItemAt(0), sourceListBox.getItemCount());
/*     */       }
/*     */       
/* 518 */       this.itemListBox.save();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleListBoxClick(GuiMacroParam guiMacroParam) {
/* 524 */     if (this.itemListBox != null) {
/*     */       
/* 526 */       IListObject selectedItem = this.itemListBox.getSelectedItem();
/*     */       
/* 528 */       if (selectedItem != null)
/*     */       {
/* 530 */         setParameterValue(selectedItem.getText());
/*     */       }
/*     */ 
/*     */       
/* 534 */       if (this.itemListBox.isDoubleClicked(true))
/*     */       {
/* 536 */         guiMacroParam.apply();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPromptPrefix() {
/* 543 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPromptMessage() {
/* 548 */     String typeDescription = (this.type == Type.Normal) ? "" : (" " + this.type.toString());
/*     */     
/* 550 */     if (this.target.getIteration() > 1)
/*     */     {
/* 552 */       return getLocalisedString("param.prompt.inner", new String[] { this.target.getIterationString(), typeDescription, this.target.getDisplayName() });
/*     */     }
/*     */     
/* 555 */     return getLocalisedString("param.prompt.param", new String[] { typeDescription, this.target.getDisplayName() });
/*     */   }
/*     */ 
/*     */   
/*     */   protected static String getLocalisedString(String string, String... args) {
/* 560 */     if (string == null) return ""; 
/* 561 */     String translation = MacroModCore.getInstance().getRawLocalisedString(string);
/* 562 */     if (translation == null) translation = string; 
/* 563 */     String format = MacroModCore.convertAmpCodes(translation.replace("&e", "&f").replace("&c", "&e"));
/* 564 */     return String.format(format, (Object[])args);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupEditEntryWindow(GuiEditListEntry gui, boolean editing) {
/* 569 */     gui.displayText = LocalisationProvider.getLocalisedString(editing ? "entry.editentry" : "entry.newentry");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupEditEntryTextbox(GuiTextFieldEx textField) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public oa getIconTexture() {
/* 579 */     return ResourceLocations.DYNAMIC_FRIENDS;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\params\MacroParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */