/*     */ package com.sk89q.worldguard.util.report;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
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
/*     */ public class ReportList
/*     */   implements Report, List<Report>
/*     */ {
/*     */   private final String title;
/*  32 */   private final List<Report> reports = Lists.newArrayList();
/*     */   
/*     */   public ReportList(String title) {
/*  35 */     this.title = title;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTitle() {
/*  40 */     return this.title;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  45 */     return this.reports.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  50 */     return this.reports.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object o) {
/*  55 */     return this.reports.contains(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Report> iterator() {
/*  60 */     return this.reports.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/*  65 */     return this.reports.toArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T[] toArray(T[] a) {
/*  70 */     return this.reports.toArray(a);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(Report report) {
/*  75 */     return this.reports.add(report);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object o) {
/*  80 */     return this.reports.remove(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAll(Collection<?> c) {
/*  85 */     return this.reports.containsAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Report> c) {
/*  90 */     return this.reports.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, Collection<? extends Report> c) {
/*  95 */     return this.reports.addAll(index, c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeAll(Collection<?> c) {
/* 100 */     return this.reports.removeAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean retainAll(Collection<?> c) {
/* 105 */     return this.reports.retainAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 110 */     this.reports.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 115 */     return this.reports.equals(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 120 */     return this.reports.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public Report get(int index) {
/* 125 */     return this.reports.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public Report set(int index, Report element) {
/* 130 */     return this.reports.set(index, element);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(int index, Report element) {
/* 135 */     this.reports.add(index, element);
/*     */   }
/*     */ 
/*     */   
/*     */   public Report remove(int index) {
/* 140 */     return this.reports.remove(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(Object o) {
/* 145 */     return this.reports.indexOf(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int lastIndexOf(Object o) {
/* 150 */     return this.reports.lastIndexOf(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public ListIterator<Report> listIterator() {
/* 155 */     return this.reports.listIterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public ListIterator<Report> listIterator(int index) {
/* 160 */     return this.reports.listIterator(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Report> subList(int fromIndex, int toIndex) {
/* 165 */     return this.reports.subList(fromIndex, toIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 170 */     if (!this.reports.isEmpty()) {
/* 171 */       StringBuilder builder = new StringBuilder();
/* 172 */       for (Report report : this.reports) {
/* 173 */         builder.append("================================\n")
/* 174 */           .append(report.getTitle())
/* 175 */           .append("\n================================")
/* 176 */           .append("\n\n")
/* 177 */           .append(report.toString())
/* 178 */           .append("\n\n");
/*     */       }
/* 180 */       return builder.toString();
/*     */     } 
/* 182 */     return "No reports.";
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\report\ReportList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */