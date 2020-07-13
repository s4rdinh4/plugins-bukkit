/*    */ package com.sk89q.worldguard.util.collect;
/*    */ 
/*    */ import java.util.ArrayList;
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
/*    */ public class LongHashTable<V>
/*    */   extends LongBaseHashTable
/*    */ {
/*    */   public void put(int msw, int lsw, V value) {
/* 27 */     put(toLong(msw, lsw), value);
/*    */   }
/*    */   
/*    */   public V get(int msw, int lsw) {
/* 31 */     return get(toLong(msw, lsw));
/*    */   }
/*    */   
/*    */   public synchronized void put(long key, V value) {
/* 35 */     put(new Entry(key, value));
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized V get(long key) {
/* 40 */     Entry entry = (Entry)getEntry(key);
/* 41 */     return (entry != null) ? entry.value : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized ArrayList<V> values() {
/* 46 */     ArrayList<V> ret = new ArrayList<V>();
/*    */     
/* 48 */     ArrayList<EntryBase> entries = entries();
/*    */     
/* 50 */     for (EntryBase entry : entries) {
/* 51 */       ret.add(((Entry)entry).value);
/*    */     }
/* 53 */     return ret;
/*    */   }
/*    */   
/*    */   private class Entry extends EntryBase {
/*    */     V value;
/*    */     
/*    */     Entry(long k, V v) {
/* 60 */       super(k);
/* 61 */       this.value = v;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\collect\LongHashTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */