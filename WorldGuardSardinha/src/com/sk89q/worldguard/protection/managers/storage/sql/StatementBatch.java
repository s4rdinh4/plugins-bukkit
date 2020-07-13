/*    */ package com.sk89q.worldguard.protection.managers.storage.sql;
/*    */ 
/*    */ import java.sql.PreparedStatement;
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
/*    */ class StatementBatch
/*    */ {
/*    */   public static final int MAX_BATCH_SIZE = 100;
/*    */   private final PreparedStatement stmt;
/*    */   private final int batchSize;
/* 31 */   private int count = 0;
/*    */   
/*    */   StatementBatch(PreparedStatement stmt, int batchSize) {
/* 34 */     this.stmt = stmt;
/* 35 */     this.batchSize = batchSize;
/*    */   }
/*    */   
/*    */   public void addBatch() throws SQLException {
/* 39 */     this.stmt.addBatch();
/* 40 */     this.count++;
/* 41 */     if (this.count > this.batchSize) {
/* 42 */       this.stmt.executeBatch();
/* 43 */       this.count = 0;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void executeRemaining() throws SQLException {
/* 48 */     if (this.count > 0) {
/* 49 */       this.count = 0;
/* 50 */       this.stmt.executeBatch();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\storage\sql\StatementBatch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */