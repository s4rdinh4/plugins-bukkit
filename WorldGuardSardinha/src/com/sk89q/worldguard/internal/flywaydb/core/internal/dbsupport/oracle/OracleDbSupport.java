/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.oracle;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.DbSupport;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.JdbcTemplate;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.SqlStatementBuilder;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OracleDbSupport
/*    */   extends DbSupport
/*    */ {
/*    */   public OracleDbSupport(Connection connection) {
/* 37 */     super(new JdbcTemplate(connection, 12));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDbName() {
/* 42 */     return "oracle";
/*    */   }
/*    */   
/*    */   public String getCurrentUserFunction() {
/* 46 */     return "USER";
/*    */   }
/*    */ 
/*    */   
/*    */   protected String doGetCurrentSchema() throws SQLException {
/* 51 */     return this.jdbcTemplate.queryForString("SELECT USER FROM dual", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doSetCurrentSchema(Schema schema) throws SQLException {
/* 56 */     this.jdbcTemplate.execute("ALTER SESSION SET CURRENT_SCHEMA=" + schema, new Object[0]);
/*    */   }
/*    */   
/*    */   public boolean supportsDdlTransactions() {
/* 60 */     return false;
/*    */   }
/*    */   
/*    */   public String getBooleanTrue() {
/* 64 */     return "1";
/*    */   }
/*    */   
/*    */   public String getBooleanFalse() {
/* 68 */     return "0";
/*    */   }
/*    */   
/*    */   public SqlStatementBuilder createSqlStatementBuilder() {
/* 72 */     return new OracleSqlStatementBuilder();
/*    */   }
/*    */ 
/*    */   
/*    */   public String doQuote(String identifier) {
/* 77 */     return "\"" + identifier + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   public Schema getSchema(String name) {
/* 82 */     return new OracleSchema(this.jdbcTemplate, this, name);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean catalogIsSchema() {
/* 87 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\oracle\OracleDbSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */