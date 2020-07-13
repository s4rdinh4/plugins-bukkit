/*    */ package net.eq2online.macros.core.params.providers;
/*    */ 
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.core.params.MacroParam;
/*    */ import net.eq2online.macros.core.params.MacroParamTown;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MacroParamProviderTown
/*    */   extends MacroParamProvider
/*    */ {
/* 17 */   public static Pattern townPattern = Pattern.compile(MacroParam.paramSequence + "t", 2);
/*    */ 
/*    */   
/*    */   public MacroParamProviderTown(MacroParam.Type type) {
/* 21 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Pattern getPattern() {
/* 27 */     return townPattern;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MacroParam getMacroParam(IMacroParamTarget target, MacroParams params) {
/* 33 */     return (MacroParam)new MacroParamTown(getType(), target, params, this);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\params\providers\MacroParamProviderTown.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */