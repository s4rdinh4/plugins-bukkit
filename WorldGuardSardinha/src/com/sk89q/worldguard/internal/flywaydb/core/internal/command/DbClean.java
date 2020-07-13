/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.command;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.callback.FlywayCallback;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;
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
/*     */ public class DbClean
/*     */ {
/*  36 */   private static final Log LOG = LogFactory.getLog(DbClean.class);
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
/*     */   private final Schema[] schemas;
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
/*     */   public DbClean(Connection connection, MetaDataTable metaDataTable, Schema[] schemas, FlywayCallback[] callbacks) {
/*  68 */     this.connection = connection;
/*  69 */     this.metaDataTable = metaDataTable;
/*  70 */     this.schemas = schemas;
/*  71 */     this.callbacks = callbacks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clean() throws FlywayException {
/*  80 */     for (FlywayCallback callback : this.callbacks) {
/*  81 */       (new TransactionTemplate(this.connection)).execute(new TransactionCallback<Object>()
/*     */           {
/*     */             public Object doInTransaction() throws SQLException {
/*  84 */               callback.beforeClean(DbClean.this.connection);
/*  85 */               return null;
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/*  90 */     boolean dropSchemas = false;
/*     */     try {
/*  92 */       dropSchemas = this.metaDataTable.hasSchemasMarker();
/*  93 */     } catch (Exception e) {
/*  94 */       LOG.error("Error while checking whether the schemas should be dropped", e);
/*     */     } 
/*     */     
/*  97 */     for (Schema schema : this.schemas) {
/*  98 */       if (dropSchemas) {
/*  99 */         dropSchema(schema);
/*     */       } else {
/* 101 */         cleanSchema(schema);
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     for (FlywayCallback callback : this.callbacks) {
/* 106 */       (new TransactionTemplate(this.connection)).execute(new TransactionCallback<Object>()
/*     */           {
/*     */             public Object doInTransaction() throws SQLException {
/* 109 */               callback.afterClean(DbClean.this.connection);
/* 110 */               return null;
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void dropSchema(final Schema schema) {
/* 123 */     LOG.debug("Dropping schema " + schema + " ...");
/* 124 */     StopWatch stopWatch = new StopWatch();
/* 125 */     stopWatch.start();
/* 126 */     (new TransactionTemplate(this.connection)).execute(new TransactionCallback<Void>() {
/*     */           public Void doInTransaction() {
/* 128 */             schema.drop();
/* 129 */             return null;
/*     */           }
/*     */         });
/* 132 */     stopWatch.stop();
/* 133 */     LOG.info(String.format("Dropped schema %s (execution time %s)", new Object[] { schema, TimeFormat.format(stopWatch.getTotalTimeMillis()) }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void cleanSchema(final Schema schema) {
/* 144 */     LOG.debug("Cleaning schema " + schema + " ...");
/* 145 */     StopWatch stopWatch = new StopWatch();
/* 146 */     stopWatch.start();
/* 147 */     (new TransactionTemplate(this.connection)).execute(new TransactionCallback<Void>() {
/*     */           public Void doInTransaction() {
/* 149 */             schema.clean();
/* 150 */             return null;
/*     */           }
/*     */         });
/* 153 */     stopWatch.stop();
/* 154 */     LOG.info(String.format("Cleaned schema %s (execution time %s)", new Object[] { schema, TimeFormat.format(stopWatch.getTotalTimeMillis()) }));
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\command\DbClean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */