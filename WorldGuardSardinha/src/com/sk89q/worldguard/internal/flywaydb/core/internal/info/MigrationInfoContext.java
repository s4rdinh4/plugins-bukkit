/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.info;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationVersion;
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
/*    */ public class MigrationInfoContext
/*    */ {
/*    */   public boolean outOfOrder;
/*    */   public boolean pending;
/*    */   public MigrationVersion target;
/*    */   public MigrationVersion schema;
/*    */   public MigrationVersion init;
/* 52 */   public MigrationVersion lastResolved = MigrationVersion.EMPTY;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 57 */   public MigrationVersion lastApplied = MigrationVersion.EMPTY;
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 62 */     if (this == o) return true; 
/* 63 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 65 */     MigrationInfoContext context = (MigrationInfoContext)o;
/*    */     
/* 67 */     if (this.outOfOrder != context.outOfOrder) return false; 
/* 68 */     if (this.pending != context.pending) return false; 
/* 69 */     if ((this.schema != null) ? !this.schema.equals(context.schema) : (context.schema != null)) return false; 
/* 70 */     if ((this.init != null) ? !this.init.equals(context.init) : (context.init != null)) return false; 
/* 71 */     if (!this.lastApplied.equals(context.lastApplied)) return false; 
/* 72 */     if (!this.lastResolved.equals(context.lastResolved)) return false; 
/* 73 */     return this.target.equals(context.target);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 78 */     int result = this.outOfOrder ? 1 : 0;
/* 79 */     result = 31 * result + (this.pending ? 1 : 0);
/* 80 */     result = 31 * result + this.target.hashCode();
/* 81 */     result = 31 * result + ((this.schema != null) ? this.schema.hashCode() : 0);
/* 82 */     result = 31 * result + ((this.init != null) ? this.init.hashCode() : 0);
/* 83 */     result = 31 * result + this.lastResolved.hashCode();
/* 84 */     result = 31 * result + this.lastApplied.hashCode();
/* 85 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\info\MigrationInfoContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */