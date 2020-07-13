/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.h2;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.DbSupport;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.JdbcTemplate;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Table;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.StringUtils;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.Log;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.LogFactory;
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
/*     */ public class H2Schema
/*     */   extends Schema
/*     */ {
/*  34 */   private static final Log LOG = LogFactory.getLog(H2Schema.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public H2Schema(JdbcTemplate jdbcTemplate, DbSupport dbSupport, String name) {
/*  44 */     super(jdbcTemplate, dbSupport, name);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doExists() throws SQLException {
/*  49 */     return (this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM INFORMATION_SCHEMA.schemata WHERE schema_name=?", new String[] { this.name }) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doEmpty() throws SQLException {
/*  54 */     return ((allTables()).length == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doCreate() throws SQLException {
/*  59 */     this.jdbcTemplate.execute("CREATE SCHEMA " + this.dbSupport.quote(new String[] { this.name }, ), new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDrop() throws SQLException {
/*  64 */     this.jdbcTemplate.execute("DROP SCHEMA " + this.dbSupport.quote(new String[] { this.name }, ), new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClean() throws SQLException {
/*  69 */     for (Table table : allTables()) {
/*  70 */       table.drop();
/*     */     }
/*     */     
/*  73 */     List<String> sequenceNames = listObjectNames("SEQUENCE", "IS_GENERATED = false");
/*  74 */     for (String statement : generateDropStatements("SEQUENCE", sequenceNames, "")) {
/*  75 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/*  78 */     List<String> constantNames = listObjectNames("CONSTANT", "");
/*  79 */     for (String statement : generateDropStatements("CONSTANT", constantNames, "")) {
/*  80 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/*  83 */     List<String> domainNames = listObjectNames("DOMAIN", "");
/*  84 */     if (!domainNames.isEmpty()) {
/*  85 */       if (this.name.equals(this.dbSupport.getCurrentSchema().getName())) {
/*  86 */         for (String statement : generateDropStatementsForCurrentSchema("DOMAIN", domainNames, "")) {
/*  87 */           this.jdbcTemplate.execute(statement, new Object[0]);
/*     */         }
/*     */       } else {
/*  90 */         LOG.error("Unable to drop DOMAIN objects in schema " + this.dbSupport.quote(new String[] { this.name }) + " due to H2 bug! (More info: http://code.google.com/p/h2database/issues/detail?id=306)");
/*     */       } 
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
/*     */   private List<String> generateDropStatements(String objectType, List<String> objectNames, String dropStatementSuffix) {
/* 105 */     List<String> statements = new ArrayList<String>();
/* 106 */     for (String objectName : objectNames) {
/* 107 */       String dropStatement = "DROP " + objectType + this.dbSupport.quote(new String[] { this.name, objectName }) + " " + dropStatementSuffix;
/*     */ 
/*     */       
/* 110 */       statements.add(dropStatement);
/*     */     } 
/* 112 */     return statements;
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
/*     */   private List<String> generateDropStatementsForCurrentSchema(String objectType, List<String> objectNames, String dropStatementSuffix) {
/* 124 */     List<String> statements = new ArrayList<String>();
/* 125 */     for (String objectName : objectNames) {
/* 126 */       String dropStatement = "DROP " + objectType + this.dbSupport.quote(new String[] { objectName }) + " " + dropStatementSuffix;
/*     */ 
/*     */       
/* 129 */       statements.add(dropStatement);
/*     */     } 
/* 131 */     return statements;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Table[] doAllTables() throws SQLException {
/* 136 */     List<String> tableNames = listObjectNames("TABLE", "TABLE_TYPE = 'TABLE'");
/*     */     
/* 138 */     Table[] tables = new Table[tableNames.size()];
/* 139 */     for (int i = 0; i < tableNames.size(); i++) {
/* 140 */       tables[i] = new H2Table(this.jdbcTemplate, this.dbSupport, this, tableNames.get(i));
/*     */     }
/* 142 */     return tables;
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
/* 154 */     String query = "SELECT " + objectType + "_NAME FROM INFORMATION_SCHEMA." + objectType + "s WHERE " + objectType + "_schema = ?";
/* 155 */     if (StringUtils.hasLength(querySuffix)) {
/* 156 */       query = query + " AND " + querySuffix;
/*     */     }
/*     */     
/* 159 */     return this.jdbcTemplate.queryForStringList(query, new String[] { this.name });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Table getTable(String tableName) {
/* 165 */     return new H2Table(this.jdbcTemplate, this.dbSupport, this, tableName);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\h2\H2Schema.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */