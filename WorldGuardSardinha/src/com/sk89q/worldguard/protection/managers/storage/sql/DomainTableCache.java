/*    */ package com.sk89q.worldguard.protection.managers.storage.sql;
/*    */ 
/*    */ import com.sk89q.worldguard.util.sql.DataSourceConfig;
/*    */ import java.sql.Connection;
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
/*    */ class DomainTableCache
/*    */ {
/*    */   private final TableCache.UserNameCache userNameCache;
/*    */   private final TableCache.UserUuidCache userUuidCache;
/*    */   private final TableCache.GroupNameCache groupNameCache;
/*    */   
/*    */   DomainTableCache(DataSourceConfig config, Connection conn) {
/* 36 */     this.userNameCache = new TableCache.UserNameCache(config, conn);
/* 37 */     this.userUuidCache = new TableCache.UserUuidCache(config, conn);
/* 38 */     this.groupNameCache = new TableCache.GroupNameCache(config, conn);
/*    */   }
/*    */   
/*    */   public TableCache.UserNameCache getUserNameCache() {
/* 42 */     return this.userNameCache;
/*    */   }
/*    */   
/*    */   public TableCache.UserUuidCache getUserUuidCache() {
/* 46 */     return this.userUuidCache;
/*    */   }
/*    */   
/*    */   public TableCache.GroupNameCache getGroupNameCache() {
/* 50 */     return this.groupNameCache;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\storage\sql\DomainTableCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */