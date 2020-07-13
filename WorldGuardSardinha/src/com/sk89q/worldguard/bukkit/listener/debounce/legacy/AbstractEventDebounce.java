/*    */ package com.sk89q.worldguard.bukkit.listener.debounce.legacy;
/*    */ 
/*    */ import com.sk89q.worldguard.bukkit.util.Events;
/*    */ import com.sk89q.worldguard.internal.guava.cache.CacheBuilder;
/*    */ import com.sk89q.worldguard.internal.guava.cache.CacheLoader;
/*    */ import com.sk89q.worldguard.internal.guava.cache.LoadingCache;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
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
/*    */ public class AbstractEventDebounce<K>
/*    */ {
/*    */   private final LoadingCache<K, Entry> cache;
/*    */   
/*    */   AbstractEventDebounce(int debounceTime) {
/* 37 */     this
/*    */ 
/*    */ 
/*    */       
/* 41 */       .cache = CacheBuilder.newBuilder().maximumSize(1000L).expireAfterWrite(debounceTime, TimeUnit.MILLISECONDS).concurrencyLevel(2).build(new CacheLoader<K, Entry>()
/*    */         {
/*    */           public AbstractEventDebounce.Entry load(K key) throws Exception {
/* 44 */             return new AbstractEventDebounce.Entry();
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   protected <T extends Event & Cancellable> void debounce(K key, Cancellable originalEvent, T firedEvent) {
/* 50 */     Entry entry = (Entry)this.cache.getUnchecked(key);
/* 51 */     if (entry.cancelled != null) {
/* 52 */       if (entry.cancelled.booleanValue()) {
/* 53 */         originalEvent.setCancelled(true);
/*    */       }
/*    */     } else {
/* 56 */       boolean cancelled = Events.fireAndTestCancel((Event)firedEvent);
/* 57 */       if (cancelled) {
/* 58 */         originalEvent.setCancelled(true);
/*    */       }
/* 60 */       entry.cancelled = Boolean.valueOf(cancelled);
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   protected <T extends Event & Cancellable> Entry getEntry(K key, Cancellable originalEvent) {
/* 66 */     Entry entry = (Entry)this.cache.getUnchecked(key);
/* 67 */     if (entry.cancelled != null) {
/* 68 */       if (entry.cancelled.booleanValue()) {
/* 69 */         originalEvent.setCancelled(true);
/*    */       }
/* 71 */       return null;
/*    */     } 
/* 73 */     return entry;
/*    */   }
/*    */   
/*    */   public static class Entry
/*    */   {
/*    */     private Boolean cancelled;
/*    */     
/*    */     public void setCancelled(boolean cancelled) {
/* 81 */       this.cancelled = Boolean.valueOf(cancelled);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\debounce\legacy\AbstractEventDebounce.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */