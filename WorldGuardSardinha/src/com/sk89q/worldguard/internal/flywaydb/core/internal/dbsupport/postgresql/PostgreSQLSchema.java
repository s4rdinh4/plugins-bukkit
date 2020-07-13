/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.postgresql;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.DbSupport;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.JdbcTemplate;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Table;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Type;
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
/*     */ public class PostgreSQLSchema
/*     */   extends Schema
/*     */ {
/*     */   public PostgreSQLSchema(JdbcTemplate jdbcTemplate, DbSupport dbSupport, String name) {
/*  41 */     super(jdbcTemplate, dbSupport, name);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doExists() throws SQLException {
/*  46 */     return (this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM pg_namespace WHERE nspname=?", new String[] { this.name }) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doEmpty() throws SQLException {
/*  51 */     int objectCount = this.jdbcTemplate.queryForInt("SELECT count(*) FROM information_schema.tables WHERE table_schema=? AND table_type='BASE TABLE'", new String[] { this.name });
/*     */ 
/*     */     
/*  54 */     return (objectCount == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doCreate() throws SQLException {
/*  59 */     this.jdbcTemplate.execute("CREATE SCHEMA " + this.dbSupport.quote(new String[] { this.name }, ), new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDrop() throws SQLException {
/*  64 */     this.jdbcTemplate.execute("DROP SCHEMA " + this.dbSupport.quote(new String[] { this.name }, ) + " CASCADE", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClean() throws SQLException {
/*  69 */     for (Table table : allTables()) {
/*  70 */       table.drop();
/*     */     }
/*     */     
/*  73 */     for (String statement : generateDropStatementsForSequences()) {
/*  74 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/*  77 */     for (String statement : generateDropStatementsForBaseTypes(true)) {
/*  78 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/*  81 */     for (String statement : generateDropStatementsForAggregates()) {
/*  82 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/*  85 */     for (String statement : generateDropStatementsForRoutines()) {
/*  86 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/*  89 */     for (String statement : generateDropStatementsForEnums()) {
/*  90 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/*  93 */     for (String statement : generateDropStatementsForDomains()) {
/*  94 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/*  97 */     for (String statement : generateDropStatementsForBaseTypes(false)) {
/*  98 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/* 101 */     for (Type type : allTypes()) {
/* 102 */       type.drop();
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
/* 113 */     List<String> sequenceNames = this.jdbcTemplate.queryForStringList("SELECT sequence_name FROM information_schema.sequences WHERE sequence_schema=?", new String[] { this.name });
/*     */ 
/*     */ 
/*     */     
/* 117 */     List<String> statements = new ArrayList<String>();
/* 118 */     for (String sequenceName : sequenceNames) {
/* 119 */       statements.add("DROP SEQUENCE IF EXISTS " + this.dbSupport.quote(new String[] { this.name, sequenceName }));
/*     */     } 
/*     */     
/* 122 */     return statements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> generateDropStatementsForBaseTypes(boolean recreate) throws SQLException {
/* 133 */     List<String> typeNames = this.jdbcTemplate.queryForStringList("select typname from pg_catalog.pg_type where typcategory in ('P', 'U') and typnamespace in (select oid from pg_catalog.pg_namespace where nspname = ?)", new String[] { this.name });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     List<String> statements = new ArrayList<String>();
/* 139 */     for (String typeName : typeNames) {
/* 140 */       statements.add("DROP TYPE IF EXISTS " + this.dbSupport.quote(new String[] { this.name, typeName }) + " CASCADE");
/*     */     } 
/*     */     
/* 143 */     if (recreate) {
/* 144 */       for (String typeName : typeNames) {
/* 145 */         statements.add("CREATE TYPE " + this.dbSupport.quote(new String[] { this.name, typeName }));
/*     */       } 
/*     */     }
/*     */     
/* 149 */     return statements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> generateDropStatementsForAggregates() throws SQLException {
/* 159 */     List<Map<String, String>> rows = this.jdbcTemplate.queryForList("SELECT proname, oidvectortypes(proargtypes) AS args FROM pg_proc INNER JOIN pg_namespace ns ON (pg_proc.pronamespace = ns.oid) WHERE pg_proc.proisagg = true AND ns.nspname = ?", new String[] { this.name });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 166 */     List<String> statements = new ArrayList<String>();
/* 167 */     for (Map<String, String> row : rows) {
/* 168 */       statements.add("DROP AGGREGATE IF EXISTS " + this.dbSupport.quote(new String[] { this.name, row.get("proname") }) + "(" + (String)row.get("args") + ") CASCADE");
/*     */     } 
/* 170 */     return statements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> generateDropStatementsForRoutines() throws SQLException {
/* 180 */     List<Map<String, String>> rows = this.jdbcTemplate.queryForList("SELECT proname, oidvectortypes(proargtypes) AS args FROM pg_proc INNER JOIN pg_namespace ns ON (pg_proc.pronamespace = ns.oid) WHERE pg_proc.proisagg = false AND ns.nspname = ?", new String[] { this.name });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 187 */     List<String> statements = new ArrayList<String>();
/* 188 */     for (Map<String, String> row : rows) {
/* 189 */       statements.add("DROP FUNCTION IF EXISTS " + this.dbSupport.quote(new String[] { this.name, row.get("proname") }) + "(" + (String)row.get("args") + ") CASCADE");
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
/*     */   private List<String> generateDropStatementsForEnums() throws SQLException {
/* 201 */     List<String> enumNames = this.jdbcTemplate.queryForStringList("SELECT t.typname FROM pg_catalog.pg_type t INNER JOIN pg_catalog.pg_namespace n ON n.oid = t.typnamespace WHERE n.nspname = ? and t.typtype = 'e'", new String[] { this.name });
/*     */ 
/*     */ 
/*     */     
/* 205 */     List<String> statements = new ArrayList<String>();
/* 206 */     for (String enumName : enumNames) {
/* 207 */       statements.add("DROP TYPE " + this.dbSupport.quote(new String[] { this.name, enumName }));
/*     */     } 
/*     */     
/* 210 */     return statements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> generateDropStatementsForDomains() throws SQLException {
/* 220 */     List<String> domainNames = this.jdbcTemplate.queryForStringList("SELECT domain_name FROM information_schema.domains WHERE domain_schema=?", new String[] { this.name });
/*     */ 
/*     */ 
/*     */     
/* 224 */     List<String> statements = new ArrayList<String>();
/* 225 */     for (String domainName : domainNames) {
/* 226 */       statements.add("DROP DOMAIN " + this.dbSupport.quote(new String[] { this.name, domainName }));
/*     */     } 
/*     */     
/* 229 */     return statements;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Table[] doAllTables() throws SQLException {
/* 234 */     List<String> tableNames = this.jdbcTemplate.queryForStringList("SELECT t.table_name FROM information_schema.tables t WHERE table_schema=? AND table_type='BASE TABLE' AND NOT (SELECT EXISTS (SELECT inhrelid FROM pg_catalog.pg_inherits WHERE inhrelid = ('\"'||t.table_schema||'\".\"'||t.table_name||'\"')::regclass::oid))", new String[] { this.name });
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
/* 248 */     Table[] tables = new Table[tableNames.size()];
/* 249 */     for (int i = 0; i < tableNames.size(); i++) {
/* 250 */       tables[i] = new PostgreSQLTable(this.jdbcTemplate, this.dbSupport, this, tableNames.get(i));
/*     */     }
/* 252 */     return tables;
/*     */   }
/*     */ 
/*     */   
/*     */   public Table getTable(String tableName) {
/* 257 */     return new PostgreSQLTable(this.jdbcTemplate, this.dbSupport, this, tableName);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Type getType(String typeName) {
/* 262 */     return new PostgreSQLType(this.jdbcTemplate, this.dbSupport, this, typeName);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\postgresql\PostgreSQLSchema.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */