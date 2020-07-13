/*     */ package com.sk89q.worldguard.protection.managers.storage.sql;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.Flyway;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationVersion;
/*     */ import com.sk89q.worldguard.protection.managers.storage.RegionDatabase;
/*     */ import com.sk89q.worldguard.protection.managers.storage.RegionDriver;
/*     */ import com.sk89q.worldguard.protection.managers.storage.StorageException;
/*     */ import com.sk89q.worldguard.util.io.Closer;
/*     */ import com.sk89q.worldguard.util.sql.DataSourceConfig;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class SQLDriver
/*     */   implements RegionDriver
/*     */ {
/*  59 */   private static final Logger log = Logger.getLogger(SQLDriver.class.getCanonicalName());
/*  60 */   private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();
/*     */ 
/*     */   
/*     */   private static final int CONNECTION_TIMEOUT = 6000;
/*     */ 
/*     */   
/*     */   private final DataSourceConfig config;
/*     */   
/*     */   private boolean initialized = false;
/*     */ 
/*     */   
/*     */   public SQLDriver(DataSourceConfig config) {
/*  72 */     Preconditions.checkNotNull(config);
/*  73 */     this.config = config;
/*     */   }
/*     */ 
/*     */   
/*     */   public RegionDatabase get(String name) {
/*  78 */     return new SQLRegionDatabase(this, name);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<RegionDatabase> getAll() throws StorageException {
/*  83 */     Closer closer = Closer.create();
/*     */     try {
/*  85 */       List<RegionDatabase> stores = new ArrayList<RegionDatabase>();
/*  86 */       Connection connection = closer.register(getConnection());
/*  87 */       Statement stmt = connection.createStatement();
/*  88 */       ResultSet rs = closer.register(stmt.executeQuery("SELECT name FROM " + this.config.getTablePrefix() + "world"));
/*  89 */       while (rs.next()) {
/*  90 */         stores.add(get(rs.getString(1)));
/*     */       }
/*  92 */       return stores;
/*  93 */     } catch (SQLException e) {
/*  94 */       throw new StorageException("Failed to fetch list of worlds", e);
/*     */     } finally {
/*  96 */       closer.closeQuietly();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void initialize() throws StorageException {
/* 106 */     if (!this.initialized) {
/*     */       try {
/* 108 */         migrate();
/* 109 */       } catch (SQLException e) {
/* 110 */         throw new StorageException("Failed to migrate database tables", e);
/*     */       } 
/* 112 */       this.initialized = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void migrate() throws SQLException, StorageException {
/* 123 */     Closer closer = Closer.create();
/* 124 */     Connection conn = closer.register(getConnection());
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*     */       boolean tablesExist, isRecent, isBeforeMigrations, hasMigrations;
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 134 */         tablesExist = tryQuery(conn, "SELECT * FROM " + this.config.getTablePrefix() + "region_cuboid LIMIT 1");
/* 135 */         isRecent = tryQuery(conn, "SELECT world_id FROM " + this.config.getTablePrefix() + "region_cuboid LIMIT 1");
/* 136 */         isBeforeMigrations = !tryQuery(conn, "SELECT uuid FROM " + this.config.getTablePrefix() + "user LIMIT 1");
/* 137 */         hasMigrations = tryQuery(conn, "SELECT * FROM " + this.config.getTablePrefix() + "migrations LIMIT 1");
/*     */       } finally {
/* 139 */         closer.closeQuietly();
/*     */       } 
/*     */ 
/*     */       
/* 143 */       if (tablesExist && !isRecent) {
/* 144 */         throw new StorageException("Sorry, your tables are too old for the region SQL auto-migration system. Please run region_manual_update_20110325.sql on your database, which comes with WorldGuard or can be found in http://github.com/sk89q/worldguard");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 151 */       Map<String, String> placeHolders = new HashMap<String, String>();
/* 152 */       placeHolders.put("tablePrefix", this.config.getTablePrefix());
/*     */       
/* 154 */       Flyway flyway = new Flyway();
/*     */ 
/*     */ 
/*     */       
/* 158 */       if (!hasMigrations) {
/* 159 */         flyway.setInitOnMigrate(true);
/*     */         
/* 161 */         if (tablesExist) {
/*     */           
/* 163 */           if (isBeforeMigrations) {
/* 164 */             flyway.setInitVersion(MigrationVersion.fromVersion("1"));
/*     */           }
/*     */           
/* 167 */           log.log(Level.INFO, "The SQL region tables exist but the migrations table seems to not exist yet. Creating the migrations table...");
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 173 */           flyway.setInitVersion(MigrationVersion.fromVersion("0"));
/*     */           
/* 175 */           log.log(Level.INFO, "SQL region tables do not exist: creating...");
/*     */         } 
/*     */       } 
/*     */       
/* 179 */       flyway.setClassLoader(getClass().getClassLoader());
/* 180 */       flyway.setLocations(new String[] { "migrations/region/" + getMigrationFolderName() });
/* 181 */       flyway.setDataSource(this.config.getDsn(), this.config.getUsername(), this.config.getPassword(), new String[0]);
/* 182 */       flyway.setTable(this.config.getTablePrefix() + "migrations");
/* 183 */       flyway.setPlaceholders(placeHolders);
/* 184 */       flyway.setValidateOnMigrate(false);
/* 185 */       flyway.migrate();
/* 186 */     } catch (FlywayException e) {
/* 187 */       throw new StorageException("Failed to migrate tables", e);
/*     */     } finally {
/* 189 */       closer.closeQuietly();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMigrationFolderName() {
/* 199 */     return "mysql";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean tryQuery(Connection conn, String sql) {
/* 210 */     Closer closer = Closer.create();
/*     */     try {
/* 212 */       Statement statement = closer.register(conn.createStatement());
/* 213 */       statement.executeQuery(sql);
/* 214 */       return true;
/* 215 */     } catch (SQLException ex) {
/* 216 */       return false;
/*     */     } finally {
/* 218 */       closer.closeQuietly();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DataSourceConfig getConfig() {
/* 228 */     return this.config;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Connection getConnection() throws SQLException {
/* 238 */     Future<Connection> future = EXECUTOR.submit(new Callable<Connection>()
/*     */         {
/*     */           public Connection call() throws Exception {
/* 241 */             return SQLDriver.this.config.getConnection();
/*     */           }
/*     */         });
/*     */     
/*     */     try {
/* 246 */       return future.get(6000L, TimeUnit.MILLISECONDS);
/* 247 */     } catch (InterruptedException e) {
/* 248 */       throw new SQLException("Failed to get a SQL connection because the operation was interrupted", e);
/* 249 */     } catch (ExecutionException e) {
/* 250 */       throw new SQLException("Failed to get a SQL connection due to an error", e);
/* 251 */     } catch (TimeoutException e) {
/* 252 */       future.cancel(true);
/* 253 */       throw new SQLException("Failed to get a SQL connection within the time limit");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\storage\sql\SQLDriver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */