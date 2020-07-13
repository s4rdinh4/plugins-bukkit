/*    */ package net.eq2online.macros.core.params.providers;
/*    */ 
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Set;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.core.params.MacroParam;
/*    */ import net.eq2online.macros.core.params.MacroParamNamed;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MacroParamProviderNamed
/*    */   extends MacroParamProvider
/*    */ {
/* 20 */   public static Pattern namedVarPattern = Pattern.compile(MacroParam.paramSequence + "\\[([a-z0-9]{1,32})\\]", 2);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 25 */   private LinkedHashSet<String> namedVars = new LinkedHashSet<String>();
/*    */ 
/*    */   
/*    */   public MacroParamProviderNamed(MacroParam.Type type) {
/* 29 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Matcher find(String script) {
/* 35 */     this.start = script.length();
/*    */ 
/*    */     
/* 38 */     Matcher matcher = matcher(script);
/*    */     
/* 40 */     while (matcher.find()) {
/*    */       
/* 42 */       this.start = matcher.start();
/* 43 */       this.namedVars.add(matcher.group(1));
/*    */     } 
/*    */     
/* 46 */     return matcher;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> getNamedVars() {
/* 51 */     return this.namedVars;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getNextNamedVar() {
/* 61 */     if (this.namedVars.size() > 0)
/*    */     {
/* 63 */       return this.namedVars.iterator().next();
/*    */     }
/*    */     
/* 66 */     return "var";
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeNamedVar(String name) {
/* 71 */     this.namedVars.remove(name);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Pattern getPattern() {
/* 77 */     return namedVarPattern;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MacroParam getMacroParam(IMacroParamTarget target, MacroParams params) {
/* 83 */     return (MacroParam)new MacroParamNamed(getType(), target, params, this);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\params\providers\MacroParamProviderNamed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */