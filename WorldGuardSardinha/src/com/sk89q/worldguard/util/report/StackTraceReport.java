/*    */ package com.sk89q.worldguard.util.report;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
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
/*    */ public class StackTraceReport
/*    */   implements Report
/*    */ {
/*    */   private final StackTraceElement[] stackTrace;
/*    */   
/*    */   public StackTraceReport(StackTraceElement[] stackTrace) {
/* 29 */     Preconditions.checkNotNull(stackTrace, "stackTrace");
/* 30 */     this.stackTrace = stackTrace;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTitle() {
/* 35 */     return "Stack Trace";
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 40 */     if (this.stackTrace.length > 0) {
/* 41 */       StringBuilder builder = new StringBuilder();
/* 42 */       boolean first = true;
/* 43 */       for (StackTraceElement element : this.stackTrace) {
/* 44 */         if (first) {
/* 45 */           first = false;
/*    */         } else {
/* 47 */           builder.append("\n");
/*    */         } 
/* 49 */         builder.append(element.getClassName())
/* 50 */           .append(".")
/* 51 */           .append(element.getMethodName())
/* 52 */           .append("() (")
/* 53 */           .append(element.getFileName())
/* 54 */           .append(":")
/* 55 */           .append(element.getLineNumber())
/* 56 */           .append(")");
/*    */       } 
/* 58 */       return builder.toString();
/*    */     } 
/* 60 */     return "No stack trace available.";
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\report\StackTraceReport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */