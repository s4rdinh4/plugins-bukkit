/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.hsql;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.DbSupport;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.JdbcTemplate;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.SqlStatementBuilder;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.jdbc.JdbcUtils;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
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
/*     */ 
/*     */ 
/*     */ public class HsqlDbSupport
/*     */   extends DbSupport
/*     */ {
/*     */   public HsqlDbSupport(Connection connection) {
/*  39 */     super(new JdbcTemplate(connection, 12));
/*     */   }
/*     */   
/*     */   public String getDbName() {
/*  43 */     return "hsql";
/*     */   }
/*     */   
/*     */   public String getCurrentUserFunction() {
/*  47 */     return "USER()";
/*     */   }
/*     */ 
/*     */   
/*     */   protected String doGetCurrentSchema() throws SQLException {
/*  52 */     ResultSet resultSet = null;
/*  53 */     String schema = null;
/*     */     
/*     */     try {
/*  56 */       resultSet = this.jdbcTemplate.getMetaData().getSchemas();
/*  57 */       while (resultSet.next()) {
/*  58 */         if (resultSet.getBoolean("IS_DEFAULT")) {
/*  59 */           schema = resultSet.getString("TABLE_SCHEM");
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } finally {
/*  64 */       JdbcUtils.closeResultSet(resultSet);
/*     */     } 
/*     */     
/*  67 */     return schema;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doSetCurrentSchema(Schema schema) throws SQLException {
/*  72 */     this.jdbcTemplate.execute("SET SCHEMA " + schema, new Object[0]);
/*     */   }
/*     */   
/*     */   public boolean supportsDdlTransactions() {
/*  76 */     return false;
/*     */   }
/*     */   
/*     */   public String getBooleanTrue() {
/*  80 */     return "1";
/*     */   }
/*     */   
/*     */   public String getBooleanFalse() {
/*  84 */     return "0";
/*     */   }
/*     */   
/*     */   public SqlStatementBuilder createSqlStatementBuilder() {
/*  88 */     return new HsqlSqlStatementBuilder();
/*     */   }
/*     */ 
/*     */   
/*     */   public String doQuote(String identifier) {
/*  93 */     return "\"" + identifier + "\"";
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema getSchema(String name) {
/*  98 */     return new HsqlSchema(this.jdbcTemplate, this, name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean catalogIsSchema() {
/* 103 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\hsql\HsqlDbSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */