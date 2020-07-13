/*    */ package org.khelekore.prtree;
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
/*    */ public class MinDist
/*    */ {
/*    */   public static double get(MBR mbr, PointND p) {
/* 18 */     double res = 0.0D;
/* 19 */     for (int i = 0; i < p.getDimensions(); i++) {
/* 20 */       double o = p.getOrd(i);
/* 21 */       double rv = r(o, mbr.getMin(i), mbr.getMax(i));
/* 22 */       double dr = o - rv;
/* 23 */       res += dr * dr;
/*    */     } 
/* 25 */     return res;
/*    */   }
/*    */   
/*    */   private static double r(double x, double min, double max) {
/* 29 */     double r = x;
/* 30 */     if (x < min)
/* 31 */       r = min; 
/* 32 */     if (x > max)
/* 33 */       r = max; 
/* 34 */     return r;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\org\khelekore\prtree\MinDist.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */