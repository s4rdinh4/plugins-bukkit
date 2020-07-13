/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.sqlite;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.DbSupport;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.JdbcTemplate;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Table;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.Log;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.LogFactory;
/*    */ import java.sql.SQLException;
/*    */ import java.util.List;
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
/*    */ 
/*    */ public class SQLiteSchema
/*    */   extends Schema
/*    */ {
/* 33 */   private static final Log LOG = LogFactory.getLog(SQLiteSchema.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SQLiteSchema(JdbcTemplate jdbcTemplate, DbSupport dbSupport, String name) {
/* 43 */     super(jdbcTemplate, dbSupport, name);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean doExists() throws SQLException {
/*    */     try {
/* 49 */       doAllTables();
/* 50 */       return true;
/* 51 */     } catch (SQLException e) {
/* 52 */       return false;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean doEmpty() throws SQLException {
/* 58 */     Table[] tables = allTables();
/* 59 */     return (tables.length == 0 || (tables.length == 1 && "android_metadata".equals(tables[0].getName())));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doCreate() throws SQLException {
/* 64 */     LOG.info("SQLite does not support creating schemas. Schema not created: " + this.name);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doDrop() throws SQLException {
/* 69 */     LOG.info("SQLite does not support dropping schemas. Schema not dropped: " + this.name);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doClean() throws SQLException {
/* 74 */     List<String> viewNames = this.jdbcTemplate.queryForStringList("SELECT tbl_name FROM " + this.dbSupport.quote(new String[] { this.name }, ) + ".sqlite_master WHERE type='view'", new String[0]);
/* 75 */     for (String viewName : viewNames) {
/* 76 */       this.jdbcTemplate.executeStatement("DROP VIEW " + this.dbSupport.quote(new String[] { this.name, viewName }));
/*    */     } 
/*    */     
/* 79 */     for (Table table : allTables()) {
/* 80 */       table.drop();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected Table[] doAllTables() throws SQLException {
/* 86 */     List<String> tableNames = this.jdbcTemplate.queryForStringList("SELECT tbl_name FROM " + this.dbSupport.quote(new String[] { this.name }, ) + ".sqlite_master WHERE type='table'", new String[0]);
/*    */     
/* 88 */     Table[] tables = new Table[tableNames.size()];
/* 89 */     for (int i = 0; i < tableNames.size(); i++) {
/* 90 */       tables[i] = new SQLiteTable(this.jdbcTemplate, this.dbSupport, this, tableNames.get(i));
/*    */     }
/* 92 */     return tables;
/*    */   }
/*    */ 
/*    */   
/*    */   public Table getTable(String tableName) {
/* 97 */     return new SQLiteTable(this.jdbcTemplate, this.dbSupport, this, tableName);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\sqlite\SQLiteSchema.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */