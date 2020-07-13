/*    */ package com.sk89q.worldguard.bukkit.listener;
/*    */ 
/*    */ import com.sk89q.worldguard.bukkit.ConfigurationManager;
/*    */ import com.sk89q.worldguard.bukkit.WorldConfiguration;
/*    */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*    */ import com.sk89q.worldguard.bukkit.util.Locations;
/*    */ import com.sk89q.worldguard.session.MoveType;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.entity.Vehicle;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.vehicle.VehicleMoveEvent;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.util.Vector;
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
/*    */ public class WorldGuardVehicleListener
/*    */   implements Listener
/*    */ {
/*    */   private WorldGuardPlugin plugin;
/*    */   
/*    */   public WorldGuardVehicleListener(WorldGuardPlugin plugin) {
/* 45 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void registerEvents() {
/* 52 */     this.plugin.getServer().getPluginManager().registerEvents(this, (Plugin)this.plugin);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onVehicleMove(VehicleMoveEvent event) {
/* 57 */     Vehicle vehicle = event.getVehicle();
/* 58 */     if (vehicle.getPassenger() == null || !(vehicle.getPassenger() instanceof Player))
/* 59 */       return;  Player player = (Player)vehicle.getPassenger();
/* 60 */     World world = vehicle.getWorld();
/* 61 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 62 */     WorldConfiguration wcfg = cfg.get(world);
/*    */     
/* 64 */     if (wcfg.useRegions)
/*    */     {
/* 66 */       if (Locations.isDifferentBlock(event.getFrom(), event.getTo()) && 
/* 67 */         null != this.plugin.getSessionManager().get(player).testMoveTo(player, event.getTo(), MoveType.RIDE)) {
/* 68 */         vehicle.setVelocity(new Vector(0, 0, 0));
/* 69 */         vehicle.teleport(event.getFrom());
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\WorldGuardVehicleListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */