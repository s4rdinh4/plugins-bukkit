/*    */ package com.sk89q.worldguard.protection.managers.migration;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.sk89q.worldguard.protection.managers.storage.RegionDatabase;
/*    */ import com.sk89q.worldguard.protection.managers.storage.RegionDriver;
/*    */ import com.sk89q.worldguard.protection.managers.storage.StorageException;
/*    */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*    */ import java.util.Set;
/*    */ import java.util.logging.Logger;
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
/*    */ public class DriverMigration
/*    */   extends AbstractMigration
/*    */ {
/* 37 */   private static final Logger log = Logger.getLogger(DriverMigration.class.getCanonicalName());
/*    */ 
/*    */ 
/*    */   
/*    */   private final RegionDriver target;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DriverMigration(RegionDriver driver, RegionDriver target) {
/* 47 */     super(driver);
/* 48 */     Preconditions.checkNotNull(target);
/* 49 */     this.target = target;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void migrate(RegionDatabase store) throws MigrationException {
/*    */     Set<ProtectedRegion> regions;
/* 56 */     log.info("Loading the regions for '" + store.getName() + "' with the old driver...");
/*    */     
/*    */     try {
/* 59 */       regions = store.loadAll();
/* 60 */     } catch (StorageException e) {
/* 61 */       throw new MigrationException("Failed to load region data for the world '" + store.getName() + "'", e);
/*    */     } 
/*    */     
/* 64 */     write(store.getName(), regions);
/*    */   }
/*    */   
/*    */   private void write(String name, Set<ProtectedRegion> regions) throws MigrationException {
/* 68 */     log.info("Saving the data for '" + name + "' with the new driver...");
/*    */     
/* 70 */     RegionDatabase store = this.target.get(name);
/*    */     
/*    */     try {
/* 73 */       store.saveAll(regions);
/* 74 */     } catch (StorageException e) {
/* 75 */       throw new MigrationException("Failed to save region data for '" + store.getName() + "' to the new driver", e);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void postMigration() {}
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\migration\DriverMigration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */