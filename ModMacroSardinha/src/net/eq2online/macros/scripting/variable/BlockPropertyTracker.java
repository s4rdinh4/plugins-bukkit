/*     */ package net.eq2online.macros.scripting.variable;
/*     */ 
/*     */ import atr;
/*     */ import bec;
/*     */ import bed;
/*     */ import bex;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockPropertyTracker
/*     */ {
/*     */   private final String prefix;
/*     */   private final IVariableStore store;
/*     */   private final boolean clear;
/*  23 */   private Set<String> strings = new HashSet<String>();
/*     */   
/*  25 */   private Set<String> numbers = new HashSet<String>();
/*     */   
/*  27 */   private Set<String> flags = new HashSet<String>();
/*     */   
/*     */   private bec currentState;
/*     */ 
/*     */   
/*     */   public BlockPropertyTracker(String prefix, IVariableStore store) {
/*  33 */     this(prefix, store, true, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPropertyTracker(String prefix, IVariableStore store, boolean clear) {
/*  38 */     this(prefix, store, clear, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPropertyTracker(String prefix, IVariableStore store, boolean clear, bec initialState) {
/*  43 */     this.prefix = prefix;
/*  44 */     this.store = store;
/*  45 */     this.clear = clear;
/*     */     
/*  47 */     init();
/*  48 */     clear();
/*  49 */     update(initialState);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void init() {
/*  55 */     for (Iterator<atr> iter = atr.c.iterator(); iter.hasNext(); ) {
/*     */       
/*  57 */       bed blockState = ((atr)iter.next()).O();
/*  58 */       for (bex property : blockState.d()) {
/*     */         
/*  60 */         String propertyName = getPropertyName(property);
/*     */         
/*  62 */         if (property instanceof bet) {
/*     */           
/*  64 */           this.flags.add(propertyName); continue;
/*     */         } 
/*  66 */         if (property instanceof bew) {
/*     */           
/*  68 */           this.numbers.add(propertyName);
/*     */           
/*     */           continue;
/*     */         } 
/*  72 */         this.strings.add(propertyName);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  77 */     this.flags.removeAll(this.strings);
/*  78 */     this.flags.remove(this.numbers);
/*  79 */     this.numbers.removeAll(this.strings);
/*     */   }
/*     */ 
/*     */   
/*     */   private String getPropertyName(bex property) {
/*  84 */     return this.prefix + property.a().toUpperCase();
/*     */   }
/*     */ 
/*     */   
/*     */   private void clear() {
/*  89 */     if (!this.clear)
/*     */       return; 
/*  91 */     for (String string : this.strings)
/*     */     {
/*  93 */       this.store.storeVariable(string, "");
/*     */     }
/*     */     
/*  96 */     for (String number : this.numbers)
/*     */     {
/*  98 */       this.store.storeVariable(number, 0);
/*     */     }
/*     */     
/* 101 */     for (String flag : this.flags)
/*     */     {
/* 103 */       this.store.storeVariable(flag, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(bec state) {
/* 109 */     if (this.currentState != state) {
/*     */       
/* 111 */       clear();
/* 112 */       this.currentState = state;
/*     */       
/* 114 */       if (state != null)
/*     */       {
/* 116 */         setProperties(state);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setProperties(bec state) {
/* 124 */     for (bex property : state.a()) {
/*     */       
/* 126 */       String propertyName = getPropertyName(property);
/* 127 */       if (property instanceof bet && this.flags.contains(propertyName)) {
/*     */         
/* 129 */         this.store.storeVariable(propertyName, ((Boolean)state.b(property)).booleanValue()); continue;
/*     */       } 
/* 131 */       if (property instanceof bew && this.numbers.contains(propertyName)) {
/*     */         
/* 133 */         this.store.storeVariable(propertyName, ((Integer)state.b(property)).intValue());
/*     */         
/*     */         continue;
/*     */       } 
/* 137 */       this.store.storeVariable(propertyName, state.b(property).toString());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\variable\BlockPropertyTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */