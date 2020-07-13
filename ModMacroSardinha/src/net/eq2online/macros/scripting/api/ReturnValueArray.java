/*     */ package net.eq2online.macros.scripting.api;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ 
/*     */ public class ReturnValueArray
/*     */   implements IReturnValueArray
/*     */ {
/*  11 */   private List<Boolean> bools = new LinkedList<Boolean>();
/*     */   
/*  13 */   private List<Integer> ints = new LinkedList<Integer>();
/*     */   
/*  15 */   private List<String> strings = new LinkedList<String>();
/*     */   
/*     */   private boolean append;
/*     */ 
/*     */   
/*     */   public ReturnValueArray(boolean append) {
/*  21 */     this.append = append;
/*     */   }
/*     */ 
/*     */   
/*     */   public void putStrings(List<String> strings) {
/*  26 */     this.strings = strings;
/*  27 */     this.ints.clear();
/*  28 */     this.bools.clear();
/*     */     
/*  30 */     for (String value : this.strings) {
/*     */       
/*  32 */       int intVal = ScriptCore.tryParseInt(value, 0);
/*  33 */       this.ints.add(Integer.valueOf(intVal));
/*  34 */       this.bools.add(Boolean.valueOf((value == null || value.toLowerCase().equals("true") || intVal != 0)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void putInts(List<Integer> ints) {
/*  40 */     this.ints = ints;
/*  41 */     this.strings.clear();
/*  42 */     this.bools.clear();
/*     */     
/*  44 */     for (Integer value : this.ints) {
/*     */       
/*  46 */       this.strings.add(String.valueOf(value));
/*  47 */       this.bools.add(Boolean.valueOf((value.intValue() != 0)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void putBools(List<Boolean> bools) {
/*  53 */     this.bools = bools;
/*  54 */     this.strings.clear();
/*  55 */     this.ints.clear();
/*     */     
/*  57 */     for (Boolean value : this.bools) {
/*     */       
/*  59 */       this.strings.add(value.booleanValue() ? "True" : "False");
/*  60 */       this.ints.add(Integer.valueOf(value.booleanValue() ? 1 : 0));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVoid() {
/*  67 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBoolean() {
/*  73 */     return (this.bools.size() > 0) ? ((Boolean)this.bools.get(0)).booleanValue() : false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInteger() {
/*  79 */     return (this.ints.size() > 0) ? ((Integer)this.ints.get(0)).intValue() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString() {
/*  85 */     return (this.strings.size() > 0) ? this.strings.get(0) : "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalMessage() {
/*  91 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRemoteMessage() {
/*  97 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 103 */     return this.strings.size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldAppend() {
/* 109 */     return this.append;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Boolean> getBooleans() {
/* 115 */     return Collections.unmodifiableList(this.bools);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Integer> getIntegers() {
/* 121 */     return Collections.unmodifiableList(this.ints);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getStrings() {
/* 127 */     return Collections.unmodifiableList(this.strings);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\api\ReturnValueArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */