/*    */ package net.eq2online.macros.core.params;
/*    */ 
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ public class MacroParamShaderGroup
/*    */   extends MacroParamResourcePack
/*    */ {
/*    */   public MacroParamShaderGroup(MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider provider) {
/* 11 */     super(type, target, params, provider);
/*    */     
/* 13 */     this.enableTextField = Boolean.valueOf(false);
/* 14 */     setParameterValue(params.getParameterValueFromStore(provider));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void updateValue() {
/* 20 */     String selectedValue = (String)this.itemListBox.getSelectedItem().getData();
/* 21 */     setParameterValue(selectedValue);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean replace() {
/* 30 */     if (this.target.getIteration() == 1 && this.target.getParamStore() != null) this.target.getParamStore().setStoredParam(this.type, 0, getParameterValue());
/*    */     
/* 32 */     this.target.setTargetString(this.provider.matcher(this.target.getTargetString()).replaceAll(getParameterValueEscaped()));
/*    */     
/* 34 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\params\MacroParamShaderGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */