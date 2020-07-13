/*    */ package com.sk89q.worldguard.util.profiler;
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
/*    */ public class StackTraceNode
/*    */   extends StackNode
/*    */ {
/*    */   private final String className;
/*    */   private final String methodName;
/*    */   
/*    */   public StackTraceNode(String className, String methodName) {
/* 30 */     super(className + "." + methodName + "()");
/* 31 */     this.className = className;
/* 32 */     this.methodName = methodName;
/*    */   }
/*    */   
/*    */   public String getClassName() {
/* 36 */     return this.className;
/*    */   }
/*    */   
/*    */   public String getMethodName() {
/* 40 */     return this.methodName;
/*    */   }
/*    */ 
/*    */   
/*    */   public int compareTo(StackNode o) {
/* 45 */     if (getTotalTime() == o.getTotalTime())
/* 46 */       return 0; 
/* 47 */     if (getTotalTime() > o.getTotalTime()) {
/* 48 */       return -1;
/*    */     }
/* 50 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\profiler\StackTraceNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */