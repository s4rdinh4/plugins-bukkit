/*     */ package com.sk89q.worldguard.bukkit.listener;
/*     */ 
/*     */ import com.sk89q.worldguard.bukkit.ConfigurationManager;
/*     */ import com.sk89q.worldguard.bukkit.WorldConfiguration;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.protection.flags.DefaultFlag;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Hanging;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Projectile;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.hanging.HangingBreakByEntityEvent;
/*     */ import org.bukkit.event.hanging.HangingBreakEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.projectiles.ProjectileSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGuardHangingListener
/*     */   implements Listener
/*     */ {
/*     */   private WorldGuardPlugin plugin;
/*     */   
/*     */   public WorldGuardHangingListener(WorldGuardPlugin plugin) {
/*  58 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerEvents() {
/*  65 */     this.plugin.getServer().getPluginManager().registerEvents(this, (Plugin)this.plugin);
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
/*     */   public void onHangingBreak(HangingBreakEvent event) {
/*  70 */     Hanging hanging = event.getEntity();
/*  71 */     World world = hanging.getWorld();
/*  72 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/*  73 */     WorldConfiguration wcfg = cfg.get(world);
/*     */     
/*  75 */     if (event instanceof HangingBreakByEntityEvent) {
/*  76 */       LivingEntity livingEntity; HangingBreakByEntityEvent entityEvent = (HangingBreakByEntityEvent)event;
/*  77 */       Entity removerEntity = entityEvent.getRemover();
/*  78 */       if (removerEntity instanceof Projectile) {
/*  79 */         Projectile projectile = (Projectile)removerEntity;
/*  80 */         ProjectileSource remover = projectile.getShooter();
/*  81 */         livingEntity = (remover instanceof LivingEntity) ? (LivingEntity)remover : null;
/*     */       } 
/*     */       
/*  84 */       if (!(livingEntity instanceof org.bukkit.entity.Player)) {
/*  85 */         if (livingEntity instanceof org.bukkit.entity.Creeper) {
/*  86 */           if (wcfg.blockCreeperBlockDamage || wcfg.blockCreeperExplosions) {
/*  87 */             event.setCancelled(true);
/*     */             return;
/*     */           } 
/*  90 */           if (wcfg.useRegions && !this.plugin.getGlobalRegionManager().allows(DefaultFlag.CREEPER_EXPLOSION, hanging.getLocation())) {
/*  91 */             event.setCancelled(true);
/*     */ 
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */         
/*  98 */         if (hanging instanceof org.bukkit.entity.Painting && (wcfg.blockEntityPaintingDestroy || (wcfg.useRegions && 
/*     */ 
/*     */           
/* 101 */           !this.plugin.getGlobalRegionManager().allows(DefaultFlag.ENTITY_PAINTING_DESTROY, hanging.getLocation())))) {
/* 102 */           event.setCancelled(true);
/* 103 */         } else if (hanging instanceof org.bukkit.entity.ItemFrame && (wcfg.blockEntityItemFrameDestroy || (wcfg.useRegions && 
/*     */ 
/*     */           
/* 106 */           !this.plugin.getGlobalRegionManager().allows(DefaultFlag.ENTITY_ITEM_FRAME_DESTROY, hanging.getLocation())))) {
/* 107 */           event.setCancelled(true);
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 112 */     } else if (hanging instanceof org.bukkit.entity.Painting && wcfg.blockEntityPaintingDestroy && event
/* 113 */       .getCause() == HangingBreakEvent.RemoveCause.EXPLOSION) {
/* 114 */       event.setCancelled(true);
/* 115 */     } else if (hanging instanceof org.bukkit.entity.ItemFrame && wcfg.blockEntityItemFrameDestroy && event
/* 116 */       .getCause() == HangingBreakEvent.RemoveCause.EXPLOSION) {
/* 117 */       event.setCancelled(true);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\WorldGuardHangingListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */