/*     */ package com.sk89q.worldguard.protection.managers.storage.sql;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.sk89q.worldguard.protection.managers.RegionDifference;
/*     */ import com.sk89q.worldguard.protection.managers.storage.DifferenceSaveException;
/*     */ import com.sk89q.worldguard.protection.managers.storage.RegionDatabase;
/*     */ import com.sk89q.worldguard.protection.managers.storage.StorageException;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import com.sk89q.worldguard.util.io.Closer;
/*     */ import com.sk89q.worldguard.util.sql.DataSourceConfig;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Set;
/*     */ import org.yaml.snakeyaml.DumperOptions;
/*     */ import org.yaml.snakeyaml.Yaml;
/*     */ import org.yaml.snakeyaml.constructor.BaseConstructor;
/*     */ import org.yaml.snakeyaml.constructor.SafeConstructor;
/*     */ import org.yaml.snakeyaml.representer.Representer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SQLRegionDatabase
/*     */   implements RegionDatabase
/*     */ {
/*     */   private final String worldName;
/*     */   private final DataSourceConfig config;
/*     */   private final SQLDriver driver;
/*     */   private int worldId;
/*     */   private boolean initialized = false;
/*     */   
/*     */   SQLRegionDatabase(SQLDriver driver, String worldName) {
/*  65 */     Preconditions.checkNotNull(driver);
/*  66 */     Preconditions.checkNotNull(worldName);
/*     */     
/*  68 */     this.config = driver.getConfig();
/*  69 */     this.worldName = worldName;
/*  70 */     this.driver = driver;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  75 */     return this.worldName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void initialize() throws StorageException {
/*  84 */     if (!this.initialized) {
/*  85 */       this.driver.initialize();
/*     */       
/*     */       try {
/*  88 */         this.worldId = chooseWorldId(this.worldName);
/*  89 */       } catch (SQLException e) {
/*  90 */         throw new StorageException("Failed to choose the ID for this world", e);
/*     */       } 
/*     */       
/*  93 */       this.initialized = true;
/*     */     } 
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
/*     */   private int chooseWorldId(String worldName) throws SQLException {
/* 106 */     Closer closer = Closer.create();
/*     */     try {
/* 108 */       Connection conn = closer.register(getConnection());
/*     */       
/* 110 */       PreparedStatement stmt = (PreparedStatement)closer.register(conn.prepareStatement("SELECT id FROM " + this.config
/* 111 */             .getTablePrefix() + "world WHERE name = ? LIMIT 0, 1"));
/*     */       
/* 113 */       stmt.setString(1, worldName);
/* 114 */       ResultSet worldResult = closer.register(stmt.executeQuery());
/*     */       
/* 116 */       if (worldResult.next()) {
/* 117 */         return worldResult.getInt("id");
/*     */       }
/* 119 */       PreparedStatement stmt2 = (PreparedStatement)closer.register(conn.prepareStatement("INSERT INTO " + this.config
/* 120 */             .getTablePrefix() + "world  (id, name) VALUES (null, ?)", 1));
/*     */ 
/*     */       
/* 123 */       stmt2.setString(1, worldName);
/* 124 */       stmt2.execute();
/* 125 */       ResultSet generatedKeys = stmt2.getGeneratedKeys();
/*     */       
/* 127 */       if (generatedKeys.next()) {
/* 128 */         return generatedKeys.getInt(1);
/*     */       }
/* 130 */       throw new SQLException("Expected result, got none");
/*     */     }
/*     */     finally {
/*     */       
/* 134 */       closer.closeQuietly();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Connection getConnection() throws SQLException {
/* 145 */     return this.driver.getConnection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataSourceConfig getDataSourceConfig() {
/* 154 */     return this.config;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWorldId() {
/* 163 */     return this.worldId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getRegionTypeName(ProtectedRegion region) {
/* 173 */     if (region instanceof com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion)
/* 174 */       return "cuboid"; 
/* 175 */     if (region instanceof com.sk89q.worldguard.protection.regions.ProtectedPolygonalRegion)
/* 176 */       return "poly2d"; 
/* 177 */     if (region instanceof com.sk89q.worldguard.protection.regions.GlobalProtectedRegion) {
/* 178 */       return "global";
/*     */     }
/* 180 */     throw new IllegalArgumentException("Unexpected region type: " + region.getClass().getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Yaml createYaml() {
/* 190 */     DumperOptions options = new DumperOptions();
/* 191 */     options.setIndent(2);
/* 192 */     options.setDefaultFlowStyle(DumperOptions.FlowStyle.FLOW);
/* 193 */     Representer representer = new Representer();
/* 194 */     representer.setDefaultFlowStyle(DumperOptions.FlowStyle.FLOW);
/*     */ 
/*     */     
/* 197 */     return new Yaml((BaseConstructor)new SafeConstructor(), new Representer(), options);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<ProtectedRegion> loadAll() throws StorageException {
/* 202 */     initialize();
/*     */     
/* 204 */     Closer closer = Closer.create();
/*     */     
/*     */     try {
/*     */       DataLoader loader;
/*     */       try {
/* 209 */         loader = new DataLoader(this, closer.register(getConnection()));
/* 210 */       } catch (SQLException e) {
/* 211 */         throw new StorageException("Failed to get a connection to the database", e);
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 220 */       closer.closeQuietly();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveAll(Set<ProtectedRegion> regions) throws StorageException {
/* 226 */     Preconditions.checkNotNull(regions);
/*     */     
/* 228 */     initialize();
/*     */     
/* 230 */     Closer closer = Closer.create();
/*     */     
/*     */     try {
/*     */       DataUpdater updater;
/*     */       try {
/* 235 */         updater = new DataUpdater(this, closer.register(getConnection()));
/* 236 */       } catch (SQLException e) {
/* 237 */         throw new StorageException("Failed to get a connection to the database", e);
/*     */       } 
/*     */       
/*     */       try {
/* 241 */         updater.saveAll(regions);
/* 242 */       } catch (SQLException e) {
/* 243 */         throw new StorageException("Failed to save the region data to the database", e);
/*     */       } 
/*     */     } finally {
/* 246 */       closer.closeQuietly();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveChanges(RegionDifference difference) throws DifferenceSaveException, StorageException {
/* 252 */     Preconditions.checkNotNull(difference);
/*     */     
/* 254 */     initialize();
/*     */     
/* 256 */     Closer closer = Closer.create();
/*     */     
/*     */     try {
/*     */       DataUpdater updater;
/*     */       try {
/* 261 */         updater = new DataUpdater(this, closer.register(getConnection()));
/* 262 */       } catch (SQLException e) {
/* 263 */         throw new StorageException("Failed to get a connection to the database", e);
/*     */       } 
/*     */       
/*     */       try {
/* 267 */         updater.saveChanges(difference.getChanged(), difference.getRemoved());
/* 268 */       } catch (SQLException e) {
/* 269 */         throw new StorageException("Failed to save the region data to the database", e);
/*     */       } 
/*     */     } finally {
/* 272 */       closer.closeQuietly();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\storage\sql\SQLRegionDatabase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */