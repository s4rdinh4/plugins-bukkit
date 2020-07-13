/*    */ package com.sk89q.worldguard.bukkit.protection;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.sk89q.worldguard.bukkit.RegionQuery;
/*    */ import com.sk89q.worldguard.domains.Association;
/*    */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*    */ import com.sk89q.worldguard.protection.association.RegionAssociable;
/*    */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.Location;
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
/*    */ public class DelayedRegionOverlapAssociation
/*    */   implements RegionAssociable
/*    */ {
/*    */   private final RegionQuery query;
/*    */   private final Location location;
/*    */   @Nullable
/*    */   private Set<ProtectedRegion> source;
/*    */   
/*    */   public DelayedRegionOverlapAssociation(RegionQuery query, Location location) {
/* 56 */     Preconditions.checkNotNull(query);
/* 57 */     Preconditions.checkNotNull(location);
/* 58 */     this.query = query;
/* 59 */     this.location = location;
/*    */   }
/*    */ 
/*    */   
/*    */   public Association getAssociation(List<ProtectedRegion> regions) {
/* 64 */     if (this.source == null) {
/* 65 */       ApplicableRegionSet result = this.query.getApplicableRegions(this.location);
/* 66 */       this.source = result.getRegions();
/*    */     } 
/*    */     
/* 69 */     for (ProtectedRegion region : regions) {
/* 70 */       if ((region.getId().equals("__global__") && this.source.isEmpty()) || this.source.contains(region)) {
/* 71 */         return Association.OWNER;
/*    */       }
/*    */     } 
/*    */     
/* 75 */     return Association.NON_MEMBER;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\protection\DelayedRegionOverlapAssociation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */