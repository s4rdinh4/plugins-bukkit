/*    */ package org.khelekore.prtree;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SimpleMBR
/*    */   implements MBR
/*    */ {
/*    */   private final double[] values;
/*    */   
/*    */   private SimpleMBR(int dimensions) {
/* 13 */     this.values = new double[dimensions * 2];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SimpleMBR(double... values) {
/* 21 */     this.values = (double[])values.clone();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public <T> SimpleMBR(T t, MBRConverter<T> converter) {
/* 29 */     int dims = converter.getDimensions();
/* 30 */     this.values = new double[dims * 2];
/* 31 */     int p = 0;
/* 32 */     for (int i = 0; i < dims; i++) {
/* 33 */       this.values[p++] = converter.getMin(i, t);
/* 34 */       this.values[p++] = converter.getMax(i, t);
/*    */     } 
/*    */   }
/*    */   
/*    */   public int getDimensions() {
/* 39 */     return this.values.length / 2;
/*    */   }
/*    */   
/*    */   public double getMin(int axis) {
/* 43 */     return this.values[axis * 2];
/*    */   }
/*    */   
/*    */   public double getMax(int axis) {
/* 47 */     return this.values[axis * 2 + 1];
/*    */   }
/*    */   
/*    */   public MBR union(MBR mbr) {
/* 51 */     int dims = getDimensions();
/* 52 */     SimpleMBR n = new SimpleMBR(dims);
/* 53 */     int p = 0;
/* 54 */     for (int i = 0; i < dims; i++) {
/* 55 */       n.values[p] = Math.min(getMin(i), mbr.getMin(i));
/* 56 */       p++;
/* 57 */       n.values[p] = Math.max(getMax(i), mbr.getMax(i));
/* 58 */       p++;
/*    */     } 
/* 60 */     return n;
/*    */   }
/*    */   
/*    */   public boolean intersects(MBR other) {
/* 64 */     for (int i = 0; i < getDimensions(); i++) {
/* 65 */       if (other.getMax(i) < getMin(i) || other.getMin(i) > getMax(i))
/* 66 */         return false; 
/*    */     } 
/* 68 */     return true;
/*    */   }
/*    */   
/*    */   public <T> boolean intersects(T t, MBRConverter<T> converter) {
/* 72 */     for (int i = 0; i < getDimensions(); i++) {
/* 73 */       if (converter.getMax(i, t) < getMin(i) || converter.getMin(i, t) > getMax(i))
/*    */       {
/* 75 */         return false; } 
/*    */     } 
/* 77 */     return true;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 81 */     return getClass().getSimpleName() + "{values: " + Arrays.toString(this.values) + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\org\khelekore\prtree\SimpleMBR.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */