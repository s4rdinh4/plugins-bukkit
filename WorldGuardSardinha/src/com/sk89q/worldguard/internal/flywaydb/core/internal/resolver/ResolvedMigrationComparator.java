/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.resolver;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.api.resolver.ResolvedMigration;
/*    */ import java.util.Comparator;
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
/*    */ public class ResolvedMigrationComparator
/*    */   implements Comparator<ResolvedMigration>
/*    */ {
/*    */   public int compare(ResolvedMigration o1, ResolvedMigration o2) {
/* 28 */     return o1.getVersion().compareTo(o2.getVersion());
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\resolver\ResolvedMigrationComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */