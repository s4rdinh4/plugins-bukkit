/*    */ package com.sk89q.worldguard.protection.managers.storage;
/*    */ 
/*    */ import com.sk89q.worldguard.protection.managers.RegionDifference;
/*    */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
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
/*    */ public class MemoryRegionDatabase
/*    */   implements RegionDatabase
/*    */ {
/* 37 */   private Set<ProtectedRegion> regions = Collections.emptySet();
/*    */ 
/*    */   
/*    */   public String getName() {
/* 41 */     return "MEMORY";
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<ProtectedRegion> loadAll() {
/* 46 */     return this.regions;
/*    */   }
/*    */ 
/*    */   
/*    */   public void saveAll(Set<ProtectedRegion> regions) {
/* 51 */     this.regions = Collections.unmodifiableSet(new HashSet<ProtectedRegion>(regions));
/*    */   }
/*    */ 
/*    */   
/*    */   public void saveChanges(RegionDifference difference) throws DifferenceSaveException {
/* 56 */     throw new DifferenceSaveException();
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\storage\MemoryRegionDatabase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */