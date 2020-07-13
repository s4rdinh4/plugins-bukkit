/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.command;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationVersion;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.callback.FlywayCallback;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.metadatatable.AppliedMigration;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.metadatatable.MetaDataTable;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.jdbc.TransactionCallback;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.jdbc.TransactionTemplate;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.Log;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.LogFactory;
/*     */ import java.sql.Connection;
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
/*     */ public class DbInit
/*     */ {
/*  35 */   private static final Log LOG = LogFactory.getLog(DbInit.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Connection connection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final MetaDataTable metaDataTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final MigrationVersion initVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String initDescription;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final FlywayCallback[] callbacks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DbInit(Connection connection, MetaDataTable metaDataTable, MigrationVersion initVersion, String initDescription, FlywayCallback[] callbacks) {
/*  73 */     this.connection = connection;
/*  74 */     this.metaDataTable = metaDataTable;
/*  75 */     this.initVersion = initVersion;
/*  76 */     this.initDescription = initDescription;
/*  77 */     this.callbacks = callbacks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  84 */     for (FlywayCallback callback : this.callbacks) {
/*  85 */       (new TransactionTemplate(this.connection)).execute(new TransactionCallback<Object>()
/*     */           {
/*     */             public Object doInTransaction() throws SQLException {
/*  88 */               callback.beforeInit(DbInit.this.connection);
/*  89 */               return null;
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/*  94 */     (new TransactionTemplate(this.connection)).execute(new TransactionCallback<Void>() {
/*     */           public Void doInTransaction() {
/*  96 */             if (DbInit.this.metaDataTable.hasAppliedMigrations()) {
/*  97 */               throw new FlywayException("Unable to init metadata table " + DbInit.this.metaDataTable + " as it already contains migrations");
/*     */             }
/*  99 */             if (DbInit.this.metaDataTable.hasInitMarker()) {
/* 100 */               AppliedMigration initMarker = DbInit.this.metaDataTable.getInitMarker();
/* 101 */               if (DbInit.this.initVersion.equals(initMarker.getVersion()) && DbInit.this.initDescription.equals(initMarker.getDescription())) {
/*     */                 
/* 103 */                 DbInit.LOG.info("Metadata table " + DbInit.this.metaDataTable + " already initialized with (" + DbInit.this.initVersion + "," + DbInit.this.initDescription + "). Skipping.");
/*     */                 
/* 105 */                 return null;
/*     */               } 
/* 107 */               throw new FlywayException("Unable to init metadata table " + DbInit.this.metaDataTable + " with (" + DbInit.this.initVersion + "," + DbInit.this.initDescription + ") as it has already been initialized with (" + initMarker.getVersion() + "," + initMarker.getDescription() + ")");
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 112 */             if (DbInit.this.metaDataTable.hasSchemasMarker() && DbInit.this.initVersion.equals(MigrationVersion.fromVersion("0"))) {
/* 113 */               throw new FlywayException("Unable to init metadata table " + DbInit.this.metaDataTable + " with version 0 as this version was used for schema creation");
/*     */             }
/* 115 */             DbInit.this.metaDataTable.addInitMarker(DbInit.this.initVersion, DbInit.this.initDescription);
/*     */             
/* 117 */             return null;
/*     */           }
/*     */         });
/*     */     
/* 121 */     LOG.info("Schema initialized with version: " + this.initVersion);
/*     */     
/* 123 */     for (FlywayCallback callback : this.callbacks) {
/* 124 */       (new TransactionTemplate(this.connection)).execute(new TransactionCallback<Object>()
/*     */           {
/*     */             public Object doInTransaction() throws SQLException {
/* 127 */               callback.afterInit(DbInit.this.connection);
/* 128 */               return null;
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\command\DbInit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */