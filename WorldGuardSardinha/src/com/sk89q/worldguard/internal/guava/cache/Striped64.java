/*     */ package com.sk89q.worldguard.internal.guava.cache;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Random;
/*     */ import sun.misc.Unsafe;
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
/*     */ abstract class Striped64
/*     */   extends Number
/*     */ {
/*     */   static final class Cell
/*     */   {
/*     */     volatile long p0;
/*     */     volatile long p1;
/*     */     volatile long p2;
/*     */     volatile long p3;
/*     */     volatile long p4;
/*     */     volatile long p5;
/*     */     volatile long p6;
/*     */     volatile long value;
/*     */     volatile long q0;
/*     */     volatile long q1;
/*     */     volatile long q2;
/*     */     volatile long q3;
/*     */     volatile long q4;
/*     */     volatile long q5;
/*     */     volatile long q6;
/*     */     private static final Unsafe UNSAFE;
/*     */     private static final long valueOffset;
/*     */     
/*     */     Cell(long x) {
/* 116 */       this.value = x;
/*     */     }
/*     */     final boolean cas(long cmp, long val) {
/* 119 */       return UNSAFE.compareAndSwapLong(this, valueOffset, cmp, val);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */       try {
/* 127 */         UNSAFE = Striped64.getUnsafe();
/* 128 */         Class<?> ak = Cell.class;
/*     */         
/* 130 */         valueOffset = UNSAFE.objectFieldOffset(ak.getDeclaredField("value"));
/* 131 */       } catch (Exception e) {
/* 132 */         throw new Error(e);
/*     */       } 
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
/* 144 */   static final ThreadLocal<int[]> threadHashCode = (ThreadLocal)new ThreadLocal<int>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   static final Random rng = new Random();
/*     */ 
/*     */   
/* 152 */   static final int NCPU = Runtime.getRuntime().availableProcessors();
/*     */ 
/*     */ 
/*     */   
/*     */   volatile transient Cell[] cells;
/*     */ 
/*     */ 
/*     */   
/*     */   volatile transient long base;
/*     */ 
/*     */ 
/*     */   
/*     */   volatile transient int busy;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final Unsafe UNSAFE;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long baseOffset;
/*     */ 
/*     */   
/*     */   private static final long busyOffset;
/*     */ 
/*     */ 
/*     */   
/*     */   final boolean casBase(long cmp, long val) {
/* 180 */     return UNSAFE.compareAndSwapLong(this, baseOffset, cmp, val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final boolean casBusy() {
/* 187 */     return UNSAFE.compareAndSwapInt(this, busyOffset, 0, 1);
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
/*     */   final void retryUpdate(long x, int[] hc, boolean wasUncontended) {
/*     */     int h;
/* 214 */     if (hc == null) {
/* 215 */       threadHashCode.set(hc = new int[1]);
/* 216 */       int r = rng.nextInt();
/* 217 */       h = hc[0] = (r == 0) ? 1 : r;
/*     */     } else {
/*     */       
/* 220 */       h = hc[0];
/* 221 */     }  boolean collide = false; while (true) {
/*     */       Cell[] as;
/*     */       int n;
/* 224 */       if ((as = this.cells) != null && (n = as.length) > 0) {
/* 225 */         Cell a; if ((a = as[n - 1 & h]) == null)
/* 226 */         { if (this.busy == 0) {
/* 227 */             Cell r = new Cell(x);
/* 228 */             if (this.busy == 0 && casBusy()) {
/* 229 */               boolean created = false; try {
/*     */                 Cell[] rs; int m;
/*     */                 int j;
/* 232 */                 if ((rs = this.cells) != null && (m = rs.length) > 0 && rs[j = m - 1 & h] == null) {
/*     */ 
/*     */                   
/* 235 */                   rs[j] = r;
/* 236 */                   created = true;
/*     */                 } 
/*     */               } finally {
/* 239 */                 this.busy = 0;
/*     */               } 
/* 241 */               if (created)
/*     */                 break; 
/*     */               continue;
/*     */             } 
/*     */           } 
/* 246 */           collide = false; }
/*     */         
/* 248 */         else if (!wasUncontended)
/* 249 */         { wasUncontended = true; }
/* 250 */         else { long l; if (a.cas(l = a.value, fn(l, x)))
/*     */             break; 
/* 252 */           if (n >= NCPU || this.cells != as) {
/* 253 */             collide = false;
/* 254 */           } else if (!collide) {
/* 255 */             collide = true;
/* 256 */           } else if (this.busy == 0 && casBusy()) {
/*     */             try {
/* 258 */               if (this.cells == as) {
/* 259 */                 Cell[] rs = new Cell[n << 1];
/* 260 */                 for (int i = 0; i < n; i++)
/* 261 */                   rs[i] = as[i]; 
/* 262 */                 this.cells = rs;
/*     */               } 
/*     */             } finally {
/* 265 */               this.busy = 0;
/*     */             } 
/* 267 */             collide = false; continue;
/*     */           }  }
/*     */         
/* 270 */         h ^= h << 13;
/* 271 */         h ^= h >>> 17;
/* 272 */         h ^= h << 5;
/* 273 */         hc[0] = h; continue;
/*     */       } 
/* 275 */       if (this.busy == 0 && this.cells == as && casBusy()) {
/* 276 */         boolean init = false;
/*     */         try {
/* 278 */           if (this.cells == as) {
/* 279 */             Cell[] rs = new Cell[2];
/* 280 */             rs[h & 0x1] = new Cell(x);
/* 281 */             this.cells = rs;
/* 282 */             init = true;
/*     */           } 
/*     */         } finally {
/* 285 */           this.busy = 0;
/*     */         } 
/* 287 */         if (init)
/*     */           break;  continue;
/*     */       }  long v;
/* 290 */       if (casBase(v = this.base, fn(v, x))) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final void internalReset(long initialValue) {
/* 299 */     Cell[] as = this.cells;
/* 300 */     this.base = initialValue;
/* 301 */     if (as != null) {
/* 302 */       int n = as.length;
/* 303 */       for (int i = 0; i < n; i++) {
/* 304 */         Cell a = as[i];
/* 305 */         if (a != null) {
/* 306 */           a.value = initialValue;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 317 */       UNSAFE = getUnsafe();
/* 318 */       Class<?> sk = Striped64.class;
/*     */       
/* 320 */       baseOffset = UNSAFE.objectFieldOffset(sk.getDeclaredField("base"));
/*     */       
/* 322 */       busyOffset = UNSAFE.objectFieldOffset(sk.getDeclaredField("busy"));
/* 323 */     } catch (Exception e) {
/* 324 */       throw new Error(e);
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
/*     */   private static Unsafe getUnsafe() {
/*     */     try {
/* 337 */       return Unsafe.getUnsafe();
/* 338 */     } catch (SecurityException tryReflectionInstead) {
/*     */       
/*     */       try {
/* 341 */         return AccessController.<Unsafe>doPrivileged(new PrivilegedExceptionAction<Unsafe>() {
/*     */               public Unsafe run() throws Exception {
/* 343 */                 Class<Unsafe> k = Unsafe.class;
/* 344 */                 for (Field f : k.getDeclaredFields()) {
/* 345 */                   f.setAccessible(true);
/* 346 */                   Object x = f.get((Object)null);
/* 347 */                   if (k.isInstance(x))
/* 348 */                     return k.cast(x); 
/*     */                 } 
/* 350 */                 throw new NoSuchFieldError("the Unsafe"); }
/*     */             });
/* 352 */       } catch (PrivilegedActionException e) {
/* 353 */         throw new RuntimeException("Could not initialize intrinsics", e
/* 354 */             .getCause());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   abstract long fn(long paramLong1, long paramLong2);
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\guava\cache\Striped64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */