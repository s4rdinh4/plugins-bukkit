/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
/*    */ import java.sql.SQLException;
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
/*    */ public class FlywaySqlScriptException
/*    */   extends FlywayException
/*    */ {
/*    */   private final int lineNumber;
/*    */   private final String statement;
/*    */   
/*    */   public FlywaySqlScriptException(int lineNumber, String statement, SQLException sqlException) {
/* 38 */     super("Error executing statement at line " + lineNumber + ": " + statement, sqlException);
/*    */     
/* 40 */     this.lineNumber = lineNumber;
/* 41 */     this.statement = statement;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLineNumber() {
/* 50 */     return this.lineNumber;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getStatement() {
/* 59 */     return this.statement;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\FlywaySqlScriptException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */