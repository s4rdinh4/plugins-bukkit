/*    */ package com.sk89q.worldguard.protection.regions;
/*    */ 
/*    */ import org.khelekore.prtree.MBRConverter;
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
/*    */ public class ProtectedRegionMBRConverter
/*    */   implements MBRConverter<ProtectedRegion>
/*    */ {
/*    */   public int getDimensions() {
/* 28 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getMax(int dimension, ProtectedRegion region) {
/* 33 */     switch (dimension) {
/*    */       case 0:
/* 35 */         return region.getMaximumPoint().getBlockX();
/*    */       case 1:
/* 37 */         return region.getMaximumPoint().getBlockY();
/*    */       case 2:
/* 39 */         return region.getMaximumPoint().getBlockZ();
/*    */     } 
/* 41 */     return 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getMin(int dimension, ProtectedRegion region) {
/* 46 */     switch (dimension) {
/*    */       case 0:
/* 48 */         return region.getMinimumPoint().getBlockX();
/*    */       case 1:
/* 50 */         return region.getMinimumPoint().getBlockY();
/*    */       case 2:
/* 52 */         return region.getMinimumPoint().getBlockZ();
/*    */     } 
/* 54 */     return 0.0D;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\regions\ProtectedRegionMBRConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */