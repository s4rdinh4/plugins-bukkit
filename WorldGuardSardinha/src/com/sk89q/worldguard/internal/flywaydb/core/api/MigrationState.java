/*     */ package com.sk89q.worldguard.internal.flywaydb.core.api;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum MigrationState
/*     */ {
/*  25 */   PENDING("Pending", true, false, false),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  30 */   ABOVE_TARGET(">Target", true, false, false),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  35 */   PREINIT("PreInit", true, false, false),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   IGNORED("Ignored", true, false, false),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   MISSING_SUCCESS("Missing", false, true, false),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   MISSING_FAILED("MisFail", false, true, true),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   SUCCESS("Success", true, true, false),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   FAILED("Failed", true, true, true),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   OUT_OF_ORDER("OutOrder", true, true, false),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   FUTURE_SUCCESS("Future", false, true, false),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   FUTURE_FAILED("FutFail", false, true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String displayName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean resolved;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean applied;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean failed;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MigrationState(String displayName, boolean resolved, boolean applied, boolean failed) {
/* 134 */     this.displayName = displayName;
/* 135 */     this.resolved = resolved;
/* 136 */     this.applied = applied;
/* 137 */     this.failed = failed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayName() {
/* 144 */     return this.displayName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isApplied() {
/* 151 */     return this.applied;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isResolved() {
/* 158 */     return this.resolved;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFailed() {
/* 165 */     return this.failed;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\api\MigrationState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */