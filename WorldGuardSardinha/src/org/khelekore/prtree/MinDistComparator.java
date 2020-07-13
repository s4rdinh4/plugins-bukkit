/*    */ package org.khelekore.prtree;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ class MinDistComparator<T, S extends Node<T>>
/*    */   implements Comparator<S>
/*    */ {
/*    */   public final MBRConverter<T> converter;
/*    */   public final PointND p;
/*    */   
/*    */   public MinDistComparator(MBRConverter<T> converter, PointND p) {
/* 14 */     this.converter = converter;
/* 15 */     this.p = p;
/*    */   }
/*    */   
/*    */   public int compare(S t1, S t2) {
/* 19 */     MBR mbr1 = t1.getMBR(this.converter);
/* 20 */     MBR mbr2 = t2.getMBR(this.converter);
/* 21 */     return Double.compare(MinDist.get(mbr1, this.p), MinDist.get(mbr2, this.p));
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\org\khelekore\prtree\MinDistComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */