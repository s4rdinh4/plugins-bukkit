/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.sqlserver;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.DbSupport;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.JdbcTemplate;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.SqlStatementBuilder;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.StringUtils;
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
/*     */ public class SQLServerDbSupport
/*     */   extends DbSupport
/*     */ {
/*  34 */   private static final Log LOG = LogFactory.getLog(SQLServerDbSupport.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SQLServerDbSupport(Connection connection) {
/*  42 */     super(new JdbcTemplate(connection, 12));
/*     */   }
/*     */   
/*     */   public String getDbName() {
/*  46 */     return "sqlserver";
/*     */   }
/*     */   
/*     */   public String getCurrentUserFunction() {
/*  50 */     return "SUSER_SNAME()";
/*     */   }
/*     */ 
/*     */   
/*     */   protected String doGetCurrentSchema() throws SQLException {
/*  55 */     return this.jdbcTemplate.queryForString("SELECT SCHEMA_NAME()", new String[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doSetCurrentSchema(Schema schema) throws SQLException {
/*  60 */     LOG.info("SQLServer does not support setting the schema for the current session. Default schema NOT changed to " + schema);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supportsDdlTransactions() {
/*  66 */     return true;
/*     */   }
/*     */   
/*     */   public String getBooleanTrue() {
/*  70 */     return "1";
/*     */   }
/*     */   
/*     */   public String getBooleanFalse() {
/*  74 */     return "0";
/*     */   }
/*     */   
/*     */   public SqlStatementBuilder createSqlStatementBuilder() {
/*  78 */     return new SQLServerSqlStatementBuilder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String escapeIdentifier(String identifier) {
/*  88 */     return StringUtils.replaceAll(identifier, "]", "]]");
/*     */   }
/*     */ 
/*     */   
/*     */   public String doQuote(String identifier) {
/*  93 */     return "[" + escapeIdentifier(identifier) + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema getSchema(String name) {
/*  98 */     return new SQLServerSchema(this.jdbcTemplate, this, name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean catalogIsSchema() {
/* 103 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\sqlserver\SQLServerDbSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */