/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.sqlite;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.DbSupport;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.JdbcTemplate;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.SqlStatementBuilder;
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
/*    */ 
/*    */ public class SQLiteDbSupport
/*    */   extends DbSupport
/*    */ {
/* 33 */   private static final Log LOG = LogFactory.getLog(SQLiteDbSupport.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SQLiteDbSupport(Connection connection) {
/* 41 */     super(new JdbcTemplate(connection, 12));
/*    */   }
/*    */   
/*    */   public String getDbName() {
/* 45 */     return "sqlite";
/*    */   }
/*    */   
/*    */   public String getCurrentUserFunction() {
/* 49 */     return "''";
/*    */   }
/*    */   
/*    */   protected String doGetCurrentSchema() throws SQLException {
/* 53 */     return "main";
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doSetCurrentSchema(Schema schema) throws SQLException {
/* 58 */     LOG.info("SQLite does not support setting the schema. Default schema NOT changed to " + schema);
/*    */   }
/*    */   
/*    */   public boolean supportsDdlTransactions() {
/* 62 */     return true;
/*    */   }
/*    */   
/*    */   public String getBooleanTrue() {
/* 66 */     return "1";
/*    */   }
/*    */   
/*    */   public String getBooleanFalse() {
/* 70 */     return "0";
/*    */   }
/*    */   
/*    */   public SqlStatementBuilder createSqlStatementBuilder() {
/* 74 */     return new SQLiteSqlStatementBuilder();
/*    */   }
/*    */ 
/*    */   
/*    */   public String doQuote(String identifier) {
/* 79 */     return "\"" + identifier + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   public Schema getSchema(String name) {
/* 84 */     return new SQLiteSchema(this.jdbcTemplate, this, name);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean catalogIsSchema() {
/* 89 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\sqlite\SQLiteDbSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */