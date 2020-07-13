/*     */ package net.eq2online.macros.core.params;
/*     */ 
/*     */ import bvz;
/*     */ import bwa;
/*     */ import bxf;
/*     */ import java.io.File;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.Macro;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroParamProvider;
/*     */ import net.eq2online.macros.core.MacroParams;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.controls.GuiTextFieldEx;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiListBoxFile;
/*     */ import net.eq2online.macros.gui.screens.GuiEditListEntry;
/*     */ import net.eq2online.macros.gui.screens.GuiEditTextFile;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroParam;
/*     */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*     */ import net.eq2online.macros.interfaces.IListObject;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ 
/*     */ public class MacroParamFile
/*     */   extends MacroParamListOnly {
/*     */   public static final String allowedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890_-. ";
/*     */   
/*     */   public MacroParamFile(MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider provider) {
/*  29 */     super(type, target, params, provider);
/*     */     
/*  31 */     setParameterValue(params.getParameterValueFromStore(provider));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean replace() {
/*  40 */     if (this.target.getIteration() == 1 && this.target.getParamStore() != null) this.target.getParamStore().setStoredParam(MacroParam.Type.File, 0, getParameterValue());
/*     */     
/*  42 */     if (getParameterValue().length() > 0 && !getParameterValue().equalsIgnoreCase("macros.txt")) {
/*     */       
/*  44 */       this.target.setTargetString(this.provider.matcher(this.target.getTargetString()).replaceAll(Macro.escapeReplacement("$$<" + getParameterValue() + ">")));
/*  45 */       this.target.compile();
/*  46 */       return false;
/*     */     } 
/*     */     
/*  49 */     this.target.setTargetString(this.provider.matcher(this.target.getTargetString()).replaceAll(""));
/*  50 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initListBox(int width, int height) {
/*  59 */     this.itemListBox = MacroModCore.getInstance().getListProvider().getListBox(this.type.toString());
/*  60 */     ((GuiListBoxFile)this.itemListBox).refresh();
/*  61 */     this.itemListBox.setSizeAndPosition(2, 2, 180, height - 4, GuiListBox.defaultRowHeight, true);
/*  62 */     this.itemListBox.setSelectedItemIndex(0);
/*  63 */     this.itemListBox.selectData(getParameterValue());
/*  64 */     this.itemListBox.scrollToCentre();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleListBoxClick(GuiMacroParam guiMacroParam) {
/*  73 */     IListObject selectedItem = this.itemListBox.getSelectedItem();
/*     */ 
/*     */     
/*  76 */     if (selectedItem.getId() == -1) {
/*     */       
/*  78 */       AbstractionLayer.displayGuiScreen((bxf)new GuiEditListEntry(guiMacroParam, this, null));
/*     */       return;
/*     */     } 
/*  81 */     String customAction = selectedItem.getCustomAction(true);
/*     */     
/*  83 */     if (customAction == "delete") {
/*     */       
/*  85 */       if (!MacroModCore.checkDisallowedTextFileName((String)selectedItem.getData()))
/*     */       {
/*     */         
/*  88 */         AbstractionLayer.displayGuiScreen((bxf)new bwa((bvz)guiMacroParam, LocalisationProvider.getLocalisedString("param.action.confirmdeletefile"), (String)selectedItem.getData(), LocalisationProvider.getLocalisedString("gui.yes"), LocalisationProvider.getLocalisedString("gui.no"), 0));
/*     */       }
/*     */     }
/*  91 */     else if (customAction == "edit") {
/*     */       
/*  93 */       GuiEditTextFile guiEditTextFile = new GuiEditTextFile((GuiScreenEx)guiMacroParam, new File(MacroModCore.getMacrosDirectory(), (String)selectedItem.getData()), ScriptContext.MAIN);
/*  94 */       AbstractionLayer.displayGuiScreen((bxf)guiEditTextFile);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  99 */       setParameterValue((String)selectedItem.getData());
/*     */     } 
/*     */ 
/*     */     
/* 103 */     if (this.itemListBox.isDoubleClicked(true))
/*     */     {
/* 105 */       guiMacroParam.apply();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkForInvalidParameterValue(String paramValue) {
/* 115 */     return MacroModCore.checkDisallowedTextFileName(paramValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupEditEntryWindow(GuiEditListEntry gui, boolean editing) {
/* 124 */     gui.displayText = LocalisationProvider.getLocalisedString("entry.newfile");
/* 125 */     gui.enableIconChoice = false;
/* 126 */     gui.windowHeight = 78;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupEditEntryTextbox(GuiTextFieldEx textField) {
/* 135 */     textField.minStringLength = 1;
/* 136 */     textField.f(64);
/* 137 */     textField.allowedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890_-. ";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addItem(GuiEditListEntry gui, String newItem, String displayName, int iconID, Object newItemData) {
/* 146 */     return this.parentScreen.createFileAndEdit(newItem);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\params\MacroParamFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */