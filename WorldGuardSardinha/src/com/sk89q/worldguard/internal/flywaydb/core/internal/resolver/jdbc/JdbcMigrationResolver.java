/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.resolver.jdbc;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationType;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationVersion;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.migration.MigrationChecksumProvider;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.migration.MigrationInfoProvider;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.migration.jdbc.JdbcMigration;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.resolver.MigrationResolver;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.resolver.ResolvedMigration;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.resolver.MigrationInfoHelper;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.resolver.ResolvedMigrationComparator;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.resolver.ResolvedMigrationImpl;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.ClassUtils;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.Location;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.Pair;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.StringUtils;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner.Scanner;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
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
/*     */ public class JdbcMigrationResolver
/*     */   implements MigrationResolver
/*     */ {
/*     */   private final Location location;
/*     */   private ClassLoader classLoader;
/*     */   
/*     */   public JdbcMigrationResolver(ClassLoader classLoader, Location location) {
/*  61 */     this.location = location;
/*  62 */     this.classLoader = classLoader;
/*     */   }
/*     */   
/*     */   public List<ResolvedMigration> resolveMigrations() {
/*  66 */     List<ResolvedMigration> migrations = new ArrayList<ResolvedMigration>();
/*     */     
/*  68 */     if (!this.location.isClassPath()) {
/*  69 */       return migrations;
/*     */     }
/*     */     
/*     */     try {
/*  73 */       Class<?>[] classes = (new Scanner(this.classLoader)).scanForClasses(this.location, JdbcMigration.class);
/*  74 */       for (Class<?> clazz : classes) {
/*  75 */         JdbcMigration jdbcMigration = (JdbcMigration)ClassUtils.instantiate(clazz.getName(), this.classLoader);
/*     */         
/*  77 */         ResolvedMigrationImpl migrationInfo = extractMigrationInfo(jdbcMigration);
/*  78 */         migrationInfo.setPhysicalLocation(ClassUtils.getLocationOnDisk(clazz));
/*  79 */         migrationInfo.setExecutor(new JdbcMigrationExecutor(jdbcMigration));
/*     */         
/*  81 */         migrations.add(migrationInfo);
/*     */       } 
/*  83 */     } catch (Exception e) {
/*  84 */       throw new FlywayException("Unable to resolve Jdbc Java migrations in location: " + this.location, e);
/*     */     } 
/*     */     
/*  87 */     Collections.sort(migrations, (Comparator<? super ResolvedMigration>)new ResolvedMigrationComparator());
/*  88 */     return migrations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ResolvedMigrationImpl extractMigrationInfo(JdbcMigration jdbcMigration) {
/*     */     MigrationVersion version;
/*     */     String description;
/*  98 */     Integer checksum = null;
/*  99 */     if (jdbcMigration instanceof MigrationChecksumProvider) {
/* 100 */       MigrationChecksumProvider checksumProvider = (MigrationChecksumProvider)jdbcMigration;
/* 101 */       checksum = checksumProvider.getChecksum();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 106 */     if (jdbcMigration instanceof MigrationInfoProvider) {
/* 107 */       MigrationInfoProvider infoProvider = (MigrationInfoProvider)jdbcMigration;
/* 108 */       version = infoProvider.getVersion();
/* 109 */       description = infoProvider.getDescription();
/* 110 */       if (!StringUtils.hasText(description)) {
/* 111 */         throw new FlywayException("Missing description for migration " + version);
/*     */       }
/*     */     } else {
/* 114 */       Pair<MigrationVersion, String> info = MigrationInfoHelper.extractVersionAndDescription(ClassUtils.getShortName(jdbcMigration.getClass()), "V", "__", "");
/*     */ 
/*     */       
/* 117 */       version = (MigrationVersion)info.getLeft();
/* 118 */       description = (String)info.getRight();
/*     */     } 
/*     */     
/* 121 */     String script = jdbcMigration.getClass().getName();
/*     */ 
/*     */     
/* 124 */     ResolvedMigrationImpl resolvedMigration = new ResolvedMigrationImpl();
/* 125 */     resolvedMigration.setVersion(version);
/* 126 */     resolvedMigration.setDescription(description);
/* 127 */     resolvedMigration.setScript(script);
/* 128 */     resolvedMigration.setChecksum(checksum);
/* 129 */     resolvedMigration.setType(MigrationType.JDBC);
/* 130 */     return resolvedMigration;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\resolver\jdbc\JdbcMigrationResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */