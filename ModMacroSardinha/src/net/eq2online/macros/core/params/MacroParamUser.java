/*    */ package net.eq2online.macros.core.params;
/*    */ 
/*    */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*    */ import net.eq2online.macros.core.MacroModSettings;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ public class MacroParamUser
/*    */   extends MacroParamFriend
/*    */ {
/*    */   public MacroParamUser(MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider provider) {
/* 13 */     super(type, target, params, provider);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean replace() {
/* 22 */     if (this.target.getParamStore() != null)
/*    */     {
/* 24 */       this.target.getParamStore().setStoredParam(this.type, 0, getParameterValue());
/*    */     }
/*    */     
/* 27 */     if (shouldReplaceFirstOccurrenceOnly()) {
/*    */       
/* 29 */       this.target.setTargetString(this.provider.matcher(this.target.getTargetString()).replaceFirst(getParameterValueEscaped()));
/*    */     }
/*    */     else {
/*    */       
/* 33 */       this.target.setTargetString(this.provider.matcher(this.target.getTargetString()).replaceAll(getParameterValueEscaped()));
/*    */     } 
/*    */     
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldReplaceFirstOccurrenceOnly() {
/* 45 */     return (this.replaceFirstOccurrenceOnly || MacroModSettings.getCompilerFlagUser());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isAutoPopulateSupported() {
/* 54 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPromptMessage() {
/* 63 */     return MacroParam.getLocalisedString("param.prompt.person", new String[] { LocalisationProvider.getLocalisedString("param.prompt.user"), this.target.getDisplayName() });
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\params\MacroParamUser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */