/*    */ package org.khelekore.prtree;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Circle<T>
/*    */ {
/*    */   private final List<T> data;
/*    */   private int currentPos;
/*    */   
/*    */   public Circle(int size) {
/* 15 */     this.data = new ArrayList<T>(size);
/*    */   }
/*    */   
/*    */   public void add(T t) {
/* 19 */     this.data.add(t);
/*    */   }
/*    */   
/*    */   public T get(int pos) {
/* 23 */     pos %= this.data.size();
/* 24 */     return this.data.get(pos);
/*    */   }
/*    */   
/*    */   public int getNumElements() {
/* 28 */     return this.data.size();
/*    */   }
/*    */   
/*    */   public void reset() {
/* 32 */     this.currentPos = 0;
/*    */   }
/*    */   
/*    */   public T getNext() {
/* 36 */     T ret = this.data.get(this.currentPos++);
/* 37 */     this.currentPos %= this.data.size();
/* 38 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\org\khelekore\prtree\Circle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */