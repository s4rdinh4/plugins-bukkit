/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.resolver.spring;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationType;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationVersion;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.migration.MigrationChecksumProvider;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.migration.MigrationInfoProvider;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.migration.spring.SpringJdbcMigration;
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
/*     */ 
/*     */ public class SpringJdbcMigrationResolver
/*     */   implements MigrationResolver
/*     */ {
/*     */   private final Location location;
/*     */   private ClassLoader classLoader;
/*     */   
/*     */   public SpringJdbcMigrationResolver(ClassLoader classLoader, Location location) {
/*  62 */     this.location = location;
/*  63 */     this.classLoader = classLoader;
/*     */   }
/*     */   
/*     */   public Collection<ResolvedMigration> resolveMigrations() {
/*  67 */     List<ResolvedMigration> migrations = new ArrayList<ResolvedMigration>();
/*     */     
/*  69 */     if (!this.location.isClassPath()) {
/*  70 */       return migrations;
/*     */     }
/*     */     
/*     */     try {
/*  74 */       Class<?>[] classes = (new Scanner(this.classLoader)).scanForClasses(this.location, SpringJdbcMigration.class);
/*  75 */       for (Class<?> clazz : classes) {
/*  76 */         SpringJdbcMigration springJdbcMigration = (SpringJdbcMigration)ClassUtils.instantiate(clazz.getName(), this.classLoader);
/*     */         
/*  78 */         ResolvedMigrationImpl migrationInfo = extractMigrationInfo(springJdbcMigration);
/*  79 */         migrationInfo.setPhysicalLocation(ClassUtils.getLocationOnDisk(clazz));
/*  80 */         migrationInfo.setExecutor(new SpringJdbcMigrationExecutor(springJdbcMigration));
/*     */         
/*  82 */         migrations.add(migrationInfo);
/*     */       } 
/*  84 */     } catch (Exception e) {
/*  85 */       throw new FlywayException("Unable to resolve Spring Jdbc Java migrations in location: " + this.location, e);
/*     */     } 
/*     */     
/*  88 */     Collections.sort(migrations, (Comparator<? super ResolvedMigration>)new ResolvedMigrationComparator());
/*  89 */     return migrations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ResolvedMigrationImpl extractMigrationInfo(SpringJdbcMigration springJdbcMigration) {
/*     */     MigrationVersion version;
/*     */     String description;
/*  99 */     Integer checksum = null;
/* 100 */     if (springJdbcMigration instanceof MigrationChecksumProvider) {
/* 101 */       MigrationChecksumProvider checksumProvider = (MigrationChecksumProvider)springJdbcMigration;
/* 102 */       checksum = checksumProvider.getChecksum();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 107 */     if (springJdbcMigration instanceof MigrationInfoProvider) {
/* 108 */       MigrationInfoProvider infoProvider = (MigrationInfoProvider)springJdbcMigration;
/* 109 */       version = infoProvider.getVersion();
/* 110 */       description = infoProvider.getDescription();
/* 111 */       if (!StringUtils.hasText(description)) {
/* 112 */         throw new FlywayException("Missing description for migration " + version);
/*     */       }
/*     */     } else {
/* 115 */       Pair<MigrationVersion, String> info = MigrationInfoHelper.extractVersionAndDescription(ClassUtils.getShortName(springJdbcMigration.getClass()), "V", "__", "");
/*     */ 
/*     */       
/* 118 */       version = (MigrationVersion)info.getLeft();
/* 119 */       description = (String)info.getRight();
/*     */     } 
/*     */     
/* 122 */     String script = springJdbcMigration.getClass().getName();
/*     */     
/* 124 */     ResolvedMigrationImpl resolvedMigration = new ResolvedMigrationImpl();
/* 125 */     resolvedMigration.setVersion(version);
/* 126 */     resolvedMigration.setDescription(description);
/* 127 */     resolvedMigration.setScript(script);
/* 128 */     resolvedMigration.setChecksum(checksum);
/* 129 */     resolvedMigration.setType(MigrationType.SPRING_JDBC);
/* 130 */     return resolvedMigration;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\resolver\spring\SpringJdbcMigrationResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */