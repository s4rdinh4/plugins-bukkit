/*    */ package net.eq2online.macros.core.params;
/*    */ 
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ public abstract class MacroParamListOnly
/*    */   extends MacroParam
/*    */ {
/*    */   protected MacroParamListOnly(MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider provider) {
/* 11 */     super(type, target, params, provider);
/*    */     
/* 13 */     this.enableTextField = Boolean.valueOf(false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPromptMessage() {
/* 19 */     return "";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean apply() {
/* 25 */     boolean success = true;
/*    */     
/* 27 */     if (this.itemListBox.getSelectedItem().getId() != -1) {
/*    */       
/* 29 */       String selectedValue = this.itemListBox.getSelectedItem().getText();
/*    */       
/* 31 */       if (!checkForInvalidParameterValue(selectedValue))
/*    */       {
/* 33 */         setParameterValue(selectedValue);
/*    */       }
/*    */       else
/*    */       {
/* 37 */         setParameterValue("");
/* 38 */         success = false;
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 43 */       setParameterValue("");
/*    */     } 
/*    */     
/* 46 */     if (success)
/*    */     {
/*    */       
/* 49 */       replace();
/*    */     }
/*    */     
/* 52 */     return success;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean checkForInvalidParameterValue(String paramValue) {
/* 60 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\params\MacroParamListOnly.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */