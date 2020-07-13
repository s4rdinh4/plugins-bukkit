/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.sqlite;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.DbSupport;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.JdbcTemplate;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Table;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.Log;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.LogFactory;
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
/*    */ public class SQLiteTable
/*    */   extends Table
/*    */ {
/* 31 */   private static final Log LOG = LogFactory.getLog(SQLiteTable.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SQLiteTable(JdbcTemplate jdbcTemplate, DbSupport dbSupport, Schema schema, String name) {
/* 42 */     super(jdbcTemplate, dbSupport, schema, name);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doDrop() throws SQLException {
/* 47 */     this.jdbcTemplate.execute("DROP TABLE " + this.dbSupport.quote(new String[] { this.schema.getName(), this.name }, ), new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean doExists() throws SQLException {
/* 52 */     return (this.jdbcTemplate.queryForInt("SELECT count(tbl_name) FROM " + this.dbSupport.quote(new String[] { this.schema.getName() }, ) + ".sqlite_master WHERE type='table' AND tbl_name='" + this.name + "'", new String[0]) > 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void doLock() throws SQLException {
/* 58 */     LOG.debug("Unable to lock " + this + " as SQLite does not support locking. No concurrent migration supported.");
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\sqlite\SQLiteTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */