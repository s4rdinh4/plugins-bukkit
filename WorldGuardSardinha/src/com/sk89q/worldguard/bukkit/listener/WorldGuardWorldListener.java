/*     */ package com.sk89q.worldguard.bukkit.listener;
/*     */ 
/*     */ import com.sk89q.worldguard.bukkit.BukkitUtil;
/*     */ import com.sk89q.worldguard.bukkit.ConfigurationManager;
/*     */ import com.sk89q.worldguard.bukkit.WorldConfiguration;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.world.ChunkLoadEvent;
/*     */ import org.bukkit.event.world.WorldLoadEvent;
/*     */ import org.bukkit.plugin.Plugin;
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
/*     */ public class WorldGuardWorldListener
/*     */   implements Listener
/*     */ {
/*  37 */   private static final Logger log = Logger.getLogger(WorldGuardWorldListener.class.getCanonicalName());
/*     */ 
/*     */ 
/*     */   
/*     */   private WorldGuardPlugin plugin;
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldGuardWorldListener(WorldGuardPlugin plugin) {
/*  46 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerEvents() {
/*  53 */     this.plugin.getServer().getPluginManager().registerEvents(this, (Plugin)this.plugin);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onChunkLoad(ChunkLoadEvent event) {
/*  58 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/*     */     
/*  60 */     if (cfg.activityHaltToggle) {
/*  61 */       int removed = 0;
/*     */       
/*  63 */       for (Entity entity : event.getChunk().getEntities()) {
/*  64 */         if (BukkitUtil.isIntensiveEntity(entity)) {
/*  65 */           entity.remove();
/*  66 */           removed++;
/*     */         } 
/*     */       } 
/*     */       
/*  70 */       if (removed > 50) {
/*  71 */         log.info("Halt-Act: " + removed + " entities (>50) auto-removed from " + event.getChunk().toString());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onWorldLoad(WorldLoadEvent event) {
/*  78 */     initWorld(event.getWorld());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initWorld(World world) {
/*  90 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/*  91 */     WorldConfiguration wcfg = cfg.get(world);
/*  92 */     if (wcfg.alwaysRaining && !wcfg.disableWeather) {
/*  93 */       world.setStorm(true);
/*  94 */     } else if (wcfg.disableWeather && !wcfg.alwaysRaining) {
/*  95 */       world.setStorm(false);
/*     */     } 
/*  97 */     if (wcfg.alwaysThundering && !wcfg.disableThunder) {
/*  98 */       world.setThundering(true);
/*  99 */     } else if (wcfg.disableThunder && !wcfg.alwaysThundering) {
/* 100 */       world.setStorm(false);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\WorldGuardWorldListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */