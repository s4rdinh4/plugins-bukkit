/*    */ package com.sk89q.worldguard.bukkit.listener;
/*    */ 
/*    */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.server.PluginDisableEvent;
/*    */ import org.bukkit.event.server.PluginEnableEvent;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.PluginManager;
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
/*    */ public class WorldGuardServerListener
/*    */   implements Listener
/*    */ {
/*    */   private final WorldGuardPlugin plugin;
/*    */   
/*    */   public WorldGuardServerListener(WorldGuardPlugin plugin) {
/* 37 */     this.plugin = plugin;
/*    */   }
/*    */   
/*    */   public void registerEvents() {
/* 41 */     PluginManager pm = this.plugin.getServer().getPluginManager();
/* 42 */     pm.registerEvents(this, (Plugin)this.plugin);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPluginEnable(PluginEnableEvent event) {
/* 47 */     if (event.getPlugin().getDescription().getName().equalsIgnoreCase("CommandBook")) {
/* 48 */       this.plugin.getGlobalStateManager().updateCommandBookGodMode();
/*    */     }
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPluginDisable(PluginDisableEvent event) {
/* 54 */     if (event.getPlugin().getDescription().getName().equalsIgnoreCase("CommandBook"))
/* 55 */       this.plugin.getGlobalStateManager().updateCommandBookGodMode(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\WorldGuardServerListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */