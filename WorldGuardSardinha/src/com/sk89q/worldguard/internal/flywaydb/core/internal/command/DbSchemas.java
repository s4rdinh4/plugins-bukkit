/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.command;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.metadatatable.MetaDataTable;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.jdbc.TransactionCallback;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.jdbc.TransactionTemplate;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.Log;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.LogFactory;
/*    */ import java.sql.Connection;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DbSchemas
/*    */ {
/* 31 */   private static final Log LOG = LogFactory.getLog(DbSchemas.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final Connection connection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final Schema[] schemas;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final MetaDataTable metaDataTable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DbSchemas(Connection connection, Schema[] schemas, MetaDataTable metaDataTable) {
/* 56 */     this.connection = connection;
/* 57 */     this.schemas = schemas;
/* 58 */     this.metaDataTable = metaDataTable;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void create() {
/* 65 */     (new TransactionTemplate(this.connection)).execute(new TransactionCallback<Void>() {
/*    */           public Void doInTransaction() {
/* 67 */             for (Schema schema : DbSchemas.this.schemas) {
/* 68 */               if (schema.exists()) {
/* 69 */                 DbSchemas.LOG.debug("Schema " + schema + " already exists. Skipping schema creation.");
/* 70 */                 return null;
/*    */               } 
/*    */             } 
/*    */             
/* 74 */             for (Schema schema : DbSchemas.this.schemas) {
/* 75 */               DbSchemas.LOG.info("Creating schema " + schema + " ...");
/* 76 */               schema.create();
/*    */             } 
/*    */             
/* 79 */             DbSchemas.this.metaDataTable.addSchemasMarker(DbSchemas.this.schemas);
/*    */             
/* 81 */             return null;
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\command\DbSchemas.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */