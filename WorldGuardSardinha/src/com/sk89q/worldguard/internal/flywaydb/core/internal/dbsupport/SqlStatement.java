/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport;
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
/*    */ public class SqlStatement
/*    */ {
/*    */   private int lineNumber;
/*    */   private String sql;
/*    */   
/*    */   public SqlStatement(int lineNumber, String sql) {
/* 39 */     this.lineNumber = lineNumber;
/* 40 */     this.sql = sql;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLineNumber() {
/* 47 */     return this.lineNumber;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSql() {
/* 54 */     return this.sql;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\SqlStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */