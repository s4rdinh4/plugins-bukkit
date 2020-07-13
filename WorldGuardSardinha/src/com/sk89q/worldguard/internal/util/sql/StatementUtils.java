/*    */ package com.sk89q.worldguard.internal.util.sql;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class StatementUtils
/*    */ {
/*    */   public static String preparePlaceHolders(int length) {
/* 37 */     StringBuilder builder = new StringBuilder();
/* 38 */     for (int i = 0; i < length; ) {
/* 39 */       builder.append("?");
/* 40 */       if (++i < length) {
/* 41 */         builder.append(",");
/*    */       }
/*    */     } 
/* 44 */     return builder.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setValues(PreparedStatement preparedStatement, String... values) throws SQLException {
/* 55 */     for (int i = 0; i < values.length; i++)
/* 56 */       preparedStatement.setString(i + 1, values[i]); 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\interna\\util\sql\StatementUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */