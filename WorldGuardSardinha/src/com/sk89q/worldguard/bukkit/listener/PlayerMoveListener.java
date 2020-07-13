/*     */ package com.sk89q.worldguard.bukkit.listener;
/*     */ 
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.session.MoveType;
/*     */ import com.sk89q.worldguard.session.Session;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.event.player.PlayerRespawnEvent;
/*     */ import org.bukkit.event.vehicle.VehicleEnterEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerMoveListener
/*     */   implements Listener
/*     */ {
/*     */   private final WorldGuardPlugin plugin;
/*     */   
/*     */   public PlayerMoveListener(WorldGuardPlugin plugin) {
/*  44 */     this.plugin = plugin;
/*     */   }
/*     */   
/*     */   public void registerEvents() {
/*  48 */     if ((this.plugin.getGlobalStateManager()).usePlayerMove) {
/*  49 */       PluginManager pm = this.plugin.getServer().getPluginManager();
/*  50 */       pm.registerEvents(this, (Plugin)this.plugin);
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerRespawn(PlayerRespawnEvent event) {
/*  56 */     Player player = event.getPlayer();
/*     */     
/*  58 */     Session session = this.plugin.getSessionManager().get(player);
/*  59 */     session.testMoveTo(player, event.getRespawnLocation(), MoveType.RESPAWN, true);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onVehicleEnter(VehicleEnterEvent event) {
/*  64 */     Entity entity = event.getEntered();
/*  65 */     if (entity instanceof Player) {
/*  66 */       Player player = (Player)entity;
/*  67 */       Session session = this.plugin.getSessionManager().get(player);
/*  68 */       if (null != session.testMoveTo(player, event.getVehicle().getLocation(), MoveType.EMBARK, true)) {
/*  69 */         event.setCancelled(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH)
/*     */   public void onPlayerMove(PlayerMoveEvent event) {
/*  76 */     final Player player = event.getPlayer();
/*     */     
/*  78 */     Session session = this.plugin.getSessionManager().get(player);
/*  79 */     final Location override = session.testMoveTo(player, event.getTo(), MoveType.MOVE);
/*     */     
/*  81 */     if (override != null) {
/*  82 */       override.setX(override.getBlockX() + 0.5D);
/*  83 */       override.setY(override.getBlockY());
/*  84 */       override.setZ(override.getBlockZ() + 0.5D);
/*  85 */       override.setPitch(event.getTo().getPitch());
/*  86 */       override.setYaw(event.getTo().getYaw());
/*     */       
/*  88 */       event.setTo(override.clone());
/*     */       
/*  90 */       Entity vehicle = player.getVehicle();
/*  91 */       if (vehicle != null) {
/*  92 */         vehicle.eject();
/*     */         
/*  94 */         Entity current = vehicle;
/*  95 */         while (current != null) {
/*  96 */           current.eject();
/*  97 */           vehicle.setVelocity(new Vector());
/*  98 */           if (vehicle instanceof org.bukkit.entity.LivingEntity) {
/*  99 */             vehicle.teleport(override.clone());
/*     */           } else {
/* 101 */             vehicle.teleport(override.clone().add(0.0D, 1.0D, 0.0D));
/*     */           } 
/* 103 */           current = current.getVehicle();
/*     */         } 
/*     */         
/* 106 */         player.teleport(override.clone().add(0.0D, 1.0D, 0.0D));
/*     */         
/* 108 */         Bukkit.getScheduler().runTaskLater((Plugin)this.plugin, new Runnable()
/*     */             {
/*     */               public void run() {
/* 111 */                 player.teleport(override.clone().add(0.0D, 1.0D, 0.0D));
/*     */               }
/*     */             }1L);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\PlayerMoveListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */