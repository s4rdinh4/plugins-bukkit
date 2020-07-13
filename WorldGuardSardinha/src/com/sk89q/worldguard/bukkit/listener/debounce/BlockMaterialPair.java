/*    */ package com.sk89q.worldguard.bukkit.listener.debounce;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
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
/*    */ public class BlockMaterialPair
/*    */ {
/*    */   private final Block block;
/*    */   private final Material material;
/*    */   
/*    */   public BlockMaterialPair(Block block) {
/* 33 */     Preconditions.checkNotNull(block);
/* 34 */     this.block = block;
/* 35 */     this.material = block.getType();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 40 */     if (this == o) return true; 
/* 41 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 43 */     BlockMaterialPair blockKey = (BlockMaterialPair)o;
/*    */     
/* 45 */     if (!this.block.equals(blockKey.block)) return false; 
/* 46 */     if (this.material != blockKey.material) return false;
/*    */     
/* 48 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 53 */     int result = this.block.hashCode();
/* 54 */     result = 31 * result + this.material.hashCode();
/* 55 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\debounce\BlockMaterialPair.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */