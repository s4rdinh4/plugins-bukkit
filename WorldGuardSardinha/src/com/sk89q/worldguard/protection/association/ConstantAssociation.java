/*    */ package com.sk89q.worldguard.protection.association;
/*    */ 
/*    */ import com.sk89q.worldguard.domains.Association;
/*    */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*    */ import java.util.List;
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
/*    */ class ConstantAssociation
/*    */   implements RegionAssociable
/*    */ {
/*    */   private final Association association;
/*    */   
/*    */   ConstantAssociation(Association association) {
/* 32 */     this.association = association;
/*    */   }
/*    */ 
/*    */   
/*    */   public Association getAssociation(List<ProtectedRegion> regions) {
/* 37 */     return this.association;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\association\ConstantAssociation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */