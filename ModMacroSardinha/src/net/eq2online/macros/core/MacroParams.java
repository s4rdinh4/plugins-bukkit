/*     */ package net.eq2online.macros.core;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.core.params.MacroParam;
/*     */ import net.eq2online.macros.core.params.MacroParamStandard;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderNamed;
/*     */ import net.eq2online.macros.interfaces.IMacroParamStorage;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MacroParams
/*     */ {
/*  24 */   private static final List<Pattern> highlighters = new ArrayList<Pattern>();
/*     */   protected MacroParamProvider nextParam;
/*     */   protected IMacroParamTarget target;
/*     */   
/*     */   static {
/*     */     try {
/*  30 */       for (MacroParamProvider provider : MacroParamProvider.getProviders())
/*     */       {
/*  32 */         highlighters.add(provider.getPattern());
/*     */       }
/*     */       
/*  35 */       Collections.reverse(highlighters);
/*     */     }
/*  37 */     catch (Exception ex) {
/*     */       
/*  39 */       throw new RuntimeException(ex);
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
/*  50 */   public static String escapeCharacter = "\\x5C";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   public static String nonPrintingEscapeCharacter = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   public static String paramPrefix = "\\x24\\x24";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public static String paramSequence = "(?<![" + escapeCharacter + nonPrintingEscapeCharacter + "])" + paramPrefix;
/*     */   
/*     */   private final List<MacroParamProvider> providers;
/*     */ 
/*     */   
/*     */   public MacroParams(IMacroParamTarget target) {
/*  71 */     this.target = target;
/*     */ 
/*     */     
/*     */     try {
/*  75 */       this.providers = MacroParamProvider.getProviders();
/*     */     }
/*  77 */     catch (Exception ex) {
/*     */       
/*  79 */       throw new RuntimeException(ex);
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
/*     */   public static String highlightParams(String macro, String prefix, String suffix) {
/*  91 */     for (Pattern highlighter : highlighters)
/*     */     {
/*  93 */       macro = highlighter.matcher(macro).replaceAll(prefix + "$0" + suffix);
/*     */     }
/*     */     
/*  96 */     return macro;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void evaluateParams() {
/* 104 */     this.nextParam = null;
/*     */     
/* 106 */     String script = this.target.getTargetString();
/* 107 */     int minPos = script.length();
/*     */     
/* 109 */     for (MacroParamProvider provider : this.providers) {
/*     */       
/* 111 */       provider.reset();
/* 112 */       provider.find(script);
/*     */       
/* 114 */       int start = provider.getStart();
/* 115 */       if (start < minPos) {
/*     */         
/* 117 */         this.nextParam = provider;
/* 118 */         minPos = start;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public MacroParam getNextParam() {
/* 125 */     if (hasRemainingParams())
/*     */     {
/* 127 */       return getMacroParam(this.nextParam, this.target, this);
/*     */     }
/*     */     
/* 130 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MacroParam.Type getNextParameterType() {
/* 140 */     return this.nextParam.getType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getParameterValueFromStore(MacroParamProvider provider) {
/* 150 */     if ((this.target.getIteration() > 1 && getNextParameterType() == MacroParam.Type.Normal) || this.target.getParamStore() == null)
/*     */     {
/* 152 */       return "";
/*     */     }
/*     */     
/* 155 */     return this.target.getParamStore().getStoredParam(provider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasRemainingParams() {
/* 165 */     return (this.nextParam != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeNamedVar(String listName) {
/* 170 */     for (MacroParamProvider provider : this.providers) {
/*     */       
/* 172 */       if (provider instanceof MacroParamProviderNamed)
/*     */       {
/* 174 */         ((MacroParamProviderNamed)provider).removeNamedVar(listName);
/*     */       }
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
/*     */   public void replaceParam(MacroParam param) {
/* 187 */     if (param.isFirstOccurrenceSupported() && this.target.getParamStore() != null)
/*     */     {
/* 189 */       this.target.getParamStore().setReplaceFirstOccurrenceOnly(param.getType(), param.shouldReplaceFirstOccurrenceOnly());
/*     */     }
/*     */ 
/*     */     
/* 193 */     boolean evalNeeded = param.replace();
/*     */     
/* 195 */     if (evalNeeded)
/*     */     {
/* 197 */       evaluateParams();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private MacroParam getMacroParam(MacroParamProvider provider, IMacroParamTarget target, MacroParams params) {
/* 203 */     IMacroParamStorage paramStore = target.getParamStore();
/*     */ 
/*     */     
/*     */     try {
/* 207 */       MacroParam newParam = provider.getMacroParam(target, params);
/* 208 */       return initParam(paramStore, newParam);
/*     */     } catch (Exception ex) {
/* 210 */       Log.printStackTrace(ex);
/*     */       
/* 212 */       MacroParamStandard newParam = new MacroParamStandard(provider.getType(), target, params, provider);
/* 213 */       return initParam(paramStore, (MacroParam)newParam);
/*     */     } 
/*     */   }
/*     */   
/*     */   private MacroParam initParam(IMacroParamStorage paramStore, MacroParam newParam) {
/* 218 */     if (newParam.isFirstOccurrenceSupported() && paramStore != null)
/*     */     {
/* 220 */       newParam.setReplaceFirstOccurrenceOnly(paramStore.shouldReplaceFirstOccurrenceOnly(newParam.getType()));
/*     */     }
/*     */     
/* 223 */     return newParam;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\MacroParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */