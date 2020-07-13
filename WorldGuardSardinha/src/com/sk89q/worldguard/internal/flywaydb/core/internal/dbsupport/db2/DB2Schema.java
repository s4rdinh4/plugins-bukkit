/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.db2;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.DbSupport;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Function;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.JdbcTemplate;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Table;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Type;
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
/*     */ public class DB2Schema
/*     */   extends Schema
/*     */ {
/*     */   public DB2Schema(JdbcTemplate jdbcTemplate, DbSupport dbSupport, String name) {
/*  43 */     super(jdbcTemplate, dbSupport, name);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doExists() throws SQLException {
/*  48 */     return (this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM syscat.schemata WHERE schemaname=?", new String[] { this.name }) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doEmpty() throws SQLException {
/*  53 */     int objectCount = this.jdbcTemplate.queryForInt("select count(*) from syscat.tables where tabschema = ?", new String[] { this.name });
/*  54 */     objectCount += this.jdbcTemplate.queryForInt("select count(*) from syscat.views where viewschema = ?", new String[] { this.name });
/*  55 */     objectCount += this.jdbcTemplate.queryForInt("select count(*) from syscat.sequences where seqschema = ?", new String[] { this.name });
/*  56 */     objectCount += this.jdbcTemplate.queryForInt("select count(*) from syscat.indexes where indschema = ?", new String[] { this.name });
/*  57 */     objectCount += this.jdbcTemplate.queryForInt("select count(*) from syscat.procedures where procschema = ?", new String[] { this.name });
/*  58 */     objectCount += this.jdbcTemplate.queryForInt("select count(*) from syscat.functions where funcschema = ?", new String[] { this.name });
/*  59 */     return (objectCount == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doCreate() throws SQLException {
/*  64 */     this.jdbcTemplate.execute("CREATE SCHEMA " + this.dbSupport.quote(new String[] { this.name }, ), new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDrop() throws SQLException {
/*  69 */     clean();
/*  70 */     this.jdbcTemplate.execute("DROP SCHEMA " + this.dbSupport.quote(new String[] { this.name }, ) + " RESTRICT", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doClean() throws SQLException {
/*  79 */     for (String dropVersioningStatement : generateDropVersioningStatement()) {
/*  80 */       this.jdbcTemplate.execute(dropVersioningStatement, new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  84 */     for (String dropStatement : generateDropStatements(this.name, "V", "VIEW")) {
/*  85 */       this.jdbcTemplate.execute(dropStatement, new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  89 */     for (String dropStatement : generateDropStatements(this.name, "A", "ALIAS")) {
/*  90 */       this.jdbcTemplate.execute(dropStatement, new Object[0]);
/*     */     }
/*     */     
/*  93 */     for (Table table : allTables()) {
/*  94 */       table.drop();
/*     */     }
/*     */ 
/*     */     
/*  98 */     for (String dropStatement : generateDropStatementsForSequences(this.name)) {
/*  99 */       this.jdbcTemplate.execute(dropStatement, new Object[0]);
/*     */     }
/*     */ 
/*     */     
/* 103 */     for (String dropStatement : generateDropStatementsForProcedures(this.name)) {
/* 104 */       this.jdbcTemplate.execute(dropStatement, new Object[0]);
/*     */     }
/*     */     
/* 107 */     for (Function function : allFunctions()) {
/* 108 */       function.drop();
/*     */     }
/*     */     
/* 111 */     for (Type type : allTypes()) {
/* 112 */       type.drop();
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
/*     */ 
/*     */   
/*     */   private List<String> generateDropStatementsForProcedures(String schema) throws SQLException {
/* 126 */     String dropProcGenQuery = "select rtrim(PROCNAME) from SYSCAT.PROCEDURES where PROCSCHEMA = '" + schema + "'";
/* 127 */     return buildDropStatements("DROP PROCEDURE", dropProcGenQuery, schema);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> generateDropStatementsForSequences(String schema) throws SQLException {
/* 138 */     String dropSeqGenQuery = "select rtrim(SEQNAME) from SYSCAT.SEQUENCES where SEQSCHEMA = '" + schema + "' and SEQTYPE='S'";
/*     */     
/* 140 */     return buildDropStatements("DROP SEQUENCE", dropSeqGenQuery, schema);
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
/*     */   
/*     */   private List<String> generateDropStatements(String schema, String tableType, String objectType) throws SQLException {
/* 153 */     String dropTablesGenQuery = "select rtrim(TABNAME) from SYSCAT.TABLES where TYPE='" + tableType + "' and TABSCHEMA = '" + schema + "'";
/*     */     
/* 155 */     return buildDropStatements("DROP " + objectType, dropTablesGenQuery, schema);
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
/*     */   
/*     */   private List<String> buildDropStatements(String dropPrefix, String query, String schema) throws SQLException {
/* 168 */     List<String> dropStatements = new ArrayList<String>();
/* 169 */     List<String> dbObjects = this.jdbcTemplate.queryForStringList(query, new String[0]);
/* 170 */     for (String dbObject : dbObjects) {
/* 171 */       dropStatements.add(dropPrefix + " " + this.dbSupport.quote(new String[] { schema, dbObject }));
/*     */     } 
/* 173 */     return dropStatements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> generateDropVersioningStatement() throws SQLException {
/* 183 */     List<String> dropVersioningStatements = new ArrayList<String>();
/* 184 */     Table[] versioningTables = findTables("select rtrim(TABNAME) from SYSCAT.TABLES where TEMPORALTYPE <> 'N' and TABSCHEMA = ?", new String[] { this.name });
/* 185 */     for (Table table : versioningTables) {
/* 186 */       dropVersioningStatements.add("ALTER TABLE " + table.toString() + " DROP VERSIONING");
/*     */     }
/*     */     
/* 189 */     return dropVersioningStatements;
/*     */   }
/*     */   
/*     */   private Table[] findTables(String sqlQuery, String... params) throws SQLException {
/* 193 */     List<String> tableNames = this.jdbcTemplate.queryForStringList(sqlQuery, params);
/* 194 */     Table[] tables = new Table[tableNames.size()];
/* 195 */     for (int i = 0; i < tableNames.size(); i++) {
/* 196 */       tables[i] = new DB2Table(this.jdbcTemplate, this.dbSupport, this, tableNames.get(i));
/*     */     }
/* 198 */     return tables;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Table[] doAllTables() throws SQLException {
/* 203 */     return findTables("select rtrim(TABNAME) from SYSCAT.TABLES where TYPE='T' and TABSCHEMA = ?", new String[] { this.name });
/*     */   }
/*     */ 
/*     */   
/*     */   protected Function[] doAllFunctions() throws SQLException {
/* 208 */     List<Map<String, String>> rows = this.jdbcTemplate.queryForList("select p.SPECIFICNAME, p.FUNCNAME, substr( xmlserialize( xmlagg( xmltext( concat( ', ', TYPENAME ) ) ) as varchar( 1024 ) ), 3 ) as PARAMS from SYSCAT.FUNCTIONS f inner join SYSCAT.FUNCPARMS p on f.SPECIFICNAME = p.SPECIFICNAME where f.ORIGIN = 'Q' and p.FUNCSCHEMA = ? and p.ROWTYPE = 'P' group by p.SPECIFICNAME, p.FUNCNAME order by p.SPECIFICNAME", new String[] { this.name });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 216 */     List<Function> functions = new ArrayList<Function>();
/* 217 */     for (Map<String, String> row : rows) {
/* 218 */       functions.add(getFunction(row.get("FUNCNAME"), StringUtils.tokenizeToStringArray(row.get("PARAMS"), ",")));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 223 */     return functions.<Function>toArray(new Function[functions.size()]);
/*     */   }
/*     */ 
/*     */   
/*     */   public Table getTable(String tableName) {
/* 228 */     return new DB2Table(this.jdbcTemplate, this.dbSupport, this, tableName);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Type getType(String typeName) {
/* 233 */     return new DB2Type(this.jdbcTemplate, this.dbSupport, this, typeName);
/*     */   }
/*     */ 
/*     */   
/*     */   public Function getFunction(String functionName, String... args) {
/* 238 */     return new DB2Function(this.jdbcTemplate, this.dbSupport, this, functionName, args);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\db2\DB2Schema.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */