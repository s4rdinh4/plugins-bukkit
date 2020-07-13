/*     */ package com.sk89q.worldguard.util.collect;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
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
/*     */ 
/*     */ 
/*     */ public class LongHashSet
/*     */   extends LongHash
/*     */ {
/*  29 */   protected long[][][] values = new long[256][][];
/*  30 */   protected int count = 0;
/*  31 */   protected ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
/*  32 */   protected ReentrantReadWriteLock.ReadLock rl = this.rwl.readLock();
/*  33 */   protected ReentrantReadWriteLock.WriteLock wl = this.rwl.writeLock();
/*     */   
/*     */   public boolean isEmpty() {
/*  36 */     this.rl.lock();
/*     */     try {
/*  38 */       return (this.count == 0);
/*     */     } finally {
/*  40 */       this.rl.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int size() {
/*  45 */     return this.count;
/*     */   }
/*     */   
/*     */   public void add(int msw, int lsw) {
/*  49 */     add(toLong(msw, lsw));
/*     */   }
/*     */   
/*     */   public void add(long key) {
/*  53 */     this.wl.lock();
/*     */     try {
/*  55 */       int mainIdx = (int)(key & 0xFFL);
/*  56 */       long[][] outer = this.values[mainIdx];
/*  57 */       if (outer == null) this.values[mainIdx] = outer = new long[256][];
/*     */       
/*  59 */       int outerIdx = (int)(key >> 32L & 0xFFL);
/*  60 */       long[] inner = outer[outerIdx];
/*     */       
/*  62 */       if (inner == null) {
/*  63 */         synchronized (this) {
/*  64 */           outer[outerIdx] = inner = new long[1];
/*  65 */           inner[0] = key;
/*  66 */           this.count++;
/*     */         } 
/*     */       } else {
/*     */         int i;
/*  70 */         for (i = 0; i < inner.length; i++) {
/*  71 */           if (inner[i] == key) {
/*     */             return;
/*     */           }
/*     */         } 
/*  75 */         inner = Arrays.copyOf(inner, i + 1);
/*  76 */         outer[outerIdx] = inner;
/*  77 */         inner[i] = key;
/*  78 */         this.count++;
/*     */       } 
/*     */     } finally {
/*  81 */       this.wl.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean containsKey(long key) {
/*  86 */     this.rl.lock();
/*     */     try {
/*  88 */       long[][] outer = this.values[(int)(key & 0xFFL)];
/*  89 */       if (outer == null) return false;
/*     */       
/*  91 */       long[] inner = outer[(int)(key >> 32L & 0xFFL)];
/*  92 */       if (inner == null) return false;
/*     */       
/*  94 */       for (long entry : inner) {
/*  95 */         if (entry == key) return true; 
/*     */       } 
/*  97 */       return false;
/*     */     } finally {
/*  99 */       this.rl.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void remove(long key) {
/* 104 */     this.wl.lock();
/*     */     try {
/* 106 */       long[][] outer = this.values[(int)(key & 0xFFL)];
/* 107 */       if (outer == null)
/*     */         return; 
/* 109 */       long[] inner = outer[(int)(key >> 32L & 0xFFL)];
/* 110 */       if (inner == null)
/*     */         return; 
/* 112 */       int max = inner.length - 1;
/* 113 */       for (int i = 0; i <= max; i++) {
/* 114 */         if (inner[i] == key) {
/* 115 */           this.count--;
/* 116 */           if (i != max) {
/* 117 */             inner[i] = inner[max];
/*     */           }
/*     */           
/* 120 */           outer[(int)(key >> 32L & 0xFFL)] = (max == 0) ? null : Arrays.copyOf(inner, max);
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 125 */       this.wl.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public long popFirst() {
/* 130 */     this.wl.lock();
/*     */     try {
/* 132 */       for (long[][] outer : this.values) {
/* 133 */         if (outer != null)
/*     */         {
/* 135 */           for (int i = 0; i < outer.length; ) {
/* 136 */             long[] inner = outer[i];
/* 137 */             if (inner == null || inner.length == 0) {
/*     */               i++; continue;
/* 139 */             }  this.count--;
/* 140 */             long ret = inner[inner.length - 1];
/* 141 */             outer[i] = Arrays.copyOf(inner, inner.length - 1);
/*     */             
/* 143 */             return ret;
/*     */           }  } 
/*     */       } 
/*     */     } finally {
/* 147 */       this.wl.unlock();
/*     */     } 
/* 149 */     return 0L;
/*     */   }
/*     */   
/*     */   public long[] popAll() {
/* 153 */     int index = 0;
/* 154 */     this.wl.lock();
/*     */     try {
/* 156 */       long[] ret = new long[this.count];
/* 157 */       for (long[][] outer : this.values) {
/* 158 */         if (outer != null)
/*     */         {
/* 160 */           for (int oIdx = outer.length - 1; oIdx >= 0; oIdx--) {
/* 161 */             long[] inner = outer[oIdx];
/* 162 */             if (inner != null) {
/*     */               
/* 164 */               for (long entry : inner) {
/* 165 */                 ret[index++] = entry;
/*     */               }
/* 167 */               outer[oIdx] = null;
/*     */             } 
/*     */           }  } 
/* 170 */       }  this.count = 0;
/* 171 */       return ret;
/*     */     } finally {
/* 173 */       this.wl.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public long[] keys() {
/* 178 */     int index = 0;
/* 179 */     this.rl.lock();
/*     */     try {
/* 181 */       long[] ret = new long[this.count];
/* 182 */       for (long[][] outer : this.values) {
/* 183 */         if (outer != null)
/*     */         {
/* 185 */           for (long[] inner : outer) {
/* 186 */             if (inner != null)
/*     */             {
/* 188 */               for (long entry : inner)
/* 189 */                 ret[index++] = entry;  } 
/*     */           } 
/*     */         }
/*     */       } 
/* 193 */       return ret;
/*     */     } finally {
/* 195 */       this.rl.unlock();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\collect\LongHashSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */