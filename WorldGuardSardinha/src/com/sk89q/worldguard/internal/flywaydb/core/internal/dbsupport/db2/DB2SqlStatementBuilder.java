/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.db2;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Delimiter;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.SqlStatementBuilder;
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
/*    */ public class DB2SqlStatementBuilder
/*    */   extends SqlStatementBuilder
/*    */ {
/*    */   private boolean insideBeginEndBlock;
/* 34 */   private String statementStart = "";
/*    */ 
/*    */   
/*    */   protected Delimiter changeDelimiterIfNecessary(String line, Delimiter delimiter) {
/* 38 */     if (StringUtils.countOccurrencesOf(this.statementStart, " ") < 4) {
/* 39 */       this.statementStart += line;
/* 40 */       this.statementStart += " ";
/*    */     } 
/*    */     
/* 43 */     if (this.statementStart.startsWith("CREATE FUNCTION") || this.statementStart.startsWith("CREATE PROCEDURE") || this.statementStart.startsWith("CREATE TRIGGER") || this.statementStart.startsWith("CREATE OR REPLACE FUNCTION") || this.statementStart.startsWith("CREATE OR REPLACE PROCEDURE") || this.statementStart.startsWith("CREATE OR REPLACE TRIGGER")) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 49 */       if (line.startsWith("BEGIN")) {
/* 50 */         this.insideBeginEndBlock = true;
/*    */       }
/*    */       
/* 53 */       if (line.endsWith("END;")) {
/* 54 */         this.insideBeginEndBlock = false;
/*    */       }
/*    */     } 
/*    */     
/* 58 */     if (this.insideBeginEndBlock) {
/* 59 */       return null;
/*    */     }
/* 61 */     return getDefaultDelimiter();
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\db2\DB2SqlStatementBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */