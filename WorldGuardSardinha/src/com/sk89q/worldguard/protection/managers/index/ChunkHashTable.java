/*     */ package com.sk89q.worldguard.protection.managers.index;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Supplier;
/*     */ import com.google.common.util.concurrent.ListeningExecutorService;
/*     */ import com.google.common.util.concurrent.MoreExecutors;
/*     */ import com.sk89q.worldedit.Vector;
/*     */ import com.sk89q.worldedit.Vector2D;
/*     */ import com.sk89q.worldguard.protection.managers.RegionDifference;
/*     */ import com.sk89q.worldguard.protection.managers.RemovalStrategy;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import com.sk89q.worldguard.protection.util.RegionCollectionConsumer;
/*     */ import com.sk89q.worldguard.util.collect.LongHashTable;
/*     */ import com.sk89q.worldguard.util.concurrent.EvenMoreExecutors;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ public class ChunkHashTable
/*     */   implements ConcurrentRegionIndex
/*     */ {
/*  54 */   private ListeningExecutorService executor = createExecutor();
/*  55 */   private LongHashTable<ChunkState> states = new LongHashTable();
/*     */   private final RegionIndex index;
/*  57 */   private final Object lock = new Object();
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private ChunkState lastState;
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkHashTable(RegionIndex index) {
/*  67 */     Preconditions.checkNotNull(index);
/*  68 */     this.index = index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ListeningExecutorService createExecutor() {
/*  77 */     return MoreExecutors.listeningDecorator(
/*  78 */         EvenMoreExecutors.newBoundedCachedThreadPool(0, 4, 2147483647));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private ChunkState get(Vector2D position, boolean create) {
/*     */     ChunkState state;
/*  91 */     synchronized (this.lock) {
/*  92 */       state = (ChunkState)this.states.get(position.getBlockX(), position.getBlockZ());
/*  93 */       if (state == null && create) {
/*  94 */         state = new ChunkState(position);
/*  95 */         this.states.put(position.getBlockX(), position.getBlockZ(), state);
/*  96 */         this.executor.submit(new EnumerateRegions(position));
/*     */       } 
/*     */     } 
/*  99 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ChunkState getOrCreate(Vector2D position) {
/* 110 */     return get(position, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rebuild() {
/* 117 */     synchronized (this.lock) {
/* 118 */       ListeningExecutorService previousExecutor = this.executor;
/* 119 */       LongHashTable<ChunkState> previousStates = this.states;
/*     */       
/* 121 */       previousExecutor.shutdownNow();
/* 122 */       this.states = new LongHashTable();
/* 123 */       this.executor = createExecutor();
/*     */       
/* 125 */       List<Vector2D> positions = new ArrayList<Vector2D>();
/* 126 */       for (ChunkState state : previousStates.values()) {
/* 127 */         Vector2D position = state.getPosition();
/* 128 */         positions.add(position);
/* 129 */         this.states.put(position.getBlockX(), position.getBlockZ(), new ChunkState(position));
/*     */       } 
/*     */       
/* 132 */       if (!positions.isEmpty()) {
/* 133 */         this.executor.submit(new EnumerateRegions(positions));
/*     */       }
/*     */       
/* 136 */       this.lastState = null;
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
/*     */ 
/*     */   
/*     */   public boolean awaitCompletion(long timeout, TimeUnit unit) throws InterruptedException {
/*     */     ListeningExecutorService previousExecutor;
/* 151 */     synchronized (this.lock) {
/* 152 */       previousExecutor = this.executor;
/* 153 */       this.executor = createExecutor();
/*     */     } 
/* 155 */     previousExecutor.shutdown();
/* 156 */     return previousExecutor.awaitTermination(timeout, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bias(Vector2D chunkPosition) {
/* 161 */     Preconditions.checkNotNull(chunkPosition);
/* 162 */     getOrCreate(chunkPosition);
/*     */   }
/*     */ 
/*     */   
/*     */   public void biasAll(Collection<Vector2D> chunkPositions) {
/* 167 */     synchronized (this.lock) {
/* 168 */       for (Vector2D position : chunkPositions) {
/* 169 */         bias(position);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void forget(Vector2D chunkPosition) {
/* 176 */     Preconditions.checkNotNull(chunkPosition);
/* 177 */     synchronized (this.lock) {
/* 178 */       this.states.remove(chunkPosition.getBlockX(), chunkPosition.getBlockZ());
/* 179 */       ChunkState state = this.lastState;
/* 180 */       if (state != null && state.getPosition().getBlockX() == chunkPosition.getBlockX() && state.getPosition().getBlockZ() == chunkPosition.getBlockZ()) {
/* 181 */         this.lastState = null;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void forgetAll() {
/* 188 */     synchronized (this.lock) {
/* 189 */       this.executor.shutdownNow();
/* 190 */       this.states = new LongHashTable();
/* 191 */       this.executor = createExecutor();
/* 192 */       this.lastState = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(ProtectedRegion region) {
/* 198 */     this.index.add(region);
/* 199 */     rebuild();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAll(Collection<ProtectedRegion> regions) {
/* 204 */     this.index.addAll(regions);
/* 205 */     rebuild();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<ProtectedRegion> remove(String id, RemovalStrategy strategy) {
/* 210 */     Set<ProtectedRegion> removed = this.index.remove(id, strategy);
/* 211 */     rebuild();
/* 212 */     return removed;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(String id) {
/* 217 */     return this.index.contains(id);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ProtectedRegion get(String id) {
/* 223 */     return this.index.get(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public void apply(Predicate<ProtectedRegion> consumer) {
/* 228 */     this.index.apply(consumer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyContaining(Vector position, Predicate<ProtectedRegion> consumer) {
/* 233 */     Preconditions.checkNotNull(position);
/* 234 */     Preconditions.checkNotNull(consumer);
/*     */     
/* 236 */     ChunkState state = this.lastState;
/* 237 */     int chunkX = position.getBlockX() >> 4;
/* 238 */     int chunkZ = position.getBlockZ() >> 4;
/*     */     
/* 240 */     if (state == null || state.getPosition().getBlockX() != chunkX || state.getPosition().getBlockZ() != chunkZ) {
/* 241 */       state = get(new Vector2D(chunkX, chunkZ), false);
/*     */     }
/*     */     
/* 244 */     if (state != null && state.isLoaded()) {
/* 245 */       for (ProtectedRegion region : state.getRegions()) {
/* 246 */         if (region.contains(position)) {
/* 247 */           consumer.apply(region);
/*     */         }
/*     */       } 
/*     */     } else {
/* 251 */       this.index.applyContaining(position, consumer);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyIntersecting(ProtectedRegion region, Predicate<ProtectedRegion> consumer) {
/* 257 */     this.index.applyIntersecting(region, consumer);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 262 */     return this.index.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public RegionDifference getAndClearDifference() {
/* 267 */     return this.index.getAndClearDifference();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDirty(RegionDifference difference) {
/* 272 */     this.index.setDirty(difference);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<ProtectedRegion> values() {
/* 277 */     return this.index.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirty() {
/* 282 */     return this.index.isDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDirty(boolean dirty) {
/* 287 */     this.index.setDirty(dirty);
/*     */   }
/*     */ 
/*     */   
/*     */   private class EnumerateRegions
/*     */     implements Runnable
/*     */   {
/*     */     private final List<Vector2D> positions;
/*     */     
/*     */     private EnumerateRegions(Vector2D position) {
/* 297 */       this(Arrays.asList(new Vector2D[] { (Vector2D)Preconditions.checkNotNull(position) }));
/*     */     }
/*     */     
/*     */     private EnumerateRegions(List<Vector2D> positions) {
/* 301 */       Preconditions.checkNotNull(positions);
/* 302 */       Preconditions.checkArgument(!positions.isEmpty(), "List of positions can't be empty");
/* 303 */       this.positions = positions;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 308 */       for (Vector2D position : this.positions) {
/* 309 */         ChunkHashTable.ChunkState state = ChunkHashTable.this.get(position, false);
/*     */         
/* 311 */         if (state != null) {
/* 312 */           List<ProtectedRegion> regions = new ArrayList<ProtectedRegion>();
/*     */ 
/*     */ 
/*     */           
/* 316 */           ProtectedCuboidRegion protectedCuboidRegion = new ProtectedCuboidRegion("_", position.multiply(16).toVector(0.0D).toBlockVector(), position.add(1, 1).multiply(16).toVector(2.147483647E9D).toBlockVector());
/* 317 */           ChunkHashTable.this.index.applyIntersecting((ProtectedRegion)protectedCuboidRegion, (Predicate<ProtectedRegion>)new RegionCollectionConsumer(regions, false));
/* 318 */           Collections.sort(regions);
/*     */           
/* 320 */           state.setRegions(Collections.unmodifiableList(regions));
/*     */           
/* 322 */           if (Thread.currentThread().isInterrupted()) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private class ChunkState
/*     */   {
/*     */     private final Vector2D position;
/*     */     
/*     */     private boolean loaded = false;
/* 336 */     private List<ProtectedRegion> regions = Collections.emptyList();
/*     */     
/*     */     private ChunkState(Vector2D position) {
/* 339 */       this.position = position;
/*     */     }
/*     */     
/*     */     public Vector2D getPosition() {
/* 343 */       return this.position;
/*     */     }
/*     */     
/*     */     public List<ProtectedRegion> getRegions() {
/* 347 */       return this.regions;
/*     */     }
/*     */     
/*     */     public void setRegions(List<ProtectedRegion> regions) {
/* 351 */       this.regions = regions;
/* 352 */       this.loaded = true;
/*     */     }
/*     */     
/*     */     public boolean isLoaded() {
/* 356 */       return this.loaded;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Factory
/*     */     implements Supplier<ChunkHashTable>
/*     */   {
/*     */     private final Supplier<? extends ConcurrentRegionIndex> supplier;
/*     */     
/*     */     public Factory(Supplier<? extends ConcurrentRegionIndex> supplier) {
/* 367 */       Preconditions.checkNotNull(supplier);
/* 368 */       this.supplier = supplier;
/*     */     }
/*     */ 
/*     */     
/*     */     public ChunkHashTable get() {
/* 373 */       return new ChunkHashTable((RegionIndex)this.supplier.get());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\index\ChunkHashTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */