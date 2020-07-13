/*    */ package com.sk89q.worldguard.blacklist.target;
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
/*    */ public class WildcardDataMatcher
/*    */   implements TargetMatcher
/*    */ {
/*    */   private final int typeId;
/*    */   
/*    */   public WildcardDataMatcher(int typeId) {
/* 27 */     this.typeId = typeId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMatchedTypeId() {
/* 32 */     return this.typeId;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(Target target) {
/* 37 */     return (target.getTypeId() == this.typeId);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\target\WildcardDataMatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */