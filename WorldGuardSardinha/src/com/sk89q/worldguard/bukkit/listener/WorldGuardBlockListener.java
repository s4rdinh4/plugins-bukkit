/*     */ package com.sk89q.worldguard.bukkit.listener;
/*     */ 
/*     */ import com.sk89q.worldedit.blocks.BlockType;
/*     */ import com.sk89q.worldedit.blocks.ItemType;
/*     */ import com.sk89q.worldguard.bukkit.ConfigurationManager;
/*     */ import com.sk89q.worldguard.bukkit.WorldConfiguration;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*     */ import com.sk89q.worldguard.protection.flags.DefaultFlag;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.BlockBreakEvent;
/*     */ import org.bukkit.event.block.BlockBurnEvent;
/*     */ import org.bukkit.event.block.BlockFadeEvent;
/*     */ import org.bukkit.event.block.BlockFormEvent;
/*     */ import org.bukkit.event.block.BlockFromToEvent;
/*     */ import org.bukkit.event.block.BlockIgniteEvent;
/*     */ import org.bukkit.event.block.BlockPhysicsEvent;
/*     */ import org.bukkit.event.block.BlockPlaceEvent;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ import org.bukkit.event.block.BlockSpreadEvent;
/*     */ import org.bukkit.event.block.EntityBlockFormEvent;
/*     */ import org.bukkit.event.block.LeavesDecayEvent;
/*     */ import org.bukkit.inventory.ItemStack;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGuardBlockListener
/*     */   implements Listener
/*     */ {
/*     */   private WorldGuardPlugin plugin;
/*     */   
/*     */   public WorldGuardBlockListener(WorldGuardPlugin plugin) {
/*  68 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerEvents() {
/*  75 */     this.plugin.getServer().getPluginManager().registerEvents(this, (Plugin)this.plugin);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WorldConfiguration getWorldConfig(World world) {
/*  85 */     return this.plugin.getGlobalStateManager().get(world);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WorldConfiguration getWorldConfig(Player player) {
/*  95 */     return getWorldConfig(player.getWorld());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
/*     */   public void onBlockBreak(BlockBreakEvent event) {
/* 103 */     Player player = event.getPlayer();
/* 104 */     Block target = event.getBlock();
/* 105 */     WorldConfiguration wcfg = getWorldConfig(player);
/*     */     
/* 107 */     if (!wcfg.itemDurability) {
/* 108 */       ItemStack held = player.getItemInHand();
/* 109 */       if (held.getType() != Material.AIR && !ItemType.usesDamageValue(held.getTypeId()) && !BlockType.usesData(held.getTypeId())) {
/* 110 */         held.setDurability((short)0);
/* 111 */         player.setItemInHand(held);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onBlockFromTo(BlockFromToEvent event) {
/* 121 */     World world = event.getBlock().getWorld();
/* 122 */     Block blockFrom = event.getBlock();
/* 123 */     Block blockTo = event.getToBlock();
/*     */     
/* 125 */     boolean isWater = (blockFrom.getTypeId() == 8 || blockFrom.getTypeId() == 9);
/* 126 */     boolean isLava = (blockFrom.getTypeId() == 10 || blockFrom.getTypeId() == 11);
/* 127 */     boolean isAir = (blockFrom.getTypeId() == 0);
/*     */     
/* 129 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 130 */     WorldConfiguration wcfg = cfg.get(event.getBlock().getWorld());
/*     */     
/* 132 */     if (cfg.activityHaltToggle) {
/* 133 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 137 */     if (wcfg.simulateSponge && isWater) {
/* 138 */       int ox = blockTo.getX();
/* 139 */       int oy = blockTo.getY();
/* 140 */       int oz = blockTo.getZ();
/*     */       
/* 142 */       for (int cx = -wcfg.spongeRadius; cx <= wcfg.spongeRadius; cx++) {
/* 143 */         for (int cy = -wcfg.spongeRadius; cy <= wcfg.spongeRadius; cy++) {
/* 144 */           for (int cz = -wcfg.spongeRadius; cz <= wcfg.spongeRadius; cz++) {
/* 145 */             Block sponge = world.getBlockAt(ox + cx, oy + cy, oz + cz);
/* 146 */             if (sponge.getTypeId() == 19 && (!wcfg.redstoneSponges || 
/* 147 */               !sponge.isBlockIndirectlyPowered())) {
/* 148 */               event.setCancelled(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               return;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 169 */     if (wcfg.preventWaterDamage.size() > 0) {
/* 170 */       int targetId = blockTo.getTypeId();
/*     */       
/* 172 */       if ((isAir || isWater) && wcfg.preventWaterDamage
/* 173 */         .contains(Integer.valueOf(targetId))) {
/* 174 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 179 */     if (wcfg.allowedLavaSpreadOver.size() > 0 && isLava) {
/* 180 */       int targetId = blockTo.getRelative(0, -1, 0).getTypeId();
/*     */       
/* 182 */       if (!wcfg.allowedLavaSpreadOver.contains(Integer.valueOf(targetId))) {
/* 183 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 188 */     if (wcfg.highFreqFlags && isWater && 
/* 189 */       !this.plugin.getGlobalRegionManager().allows(DefaultFlag.WATER_FLOW, blockFrom
/* 190 */         .getLocation())) {
/* 191 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 195 */     if (wcfg.highFreqFlags && isLava && 
/* 196 */       !this.plugin.getGlobalRegionManager().allows(DefaultFlag.LAVA_FLOW, blockFrom
/* 197 */         .getLocation())) {
/* 198 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 202 */     if (wcfg.disableObsidianGenerators && (isAir || isLava) && (blockTo
/* 203 */       .getTypeId() == 55 || blockTo
/* 204 */       .getTypeId() == 132)) {
/* 205 */       blockTo.setTypeId(0);
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
/*     */   public void onBlockIgnite(BlockIgniteEvent event) {
/* 215 */     BlockIgniteEvent.IgniteCause cause = event.getCause();
/* 216 */     Block block = event.getBlock();
/* 217 */     World world = block.getWorld();
/*     */     
/* 219 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 220 */     WorldConfiguration wcfg = cfg.get(world);
/*     */     
/* 222 */     if (cfg.activityHaltToggle) {
/* 223 */       event.setCancelled(true);
/*     */       return;
/*     */     } 
/* 226 */     boolean isFireSpread = (cause == BlockIgniteEvent.IgniteCause.SPREAD);
/*     */     
/* 228 */     if (wcfg.preventLightningFire && cause == BlockIgniteEvent.IgniteCause.LIGHTNING) {
/* 229 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 233 */     if (wcfg.preventLavaFire && cause == BlockIgniteEvent.IgniteCause.LAVA) {
/* 234 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 238 */     if (wcfg.disableFireSpread && isFireSpread) {
/* 239 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 243 */     if (wcfg.blockLighter && (cause == BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL || cause == BlockIgniteEvent.IgniteCause.FIREBALL) && event
/* 244 */       .getPlayer() != null && 
/* 245 */       !this.plugin.hasPermission((CommandSender)event.getPlayer(), "worldguard.override.lighter")) {
/* 246 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 250 */     if (wcfg.fireSpreadDisableToggle && isFireSpread) {
/* 251 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 255 */     if (wcfg.disableFireSpreadBlocks.size() > 0 && isFireSpread) {
/* 256 */       int x = block.getX();
/* 257 */       int y = block.getY();
/* 258 */       int z = block.getZ();
/*     */       
/* 260 */       if (wcfg.disableFireSpreadBlocks.contains(Integer.valueOf(world.getBlockTypeIdAt(x, y - 1, z))) || wcfg.disableFireSpreadBlocks
/* 261 */         .contains(Integer.valueOf(world.getBlockTypeIdAt(x + 1, y, z))) || wcfg.disableFireSpreadBlocks
/* 262 */         .contains(Integer.valueOf(world.getBlockTypeIdAt(x - 1, y, z))) || wcfg.disableFireSpreadBlocks
/* 263 */         .contains(Integer.valueOf(world.getBlockTypeIdAt(x, y, z - 1))) || wcfg.disableFireSpreadBlocks
/* 264 */         .contains(Integer.valueOf(world.getBlockTypeIdAt(x, y, z + 1)))) {
/* 265 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 270 */     if (wcfg.useRegions) {
/* 271 */       ApplicableRegionSet set = this.plugin.getRegionContainer().createQuery().getApplicableRegions(block.getLocation());
/*     */       
/* 273 */       if (wcfg.highFreqFlags && isFireSpread && 
/* 274 */         !set.allows(DefaultFlag.FIRE_SPREAD)) {
/* 275 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/* 279 */       if (wcfg.highFreqFlags && cause == BlockIgniteEvent.IgniteCause.LAVA && 
/* 280 */         !set.allows(DefaultFlag.LAVA_FIRE)) {
/* 281 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/* 285 */       if (cause == BlockIgniteEvent.IgniteCause.FIREBALL && event.getPlayer() == null)
/*     */       {
/* 287 */         if (!set.allows(DefaultFlag.GHAST_FIREBALL)) {
/* 288 */           event.setCancelled(true);
/*     */           
/*     */           return;
/*     */         } 
/*     */       }
/* 293 */       if (cause == BlockIgniteEvent.IgniteCause.LIGHTNING && !set.allows(DefaultFlag.LIGHTNING)) {
/* 294 */         event.setCancelled(true);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
/*     */   public void onBlockBurn(BlockBurnEvent event) {
/* 305 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 306 */     WorldConfiguration wcfg = cfg.get(event.getBlock().getWorld());
/*     */     
/* 308 */     if (cfg.activityHaltToggle) {
/* 309 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 313 */     if (wcfg.disableFireSpread) {
/* 314 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 318 */     if (wcfg.fireSpreadDisableToggle) {
/* 319 */       Block block = event.getBlock();
/* 320 */       event.setCancelled(true);
/* 321 */       checkAndDestroyAround(block.getWorld(), block.getX(), block.getY(), block.getZ(), 51);
/*     */       
/*     */       return;
/*     */     } 
/* 325 */     if (wcfg.disableFireSpreadBlocks.size() > 0) {
/* 326 */       Block block = event.getBlock();
/*     */       
/* 328 */       if (wcfg.disableFireSpreadBlocks.contains(Integer.valueOf(block.getTypeId()))) {
/* 329 */         event.setCancelled(true);
/* 330 */         checkAndDestroyAround(block.getWorld(), block.getX(), block.getY(), block.getZ(), 51);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 335 */     if (wcfg.isChestProtected(event.getBlock())) {
/* 336 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 340 */     if (wcfg.useRegions) {
/* 341 */       Block block = event.getBlock();
/* 342 */       int x = block.getX();
/* 343 */       int y = block.getY();
/* 344 */       int z = block.getZ();
/* 345 */       ApplicableRegionSet set = this.plugin.getRegionContainer().createQuery().getApplicableRegions(block.getLocation());
/*     */       
/* 347 */       if (!set.allows(DefaultFlag.FIRE_SPREAD)) {
/* 348 */         checkAndDestroyAround(block.getWorld(), x, y, z, 51);
/* 349 */         event.setCancelled(true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkAndDestroyAround(World world, int x, int y, int z, int required) {
/* 356 */     checkAndDestroy(world, x, y, z + 1, required);
/* 357 */     checkAndDestroy(world, x, y, z - 1, required);
/* 358 */     checkAndDestroy(world, x, y + 1, z, required);
/* 359 */     checkAndDestroy(world, x, y - 1, z, required);
/* 360 */     checkAndDestroy(world, x + 1, y, z, required);
/* 361 */     checkAndDestroy(world, x - 1, y, z, required);
/*     */   }
/*     */   
/*     */   private void checkAndDestroy(World world, int x, int y, int z, int required) {
/* 365 */     if (world.getBlockTypeIdAt(x, y, z) == required) {
/* 366 */       world.getBlockAt(x, y, z).setTypeId(0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onBlockPhysics(BlockPhysicsEvent event) {
/* 375 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 376 */     WorldConfiguration wcfg = cfg.get(event.getBlock().getWorld());
/*     */     
/* 378 */     if (cfg.activityHaltToggle) {
/* 379 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 383 */     int id = event.getChangedTypeId();
/*     */     
/* 385 */     if (id == 13 && wcfg.noPhysicsGravel) {
/* 386 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 390 */     if (id == 12 && wcfg.noPhysicsSand) {
/* 391 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 395 */     if (id == 90 && wcfg.allowPortalAnywhere) {
/* 396 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 400 */     if (wcfg.ropeLadders && event.getBlock().getType() == Material.LADDER && 
/* 401 */       event.getBlock().getRelative(0, 1, 0).getType() == Material.LADDER) {
/* 402 */       event.setCancelled(true);
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
/*     */   public void onBlockPlace(BlockPlaceEvent event) {
/* 413 */     Block target = event.getBlock();
/* 414 */     World world = target.getWorld();
/*     */     
/* 416 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 417 */     WorldConfiguration wcfg = cfg.get(world);
/*     */     
/* 419 */     if (wcfg.simulateSponge && target.getType() == Material.SPONGE) {
/* 420 */       if (wcfg.redstoneSponges && target.isBlockIndirectlyPowered()) {
/*     */         return;
/*     */       }
/*     */       
/* 424 */       int ox = target.getX();
/* 425 */       int oy = target.getY();
/* 426 */       int oz = target.getZ();
/*     */       
/* 428 */       SpongeUtil.clearSpongeWater(this.plugin, world, ox, oy, oz);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH)
/*     */   public void onBlockRedstoneChange(BlockRedstoneEvent event) {
/* 437 */     Block blockTo = event.getBlock();
/* 438 */     World world = blockTo.getWorld();
/*     */     
/* 440 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 441 */     WorldConfiguration wcfg = cfg.get(world);
/*     */     
/* 443 */     if (wcfg.simulateSponge && wcfg.redstoneSponges) {
/* 444 */       int ox = blockTo.getX();
/* 445 */       int oy = blockTo.getY();
/* 446 */       int oz = blockTo.getZ();
/*     */       
/* 448 */       for (int cx = -1; cx <= 1; cx++) {
/* 449 */         for (int cy = -1; cy <= 1; cy++) {
/* 450 */           for (int cz = -1; cz <= 1; cz++) {
/* 451 */             Block sponge = world.getBlockAt(ox + cx, oy + cy, oz + cz);
/* 452 */             if (sponge.getTypeId() == 19 && sponge
/* 453 */               .isBlockIndirectlyPowered()) {
/* 454 */               SpongeUtil.clearSpongeWater(this.plugin, world, ox + cx, oy + cy, oz + cz);
/* 455 */             } else if (sponge.getTypeId() == 19 && 
/* 456 */               !sponge.isBlockIndirectlyPowered()) {
/* 457 */               SpongeUtil.addSpongeWater(this.plugin, world, ox + cx, oy + cy, oz + cz);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
/*     */   public void onLeavesDecay(LeavesDecayEvent event) {
/* 469 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 470 */     WorldConfiguration wcfg = cfg.get(event.getBlock().getWorld());
/*     */     
/* 472 */     if (cfg.activityHaltToggle) {
/* 473 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 477 */     if (wcfg.disableLeafDecay) {
/* 478 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 482 */     if (wcfg.useRegions && 
/* 483 */       !this.plugin.getGlobalRegionManager().allows(DefaultFlag.LEAF_DECAY, event
/* 484 */         .getBlock().getLocation())) {
/* 485 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
/*     */   public void onBlockForm(BlockFormEvent event) {
/* 495 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 496 */     WorldConfiguration wcfg = cfg.get(event.getBlock().getWorld());
/*     */     
/* 498 */     if (cfg.activityHaltToggle) {
/* 499 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 503 */     int type = event.getNewState().getTypeId();
/*     */     
/* 505 */     if (event instanceof EntityBlockFormEvent) {
/* 506 */       if (((EntityBlockFormEvent)event).getEntity() instanceof org.bukkit.entity.Snowman && 
/* 507 */         wcfg.disableSnowmanTrails) {
/* 508 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 515 */     if (type == 79) {
/* 516 */       if (wcfg.disableIceFormation) {
/* 517 */         event.setCancelled(true);
/*     */         return;
/*     */       } 
/* 520 */       if (wcfg.useRegions && !this.plugin.getGlobalRegionManager().allows(DefaultFlag.ICE_FORM, event
/* 521 */           .getBlock().getLocation())) {
/* 522 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 527 */     if (type == 78) {
/* 528 */       if (wcfg.disableSnowFormation) {
/* 529 */         event.setCancelled(true);
/*     */         return;
/*     */       } 
/* 532 */       if (wcfg.allowedSnowFallOver.size() > 0) {
/* 533 */         int targetId = event.getBlock().getRelative(0, -1, 0).getTypeId();
/*     */         
/* 535 */         if (!wcfg.allowedSnowFallOver.contains(Integer.valueOf(targetId))) {
/* 536 */           event.setCancelled(true);
/*     */           return;
/*     */         } 
/*     */       } 
/* 540 */       if (wcfg.useRegions && !this.plugin.getGlobalRegionManager().allows(DefaultFlag.SNOW_FALL, event
/* 541 */           .getBlock().getLocation())) {
/* 542 */         event.setCancelled(true);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
/*     */   public void onBlockSpread(BlockSpreadEvent event) {
/* 553 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 554 */     WorldConfiguration wcfg = cfg.get(event.getBlock().getWorld());
/*     */     
/* 556 */     if (cfg.activityHaltToggle) {
/* 557 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 561 */     int fromType = event.getSource().getTypeId();
/*     */     
/* 563 */     if (fromType == 40 || fromType == 39) {
/* 564 */       if (wcfg.disableMushroomSpread) {
/* 565 */         event.setCancelled(true);
/*     */         return;
/*     */       } 
/* 568 */       if (wcfg.useRegions && !this.plugin.getGlobalRegionManager().allows(DefaultFlag.MUSHROOMS, event
/* 569 */           .getBlock().getLocation())) {
/* 570 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 575 */     if (fromType == 2) {
/* 576 */       if (wcfg.disableGrassGrowth) {
/* 577 */         event.setCancelled(true);
/*     */         return;
/*     */       } 
/* 580 */       if (wcfg.useRegions && !this.plugin.getGlobalRegionManager().allows(DefaultFlag.GRASS_SPREAD, event
/* 581 */           .getBlock().getLocation())) {
/* 582 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 587 */     if (fromType == 110) {
/* 588 */       if (wcfg.disableMyceliumSpread) {
/* 589 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/* 593 */       if (wcfg.useRegions && 
/* 594 */         !this.plugin.getGlobalRegionManager().allows(DefaultFlag.MYCELIUM_SPREAD, event
/* 595 */           .getBlock().getLocation())) {
/* 596 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 601 */     if (fromType == 106) {
/* 602 */       if (wcfg.disableVineGrowth) {
/* 603 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/* 607 */       if (wcfg.useRegions && 
/* 608 */         !this.plugin.getGlobalRegionManager().allows(DefaultFlag.VINE_GROWTH, event
/* 609 */           .getBlock().getLocation())) {
/* 610 */         event.setCancelled(true);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
/*     */   public void onBlockFade(BlockFadeEvent event) {
/* 622 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 623 */     WorldConfiguration wcfg = cfg.get(event.getBlock().getWorld());
/*     */     
/* 625 */     switch (event.getBlock().getTypeId()) {
/*     */       case 79:
/* 627 */         if (wcfg.disableIceMelting) {
/* 628 */           event.setCancelled(true);
/*     */           
/*     */           return;
/*     */         } 
/* 632 */         if (wcfg.useRegions && !this.plugin.getGlobalRegionManager().allows(DefaultFlag.ICE_MELT, event
/* 633 */             .getBlock().getLocation())) {
/* 634 */           event.setCancelled(true);
/*     */           return;
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 78:
/* 640 */         if (wcfg.disableSnowMelting) {
/* 641 */           event.setCancelled(true);
/*     */           
/*     */           return;
/*     */         } 
/* 645 */         if (wcfg.useRegions && !this.plugin.getGlobalRegionManager().allows(DefaultFlag.SNOW_MELT, event
/* 646 */             .getBlock().getLocation())) {
/* 647 */           event.setCancelled(true);
/*     */           return;
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 60:
/* 653 */         if (wcfg.disableSoilDehydration) {
/* 654 */           event.setCancelled(true);
/*     */           return;
/*     */         } 
/* 657 */         if (wcfg.useRegions && !this.plugin.getGlobalRegionManager().allows(DefaultFlag.SOIL_DRY, event
/* 658 */             .getBlock().getLocation())) {
/* 659 */           event.setCancelled(true);
/*     */           return;
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\WorldGuardBlockListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */