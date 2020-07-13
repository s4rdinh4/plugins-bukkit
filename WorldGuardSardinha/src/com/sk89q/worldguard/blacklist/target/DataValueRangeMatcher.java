/*    */ package com.sk89q.worldguard.blacklist.target;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.google.common.base.Predicate;
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
/*    */ public class DataValueRangeMatcher
/*    */   implements TargetMatcher
/*    */ {
/*    */   private final int typeId;
/*    */   private final Predicate<Short> dataMatcher;
/*    */   
/*    */   public DataValueRangeMatcher(int typeId, Predicate<Short> dataMatcher) {
/* 32 */     Preconditions.checkNotNull(dataMatcher);
/* 33 */     this.typeId = typeId;
/* 34 */     this.dataMatcher = dataMatcher;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMatchedTypeId() {
/* 39 */     return this.typeId;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(Target target) {
/* 44 */     return (this.typeId == target.getTypeId() && isDataInRange(target.getData()));
/*    */   }
/*    */   
/*    */   private boolean isDataInRange(short data) {
/* 48 */     return this.dataMatcher.apply(Short.valueOf(data));
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\target\DataValueRangeMatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */