/*    */ package com.sk89q.worldguard.bukkit.listener.debounce;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.block.BlockPistonExtendEvent;
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
/*    */ public class BlockPistonExtendKey
/*    */ {
/*    */   private final Block piston;
/*    */   private final List<Block> blocks;
/*    */   private final int blocksHashCode;
/*    */   
/*    */   public BlockPistonExtendKey(BlockPistonExtendEvent event) {
/* 34 */     this.piston = event.getBlock();
/* 35 */     this.blocks = event.getBlocks();
/* 36 */     this.blocksHashCode = this.blocks.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 41 */     if (this == o) return true; 
/* 42 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 44 */     BlockPistonExtendKey that = (BlockPistonExtendKey)o;
/*    */     
/* 46 */     if (!this.blocks.equals(that.blocks)) return false; 
/* 47 */     if (!this.piston.equals(that.piston)) return false;
/*    */     
/* 49 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 54 */     int result = this.piston.hashCode();
/* 55 */     result = 31 * result + this.blocksHashCode;
/* 56 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\debounce\BlockPistonExtendKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */