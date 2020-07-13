/*    */ package com.sk89q.worldguard.protection.util;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.google.common.base.Predicate;
/*    */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*    */ import java.util.Collection;
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
/*    */ public class RegionCollectionConsumer
/*    */   implements Predicate<ProtectedRegion>
/*    */ {
/*    */   private final Collection<ProtectedRegion> collection;
/*    */   private final boolean addParents;
/*    */   
/*    */   public RegionCollectionConsumer(Collection<ProtectedRegion> collection, boolean addParents) {
/* 48 */     Preconditions.checkNotNull(collection);
/*    */     
/* 50 */     this.collection = collection;
/* 51 */     this.addParents = addParents;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean apply(ProtectedRegion region) {
/* 56 */     this.collection.add(region);
/*    */     
/* 58 */     if (this.addParents) {
/* 59 */       ProtectedRegion parent = region.getParent();
/*    */       
/* 61 */       while (parent != null) {
/* 62 */         this.collection.add(parent);
/* 63 */         parent = parent.getParent();
/*    */       } 
/*    */     } 
/*    */     
/* 67 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protectio\\util\RegionCollectionConsumer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */