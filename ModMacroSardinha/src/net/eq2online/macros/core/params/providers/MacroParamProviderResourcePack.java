/*    */ package net.eq2online.macros.core.params.providers;
/*    */ 
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.core.params.MacroParam;
/*    */ import net.eq2online.macros.core.params.MacroParamResourcePack;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ 
/*    */ public class MacroParamProviderResourcePack
/*    */   extends MacroParamProvider
/*    */ {
/* 14 */   public static Pattern resourcePackListPattern = Pattern.compile(MacroParam.paramSequence + "k", 2);
/*    */ 
/*    */   
/*    */   public MacroParamProviderResourcePack(MacroParam.Type type) {
/* 18 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Pattern getPattern() {
/* 24 */     return resourcePackListPattern;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MacroParam getMacroParam(IMacroParamTarget target, MacroParams params) {
/* 30 */     return (MacroParam)new MacroParamResourcePack(getType(), target, params, this);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\params\providers\MacroParamProviderResourcePack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */