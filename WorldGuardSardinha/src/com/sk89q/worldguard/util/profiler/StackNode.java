/*     */ package com.sk89q.worldguard.util.profiler;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class StackNode
/*     */   implements Comparable<StackNode>
/*     */ {
/*     */   private final String name;
/*  33 */   private final Map<String, StackNode> children = Maps.newHashMap();
/*     */   private long totalTime;
/*     */   
/*     */   public StackNode(String name) {
/*  37 */     this.name = name;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  41 */     return this.name;
/*     */   }
/*     */   
/*     */   public Collection<StackNode> getChildren() {
/*  45 */     List<StackNode> list = Lists.newArrayList(this.children.values());
/*  46 */     Collections.sort(list);
/*  47 */     return list;
/*     */   }
/*     */   
/*     */   public StackNode getChild(String name) {
/*  51 */     StackNode child = this.children.get(name);
/*  52 */     if (child == null) {
/*  53 */       child = new StackNode(name);
/*  54 */       this.children.put(name, child);
/*     */     } 
/*  56 */     return child;
/*     */   }
/*     */   
/*     */   public StackNode getChild(String className, String methodName) {
/*  60 */     StackTraceNode node = new StackTraceNode(className, methodName);
/*  61 */     StackNode child = this.children.get(node.getName());
/*  62 */     if (child == null) {
/*  63 */       child = node;
/*  64 */       this.children.put(node.getName(), node);
/*     */     } 
/*  66 */     return child;
/*     */   }
/*     */   
/*     */   public long getTotalTime() {
/*  70 */     return this.totalTime;
/*     */   }
/*     */   
/*     */   public void log(long time) {
/*  74 */     this.totalTime += time;
/*     */   }
/*     */   
/*     */   private void log(StackTraceElement[] elements, int skip, long time) {
/*  78 */     log(time);
/*     */     
/*  80 */     if (elements.length - skip == 0) {
/*     */       return;
/*     */     }
/*     */     
/*  84 */     StackTraceElement bottom = elements[elements.length - skip + 1];
/*  85 */     getChild(bottom.getClassName(), bottom.getMethodName())
/*  86 */       .log(elements, skip + 1, time);
/*     */   }
/*     */   
/*     */   public void log(StackTraceElement[] elements, long time) {
/*  90 */     log(elements, 0, time);
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(StackNode o) {
/*  95 */     return getName().compareTo(o.getName());
/*     */   }
/*     */   
/*     */   void writeString(StringBuilder builder, int indent) {
/*  99 */     StringBuilder b = new StringBuilder();
/* 100 */     for (int i = 0; i < indent; i++) {
/* 101 */       b.append(" ");
/*     */     }
/* 103 */     String padding = b.toString();
/*     */     
/* 105 */     for (StackNode child : getChildren()) {
/* 106 */       builder.append(padding).append(child.getName());
/* 107 */       builder.append(" ");
/* 108 */       builder.append(child.getTotalTime()).append("ms");
/* 109 */       builder.append("\n");
/* 110 */       child.writeString(builder, indent + 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 116 */     StringBuilder builder = new StringBuilder();
/* 117 */     writeString(builder, 0);
/* 118 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\profiler\StackNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */