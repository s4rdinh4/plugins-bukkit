/*    */ package com.sk89q.worldguard.protection.flags;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.sk89q.worldguard.domains.Association;
/*    */ import java.util.Arrays;
/*    */ import java.util.EnumSet;
/*    */ import java.util.Set;
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
/*    */ public enum RegionGroup
/*    */ {
/* 35 */   MEMBERS(new Association[] { Association.MEMBER, Association.OWNER }),
/* 36 */   OWNERS(new Association[] { Association.OWNER }),
/* 37 */   NON_MEMBERS(new Association[] { Association.NON_MEMBER }),
/* 38 */   NON_OWNERS(new Association[] { Association.MEMBER, Association.NON_MEMBER }),
/* 39 */   ALL(new Association[] { Association.OWNER, Association.MEMBER, Association.NON_MEMBER }),
/* 40 */   NONE(new Association[0]);
/*    */   
/*    */   private final Set<Association> contained;
/*    */   
/*    */   RegionGroup(Association... association) {
/* 45 */     this.contained = (association.length > 0) ? EnumSet.<Association>copyOf(Arrays.asList(association)) : EnumSet.<Association>noneOf(Association.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean contains(Association association) {
/* 55 */     Preconditions.checkNotNull(association);
/* 56 */     return this.contained.contains(association);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\flags\RegionGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */