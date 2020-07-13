/*     */ package net.eq2online.macros.core.params;
/*     */ 
/*     */ import bvz;
/*     */ import bwa;
/*     */ import bxf;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroParamProvider;
/*     */ import net.eq2online.macros.core.MacroParams;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.screens.GuiEditListEntry;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroParam;
/*     */ import net.eq2online.macros.interfaces.IListObject;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ 
/*     */ public abstract class MacroParamGenericEditableList extends MacroParam {
/*     */   protected MacroParamGenericEditableList(MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider provider) {
/*  19 */     super(type, target, params, provider);
/*     */     
/*  21 */     this.enableTextField = Boolean.valueOf(true);
/*  22 */     setParameterValue(params.getParameterValueFromStore(provider));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initListBox(int width, int height) {
/*  31 */     this.itemListBox = MacroModCore.getInstance().getListProvider().getListBox(this.type.toString());
/*  32 */     this.itemListBox.setSizeAndPosition(2, 2, 180, height - 40, GuiListBox.defaultRowHeight, true);
/*  33 */     this.itemListBox.setSelectedItemIndex(0);
/*  34 */     this.itemListBox.selectData(getParameterValue());
/*  35 */     this.itemListBox.scrollToCentre();
/*     */     
/*  37 */     if (this.itemListBox.getSelectedItem() != null && this.itemListBox.getSelectedItem().getId() != -1)
/*     */     {
/*  39 */       setParameterValue(this.itemListBox.getSelectedItem().getText());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAutoPopulateSupported() {
/*  49 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void editItem(GuiEditListEntry gui, String editedText, String displayName, int editedIconID, IListObject editedObject) {
/*  58 */     editedObject.setData(editedText);
/*  59 */     editedObject.setIconId(editedIconID);
/*  60 */     editedObject.setText(editedText);
/*     */     
/*  62 */     this.itemListBox.save();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleListBoxClick(GuiMacroParam guiMacroParam) {
/*  71 */     IListObject selectedItem = this.itemListBox.getSelectedItem();
/*     */ 
/*     */     
/*  74 */     if (selectedItem.getId() == -1) {
/*     */       
/*  76 */       AbstractionLayer.displayGuiScreen((bxf)new GuiEditListEntry(guiMacroParam, this, null));
/*     */       return;
/*     */     } 
/*  79 */     String customAction = selectedItem.getCustomAction(true);
/*     */     
/*  81 */     if (customAction == "delete") {
/*     */ 
/*     */       
/*  84 */       AbstractionLayer.displayGuiScreen((bxf)new bwa((bvz)guiMacroParam, LocalisationProvider.getLocalisedString("param.action.confirmdelete"), selectedItem.getText(), LocalisationProvider.getLocalisedString("gui.yes"), LocalisationProvider.getLocalisedString("gui.no"), 0));
/*     */     }
/*  86 */     else if (customAction == "edit") {
/*     */       
/*  88 */       AbstractionLayer.displayGuiScreen((bxf)new GuiEditListEntry(guiMacroParam, this, selectedItem));
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  93 */       handleListItemClick(selectedItem);
/*     */ 
/*     */       
/*  96 */       if (this.itemListBox.isDoubleClicked(true))
/*     */       {
/*  98 */         guiMacroParam.apply();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleListItemClick(IListObject selectedItem) {
/* 108 */     setParameterValue((String)selectedItem.getData());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTextFieldTextChanged() {
/* 114 */     this.itemListBox.selectData(getParameterValue());
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\params\MacroParamGenericEditableList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */