/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.postgresql;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.SqlStatementBuilder;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
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
/*    */ public class PostgreSQLSqlStatementBuilder
/*    */   extends SqlStatementBuilder
/*    */ {
/*    */   static final String DOLLAR_QUOTE_REGEX = "\\$[A-Za-z0-9_]*\\$.*";
/*    */   
/*    */   protected String extractAlternateOpenQuote(String token) {
/* 35 */     Matcher matcher = Pattern.compile("\\$[A-Za-z0-9_]*\\$.*").matcher(token);
/* 36 */     if (matcher.find()) {
/* 37 */       return token.substring(matcher.start(), matcher.end());
/*    */     }
/* 39 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\postgresql\PostgreSQLSqlStatementBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */