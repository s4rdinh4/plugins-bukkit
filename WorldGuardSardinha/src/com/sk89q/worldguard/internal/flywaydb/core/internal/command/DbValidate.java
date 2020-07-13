/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.command;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationVersion;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.callback.FlywayCallback;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.resolver.MigrationResolver;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.info.MigrationInfoServiceImpl;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.metadatatable.MetaDataTable;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.Pair;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.StopWatch;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.TimeFormat;
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
/*     */ 
/*     */ 
/*     */ public class DbValidate
/*     */ {
/*  40 */   private static final Log LOG = LogFactory.getLog(DbValidate.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final MigrationVersion target;
/*     */ 
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
/*     */   
/*     */   private final MigrationResolver migrationResolver;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Connection connectionMetaDataTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Connection connectionUserObjects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean outOfOrder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean pending;
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
/*     */   public DbValidate(Connection connectionMetaDataTable, Connection connectionUserObjects, MetaDataTable metaDataTable, MigrationResolver migrationResolver, MigrationVersion target, boolean outOfOrder, boolean pending, FlywayCallback[] callbacks) {
/* 101 */     this.connectionMetaDataTable = connectionMetaDataTable;
/* 102 */     this.connectionUserObjects = connectionUserObjects;
/* 103 */     this.metaDataTable = metaDataTable;
/* 104 */     this.migrationResolver = migrationResolver;
/* 105 */     this.target = target;
/* 106 */     this.outOfOrder = outOfOrder;
/* 107 */     this.pending = pending;
/* 108 */     this.callbacks = callbacks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String validate() {
/* 117 */     for (FlywayCallback callback : this.callbacks) {
/* 118 */       (new TransactionTemplate(this.connectionUserObjects)).execute(new TransactionCallback<Object>()
/*     */           {
/*     */             public Object doInTransaction() throws SQLException {
/* 121 */               callback.beforeValidate(DbValidate.this.connectionUserObjects);
/* 122 */               return null;
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 127 */     LOG.debug("Validating migrations ...");
/* 128 */     StopWatch stopWatch = new StopWatch();
/* 129 */     stopWatch.start();
/*     */     
/* 131 */     Pair<Integer, String> result = (Pair<Integer, String>)(new TransactionTemplate(this.connectionMetaDataTable)).execute(new TransactionCallback<Pair<Integer, String>>() {
/*     */           public Pair<Integer, String> doInTransaction() {
/* 133 */             MigrationInfoServiceImpl migrationInfoService = new MigrationInfoServiceImpl(DbValidate.this.migrationResolver, DbValidate.this.metaDataTable, DbValidate.this.target, DbValidate.this.outOfOrder, DbValidate.this.pending);
/*     */ 
/*     */             
/* 136 */             migrationInfoService.refresh();
/*     */             
/* 138 */             int count = (migrationInfoService.all()).length;
/* 139 */             String validationError = migrationInfoService.validate();
/* 140 */             return Pair.of(Integer.valueOf(count), validationError);
/*     */           }
/*     */         });
/*     */     
/* 144 */     stopWatch.stop();
/*     */     
/* 146 */     int count = ((Integer)result.getLeft()).intValue();
/* 147 */     if (count == 1) {
/* 148 */       LOG.info(String.format("Validated 1 migration (execution time %s)", new Object[] { TimeFormat.format(stopWatch.getTotalTimeMillis()) }));
/*     */     } else {
/*     */       
/* 151 */       LOG.info(String.format("Validated %d migrations (execution time %s)", new Object[] { Integer.valueOf(count), TimeFormat.format(stopWatch.getTotalTimeMillis()) }));
/*     */     } 
/*     */ 
/*     */     
/* 155 */     for (FlywayCallback callback : this.callbacks) {
/* 156 */       (new TransactionTemplate(this.connectionUserObjects)).execute(new TransactionCallback<Object>()
/*     */           {
/*     */             public Object doInTransaction() throws SQLException {
/* 159 */               callback.afterValidate(DbValidate.this.connectionUserObjects);
/* 160 */               return null;
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 165 */     return (String)result.getRight();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\command\DbValidate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */