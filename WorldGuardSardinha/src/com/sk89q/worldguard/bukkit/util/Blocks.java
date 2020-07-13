/*    */ package com.sk89q.worldguard.bukkit.util;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.material.Bed;
/*    */ import org.bukkit.material.MaterialData;
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
/*    */ public final class Blocks
/*    */ {
/*    */   public static List<Block> getConnected(Block block) {
/* 46 */     MaterialData data = block.getState().getData();
/*    */     
/* 48 */     if (data instanceof Bed) {
/* 49 */       Bed bed = (Bed)data;
/* 50 */       if (bed.isHeadOfBed()) {
/* 51 */         return Arrays.asList(new Block[] { block.getRelative(bed.getFacing().getOppositeFace()) });
/*    */       }
/* 53 */       return Arrays.asList(new Block[] { block.getRelative(bed.getFacing()) });
/*    */     } 
/*    */     
/* 56 */     return Collections.emptyList();
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukki\\util\Blocks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */