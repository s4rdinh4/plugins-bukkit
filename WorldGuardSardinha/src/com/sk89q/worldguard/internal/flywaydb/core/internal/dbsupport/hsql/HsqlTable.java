/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.hsql;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
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
/*    */ public class HsqlTable
/*    */   extends Table
/*    */ {
/* 32 */   private static final Log LOG = LogFactory.getLog(HsqlDbSupport.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean version18;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HsqlTable(JdbcTemplate jdbcTemplate, DbSupport dbSupport, Schema schema, String name) {
/* 48 */     super(jdbcTemplate, dbSupport, schema, name);
/*    */     
/*    */     try {
/* 51 */       int majorVersion = jdbcTemplate.getMetaData().getDatabaseMajorVersion();
/* 52 */       this.version18 = (majorVersion < 2);
/* 53 */     } catch (SQLException e) {
/* 54 */       throw new FlywayException("Unable to determine the Hsql version", e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doDrop() throws SQLException {
/* 60 */     this.jdbcTemplate.execute("DROP TABLE " + this.dbSupport.quote(new String[] { this.schema.getName(), this.name }, ) + " CASCADE", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean doExists() throws SQLException {
/* 65 */     return exists(null, this.schema, this.name, new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doLock() throws SQLException {
/* 70 */     if (this.version18) {
/* 71 */       LOG.debug("Unable to lock " + this + " as Hsql 1.8 does not support locking. No concurrent migration supported.");
/*    */     } else {
/* 73 */       this.jdbcTemplate.execute("LOCK TABLE " + this + " WRITE", new Object[0]);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\hsql\HsqlTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */