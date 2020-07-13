/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.StringUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ public class SqlStatementBuilder
/*     */ {
/*  30 */   private StringBuilder statement = new StringBuilder();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int lineNumber;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean empty = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean terminated;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean insideQuoteStringLiteral = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean insideAlternateQuoteStringLiteral = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String alternateQuote;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean insideMultiLineComment = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   private Delimiter delimiter = getDefaultDelimiter();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Delimiter getDefaultDelimiter() {
/*  76 */     return new Delimiter(";", false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLineNumber(int lineNumber) {
/*  83 */     this.lineNumber = lineNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDelimiter(Delimiter delimiter) {
/*  90 */     this.delimiter = delimiter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  99 */     return this.empty;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTerminated() {
/* 108 */     return this.terminated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SqlStatement getSqlStatement() {
/* 115 */     return new SqlStatement(this.lineNumber, this.statement.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Delimiter extractNewDelimiterFromLine(String line) {
/* 126 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCommentDirective(String line) {
/* 136 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSingleLineComment(String line) {
/* 146 */     return line.startsWith("--");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLine(String line) {
/* 155 */     if (isEmpty()) {
/* 156 */       this.empty = false;
/*     */     } else {
/* 158 */       this.statement.append("\n");
/*     */     } 
/*     */     
/* 161 */     String lineSimplified = simplifyLine(line);
/*     */     
/* 163 */     if (endsWithOpenMultilineStringLiteral(lineSimplified)) {
/* 164 */       this.statement.append(line);
/*     */       
/*     */       return;
/*     */     } 
/* 168 */     this.delimiter = changeDelimiterIfNecessary(lineSimplified, this.delimiter);
/*     */     
/* 170 */     this.statement.append(line);
/*     */     
/* 172 */     if (lineTerminatesStatement(lineSimplified, this.delimiter)) {
/*     */       
/* 174 */       this.statement = new StringBuilder(stripDelimiter(this.statement.toString(), this.delimiter));
/* 175 */       this.terminated = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String simplifyLine(String line) {
/* 186 */     return line.replaceAll("\\s+", " ").trim().toUpperCase();
/*     */   }
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
/*     */   protected Delimiter changeDelimiterIfNecessary(String line, Delimiter delimiter) {
/* 199 */     return delimiter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean lineTerminatesStatement(String line, Delimiter delimiter) {
/* 210 */     if (delimiter == null) {
/* 211 */       return false;
/*     */     }
/*     */     
/* 214 */     String upperCaseDelimiter = delimiter.getDelimiter().toUpperCase();
/*     */     
/* 216 */     if (delimiter.isAloneOnLine()) {
/* 217 */       return line.equals(upperCaseDelimiter);
/*     */     }
/*     */     
/* 220 */     return line.endsWith(upperCaseDelimiter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String stripDelimiter(String sql, Delimiter delimiter) {
/* 232 */     int lowerCaseIndex = sql.lastIndexOf(delimiter.getDelimiter().toLowerCase());
/* 233 */     int upperCaseIndex = sql.lastIndexOf(delimiter.getDelimiter().toUpperCase());
/* 234 */     return sql.substring(0, Math.max(lowerCaseIndex, upperCaseIndex));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String extractAlternateOpenQuote(String token) {
/* 244 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String computeAlternateCloseQuote(String openQuote) {
/* 254 */     return openQuote;
/*     */   }
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
/*     */   protected boolean endsWithOpenMultilineStringLiteral(String line) {
/* 267 */     String[] tokens = StringUtils.tokenizeToStringArray(line, " @<>;:=|(),+{}");
/*     */     
/* 269 */     List<TokenType> delimitingTokens = extractStringLiteralDelimitingTokens(tokens);
/*     */     
/* 271 */     for (TokenType delimitingToken : delimitingTokens) {
/* 272 */       if (!this.insideQuoteStringLiteral && !this.insideAlternateQuoteStringLiteral && TokenType.MULTI_LINE_COMMENT.equals(delimitingToken))
/*     */       {
/* 274 */         this.insideMultiLineComment = !this.insideMultiLineComment;
/*     */       }
/*     */       
/* 277 */       if (!this.insideQuoteStringLiteral && !this.insideAlternateQuoteStringLiteral && !this.insideMultiLineComment && TokenType.SINGLE_LINE_COMMENT.equals(delimitingToken))
/*     */       {
/* 279 */         return false;
/*     */       }
/*     */       
/* 282 */       if (!this.insideMultiLineComment && !this.insideQuoteStringLiteral && TokenType.ALTERNATE_QUOTE.equals(delimitingToken))
/*     */       {
/* 284 */         this.insideAlternateQuoteStringLiteral = !this.insideAlternateQuoteStringLiteral;
/*     */       }
/*     */       
/* 287 */       if (!this.insideMultiLineComment && !this.insideAlternateQuoteStringLiteral && TokenType.QUOTE.equals(delimitingToken))
/*     */       {
/* 289 */         this.insideQuoteStringLiteral = !this.insideQuoteStringLiteral;
/*     */       }
/*     */     } 
/*     */     
/* 293 */     return (this.insideQuoteStringLiteral || this.insideAlternateQuoteStringLiteral);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<TokenType> extractStringLiteralDelimitingTokens(String[] tokens) {
/* 304 */     List<TokenType> delimitingTokens = new ArrayList<TokenType>();
/* 305 */     for (String token : tokens) {
/* 306 */       String cleanToken = removeCharsetCasting(removeEscapedQuotes(token));
/*     */       
/* 308 */       if (this.alternateQuote == null) {
/* 309 */         String alternateQuoteFromToken = extractAlternateOpenQuote(cleanToken);
/* 310 */         if (alternateQuoteFromToken != null) {
/* 311 */           String closeQuote = computeAlternateCloseQuote(alternateQuoteFromToken);
/* 312 */           if (cleanToken.length() < alternateQuoteFromToken.length() + closeQuote.length() || !cleanToken.startsWith(alternateQuoteFromToken) || !cleanToken.endsWith(closeQuote)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 318 */             this.alternateQuote = closeQuote;
/* 319 */             delimitingTokens.add(TokenType.ALTERNATE_QUOTE);
/*     */             
/* 321 */             if (cleanToken.startsWith("\"") && cleanToken.endsWith("'"))
/*     */             {
/* 323 */               delimitingTokens.add(TokenType.QUOTE);
/*     */             }
/*     */           } 
/*     */           continue;
/*     */         } 
/*     */       } 
/* 329 */       if (this.alternateQuote != null && cleanToken.endsWith(this.alternateQuote)) {
/* 330 */         this.alternateQuote = null;
/* 331 */         delimitingTokens.add(TokenType.ALTERNATE_QUOTE);
/*     */ 
/*     */       
/*     */       }
/* 335 */       else if (cleanToken.length() < 2 || !cleanToken.startsWith("'") || !cleanToken.endsWith("'")) {
/*     */ 
/*     */ 
/*     */         
/* 339 */         if (cleanToken.startsWith("'") || cleanToken.endsWith("'")) {
/* 340 */           delimitingTokens.add(TokenType.QUOTE);
/*     */         }
/*     */         
/* 343 */         if (isSingleLineComment(cleanToken)) {
/* 344 */           delimitingTokens.add(TokenType.SINGLE_LINE_COMMENT);
/*     */         }
/*     */         
/* 347 */         if (cleanToken.length() < 4 || !cleanToken.startsWith("/*") || !cleanToken.endsWith("*/"))
/*     */         {
/*     */ 
/*     */           
/* 351 */           if (cleanToken.startsWith("/*") || cleanToken.endsWith("*/"))
/* 352 */             delimitingTokens.add(TokenType.MULTI_LINE_COMMENT);  } 
/*     */       } 
/*     */       continue;
/*     */     } 
/* 356 */     return delimitingTokens;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String removeEscapedQuotes(String token) {
/* 365 */     return StringUtils.replaceAll(token, "''", "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String removeCharsetCasting(String token) {
/* 375 */     return token;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private enum TokenType
/*     */   {
/* 385 */     QUOTE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 390 */     ALTERNATE_QUOTE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 395 */     SINGLE_LINE_COMMENT,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 400 */     MULTI_LINE_COMMENT;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\SqlStatementBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */