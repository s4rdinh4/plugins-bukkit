/*     */ package com.sk89q.worldguard.bukkit;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*     */ import com.sk89q.worldguard.protection.FailedLoadRegionSet;
/*     */ import com.sk89q.worldguard.protection.GlobalRegionManager;
/*     */ import com.sk89q.worldguard.protection.PermissiveRegionSet;
/*     */ import com.sk89q.worldguard.protection.association.RegionAssociable;
/*     */ import com.sk89q.worldguard.protection.flags.DefaultFlag;
/*     */ import com.sk89q.worldguard.protection.flags.Flag;
/*     */ import com.sk89q.worldguard.protection.flags.StateFlag;
/*     */ import com.sk89q.worldguard.protection.managers.RegionManager;
/*     */ import java.util.Collection;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RegionQuery
/*     */ {
/*     */   private final WorldGuardPlugin plugin;
/*     */   private final ConfigurationManager config;
/*     */   private final GlobalRegionManager globalManager;
/*     */   private final QueryCache cache;
/*     */   
/*     */   RegionQuery(WorldGuardPlugin plugin, QueryCache cache) {
/*  68 */     Preconditions.checkNotNull(plugin);
/*  69 */     Preconditions.checkNotNull(cache);
/*     */     
/*  71 */     this.plugin = plugin;
/*  72 */     this.config = plugin.getGlobalStateManager();
/*  73 */     this.cache = cache;
/*     */     
/*  75 */     this.globalManager = plugin.getGlobalRegionManager();
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
/*     */   public ApplicableRegionSet getApplicableRegions(Location location) {
/*  91 */     Preconditions.checkNotNull(location);
/*     */     
/*  93 */     World world = location.getWorld();
/*  94 */     WorldConfiguration worldConfig = this.config.get(world);
/*     */     
/*  96 */     if (!worldConfig.useRegions) {
/*  97 */       return (ApplicableRegionSet)PermissiveRegionSet.getInstance();
/*     */     }
/*     */     
/* 100 */     RegionManager manager = this.globalManager.get(location.getWorld());
/* 101 */     if (manager != null) {
/* 102 */       return this.cache.queryContains(manager, location);
/*     */     }
/* 104 */     return (ApplicableRegionSet)FailedLoadRegionSet.getInstance();
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
/*     */   public boolean testBuild(Location location, Player player, StateFlag... flag) {
/* 135 */     if (flag.length == 0) {
/* 136 */       return testState(location, player, new StateFlag[] { DefaultFlag.BUILD });
/*     */     }
/*     */     
/* 139 */     return StateFlag.test(new StateFlag.State[] { StateFlag.combine(new StateFlag.State[] {
/* 140 */               StateFlag.denyToNone(queryState(location, player, new StateFlag[] { DefaultFlag.BUILD
/* 141 */                   })), queryState(location, player, flag)
/*     */             }) });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean testBuild(Location location, RegionAssociable associable, StateFlag... flag) {
/* 171 */     if (flag.length == 0) {
/* 172 */       return testState(location, associable, new StateFlag[] { DefaultFlag.BUILD });
/*     */     }
/*     */     
/* 175 */     return StateFlag.test(new StateFlag.State[] { StateFlag.combine(new StateFlag.State[] {
/* 176 */               StateFlag.denyToNone(queryState(location, associable, new StateFlag[] { DefaultFlag.BUILD
/* 177 */                   })), queryState(location, associable, flag)
/*     */             }) });
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
/*     */ 
/*     */   
/*     */   public boolean testState(Location location, @Nullable Player player, StateFlag... flag) {
/* 200 */     return StateFlag.test(new StateFlag.State[] { queryState(location, player, flag) });
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean testState(Location location, @Nullable RegionAssociable associable, StateFlag... flag) {
/* 223 */     return StateFlag.test(new StateFlag.State[] { queryState(location, associable, flag) });
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
/*     */   
/*     */   @Nullable
/*     */   public StateFlag.State queryState(Location location, @Nullable Player player, StateFlag... flags) {
/* 245 */     LocalPlayer localPlayer = (player != null) ? this.plugin.wrapPlayer(player) : null;
/* 246 */     return getApplicableRegions(location).queryState((RegionAssociable)localPlayer, flags);
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
/*     */   
/*     */   @Nullable
/*     */   public StateFlag.State queryState(Location location, @Nullable RegionAssociable associable, StateFlag... flags) {
/* 268 */     return getApplicableRegions(location).queryState(associable, flags);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <V> V queryValue(Location location, @Nullable Player player, Flag<V> flag) {
/* 297 */     LocalPlayer localPlayer = (player != null) ? this.plugin.wrapPlayer(player) : null;
/* 298 */     return (V)getApplicableRegions(location).queryValue((RegionAssociable)localPlayer, flag);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <V> V queryValue(Location location, @Nullable RegionAssociable associable, Flag<V> flag) {
/* 327 */     return (V)getApplicableRegions(location).queryValue(associable, flag);
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
/*     */   
/*     */   public <V> Collection<V> queryAllValues(Location location, @Nullable Player player, Flag<V> flag) {
/* 348 */     LocalPlayer localPlayer = (player != null) ? this.plugin.wrapPlayer(player) : null;
/* 349 */     return getApplicableRegions(location).queryAllValues((RegionAssociable)localPlayer, flag);
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
/*     */   
/*     */   public <V> Collection<V> queryAllValues(Location location, @Nullable RegionAssociable associable, Flag<V> flag) {
/* 370 */     return getApplicableRegions(location).queryAllValues(associable, flag);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\RegionQuery.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */