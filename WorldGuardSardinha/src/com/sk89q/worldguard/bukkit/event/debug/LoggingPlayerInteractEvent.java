/*    */ package com.sk89q.worldguard.bukkit.event.debug;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockFace;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.block.Action;
/*    */ import org.bukkit.event.player.PlayerInteractEvent;
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
/*    */ public class LoggingPlayerInteractEvent
/*    */   extends PlayerInteractEvent
/*    */   implements CancelLogging
/*    */ {
/* 33 */   private final CancelLogger logger = new CancelLogger();
/*    */   
/*    */   public LoggingPlayerInteractEvent(Player who, Action action, ItemStack item, Block clickedBlock, BlockFace clickedFace) {
/* 36 */     super(who, action, item, clickedBlock, clickedFace);
/*    */   }
/*    */   
/*    */   public List<CancelAttempt> getCancels() {
/* 40 */     return this.logger.getCancels();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 45 */     this.logger.log(isCancelled(), cancel, (new Exception()).getStackTrace());
/* 46 */     super.setCancelled(cancel);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\event\debug\LoggingPlayerInteractEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */