/*    */ package com.sk89q.worldguard.bukkit.listener;
/*    */ 
/*    */ import com.sk89q.worldguard.bukkit.WorldConfiguration;
/*    */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*    */ import com.sk89q.worldguard.bukkit.event.entity.SpawnEntityEvent;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
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
/*    */ public class WorldRulesListener
/*    */   extends AbstractListener
/*    */ {
/*    */   public WorldRulesListener(WorldGuardPlugin plugin) {
/* 37 */     super(plugin);
/*    */   }
/*    */   
/*    */   @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
/*    */   public void onSpawnEntity(SpawnEntityEvent event) {
/* 42 */     WorldConfiguration config = getWorldConfig(event.getWorld());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 48 */     if (event.getEffectiveType() == EntityType.EXPERIENCE_ORB && 
/* 49 */       config.disableExpDrops)
/* 50 */       event.setCancelled(true); 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\WorldRulesListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */