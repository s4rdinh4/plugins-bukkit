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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MinDist2D
/*    */ {
/*    */   public static double get(double minx, double miny, double maxx, double maxy, double x, double y) {
/* 24 */     double rx = r(x, minx, maxx);
/* 25 */     double ry = r(y, miny, maxy);
/* 26 */     double xd = x - rx;
/* 27 */     double yd = y - ry;
/* 28 */     return xd * xd + yd * yd;
/*    */   }
/*    */   
/*    */   private static double r(double x, double min, double max) {
/* 32 */     double r = x;
/* 33 */     if (x < min)
/* 34 */       r = min; 
/* 35 */     if (x > max)
/* 36 */       r = max; 
/* 37 */     return r;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\org\khelekore\prtree\MinDist2D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */