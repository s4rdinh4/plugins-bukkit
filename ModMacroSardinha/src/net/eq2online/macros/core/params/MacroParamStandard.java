/*    */ package net.eq2online.macros.core.params;
/*    */ 
/*    */ import net.eq2online.macros.core.Macro;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ public class MacroParamStandard
/*    */   extends MacroParam
/*    */ {
/*    */   public MacroParamStandard(MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider provider) {
/* 12 */     super(type, target, params, provider);
/*    */     
/* 14 */     this.enableTextField = Boolean.valueOf(true);
/* 15 */     setParameterValue(params.getParameterValueFromStore(provider));
/*    */     
/* 17 */     if (target.getIteration() > 1) {
/* 18 */       setParameterValue("");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean replace() {
/* 27 */     if (this.target.getIteration() == 1 && this.target.getParamStore() != null)
/*    */     {
/* 29 */       this.target.getParamStore().setStoredParam(this.type, 0, getParameterValue());
/*    */     }
/*    */     
/* 32 */     this.target.setTargetString(this.provider.matcher(this.target.getTargetString()).replaceAll(Macro.escapeReplacement(getParameterValue())));
/*    */     
/* 34 */     this.target.compile();
/* 35 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\params\MacroParamStandard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */