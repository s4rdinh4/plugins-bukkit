/*    */ package com.sk89q.worldguard.bukkit.event.debug;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockState;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.block.BlockPlaceEvent;
/*    */ import org.bukkit.inventory.ItemStack;
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
/*    */ public class LoggingBlockPlaceEvent
/*    */   extends BlockPlaceEvent
/*    */   implements CancelLogging
/*    */ {
/* 32 */   private final CancelLogger logger = new CancelLogger();
/*    */   
/*    */   public LoggingBlockPlaceEvent(Block placedBlock, BlockState replacedBlockState, Block placedAgainst, ItemStack itemInHand, Player thePlayer, boolean canBuild) {
/* 35 */     super(placedBlock, replacedBlockState, placedAgainst, itemInHand, thePlayer, canBuild);
/*    */   }
/*    */   
/*    */   public List<CancelAttempt> getCancels() {
/* 39 */     return this.logger.getCancels();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 44 */     this.logger.log(isCancelled(), cancel, (new Exception()).getStackTrace());
/* 45 */     super.setCancelled(cancel);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\event\debug\LoggingBlockPlaceEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */