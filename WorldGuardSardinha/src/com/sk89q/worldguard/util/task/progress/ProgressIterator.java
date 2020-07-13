/*    */ package com.sk89q.worldguard.util.task.progress;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
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
/*    */ public class ProgressIterator<V>
/*    */   implements Iterator<V>, ProgressObservable
/*    */ {
/*    */   private final Iterator<V> iterator;
/*    */   private final int count;
/* 41 */   private int visited = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private ProgressIterator(Iterator<V> iterator, int count) {
/* 50 */     Preconditions.checkNotNull(iterator);
/* 51 */     this.iterator = iterator;
/* 52 */     this.count = count;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasNext() {
/* 57 */     return this.iterator.hasNext();
/*    */   }
/*    */ 
/*    */   
/*    */   public V next() {
/* 62 */     V value = this.iterator.next();
/* 63 */     this.visited++;
/* 64 */     return value;
/*    */   }
/*    */ 
/*    */   
/*    */   public void remove() {
/* 69 */     this.iterator.remove();
/*    */   }
/*    */ 
/*    */   
/*    */   public Progress getProgress() {
/* 74 */     return Progress.of((this.count > 0) ? Math.min(1.0D, Math.max(0.0D, this.visited / this.count)) : 1.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <V> ProgressIterator<V> create(Iterator<V> iterator, int count) {
/* 86 */     return new ProgressIterator<V>(iterator, count);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <V> ProgressIterator<V> create(List<V> list) {
/* 97 */     return create(list.iterator(), list.size());
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\task\progress\ProgressIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */