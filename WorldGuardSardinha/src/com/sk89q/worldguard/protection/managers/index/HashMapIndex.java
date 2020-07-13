/*     */ package com.sk89q.worldguard.protection.managers.index;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Supplier;
/*     */ import com.sk89q.worldedit.Vector;
/*     */ import com.sk89q.worldedit.Vector2D;
/*     */ import com.sk89q.worldguard.protection.managers.RegionDifference;
/*     */ import com.sk89q.worldguard.protection.managers.RemovalStrategy;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import com.sk89q.worldguard.util.Normal;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
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
/*     */ public class HashMapIndex
/*     */   extends AbstractRegionIndex
/*     */   implements ConcurrentRegionIndex
/*     */ {
/*  51 */   private final ConcurrentMap<String, ProtectedRegion> regions = new ConcurrentHashMap<String, ProtectedRegion>();
/*  52 */   private Set<ProtectedRegion> removed = new HashSet<ProtectedRegion>();
/*  53 */   private final Object lock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void rebuildIndex() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void performAdd(ProtectedRegion region) {
/*  68 */     Preconditions.checkNotNull(region);
/*     */     
/*  70 */     region.setDirty(true);
/*     */     
/*  72 */     synchronized (this.lock) {
/*  73 */       String normalId = Normal.normalize(region.getId());
/*     */       
/*  75 */       ProtectedRegion existing = this.regions.get(normalId);
/*     */ 
/*     */       
/*  78 */       if (existing != null && !existing.getId().equals(region.getId())) {
/*  79 */         this.removed.add(existing);
/*     */       }
/*     */       
/*  82 */       this.regions.put(normalId, region);
/*     */       
/*  84 */       this.removed.remove(region);
/*     */       
/*  86 */       ProtectedRegion parent = region.getParent();
/*  87 */       if (parent != null) {
/*  88 */         performAdd(parent);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAll(Collection<ProtectedRegion> regions) {
/*  95 */     Preconditions.checkNotNull(regions);
/*     */     
/*  97 */     synchronized (this.lock) {
/*  98 */       for (ProtectedRegion region : regions) {
/*  99 */         performAdd(region);
/*     */       }
/*     */       
/* 102 */       rebuildIndex();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bias(Vector2D chunkPosition) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void biasAll(Collection<Vector2D> chunkPositions) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void forget(Vector2D chunkPosition) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void forgetAll() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(ProtectedRegion region) {
/* 128 */     synchronized (this.lock) {
/* 129 */       performAdd(region);
/*     */       
/* 131 */       rebuildIndex();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<ProtectedRegion> remove(String id, RemovalStrategy strategy) {
/* 137 */     Preconditions.checkNotNull(id);
/* 138 */     Preconditions.checkNotNull(strategy);
/*     */     
/* 140 */     Set<ProtectedRegion> removedSet = new HashSet<ProtectedRegion>();
/*     */     
/* 142 */     synchronized (this.lock) {
/* 143 */       ProtectedRegion removed = this.regions.remove(Normal.normalize(id));
/*     */       
/* 145 */       if (removed != null) {
/* 146 */         removedSet.add(removed);
/*     */         
/* 148 */         Iterator<ProtectedRegion> it = this.regions.values().iterator();
/*     */ 
/*     */         
/* 151 */         while (it.hasNext()) {
/* 152 */           ProtectedRegion current = it.next();
/* 153 */           ProtectedRegion parent = current.getParent();
/*     */           
/* 155 */           if (parent != null && parent == removed) {
/* 156 */             switch (strategy) {
/*     */               case REMOVE_CHILDREN:
/* 158 */                 removedSet.add(current);
/* 159 */                 it.remove();
/*     */               
/*     */               case UNSET_PARENT_IN_CHILDREN:
/* 162 */                 current.clearParent();
/*     */             } 
/*     */           
/*     */           }
/*     */         } 
/*     */       } 
/* 168 */       this.removed.addAll(removedSet);
/*     */       
/* 170 */       rebuildIndex();
/*     */     } 
/*     */     
/* 173 */     return removedSet;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(String id) {
/* 178 */     return this.regions.containsKey(Normal.normalize(id));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ProtectedRegion get(String id) {
/* 184 */     return this.regions.get(Normal.normalize(id));
/*     */   }
/*     */ 
/*     */   
/*     */   public void apply(Predicate<ProtectedRegion> consumer) {
/* 189 */     for (ProtectedRegion region : this.regions.values()) {
/* 190 */       if (!consumer.apply(region)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyContaining(final Vector position, final Predicate<ProtectedRegion> consumer) {
/* 198 */     apply(new Predicate<ProtectedRegion>()
/*     */         {
/*     */           public boolean apply(ProtectedRegion region) {
/* 201 */             return (!region.contains(position) || consumer.apply(region));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyIntersecting(ProtectedRegion region, Predicate<ProtectedRegion> consumer) {
/* 208 */     for (ProtectedRegion found : region.getIntersectingRegions(this.regions.values())) {
/* 209 */       if (!consumer.apply(found)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 217 */     return this.regions.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public RegionDifference getAndClearDifference() {
/* 222 */     synchronized (this.lock) {
/* 223 */       Set<ProtectedRegion> changed = new HashSet<ProtectedRegion>();
/* 224 */       Set<ProtectedRegion> removed = this.removed;
/*     */       
/* 226 */       for (ProtectedRegion region : this.regions.values()) {
/* 227 */         if (region.isDirty()) {
/* 228 */           changed.add(region);
/* 229 */           region.setDirty(false);
/*     */         } 
/*     */       } 
/*     */       
/* 233 */       this.removed = new HashSet<ProtectedRegion>();
/*     */       
/* 235 */       return new RegionDifference(changed, removed);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDirty(RegionDifference difference) {
/* 241 */     synchronized (this.lock) {
/* 242 */       for (ProtectedRegion changed : difference.getChanged()) {
/* 243 */         changed.setDirty(true);
/*     */       }
/* 245 */       this.removed.addAll(difference.getRemoved());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<ProtectedRegion> values() {
/* 251 */     return Collections.unmodifiableCollection(this.regions.values());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirty() {
/* 256 */     synchronized (this.lock) {
/* 257 */       if (!this.removed.isEmpty()) {
/* 258 */         return true;
/*     */       }
/*     */       
/* 261 */       for (ProtectedRegion region : this.regions.values()) {
/* 262 */         if (region.isDirty()) {
/* 263 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 268 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDirty(boolean dirty) {
/* 273 */     synchronized (this.lock) {
/* 274 */       if (!dirty) {
/* 275 */         this.removed.clear();
/*     */       }
/*     */       
/* 278 */       for (ProtectedRegion region : this.regions.values()) {
/* 279 */         region.setDirty(dirty);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Factory
/*     */     implements Supplier<HashMapIndex>
/*     */   {
/*     */     public HashMapIndex get() {
/* 290 */       return new HashMapIndex();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\index\HashMapIndex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */