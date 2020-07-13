/*    */ package com.sk89q.worldguard.bukkit.listener;
/*    */ 
/*    */ import com.google.common.base.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockState;
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
/*    */ class BlockStateAsBlockFunction
/*    */   implements Function<BlockState, Block>
/*    */ {
/*    */   public Block apply(@Nullable BlockState blockState) {
/* 32 */     return (blockState != null) ? blockState.getBlock() : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\BlockStateAsBlockFunction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */