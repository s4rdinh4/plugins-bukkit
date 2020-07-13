/*    */ package com.sk89q.worldguard.bukkit.listener.debounce;
/*    */ 
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
/*    */ public class BlockBlockPair
/*    */ {
/*    */   private final BlockMaterialPair block1;
/*    */   private final BlockMaterialPair block2;
/*    */   
/*    */   public BlockBlockPair(Block block1, Block block2) {
/* 30 */     this.block1 = new BlockMaterialPair(block1);
/* 31 */     this.block2 = new BlockMaterialPair(block2);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 36 */     if (this == o) return true; 
/* 37 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 39 */     BlockBlockPair that = (BlockBlockPair)o;
/*    */     
/* 41 */     if (!this.block1.equals(that.block1)) return false; 
/* 42 */     if (!this.block2.equals(that.block2)) return false;
/*    */     
/* 44 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 49 */     int result = this.block1.hashCode();
/* 50 */     result = 31 * result + this.block2.hashCode();
/* 51 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\debounce\BlockBlockPair.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */