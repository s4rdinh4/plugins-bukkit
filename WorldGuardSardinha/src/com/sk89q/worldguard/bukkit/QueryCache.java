/*     */ package com.sk89q.worldguard.bukkit;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*     */ import com.sk89q.worldguard.protection.managers.RegionManager;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class QueryCache
/*     */ {
/*  41 */   private final ConcurrentMap<CacheKey, ApplicableRegionSet> cache = new ConcurrentHashMap<CacheKey, ApplicableRegionSet>(16, 0.75F, 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ApplicableRegionSet queryContains(RegionManager manager, Location location) {
/*  52 */     Preconditions.checkNotNull(manager);
/*  53 */     Preconditions.checkNotNull(location);
/*     */     
/*  55 */     CacheKey key = new CacheKey(location);
/*  56 */     ApplicableRegionSet result = this.cache.get(key);
/*  57 */     if (result == null) {
/*  58 */       result = manager.getApplicableRegions(location);
/*  59 */       this.cache.put(key, result);
/*     */     } 
/*     */     
/*  62 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidateAll() {
/*  69 */     this.cache.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   private static class CacheKey
/*     */   {
/*     */     private final World world;
/*     */     
/*     */     private final int x;
/*     */     private final int y;
/*     */     private final int z;
/*     */     private final int hashCode;
/*     */     
/*     */     private CacheKey(Location location) {
/*  83 */       this.world = location.getWorld();
/*  84 */       this.x = location.getBlockX();
/*  85 */       this.y = location.getBlockY();
/*  86 */       this.z = location.getBlockZ();
/*     */ 
/*     */       
/*  89 */       int result = this.world.hashCode();
/*  90 */       result = 31 * result + this.x;
/*  91 */       result = 31 * result + this.y;
/*  92 */       result = 31 * result + this.z;
/*  93 */       this.hashCode = result;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  98 */       if (this == o) return true; 
/*  99 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/* 101 */       CacheKey cacheKey = (CacheKey)o;
/*     */       
/* 103 */       if (this.x != cacheKey.x) return false; 
/* 104 */       if (this.y != cacheKey.y) return false; 
/* 105 */       if (this.z != cacheKey.z) return false; 
/* 106 */       if (!this.world.equals(cacheKey.world)) return false;
/*     */       
/* 108 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 113 */       return this.hashCode;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\QueryCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */