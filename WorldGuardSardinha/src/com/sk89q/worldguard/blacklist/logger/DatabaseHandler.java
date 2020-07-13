/*     */ package com.sk89q.worldguard.blacklist.logger;
/*     */ 
/*     */ import com.sk89q.worldedit.Vector;
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import com.sk89q.worldguard.blacklist.event.BlacklistEvent;
/*     */ import com.sk89q.worldguard.blacklist.event.EventType;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DatabaseHandler
/*     */   implements LoggerHandler
/*     */ {
/*     */   private final String dsn;
/*     */   private final String user;
/*     */   private final String pass;
/*     */   private final String table;
/*     */   private final String worldName;
/*     */   private Connection conn;
/*     */   private final Logger logger;
/*     */   
/*     */   public DatabaseHandler(String dsn, String user, String pass, String table, String worldName, Logger logger) {
/*  57 */     this.dsn = dsn;
/*  58 */     this.user = user;
/*  59 */     this.pass = pass;
/*  60 */     this.table = table;
/*  61 */     this.worldName = worldName;
/*  62 */     this.logger = logger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Connection getConnection() throws SQLException {
/*  72 */     if (this.conn == null || this.conn.isClosed()) {
/*  73 */       this.conn = DriverManager.getConnection(this.dsn, this.user, this.pass);
/*     */     }
/*  75 */     return this.conn;
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
/*     */   
/*     */   private void logEvent(EventType eventType, @Nullable LocalPlayer player, Vector pos, int item, String comment) {
/*     */     try {
/*  89 */       Connection conn = getConnection();
/*  90 */       PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + this.table + "(event, world, player, x, y, z, item, time, comment) VALUES " + "(?, ?, ?, ?, ?, ?, ?, ?, ?)");
/*     */ 
/*     */ 
/*     */       
/*  94 */       stmt.setString(1, eventType.name());
/*  95 */       stmt.setString(2, this.worldName);
/*  96 */       stmt.setString(3, (player != null) ? player.getName() : "");
/*  97 */       stmt.setInt(4, pos.getBlockX());
/*  98 */       stmt.setInt(5, pos.getBlockY());
/*  99 */       stmt.setInt(6, pos.getBlockZ());
/* 100 */       stmt.setInt(7, item);
/* 101 */       stmt.setInt(8, (int)(System.currentTimeMillis() / 1000L));
/* 102 */       stmt.setString(9, comment);
/* 103 */       stmt.executeUpdate();
/* 104 */     } catch (SQLException e) {
/* 105 */       this.logger.log(Level.SEVERE, "Failed to log blacklist event to database: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void logEvent(BlacklistEvent event, String comment) {
/* 111 */     logEvent(event.getEventType(), event.getPlayer(), event.getLoggedPosition(), event.getTarget().getTypeId(), comment);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/*     */     try {
/* 117 */       if (this.conn != null && !this.conn.isClosed()) {
/* 118 */         this.conn.close();
/*     */       }
/* 120 */     } catch (SQLException ignore) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\logger\DatabaseHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */