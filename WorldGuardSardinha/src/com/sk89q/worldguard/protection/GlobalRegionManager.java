/*     */ package com.sk89q.worldguard.protection;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import com.sk89q.worldguard.bukkit.BukkitPlayer;
/*     */ import com.sk89q.worldguard.bukkit.RegionContainer;
/*     */ import com.sk89q.worldguard.bukkit.RegionQuery;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.protection.association.RegionAssociable;
/*     */ import com.sk89q.worldguard.protection.flags.DefaultFlag;
/*     */ import com.sk89q.worldguard.protection.flags.StateFlag;
/*     */ import com.sk89q.worldguard.protection.managers.RegionManager;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class GlobalRegionManager
/*     */ {
/*     */   private final WorldGuardPlugin plugin;
/*     */   private final RegionContainer container;
/*     */   
/*     */   public GlobalRegionManager(WorldGuardPlugin plugin, RegionContainer container) {
/*  60 */     Preconditions.checkNotNull(plugin);
/*  61 */     Preconditions.checkNotNull(container);
/*  62 */     this.plugin = plugin;
/*  63 */     this.container = container;
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
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public RegionManager get(World world) {
/*  78 */     return this.container.get(world);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<RegionManager> getLoaded() {
/*  87 */     return Collections.unmodifiableList(this.container.getLoaded());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RegionQuery createQuery() {
/*  96 */     return this.container.createQuery();
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
/*     */   @Deprecated
/*     */   public boolean hasBypass(LocalPlayer player, World world) {
/* 109 */     return player.hasPermission("worldguard.region.bypass." + world.getName());
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
/*     */   @Deprecated
/*     */   public boolean hasBypass(Player player, World world) {
/* 122 */     return this.plugin.hasPermission((CommandSender)player, "worldguard.region.bypass." + world.getName());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean canBuild(Player player, Block block) {
/* 143 */     return canBuild(player, block.getLocation());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean canBuild(Player player, Location location) {
/* 163 */     return (hasBypass(player, location.getWorld()) || createQuery().testState(location, player, new StateFlag[] { DefaultFlag.BUILD }));
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
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean canConstruct(Player player, Block block) {
/* 178 */     return canBuild(player, block.getLocation());
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
/*     */   @Deprecated
/*     */   public boolean canConstruct(Player player, Location location) {
/* 191 */     return canBuild(player, location);
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
/*     */   
/*     */   @Deprecated
/*     */   public boolean allows(StateFlag flag, Location location) {
/* 205 */     return allows(flag, location, null);
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
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean allows(StateFlag flag, Location location, @Nullable LocalPlayer player) {
/* 220 */     if (player == null)
/* 221 */       return StateFlag.test(new StateFlag.State[] { createQuery().queryState(location, (RegionAssociable)null, new StateFlag[] { flag }) }); 
/* 222 */     if (player instanceof BukkitPlayer) {
/* 223 */       Player p = ((BukkitPlayer)player).getPlayer();
/* 224 */       return StateFlag.test(new StateFlag.State[] { createQuery().queryState(location, p, new StateFlag[] { flag }) });
/*     */     } 
/* 226 */     throw new IllegalArgumentException("Can't take a non-Bukkit player");
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\GlobalRegionManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */