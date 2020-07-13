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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Function
/*    */   extends SchemaObject
/*    */ {
/*    */   protected String[] args;
/*    */   
/*    */   public Function(JdbcTemplate jdbcTemplate, DbSupport dbSupport, Schema schema, String name, String... args) {
/* 37 */     super(jdbcTemplate, dbSupport, schema, name);
/* 38 */     this.args = args;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\Function.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */