/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.db2;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.DbSupport;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Function;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.JdbcTemplate;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.StringUtils;
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
/*    */ public class DB2Function
/*    */   extends Function
/*    */ {
/*    */   public DB2Function(JdbcTemplate jdbcTemplate, DbSupport dbSupport, Schema schema, String name, String... args) {
/* 40 */     super(jdbcTemplate, dbSupport, schema, name, args);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doDrop() throws SQLException {
/* 45 */     this.jdbcTemplate.execute("DROP FUNCTION " + this.dbSupport.quote(new String[] { this.schema.getName(), this.name }, ) + "(" + StringUtils.arrayToCommaDelimitedString((Object[])this.args) + ")", new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 52 */     return super.toString() + "(" + StringUtils.arrayToCommaDelimitedString((Object[])this.args) + ")";
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\db2\DB2Function.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */