/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.derby;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.DbSupport;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.JdbcTemplate;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Table;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.StringUtils;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class DerbySchema
/*     */   extends Schema
/*     */ {
/*     */   public DerbySchema(JdbcTemplate jdbcTemplate, DbSupport dbSupport, String name) {
/*  41 */     super(jdbcTemplate, dbSupport, name);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doExists() throws SQLException {
/*  46 */     return (this.jdbcTemplate.queryForInt("SELECT COUNT (*) FROM sys.sysschemas WHERE schemaname=?", new String[] { this.name }) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doEmpty() throws SQLException {
/*  51 */     return ((allTables()).length == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doCreate() throws SQLException {
/*  56 */     this.jdbcTemplate.execute("CREATE SCHEMA " + this.dbSupport.quote(new String[] { this.name }, ), new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDrop() throws SQLException {
/*  61 */     clean();
/*  62 */     this.jdbcTemplate.execute("DROP SCHEMA " + this.dbSupport.quote(new String[] { this.name }, ) + " RESTRICT", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClean() throws SQLException {
/*  67 */     for (String statement : generateDropStatementsForConstraints()) {
/*  68 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/*  71 */     List<String> viewNames = listObjectNames("TABLE", "TABLETYPE='V'");
/*  72 */     for (String statement : generateDropStatements("VIEW", viewNames, "")) {
/*  73 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/*  76 */     for (Table table : allTables()) {
/*  77 */       table.drop();
/*     */     }
/*     */     
/*  80 */     List<String> sequenceNames = listObjectNames("SEQUENCE", "");
/*  81 */     for (String statement : generateDropStatements("SEQUENCE", sequenceNames, "RESTRICT")) {
/*  82 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> generateDropStatementsForConstraints() throws SQLException {
/*  93 */     List<Map<String, String>> results = this.jdbcTemplate.queryForList("SELECT c.constraintname, t.tablename FROM sys.sysconstraints c INNER JOIN sys.systables t ON c.tableid = t.tableid INNER JOIN sys.sysschemas s ON c.schemaid = s.schemaid WHERE c.type = 'F' AND s.schemaname = ?", new String[] { this.name });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     List<String> statements = new ArrayList<String>();
/*  99 */     for (Map<String, String> result : results) {
/* 100 */       String dropStatement = "ALTER TABLE " + this.dbSupport.quote(new String[] { this.name, result.get("TABLENAME") }) + " DROP CONSTRAINT " + this.dbSupport.quote(new String[] { result.get("CONSTRAINTNAME") });
/*     */ 
/*     */       
/* 103 */       statements.add(dropStatement);
/*     */     } 
/* 105 */     return statements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> generateDropStatements(String objectType, List<String> objectNames, String dropStatementSuffix) {
/* 117 */     List<String> statements = new ArrayList<String>();
/* 118 */     for (String objectName : objectNames) {
/* 119 */       String dropStatement = "DROP " + objectType + " " + this.dbSupport.quote(new String[] { this.name, objectName }) + " " + dropStatementSuffix;
/*     */ 
/*     */       
/* 122 */       statements.add(dropStatement);
/*     */     } 
/* 124 */     return statements;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Table[] doAllTables() throws SQLException {
/* 129 */     List<String> tableNames = listObjectNames("TABLE", "TABLETYPE='T'");
/*     */     
/* 131 */     Table[] tables = new Table[tableNames.size()];
/* 132 */     for (int i = 0; i < tableNames.size(); i++) {
/* 133 */       tables[i] = new DerbyTable(this.jdbcTemplate, this.dbSupport, this, tableNames.get(i));
/*     */     }
/* 135 */     return tables;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> listObjectNames(String objectType, String querySuffix) throws SQLException {
/* 147 */     String query = "SELECT " + objectType + "name FROM sys.sys" + objectType + "s WHERE schemaid in (SELECT schemaid FROM sys.sysschemas where schemaname = ?)";
/* 148 */     if (StringUtils.hasLength(querySuffix)) {
/* 149 */       query = query + " AND " + querySuffix;
/*     */     }
/*     */     
/* 152 */     return this.jdbcTemplate.queryForStringList(query, new String[] { this.name });
/*     */   }
/*     */ 
/*     */   
/*     */   public Table getTable(String tableName) {
/* 157 */     return new DerbyTable(this.jdbcTemplate, this.dbSupport, this, tableName);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\derby\DerbySchema.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */