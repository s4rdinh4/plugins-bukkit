/*     */ package com.sk89q.worldguard.util.report;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class DataReport
/*     */   implements Report
/*     */ {
/*     */   private final String title;
/*  32 */   private final List<Line> lines = Lists.newArrayList();
/*     */   
/*     */   public DataReport(String title) {
/*  35 */     Preconditions.checkNotNull(title, "title");
/*  36 */     this.title = title;
/*     */   }
/*     */   
/*     */   public void append(String key, String message) {
/*  40 */     Preconditions.checkNotNull(key, "key");
/*  41 */     this.lines.add(new Line(key, message));
/*     */   }
/*     */   
/*     */   public void append(String key, String message, Object... values) {
/*  45 */     Preconditions.checkNotNull(message, "values");
/*  46 */     Preconditions.checkNotNull(values, "values");
/*  47 */     append(key, String.format(message, values));
/*     */   }
/*     */   
/*     */   public void append(String key, byte value) {
/*  51 */     append(key, String.valueOf(value));
/*     */   }
/*     */   
/*     */   public void append(String key, short value) {
/*  55 */     append(key, String.valueOf(value));
/*     */   }
/*     */   
/*     */   public void append(String key, int value) {
/*  59 */     append(key, String.valueOf(value));
/*     */   }
/*     */   
/*     */   public void append(String key, long value) {
/*  63 */     append(key, String.valueOf(value));
/*     */   }
/*     */   
/*     */   public void append(String key, float value) {
/*  67 */     append(key, String.valueOf(value));
/*     */   }
/*     */   
/*     */   public void append(String key, double value) {
/*  71 */     append(key, String.valueOf(value));
/*     */   }
/*     */   
/*     */   public void append(String key, boolean value) {
/*  75 */     append(key, String.valueOf(value));
/*     */   }
/*     */   
/*     */   public void append(String key, char value) {
/*  79 */     append(key, String.valueOf(value));
/*     */   }
/*     */   
/*     */   public void append(String key, Object value) {
/*  83 */     append(key, getStringValue(value, Sets.newHashSet()));
/*     */   }
/*     */   
/*     */   private static String getStringValue(Object value, Set<Object> seen) {
/*  87 */     if (seen.contains(value)) {
/*  88 */       return "<Recursive>";
/*     */     }
/*  90 */     seen.add(value);
/*     */ 
/*     */     
/*  93 */     if (value instanceof Object[]) {
/*  94 */       value = Arrays.asList(new Object[] { value });
/*     */     }
/*     */     
/*  97 */     if (value instanceof java.util.Collection) {
/*  98 */       StringBuilder builder = new StringBuilder();
/*  99 */       boolean first = true;
/* 100 */       for (Object entry : value) {
/* 101 */         if (first) {
/* 102 */           first = false;
/*     */         } else {
/* 104 */           builder.append("\n");
/*     */         } 
/* 106 */         builder.append(getStringValue(entry, Sets.newHashSet(seen)));
/*     */       } 
/* 108 */       return builder.toString();
/* 109 */     }  if (value instanceof Map) {
/* 110 */       StringBuilder builder = new StringBuilder();
/* 111 */       boolean first = true;
/* 112 */       for (Map.Entry<?, ?> entry : (Iterable<Map.Entry<?, ?>>)((Map)value).entrySet()) {
/* 113 */         if (first) {
/* 114 */           first = false;
/*     */         } else {
/* 116 */           builder.append("\n");
/*     */         } 
/*     */         
/* 119 */         String key = getStringValue(entry.getKey(), Sets.newHashSet(seen)).replaceAll("[\r\n]", "");
/* 120 */         if (key.length() > 60) {
/* 121 */           key = key.substring(0, 60) + "...";
/*     */         }
/*     */         
/* 124 */         builder
/* 125 */           .append(key)
/* 126 */           .append(": ")
/* 127 */           .append(getStringValue(entry.getValue(), Sets.newHashSet(seen)));
/*     */       } 
/* 129 */       return builder.toString();
/*     */     } 
/* 131 */     return String.valueOf(value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTitle() {
/* 137 */     return this.title;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 142 */     if (!this.lines.isEmpty()) {
/* 143 */       StringBuilder builder = new StringBuilder();
/* 144 */       boolean first = true;
/* 145 */       for (Line line : this.lines) {
/* 146 */         if (first) {
/* 147 */           first = false;
/*     */         } else {
/* 149 */           builder.append("\n");
/*     */         } 
/* 151 */         builder.append(line.key).append(": ");
/* 152 */         if (line.value == null) {
/* 153 */           builder.append("null"); continue;
/* 154 */         }  if (line.value.contains("\n")) {
/* 155 */           builder.append("\n");
/* 156 */           builder.append(line.value.replaceAll("(?m)^", "\t")); continue;
/*     */         } 
/* 158 */         builder.append(line.value);
/*     */       } 
/*     */       
/* 161 */       return builder.toString();
/*     */     } 
/* 163 */     return "No data.";
/*     */   }
/*     */   
/*     */   private static class Line
/*     */   {
/*     */     private final String key;
/*     */     private final String value;
/*     */     
/*     */     public Line(String key, String value) {
/* 172 */       this.key = key;
/* 173 */       this.value = value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\report\DataReport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */