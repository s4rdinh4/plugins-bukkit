/*    */ package com.sk89q.worldguard.bukkit.event.debug;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.block.BlockBreakEvent;
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
/*    */ public class LoggingBlockBreakEvent
/*    */   extends BlockBreakEvent
/*    */   implements CancelLogging
/*    */ {
/* 30 */   private final CancelLogger logger = new CancelLogger();
/*    */   
/*    */   public LoggingBlockBreakEvent(Block block, Player player) {
/* 33 */     super(block, player);
/*    */   }
/*    */   
/*    */   public List<CancelAttempt> getCancels() {
/* 37 */     return this.logger.getCancels();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 42 */     this.logger.log(isCancelled(), cancel, (new Exception()).getStackTrace());
/* 43 */     super.setCancelled(cancel);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\event\debug\LoggingBlockBreakEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */