/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.postgresql;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.DbSupport;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.JdbcTemplate;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Type;
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
/*    */ public class PostgreSQLType
/*    */   extends Type
/*    */ {
/*    */   public PostgreSQLType(JdbcTemplate jdbcTemplate, DbSupport dbSupport, Schema schema, String name) {
/* 38 */     super(jdbcTemplate, dbSupport, schema, name);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doDrop() throws SQLException {
/* 43 */     this.jdbcTemplate.execute("DROP TYPE " + this.dbSupport.quote(new String[] { this.schema.getName(), this.name }, ), new Object[0]);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\postgresql\PostgreSQLType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */