/*    */ package com.sk89q.worldguard.protection.managers;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*    */ import java.util.Collections;
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
/*    */ 
/*    */ public final class RegionDifference
/*    */ {
/*    */   private final Set<ProtectedRegion> changed;
/*    */   private final Set<ProtectedRegion> removed;
/*    */   
/*    */   public RegionDifference(Set<ProtectedRegion> changed, Set<ProtectedRegion> removed) {
/* 44 */     Preconditions.checkNotNull(changed);
/* 45 */     Preconditions.checkNotNull(removed);
/*    */     
/* 47 */     this.changed = changed;
/* 48 */     this.removed = removed;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<ProtectedRegion> getChanged() {
/* 57 */     return Collections.unmodifiableSet(this.changed);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<ProtectedRegion> getRemoved() {
/* 66 */     return Collections.unmodifiableSet(this.removed);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean containsChanges() {
/* 75 */     return (!this.changed.isEmpty() || !this.removed.isEmpty());
/*    */   }
/*    */   
/*    */   public void addAll(RegionDifference diff) {
/* 79 */     this.changed.addAll(diff.getChanged());
/* 80 */     this.removed.addAll(diff.getRemoved());
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\RegionDifference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */