/*    */ package net.eq2online.macros.core.params.providers;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.core.params.MacroParam;
/*    */ import net.eq2online.macros.core.params.MacroParamPreset;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MacroParamProviderPreset
/*    */   extends MacroParamProvider
/*    */ {
/* 18 */   public static Pattern presetTextPattern = Pattern.compile(MacroParam.paramSequence + "([0-9])", 2);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   public static Pattern[] presetTextPatterns = new Pattern[] {
/* 24 */       Pattern.compile(MacroParam.paramSequence + "0"), 
/* 25 */       Pattern.compile(MacroParam.paramSequence + "1"), 
/* 26 */       Pattern.compile(MacroParam.paramSequence + "2"), 
/* 27 */       Pattern.compile(MacroParam.paramSequence + "3"), 
/* 28 */       Pattern.compile(MacroParam.paramSequence + "4"), 
/* 29 */       Pattern.compile(MacroParam.paramSequence + "5"), 
/* 30 */       Pattern.compile(MacroParam.paramSequence + "6"), 
/* 31 */       Pattern.compile(MacroParam.paramSequence + "7"), 
/* 32 */       Pattern.compile(MacroParam.paramSequence + "8"), 
/* 33 */       Pattern.compile(MacroParam.paramSequence + "9")
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 39 */   protected int nextPresetTextIndex = -1;
/*    */ 
/*    */   
/*    */   public MacroParamProviderPreset(MacroParam.Type type) {
/* 43 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Matcher find(String script) {
/* 49 */     Matcher matcher = super.find(script);
/*    */     
/* 51 */     if (this.found)
/*    */     {
/* 53 */       this.nextPresetTextIndex = Integer.parseInt(matcher.group(1));
/*    */     }
/*    */     
/* 56 */     return matcher;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getNextPresetIndex() {
/* 61 */     return this.nextPresetTextIndex;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Pattern getPattern() {
/* 67 */     return presetTextPattern;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MacroParam getMacroParam(IMacroParamTarget target, MacroParams params) {
/* 73 */     return (MacroParam)new MacroParamPreset(getType(), target, params, this);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\params\providers\MacroParamProviderPreset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */