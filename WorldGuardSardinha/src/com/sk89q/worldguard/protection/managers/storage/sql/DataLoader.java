/*     */ package com.sk89q.worldguard.protection.managers.storage.sql;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.ArrayListMultimap;
/*     */ import com.google.common.collect.HashBasedTable;
/*     */ import com.sk89q.worldedit.BlockVector;
/*     */ import com.sk89q.worldedit.BlockVector2D;
/*     */ import com.sk89q.worldedit.Vector;
/*     */ import com.sk89q.worldguard.domains.DefaultDomain;
/*     */ import com.sk89q.worldguard.protection.managers.storage.RegionDatabaseUtils;
/*     */ import com.sk89q.worldguard.protection.regions.GlobalProtectedRegion;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedPolygonalRegion;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import com.sk89q.worldguard.util.io.Closer;
/*     */ import com.sk89q.worldguard.util.sql.DataSourceConfig;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.yaml.snakeyaml.Yaml;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
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
/*     */ class DataLoader
/*     */ {
/*  57 */   private static final Logger log = Logger.getLogger(DataLoader.class.getCanonicalName());
/*     */   
/*     */   final Connection conn;
/*     */   
/*     */   final DataSourceConfig config;
/*     */   final int worldId;
/*  63 */   private final Map<String, ProtectedRegion> loaded = new HashMap<String, ProtectedRegion>();
/*  64 */   private final Map<ProtectedRegion, String> parentSets = new HashMap<ProtectedRegion, String>();
/*  65 */   private final Yaml yaml = SQLRegionDatabase.createYaml();
/*     */   
/*     */   DataLoader(SQLRegionDatabase regionStore, Connection conn) throws SQLException {
/*  68 */     Preconditions.checkNotNull(regionStore);
/*     */     
/*  70 */     this.conn = conn;
/*  71 */     this.config = regionStore.getDataSourceConfig();
/*  72 */     this.worldId = regionStore.getWorldId();
/*     */   }
/*     */   
/*     */   public Set<ProtectedRegion> load() throws SQLException {
/*  76 */     loadCuboids();
/*  77 */     loadPolygons();
/*  78 */     loadGlobals();
/*     */     
/*  80 */     loadFlags();
/*  81 */     loadDomainUsers();
/*  82 */     loadDomainGroups();
/*     */     
/*  84 */     RegionDatabaseUtils.relinkParents(this.loaded, this.parentSets);
/*     */     
/*  86 */     return new HashSet<ProtectedRegion>(this.loaded.values());
/*     */   }
/*     */   
/*     */   private void loadCuboids() throws SQLException {
/*  90 */     Closer closer = Closer.create();
/*     */     try {
/*  92 */       PreparedStatement stmt = (PreparedStatement)closer.register(this.conn.prepareStatement("SELECT g.min_z, g.min_y, g.min_x,        g.max_z, g.max_y, g.max_x,        r.id, r.priority, p.id AS parent FROM " + this.config
/*     */ 
/*     */ 
/*     */             
/*  96 */             .getTablePrefix() + "region_cuboid AS g " + "LEFT JOIN " + this.config
/*  97 */             .getTablePrefix() + "region AS r " + "          ON (g.region_id = r.id AND g.world_id = r.world_id) " + "LEFT JOIN " + this.config
/*     */             
/*  99 */             .getTablePrefix() + "region AS p " + "          ON (r.parent = p.id AND r.world_id = p.world_id) " + "WHERE r.world_id = " + this.worldId));
/*     */ 
/*     */ 
/*     */       
/* 103 */       ResultSet rs = closer.register(stmt.executeQuery());
/*     */       
/* 105 */       while (rs.next()) {
/* 106 */         Vector pt1 = new Vector(rs.getInt("min_x"), rs.getInt("min_y"), rs.getInt("min_z"));
/* 107 */         Vector pt2 = new Vector(rs.getInt("max_x"), rs.getInt("max_y"), rs.getInt("max_z"));
/*     */         
/* 109 */         BlockVector min = Vector.getMinimum(pt1, pt2).toBlockVector();
/* 110 */         BlockVector max = Vector.getMaximum(pt1, pt2).toBlockVector();
/* 111 */         ProtectedCuboidRegion protectedCuboidRegion = new ProtectedCuboidRegion(rs.getString("id"), min, max);
/*     */         
/* 113 */         protectedCuboidRegion.setPriority(rs.getInt("priority"));
/*     */         
/* 115 */         this.loaded.put(rs.getString("id"), protectedCuboidRegion);
/*     */         
/* 117 */         String parentId = rs.getString("parent");
/* 118 */         if (parentId != null) {
/* 119 */           this.parentSets.put(protectedCuboidRegion, parentId);
/*     */         }
/*     */       } 
/*     */     } finally {
/* 123 */       closer.closeQuietly();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void loadGlobals() throws SQLException {
/* 128 */     Closer closer = Closer.create();
/*     */     try {
/* 130 */       PreparedStatement stmt = (PreparedStatement)closer.register(this.conn.prepareStatement("SELECT r.id, r.priority, p.id AS parent FROM " + this.config
/*     */             
/* 132 */             .getTablePrefix() + "region AS r " + "LEFT JOIN " + this.config
/* 133 */             .getTablePrefix() + "region AS p " + "          ON (r.parent = p.id AND r.world_id = p.world_id) " + "WHERE r.type = 'global' AND r.world_id = " + this.worldId));
/*     */ 
/*     */ 
/*     */       
/* 137 */       ResultSet rs = closer.register(stmt.executeQuery());
/*     */       
/* 139 */       while (rs.next()) {
/* 140 */         GlobalProtectedRegion globalProtectedRegion = new GlobalProtectedRegion(rs.getString("id"));
/*     */         
/* 142 */         globalProtectedRegion.setPriority(rs.getInt("priority"));
/*     */         
/* 144 */         this.loaded.put(rs.getString("id"), globalProtectedRegion);
/*     */         
/* 146 */         String parentId = rs.getString("parent");
/* 147 */         if (parentId != null) {
/* 148 */           this.parentSets.put(globalProtectedRegion, parentId);
/*     */         }
/*     */       } 
/*     */     } finally {
/* 152 */       closer.closeQuietly();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void loadPolygons() throws SQLException {
/* 157 */     ArrayListMultimap arrayListMultimap = ArrayListMultimap.create();
/*     */ 
/*     */     
/* 160 */     Closer closer = Closer.create();
/*     */     try {
/* 162 */       PreparedStatement stmt = (PreparedStatement)closer.register(this.conn.prepareStatement("SELECT region_id, x, z FROM " + this.config
/*     */             
/* 164 */             .getTablePrefix() + "region_poly2d_point " + "WHERE world_id = " + this.worldId));
/*     */ 
/*     */       
/* 167 */       ResultSet rs = closer.register(stmt.executeQuery());
/*     */       
/* 169 */       while (rs.next()) {
/* 170 */         arrayListMultimap.put(rs.getString("region_id"), new BlockVector2D(rs.getInt("x"), rs.getInt("z")));
/*     */       }
/*     */     } finally {
/* 173 */       closer.closeQuietly();
/*     */     } 
/*     */ 
/*     */     
/* 177 */     closer = Closer.create();
/*     */     try {
/* 179 */       PreparedStatement stmt = (PreparedStatement)closer.register(this.conn.prepareStatement("SELECT g.min_y, g.max_y, r.id, r.priority, p.id AS parent FROM " + this.config
/*     */             
/* 181 */             .getTablePrefix() + "region_poly2d AS g " + "LEFT JOIN " + this.config
/* 182 */             .getTablePrefix() + "region AS r " + "          ON (g.region_id = r.id AND g.world_id = r.world_id) " + "LEFT JOIN " + this.config
/*     */             
/* 184 */             .getTablePrefix() + "region AS p " + "          ON (r.parent = p.id AND r.world_id = p.world_id) " + "WHERE r.world_id = " + this.worldId));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 189 */       ResultSet rs = closer.register(stmt.executeQuery());
/*     */       
/* 191 */       while (rs.next()) {
/* 192 */         String id = rs.getString("id");
/*     */ 
/*     */         
/* 195 */         List<BlockVector2D> points = arrayListMultimap.get(id);
/*     */         
/* 197 */         if (points.size() < 3) {
/* 198 */           log.log(Level.WARNING, "Invalid polygonal region '" + id + "': region has " + points.size() + " point(s) (less than the required 3). Skipping this region.");
/*     */           
/*     */           continue;
/*     */         } 
/* 202 */         Integer minY = Integer.valueOf(rs.getInt("min_y"));
/* 203 */         Integer maxY = Integer.valueOf(rs.getInt("max_y"));
/*     */         
/* 205 */         ProtectedPolygonalRegion protectedPolygonalRegion = new ProtectedPolygonalRegion(id, points, minY.intValue(), maxY.intValue());
/* 206 */         protectedPolygonalRegion.setPriority(rs.getInt("priority"));
/*     */         
/* 208 */         this.loaded.put(id, protectedPolygonalRegion);
/*     */         
/* 210 */         String parentId = rs.getString("parent");
/* 211 */         if (parentId != null) {
/* 212 */           this.parentSets.put(protectedPolygonalRegion, parentId);
/*     */         }
/*     */       } 
/*     */     } finally {
/* 216 */       closer.closeQuietly();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void loadFlags() throws SQLException {
/* 221 */     Closer closer = Closer.create();
/*     */     try {
/* 223 */       PreparedStatement stmt = (PreparedStatement)closer.register(this.conn.prepareStatement("SELECT region_id, flag, value FROM " + this.config
/*     */             
/* 225 */             .getTablePrefix() + "region_flag " + "WHERE world_id = " + this.worldId));
/*     */ 
/*     */       
/* 228 */       ResultSet rs = closer.register(stmt.executeQuery());
/*     */       
/* 230 */       HashBasedTable hashBasedTable = HashBasedTable.create();
/* 231 */       while (rs.next()) {
/* 232 */         hashBasedTable.put(rs
/* 233 */             .getString("region_id"), rs
/* 234 */             .getString("flag"), 
/* 235 */             unmarshalFlagValue(rs.getString("value")));
/*     */       }
/*     */       
/* 238 */       for (Map.Entry<String, Map<String, Object>> entry : (Iterable<Map.Entry<String, Map<String, Object>>>)hashBasedTable.rowMap().entrySet()) {
/* 239 */         ProtectedRegion region = this.loaded.get(entry.getKey());
/* 240 */         if (region != null) {
/* 241 */           RegionDatabaseUtils.trySetFlagMap(region, entry.getValue()); continue;
/*     */         } 
/* 243 */         throw new RuntimeException("An unexpected error occurred (loaded.get() returned null)");
/*     */       } 
/*     */     } finally {
/*     */       
/* 247 */       closer.closeQuietly();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void loadDomainUsers() throws SQLException {
/* 252 */     Closer closer = Closer.create();
/*     */     try {
/* 254 */       PreparedStatement stmt = (PreparedStatement)closer.register(this.conn.prepareStatement("SELECT p.region_id, u.name, u.uuid, p.owner FROM " + this.config
/*     */             
/* 256 */             .getTablePrefix() + "region_players AS p " + "LEFT JOIN " + this.config
/* 257 */             .getTablePrefix() + "user AS u " + "          ON (p.user_id = u.id) " + "WHERE p.world_id = " + this.worldId));
/*     */ 
/*     */ 
/*     */       
/* 261 */       ResultSet rs = closer.register(stmt.executeQuery());
/*     */       
/* 263 */       while (rs.next()) {
/* 264 */         ProtectedRegion region = this.loaded.get(rs.getString("region_id"));
/*     */         
/* 266 */         if (region != null) {
/*     */           DefaultDomain domain;
/*     */           
/* 269 */           if (rs.getBoolean("owner")) {
/* 270 */             domain = region.getOwners();
/*     */           } else {
/* 272 */             domain = region.getMembers();
/*     */           } 
/*     */           
/* 275 */           String name = rs.getString("name");
/* 276 */           String uuid = rs.getString("uuid");
/*     */           
/* 278 */           if (name != null) {
/*     */             
/* 280 */             domain.addPlayer(name); continue;
/* 281 */           }  if (uuid != null) {
/*     */             try {
/* 283 */               domain.addPlayer(UUID.fromString(uuid));
/* 284 */             } catch (IllegalArgumentException e) {
/* 285 */               log.warning("Invalid UUID '" + uuid + "' for region '" + region.getId() + "'");
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 291 */       closer.closeQuietly();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void loadDomainGroups() throws SQLException {
/* 296 */     Closer closer = Closer.create();
/*     */     try {
/* 298 */       PreparedStatement stmt = (PreparedStatement)closer.register(this.conn.prepareStatement("SELECT rg.region_id, g.name, rg.owner FROM `" + this.config
/*     */             
/* 300 */             .getTablePrefix() + "region_groups` AS rg " + "INNER JOIN `" + this.config
/* 301 */             .getTablePrefix() + "group` AS g ON (rg.group_id = g.id) " + "AND rg.world_id = " + this.worldId));
/*     */ 
/*     */ 
/*     */       
/* 305 */       ResultSet rs = closer.register(stmt.executeQuery());
/*     */       
/* 307 */       while (rs.next()) {
/* 308 */         ProtectedRegion region = this.loaded.get(rs.getString("region_id"));
/*     */         
/* 310 */         if (region != null) {
/*     */           DefaultDomain domain;
/*     */           
/* 313 */           if (rs.getBoolean("owner")) {
/* 314 */             domain = region.getOwners();
/*     */           } else {
/* 316 */             domain = region.getMembers();
/*     */           } 
/*     */           
/* 319 */           domain.addGroup(rs.getString("name"));
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 323 */       closer.closeQuietly();
/*     */     } 
/*     */   }
/*     */   
/*     */   private Object unmarshalFlagValue(String rawValue) {
/*     */     try {
/* 329 */       return this.yaml.load(rawValue);
/* 330 */     } catch (YAMLException e) {
/* 331 */       return String.valueOf(rawValue);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\storage\sql\DataLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */