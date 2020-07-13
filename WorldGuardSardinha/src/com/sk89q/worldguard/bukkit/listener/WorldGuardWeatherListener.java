/*     */ package com.sk89q.worldguard.bukkit.listener;
/*     */ 
/*     */ import com.sk89q.worldguard.bukkit.ConfigurationManager;
/*     */ import com.sk89q.worldguard.bukkit.WorldConfiguration;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*     */ import com.sk89q.worldguard.protection.flags.DefaultFlag;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.weather.LightningStrikeEvent;
/*     */ import org.bukkit.event.weather.ThunderChangeEvent;
/*     */ import org.bukkit.event.weather.WeatherChangeEvent;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGuardWeatherListener
/*     */   implements Listener
/*     */ {
/*     */   private WorldGuardPlugin plugin;
/*     */   
/*     */   public WorldGuardWeatherListener(WorldGuardPlugin plugin) {
/*  48 */     this.plugin = plugin;
/*     */   }
/*     */   
/*     */   public void registerEvents() {
/*  52 */     this.plugin.getServer().getPluginManager().registerEvents(this, (Plugin)this.plugin);
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
/*     */   public void onWeatherChange(WeatherChangeEvent event) {
/*  57 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/*  58 */     WorldConfiguration wcfg = cfg.get(event.getWorld());
/*     */     
/*  60 */     if (event.toWeatherState()) {
/*  61 */       if (wcfg.disableWeather) {
/*  62 */         event.setCancelled(true);
/*     */       }
/*     */     }
/*  65 */     else if (!wcfg.disableWeather && wcfg.alwaysRaining) {
/*  66 */       event.setCancelled(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
/*     */   public void onThunderChange(ThunderChangeEvent event) {
/*  73 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/*  74 */     WorldConfiguration wcfg = cfg.get(event.getWorld());
/*     */     
/*  76 */     if (event.toThunderState()) {
/*  77 */       if (wcfg.disableThunder) {
/*  78 */         event.setCancelled(true);
/*     */       }
/*     */     }
/*  81 */     else if (!wcfg.disableWeather && wcfg.alwaysThundering) {
/*  82 */       event.setCancelled(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
/*     */   public void onLightningStrike(LightningStrikeEvent event) {
/*  89 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/*  90 */     WorldConfiguration wcfg = cfg.get(event.getWorld());
/*     */     
/*  92 */     if (wcfg.disallowedLightningBlocks.size() > 0) {
/*  93 */       int targetId = event.getLightning().getLocation().getBlock().getTypeId();
/*  94 */       if (wcfg.disallowedLightningBlocks.contains(Integer.valueOf(targetId))) {
/*  95 */         event.setCancelled(true);
/*     */       }
/*     */     } 
/*     */     
/*  99 */     Location loc = event.getLightning().getLocation();
/* 100 */     if (wcfg.useRegions) {
/* 101 */       ApplicableRegionSet set = this.plugin.getRegionContainer().createQuery().getApplicableRegions(loc);
/*     */       
/* 103 */       if (!set.allows(DefaultFlag.LIGHTNING))
/* 104 */         event.setCancelled(true); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\WorldGuardWeatherListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */