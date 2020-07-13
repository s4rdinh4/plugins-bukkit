/*    */ package org.khelekore.prtree;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.PriorityQueue;
/*    */ 
/*    */ class LeafNode<T>
/*    */   extends NodeBase<T, T> {
/*    */   public LeafNode(Object[] data) {
/* 11 */     super(data);
/*    */   }
/*    */   
/*    */   public MBR getMBR(T t, MBRConverter<T> converter) {
/* 15 */     return new SimpleMBR(t, converter);
/*    */   }
/*    */   
/*    */   public MBR computeMBR(MBRConverter<T> converter) {
/* 19 */     MBR ret = null;
/* 20 */     for (int i = 0, s = size(); i < s; i++)
/* 21 */       ret = getUnion(ret, getMBR(get(i), converter)); 
/* 22 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public void expand(MBR mbr, MBRConverter<T> converter, List<T> found, List<Node<T>> nodesToExpand) {
/* 27 */     find(mbr, converter, found);
/*    */   }
/*    */   
/*    */   public void find(MBR mbr, MBRConverter<T> converter, List<T> result) {
/* 31 */     for (int i = 0, s = size(); i < s; i++) {
/* 32 */       T t = get(i);
/* 33 */       if (mbr.intersects(t, converter)) {
/* 34 */         result.add(t);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void nnExpand(DistanceCalculator<T> dc, NodeFilter<T> filter, List<DistanceResult<T>> drs, int maxHits, PriorityQueue<Node<T>> queue, MinDistComparator<T, Node<T>> mdc) {
/* 44 */     for (int i = 0, s = size(); i < s; i++) {
/* 45 */       T t = get(i);
/* 46 */       if (filter.accept(t)) {
/* 47 */         double dist = dc.distanceTo(t, mdc.p);
/* 48 */         int n = drs.size();
/* 49 */         if (n < maxHits || dist < ((DistanceResult)drs.get(n - 1)).getDistance()) {
/* 50 */           add(drs, new DistanceResult<T>(t, dist), maxHits);
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void add(List<DistanceResult<T>> drs, DistanceResult<T> dr, int maxHits) {
/* 59 */     int n = drs.size();
/* 60 */     if (n == maxHits)
/* 61 */       drs.remove(n - 1); 
/* 62 */     int pos = Collections.binarySearch(drs, dr, comp);
/* 63 */     if (pos < 0)
/*    */     {
/* 65 */       pos = -(pos + 1);
/*    */     }
/* 67 */     drs.add(pos, dr);
/*    */   }
/*    */   
/* 70 */   private static final Comparator<DistanceResult<?>> comp = new Comparator<DistanceResult<?>>()
/*    */     {
/*    */       public int compare(DistanceResult<?> d1, DistanceResult<?> d2) {
/* 73 */         return Double.compare(d1.getDistance(), d2.getDistance());
/*    */       }
/*    */     };
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\org\khelekore\prtree\LeafNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */