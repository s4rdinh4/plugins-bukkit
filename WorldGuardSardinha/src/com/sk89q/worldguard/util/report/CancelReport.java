/*     */ package com.sk89q.worldguard.util.report;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.sk89q.worldguard.bukkit.event.debug.CancelAttempt;
/*     */ import com.sk89q.worldguard.bukkit.util.HandlerTracer;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CancelReport
/*     */   implements Report
/*     */ {
/*     */   private final Event event;
/*     */   private final Cancellable cancellable;
/*     */   private final List<CancelAttempt> cancels;
/*     */   private final HandlerTracer tracer;
/*     */   private final int stackTruncateLength;
/*     */   
/*     */   public <T extends Event & Cancellable> CancelReport(T event, List<CancelAttempt> cancels, int stackTruncateLength) {
/*  45 */     Preconditions.checkNotNull(event, "event");
/*  46 */     Preconditions.checkNotNull(cancels, "cancels");
/*  47 */     this.event = (Event)event;
/*  48 */     this.cancellable = (Cancellable)event;
/*  49 */     this.cancels = cancels;
/*  50 */     this.tracer = new HandlerTracer((Event)event);
/*  51 */     this.stackTruncateLength = stackTruncateLength;
/*     */   }
/*     */   
/*     */   private StackTraceElement[] truncateStackTrace(StackTraceElement[] elements) {
/*  55 */     int newLength = elements.length - this.stackTruncateLength;
/*  56 */     if (newLength <= 0) {
/*  57 */       return new StackTraceElement[0];
/*     */     }
/*  59 */     return Arrays.<StackTraceElement>copyOf(elements, newLength);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTitle() {
/*  65 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  70 */     if (!this.cancels.isEmpty()) {
/*  71 */       StringBuilder builder = new StringBuilder();
/*     */       
/*  73 */       builder.append("Was the action blocked? ").append(this.cancellable.isCancelled() ? "YES" : "NO").append("\n");
/*     */       
/*  75 */       if (this.cancels.size() != 1) {
/*  76 */         builder.append("Entry #1 had the last word.\n");
/*     */       }
/*     */       
/*  79 */       for (int i = this.cancels.size() - 1; i >= 0; i--) {
/*  80 */         CancelAttempt cancel = this.cancels.get(i);
/*  81 */         int index = this.cancels.size() - i;
/*     */         
/*  83 */         StackTraceElement[] stackTrace = truncateStackTrace(cancel.getStackTrace());
/*  84 */         Plugin cause = this.tracer.detectPlugin(stackTrace);
/*     */         
/*  86 */         builder.append("#").append(index).append(" ");
/*  87 */         builder.append(getCancelText(cancel.getAfter()));
/*  88 */         builder.append(" by ");
/*     */         
/*  90 */         if (cause != null) {
/*  91 */           builder.append(cause.getName());
/*     */         } else {
/*  93 */           builder.append(" (NOT KNOWN - use the stack trace below)");
/*  94 */           builder.append("\n");
/*  95 */           builder.append((new StackTraceReport(stackTrace)).toString().replaceAll("(?m)^", "\t"));
/*     */         } 
/*     */         
/*  98 */         builder.append("\n");
/*     */       } 
/*     */       
/* 101 */       return builder.toString();
/*     */     } 
/* 103 */     return "No plugins cancelled the event. Other causes for cancellation: (1) Bukkit may be using a different event for the action  (example: buckets have their own bucket events); or (2) Minecraft's spawn protection has not been disabled.";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getCancelText(boolean flag) {
/* 111 */     return flag ? "BLOCKED" : "ALLOWED";
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\report\CancelReport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */