/*     */ package com.sk89q.worldguard.protection;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import com.sk89q.worldguard.protection.association.RegionAssociable;
/*     */ import com.sk89q.worldguard.protection.flags.Flag;
/*     */ import com.sk89q.worldguard.protection.flags.StateFlag;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import com.sk89q.worldguard.protection.util.NormativeOrders;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public class RegionResultSet
/*     */   extends AbstractRegionSet
/*     */ {
/*     */   private final List<ProtectedRegion> applicable;
/*     */   private final FlagValueCalculator flagValueCalculator;
/*     */   @Nullable
/*     */   private Set<ProtectedRegion> regionSet;
/*     */   
/*     */   public RegionResultSet(List<ProtectedRegion> applicable, @Nullable ProtectedRegion globalRegion) {
/*  55 */     this(applicable, globalRegion, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegionResultSet(Set<ProtectedRegion> applicable, @Nullable ProtectedRegion globalRegion) {
/*  65 */     this(NormativeOrders.fromSet(applicable), globalRegion, true);
/*  66 */     this.regionSet = applicable;
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
/*     */   public RegionResultSet(List<ProtectedRegion> applicable, @Nullable ProtectedRegion globalRegion, boolean sorted) {
/*  81 */     Preconditions.checkNotNull(applicable);
/*  82 */     if (!sorted) {
/*  83 */       NormativeOrders.sort(applicable);
/*     */     }
/*  85 */     this.applicable = applicable;
/*  86 */     this.flagValueCalculator = new FlagValueCalculator(applicable, globalRegion);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isVirtual() {
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public StateFlag.State queryState(@Nullable RegionAssociable subject, StateFlag... flags) {
/*  97 */     return this.flagValueCalculator.queryState(subject, flags);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <V> V queryValue(@Nullable RegionAssociable subject, Flag<V> flag) {
/* 103 */     return this.flagValueCalculator.queryValue(subject, flag);
/*     */   }
/*     */ 
/*     */   
/*     */   public <V> Collection<V> queryAllValues(@Nullable RegionAssociable subject, Flag<V> flag) {
/* 108 */     return this.flagValueCalculator.queryAllValues(subject, flag);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnerOfAll(LocalPlayer player) {
/* 113 */     Preconditions.checkNotNull(player);
/*     */     
/* 115 */     for (ProtectedRegion region : this.applicable) {
/* 116 */       if (!region.isOwner(player)) {
/* 117 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 121 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMemberOfAll(LocalPlayer player) {
/* 126 */     Preconditions.checkNotNull(player);
/*     */     
/* 128 */     for (ProtectedRegion region : this.applicable) {
/* 129 */       if (!region.isMember(player)) {
/* 130 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 134 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 139 */     return this.applicable.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<ProtectedRegion> getRegions() {
/* 144 */     if (this.regionSet != null) {
/* 145 */       return this.regionSet;
/*     */     }
/* 147 */     this.regionSet = Collections.unmodifiableSet(new HashSet<ProtectedRegion>(this.applicable));
/* 148 */     return this.regionSet;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<ProtectedRegion> iterator() {
/* 153 */     return this.applicable.iterator();
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
/*     */   public static RegionResultSet fromSortedList(List<ProtectedRegion> regions, @Nullable ProtectedRegion globalRegion) {
/* 165 */     return new RegionResultSet(regions, globalRegion, true);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\RegionResultSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */