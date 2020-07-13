/*    */ package org.khelekore.prtree;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class NodeUsage<T>
/*    */ {
/*    */   private final T data;
/*    */   private int id;
/*    */   
/*    */   public NodeUsage(T data, int id) {
/* 13 */     this.data = data;
/* 14 */     this.id = id;
/*    */   }
/*    */   
/*    */   public T getData() {
/* 18 */     return this.data;
/*    */   }
/*    */   
/*    */   public int getOwner() {
/* 22 */     return this.id;
/*    */   }
/*    */   
/*    */   public void changeOwner(int id) {
/* 26 */     this.id = id;
/*    */   }
/*    */   
/*    */   public void use() {
/* 30 */     if (this.id >= 0) {
/* 31 */       this.id = -this.id;
/*    */     } else {
/* 33 */       throw new RuntimeException("Trying to use already used node");
/*    */     } 
/*    */   }
/*    */   public boolean isUsed() {
/* 37 */     return (this.id < 0);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 41 */     return getClass().getSimpleName() + "{data: " + this.data + ", id: " + this.id + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\org\khelekore\prtree\NodeUsage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */