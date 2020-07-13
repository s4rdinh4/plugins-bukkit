/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.hsql;
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
/*    */ public class HsqlSqlStatementBuilder
/*    */   extends SqlStatementBuilder
/*    */ {
/*    */   private boolean insideAtomicBlock;
/*    */   
/*    */   protected Delimiter changeDelimiterIfNecessary(String line, Delimiter delimiter) {
/* 32 */     if (line.contains("BEGIN ATOMIC")) {
/* 33 */       this.insideAtomicBlock = true;
/*    */     }
/*    */     
/* 36 */     if (line.endsWith("END;")) {
/* 37 */       this.insideAtomicBlock = false;
/*    */     }
/*    */     
/* 40 */     if (this.insideAtomicBlock) {
/* 41 */       return null;
/*    */     }
/* 43 */     return getDefaultDelimiter();
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\hsql\HsqlSqlStatementBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */