/*    */ package com.sk89q.worldguard.util.collect;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class LongHash
/*    */ {
/*    */   public static long toLong(int msw, int lsw) {
/* 25 */     return (msw << 32L) + lsw - -2147483648L;
/*    */   }
/*    */   
/*    */   public static int msw(long l) {
/* 29 */     return (int)(l >> 32L);
/*    */   }
/*    */   
/*    */   public static int lsw(long l) {
/* 33 */     return (int)(l & 0xFFFFFFFFFFFFFFFFL) + Integer.MIN_VALUE;
/*    */   }
/*    */   
/*    */   public boolean containsKey(int msw, int lsw) {
/* 37 */     return containsKey(toLong(msw, lsw));
/*    */   }
/*    */   
/*    */   public void remove(int msw, int lsw) {
/* 41 */     remove(toLong(msw, lsw));
/*    */   }
/*    */   
/*    */   public abstract boolean containsKey(long paramLong);
/*    */   
/*    */   public abstract void remove(long paramLong);
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\collect\LongHash.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */