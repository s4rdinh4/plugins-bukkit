/*    */ package com.sk89q.worldguard.bukkit.commands.task;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.sk89q.worldguard.protection.managers.RegionManager;
/*    */ import com.sk89q.worldguard.protection.managers.storage.StorageException;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.concurrent.Callable;
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
/*    */ public class RegionManagerSaver
/*    */   implements Callable<Collection<RegionManager>>
/*    */ {
/*    */   private final Collection<RegionManager> managers;
/*    */   
/*    */   public RegionManagerSaver(Collection<RegionManager> managers) {
/* 36 */     Preconditions.checkNotNull(managers);
/* 37 */     this.managers = managers;
/*    */   }
/*    */   
/*    */   public RegionManagerSaver(RegionManager... manager) {
/* 41 */     this(Arrays.asList(manager));
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<RegionManager> call() throws StorageException {
/* 46 */     for (RegionManager manager : this.managers) {
/* 47 */       manager.save();
/*    */     }
/*    */     
/* 50 */     return this.managers;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\commands\task\RegionManagerSaver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */