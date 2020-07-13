/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.db2.DB2DbSupport;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.derby.DerbyDbSupport;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.h2.H2DbSupport;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.hsql.HsqlDbSupport;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.mysql.MySQLDbSupport;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.oracle.OracleDbSupport;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.postgresql.PostgreSQLDbSupport;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.sqlite.SQLiteDbSupport;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.sqlserver.SQLServerDbSupport;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.Log;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.LogFactory;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
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
/*     */ public class DbSupportFactory
/*     */ {
/*  42 */   private static final Log LOG = LogFactory.getLog(DbSupportFactory.class);
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
/*     */   public static DbSupport createDbSupport(Connection connection, boolean printInfo) {
/*  59 */     String databaseProductName = getDatabaseProductName(connection);
/*     */     
/*  61 */     if (printInfo) {
/*  62 */       LOG.info("Database: " + getJdbcUrl(connection) + " (" + databaseProductName + ")");
/*     */     }
/*     */     
/*  65 */     if (databaseProductName.startsWith("Apache Derby")) {
/*  66 */       return (DbSupport)new DerbyDbSupport(connection);
/*     */     }
/*  68 */     if (databaseProductName.startsWith("SQLite")) {
/*  69 */       return (DbSupport)new SQLiteDbSupport(connection);
/*     */     }
/*  71 */     if (databaseProductName.startsWith("H2")) {
/*  72 */       return (DbSupport)new H2DbSupport(connection);
/*     */     }
/*  74 */     if (databaseProductName.contains("HSQL Database Engine"))
/*     */     {
/*  76 */       return (DbSupport)new HsqlDbSupport(connection);
/*     */     }
/*  78 */     if (databaseProductName.startsWith("Microsoft SQL Server")) {
/*  79 */       return (DbSupport)new SQLServerDbSupport(connection);
/*     */     }
/*  81 */     if (databaseProductName.contains("MySQL"))
/*     */     {
/*     */ 
/*     */       
/*  85 */       return (DbSupport)new MySQLDbSupport(connection);
/*     */     }
/*  87 */     if (databaseProductName.startsWith("Oracle")) {
/*  88 */       return (DbSupport)new OracleDbSupport(connection);
/*     */     }
/*  90 */     if (databaseProductName.startsWith("PostgreSQL")) {
/*  91 */       return (DbSupport)new PostgreSQLDbSupport(connection);
/*     */     }
/*  93 */     if (databaseProductName.startsWith("DB2"))
/*     */     {
/*     */       
/*  96 */       return (DbSupport)new DB2DbSupport(connection);
/*     */     }
/*     */     
/*  99 */     throw new FlywayException("Unsupported Database: " + databaseProductName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getJdbcUrl(Connection connection) {
/*     */     try {
/* 110 */       return connection.getMetaData().getURL();
/* 111 */     } catch (SQLException e) {
/* 112 */       throw new FlywayException("Unable to retrieve the Jdbc connection Url!", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getDatabaseProductName(Connection connection) {
/*     */     try {
/* 124 */       DatabaseMetaData databaseMetaData = connection.getMetaData();
/* 125 */       if (databaseMetaData == null) {
/* 126 */         throw new FlywayException("Unable to read database metadata while it is null!");
/*     */       }
/*     */       
/* 129 */       String databaseProductName = databaseMetaData.getDatabaseProductName();
/* 130 */       if (databaseProductName == null) {
/* 131 */         throw new FlywayException("Unable to determine database. Product name is null.");
/*     */       }
/*     */       
/* 134 */       int databaseMajorVersion = databaseMetaData.getDatabaseMajorVersion();
/* 135 */       int databaseMinorVersion = databaseMetaData.getDatabaseMinorVersion();
/*     */       
/* 137 */       return databaseProductName + " " + databaseMajorVersion + "." + databaseMinorVersion;
/* 138 */     } catch (SQLException e) {
/* 139 */       throw new FlywayException("Error while determining database product name", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\DbSupportFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */