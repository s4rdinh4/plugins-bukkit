/*     */ package com.sk89q.worldguard.util.sql;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.SQLException;
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
/*     */ public class DataSourceConfig
/*     */ {
/*     */   private final String dsn;
/*     */   private final String username;
/*     */   private final String password;
/*     */   private final String tablePrefix;
/*     */   
/*     */   public DataSourceConfig(String dsn, String username, String password, String tablePrefix) {
/*  47 */     Preconditions.checkNotNull(dsn);
/*  48 */     Preconditions.checkNotNull(username);
/*  49 */     Preconditions.checkNotNull(password);
/*  50 */     Preconditions.checkNotNull(tablePrefix);
/*     */     
/*  52 */     this.dsn = dsn;
/*  53 */     this.username = username;
/*  54 */     this.password = password;
/*  55 */     this.tablePrefix = tablePrefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDsn() {
/*  64 */     return this.dsn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUsername() {
/*  73 */     return this.username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPassword() {
/*  82 */     return this.password;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTablePrefix() {
/*  91 */     return this.tablePrefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataSourceConfig setDsn(String dsn) {
/* 101 */     return new DataSourceConfig(dsn, this.username, this.password, this.tablePrefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataSourceConfig setUsername(String username) {
/* 111 */     return new DataSourceConfig(this.dsn, username, this.password, this.tablePrefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataSourceConfig setPassword(String password) {
/* 121 */     return new DataSourceConfig(this.dsn, this.username, password, this.tablePrefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataSourceConfig setTablePrefix(String tablePrefix) {
/* 131 */     return new DataSourceConfig(this.dsn, this.username, this.password, tablePrefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection getConnection() throws SQLException {
/* 141 */     return DriverManager.getConnection(this.dsn, this.username, this.password);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\sql\DataSourceConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */