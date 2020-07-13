/*     */ package net.eq2online.macros.scripting;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.scripting.api.IArrayProvider;
/*     */ import net.eq2online.macros.scripting.api.ICounterProvider;
/*     */ import net.eq2online.macros.scripting.api.IFlagProvider;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMutableArrayProvider;
/*     */ import net.eq2online.macros.scripting.api.IStringProvider;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Variable
/*     */ {
/*     */   public static final String PREFIX_SHARED = "@";
/*     */   public static final String PREFIX_STRING = "&";
/*     */   public static final String PREFIX_INT = "#";
/*     */   public static final String PREFIX_BOOL = "";
/*     */   public static final String SUFFIX_ARRAY = "[]";
/*     */   public static final String PREFIX_TYPES = "#&";
/*     */   
/*     */   public enum Type
/*     */   {
/*  32 */     Flag,
/*  33 */     Counter,
/*  34 */     String;
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
/*  49 */   public static final Pattern variableNamePattern = Pattern.compile("^(@?)([#&]?)([a-z~]([a-z0-9_\\-]*))(\\[([0-9]{1,5})\\])?$", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   public static final Pattern arrayVariablePattern = Pattern.compile("\\[([0-9]{1,5})\\]$");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IArrayProvider arrayProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IMacro macro;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IVariableProvider variableProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   public Type type = Type.Flag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShared;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String prefix;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String variableName;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String qualifiedName;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   public int arrayOffset = -1;
/*     */ 
/*     */   
/*     */   private Variable(IVariableProvider variableProvider, List<IArrayProvider> arrayProviders, IMacro macro, String sharedPrefix, String typePrefix, String variableName, String arrayFormat, String arrayIndex, boolean array) {
/* 103 */     this.variableProvider = variableProvider;
/* 104 */     this.macro = macro;
/* 105 */     this.isShared = sharedPrefix.equals("@");
/* 106 */     this.prefix = typePrefix;
/* 107 */     this.variableName = variableName;
/* 108 */     this.qualifiedName = typePrefix + variableName;
/*     */     
/* 110 */     if (this.prefix.equals("#")) { this.type = Type.Counter; }
/* 111 */     else if (this.prefix.equals("&")) { this.type = Type.String; }
/*     */     
/* 113 */     if (arrayFormat != null || array) {
/*     */       
/* 115 */       if (this.isShared) {
/*     */         
/* 117 */         if (this.variableProvider instanceof IArrayProvider)
/*     */         {
/* 119 */           this.arrayProvider = (IArrayProvider)this.variableProvider;
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 124 */         this.arrayProvider = (IArrayProvider)this.macro.getArrayProvider();
/*     */       } 
/*     */       
/* 127 */       if (this.arrayProvider == null || !this.arrayProvider.checkArrayExists(this.qualifiedName))
/*     */       {
/* 129 */         for (IArrayProvider otherProvider : arrayProviders) {
/*     */           
/* 131 */           if (otherProvider.checkArrayExists(this.qualifiedName)) {
/*     */             
/* 133 */             this.arrayProvider = otherProvider;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 140 */     if (arrayFormat != null && arrayIndex != null) {
/*     */       
/* 142 */       this.arrayOffset = Math.max(0, Integer.parseInt(arrayIndex));
/*     */     }
/* 144 */     else if (array) {
/*     */       
/* 146 */       this.arrayOffset = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 153 */     return String.format("Variable(%s)[%s] %s", new Object[] { this.qualifiedName, Integer.valueOf(this.arrayOffset), this.arrayProvider });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isArray() {
/* 161 */     return (this.arrayProvider != null && this.arrayOffset > -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ICounterProvider getCounterProvider() {
/* 169 */     return this.isShared ? ((this.variableProvider instanceof ICounterProvider) ? (ICounterProvider)this.variableProvider : null) : this.macro.getCounterProvider();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IFlagProvider getFlagProvider() {
/* 177 */     return this.isShared ? ((this.variableProvider instanceof IFlagProvider) ? (IFlagProvider)this.variableProvider : null) : this.macro.getFlagProvider();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IStringProvider getStringProvider() {
/* 185 */     return this.isShared ? ((this.variableProvider instanceof IStringProvider) ? (IStringProvider)this.variableProvider : null) : this.macro.getStringProvider();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getFlag() {
/* 193 */     if (this.type != Type.Flag) return false; 
/* 194 */     IFlagProvider flagProvider = getFlagProvider();
/* 195 */     return (flagProvider != null) ? flagProvider.getFlag(this.variableName, this.arrayOffset) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCounter() {
/* 203 */     if (this.type != Type.Counter) return 0; 
/* 204 */     ICounterProvider counterProvider = getCounterProvider();
/* 205 */     return (counterProvider != null) ? counterProvider.getCounter(this.variableName, this.arrayOffset) : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString() {
/* 213 */     if (this.type != Type.String) return ""; 
/* 214 */     IStringProvider stringProvider = getStringProvider();
/* 215 */     return (stringProvider != null) ? stringProvider.getString(this.variableName, this.arrayOffset) : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlag(boolean newValue) {
/* 223 */     if (this.type == Type.Flag) {
/*     */       
/* 225 */       IFlagProvider flagProvider = getFlagProvider();
/* 226 */       if (flagProvider != null) flagProvider.setFlag(this.variableName, this.arrayOffset, newValue);
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCounter(int newValue) {
/* 235 */     if (this.type == Type.Counter) {
/*     */       
/* 237 */       ICounterProvider counterProvider = getCounterProvider();
/* 238 */       if (counterProvider != null) counterProvider.setCounter(this.variableName, this.arrayOffset, newValue);
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setString(String newValue) {
/* 247 */     if (this.type == Type.String) {
/*     */       
/* 249 */       IStringProvider stringProvider = getStringProvider();
/* 250 */       if (stringProvider != null) stringProvider.setString(this.variableName, this.arrayOffset, newValue);
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unSetFlag() {
/* 259 */     if (this.type == Type.Flag) {
/*     */       
/* 261 */       IFlagProvider flagProvider = getFlagProvider();
/* 262 */       if (flagProvider != null) flagProvider.unsetFlag(this.variableName, this.arrayOffset);
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unSetCounter() {
/* 271 */     if (this.type == Type.Counter) {
/*     */       
/* 273 */       ICounterProvider counterProvider = getCounterProvider();
/* 274 */       if (counterProvider != null) counterProvider.unsetCounter(this.variableName, this.arrayOffset);
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unSetString() {
/* 283 */     if (this.type == Type.String) {
/*     */       
/* 285 */       IStringProvider stringProvider = getStringProvider();
/* 286 */       if (stringProvider != null) stringProvider.unsetString(this.variableName, this.arrayOffset);
/*     */     
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
/*     */   public static Variable getVariable(IVariableProvider variableProvider, List<IArrayProvider> arrayProviders, IMacro macro, String variableName) {
/* 300 */     return getVariable(variableProvider, arrayProviders, macro, variableName, false);
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
/*     */   public static Variable getArrayVariable(IVariableProvider variableProvider, List<IArrayProvider> arrayProviders, IMacro macro, String variableName) {
/* 313 */     return getVariable(variableProvider, arrayProviders, macro, variableName, true);
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
/*     */   private static Variable getVariable(IVariableProvider variableProvider, List<IArrayProvider> arrayProviders, IMacro macro, String variableName, boolean assumeArray) {
/* 325 */     Matcher var = variableNamePattern.matcher(variableName);
/*     */     
/* 327 */     if (var.matches())
/*     */     {
/* 329 */       return new Variable(variableProvider, arrayProviders, macro, var.group(1), var.group(2), var.group(3), var.group(5), var.group(6), assumeArray);
/*     */     }
/* 331 */     if (assumeArray && couldBeArraySpecifier(variableName)) {
/*     */       
/* 333 */       var = variableNamePattern.matcher(getValidVariableOrArraySpecifier(variableName));
/*     */       
/* 335 */       if (var.matches())
/*     */       {
/* 337 */         return new Variable(variableProvider, arrayProviders, macro, var.group(1), var.group(2), var.group(3), var.group(5), var.group(6), assumeArray);
/*     */       }
/*     */     } 
/*     */     
/* 341 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final boolean isValidVariableName(String variableName) {
/* 352 */     return variableNamePattern.matcher(variableName).matches();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final boolean isValidScalarVariableName(String variableName) {
/* 363 */     Matcher matcher = variableNamePattern.matcher(variableName);
/* 364 */     return (matcher.matches() && matcher.group(5) == null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isValidVariableOrArraySpecifier(String variableName) {
/* 369 */     String name = getValidVariableOrArraySpecifier(variableName);
/* 370 */     if (name != null) return true;
/*     */     
/* 372 */     String expandedVariableName = (new VariableExpander(null, null, variableName, false, "var")).toString();
/* 373 */     return (getValidVariableOrArraySpecifier(expandedVariableName) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getValidVariableOrArraySpecifier(String variableName) {
/* 378 */     if (isValidVariableName(variableName)) return variableName; 
/* 379 */     if (couldBeArraySpecifier(variableName) && isValidVariableName(variableName.substring(0, variableName.length() - 2)))
/*     */     {
/* 381 */       return variableName.substring(0, variableName.length() - 2);
/*     */     }
/*     */     
/* 384 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean couldBeArraySpecifier(String variableName) {
/* 393 */     return (variableName != null && variableName.endsWith("[]") && variableName.length() > 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean couldBeString(String variableName) {
/* 404 */     if (variableName != null && variableName.startsWith("@")) variableName = variableName.substring(1); 
/* 405 */     return (variableName != null && variableName.startsWith("&"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean couldBeInt(String variableName) {
/* 414 */     if (variableName != null && variableName.startsWith("@")) variableName = variableName.substring(1); 
/* 415 */     return (variableName != null && variableName.startsWith("#"));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean couldBeBoolean(String variableName) {
/* 420 */     if (variableName != null && variableName.startsWith("@")) variableName = variableName.substring(1); 
/* 421 */     return (variableName != null && variableName.matches("^[a-z]([a-z0-9_\\-]*)$"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void arrayPush(String value) {
/* 426 */     if (this.arrayProvider instanceof IMutableArrayProvider)
/*     */     {
/* 428 */       ((IMutableArrayProvider)this.arrayProvider).push(this.qualifiedName, value);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void arrayPut(String value) {
/* 434 */     if (this.arrayProvider instanceof IMutableArrayProvider)
/*     */     {
/* 436 */       ((IMutableArrayProvider)this.arrayProvider).put(this.qualifiedName, value);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String arrayPop() {
/* 442 */     if (this.arrayProvider instanceof IMutableArrayProvider)
/*     */     {
/* 444 */       return ((IMutableArrayProvider)this.arrayProvider).pop(this.qualifiedName);
/*     */     }
/*     */     
/* 447 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public int arrayIndexOf(String search, boolean caseSensitive) {
/* 452 */     return (this.arrayProvider != null) ? this.arrayProvider.indexOf(this.qualifiedName, search, caseSensitive) : -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void arrayClear() {
/* 457 */     if (this.arrayProvider instanceof IMutableArrayProvider)
/*     */     {
/* 459 */       ((IMutableArrayProvider)this.arrayProvider).clear(this.qualifiedName);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Object arrayGetValue(int offset) {
/* 465 */     return (this.arrayProvider != null) ? this.arrayProvider.getArrayVariableValue(this.qualifiedName, offset) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int arrayGetMaxIndex() {
/* 470 */     return (this.arrayProvider != null) ? this.arrayProvider.getMaxArrayIndex(this.qualifiedName) : -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean arrayExists() {
/* 475 */     return (this.arrayProvider != null) ? this.arrayProvider.checkArrayExists(this.qualifiedName) : false;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\Variable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */