/*     */ package net.eq2online.macros.scripting;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VariableExpander
/*     */ {
/*     */   private final IScriptActionProvider provider;
/*     */   private final String templateString;
/*     */   private String innerString;
/*     */   private final boolean quoteStrings;
/*     */   private final String defaultStringValue;
/*  40 */   private static Pattern variablePattern = Pattern.compile("%(@?[#&]?[a-z~]([a-z0-9_\\-]*?)(\\[[0-9]{1,5}\\])?)%", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VariableExpander(IScriptActionProvider provider, IMacro macro, String text, boolean quoteStrings) {
/*  51 */     this(provider, macro, text, quoteStrings, "");
/*     */   }
/*     */ 
/*     */   
/*     */   public VariableExpander(IScriptActionProvider provider, IMacro macro, String text, boolean quoteStrings, String defaultStringValue) {
/*  56 */     this.provider = provider;
/*  57 */     this.templateString = text;
/*  58 */     this.quoteStrings = quoteStrings;
/*  59 */     this.defaultStringValue = defaultStringValue;
/*     */     
/*  61 */     apply(macro);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(IMacro macro) {
/*  71 */     this.innerString = this.templateString;
/*     */     
/*  73 */     Matcher variablePatternMatcher = variablePattern.matcher(this.innerString);
/*  74 */     int replacements = 0;
/*     */     
/*  76 */     while (variablePatternMatcher.find() && replacements < 256) {
/*     */       
/*  78 */       replacements++;
/*     */       
/*  80 */       this.innerString = replaceVariable(this.provider, macro, this.innerString, this.quoteStrings, variablePatternMatcher.group(), variablePatternMatcher.group(1), this.defaultStringValue);
/*  81 */       variablePatternMatcher.reset(this.innerString);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String expand(IScriptActionProvider provider, IMacro macro, String variableName) {
/*  95 */     String variable = "%" + variableName + "%";
/*  96 */     return replaceVariable(provider, macro, variable, false, variable, variableName, "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String replaceVariable(IScriptActionProvider provider, IMacro macro, String subject, boolean quoteStrings, String variable, String variableName, String defaultStringValue) {
/* 112 */     Object oVariableValue = (provider != null) ? provider.getVariable(variableName, macro) : null;
/*     */     
/* 114 */     if (oVariableValue == null) {
/*     */       
/* 116 */       if (Variable.couldBeInt(variableName)) return subject.replace(variable, "0"); 
/* 117 */       if (Variable.couldBeString(variableName)) return subject.replace(variable, defaultStringValue); 
/* 118 */       if (Variable.couldBeBoolean(variableName)) return subject.replace(variable, "False"); 
/* 119 */       return subject.replace(variable, variableName);
/*     */     } 
/*     */     
/* 122 */     if (oVariableValue instanceof Integer) {
/*     */       
/* 124 */       int iVariableValue = ((Integer)oVariableValue).intValue();
/* 125 */       return subject.replace(variable, String.valueOf(iVariableValue));
/*     */     } 
/* 127 */     if (oVariableValue instanceof Boolean)
/*     */     {
/* 129 */       return subject.replace(variable, ((Boolean)oVariableValue).booleanValue() ? "True" : "False");
/*     */     }
/*     */     
/* 132 */     String variableValue = oVariableValue.toString();
/*     */     
/* 134 */     if (quoteStrings)
/*     */     {
/* 136 */       variableValue = "\"" + variableValue + "\"";
/*     */     }
/*     */     
/* 139 */     return subject.replace(variable, variableValue.replace(variable, variableName));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 145 */     return this.innerString;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\VariableExpander.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */