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
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderPreset;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroParam;
/*     */ import net.eq2online.macros.gui.shared.GuiControlEx;
/*     */ import net.eq2online.macros.interfaces.IListObject;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ 
/*     */ public class MacroParamPreset
/*     */   extends MacroParamListOnly
/*     */ {
/*     */   protected int presetIndex;
/*     */   
/*     */   public MacroParamPreset(MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProviderPreset provider) {
/*  24 */     super(type, target, params, (MacroParamProvider)provider);
/*     */     
/*  26 */     this.presetIndex = provider.getNextPresetIndex();
/*     */     
/*  28 */     setParameterValue(params.getParameterValueFromStore((MacroParamProvider)provider));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean replace() {
/*  37 */     if (this.presetIndex > -1 && this.presetIndex < 10) {
/*     */       
/*  39 */       if (this.target.getParamStore() != null)
/*     */       {
/*  41 */         this.target.getParamStore().setStoredParam(this.type, this.presetIndex, getParameterValue());
/*     */       }
/*     */       
/*  44 */       this.target.setTargetString(MacroParamProviderPreset.presetTextPatterns[this.presetIndex].matcher(this.target.getTargetString()).replaceAll(getParameterValueEscaped()));
/*  45 */       this.presetIndex = -1;
/*  46 */       this.target.recompile();
/*     */     } 
/*     */     
/*  49 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initListBox(int width, int height) {
/*  58 */     this.itemListBox = MacroModCore.getInstance().getListProvider().getListBox(this.type.toString() + this.presetIndex);
/*  59 */     this.itemListBox.setSizeAndPosition(2, 2, width - 4, height - 4, GuiListBox.defaultRowHeight, false);
/*  60 */     this.itemListBox.setSelectedItemIndex(0);
/*  61 */     this.itemListBox.selectData(getParameterValue());
/*  62 */     this.itemListBox.scrollToCentre();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setupListBox(int width, int height) {
/*  71 */     this.itemListBox.setSize(width - 4, height - 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleListBoxClick(GuiMacroParam guiMacroParam) {
/*  80 */     IListObject selectedItem = this.itemListBox.getSelectedItem();
/*     */ 
/*     */     
/*  83 */     if (selectedItem.getId() == -1) {
/*     */       
/*  85 */       IListObject newItem = this.itemListBox.createObject("");
/*  86 */       this.itemListBox.addItemAt(newItem, this.itemListBox.getSelectedItemIndex());
/*  87 */       this.itemListBox.selectItem(newItem);
/*  88 */       this.itemListBox.beginEditInPlace();
/*     */       
/*     */       return;
/*     */     } 
/*  92 */     String customAction = selectedItem.getCustomAction(true);
/*     */     
/*  94 */     if (customAction == "delete") {
/*     */ 
/*     */       
/*  97 */       AbstractionLayer.displayGuiScreen((bxf)new bwa((bvz)guiMacroParam, LocalisationProvider.getLocalisedString("param.action.confirmdelete"), selectedItem.getText(), LocalisationProvider.getLocalisedString("gui.yes"), LocalisationProvider.getLocalisedString("gui.no"), 0));
/*     */     } else {
/*  99 */       if (customAction == "edit") {
/*     */         
/* 101 */         this.itemListBox.beginEditInPlace();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 106 */       if (selectedItem.getText().equals(""))
/*     */       {
/* 108 */         this.itemListBox.removeSelectedItem();
/*     */       }
/*     */       
/* 111 */       selectedItem = this.itemListBox.getSelectedItem();
/* 112 */       if (selectedItem != null) setParameterValue(selectedItem.getText());
/*     */     
/*     */     } 
/*     */     
/* 116 */     if (this.itemListBox.isDoubleClicked(true) && selectedItem != null && selectedItem.getId() > -1)
/*     */     {
/* 118 */       guiMacroParam.apply();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiControlEx.KeyHandledState keyTyped(char keyChar, int keyCode) {
/* 126 */     if (this.itemListBox != null)
/*     */     {
/* 128 */       return this.itemListBox.listBoxKeyTyped(keyChar, keyCode);
/*     */     }
/*     */     
/* 131 */     return GuiControlEx.KeyHandledState.None;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\params\MacroParamPreset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */