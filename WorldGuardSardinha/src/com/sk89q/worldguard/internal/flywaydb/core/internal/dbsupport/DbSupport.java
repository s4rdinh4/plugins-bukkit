/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DbSupport
/*     */ {
/*     */   protected final JdbcTemplate jdbcTemplate;
/*     */   
/*     */   public DbSupport(JdbcTemplate jdbcTemplate) {
/*  37 */     this.jdbcTemplate = jdbcTemplate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdbcTemplate getJdbcTemplate() {
/*  44 */     return this.jdbcTemplate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Schema getSchema(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract SqlStatementBuilder createSqlStatementBuilder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getDbName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Schema getCurrentSchema() {
/*     */     try {
/*  74 */       String schemaName = doGetCurrentSchema();
/*  75 */       if (schemaName == null) {
/*  76 */         return null;
/*     */       }
/*     */       
/*  79 */       return getSchema(schemaName);
/*  80 */     } catch (SQLException e) {
/*  81 */       throw new FlywayException("Unable to retrieve the current schema for the connection", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract String doGetCurrentSchema() throws SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentSchema(Schema schema) {
/*     */     try {
/* 100 */       doSetCurrentSchema(schema);
/* 101 */     } catch (SQLException e) {
/* 102 */       throw new FlywayException("Error setting current schema to " + schema, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void doSetCurrentSchema(Schema paramSchema) throws SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getCurrentUserFunction();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean supportsDdlTransactions();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getBooleanTrue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getBooleanFalse();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String quote(String... identifiers) {
/* 143 */     String result = "";
/*     */     
/* 145 */     boolean first = true;
/* 146 */     for (String identifier : identifiers) {
/* 147 */       if (!first) {
/* 148 */         result = result + ".";
/*     */       }
/* 150 */       first = false;
/* 151 */       result = result + doQuote(identifier);
/*     */     } 
/*     */     
/* 154 */     return result;
/*     */   }
/*     */   
/*     */   protected abstract String doQuote(String paramString);
/*     */   
/*     */   public abstract boolean catalogIsSchema();
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\DbSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */