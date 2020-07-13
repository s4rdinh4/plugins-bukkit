/*     */ package com.sk89q.worldguard.protection.managers.storage.sql;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.sk89q.worldguard.domains.DefaultDomain;
/*     */ import com.sk89q.worldguard.protection.flags.Flag;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import com.sk89q.worldguard.util.io.Closer;
/*     */ import com.sk89q.worldguard.util.sql.DataSourceConfig;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.yaml.snakeyaml.Yaml;
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
/*     */ class RegionUpdater
/*     */ {
/*  47 */   private static final Logger log = Logger.getLogger(RegionUpdater.class.getCanonicalName());
/*     */   
/*     */   private final DataSourceConfig config;
/*     */   private final Connection conn;
/*     */   private final int worldId;
/*     */   private final DomainTableCache domainTableCache;
/*  53 */   private final Set<String> userNames = new HashSet<String>();
/*  54 */   private final Set<UUID> userUuids = new HashSet<UUID>();
/*  55 */   private final Set<String> groupNames = new HashSet<String>();
/*     */   
/*  57 */   private final Yaml yaml = SQLRegionDatabase.createYaml();
/*     */   
/*  59 */   private final List<ProtectedRegion> typesToUpdate = new ArrayList<ProtectedRegion>();
/*  60 */   private final List<ProtectedRegion> parentsToSet = new ArrayList<ProtectedRegion>();
/*  61 */   private final List<ProtectedRegion> flagsToReplace = new ArrayList<ProtectedRegion>();
/*  62 */   private final List<ProtectedRegion> domainsToReplace = new ArrayList<ProtectedRegion>();
/*     */   
/*     */   RegionUpdater(DataUpdater updater) {
/*  65 */     this.config = updater.config;
/*  66 */     this.conn = updater.conn;
/*  67 */     this.worldId = updater.worldId;
/*  68 */     this.domainTableCache = updater.domainTableCache;
/*     */   }
/*     */   
/*     */   public void updateRegionType(ProtectedRegion region) {
/*  72 */     this.typesToUpdate.add(region);
/*     */   }
/*     */   
/*     */   public void updateRegionProperties(ProtectedRegion region) {
/*  76 */     if (region.getParent() != null) {
/*  77 */       this.parentsToSet.add(region);
/*     */     }
/*     */     
/*  80 */     this.flagsToReplace.add(region);
/*  81 */     this.domainsToReplace.add(region);
/*     */     
/*  83 */     addDomain(region.getOwners());
/*  84 */     addDomain(region.getMembers());
/*     */   }
/*     */ 
/*     */   
/*     */   private void addDomain(DefaultDomain domain) {
/*  89 */     for (String name : domain.getPlayers()) {
/*  90 */       this.userNames.add(name.toLowerCase());
/*     */     }
/*     */     
/*  93 */     for (UUID uuid : domain.getUniqueIds()) {
/*  94 */       this.userUuids.add(uuid);
/*     */     }
/*     */     
/*  97 */     for (String name : domain.getGroups()) {
/*  98 */       this.groupNames.add(name.toLowerCase());
/*     */     }
/*     */   }
/*     */   
/*     */   private void setParents() throws SQLException {
/* 103 */     Closer closer = Closer.create();
/*     */     try {
/* 105 */       PreparedStatement stmt = (PreparedStatement)closer.register(this.conn.prepareStatement("UPDATE " + this.config
/* 106 */             .getTablePrefix() + "region " + "SET parent = ? " + "WHERE id = ? AND world_id = " + this.worldId));
/*     */ 
/*     */ 
/*     */       
/* 110 */       for (List<ProtectedRegion> partition : (Iterable<List<ProtectedRegion>>)Lists.partition(this.parentsToSet, 100)) {
/* 111 */         for (ProtectedRegion region : partition) {
/* 112 */           ProtectedRegion parent = region.getParent();
/* 113 */           if (parent != null) {
/* 114 */             stmt.setString(1, parent.getId());
/* 115 */             stmt.setString(2, region.getId());
/* 116 */             stmt.addBatch();
/*     */           } 
/*     */         } 
/*     */         
/* 120 */         stmt.executeBatch();
/*     */       } 
/*     */     } finally {
/* 123 */       closer.closeQuietly();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void replaceFlags() throws SQLException {
/* 128 */     Closer closer = Closer.create();
/*     */     try {
/* 130 */       PreparedStatement stmt = (PreparedStatement)closer.register(this.conn.prepareStatement("DELETE FROM " + this.config
/* 131 */             .getTablePrefix() + "region_flag " + "WHERE region_id = ? " + "AND world_id = " + this.worldId));
/*     */ 
/*     */ 
/*     */       
/* 135 */       for (List<ProtectedRegion> partition : (Iterable<List<ProtectedRegion>>)Lists.partition(this.flagsToReplace, 100)) {
/* 136 */         for (ProtectedRegion region : partition) {
/* 137 */           stmt.setString(1, region.getId());
/* 138 */           stmt.addBatch();
/*     */         } 
/*     */         
/* 141 */         stmt.executeBatch();
/*     */       } 
/*     */     } finally {
/* 144 */       closer.closeQuietly();
/*     */     } 
/*     */     
/* 147 */     closer = Closer.create();
/*     */     try {
/* 149 */       PreparedStatement stmt = (PreparedStatement)closer.register(this.conn.prepareStatement("INSERT INTO " + this.config
/* 150 */             .getTablePrefix() + "region_flag " + "(id, region_id, world_id, flag, value) " + "VALUES " + "(null, ?, " + this.worldId + ", ?, ?)"));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 155 */       StatementBatch batch = new StatementBatch(stmt, 100);
/*     */       
/* 157 */       for (ProtectedRegion region : this.flagsToReplace) {
/* 158 */         for (Map.Entry<Flag<?>, Object> entry : (Iterable<Map.Entry<Flag<?>, Object>>)region.getFlags().entrySet()) {
/* 159 */           if (entry.getValue() == null)
/*     */             continue; 
/* 161 */           Object flag = marshalFlagValue(entry.getKey(), entry.getValue());
/*     */           
/* 163 */           stmt.setString(1, region.getId());
/* 164 */           stmt.setString(2, ((Flag)entry.getKey()).getName());
/* 165 */           stmt.setObject(3, flag);
/* 166 */           batch.addBatch();
/*     */         } 
/*     */       } 
/*     */       
/* 170 */       batch.executeRemaining();
/*     */     } finally {
/* 172 */       closer.closeQuietly();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void replaceDomainUsers() throws SQLException {
/* 178 */     Closer closer = Closer.create();
/*     */     try {
/* 180 */       PreparedStatement stmt = (PreparedStatement)closer.register(this.conn.prepareStatement("DELETE FROM " + this.config
/* 181 */             .getTablePrefix() + "region_players " + "WHERE region_id = ? " + "AND world_id = " + this.worldId));
/*     */ 
/*     */ 
/*     */       
/* 185 */       for (List<ProtectedRegion> partition : (Iterable<List<ProtectedRegion>>)Lists.partition(this.domainsToReplace, 100)) {
/* 186 */         for (ProtectedRegion region : partition) {
/* 187 */           stmt.setString(1, region.getId());
/* 188 */           stmt.addBatch();
/*     */         } 
/*     */         
/* 191 */         stmt.executeBatch();
/*     */       } 
/*     */     } finally {
/* 194 */       closer.closeQuietly();
/*     */     } 
/*     */ 
/*     */     
/* 198 */     closer = Closer.create();
/*     */     try {
/* 200 */       PreparedStatement stmt = (PreparedStatement)closer.register(this.conn.prepareStatement("INSERT INTO " + this.config
/* 201 */             .getTablePrefix() + "region_players " + "(region_id, world_id, user_id, owner) " + "VALUES (?, " + this.worldId + ",  ?, ?)"));
/*     */ 
/*     */ 
/*     */       
/* 205 */       StatementBatch batch = new StatementBatch(stmt, 100);
/*     */       
/* 207 */       for (ProtectedRegion region : this.domainsToReplace) {
/* 208 */         insertDomainUsers(stmt, batch, region, region.getMembers(), false);
/* 209 */         insertDomainUsers(stmt, batch, region, region.getOwners(), true);
/*     */       } 
/*     */       
/* 212 */       batch.executeRemaining();
/*     */     } finally {
/* 214 */       closer.closeQuietly();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void insertDomainUsers(PreparedStatement stmt, StatementBatch batch, ProtectedRegion region, DefaultDomain domain, boolean owner) throws SQLException {
/* 220 */     for (String name : domain.getPlayers()) {
/* 221 */       Integer id = this.domainTableCache.getUserNameCache().find(name);
/* 222 */       if (id != null) {
/* 223 */         stmt.setString(1, region.getId());
/* 224 */         stmt.setInt(2, id.intValue());
/* 225 */         stmt.setBoolean(3, owner);
/* 226 */         batch.addBatch(); continue;
/*     */       } 
/* 228 */       log.log(Level.WARNING, "Did not find an ID for the user identified as '" + name + "'");
/*     */     } 
/*     */ 
/*     */     
/* 232 */     for (UUID uuid : domain.getUniqueIds()) {
/* 233 */       Integer id = this.domainTableCache.getUserUuidCache().find(uuid);
/* 234 */       if (id != null) {
/* 235 */         stmt.setString(1, region.getId());
/* 236 */         stmt.setInt(2, id.intValue());
/* 237 */         stmt.setBoolean(3, owner);
/* 238 */         batch.addBatch(); continue;
/*     */       } 
/* 240 */       log.log(Level.WARNING, "Did not find an ID for the user identified by '" + uuid + "'");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void replaceDomainGroups() throws SQLException {
/* 247 */     Closer closer = Closer.create();
/*     */     try {
/* 249 */       PreparedStatement stmt = (PreparedStatement)closer.register(this.conn.prepareStatement("DELETE FROM " + this.config
/* 250 */             .getTablePrefix() + "region_groups " + "WHERE region_id = ? " + "AND world_id = " + this.worldId));
/*     */ 
/*     */ 
/*     */       
/* 254 */       for (List<ProtectedRegion> partition : (Iterable<List<ProtectedRegion>>)Lists.partition(this.domainsToReplace, 100)) {
/* 255 */         for (ProtectedRegion region : partition) {
/* 256 */           stmt.setString(1, region.getId());
/* 257 */           stmt.addBatch();
/*     */         } 
/*     */         
/* 260 */         stmt.executeBatch();
/*     */       } 
/*     */     } finally {
/* 263 */       closer.closeQuietly();
/*     */     } 
/*     */ 
/*     */     
/* 267 */     closer = Closer.create();
/*     */     try {
/* 269 */       PreparedStatement stmt = (PreparedStatement)closer.register(this.conn.prepareStatement("INSERT INTO " + this.config
/* 270 */             .getTablePrefix() + "region_groups " + "(region_id, world_id, group_id, owner) " + "VALUES (?, " + this.worldId + ",  ?, ?)"));
/*     */ 
/*     */ 
/*     */       
/* 274 */       StatementBatch batch = new StatementBatch(stmt, 100);
/*     */       
/* 276 */       for (ProtectedRegion region : this.domainsToReplace) {
/* 277 */         insertDomainGroups(stmt, batch, region, region.getMembers(), false);
/* 278 */         insertDomainGroups(stmt, batch, region, region.getOwners(), true);
/*     */       } 
/*     */       
/* 281 */       batch.executeRemaining();
/*     */     } finally {
/* 283 */       closer.closeQuietly();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void insertDomainGroups(PreparedStatement stmt, StatementBatch batch, ProtectedRegion region, DefaultDomain domain, boolean owner) throws SQLException {
/* 288 */     for (String name : domain.getGroups()) {
/* 289 */       Integer id = this.domainTableCache.getGroupNameCache().find(name);
/* 290 */       if (id != null) {
/* 291 */         stmt.setString(1, region.getId());
/* 292 */         stmt.setInt(2, id.intValue());
/* 293 */         stmt.setBoolean(3, owner);
/* 294 */         batch.addBatch(); continue;
/*     */       } 
/* 296 */       log.log(Level.WARNING, "Did not find an ID for the group identified as '" + name + "'");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateRegionTypes() throws SQLException {
/* 302 */     Closer closer = Closer.create();
/*     */     try {
/* 304 */       PreparedStatement stmt = (PreparedStatement)closer.register(this.conn.prepareStatement("UPDATE " + this.config
/* 305 */             .getTablePrefix() + "region " + "SET type = ?, priority = ?, parent = NULL " + "WHERE id = ? AND world_id = " + this.worldId));
/*     */ 
/*     */ 
/*     */       
/* 309 */       for (List<ProtectedRegion> partition : (Iterable<List<ProtectedRegion>>)Lists.partition(this.typesToUpdate, 100)) {
/* 310 */         for (ProtectedRegion region : partition) {
/* 311 */           stmt.setString(1, SQLRegionDatabase.getRegionTypeName(region));
/* 312 */           stmt.setInt(2, region.getPriority());
/* 313 */           stmt.setString(3, region.getId());
/* 314 */           stmt.addBatch();
/*     */         } 
/*     */         
/* 317 */         stmt.executeBatch();
/*     */       } 
/*     */     } finally {
/* 320 */       closer.closeQuietly();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void apply() throws SQLException {
/* 325 */     this.domainTableCache.getUserNameCache().fetch(this.userNames);
/* 326 */     this.domainTableCache.getUserUuidCache().fetch(this.userUuids);
/* 327 */     this.domainTableCache.getGroupNameCache().fetch(this.groupNames);
/*     */     
/* 329 */     updateRegionTypes();
/* 330 */     setParents();
/* 331 */     replaceFlags();
/* 332 */     replaceDomainUsers();
/* 333 */     replaceDomainGroups();
/*     */   }
/*     */ 
/*     */   
/*     */   private <V> Object marshalFlagValue(Flag<V> flag, Object val) {
/* 338 */     return this.yaml.dump(flag.marshal(val));
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\storage\sql\RegionUpdater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */