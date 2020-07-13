/*     */ package com.sk89q.worldguard.bukkit.listener;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.sk89q.worldguard.bukkit.ConfigurationManager;
/*     */ import com.sk89q.worldguard.bukkit.WorldConfiguration;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Listener;
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
/*     */ 
/*     */ class AbstractListener
/*     */   implements Listener
/*     */ {
/*     */   private final WorldGuardPlugin plugin;
/*     */   
/*     */   public AbstractListener(WorldGuardPlugin plugin) {
/*  44 */     Preconditions.checkNotNull(plugin);
/*  45 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerEvents() {
/*  52 */     this.plugin.getServer().getPluginManager().registerEvents(this, (Plugin)this.plugin);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WorldGuardPlugin getPlugin() {
/*  61 */     return this.plugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ConfigurationManager getConfig() {
/*  70 */     return this.plugin.getGlobalStateManager();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WorldConfiguration getWorldConfig(World world) {
/*  80 */     return this.plugin.getGlobalStateManager().get(world);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WorldConfiguration getWorldConfig(Player player) {
/*  90 */     return getWorldConfig(player.getWorld());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isRegionSupportEnabled(World world) {
/* 100 */     return (getWorldConfig(world)).useRegions;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\AbstractListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */