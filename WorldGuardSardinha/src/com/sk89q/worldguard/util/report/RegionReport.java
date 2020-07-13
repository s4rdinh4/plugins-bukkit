/*    */ package com.sk89q.worldguard.util.report;
/*    */ 
/*    */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
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
/*    */ public class RegionReport
/*    */   extends DataReport
/*    */ {
/*    */   public RegionReport(ProtectedRegion region) {
/* 30 */     super("Region: " + region.getId());
/*    */     
/* 32 */     append("Type", region.getType());
/* 33 */     append("Priority", region.getPriority());
/* 34 */     append("Owners", region.getOwners());
/* 35 */     append("Members", region.getMembers());
/* 36 */     append("Flags", region.getFlags());
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\report\RegionReport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */