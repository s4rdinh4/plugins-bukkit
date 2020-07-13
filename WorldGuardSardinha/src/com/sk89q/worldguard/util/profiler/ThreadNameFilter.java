/*    */ package com.sk89q.worldguard.util.profiler;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
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
/*    */ 
/*    */ public class ThreadNameFilter
/*    */   implements Predicate<ThreadInfo>
/*    */ {
/*    */   private final String name;
/*    */   
/*    */   public ThreadNameFilter(String name) {
/* 33 */     Preconditions.checkNotNull(name, "name");
/* 34 */     this.name = name;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean apply(ThreadInfo threadInfo) {
/* 39 */     return threadInfo.getThreadName().equalsIgnoreCase(this.name);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\profiler\ThreadNameFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */