/*    */ package net.eq2online.macros.core.params.providers;
/*    */ 
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.core.params.MacroParam;
/*    */ import net.eq2online.macros.core.params.MacroParamShaderGroup;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ 
/*    */ public class MacroParamProviderShader
/*    */   extends MacroParamProvider
/*    */ {
/* 14 */   public static Pattern shaderGroupListPattern = Pattern.compile(MacroParam.paramSequence + "s", 2);
/*    */ 
/*    */   
/*    */   public MacroParamProviderShader(MacroParam.Type type) {
/* 18 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Pattern getPattern() {
/* 24 */     return shaderGroupListPattern;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MacroParam getMacroParam(IMacroParamTarget target, MacroParams params) {
/* 30 */     return (MacroParam)new MacroParamShaderGroup(getType(), target, params, this);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\params\providers\MacroParamProviderShader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */