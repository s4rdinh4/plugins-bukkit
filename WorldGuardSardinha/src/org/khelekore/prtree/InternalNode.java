/*    */ package org.khelekore.prtree;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.PriorityQueue;
/*    */ 
/*    */ class InternalNode<T> extends NodeBase<Node<T>, T> {
/*    */   public InternalNode(Object[] data) {
/*  8 */     super(data);
/*    */   }
/*    */   
/*    */   public MBR computeMBR(MBRConverter<T> converter) {
/* 12 */     MBR ret = null;
/* 13 */     for (int i = 0, s = size(); i < s; i++)
/* 14 */       ret = getUnion(ret, get(i).getMBR(converter)); 
/* 15 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public void expand(MBR mbr, MBRConverter<T> converter, List<T> found, List<Node<T>> nodesToExpand) {
/* 20 */     for (int i = 0, s = size(); i < s; i++) {
/* 21 */       Node<T> n = get(i);
/* 22 */       if (mbr.intersects(n.getMBR(converter)))
/* 23 */         nodesToExpand.add(n); 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void find(MBR mbr, MBRConverter<T> converter, List<T> result) {
/* 28 */     for (int i = 0, s = size(); i < s; i++) {
/* 29 */       Node<T> n = get(i);
/* 30 */       if (mbr.intersects(n.getMBR(converter))) {
/* 31 */         n.find(mbr, converter, result);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void nnExpand(DistanceCalculator<T> dc, NodeFilter<T> filter, List<DistanceResult<T>> drs, int maxHits, PriorityQueue<Node<T>> queue, MinDistComparator<T, Node<T>> mdc) {
/* 41 */     int s = size();
/* 42 */     for (int i = 0; i < s; i++) {
/* 43 */       Node<T> n = get(i);
/* 44 */       MBR mbr = n.getMBR(mdc.converter);
/* 45 */       double minDist = MinDist.get(mbr, mdc.p);
/* 46 */       int t = drs.size();
/*    */       
/* 48 */       if (t < maxHits || minDist <= ((DistanceResult)drs.get(t - 1)).getDistance())
/* 49 */         queue.add(n); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\org\khelekore\prtree\InternalNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */