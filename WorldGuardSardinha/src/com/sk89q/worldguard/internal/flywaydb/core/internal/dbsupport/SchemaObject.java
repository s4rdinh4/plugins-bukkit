/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
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
/*    */ public abstract class SchemaObject
/*    */ {
/*    */   protected final JdbcTemplate jdbcTemplate;
/*    */   protected final DbSupport dbSupport;
/*    */   protected final Schema schema;
/*    */   protected final String name;
/*    */   
/*    */   public SchemaObject(JdbcTemplate jdbcTemplate, DbSupport dbSupport, Schema schema, String name) {
/* 55 */     this.name = name;
/* 56 */     this.jdbcTemplate = jdbcTemplate;
/* 57 */     this.dbSupport = dbSupport;
/* 58 */     this.schema = schema;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final Schema getSchema() {
/* 65 */     return this.schema;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final String getName() {
/* 72 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final void drop() {
/*    */     try {
/* 80 */       doDrop();
/* 81 */     } catch (SQLException e) {
/* 82 */       throw new FlywayException("Unable to drop " + this, e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract void doDrop() throws SQLException;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 95 */     return this.dbSupport.quote(new String[] { this.schema.getName(), this.name });
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\SchemaObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */