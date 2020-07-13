/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.mysql;
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
/*     */ public class MySQLSchema
/*     */   extends Schema
/*     */ {
/*     */   public MySQLSchema(JdbcTemplate jdbcTemplate, DbSupport dbSupport, String name) {
/*  40 */     super(jdbcTemplate, dbSupport, name);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doExists() throws SQLException {
/*  45 */     return (this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM information_schema.schemata WHERE schema_name=?", new String[] { this.name }) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doEmpty() throws SQLException {
/*  50 */     int objectCount = this.jdbcTemplate.queryForInt("Select (Select count(*) from information_schema.TABLES Where TABLE_SCHEMA=?) + (Select count(*) from information_schema.VIEWS Where TABLE_SCHEMA=?) + (Select count(*) from information_schema.TABLE_CONSTRAINTS Where TABLE_SCHEMA=?) + (Select count(*) from information_schema.EVENTS Where EVENT_SCHEMA=?) + (Select count(*) from information_schema.ROUTINES Where ROUTINE_SCHEMA=?)", new String[] { this.name, this.name, this.name, this.name, this.name });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  57 */     return (objectCount == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doCreate() throws SQLException {
/*  62 */     this.jdbcTemplate.execute("CREATE SCHEMA " + this.dbSupport.quote(new String[] { this.name }, ), new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDrop() throws SQLException {
/*  67 */     this.jdbcTemplate.execute("DROP SCHEMA " + this.dbSupport.quote(new String[] { this.name }, ), new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClean() throws SQLException {
/*  72 */     for (String statement : cleanEvents()) {
/*  73 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/*  76 */     for (String statement : cleanRoutines()) {
/*  77 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/*  80 */     for (String statement : cleanViews()) {
/*  81 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/*  84 */     this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0", new Object[0]);
/*  85 */     for (Table table : allTables()) {
/*  86 */       table.drop();
/*     */     }
/*  88 */     this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> cleanEvents() throws SQLException {
/*  98 */     List<Map<String, String>> eventNames = this.jdbcTemplate.queryForList("SELECT event_name FROM information_schema.events WHERE event_schema=?", new String[] { this.name });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     List<String> statements = new ArrayList<String>();
/* 104 */     for (Map<String, String> row : eventNames) {
/* 105 */       statements.add("DROP EVENT " + this.dbSupport.quote(new String[] { this.name, row.get("event_name") }));
/*     */     } 
/* 107 */     return statements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> cleanRoutines() throws SQLException {
/* 117 */     List<Map<String, String>> routineNames = this.jdbcTemplate.queryForList("SELECT routine_name, routine_type FROM information_schema.routines WHERE routine_schema=?", new String[] { this.name });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 122 */     List<String> statements = new ArrayList<String>();
/* 123 */     for (Map<String, String> row : routineNames) {
/* 124 */       String routineName = row.get("routine_name");
/* 125 */       String routineType = row.get("routine_type");
/* 126 */       statements.add("DROP " + routineType + " " + this.dbSupport.quote(new String[] { this.name, routineName }));
/*     */     } 
/* 128 */     return statements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> cleanViews() throws SQLException {
/* 138 */     List<String> viewNames = this.jdbcTemplate.queryForStringList("SELECT table_name FROM information_schema.views WHERE table_schema=?", new String[] { this.name });
/*     */ 
/*     */ 
/*     */     
/* 142 */     List<String> statements = new ArrayList<String>();
/* 143 */     for (String viewName : viewNames) {
/* 144 */       statements.add("DROP VIEW " + this.dbSupport.quote(new String[] { this.name, viewName }));
/*     */     } 
/* 146 */     return statements;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Table[] doAllTables() throws SQLException {
/* 151 */     List<String> tableNames = this.jdbcTemplate.queryForStringList("SELECT table_name FROM information_schema.tables WHERE table_schema=? AND table_type='BASE TABLE'", new String[] { this.name });
/*     */ 
/*     */     
/* 154 */     Table[] tables = new Table[tableNames.size()];
/* 155 */     for (int i = 0; i < tableNames.size(); i++) {
/* 156 */       tables[i] = new MySQLTable(this.jdbcTemplate, this.dbSupport, this, tableNames.get(i));
/*     */     }
/* 158 */     return tables;
/*     */   }
/*     */ 
/*     */   
/*     */   public Table getTable(String tableName) {
/* 163 */     return new MySQLTable(this.jdbcTemplate, this.dbSupport, this, tableName);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\mysql\MySQLSchema.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */