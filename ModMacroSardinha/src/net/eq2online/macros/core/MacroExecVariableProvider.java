/*     */ package net.eq2online.macros.core;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.eq2online.macros.scripting.VariableExpander;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IParameterProvider;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.variable.VariableCache;
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
/*     */ public class MacroExecVariableProvider
/*     */   extends VariableCache
/*     */   implements IParameterProvider
/*     */ {
/*     */   private ArrayList<Object> parameters;
/*     */   
/*     */   public MacroExecVariableProvider(String[] parameters, int ignore, IScriptActionProvider provider, IMacro macro) {
/*  33 */     this.parameters = new ArrayList(parameters.length - ignore);
/*     */     
/*  35 */     for (int paramIndex = ignore; paramIndex < parameters.length; paramIndex++) {
/*     */       
/*  37 */       String parameter = (new VariableExpander(provider, macro, parameters[paramIndex], false)).toString();
/*     */       
/*  39 */       if (parameter.matches("^\\-?[0-9\\.]+$")) {
/*     */         
/*  41 */         Integer paramValue = Integer.valueOf(Integer.parseInt(parameter));
/*  42 */         this.parameters.add(paramValue);
/*     */       }
/*  44 */       else if (parameter.equalsIgnoreCase("true")) {
/*     */         
/*  46 */         this.parameters.add(Boolean.valueOf(true));
/*     */       }
/*  48 */       else if (parameter.equalsIgnoreCase("false")) {
/*     */         
/*  50 */         this.parameters.add(Boolean.valueOf(false));
/*     */       }
/*     */       else {
/*     */         
/*  54 */         this.parameters.add(parameter);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateVariables(boolean clock) {
/*  65 */     if (this.parameters != null) {
/*     */       
/*  67 */       int variableIndex = 1;
/*     */       
/*  69 */       for (Object parameter : this.parameters) {
/*     */         
/*  71 */         if (parameter instanceof Integer) {
/*     */           
/*  73 */           storeVariable("var" + String.valueOf(variableIndex), ((Integer)parameter).intValue());
/*     */         }
/*  75 */         else if (parameter instanceof Boolean) {
/*     */           
/*  77 */           storeVariable("var" + String.valueOf(variableIndex), ((Boolean)parameter).booleanValue());
/*     */         }
/*     */         else {
/*     */           
/*  81 */           storeVariable("var" + String.valueOf(variableIndex), (String)parameter);
/*     */         } 
/*     */         
/*  84 */         variableIndex++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getVariable(String variableName) {
/*  92 */     return getCachedValue(variableName);
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
/*     */   public String provideParameters(String macro) {
/* 104 */     if (this.parameters != null) {
/*     */       
/* 106 */       int variableIndex = 1;
/*     */       
/* 108 */       for (Object parameter : this.parameters) {
/*     */         
/* 110 */         macro = macro.replaceAll(MacroParams.paramPrefix + "\\[" + variableIndex + "\\]", parameter.toString());
/* 111 */         variableIndex++;
/*     */       } 
/*     */     } 
/*     */     
/* 115 */     macro = macro.replaceAll(MacroParams.paramPrefix + "\\[[0-9]+\\]", "");
/*     */     
/* 117 */     return macro;
/*     */   }
/*     */   
/*     */   public void onInit() {}
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\MacroExecVariableProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */