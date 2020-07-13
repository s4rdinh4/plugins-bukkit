/*     */ package com.sk89q.worldguard.protection.managers.storage;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.sk89q.worldguard.protection.flags.DefaultFlag;
/*     */ import com.sk89q.worldguard.protection.flags.Flag;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Logger;
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
/*     */ public final class RegionDatabaseUtils
/*     */ {
/*  41 */   private static final Logger log = Logger.getLogger(RegionDatabaseUtils.class.getCanonicalName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void trySetFlagMap(ProtectedRegion region, Map<String, Object> flagData) {
/*  53 */     Preconditions.checkNotNull(region);
/*  54 */     Preconditions.checkNotNull(flagData);
/*     */     
/*  56 */     for (Flag<?> flag : DefaultFlag.getFlags()) {
/*  57 */       if (flag != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  63 */         Object o = flagData.get(flag.getName());
/*  64 */         if (o != null) {
/*  65 */           trySetFlag(region, flag, o);
/*     */         }
/*     */ 
/*     */         
/*  69 */         if (flag.getRegionGroupFlag() != null) {
/*  70 */           Object o2 = flagData.get(flag.getRegionGroupFlag().getName());
/*  71 */           if (o2 != null) {
/*  72 */             trySetFlag(region, (Flag<?>)flag.getRegionGroupFlag(), o2);
/*     */           }
/*     */         } 
/*     */       } 
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
/*     */   public static <T> boolean trySetFlag(ProtectedRegion region, Flag<T> flag, @Nullable Object value) {
/*  88 */     Preconditions.checkNotNull(region);
/*  89 */     Preconditions.checkNotNull(flag);
/*     */     
/*  91 */     T val = (T)flag.unmarshal(value);
/*     */     
/*  93 */     if (val != null) {
/*  94 */       region.setFlag(flag, val);
/*  95 */       return true;
/*     */     } 
/*  97 */     log.warning("Failed to parse flag '" + flag.getName() + "' with value '" + value + "'");
/*  98 */     return false;
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
/*     */   public static void relinkParents(Map<String, ProtectedRegion> regions, Map<ProtectedRegion, String> parentSets) {
/* 110 */     Preconditions.checkNotNull(regions);
/* 111 */     Preconditions.checkNotNull(parentSets);
/*     */     
/* 113 */     for (Map.Entry<ProtectedRegion, String> entry : parentSets.entrySet()) {
/* 114 */       ProtectedRegion target = entry.getKey();
/* 115 */       ProtectedRegion parent = regions.get(entry.getValue());
/* 116 */       if (parent != null) {
/*     */         try {
/* 118 */           target.setParent(parent);
/* 119 */         } catch (com.sk89q.worldguard.protection.regions.ProtectedRegion.CircularInheritanceException e) {
/* 120 */           log.warning("Circular inheritance detected! Can't set the parent of '" + target + "' to parent '" + parent.getId() + "'");
/*     */         }  continue;
/*     */       } 
/* 123 */       log.warning("Unknown region parent: " + (String)entry.getValue());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\storage\RegionDatabaseUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */