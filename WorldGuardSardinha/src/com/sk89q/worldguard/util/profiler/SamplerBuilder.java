/*     */ package com.sk89q.worldguard.util.profiler;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import com.google.common.util.concurrent.SettableFuture;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.ThreadInfo;
/*     */ import java.lang.management.ThreadMXBean;
/*     */ import java.util.Map;
/*     */ import java.util.SortedMap;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.TreeMap;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ public class SamplerBuilder
/*     */ {
/*  38 */   private static final Timer timer = new Timer("WorldGuard Sampler", true);
/*  39 */   private int interval = 100;
/*  40 */   private long runTime = TimeUnit.MINUTES.toMillis(5L);
/*  41 */   private Predicate<ThreadInfo> threadFilter = Predicates.alwaysTrue();
/*     */   
/*     */   public int getInterval() {
/*  44 */     return this.interval;
/*     */   }
/*     */   
/*     */   public void setInterval(int interval) {
/*  48 */     Preconditions.checkArgument((interval >= 10), "interval >= 10");
/*  49 */     this.interval = interval;
/*     */   }
/*     */   
/*     */   public Predicate<ThreadInfo> getThreadFilter() {
/*  53 */     return this.threadFilter;
/*     */   }
/*     */   
/*     */   public void setThreadFilter(Predicate<ThreadInfo> threadFilter) {
/*  57 */     Preconditions.checkNotNull(threadFilter, "threadFilter");
/*  58 */     this.threadFilter = threadFilter;
/*     */   }
/*     */   
/*     */   public long getRunTime(TimeUnit timeUnit) {
/*  62 */     return timeUnit.convert(this.runTime, TimeUnit.MILLISECONDS);
/*     */   }
/*     */   
/*     */   public void setRunTime(long time, TimeUnit timeUnit) {
/*  66 */     Preconditions.checkArgument((time > 0L), "time > 0");
/*  67 */     this.runTime = timeUnit.toMillis(time);
/*     */   }
/*     */   
/*     */   public Sampler start() {
/*  71 */     Sampler sampler = new Sampler(this.interval, this.threadFilter, System.currentTimeMillis() + this.runTime);
/*  72 */     timer.scheduleAtFixedRate(sampler, 0L, this.interval);
/*  73 */     return sampler;
/*     */   }
/*     */   
/*     */   public static class Sampler
/*     */     extends TimerTask {
/*     */     private final int interval;
/*     */     private final Predicate<ThreadInfo> threadFilter;
/*     */     private final long endTime;
/*  81 */     private final SortedMap<String, StackNode> nodes = new TreeMap<String, StackNode>();
/*  82 */     private final ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
/*  83 */     private final SettableFuture<Sampler> future = SettableFuture.create();
/*     */     
/*     */     private Sampler(int interval, Predicate<ThreadInfo> threadFilter, long endTime) {
/*  86 */       this.interval = interval;
/*  87 */       this.threadFilter = threadFilter;
/*  88 */       this.endTime = endTime;
/*     */     }
/*     */     
/*     */     public ListenableFuture<Sampler> getFuture() {
/*  92 */       return (ListenableFuture<Sampler>)this.future;
/*     */     }
/*     */     
/*     */     private Map<String, StackNode> getData() {
/*  96 */       return this.nodes;
/*     */     }
/*     */     
/*     */     private StackNode getNode(String name) {
/* 100 */       StackNode node = this.nodes.get(name);
/* 101 */       if (node == null) {
/* 102 */         node = new StackNode(name);
/* 103 */         this.nodes.put(name, node);
/*     */       } 
/* 105 */       return node;
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized void run() {
/*     */       try {
/* 111 */         if (this.endTime <= System.currentTimeMillis()) {
/* 112 */           this.future.set(this);
/* 113 */           cancel();
/*     */           
/*     */           return;
/*     */         } 
/* 117 */         ThreadInfo[] threadDumps = this.threadBean.dumpAllThreads(false, false);
/* 118 */         for (ThreadInfo threadInfo : threadDumps) {
/* 119 */           String threadName = threadInfo.getThreadName();
/* 120 */           StackTraceElement[] stack = threadInfo.getStackTrace();
/*     */           
/* 122 */           if (threadName != null && stack != null && this.threadFilter.apply(threadInfo)) {
/* 123 */             StackNode node = getNode(threadName);
/* 124 */             node.log(stack, this.interval);
/*     */           } 
/*     */         } 
/* 127 */       } catch (Throwable t) {
/* 128 */         this.future.setException(t);
/* 129 */         cancel();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 135 */       StringBuilder builder = new StringBuilder();
/* 136 */       for (Map.Entry<String, StackNode> entry : getData().entrySet()) {
/* 137 */         builder.append(entry.getKey());
/* 138 */         builder.append(" ");
/* 139 */         builder.append(((StackNode)entry.getValue()).getTotalTime()).append("ms");
/* 140 */         builder.append("\n");
/* 141 */         ((StackNode)entry.getValue()).writeString(builder, 1);
/*     */       } 
/* 143 */       return builder.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\profiler\SamplerBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */