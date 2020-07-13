/*     */ package com.sk89q.worldguard.internal.guava.cache;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Beta
/*     */ @GwtCompatible
/*     */ public final class RemovalNotification<K, V>
/*     */   implements Map.Entry<K, V>
/*     */ {
/*     */   @Nullable
/*     */   private final K key;
/*     */   @Nullable
/*     */   private final V value;
/*     */   private final RemovalCause cause;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   RemovalNotification(@Nullable K key, @Nullable V value, RemovalCause cause) {
/*  52 */     this.key = key;
/*  53 */     this.value = value;
/*  54 */     this.cause = (RemovalCause)Preconditions.checkNotNull(cause);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RemovalCause getCause() {
/*  61 */     return this.cause;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean wasEvicted() {
/*  69 */     return this.cause.wasEvicted();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public K getKey() {
/*  74 */     return this.key;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public V getValue() {
/*  79 */     return this.value;
/*     */   }
/*     */   
/*     */   public final V setValue(V value) {
/*  83 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean equals(@Nullable Object object) {
/*  87 */     if (object instanceof Map.Entry) {
/*  88 */       Map.Entry<?, ?> that = (Map.Entry<?, ?>)object;
/*  89 */       return (Objects.equal(getKey(), that.getKey()) && 
/*  90 */         Objects.equal(getValue(), that.getValue()));
/*     */     } 
/*  92 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  96 */     K k = getKey();
/*  97 */     V v = getValue();
/*  98 */     return ((k == null) ? 0 : k.hashCode()) ^ ((v == null) ? 0 : v.hashCode());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 105 */     return (new StringBuilder()).append(getKey()).append("=").append(getValue()).toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\guava\cache\RemovalNotification.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */