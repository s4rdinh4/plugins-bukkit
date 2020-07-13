/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.mysql;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.DbSupport;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.JdbcTemplate;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.SqlStatementBuilder;
/*    */ import java.sql.Connection;
/*    */ import java.sql.SQLException;
/*    */ import java.util.UUID;
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
/*    */ public class MySQLDbSupport
/*    */   extends DbSupport
/*    */ {
/*    */   public MySQLDbSupport(Connection connection) {
/* 38 */     super(new JdbcTemplate(connection, 12));
/*    */   }
/*    */   
/*    */   public String getDbName() {
/* 42 */     return "mysql";
/*    */   }
/*    */   
/*    */   public String getCurrentUserFunction() {
/* 46 */     return "SUBSTRING_INDEX(USER(),'@',1)";
/*    */   }
/*    */ 
/*    */   
/*    */   protected String doGetCurrentSchema() throws SQLException {
/* 51 */     return this.jdbcTemplate.getConnection().getCatalog();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doSetCurrentSchema(Schema schema) throws SQLException {
/* 56 */     if ("".equals(schema.getName())) {
/*    */       
/* 58 */       String newDb = quote(new String[] { UUID.randomUUID().toString() });
/* 59 */       this.jdbcTemplate.execute("CREATE SCHEMA " + newDb, new Object[0]);
/* 60 */       this.jdbcTemplate.execute("USE " + newDb, new Object[0]);
/* 61 */       this.jdbcTemplate.execute("DROP SCHEMA " + newDb, new Object[0]);
/*    */     } else {
/* 63 */       this.jdbcTemplate.execute("USE " + schema, new Object[0]);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean supportsDdlTransactions() {
/* 68 */     return false;
/*    */   }
/*    */   
/*    */   public String getBooleanTrue() {
/* 72 */     return "1";
/*    */   }
/*    */   
/*    */   public String getBooleanFalse() {
/* 76 */     return "0";
/*    */   }
/*    */   
/*    */   public SqlStatementBuilder createSqlStatementBuilder() {
/* 80 */     return new MySQLSqlStatementBuilder();
/*    */   }
/*    */ 
/*    */   
/*    */   public String doQuote(String identifier) {
/* 85 */     return "`" + identifier + "`";
/*    */   }
/*    */ 
/*    */   
/*    */   public Schema getSchema(String name) {
/* 90 */     return new MySQLSchema(this.jdbcTemplate, this, name);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean catalogIsSchema() {
/* 95 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\mysql\MySQLDbSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */