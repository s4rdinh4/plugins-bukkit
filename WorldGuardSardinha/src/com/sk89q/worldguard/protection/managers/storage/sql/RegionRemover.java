/*    */ package com.sk89q.worldguard.protection.managers.storage.sql;
/*    */ 
/*    */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*    */ import com.sk89q.worldguard.util.io.Closer;
/*    */ import com.sk89q.worldguard.util.sql.DataSourceConfig;
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.SQLException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
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
/*    */ class RegionRemover
/*    */ {
/*    */   private final DataSourceConfig config;
/*    */   private final Connection conn;
/*    */   private final int worldId;
/* 38 */   private final List<String> regionQueue = new ArrayList<String>();
/* 39 */   private final List<String> cuboidGeometryQueue = new ArrayList<String>();
/* 40 */   private final List<String> polygonGeometryQueue = new ArrayList<String>();
/*    */   
/*    */   RegionRemover(DataUpdater updater) {
/* 43 */     this.config = updater.config;
/* 44 */     this.conn = updater.conn;
/* 45 */     this.worldId = updater.worldId;
/*    */   }
/*    */   
/*    */   public void removeRegionsEntirely(Collection<String> names) {
/* 49 */     this.regionQueue.addAll(names);
/*    */   }
/*    */   
/*    */   public void removeGeometry(ProtectedRegion region, String currentType) {
/* 53 */     if (currentType.equals("cuboid")) {
/* 54 */       this.cuboidGeometryQueue.add(region.getId());
/* 55 */     } else if (currentType.equals("poly2d")) {
/* 56 */       this.polygonGeometryQueue.add(region.getId());
/* 57 */     } else if (!currentType.equals("global")) {
/*    */ 
/*    */       
/* 60 */       throw new RuntimeException("Unknown type of region in the database: " + currentType);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void removeRows(Collection<String> names, String table, String field) throws SQLException {
/* 66 */     Closer closer = Closer.create();
/*    */     try {
/* 68 */       PreparedStatement stmt = (PreparedStatement)closer.register(this.conn.prepareStatement("DELETE FROM " + this.config
/* 69 */             .getTablePrefix() + table + " WHERE " + field + " = ? AND world_id = " + this.worldId));
/*    */       
/* 71 */       StatementBatch batch = new StatementBatch(stmt, 100);
/* 72 */       for (String name : names) {
/* 73 */         stmt.setString(1, name);
/* 74 */         batch.addBatch();
/*    */       } 
/*    */       
/* 77 */       batch.executeRemaining();
/*    */     } finally {
/* 79 */       closer.closeQuietly();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void apply() throws SQLException {
/* 84 */     removeRows(this.regionQueue, "region", "id");
/* 85 */     removeRows(this.cuboidGeometryQueue, "region_cuboid", "region_id");
/* 86 */     removeRows(this.polygonGeometryQueue, "region_poly2d", "region_id");
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\storage\sql\RegionRemover.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */