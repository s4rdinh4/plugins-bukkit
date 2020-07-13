/*     */ package com.sk89q.worldguard.bukkit.listener;
/*     */ 
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import com.sk89q.worldguard.bukkit.BukkitUtil;
/*     */ import com.sk89q.worldguard.bukkit.ConfigurationManager;
/*     */ import com.sk89q.worldguard.bukkit.RegionQuery;
/*     */ import com.sk89q.worldguard.bukkit.WorldConfiguration;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*     */ import com.sk89q.worldguard.protection.flags.DefaultFlag;
/*     */ import com.sk89q.worldguard.protection.flags.Flag;
/*     */ import com.sk89q.worldguard.protection.flags.StateFlag;
/*     */ import com.sk89q.worldguard.protection.managers.RegionManager;
/*     */ import java.util.Set;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Fireball;
/*     */ import org.bukkit.entity.ItemFrame;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Projectile;
/*     */ import org.bukkit.entity.Tameable;
/*     */ import org.bukkit.entity.Wolf;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ import org.bukkit.event.entity.CreeperPowerEvent;
/*     */ import org.bukkit.event.entity.EntityChangeBlockEvent;
/*     */ import org.bukkit.event.entity.EntityCreatePortalEvent;
/*     */ import org.bukkit.event.entity.EntityDamageByBlockEvent;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.EntityDeathEvent;
/*     */ import org.bukkit.event.entity.EntityExplodeEvent;
/*     */ import org.bukkit.event.entity.EntityInteractEvent;
/*     */ import org.bukkit.event.entity.EntityRegainHealthEvent;
/*     */ import org.bukkit.event.entity.ExplosionPrimeEvent;
/*     */ import org.bukkit.event.entity.PigZapEvent;
/*     */ import org.bukkit.event.entity.PlayerDeathEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.projectiles.ProjectileSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGuardEntityListener
/*     */   implements Listener
/*     */ {
/*     */   private WorldGuardPlugin plugin;
/*     */   
/*     */   public WorldGuardEntityListener(WorldGuardPlugin plugin) {
/*  59 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerEvents() {
/*  66 */     this.plugin.getServer().getPluginManager().registerEvents(this, (Plugin)this.plugin);
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
/*     */   public void onEntityInteract(EntityInteractEvent event) {
/*  71 */     Entity entity = event.getEntity();
/*  72 */     Block block = event.getBlock();
/*     */     
/*  74 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/*  75 */     WorldConfiguration wcfg = cfg.get(entity.getWorld());
/*     */     
/*  77 */     if (block.getTypeId() == 60 && 
/*  78 */       wcfg.disableCreatureCropTrampling)
/*     */     {
/*  80 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH)
/*     */   public void onEntityDeath(EntityDeathEvent event) {
/*  87 */     WorldConfiguration wcfg = this.plugin.getGlobalStateManager().get(event.getEntity().getWorld());
/*     */     
/*  89 */     if (event instanceof PlayerDeathEvent && wcfg.disableDeathMessages) {
/*  90 */       ((PlayerDeathEvent)event).setDeathMessage("");
/*     */     }
/*     */   }
/*     */   
/*     */   private void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
/*  95 */     Entity defender = event.getEntity();
/*  96 */     EntityDamageEvent.DamageCause type = event.getCause();
/*     */     
/*  98 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/*  99 */     WorldConfiguration wcfg = cfg.get(defender.getWorld());
/*     */     
/* 101 */     if (defender instanceof Wolf && ((Wolf)defender).isTamed()) {
/* 102 */       if (wcfg.antiWolfDumbness && type != EntityDamageEvent.DamageCause.VOID) {
/* 103 */         event.setCancelled(true);
/*     */         return;
/*     */       } 
/* 106 */     } else if (defender instanceof Player) {
/* 107 */       Player player = (Player)defender;
/*     */       
/* 109 */       if (wcfg.disableLavaDamage && type == EntityDamageEvent.DamageCause.LAVA) {
/* 110 */         event.setCancelled(true);
/* 111 */         player.setFireTicks(0);
/*     */         
/*     */         return;
/*     */       } 
/* 115 */       if (wcfg.disableContactDamage && type == EntityDamageEvent.DamageCause.CONTACT) {
/* 116 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/* 120 */       if (wcfg.teleportOnVoid && type == EntityDamageEvent.DamageCause.VOID) {
/* 121 */         BukkitUtil.findFreePosition(player);
/* 122 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/* 126 */       if (wcfg.disableVoidDamage && type == EntityDamageEvent.DamageCause.VOID) {
/* 127 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/* 131 */       if (type == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION && (wcfg.disableExplosionDamage || wcfg.blockOtherExplosions || (wcfg.explosionFlagCancellation && 
/*     */ 
/*     */         
/* 134 */         !this.plugin.getGlobalRegionManager().allows(DefaultFlag.OTHER_EXPLOSION, player.getLocation())))) {
/* 135 */         event.setCancelled(true);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/* 142 */     } else if (type == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION && (wcfg.blockOtherExplosions || (wcfg.explosionFlagCancellation && 
/*     */ 
/*     */       
/* 145 */       !this.plugin.getGlobalRegionManager().allows(DefaultFlag.OTHER_EXPLOSION, defender.getLocation())))) {
/* 146 */       event.setCancelled(true);
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
/* 154 */     if (event.getDamager() instanceof Projectile) {
/* 155 */       onEntityDamageByProjectile(event);
/*     */       
/*     */       return;
/*     */     } 
/* 159 */     Entity attacker = event.getDamager();
/* 160 */     Entity defender = event.getEntity();
/*     */     
/* 162 */     if (defender instanceof ItemFrame && 
/* 163 */       checkItemFrameProtection(attacker, (ItemFrame)defender)) {
/* 164 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 169 */     if (defender instanceof Player) {
/* 170 */       Player player = (Player)defender;
/* 171 */       LocalPlayer localPlayer = this.plugin.wrapPlayer(player);
/*     */       
/* 173 */       ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 174 */       WorldConfiguration wcfg = cfg.get(player.getWorld());
/*     */       
/* 176 */       if (wcfg.disableLightningDamage && event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
/* 177 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/* 181 */       if (wcfg.disableExplosionDamage) {
/* 182 */         switch (event.getCause()) {
/*     */           case ENDER_DRAGON:
/*     */           case null:
/* 185 */             event.setCancelled(true);
/*     */             return;
/*     */         } 
/*     */       
/*     */       }
/* 190 */       if (attacker != null) {
/* 191 */         if (attacker instanceof org.bukkit.entity.TNTPrimed || attacker instanceof org.bukkit.entity.minecart.ExplosiveMinecart)
/*     */         {
/* 193 */           if (wcfg.blockTNTExplosions) {
/* 194 */             event.setCancelled(true);
/*     */             
/*     */             return;
/*     */           } 
/*     */         }
/* 199 */         if (attacker instanceof Fireball) {
/* 200 */           if (attacker instanceof org.bukkit.entity.WitherSkull) {
/* 201 */             if (wcfg.blockWitherSkullExplosions) {
/* 202 */               event.setCancelled(true);
/*     */               
/*     */               return;
/*     */             } 
/* 206 */           } else if (wcfg.blockFireballExplosions) {
/* 207 */             event.setCancelled(true);
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/* 212 */           Fireball fireball = (Fireball)attacker;
/* 213 */           RegionQuery query = this.plugin.getRegionContainer().createQuery();
/* 214 */           if (wcfg.useRegions && !query.testState(defender.getLocation(), (Player)defender, new StateFlag[] { DefaultFlag.GHAST_FIREBALL }) && wcfg.explosionFlagCancellation) {
/* 215 */             event.setCancelled(true);
/*     */ 
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */         
/* 222 */         if (attacker instanceof LivingEntity && !(attacker instanceof Player)) {
/* 223 */           if (attacker instanceof org.bukkit.entity.Creeper && wcfg.blockCreeperExplosions) {
/* 224 */             event.setCancelled(true);
/*     */             
/*     */             return;
/*     */           } 
/* 228 */           if (wcfg.disableMobDamage) {
/* 229 */             event.setCancelled(true);
/*     */             
/*     */             return;
/*     */           } 
/* 233 */           if (wcfg.useRegions) {
/* 234 */             RegionManager mgr = this.plugin.getGlobalRegionManager().get(player.getWorld());
/* 235 */             ApplicableRegionSet set = this.plugin.getRegionContainer().createQuery().getApplicableRegions(defender.getLocation());
/*     */             
/* 237 */             if (!set.allows(DefaultFlag.MOB_DAMAGE, localPlayer) && !(attacker instanceof Tameable)) {
/* 238 */               event.setCancelled(true);
/*     */               
/*     */               return;
/*     */             } 
/* 242 */             if (attacker instanceof org.bukkit.entity.Creeper && 
/* 243 */               !set.allows(DefaultFlag.CREEPER_EXPLOSION, localPlayer) && wcfg.explosionFlagCancellation) {
/* 244 */               event.setCancelled(true);
/*     */               return;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void onEntityDamageByProjectile(EntityDamageByEntityEvent event) {
/*     */     LivingEntity livingEntity;
/* 255 */     Entity defender = event.getEntity();
/*     */     
/* 257 */     ProjectileSource source = ((Projectile)event.getDamager()).getShooter();
/* 258 */     if (source instanceof LivingEntity) {
/* 259 */       livingEntity = (LivingEntity)source;
/*     */     } else {
/*     */       return;
/*     */     } 
/*     */     
/* 264 */     if (defender instanceof Player) {
/* 265 */       Player player = (Player)defender;
/* 266 */       LocalPlayer localPlayer = this.plugin.wrapPlayer(player);
/*     */       
/* 268 */       ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 269 */       WorldConfiguration wcfg = cfg.get(player.getWorld());
/*     */ 
/*     */       
/* 272 */       if (livingEntity != null && !(livingEntity instanceof Player)) {
/* 273 */         if (wcfg.disableMobDamage) {
/* 274 */           event.setCancelled(true);
/*     */           return;
/*     */         } 
/* 277 */         if (wcfg.useRegions && 
/* 278 */           !this.plugin.getRegionContainer().createQuery().getApplicableRegions(defender.getLocation()).allows(DefaultFlag.MOB_DAMAGE, localPlayer)) {
/* 279 */           event.setCancelled(true);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 284 */     } else if (defender instanceof ItemFrame && 
/* 285 */       checkItemFrameProtection((Entity)livingEntity, (ItemFrame)defender)) {
/* 286 */       event.setCancelled(true);
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
/*     */   public void onEntityDamage(EntityDamageEvent event) {
/* 296 */     if (event instanceof EntityDamageByEntityEvent) {
/* 297 */       onEntityDamageByEntity((EntityDamageByEntityEvent)event); return;
/*     */     } 
/* 299 */     if (event instanceof EntityDamageByBlockEvent) {
/* 300 */       onEntityDamageByBlock((EntityDamageByBlockEvent)event);
/*     */       
/*     */       return;
/*     */     } 
/* 304 */     Entity defender = event.getEntity();
/* 305 */     EntityDamageEvent.DamageCause type = event.getCause();
/*     */     
/* 307 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 308 */     WorldConfiguration wcfg = cfg.get(defender.getWorld());
/*     */     
/* 310 */     if (defender instanceof Wolf && ((Wolf)defender).isTamed()) {
/* 311 */       if (wcfg.antiWolfDumbness) {
/* 312 */         event.setCancelled(true);
/*     */         return;
/*     */       } 
/* 315 */     } else if (defender instanceof Player) {
/* 316 */       Player player = (Player)defender;
/*     */       
/* 318 */       if (type == EntityDamageEvent.DamageCause.WITHER) {
/*     */         
/* 320 */         if (wcfg.disableMobDamage) {
/* 321 */           event.setCancelled(true);
/*     */           
/*     */           return;
/*     */         } 
/* 325 */         if (wcfg.useRegions) {
/* 326 */           ApplicableRegionSet set = this.plugin.getRegionContainer().createQuery().getApplicableRegions(defender.getLocation());
/*     */           
/* 328 */           if (!set.allows(DefaultFlag.MOB_DAMAGE, this.plugin.wrapPlayer(player))) {
/* 329 */             event.setCancelled(true);
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/* 335 */       if (type == EntityDamageEvent.DamageCause.DROWNING && cfg.hasAmphibiousMode(player)) {
/* 336 */         player.setRemainingAir(player.getMaximumAir());
/* 337 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/* 341 */       ItemStack helmet = player.getInventory().getHelmet();
/*     */       
/* 343 */       if (type == EntityDamageEvent.DamageCause.DROWNING && wcfg.pumpkinScuba && helmet != null && (helmet
/*     */         
/* 345 */         .getTypeId() == 86 || helmet
/* 346 */         .getTypeId() == 91)) {
/* 347 */         player.setRemainingAir(player.getMaximumAir());
/* 348 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/* 352 */       if (wcfg.disableFallDamage && type == EntityDamageEvent.DamageCause.FALL) {
/* 353 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/* 357 */       if (wcfg.disableFireDamage && (type == EntityDamageEvent.DamageCause.FIRE || type == EntityDamageEvent.DamageCause.FIRE_TICK)) {
/*     */         
/* 359 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/* 363 */       if (wcfg.disableDrowningDamage && type == EntityDamageEvent.DamageCause.DROWNING) {
/* 364 */         player.setRemainingAir(player.getMaximumAir());
/* 365 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/* 369 */       if (wcfg.teleportOnSuffocation && type == EntityDamageEvent.DamageCause.SUFFOCATION) {
/* 370 */         BukkitUtil.findFreePosition(player);
/* 371 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/* 375 */       if (wcfg.disableSuffocationDamage && type == EntityDamageEvent.DamageCause.SUFFOCATION) {
/* 376 */         event.setCancelled(true);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
/*     */   public void onEntityExplode(EntityExplodeEvent event) {
/* 387 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 388 */     Location l = event.getLocation();
/* 389 */     World world = l.getWorld();
/* 390 */     WorldConfiguration wcfg = cfg.get(world);
/* 391 */     Entity ent = event.getEntity();
/*     */     
/* 393 */     if (cfg.activityHaltToggle) {
/* 394 */       if (ent != null) {
/* 395 */         ent.remove();
/*     */       }
/* 397 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 401 */     if (ent instanceof org.bukkit.entity.Creeper) {
/* 402 */       if (wcfg.blockCreeperExplosions) {
/* 403 */         event.setCancelled(true);
/*     */         return;
/*     */       } 
/* 406 */       if (wcfg.blockCreeperBlockDamage) {
/* 407 */         event.blockList().clear();
/*     */         return;
/*     */       } 
/* 410 */     } else if (ent instanceof org.bukkit.entity.EnderDragon) {
/* 411 */       if (wcfg.blockEnderDragonBlockDamage) {
/* 412 */         event.blockList().clear();
/*     */         return;
/*     */       } 
/* 415 */     } else if (ent instanceof org.bukkit.entity.TNTPrimed || ent instanceof org.bukkit.entity.minecart.ExplosiveMinecart) {
/* 416 */       if (wcfg.blockTNTExplosions) {
/* 417 */         event.setCancelled(true);
/*     */         return;
/*     */       } 
/* 420 */       if (wcfg.blockTNTBlockDamage) {
/* 421 */         event.blockList().clear();
/*     */         return;
/*     */       } 
/* 424 */     } else if (ent instanceof Fireball) {
/* 425 */       if (ent instanceof org.bukkit.entity.WitherSkull) {
/* 426 */         if (wcfg.blockWitherSkullExplosions) {
/* 427 */           event.setCancelled(true);
/*     */           return;
/*     */         } 
/* 430 */         if (wcfg.blockWitherSkullBlockDamage) {
/* 431 */           event.blockList().clear();
/*     */           return;
/*     */         } 
/*     */       } else {
/* 435 */         if (wcfg.blockFireballExplosions) {
/* 436 */           event.setCancelled(true);
/*     */           return;
/*     */         } 
/* 439 */         if (wcfg.blockFireballBlockDamage) {
/* 440 */           event.blockList().clear();
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 445 */       if (wcfg.useRegions) {
/* 446 */         RegionManager mgr = this.plugin.getGlobalRegionManager().get(world);
/*     */         
/* 448 */         for (Block block : event.blockList()) {
/* 449 */           if (!this.plugin.getRegionContainer().createQuery().getApplicableRegions(block.getLocation()).allows(DefaultFlag.GHAST_FIREBALL)) {
/* 450 */             event.blockList().clear();
/* 451 */             if (wcfg.explosionFlagCancellation) event.setCancelled(true); 
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/* 456 */     } else if (ent instanceof org.bukkit.entity.Wither) {
/* 457 */       if (wcfg.blockWitherExplosions) {
/* 458 */         event.setCancelled(true);
/*     */         return;
/*     */       } 
/* 461 */       if (wcfg.blockWitherBlockDamage) {
/* 462 */         event.blockList().clear();
/*     */         
/*     */         return;
/*     */       } 
/*     */     } else {
/* 467 */       if (wcfg.blockOtherExplosions) {
/* 468 */         event.setCancelled(true);
/*     */         return;
/*     */       } 
/* 471 */       if (wcfg.useRegions) {
/* 472 */         RegionManager mgr = this.plugin.getGlobalRegionManager().get(world);
/* 473 */         for (Block block : event.blockList()) {
/* 474 */           if (!this.plugin.getRegionContainer().createQuery().getApplicableRegions(block.getLocation()).allows(DefaultFlag.OTHER_EXPLOSION)) {
/* 475 */             event.blockList().clear();
/* 476 */             if (wcfg.explosionFlagCancellation) event.setCancelled(true);
/*     */ 
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 484 */     if (wcfg.signChestProtection) {
/* 485 */       for (Block block : event.blockList()) {
/* 486 */         if (wcfg.isChestProtected(block)) {
/* 487 */           event.blockList().clear();
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
/*     */   public void onExplosionPrime(ExplosionPrimeEvent event) {
/* 500 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 501 */     WorldConfiguration wcfg = cfg.get(event.getEntity().getWorld());
/* 502 */     Entity ent = event.getEntity();
/*     */     
/* 504 */     if (cfg.activityHaltToggle) {
/* 505 */       ent.remove();
/* 506 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 510 */     if (event.getEntityType() == EntityType.WITHER) {
/* 511 */       if (wcfg.blockWitherExplosions) {
/* 512 */         event.setCancelled(true);
/*     */         return;
/*     */       } 
/* 515 */     } else if (event.getEntityType() == EntityType.WITHER_SKULL) {
/* 516 */       if (wcfg.blockWitherSkullExplosions) {
/* 517 */         event.setCancelled(true);
/*     */         return;
/*     */       } 
/* 520 */     } else if (event.getEntityType() == EntityType.FIREBALL) {
/* 521 */       if (wcfg.blockFireballExplosions) {
/* 522 */         event.setCancelled(true);
/*     */         return;
/*     */       } 
/* 525 */     } else if (event.getEntityType() == EntityType.CREEPER) {
/* 526 */       if (wcfg.blockCreeperExplosions) {
/* 527 */         event.setCancelled(true);
/*     */         return;
/*     */       } 
/* 530 */     } else if ((event.getEntityType() == EntityType.PRIMED_TNT || event
/* 531 */       .getEntityType() == EntityType.MINECART_TNT) && 
/* 532 */       wcfg.blockTNTExplosions) {
/* 533 */       event.setCancelled(true);
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
/*     */   public void onCreatureSpawn(CreatureSpawnEvent event) {
/* 541 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/*     */     
/* 543 */     if (cfg.activityHaltToggle) {
/* 544 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 548 */     WorldConfiguration wcfg = cfg.get(event.getEntity().getWorld());
/*     */ 
/*     */     
/* 551 */     if (!wcfg.blockPluginSpawning && event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM) {
/*     */       return;
/*     */     }
/*     */     
/* 555 */     if (wcfg.allowTamedSpawns && event
/* 556 */       .getEntity() instanceof Tameable && ((Tameable)event
/* 557 */       .getEntity()).isTamed()) {
/*     */       return;
/*     */     }
/*     */     
/* 561 */     EntityType entityType = event.getEntityType();
/*     */     
/* 563 */     if (wcfg.blockCreatureSpawn.contains(entityType)) {
/* 564 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 568 */     Location eventLoc = event.getLocation();
/*     */     
/* 570 */     if (wcfg.useRegions && cfg.useRegionsCreatureSpawnEvent) {
/* 571 */       ApplicableRegionSet set = this.plugin.getRegionContainer().createQuery().getApplicableRegions(eventLoc);
/*     */       
/* 573 */       if (!set.allows(DefaultFlag.MOB_SPAWNING)) {
/* 574 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/* 578 */       Set<EntityType> entityTypes = (Set<EntityType>)set.getFlag((Flag)DefaultFlag.DENY_SPAWN);
/* 579 */       if (entityTypes != null && entityTypes.contains(entityType)) {
/* 580 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 585 */     if (wcfg.blockGroundSlimes && entityType == EntityType.SLIME && eventLoc
/* 586 */       .getY() >= 60.0D && event
/* 587 */       .getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
/* 588 */       event.setCancelled(true);
/*     */       return;
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
/*     */   public void onCreatePortal(EntityCreatePortalEvent event) {
/* 595 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 596 */     WorldConfiguration wcfg = cfg.get(event.getEntity().getWorld());
/*     */     
/* 598 */     switch (event.getEntityType()) {
/*     */       case ENDER_DRAGON:
/* 600 */         if (wcfg.blockEnderDragonPortalCreation) event.setCancelled(true); 
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
/*     */   public void onPigZap(PigZapEvent event) {
/* 607 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 608 */     WorldConfiguration wcfg = cfg.get(event.getEntity().getWorld());
/*     */     
/* 610 */     if (wcfg.disablePigZap) {
/* 611 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
/*     */   public void onCreeperPower(CreeperPowerEvent event) {
/* 617 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 618 */     WorldConfiguration wcfg = cfg.get(event.getEntity().getWorld());
/*     */     
/* 620 */     if (wcfg.disableCreeperPower) {
/* 621 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
/*     */   public void onEntityRegainHealth(EntityRegainHealthEvent event) {
/* 628 */     Entity ent = event.getEntity();
/* 629 */     World world = ent.getWorld();
/*     */     
/* 631 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 632 */     WorldConfiguration wcfg = cfg.get(world);
/*     */     
/* 634 */     if (wcfg.disableHealthRegain) {
/* 635 */       event.setCancelled(true);
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
/*     */   public void onEntityChangeBlock(EntityChangeBlockEvent event) {
/* 647 */     Entity ent = event.getEntity();
/* 648 */     Block block = event.getBlock();
/* 649 */     Location location = block.getLocation();
/*     */     
/* 651 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 652 */     WorldConfiguration wcfg = cfg.get(ent.getWorld());
/* 653 */     if (ent instanceof org.bukkit.entity.Enderman) {
/* 654 */       if (wcfg.disableEndermanGriefing) {
/* 655 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/* 659 */       if (wcfg.useRegions && 
/* 660 */         !this.plugin.getGlobalRegionManager().allows(DefaultFlag.ENDER_BUILD, location)) {
/* 661 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/* 665 */     } else if (ent.getType() == EntityType.WITHER) {
/* 666 */       if (wcfg.blockWitherBlockDamage || wcfg.blockWitherExplosions) {
/* 667 */         event.setCancelled(true);
/*     */         return;
/*     */       } 
/* 670 */     } else if (event instanceof org.bukkit.event.entity.EntityBreakDoorEvent && 
/* 671 */       wcfg.blockZombieDoorDestruction) {
/* 672 */       event.setCancelled(true);
/*     */       return;
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
/*     */   private boolean checkItemFrameProtection(Entity attacker, ItemFrame defender) {
/* 686 */     World world = attacker.getWorld();
/* 687 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 688 */     WorldConfiguration wcfg = cfg.get(world);
/* 689 */     if (wcfg.useRegions) {
/*     */       
/* 691 */       RegionManager mgr = this.plugin.getGlobalRegionManager().get(world);
/* 692 */       if (!(attacker instanceof Player) && 
/* 693 */         !this.plugin.getGlobalRegionManager().allows(DefaultFlag.ENTITY_ITEM_FRAME_DESTROY, defender
/* 694 */           .getLocation())) {
/* 695 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 699 */     if (wcfg.blockEntityItemFrameDestroy && !(attacker instanceof Player)) {
/* 700 */       return true;
/*     */     }
/* 702 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\WorldGuardEntityListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */