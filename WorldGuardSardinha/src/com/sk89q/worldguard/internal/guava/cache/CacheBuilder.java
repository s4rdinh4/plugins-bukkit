/*     */ package com.sk89q.worldguard.internal.guava.cache;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import com.google.common.base.Ascii;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Supplier;
/*     */ import com.google.common.base.Suppliers;
/*     */ import com.google.common.base.Ticker;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.CheckReturnValue;
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
/*     */ @GwtCompatible(emulated = true)
/*     */ public final class CacheBuilder<K, V>
/*     */ {
/*     */   private static final int DEFAULT_INITIAL_CAPACITY = 16;
/*     */   private static final int DEFAULT_CONCURRENCY_LEVEL = 4;
/*     */   private static final int DEFAULT_EXPIRATION_NANOS = 0;
/*     */   private static final int DEFAULT_REFRESH_NANOS = 0;
/*     */   
/* 157 */   static final Supplier<? extends AbstractCache.StatsCounter> NULL_STATS_COUNTER = Suppliers.ofInstance(new AbstractCache.StatsCounter()
/*     */       {
/*     */         public void recordHits(int count) {}
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void recordMisses(int count) {}
/*     */ 
/*     */ 
/*     */         
/*     */         public void recordLoadSuccess(long loadTime) {}
/*     */ 
/*     */ 
/*     */         
/*     */         public void recordLoadException(long loadTime) {}
/*     */ 
/*     */ 
/*     */         
/*     */         public void recordEviction() {}
/*     */ 
/*     */ 
/*     */         
/*     */         public CacheStats snapshot() {
/* 181 */           return CacheBuilder.EMPTY_STATS;
/*     */         }
/*     */       });
/* 184 */   static final CacheStats EMPTY_STATS = new CacheStats(0L, 0L, 0L, 0L, 0L, 0L);
/*     */   
/* 186 */   static final Supplier<AbstractCache.StatsCounter> CACHE_STATS_COUNTER = new Supplier<AbstractCache.StatsCounter>()
/*     */     {
/*     */       public AbstractCache.StatsCounter get()
/*     */       {
/* 190 */         return new AbstractCache.SimpleStatsCounter();
/*     */       }
/*     */     };
/*     */   
/*     */   enum NullListener implements RemovalListener<Object, Object> {
/* 195 */     INSTANCE;
/*     */     
/*     */     public void onRemoval(RemovalNotification<Object, Object> notification) {}
/*     */   }
/*     */   
/*     */   enum OneWeigher
/*     */     implements Weigher<Object, Object> {
/* 202 */     INSTANCE;
/*     */ 
/*     */     
/*     */     public int weigh(Object key, Object value) {
/* 206 */       return 1;
/*     */     }
/*     */   }
/*     */   
/* 210 */   static final Ticker NULL_TICKER = new Ticker()
/*     */     {
/*     */       public long read() {
/* 213 */         return 0L;
/*     */       }
/*     */     };
/*     */   
/* 217 */   private static final Logger logger = Logger.getLogger(CacheBuilder.class.getName());
/*     */   
/*     */   static final int UNSET_INT = -1;
/*     */   
/*     */   boolean strictParsing = true;
/*     */   
/* 223 */   int initialCapacity = -1;
/* 224 */   int concurrencyLevel = -1;
/* 225 */   long maximumSize = -1L;
/* 226 */   long maximumWeight = -1L;
/*     */   
/*     */   Weigher<? super K, ? super V> weigher;
/*     */   
/*     */   LocalCache.Strength keyStrength;
/*     */   LocalCache.Strength valueStrength;
/* 232 */   long expireAfterWriteNanos = -1L;
/* 233 */   long expireAfterAccessNanos = -1L;
/* 234 */   long refreshNanos = -1L;
/*     */   
/*     */   Equivalence<Object> keyEquivalence;
/*     */   
/*     */   Equivalence<Object> valueEquivalence;
/*     */   
/*     */   RemovalListener<? super K, ? super V> removalListener;
/*     */   Ticker ticker;
/* 242 */   Supplier<? extends AbstractCache.StatsCounter> statsCounterSupplier = NULL_STATS_COUNTER;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CacheBuilder<Object, Object> newBuilder() {
/* 252 */     return new CacheBuilder<Object, Object>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   @GwtIncompatible("To be supported")
/*     */   public static CacheBuilder<Object, Object> from(CacheBuilderSpec spec) {
/* 264 */     return spec.toCacheBuilder().lenientParsing();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   @GwtIncompatible("To be supported")
/*     */   public static CacheBuilder<Object, Object> from(String spec) {
/* 277 */     return from(CacheBuilderSpec.parse(spec));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("To be supported")
/*     */   CacheBuilder<K, V> lenientParsing() {
/* 285 */     this.strictParsing = false;
/* 286 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("To be supported")
/*     */   CacheBuilder<K, V> keyEquivalence(Equivalence<Object> equivalence) {
/* 297 */     Preconditions.checkState((this.keyEquivalence == null), "key equivalence was already set to %s", new Object[] { this.keyEquivalence });
/* 298 */     this.keyEquivalence = (Equivalence<Object>)Preconditions.checkNotNull(equivalence);
/* 299 */     return this;
/*     */   }
/*     */   
/*     */   Equivalence<Object> getKeyEquivalence() {
/* 303 */     return MoreObjects.<Equivalence<Object>>firstNonNull(this.keyEquivalence, getKeyStrength().defaultEquivalence());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("To be supported")
/*     */   CacheBuilder<K, V> valueEquivalence(Equivalence<Object> equivalence) {
/* 315 */     Preconditions.checkState((this.valueEquivalence == null), "value equivalence was already set to %s", new Object[] { this.valueEquivalence });
/*     */     
/* 317 */     this.valueEquivalence = (Equivalence<Object>)Preconditions.checkNotNull(equivalence);
/* 318 */     return this;
/*     */   }
/*     */   
/*     */   Equivalence<Object> getValueEquivalence() {
/* 322 */     return MoreObjects.<Equivalence<Object>>firstNonNull(this.valueEquivalence, getValueStrength().defaultEquivalence());
/*     */   }
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
/*     */   public CacheBuilder<K, V> initialCapacity(int initialCapacity) {
/* 336 */     Preconditions.checkState((this.initialCapacity == -1), "initial capacity was already set to %s", new Object[] {
/* 337 */           Integer.valueOf(this.initialCapacity) });
/* 338 */     Preconditions.checkArgument((initialCapacity >= 0));
/* 339 */     this.initialCapacity = initialCapacity;
/* 340 */     return this;
/*     */   }
/*     */   
/*     */   int getInitialCapacity() {
/* 344 */     return (this.initialCapacity == -1) ? 16 : this.initialCapacity;
/*     */   }
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
/*     */   
/*     */   public CacheBuilder<K, V> concurrencyLevel(int concurrencyLevel) {
/* 378 */     Preconditions.checkState((this.concurrencyLevel == -1), "concurrency level was already set to %s", new Object[] {
/* 379 */           Integer.valueOf(this.concurrencyLevel) });
/* 380 */     Preconditions.checkArgument((concurrencyLevel > 0));
/* 381 */     this.concurrencyLevel = concurrencyLevel;
/* 382 */     return this;
/*     */   }
/*     */   
/*     */   int getConcurrencyLevel() {
/* 386 */     return (this.concurrencyLevel == -1) ? 4 : this.concurrencyLevel;
/*     */   }
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
/*     */   public CacheBuilder<K, V> maximumSize(long size) {
/* 405 */     Preconditions.checkState((this.maximumSize == -1L), "maximum size was already set to %s", new Object[] {
/* 406 */           Long.valueOf(this.maximumSize) });
/* 407 */     Preconditions.checkState((this.maximumWeight == -1L), "maximum weight was already set to %s", new Object[] {
/* 408 */           Long.valueOf(this.maximumWeight) });
/* 409 */     Preconditions.checkState((this.weigher == null), "maximum size can not be combined with weigher");
/* 410 */     Preconditions.checkArgument((size >= 0L), "maximum size must not be negative");
/* 411 */     this.maximumSize = size;
/* 412 */     return this;
/*     */   }
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
/*     */   @GwtIncompatible("To be supported")
/*     */   public CacheBuilder<K, V> maximumWeight(long weight) {
/* 441 */     Preconditions.checkState((this.maximumWeight == -1L), "maximum weight was already set to %s", new Object[] {
/* 442 */           Long.valueOf(this.maximumWeight) });
/* 443 */     Preconditions.checkState((this.maximumSize == -1L), "maximum size was already set to %s", new Object[] {
/* 444 */           Long.valueOf(this.maximumSize) });
/* 445 */     this.maximumWeight = weight;
/* 446 */     Preconditions.checkArgument((weight >= 0L), "maximum weight must not be negative");
/* 447 */     return this;
/*     */   }
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
/*     */   @GwtIncompatible("To be supported")
/*     */   public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> weigher(Weigher<? super K1, ? super V1> weigher) {
/* 481 */     Preconditions.checkState((this.weigher == null));
/* 482 */     if (this.strictParsing) {
/* 483 */       Preconditions.checkState((this.maximumSize == -1L), "weigher can not be combined with maximum size", new Object[] {
/* 484 */             Long.valueOf(this.maximumSize)
/*     */           });
/*     */     }
/*     */ 
/*     */     
/* 489 */     CacheBuilder<K1, V1> me = this;
/* 490 */     me.weigher = (Weigher<? super K, ? super V>)Preconditions.checkNotNull(weigher);
/* 491 */     return me;
/*     */   }
/*     */   
/*     */   long getMaximumWeight() {
/* 495 */     if (this.expireAfterWriteNanos == 0L || this.expireAfterAccessNanos == 0L) {
/* 496 */       return 0L;
/*     */     }
/* 498 */     return (this.weigher == null) ? this.maximumSize : this.maximumWeight;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   <K1 extends K, V1 extends V> Weigher<K1, V1> getWeigher() {
/* 504 */     return (Weigher<K1, V1>)MoreObjects.<Weigher<? super K, ? super V>>firstNonNull(this.weigher, OneWeigher.INSTANCE);
/*     */   }
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
/*     */   @GwtIncompatible("java.lang.ref.WeakReference")
/*     */   public CacheBuilder<K, V> weakKeys() {
/* 522 */     return setKeyStrength(LocalCache.Strength.WEAK);
/*     */   }
/*     */   
/*     */   CacheBuilder<K, V> setKeyStrength(LocalCache.Strength strength) {
/* 526 */     Preconditions.checkState((this.keyStrength == null), "Key strength was already set to %s", new Object[] { this.keyStrength });
/* 527 */     this.keyStrength = (LocalCache.Strength)Preconditions.checkNotNull(strength);
/* 528 */     return this;
/*     */   }
/*     */   
/*     */   LocalCache.Strength getKeyStrength() {
/* 532 */     return MoreObjects.<LocalCache.Strength>firstNonNull(this.keyStrength, LocalCache.Strength.STRONG);
/*     */   }
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
/*     */   @GwtIncompatible("java.lang.ref.WeakReference")
/*     */   public CacheBuilder<K, V> weakValues() {
/* 553 */     return setValueStrength(LocalCache.Strength.WEAK);
/*     */   }
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
/*     */   @GwtIncompatible("java.lang.ref.SoftReference")
/*     */   public CacheBuilder<K, V> softValues() {
/* 577 */     return setValueStrength(LocalCache.Strength.SOFT);
/*     */   }
/*     */   
/*     */   CacheBuilder<K, V> setValueStrength(LocalCache.Strength strength) {
/* 581 */     Preconditions.checkState((this.valueStrength == null), "Value strength was already set to %s", new Object[] { this.valueStrength });
/* 582 */     this.valueStrength = (LocalCache.Strength)Preconditions.checkNotNull(strength);
/* 583 */     return this;
/*     */   }
/*     */   
/*     */   LocalCache.Strength getValueStrength() {
/* 587 */     return MoreObjects.<LocalCache.Strength>firstNonNull(this.valueStrength, LocalCache.Strength.STRONG);
/*     */   }
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
/*     */   public CacheBuilder<K, V> expireAfterWrite(long duration, TimeUnit unit) {
/* 610 */     Preconditions.checkState((this.expireAfterWriteNanos == -1L), "expireAfterWrite was already set to %s ns", new Object[] {
/* 611 */           Long.valueOf(this.expireAfterWriteNanos) });
/* 612 */     Preconditions.checkArgument((duration >= 0L), "duration cannot be negative: %s %s", new Object[] { Long.valueOf(duration), unit });
/* 613 */     this.expireAfterWriteNanos = unit.toNanos(duration);
/* 614 */     return this;
/*     */   }
/*     */   
/*     */   long getExpireAfterWriteNanos() {
/* 618 */     return (this.expireAfterWriteNanos == -1L) ? 0L : this.expireAfterWriteNanos;
/*     */   }
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
/*     */   public CacheBuilder<K, V> expireAfterAccess(long duration, TimeUnit unit) {
/* 644 */     Preconditions.checkState((this.expireAfterAccessNanos == -1L), "expireAfterAccess was already set to %s ns", new Object[] {
/* 645 */           Long.valueOf(this.expireAfterAccessNanos) });
/* 646 */     Preconditions.checkArgument((duration >= 0L), "duration cannot be negative: %s %s", new Object[] { Long.valueOf(duration), unit });
/* 647 */     this.expireAfterAccessNanos = unit.toNanos(duration);
/* 648 */     return this;
/*     */   }
/*     */   
/*     */   long getExpireAfterAccessNanos() {
/* 652 */     return (this.expireAfterAccessNanos == -1L) ? 0L : this.expireAfterAccessNanos;
/*     */   }
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
/*     */   @Beta
/*     */   @GwtIncompatible("To be supported (synchronously).")
/*     */   public CacheBuilder<K, V> refreshAfterWrite(long duration, TimeUnit unit) {
/* 684 */     Preconditions.checkNotNull(unit);
/* 685 */     Preconditions.checkState((this.refreshNanos == -1L), "refresh was already set to %s ns", new Object[] { Long.valueOf(this.refreshNanos) });
/* 686 */     Preconditions.checkArgument((duration > 0L), "duration must be positive: %s %s", new Object[] { Long.valueOf(duration), unit });
/* 687 */     this.refreshNanos = unit.toNanos(duration);
/* 688 */     return this;
/*     */   }
/*     */   
/*     */   long getRefreshNanos() {
/* 692 */     return (this.refreshNanos == -1L) ? 0L : this.refreshNanos;
/*     */   }
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
/*     */   public CacheBuilder<K, V> ticker(Ticker ticker) {
/* 705 */     Preconditions.checkState((this.ticker == null));
/* 706 */     this.ticker = (Ticker)Preconditions.checkNotNull(ticker);
/* 707 */     return this;
/*     */   }
/*     */   
/*     */   Ticker getTicker(boolean recordsTime) {
/* 711 */     if (this.ticker != null) {
/* 712 */       return this.ticker;
/*     */     }
/* 714 */     return recordsTime ? Ticker.systemTicker() : NULL_TICKER;
/*     */   }
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
/*     */   @CheckReturnValue
/*     */   public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> removalListener(RemovalListener<? super K1, ? super V1> listener) {
/* 741 */     Preconditions.checkState((this.removalListener == null));
/*     */ 
/*     */ 
/*     */     
/* 745 */     CacheBuilder<K1, V1> me = this;
/* 746 */     me.removalListener = (RemovalListener<? super K, ? super V>)Preconditions.checkNotNull(listener);
/* 747 */     return me;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   <K1 extends K, V1 extends V> RemovalListener<K1, V1> getRemovalListener() {
/* 754 */     return (RemovalListener<K1, V1>)MoreObjects.<RemovalListener<? super K, ? super V>>firstNonNull(this.removalListener, NullListener.INSTANCE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CacheBuilder<K, V> recordStats() {
/* 766 */     this.statsCounterSupplier = CACHE_STATS_COUNTER;
/* 767 */     return this;
/*     */   }
/*     */   
/*     */   boolean isRecordingStats() {
/* 771 */     return (this.statsCounterSupplier == CACHE_STATS_COUNTER);
/*     */   }
/*     */   
/*     */   Supplier<? extends AbstractCache.StatsCounter> getStatsCounterSupplier() {
/* 775 */     return this.statsCounterSupplier;
/*     */   }
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
/*     */   public <K1 extends K, V1 extends V> LoadingCache<K1, V1> build(CacheLoader<? super K1, V1> loader) {
/* 792 */     checkWeightWithWeigher();
/* 793 */     return new LocalCache.LocalLoadingCache<K1, V1>(this, loader);
/*     */   }
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
/*     */   public <K1 extends K, V1 extends V> Cache<K1, V1> build() {
/* 809 */     checkWeightWithWeigher();
/* 810 */     checkNonLoadingCache();
/* 811 */     return new LocalCache.LocalManualCache<K1, V1>(this);
/*     */   }
/*     */   
/*     */   private void checkNonLoadingCache() {
/* 815 */     Preconditions.checkState((this.refreshNanos == -1L), "refreshAfterWrite requires a LoadingCache");
/*     */   }
/*     */   
/*     */   private void checkWeightWithWeigher() {
/* 819 */     if (this.weigher == null) {
/* 820 */       Preconditions.checkState((this.maximumWeight == -1L), "maximumWeight requires weigher");
/*     */     }
/* 822 */     else if (this.strictParsing) {
/* 823 */       Preconditions.checkState((this.maximumWeight != -1L), "weigher requires maximumWeight");
/*     */     }
/* 825 */     else if (this.maximumWeight == -1L) {
/* 826 */       logger.log(Level.WARNING, "ignoring weigher specified without maximumWeight");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 838 */     MoreObjects.ToStringHelper s = MoreObjects.toStringHelper(this);
/* 839 */     if (this.initialCapacity != -1) {
/* 840 */       s.add("initialCapacity", this.initialCapacity);
/*     */     }
/* 842 */     if (this.concurrencyLevel != -1) {
/* 843 */       s.add("concurrencyLevel", this.concurrencyLevel);
/*     */     }
/* 845 */     if (this.maximumSize != -1L) {
/* 846 */       s.add("maximumSize", this.maximumSize);
/*     */     }
/* 848 */     if (this.maximumWeight != -1L) {
/* 849 */       s.add("maximumWeight", this.maximumWeight);
/*     */     }
/* 851 */     if (this.expireAfterWriteNanos != -1L) {
/* 852 */       s.add("expireAfterWrite", this.expireAfterWriteNanos + "ns");
/*     */     }
/* 854 */     if (this.expireAfterAccessNanos != -1L) {
/* 855 */       s.add("expireAfterAccess", this.expireAfterAccessNanos + "ns");
/*     */     }
/* 857 */     if (this.keyStrength != null) {
/* 858 */       s.add("keyStrength", Ascii.toLowerCase(this.keyStrength.toString()));
/*     */     }
/* 860 */     if (this.valueStrength != null) {
/* 861 */       s.add("valueStrength", Ascii.toLowerCase(this.valueStrength.toString()));
/*     */     }
/* 863 */     if (this.keyEquivalence != null) {
/* 864 */       s.addValue("keyEquivalence");
/*     */     }
/* 866 */     if (this.valueEquivalence != null) {
/* 867 */       s.addValue("valueEquivalence");
/*     */     }
/* 869 */     if (this.removalListener != null) {
/* 870 */       s.addValue("removalListener");
/*     */     }
/* 872 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\guava\cache\CacheBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */