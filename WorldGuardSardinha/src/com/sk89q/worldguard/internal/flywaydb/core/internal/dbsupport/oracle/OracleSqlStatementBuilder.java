/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.oracle;
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
/*    */ public class OracleSqlStatementBuilder
/*    */   extends SqlStatementBuilder
/*    */ {
/* 29 */   private static final Delimiter PLSQL_DELIMITER = new Delimiter("/", true);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 34 */   private String statementStart = "";
/*    */ 
/*    */   
/*    */   protected Delimiter changeDelimiterIfNecessary(String line, Delimiter delimiter) {
/* 38 */     if (line.matches("DECLARE|DECLARE\\s.*") || line.matches("BEGIN|BEGIN\\s.*")) {
/* 39 */       return PLSQL_DELIMITER;
/*    */     }
/*    */     
/* 42 */     if (StringUtils.countOccurrencesOf(this.statementStart, " ") < 4) {
/* 43 */       this.statementStart += line;
/* 44 */       this.statementStart += " ";
/* 45 */       this.statementStart = this.statementStart.replaceAll("\\s+", " ");
/*    */     } 
/*    */     
/* 48 */     if (this.statementStart.startsWith("CREATE FUNCTION") || this.statementStart.startsWith("CREATE PROCEDURE") || this.statementStart.startsWith("CREATE PACKAGE") || this.statementStart.startsWith("CREATE TYPE") || this.statementStart.startsWith("CREATE TRIGGER") || this.statementStart.startsWith("CREATE OR REPLACE FUNCTION") || this.statementStart.startsWith("CREATE OR REPLACE PROCEDURE") || this.statementStart.startsWith("CREATE OR REPLACE PACKAGE") || this.statementStart.startsWith("CREATE OR REPLACE TYPE") || this.statementStart.startsWith("CREATE OR REPLACE TRIGGER"))
/*    */     {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 58 */       return PLSQL_DELIMITER;
/*    */     }
/*    */     
/* 61 */     return delimiter;
/*    */   }
/*    */ 
/*    */   
/*    */   protected String removeCharsetCasting(String token) {
/* 66 */     if (token.startsWith("N'")) {
/* 67 */       return token.substring(1);
/*    */     }
/* 69 */     return token;
/*    */   }
/*    */ 
/*    */   
/*    */   protected String simplifyLine(String line) {
/* 74 */     String simplifiedQQuotes = StringUtils.replaceAll(StringUtils.replaceAll(line, "q'(", "q'["), ")'", "]'");
/* 75 */     return super.simplifyLine(simplifiedQQuotes);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String extractAlternateOpenQuote(String token) {
/* 80 */     if (token.startsWith("Q'") && token.length() >= 3) {
/* 81 */       return token.substring(0, 3);
/*    */     }
/* 83 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected String computeAlternateCloseQuote(String openQuote) {
/* 88 */     char specialChar = openQuote.charAt(2);
/* 89 */     switch (specialChar) {
/*    */       case '[':
/* 91 */         return "]'";
/*    */       case '(':
/* 93 */         return ")'";
/*    */       case '{':
/* 95 */         return "}'";
/*    */       case '<':
/* 97 */         return ">'";
/*    */     } 
/* 99 */     return specialChar + "'";
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\oracle\OracleSqlStatementBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */