/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.resolver;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationVersion;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.Pair;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MigrationInfoHelper
/*    */ {
/*    */   public static Pair<MigrationVersion, String> extractVersionAndDescription(String migrationName, String prefix, String separator, String suffix) {
/* 46 */     String cleanMigrationName = migrationName.substring(prefix.length(), migrationName.length() - suffix.length());
/*    */ 
/*    */     
/* 49 */     int descriptionPos = cleanMigrationName.indexOf(separator);
/* 50 */     if (descriptionPos < 0) {
/* 51 */       throw new FlywayException("Wrong migration name format: " + migrationName + "(It should look like this: " + prefix + "1_2" + separator + "Description" + suffix + ")");
/*    */     }
/*    */ 
/*    */     
/* 55 */     String version = cleanMigrationName.substring(0, descriptionPos);
/* 56 */     String description = cleanMigrationName.substring(descriptionPos + separator.length()).replaceAll("_", " ");
/* 57 */     return Pair.of(MigrationVersion.fromVersion(version), description);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\resolver\MigrationInfoHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */