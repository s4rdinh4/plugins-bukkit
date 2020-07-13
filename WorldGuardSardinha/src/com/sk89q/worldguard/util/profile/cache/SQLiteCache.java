/*     */ package com.sk89q.worldguard.util.profile.cache;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.sk89q.worldguard.util.profile.Profile;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SQLiteCache
/*     */   extends AbstractProfileCache
/*     */ {
/*  49 */   private static final Logger log = Logger.getLogger(SQLiteCache.class.getCanonicalName());
/*     */ 
/*     */   
/*     */   private final Connection connection;
/*     */ 
/*     */   
/*     */   private final PreparedStatement updateStatement;
/*     */ 
/*     */   
/*     */   public SQLiteCache(File file) throws IOException {
/*  59 */     Preconditions.checkNotNull(file);
/*     */     
/*     */     try {
/*  62 */       Class.forName("org.sqlite.JDBC");
/*  63 */       this.connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
/*  64 */     } catch (ClassNotFoundException e) {
/*  65 */       throw new IOException("SQLite JDBC support is not installed");
/*  66 */     } catch (SQLException e) {
/*  67 */       throw new IOException("Failed to connect to cache file", e);
/*     */     } 
/*     */     
/*     */     try {
/*  71 */       createTable();
/*  72 */     } catch (SQLException e) {
/*  73 */       throw new IOException("Failed to create tables", e);
/*     */     } 
/*     */     
/*     */     try {
/*  77 */       this.updateStatement = this.connection.prepareStatement("INSERT OR REPLACE INTO uuid_cache (uuid, name) VALUES (?, ?)");
/*  78 */     } catch (SQLException e) {
/*  79 */       throw new IOException("Failed to prepare statements", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Connection getConnection() throws SQLException {
/*  90 */     return this.connection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createTable() throws SQLException {
/*  99 */     Connection conn = getConnection();
/* 100 */     Statement stmt = conn.createStatement();
/* 101 */     stmt.executeUpdate("CREATE TABLE IF NOT EXISTS uuid_cache (\n  uuid CHAR(36) PRIMARY KEY NOT NULL,\n  name CHAR(32) NOT NULL)");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 107 */       stmt.executeUpdate("CREATE INDEX name_index ON uuid_cache (name)");
/* 108 */     } catch (SQLException ignored) {}
/*     */ 
/*     */     
/* 111 */     stmt.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public void putAll(Iterable<Profile> entries) {
/*     */     try {
/* 117 */       executePut(entries);
/* 118 */     } catch (SQLException e) {
/* 119 */       log.log(Level.WARNING, "Failed to execute queries", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ImmutableMap<UUID, Profile> getAllPresent(Iterable<UUID> uuids) {
/*     */     try {
/* 126 */       return executeGet(uuids);
/* 127 */     } catch (SQLException e) {
/* 128 */       log.log(Level.WARNING, "Failed to execute queries", e);
/*     */ 
/*     */       
/* 131 */       return ImmutableMap.of();
/*     */     } 
/*     */   }
/*     */   protected synchronized void executePut(Iterable<Profile> profiles) throws SQLException {
/* 135 */     for (Profile profile : profiles) {
/* 136 */       this.updateStatement.setString(1, profile.getUniqueId().toString());
/* 137 */       this.updateStatement.setString(2, profile.getName());
/* 138 */       this.updateStatement.executeUpdate();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected ImmutableMap<UUID, Profile> executeGet(Iterable<UUID> uuids) throws SQLException {
/* 143 */     StringBuilder builder = new StringBuilder();
/* 144 */     builder.append("SELECT name, uuid FROM uuid_cache WHERE uuid IN (");
/*     */     
/* 146 */     boolean first = true;
/* 147 */     for (UUID uuid : uuids) {
/* 148 */       Preconditions.checkNotNull(uuid, "Unexpected null UUID");
/*     */       
/* 150 */       if (!first) {
/* 151 */         builder.append(", ");
/*     */       }
/* 153 */       builder.append("'").append(uuid).append("'");
/* 154 */       first = false;
/*     */     } 
/*     */ 
/*     */     
/* 158 */     if (first) {
/* 159 */       return ImmutableMap.of();
/*     */     }
/*     */     
/* 162 */     builder.append(")");
/*     */     
/* 164 */     synchronized (this) {
/* 165 */       Connection conn = getConnection();
/* 166 */       Statement stmt = conn.createStatement();
/*     */       try {
/* 168 */         ResultSet rs = stmt.executeQuery(builder.toString());
/* 169 */         Map<UUID, Profile> map = new HashMap<UUID, Profile>();
/*     */         
/* 171 */         while (rs.next()) {
/* 172 */           UUID uniqueId = UUID.fromString(rs.getString("uuid"));
/* 173 */           map.put(uniqueId, new Profile(uniqueId, rs.getString("name")));
/*     */         } 
/*     */         
/* 176 */         return ImmutableMap.copyOf(map);
/*     */       } finally {
/* 178 */         stmt.close();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\profile\cache\SQLiteCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */