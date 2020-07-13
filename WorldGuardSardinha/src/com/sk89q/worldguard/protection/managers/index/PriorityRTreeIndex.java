/*     */ package com.sk89q.worldguard.protection.managers.index;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Supplier;
/*     */ import com.sk89q.worldedit.Vector;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegionMBRConverter;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.khelekore.prtree.MBR;
/*     */ import org.khelekore.prtree.MBRConverter;
/*     */ import org.khelekore.prtree.PRTree;
/*     */ import org.khelekore.prtree.SimpleMBR;
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
/*     */ public class PriorityRTreeIndex
/*     */   extends HashMapIndex
/*     */ {
/*     */   private static final int BRANCH_FACTOR = 30;
/*  53 */   private static final MBRConverter<ProtectedRegion> CONVERTER = (MBRConverter<ProtectedRegion>)new ProtectedRegionMBRConverter();
/*     */   
/*     */   private PRTree<ProtectedRegion> tree;
/*     */   
/*     */   public PriorityRTreeIndex() {
/*  58 */     this.tree = new PRTree(CONVERTER, 30);
/*  59 */     this.tree.load(Collections.emptyList());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void rebuildIndex() {
/*  64 */     PRTree<ProtectedRegion> newTree = new PRTree(CONVERTER, 30);
/*  65 */     newTree.load(values());
/*  66 */     this.tree = newTree;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyContaining(Vector position, Predicate<ProtectedRegion> consumer) {
/*  72 */     position = position.floor();
/*     */     
/*  74 */     Set<ProtectedRegion> seen = new HashSet<ProtectedRegion>();
/*  75 */     SimpleMBR simpleMBR = new SimpleMBR(new double[] { position.getX(), position.getX(), position.getY(), position.getY(), position.getZ(), position.getZ() });
/*     */     
/*  77 */     for (ProtectedRegion region : this.tree.find((MBR)simpleMBR)) {
/*  78 */       if (region.contains(position) && !seen.contains(region)) {
/*  79 */         seen.add(region);
/*  80 */         if (!consumer.apply(region)) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyIntersecting(ProtectedRegion region, Predicate<ProtectedRegion> consumer) {
/*  89 */     Vector min = region.getMinimumPoint().floor();
/*  90 */     Vector max = region.getMaximumPoint().ceil();
/*     */     
/*  92 */     Set<ProtectedRegion> candidates = new HashSet<ProtectedRegion>();
/*  93 */     SimpleMBR simpleMBR = new SimpleMBR(new double[] { min.getX(), max.getX(), min.getY(), max.getY(), min.getZ(), max.getZ() });
/*     */     
/*  95 */     for (ProtectedRegion found : this.tree.find((MBR)simpleMBR)) {
/*  96 */       candidates.add(found);
/*     */     }
/*     */     
/*  99 */     for (ProtectedRegion found : region.getIntersectingRegions(candidates)) {
/* 100 */       if (!consumer.apply(found)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Factory
/*     */     implements Supplier<PriorityRTreeIndex>
/*     */   {
/*     */     public PriorityRTreeIndex get() {
/* 112 */       return new PriorityRTreeIndex();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\index\PriorityRTreeIndex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */