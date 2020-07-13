/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.db2;
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
/*    */ public class DB2DbSupport
/*    */   extends DbSupport
/*    */ {
/*    */   public DB2DbSupport(Connection connection) {
/* 37 */     super(new JdbcTemplate(connection, 12));
/*    */   }
/*    */   
/*    */   public SqlStatementBuilder createSqlStatementBuilder() {
/* 41 */     return new DB2SqlStatementBuilder();
/*    */   }
/*    */   
/*    */   public String getDbName() {
/* 45 */     return "db2";
/*    */   }
/*    */ 
/*    */   
/*    */   protected String doGetCurrentSchema() throws SQLException {
/* 50 */     return this.jdbcTemplate.queryForString("select current_schema from sysibm.sysdummy1", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doSetCurrentSchema(Schema schema) throws SQLException {
/* 55 */     this.jdbcTemplate.execute("SET SCHEMA " + schema, new Object[0]);
/*    */   }
/*    */   
/*    */   public String getCurrentUserFunction() {
/* 59 */     return "CURRENT_USER";
/*    */   }
/*    */   
/*    */   public boolean supportsDdlTransactions() {
/* 63 */     return true;
/*    */   }
/*    */   
/*    */   public String getBooleanTrue() {
/* 67 */     return "1";
/*    */   }
/*    */   
/*    */   public String getBooleanFalse() {
/* 71 */     return "0";
/*    */   }
/*    */ 
/*    */   
/*    */   public String doQuote(String identifier) {
/* 76 */     return "\"" + identifier + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   public Schema getSchema(String name) {
/* 81 */     return new DB2Schema(this.jdbcTemplate, this, name);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean catalogIsSchema() {
/* 86 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\db2\DB2DbSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */