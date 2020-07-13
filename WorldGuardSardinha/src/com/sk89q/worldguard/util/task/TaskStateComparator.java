/*    */ package com.sk89q.worldguard.util.task;
/*    */ 
/*    */ import java.util.Comparator;
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
/*    */ public class TaskStateComparator
/*    */   implements Comparator<Task<?>>
/*    */ {
/*    */   public int compare(Task<?> o1, Task<?> o2) {
/* 32 */     int ordinal1 = o1.getState().ordinal();
/* 33 */     int ordinal2 = o2.getState().ordinal();
/* 34 */     if (ordinal1 < ordinal2)
/* 35 */       return -1; 
/* 36 */     if (ordinal1 > ordinal2) {
/* 37 */       return 1;
/*    */     }
/* 39 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\task\TaskStateComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */