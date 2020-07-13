/*     */ package com.sk89q.worldguard.protection.managers.storage.sql;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.sk89q.worldedit.BlockVector;
/*     */ import com.sk89q.worldedit.BlockVector2D;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedPolygonalRegion;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import com.sk89q.worldguard.util.io.Closer;
/*     */ import com.sk89q.worldguard.util.sql.DataSourceConfig;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class RegionInserter
/*     */ {
/*     */   private final DataSourceConfig config;
/*     */   private final Connection conn;
/*     */   private final int worldId;
/*  46 */   private final List<ProtectedRegion> all = new ArrayList<ProtectedRegion>();
/*  47 */   private final List<ProtectedCuboidRegion> cuboids = new ArrayList<ProtectedCuboidRegion>();
/*  48 */   private final List<ProtectedPolygonalRegion> polygons = new ArrayList<ProtectedPolygonalRegion>();
/*     */   
/*     */   RegionInserter(DataUpdater updater) {
/*  51 */     this.config = updater.config;
/*  52 */     this.conn = updater.conn;
/*  53 */     this.worldId = updater.worldId;
/*     */   }
/*     */   
/*     */   public void insertRegionType(ProtectedRegion region) throws SQLException {
/*  57 */     this.all.add(region);
/*     */   }
/*     */ 
/*     */   
/*     */   public void insertGeometry(ProtectedRegion region) throws SQLException {
/*  62 */     if (region instanceof ProtectedCuboidRegion) {
/*  63 */       this.cuboids.add((ProtectedCuboidRegion)region);
/*     */     }
/*  65 */     else if (region instanceof ProtectedPolygonalRegion) {
/*  66 */       this.polygons.add((ProtectedPolygonalRegion)region);
/*     */     }
/*  68 */     else if (!(region instanceof com.sk89q.worldguard.protection.regions.GlobalProtectedRegion)) {
/*     */ 
/*     */ 
/*     */       
/*  72 */       throw new IllegalArgumentException("Unknown type of region: " + region.getClass().getName());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void insertRegionTypes() throws SQLException {
/*  77 */     Closer closer = Closer.create();
/*     */     try {
/*  79 */       PreparedStatement stmt = (PreparedStatement)closer.register(this.conn.prepareStatement("INSERT INTO " + this.config
/*  80 */             .getTablePrefix() + "region " + "(id, world_id, type, priority, parent) " + "VALUES " + "(?, ?, ?, ?, NULL)"));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  85 */       for (List<ProtectedRegion> partition : (Iterable<List<ProtectedRegion>>)Lists.partition(this.all, 100)) {
/*  86 */         for (ProtectedRegion region : partition) {
/*  87 */           stmt.setString(1, region.getId());
/*  88 */           stmt.setInt(2, this.worldId);
/*  89 */           stmt.setString(3, SQLRegionDatabase.getRegionTypeName(region));
/*  90 */           stmt.setInt(4, region.getPriority());
/*  91 */           stmt.addBatch();
/*     */         } 
/*     */         
/*  94 */         stmt.executeBatch();
/*     */       } 
/*     */     } finally {
/*  97 */       closer.closeQuietly();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void insertCuboids() throws SQLException {
/* 102 */     Closer closer = Closer.create();
/*     */     try {
/* 104 */       PreparedStatement stmt = (PreparedStatement)closer.register(this.conn.prepareStatement("INSERT INTO " + this.config
/* 105 */             .getTablePrefix() + "region_cuboid " + "(region_id, world_id, min_z, min_y, min_x, max_z, max_y, max_x ) " + "VALUES " + "(?, " + this.worldId + ", ?, ?, ?, ?, ?, ?)"));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 110 */       for (List<ProtectedCuboidRegion> partition : (Iterable<List<ProtectedCuboidRegion>>)Lists.partition(this.cuboids, 100)) {
/* 111 */         for (ProtectedCuboidRegion region : partition) {
/* 112 */           BlockVector min = region.getMinimumPoint();
/* 113 */           BlockVector max = region.getMaximumPoint();
/*     */           
/* 115 */           stmt.setString(1, region.getId());
/* 116 */           stmt.setInt(2, min.getBlockZ());
/* 117 */           stmt.setInt(3, min.getBlockY());
/* 118 */           stmt.setInt(4, min.getBlockX());
/* 119 */           stmt.setInt(5, max.getBlockZ());
/* 120 */           stmt.setInt(6, max.getBlockY());
/* 121 */           stmt.setInt(7, max.getBlockX());
/* 122 */           stmt.addBatch();
/*     */         } 
/*     */         
/* 125 */         stmt.executeBatch();
/*     */       } 
/*     */     } finally {
/* 128 */       closer.closeQuietly();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void insertPolygons() throws SQLException {
/* 133 */     Closer closer = Closer.create();
/*     */     try {
/* 135 */       PreparedStatement stmt = (PreparedStatement)closer.register(this.conn.prepareStatement("INSERT INTO " + this.config
/* 136 */             .getTablePrefix() + "region_poly2d " + "(region_id, world_id, max_y, min_y) " + "VALUES " + "(?, " + this.worldId + ", ?, ?)"));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 141 */       for (List<ProtectedPolygonalRegion> partition : (Iterable<List<ProtectedPolygonalRegion>>)Lists.partition(this.polygons, 100)) {
/* 142 */         for (ProtectedPolygonalRegion region : partition) {
/* 143 */           stmt.setString(1, region.getId());
/* 144 */           stmt.setInt(2, region.getMaximumPoint().getBlockY());
/* 145 */           stmt.setInt(3, region.getMinimumPoint().getBlockY());
/* 146 */           stmt.addBatch();
/*     */         } 
/*     */         
/* 149 */         stmt.executeBatch();
/*     */       } 
/*     */     } finally {
/* 152 */       closer.closeQuietly();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void insertPolygonVertices() throws SQLException {
/* 157 */     Closer closer = Closer.create();
/*     */     try {
/* 159 */       PreparedStatement stmt = (PreparedStatement)closer.register(this.conn.prepareStatement("INSERT INTO " + this.config
/* 160 */             .getTablePrefix() + "region_poly2d_point" + "(region_id, world_id, z, x) " + "VALUES " + "(?, " + this.worldId + ", ?, ?)"));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 165 */       StatementBatch batch = new StatementBatch(stmt, 100);
/*     */       
/* 167 */       for (ProtectedPolygonalRegion region : this.polygons) {
/* 168 */         for (BlockVector2D point : region.getPoints()) {
/* 169 */           stmt.setString(1, region.getId());
/* 170 */           stmt.setInt(2, point.getBlockZ());
/* 171 */           stmt.setInt(3, point.getBlockX());
/* 172 */           batch.addBatch();
/*     */         } 
/*     */       } 
/*     */       
/* 176 */       batch.executeRemaining();
/*     */     } finally {
/* 178 */       closer.closeQuietly();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void apply() throws SQLException {
/* 183 */     insertRegionTypes();
/* 184 */     insertCuboids();
/* 185 */     insertPolygons();
/* 186 */     insertPolygonVertices();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\storage\sql\RegionInserter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */