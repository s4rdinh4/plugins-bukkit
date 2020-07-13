/*    */ package com.sk89q.worldguard.protection.association;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.sk89q.worldguard.domains.Association;
/*    */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*    */ import java.util.List;
/*    */ import java.util.Set;
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
/*    */ public class RegionOverlapAssociation
/*    */   implements RegionAssociable
/*    */ {
/*    */   private final Set<ProtectedRegion> source;
/*    */   
/*    */   public RegionOverlapAssociation(Set<ProtectedRegion> source) {
/* 44 */     Preconditions.checkNotNull(source);
/* 45 */     this.source = source;
/*    */   }
/*    */ 
/*    */   
/*    */   public Association getAssociation(List<ProtectedRegion> regions) {
/* 50 */     for (ProtectedRegion region : regions) {
/* 51 */       if ((region.getId().equals("__global__") && this.source.isEmpty()) || this.source.contains(region)) {
/* 52 */         return Association.OWNER;
/*    */       }
/*    */     } 
/*    */     
/* 56 */     return Association.NON_MEMBER;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\association\RegionOverlapAssociation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */