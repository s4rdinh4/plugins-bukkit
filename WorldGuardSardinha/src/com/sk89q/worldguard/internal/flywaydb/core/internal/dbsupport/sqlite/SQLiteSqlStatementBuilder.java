/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.sqlite;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Delimiter;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.SqlStatementBuilder;
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
/*    */ public class SQLiteSqlStatementBuilder
/*    */   extends SqlStatementBuilder
/*    */ {
/*    */   private boolean insideBeginEndBlock;
/*    */   
/*    */   protected Delimiter changeDelimiterIfNecessary(String line, Delimiter delimiter) {
/* 32 */     if (line.contains("BEGIN")) {
/* 33 */       this.insideBeginEndBlock = true;
/*    */     }
/*    */     
/* 36 */     if (line.endsWith("END;")) {
/* 37 */       this.insideBeginEndBlock = false;
/*    */     }
/*    */     
/* 40 */     if (this.insideBeginEndBlock) {
/* 41 */       return null;
/*    */     }
/* 43 */     return getDefaultDelimiter();
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\sqlite\SQLiteSqlStatementBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */