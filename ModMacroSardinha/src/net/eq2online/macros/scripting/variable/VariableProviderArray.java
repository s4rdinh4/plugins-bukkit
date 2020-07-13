/*     */ package net.eq2online.macros.scripting.variable;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import net.eq2online.macros.scripting.Variable;
/*     */ import net.eq2online.macros.scripting.api.ICounterProvider;
/*     */ import net.eq2online.macros.scripting.api.IFlagProvider;
/*     */ import net.eq2online.macros.scripting.api.IMutableArrayProvider;
/*     */ import net.eq2online.macros.scripting.api.IStringProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ import net.eq2online.xml.IArrayStorageBundle;
/*     */ import net.eq2online.xml.PropertiesXMLUtils;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class VariableProviderArray
/*     */   implements IMutableArrayProvider, ICounterProvider, IFlagProvider, IStringProvider
/*     */ {
/*  24 */   protected final ArrayStorage<Boolean> flagStore = new ArrayStorage<Boolean>("boolean", Boolean.valueOf(false));
/*  25 */   protected final ArrayStorage<Integer> counterStore = new ArrayStorage<Integer>("int", Integer.valueOf(0), "#");
/*  26 */   protected final ArrayStorage<String> stringStore = new ArrayStorage<String>("string", "", "&");
/*     */ 
/*     */   
/*     */   private Map<Integer, ?> getArray(String arrayName) {
/*  30 */     return getStorage(arrayName).lookup(arrayName);
/*     */   }
/*     */ 
/*     */   
/*     */   private ArrayStorage<?> getStorage(String arrayName) {
/*  35 */     return arrayName.startsWith("#") ? this.counterStore : (arrayName.startsWith("&") ? this.stringStore : this.flagStore);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxArrayIndex(String arrayName) {
/*  41 */     return getStorage(arrayName).getMaxIndex(arrayName);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFreeArrayIndex(String arrayName) {
/*  46 */     return getStorage(arrayName).getFreeIndex(arrayName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkArrayExists(String arrayName) {
/*  52 */     return getStorage(arrayName).has(arrayName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean push(String arrayName, String value) {
/*  58 */     arrayName = arrayName.toLowerCase();
/*  59 */     insert(arrayName, getMaxArrayIndex(arrayName) + 1, value);
/*  60 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean put(String arrayName, String value) {
/*  66 */     arrayName = arrayName.toLowerCase();
/*  67 */     insert(arrayName, getFreeArrayIndex(arrayName), value);
/*  68 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void insert(String arrayName, int offset, String value) {
/*  73 */     if (arrayName.startsWith("#")) {
/*     */       
/*  75 */       int intValue = ScriptCore.tryParseInt(value, 0);
/*  76 */       this.counterStore.set(arrayName, offset, Integer.valueOf(intValue));
/*     */     }
/*  78 */     else if (arrayName.startsWith("&")) {
/*     */       
/*  80 */       this.stringStore.set(arrayName, offset, value);
/*     */     }
/*     */     else {
/*     */       
/*  84 */       boolean booleanValue = (value.equals("1") || value.equalsIgnoreCase("true"));
/*  85 */       this.flagStore.set(arrayName, offset, Boolean.valueOf(booleanValue));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String pop(String arrayName) {
/*  92 */     arrayName = arrayName.toLowerCase();
/*  93 */     int popIndex = getMaxArrayIndex(arrayName);
/*     */     
/*  95 */     if (popIndex > -1) {
/*     */       
/*  97 */       if (arrayName.startsWith("#")) {
/*     */         
/*  99 */         Integer integer = this.counterStore.pop(arrayName, popIndex);
/* 100 */         return (integer != null) ? String.valueOf(integer) : null;
/*     */       } 
/* 102 */       if (arrayName.startsWith("&"))
/*     */       {
/* 104 */         return this.stringStore.pop(arrayName, popIndex);
/*     */       }
/*     */       
/* 107 */       Boolean value = this.flagStore.pop(arrayName, popIndex);
/* 108 */       return (value != null) ? (value.booleanValue() ? "1" : "0") : null;
/*     */     } 
/*     */     
/* 111 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int indexOf(String arrayName, String value, boolean caseSensitive) {
/* 117 */     arrayName = arrayName.toLowerCase();
/* 118 */     Map<Integer, ?> map = getArray(arrayName);
/*     */     
/* 120 */     if (map != null) {
/*     */       
/* 122 */       for (Map.Entry<Integer, ?> mapEntry : map.entrySet()) {
/*     */         
/* 124 */         String entryValue = (mapEntry.getValue() instanceof String) ? (String)mapEntry.getValue() : String.valueOf(mapEntry.getValue());
/*     */         
/* 126 */         if ((!caseSensitive && entryValue.equalsIgnoreCase(value)) || entryValue.equals(value))
/*     */         {
/* 128 */           return ((Integer)mapEntry.getKey()).intValue();
/*     */         }
/*     */       } 
/*     */       
/* 132 */       boolean arrayIsString = arrayName.startsWith("&");
/* 133 */       if ((arrayIsString && value.length() == 0) || (!arrayIsString && (value.equals("0") || value.equalsIgnoreCase("false"))))
/*     */       {
/* 135 */         for (int vid = 0; vid < getMaxArrayIndex(arrayName); vid++) {
/*     */           
/* 137 */           if (map.get(Integer.valueOf(vid)) == null)
/*     */           {
/* 139 */             return vid;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 145 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String arrayName, int offset) {
/* 151 */     getStorage(arrayName).delete(arrayName.toLowerCase(), offset);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear(String arrayName) {
/* 157 */     getStorage(arrayName).remove(arrayName.toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getVariable(String variableName) {
/* 163 */     Matcher arrayIndexMatcher = Variable.arrayVariablePattern.matcher(variableName);
/*     */     
/* 165 */     if (arrayIndexMatcher.find()) {
/*     */       
/* 167 */       int arrayIndex = Math.max(0, Integer.parseInt(arrayIndexMatcher.group(1)));
/* 168 */       variableName = variableName.substring(0, variableName.indexOf('['));
/*     */       
/* 170 */       return getArrayVariableValue(variableName, arrayIndex);
/*     */     } 
/*     */     
/* 173 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getVariables() {
/* 179 */     return addVariables(addVariables(addVariables(new HashSet<String>(), this.counterStore), this.stringStore), this.flagStore);
/*     */   }
/*     */ 
/*     */   
/*     */   private Set<String> addVariables(Set<String> variables, ArrayStorage<?> store1) {
/* 184 */     for (String var : store1.getArrayNames()) {
/* 185 */       variables.add(store1.getPrefix() + var + "[]");
/*     */     }
/* 187 */     return variables;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getArrayVariableValue(String variableName, int offset) {
/* 193 */     return getStorage(variableName).getValue(variableName, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getFlag(String flag, int offset) {
/* 199 */     flag = flag.toLowerCase();
/* 200 */     if (offset < 0) return getFlag(flag); 
/* 201 */     return ((Boolean)this.flagStore.getNotNull(flag, offset)).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlag(String flag, int offset, boolean value) {
/* 207 */     flag = flag.toLowerCase();
/* 208 */     if (offset < 0) {
/*     */       
/* 210 */       setFlag(flag, value);
/*     */       
/*     */       return;
/*     */     } 
/* 214 */     this.flagStore.set(flag, offset, Boolean.valueOf(value));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlag(String flag, int offset) {
/* 220 */     setFlag(flag, offset, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetFlag(String flag, int offset) {
/* 226 */     setFlag(flag, offset, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCounter(String counter, int offset) {
/* 232 */     counter = counter.toLowerCase();
/* 233 */     if (offset < 0) return getCounter(counter); 
/* 234 */     return ((Integer)this.counterStore.getNotNull(counter, offset)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCounter(String counter, int offset, int value) {
/* 240 */     counter = counter.toLowerCase();
/* 241 */     if (offset < 0) {
/*     */       
/* 243 */       setCounter(counter, value);
/*     */       
/*     */       return;
/*     */     } 
/* 247 */     this.counterStore.set(counter, offset, Integer.valueOf(value));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetCounter(String counter, int offset) {
/* 253 */     counter = counter.toLowerCase();
/* 254 */     if (offset < 0) {
/*     */       
/* 256 */       unsetCounter(counter);
/*     */       
/*     */       return;
/*     */     } 
/* 260 */     delete("#" + counter, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void incrementCounter(String counter, int offset, int increment) {
/* 266 */     counter = counter.toLowerCase();
/* 267 */     int counterValue = getCounter(counter, offset);
/* 268 */     counterValue += increment;
/* 269 */     setCounter(counter, offset, counterValue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void decrementCounter(String counter, int offset, int increment) {
/* 275 */     counter = counter.toLowerCase();
/* 276 */     int counterValue = getCounter(counter, offset);
/* 277 */     counterValue -= increment;
/* 278 */     setCounter(counter, offset, counterValue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(String stringName, int offset) {
/* 284 */     stringName = stringName.toLowerCase();
/* 285 */     if (offset < 0) return getString(stringName); 
/* 286 */     return this.stringStore.getNotNull(stringName, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setString(String stringName, int offset, String value) {
/* 292 */     stringName = stringName.toLowerCase();
/* 293 */     if (offset < 0) {
/*     */       
/* 295 */       setString(stringName, value);
/*     */       
/*     */       return;
/*     */     } 
/* 299 */     this.stringStore.set(stringName, offset, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetString(String stringName, int offset) {
/* 305 */     stringName = stringName.toLowerCase();
/* 306 */     if (offset < 0) {
/*     */       
/* 308 */       unsetString(stringName);
/*     */       
/*     */       return;
/*     */     } 
/* 312 */     delete("&" + stringName, offset);
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveVariables(Document xml, Element node) {
/* 317 */     this.flagStore.save(xml, node);
/* 318 */     this.counterStore.save(xml, node);
/* 319 */     this.stringStore.save(xml, node);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadVariables(Node node) {
/* 324 */     IArrayStorageBundle arrayStorage = ArrayStorage.getStorageBundle((ArrayStorage<?>[])new ArrayStorage[] { this.flagStore, this.counterStore, this.stringStore });
/* 325 */     PropertiesXMLUtils.importProperties(arrayStorage, node);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\variable\VariableProviderArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */