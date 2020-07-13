/*    */ package com.sk89q.worldguard.protection.association;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.sk89q.worldguard.domains.Association;
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
/*    */ public final class Associables
/*    */ {
/* 31 */   private static final RegionAssociable OWNER_ASSOCIABLE = new ConstantAssociation(Association.OWNER);
/* 32 */   private static final RegionAssociable MEMBER_ASSOCIABLE = new ConstantAssociation(Association.MEMBER);
/* 33 */   private static final RegionAssociable NON_MEMBER_ASSOCIABLE = new ConstantAssociation(Association.NON_MEMBER);
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
/*    */   public static RegionAssociable constant(Association association) {
/* 45 */     Preconditions.checkNotNull(association);
/* 46 */     switch (association) {
/*    */       case OWNER:
/* 48 */         return OWNER_ASSOCIABLE;
/*    */       case MEMBER:
/* 50 */         return MEMBER_ASSOCIABLE;
/*    */       case NON_MEMBER:
/* 52 */         return NON_MEMBER_ASSOCIABLE;
/*    */     } 
/* 54 */     return new ConstantAssociation(association);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\association\Associables.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */