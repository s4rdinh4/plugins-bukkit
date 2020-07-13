/*     */ package com.sk89q.worldguard.protection.managers;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Supplier;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.sk89q.worldedit.Vector;
/*     */ import com.sk89q.worldedit.Vector2D;
/*     */ import com.sk89q.worldedit.bukkit.BukkitUtil;
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*     */ import com.sk89q.worldguard.protection.RegionResultSet;
/*     */ import com.sk89q.worldguard.protection.managers.index.ConcurrentRegionIndex;
/*     */ import com.sk89q.worldguard.protection.managers.storage.DifferenceSaveException;
/*     */ import com.sk89q.worldguard.protection.managers.storage.RegionDatabase;
/*     */ import com.sk89q.worldguard.protection.managers.storage.StorageException;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import com.sk89q.worldguard.protection.util.RegionCollectionConsumer;
/*     */ import com.sk89q.worldguard.util.Normal;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bukkit.Location;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RegionManager
/*     */ {
/*     */   private final RegionDatabase store;
/*     */   private final Supplier<? extends ConcurrentRegionIndex> indexFactory;
/*     */   private ConcurrentRegionIndex index;
/*     */   
/*     */   public RegionManager(RegionDatabase store, Supplier<? extends ConcurrentRegionIndex> indexFactory) {
/*  69 */     Preconditions.checkNotNull(store);
/*  70 */     Preconditions.checkNotNull(indexFactory);
/*     */     
/*  72 */     this.store = store;
/*  73 */     this.indexFactory = indexFactory;
/*  74 */     this.index = (ConcurrentRegionIndex)indexFactory.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  81 */     return this.store.getName();
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
/*     */   public void load() throws StorageException {
/*  96 */     Set<ProtectedRegion> regions = this.store.loadAll();
/*  97 */     for (ProtectedRegion region : regions) {
/*  98 */       region.setDirty(false);
/*     */     }
/* 100 */     setRegions(regions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save() throws StorageException {
/* 109 */     this.index.setDirty(false);
/* 110 */     this.store.saveAll(new HashSet<ProtectedRegion>(getValuesCopy()));
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
/*     */   public boolean saveChanges() throws StorageException {
/* 124 */     RegionDifference diff = this.index.getAndClearDifference();
/* 125 */     boolean successful = false;
/*     */     
/*     */     try {
/* 128 */       if (diff.containsChanges()) {
/*     */         try {
/* 130 */           this.store.saveChanges(diff);
/* 131 */         } catch (DifferenceSaveException e) {
/* 132 */           save();
/*     */         } 
/* 134 */         successful = true;
/* 135 */         return true;
/*     */       } 
/* 137 */       successful = true;
/* 138 */       return false;
/*     */     } finally {
/*     */       
/* 141 */       if (!successful) {
/* 142 */         this.index.setDirty(diff);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadChunk(Vector2D position) {
/* 153 */     this.index.bias(position);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadChunks(Collection<Vector2D> positions) {
/* 162 */     this.index.biasAll(positions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unloadChunk(Vector2D position) {
/* 171 */     this.index.forget(position);
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
/*     */   public Map<String, ProtectedRegion> getRegions() {
/* 184 */     Map<String, ProtectedRegion> map = new HashMap<String, ProtectedRegion>();
/* 185 */     for (ProtectedRegion region : this.index.values()) {
/* 186 */       map.put(Normal.normalize(region.getId()), region);
/*     */     }
/* 188 */     return Collections.unmodifiableMap(map);
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
/*     */   public void setRegions(Map<String, ProtectedRegion> regions) {
/* 200 */     Preconditions.checkNotNull(regions);
/*     */     
/* 202 */     setRegions(regions.values());
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
/*     */   public void setRegions(Collection<ProtectedRegion> regions) {
/* 214 */     Preconditions.checkNotNull(regions);
/*     */     
/* 216 */     ConcurrentRegionIndex newIndex = (ConcurrentRegionIndex)this.indexFactory.get();
/* 217 */     newIndex.addAll(regions);
/* 218 */     newIndex.getAndClearDifference();
/* 219 */     this.index = newIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addRegion(ProtectedRegion region) {
/* 230 */     Preconditions.checkNotNull(region);
/* 231 */     this.index.add(region);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasRegion(String id) {
/* 242 */     return this.index.contains(id);
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
/*     */   public ProtectedRegion getRegion(String id) {
/* 254 */     Preconditions.checkNotNull(id);
/* 255 */     return this.index.get(id);
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
/*     */   public ProtectedRegion matchRegion(String pattern) {
/* 267 */     Preconditions.checkNotNull(pattern);
/*     */     
/* 269 */     if (pattern.startsWith("#")) {
/*     */       int index;
/*     */       try {
/* 272 */         index = Integer.parseInt(pattern.substring(1)) - 1;
/* 273 */       } catch (NumberFormatException e) {
/* 274 */         return null;
/*     */       } 
/* 276 */       for (ProtectedRegion region : this.index.values()) {
/* 277 */         if (index == 0) {
/* 278 */           return region;
/*     */         }
/* 280 */         index--;
/*     */       } 
/* 282 */       return null;
/*     */     } 
/*     */     
/* 285 */     return getRegion(pattern);
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
/*     */   public Set<ProtectedRegion> removeRegion(String id) {
/* 297 */     return removeRegion(id, RemovalStrategy.REMOVE_CHILDREN);
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
/*     */   public Set<ProtectedRegion> removeRegion(String id, RemovalStrategy strategy) {
/* 309 */     return this.index.remove(id, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ApplicableRegionSet getApplicableRegions(Vector position) {
/* 319 */     Preconditions.checkNotNull(position);
/*     */     
/* 321 */     Set<ProtectedRegion> regions = Sets.newHashSet();
/* 322 */     this.index.applyContaining(position, (Predicate)new RegionCollectionConsumer(regions, true));
/* 323 */     return (ApplicableRegionSet)new RegionResultSet(regions, this.index.get("__global__"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ApplicableRegionSet getApplicableRegions(ProtectedRegion region) {
/* 334 */     Preconditions.checkNotNull(region);
/*     */     
/* 336 */     Set<ProtectedRegion> regions = Sets.newHashSet();
/* 337 */     this.index.applyIntersecting(region, (Predicate)new RegionCollectionConsumer(regions, true));
/* 338 */     return (ApplicableRegionSet)new RegionResultSet(regions, this.index.get("__global__"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getApplicableRegionsIDs(Vector position) {
/* 348 */     Preconditions.checkNotNull(position);
/*     */     
/* 350 */     final List<String> names = new ArrayList<String>();
/*     */     
/* 352 */     this.index.applyContaining(position, new Predicate<ProtectedRegion>()
/*     */         {
/*     */           public boolean apply(ProtectedRegion region) {
/* 355 */             return names.add(region.getId());
/*     */           }
/*     */         });
/*     */     
/* 359 */     return names;
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
/*     */   public boolean overlapsUnownedRegion(ProtectedRegion region, final LocalPlayer player) {
/* 371 */     Preconditions.checkNotNull(region);
/* 372 */     Preconditions.checkNotNull(player);
/*     */     
/* 374 */     ConcurrentRegionIndex concurrentRegionIndex = this.index;
/*     */     
/* 376 */     final AtomicBoolean overlapsUnowned = new AtomicBoolean();
/*     */     
/* 378 */     concurrentRegionIndex.applyIntersecting(region, new Predicate<ProtectedRegion>()
/*     */         {
/*     */           public boolean apply(ProtectedRegion test) {
/* 381 */             if (!test.getOwners().contains(player)) {
/* 382 */               overlapsUnowned.set(true);
/* 383 */               return false;
/*     */             } 
/* 385 */             return true;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 390 */     return overlapsUnowned.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 399 */     return this.index.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRegionCountOfPlayer(final LocalPlayer player) {
/* 409 */     Preconditions.checkNotNull(player);
/*     */     
/* 411 */     final AtomicInteger count = new AtomicInteger();
/*     */     
/* 413 */     this.index.apply(new Predicate<ProtectedRegion>()
/*     */         {
/*     */           public boolean apply(ProtectedRegion test) {
/* 416 */             if (test.getOwners().contains(player)) {
/* 417 */               count.incrementAndGet();
/*     */             }
/* 419 */             return true;
/*     */           }
/*     */         });
/*     */     
/* 423 */     return count.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<ProtectedRegion> getValuesCopy() {
/* 432 */     return new ArrayList<ProtectedRegion>(this.index.values());
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
/*     */   public ApplicableRegionSet getApplicableRegions(Location loc) {
/* 445 */     return getApplicableRegions(BukkitUtil.toVector(loc).floor());
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\RegionManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */