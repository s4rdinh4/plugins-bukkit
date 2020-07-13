/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.sqlserver;
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
/*    */ public class SQLServerSqlStatementBuilder
/*    */   extends SqlStatementBuilder
/*    */ {
/*    */   protected Delimiter getDefaultDelimiter() {
/* 27 */     return new Delimiter("GO", true);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String extractAlternateOpenQuote(String token) {
/* 32 */     if (token.startsWith("N'")) {
/* 33 */       return "N'";
/*    */     }
/* 35 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected String computeAlternateCloseQuote(String openQuote) {
/* 40 */     return "'";
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\sqlserver\SQLServerSqlStatementBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */