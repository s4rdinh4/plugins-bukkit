/*     */ package net.eq2online.macros.scripting.variable;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import net.eq2online.xml.IArrayStorageBundle;
/*     */ import net.eq2online.xml.PropertiesXMLUtils;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
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
/*     */ public class ArrayStorage<T>
/*     */ {
/*  27 */   final Map<String, Map<Integer, T>> arrays = new HashMap<String, Map<Integer, T>>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  32 */   final Map<String, Integer> lengths = new HashMap<String, Integer>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String key;
/*     */ 
/*     */ 
/*     */   
/*     */   private final T defaultValue;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String prefix;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayStorage(String key, T defaultValue) {
/*  51 */     this(key, defaultValue, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayStorage(String key, T defaultValue, String prefix) {
/*  56 */     this.key = key;
/*  57 */     this.defaultValue = defaultValue;
/*  58 */     this.prefix = prefix;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getKey() {
/*  63 */     return this.key;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/*  68 */     return (this.prefix != null) ? this.prefix : "";
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getArrayNames() {
/*  73 */     return this.arrays.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   void updateArrayLengths() {
/*  78 */     this.lengths.clear();
/*     */     
/*  80 */     for (Map.Entry<String, ? extends Map<Integer, ?>> arrayEntry : this.arrays.entrySet())
/*     */     {
/*  82 */       updateArrayLength(arrayEntry.getKey(), arrayEntry.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateArrayLength(String name, Map<Integer, ?> array) {
/*  88 */     int max = -1;
/*  89 */     for (Integer pos : array.keySet())
/*     */     {
/*  91 */       max = Math.max(pos.intValue(), max);
/*     */     }
/*  93 */     this.lengths.put(name, Integer.valueOf(max));
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  98 */     this.arrays.clear();
/*  99 */     this.lengths.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   private final String trim(String arrayName) {
/* 104 */     return (this.prefix == null) ? arrayName : (arrayName.startsWith(this.prefix) ? arrayName.substring(this.prefix.length()) : arrayName);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean has(String arrayName) {
/* 109 */     return has0(trim(arrayName));
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean has0(String arrayName) {
/* 114 */     return this.arrays.containsKey(arrayName);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<Integer, T> lookup(String arrayName) {
/* 119 */     return lookup0(trim(arrayName));
/*     */   }
/*     */ 
/*     */   
/*     */   private Map<Integer, T> lookup0(String arrayName) {
/* 124 */     return this.arrays.get(arrayName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(String arrayName) {
/* 129 */     remove0(trim(arrayName));
/*     */   }
/*     */ 
/*     */   
/*     */   private void remove0(String arrayName) {
/* 134 */     this.arrays.remove(arrayName);
/* 135 */     this.lengths.remove(arrayName);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxIndex(String arrayName) {
/* 140 */     return getMaxArrayIndex0(trim(arrayName));
/*     */   }
/*     */ 
/*     */   
/*     */   private int getMaxArrayIndex0(String arrayName) {
/* 145 */     return this.lengths.containsKey(arrayName) ? ((Integer)this.lengths.get(arrayName)).intValue() : -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFreeIndex(String arrayName) {
/* 150 */     arrayName = trim(arrayName);
/* 151 */     Map<Integer, T> map = lookup0(arrayName);
/*     */     
/* 153 */     if (map == null) return 0; 
/* 154 */     int maxIndex = getMaxArrayIndex0(arrayName);
/* 155 */     int pos = 0;
/* 156 */     for (; pos <= maxIndex; pos++) {
/*     */       
/* 158 */       if (!map.containsKey(Integer.valueOf(pos))) return pos;
/*     */     
/*     */     } 
/* 161 */     return pos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getValue(String arrayName, int arrayIndex) {
/* 169 */     Map<Integer, T> map = lookup(arrayName);
/* 170 */     if (map == null) return null; 
/* 171 */     T value = map.get(Integer.valueOf(arrayIndex));
/* 172 */     return (value != null) ? value : this.defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getNotNull(String arrayName, int arrayIndex) {
/* 180 */     T value = get(arrayName, arrayIndex);
/* 181 */     return (value != null) ? value : this.defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T get(String arrayName, int arrayIndex) {
/* 189 */     Map<Integer, T> map = lookup(arrayName);
/* 190 */     return (map != null) ? map.get(Integer.valueOf(arrayIndex)) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   private T get0(String arrayName, int arrayIndex) {
/* 195 */     Map<Integer, T> map = lookup0(arrayName);
/* 196 */     return (map != null) ? map.get(Integer.valueOf(arrayIndex)) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(String arrayName, int offset, T value) {
/* 201 */     set0(trim(arrayName), offset, value);
/*     */   }
/*     */ 
/*     */   
/*     */   private void set0(String arrayName, int offset, T value) {
/* 206 */     Map<Integer, T> map = lookup0(arrayName);
/* 207 */     if (map == null) {
/*     */       
/* 209 */       map = new TreeMap<Integer, T>();
/* 210 */       this.arrays.put(arrayName, map);
/* 211 */       this.lengths.put(arrayName, Integer.valueOf(0));
/*     */     } 
/*     */     
/* 214 */     map.put(Integer.valueOf(offset), value);
/*     */     
/* 216 */     if (offset > getMaxArrayIndex0(arrayName))
/*     */     {
/* 218 */       this.lengths.put(arrayName, Integer.valueOf(offset));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public T pop(String arrayName, int popIndex) {
/* 224 */     arrayName = trim(arrayName);
/* 225 */     return pop0(arrayName, popIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   private T pop0(String arrayName, int popIndex) {
/* 230 */     Map<Integer, T> array = lookup0(arrayName);
/*     */     
/* 232 */     if (array != null) {
/*     */       
/* 234 */       T valueAt = get0(arrayName, popIndex);
/* 235 */       array.remove(Integer.valueOf(popIndex));
/* 236 */       this.lengths.put(arrayName, Integer.valueOf(popIndex - 1));
/*     */       
/* 238 */       if (popIndex == 0)
/*     */       {
/* 240 */         remove0(arrayName);
/*     */       }
/*     */       
/* 243 */       return valueAt;
/*     */     } 
/*     */     
/* 246 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void delete(String arrayName, int offset) {
/* 251 */     delete0(trim(arrayName), offset);
/*     */   }
/*     */ 
/*     */   
/*     */   private void delete0(String arrayName, int offset) {
/* 256 */     Map<Integer, T> array = lookup0(arrayName);
/*     */     
/* 258 */     if (array == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 263 */     if (array.remove(Integer.valueOf(offset)) != null) {
/*     */       
/* 265 */       this.lengths.remove(arrayName);
/*     */       
/* 267 */       if (array.size() == 0) {
/*     */         
/* 269 */         array.remove(arrayName);
/*     */         
/*     */         return;
/*     */       } 
/* 273 */       updateArrayLength(arrayName, array);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static IArrayStorageBundle getStorageBundle(ArrayStorage<?>... stores) {
/* 279 */     return new ArrayStorageBundle(stores);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(Document xml, Element node) {
/* 285 */     PropertiesXMLUtils.serialiseArrays(this.key, this.arrays, xml, node);
/*     */   }
/*     */   
/*     */   static class ArrayStorageBundle
/*     */     implements IArrayStorageBundle
/*     */   {
/*     */     private final ArrayStorage<?>[] stores;
/* 292 */     private final Map<String, Map<String, Map<Integer, ?>>> arrayStorage = new HashMap<String, Map<String, Map<Integer, ?>>>();
/*     */ 
/*     */     
/*     */     public ArrayStorageBundle(ArrayStorage<?>... stores) {
/* 296 */       this.stores = stores;
/*     */       
/* 298 */       for (ArrayStorage<?> store : stores) {
/*     */ 
/*     */         
/* 301 */         Map<String, Map<Integer, ?>> arrays = store.arrays;
/* 302 */         this.arrayStorage.put(store.getKey(), arrays);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<String> getStorageTypes() {
/* 309 */       return this.arrayStorage.keySet();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Map<String, Map<Integer, ?>> getStorage(String storageType) {
/* 315 */       return this.arrayStorage.get(storageType);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void preDeserialise() {
/* 321 */       for (ArrayStorage<?> store : this.stores)
/*     */       {
/* 323 */         store.clear();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void preSerialise() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void postDeserialise() {
/* 335 */       for (ArrayStorage<?> store : this.stores)
/*     */       {
/* 337 */         store.updateArrayLengths();
/*     */       }
/*     */     }
/*     */     
/*     */     public void postSerialise() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\variable\ArrayStorage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */