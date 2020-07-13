/*    */ package com.sk89q.worldguard.protection.managers.migration;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.sk89q.worldguard.protection.managers.storage.RegionDatabase;
/*    */ import com.sk89q.worldguard.protection.managers.storage.RegionDriver;
/*    */ import com.sk89q.worldguard.protection.managers.storage.StorageException;
/*    */ import java.util.logging.Level;
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
/*    */ 
/*    */ 
/*    */ abstract class AbstractMigration
/*    */   implements Migration
/*    */ {
/* 38 */   private static final Logger log = Logger.getLogger(AbstractMigration.class.getCanonicalName());
/*    */ 
/*    */ 
/*    */   
/*    */   private final RegionDriver driver;
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractMigration(RegionDriver driver) {
/* 47 */     Preconditions.checkNotNull(driver);
/*    */     
/* 49 */     this.driver = driver;
/*    */   }
/*    */ 
/*    */   
/*    */   public final void migrate() throws MigrationException {
/*    */     try {
/* 55 */       for (RegionDatabase store : this.driver.getAll()) {
/*    */         try {
/* 57 */           migrate(store);
/* 58 */         } catch (MigrationException e) {
/* 59 */           log.log(Level.WARNING, "Migration of one world (" + store.getName() + ") failed with an error", e);
/*    */         } 
/*    */       } 
/*    */       
/* 63 */       postMigration();
/* 64 */     } catch (StorageException e) {
/* 65 */       throw new MigrationException("Migration failed because the process of getting a list of all the worlds to migrate failed", e);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract void migrate(RegionDatabase paramRegionDatabase) throws MigrationException;
/*    */   
/*    */   protected abstract void postMigration();
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\migration\AbstractMigration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */