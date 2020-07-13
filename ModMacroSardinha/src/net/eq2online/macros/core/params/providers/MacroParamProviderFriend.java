/*    */ package net.eq2online.macros.core.params.providers;
/*    */ 
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.core.params.MacroParam;
/*    */ import net.eq2online.macros.core.params.MacroParamFriend;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MacroParamProviderFriend
/*    */   extends MacroParamProvider
/*    */ {
/* 17 */   public static Pattern friendPattern = Pattern.compile(MacroParam.paramSequence + "f", 2);
/*    */ 
/*    */   
/*    */   public MacroParamProviderFriend(MacroParam.Type type) {
/* 21 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Pattern getPattern() {
/* 27 */     return friendPattern;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MacroParam getMacroParam(IMacroParamTarget target, MacroParams params) {
/* 33 */     return (MacroParam)new MacroParamFriend(getType(), target, params, this);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\params\providers\MacroParamProviderFriend.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */