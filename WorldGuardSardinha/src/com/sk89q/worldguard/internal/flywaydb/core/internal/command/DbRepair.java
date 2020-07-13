/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.command;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationInfo;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationVersion;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.callback.FlywayCallback;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.resolver.MigrationResolver;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.resolver.ResolvedMigration;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.info.MigrationInfoImpl;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.info.MigrationInfoServiceImpl;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.metadatatable.AppliedMigration;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.metadatatable.MetaDataTable;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.ObjectUtils;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.jdbc.TransactionCallback;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.jdbc.TransactionTemplate;
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
/*     */ public class DbRepair
/*     */ {
/*     */   private final Connection connection;
/*     */   private final MigrationInfoServiceImpl migrationInfoService;
/*     */   private final MetaDataTable metaDataTable;
/*     */   private final FlywayCallback[] callbacks;
/*     */   
/*     */   public DbRepair(Connection connection, MigrationResolver migrationResolver, MetaDataTable metaDataTable, FlywayCallback[] callbacks) {
/*  69 */     this.connection = connection;
/*  70 */     this.migrationInfoService = new MigrationInfoServiceImpl(migrationResolver, metaDataTable, MigrationVersion.LATEST, true, true);
/*  71 */     this.metaDataTable = metaDataTable;
/*  72 */     this.callbacks = callbacks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void repair() {
/*  79 */     for (FlywayCallback callback : this.callbacks) {
/*  80 */       (new TransactionTemplate(this.connection)).execute(new TransactionCallback<Object>()
/*     */           {
/*     */             public Object doInTransaction() throws SQLException {
/*  83 */               callback.beforeRepair(DbRepair.this.connection);
/*  84 */               return null;
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/*  89 */     (new TransactionTemplate(this.connection)).execute(new TransactionCallback<Void>() {
/*     */           public Void doInTransaction() {
/*  91 */             DbRepair.this.metaDataTable.removeFailedMigrations();
/*     */             
/*  93 */             DbRepair.this.migrationInfoService.refresh();
/*  94 */             for (MigrationInfo migrationInfo : DbRepair.this.migrationInfoService.all()) {
/*  95 */               MigrationInfoImpl migrationInfoImpl = (MigrationInfoImpl)migrationInfo;
/*     */               
/*  97 */               ResolvedMigration resolved = migrationInfoImpl.getResolvedMigration();
/*  98 */               AppliedMigration applied = migrationInfoImpl.getAppliedMigration();
/*  99 */               if (resolved != null && applied != null && 
/* 100 */                 !ObjectUtils.nullSafeEquals(resolved.getChecksum(), applied.getChecksum())) {
/* 101 */                 DbRepair.this.metaDataTable.updateChecksum(migrationInfoImpl.getVersion(), resolved.getChecksum());
/*     */               }
/*     */             } 
/*     */ 
/*     */             
/* 106 */             return null;
/*     */           }
/*     */         });
/*     */     
/* 110 */     for (FlywayCallback callback : this.callbacks) {
/* 111 */       (new TransactionTemplate(this.connection)).execute(new TransactionCallback<Object>()
/*     */           {
/*     */             public Object doInTransaction() throws SQLException {
/* 114 */               callback.afterRepair(DbRepair.this.connection);
/* 115 */               return null;
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\command\DbRepair.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */