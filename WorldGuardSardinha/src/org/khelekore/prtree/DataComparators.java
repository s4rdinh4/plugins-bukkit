/*    */ package org.khelekore.prtree;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ class DataComparators<T> implements NodeComparators<T> {
/*    */   private final MBRConverter<T> converter;
/*    */   
/*    */   public DataComparators(MBRConverter<T> converter) {
/*  9 */     this.converter = converter;
/*    */   }
/*    */   
/*    */   public Comparator<T> getMinComparator(final int axis) {
/* 13 */     return new Comparator<T>() {
/*    */         public int compare(T t1, T t2) {
/* 15 */           double d1 = DataComparators.this.converter.getMin(axis, t1);
/* 16 */           double d2 = DataComparators.this.converter.getMin(axis, t2);
/* 17 */           return Double.compare(d1, d2);
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   public Comparator<T> getMaxComparator(final int axis) {
/* 23 */     return new Comparator<T>() {
/*    */         public int compare(T t1, T t2) {
/* 25 */           double d1 = DataComparators.this.converter.getMax(axis, t1);
/* 26 */           double d2 = DataComparators.this.converter.getMax(axis, t2);
/* 27 */           return Double.compare(d1, d2);
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\org\khelekore\prtree\DataComparators.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */