/*    */ package net.eq2online.macros.core.params;
/*    */ 
/*    */ import net.eq2online.macros.core.Macro;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.core.params.providers.MacroParamProviderNamed;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MacroParamNamed
/*    */   extends MacroParamStandard
/*    */ {
/*    */   protected String namedParamName;
/*    */   
/*    */   public MacroParamNamed(MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProviderNamed provider) {
/* 17 */     super(type, target, params, (MacroParamProvider)provider);
/*    */     
/* 19 */     this.namedParamName = provider.getNextNamedVar();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean replace() {
/* 28 */     if (this.namedParamName != "var") {
/*    */       
/* 30 */       if (this.target.getParamStore() != null)
/*    */       {
/* 32 */         this.target.getParamStore().setStoredParam(this.type, 0, this.namedParamName, getParameterValue());
/*    */       }
/*    */       
/* 35 */       this.target.setTargetString(this.target.getTargetString().replaceAll("\\$\\$\\[" + this.namedParamName + "\\]", Macro.escapeReplacement(getParameterValue())));
/* 36 */       this.params.removeNamedVar(this.namedParamName);
/* 37 */       this.target.recompile();
/*    */     } 
/*    */     
/* 40 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPromptMessage() {
/* 46 */     return MacroParam.getLocalisedString("param.prompt.named", new String[] { this.target.getDisplayName() });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPromptPrefix() {
/* 52 */     return this.namedParamName;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\params\MacroParamNamed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */