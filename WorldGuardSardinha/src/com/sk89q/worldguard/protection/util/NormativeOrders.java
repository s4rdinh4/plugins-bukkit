/*     */ package com.sk89q.worldguard.protection.util;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
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
/*     */ public final class NormativeOrders
/*     */ {
/*  63 */   private static final PriorityComparator PRIORITY_COMPARATOR = new PriorityComparator();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sort(List<ProtectedRegion> regions) {
/*  69 */     sortInto(Sets.newHashSet(regions), regions);
/*     */   }
/*     */   
/*     */   public static List<ProtectedRegion> fromSet(Set<ProtectedRegion> regions) {
/*  73 */     List<ProtectedRegion> sorted = Arrays.asList(new ProtectedRegion[regions.size()]);
/*  74 */     sortInto(regions, sorted);
/*  75 */     return sorted;
/*     */   }
/*     */   
/*     */   private static void sortInto(Set<ProtectedRegion> regions, List<ProtectedRegion> sorted) {
/*  79 */     List<RegionNode> root = Lists.newArrayList();
/*  80 */     Map<ProtectedRegion, RegionNode> nodes = Maps.newHashMap();
/*  81 */     for (ProtectedRegion region : regions) {
/*  82 */       addNode(nodes, root, region);
/*     */     }
/*     */     
/*  85 */     int index = regions.size() - 1;
/*  86 */     for (RegionNode node : root) {
/*  87 */       while (node != null) {
/*  88 */         if (regions.contains(node.region)) {
/*  89 */           sorted.set(index, node.region);
/*  90 */           index--;
/*     */         } 
/*  92 */         node = node.next;
/*     */       } 
/*     */     } 
/*     */     
/*  96 */     Collections.sort(sorted, PRIORITY_COMPARATOR);
/*     */   }
/*     */   
/*     */   private static RegionNode addNode(Map<ProtectedRegion, RegionNode> nodes, List<RegionNode> root, ProtectedRegion region) {
/* 100 */     RegionNode node = nodes.get(region);
/* 101 */     if (node == null) {
/* 102 */       node = new RegionNode(region);
/* 103 */       nodes.put(region, node);
/* 104 */       if (region.getParent() != null) {
/* 105 */         addNode(nodes, root, region.getParent()).insertAfter(node);
/*     */       } else {
/* 107 */         root.add(node);
/*     */       } 
/*     */     } 
/* 110 */     return node;
/*     */   }
/*     */   
/*     */   private static class RegionNode {
/*     */     @Nullable
/*     */     private RegionNode next;
/*     */     
/*     */     private RegionNode(ProtectedRegion region) {
/* 118 */       this.region = region;
/*     */     }
/*     */     private final ProtectedRegion region;
/*     */     private void insertAfter(RegionNode node) {
/* 122 */       if (this.next == null) {
/* 123 */         this.next = node;
/*     */       } else {
/* 125 */         node.next = this.next;
/* 126 */         this.next = node;
/*     */       } 
/*     */     } }
/*     */   
/*     */   private static class PriorityComparator implements Comparator<ProtectedRegion> {
/*     */     private PriorityComparator() {}
/*     */     
/*     */     public int compare(ProtectedRegion o1, ProtectedRegion o2) {
/* 134 */       if (o1.getPriority() > o2.getPriority())
/* 135 */         return -1; 
/* 136 */       if (o1.getPriority() < o2.getPriority()) {
/* 137 */         return 1;
/*     */       }
/* 139 */       return 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protectio\\util\NormativeOrders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */