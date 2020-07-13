/*     */ package com.sk89q.worldguard.bukkit.listener;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.sk89q.worldguard.bukkit.RegionQuery;
/*     */ import com.sk89q.worldguard.bukkit.WorldConfiguration;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.bukkit.event.block.BreakBlockEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.block.PlaceBlockEvent;
/*     */ import com.sk89q.worldguard.bukkit.util.Materials;
/*     */ import com.sk89q.worldguard.protection.association.RegionAssociable;
/*     */ import com.sk89q.worldguard.protection.flags.DefaultFlag;
/*     */ import com.sk89q.worldguard.protection.flags.StateFlag;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RegionFlagsListener
/*     */   extends AbstractListener
/*     */ {
/*     */   public RegionFlagsListener(WorldGuardPlugin plugin) {
/*  50 */     super(plugin);
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
/*     */   public void onPlaceBlock(PlaceBlockEvent event) {
/*  55 */     if (!isRegionSupportEnabled(event.getWorld()))
/*     */       return; 
/*  57 */     RegionQuery query = getPlugin().getRegionContainer().createQuery();
/*     */     
/*     */     Block block;
/*  60 */     if ((block = event.getCause().getFirstBlock()) != null)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/*  65 */       if (Materials.isPistonBlock(block.getType())) {
/*  66 */         event.filter(testState(query, DefaultFlag.PISTONS), false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
/*     */   public void onBreakBlock(BreakBlockEvent event) {
/*  73 */     if (!isRegionSupportEnabled(event.getWorld()))
/*     */       return; 
/*  75 */     WorldConfiguration config = getWorldConfig(event.getWorld());
/*  76 */     RegionQuery query = getPlugin().getRegionContainer().createQuery();
/*     */     
/*     */     Block block;
/*  79 */     if ((block = event.getCause().getFirstBlock()) != null)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/*  84 */       if (Materials.isPistonBlock(block.getType())) {
/*  85 */         event.filter(testState(query, DefaultFlag.PISTONS), false);
/*     */       }
/*     */     }
/*     */     
/*     */     Entity entity;
/*  90 */     if ((entity = event.getCause().getFirstEntity()) != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  95 */       if (entity instanceof org.bukkit.entity.Creeper) {
/*  96 */         event.filter(testState(query, DefaultFlag.CREEPER_EXPLOSION), config.explosionFlagCancellation);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 103 */       if (entity instanceof org.bukkit.entity.EnderDragon) {
/* 104 */         event.filter(testState(query, DefaultFlag.ENDERDRAGON_BLOCK_DAMAGE), config.explosionFlagCancellation);
/*     */       }
/*     */     } 
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
/*     */   private Predicate<Location> testState(final RegionQuery query, final StateFlag flag) {
/* 118 */     return new Predicate<Location>()
/*     */       {
/*     */         public boolean apply(@Nullable Location location) {
/* 121 */           return query.testState(location, (RegionAssociable)null, new StateFlag[] { this.val$flag });
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\RegionFlagsListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */