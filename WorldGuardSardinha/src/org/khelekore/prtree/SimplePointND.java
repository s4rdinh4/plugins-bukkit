/*    */ package org.khelekore.prtree;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SimplePointND
/*    */   implements PointND
/*    */ {
/*    */   private final double[] ords;
/*    */   
/*    */   public SimplePointND(double... ords) {
/* 12 */     this.ords = ords;
/*    */   }
/*    */   
/*    */   public int getDimensions() {
/* 16 */     return this.ords.length;
/*    */   }
/*    */   
/*    */   public double getOrd(int axis) {
/* 20 */     return this.ords[axis];
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\org\khelekore\prtree\SimplePointND.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */