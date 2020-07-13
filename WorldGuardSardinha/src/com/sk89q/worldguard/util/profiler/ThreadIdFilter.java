/*    */ package com.sk89q.worldguard.util.profiler;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import java.lang.management.ThreadInfo;
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
/*    */ public class ThreadIdFilter
/*    */   implements Predicate<ThreadInfo>
/*    */ {
/*    */   private final long id;
/*    */   
/*    */   public ThreadIdFilter(long id) {
/* 31 */     this.id = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean apply(ThreadInfo threadInfo) {
/* 36 */     return (threadInfo.getThreadId() == this.id);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\profiler\ThreadIdFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */