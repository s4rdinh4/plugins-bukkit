/*    */ package org.khelekore.prtree;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.PriorityQueue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class NearestNeighbour<T>
/*    */ {
/*    */   private final MBRConverter<T> converter;
/*    */   private final NodeFilter<T> filter;
/*    */   private final int maxHits;
/*    */   private final Node<T> root;
/*    */   private final DistanceCalculator<T> dc;
/*    */   private final PointND p;
/*    */   
/*    */   public NearestNeighbour(MBRConverter<T> converter, NodeFilter<T> filter, int maxHits, Node<T> root, DistanceCalculator<T> dc, PointND p) {
/* 24 */     this.converter = converter;
/* 25 */     this.filter = filter;
/* 26 */     this.maxHits = maxHits;
/* 27 */     this.root = root;
/* 28 */     this.dc = dc;
/* 29 */     this.p = p;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<DistanceResult<T>> find() {
/* 36 */     List<DistanceResult<T>> ret = new ArrayList<DistanceResult<T>>(this.maxHits);
/*    */     
/* 38 */     MinDistComparator<T, Node<T>> nc = new MinDistComparator<T, Node<T>>(this.converter, this.p);
/*    */     
/* 40 */     PriorityQueue<Node<T>> queue = new PriorityQueue<Node<T>>(20, nc);
/* 41 */     queue.add(this.root);
/* 42 */     while (!queue.isEmpty()) {
/* 43 */       Node<T> n = queue.remove();
/* 44 */       n.nnExpand(this.dc, this.filter, ret, this.maxHits, queue, nc);
/*    */     } 
/* 46 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\org\khelekore\prtree\NearestNeighbour.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */