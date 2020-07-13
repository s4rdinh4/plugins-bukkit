/*     */ package com.sk89q.worldguard.util.collect;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
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
/*     */ public class LongBaseHashTable
/*     */   extends LongHash
/*     */ {
/*  27 */   EntryBase[][][] values = new EntryBase[256][][];
/*  28 */   EntryBase cache = null;
/*     */   
/*     */   public void put(int msw, int lsw, EntryBase entry) {
/*  31 */     put(entry);
/*     */   }
/*     */   
/*     */   public EntryBase getEntry(int msw, int lsw) {
/*  35 */     return getEntry(toLong(msw, lsw));
/*     */   }
/*     */   
/*     */   public synchronized void put(EntryBase entry) {
/*  39 */     int mainIdx = (int)(entry.key & 0xFFL);
/*  40 */     EntryBase[][] outer = this.values[mainIdx];
/*  41 */     if (outer == null) this.values[mainIdx] = outer = new EntryBase[256][];
/*     */     
/*  43 */     int outerIdx = (int)(entry.key >> 32L & 0xFFL);
/*  44 */     EntryBase[] inner = outer[outerIdx];
/*     */     
/*  46 */     if (inner == null) {
/*  47 */       outer[outerIdx] = inner = new EntryBase[5];
/*  48 */       inner[0] = this.cache = entry;
/*     */     } else {
/*     */       int i;
/*  51 */       for (i = 0; i < inner.length; i++) {
/*  52 */         if (inner[i] == null || (inner[i]).key == entry.key) {
/*  53 */           inner[i] = this.cache = entry;
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*  58 */       outer[outerIdx] = inner = Arrays.copyOf(inner, i + i);
/*  59 */       inner[i] = entry;
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized EntryBase getEntry(long key) {
/*  64 */     return containsKey(key) ? this.cache : null;
/*     */   }
/*     */   
/*     */   public synchronized boolean containsKey(long key) {
/*  68 */     if (this.cache != null && this.cache.key == key) return true;
/*     */     
/*  70 */     int outerIdx = (int)(key >> 32L & 0xFFL);
/*  71 */     EntryBase[][] outer = this.values[(int)(key & 0xFFL)];
/*  72 */     if (outer == null) return false;
/*     */     
/*  74 */     EntryBase[] inner = outer[outerIdx];
/*  75 */     if (inner == null) return false;
/*     */     
/*  77 */     for (int i = 0; i < inner.length; i++) {
/*  78 */       EntryBase e = inner[i];
/*  79 */       if (e == null)
/*  80 */         return false; 
/*  81 */       if (e.key == key) {
/*  82 */         this.cache = e;
/*  83 */         return true;
/*     */       } 
/*     */     } 
/*  86 */     return false;
/*     */   }
/*     */   
/*     */   public synchronized void remove(long key) {
/*  90 */     EntryBase[][] outer = this.values[(int)(key & 0xFFL)];
/*  91 */     if (outer == null)
/*     */       return; 
/*  93 */     EntryBase[] inner = outer[(int)(key >> 32L & 0xFFL)];
/*  94 */     if (inner == null)
/*     */       return; 
/*  96 */     for (int i = 0; i < inner.length; i++) {
/*  97 */       if (inner[i] != null)
/*     */       {
/*  99 */         if ((inner[i]).key == key) {
/* 100 */           for (; ++i < inner.length && 
/* 101 */             inner[i] != null; i++) {
/* 102 */             inner[i - 1] = inner[i];
/*     */           }
/*     */           
/* 105 */           inner[i - 1] = null;
/* 106 */           this.cache = null;
/*     */           return;
/*     */         }  } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized ArrayList<EntryBase> entries() {
/* 113 */     ArrayList<EntryBase> ret = new ArrayList<EntryBase>();
/*     */     
/* 115 */     for (EntryBase[][] outer : this.values) {
/* 116 */       if (outer != null)
/*     */       {
/* 118 */         for (EntryBase[] inner : outer) {
/* 119 */           if (inner != null)
/*     */           {
/* 121 */             for (EntryBase entry : inner) {
/* 122 */               if (entry == null)
/*     */                 break; 
/* 124 */               ret.add(entry);
/*     */             }  } 
/*     */         }  } 
/*     */     } 
/* 128 */     return ret;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\collect\LongBaseHashTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */