/*      */ package com.sk89q.worldguard.internal.flywaydb.core;
/*      */ 
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationInfoService;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationVersion;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.api.callback.FlywayCallback;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.api.resolver.MigrationResolver;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.internal.command.DbClean;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.internal.command.DbInit;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.internal.command.DbMigrate;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.internal.command.DbRepair;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.internal.command.DbSchemas;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.internal.command.DbValidate;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.DbSupport;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.DbSupportFactory;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.internal.info.MigrationInfoServiceImpl;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.internal.metadatatable.MetaDataTable;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.internal.metadatatable.MetaDataTableImpl;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.internal.resolver.CompositeMigrationResolver;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.ClassUtils;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.Location;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.Locations;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.PlaceholderReplacer;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.StringUtils;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.jdbc.DriverDataSource;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.jdbc.JdbcUtils;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.Log;
/*      */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.LogFactory;
/*      */ import java.sql.Connection;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Flyway
/*      */ {
/*   51 */   private static final Log LOG = LogFactory.getLog(Flyway.class);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String PLACEHOLDERS_PROPERTY_PREFIX = "flyway.placeholders.";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   69 */   private Locations locations = new Locations(new String[] { "db/migration" });
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   74 */   private String encoding = "UTF-8";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   85 */   private String[] schemaNames = new String[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   93 */   private String table = "schema_version";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   99 */   private MigrationVersion target = MigrationVersion.LATEST;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  104 */   private Map<String, String> placeholders = new HashMap<String, String>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  109 */   private String placeholderPrefix = "${";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  114 */   private String placeholderSuffix = "}";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  122 */   private String sqlMigrationPrefix = "V";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  130 */   private String sqlMigrationSeparator = "__";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  138 */   private String sqlMigrationSuffix = ".sql";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean ignoreFailedFutureMigration;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean validateOnMigrate = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean cleanOnValidationError;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  169 */   private MigrationVersion initVersion = MigrationVersion.fromVersion("1");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  174 */   private String initDescription = "<< Flyway Init >>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean initOnMigrate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean outOfOrder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  204 */   private FlywayCallback[] callbacks = new FlywayCallback[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  210 */   private MigrationResolver[] resolvers = new MigrationResolver[0];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean createdDataSource;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DataSource dataSource;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  225 */   private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean dbConnectionInfoPrinted;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getLocations() {
/*  251 */     String[] result = new String[this.locations.getLocations().size()];
/*  252 */     for (int i = 0; i < this.locations.getLocations().size(); i++) {
/*  253 */       result[i] = ((Location)this.locations.getLocations().get(i)).toString();
/*      */     }
/*  255 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getEncoding() {
/*  264 */     return this.encoding;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getSchemas() {
/*  279 */     return this.schemaNames;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTable() {
/*  291 */     return this.table;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MigrationVersion getTarget() {
/*  302 */     return this.target;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, String> getPlaceholders() {
/*  311 */     return this.placeholders;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPlaceholderPrefix() {
/*  320 */     return this.placeholderPrefix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPlaceholderSuffix() {
/*  329 */     return this.placeholderSuffix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSqlMigrationPrefix() {
/*  341 */     return this.sqlMigrationPrefix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSqlMigrationSeparator() {
/*  353 */     return this.sqlMigrationSeparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSqlMigrationSuffix() {
/*  365 */     return this.sqlMigrationSuffix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isIgnoreFailedFutureMigration() {
/*  381 */     return this.ignoreFailedFutureMigration;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isValidateOnMigrate() {
/*  390 */     return this.validateOnMigrate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCleanOnValidationError() {
/*  404 */     return this.cleanOnValidationError;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MigrationVersion getInitVersion() {
/*  413 */     return this.initVersion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getInitDescription() {
/*  422 */     return this.initDescription;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInitOnMigrate() {
/*  442 */     return this.initOnMigrate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOutOfOrder() {
/*  453 */     return this.outOfOrder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MigrationResolver[] getResolvers() {
/*  463 */     return this.resolvers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DataSource getDataSource() {
/*  472 */     return this.dataSource;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassLoader getClassLoader() {
/*  482 */     return this.classLoader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIgnoreFailedFutureMigration(boolean ignoreFailedFutureMigration) {
/*  498 */     this.ignoreFailedFutureMigration = ignoreFailedFutureMigration;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setValidateOnMigrate(boolean validateOnMigrate) {
/*  507 */     this.validateOnMigrate = validateOnMigrate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCleanOnValidationError(boolean cleanOnValidationError) {
/*  521 */     this.cleanOnValidationError = cleanOnValidationError;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLocations(String... locations) {
/*  536 */     this.locations = new Locations(locations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEncoding(String encoding) {
/*  545 */     this.encoding = encoding;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSchemas(String... schemas) {
/*  560 */     this.schemaNames = schemas;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTable(String table) {
/*  572 */     this.table = table;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTarget(MigrationVersion target) {
/*  583 */     this.target = target;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTarget(String target) {
/*  594 */     this.target = MigrationVersion.fromVersion(target);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlaceholders(Map<String, String> placeholders) {
/*  603 */     this.placeholders = placeholders;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlaceholderPrefix(String placeholderPrefix) {
/*  612 */     this.placeholderPrefix = placeholderPrefix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlaceholderSuffix(String placeholderSuffix) {
/*  621 */     this.placeholderSuffix = placeholderSuffix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSqlMigrationPrefix(String sqlMigrationPrefix) {
/*  633 */     this.sqlMigrationPrefix = sqlMigrationPrefix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSqlMigrationSeparator(String sqlMigrationSeparator) {
/*  645 */     if (!StringUtils.hasLength(sqlMigrationSeparator)) {
/*  646 */       throw new FlywayException("sqlMigrationSeparator cannot be empty!");
/*      */     }
/*      */     
/*  649 */     this.sqlMigrationSeparator = sqlMigrationSeparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSqlMigrationSuffix(String sqlMigrationSuffix) {
/*  661 */     this.sqlMigrationSuffix = sqlMigrationSuffix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDataSource(DataSource dataSource) {
/*  670 */     this.dataSource = dataSource;
/*  671 */     this.createdDataSource = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDataSource(String url, String user, String password, String... initSqls) {
/*  685 */     this.dataSource = (DataSource)new DriverDataSource(this.classLoader, null, url, user, password, initSqls);
/*  686 */     this.createdDataSource = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClassLoader(ClassLoader classLoader) {
/*  695 */     this.classLoader = classLoader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInitVersion(MigrationVersion initVersion) {
/*  704 */     this.initVersion = initVersion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInitVersion(String initVersion) {
/*  713 */     this.initVersion = MigrationVersion.fromVersion(initVersion);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInitDescription(String initDescription) {
/*  722 */     this.initDescription = initDescription;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInitOnMigrate(boolean initOnMigrate) {
/*  742 */     this.initOnMigrate = initOnMigrate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOutOfOrder(boolean outOfOrder) {
/*  753 */     this.outOfOrder = outOfOrder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FlywayCallback[] getCallbacks() {
/*  762 */     return this.callbacks;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCallbacks(FlywayCallback... callbacks) {
/*  771 */     this.callbacks = callbacks;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCallbacks(String... callbacks) {
/*  780 */     List<FlywayCallback> callbackList = ClassUtils.instantiateAll(callbacks, this.classLoader);
/*  781 */     this.callbacks = callbackList.<FlywayCallback>toArray(new FlywayCallback[callbacks.length]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setResolvers(MigrationResolver... resolvers) {
/*  790 */     this.resolvers = resolvers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setResolvers(String... resolvers) {
/*  799 */     List<MigrationResolver> resolverList = ClassUtils.instantiateAll(resolvers, this.classLoader);
/*  800 */     this.resolvers = resolverList.<MigrationResolver>toArray(new MigrationResolver[resolvers.length]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int migrate() throws FlywayException {
/*  811 */     return ((Integer)execute(new Command<Integer>() {
/*      */           public Integer execute(Connection connectionMetaDataTable, Connection connectionUserObjects, DbSupport dbSupport, Schema[] schemas) {
/*  813 */             MetaDataTableImpl metaDataTableImpl = new MetaDataTableImpl(dbSupport, schemas[0].getTable(Flyway.this.table), Flyway.this.classLoader);
/*      */             
/*  815 */             MigrationResolver migrationResolver = Flyway.this.createMigrationResolver(dbSupport);
/*  816 */             if (Flyway.this.validateOnMigrate) {
/*  817 */               Flyway.this.doValidate(connectionMetaDataTable, connectionUserObjects, migrationResolver, (MetaDataTable)metaDataTableImpl, schemas, true);
/*      */             }
/*      */ 
/*      */             
/*  821 */             (new DbSchemas(connectionMetaDataTable, schemas, (MetaDataTable)metaDataTableImpl)).create();
/*      */             
/*  823 */             if (!metaDataTableImpl.hasSchemasMarker() && !metaDataTableImpl.hasInitMarker() && !metaDataTableImpl.hasAppliedMigrations()) {
/*  824 */               List<Schema> nonEmptySchemas = new ArrayList<Schema>();
/*  825 */               for (Schema schema : schemas) {
/*  826 */                 if (!schema.empty()) {
/*  827 */                   nonEmptySchemas.add(schema);
/*      */                 }
/*      */               } 
/*      */               
/*  831 */               if (Flyway.this.initOnMigrate || nonEmptySchemas.isEmpty()) {
/*  832 */                 if (Flyway.this.initOnMigrate && !nonEmptySchemas.isEmpty()) {
/*  833 */                   (new DbInit(connectionMetaDataTable, (MetaDataTable)metaDataTableImpl, Flyway.this.initVersion, Flyway.this.initDescription, Flyway.this.callbacks)).init();
/*      */                 }
/*      */               }
/*  836 */               else if (nonEmptySchemas.size() == 1) {
/*  837 */                 Schema schema = nonEmptySchemas.get(0);
/*      */                 
/*  839 */                 if ((schema.allTables()).length != 1 || !schema.getTable(Flyway.this.table).exists()) {
/*  840 */                   throw new FlywayException("Found non-empty schema " + schema + " without metadata table! Use init()" + " or set initOnMigrate to true to initialize the metadata table.");
/*      */                 }
/*      */               }
/*      */               else {
/*      */                 
/*  845 */                 throw new FlywayException("Found non-empty schemas " + StringUtils.collectionToCommaDelimitedString(nonEmptySchemas) + " without metadata table! Use init()" + " or set initOnMigrate to true to initialize the metadata table.");
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  853 */             DbSupport dbSupportUserObjects = DbSupportFactory.createDbSupport(connectionUserObjects, false);
/*  854 */             Schema originalSchemaUserObjects = dbSupportUserObjects.getCurrentSchema();
/*  855 */             boolean schemaChange = !schemas[0].equals(originalSchemaUserObjects);
/*  856 */             if (schemaChange) {
/*  857 */               dbSupportUserObjects.setCurrentSchema(schemas[0]);
/*      */             }
/*      */             
/*  860 */             DbMigrate dbMigrate = new DbMigrate(connectionMetaDataTable, connectionUserObjects, dbSupport, (MetaDataTable)metaDataTableImpl, schemas[0], migrationResolver, Flyway.this.target, Flyway.this.ignoreFailedFutureMigration, Flyway.this.outOfOrder, Flyway.this.callbacks);
/*      */ 
/*      */             
/*      */             try {
/*  864 */               return Integer.valueOf(dbMigrate.migrate());
/*      */             } finally {
/*  866 */               if (schemaChange) {
/*  867 */                 dbSupportUserObjects.setCurrentSchema(originalSchemaUserObjects);
/*      */               }
/*      */             } 
/*      */           }
/*      */         })).intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void validate() throws FlywayException {
/*  880 */     execute(new Command<Void>() {
/*      */           public Void execute(Connection connectionMetaDataTable, Connection connectionUserObjects, DbSupport dbSupport, Schema[] schemas) {
/*  882 */             MetaDataTableImpl metaDataTableImpl = new MetaDataTableImpl(dbSupport, schemas[0].getTable(Flyway.this.table), Flyway.this.classLoader);
/*  883 */             MigrationResolver migrationResolver = Flyway.this.createMigrationResolver(dbSupport);
/*      */             
/*  885 */             Flyway.this.doValidate(connectionMetaDataTable, connectionUserObjects, migrationResolver, (MetaDataTable)metaDataTableImpl, schemas, false);
/*      */             
/*  887 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doValidate(Connection connectionMetaDataTable, Connection connectionUserObjects, MigrationResolver migrationResolver, MetaDataTable metaDataTable, Schema[] schemas, boolean pending) {
/*  904 */     String validationError = (new DbValidate(connectionMetaDataTable, connectionUserObjects, metaDataTable, migrationResolver, this.target, this.outOfOrder, pending, this.callbacks)).validate();
/*      */ 
/*      */ 
/*      */     
/*  908 */     if (validationError != null) {
/*  909 */       if (this.cleanOnValidationError) {
/*  910 */         (new DbClean(connectionMetaDataTable, metaDataTable, schemas, this.callbacks)).clean();
/*      */       } else {
/*  912 */         throw new FlywayException("Validate failed. Found differences between applied migrations and available migrations: " + validationError);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clean() {
/*  924 */     execute(new Command<Void>() {
/*      */           public Void execute(Connection connectionMetaDataTable, Connection connectionUserObjects, DbSupport dbSupport, Schema[] schemas) {
/*  926 */             MetaDataTableImpl metaDataTable = new MetaDataTableImpl(dbSupport, schemas[0].getTable(Flyway.this.table), Flyway.this.classLoader);
/*      */             
/*  928 */             (new DbClean(connectionMetaDataTable, (MetaDataTable)metaDataTable, schemas, Flyway.this.callbacks)).clean();
/*  929 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MigrationInfoService info() {
/*  942 */     return execute(new Command<MigrationInfoService>() {
/*      */           public MigrationInfoService execute(Connection connectionMetaDataTable, Connection connectionUserObjects, DbSupport dbSupport, Schema[] schemas) {
/*  944 */             for (FlywayCallback callback : Flyway.this.getCallbacks()) {
/*  945 */               callback.beforeInfo(connectionUserObjects);
/*      */             }
/*      */             
/*  948 */             MigrationResolver migrationResolver = Flyway.this.createMigrationResolver(dbSupport);
/*  949 */             MetaDataTableImpl metaDataTableImpl = new MetaDataTableImpl(dbSupport, schemas[0].getTable(Flyway.this.table), Flyway.this.classLoader);
/*      */             
/*  951 */             MigrationInfoServiceImpl migrationInfoService = new MigrationInfoServiceImpl(migrationResolver, (MetaDataTable)metaDataTableImpl, Flyway.this.target, Flyway.this.outOfOrder, true);
/*      */             
/*  953 */             migrationInfoService.refresh();
/*      */             
/*  955 */             for (FlywayCallback callback : Flyway.this.getCallbacks()) {
/*  956 */               callback.afterInfo(connectionUserObjects);
/*      */             }
/*      */             
/*  959 */             return (MigrationInfoService)migrationInfoService;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void init() throws FlywayException {
/*  970 */     execute(new Command<Void>() {
/*      */           public Void execute(Connection connectionMetaDataTable, Connection connectionUserObjects, DbSupport dbSupport, Schema[] schemas) {
/*  972 */             MetaDataTableImpl metaDataTableImpl = new MetaDataTableImpl(dbSupport, schemas[0].getTable(Flyway.this.table), Flyway.this.classLoader);
/*  973 */             (new DbSchemas(connectionMetaDataTable, schemas, (MetaDataTable)metaDataTableImpl)).create();
/*  974 */             (new DbInit(connectionMetaDataTable, (MetaDataTable)metaDataTableImpl, Flyway.this.initVersion, Flyway.this.initDescription, Flyway.this.callbacks)).init();
/*  975 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void repair() throws FlywayException {
/*  990 */     execute(new Command<Void>() {
/*      */           public Void execute(Connection connectionMetaDataTable, Connection connectionUserObjects, DbSupport dbSupport, Schema[] schemas) {
/*  992 */             MigrationResolver migrationResolver = Flyway.this.createMigrationResolver(dbSupport);
/*  993 */             MetaDataTableImpl metaDataTableImpl = new MetaDataTableImpl(dbSupport, schemas[0].getTable(Flyway.this.table), Flyway.this.classLoader);
/*  994 */             (new DbRepair(connectionMetaDataTable, migrationResolver, (MetaDataTable)metaDataTableImpl, Flyway.this.callbacks)).repair();
/*  995 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MigrationResolver createMigrationResolver(DbSupport dbSupport) {
/* 1007 */     PlaceholderReplacer placeholderReplacer = new PlaceholderReplacer(this.placeholders, this.placeholderPrefix, this.placeholderSuffix);
/*      */     
/* 1009 */     return (MigrationResolver)new CompositeMigrationResolver(dbSupport, this.classLoader, this.locations, this.encoding, this.sqlMigrationPrefix, this.sqlMigrationSeparator, this.sqlMigrationSuffix, placeholderReplacer, this.resolvers);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void configure(Properties properties) {
/* 1024 */     String driverProp = properties.getProperty("flyway.driver");
/* 1025 */     String urlProp = properties.getProperty("flyway.url");
/* 1026 */     String userProp = properties.getProperty("flyway.user");
/* 1027 */     String passwordProp = properties.getProperty("flyway.password");
/*      */     
/* 1029 */     if (StringUtils.hasText(urlProp)) {
/* 1030 */       setDataSource((DataSource)new DriverDataSource(this.classLoader, driverProp, urlProp, userProp, passwordProp, new String[0]));
/* 1031 */     } else if (!StringUtils.hasText(urlProp) && (StringUtils.hasText(driverProp) || StringUtils.hasText(userProp) || StringUtils.hasText(passwordProp))) {
/*      */       
/* 1033 */       LOG.warn("Discarding INCOMPLETE dataSource configuration! flyway.url must be set.");
/*      */     } 
/*      */     
/* 1036 */     String locationsProp = properties.getProperty("flyway.locations");
/* 1037 */     if (locationsProp != null) {
/* 1038 */       setLocations(StringUtils.tokenizeToStringArray(locationsProp, ","));
/*      */     }
/* 1040 */     String placeholderPrefixProp = properties.getProperty("flyway.placeholderPrefix");
/* 1041 */     if (placeholderPrefixProp != null) {
/* 1042 */       setPlaceholderPrefix(placeholderPrefixProp);
/*      */     }
/* 1044 */     String placeholderSuffixProp = properties.getProperty("flyway.placeholderSuffix");
/* 1045 */     if (placeholderSuffixProp != null) {
/* 1046 */       setPlaceholderSuffix(placeholderSuffixProp);
/*      */     }
/* 1048 */     String sqlMigrationPrefixProp = properties.getProperty("flyway.sqlMigrationPrefix");
/* 1049 */     if (sqlMigrationPrefixProp != null) {
/* 1050 */       setSqlMigrationPrefix(sqlMigrationPrefixProp);
/*      */     }
/* 1052 */     String sqlMigrationSeparatorProp = properties.getProperty("flyway.sqlMigrationSeparator");
/* 1053 */     if (sqlMigrationSeparatorProp != null) {
/* 1054 */       setSqlMigrationSeparator(sqlMigrationSeparatorProp);
/*      */     }
/* 1056 */     String sqlMigrationSuffixProp = properties.getProperty("flyway.sqlMigrationSuffix");
/* 1057 */     if (sqlMigrationSuffixProp != null) {
/* 1058 */       setSqlMigrationSuffix(sqlMigrationSuffixProp);
/*      */     }
/* 1060 */     String encodingProp = properties.getProperty("flyway.encoding");
/* 1061 */     if (encodingProp != null) {
/* 1062 */       setEncoding(encodingProp);
/*      */     }
/* 1064 */     String schemasProp = properties.getProperty("flyway.schemas");
/* 1065 */     if (schemasProp != null) {
/* 1066 */       setSchemas(StringUtils.tokenizeToStringArray(schemasProp, ","));
/*      */     }
/* 1068 */     String tableProp = properties.getProperty("flyway.table");
/* 1069 */     if (tableProp != null) {
/* 1070 */       setTable(tableProp);
/*      */     }
/* 1072 */     String cleanOnValidationErrorProp = properties.getProperty("flyway.cleanOnValidationError");
/* 1073 */     if (cleanOnValidationErrorProp != null) {
/* 1074 */       setCleanOnValidationError(Boolean.parseBoolean(cleanOnValidationErrorProp));
/*      */     }
/* 1076 */     String validateOnMigrateProp = properties.getProperty("flyway.validateOnMigrate");
/* 1077 */     if (validateOnMigrateProp != null) {
/* 1078 */       setValidateOnMigrate(Boolean.parseBoolean(validateOnMigrateProp));
/*      */     }
/* 1080 */     String initVersionProp = properties.getProperty("flyway.initVersion");
/* 1081 */     if (initVersionProp != null) {
/* 1082 */       setInitVersion(MigrationVersion.fromVersion(initVersionProp));
/*      */     }
/* 1084 */     String initDescriptionProp = properties.getProperty("flyway.initDescription");
/* 1085 */     if (initDescriptionProp != null) {
/* 1086 */       setInitDescription(initDescriptionProp);
/*      */     }
/* 1088 */     String initOnMigrateProp = properties.getProperty("flyway.initOnMigrate");
/* 1089 */     if (initOnMigrateProp != null) {
/* 1090 */       setInitOnMigrate(Boolean.parseBoolean(initOnMigrateProp));
/*      */     }
/* 1092 */     String ignoreFailedFutureMigrationProp = properties.getProperty("flyway.ignoreFailedFutureMigration");
/* 1093 */     if (ignoreFailedFutureMigrationProp != null) {
/* 1094 */       setIgnoreFailedFutureMigration(Boolean.parseBoolean(ignoreFailedFutureMigrationProp));
/*      */     }
/* 1096 */     String targetProp = properties.getProperty("flyway.target");
/* 1097 */     if (targetProp != null) {
/* 1098 */       setTarget(MigrationVersion.fromVersion(targetProp));
/*      */     }
/* 1100 */     String outOfOrderProp = properties.getProperty("flyway.outOfOrder");
/* 1101 */     if (outOfOrderProp != null) {
/* 1102 */       setOutOfOrder(Boolean.parseBoolean(outOfOrderProp));
/*      */     }
/* 1104 */     String resolversProp = properties.getProperty("flyway.resolvers");
/* 1105 */     if (StringUtils.hasLength(resolversProp)) {
/* 1106 */       setResolvers(StringUtils.tokenizeToStringArray(resolversProp, ","));
/*      */     }
/* 1108 */     String callbacksProp = properties.getProperty("flyway.callbacks");
/* 1109 */     if (StringUtils.hasLength(callbacksProp)) {
/* 1110 */       setCallbacks(StringUtils.tokenizeToStringArray(callbacksProp, ","));
/*      */     }
/*      */     
/* 1113 */     Map<String, String> placeholdersFromProps = new HashMap<String, String>(this.placeholders);
/* 1114 */     for (Object property : properties.keySet()) {
/* 1115 */       String propertyName = (String)property;
/* 1116 */       if (propertyName.startsWith("flyway.placeholders.") && propertyName.length() > "flyway.placeholders.".length()) {
/*      */         
/* 1118 */         String placeholderName = propertyName.substring("flyway.placeholders.".length());
/* 1119 */         String placeholderValue = properties.getProperty(propertyName);
/* 1120 */         placeholdersFromProps.put(placeholderName, placeholderValue);
/*      */       } 
/*      */     } 
/* 1123 */     setPlaceholders(placeholdersFromProps);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   <T> T execute(Command<T> command) {
/*      */     T result;
/* 1136 */     Connection connectionMetaDataTable = null;
/* 1137 */     Connection connectionUserObjects = null;
/*      */     
/*      */     try {
/* 1140 */       if (this.dataSource == null) {
/* 1141 */         throw new FlywayException("DataSource not set! Check your configuration!");
/*      */       }
/*      */       
/* 1144 */       connectionMetaDataTable = JdbcUtils.openConnection(this.dataSource);
/* 1145 */       connectionUserObjects = JdbcUtils.openConnection(this.dataSource);
/*      */       
/* 1147 */       DbSupport dbSupport = DbSupportFactory.createDbSupport(connectionMetaDataTable, !this.dbConnectionInfoPrinted);
/* 1148 */       this.dbConnectionInfoPrinted = true;
/* 1149 */       LOG.debug("DDL Transactions Supported: " + dbSupport.supportsDdlTransactions());
/*      */       
/* 1151 */       if (this.schemaNames.length == 0) {
/* 1152 */         Schema currentSchema = dbSupport.getCurrentSchema();
/* 1153 */         if (currentSchema == null) {
/* 1154 */           throw new FlywayException("Unable to determine schema for the metadata table. Set a default schema for the connection or specify one using the schemas property!");
/*      */         }
/*      */         
/* 1157 */         setSchemas(new String[] { currentSchema.getName() });
/*      */       } 
/*      */       
/* 1160 */       if (this.schemaNames.length == 1) {
/* 1161 */         LOG.debug("Schema: " + this.schemaNames[0]);
/*      */       } else {
/* 1163 */         LOG.debug("Schemas: " + StringUtils.arrayToCommaDelimitedString((Object[])this.schemaNames));
/*      */       } 
/*      */       
/* 1166 */       Schema[] schemas = new Schema[this.schemaNames.length];
/* 1167 */       for (int i = 0; i < this.schemaNames.length; i++) {
/* 1168 */         schemas[i] = dbSupport.getSchema(this.schemaNames[i]);
/*      */       }
/*      */       
/* 1171 */       result = command.execute(connectionMetaDataTable, connectionUserObjects, dbSupport, schemas);
/*      */     } finally {
/* 1173 */       JdbcUtils.closeConnection(connectionUserObjects);
/* 1174 */       JdbcUtils.closeConnection(connectionMetaDataTable);
/*      */       
/* 1176 */       if (this.dataSource instanceof DriverDataSource && this.createdDataSource) {
/* 1177 */         ((DriverDataSource)this.dataSource).close();
/*      */       }
/*      */     } 
/* 1180 */     return result;
/*      */   }
/*      */   
/*      */   static interface Command<T> {
/*      */     T execute(Connection param1Connection1, Connection param1Connection2, DbSupport param1DbSupport, Schema[] param1ArrayOfSchema);
/*      */   }
/*      */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\Flyway.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */