/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.sqlserver;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.DbSupport;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.JdbcTemplate;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Table;
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
/*     */ public class SQLServerSchema
/*     */   extends Schema
/*     */ {
/*     */   public SQLServerSchema(JdbcTemplate jdbcTemplate, DbSupport dbSupport, String name) {
/*  40 */     super(jdbcTemplate, dbSupport, name);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doExists() throws SQLException {
/*  45 */     return (this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME=?", new String[] { this.name }) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doEmpty() throws SQLException {
/*  50 */     int objectCount = this.jdbcTemplate.queryForInt("Select count(*) FROM ( Select TABLE_NAME as OBJECT_NAME, TABLE_SCHEMA as OBJECT_SCHEMA from INFORMATION_SCHEMA.TABLES Union Select TABLE_NAME as OBJECT_NAME, TABLE_SCHEMA as OBJECT_SCHEMA from INFORMATION_SCHEMA.VIEWS Union Select CONSTRAINT_NAME as OBJECT_NAME, TABLE_SCHEMA as OBJECT_SCHEMA from INFORMATION_SCHEMA.TABLE_CONSTRAINTS Union Select ROUTINE_NAME as OBJECT_NAME, ROUTINE_SCHEMA as OBJECT_SCHEMA from INFORMATION_SCHEMA.ROUTINES ) R where OBJECT_SCHEMA = ?", new String[] { this.name });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     return (objectCount == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doCreate() throws SQLException {
/*  65 */     this.jdbcTemplate.execute("CREATE SCHEMA " + this.dbSupport.quote(new String[] { this.name }, ), new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDrop() throws SQLException {
/*  70 */     clean();
/*  71 */     this.jdbcTemplate.execute("DROP SCHEMA " + this.dbSupport.quote(new String[] { this.name }, ), new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClean() throws SQLException {
/*  76 */     for (String statement : cleanForeignKeys()) {
/*  77 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/*  80 */     for (String statement : cleanDefaultConstraints()) {
/*  81 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/*  84 */     for (String statement : cleanRoutines()) {
/*  85 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/*  88 */     for (String statement : cleanViews()) {
/*  89 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/*  92 */     for (Table table : allTables()) {
/*  93 */       table.drop();
/*     */     }
/*     */     
/*  96 */     for (String statement : cleanTypes()) {
/*  97 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/* 100 */     for (String statement : cleanSynonyms()) {
/* 101 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> cleanForeignKeys() throws SQLException {
/* 113 */     List<Map<String, String>> constraintNames = this.jdbcTemplate.queryForList("SELECT table_name, constraint_name FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE constraint_type in ('FOREIGN KEY','CHECK') and table_schema=?", new String[] { this.name });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     List<String> statements = new ArrayList<String>();
/* 120 */     for (Map<String, String> row : constraintNames) {
/* 121 */       String tableName = row.get("table_name");
/* 122 */       String constraintName = row.get("constraint_name");
/* 123 */       statements.add("ALTER TABLE " + this.dbSupport.quote(new String[] { this.name, tableName }) + " DROP CONSTRAINT " + this.dbSupport.quote(new String[] { constraintName }));
/*     */     } 
/* 125 */     return statements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> cleanDefaultConstraints() throws SQLException {
/* 136 */     List<Map<String, String>> constraintNames = this.jdbcTemplate.queryForList("select t.name as table_name, d.name as constraint_name from sys.tables t inner join sys.default_constraints d on d.parent_object_id = t.object_id\n inner join sys.schemas s on s.schema_id = t.schema_id\n where s.name = ?", new String[] { this.name });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 145 */     List<String> statements = new ArrayList<String>();
/* 146 */     for (Map<String, String> row : constraintNames) {
/* 147 */       String tableName = row.get("table_name");
/* 148 */       String constraintName = row.get("constraint_name");
/* 149 */       statements.add("ALTER TABLE " + this.dbSupport.quote(new String[] { this.name, tableName }) + " DROP CONSTRAINT " + this.dbSupport.quote(new String[] { constraintName }));
/*     */     } 
/* 151 */     return statements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> cleanRoutines() throws SQLException {
/* 162 */     List<Map<String, String>> routineNames = this.jdbcTemplate.queryForList("SELECT routine_name, routine_type FROM INFORMATION_SCHEMA.ROUTINES WHERE routine_schema=?", new String[] { this.name });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 167 */     List<String> statements = new ArrayList<String>();
/* 168 */     for (Map<String, String> row : routineNames) {
/* 169 */       String routineName = row.get("routine_name");
/* 170 */       String routineType = row.get("routine_type");
/* 171 */       statements.add("DROP " + routineType + " " + this.dbSupport.quote(new String[] { this.name, routineName }));
/*     */     } 
/* 173 */     return statements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> cleanViews() throws SQLException {
/* 183 */     List<String> viewNames = this.jdbcTemplate.queryForStringList("SELECT table_name FROM INFORMATION_SCHEMA.VIEWS WHERE table_schema=?", new String[] { this.name });
/*     */ 
/*     */ 
/*     */     
/* 187 */     List<String> statements = new ArrayList<String>();
/* 188 */     for (String viewName : viewNames) {
/* 189 */       statements.add("DROP VIEW " + this.dbSupport.quote(new String[] { this.name, viewName }));
/*     */     } 
/* 191 */     return statements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> cleanTypes() throws SQLException {
/* 201 */     List<String> typeNames = this.jdbcTemplate.queryForStringList("SELECT t.name FROM sys.types t INNER JOIN sys.schemas s ON t.schema_id = s.schema_id WHERE t.is_user_defined = 1 AND s.name = ?", new String[] { this.name });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 207 */     List<String> statements = new ArrayList<String>();
/* 208 */     for (String typeName : typeNames) {
/* 209 */       statements.add("DROP TYPE " + this.dbSupport.quote(new String[] { this.name, typeName }));
/*     */     } 
/* 211 */     return statements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> cleanSynonyms() throws SQLException {
/* 221 */     List<String> synonymNames = this.jdbcTemplate.queryForStringList("SELECT sn.name FROM sys.synonyms sn INNER JOIN sys.schemas s ON sn.schema_id = s.schema_id WHERE s.name = ?", new String[] { this.name });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 227 */     List<String> statements = new ArrayList<String>();
/* 228 */     for (String synonymName : synonymNames) {
/* 229 */       statements.add("DROP SYNONYM " + this.dbSupport.quote(new String[] { this.name, synonymName }));
/*     */     } 
/* 231 */     return statements;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Table[] doAllTables() throws SQLException {
/* 236 */     List<String> tableNames = this.jdbcTemplate.queryForStringList("SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE table_type='BASE TABLE' and table_schema=?", new String[] { this.name });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 241 */     Table[] tables = new Table[tableNames.size()];
/* 242 */     for (int i = 0; i < tableNames.size(); i++) {
/* 243 */       tables[i] = new SQLServerTable(this.jdbcTemplate, this.dbSupport, this, tableNames.get(i));
/*     */     }
/* 245 */     return tables;
/*     */   }
/*     */ 
/*     */   
/*     */   public Table getTable(String tableName) {
/* 250 */     return new SQLServerTable(this.jdbcTemplate, this.dbSupport, this, tableName);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\sqlserver\SQLServerSchema.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */