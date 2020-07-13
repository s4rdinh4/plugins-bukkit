/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.resolver.spring;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.api.migration.spring.SpringJdbcMigration;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.api.resolver.MigrationExecutor;
/*    */ import java.sql.Connection;
/*    */ import javax.sql.DataSource;
/*    */ import org.springframework.jdbc.core.JdbcTemplate;
/*    */ import org.springframework.jdbc.datasource.SingleConnectionDataSource;
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
/*    */ public class SpringJdbcMigrationExecutor
/*    */   implements MigrationExecutor
/*    */ {
/*    */   private final SpringJdbcMigration springJdbcMigration;
/*    */   
/*    */   public SpringJdbcMigrationExecutor(SpringJdbcMigration springJdbcMigration) {
/* 40 */     this.springJdbcMigration = springJdbcMigration;
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(Connection connection) {
/*    */     try {
/* 46 */       this.springJdbcMigration.migrate(new JdbcTemplate((DataSource)new SingleConnectionDataSource(connection, true)));
/*    */     }
/* 48 */     catch (Exception e) {
/* 49 */       throw new FlywayException("Migration failed !", e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean executeInTransaction() {
/* 55 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\resolver\spring\SpringJdbcMigrationExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */