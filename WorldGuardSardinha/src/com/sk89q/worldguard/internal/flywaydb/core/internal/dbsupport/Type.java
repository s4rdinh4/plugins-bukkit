/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport;
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
/*    */ public abstract class Type
/*    */   extends SchemaObject
/*    */ {
/*    */   public Type(JdbcTemplate jdbcTemplate, DbSupport dbSupport, Schema schema, String name) {
/* 31 */     super(jdbcTemplate, dbSupport, schema, name);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\Type.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */