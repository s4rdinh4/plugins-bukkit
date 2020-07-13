/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.h2;
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
/*     */ public class H2DbSupport
/*     */   extends DbSupport
/*     */ {
/*     */   public H2DbSupport(Connection connection) {
/*  39 */     super(new JdbcTemplate(connection, 12));
/*     */   }
/*     */   
/*     */   public String getDbName() {
/*  43 */     return "h2";
/*     */   }
/*     */   
/*     */   public String getCurrentUserFunction() {
/*  47 */     return "USER()";
/*     */   }
/*     */   
/*     */   protected String doGetCurrentSchema() throws SQLException {
/*  51 */     ResultSet resultSet = null;
/*  52 */     String schema = null;
/*     */     try {
/*  54 */       resultSet = this.jdbcTemplate.getMetaData().getSchemas();
/*  55 */       while (resultSet.next()) {
/*  56 */         if (resultSet.getBoolean("IS_DEFAULT")) {
/*  57 */           schema = resultSet.getString("TABLE_SCHEM");
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } finally {
/*  62 */       JdbcUtils.closeResultSet(resultSet);
/*     */     } 
/*     */     
/*  65 */     return schema;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doSetCurrentSchema(Schema schema) throws SQLException {
/*  70 */     this.jdbcTemplate.execute("SET SCHEMA " + schema, new Object[0]);
/*     */   }
/*     */   
/*     */   public boolean supportsDdlTransactions() {
/*  74 */     return false;
/*     */   }
/*     */   
/*     */   public String getBooleanTrue() {
/*  78 */     return "1";
/*     */   }
/*     */   
/*     */   public String getBooleanFalse() {
/*  82 */     return "0";
/*     */   }
/*     */   
/*     */   public SqlStatementBuilder createSqlStatementBuilder() {
/*  86 */     return new H2SqlStatementBuilder();
/*     */   }
/*     */ 
/*     */   
/*     */   public String doQuote(String identifier) {
/*  91 */     return "\"" + identifier + "\"";
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema getSchema(String name) {
/*  96 */     return new H2Schema(this.jdbcTemplate, this, name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean catalogIsSchema() {
/* 101 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\h2\H2DbSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */