/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.resolver.sql;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.api.resolver.MigrationExecutor;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.DbSupport;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.JdbcTemplate;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.SqlScript;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.PlaceholderReplacer;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner.Resource;
/*    */ import java.sql.Connection;
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
/*    */ public class SqlMigrationExecutor
/*    */   implements MigrationExecutor
/*    */ {
/*    */   private final DbSupport dbSupport;
/*    */   private final PlaceholderReplacer placeholderReplacer;
/*    */   private final Resource sqlScriptResource;
/*    */   private final String encoding;
/*    */   
/*    */   public SqlMigrationExecutor(DbSupport dbSupport, Resource sqlScriptResource, PlaceholderReplacer placeholderReplacer, String encoding) {
/* 62 */     this.dbSupport = dbSupport;
/* 63 */     this.sqlScriptResource = sqlScriptResource;
/* 64 */     this.encoding = encoding;
/* 65 */     this.placeholderReplacer = placeholderReplacer;
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(Connection connection) {
/* 70 */     String sqlScriptSource = this.sqlScriptResource.loadAsString(this.encoding);
/* 71 */     String sqlScriptSourceNoPlaceholders = this.placeholderReplacer.replacePlaceholders(sqlScriptSource);
/* 72 */     SqlScript sqlScript = new SqlScript(sqlScriptSourceNoPlaceholders, this.dbSupport);
/* 73 */     sqlScript.execute(new JdbcTemplate(connection, 0));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean executeInTransaction() {
/* 78 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\resolver\sql\SqlMigrationExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */