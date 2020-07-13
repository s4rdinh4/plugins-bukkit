/*    */ package org.khelekore.prtree;
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class NodeBase<N, T>
/*    */   implements Node<T>
/*    */ {
/*    */   private MBR mbr;
/*    */   private Object[] data;
/*    */   
/*    */   public NodeBase(Object[] data) {
/* 12 */     this.data = data;
/*    */   }
/*    */   
/*    */   public int size() {
/* 16 */     return this.data.length;
/*    */   }
/*    */ 
/*    */   
/*    */   public N get(int i) {
/* 21 */     return (N)this.data[i];
/*    */   }
/*    */   
/*    */   public MBR getMBR(MBRConverter<T> converter) {
/* 25 */     if (this.mbr == null)
/* 26 */       this.mbr = computeMBR(converter); 
/* 27 */     return this.mbr;
/*    */   }
/*    */   
/*    */   public abstract MBR computeMBR(MBRConverter<T> paramMBRConverter);
/*    */   
/*    */   public MBR getUnion(MBR m1, MBR m2) {
/* 33 */     if (m1 == null)
/* 34 */       return m2; 
/* 35 */     return m1.union(m2);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\org\khelekore\prtree\NodeBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */