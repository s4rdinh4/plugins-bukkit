/*    */ package com.sk89q.worldguard.bukkit.commands.task;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.sk89q.minecraft.util.commands.CommandException;
/*    */ import com.sk89q.worldguard.protection.managers.RegionManager;
/*    */ import com.sk89q.worldguard.protection.managers.RemovalStrategy;
/*    */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.Callable;
/*    */ import javax.annotation.Nullable;
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
/*    */ public class RegionRemover
/*    */   implements Callable<Set<ProtectedRegion>>
/*    */ {
/*    */   private final RegionManager manager;
/*    */   private final ProtectedRegion region;
/*    */   @Nullable
/*    */   private RemovalStrategy removalStrategy;
/*    */   
/*    */   public RegionRemover(RegionManager manager, ProtectedRegion region) {
/* 50 */     Preconditions.checkNotNull(manager);
/* 51 */     Preconditions.checkNotNull(region);
/* 52 */     this.manager = manager;
/* 53 */     this.region = region;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public RemovalStrategy getRemovalStrategy() {
/* 63 */     return this.removalStrategy;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRemovalStrategy(@Nullable RemovalStrategy removalStrategy) {
/* 73 */     this.removalStrategy = removalStrategy;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<ProtectedRegion> call() throws Exception {
/* 78 */     if (this.removalStrategy == null) {
/* 79 */       for (ProtectedRegion test : this.manager.getRegions().values()) {
/* 80 */         ProtectedRegion parent = test.getParent();
/* 81 */         if (parent != null && parent.equals(this.region)) {
/* 82 */           throw new CommandException("The region '" + this.region
/* 83 */               .getId() + "' has child regions. Use -f to force removal of children " + "or -u to unset the parent value of these children.");
/*    */         }
/*    */       } 
/*    */ 
/*    */       
/* 88 */       return this.manager.removeRegion(this.region.getId(), RemovalStrategy.UNSET_PARENT_IN_CHILDREN);
/*    */     } 
/* 90 */     return this.manager.removeRegion(this.region.getId(), this.removalStrategy);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\commands\task\RegionRemover.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */