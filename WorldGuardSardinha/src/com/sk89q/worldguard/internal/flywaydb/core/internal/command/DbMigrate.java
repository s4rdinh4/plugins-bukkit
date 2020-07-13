/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.command;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationInfo;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationState;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationVersion;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.callback.FlywayCallback;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.resolver.MigrationExecutor;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.resolver.MigrationResolver;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.DbSupport;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.info.MigrationInfoImpl;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.info.MigrationInfoServiceImpl;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.metadatatable.AppliedMigration;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.metadatatable.MetaDataTable;
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
/*     */ 
/*     */ public class DbMigrate
/*     */ {
/*  48 */   private static final Log LOG = LogFactory.getLog(DbMigrate.class);
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
/*     */   private final DbSupport dbSupport;
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
/*     */   private final Schema schema;
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
/*     */   private final Connection connectionMetaDataTable;
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
/*     */   private final boolean ignoreFailedFutureMigration;
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
/*     */   private final FlywayCallback[] callbacks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DbMigrate(Connection connectionMetaDataTable, Connection connectionUserObjects, DbSupport dbSupport, MetaDataTable metaDataTable, Schema schema, MigrationResolver migrationResolver, MigrationVersion target, boolean ignoreFailedFutureMigration, boolean outOfOrder, FlywayCallback[] callbacks) {
/* 121 */     this.connectionMetaDataTable = connectionMetaDataTable;
/* 122 */     this.connectionUserObjects = connectionUserObjects;
/* 123 */     this.dbSupport = dbSupport;
/* 124 */     this.metaDataTable = metaDataTable;
/* 125 */     this.schema = schema;
/* 126 */     this.migrationResolver = migrationResolver;
/* 127 */     this.target = target;
/* 128 */     this.ignoreFailedFutureMigration = ignoreFailedFutureMigration;
/* 129 */     this.outOfOrder = outOfOrder;
/* 130 */     this.callbacks = callbacks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int migrate() throws FlywayException {
/* 140 */     for (FlywayCallback callback : this.callbacks) {
/* 141 */       (new TransactionTemplate(this.connectionUserObjects)).execute(new TransactionCallback<Object>()
/*     */           {
/*     */             public Object doInTransaction() throws SQLException {
/* 144 */               callback.beforeMigrate(DbMigrate.this.connectionUserObjects);
/* 145 */               return null;
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 150 */     StopWatch stopWatch = new StopWatch();
/* 151 */     stopWatch.start();
/*     */     
/* 153 */     int migrationSuccessCount = 0;
/*     */     while (true) {
/* 155 */       final boolean firstRun = (migrationSuccessCount == 0);
/* 156 */       MigrationVersion result = (MigrationVersion)(new TransactionTemplate(this.connectionMetaDataTable, false)).execute(new TransactionCallback<MigrationVersion>() {
/*     */             public MigrationVersion doInTransaction() {
/* 158 */               DbMigrate.this.metaDataTable.lock();
/*     */               
/* 160 */               MigrationInfoServiceImpl infoService = new MigrationInfoServiceImpl(DbMigrate.this.migrationResolver, DbMigrate.this.metaDataTable, DbMigrate.this.target, DbMigrate.this.outOfOrder, true);
/*     */               
/* 162 */               infoService.refresh();
/*     */               
/* 164 */               MigrationVersion currentSchemaVersion = MigrationVersion.EMPTY;
/* 165 */               if (infoService.current() != null) {
/* 166 */                 currentSchemaVersion = infoService.current().getVersion();
/*     */               }
/* 168 */               if (firstRun) {
/* 169 */                 DbMigrate.LOG.info("Current version of schema " + DbMigrate.this.schema + ": " + currentSchemaVersion);
/*     */                 
/* 171 */                 if (DbMigrate.this.outOfOrder) {
/* 172 */                   DbMigrate.LOG.warn("outOfOrder mode is active. Migration of schema " + DbMigrate.this.schema + " may not be reproducible.");
/*     */                 }
/*     */               } 
/*     */               
/* 176 */               MigrationInfo[] future = infoService.future();
/* 177 */               if (future.length > 0) {
/* 178 */                 MigrationInfo[] resolved = infoService.resolved();
/* 179 */                 if (resolved.length == 0) {
/* 180 */                   DbMigrate.LOG.warn("Schema " + DbMigrate.this.schema + " has version " + currentSchemaVersion + ", but no migration could be resolved in the configured locations !");
/*     */                 } else {
/*     */                   
/* 183 */                   DbMigrate.LOG.warn("Schema " + DbMigrate.this.schema + " has a version (" + currentSchemaVersion + ") that is newer than the latest available migration (" + resolved[resolved.length - 1].getVersion() + ") !");
/*     */                 } 
/*     */               } 
/*     */ 
/*     */ 
/*     */               
/* 189 */               MigrationInfo[] failed = infoService.failed();
/* 190 */               if (failed.length > 0) {
/* 191 */                 if (failed.length == 1 && failed[0].getState() == MigrationState.FUTURE_FAILED && DbMigrate.this.ignoreFailedFutureMigration) {
/*     */ 
/*     */                   
/* 194 */                   DbMigrate.LOG.warn("Schema " + DbMigrate.this.schema + " contains a failed future migration to version " + failed[0].getVersion() + " !");
/*     */                 } else {
/* 196 */                   throw new FlywayException("Schema " + DbMigrate.this.schema + " contains a failed migration to version " + failed[0].getVersion() + " !");
/*     */                 } 
/*     */               }
/*     */               
/* 200 */               MigrationInfoImpl[] pendingMigrations = infoService.pending();
/*     */               
/* 202 */               if (pendingMigrations.length == 0) {
/* 203 */                 return null;
/*     */               }
/*     */               
/* 206 */               boolean isOutOfOrder = (pendingMigrations[0].getVersion().compareTo(currentSchemaVersion) < 0);
/* 207 */               return DbMigrate.this.applyMigration(pendingMigrations[0], isOutOfOrder);
/*     */             }
/*     */           });
/* 210 */       if (result == null) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 215 */       migrationSuccessCount++;
/*     */     } 
/*     */     
/* 218 */     stopWatch.stop();
/*     */     
/* 220 */     logSummary(migrationSuccessCount, stopWatch.getTotalTimeMillis());
/*     */     
/* 222 */     for (FlywayCallback callback : this.callbacks) {
/* 223 */       (new TransactionTemplate(this.connectionUserObjects)).execute(new TransactionCallback<Object>()
/*     */           {
/*     */             public Object doInTransaction() throws SQLException {
/* 226 */               callback.afterMigrate(DbMigrate.this.connectionUserObjects);
/* 227 */               return null;
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 232 */     return migrationSuccessCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void logSummary(int migrationSuccessCount, long executionTime) {
/* 242 */     if (migrationSuccessCount == 0) {
/* 243 */       LOG.info("Schema " + this.schema + " is up to date. No migration necessary.");
/*     */       
/*     */       return;
/*     */     } 
/* 247 */     if (migrationSuccessCount == 1) {
/* 248 */       LOG.info("Successfully applied 1 migration to schema " + this.schema + " (execution time " + TimeFormat.format(executionTime) + ").");
/*     */     } else {
/* 250 */       LOG.info("Successfully applied " + migrationSuccessCount + " migrations to schema " + this.schema + " (execution time " + TimeFormat.format(executionTime) + ").");
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
/*     */   private MigrationVersion applyMigration(final MigrationInfoImpl migration, boolean isOutOfOrder) {
/* 262 */     MigrationVersion version = migration.getVersion();
/* 263 */     if (isOutOfOrder) {
/* 264 */       LOG.info("Migrating schema " + this.schema + " to version " + version + " (out of order)");
/*     */     } else {
/* 266 */       LOG.info("Migrating schema " + this.schema + " to version " + version);
/*     */     } 
/*     */     
/* 269 */     StopWatch stopWatch = new StopWatch();
/* 270 */     stopWatch.start();
/*     */     
/*     */     try {
/* 273 */       for (FlywayCallback callback : this.callbacks) {
/* 274 */         (new TransactionTemplate(this.connectionUserObjects)).execute(new TransactionCallback<Object>()
/*     */             {
/*     */               public Object doInTransaction() throws SQLException {
/* 277 */                 callback.beforeEachMigrate(DbMigrate.this.connectionUserObjects, (MigrationInfo)migration);
/* 278 */                 return null;
/*     */               }
/*     */             });
/*     */       } 
/*     */       
/* 283 */       final MigrationExecutor migrationExecutor = migration.getResolvedMigration().getExecutor();
/* 284 */       if (migrationExecutor.executeInTransaction()) {
/* 285 */         (new TransactionTemplate(this.connectionUserObjects)).execute(new TransactionCallback<Void>() {
/*     */               public Void doInTransaction() throws SQLException {
/* 287 */                 migrationExecutor.execute(DbMigrate.this.connectionUserObjects);
/* 288 */                 return null;
/*     */               }
/*     */             });
/*     */       } else {
/*     */         try {
/* 293 */           migrationExecutor.execute(this.connectionUserObjects);
/* 294 */         } catch (SQLException e) {
/* 295 */           throw new FlywayException("Unable to apply migration", e);
/*     */         } 
/*     */       } 
/* 298 */       LOG.debug("Successfully completed and committed migration of schema " + this.schema + " to version " + version);
/*     */       
/* 300 */       for (FlywayCallback callback : this.callbacks) {
/* 301 */         (new TransactionTemplate(this.connectionUserObjects)).execute(new TransactionCallback<Object>()
/*     */             {
/*     */               public Object doInTransaction() throws SQLException {
/* 304 */                 callback.afterEachMigrate(DbMigrate.this.connectionUserObjects, (MigrationInfo)migration);
/* 305 */                 return null;
/*     */               }
/*     */             });
/*     */       } 
/* 309 */     } catch (FlywayException e) {
/* 310 */       String failedMsg = "Migration of schema " + this.schema + " to version " + version + " failed!";
/* 311 */       if (this.dbSupport.supportsDdlTransactions()) {
/* 312 */         LOG.error(failedMsg + " Changes successfully rolled back.");
/*     */       } else {
/* 314 */         LOG.error(failedMsg + " Please restore backups and roll back database and code!");
/*     */         
/* 316 */         stopWatch.stop();
/* 317 */         int i = (int)stopWatch.getTotalTimeMillis();
/* 318 */         AppliedMigration appliedMigration1 = new AppliedMigration(version, migration.getDescription(), migration.getType(), migration.getScript(), migration.getChecksum(), i, false);
/*     */         
/* 320 */         this.metaDataTable.addAppliedMigration(appliedMigration1);
/*     */       } 
/* 322 */       throw e;
/*     */     } 
/*     */     
/* 325 */     stopWatch.stop();
/* 326 */     int executionTime = (int)stopWatch.getTotalTimeMillis();
/*     */     
/* 328 */     AppliedMigration appliedMigration = new AppliedMigration(version, migration.getDescription(), migration.getType(), migration.getScript(), migration.getChecksum(), executionTime, true);
/*     */     
/* 330 */     this.metaDataTable.addAppliedMigration(appliedMigration);
/*     */     
/* 332 */     return version;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\command\DbMigrate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */