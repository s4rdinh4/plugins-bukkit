/*    */ package com.sk89q.worldguard.bukkit.listener.debounce;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.block.BlockPistonRetractEvent;
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
/*    */ public class BlockPistonRetractKey
/*    */ {
/*    */   private final Block piston;
/*    */   private final Block retract;
/*    */   
/*    */   public BlockPistonRetractKey(BlockPistonRetractEvent event) {
/* 31 */     this.piston = event.getBlock();
/* 32 */     this.retract = event.getRetractLocation().getBlock();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 37 */     if (this == o) return true; 
/* 38 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 40 */     BlockPistonRetractKey that = (BlockPistonRetractKey)o;
/*    */     
/* 42 */     if (!this.piston.equals(that.piston)) return false; 
/* 43 */     if (!this.retract.equals(that.retract)) return false;
/*    */     
/* 45 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 50 */     int result = this.piston.hashCode();
/* 51 */     result = 31 * result + this.retract.hashCode();
/* 52 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\debounce\BlockPistonRetractKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */