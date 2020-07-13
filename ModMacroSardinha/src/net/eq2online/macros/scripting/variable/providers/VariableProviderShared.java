/*     */ package net.eq2online.macros.scripting.variable.providers;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.scripting.Variable;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.IVariableProviderShared;
/*     */ import net.eq2online.macros.scripting.variable.ArrayStorage;
/*     */ import net.eq2online.macros.scripting.variable.VariableProviderArray;
/*     */ import net.eq2online.xml.IArrayStorageBundle;
/*     */ import net.eq2online.xml.PropertiesXMLUtils;
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
/*     */ public class VariableProviderShared
/*     */   extends VariableProviderArray
/*     */   implements IVariableProviderShared
/*     */ {
/*     */   protected boolean dirty = false;
/*  34 */   protected int unSavedTicks = 100;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  39 */   protected Properties sharedVariables = new Properties();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private File propertiesFile;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VariableProviderShared(IScriptActionProvider parent) {
/*  53 */     this.propertiesFile = new File(MacroModCore.getMacrosDirectory(), ".globalvars.xml");
/*  54 */     load();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void markDirty() {
/*  59 */     this.dirty = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void load() {
/*  67 */     this.sharedVariables.clear();
/*     */     
/*  69 */     if (this.propertiesFile != null && this.propertiesFile.exists()) {
/*     */       
/*     */       try {
/*     */         
/*  73 */         BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(this.propertiesFile));
/*  74 */         IArrayStorageBundle arrayStorage = ArrayStorage.getStorageBundle(new ArrayStorage[] { this.flagStore, this.counterStore, this.stringStore });
/*  75 */         this.dirty |= PropertiesXMLUtils.load(this.sharedVariables, arrayStorage, inputStream);
/*  76 */         inputStream.close();
/*     */       } catch (IOException ex) {
/*  78 */         ex.printStackTrace();
/*     */       } 
/*     */     }
/*  81 */     save();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void save() {
/*  89 */     if (this.propertiesFile != null) {
/*     */       
/*  91 */       this.dirty = false;
/*     */ 
/*     */       
/*     */       try {
/*  95 */         BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(this.propertiesFile));
/*  96 */         IArrayStorageBundle arrayStorage = ArrayStorage.getStorageBundle(new ArrayStorage[] { this.flagStore, this.counterStore, this.stringStore });
/*  97 */         PropertiesXMLUtils.save(this.sharedVariables, arrayStorage, outputStream, "Shared variables store for mod_Macros", "UTF-8");
/*  98 */         outputStream.close();
/*     */       }
/* 100 */       catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateVariables(boolean clock) {
/* 110 */     if (clock) {
/*     */       
/* 112 */       this.unSavedTicks--;
/* 113 */       if (this.dirty && this.unSavedTicks < 0) {
/*     */         
/* 115 */         save();
/* 116 */         this.unSavedTicks = 100;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSharedVariable(String variableName, String variableValue) {
/* 127 */     this.sharedVariables.setProperty(variableName, variableValue);
/* 128 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSharedVariable(String variableName) {
/* 137 */     return this.sharedVariables.containsKey(variableName) ? this.sharedVariables.getProperty(variableName) : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getVariable(String variableName) {
/* 146 */     if (variableName.startsWith("@") && Variable.isValidVariableName(variableName)) {
/*     */       
/* 148 */       variableName = variableName.substring(1);
/*     */       
/* 150 */       if (this.sharedVariables.containsKey(variableName)) {
/*     */         
/* 152 */         String propertyValue = this.sharedVariables.getProperty(variableName);
/* 153 */         if (propertyValue.matches("^(\\-)?\\d+$")) {
/*     */           
/*     */           try {
/*     */             
/* 157 */             int intValue = Integer.parseInt(propertyValue);
/* 158 */             return Integer.valueOf(intValue);
/*     */           }
/* 160 */           catch (NumberFormatException numberFormatException) {}
/*     */         }
/*     */         
/* 163 */         return propertyValue;
/*     */       } 
/*     */       
/* 166 */       return super.getVariable(variableName);
/*     */     } 
/*     */     
/* 169 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getVariables() {
/* 175 */     Set<String> variables = super.getVariables();
/*     */     
/* 177 */     for (Object sharedVar : this.sharedVariables.keySet()) {
/* 178 */       variables.add("@" + sharedVar.toString());
/*     */     }
/* 180 */     return variables;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSharedVariable(String variableName, int defaultValue) {
/*     */     try {
/* 191 */       int value = Integer.parseInt(getSharedVariable(variableName));
/* 192 */       return value;
/*     */     }
/* 194 */     catch (NumberFormatException ex) {
/*     */       
/* 196 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInit() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCounter(String counter, int value) {
/* 214 */     setSharedVariable("#" + counter, String.valueOf(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetCounter(String counter) {
/* 223 */     this.sharedVariables.remove("#" + counter.toLowerCase());
/* 224 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetCounter(String counter, int offset) {
/* 233 */     markDirty();
/* 234 */     super.unsetCounter(counter, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void incrementCounter(String counter, int increment) {
/* 243 */     int currentValue = getCounter(counter);
/* 244 */     setSharedVariable("#" + counter, String.valueOf(currentValue + increment));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void decrementCounter(String counter, int decrement) {
/* 253 */     incrementCounter(counter, decrement * -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCounter(String counter) {
/* 262 */     return getSharedVariable("#" + counter, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(String stringName) {
/* 271 */     return getSharedVariable("&" + stringName.toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setString(String stringName, String value) {
/* 280 */     setSharedVariable("&" + stringName.toLowerCase(), value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetString(String stringName) {
/* 289 */     this.sharedVariables.remove("&" + stringName.toLowerCase());
/* 290 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getFlag(String flag) {
/* 299 */     String flagValue = getSharedVariable(flag);
/* 300 */     return (flagValue.equals("1") || flagValue.equalsIgnoreCase("true"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlag(String flag, boolean value) {
/* 309 */     setSharedVariable(flag, value ? "1" : "0");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlag(String flag) {
/* 318 */     setSharedVariable(flag, "1");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetFlag(String flag) {
/* 327 */     setSharedVariable(flag, "0");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlag(String flag, int offset, boolean value) {
/* 336 */     markDirty();
/* 337 */     super.setFlag(flag, offset, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlag(String flag, int offset) {
/* 346 */     markDirty();
/* 347 */     super.setFlag(flag, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetFlag(String flag, int offset) {
/* 356 */     markDirty();
/* 357 */     super.unsetFlag(flag, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCounter(String counter, int offset, int value) {
/* 366 */     markDirty();
/* 367 */     super.setCounter(counter, offset, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setString(String stringName, int offset, String value) {
/* 376 */     markDirty();
/* 377 */     super.setString(stringName, offset, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetString(String stringName, int offset) {
/* 386 */     markDirty();
/* 387 */     super.unsetString(stringName, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean push(String arrayName, String value) {
/* 396 */     markDirty();
/* 397 */     return super.push(arrayName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String pop(String arrayName) {
/* 406 */     markDirty();
/* 407 */     return super.pop(arrayName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String arrayName, int offset) {
/* 416 */     markDirty();
/* 417 */     super.delete(arrayName, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear(String arrayName) {
/* 426 */     markDirty();
/* 427 */     super.clear(arrayName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void incrementCounter(String counter, int offset, int increment) {
/* 436 */     markDirty();
/* 437 */     super.incrementCounter(counter, offset, increment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void decrementCounter(String counter, int offset, int increment) {
/* 446 */     markDirty();
/* 447 */     super.decrementCounter(counter, offset, increment);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\variable\providers\VariableProviderShared.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */