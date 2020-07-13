/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.resolver;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.resolver.MigrationResolver;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.resolver.ResolvedMigration;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.DbSupport;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.resolver.jdbc.JdbcMigrationResolver;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.resolver.spring.SpringJdbcMigrationResolver;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.resolver.sql.SqlMigrationResolver;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.FeatureDetector;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.Location;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.Locations;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.PlaceholderReplacer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public class CompositeMigrationResolver
/*     */   implements MigrationResolver
/*     */ {
/*  46 */   private Collection<MigrationResolver> migrationResolvers = new ArrayList<MigrationResolver>();
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
/*     */   private List<ResolvedMigration> availableMigrations;
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
/*     */   public CompositeMigrationResolver(DbSupport dbSupport, ClassLoader classLoader, Locations locations, String encoding, String sqlMigrationPrefix, String sqlMigrationSeparator, String sqlMigrationSuffix, PlaceholderReplacer placeholderReplacer, MigrationResolver... customMigrationResolvers) {
/*  72 */     for (Location location : locations.getLocations()) {
/*  73 */       this.migrationResolvers.add(new SqlMigrationResolver(dbSupport, classLoader, location, placeholderReplacer, encoding, sqlMigrationPrefix, sqlMigrationSeparator, sqlMigrationSuffix));
/*     */       
/*  75 */       this.migrationResolvers.add(new JdbcMigrationResolver(classLoader, location));
/*     */       
/*  77 */       if ((new FeatureDetector(classLoader)).isSpringJdbcAvailable()) {
/*  78 */         this.migrationResolvers.add(new SpringJdbcMigrationResolver(classLoader, location));
/*     */       }
/*     */     } 
/*     */     
/*  82 */     this.migrationResolvers.addAll(Arrays.asList(customMigrationResolvers));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ResolvedMigration> resolveMigrations() {
/*  93 */     if (this.availableMigrations == null) {
/*  94 */       this.availableMigrations = doFindAvailableMigrations();
/*     */     }
/*     */     
/*  97 */     return this.availableMigrations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<ResolvedMigration> doFindAvailableMigrations() throws FlywayException {
/* 108 */     List<ResolvedMigration> migrations = new ArrayList<ResolvedMigration>(collectMigrations(this.migrationResolvers));
/* 109 */     Collections.sort(migrations, new ResolvedMigrationComparator());
/*     */     
/* 111 */     checkForIncompatibilities(migrations);
/*     */     
/* 113 */     return migrations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Collection<ResolvedMigration> collectMigrations(Collection<MigrationResolver> migrationResolvers) {
/* 124 */     Set<ResolvedMigration> migrations = new HashSet<ResolvedMigration>();
/* 125 */     for (MigrationResolver migrationResolver : migrationResolvers) {
/* 126 */       migrations.addAll(migrationResolver.resolveMigrations());
/*     */     }
/* 128 */     return migrations;
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
/*     */   static void checkForIncompatibilities(List<ResolvedMigration> migrations) {
/* 140 */     for (int i = 0; i < migrations.size() - 1; i++) {
/* 141 */       ResolvedMigration current = migrations.get(i);
/* 142 */       ResolvedMigration next = migrations.get(i + 1);
/* 143 */       if (current.getVersion().compareTo(next.getVersion()) == 0)
/* 144 */         throw new FlywayException(String.format("Found more than one migration with version '%s' (Offenders: %s '%s' and %s '%s')", new Object[] { current.getVersion(), current.getType(), current.getPhysicalLocation(), next.getType(), next.getPhysicalLocation() })); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\resolver\CompositeMigrationResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */