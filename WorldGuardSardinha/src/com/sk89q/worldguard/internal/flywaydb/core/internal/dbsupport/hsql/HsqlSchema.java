/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.hsql;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.DbSupport;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.JdbcTemplate;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Table;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class HsqlSchema
/*     */   extends Schema
/*     */ {
/*     */   public HsqlSchema(JdbcTemplate jdbcTemplate, DbSupport dbSupport, String name) {
/*  39 */     super(jdbcTemplate, dbSupport, name);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doExists() throws SQLException {
/*  44 */     return (this.jdbcTemplate.queryForInt("SELECT COUNT (*) FROM information_schema.system_schemas WHERE table_schem=?", new String[] { this.name }) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doEmpty() throws SQLException {
/*  49 */     return ((allTables()).length == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doCreate() throws SQLException {
/*  54 */     String user = this.jdbcTemplate.queryForString("SELECT USER() FROM (VALUES(0))", new String[0]);
/*  55 */     this.jdbcTemplate.execute("CREATE SCHEMA " + this.dbSupport.quote(new String[] { this.name }, ) + " AUTHORIZATION " + user, new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDrop() throws SQLException {
/*  60 */     this.jdbcTemplate.execute("DROP SCHEMA " + this.dbSupport.quote(new String[] { this.name }, ) + " CASCADE", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClean() throws SQLException {
/*  65 */     for (Table table : allTables()) {
/*  66 */       table.drop();
/*     */     }
/*     */     
/*  69 */     for (String statement : generateDropStatementsForSequences()) {
/*  70 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> generateDropStatementsForSequences() throws SQLException {
/*  81 */     List<String> sequenceNames = this.jdbcTemplate.queryForStringList("SELECT SEQUENCE_NAME FROM INFORMATION_SCHEMA.SYSTEM_SEQUENCES where SEQUENCE_SCHEMA = ?", new String[] { this.name });
/*     */ 
/*     */     
/*  84 */     List<String> statements = new ArrayList<String>();
/*  85 */     for (String seqName : sequenceNames) {
/*  86 */       statements.add("DROP SEQUENCE " + this.dbSupport.quote(new String[] { this.name, seqName }));
/*     */     } 
/*     */     
/*  89 */     return statements;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Table[] doAllTables() throws SQLException {
/*  94 */     List<String> tableNames = this.jdbcTemplate.queryForStringList("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.SYSTEM_TABLES where TABLE_SCHEM = ? AND TABLE_TYPE = 'TABLE'", new String[] { this.name });
/*     */ 
/*     */     
/*  97 */     Table[] tables = new Table[tableNames.size()];
/*  98 */     for (int i = 0; i < tableNames.size(); i++) {
/*  99 */       tables[i] = new HsqlTable(this.jdbcTemplate, this.dbSupport, this, tableNames.get(i));
/*     */     }
/* 101 */     return tables;
/*     */   }
/*     */ 
/*     */   
/*     */   public Table getTable(String tableName) {
/* 106 */     return new HsqlTable(this.jdbcTemplate, this.dbSupport, this, tableName);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\hsql\HsqlSchema.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */