/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.postgresql;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.DbSupport;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.JdbcTemplate;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.SqlStatementBuilder;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.StringUtils;
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
/*    */ public class PostgreSQLDbSupport
/*    */   extends DbSupport
/*    */ {
/*    */   public PostgreSQLDbSupport(Connection connection) {
/* 38 */     super(new JdbcTemplate(connection, 0));
/*    */   }
/*    */   
/*    */   public String getDbName() {
/* 42 */     return "postgresql";
/*    */   }
/*    */   
/*    */   public String getCurrentUserFunction() {
/* 46 */     return "current_user";
/*    */   }
/*    */ 
/*    */   
/*    */   protected String doGetCurrentSchema() throws SQLException {
/* 51 */     return this.jdbcTemplate.queryForString("SELECT current_schema()", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doSetCurrentSchema(Schema schema) throws SQLException {
/* 56 */     if (schema == null) {
/* 57 */       this.jdbcTemplate.execute("SELECT set_config('search_path', '', false)", new Object[0]);
/*    */       
/*    */       return;
/*    */     } 
/* 61 */     String searchPath = this.jdbcTemplate.queryForString("SHOW search_path", new String[0]);
/* 62 */     if (StringUtils.hasText(searchPath)) {
/* 63 */       this.jdbcTemplate.execute("SET search_path = " + schema + "," + searchPath, new Object[0]);
/*    */     } else {
/* 65 */       this.jdbcTemplate.execute("SET search_path = " + schema, new Object[0]);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean supportsDdlTransactions() {
/* 70 */     return true;
/*    */   }
/*    */   
/*    */   public String getBooleanTrue() {
/* 74 */     return "TRUE";
/*    */   }
/*    */   
/*    */   public String getBooleanFalse() {
/* 78 */     return "FALSE";
/*    */   }
/*    */   
/*    */   public SqlStatementBuilder createSqlStatementBuilder() {
/* 82 */     return new PostgreSQLSqlStatementBuilder();
/*    */   }
/*    */ 
/*    */   
/*    */   public String doQuote(String identifier) {
/* 87 */     return "\"" + StringUtils.replaceAll(identifier, "\"", "\"\"") + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   public Schema getSchema(String name) {
/* 92 */     return new PostgreSQLSchema(this.jdbcTemplate, this, name);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean catalogIsSchema() {
/* 97 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\postgresql\PostgreSQLDbSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */