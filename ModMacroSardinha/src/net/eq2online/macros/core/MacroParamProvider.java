/*    */ package net.eq2online.macros.core;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.core.params.MacroParam;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MacroParamProvider
/*    */ {
/*    */   private final MacroParam.Type type;
/*    */   protected boolean found;
/*    */   protected int start;
/*    */   
/*    */   protected MacroParamProvider(MacroParam.Type type) {
/* 22 */     this.type = type;
/*    */   }
/*    */ 
/*    */   
/*    */   public MacroParam.Type getType() {
/* 27 */     return this.type;
/*    */   }
/*    */ 
/*    */   
/*    */   public String highlight(String macro, String prefix, String suffix) {
/* 32 */     return matcher(macro).replaceAll(prefix + "$0" + suffix);
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {
/* 37 */     this.found = false;
/* 38 */     this.start = Integer.MAX_VALUE;
/*    */   }
/*    */ 
/*    */   
/*    */   public Matcher find(String script) {
/* 43 */     Matcher matcher = matcher(script);
/* 44 */     this.found = matcher.find();
/* 45 */     this.start = this.found ? matcher.start() : Integer.MAX_VALUE;
/* 46 */     return matcher;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getStart() {
/* 51 */     return this.start;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFound() {
/* 56 */     return this.found;
/*    */   }
/*    */ 
/*    */   
/*    */   public Matcher matcher(String charSequence) {
/* 61 */     return getPattern().matcher(charSequence);
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract Pattern getPattern();
/*    */   
/*    */   public abstract MacroParam getMacroParam(IMacroParamTarget paramIMacroParamTarget, MacroParams paramMacroParams);
/*    */   
/*    */   public static List<MacroParamProvider> getProviders() throws Exception {
/* 70 */     List<MacroParamProvider> providers = new ArrayList<MacroParamProvider>();
/* 71 */     for (MacroParam.Type type : MacroParam.Type.values())
/*    */     {
/* 73 */       providers.add(type.getProvider());
/*    */     }
/* 75 */     return providers;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\MacroParamProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */