/*     */ package net.eq2online.macros.scripting.variable;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.scripting.api.IArrayProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class VariableCache
/*     */   implements IArrayProvider, IVariableStore
/*     */ {
/*  20 */   private static Pattern varPattern = Pattern.compile("^([A-Z]+)(\\[([0-9]{1,5})\\])?$");
/*     */   
/*  22 */   private Map<String, Object> variables = new HashMap<String, Object>();
/*  23 */   private Map<String, Boolean> booleanVariables = new HashMap<String, Boolean>();
/*  24 */   private Map<String, Integer> intVariables = new HashMap<String, Integer>();
/*  25 */   private Map<String, String> stringVariables = new HashMap<String, String>();
/*     */   
/*  27 */   private Map<String, String[]> arrayVariables = (Map)new HashMap<String, String>();
/*     */ 
/*     */ 
/*     */   
/*     */   public void storeVariable(String variableName, boolean variableValue) {
/*  32 */     this.variables.put(variableName, Boolean.valueOf(variableValue));
/*  33 */     this.booleanVariables.put(variableName, Boolean.valueOf(variableValue));
/*  34 */     this.intVariables.remove(variableName);
/*  35 */     this.stringVariables.remove(variableName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void storeVariable(String variableName, int variableValue) {
/*  41 */     this.variables.put(variableName, Integer.valueOf(variableValue));
/*  42 */     this.intVariables.put(variableName, Integer.valueOf(variableValue));
/*  43 */     this.booleanVariables.remove(variableName);
/*  44 */     this.stringVariables.remove(variableName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void storeVariable(String variableName, String variableValue) {
/*  50 */     this.variables.put(variableName, variableValue);
/*  51 */     this.stringVariables.put(variableName, variableValue);
/*  52 */     this.booleanVariables.remove(variableName);
/*  53 */     this.intVariables.remove(variableName);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setCachedVariable(String variableName, String[] variableValue) {
/*  58 */     this.arrayVariables.put(variableName, variableValue);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void clearCachedVariables() {
/*  63 */     this.booleanVariables.clear();
/*  64 */     this.intVariables.clear();
/*  65 */     this.stringVariables.clear();
/*  66 */     this.arrayVariables.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean getCachedBooleanValue(String variableName) {
/*  71 */     return ((Boolean)this.booleanVariables.get(variableName)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getCachedIntegerValue(String variableName) {
/*  76 */     return ((Integer)this.intVariables.get(variableName)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getCachedStringValue(String variableName) {
/*  81 */     return this.stringVariables.get(variableName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected <T> T getCachedGenericValue(String variableName) {
/*  87 */     return (T)this.variables.get(variableName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object getCachedValue(String variableName) {
/*     */     try {
/*  94 */       Matcher varPatternMatcher = varPattern.matcher(variableName);
/*  95 */       if (varPatternMatcher.matches()) {
/*     */         
/*  97 */         String varName = varPatternMatcher.group(1);
/*  98 */         if (this.arrayVariables.containsKey(varName))
/*     */         {
/* 100 */           if (varPatternMatcher.group(3) != null) {
/*     */             
/* 102 */             int arrayIndex = Math.max(0, Integer.parseInt(varPatternMatcher.group(3)));
/* 103 */             String[] values = this.arrayVariables.get(varName);
/* 104 */             return (arrayIndex < values.length) ? values[arrayIndex] : "";
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } catch (Exception ex) {
/* 109 */       ex.printStackTrace();
/*     */     } 
/* 111 */     return this.variables.get(variableName);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean hasCachedValue(String variableName) {
/* 116 */     return this.variables.containsKey(variableName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getVariables() {
/* 122 */     return this.variables.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int indexOf(String arrayName, String value, boolean caseSensitive) {
/* 128 */     String[] array = this.arrayVariables.get(arrayName);
/* 129 */     if (array != null)
/*     */     {
/* 131 */       for (int index = 0; index < array.length; index++) {
/*     */         
/* 133 */         if ((!caseSensitive && array[index].equalsIgnoreCase(value)) || array[index].equals(value))
/*     */         {
/* 135 */           return index;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 140 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxArrayIndex(String arrayName) {
/* 146 */     String[] array = this.arrayVariables.get(arrayName);
/* 147 */     return (array != null) ? (array.length - 1) : -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkArrayExists(String arrayName) {
/* 153 */     return this.arrayVariables.containsKey(arrayName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getArrayVariableValue(String arrayName, int offset) {
/* 159 */     String[] array = this.arrayVariables.get(arrayName);
/* 160 */     if (array != null)
/*     */     {
/* 162 */       return (offset > -1 && offset < array.length) ? array[offset] : "";
/*     */     }
/*     */     
/* 165 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\variable\VariableCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */