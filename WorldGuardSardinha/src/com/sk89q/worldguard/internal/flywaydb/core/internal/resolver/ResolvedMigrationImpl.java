/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.resolver;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationType;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationVersion;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.resolver.MigrationExecutor;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.resolver.ResolvedMigration;
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
/*     */ public class ResolvedMigrationImpl
/*     */   implements ResolvedMigration
/*     */ {
/*     */   private MigrationVersion version;
/*     */   private String description;
/*     */   private String script;
/*     */   private Integer checksum;
/*     */   private MigrationType type;
/*     */   private String physicalLocation;
/*     */   private MigrationExecutor executor;
/*     */   
/*     */   public MigrationVersion getVersion() {
/*  64 */     return this.version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVersion(MigrationVersion version) {
/*  71 */     this.version = version;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  76 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDescription(String description) {
/*  83 */     this.description = description;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getScript() {
/*  88 */     return this.script;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScript(String script) {
/*  95 */     this.script = script;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getChecksum() {
/* 100 */     return this.checksum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChecksum(Integer checksum) {
/* 107 */     this.checksum = checksum;
/*     */   }
/*     */ 
/*     */   
/*     */   public MigrationType getType() {
/* 112 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setType(MigrationType type) {
/* 119 */     this.type = type;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPhysicalLocation() {
/* 124 */     return this.physicalLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPhysicalLocation(String physicalLocation) {
/* 131 */     this.physicalLocation = physicalLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public MigrationExecutor getExecutor() {
/* 136 */     return this.executor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExecutor(MigrationExecutor executor) {
/* 143 */     this.executor = executor;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(ResolvedMigrationImpl o) {
/* 148 */     return this.version.compareTo(o.version);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 154 */     if (this == o) return true; 
/* 155 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 157 */     ResolvedMigrationImpl migration = (ResolvedMigrationImpl)o;
/*     */     
/* 159 */     if ((this.checksum != null) ? !this.checksum.equals(migration.checksum) : (migration.checksum != null)) return false; 
/* 160 */     if ((this.description != null) ? !this.description.equals(migration.description) : (migration.description != null))
/* 161 */       return false; 
/* 162 */     if ((this.physicalLocation != null) ? !this.physicalLocation.equals(migration.physicalLocation) : (migration.physicalLocation != null))
/* 163 */       return false; 
/* 164 */     if ((this.script != null) ? !this.script.equals(migration.script) : (migration.script != null)) return false; 
/* 165 */     if (this.type != migration.type) return false; 
/* 166 */     return this.version.equals(migration.version);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 171 */     int result = this.version.hashCode();
/* 172 */     result = 31 * result + ((this.description != null) ? this.description.hashCode() : 0);
/* 173 */     result = 31 * result + ((this.script != null) ? this.script.hashCode() : 0);
/* 174 */     result = 31 * result + ((this.checksum != null) ? this.checksum.hashCode() : 0);
/* 175 */     result = 31 * result + this.type.hashCode();
/* 176 */     result = 31 * result + ((this.physicalLocation != null) ? this.physicalLocation.hashCode() : 0);
/* 177 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\resolver\ResolvedMigrationImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */