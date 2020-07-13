/*    */ package net.eq2online.macros.core.params.providers;
/*    */ 
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.core.params.MacroParam;
/*    */ import net.eq2online.macros.core.params.MacroParamItem;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MacroParamProviderItem
/*    */   extends MacroParamProvider
/*    */ {
/* 17 */   public static Pattern itemPattern = Pattern.compile(MacroParam.paramSequence + "(d|i(:d)?)", 2);
/*    */   
/* 19 */   public static Pattern itemPatternNoDamage = Pattern.compile(MacroParam.paramSequence + "i(?!:d)", 2);
/*    */   
/* 21 */   public static Pattern itemPatternDamageOnly = Pattern.compile(MacroParam.paramSequence + "d", 2);
/*    */ 
/*    */   
/*    */   public MacroParamProviderItem(MacroParam.Type type) {
/* 25 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Pattern getPattern() {
/* 31 */     return itemPattern;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MacroParam getMacroParam(IMacroParamTarget target, MacroParams params) {
/* 37 */     return (MacroParam)new MacroParamItem(getType(), target, params, this);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\params\providers\MacroParamProviderItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */