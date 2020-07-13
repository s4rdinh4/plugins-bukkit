/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.metadatatable;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationType;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationVersion;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.DbSupport;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.JdbcTemplate;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.SqlScript;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Table;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.PlaceholderReplacer;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.StopWatch;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.StringUtils;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.TimeFormat;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.jdbc.RowMapper;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.Log;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.LogFactory;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner.classpath.ClassPathResource;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
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
/*     */ public class MetaDataTableImpl
/*     */   implements MetaDataTable
/*     */ {
/*  47 */   private static final Log LOG = LogFactory.getLog(MetaDataTableImpl.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final DbSupport dbSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Table table;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final JdbcTemplate jdbcTemplate;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClassLoader classLoader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetaDataTableImpl(DbSupport dbSupport, Table table, ClassLoader classLoader) {
/*  77 */     this.jdbcTemplate = dbSupport.getJdbcTemplate();
/*  78 */     this.dbSupport = dbSupport;
/*  79 */     this.table = table;
/*  80 */     this.classLoader = classLoader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createIfNotExists() {
/*  87 */     if (this.table.exists()) {
/*     */       return;
/*     */     }
/*     */     
/*  91 */     LOG.info("Creating Metadata table: " + this.table);
/*     */     
/*  93 */     String resourceName = "com/sk89q/worldguard/internal/flywaydb/core/internal/dbsupport/" + this.dbSupport.getDbName() + "/createMetaDataTable.sql";
/*  94 */     String source = (new ClassPathResource(resourceName, this.classLoader)).loadAsString("UTF-8");
/*     */     
/*  96 */     Map<String, String> placeholders = new HashMap<String, String>();
/*  97 */     placeholders.put("schema", this.table.getSchema().getName());
/*  98 */     placeholders.put("table", this.table.getName());
/*  99 */     String sourceNoPlaceholders = (new PlaceholderReplacer(placeholders, "${", "}")).replacePlaceholders(source);
/*     */     
/* 101 */     SqlScript sqlScript = new SqlScript(sourceNoPlaceholders, this.dbSupport);
/* 102 */     sqlScript.execute(this.jdbcTemplate);
/*     */     
/* 104 */     LOG.debug("Metadata table " + this.table + " created.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void lock() {
/* 109 */     createIfNotExists();
/* 110 */     this.table.lock();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAppliedMigration(AppliedMigration appliedMigration) {
/* 115 */     createIfNotExists();
/*     */     
/* 117 */     MigrationVersion version = appliedMigration.getVersion();
/*     */     try {
/* 119 */       int versionRank = calculateVersionRank(version);
/*     */       
/* 121 */       this.jdbcTemplate.update("UPDATE " + this.table + " SET " + this.dbSupport.quote(new String[] { "version_rank" }, ) + " = " + this.dbSupport.quote(new String[] { "version_rank" }, ) + " + 1 WHERE " + this.dbSupport.quote(new String[] { "version_rank" }, ) + " >= ?", new Object[] { Integer.valueOf(versionRank) });
/*     */ 
/*     */       
/* 124 */       this.jdbcTemplate.update("INSERT INTO " + this.table + " (" + this.dbSupport.quote(new String[] { "version_rank" }, ) + "," + this.dbSupport.quote(new String[] { "installed_rank" }, ) + "," + this.dbSupport.quote(new String[] { "version" }, ) + "," + this.dbSupport.quote(new String[] { "description" }, ) + "," + this.dbSupport.quote(new String[] { "type" }, ) + "," + this.dbSupport.quote(new String[] { "script" }, ) + "," + this.dbSupport.quote(new String[] { "checksum" }, ) + "," + this.dbSupport.quote(new String[] { "installed_by" }, ) + "," + this.dbSupport.quote(new String[] { "execution_time" }, ) + "," + this.dbSupport.quote(new String[] { "success" }, ) + ")" + " VALUES (?, ?, ?, ?, ?, ?, ?, " + this.dbSupport.getCurrentUserFunction() + ", ?, ?)", new Object[] { Integer.valueOf(versionRank), Integer.valueOf(calculateInstalledRank()), version.toString(), appliedMigration.getDescription(), appliedMigration.getType().name(), appliedMigration.getScript(), appliedMigration.getChecksum(), Integer.valueOf(appliedMigration.getExecutionTime()), Boolean.valueOf(appliedMigration.isSuccess()) });
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
/* 146 */       LOG.debug("MetaData table " + this.table + " successfully updated to reflect changes");
/* 147 */     } catch (SQLException e) {
/* 148 */       throw new FlywayException("Unable to insert row for version '" + version + "' in metadata table " + this.table, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int calculateInstalledRank() throws SQLException {
/* 158 */     int currentMax = this.jdbcTemplate.queryForInt("SELECT MAX(" + this.dbSupport.quote(new String[] { "installed_rank" }, ) + ")" + " FROM " + this.table, new String[0]);
/*     */     
/* 160 */     return currentMax + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int calculateVersionRank(MigrationVersion version) throws SQLException {
/* 170 */     List<String> versions = this.jdbcTemplate.queryForStringList("select " + this.dbSupport.quote(new String[] { "version" }, ) + " from " + this.table, new String[0]);
/*     */     
/* 172 */     List<MigrationVersion> migrationVersions = new ArrayList<MigrationVersion>();
/* 173 */     for (String versionStr : versions) {
/* 174 */       migrationVersions.add(MigrationVersion.fromVersion(versionStr));
/*     */     }
/*     */     
/* 177 */     Collections.sort(migrationVersions);
/*     */     
/* 179 */     for (int i = 0; i < migrationVersions.size(); i++) {
/* 180 */       if (version.compareTo(migrationVersions.get(i)) < 0) {
/* 181 */         return i + 1;
/*     */       }
/*     */     } 
/*     */     
/* 185 */     return migrationVersions.size() + 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<AppliedMigration> allAppliedMigrations() {
/* 190 */     return findAppliedMigrations(new MigrationType[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<AppliedMigration> findAppliedMigrations(MigrationType... migrationTypes) {
/* 200 */     if (!this.table.exists()) {
/* 201 */       return new ArrayList<AppliedMigration>();
/*     */     }
/*     */     
/* 204 */     createIfNotExists();
/*     */     
/* 206 */     String query = "SELECT " + this.dbSupport.quote(new String[] { "version_rank" }) + "," + this.dbSupport.quote(new String[] { "installed_rank" }) + "," + this.dbSupport.quote(new String[] { "version" }) + "," + this.dbSupport.quote(new String[] { "description" }) + "," + this.dbSupport.quote(new String[] { "type" }) + "," + this.dbSupport.quote(new String[] { "script" }) + "," + this.dbSupport.quote(new String[] { "checksum" }) + "," + this.dbSupport.quote(new String[] { "installed_on" }) + "," + this.dbSupport.quote(new String[] { "installed_by" }) + "," + this.dbSupport.quote(new String[] { "execution_time" }) + "," + this.dbSupport.quote(new String[] { "success" }) + " FROM " + this.table;
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
/* 219 */     if (migrationTypes.length > 0) {
/* 220 */       query = query + " WHERE " + this.dbSupport.quote(new String[] { "type" }) + " IN (";
/* 221 */       for (int i = 0; i < migrationTypes.length; i++) {
/* 222 */         if (i > 0) {
/* 223 */           query = query + ",";
/*     */         }
/* 225 */         query = query + "'" + migrationTypes[i] + "'";
/*     */       } 
/* 227 */       query = query + ")";
/*     */     } 
/*     */     
/* 230 */     query = query + " ORDER BY " + this.dbSupport.quote(new String[] { "version_rank" });
/*     */     
/*     */     try {
/* 233 */       return this.jdbcTemplate.query(query, new RowMapper<AppliedMigration>() {
/*     */             public AppliedMigration mapRow(ResultSet rs) throws SQLException {
/* 235 */               Integer checksum = Integer.valueOf(rs.getInt("checksum"));
/* 236 */               if (rs.wasNull()) {
/* 237 */                 checksum = null;
/*     */               }
/*     */               
/* 240 */               return new AppliedMigration(rs.getInt("version_rank"), rs.getInt("installed_rank"), MigrationVersion.fromVersion(rs.getString("version")), rs.getString("description"), MigrationType.valueOf(rs.getString("type")), rs.getString("script"), checksum, rs.getTimestamp("installed_on"), rs.getString("installed_by"), rs.getInt("execution_time"), rs.getBoolean("success"));
/*     */             }
/*     */           });
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
/*     */     }
/* 255 */     catch (SQLException e) {
/* 256 */       throw new FlywayException("Error while retrieving the list of applied migrations from metadata table " + this.table, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInitMarker(MigrationVersion initVersion, String initDescription) {
/* 263 */     addAppliedMigration(new AppliedMigration(initVersion, initDescription, MigrationType.INIT, initDescription, null, 0, true));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeFailedMigrations() {
/* 269 */     if (!this.table.exists()) {
/* 270 */       LOG.info("Repair of metadata table " + this.table + " not necessary. No failed migration detected.");
/*     */       
/*     */       return;
/*     */     } 
/* 274 */     createIfNotExists();
/*     */     
/*     */     try {
/* 277 */       int failedCount = this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM " + this.table + " WHERE " + this.dbSupport.quote(new String[] { "success" }, ) + "=" + this.dbSupport.getBooleanFalse(), new String[0]);
/*     */       
/* 279 */       if (failedCount == 0) {
/* 280 */         LOG.info("Repair of metadata table " + this.table + " not necessary. No failed migration detected.");
/*     */         return;
/*     */       } 
/* 283 */     } catch (SQLException e) {
/* 284 */       throw new FlywayException("Unable to check the metadata table " + this.table + " for failed migrations", e);
/*     */     } 
/*     */     
/* 287 */     StopWatch stopWatch = new StopWatch();
/* 288 */     stopWatch.start();
/*     */     
/*     */     try {
/* 291 */       this.jdbcTemplate.execute("DELETE FROM " + this.table + " WHERE " + this.dbSupport.quote(new String[] { "success" }, ) + " = " + this.dbSupport.getBooleanFalse(), new Object[0]);
/*     */     }
/* 293 */     catch (SQLException e) {
/* 294 */       throw new FlywayException("Unable to repair metadata table " + this.table, e);
/*     */     } 
/*     */     
/* 297 */     stopWatch.stop();
/*     */     
/* 299 */     LOG.info("Metadata table " + this.table + " successfully repaired (execution time " + TimeFormat.format(stopWatch.getTotalTimeMillis()) + ").");
/*     */     
/* 301 */     LOG.info("Manual cleanup of the remaining effects the failed migration may still be required.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSchemasMarker(Schema[] schemas) {
/* 306 */     createIfNotExists();
/*     */     
/* 308 */     addAppliedMigration(new AppliedMigration(MigrationVersion.fromVersion("0"), "<< Flyway Schema Creation >>", MigrationType.SCHEMA, StringUtils.arrayToCommaDelimitedString((Object[])schemas), null, 0, true));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSchemasMarker() {
/* 314 */     if (!this.table.exists()) {
/* 315 */       return false;
/*     */     }
/*     */     
/* 318 */     createIfNotExists();
/*     */     
/*     */     try {
/* 321 */       int count = this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM " + this.table + " WHERE " + this.dbSupport.quote(new String[] { "type" }, ) + "='SCHEMA'", new String[0]);
/*     */       
/* 323 */       return (count > 0);
/* 324 */     } catch (SQLException e) {
/* 325 */       throw new FlywayException("Unable to check whether the metadata table " + this.table + " has a schema marker migration", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasInitMarker() {
/* 331 */     if (!this.table.exists()) {
/* 332 */       return false;
/*     */     }
/*     */     
/* 335 */     createIfNotExists();
/*     */     
/*     */     try {
/* 338 */       int count = this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM " + this.table + " WHERE " + this.dbSupport.quote(new String[] { "type" }, ) + "='INIT'", new String[0]);
/*     */       
/* 340 */       return (count > 0);
/* 341 */     } catch (SQLException e) {
/* 342 */       throw new FlywayException("Unable to check whether the metadata table " + this.table + " has an init marker migration", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AppliedMigration getInitMarker() {
/* 348 */     List<AppliedMigration> appliedMigrations = findAppliedMigrations(new MigrationType[] { MigrationType.INIT });
/* 349 */     return appliedMigrations.isEmpty() ? null : appliedMigrations.get(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAppliedMigrations() {
/* 354 */     if (!this.table.exists()) {
/* 355 */       return false;
/*     */     }
/*     */     
/* 358 */     createIfNotExists();
/*     */     
/*     */     try {
/* 361 */       int count = this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM " + this.table + " WHERE " + this.dbSupport.quote(new String[] { "type" }, ) + " NOT IN ('SCHEMA', 'INIT')", new String[0]);
/*     */       
/* 363 */       return (count > 0);
/* 364 */     } catch (SQLException e) {
/* 365 */       throw new FlywayException("Unable to check whether the metadata table " + this.table + " has applied migrations", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateChecksum(MigrationVersion version, Integer checksum) {
/*     */     try {
/* 372 */       this.jdbcTemplate.update("UPDATE " + this.table + " SET " + this.dbSupport.quote(new String[] { "checksum" }, ) + "=" + checksum + " WHERE " + this.dbSupport.quote(new String[] { "version" }, ) + "='" + version + "'", new Object[0]);
/*     */     }
/* 374 */     catch (SQLException e) {
/* 375 */       throw new FlywayException("Unable to update checksum in metadata table " + this.table + " for version " + version + " to " + checksum, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 382 */     return this.table.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\metadatatable\MetaDataTableImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */