/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.info;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationInfo;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationInfoService;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationState;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationType;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationVersion;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.resolver.MigrationResolver;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.resolver.ResolvedMigration;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.metadatatable.AppliedMigration;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.metadatatable.MetaDataTable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
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
/*     */ public class MigrationInfoServiceImpl
/*     */   implements MigrationInfoService
/*     */ {
/*     */   private final MigrationResolver migrationResolver;
/*     */   private final MetaDataTable metaDataTable;
/*     */   private final MigrationVersion target;
/*     */   private boolean outOfOrder;
/*     */   private final boolean pending;
/*     */   private List<MigrationInfoImpl> migrationInfos;
/*     */   
/*     */   public MigrationInfoServiceImpl(MigrationResolver migrationResolver, MetaDataTable metaDataTable, MigrationVersion target, boolean outOfOrder, boolean pending) {
/*  85 */     this.migrationResolver = migrationResolver;
/*  86 */     this.metaDataTable = metaDataTable;
/*  87 */     this.target = target;
/*  88 */     this.outOfOrder = outOfOrder;
/*  89 */     this.pending = pending;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refresh() {
/*  96 */     Collection<ResolvedMigration> availableMigrations = this.migrationResolver.resolveMigrations();
/*  97 */     List<AppliedMigration> appliedMigrations = this.metaDataTable.allAppliedMigrations();
/*     */     
/*  99 */     this.migrationInfos = mergeAvailableAndAppliedMigrations(availableMigrations, appliedMigrations);
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
/*     */   List<MigrationInfoImpl> mergeAvailableAndAppliedMigrations(Collection<ResolvedMigration> resolvedMigrations, List<AppliedMigration> appliedMigrations) {
/* 111 */     MigrationInfoContext context = new MigrationInfoContext();
/* 112 */     context.outOfOrder = this.outOfOrder;
/* 113 */     context.pending = this.pending;
/* 114 */     context.target = this.target;
/*     */     
/* 116 */     Map<MigrationVersion, ResolvedMigration> resolvedMigrationsMap = new TreeMap<MigrationVersion, ResolvedMigration>();
/* 117 */     for (ResolvedMigration resolvedMigration : resolvedMigrations) {
/* 118 */       MigrationVersion version = resolvedMigration.getVersion();
/* 119 */       if (version.compareTo(context.lastResolved) > 0) {
/* 120 */         context.lastResolved = version;
/*     */       }
/* 122 */       resolvedMigrationsMap.put(version, resolvedMigration);
/*     */     } 
/*     */     
/* 125 */     Map<MigrationVersion, AppliedMigration> appliedMigrationsMap = new TreeMap<MigrationVersion, AppliedMigration>();
/* 126 */     for (AppliedMigration appliedMigration : appliedMigrations) {
/* 127 */       MigrationVersion version = appliedMigration.getVersion();
/* 128 */       if (version.compareTo(context.lastApplied) > 0) {
/* 129 */         context.lastApplied = version;
/*     */       }
/* 131 */       if (appliedMigration.getType() == MigrationType.SCHEMA) {
/* 132 */         context.schema = version;
/*     */       }
/* 134 */       if (appliedMigration.getType() == MigrationType.INIT) {
/* 135 */         context.init = version;
/*     */       }
/* 137 */       appliedMigrationsMap.put(version, appliedMigration);
/*     */     } 
/*     */     
/* 140 */     Set<MigrationVersion> allVersions = new HashSet<MigrationVersion>();
/* 141 */     allVersions.addAll(resolvedMigrationsMap.keySet());
/* 142 */     allVersions.addAll(appliedMigrationsMap.keySet());
/*     */     
/* 144 */     List<MigrationInfoImpl> migrationInfos = new ArrayList<MigrationInfoImpl>();
/* 145 */     for (MigrationVersion version : allVersions) {
/* 146 */       ResolvedMigration resolvedMigration = resolvedMigrationsMap.get(version);
/* 147 */       AppliedMigration appliedMigration = appliedMigrationsMap.get(version);
/* 148 */       migrationInfos.add(new MigrationInfoImpl(resolvedMigration, appliedMigration, context));
/*     */     } 
/*     */     
/* 151 */     Collections.sort(migrationInfos);
/*     */     
/* 153 */     return migrationInfos;
/*     */   }
/*     */   
/*     */   public MigrationInfo[] all() {
/* 157 */     return this.migrationInfos.<MigrationInfo>toArray((MigrationInfo[])new MigrationInfoImpl[this.migrationInfos.size()]);
/*     */   }
/*     */   
/*     */   public MigrationInfo current() {
/* 161 */     for (int i = this.migrationInfos.size() - 1; i >= 0; i--) {
/* 162 */       MigrationInfo migrationInfo = this.migrationInfos.get(i);
/* 163 */       if (migrationInfo.getState().isApplied()) {
/* 164 */         return migrationInfo;
/*     */       }
/*     */     } 
/*     */     
/* 168 */     return null;
/*     */   }
/*     */   
/*     */   public MigrationInfoImpl[] pending() {
/* 172 */     List<MigrationInfoImpl> pendingMigrations = new ArrayList<MigrationInfoImpl>();
/* 173 */     for (MigrationInfoImpl migrationInfo : this.migrationInfos) {
/* 174 */       if (MigrationState.PENDING == migrationInfo.getState()) {
/* 175 */         pendingMigrations.add(migrationInfo);
/*     */       }
/*     */     } 
/*     */     
/* 179 */     return pendingMigrations.<MigrationInfoImpl>toArray(new MigrationInfoImpl[pendingMigrations.size()]);
/*     */   }
/*     */   
/*     */   public MigrationInfo[] applied() {
/* 183 */     List<MigrationInfo> appliedMigrations = new ArrayList<MigrationInfo>();
/* 184 */     for (MigrationInfo migrationInfo : this.migrationInfos) {
/* 185 */       if (migrationInfo.getState().isApplied()) {
/* 186 */         appliedMigrations.add(migrationInfo);
/*     */       }
/*     */     } 
/*     */     
/* 190 */     return appliedMigrations.<MigrationInfo>toArray(new MigrationInfo[appliedMigrations.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MigrationInfo[] resolved() {
/* 199 */     List<MigrationInfo> resolvedMigrations = new ArrayList<MigrationInfo>();
/* 200 */     for (MigrationInfo migrationInfo : this.migrationInfos) {
/* 201 */       if (migrationInfo.getState().isResolved()) {
/* 202 */         resolvedMigrations.add(migrationInfo);
/*     */       }
/*     */     } 
/*     */     
/* 206 */     return resolvedMigrations.<MigrationInfo>toArray(new MigrationInfo[resolvedMigrations.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MigrationInfo[] failed() {
/* 215 */     List<MigrationInfo> failedMigrations = new ArrayList<MigrationInfo>();
/* 216 */     for (MigrationInfo migrationInfo : this.migrationInfos) {
/* 217 */       if (migrationInfo.getState().isFailed()) {
/* 218 */         failedMigrations.add(migrationInfo);
/*     */       }
/*     */     } 
/*     */     
/* 222 */     return failedMigrations.<MigrationInfo>toArray(new MigrationInfo[failedMigrations.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MigrationInfo[] future() {
/* 231 */     List<MigrationInfo> futureMigrations = new ArrayList<MigrationInfo>();
/* 232 */     for (MigrationInfo migrationInfo : this.migrationInfos) {
/* 233 */       if (migrationInfo.getState() == MigrationState.FUTURE_SUCCESS || migrationInfo.getState() == MigrationState.FUTURE_FAILED)
/*     */       {
/* 235 */         futureMigrations.add(migrationInfo);
/*     */       }
/*     */     } 
/*     */     
/* 239 */     return futureMigrations.<MigrationInfo>toArray(new MigrationInfo[futureMigrations.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MigrationInfo[] outOfOrder() {
/* 248 */     List<MigrationInfo> outOfOrderMigrations = new ArrayList<MigrationInfo>();
/* 249 */     for (MigrationInfo migrationInfo : this.migrationInfos) {
/* 250 */       if (migrationInfo.getState() == MigrationState.OUT_OF_ORDER) {
/* 251 */         outOfOrderMigrations.add(migrationInfo);
/*     */       }
/*     */     } 
/*     */     
/* 255 */     return outOfOrderMigrations.<MigrationInfo>toArray(new MigrationInfo[outOfOrderMigrations.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String validate() {
/* 264 */     for (MigrationInfoImpl migrationInfo : this.migrationInfos) {
/* 265 */       String message = migrationInfo.validate();
/* 266 */       if (message != null) {
/* 267 */         return message;
/*     */       }
/*     */     } 
/* 270 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\info\MigrationInfoServiceImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */