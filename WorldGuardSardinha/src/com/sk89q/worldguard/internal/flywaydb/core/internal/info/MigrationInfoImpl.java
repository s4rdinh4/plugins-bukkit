/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.info;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationInfo;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationState;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationType;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationVersion;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.resolver.ResolvedMigration;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.metadatatable.AppliedMigration;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.ObjectUtils;
/*     */ import java.util.Date;
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
/*     */ public class MigrationInfoImpl
/*     */   implements MigrationInfo
/*     */ {
/*     */   private final ResolvedMigration resolvedMigration;
/*     */   private final AppliedMigration appliedMigration;
/*     */   private final MigrationInfoContext context;
/*     */   
/*     */   public MigrationInfoImpl(ResolvedMigration resolvedMigration, AppliedMigration appliedMigration, MigrationInfoContext context) {
/*  57 */     this.resolvedMigration = resolvedMigration;
/*  58 */     this.appliedMigration = appliedMigration;
/*  59 */     this.context = context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResolvedMigration getResolvedMigration() {
/*  66 */     return this.resolvedMigration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AppliedMigration getAppliedMigration() {
/*  73 */     return this.appliedMigration;
/*     */   }
/*     */   
/*     */   public MigrationType getType() {
/*  77 */     if (this.appliedMigration != null) {
/*  78 */       return this.appliedMigration.getType();
/*     */     }
/*  80 */     return this.resolvedMigration.getType();
/*     */   }
/*     */   
/*     */   public Integer getChecksum() {
/*  84 */     if (this.appliedMigration != null) {
/*  85 */       return this.appliedMigration.getChecksum();
/*     */     }
/*  87 */     return this.resolvedMigration.getChecksum();
/*     */   }
/*     */   
/*     */   public MigrationVersion getVersion() {
/*  91 */     if (this.appliedMigration != null) {
/*  92 */       return this.appliedMigration.getVersion();
/*     */     }
/*  94 */     return this.resolvedMigration.getVersion();
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  98 */     if (this.appliedMigration != null) {
/*  99 */       return this.appliedMigration.getDescription();
/*     */     }
/* 101 */     return this.resolvedMigration.getDescription();
/*     */   }
/*     */   
/*     */   public String getScript() {
/* 105 */     if (this.appliedMigration != null) {
/* 106 */       return this.appliedMigration.getScript();
/*     */     }
/* 108 */     return this.resolvedMigration.getScript();
/*     */   }
/*     */   
/*     */   public MigrationState getState() {
/* 112 */     if (this.appliedMigration == null) {
/* 113 */       if (this.resolvedMigration.getVersion().compareTo(this.context.init) < 0) {
/* 114 */         return MigrationState.PREINIT;
/*     */       }
/* 116 */       if (this.resolvedMigration.getVersion().compareTo(this.context.target) > 0) {
/* 117 */         return MigrationState.ABOVE_TARGET;
/*     */       }
/* 119 */       if (this.resolvedMigration.getVersion().compareTo(this.context.lastApplied) < 0 && !this.context.outOfOrder) {
/* 120 */         return MigrationState.IGNORED;
/*     */       }
/* 122 */       return MigrationState.PENDING;
/*     */     } 
/*     */     
/* 125 */     if (this.resolvedMigration == null) {
/* 126 */       if (MigrationType.SCHEMA == this.appliedMigration.getType()) {
/* 127 */         return MigrationState.SUCCESS;
/*     */       }
/*     */       
/* 130 */       if (MigrationType.INIT == this.appliedMigration.getType()) {
/* 131 */         return MigrationState.SUCCESS;
/*     */       }
/*     */       
/* 134 */       if (getVersion().compareTo(this.context.lastResolved) < 0) {
/* 135 */         if (this.appliedMigration.isSuccess()) {
/* 136 */           return MigrationState.MISSING_SUCCESS;
/*     */         }
/* 138 */         return MigrationState.MISSING_FAILED;
/*     */       } 
/* 140 */       if (getVersion().compareTo(this.context.lastResolved) > 0) {
/* 141 */         if (this.appliedMigration.isSuccess()) {
/* 142 */           return MigrationState.FUTURE_SUCCESS;
/*     */         }
/* 144 */         return MigrationState.FUTURE_FAILED;
/*     */       } 
/*     */     } 
/*     */     
/* 148 */     if (this.appliedMigration.isSuccess()) {
/* 149 */       if (this.appliedMigration.getVersionRank() == this.appliedMigration.getInstalledRank()) {
/* 150 */         return MigrationState.SUCCESS;
/*     */       }
/* 152 */       return MigrationState.OUT_OF_ORDER;
/*     */     } 
/* 154 */     return MigrationState.FAILED;
/*     */   }
/*     */   
/*     */   public Date getInstalledOn() {
/* 158 */     if (this.appliedMigration != null) {
/* 159 */       return this.appliedMigration.getInstalledOn();
/*     */     }
/* 161 */     return null;
/*     */   }
/*     */   
/*     */   public Integer getExecutionTime() {
/* 165 */     if (this.appliedMigration != null) {
/* 166 */       return Integer.valueOf(this.appliedMigration.getExecutionTime());
/*     */     }
/* 168 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String validate() {
/* 177 */     if (this.resolvedMigration == null && this.appliedMigration.getType() != MigrationType.SCHEMA && this.appliedMigration.getType() != MigrationType.INIT)
/*     */     {
/*     */       
/* 180 */       return "Detected applied migration missing on the classpath: " + getVersion();
/*     */     }
/*     */     
/* 183 */     if ((!this.context.pending && MigrationState.PENDING == getState()) || MigrationState.IGNORED == getState())
/*     */     {
/* 185 */       return "Migration on the classpath has not been applied to database: " + getVersion();
/*     */     }
/*     */     
/* 188 */     if (this.resolvedMigration != null && this.appliedMigration != null && 
/* 189 */       getVersion().compareTo(this.context.init) > 0) {
/* 190 */       if (this.resolvedMigration.getType() != this.appliedMigration.getType()) {
/* 191 */         return String.format("Migration Type mismatch for migration %s: DB=%s, Classpath=%s", new Object[] { this.appliedMigration.getScript(), this.appliedMigration.getType(), this.resolvedMigration.getType() });
/*     */       }
/*     */       
/* 194 */       if (!ObjectUtils.nullSafeEquals(this.resolvedMigration.getChecksum(), this.appliedMigration.getChecksum())) {
/* 195 */         return String.format("Migration Checksum mismatch for migration %s: DB=%s, Classpath=%s", new Object[] { this.appliedMigration.getScript(), this.appliedMigration.getChecksum(), this.resolvedMigration.getChecksum() });
/*     */       }
/*     */       
/* 198 */       if (!this.resolvedMigration.getDescription().equals(this.appliedMigration.getDescription())) {
/* 199 */         return String.format("Migration Description mismatch for migration %s: DB=%s, Classpath=%s", new Object[] { this.appliedMigration.getScript(), this.appliedMigration.getDescription(), this.resolvedMigration.getDescription() });
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 204 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(MigrationInfo o) {
/* 209 */     return getVersion().compareTo(o.getVersion());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 215 */     if (this == o) return true; 
/* 216 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 218 */     MigrationInfoImpl that = (MigrationInfoImpl)o;
/*     */     
/* 220 */     if ((this.appliedMigration != null) ? !this.appliedMigration.equals(that.appliedMigration) : (that.appliedMigration != null))
/* 221 */       return false; 
/* 222 */     if (!this.context.equals(that.context)) return false; 
/* 223 */     if ((this.resolvedMigration != null) ? !this.resolvedMigration.equals(that.resolvedMigration) : (that.resolvedMigration != null)) return false;
/*     */   
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 228 */     int result = (this.resolvedMigration != null) ? this.resolvedMigration.hashCode() : 0;
/* 229 */     result = 31 * result + ((this.appliedMigration != null) ? this.appliedMigration.hashCode() : 0);
/* 230 */     result = 31 * result + this.context.hashCode();
/* 231 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\info\MigrationInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */