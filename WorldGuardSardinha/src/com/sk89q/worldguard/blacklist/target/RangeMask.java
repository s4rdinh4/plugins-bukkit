/*    */ package com.sk89q.worldguard.blacklist.target;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.google.common.collect.Range;
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
/*    */ public class RangeMask
/*    */   implements DataMask
/*    */ {
/*    */   private final Range<Short> range;
/*    */   
/*    */   public RangeMask(Range<Short> range) {
/* 31 */     Preconditions.checkNotNull(range);
/* 32 */     this.range = range;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean apply(Short data) {
/* 37 */     return this.range.contains(data);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\target\RangeMask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */