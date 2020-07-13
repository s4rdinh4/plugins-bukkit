/*      */ package com.sk89q.worldguard.internal.guava.cache;
/*      */ 
/*      */ import com.google.common.annotations.GwtCompatible;
/*      */ import com.google.common.annotations.GwtIncompatible;
/*      */ import com.google.common.annotations.VisibleForTesting;
/*      */ import com.google.common.base.Function;
/*      */ import com.google.common.base.Preconditions;
/*      */ import com.google.common.base.Ticker;
/*      */ import com.google.common.collect.ImmutableMap;
/*      */ import com.google.common.collect.ImmutableSet;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Sets;
/*      */ import com.google.common.primitives.Ints;
/*      */ import com.google.common.util.concurrent.ExecutionError;
/*      */ import com.google.common.util.concurrent.Futures;
/*      */ import com.google.common.util.concurrent.ListenableFuture;
/*      */ import com.google.common.util.concurrent.ListeningExecutorService;
/*      */ import com.google.common.util.concurrent.MoreExecutors;
/*      */ import com.google.common.util.concurrent.SettableFuture;
/*      */ import com.google.common.util.concurrent.UncheckedExecutionException;
/*      */ import com.google.common.util.concurrent.Uninterruptibles;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.ref.Reference;
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.AbstractCollection;
/*      */ import java.util.AbstractMap;
/*      */ import java.util.AbstractQueue;
/*      */ import java.util.AbstractSet;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Queue;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.ConcurrentLinkedQueue;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import java.util.concurrent.ExecutionException;
/*      */ import java.util.concurrent.Executor;
/*      */ import java.util.concurrent.Future;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.atomic.AtomicReferenceArray;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.annotation.Nullable;
/*      */ import javax.annotation.concurrent.GuardedBy;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @GwtCompatible(emulated = true)
/*      */ class LocalCache<K, V>
/*      */   extends AbstractMap<K, V>
/*      */   implements ConcurrentMap<K, V>
/*      */ {
/*   77 */   private static ListeningExecutorService directExecutor = MoreExecutors.sameThreadExecutor();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int MAXIMUM_CAPACITY = 1073741824;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int MAX_SEGMENTS = 65536;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int CONTAINS_VALUE_RETRIES = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int DRAIN_THRESHOLD = 63;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int DRAIN_MAX = 16;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  138 */   static final Logger logger = Logger.getLogger(LocalCache.class.getName());
/*      */ 
/*      */ 
/*      */   
/*      */   final int segmentMask;
/*      */ 
/*      */ 
/*      */   
/*      */   final int segmentShift;
/*      */ 
/*      */ 
/*      */   
/*      */   final Segment<K, V>[] segments;
/*      */ 
/*      */ 
/*      */   
/*      */   final int concurrencyLevel;
/*      */ 
/*      */ 
/*      */   
/*      */   final Equivalence<Object> keyEquivalence;
/*      */ 
/*      */ 
/*      */   
/*      */   final Equivalence<Object> valueEquivalence;
/*      */ 
/*      */ 
/*      */   
/*      */   final Strength keyStrength;
/*      */ 
/*      */ 
/*      */   
/*      */   final Strength valueStrength;
/*      */ 
/*      */ 
/*      */   
/*      */   final long maxWeight;
/*      */ 
/*      */ 
/*      */   
/*      */   final Weigher<K, V> weigher;
/*      */ 
/*      */ 
/*      */   
/*      */   final long expireAfterAccessNanos;
/*      */ 
/*      */ 
/*      */   
/*      */   final long expireAfterWriteNanos;
/*      */ 
/*      */ 
/*      */   
/*      */   final long refreshNanos;
/*      */ 
/*      */ 
/*      */   
/*      */   final Queue<RemovalNotification<K, V>> removalNotificationQueue;
/*      */ 
/*      */ 
/*      */   
/*      */   final RemovalListener<K, V> removalListener;
/*      */ 
/*      */ 
/*      */   
/*      */   final Ticker ticker;
/*      */ 
/*      */ 
/*      */   
/*      */   final EntryFactory entryFactory;
/*      */ 
/*      */   
/*      */   final AbstractCache.StatsCounter globalStatsCounter;
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   final CacheLoader<? super K, V> defaultLoader;
/*      */ 
/*      */ 
/*      */   
/*      */   LocalCache(CacheBuilder<? super K, ? super V> builder, @Nullable CacheLoader<? super K, V> loader) {
/*  218 */     this.concurrencyLevel = Math.min(builder.getConcurrencyLevel(), 65536);
/*      */     
/*  220 */     this.keyStrength = builder.getKeyStrength();
/*  221 */     this.valueStrength = builder.getValueStrength();
/*      */     
/*  223 */     this.keyEquivalence = builder.getKeyEquivalence();
/*  224 */     this.valueEquivalence = builder.getValueEquivalence();
/*      */     
/*  226 */     this.maxWeight = builder.getMaximumWeight();
/*  227 */     this.weigher = builder.getWeigher();
/*  228 */     this.expireAfterAccessNanos = builder.getExpireAfterAccessNanos();
/*  229 */     this.expireAfterWriteNanos = builder.getExpireAfterWriteNanos();
/*  230 */     this.refreshNanos = builder.getRefreshNanos();
/*      */     
/*  232 */     this.removalListener = builder.getRemovalListener();
/*  233 */     this
/*  234 */       .removalNotificationQueue = (this.removalListener == CacheBuilder.NullListener.INSTANCE) ? discardingQueue() : new ConcurrentLinkedQueue<RemovalNotification<K, V>>();
/*      */ 
/*      */     
/*  237 */     this.ticker = builder.getTicker(recordsTime());
/*  238 */     this.entryFactory = EntryFactory.getFactory(this.keyStrength, usesAccessEntries(), usesWriteEntries());
/*  239 */     this.globalStatsCounter = (AbstractCache.StatsCounter)builder.getStatsCounterSupplier().get();
/*  240 */     this.defaultLoader = loader;
/*      */     
/*  242 */     int initialCapacity = Math.min(builder.getInitialCapacity(), 1073741824);
/*  243 */     if (evictsBySize() && !customWeigher()) {
/*  244 */       initialCapacity = Math.min(initialCapacity, (int)this.maxWeight);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  252 */     int segmentShift = 0;
/*  253 */     int segmentCount = 1;
/*  254 */     while (segmentCount < this.concurrencyLevel && (
/*  255 */       !evictsBySize() || (segmentCount * 20) <= this.maxWeight)) {
/*  256 */       segmentShift++;
/*  257 */       segmentCount <<= 1;
/*      */     } 
/*  259 */     this.segmentShift = 32 - segmentShift;
/*  260 */     this.segmentMask = segmentCount - 1;
/*      */     
/*  262 */     this.segments = newSegmentArray(segmentCount);
/*      */     
/*  264 */     int segmentCapacity = initialCapacity / segmentCount;
/*  265 */     if (segmentCapacity * segmentCount < initialCapacity) {
/*  266 */       segmentCapacity++;
/*      */     }
/*      */     
/*  269 */     int segmentSize = 1;
/*  270 */     while (segmentSize < segmentCapacity) {
/*  271 */       segmentSize <<= 1;
/*      */     }
/*      */     
/*  274 */     if (evictsBySize()) {
/*      */       
/*  276 */       long maxSegmentWeight = this.maxWeight / segmentCount + 1L;
/*  277 */       long remainder = this.maxWeight % segmentCount;
/*  278 */       for (int i = 0; i < this.segments.length; i++) {
/*  279 */         if (i == remainder) {
/*  280 */           maxSegmentWeight--;
/*      */         }
/*  282 */         this.segments[i] = 
/*  283 */           createSegment(segmentSize, maxSegmentWeight, (AbstractCache.StatsCounter)builder.getStatsCounterSupplier().get());
/*      */       } 
/*      */     } else {
/*  286 */       for (int i = 0; i < this.segments.length; i++) {
/*  287 */         this.segments[i] = 
/*  288 */           createSegment(segmentSize, -1L, (AbstractCache.StatsCounter)builder.getStatsCounterSupplier().get());
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   boolean evictsBySize() {
/*  294 */     return (this.maxWeight >= 0L);
/*      */   }
/*      */   
/*      */   boolean customWeigher() {
/*  298 */     return (this.weigher != CacheBuilder.OneWeigher.INSTANCE);
/*      */   }
/*      */   
/*      */   boolean expires() {
/*  302 */     return (expiresAfterWrite() || expiresAfterAccess());
/*      */   }
/*      */   
/*      */   boolean expiresAfterWrite() {
/*  306 */     return (this.expireAfterWriteNanos > 0L);
/*      */   }
/*      */   
/*      */   boolean expiresAfterAccess() {
/*  310 */     return (this.expireAfterAccessNanos > 0L);
/*      */   }
/*      */   
/*      */   boolean refreshes() {
/*  314 */     return (this.refreshNanos > 0L);
/*      */   }
/*      */   
/*      */   boolean usesAccessQueue() {
/*  318 */     return (expiresAfterAccess() || evictsBySize());
/*      */   }
/*      */   
/*      */   boolean usesWriteQueue() {
/*  322 */     return expiresAfterWrite();
/*      */   }
/*      */   
/*      */   boolean recordsWrite() {
/*  326 */     return (expiresAfterWrite() || refreshes());
/*      */   }
/*      */   
/*      */   boolean recordsAccess() {
/*  330 */     return expiresAfterAccess();
/*      */   }
/*      */   
/*      */   boolean recordsTime() {
/*  334 */     return (recordsWrite() || recordsAccess());
/*      */   }
/*      */   
/*      */   boolean usesWriteEntries() {
/*  338 */     return (usesWriteQueue() || recordsWrite());
/*      */   }
/*      */   
/*      */   boolean usesAccessEntries() {
/*  342 */     return (usesAccessQueue() || recordsAccess());
/*      */   }
/*      */   
/*      */   boolean usesKeyReferences() {
/*  346 */     return (this.keyStrength != Strength.STRONG);
/*      */   }
/*      */   
/*      */   boolean usesValueReferences() {
/*  350 */     return (this.valueStrength != Strength.STRONG);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   enum Strength
/*      */   {
/*  359 */     STRONG
/*      */     {
/*      */       <K, V> LocalCache.ValueReference<K, V> referenceValue(LocalCache.Segment<K, V> segment, LocalCache.ReferenceEntry<K, V> entry, V value, int weight)
/*      */       {
/*  363 */         return (weight == 1) ? new LocalCache.StrongValueReference<K, V>(value) : new LocalCache.WeightedStrongValueReference<K, V>(value, weight);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       Equivalence<Object> defaultEquivalence() {
/*  370 */         return Equivalence.equals();
/*      */       }
/*      */     },
/*      */     
/*  374 */     SOFT
/*      */     {
/*      */       <K, V> LocalCache.ValueReference<K, V> referenceValue(LocalCache.Segment<K, V> segment, LocalCache.ReferenceEntry<K, V> entry, V value, int weight)
/*      */       {
/*  378 */         return (weight == 1) ? new LocalCache.SoftValueReference<K, V>(segment.valueReferenceQueue, value, entry) : new LocalCache.WeightedSoftValueReference<K, V>(segment.valueReferenceQueue, value, entry, weight);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       Equivalence<Object> defaultEquivalence() {
/*  386 */         return Equivalence.identity();
/*      */       }
/*      */     },
/*      */     
/*  390 */     WEAK
/*      */     {
/*      */       <K, V> LocalCache.ValueReference<K, V> referenceValue(LocalCache.Segment<K, V> segment, LocalCache.ReferenceEntry<K, V> entry, V value, int weight)
/*      */       {
/*  394 */         return (weight == 1) ? new LocalCache.WeakValueReference<K, V>(segment.valueReferenceQueue, value, entry) : new LocalCache.WeightedWeakValueReference<K, V>(segment.valueReferenceQueue, value, entry, weight);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       Equivalence<Object> defaultEquivalence() {
/*  402 */         return Equivalence.identity();
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract <K, V> LocalCache.ValueReference<K, V> referenceValue(LocalCache.Segment<K, V> param1Segment, LocalCache.ReferenceEntry<K, V> param1ReferenceEntry, V param1V, int param1Int);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract Equivalence<Object> defaultEquivalence();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   enum EntryFactory
/*      */   {
/*  424 */     STRONG
/*      */     {
/*      */       <K, V> LocalCache.ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> segment, K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next)
/*      */       {
/*  428 */         return new LocalCache.StrongEntry<K, V>(key, hash, next);
/*      */       }
/*      */     },
/*  431 */     STRONG_ACCESS
/*      */     {
/*      */       <K, V> LocalCache.ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> segment, K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next)
/*      */       {
/*  435 */         return new LocalCache.StrongAccessEntry<K, V>(key, hash, next);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       <K, V> LocalCache.ReferenceEntry<K, V> copyEntry(LocalCache.Segment<K, V> segment, LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newNext) {
/*  441 */         LocalCache.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
/*  442 */         copyAccessEntry(original, newEntry);
/*  443 */         return newEntry;
/*      */       }
/*      */     },
/*  446 */     STRONG_WRITE
/*      */     {
/*      */       <K, V> LocalCache.ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> segment, K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next)
/*      */       {
/*  450 */         return new LocalCache.StrongWriteEntry<K, V>(key, hash, next);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       <K, V> LocalCache.ReferenceEntry<K, V> copyEntry(LocalCache.Segment<K, V> segment, LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newNext) {
/*  456 */         LocalCache.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
/*  457 */         copyWriteEntry(original, newEntry);
/*  458 */         return newEntry;
/*      */       }
/*      */     },
/*  461 */     STRONG_ACCESS_WRITE
/*      */     {
/*      */       <K, V> LocalCache.ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> segment, K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next)
/*      */       {
/*  465 */         return new LocalCache.StrongAccessWriteEntry<K, V>(key, hash, next);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       <K, V> LocalCache.ReferenceEntry<K, V> copyEntry(LocalCache.Segment<K, V> segment, LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newNext) {
/*  471 */         LocalCache.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
/*  472 */         copyAccessEntry(original, newEntry);
/*  473 */         copyWriteEntry(original, newEntry);
/*  474 */         return newEntry;
/*      */       }
/*      */     },
/*      */     
/*  478 */     WEAK
/*      */     {
/*      */       <K, V> LocalCache.ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> segment, K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next)
/*      */       {
/*  482 */         return new LocalCache.WeakEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
/*      */       }
/*      */     },
/*  485 */     WEAK_ACCESS
/*      */     {
/*      */       <K, V> LocalCache.ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> segment, K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next)
/*      */       {
/*  489 */         return new LocalCache.WeakAccessEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       <K, V> LocalCache.ReferenceEntry<K, V> copyEntry(LocalCache.Segment<K, V> segment, LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newNext) {
/*  495 */         LocalCache.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
/*  496 */         copyAccessEntry(original, newEntry);
/*  497 */         return newEntry;
/*      */       }
/*      */     },
/*  500 */     WEAK_WRITE
/*      */     {
/*      */       <K, V> LocalCache.ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> segment, K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next)
/*      */       {
/*  504 */         return new LocalCache.WeakWriteEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       <K, V> LocalCache.ReferenceEntry<K, V> copyEntry(LocalCache.Segment<K, V> segment, LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newNext) {
/*  510 */         LocalCache.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
/*  511 */         copyWriteEntry(original, newEntry);
/*  512 */         return newEntry;
/*      */       }
/*      */     },
/*  515 */     WEAK_ACCESS_WRITE
/*      */     {
/*      */       <K, V> LocalCache.ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> segment, K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next)
/*      */       {
/*  519 */         return new LocalCache.WeakAccessWriteEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       <K, V> LocalCache.ReferenceEntry<K, V> copyEntry(LocalCache.Segment<K, V> segment, LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newNext) {
/*  525 */         LocalCache.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
/*  526 */         copyAccessEntry(original, newEntry);
/*  527 */         copyWriteEntry(original, newEntry);
/*  528 */         return newEntry;
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */     
/*      */     static final int ACCESS_MASK = 1;
/*      */ 
/*      */     
/*      */     static final int WRITE_MASK = 2;
/*      */ 
/*      */     
/*      */     static final int WEAK_MASK = 4;
/*      */     
/*  542 */     static final EntryFactory[] factories = new EntryFactory[] { STRONG, STRONG_ACCESS, STRONG_WRITE, STRONG_ACCESS_WRITE, WEAK, WEAK_ACCESS, WEAK_WRITE, WEAK_ACCESS_WRITE };
/*      */     
/*      */     static {
/*      */     
/*      */     }
/*      */     
/*      */     static EntryFactory getFactory(LocalCache.Strength keyStrength, boolean usesAccessQueue, boolean usesWriteQueue) {
/*  549 */       int flags = ((keyStrength == LocalCache.Strength.WEAK) ? 4 : 0) | (usesAccessQueue ? 1 : 0) | (usesWriteQueue ? 2 : 0);
/*      */ 
/*      */       
/*  552 */       return factories[flags];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     <K, V> LocalCache.ReferenceEntry<K, V> copyEntry(LocalCache.Segment<K, V> segment, LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newNext) {
/*  575 */       return newEntry(segment, original.getKey(), original.getHash(), newNext);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     <K, V> void copyAccessEntry(LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newEntry) {
/*  582 */       newEntry.setAccessTime(original.getAccessTime());
/*      */       
/*  584 */       LocalCache.connectAccessOrder(original.getPreviousInAccessQueue(), newEntry);
/*  585 */       LocalCache.connectAccessOrder(newEntry, original.getNextInAccessQueue());
/*      */       
/*  587 */       LocalCache.nullifyAccessOrder(original);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     <K, V> void copyWriteEntry(LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newEntry) {
/*  594 */       newEntry.setWriteTime(original.getWriteTime());
/*      */       
/*  596 */       LocalCache.connectWriteOrder(original.getPreviousInWriteQueue(), newEntry);
/*  597 */       LocalCache.connectWriteOrder(newEntry, original.getNextInWriteQueue());
/*      */       
/*  599 */       LocalCache.nullifyWriteOrder(original);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract <K, V> LocalCache.ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> param1Segment, K param1K, int param1Int, @Nullable LocalCache.ReferenceEntry<K, V> param1ReferenceEntry);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  668 */   static final ValueReference<Object, Object> UNSET = new ValueReference<Object, Object>()
/*      */     {
/*      */       public Object get() {
/*  671 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public int getWeight() {
/*  676 */         return 0;
/*      */       }
/*      */ 
/*      */       
/*      */       public LocalCache.ReferenceEntry<Object, Object> getEntry() {
/*  681 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public LocalCache.ValueReference<Object, Object> copyFor(ReferenceQueue<Object> queue, @Nullable Object value, LocalCache.ReferenceEntry<Object, Object> entry) {
/*  687 */         return this;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean isLoading() {
/*  692 */         return false;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean isActive() {
/*  697 */         return false;
/*      */       }
/*      */ 
/*      */       
/*      */       public Object waitForValue() {
/*  702 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void notifyNewValue(Object newValue) {}
/*      */     };
/*      */ 
/*      */ 
/*      */   
/*      */   static <K, V> ValueReference<K, V> unset() {
/*  714 */     return (ValueReference)UNSET;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private enum NullEntry
/*      */     implements ReferenceEntry<Object, Object>
/*      */   {
/*  833 */     INSTANCE;
/*      */ 
/*      */     
/*      */     public LocalCache.ValueReference<Object, Object> getValueReference() {
/*  837 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setValueReference(LocalCache.ValueReference<Object, Object> valueReference) {}
/*      */ 
/*      */     
/*      */     public LocalCache.ReferenceEntry<Object, Object> getNext() {
/*  845 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getHash() {
/*  850 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object getKey() {
/*  855 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public long getAccessTime() {
/*  860 */       return 0L;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setAccessTime(long time) {}
/*      */ 
/*      */     
/*      */     public LocalCache.ReferenceEntry<Object, Object> getNextInAccessQueue() {
/*  868 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setNextInAccessQueue(LocalCache.ReferenceEntry<Object, Object> next) {}
/*      */ 
/*      */     
/*      */     public LocalCache.ReferenceEntry<Object, Object> getPreviousInAccessQueue() {
/*  876 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<Object, Object> previous) {}
/*      */ 
/*      */     
/*      */     public long getWriteTime() {
/*  884 */       return 0L;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setWriteTime(long time) {}
/*      */ 
/*      */     
/*      */     public LocalCache.ReferenceEntry<Object, Object> getNextInWriteQueue() {
/*  892 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setNextInWriteQueue(LocalCache.ReferenceEntry<Object, Object> next) {}
/*      */ 
/*      */     
/*      */     public LocalCache.ReferenceEntry<Object, Object> getPreviousInWriteQueue() {
/*  900 */       return this;
/*      */     }
/*      */     
/*      */     public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<Object, Object> previous) {}
/*      */   }
/*      */   
/*      */   static abstract class AbstractReferenceEntry<K, V>
/*      */     implements ReferenceEntry<K, V>
/*      */   {
/*      */     public LocalCache.ValueReference<K, V> getValueReference() {
/*  910 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setValueReference(LocalCache.ValueReference<K, V> valueReference) {
/*  915 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public LocalCache.ReferenceEntry<K, V> getNext() {
/*  920 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int getHash() {
/*  925 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public K getKey() {
/*  930 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long getAccessTime() {
/*  935 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setAccessTime(long time) {
/*  940 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public LocalCache.ReferenceEntry<K, V> getNextInAccessQueue() {
/*  945 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setNextInAccessQueue(LocalCache.ReferenceEntry<K, V> next) {
/*  950 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public LocalCache.ReferenceEntry<K, V> getPreviousInAccessQueue() {
/*  955 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<K, V> previous) {
/*  960 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long getWriteTime() {
/*  965 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setWriteTime(long time) {
/*  970 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public LocalCache.ReferenceEntry<K, V> getNextInWriteQueue() {
/*  975 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setNextInWriteQueue(LocalCache.ReferenceEntry<K, V> next) {
/*  980 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public LocalCache.ReferenceEntry<K, V> getPreviousInWriteQueue() {
/*  985 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<K, V> previous) {
/*  990 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static <K, V> ReferenceEntry<K, V> nullEntry() {
/*  996 */     return NullEntry.INSTANCE;
/*      */   }
/*      */   
/*  999 */   static final Queue<? extends Object> DISCARDING_QUEUE = new AbstractQueue()
/*      */     {
/*      */       public boolean offer(Object o) {
/* 1002 */         return true;
/*      */       }
/*      */ 
/*      */       
/*      */       public Object peek() {
/* 1007 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public Object poll() {
/* 1012 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public int size() {
/* 1017 */         return 0;
/*      */       }
/*      */ 
/*      */       
/*      */       public Iterator<Object> iterator() {
/* 1022 */         return (Iterator<Object>)ImmutableSet.of().iterator();
/*      */       }
/*      */     };
/*      */   
/*      */   Set<K> keySet;
/*      */   Collection<V> values;
/*      */   Set<Map.Entry<K, V>> entrySet;
/*      */   
/*      */   static <E> Queue<E> discardingQueue() {
/* 1031 */     return (Queue)DISCARDING_QUEUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class StrongEntry<K, V>
/*      */     extends AbstractReferenceEntry<K, V>
/*      */   {
/*      */     final K key;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final int hash;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final LocalCache.ReferenceEntry<K, V> next;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     volatile LocalCache.ValueReference<K, V> valueReference;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     StrongEntry(K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) {
/* 1063 */       this.valueReference = LocalCache.unset();
/*      */       this.key = key;
/*      */       this.hash = hash;
/*      */       this.next = next;
/* 1067 */     } public K getKey() { return this.key; } public LocalCache.ValueReference<K, V> getValueReference() { return this.valueReference; }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setValueReference(LocalCache.ValueReference<K, V> valueReference) {
/* 1072 */       this.valueReference = valueReference;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getHash() {
/* 1077 */       return this.hash;
/*      */     }
/*      */ 
/*      */     
/*      */     public LocalCache.ReferenceEntry<K, V> getNext() {
/* 1082 */       return this.next;
/*      */     } }
/*      */   static final class StrongAccessEntry<K, V> extends StrongEntry<K, V> { volatile long accessTime;
/*      */     LocalCache.ReferenceEntry<K, V> nextAccess;
/*      */     LocalCache.ReferenceEntry<K, V> previousAccess;
/*      */     
/* 1088 */     StrongAccessEntry(K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) { super(key, hash, next);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1093 */       this.accessTime = Long.MAX_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1106 */       this.nextAccess = LocalCache.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1119 */       this.previousAccess = LocalCache.nullEntry(); } public long getAccessTime() { return this.accessTime; }
/*      */     public void setAccessTime(long time) { this.accessTime = time; }
/*      */     public LocalCache.ReferenceEntry<K, V> getNextInAccessQueue() { return this.nextAccess; }
/*      */     public void setNextInAccessQueue(LocalCache.ReferenceEntry<K, V> next) { this.nextAccess = next; }
/* 1123 */     public LocalCache.ReferenceEntry<K, V> getPreviousInAccessQueue() { return this.previousAccess; }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<K, V> previous) {
/* 1128 */       this.previousAccess = previous;
/*      */     } }
/*      */   static final class StrongWriteEntry<K, V> extends StrongEntry<K, V> { volatile long writeTime;
/*      */     LocalCache.ReferenceEntry<K, V> nextWrite;
/*      */     LocalCache.ReferenceEntry<K, V> previousWrite;
/*      */     
/* 1134 */     StrongWriteEntry(K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) { super(key, hash, next);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1139 */       this.writeTime = Long.MAX_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1152 */       this.nextWrite = LocalCache.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1165 */       this.previousWrite = LocalCache.nullEntry(); } public long getWriteTime() { return this.writeTime; }
/*      */     public void setWriteTime(long time) { this.writeTime = time; }
/*      */     public LocalCache.ReferenceEntry<K, V> getNextInWriteQueue() { return this.nextWrite; }
/*      */     public void setNextInWriteQueue(LocalCache.ReferenceEntry<K, V> next) { this.nextWrite = next; }
/* 1169 */     public LocalCache.ReferenceEntry<K, V> getPreviousInWriteQueue() { return this.previousWrite; }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<K, V> previous) {
/* 1174 */       this.previousWrite = previous;
/*      */     } }
/*      */   static final class StrongAccessWriteEntry<K, V> extends StrongEntry<K, V> { volatile long accessTime; LocalCache.ReferenceEntry<K, V> nextAccess; LocalCache.ReferenceEntry<K, V> previousAccess; volatile long writeTime;
/*      */     LocalCache.ReferenceEntry<K, V> nextWrite;
/*      */     LocalCache.ReferenceEntry<K, V> previousWrite;
/*      */     
/* 1180 */     StrongAccessWriteEntry(K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) { super(key, hash, next);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1185 */       this.accessTime = Long.MAX_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1198 */       this.nextAccess = LocalCache.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1211 */       this.previousAccess = LocalCache.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1225 */       this.writeTime = Long.MAX_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1238 */       this.nextWrite = LocalCache.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1251 */       this.previousWrite = LocalCache.nullEntry(); } public long getAccessTime() { return this.accessTime; } public void setAccessTime(long time) { this.accessTime = time; } public LocalCache.ReferenceEntry<K, V> getNextInAccessQueue() { return this.nextAccess; } public void setNextInAccessQueue(LocalCache.ReferenceEntry<K, V> next) { this.nextAccess = next; } public LocalCache.ReferenceEntry<K, V> getPreviousInAccessQueue() { return this.previousAccess; } public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<K, V> previous) { this.previousAccess = previous; } public long getWriteTime() { return this.writeTime; }
/*      */     public void setWriteTime(long time) { this.writeTime = time; }
/*      */     public LocalCache.ReferenceEntry<K, V> getNextInWriteQueue() { return this.nextWrite; }
/*      */     public void setNextInWriteQueue(LocalCache.ReferenceEntry<K, V> next) { this.nextWrite = next; }
/* 1255 */     public LocalCache.ReferenceEntry<K, V> getPreviousInWriteQueue() { return this.previousWrite; }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<K, V> previous) {
/* 1260 */       this.previousWrite = previous;
/*      */     } }
/*      */ 
/*      */   
/*      */   static class WeakEntry<K, V> extends WeakReference<K> implements ReferenceEntry<K, V> {
/*      */     final int hash;
/*      */     final LocalCache.ReferenceEntry<K, V> next;
/*      */     volatile LocalCache.ValueReference<K, V> valueReference;
/*      */     
/* 1269 */     WeakEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) { super(key, queue);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1352 */       this.valueReference = LocalCache.unset(); this.hash = hash; this.next = next; }
/*      */     public K getKey() { return get(); }
/*      */     public long getAccessTime() { throw new UnsupportedOperationException(); }
/*      */     public void setAccessTime(long time) { throw new UnsupportedOperationException(); }
/* 1356 */     public LocalCache.ReferenceEntry<K, V> getNextInAccessQueue() { throw new UnsupportedOperationException(); } public void setNextInAccessQueue(LocalCache.ReferenceEntry<K, V> next) { throw new UnsupportedOperationException(); } public LocalCache.ReferenceEntry<K, V> getPreviousInAccessQueue() { throw new UnsupportedOperationException(); } public LocalCache.ValueReference<K, V> getValueReference() { return this.valueReference; }
/*      */     public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<K, V> previous) { throw new UnsupportedOperationException(); }
/*      */     public long getWriteTime() { throw new UnsupportedOperationException(); }
/*      */     public void setWriteTime(long time) { throw new UnsupportedOperationException(); }
/*      */     public LocalCache.ReferenceEntry<K, V> getNextInWriteQueue() { throw new UnsupportedOperationException(); }
/* 1361 */     public void setNextInWriteQueue(LocalCache.ReferenceEntry<K, V> next) { throw new UnsupportedOperationException(); } public LocalCache.ReferenceEntry<K, V> getPreviousInWriteQueue() { throw new UnsupportedOperationException(); } public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<K, V> previous) { throw new UnsupportedOperationException(); } public void setValueReference(LocalCache.ValueReference<K, V> valueReference) { this.valueReference = valueReference; }
/*      */ 
/*      */ 
/*      */     
/*      */     public int getHash() {
/* 1366 */       return this.hash;
/*      */     }
/*      */ 
/*      */     
/*      */     public LocalCache.ReferenceEntry<K, V> getNext() {
/* 1371 */       return this.next;
/*      */     } }
/*      */   
/*      */   static final class WeakAccessEntry<K, V> extends WeakEntry<K, V> { volatile long accessTime;
/*      */     LocalCache.ReferenceEntry<K, V> nextAccess;
/*      */     LocalCache.ReferenceEntry<K, V> previousAccess;
/*      */     
/* 1378 */     WeakAccessEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) { super(queue, key, hash, next);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1383 */       this.accessTime = Long.MAX_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1396 */       this.nextAccess = LocalCache.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1409 */       this.previousAccess = LocalCache.nullEntry(); } public long getAccessTime() { return this.accessTime; }
/*      */     public void setAccessTime(long time) { this.accessTime = time; }
/*      */     public LocalCache.ReferenceEntry<K, V> getNextInAccessQueue() { return this.nextAccess; }
/*      */     public void setNextInAccessQueue(LocalCache.ReferenceEntry<K, V> next) { this.nextAccess = next; }
/* 1413 */     public LocalCache.ReferenceEntry<K, V> getPreviousInAccessQueue() { return this.previousAccess; }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<K, V> previous) {
/* 1418 */       this.previousAccess = previous;
/*      */     } }
/*      */   
/*      */   static final class WeakWriteEntry<K, V> extends WeakEntry<K, V> { volatile long writeTime;
/*      */     LocalCache.ReferenceEntry<K, V> nextWrite;
/*      */     LocalCache.ReferenceEntry<K, V> previousWrite;
/*      */     
/* 1425 */     WeakWriteEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) { super(queue, key, hash, next);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1430 */       this.writeTime = Long.MAX_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1443 */       this.nextWrite = LocalCache.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1456 */       this.previousWrite = LocalCache.nullEntry(); } public long getWriteTime() { return this.writeTime; }
/*      */     public void setWriteTime(long time) { this.writeTime = time; }
/*      */     public LocalCache.ReferenceEntry<K, V> getNextInWriteQueue() { return this.nextWrite; }
/*      */     public void setNextInWriteQueue(LocalCache.ReferenceEntry<K, V> next) { this.nextWrite = next; }
/* 1460 */     public LocalCache.ReferenceEntry<K, V> getPreviousInWriteQueue() { return this.previousWrite; }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<K, V> previous) {
/* 1465 */       this.previousWrite = previous;
/*      */     } }
/*      */   static final class WeakAccessWriteEntry<K, V> extends WeakEntry<K, V> { volatile long accessTime; LocalCache.ReferenceEntry<K, V> nextAccess; LocalCache.ReferenceEntry<K, V> previousAccess;
/*      */     volatile long writeTime;
/*      */     LocalCache.ReferenceEntry<K, V> nextWrite;
/*      */     LocalCache.ReferenceEntry<K, V> previousWrite;
/*      */     
/* 1472 */     WeakAccessWriteEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) { super(queue, key, hash, next);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1477 */       this.accessTime = Long.MAX_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1490 */       this.nextAccess = LocalCache.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1503 */       this.previousAccess = LocalCache.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1517 */       this.writeTime = Long.MAX_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1530 */       this.nextWrite = LocalCache.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1543 */       this.previousWrite = LocalCache.nullEntry(); } public long getAccessTime() { return this.accessTime; } public void setAccessTime(long time) { this.accessTime = time; } public LocalCache.ReferenceEntry<K, V> getNextInAccessQueue() { return this.nextAccess; } public void setNextInAccessQueue(LocalCache.ReferenceEntry<K, V> next) { this.nextAccess = next; } public LocalCache.ReferenceEntry<K, V> getPreviousInAccessQueue() { return this.previousAccess; } public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<K, V> previous) { this.previousAccess = previous; } public long getWriteTime() { return this.writeTime; }
/*      */     public void setWriteTime(long time) { this.writeTime = time; }
/*      */     public LocalCache.ReferenceEntry<K, V> getNextInWriteQueue() { return this.nextWrite; }
/*      */     public void setNextInWriteQueue(LocalCache.ReferenceEntry<K, V> next) { this.nextWrite = next; }
/* 1547 */     public LocalCache.ReferenceEntry<K, V> getPreviousInWriteQueue() { return this.previousWrite; }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<K, V> previous) {
/* 1552 */       this.previousWrite = previous;
/*      */     } }
/*      */ 
/*      */ 
/*      */   
/*      */   static class WeakValueReference<K, V>
/*      */     extends WeakReference<V>
/*      */     implements ValueReference<K, V>
/*      */   {
/*      */     final LocalCache.ReferenceEntry<K, V> entry;
/*      */     
/*      */     WeakValueReference(ReferenceQueue<V> queue, V referent, LocalCache.ReferenceEntry<K, V> entry) {
/* 1564 */       super(referent, queue);
/* 1565 */       this.entry = entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getWeight() {
/* 1570 */       return 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public LocalCache.ReferenceEntry<K, V> getEntry() {
/* 1575 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void notifyNewValue(V newValue) {}
/*      */ 
/*      */     
/*      */     public LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, LocalCache.ReferenceEntry<K, V> entry) {
/* 1584 */       return new WeakValueReference(queue, value, entry);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isLoading() {
/* 1589 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isActive() {
/* 1594 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public V waitForValue() {
/* 1599 */       return get();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class SoftValueReference<K, V>
/*      */     extends SoftReference<V>
/*      */     implements ValueReference<K, V>
/*      */   {
/*      */     final LocalCache.ReferenceEntry<K, V> entry;
/*      */     
/*      */     SoftValueReference(ReferenceQueue<V> queue, V referent, LocalCache.ReferenceEntry<K, V> entry) {
/* 1611 */       super(referent, queue);
/* 1612 */       this.entry = entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getWeight() {
/* 1617 */       return 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public LocalCache.ReferenceEntry<K, V> getEntry() {
/* 1622 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void notifyNewValue(V newValue) {}
/*      */ 
/*      */     
/*      */     public LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, LocalCache.ReferenceEntry<K, V> entry) {
/* 1631 */       return new SoftValueReference(queue, value, entry);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isLoading() {
/* 1636 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isActive() {
/* 1641 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public V waitForValue() {
/* 1646 */       return get();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class StrongValueReference<K, V>
/*      */     implements ValueReference<K, V>
/*      */   {
/*      */     final V referent;
/*      */     
/*      */     StrongValueReference(V referent) {
/* 1657 */       this.referent = referent;
/*      */     }
/*      */ 
/*      */     
/*      */     public V get() {
/* 1662 */       return this.referent;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getWeight() {
/* 1667 */       return 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public LocalCache.ReferenceEntry<K, V> getEntry() {
/* 1672 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, LocalCache.ReferenceEntry<K, V> entry) {
/* 1678 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isLoading() {
/* 1683 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isActive() {
/* 1688 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public V waitForValue() {
/* 1693 */       return get();
/*      */     }
/*      */ 
/*      */     
/*      */     public void notifyNewValue(V newValue) {}
/*      */   }
/*      */ 
/*      */   
/*      */   static final class WeightedWeakValueReference<K, V>
/*      */     extends WeakValueReference<K, V>
/*      */   {
/*      */     final int weight;
/*      */ 
/*      */     
/*      */     WeightedWeakValueReference(ReferenceQueue<V> queue, V referent, LocalCache.ReferenceEntry<K, V> entry, int weight) {
/* 1708 */       super(queue, referent, entry);
/* 1709 */       this.weight = weight;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getWeight() {
/* 1714 */       return this.weight;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, LocalCache.ReferenceEntry<K, V> entry) {
/* 1720 */       return new WeightedWeakValueReference(queue, value, entry, this.weight);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class WeightedSoftValueReference<K, V>
/*      */     extends SoftValueReference<K, V>
/*      */   {
/*      */     final int weight;
/*      */ 
/*      */     
/*      */     WeightedSoftValueReference(ReferenceQueue<V> queue, V referent, LocalCache.ReferenceEntry<K, V> entry, int weight) {
/* 1732 */       super(queue, referent, entry);
/* 1733 */       this.weight = weight;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getWeight() {
/* 1738 */       return this.weight;
/*      */     }
/*      */ 
/*      */     
/*      */     public LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, LocalCache.ReferenceEntry<K, V> entry) {
/* 1743 */       return new WeightedSoftValueReference(queue, value, entry, this.weight);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class WeightedStrongValueReference<K, V>
/*      */     extends StrongValueReference<K, V>
/*      */   {
/*      */     final int weight;
/*      */ 
/*      */     
/*      */     WeightedStrongValueReference(V referent, int weight) {
/* 1755 */       super(referent);
/* 1756 */       this.weight = weight;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getWeight() {
/* 1761 */       return this.weight;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int rehash(int h) {
/* 1777 */     h += h << 15 ^ 0xFFFFCD7D;
/* 1778 */     h ^= h >>> 10;
/* 1779 */     h += h << 3;
/* 1780 */     h ^= h >>> 6;
/* 1781 */     h += (h << 2) + (h << 14);
/* 1782 */     return h ^ h >>> 16;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @VisibleForTesting
/*      */   ReferenceEntry<K, V> newEntry(K key, int hash, @Nullable ReferenceEntry<K, V> next) {
/* 1790 */     Segment<K, V> segment = segmentFor(hash);
/* 1791 */     segment.lock();
/*      */     try {
/* 1793 */       return segment.newEntry(key, hash, next);
/*      */     } finally {
/* 1795 */       segment.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @VisibleForTesting
/*      */   ReferenceEntry<K, V> copyEntry(ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
/* 1805 */     int hash = original.getHash();
/* 1806 */     return segmentFor(hash).copyEntry(original, newNext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @VisibleForTesting
/*      */   ValueReference<K, V> newValueReference(ReferenceEntry<K, V> entry, V value, int weight) {
/* 1815 */     int hash = entry.getHash();
/* 1816 */     return this.valueStrength.referenceValue(segmentFor(hash), entry, (V)Preconditions.checkNotNull(value), weight);
/*      */   }
/*      */   
/*      */   int hash(@Nullable Object key) {
/* 1820 */     int h = this.keyEquivalence.hash(key);
/* 1821 */     return rehash(h);
/*      */   }
/*      */   
/*      */   void reclaimValue(ValueReference<K, V> valueReference) {
/* 1825 */     ReferenceEntry<K, V> entry = valueReference.getEntry();
/* 1826 */     int hash = entry.getHash();
/* 1827 */     segmentFor(hash).reclaimValue(entry.getKey(), hash, valueReference);
/*      */   }
/*      */   
/*      */   void reclaimKey(ReferenceEntry<K, V> entry) {
/* 1831 */     int hash = entry.getHash();
/* 1832 */     segmentFor(hash).reclaimKey(entry, hash);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @VisibleForTesting
/*      */   boolean isLive(ReferenceEntry<K, V> entry, long now) {
/* 1841 */     return (segmentFor(entry.getHash()).getLiveValue(entry, now) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Segment<K, V> segmentFor(int hash) {
/* 1852 */     return this.segments[hash >>> this.segmentShift & this.segmentMask];
/*      */   }
/*      */ 
/*      */   
/*      */   Segment<K, V> createSegment(int initialCapacity, long maxSegmentWeight, AbstractCache.StatsCounter statsCounter) {
/* 1857 */     return new Segment<K, V>(this, initialCapacity, maxSegmentWeight, statsCounter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   V getLiveValue(ReferenceEntry<K, V> entry, long now) {
/* 1868 */     if (entry.getKey() == null) {
/* 1869 */       return null;
/*      */     }
/* 1871 */     V value = (V)entry.getValueReference().get();
/* 1872 */     if (value == null) {
/* 1873 */       return null;
/*      */     }
/*      */     
/* 1876 */     if (isExpired(entry, now)) {
/* 1877 */       return null;
/*      */     }
/* 1879 */     return value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isExpired(ReferenceEntry<K, V> entry, long now) {
/* 1888 */     Preconditions.checkNotNull(entry);
/* 1889 */     if (expiresAfterAccess() && now - entry
/* 1890 */       .getAccessTime() >= this.expireAfterAccessNanos) {
/* 1891 */       return true;
/*      */     }
/* 1893 */     if (expiresAfterWrite() && now - entry
/* 1894 */       .getWriteTime() >= this.expireAfterWriteNanos) {
/* 1895 */       return true;
/*      */     }
/* 1897 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <K, V> void connectAccessOrder(ReferenceEntry<K, V> previous, ReferenceEntry<K, V> next) {
/* 1904 */     previous.setNextInAccessQueue(next);
/* 1905 */     next.setPreviousInAccessQueue(previous);
/*      */   }
/*      */ 
/*      */   
/*      */   static <K, V> void nullifyAccessOrder(ReferenceEntry<K, V> nulled) {
/* 1910 */     ReferenceEntry<K, V> nullEntry = nullEntry();
/* 1911 */     nulled.setNextInAccessQueue(nullEntry);
/* 1912 */     nulled.setPreviousInAccessQueue(nullEntry);
/*      */   }
/*      */ 
/*      */   
/*      */   static <K, V> void connectWriteOrder(ReferenceEntry<K, V> previous, ReferenceEntry<K, V> next) {
/* 1917 */     previous.setNextInWriteQueue(next);
/* 1918 */     next.setPreviousInWriteQueue(previous);
/*      */   }
/*      */ 
/*      */   
/*      */   static <K, V> void nullifyWriteOrder(ReferenceEntry<K, V> nulled) {
/* 1923 */     ReferenceEntry<K, V> nullEntry = nullEntry();
/* 1924 */     nulled.setNextInWriteQueue(nullEntry);
/* 1925 */     nulled.setPreviousInWriteQueue(nullEntry);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void processPendingNotifications() {
/*      */     RemovalNotification<K, V> notification;
/* 1935 */     while ((notification = this.removalNotificationQueue.poll()) != null) {
/*      */       try {
/* 1937 */         this.removalListener.onRemoval(notification);
/* 1938 */       } catch (Throwable e) {
/* 1939 */         logger.log(Level.WARNING, "Exception thrown by removal listener", e);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   final Segment<K, V>[] newSegmentArray(int ssize) {
/* 1946 */     return (Segment<K, V>[])new Segment[ssize];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class Segment<K, V>
/*      */     extends ReentrantLock
/*      */   {
/*      */     final LocalCache<K, V> map;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     volatile int count;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("this")
/*      */     long totalWeight;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int modCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int threshold;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     volatile AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final long maxSegmentWeight;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final ReferenceQueue<K> keyReferenceQueue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final ReferenceQueue<V> valueReferenceQueue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final Queue<LocalCache.ReferenceEntry<K, V>> recencyQueue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2051 */     final AtomicInteger readCount = new AtomicInteger();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("this")
/*      */     final Queue<LocalCache.ReferenceEntry<K, V>> writeQueue;
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("this")
/*      */     final Queue<LocalCache.ReferenceEntry<K, V>> accessQueue;
/*      */ 
/*      */ 
/*      */     
/*      */     final AbstractCache.StatsCounter statsCounter;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Segment(LocalCache<K, V> map, int initialCapacity, long maxSegmentWeight, AbstractCache.StatsCounter statsCounter) {
/* 2072 */       this.map = map;
/* 2073 */       this.maxSegmentWeight = maxSegmentWeight;
/* 2074 */       this.statsCounter = (AbstractCache.StatsCounter)Preconditions.checkNotNull(statsCounter);
/* 2075 */       initTable(newEntryArray(initialCapacity));
/*      */       
/* 2077 */       this.keyReferenceQueue = map.usesKeyReferences() ? new ReferenceQueue<K>() : null;
/*      */ 
/*      */       
/* 2080 */       this.valueReferenceQueue = map.usesValueReferences() ? new ReferenceQueue<V>() : null;
/*      */ 
/*      */       
/* 2083 */       this
/*      */         
/* 2085 */         .recencyQueue = map.usesAccessQueue() ? new ConcurrentLinkedQueue<LocalCache.ReferenceEntry<K, V>>() : LocalCache.<LocalCache.ReferenceEntry<K, V>>discardingQueue();
/*      */       
/* 2087 */       this
/*      */         
/* 2089 */         .writeQueue = map.usesWriteQueue() ? new LocalCache.WriteQueue<K, V>() : LocalCache.<LocalCache.ReferenceEntry<K, V>>discardingQueue();
/*      */       
/* 2091 */       this
/*      */         
/* 2093 */         .accessQueue = map.usesAccessQueue() ? new LocalCache.AccessQueue<K, V>() : LocalCache.<LocalCache.ReferenceEntry<K, V>>discardingQueue();
/*      */     }
/*      */     
/*      */     AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> newEntryArray(int size) {
/* 2097 */       return new AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>>(size);
/*      */     }
/*      */     
/*      */     void initTable(AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> newTable) {
/* 2101 */       this.threshold = newTable.length() * 3 / 4;
/* 2102 */       if (!this.map.customWeigher() && this.threshold == this.maxSegmentWeight)
/*      */       {
/* 2104 */         this.threshold++;
/*      */       }
/* 2106 */       this.table = newTable;
/*      */     }
/*      */     
/*      */     @GuardedBy("this")
/*      */     LocalCache.ReferenceEntry<K, V> newEntry(K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) {
/* 2111 */       return this.map.entryFactory.newEntry(this, (K)Preconditions.checkNotNull(key), hash, next);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("this")
/*      */     LocalCache.ReferenceEntry<K, V> copyEntry(LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newNext) {
/* 2120 */       if (original.getKey() == null)
/*      */       {
/* 2122 */         return null;
/*      */       }
/*      */       
/* 2125 */       LocalCache.ValueReference<K, V> valueReference = original.getValueReference();
/* 2126 */       V value = valueReference.get();
/* 2127 */       if (value == null && valueReference.isActive())
/*      */       {
/* 2129 */         return null;
/*      */       }
/*      */       
/* 2132 */       LocalCache.ReferenceEntry<K, V> newEntry = this.map.entryFactory.copyEntry(this, original, newNext);
/* 2133 */       newEntry.setValueReference(valueReference.copyFor(this.valueReferenceQueue, value, newEntry));
/* 2134 */       return newEntry;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("this")
/*      */     void setValue(LocalCache.ReferenceEntry<K, V> entry, K key, V value, long now) {
/* 2142 */       LocalCache.ValueReference<K, V> previous = entry.getValueReference();
/* 2143 */       int weight = this.map.weigher.weigh(key, value);
/* 2144 */       Preconditions.checkState((weight >= 0), "Weights must be non-negative");
/*      */ 
/*      */       
/* 2147 */       LocalCache.ValueReference<K, V> valueReference = this.map.valueStrength.referenceValue(this, entry, value, weight);
/* 2148 */       entry.setValueReference(valueReference);
/* 2149 */       recordWrite(entry, weight, now);
/* 2150 */       previous.notifyNewValue(value);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     V get(K key, int hash, CacheLoader<? super K, V> loader) throws ExecutionException {
/* 2156 */       Preconditions.checkNotNull(key);
/* 2157 */       Preconditions.checkNotNull(loader);
/*      */       try {
/* 2159 */         if (this.count != 0) {
/*      */           
/* 2161 */           LocalCache.ReferenceEntry<K, V> e = getEntry(key, hash);
/* 2162 */           if (e != null) {
/* 2163 */             long now = this.map.ticker.read();
/* 2164 */             V value = getLiveValue(e, now);
/* 2165 */             if (value != null) {
/* 2166 */               recordRead(e, now);
/* 2167 */               this.statsCounter.recordHits(1);
/* 2168 */               return scheduleRefresh(e, key, hash, value, now, loader);
/*      */             } 
/* 2170 */             LocalCache.ValueReference<K, V> valueReference = e.getValueReference();
/* 2171 */             if (valueReference.isLoading()) {
/* 2172 */               return waitForLoadingValue(e, key, valueReference);
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 2178 */         return lockedGetOrLoad(key, hash, loader);
/* 2179 */       } catch (ExecutionException ee) {
/* 2180 */         Throwable cause = ee.getCause();
/* 2181 */         if (cause instanceof Error)
/* 2182 */           throw new ExecutionError((Error)cause); 
/* 2183 */         if (cause instanceof RuntimeException) {
/* 2184 */           throw new UncheckedExecutionException(cause);
/*      */         }
/* 2186 */         throw ee;
/*      */       } finally {
/* 2188 */         postReadCleanup();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     V lockedGetOrLoad(K key, int hash, CacheLoader<? super K, V> loader) throws ExecutionException {
/*      */       LocalCache.ReferenceEntry<K, V> e;
/* 2195 */       LocalCache.ValueReference<K, V> valueReference = null;
/* 2196 */       LocalCache.LoadingValueReference<K, V> loadingValueReference = null;
/* 2197 */       boolean createNewEntry = true;
/*      */       
/* 2199 */       lock();
/*      */       
/*      */       try {
/* 2202 */         long now = this.map.ticker.read();
/* 2203 */         preWriteCleanup(now);
/*      */         
/* 2205 */         int newCount = this.count - 1;
/* 2206 */         AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
/* 2207 */         int index = hash & table.length() - 1;
/* 2208 */         LocalCache.ReferenceEntry<K, V> first = table.get(index);
/*      */         
/* 2210 */         for (e = first; e != null; e = e.getNext()) {
/* 2211 */           K entryKey = e.getKey();
/* 2212 */           if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence
/* 2213 */             .equivalent(key, entryKey)) {
/* 2214 */             valueReference = e.getValueReference();
/* 2215 */             if (valueReference.isLoading()) {
/* 2216 */               createNewEntry = false; break;
/*      */             } 
/* 2218 */             V value = valueReference.get();
/* 2219 */             if (value == null) {
/* 2220 */               enqueueNotification(entryKey, hash, valueReference, RemovalCause.COLLECTED);
/* 2221 */             } else if (this.map.isExpired(e, now)) {
/*      */ 
/*      */               
/* 2224 */               enqueueNotification(entryKey, hash, valueReference, RemovalCause.EXPIRED);
/*      */             } else {
/* 2226 */               recordLockedRead(e, now);
/* 2227 */               this.statsCounter.recordHits(1);
/*      */               
/* 2229 */               return value;
/*      */             } 
/*      */ 
/*      */             
/* 2233 */             this.writeQueue.remove(e);
/* 2234 */             this.accessQueue.remove(e);
/* 2235 */             this.count = newCount;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */         
/* 2241 */         if (createNewEntry) {
/* 2242 */           loadingValueReference = new LocalCache.LoadingValueReference<K, V>();
/*      */           
/* 2244 */           if (e == null) {
/* 2245 */             e = newEntry(key, hash, first);
/* 2246 */             e.setValueReference(loadingValueReference);
/* 2247 */             table.set(index, e);
/*      */           } else {
/* 2249 */             e.setValueReference(loadingValueReference);
/*      */           } 
/*      */         } 
/*      */       } finally {
/* 2253 */         unlock();
/* 2254 */         postWriteCleanup();
/*      */       } 
/*      */       
/* 2257 */       if (createNewEntry) {
/*      */         
/*      */         try {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         } finally {
/*      */           
/* 2266 */           this.statsCounter.recordMisses(1);
/*      */         } 
/*      */       }
/*      */       
/* 2270 */       return waitForLoadingValue(e, key, valueReference);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     V waitForLoadingValue(LocalCache.ReferenceEntry<K, V> e, K key, LocalCache.ValueReference<K, V> valueReference) throws ExecutionException {
/* 2276 */       if (!valueReference.isLoading()) {
/* 2277 */         throw new AssertionError();
/*      */       }
/*      */       
/* 2280 */       Preconditions.checkState(!Thread.holdsLock(e), "Recursive load of: %s", new Object[] { key });
/*      */       
/*      */       try {
/* 2283 */         V value = valueReference.waitForValue();
/* 2284 */         if (value == null) {
/* 2285 */           throw new CacheLoader.InvalidCacheLoadException("CacheLoader returned null for key " + key + ".");
/*      */         }
/*      */         
/* 2288 */         long now = this.map.ticker.read();
/* 2289 */         recordRead(e, now);
/* 2290 */         return value;
/*      */       } finally {
/* 2292 */         this.statsCounter.recordMisses(1);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     V loadSync(K key, int hash, LocalCache.LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> loader) throws ExecutionException {
/* 2300 */       ListenableFuture<V> loadingFuture = loadingValueReference.loadFuture(key, loader);
/* 2301 */       return getAndRecordStats(key, hash, loadingValueReference, loadingFuture);
/*      */     }
/*      */ 
/*      */     
/*      */     ListenableFuture<V> loadAsync(final K key, final int hash, final LocalCache.LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> loader) {
/* 2306 */       final ListenableFuture<V> loadingFuture = loadingValueReference.loadFuture(key, loader);
/* 2307 */       loadingFuture.addListener(new Runnable()
/*      */           {
/*      */             public void run()
/*      */             {
/*      */               try {
/* 2312 */                 V newValue = LocalCache.Segment.this.getAndRecordStats(key, hash, loadingValueReference, loadingFuture);
/* 2313 */               } catch (Throwable t) {
/* 2314 */                 LocalCache.logger.log(Level.WARNING, "Exception thrown during refresh", t);
/* 2315 */                 loadingValueReference.setException(t);
/*      */               } 
/*      */             }
/* 2318 */           }(Executor)LocalCache.directExecutor);
/* 2319 */       return loadingFuture;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     V getAndRecordStats(K key, int hash, LocalCache.LoadingValueReference<K, V> loadingValueReference, ListenableFuture<V> newValue) throws ExecutionException {
/* 2327 */       V value = null;
/*      */       try {
/* 2329 */         value = (V)Uninterruptibles.getUninterruptibly((Future)newValue);
/* 2330 */         if (value == null) {
/* 2331 */           throw new CacheLoader.InvalidCacheLoadException("CacheLoader returned null for key " + key + ".");
/*      */         }
/* 2333 */         this.statsCounter.recordLoadSuccess(loadingValueReference.elapsedNanos());
/* 2334 */         storeLoadedValue(key, hash, loadingValueReference, value);
/* 2335 */         return value;
/*      */       } finally {
/* 2337 */         if (value == null) {
/* 2338 */           this.statsCounter.recordLoadException(loadingValueReference.elapsedNanos());
/* 2339 */           removeLoadingValue(key, hash, loadingValueReference);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     V scheduleRefresh(LocalCache.ReferenceEntry<K, V> entry, K key, int hash, V oldValue, long now, CacheLoader<? super K, V> loader) {
/* 2346 */       if (this.map.refreshes() && now - entry.getWriteTime() > this.map.refreshNanos && 
/* 2347 */         !entry.getValueReference().isLoading()) {
/* 2348 */         V newValue = refresh(key, hash, loader, true);
/* 2349 */         if (newValue != null) {
/* 2350 */           return newValue;
/*      */         }
/*      */       } 
/* 2353 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     V refresh(K key, int hash, CacheLoader<? super K, V> loader, boolean checkTime) {
/* 2365 */       LocalCache.LoadingValueReference<K, V> loadingValueReference = insertLoadingValueReference(key, hash, checkTime);
/* 2366 */       if (loadingValueReference == null) {
/* 2367 */         return null;
/*      */       }
/*      */       
/* 2370 */       ListenableFuture<V> result = loadAsync(key, hash, loadingValueReference, loader);
/* 2371 */       if (result.isDone()) {
/*      */         try {
/* 2373 */           return (V)Uninterruptibles.getUninterruptibly((Future)result);
/* 2374 */         } catch (Throwable t) {}
/*      */       }
/*      */ 
/*      */       
/* 2378 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     LocalCache.LoadingValueReference<K, V> insertLoadingValueReference(K key, int hash, boolean checkTime) {
/* 2388 */       LocalCache.ReferenceEntry<K, V> e = null;
/* 2389 */       lock();
/*      */       try {
/* 2391 */         long now = this.map.ticker.read();
/* 2392 */         preWriteCleanup(now);
/*      */         
/* 2394 */         AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
/* 2395 */         int index = hash & table.length() - 1;
/* 2396 */         LocalCache.ReferenceEntry<K, V> first = table.get(index);
/*      */ 
/*      */         
/* 2399 */         for (e = first; e != null; e = e.getNext()) {
/* 2400 */           K entryKey = e.getKey();
/* 2401 */           if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence
/* 2402 */             .equivalent(key, entryKey)) {
/*      */ 
/*      */             
/* 2405 */             LocalCache.ValueReference<K, V> valueReference = e.getValueReference();
/* 2406 */             if (valueReference.isLoading() || (checkTime && now - e
/* 2407 */               .getWriteTime() < this.map.refreshNanos))
/*      */             {
/*      */ 
/*      */               
/* 2411 */               return null;
/*      */             }
/*      */ 
/*      */             
/* 2415 */             this.modCount++;
/* 2416 */             LocalCache.LoadingValueReference<K, V> loadingValueReference1 = new LocalCache.LoadingValueReference<K, V>(valueReference);
/*      */             
/* 2418 */             e.setValueReference(loadingValueReference1);
/* 2419 */             return loadingValueReference1;
/*      */           } 
/*      */         } 
/*      */         
/* 2423 */         this.modCount++;
/* 2424 */         LocalCache.LoadingValueReference<K, V> loadingValueReference = new LocalCache.LoadingValueReference<K, V>();
/* 2425 */         e = newEntry(key, hash, first);
/* 2426 */         e.setValueReference(loadingValueReference);
/* 2427 */         table.set(index, e);
/* 2428 */         return loadingValueReference;
/*      */       } finally {
/* 2430 */         unlock();
/* 2431 */         postWriteCleanup();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void tryDrainReferenceQueues() {
/* 2441 */       if (tryLock()) {
/*      */         try {
/* 2443 */           drainReferenceQueues();
/*      */         } finally {
/* 2445 */           unlock();
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("this")
/*      */     void drainReferenceQueues() {
/* 2456 */       if (this.map.usesKeyReferences()) {
/* 2457 */         drainKeyReferenceQueue();
/*      */       }
/* 2459 */       if (this.map.usesValueReferences()) {
/* 2460 */         drainValueReferenceQueue();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     @GuardedBy("this")
/*      */     void drainKeyReferenceQueue() {
/* 2467 */       int i = 0; Reference<? extends K> ref;
/* 2468 */       while ((ref = this.keyReferenceQueue.poll()) != null) {
/*      */         
/* 2470 */         LocalCache.ReferenceEntry<K, V> entry = (LocalCache.ReferenceEntry)ref;
/* 2471 */         this.map.reclaimKey(entry);
/* 2472 */         if (++i == 16) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @GuardedBy("this")
/*      */     void drainValueReferenceQueue() {
/* 2481 */       int i = 0; Reference<? extends V> ref;
/* 2482 */       while ((ref = this.valueReferenceQueue.poll()) != null) {
/*      */         
/* 2484 */         LocalCache.ValueReference<K, V> valueReference = (LocalCache.ValueReference)ref;
/* 2485 */         this.map.reclaimValue(valueReference);
/* 2486 */         if (++i == 16) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void clearReferenceQueues() {
/* 2496 */       if (this.map.usesKeyReferences()) {
/* 2497 */         clearKeyReferenceQueue();
/*      */       }
/* 2499 */       if (this.map.usesValueReferences()) {
/* 2500 */         clearValueReferenceQueue();
/*      */       }
/*      */     }
/*      */     
/*      */     void clearKeyReferenceQueue() {
/* 2505 */       while (this.keyReferenceQueue.poll() != null);
/*      */     }
/*      */     
/*      */     void clearValueReferenceQueue() {
/* 2509 */       while (this.valueReferenceQueue.poll() != null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void recordRead(LocalCache.ReferenceEntry<K, V> entry, long now) {
/* 2522 */       if (this.map.recordsAccess()) {
/* 2523 */         entry.setAccessTime(now);
/*      */       }
/* 2525 */       this.recencyQueue.add(entry);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("this")
/*      */     void recordLockedRead(LocalCache.ReferenceEntry<K, V> entry, long now) {
/* 2537 */       if (this.map.recordsAccess()) {
/* 2538 */         entry.setAccessTime(now);
/*      */       }
/* 2540 */       this.accessQueue.add(entry);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("this")
/*      */     void recordWrite(LocalCache.ReferenceEntry<K, V> entry, int weight, long now) {
/* 2550 */       drainRecencyQueue();
/* 2551 */       this.totalWeight += weight;
/*      */       
/* 2553 */       if (this.map.recordsAccess()) {
/* 2554 */         entry.setAccessTime(now);
/*      */       }
/* 2556 */       if (this.map.recordsWrite()) {
/* 2557 */         entry.setWriteTime(now);
/*      */       }
/* 2559 */       this.accessQueue.add(entry);
/* 2560 */       this.writeQueue.add(entry);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("this")
/*      */     void drainRecencyQueue() {
/*      */       LocalCache.ReferenceEntry<K, V> e;
/* 2572 */       while ((e = this.recencyQueue.poll()) != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2577 */         if (this.accessQueue.contains(e)) {
/* 2578 */           this.accessQueue.add(e);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void tryExpireEntries(long now) {
/* 2589 */       if (tryLock()) {
/*      */         try {
/* 2591 */           expireEntries(now);
/*      */         } finally {
/* 2593 */           unlock();
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     @GuardedBy("this")
/*      */     void expireEntries(long now) {
/* 2601 */       drainRecencyQueue();
/*      */       
/*      */       LocalCache.ReferenceEntry<K, V> e;
/* 2604 */       while ((e = this.writeQueue.peek()) != null && this.map.isExpired(e, now)) {
/* 2605 */         if (!removeEntry(e, e.getHash(), RemovalCause.EXPIRED)) {
/* 2606 */           throw new AssertionError();
/*      */         }
/*      */       } 
/* 2609 */       while ((e = this.accessQueue.peek()) != null && this.map.isExpired(e, now)) {
/* 2610 */         if (!removeEntry(e, e.getHash(), RemovalCause.EXPIRED)) {
/* 2611 */           throw new AssertionError();
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("this")
/*      */     void enqueueNotification(LocalCache.ReferenceEntry<K, V> entry, RemovalCause cause) {
/* 2620 */       enqueueNotification(entry.getKey(), entry.getHash(), entry.getValueReference(), cause);
/*      */     }
/*      */ 
/*      */     
/*      */     @GuardedBy("this")
/*      */     void enqueueNotification(@Nullable K key, int hash, LocalCache.ValueReference<K, V> valueReference, RemovalCause cause) {
/* 2626 */       this.totalWeight -= valueReference.getWeight();
/* 2627 */       if (cause.wasEvicted()) {
/* 2628 */         this.statsCounter.recordEviction();
/*      */       }
/* 2630 */       if (this.map.removalNotificationQueue != LocalCache.DISCARDING_QUEUE) {
/* 2631 */         V value = valueReference.get();
/* 2632 */         RemovalNotification<K, V> notification = new RemovalNotification<K, V>(key, value, cause);
/* 2633 */         this.map.removalNotificationQueue.offer(notification);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("this")
/*      */     void evictEntries() {
/* 2643 */       if (!this.map.evictsBySize()) {
/*      */         return;
/*      */       }
/*      */       
/* 2647 */       drainRecencyQueue();
/* 2648 */       while (this.totalWeight > this.maxSegmentWeight) {
/* 2649 */         LocalCache.ReferenceEntry<K, V> e = getNextEvictable();
/* 2650 */         if (!removeEntry(e, e.getHash(), RemovalCause.SIZE)) {
/* 2651 */           throw new AssertionError();
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @GuardedBy("this")
/*      */     LocalCache.ReferenceEntry<K, V> getNextEvictable() {
/* 2659 */       for (LocalCache.ReferenceEntry<K, V> e : this.accessQueue) {
/* 2660 */         int weight = e.getValueReference().getWeight();
/* 2661 */         if (weight > 0) {
/* 2662 */           return e;
/*      */         }
/*      */       } 
/* 2665 */       throw new AssertionError();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     LocalCache.ReferenceEntry<K, V> getFirst(int hash) {
/* 2673 */       AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
/* 2674 */       return table.get(hash & table.length() - 1);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     LocalCache.ReferenceEntry<K, V> getEntry(Object key, int hash) {
/* 2681 */       for (LocalCache.ReferenceEntry<K, V> e = getFirst(hash); e != null; e = e.getNext()) {
/* 2682 */         if (e.getHash() == hash) {
/*      */ 
/*      */ 
/*      */           
/* 2686 */           K entryKey = e.getKey();
/* 2687 */           if (entryKey == null) {
/* 2688 */             tryDrainReferenceQueues();
/*      */ 
/*      */           
/*      */           }
/* 2692 */           else if (this.map.keyEquivalence.equivalent(key, entryKey)) {
/* 2693 */             return e;
/*      */           } 
/*      */         } 
/*      */       } 
/* 2697 */       return null;
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     LocalCache.ReferenceEntry<K, V> getLiveEntry(Object key, int hash, long now) {
/* 2702 */       LocalCache.ReferenceEntry<K, V> e = getEntry(key, hash);
/* 2703 */       if (e == null)
/* 2704 */         return null; 
/* 2705 */       if (this.map.isExpired(e, now)) {
/* 2706 */         tryExpireEntries(now);
/* 2707 */         return null;
/*      */       } 
/* 2709 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     V getLiveValue(LocalCache.ReferenceEntry<K, V> entry, long now) {
/* 2717 */       if (entry.getKey() == null) {
/* 2718 */         tryDrainReferenceQueues();
/* 2719 */         return null;
/*      */       } 
/* 2721 */       V value = (V)entry.getValueReference().get();
/* 2722 */       if (value == null) {
/* 2723 */         tryDrainReferenceQueues();
/* 2724 */         return null;
/*      */       } 
/*      */       
/* 2727 */       if (this.map.isExpired(entry, now)) {
/* 2728 */         tryExpireEntries(now);
/* 2729 */         return null;
/*      */       } 
/* 2731 */       return value;
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     V get(Object key, int hash) {
/*      */       try {
/* 2737 */         if (this.count != 0) {
/* 2738 */           long now = this.map.ticker.read();
/* 2739 */           LocalCache.ReferenceEntry<K, V> e = getLiveEntry(key, hash, now);
/* 2740 */           if (e == null) {
/* 2741 */             return null;
/*      */           }
/*      */           
/* 2744 */           V value = (V)e.getValueReference().get();
/* 2745 */           if (value != null) {
/* 2746 */             recordRead(e, now);
/* 2747 */             return scheduleRefresh(e, e.getKey(), hash, value, now, this.map.defaultLoader);
/*      */           } 
/* 2749 */           tryDrainReferenceQueues();
/*      */         } 
/* 2751 */         return null;
/*      */       } finally {
/* 2753 */         postReadCleanup();
/*      */       } 
/*      */     }
/*      */     
/*      */     boolean containsKey(Object key, int hash) {
/*      */       try {
/* 2759 */         if (this.count != 0) {
/* 2760 */           long now = this.map.ticker.read();
/* 2761 */           LocalCache.ReferenceEntry<K, V> e = getLiveEntry(key, hash, now);
/* 2762 */           if (e == null) {
/* 2763 */             return false;
/*      */           }
/* 2765 */           return (e.getValueReference().get() != null);
/*      */         } 
/*      */         
/* 2768 */         return false;
/*      */       } finally {
/* 2770 */         postReadCleanup();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @VisibleForTesting
/*      */     boolean containsValue(Object value) {
/*      */       try {
/* 2781 */         if (this.count != 0) {
/* 2782 */           long now = this.map.ticker.read();
/* 2783 */           AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
/* 2784 */           int length = table.length();
/* 2785 */           for (int i = 0; i < length; i++) {
/* 2786 */             for (LocalCache.ReferenceEntry<K, V> e = table.get(i); e != null; e = e.getNext()) {
/* 2787 */               V entryValue = getLiveValue(e, now);
/* 2788 */               if (entryValue != null)
/*      */               {
/*      */                 
/* 2791 */                 if (this.map.valueEquivalence.equivalent(value, entryValue)) {
/* 2792 */                   return true;
/*      */                 }
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/* 2798 */         return false;
/*      */       } finally {
/* 2800 */         postReadCleanup();
/*      */       } 
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     V put(K key, int hash, V value, boolean onlyIfAbsent) {
/* 2806 */       lock();
/*      */       try {
/* 2808 */         long now = this.map.ticker.read();
/* 2809 */         preWriteCleanup(now);
/*      */         
/* 2811 */         int newCount = this.count + 1;
/* 2812 */         if (newCount > this.threshold) {
/* 2813 */           expand();
/* 2814 */           newCount = this.count + 1;
/*      */         } 
/*      */         
/* 2817 */         AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
/* 2818 */         int index = hash & table.length() - 1;
/* 2819 */         LocalCache.ReferenceEntry<K, V> first = table.get(index);
/*      */ 
/*      */         
/* 2822 */         for (LocalCache.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
/* 2823 */           K entryKey = e.getKey();
/* 2824 */           if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence
/* 2825 */             .equivalent(key, entryKey)) {
/*      */ 
/*      */             
/* 2828 */             LocalCache.ValueReference<K, V> valueReference = e.getValueReference();
/* 2829 */             V entryValue = valueReference.get();
/*      */             
/* 2831 */             if (entryValue == null) {
/* 2832 */               this.modCount++;
/* 2833 */               if (valueReference.isActive()) {
/* 2834 */                 enqueueNotification(key, hash, valueReference, RemovalCause.COLLECTED);
/* 2835 */                 setValue(e, key, value, now);
/* 2836 */                 newCount = this.count;
/*      */               } else {
/* 2838 */                 setValue(e, key, value, now);
/* 2839 */                 newCount = this.count + 1;
/*      */               } 
/* 2841 */               this.count = newCount;
/* 2842 */               evictEntries();
/* 2843 */               return null;
/* 2844 */             }  if (onlyIfAbsent) {
/*      */ 
/*      */ 
/*      */               
/* 2848 */               recordLockedRead(e, now);
/* 2849 */               return entryValue;
/*      */             } 
/*      */             
/* 2852 */             this.modCount++;
/* 2853 */             enqueueNotification(key, hash, valueReference, RemovalCause.REPLACED);
/* 2854 */             setValue(e, key, value, now);
/* 2855 */             evictEntries();
/* 2856 */             return entryValue;
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 2862 */         this.modCount++;
/* 2863 */         LocalCache.ReferenceEntry<K, V> newEntry = newEntry(key, hash, first);
/* 2864 */         setValue(newEntry, key, value, now);
/* 2865 */         table.set(index, newEntry);
/* 2866 */         newCount = this.count + 1;
/* 2867 */         this.count = newCount;
/* 2868 */         evictEntries();
/* 2869 */         return null;
/*      */       } finally {
/* 2871 */         unlock();
/* 2872 */         postWriteCleanup();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("this")
/*      */     void expand() {
/* 2881 */       AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> oldTable = this.table;
/* 2882 */       int oldCapacity = oldTable.length();
/* 2883 */       if (oldCapacity >= 1073741824) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2897 */       int newCount = this.count;
/* 2898 */       AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> newTable = newEntryArray(oldCapacity << 1);
/* 2899 */       this.threshold = newTable.length() * 3 / 4;
/* 2900 */       int newMask = newTable.length() - 1;
/* 2901 */       for (int oldIndex = 0; oldIndex < oldCapacity; oldIndex++) {
/*      */ 
/*      */         
/* 2904 */         LocalCache.ReferenceEntry<K, V> head = oldTable.get(oldIndex);
/*      */         
/* 2906 */         if (head != null) {
/* 2907 */           LocalCache.ReferenceEntry<K, V> next = head.getNext();
/* 2908 */           int headIndex = head.getHash() & newMask;
/*      */ 
/*      */           
/* 2911 */           if (next == null) {
/* 2912 */             newTable.set(headIndex, head);
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 2917 */             LocalCache.ReferenceEntry<K, V> tail = head;
/* 2918 */             int tailIndex = headIndex; LocalCache.ReferenceEntry<K, V> e;
/* 2919 */             for (e = next; e != null; e = e.getNext()) {
/* 2920 */               int newIndex = e.getHash() & newMask;
/* 2921 */               if (newIndex != tailIndex) {
/*      */                 
/* 2923 */                 tailIndex = newIndex;
/* 2924 */                 tail = e;
/*      */               } 
/*      */             } 
/* 2927 */             newTable.set(tailIndex, tail);
/*      */ 
/*      */             
/* 2930 */             for (e = head; e != tail; e = e.getNext()) {
/* 2931 */               int newIndex = e.getHash() & newMask;
/* 2932 */               LocalCache.ReferenceEntry<K, V> newNext = newTable.get(newIndex);
/* 2933 */               LocalCache.ReferenceEntry<K, V> newFirst = copyEntry(e, newNext);
/* 2934 */               if (newFirst != null) {
/* 2935 */                 newTable.set(newIndex, newFirst);
/*      */               } else {
/* 2937 */                 removeCollectedEntry(e);
/* 2938 */                 newCount--;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 2944 */       this.table = newTable;
/* 2945 */       this.count = newCount;
/*      */     }
/*      */     
/*      */     boolean replace(K key, int hash, V oldValue, V newValue) {
/* 2949 */       lock();
/*      */       try {
/* 2951 */         long now = this.map.ticker.read();
/* 2952 */         preWriteCleanup(now);
/*      */         
/* 2954 */         AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
/* 2955 */         int index = hash & table.length() - 1;
/* 2956 */         LocalCache.ReferenceEntry<K, V> first = table.get(index);
/*      */         
/* 2958 */         for (LocalCache.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
/* 2959 */           K entryKey = e.getKey();
/* 2960 */           if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence
/* 2961 */             .equivalent(key, entryKey)) {
/* 2962 */             LocalCache.ValueReference<K, V> valueReference = e.getValueReference();
/* 2963 */             V entryValue = valueReference.get();
/* 2964 */             if (entryValue == null) {
/* 2965 */               if (valueReference.isActive()) {
/*      */                 
/* 2967 */                 int newCount = this.count - 1;
/* 2968 */                 this.modCount++;
/* 2969 */                 LocalCache.ReferenceEntry<K, V> newFirst = removeValueFromChain(first, e, entryKey, hash, valueReference, RemovalCause.COLLECTED);
/*      */                 
/* 2971 */                 newCount = this.count - 1;
/* 2972 */                 table.set(index, newFirst);
/* 2973 */                 this.count = newCount;
/*      */               } 
/* 2975 */               return false;
/*      */             } 
/*      */             
/* 2978 */             if (this.map.valueEquivalence.equivalent(oldValue, entryValue)) {
/* 2979 */               this.modCount++;
/* 2980 */               enqueueNotification(key, hash, valueReference, RemovalCause.REPLACED);
/* 2981 */               setValue(e, key, newValue, now);
/* 2982 */               evictEntries();
/* 2983 */               return true;
/*      */             } 
/*      */ 
/*      */             
/* 2987 */             recordLockedRead(e, now);
/* 2988 */             return false;
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 2993 */         return false;
/*      */       } finally {
/* 2995 */         unlock();
/* 2996 */         postWriteCleanup();
/*      */       } 
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     V replace(K key, int hash, V newValue) {
/* 3002 */       lock();
/*      */       
/* 3004 */       try { long now = this.map.ticker.read();
/* 3005 */         preWriteCleanup(now);
/*      */         
/* 3007 */         AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
/* 3008 */         int index = hash & table.length() - 1;
/* 3009 */         LocalCache.ReferenceEntry<K, V> first = table.get(index);
/*      */         LocalCache.ReferenceEntry<K, V> e;
/* 3011 */         for (e = first; e != null; e = e.getNext()) {
/* 3012 */           K entryKey = e.getKey();
/* 3013 */           if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence
/* 3014 */             .equivalent(key, entryKey)) {
/* 3015 */             LocalCache.ValueReference<K, V> valueReference = e.getValueReference();
/* 3016 */             V entryValue = valueReference.get();
/* 3017 */             if (entryValue == null) {
/* 3018 */               if (valueReference.isActive()) {
/*      */                 
/* 3020 */                 int newCount = this.count - 1;
/* 3021 */                 this.modCount++;
/* 3022 */                 LocalCache.ReferenceEntry<K, V> newFirst = removeValueFromChain(first, e, entryKey, hash, valueReference, RemovalCause.COLLECTED);
/*      */                 
/* 3024 */                 newCount = this.count - 1;
/* 3025 */                 table.set(index, newFirst);
/* 3026 */                 this.count = newCount;
/*      */               } 
/* 3028 */               return null;
/*      */             } 
/*      */             
/* 3031 */             this.modCount++;
/* 3032 */             enqueueNotification(key, hash, valueReference, RemovalCause.REPLACED);
/* 3033 */             setValue(e, key, newValue, now);
/* 3034 */             evictEntries();
/* 3035 */             return entryValue;
/*      */           } 
/*      */         } 
/*      */         
/* 3039 */         e = null;
/*      */ 
/*      */         
/* 3042 */         return (V)e; } finally { unlock(); postWriteCleanup(); }
/*      */     
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     V remove(Object key, int hash) {
/* 3048 */       lock();
/*      */       
/* 3050 */       try { long now = this.map.ticker.read();
/* 3051 */         preWriteCleanup(now);
/*      */         
/* 3053 */         int newCount = this.count - 1;
/* 3054 */         AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
/* 3055 */         int index = hash & table.length() - 1;
/* 3056 */         LocalCache.ReferenceEntry<K, V> first = table.get(index);
/*      */         LocalCache.ReferenceEntry<K, V> e;
/* 3058 */         for (e = first; e != null; e = e.getNext()) {
/* 3059 */           K entryKey = e.getKey();
/* 3060 */           if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence
/* 3061 */             .equivalent(key, entryKey)) {
/* 3062 */             RemovalCause cause; LocalCache.ValueReference<K, V> valueReference = e.getValueReference();
/* 3063 */             V entryValue = valueReference.get();
/*      */ 
/*      */             
/* 3066 */             if (entryValue != null) {
/* 3067 */               cause = RemovalCause.EXPLICIT;
/* 3068 */             } else if (valueReference.isActive()) {
/* 3069 */               cause = RemovalCause.COLLECTED;
/*      */             } else {
/*      */               
/* 3072 */               return null;
/*      */             } 
/*      */             
/* 3075 */             this.modCount++;
/* 3076 */             LocalCache.ReferenceEntry<K, V> newFirst = removeValueFromChain(first, e, entryKey, hash, valueReference, cause);
/*      */             
/* 3078 */             newCount = this.count - 1;
/* 3079 */             table.set(index, newFirst);
/* 3080 */             this.count = newCount;
/* 3081 */             return entryValue;
/*      */           } 
/*      */         } 
/*      */         
/* 3085 */         e = null;
/*      */ 
/*      */         
/* 3088 */         return (V)e; } finally { unlock(); postWriteCleanup(); }
/*      */     
/*      */     }
/*      */ 
/*      */     
/*      */     boolean storeLoadedValue(K key, int hash, LocalCache.LoadingValueReference<K, V> oldValueReference, V newValue) {
/* 3094 */       lock();
/*      */       try {
/* 3096 */         long now = this.map.ticker.read();
/* 3097 */         preWriteCleanup(now);
/*      */         
/* 3099 */         int newCount = this.count + 1;
/* 3100 */         if (newCount > this.threshold) {
/* 3101 */           expand();
/* 3102 */           newCount = this.count + 1;
/*      */         } 
/*      */         
/* 3105 */         AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
/* 3106 */         int index = hash & table.length() - 1;
/* 3107 */         LocalCache.ReferenceEntry<K, V> first = table.get(index);
/*      */         
/* 3109 */         for (LocalCache.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
/* 3110 */           K entryKey = e.getKey();
/* 3111 */           if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence
/* 3112 */             .equivalent(key, entryKey)) {
/* 3113 */             LocalCache.ValueReference<K, V> valueReference = e.getValueReference();
/* 3114 */             V entryValue = valueReference.get();
/*      */ 
/*      */             
/* 3117 */             if (oldValueReference == valueReference || (entryValue == null && valueReference != LocalCache.UNSET)) {
/*      */               
/* 3119 */               this.modCount++;
/* 3120 */               if (oldValueReference.isActive()) {
/* 3121 */                 RemovalCause cause = (entryValue == null) ? RemovalCause.COLLECTED : RemovalCause.REPLACED;
/*      */                 
/* 3123 */                 enqueueNotification(key, hash, oldValueReference, cause);
/* 3124 */                 newCount--;
/*      */               } 
/* 3126 */               setValue(e, key, newValue, now);
/* 3127 */               this.count = newCount;
/* 3128 */               evictEntries();
/* 3129 */               return true;
/*      */             } 
/*      */ 
/*      */             
/* 3133 */             valueReference = new LocalCache.WeightedStrongValueReference<K, V>(newValue, 0);
/* 3134 */             enqueueNotification(key, hash, valueReference, RemovalCause.REPLACED);
/* 3135 */             return false;
/*      */           } 
/*      */         } 
/*      */         
/* 3139 */         this.modCount++;
/* 3140 */         LocalCache.ReferenceEntry<K, V> newEntry = newEntry(key, hash, first);
/* 3141 */         setValue(newEntry, key, newValue, now);
/* 3142 */         table.set(index, newEntry);
/* 3143 */         this.count = newCount;
/* 3144 */         evictEntries();
/* 3145 */         return true;
/*      */       } finally {
/* 3147 */         unlock();
/* 3148 */         postWriteCleanup();
/*      */       } 
/*      */     }
/*      */     
/*      */     boolean remove(Object key, int hash, Object value) {
/* 3153 */       lock();
/*      */       try {
/* 3155 */         long now = this.map.ticker.read();
/* 3156 */         preWriteCleanup(now);
/*      */         
/* 3158 */         int newCount = this.count - 1;
/* 3159 */         AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
/* 3160 */         int index = hash & table.length() - 1;
/* 3161 */         LocalCache.ReferenceEntry<K, V> first = table.get(index);
/*      */         
/* 3163 */         for (LocalCache.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
/* 3164 */           K entryKey = e.getKey();
/* 3165 */           if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence
/* 3166 */             .equivalent(key, entryKey)) {
/* 3167 */             RemovalCause cause; LocalCache.ValueReference<K, V> valueReference = e.getValueReference();
/* 3168 */             V entryValue = valueReference.get();
/*      */ 
/*      */             
/* 3171 */             if (this.map.valueEquivalence.equivalent(value, entryValue)) {
/* 3172 */               cause = RemovalCause.EXPLICIT;
/* 3173 */             } else if (entryValue == null && valueReference.isActive()) {
/* 3174 */               cause = RemovalCause.COLLECTED;
/*      */             } else {
/*      */               
/* 3177 */               return false;
/*      */             } 
/*      */             
/* 3180 */             this.modCount++;
/* 3181 */             LocalCache.ReferenceEntry<K, V> newFirst = removeValueFromChain(first, e, entryKey, hash, valueReference, cause);
/*      */             
/* 3183 */             newCount = this.count - 1;
/* 3184 */             table.set(index, newFirst);
/* 3185 */             this.count = newCount;
/* 3186 */             return (cause == RemovalCause.EXPLICIT);
/*      */           } 
/*      */         } 
/*      */         
/* 3190 */         return false;
/*      */       } finally {
/* 3192 */         unlock();
/* 3193 */         postWriteCleanup();
/*      */       } 
/*      */     }
/*      */     
/*      */     void clear() {
/* 3198 */       if (this.count != 0) {
/* 3199 */         lock();
/*      */         try {
/* 3201 */           AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table; int i;
/* 3202 */           for (i = 0; i < table.length(); i++) {
/* 3203 */             for (LocalCache.ReferenceEntry<K, V> e = table.get(i); e != null; e = e.getNext()) {
/*      */               
/* 3205 */               if (e.getValueReference().isActive()) {
/* 3206 */                 enqueueNotification(e, RemovalCause.EXPLICIT);
/*      */               }
/*      */             } 
/*      */           } 
/* 3210 */           for (i = 0; i < table.length(); i++) {
/* 3211 */             table.set(i, null);
/*      */           }
/* 3213 */           clearReferenceQueues();
/* 3214 */           this.writeQueue.clear();
/* 3215 */           this.accessQueue.clear();
/* 3216 */           this.readCount.set(0);
/*      */           
/* 3218 */           this.modCount++;
/* 3219 */           this.count = 0;
/*      */         } finally {
/* 3221 */           unlock();
/* 3222 */           postWriteCleanup();
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     @GuardedBy("this")
/*      */     LocalCache.ReferenceEntry<K, V> removeValueFromChain(LocalCache.ReferenceEntry<K, V> first, LocalCache.ReferenceEntry<K, V> entry, @Nullable K key, int hash, LocalCache.ValueReference<K, V> valueReference, RemovalCause cause) {
/* 3232 */       enqueueNotification(key, hash, valueReference, cause);
/* 3233 */       this.writeQueue.remove(entry);
/* 3234 */       this.accessQueue.remove(entry);
/*      */       
/* 3236 */       if (valueReference.isLoading()) {
/* 3237 */         valueReference.notifyNewValue(null);
/* 3238 */         return first;
/*      */       } 
/* 3240 */       return removeEntryFromChain(first, entry);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     @GuardedBy("this")
/*      */     LocalCache.ReferenceEntry<K, V> removeEntryFromChain(LocalCache.ReferenceEntry<K, V> first, LocalCache.ReferenceEntry<K, V> entry) {
/* 3248 */       int newCount = this.count;
/* 3249 */       LocalCache.ReferenceEntry<K, V> newFirst = entry.getNext();
/* 3250 */       for (LocalCache.ReferenceEntry<K, V> e = first; e != entry; e = e.getNext()) {
/* 3251 */         LocalCache.ReferenceEntry<K, V> next = copyEntry(e, newFirst);
/* 3252 */         if (next != null) {
/* 3253 */           newFirst = next;
/*      */         } else {
/* 3255 */           removeCollectedEntry(e);
/* 3256 */           newCount--;
/*      */         } 
/*      */       } 
/* 3259 */       this.count = newCount;
/* 3260 */       return newFirst;
/*      */     }
/*      */     
/*      */     @GuardedBy("this")
/*      */     void removeCollectedEntry(LocalCache.ReferenceEntry<K, V> entry) {
/* 3265 */       enqueueNotification(entry, RemovalCause.COLLECTED);
/* 3266 */       this.writeQueue.remove(entry);
/* 3267 */       this.accessQueue.remove(entry);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean reclaimKey(LocalCache.ReferenceEntry<K, V> entry, int hash) {
/* 3274 */       lock();
/*      */       try {
/* 3276 */         int newCount = this.count - 1;
/* 3277 */         AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
/* 3278 */         int index = hash & table.length() - 1;
/* 3279 */         LocalCache.ReferenceEntry<K, V> first = table.get(index);
/*      */         
/* 3281 */         for (LocalCache.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
/* 3282 */           if (e == entry) {
/* 3283 */             this.modCount++;
/* 3284 */             LocalCache.ReferenceEntry<K, V> newFirst = removeValueFromChain(first, e, e
/* 3285 */                 .getKey(), hash, e.getValueReference(), RemovalCause.COLLECTED);
/* 3286 */             newCount = this.count - 1;
/* 3287 */             table.set(index, newFirst);
/* 3288 */             this.count = newCount;
/* 3289 */             return true;
/*      */           } 
/*      */         } 
/*      */         
/* 3293 */         return false;
/*      */       } finally {
/* 3295 */         unlock();
/* 3296 */         postWriteCleanup();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean reclaimValue(K key, int hash, LocalCache.ValueReference<K, V> valueReference) {
/* 3304 */       lock();
/*      */       try {
/* 3306 */         int newCount = this.count - 1;
/* 3307 */         AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
/* 3308 */         int index = hash & table.length() - 1;
/* 3309 */         LocalCache.ReferenceEntry<K, V> first = table.get(index);
/*      */         
/* 3311 */         for (LocalCache.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
/* 3312 */           K entryKey = e.getKey();
/* 3313 */           if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence
/* 3314 */             .equivalent(key, entryKey)) {
/* 3315 */             LocalCache.ValueReference<K, V> v = e.getValueReference();
/* 3316 */             if (v == valueReference) {
/* 3317 */               this.modCount++;
/* 3318 */               LocalCache.ReferenceEntry<K, V> newFirst = removeValueFromChain(first, e, entryKey, hash, valueReference, RemovalCause.COLLECTED);
/*      */               
/* 3320 */               newCount = this.count - 1;
/* 3321 */               table.set(index, newFirst);
/* 3322 */               this.count = newCount;
/* 3323 */               return true;
/*      */             } 
/* 3325 */             return false;
/*      */           } 
/*      */         } 
/*      */         
/* 3329 */         return false;
/*      */       } finally {
/* 3331 */         unlock();
/* 3332 */         if (!isHeldByCurrentThread()) {
/* 3333 */           postWriteCleanup();
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     boolean removeLoadingValue(K key, int hash, LocalCache.LoadingValueReference<K, V> valueReference) {
/* 3339 */       lock();
/*      */       try {
/* 3341 */         AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
/* 3342 */         int index = hash & table.length() - 1;
/* 3343 */         LocalCache.ReferenceEntry<K, V> first = table.get(index);
/*      */         
/* 3345 */         for (LocalCache.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
/* 3346 */           K entryKey = e.getKey();
/* 3347 */           if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence
/* 3348 */             .equivalent(key, entryKey)) {
/* 3349 */             LocalCache.ValueReference<K, V> v = e.getValueReference();
/* 3350 */             if (v == valueReference) {
/* 3351 */               if (valueReference.isActive()) {
/* 3352 */                 e.setValueReference(valueReference.getOldValue());
/*      */               } else {
/* 3354 */                 LocalCache.ReferenceEntry<K, V> newFirst = removeEntryFromChain(first, e);
/* 3355 */                 table.set(index, newFirst);
/*      */               } 
/* 3357 */               return true;
/*      */             } 
/* 3359 */             return false;
/*      */           } 
/*      */         } 
/*      */         
/* 3363 */         return false;
/*      */       } finally {
/* 3365 */         unlock();
/* 3366 */         postWriteCleanup();
/*      */       } 
/*      */     }
/*      */     
/*      */     @GuardedBy("this")
/*      */     boolean removeEntry(LocalCache.ReferenceEntry<K, V> entry, int hash, RemovalCause cause) {
/* 3372 */       int newCount = this.count - 1;
/* 3373 */       AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
/* 3374 */       int index = hash & table.length() - 1;
/* 3375 */       LocalCache.ReferenceEntry<K, V> first = table.get(index);
/*      */       
/* 3377 */       for (LocalCache.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
/* 3378 */         if (e == entry) {
/* 3379 */           this.modCount++;
/* 3380 */           LocalCache.ReferenceEntry<K, V> newFirst = removeValueFromChain(first, e, e
/* 3381 */               .getKey(), hash, e.getValueReference(), cause);
/* 3382 */           newCount = this.count - 1;
/* 3383 */           table.set(index, newFirst);
/* 3384 */           this.count = newCount;
/* 3385 */           return true;
/*      */         } 
/*      */       } 
/*      */       
/* 3389 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void postReadCleanup() {
/* 3397 */       if ((this.readCount.incrementAndGet() & 0x3F) == 0) {
/* 3398 */         cleanUp();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("this")
/*      */     void preWriteCleanup(long now) {
/* 3410 */       runLockedCleanup(now);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void postWriteCleanup() {
/* 3417 */       runUnlockedCleanup();
/*      */     }
/*      */     
/*      */     void cleanUp() {
/* 3421 */       long now = this.map.ticker.read();
/* 3422 */       runLockedCleanup(now);
/* 3423 */       runUnlockedCleanup();
/*      */     }
/*      */     
/*      */     void runLockedCleanup(long now) {
/* 3427 */       if (tryLock()) {
/*      */         try {
/* 3429 */           drainReferenceQueues();
/* 3430 */           expireEntries(now);
/* 3431 */           this.readCount.set(0);
/*      */         } finally {
/* 3433 */           unlock();
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     void runUnlockedCleanup() {
/* 3440 */       if (!isHeldByCurrentThread()) {
/* 3441 */         this.map.processPendingNotifications();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class LoadingValueReference<K, V>
/*      */     implements ValueReference<K, V>
/*      */   {
/*      */     volatile LocalCache.ValueReference<K, V> oldValue;
/* 3451 */     final SettableFuture<V> futureValue = SettableFuture.create();
/* 3452 */     final Stopwatch stopwatch = Stopwatch.createUnstarted();
/*      */     
/*      */     public LoadingValueReference() {
/* 3455 */       this(LocalCache.unset());
/*      */     }
/*      */     
/*      */     public LoadingValueReference(LocalCache.ValueReference<K, V> oldValue) {
/* 3459 */       this.oldValue = oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isLoading() {
/* 3464 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isActive() {
/* 3469 */       return this.oldValue.isActive();
/*      */     }
/*      */ 
/*      */     
/*      */     public int getWeight() {
/* 3474 */       return this.oldValue.getWeight();
/*      */     }
/*      */     
/*      */     public boolean set(@Nullable V newValue) {
/* 3478 */       return this.futureValue.set(newValue);
/*      */     }
/*      */     
/*      */     public boolean setException(Throwable t) {
/* 3482 */       return this.futureValue.setException(t);
/*      */     }
/*      */     
/*      */     private ListenableFuture<V> fullyFailedFuture(Throwable t) {
/* 3486 */       return Futures.immediateFailedFuture(t);
/*      */     }
/*      */ 
/*      */     
/*      */     public void notifyNewValue(@Nullable V newValue) {
/* 3491 */       if (newValue != null) {
/*      */ 
/*      */         
/* 3494 */         set(newValue);
/*      */       } else {
/*      */         
/* 3497 */         this.oldValue = LocalCache.unset();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ListenableFuture<V> loadFuture(K key, CacheLoader<? super K, V> loader) {
/* 3504 */       this.stopwatch.start();
/* 3505 */       V previousValue = this.oldValue.get();
/*      */       try {
/* 3507 */         if (previousValue == null) {
/* 3508 */           V v = loader.load(key);
/* 3509 */           return set(v) ? (ListenableFuture<V>)this.futureValue : Futures.immediateFuture(v);
/*      */         } 
/* 3511 */         ListenableFuture<V> newValue = loader.reload(key, previousValue);
/* 3512 */         if (newValue == null) {
/* 3513 */           return Futures.immediateFuture(null);
/*      */         }
/*      */ 
/*      */         
/* 3517 */         return Futures.transform(newValue, new Function<V, V>()
/*      */             {
/*      */               public V apply(V newValue) {
/* 3520 */                 LocalCache.LoadingValueReference.this.set(newValue);
/* 3521 */                 return newValue;
/*      */               }
/*      */             });
/* 3524 */       } catch (Throwable t) {
/* 3525 */         if (t instanceof InterruptedException) {
/* 3526 */           Thread.currentThread().interrupt();
/*      */         }
/* 3528 */         return setException(t) ? (ListenableFuture<V>)this.futureValue : fullyFailedFuture(t);
/*      */       } 
/*      */     }
/*      */     
/*      */     public long elapsedNanos() {
/* 3533 */       return this.stopwatch.elapsed(TimeUnit.NANOSECONDS);
/*      */     }
/*      */ 
/*      */     
/*      */     public V waitForValue() throws ExecutionException {
/* 3538 */       return (V)Uninterruptibles.getUninterruptibly((Future)this.futureValue);
/*      */     }
/*      */ 
/*      */     
/*      */     public V get() {
/* 3543 */       return this.oldValue.get();
/*      */     }
/*      */     
/*      */     public LocalCache.ValueReference<K, V> getOldValue() {
/* 3547 */       return this.oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public LocalCache.ReferenceEntry<K, V> getEntry() {
/* 3552 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, @Nullable V value, LocalCache.ReferenceEntry<K, V> entry) {
/* 3558 */       return this;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class WriteQueue<K, V>
/*      */     extends AbstractQueue<ReferenceEntry<K, V>>
/*      */   {
/* 3576 */     final LocalCache.ReferenceEntry<K, V> head = new LocalCache.AbstractReferenceEntry<K, V>()
/*      */       {
/*      */         public long getWriteTime()
/*      */         {
/* 3580 */           return Long.MAX_VALUE;
/*      */         }
/*      */ 
/*      */         
/*      */         public void setWriteTime(long time) {}
/*      */         
/* 3586 */         LocalCache.ReferenceEntry<K, V> nextWrite = this;
/*      */ 
/*      */         
/*      */         public LocalCache.ReferenceEntry<K, V> getNextInWriteQueue() {
/* 3590 */           return this.nextWrite;
/*      */         }
/*      */ 
/*      */         
/*      */         public void setNextInWriteQueue(LocalCache.ReferenceEntry<K, V> next) {
/* 3595 */           this.nextWrite = next;
/*      */         }
/*      */         
/* 3598 */         LocalCache.ReferenceEntry<K, V> previousWrite = this;
/*      */ 
/*      */         
/*      */         public LocalCache.ReferenceEntry<K, V> getPreviousInWriteQueue() {
/* 3602 */           return this.previousWrite;
/*      */         }
/*      */ 
/*      */         
/*      */         public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<K, V> previous) {
/* 3607 */           this.previousWrite = previous;
/*      */         }
/*      */       };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean offer(LocalCache.ReferenceEntry<K, V> entry) {
/* 3616 */       LocalCache.connectWriteOrder(entry.getPreviousInWriteQueue(), entry.getNextInWriteQueue());
/*      */ 
/*      */       
/* 3619 */       LocalCache.connectWriteOrder(this.head.getPreviousInWriteQueue(), entry);
/* 3620 */       LocalCache.connectWriteOrder(entry, this.head);
/*      */       
/* 3622 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public LocalCache.ReferenceEntry<K, V> peek() {
/* 3627 */       LocalCache.ReferenceEntry<K, V> next = this.head.getNextInWriteQueue();
/* 3628 */       return (next == this.head) ? null : next;
/*      */     }
/*      */ 
/*      */     
/*      */     public LocalCache.ReferenceEntry<K, V> poll() {
/* 3633 */       LocalCache.ReferenceEntry<K, V> next = this.head.getNextInWriteQueue();
/* 3634 */       if (next == this.head) {
/* 3635 */         return null;
/*      */       }
/*      */       
/* 3638 */       remove(next);
/* 3639 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 3645 */       LocalCache.ReferenceEntry<K, V> e = (LocalCache.ReferenceEntry<K, V>)o;
/* 3646 */       LocalCache.ReferenceEntry<K, V> previous = e.getPreviousInWriteQueue();
/* 3647 */       LocalCache.ReferenceEntry<K, V> next = e.getNextInWriteQueue();
/* 3648 */       LocalCache.connectWriteOrder(previous, next);
/* 3649 */       LocalCache.nullifyWriteOrder(e);
/*      */       
/* 3651 */       return (next != LocalCache.NullEntry.INSTANCE);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 3657 */       LocalCache.ReferenceEntry<K, V> e = (LocalCache.ReferenceEntry<K, V>)o;
/* 3658 */       return (e.getNextInWriteQueue() != LocalCache.NullEntry.INSTANCE);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 3663 */       return (this.head.getNextInWriteQueue() == this.head);
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 3668 */       int size = 0;
/* 3669 */       for (LocalCache.ReferenceEntry<K, V> e = this.head.getNextInWriteQueue(); e != this.head; 
/* 3670 */         e = e.getNextInWriteQueue()) {
/* 3671 */         size++;
/*      */       }
/* 3673 */       return size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 3678 */       LocalCache.ReferenceEntry<K, V> e = this.head.getNextInWriteQueue();
/* 3679 */       while (e != this.head) {
/* 3680 */         LocalCache.ReferenceEntry<K, V> next = e.getNextInWriteQueue();
/* 3681 */         LocalCache.nullifyWriteOrder(e);
/* 3682 */         e = next;
/*      */       } 
/*      */       
/* 3685 */       this.head.setNextInWriteQueue(this.head);
/* 3686 */       this.head.setPreviousInWriteQueue(this.head);
/*      */     }
/*      */ 
/*      */     
/*      */     public Iterator<LocalCache.ReferenceEntry<K, V>> iterator() {
/* 3691 */       return (Iterator<LocalCache.ReferenceEntry<K, V>>)new AbstractSequentialIterator<LocalCache.ReferenceEntry<K, V>>(peek())
/*      */         {
/*      */           protected LocalCache.ReferenceEntry<K, V> computeNext(LocalCache.ReferenceEntry<K, V> previous) {
/* 3694 */             LocalCache.ReferenceEntry<K, V> next = previous.getNextInWriteQueue();
/* 3695 */             return (next == LocalCache.WriteQueue.this.head) ? null : next;
/*      */           }
/*      */         };
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class AccessQueue<K, V>
/*      */     extends AbstractQueue<ReferenceEntry<K, V>>
/*      */   {
/* 3713 */     final LocalCache.ReferenceEntry<K, V> head = new LocalCache.AbstractReferenceEntry<K, V>()
/*      */       {
/*      */         public long getAccessTime()
/*      */         {
/* 3717 */           return Long.MAX_VALUE;
/*      */         }
/*      */ 
/*      */         
/*      */         public void setAccessTime(long time) {}
/*      */         
/* 3723 */         LocalCache.ReferenceEntry<K, V> nextAccess = this;
/*      */ 
/*      */         
/*      */         public LocalCache.ReferenceEntry<K, V> getNextInAccessQueue() {
/* 3727 */           return this.nextAccess;
/*      */         }
/*      */ 
/*      */         
/*      */         public void setNextInAccessQueue(LocalCache.ReferenceEntry<K, V> next) {
/* 3732 */           this.nextAccess = next;
/*      */         }
/*      */         
/* 3735 */         LocalCache.ReferenceEntry<K, V> previousAccess = this;
/*      */ 
/*      */         
/*      */         public LocalCache.ReferenceEntry<K, V> getPreviousInAccessQueue() {
/* 3739 */           return this.previousAccess;
/*      */         }
/*      */ 
/*      */         
/*      */         public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<K, V> previous) {
/* 3744 */           this.previousAccess = previous;
/*      */         }
/*      */       };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean offer(LocalCache.ReferenceEntry<K, V> entry) {
/* 3753 */       LocalCache.connectAccessOrder(entry.getPreviousInAccessQueue(), entry.getNextInAccessQueue());
/*      */ 
/*      */       
/* 3756 */       LocalCache.connectAccessOrder(this.head.getPreviousInAccessQueue(), entry);
/* 3757 */       LocalCache.connectAccessOrder(entry, this.head);
/*      */       
/* 3759 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public LocalCache.ReferenceEntry<K, V> peek() {
/* 3764 */       LocalCache.ReferenceEntry<K, V> next = this.head.getNextInAccessQueue();
/* 3765 */       return (next == this.head) ? null : next;
/*      */     }
/*      */ 
/*      */     
/*      */     public LocalCache.ReferenceEntry<K, V> poll() {
/* 3770 */       LocalCache.ReferenceEntry<K, V> next = this.head.getNextInAccessQueue();
/* 3771 */       if (next == this.head) {
/* 3772 */         return null;
/*      */       }
/*      */       
/* 3775 */       remove(next);
/* 3776 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 3782 */       LocalCache.ReferenceEntry<K, V> e = (LocalCache.ReferenceEntry<K, V>)o;
/* 3783 */       LocalCache.ReferenceEntry<K, V> previous = e.getPreviousInAccessQueue();
/* 3784 */       LocalCache.ReferenceEntry<K, V> next = e.getNextInAccessQueue();
/* 3785 */       LocalCache.connectAccessOrder(previous, next);
/* 3786 */       LocalCache.nullifyAccessOrder(e);
/*      */       
/* 3788 */       return (next != LocalCache.NullEntry.INSTANCE);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 3794 */       LocalCache.ReferenceEntry<K, V> e = (LocalCache.ReferenceEntry<K, V>)o;
/* 3795 */       return (e.getNextInAccessQueue() != LocalCache.NullEntry.INSTANCE);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 3800 */       return (this.head.getNextInAccessQueue() == this.head);
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 3805 */       int size = 0;
/* 3806 */       for (LocalCache.ReferenceEntry<K, V> e = this.head.getNextInAccessQueue(); e != this.head; 
/* 3807 */         e = e.getNextInAccessQueue()) {
/* 3808 */         size++;
/*      */       }
/* 3810 */       return size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 3815 */       LocalCache.ReferenceEntry<K, V> e = this.head.getNextInAccessQueue();
/* 3816 */       while (e != this.head) {
/* 3817 */         LocalCache.ReferenceEntry<K, V> next = e.getNextInAccessQueue();
/* 3818 */         LocalCache.nullifyAccessOrder(e);
/* 3819 */         e = next;
/*      */       } 
/*      */       
/* 3822 */       this.head.setNextInAccessQueue(this.head);
/* 3823 */       this.head.setPreviousInAccessQueue(this.head);
/*      */     }
/*      */ 
/*      */     
/*      */     public Iterator<LocalCache.ReferenceEntry<K, V>> iterator() {
/* 3828 */       return (Iterator<LocalCache.ReferenceEntry<K, V>>)new AbstractSequentialIterator<LocalCache.ReferenceEntry<K, V>>(peek())
/*      */         {
/*      */           protected LocalCache.ReferenceEntry<K, V> computeNext(LocalCache.ReferenceEntry<K, V> previous) {
/* 3831 */             LocalCache.ReferenceEntry<K, V> next = previous.getNextInAccessQueue();
/* 3832 */             return (next == LocalCache.AccessQueue.this.head) ? null : next;
/*      */           }
/*      */         };
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void cleanUp() {
/* 3841 */     for (Segment<?, ?> segment : this.segments) {
/* 3842 */       segment.cleanUp();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/* 3857 */     long sum = 0L;
/* 3858 */     Segment<K, V>[] segments = this.segments; int i;
/* 3859 */     for (i = 0; i < segments.length; i++) {
/* 3860 */       if ((segments[i]).count != 0) {
/* 3861 */         return false;
/*      */       }
/* 3863 */       sum += (segments[i]).modCount;
/*      */     } 
/*      */     
/* 3866 */     if (sum != 0L) {
/* 3867 */       for (i = 0; i < segments.length; i++) {
/* 3868 */         if ((segments[i]).count != 0) {
/* 3869 */           return false;
/*      */         }
/* 3871 */         sum -= (segments[i]).modCount;
/*      */       } 
/* 3873 */       if (sum != 0L) {
/* 3874 */         return false;
/*      */       }
/*      */     } 
/* 3877 */     return true;
/*      */   }
/*      */   
/*      */   long longSize() {
/* 3881 */     Segment<K, V>[] segments = this.segments;
/* 3882 */     long sum = 0L;
/* 3883 */     for (int i = 0; i < segments.length; i++) {
/* 3884 */       sum += (segments[i]).count;
/*      */     }
/* 3886 */     return sum;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/* 3891 */     return Ints.saturatedCast(longSize());
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public V get(@Nullable Object key) {
/* 3897 */     if (key == null) {
/* 3898 */       return null;
/*      */     }
/* 3900 */     int hash = hash(key);
/* 3901 */     return segmentFor(hash).get(key, hash);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public V getIfPresent(Object key) {
/* 3906 */     int hash = hash(Preconditions.checkNotNull(key));
/* 3907 */     V value = segmentFor(hash).get(key, hash);
/* 3908 */     if (value == null) {
/* 3909 */       this.globalStatsCounter.recordMisses(1);
/*      */     } else {
/* 3911 */       this.globalStatsCounter.recordHits(1);
/*      */     } 
/* 3913 */     return value;
/*      */   }
/*      */   
/*      */   V get(K key, CacheLoader<? super K, V> loader) throws ExecutionException {
/* 3917 */     int hash = hash(Preconditions.checkNotNull(key));
/* 3918 */     return segmentFor(hash).get(key, hash, loader);
/*      */   }
/*      */   
/*      */   V getOrLoad(K key) throws ExecutionException {
/* 3922 */     return get(key, this.defaultLoader);
/*      */   }
/*      */   
/*      */   ImmutableMap<K, V> getAllPresent(Iterable<?> keys) {
/* 3926 */     int hits = 0;
/* 3927 */     int misses = 0;
/*      */     
/* 3929 */     Map<K, V> result = Maps.newLinkedHashMap();
/* 3930 */     for (Object key : keys) {
/* 3931 */       V value = get(key);
/* 3932 */       if (value == null) {
/* 3933 */         misses++;
/*      */         
/*      */         continue;
/*      */       } 
/* 3937 */       K castKey = (K)key;
/* 3938 */       result.put(castKey, value);
/* 3939 */       hits++;
/*      */     } 
/*      */     
/* 3942 */     this.globalStatsCounter.recordHits(hits);
/* 3943 */     this.globalStatsCounter.recordMisses(misses);
/* 3944 */     return ImmutableMap.copyOf(result);
/*      */   }
/*      */   
/*      */   ImmutableMap<K, V> getAll(Iterable<? extends K> keys) throws ExecutionException {
/* 3948 */     int hits = 0;
/* 3949 */     int misses = 0;
/*      */     
/* 3951 */     Map<K, V> result = Maps.newLinkedHashMap();
/* 3952 */     Set<K> keysToLoad = Sets.newLinkedHashSet();
/* 3953 */     for (K key : keys) {
/* 3954 */       V value = get(key);
/* 3955 */       if (!result.containsKey(key)) {
/* 3956 */         result.put(key, value);
/* 3957 */         if (value == null) {
/* 3958 */           misses++;
/* 3959 */           keysToLoad.add(key); continue;
/*      */         } 
/* 3961 */         hits++;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/* 3967 */       if (!keysToLoad.isEmpty()) {
/*      */         try {
/* 3969 */           Map<K, V> newEntries = loadAll(keysToLoad, this.defaultLoader);
/* 3970 */           for (K key : keysToLoad) {
/* 3971 */             V value = newEntries.get(key);
/* 3972 */             if (value == null) {
/* 3973 */               throw new CacheLoader.InvalidCacheLoadException("loadAll failed to return a value for " + key);
/*      */             }
/* 3975 */             result.put(key, value);
/*      */           } 
/* 3977 */         } catch (UnsupportedLoadingOperationException e) {
/*      */           
/* 3979 */           for (K key : keysToLoad) {
/* 3980 */             misses--;
/* 3981 */             result.put(key, get(key, this.defaultLoader));
/*      */           } 
/*      */         } 
/*      */       }
/* 3985 */       return ImmutableMap.copyOf(result);
/*      */     } finally {
/* 3987 */       this.globalStatsCounter.recordHits(hits);
/* 3988 */       this.globalStatsCounter.recordMisses(misses);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   Map<K, V> loadAll(Set<? extends K> keys, CacheLoader<? super K, V> loader) throws ExecutionException {
/*      */     Map<K, V> result;
/* 3999 */     Preconditions.checkNotNull(loader);
/* 4000 */     Preconditions.checkNotNull(keys);
/* 4001 */     Stopwatch stopwatch = Stopwatch.createStarted();
/*      */     
/* 4003 */     boolean success = false;
/*      */     
/*      */     try {
/* 4006 */       Map<K, V> map = (Map)loader.loadAll(keys);
/* 4007 */       result = map;
/* 4008 */       success = true;
/* 4009 */     } catch (UnsupportedLoadingOperationException e) {
/* 4010 */       success = true;
/* 4011 */       throw e;
/* 4012 */     } catch (InterruptedException e) {
/* 4013 */       Thread.currentThread().interrupt();
/* 4014 */       throw new ExecutionException(e);
/* 4015 */     } catch (RuntimeException e) {
/* 4016 */       throw new UncheckedExecutionException(e);
/* 4017 */     } catch (Exception e) {
/* 4018 */       throw new ExecutionException(e);
/* 4019 */     } catch (Error e) {
/* 4020 */       throw new ExecutionError(e);
/*      */     } finally {
/* 4022 */       if (!success) {
/* 4023 */         this.globalStatsCounter.recordLoadException(stopwatch.elapsed(TimeUnit.NANOSECONDS));
/*      */       }
/*      */     } 
/*      */     
/* 4027 */     if (result == null) {
/* 4028 */       this.globalStatsCounter.recordLoadException(stopwatch.elapsed(TimeUnit.NANOSECONDS));
/* 4029 */       throw new CacheLoader.InvalidCacheLoadException(loader + " returned null map from loadAll");
/*      */     } 
/*      */     
/* 4032 */     stopwatch.stop();
/*      */     
/* 4034 */     boolean nullsPresent = false;
/* 4035 */     for (Map.Entry<K, V> entry : result.entrySet()) {
/* 4036 */       K key = entry.getKey();
/* 4037 */       V value = entry.getValue();
/* 4038 */       if (key == null || value == null) {
/*      */         
/* 4040 */         nullsPresent = true; continue;
/*      */       } 
/* 4042 */       put(key, value);
/*      */     } 
/*      */ 
/*      */     
/* 4046 */     if (nullsPresent) {
/* 4047 */       this.globalStatsCounter.recordLoadException(stopwatch.elapsed(TimeUnit.NANOSECONDS));
/* 4048 */       throw new CacheLoader.InvalidCacheLoadException(loader + " returned null keys or values from loadAll");
/*      */     } 
/*      */ 
/*      */     
/* 4052 */     this.globalStatsCounter.recordLoadSuccess(stopwatch.elapsed(TimeUnit.NANOSECONDS));
/* 4053 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ReferenceEntry<K, V> getEntry(@Nullable Object key) {
/* 4062 */     if (key == null) {
/* 4063 */       return null;
/*      */     }
/* 4065 */     int hash = hash(key);
/* 4066 */     return segmentFor(hash).getEntry(key, hash);
/*      */   }
/*      */   
/*      */   void refresh(K key) {
/* 4070 */     int hash = hash(Preconditions.checkNotNull(key));
/* 4071 */     segmentFor(hash).refresh(key, hash, this.defaultLoader, false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsKey(@Nullable Object key) {
/* 4077 */     if (key == null) {
/* 4078 */       return false;
/*      */     }
/* 4080 */     int hash = hash(key);
/* 4081 */     return segmentFor(hash).containsKey(key, hash);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsValue(@Nullable Object value) {
/* 4087 */     if (value == null) {
/* 4088 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4096 */     long now = this.ticker.read();
/* 4097 */     Segment<K, V>[] segments = this.segments;
/* 4098 */     long last = -1L;
/* 4099 */     for (int i = 0; i < 3; i++) {
/* 4100 */       long sum = 0L;
/* 4101 */       for (Segment<K, V> segment : segments) {
/*      */ 
/*      */         
/* 4104 */         int c = segment.count;
/*      */         
/* 4106 */         AtomicReferenceArray<ReferenceEntry<K, V>> table = segment.table;
/* 4107 */         for (int j = 0; j < table.length(); j++) {
/* 4108 */           for (ReferenceEntry<K, V> e = table.get(j); e != null; e = e.getNext()) {
/* 4109 */             V v = segment.getLiveValue(e, now);
/* 4110 */             if (v != null && this.valueEquivalence.equivalent(value, v)) {
/* 4111 */               return true;
/*      */             }
/*      */           } 
/*      */         } 
/* 4115 */         sum += segment.modCount;
/*      */       } 
/* 4117 */       if (sum == last) {
/*      */         break;
/*      */       }
/* 4120 */       last = sum;
/*      */     } 
/* 4122 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public V put(K key, V value) {
/* 4127 */     Preconditions.checkNotNull(key);
/* 4128 */     Preconditions.checkNotNull(value);
/* 4129 */     int hash = hash(key);
/* 4130 */     return segmentFor(hash).put(key, hash, value, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public V putIfAbsent(K key, V value) {
/* 4135 */     Preconditions.checkNotNull(key);
/* 4136 */     Preconditions.checkNotNull(value);
/* 4137 */     int hash = hash(key);
/* 4138 */     return segmentFor(hash).put(key, hash, value, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends V> m) {
/* 4143 */     for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
/* 4144 */       put(e.getKey(), e.getValue());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public V remove(@Nullable Object key) {
/* 4150 */     if (key == null) {
/* 4151 */       return null;
/*      */     }
/* 4153 */     int hash = hash(key);
/* 4154 */     return segmentFor(hash).remove(key, hash);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(@Nullable Object key, @Nullable Object value) {
/* 4159 */     if (key == null || value == null) {
/* 4160 */       return false;
/*      */     }
/* 4162 */     int hash = hash(key);
/* 4163 */     return segmentFor(hash).remove(key, hash, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K key, @Nullable V oldValue, V newValue) {
/* 4168 */     Preconditions.checkNotNull(key);
/* 4169 */     Preconditions.checkNotNull(newValue);
/* 4170 */     if (oldValue == null) {
/* 4171 */       return false;
/*      */     }
/* 4173 */     int hash = hash(key);
/* 4174 */     return segmentFor(hash).replace(key, hash, oldValue, newValue);
/*      */   }
/*      */ 
/*      */   
/*      */   public V replace(K key, V value) {
/* 4179 */     Preconditions.checkNotNull(key);
/* 4180 */     Preconditions.checkNotNull(value);
/* 4181 */     int hash = hash(key);
/* 4182 */     return segmentFor(hash).replace(key, hash, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/* 4187 */     for (Segment<K, V> segment : this.segments) {
/* 4188 */       segment.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   void invalidateAll(Iterable<?> keys) {
/* 4194 */     for (Object key : keys) {
/* 4195 */       remove(key);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<K> keySet() {
/* 4204 */     Set<K> ks = this.keySet;
/* 4205 */     return (ks != null) ? ks : (this.keySet = new KeySet(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<V> values() {
/* 4213 */     Collection<V> vs = this.values;
/* 4214 */     return (vs != null) ? vs : (this.values = new Values(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("Not supported.")
/*      */   public Set<Map.Entry<K, V>> entrySet() {
/* 4223 */     Set<Map.Entry<K, V>> es = this.entrySet;
/* 4224 */     return (es != null) ? es : (this.entrySet = new EntrySet(this));
/*      */   }
/*      */ 
/*      */   
/*      */   abstract class HashIterator<T>
/*      */     implements Iterator<T>
/*      */   {
/*      */     int nextSegmentIndex;
/*      */     int nextTableIndex;
/*      */     LocalCache.Segment<K, V> currentSegment;
/*      */     AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> currentTable;
/*      */     LocalCache.ReferenceEntry<K, V> nextEntry;
/*      */     LocalCache<K, V>.WriteThroughEntry nextExternal;
/*      */     LocalCache<K, V>.WriteThroughEntry lastReturned;
/*      */     
/*      */     HashIterator() {
/* 4240 */       this.nextSegmentIndex = LocalCache.this.segments.length - 1;
/* 4241 */       this.nextTableIndex = -1;
/* 4242 */       advance();
/*      */     }
/*      */ 
/*      */     
/*      */     public abstract T next();
/*      */     
/*      */     final void advance() {
/* 4249 */       this.nextExternal = null;
/*      */       
/* 4251 */       if (nextInChain()) {
/*      */         return;
/*      */       }
/*      */       
/* 4255 */       if (nextInTable()) {
/*      */         return;
/*      */       }
/*      */       
/* 4259 */       while (this.nextSegmentIndex >= 0) {
/* 4260 */         this.currentSegment = LocalCache.this.segments[this.nextSegmentIndex--];
/* 4261 */         if (this.currentSegment.count != 0) {
/* 4262 */           this.currentTable = this.currentSegment.table;
/* 4263 */           this.nextTableIndex = this.currentTable.length() - 1;
/* 4264 */           if (nextInTable()) {
/*      */             return;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean nextInChain() {
/* 4275 */       if (this.nextEntry != null) {
/* 4276 */         for (this.nextEntry = this.nextEntry.getNext(); this.nextEntry != null; this.nextEntry = this.nextEntry.getNext()) {
/* 4277 */           if (advanceTo(this.nextEntry)) {
/* 4278 */             return true;
/*      */           }
/*      */         } 
/*      */       }
/* 4282 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean nextInTable() {
/* 4289 */       while (this.nextTableIndex >= 0) {
/* 4290 */         if ((this.nextEntry = this.currentTable.get(this.nextTableIndex--)) != null && (
/* 4291 */           advanceTo(this.nextEntry) || nextInChain())) {
/* 4292 */           return true;
/*      */         }
/*      */       } 
/*      */       
/* 4296 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean advanceTo(LocalCache.ReferenceEntry<K, V> entry) {
/*      */       try {
/* 4305 */         long now = LocalCache.this.ticker.read();
/* 4306 */         K key = entry.getKey();
/* 4307 */         V value = LocalCache.this.getLiveValue(entry, now);
/* 4308 */         if (value != null) {
/* 4309 */           this.nextExternal = new LocalCache.WriteThroughEntry(key, value);
/* 4310 */           return true;
/*      */         } 
/*      */         
/* 4313 */         return false;
/*      */       } finally {
/*      */         
/* 4316 */         this.currentSegment.postReadCleanup();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 4322 */       return (this.nextExternal != null);
/*      */     }
/*      */     
/*      */     LocalCache<K, V>.WriteThroughEntry nextEntry() {
/* 4326 */       if (this.nextExternal == null) {
/* 4327 */         throw new NoSuchElementException();
/*      */       }
/* 4329 */       this.lastReturned = this.nextExternal;
/* 4330 */       advance();
/* 4331 */       return this.lastReturned;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 4336 */       Preconditions.checkState((this.lastReturned != null));
/* 4337 */       LocalCache.this.remove(this.lastReturned.getKey());
/* 4338 */       this.lastReturned = null;
/*      */     }
/*      */   }
/*      */   
/*      */   final class KeyIterator
/*      */     extends HashIterator<K>
/*      */   {
/*      */     public K next() {
/* 4346 */       return nextEntry().getKey();
/*      */     }
/*      */   }
/*      */   
/*      */   final class ValueIterator
/*      */     extends HashIterator<V>
/*      */   {
/*      */     public V next() {
/* 4354 */       return nextEntry().getValue();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   final class WriteThroughEntry
/*      */     implements Map.Entry<K, V>
/*      */   {
/*      */     final K key;
/*      */     
/*      */     V value;
/*      */     
/*      */     WriteThroughEntry(K key, V value) {
/* 4367 */       this.key = key;
/* 4368 */       this.value = value;
/*      */     }
/*      */ 
/*      */     
/*      */     public K getKey() {
/* 4373 */       return this.key;
/*      */     }
/*      */ 
/*      */     
/*      */     public V getValue() {
/* 4378 */       return this.value;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(@Nullable Object object) {
/* 4384 */       if (object instanceof Map.Entry) {
/* 4385 */         Map.Entry<?, ?> that = (Map.Entry<?, ?>)object;
/* 4386 */         return (this.key.equals(that.getKey()) && this.value.equals(that.getValue()));
/*      */       } 
/* 4388 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 4394 */       return this.key.hashCode() ^ this.value.hashCode();
/*      */     }
/*      */ 
/*      */     
/*      */     public V setValue(V newValue) {
/* 4399 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 4406 */       return (new StringBuilder()).append(getKey()).append("=").append(getValue()).toString();
/*      */     }
/*      */   }
/*      */   
/*      */   final class EntryIterator
/*      */     extends HashIterator<Map.Entry<K, V>>
/*      */   {
/*      */     public Map.Entry<K, V> next() {
/* 4414 */       return nextEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   abstract class AbstractCacheSet<T> extends AbstractSet<T> {
/*      */     final ConcurrentMap<?, ?> map;
/*      */     
/*      */     AbstractCacheSet(ConcurrentMap<?, ?> map) {
/* 4422 */       this.map = map;
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 4427 */       return this.map.size();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 4432 */       return this.map.isEmpty();
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 4437 */       this.map.clear();
/*      */     }
/*      */   }
/*      */   
/*      */   final class KeySet
/*      */     extends AbstractCacheSet<K> {
/*      */     KeySet(ConcurrentMap<?, ?> map) {
/* 4444 */       super(map);
/*      */     }
/*      */ 
/*      */     
/*      */     public Iterator<K> iterator() {
/* 4449 */       return new LocalCache.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 4454 */       return this.map.containsKey(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 4459 */       return (this.map.remove(o) != null);
/*      */     }
/*      */   }
/*      */   
/*      */   final class Values extends AbstractCollection<V> {
/*      */     private final ConcurrentMap<?, ?> map;
/*      */     
/*      */     Values(ConcurrentMap<?, ?> map) {
/* 4467 */       this.map = map;
/*      */     }
/*      */     
/*      */     public int size() {
/* 4471 */       return this.map.size();
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 4475 */       return this.map.isEmpty();
/*      */     }
/*      */     
/*      */     public void clear() {
/* 4479 */       this.map.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public Iterator<V> iterator() {
/* 4484 */       return new LocalCache.ValueIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 4489 */       return this.map.containsValue(o);
/*      */     }
/*      */   }
/*      */   
/*      */   final class EntrySet
/*      */     extends AbstractCacheSet<Map.Entry<K, V>> {
/*      */     EntrySet(ConcurrentMap<?, ?> map) {
/* 4496 */       super(map);
/*      */     }
/*      */ 
/*      */     
/*      */     public Iterator<Map.Entry<K, V>> iterator() {
/* 4501 */       return new LocalCache.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 4506 */       if (!(o instanceof Map.Entry)) {
/* 4507 */         return false;
/*      */       }
/* 4509 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 4510 */       Object key = e.getKey();
/* 4511 */       if (key == null) {
/* 4512 */         return false;
/*      */       }
/* 4514 */       V v = (V)LocalCache.this.get(key);
/*      */       
/* 4516 */       return (v != null && LocalCache.this.valueEquivalence.equivalent((V)e.getValue(), v));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 4521 */       if (!(o instanceof Map.Entry)) {
/* 4522 */         return false;
/*      */       }
/* 4524 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 4525 */       Object key = e.getKey();
/* 4526 */       return (key != null && LocalCache.this.remove(key, e.getValue()));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class ManualSerializationProxy<K, V>
/*      */     extends ForwardingCache<K, V>
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */     
/*      */     final LocalCache.Strength keyStrength;
/*      */     
/*      */     final LocalCache.Strength valueStrength;
/*      */     
/*      */     final Equivalence<Object> keyEquivalence;
/*      */     
/*      */     final Equivalence<Object> valueEquivalence;
/*      */     
/*      */     final long expireAfterWriteNanos;
/*      */     
/*      */     final long expireAfterAccessNanos;
/*      */     
/*      */     final long maxWeight;
/*      */     
/*      */     final Weigher<K, V> weigher;
/*      */     
/*      */     final int concurrencyLevel;
/*      */     final RemovalListener<? super K, ? super V> removalListener;
/*      */     final Ticker ticker;
/*      */     final CacheLoader<? super K, V> loader;
/*      */     transient Cache<K, V> delegate;
/*      */     
/*      */     ManualSerializationProxy(LocalCache<K, V> cache) {
/* 4560 */       this(cache.keyStrength, cache.valueStrength, cache.keyEquivalence, cache.valueEquivalence, cache.expireAfterWriteNanos, cache.expireAfterAccessNanos, cache.maxWeight, cache.weigher, cache.concurrencyLevel, cache.removalListener, cache.ticker, cache.defaultLoader);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private ManualSerializationProxy(LocalCache.Strength keyStrength, LocalCache.Strength valueStrength, Equivalence<Object> keyEquivalence, Equivalence<Object> valueEquivalence, long expireAfterWriteNanos, long expireAfterAccessNanos, long maxWeight, Weigher<K, V> weigher, int concurrencyLevel, RemovalListener<? super K, ? super V> removalListener, Ticker ticker, CacheLoader<? super K, V> loader) {
/* 4582 */       this.keyStrength = keyStrength;
/* 4583 */       this.valueStrength = valueStrength;
/* 4584 */       this.keyEquivalence = keyEquivalence;
/* 4585 */       this.valueEquivalence = valueEquivalence;
/* 4586 */       this.expireAfterWriteNanos = expireAfterWriteNanos;
/* 4587 */       this.expireAfterAccessNanos = expireAfterAccessNanos;
/* 4588 */       this.maxWeight = maxWeight;
/* 4589 */       this.weigher = weigher;
/* 4590 */       this.concurrencyLevel = concurrencyLevel;
/* 4591 */       this.removalListener = removalListener;
/* 4592 */       this.ticker = (ticker == Ticker.systemTicker() || ticker == CacheBuilder.NULL_TICKER) ? null : ticker;
/*      */       
/* 4594 */       this.loader = loader;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CacheBuilder<K, V> recreateCacheBuilder() {
/* 4604 */       CacheBuilder<K, V> builder = CacheBuilder.newBuilder().setKeyStrength(this.keyStrength).setValueStrength(this.valueStrength).keyEquivalence(this.keyEquivalence).valueEquivalence(this.valueEquivalence).concurrencyLevel(this.concurrencyLevel).removalListener(this.removalListener);
/* 4605 */       builder.strictParsing = false;
/* 4606 */       if (this.expireAfterWriteNanos > 0L) {
/* 4607 */         builder.expireAfterWrite(this.expireAfterWriteNanos, TimeUnit.NANOSECONDS);
/*      */       }
/* 4609 */       if (this.expireAfterAccessNanos > 0L) {
/* 4610 */         builder.expireAfterAccess(this.expireAfterAccessNanos, TimeUnit.NANOSECONDS);
/*      */       }
/* 4612 */       if (this.weigher != CacheBuilder.OneWeigher.INSTANCE) {
/* 4613 */         builder.weigher(this.weigher);
/* 4614 */         if (this.maxWeight != -1L) {
/* 4615 */           builder.maximumWeight(this.maxWeight);
/*      */         }
/*      */       }
/* 4618 */       else if (this.maxWeight != -1L) {
/* 4619 */         builder.maximumSize(this.maxWeight);
/*      */       } 
/*      */       
/* 4622 */       if (this.ticker != null) {
/* 4623 */         builder.ticker(this.ticker);
/*      */       }
/* 4625 */       return builder;
/*      */     }
/*      */     
/*      */     private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 4629 */       in.defaultReadObject();
/* 4630 */       CacheBuilder<K, V> builder = recreateCacheBuilder();
/* 4631 */       this.delegate = builder.build();
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/* 4635 */       return this.delegate;
/*      */     }
/*      */ 
/*      */     
/*      */     protected Cache<K, V> delegate() {
/* 4640 */       return this.delegate;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class LoadingSerializationProxy<K, V>
/*      */     extends ManualSerializationProxy<K, V>
/*      */     implements LoadingCache<K, V>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */     
/*      */     transient LoadingCache<K, V> autoDelegate;
/*      */ 
/*      */ 
/*      */     
/*      */     LoadingSerializationProxy(LocalCache<K, V> cache) {
/* 4659 */       super(cache);
/*      */     }
/*      */     
/*      */     private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 4663 */       in.defaultReadObject();
/* 4664 */       CacheBuilder<K, V> builder = recreateCacheBuilder();
/* 4665 */       this.autoDelegate = builder.build(this.loader);
/*      */     }
/*      */ 
/*      */     
/*      */     public V get(K key) throws ExecutionException {
/* 4670 */       return this.autoDelegate.get(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public V getUnchecked(K key) {
/* 4675 */       return this.autoDelegate.getUnchecked(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public ImmutableMap<K, V> getAll(Iterable<? extends K> keys) throws ExecutionException {
/* 4680 */       return this.autoDelegate.getAll(keys);
/*      */     }
/*      */ 
/*      */     
/*      */     public final V apply(K key) {
/* 4685 */       return this.autoDelegate.apply(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh(K key) {
/* 4690 */       this.autoDelegate.refresh(key);
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/* 4694 */       return this.autoDelegate;
/*      */     } }
/*      */   
/*      */   static class LocalManualCache<K, V> implements Cache<K, V>, Serializable {
/*      */     final LocalCache<K, V> localCache;
/*      */     private static final long serialVersionUID = 1L;
/*      */     
/*      */     LocalManualCache(CacheBuilder<? super K, ? super V> builder) {
/* 4702 */       this(new LocalCache<K, V>(builder, null));
/*      */     }
/*      */     
/*      */     private LocalManualCache(LocalCache<K, V> localCache) {
/* 4706 */       this.localCache = localCache;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     public V getIfPresent(Object key) {
/* 4714 */       return this.localCache.getIfPresent(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public V get(K key, final Callable<? extends V> valueLoader) throws ExecutionException {
/* 4719 */       Preconditions.checkNotNull(valueLoader);
/* 4720 */       return this.localCache.get(key, (CacheLoader)new CacheLoader<Object, V>()
/*      */           {
/*      */             public V load(Object key) throws Exception {
/* 4723 */               return valueLoader.call();
/*      */             }
/*      */           });
/*      */     }
/*      */ 
/*      */     
/*      */     public ImmutableMap<K, V> getAllPresent(Iterable<?> keys) {
/* 4730 */       return this.localCache.getAllPresent(keys);
/*      */     }
/*      */ 
/*      */     
/*      */     public void put(K key, V value) {
/* 4735 */       this.localCache.put(key, value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void putAll(Map<? extends K, ? extends V> m) {
/* 4740 */       this.localCache.putAll(m);
/*      */     }
/*      */ 
/*      */     
/*      */     public void invalidate(Object key) {
/* 4745 */       Preconditions.checkNotNull(key);
/* 4746 */       this.localCache.remove(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public void invalidateAll(Iterable<?> keys) {
/* 4751 */       this.localCache.invalidateAll(keys);
/*      */     }
/*      */ 
/*      */     
/*      */     public void invalidateAll() {
/* 4756 */       this.localCache.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public long size() {
/* 4761 */       return this.localCache.longSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public ConcurrentMap<K, V> asMap() {
/* 4766 */       return this.localCache;
/*      */     }
/*      */ 
/*      */     
/*      */     public CacheStats stats() {
/* 4771 */       AbstractCache.SimpleStatsCounter aggregator = new AbstractCache.SimpleStatsCounter();
/* 4772 */       aggregator.incrementBy(this.localCache.globalStatsCounter);
/* 4773 */       for (LocalCache.Segment<K, V> segment : this.localCache.segments) {
/* 4774 */         aggregator.incrementBy(segment.statsCounter);
/*      */       }
/* 4776 */       return aggregator.snapshot();
/*      */     }
/*      */ 
/*      */     
/*      */     public void cleanUp() {
/* 4781 */       this.localCache.cleanUp();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object writeReplace() {
/* 4789 */       return new LocalCache.ManualSerializationProxy<K, V>(this.localCache);
/*      */     }
/*      */   }
/*      */   
/*      */   static class LocalLoadingCache<K, V>
/*      */     extends LocalManualCache<K, V> implements LoadingCache<K, V> {
/*      */     private static final long serialVersionUID = 1L;
/*      */     
/*      */     LocalLoadingCache(CacheBuilder<? super K, ? super V> builder, CacheLoader<? super K, V> loader) {
/* 4798 */       super(new LocalCache<K, V>(builder, (CacheLoader<? super K, V>)Preconditions.checkNotNull(loader)));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V get(K key) throws ExecutionException {
/* 4805 */       return this.localCache.getOrLoad(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public V getUnchecked(K key) {
/*      */       try {
/* 4811 */         return get(key);
/* 4812 */       } catch (ExecutionException e) {
/* 4813 */         throw new UncheckedExecutionException(e.getCause());
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public ImmutableMap<K, V> getAll(Iterable<? extends K> keys) throws ExecutionException {
/* 4819 */       return this.localCache.getAll(keys);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh(K key) {
/* 4824 */       this.localCache.refresh(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public final V apply(K key) {
/* 4829 */       return getUnchecked(key);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object writeReplace() {
/* 4838 */       return new LocalCache.LoadingSerializationProxy<K, V>(this.localCache);
/*      */     }
/*      */   }
/*      */   
/*      */   static interface ReferenceEntry<K, V> {
/*      */     LocalCache.ValueReference<K, V> getValueReference();
/*      */     
/*      */     void setValueReference(LocalCache.ValueReference<K, V> param1ValueReference);
/*      */     
/*      */     @Nullable
/*      */     ReferenceEntry<K, V> getNext();
/*      */     
/*      */     int getHash();
/*      */     
/*      */     @Nullable
/*      */     K getKey();
/*      */     
/*      */     long getAccessTime();
/*      */     
/*      */     void setAccessTime(long param1Long);
/*      */     
/*      */     ReferenceEntry<K, V> getNextInAccessQueue();
/*      */     
/*      */     void setNextInAccessQueue(ReferenceEntry<K, V> param1ReferenceEntry);
/*      */     
/*      */     ReferenceEntry<K, V> getPreviousInAccessQueue();
/*      */     
/*      */     void setPreviousInAccessQueue(ReferenceEntry<K, V> param1ReferenceEntry);
/*      */     
/*      */     long getWriteTime();
/*      */     
/*      */     void setWriteTime(long param1Long);
/*      */     
/*      */     ReferenceEntry<K, V> getNextInWriteQueue();
/*      */     
/*      */     void setNextInWriteQueue(ReferenceEntry<K, V> param1ReferenceEntry);
/*      */     
/*      */     ReferenceEntry<K, V> getPreviousInWriteQueue();
/*      */     
/*      */     void setPreviousInWriteQueue(ReferenceEntry<K, V> param1ReferenceEntry);
/*      */   }
/*      */   
/*      */   static interface ValueReference<K, V> {
/*      */     @Nullable
/*      */     V get();
/*      */     
/*      */     V waitForValue() throws ExecutionException;
/*      */     
/*      */     int getWeight();
/*      */     
/*      */     @Nullable
/*      */     LocalCache.ReferenceEntry<K, V> getEntry();
/*      */     
/*      */     ValueReference<K, V> copyFor(ReferenceQueue<V> param1ReferenceQueue, @Nullable V param1V, LocalCache.ReferenceEntry<K, V> param1ReferenceEntry);
/*      */     
/*      */     void notifyNewValue(@Nullable V param1V);
/*      */     
/*      */     boolean isLoading();
/*      */     
/*      */     boolean isActive();
/*      */   }
/*      */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\guava\cache\LocalCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */