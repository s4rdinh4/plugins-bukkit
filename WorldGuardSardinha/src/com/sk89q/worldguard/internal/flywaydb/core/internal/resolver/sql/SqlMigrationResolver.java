/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.resolver.sql;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationType;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationVersion;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.resolver.MigrationResolver;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.resolver.ResolvedMigration;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.DbSupport;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.resolver.MigrationInfoHelper;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.resolver.ResolvedMigrationComparator;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.resolver.ResolvedMigrationImpl;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.Location;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.Pair;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.PlaceholderReplacer;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner.Resource;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner.Scanner;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.zip.CRC32;
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
/*     */ public class SqlMigrationResolver
/*     */   implements MigrationResolver
/*     */ {
/*     */   private final DbSupport dbSupport;
/*     */   private final Scanner scanner;
/*     */   private final Location location;
/*     */   private final PlaceholderReplacer placeholderReplacer;
/*     */   private final String encoding;
/*     */   private final String sqlMigrationPrefix;
/*     */   private final String sqlMigrationSeparator;
/*     */   private final String sqlMigrationSuffix;
/*     */   
/*     */   public SqlMigrationResolver(DbSupport dbSupport, ClassLoader classLoader, Location location, PlaceholderReplacer placeholderReplacer, String encoding, String sqlMigrationPrefix, String sqlMigrationSeparator, String sqlMigrationSuffix) {
/*  98 */     this.dbSupport = dbSupport;
/*  99 */     this.scanner = new Scanner(classLoader);
/* 100 */     this.location = location;
/* 101 */     this.placeholderReplacer = placeholderReplacer;
/* 102 */     this.encoding = encoding;
/* 103 */     this.sqlMigrationPrefix = sqlMigrationPrefix;
/* 104 */     this.sqlMigrationSeparator = sqlMigrationSeparator;
/* 105 */     this.sqlMigrationSuffix = sqlMigrationSuffix;
/*     */   }
/*     */   
/*     */   public List<ResolvedMigration> resolveMigrations() {
/* 109 */     List<ResolvedMigration> migrations = new ArrayList<ResolvedMigration>();
/*     */ 
/*     */     
/*     */     try {
/* 113 */       Resource[] resources = this.scanner.scanForResources(this.location, this.sqlMigrationPrefix, this.sqlMigrationSuffix);
/*     */       
/* 115 */       for (Resource resource : resources) {
/* 116 */         ResolvedMigrationImpl resolvedMigration = extractMigrationInfo(resource);
/* 117 */         resolvedMigration.setPhysicalLocation(resource.getLocationOnDisk());
/* 118 */         resolvedMigration.setExecutor(new SqlMigrationExecutor(this.dbSupport, resource, this.placeholderReplacer, this.encoding));
/*     */         
/* 120 */         migrations.add(resolvedMigration);
/*     */       } 
/* 122 */     } catch (Exception e) {
/* 123 */       throw new FlywayException("Unable to scan for SQL migrations in location: " + this.location, e);
/*     */     } 
/*     */     
/* 126 */     Collections.sort(migrations, (Comparator<? super ResolvedMigration>)new ResolvedMigrationComparator());
/* 127 */     return migrations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ResolvedMigrationImpl extractMigrationInfo(Resource resource) {
/* 137 */     ResolvedMigrationImpl migration = new ResolvedMigrationImpl();
/*     */     
/* 139 */     Pair<MigrationVersion, String> info = MigrationInfoHelper.extractVersionAndDescription(resource.getFilename(), this.sqlMigrationPrefix, this.sqlMigrationSeparator, this.sqlMigrationSuffix);
/*     */ 
/*     */     
/* 142 */     migration.setVersion((MigrationVersion)info.getLeft());
/* 143 */     migration.setDescription((String)info.getRight());
/*     */     
/* 145 */     migration.setScript(extractScriptName(resource));
/*     */     
/* 147 */     migration.setChecksum(Integer.valueOf(calculateChecksum(resource.loadAsBytes())));
/* 148 */     migration.setType(MigrationType.SQL);
/* 149 */     return migration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String extractScriptName(Resource resource) {
/* 159 */     if (this.location.getPath().isEmpty()) {
/* 160 */       return resource.getLocation();
/*     */     }
/*     */     
/* 163 */     return resource.getLocation().substring(this.location.getPath().length() + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int calculateChecksum(byte[] bytes) {
/* 173 */     CRC32 crc32 = new CRC32();
/* 174 */     crc32.update(bytes);
/* 175 */     return (int)crc32.getValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\resolver\sql\SqlMigrationResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */