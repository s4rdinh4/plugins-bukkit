/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.info;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationInfo;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.DateUtils;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.StringUtils;
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
/*    */ public class MigrationInfoDumper
/*    */ {
/*    */   private static final int MINIMUM_CONSOLE_WIDTH = 80;
/*    */   
/*    */   public static String dumpToAsciiTable(MigrationInfo[] migrationInfos) {
/* 45 */     return dumpToAsciiTable(migrationInfos, 80);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String dumpToAsciiTable(MigrationInfo[] migrationInfos, int consoleWidth) {
/* 56 */     int descriptionWidth = Math.max(consoleWidth, 80) - 54;
/*    */     
/* 58 */     StringBuilder table = new StringBuilder();
/*    */     
/* 60 */     table.append("+----------------+-").append(StringUtils.trimOrPad("", descriptionWidth, '-')).append("-+---------------------+---------+\n");
/* 61 */     table.append("| Version        | ").append(StringUtils.trimOrPad("Description", descriptionWidth)).append(" | Installed on        | State   |\n");
/* 62 */     table.append("+----------------+-").append(StringUtils.trimOrPad("", descriptionWidth, '-')).append("-+---------------------+---------+\n");
/*    */     
/* 64 */     if (migrationInfos.length == 0) {
/* 65 */       table.append("| No migrations found                                                         |\n");
/*    */     } else {
/* 67 */       for (MigrationInfo migrationInfo : migrationInfos) {
/* 68 */         table.append("| ").append(StringUtils.trimOrPad(migrationInfo.getVersion().toString(), 14));
/* 69 */         table.append(" | ").append(StringUtils.trimOrPad(migrationInfo.getDescription(), descriptionWidth));
/* 70 */         table.append(" | ").append(StringUtils.trimOrPad(DateUtils.formatDateAsIsoString(migrationInfo.getInstalledOn()), 19));
/* 71 */         table.append(" | ").append(StringUtils.trimOrPad(migrationInfo.getState().getDisplayName(), 7));
/* 72 */         table.append(" |\n");
/*    */       } 
/*    */     } 
/*    */     
/* 76 */     table.append("+----------------+-").append(StringUtils.trimOrPad("", descriptionWidth, '-')).append("-+---------------------+---------+");
/* 77 */     return table.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\info\MigrationInfoDumper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */