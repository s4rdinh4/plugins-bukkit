/*     */ package com.sk89q.worldguard.protection.managers.storage.sql;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import com.sk89q.worldguard.util.io.Closer;
/*     */ import com.sk89q.worldguard.util.sql.DataSourceConfig;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
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
/*     */ class DataUpdater
/*     */ {
/*     */   final Connection conn;
/*     */   final DataSourceConfig config;
/*     */   final int worldId;
/*     */   final DomainTableCache domainTableCache;
/*     */   
/*     */   DataUpdater(SQLRegionDatabase regionStore, Connection conn) throws SQLException {
/*  47 */     Preconditions.checkNotNull(regionStore);
/*     */     
/*  49 */     this.conn = conn;
/*  50 */     this.config = regionStore.getDataSourceConfig();
/*  51 */     this.worldId = regionStore.getWorldId();
/*  52 */     this.domainTableCache = new DomainTableCache(this.config, conn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveAll(Set<ProtectedRegion> regions) throws SQLException {
/*  62 */     executeSave(regions, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveChanges(Set<ProtectedRegion> changed, Set<ProtectedRegion> removed) throws SQLException {
/*  73 */     executeSave(changed, removed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void executeSave(Set<ProtectedRegion> toUpdate, @Nullable Set<ProtectedRegion> toRemove) throws SQLException {
/*  85 */     Map<String, String> existing = getExistingRegions();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  91 */       this.conn.setAutoCommit(false);
/*     */       
/*  93 */       RegionUpdater updater = new RegionUpdater(this);
/*  94 */       RegionInserter inserter = new RegionInserter(this);
/*  95 */       RegionRemover remover = new RegionRemover(this);
/*     */       
/*  97 */       for (ProtectedRegion region : toUpdate) {
/*  98 */         if (toRemove != null && toRemove.contains(region)) {
/*     */           continue;
/*     */         }
/*     */         
/* 102 */         String currentType = existing.get(region.getId());
/*     */ 
/*     */         
/* 105 */         if (currentType != null) {
/* 106 */           existing.remove(region.getId());
/*     */           
/* 108 */           updater.updateRegionType(region);
/* 109 */           remover.removeGeometry(region, currentType);
/*     */         } else {
/* 111 */           inserter.insertRegionType(region);
/*     */         } 
/*     */         
/* 114 */         inserter.insertGeometry(region);
/* 115 */         updater.updateRegionProperties(region);
/*     */       } 
/*     */       
/* 118 */       if (toRemove != null) {
/* 119 */         List<String> removeNames = new ArrayList<String>();
/* 120 */         for (ProtectedRegion region : toRemove) {
/* 121 */           removeNames.add(region.getId());
/*     */         }
/* 123 */         remover.removeRegionsEntirely(removeNames);
/*     */       } else {
/* 125 */         remover.removeRegionsEntirely(existing.keySet());
/*     */       } 
/*     */       
/* 128 */       remover.apply();
/* 129 */       inserter.apply();
/* 130 */       updater.apply();
/*     */       
/* 132 */       this.conn.commit();
/* 133 */     } catch (SQLException e) {
/* 134 */       this.conn.rollback();
/* 135 */       throw e;
/* 136 */     } catch (RuntimeException e) {
/* 137 */       this.conn.rollback();
/* 138 */       throw e;
/*     */     } finally {
/* 140 */       this.conn.setAutoCommit(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<String, String> getExistingRegions() throws SQLException {
/* 147 */     Map<String, String> existing = new HashMap<String, String>();
/*     */     
/* 149 */     Closer closer = Closer.create();
/*     */     try {
/* 151 */       PreparedStatement stmt = (PreparedStatement)closer.register(this.conn.prepareStatement("SELECT id, type FROM " + this.config
/*     */             
/* 153 */             .getTablePrefix() + "region " + "WHERE world_id = " + this.worldId));
/*     */ 
/*     */       
/* 156 */       ResultSet resultSet = closer.register(stmt.executeQuery());
/*     */       
/* 158 */       while (resultSet.next()) {
/* 159 */         existing.put(resultSet.getString("id"), resultSet.getString("type"));
/*     */       }
/*     */       
/* 162 */       return existing;
/*     */     } finally {
/* 164 */       closer.closeQuietly();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\storage\sql\DataUpdater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */