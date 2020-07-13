/*    */ package org.khelekore.prtree;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DistanceResult<T>
/*    */ {
/*    */   private final T t;
/*    */   private final double dist;
/*    */   
/*    */   public DistanceResult(T t, double dist) {
/* 15 */     this.t = t;
/* 16 */     this.dist = dist;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public T get() {
/* 23 */     return this.t;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getDistance() {
/* 30 */     return this.dist;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\org\khelekore\prtree\DistanceResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */