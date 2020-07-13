/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.metadatatable;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationType;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationVersion;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AppliedMigration
/*     */   implements Comparable<AppliedMigration>
/*     */ {
/*     */   private int versionRank;
/*     */   private int installedRank;
/*     */   private MigrationVersion version;
/*     */   private String description;
/*     */   private MigrationType type;
/*     */   private String script;
/*     */   private Integer checksum;
/*     */   private Date installedOn;
/*     */   private String installedBy;
/*     */   private int executionTime;
/*     */   private boolean success;
/*     */   
/*     */   public AppliedMigration(int versionRank, int installedRank, MigrationVersion version, String description, MigrationType type, String script, Integer checksum, Date installedOn, String installedBy, int executionTime, boolean success) {
/* 100 */     this.versionRank = versionRank;
/* 101 */     this.installedRank = installedRank;
/* 102 */     this.version = version;
/* 103 */     this.description = description;
/* 104 */     this.type = type;
/* 105 */     this.script = script;
/* 106 */     this.checksum = checksum;
/* 107 */     this.installedOn = installedOn;
/* 108 */     this.installedBy = installedBy;
/* 109 */     this.executionTime = executionTime;
/* 110 */     this.success = success;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public AppliedMigration(MigrationVersion version, String description, MigrationType type, String script, Integer checksum, int executionTime, boolean success) {
/* 126 */     this.version = version;
/* 127 */     this.description = abbreviateDescription(description);
/* 128 */     this.type = type;
/* 129 */     this.script = abbreviateScript(script);
/* 130 */     this.checksum = checksum;
/* 131 */     this.executionTime = executionTime;
/* 132 */     this.success = success;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String abbreviateDescription(String description) {
/* 142 */     if (description == null) {
/* 143 */       return null;
/*     */     }
/*     */     
/* 146 */     if (description.length() <= 200) {
/* 147 */       return description;
/*     */     }
/*     */     
/* 150 */     return description.substring(0, 197) + "...";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String abbreviateScript(String script) {
/* 160 */     if (script == null) {
/* 161 */       return null;
/*     */     }
/*     */     
/* 164 */     if (script.length() <= 1000) {
/* 165 */       return script;
/*     */     }
/*     */     
/* 168 */     return "..." + script.substring(3, 1000);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVersionRank() {
/* 175 */     return this.versionRank;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInstalledRank() {
/* 182 */     return this.installedRank;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MigrationVersion getVersion() {
/* 189 */     return this.version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 196 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MigrationType getType() {
/* 203 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getScript() {
/* 210 */     return this.script;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getChecksum() {
/* 217 */     return this.checksum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getInstalledOn() {
/* 224 */     return this.installedOn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInstalledBy() {
/* 231 */     return this.installedBy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExecutionTime() {
/* 238 */     return this.executionTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSuccess() {
/* 245 */     return this.success;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 251 */     if (this == o) return true; 
/* 252 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 254 */     AppliedMigration that = (AppliedMigration)o;
/*     */     
/* 256 */     if (this.executionTime != that.executionTime) return false; 
/* 257 */     if (this.installedRank != that.installedRank) return false; 
/* 258 */     if (this.success != that.success) return false; 
/* 259 */     if (this.versionRank != that.versionRank) return false; 
/* 260 */     if ((this.checksum != null) ? !this.checksum.equals(that.checksum) : (that.checksum != null)) return false; 
/* 261 */     if (!this.description.equals(that.description)) return false; 
/* 262 */     if ((this.installedBy != null) ? !this.installedBy.equals(that.installedBy) : (that.installedBy != null)) return false; 
/* 263 */     if ((this.installedOn != null) ? !this.installedOn.equals(that.installedOn) : (that.installedOn != null)) return false; 
/* 264 */     if (!this.script.equals(that.script)) return false; 
/* 265 */     if (this.type != that.type) return false; 
/* 266 */     return this.version.equals(that.version);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 271 */     int result = this.versionRank;
/* 272 */     result = 31 * result + this.installedRank;
/* 273 */     result = 31 * result + this.version.hashCode();
/* 274 */     result = 31 * result + this.description.hashCode();
/* 275 */     result = 31 * result + this.type.hashCode();
/* 276 */     result = 31 * result + this.script.hashCode();
/* 277 */     result = 31 * result + ((this.checksum != null) ? this.checksum.hashCode() : 0);
/* 278 */     result = 31 * result + ((this.installedOn != null) ? this.installedOn.hashCode() : 0);
/* 279 */     result = 31 * result + ((this.installedBy != null) ? this.installedBy.hashCode() : 0);
/* 280 */     result = 31 * result + this.executionTime;
/* 281 */     result = 31 * result + (this.success ? 1 : 0);
/* 282 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(AppliedMigration o) {
/* 287 */     return this.version.compareTo(o.version);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\metadatatable\AppliedMigration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */