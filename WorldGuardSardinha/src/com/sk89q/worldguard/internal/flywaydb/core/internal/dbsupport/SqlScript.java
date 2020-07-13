/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.StringUtils;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.Log;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.LogFactory;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.sql.SQLException;
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
/*     */ public class SqlScript
/*     */ {
/*  36 */   private static final Log LOG = LogFactory.getLog(SqlScript.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final DbSupport dbSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final List<SqlStatement> sqlStatements;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SqlScript(String sqlScriptSource, DbSupport dbSupport) {
/*  55 */     this.dbSupport = dbSupport;
/*  56 */     this.sqlStatements = parse(sqlScriptSource);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SqlScript(DbSupport dbSupport) {
/*  65 */     this.dbSupport = dbSupport;
/*  66 */     this.sqlStatements = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<SqlStatement> getSqlStatements() {
/*  75 */     return this.sqlStatements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(JdbcTemplate jdbcTemplate) {
/*  84 */     for (SqlStatement sqlStatement : this.sqlStatements) {
/*  85 */       String sql = sqlStatement.getSql();
/*  86 */       LOG.debug("Executing SQL: " + sql);
/*     */       
/*     */       try {
/*  89 */         jdbcTemplate.executeStatement(sql);
/*  90 */       } catch (SQLException e) {
/*  91 */         throw new FlywaySqlScriptException(sqlStatement.getLineNumber(), sql, e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   List<SqlStatement> parse(String sqlScriptSource) {
/* 104 */     return linesToStatements(readLines(new StringReader(sqlScriptSource)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   List<SqlStatement> linesToStatements(List<String> lines) {
/* 115 */     List<SqlStatement> statements = new ArrayList<SqlStatement>();
/*     */     
/* 117 */     boolean inMultilineComment = false;
/* 118 */     Delimiter nonStandardDelimiter = null;
/* 119 */     SqlStatementBuilder sqlStatementBuilder = this.dbSupport.createSqlStatementBuilder();
/*     */     
/* 121 */     int lineNumber = 1; while (true) { String line; if (lineNumber <= lines.size())
/* 122 */       { line = lines.get(lineNumber - 1);
/*     */         
/* 124 */         if (sqlStatementBuilder.isEmpty())
/* 125 */         { if (!StringUtils.hasText(line)) {
/*     */             continue;
/*     */           }
/*     */ 
/*     */           
/* 130 */           String trimmedLine = line.trim();
/*     */           
/* 132 */           if (!sqlStatementBuilder.isCommentDirective(trimmedLine)) {
/* 133 */             if (trimmedLine.startsWith("/*")) {
/* 134 */               inMultilineComment = true;
/*     */             }
/*     */             
/* 137 */             if (inMultilineComment) {
/* 138 */               if (trimmedLine.endsWith("*/")) {
/* 139 */                 inMultilineComment = false;
/*     */               }
/*     */               
/*     */               continue;
/*     */             } 
/*     */             
/* 145 */             if (sqlStatementBuilder.isSingleLineComment(trimmedLine)) {
/*     */               continue;
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 151 */           Delimiter newDelimiter = sqlStatementBuilder.extractNewDelimiterFromLine(line);
/* 152 */           if (newDelimiter != null)
/* 153 */           { nonStandardDelimiter = newDelimiter;
/*     */              }
/*     */           
/*     */           else
/*     */           
/* 158 */           { sqlStatementBuilder.setLineNumber(lineNumber);
/*     */ 
/*     */             
/* 161 */             if (nonStandardDelimiter != null) {
/* 162 */               sqlStatementBuilder.setDelimiter(nonStandardDelimiter);
/*     */             }
/*     */ 
/*     */             
/* 166 */             sqlStatementBuilder.addLine(line); }  continue; }  } else { break; }  sqlStatementBuilder.addLine(line);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       lineNumber++; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 178 */     if (!sqlStatementBuilder.isEmpty()) {
/* 179 */       statements.add(sqlStatementBuilder.getSqlStatement());
/*     */     }
/*     */     
/* 182 */     return statements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> readLines(Reader reader) {
/* 193 */     List<String> lines = new ArrayList<String>();
/*     */     
/* 195 */     BufferedReader bufferedReader = new BufferedReader(reader);
/*     */     
/*     */     try {
/*     */       String line;
/* 199 */       while ((line = bufferedReader.readLine()) != null) {
/* 200 */         lines.add(line);
/*     */       }
/* 202 */     } catch (IOException e) {
/* 203 */       throw new FlywayException("Cannot parse lines", e);
/*     */     } 
/*     */     
/* 206 */     return lines;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\SqlScript.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */