/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.oracle;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.DbSupport;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.JdbcTemplate;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Table;
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
/*     */ public class OracleSchema
/*     */   extends Schema
/*     */ {
/*  34 */   private static final Log LOG = LogFactory.getLog(OracleSchema.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OracleSchema(JdbcTemplate jdbcTemplate, DbSupport dbSupport, String name) {
/*  44 */     super(jdbcTemplate, dbSupport, name);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doExists() throws SQLException {
/*  49 */     return (this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM all_users WHERE username=?", new String[] { this.name }) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doEmpty() throws SQLException {
/*  54 */     return (this.jdbcTemplate.queryForInt("SELECT count(*) FROM all_objects WHERE owner = ?", new String[] { this.name }) == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doCreate() throws SQLException {
/*  59 */     this.jdbcTemplate.execute("CREATE USER " + this.dbSupport.quote(new String[] { this.name }, ) + " IDENTIFIED BY flyway", new Object[0]);
/*  60 */     this.jdbcTemplate.execute("GRANT RESOURCE TO " + this.dbSupport.quote(new String[] { this.name }, ), new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDrop() throws SQLException {
/*  65 */     this.jdbcTemplate.execute("DROP USER " + this.dbSupport.quote(new String[] { this.name }, ) + " CASCADE", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClean() throws SQLException {
/*  70 */     if ("SYSTEM".equals(this.name.toUpperCase())) {
/*  71 */       throw new FlywayException("Clean not supported on Oracle for user 'SYSTEM'! You should NEVER add your own objects to the SYSTEM schema!");
/*     */     }
/*     */     
/*  74 */     this.jdbcTemplate.execute("PURGE RECYCLEBIN", new Object[0]);
/*     */     
/*  76 */     for (String statement : generateDropStatementsForSpatialExtensions()) {
/*  77 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/*  80 */     for (String statement : generateDropStatementsForQueueTables())
/*     */     {
/*     */       
/*  83 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/*  86 */     if (flashbackAvailable()) {
/*  87 */       executeAlterStatementsForFlashbackTables();
/*     */     }
/*     */     
/*  90 */     for (String statement : generateDropStatementsForObjectType("TRIGGER", "")) {
/*  91 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/*  94 */     for (String statement : generateDropStatementsForObjectType("SEQUENCE", "")) {
/*  95 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/*  98 */     for (String statement : generateDropStatementsForObjectType("FUNCTION", "")) {
/*  99 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/* 102 */     for (String statement : generateDropStatementsForObjectType("MATERIALIZED VIEW", "PRESERVE TABLE")) {
/* 103 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/* 106 */     for (String statement : generateDropStatementsForObjectType("PACKAGE", "")) {
/* 107 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/* 110 */     for (String statement : generateDropStatementsForObjectType("PROCEDURE", "")) {
/* 111 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/* 114 */     for (String statement : generateDropStatementsForObjectType("SYNONYM", "")) {
/* 115 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/* 118 */     for (String statement : generateDropStatementsForObjectType("VIEW", "CASCADE CONSTRAINTS")) {
/* 119 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/* 122 */     for (Table table : allTables()) {
/* 123 */       table.drop();
/*     */     }
/*     */     
/* 126 */     for (String statement : generateDropStatementsForXmlTables()) {
/* 127 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/* 130 */     for (String statement : generateDropStatementsForObjectType("CLUSTER", "")) {
/* 131 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/* 134 */     for (String statement : generateDropStatementsForObjectType("TYPE", "FORCE")) {
/* 135 */       this.jdbcTemplate.execute(statement, new Object[0]);
/*     */     }
/*     */     
/* 138 */     for (String statement : generateDropStatementsForObjectType("JAVA SOURCE", "")) {
/* 139 */       this.jdbcTemplate.execute(statement, new Object[0]);
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
/*     */   private void executeAlterStatementsForFlashbackTables() throws SQLException {
/* 152 */     List<String> tableNames = this.jdbcTemplate.queryForStringList("SELECT table_name FROM DBA_FLASHBACK_ARCHIVE_TABLES WHERE owner_name = ?", new String[] { this.name });
/*     */     
/* 154 */     for (String tableName : tableNames) {
/* 155 */       this.jdbcTemplate.execute("ALTER TABLE " + this.dbSupport.quote(new String[] { this.name, tableName }, ) + " NO FLASHBACK ARCHIVE", new Object[0]);
/* 156 */       String queryForOracleTechnicalTables = "SELECT count(archive_table_name) FROM user_flashback_archive_tables WHERE table_name = ?";
/*     */ 
/*     */ 
/*     */       
/* 160 */       while (this.jdbcTemplate.queryForInt(queryForOracleTechnicalTables, new String[] { tableName }) > 0) {
/*     */         try {
/* 162 */           LOG.debug("Actively waiting for Flashback cleanup on table: " + tableName);
/* 163 */           Thread.sleep(1000L);
/* 164 */         } catch (InterruptedException e) {
/* 165 */           throw new FlywayException("Waiting for Flashback cleanup interrupted", e);
/*     */         } 
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
/*     */   private boolean flashbackAvailable() throws SQLException {
/* 178 */     return (this.jdbcTemplate.queryForInt("select count(*) from all_objects where object_name like 'DBA_FLASHBACK_ARCHIVE_TABLES'", new String[0]) > 0);
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
/*     */   private List<String> generateDropStatementsForXmlTables() throws SQLException {
/* 191 */     List<String> dropStatements = new ArrayList<String>();
/*     */     
/* 193 */     if (!xmlDBExtensionsAvailable()) {
/* 194 */       LOG.debug("Oracle XML DB Extensions are not available. No cleaning of XML tables.");
/* 195 */       return dropStatements;
/*     */     } 
/*     */     
/* 198 */     List<String> objectNames = this.jdbcTemplate.queryForStringList("SELECT table_name FROM all_xml_tables WHERE owner = ?", new String[] { this.name });
/*     */     
/* 200 */     for (String objectName : objectNames) {
/* 201 */       dropStatements.add("DROP TABLE " + this.dbSupport.quote(new String[] { this.name, objectName }) + " PURGE");
/*     */     } 
/* 203 */     return dropStatements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean xmlDBExtensionsAvailable() throws SQLException {
/* 213 */     return (this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM all_users WHERE username = 'XDB'", new String[0]) > 0 && this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM all_views WHERE view_name = 'RESOURCE_VIEW'", new String[0]) > 0);
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
/*     */   private List<String> generateDropStatementsForObjectType(String objectType, String extraArguments) throws SQLException {
/* 226 */     String query = "SELECT object_name FROM all_objects WHERE object_type = ? AND owner = ? AND object_name NOT LIKE 'MDRS_%$'";
/*     */ 
/*     */ 
/*     */     
/* 230 */     List<String> objectNames = this.jdbcTemplate.queryForStringList(query, new String[] { objectType, this.name });
/* 231 */     List<String> dropStatements = new ArrayList<String>();
/* 232 */     for (String objectName : objectNames) {
/* 233 */       dropStatements.add("DROP " + objectType + " " + this.dbSupport.quote(new String[] { this.name, objectName }) + " " + extraArguments);
/*     */     } 
/* 235 */     return dropStatements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> generateDropStatementsForSpatialExtensions() throws SQLException {
/* 245 */     List<String> statements = new ArrayList<String>();
/*     */     
/* 247 */     if (!spatialExtensionsAvailable()) {
/* 248 */       LOG.debug("Oracle Spatial Extensions are not available. No cleaning of MDSYS tables and views.");
/* 249 */       return statements;
/*     */     } 
/* 251 */     if (!this.dbSupport.getCurrentSchema().getName().equalsIgnoreCase(this.name)) {
/* 252 */       int count = this.jdbcTemplate.queryForInt("SELECT COUNT (*) FROM all_sdo_geom_metadata WHERE owner=?", new String[] { this.name });
/* 253 */       count += this.jdbcTemplate.queryForInt("SELECT COUNT (*) FROM all_sdo_index_info WHERE sdo_index_owner=?", new String[] { this.name });
/* 254 */       if (count > 0) {
/* 255 */         LOG.warn("Unable to clean Oracle Spatial objects for schema '" + this.name + "' as they do not belong to the default schema for this connection!");
/*     */       }
/* 257 */       return statements;
/*     */     } 
/*     */ 
/*     */     
/* 261 */     statements.add("DELETE FROM mdsys.user_sdo_geom_metadata");
/*     */     
/* 263 */     List<String> indexNames = this.jdbcTemplate.queryForStringList("select INDEX_NAME from USER_SDO_INDEX_INFO", new String[0]);
/* 264 */     for (String indexName : indexNames) {
/* 265 */       statements.add("DROP INDEX \"" + indexName + "\"");
/*     */     }
/*     */     
/* 268 */     return statements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> generateDropStatementsForQueueTables() throws SQLException {
/* 278 */     List<String> statements = new ArrayList<String>();
/*     */     
/* 280 */     List<String> queueTblNames = this.jdbcTemplate.queryForStringList("select QUEUE_TABLE from USER_QUEUE_TABLES", new String[0]);
/* 281 */     for (String queueTblName : queueTblNames) {
/* 282 */       statements.add("begin DBMS_AQADM.drop_queue_table (queue_table=> '" + queueTblName + "', FORCE => TRUE); end;");
/*     */     }
/*     */     
/* 285 */     return statements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean spatialExtensionsAvailable() throws SQLException {
/* 295 */     return (this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM all_views WHERE owner = 'MDSYS' AND view_name = 'USER_SDO_GEOM_METADATA'", new String[0]) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Table[] doAllTables() throws SQLException {
/* 300 */     List<String> tableNames = this.jdbcTemplate.queryForStringList("SELECT table_name FROM all_tables WHERE owner = ? AND table_name NOT LIKE 'BIN$%' AND table_name NOT LIKE 'MDRT_%$' AND table_name NOT LIKE 'MLOG$%' AND table_name NOT LIKE 'RUPD$%' AND table_name NOT LIKE 'DR$%' AND table_name NOT LIKE 'SYS_IOT_OVER_%' AND nested != 'YES' AND secondary != 'Y'", new String[] { this.name });
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
/* 317 */     Table[] tables = new Table[tableNames.size()];
/* 318 */     for (int i = 0; i < tableNames.size(); i++) {
/* 319 */       tables[i] = new OracleTable(this.jdbcTemplate, this.dbSupport, this, tableNames.get(i));
/*     */     }
/* 321 */     return tables;
/*     */   }
/*     */ 
/*     */   
/*     */   public Table getTable(String tableName) {
/* 326 */     return new OracleTable(this.jdbcTemplate, this.dbSupport, this, tableName);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\oracle\OracleSchema.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */