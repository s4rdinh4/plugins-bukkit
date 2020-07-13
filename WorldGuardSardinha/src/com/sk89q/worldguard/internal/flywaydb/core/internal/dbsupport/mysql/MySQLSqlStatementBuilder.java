/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.mysql;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Delimiter;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.SqlStatementBuilder;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.StringUtils;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MySQLSqlStatementBuilder
/*     */   extends SqlStatementBuilder
/*     */ {
/*     */   private static final String DELIMITER_KEYWORD = "DELIMITER";
/*  32 */   private final String[] charSets = new String[] { "ARMSCII8", "ASCII", "BIG5", "BINARY", "CP1250", "CP1251", "CP1256", "CP1257", "CP850", "CP852", "CP866", "CP932", "DEC8", "EUCJPMS", "EUCKR", "GB2312", "GBK", "GEOSTD8", "GREEK", "HEBREW", "HP8", "KEYBCS2", "KOI8R", "KOI8U", "LATIN1", "LATIN2", "LATIN5", "LATIN7", "MACCE", "MACROMAN", "SJIS", "SWE7", "TIS620", "UCS2", "UJIS", "UTF8" };
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isInMultiLineCommentDirective = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Delimiter extractNewDelimiterFromLine(String line) {
/*  42 */     if (line.toUpperCase().startsWith("DELIMITER")) {
/*  43 */       return new Delimiter(line.substring("DELIMITER".length()).trim(), false);
/*     */     }
/*     */     
/*  46 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Delimiter changeDelimiterIfNecessary(String line, Delimiter delimiter) {
/*  51 */     if (line.toUpperCase().startsWith("DELIMITER")) {
/*  52 */       return new Delimiter(line.substring("DELIMITER".length()).trim(), false);
/*     */     }
/*     */     
/*  55 */     return delimiter;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCommentDirective(String line) {
/*  61 */     if (line.matches("^" + Pattern.quote("/*!") + "\\d{5} .*" + Pattern.quote("*/") + "\\s*;?")) {
/*  62 */       return true;
/*     */     }
/*     */     
/*  65 */     if (this.isInMultiLineCommentDirective && line.matches(".*" + Pattern.quote("*/") + "\\s*;?")) {
/*  66 */       this.isInMultiLineCommentDirective = false;
/*  67 */       return true;
/*     */     } 
/*     */     
/*  70 */     if (line.matches("^" + Pattern.quote("/*!") + "\\d{5} .*")) {
/*  71 */       this.isInMultiLineCommentDirective = true;
/*  72 */       return true;
/*     */     } 
/*  74 */     return this.isInMultiLineCommentDirective;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSingleLineComment(String line) {
/*  79 */     return (line.startsWith("--") || line.startsWith("#"));
/*     */   }
/*     */ 
/*     */   
/*     */   protected String removeEscapedQuotes(String token) {
/*  84 */     String noEscapedBackslashes = StringUtils.replaceAll(token, "\\\\", "");
/*  85 */     String noBackslashEscapes = StringUtils.replaceAll(StringUtils.replaceAll(noEscapedBackslashes, "\\'", ""), "\\\"", "");
/*  86 */     return StringUtils.replaceAll(noBackslashEscapes, "''", "");
/*     */   }
/*     */ 
/*     */   
/*     */   protected String removeCharsetCasting(String token) {
/*  91 */     if (token.startsWith("_")) {
/*  92 */       for (String charSet : this.charSets) {
/*  93 */         String cast = "_" + charSet;
/*  94 */         if (token.startsWith(cast)) {
/*  95 */           return token.substring(cast.length());
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 100 */     return token;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String extractAlternateOpenQuote(String token) {
/* 105 */     if (token.startsWith("\"")) {
/* 106 */       return "\"";
/*     */     }
/*     */ 
/*     */     
/* 110 */     if (token.startsWith("B'") && token.length() > 2) {
/* 111 */       return "B'";
/*     */     }
/* 113 */     if (token.startsWith("X'") && token.length() > 2) {
/* 114 */       return "X'";
/*     */     }
/* 116 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String computeAlternateCloseQuote(String openQuote) {
/* 121 */     if ("B'".equals(openQuote) || "X'".equals(openQuote)) {
/* 122 */       return "'";
/*     */     }
/* 124 */     return openQuote;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\mysql\MySQLSqlStatementBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */