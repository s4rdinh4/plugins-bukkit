/*    */ package net.eq2online.macros.core.params;
/*    */ 
/*    */ import net.eq2online.macros.core.MacroModCore;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.gui.controls.GuiListBox;
/*    */ import net.eq2online.macros.gui.controls.specialised.GuiListBoxResourcePack;
/*    */ import net.eq2online.macros.gui.screens.GuiEditListEntry;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ public class MacroParamResourcePack
/*    */   extends MacroParam
/*    */ {
/*    */   public MacroParamResourcePack(MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider provider) {
/* 15 */     super(type, target, params, provider);
/*    */     
/* 17 */     this.enableTextField = Boolean.valueOf(false);
/* 18 */     setParameterValue(params.getParameterValueFromStore(provider));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPromptMessage() {
/* 24 */     return "";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean apply() {
/* 30 */     updateValue();
/* 31 */     return super.apply();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void updateValue() {
/* 39 */     String selectedValue = this.itemListBox.getSelectedItem().getText();
/* 40 */     setParameterValue(selectedValue);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean replace() {
/* 49 */     if (this.target.getIteration() == 1 && this.target.getParamStore() != null) this.target.getParamStore().setStoredParam(this.type, 0, getParameterValue());
/*    */     
/* 51 */     this.target.setTargetString(this.provider.matcher(this.target.getTargetString()).replaceAll(getParameterValueEscaped()));
/*    */     
/* 53 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initListBox(int width, int height) {
/* 62 */     this.itemListBox = MacroModCore.getInstance().getListProvider().getListBox(this.type.toString());
/* 63 */     ((GuiListBoxResourcePack)this.itemListBox).refresh();
/* 64 */     this.itemListBox.setSizeAndPosition(2, 2, 360, height - 40, GuiListBox.defaultRowHeight, true);
/* 65 */     this.itemListBox.scrollToCentre();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setupListBox(int width, int height) {
/* 71 */     this.itemListBox.setSize(360, height - 4);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean addItem(GuiEditListEntry gui, String newItem, String displayName, int iconID, Object newItemData) {
/* 80 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\params\MacroParamResourcePack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */