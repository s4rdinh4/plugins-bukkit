/*     */ package com.sk89q.worldguard.bukkit.listener;
/*     */ import com.sk89q.worldguard.bukkit.WorldConfiguration;
/*     */ import com.sk89q.worldguard.bukkit.cause.Cause;
/*     */ import com.sk89q.worldguard.bukkit.event.DelegateEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.block.BreakBlockEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.block.PlaceBlockEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.block.UseBlockEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.entity.DamageEntityEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.entity.DestroyEntityEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.entity.SpawnEntityEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.entity.UseEntityEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.inventory.UseItemEvent;
/*     */ import com.sk89q.worldguard.bukkit.listener.debounce.EventDebounce;
/*     */ import com.sk89q.worldguard.bukkit.listener.debounce.legacy.BlockEntityEventDebounce;
/*     */ import com.sk89q.worldguard.bukkit.util.Events;
/*     */ import com.sk89q.worldguard.bukkit.util.Materials;
/*     */ import java.util.List;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.FallingBlock;
/*     */ import org.bukkit.entity.Item;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.ThrownPotion;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.block.BlockBreakEvent;
/*     */ import org.bukkit.event.block.BlockBurnEvent;
/*     */ import org.bukkit.event.block.BlockDamageEvent;
/*     */ import org.bukkit.event.block.BlockDispenseEvent;
/*     */ import org.bukkit.event.block.BlockExpEvent;
/*     */ import org.bukkit.event.block.BlockFromToEvent;
/*     */ import org.bukkit.event.block.BlockIgniteEvent;
/*     */ import org.bukkit.event.block.BlockPistonExtendEvent;
/*     */ import org.bukkit.event.block.BlockPistonRetractEvent;
/*     */ import org.bukkit.event.block.BlockPlaceEvent;
/*     */ import org.bukkit.event.block.SignChangeEvent;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ import org.bukkit.event.entity.EntityChangeBlockEvent;
/*     */ import org.bukkit.event.entity.EntityCombustEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.EntityDeathEvent;
/*     */ import org.bukkit.event.entity.EntityExplodeEvent;
/*     */ import org.bukkit.event.entity.EntityInteractEvent;
/*     */ import org.bukkit.event.entity.EntityTameEvent;
/*     */ import org.bukkit.event.entity.EntityUnleashEvent;
/*     */ import org.bukkit.event.entity.ExpBottleEvent;
/*     */ import org.bukkit.event.entity.PotionSplashEvent;
/*     */ import org.bukkit.event.hanging.HangingBreakEvent;
/*     */ import org.bukkit.event.hanging.HangingPlaceEvent;
/*     */ import org.bukkit.event.inventory.InventoryMoveItemEvent;
/*     */ import org.bukkit.event.inventory.InventoryOpenEvent;
/*     */ import org.bukkit.event.player.PlayerBedEnterEvent;
/*     */ import org.bukkit.event.player.PlayerBucketEmptyEvent;
/*     */ import org.bukkit.event.player.PlayerBucketFillEvent;
/*     */ import org.bukkit.event.player.PlayerDropItemEvent;
/*     */ import org.bukkit.event.player.PlayerFishEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEntityEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerItemConsumeEvent;
/*     */ import org.bukkit.event.player.PlayerPickupItemEvent;
/*     */ import org.bukkit.event.player.PlayerShearEntityEvent;
/*     */ import org.bukkit.event.player.PlayerUnleashEntityEvent;
/*     */ import org.bukkit.event.vehicle.VehicleDamageEvent;
/*     */ import org.bukkit.event.vehicle.VehicleDestroyEvent;
/*     */ import org.bukkit.event.world.StructureGrowEvent;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.material.Dispenser;
/*     */ import org.bukkit.material.MaterialData;
/*     */ 
/*     */ public class EventAbstractionListener extends AbstractListener {
/*  81 */   private final BlockEntityEventDebounce interactDebounce = new BlockEntityEventDebounce(10000);
/*  82 */   private final EntityEntityEventDebounce pickupDebounce = new EntityEntityEventDebounce(10000);
/*  83 */   private final BlockEntityEventDebounce entityBreakBlockDebounce = new BlockEntityEventDebounce(10000);
/*  84 */   private final InventoryMoveItemEventDebounce moveItemDebounce = new InventoryMoveItemEventDebounce(30000);
/*  85 */   private final EventDebounce<BlockPistonRetractKey> pistonRetractDebounce = EventDebounce.create(5000);
/*  86 */   private final EventDebounce<BlockPistonExtendKey> pistonExtendDebounce = EventDebounce.create(5000);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EventAbstractionListener(WorldGuardPlugin plugin) {
/*  94 */     super(plugin);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerEvents() {
/*  99 */     super.registerEvents();
/*     */     
/*     */     try {
/* 102 */       getPlugin().getServer().getPluginManager().registerEvents(new SpigotCompatListener(), (Plugin)getPlugin());
/* 103 */     } catch (LinkageError ignored) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onBlockBreak(BlockBreakEvent event) {
/* 113 */     Events.fireToCancel((Cancellable)event, (Event)new BreakBlockEvent((Event)event, Cause.create(new Object[] { event.getPlayer() }, ), event.getBlock()));
/*     */     
/* 115 */     if (event.isCancelled()) {
/* 116 */       playDenyEffect(event.getPlayer(), event.getBlock().getLocation().add(0.5D, 1.0D, 0.5D));
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onBlockPlace(BlockPlaceEvent event) {
/* 122 */     BlockState previousState = event.getBlockReplacedState();
/*     */ 
/*     */     
/* 125 */     if (previousState.getType() != Material.AIR) {
/* 126 */       Events.fireToCancel((Cancellable)event, (Event)new BreakBlockEvent((Event)event, Cause.create(new Object[] { event.getPlayer() }, ), previousState.getLocation(), previousState.getType()));
/*     */     }
/*     */     
/* 129 */     if (!event.isCancelled()) {
/* 130 */       ItemStack itemStack = new ItemStack(event.getBlockPlaced().getType(), 1);
/* 131 */       Events.fireToCancel((Cancellable)event, (Event)new UseItemEvent((Event)event, Cause.create(new Object[] { event.getPlayer() }, ), event.getPlayer().getWorld(), itemStack));
/*     */     } 
/*     */     
/* 134 */     if (!event.isCancelled()) {
/* 135 */       Events.fireToCancel((Cancellable)event, (Event)new PlaceBlockEvent((Event)event, Cause.create(new Object[] { event.getPlayer() }, ), event.getBlock()));
/*     */     }
/*     */     
/* 138 */     if (event.isCancelled()) {
/* 139 */       playDenyEffect(event.getPlayer(), event.getBlockPlaced().getLocation().add(0.5D, 0.5D, 0.5D));
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onBlockBurn(BlockBurnEvent event) {
/* 145 */     Block target = event.getBlock();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 153 */     Block[] adjacent = { target.getRelative(BlockFace.NORTH), target.getRelative(BlockFace.SOUTH), target.getRelative(BlockFace.WEST), target.getRelative(BlockFace.EAST), target.getRelative(BlockFace.UP), target.getRelative(BlockFace.DOWN) };
/*     */     
/* 155 */     int found = 0;
/* 156 */     boolean allowed = false;
/*     */     
/* 158 */     for (Block source : adjacent) {
/* 159 */       if (source.getType() == Material.FIRE) {
/* 160 */         found++;
/* 161 */         if (Events.fireAndTestCancel((Event)new BreakBlockEvent((Event)event, Cause.create(new Object[] { source }, ), target))) {
/* 162 */           source.setType(Material.AIR);
/*     */         } else {
/* 164 */           allowed = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 169 */     if (found > 0 && !allowed) {
/* 170 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onStructureGrowEvent(StructureGrowEvent event) {
/* 176 */     int originalCount = event.getBlocks().size();
/* 177 */     List<Block> blockList = Lists.transform(event.getBlocks(), new BlockStateAsBlockFunction());
/*     */     
/* 179 */     Player player = event.getPlayer();
/* 180 */     if (player != null) {
/* 181 */       Events.fireBulkEventToCancel((Cancellable)event, (Event)new PlaceBlockEvent((Event)event, Cause.create(new Object[] { player }, ), event.getLocation().getWorld(), blockList, Material.AIR));
/*     */     } else {
/* 183 */       Events.fireBulkEventToCancel((Cancellable)event, (Event)new PlaceBlockEvent((Event)event, Cause.create(new Object[] { event.getLocation().getBlock() }, ), event.getLocation().getWorld(), blockList, Material.AIR));
/*     */     } 
/*     */     
/* 186 */     if (!event.isCancelled() && event.getBlocks().size() != originalCount) {
/* 187 */       event.getLocation().getBlock().setType(Material.AIR);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onEntityChangeBlock(EntityChangeBlockEvent event) {
/* 195 */     Block block = event.getBlock();
/* 196 */     Entity entity = event.getEntity();
/* 197 */     Material to = event.getTo();
/*     */ 
/*     */     
/* 200 */     if (Materials.isRedstoneOre(block.getType()) && Materials.isRedstoneOre(to)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 205 */     if (event.getTo() != Material.AIR && event.getBlock().getType() != Material.AIR) {
/* 206 */       Events.fireToCancel((Cancellable)event, (Event)new BreakBlockEvent((Event)event, Cause.create(new Object[] { entity }, ), block));
/* 207 */       Events.fireToCancel((Cancellable)event, (Event)new PlaceBlockEvent((Event)event, Cause.create(new Object[] { entity }, ), block.getLocation(), to));
/*     */     }
/* 209 */     else if (event.getTo() == Material.AIR) {
/*     */       
/* 211 */       if (entity instanceof FallingBlock) {
/* 212 */         Cause.trackParentCause((Metadatable)entity, block);
/*     */ 
/*     */         
/* 215 */         Events.fireToCancel((Cancellable)event, (Event)new SpawnEntityEvent((Event)event, Cause.create(new Object[] { block }, ), entity));
/*     */       } else {
/* 217 */         this.entityBreakBlockDebounce.debounce(event
/* 218 */             .getBlock(), event.getEntity(), (Cancellable)event, (Event)new BreakBlockEvent((Event)event, Cause.create(new Object[] { entity }, ), event.getBlock()));
/*     */       } 
/*     */     } else {
/* 221 */       boolean wasCancelled = event.isCancelled();
/* 222 */       Cause cause = Cause.create(new Object[] { entity });
/*     */       
/* 224 */       Events.fireToCancel((Cancellable)event, (Event)new PlaceBlockEvent((Event)event, cause, event.getBlock().getLocation(), to));
/*     */ 
/*     */       
/* 227 */       FallingBlock fallingBlock = (FallingBlock)entity;
/* 228 */       ItemStack itemStack = new ItemStack(fallingBlock.getMaterial(), 1, (short)fallingBlock.getBlockData());
/* 229 */       Item item = block.getWorld().dropItem(fallingBlock.getLocation(), itemStack);
/* 230 */       item.setVelocity(new Vector());
/* 231 */       if (event.isCancelled() && !wasCancelled && entity instanceof FallingBlock && Events.fireAndTestCancel((Event)new SpawnEntityEvent((Event)event, Cause.create(new Object[] { block, entity }, ), (Entity)item))) {
/* 232 */         item.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onEntityExplode(EntityExplodeEvent event) {
/* 241 */     Entity entity = event.getEntity();
/*     */     
/* 243 */     Events.fireBulkEventToCancel((Cancellable)event, (Event)new BreakBlockEvent((Event)event, Cause.create(new Object[] { entity }, ), event.getLocation().getWorld(), event.blockList(), Material.AIR));
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onBlockPistonRetract(BlockPistonRetractEvent event) {
/* 248 */     if (event.isSticky()) {
/* 249 */       EventDebounce.Entry entry = this.pistonRetractDebounce.getIfNotPresent(new BlockPistonRetractKey(event), (Cancellable)event);
/* 250 */       if (entry != null) {
/* 251 */         Cause cause = Cause.create(new Object[] { event.getBlock() });
/* 252 */         Events.fireToCancel((Cancellable)event, (Event)new BreakBlockEvent((Event)event, cause, event.getRetractLocation(), Material.AIR));
/* 253 */         Events.fireToCancel((Cancellable)event, (Event)new PlaceBlockEvent((Event)event, cause, event.getBlock().getRelative(event.getDirection())));
/* 254 */         entry.setCancelled(event.isCancelled());
/*     */         
/* 256 */         if (event.isCancelled()) {
/* 257 */           playDenyEffect(event.getBlock().getLocation().add(0.5D, 1.0D, 0.5D));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onBlockPistonExtend(BlockPistonExtendEvent event) {
/* 265 */     EventDebounce.Entry entry = this.pistonExtendDebounce.getIfNotPresent(new BlockPistonExtendKey(event), (Cancellable)event);
/* 266 */     if (entry != null) {
/*     */       
/* 268 */       List<Block> blocks = new ArrayList<Block>(event.getBlocks());
/* 269 */       Block lastBlock = event.getBlock().getRelative(event.getDirection(), event.getLength() + 1);
/* 270 */       blocks.add(lastBlock);
/* 271 */       int originalLength = blocks.size();
/* 272 */       Events.fireBulkEventToCancel((Cancellable)event, (Event)new PlaceBlockEvent((Event)event, Cause.create(new Object[] { event.getBlock() }, ), event.getBlock().getWorld(), blocks, Material.STONE));
/* 273 */       if (blocks.size() != originalLength) {
/* 274 */         event.setCancelled(true);
/*     */       }
/* 276 */       entry.setCancelled(event.isCancelled());
/*     */       
/* 278 */       if (event.isCancelled()) {
/* 279 */         playDenyEffect(event.getBlock().getLocation().add(0.5D, 1.0D, 0.5D));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onBlockDamage(BlockDamageEvent event) {
/* 290 */     Block target = event.getBlock();
/*     */ 
/*     */ 
/*     */     
/* 294 */     if (target.getType() == Material.CAKE_BLOCK)
/* 295 */       Events.fireToCancel((Cancellable)event, (Event)new UseBlockEvent((Event)event, Cause.create(new Object[] { event.getPlayer() }, ), target)); 
/*     */   }
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onPlayerInteract(PlayerInteractEvent event) {
/*     */     Block placed;
/*     */     boolean modifiesWorld;
/* 301 */     Player player = event.getPlayer();
/* 302 */     ItemStack item = player.getItemInHand();
/* 303 */     Block clicked = event.getClickedBlock();
/*     */     
/* 305 */     boolean silent = false;
/*     */     
/* 307 */     Cause cause = Cause.create(new Object[] { player });
/*     */     
/* 309 */     switch (event.getAction()) {
/*     */       
/*     */       case DISPENSE_EGG:
/* 312 */         if (Materials.isRedstoneOre(clicked.getType()) || clicked.getType() == Material.SOIL) {
/* 313 */           silent = true;
/*     */         }
/*     */         
/* 316 */         this.interactDebounce.debounce(clicked, (Entity)event.getPlayer(), (Cancellable)event, (Event)(new UseBlockEvent((Event)event, cause, clicked))
/* 317 */             .setSilent(silent).setAllowed(hasInteractBypass(clicked)));
/*     */         break;
/*     */       
/*     */       case EGG:
/* 321 */         placed = clicked.getRelative(event.getBlockFace());
/*     */ 
/*     */         
/* 324 */         handleBlockRightClick(event, Cause.create(new Object[] { event.getPlayer() }, ), item, clicked, event.getBlockFace(), placed);
/*     */       
/*     */       case SPAWNER_EGG:
/* 327 */         placed = clicked.getRelative(event.getBlockFace());
/*     */ 
/*     */         
/* 330 */         modifiesWorld = (isBlockModifiedOnClick(clicked, (event.getAction() == Action.RIGHT_CLICK_BLOCK)) || (item != null && isItemAppliedToBlock(item, clicked)));
/*     */         
/* 332 */         if (Events.fireAndTestCancel((Event)(new UseBlockEvent((Event)event, cause, clicked)).setAllowed(!modifiesWorld))) {
/* 333 */           event.setUseInteractedBlock(Event.Result.DENY);
/*     */         }
/*     */ 
/*     */         
/* 337 */         for (Block connected : Blocks.getConnected(clicked)) {
/* 338 */           if (Events.fireAndTestCancel((Event)(new UseBlockEvent((Event)event, Cause.create(new Object[] { event.getPlayer() }, ), connected)).setAllowed(!modifiesWorld))) {
/* 339 */             event.setUseInteractedBlock(Event.Result.DENY);
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */         
/* 345 */         if (event.getAction() == Action.LEFT_CLICK_BLOCK && placed.getType() == Material.FIRE && 
/* 346 */           Events.fireAndTestCancel((Event)new BreakBlockEvent((Event)event, Cause.create(new Object[] { event.getPlayer() }, ), placed))) {
/* 347 */           event.setUseInteractedBlock(Event.Result.DENY);
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 352 */         if (event.isCancelled()) {
/* 353 */           playDenyEffect(event.getPlayer(), clicked.getLocation().add(0.5D, 1.0D, 0.5D));
/*     */         }
/*     */       
/*     */       case NATURAL:
/*     */       case JOCKEY:
/* 358 */         if (item != null && !item.getType().isBlock() && Events.fireAndTestCancel((Event)new UseItemEvent((Event)event, cause, player.getWorld(), item))) {
/* 359 */           event.setUseItemInHand(Event.Result.DENY);
/* 360 */           event.setCancelled(true);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 367 */         if (item != null && (getWorldConfig(player.getWorld())).blockUseAtFeet.test(item) && 
/* 368 */           Events.fireAndTestCancel((Event)new UseBlockEvent((Event)event, cause, player.getLocation().getBlock()))) {
/* 369 */           event.setCancelled(true);
/*     */         }
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onEntityInteract(EntityInteractEvent event) {
/* 379 */     this.interactDebounce.debounce(event.getBlock(), event.getEntity(), (Cancellable)event, (Event)(new UseBlockEvent((Event)event, 
/* 380 */           Cause.create(new Object[] { event.getEntity() }, ), event.getBlock())).setAllowed(hasInteractBypass(event.getBlock())));
/*     */   }
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onBlockIgnite(BlockIgniteEvent event) {
/*     */     Cause cause;
/* 385 */     Block block = event.getBlock();
/* 386 */     Material type = block.getType();
/*     */ 
/*     */ 
/*     */     
/* 390 */     if (event.getPlayer() != null) {
/* 391 */       cause = Cause.create(new Object[] { event.getPlayer() });
/* 392 */     } else if (event.getIgnitingEntity() != null) {
/* 393 */       cause = Cause.create(new Object[] { event.getIgnitingEntity() });
/* 394 */     } else if (event.getIgnitingBlock() != null) {
/* 395 */       cause = Cause.create(new Object[] { event.getIgnitingBlock() });
/*     */     } else {
/* 397 */       cause = Cause.unknown();
/*     */     } 
/*     */     
/* 400 */     Events.fireToCancel((Cancellable)event, (Event)new PlaceBlockEvent((Event)event, cause, block.getLocation(), Material.FIRE));
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onSignChange(SignChangeEvent event) {
/* 405 */     Events.fireToCancel((Cancellable)event, (Event)new UseBlockEvent((Event)event, Cause.create(new Object[] { event.getPlayer() }, ), event.getBlock()));
/*     */     
/* 407 */     if (event.isCancelled()) {
/* 408 */       playDenyEffect(event.getPlayer(), event.getBlock().getLocation().add(0.5D, 0.5D, 0.5D));
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onBedEnter(PlayerBedEnterEvent event) {
/* 414 */     Events.fireToCancel((Cancellable)event, (Event)new UseBlockEvent((Event)event, Cause.create(new Object[] { event.getPlayer() }, ), event.getBed()));
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
/* 419 */     Player player = event.getPlayer();
/* 420 */     Block blockAffected = event.getBlockClicked().getRelative(event.getBlockFace());
/* 421 */     boolean allowed = false;
/*     */ 
/*     */     
/* 424 */     if (event.getBucket() == Material.MILK_BUCKET) {
/* 425 */       allowed = true;
/*     */     }
/*     */     
/* 428 */     ItemStack item = new ItemStack(event.getBucket(), 1);
/* 429 */     Material blockMaterial = Materials.getBucketBlockMaterial(event.getBucket());
/* 430 */     Events.fireToCancel((Cancellable)event, (Event)(new PlaceBlockEvent((Event)event, Cause.create(new Object[] { player }, ), blockAffected.getLocation(), blockMaterial)).setAllowed(allowed));
/* 431 */     Events.fireToCancel((Cancellable)event, (Event)(new UseItemEvent((Event)event, Cause.create(new Object[] { player }, ), player.getWorld(), item)).setAllowed(allowed));
/*     */     
/* 433 */     playDenyEffect(event.getPlayer(), blockAffected.getLocation().add(0.5D, 0.5D, 0.5D));
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onPlayerBucketFill(PlayerBucketFillEvent event) {
/* 438 */     Player player = event.getPlayer();
/* 439 */     Block blockAffected = event.getBlockClicked().getRelative(event.getBlockFace());
/* 440 */     boolean allowed = false;
/*     */ 
/*     */     
/* 443 */     if (event.getBucket() == Material.MILK_BUCKET) {
/* 444 */       allowed = true;
/*     */     }
/*     */     
/* 447 */     ItemStack item = new ItemStack(event.getBucket(), 1);
/* 448 */     Events.fireToCancel((Cancellable)event, (Event)(new BreakBlockEvent((Event)event, Cause.create(new Object[] { player }, ), blockAffected)).setAllowed(allowed));
/* 449 */     Events.fireToCancel((Cancellable)event, (Event)(new UseItemEvent((Event)event, Cause.create(new Object[] { player }, ), player.getWorld(), item)).setAllowed(allowed));
/*     */     
/* 451 */     playDenyEffect(event.getPlayer(), blockAffected.getLocation().add(0.5D, 1.0D, 0.5D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onBlockFromTo(BlockFromToEvent event) {
/* 462 */     WorldConfiguration config = getWorldConfig(event.getBlock().getWorld());
/*     */ 
/*     */ 
/*     */     
/* 466 */     if (!config.useRegions || (!config.highFreqFlags && !config.checkLiquidFlow)) {
/*     */       return;
/*     */     }
/*     */     
/* 470 */     Block from = event.getBlock();
/* 471 */     Block to = event.getToBlock();
/* 472 */     Material fromType = from.getType();
/* 473 */     Material toType = to.getType();
/*     */ 
/*     */     
/* 476 */     if (toType.isSolid() && Materials.isLiquid(fromType)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 484 */     if ((Materials.isWater(fromType) && Materials.isWater(toType)) || (Materials.isLava(fromType) && Materials.isLava(toType))) {
/*     */       return;
/*     */     }
/*     */     
/* 488 */     Cause cause = Cause.create(new Object[] { from });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 495 */     Events.fireToCancel((Cancellable)event, (Event)new PlaceBlockEvent((Event)event, cause, to.getLocation(), from.getType()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onCreatureSpawn(CreatureSpawnEvent event) {
/* 504 */     switch (event.getSpawnReason()) {
/*     */       case DISPENSE_EGG:
/*     */       case EGG:
/*     */       case SPAWNER_EGG:
/* 508 */         if ((getWorldConfig(event.getEntity().getWorld())).strictEntitySpawn) {
/* 509 */           Events.fireToCancel((Cancellable)event, (Event)new SpawnEntityEvent((Event)event, Cause.unknown(), (Entity)event.getEntity()));
/*     */         }
/*     */         break;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onHangingPlace(HangingPlaceEvent event) {
/* 538 */     Events.fireToCancel((Cancellable)event, (Event)new SpawnEntityEvent((Event)event, Cause.create(new Object[] { event.getPlayer() }, ), (Entity)event.getEntity()));
/*     */     
/* 540 */     if (event.isCancelled()) {
/* 541 */       Block effectBlock = event.getBlock().getRelative(event.getBlockFace());
/* 542 */       playDenyEffect(event.getPlayer(), effectBlock.getLocation().add(0.5D, 0.5D, 0.5D));
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onHangingBreak(HangingBreakEvent event) {
/* 548 */     if (event instanceof HangingBreakByEntityEvent) {
/* 549 */       Entity remover = ((HangingBreakByEntityEvent)event).getRemover();
/* 550 */       Events.fireToCancel((Cancellable)event, (Event)new DestroyEntityEvent((Event)event, Cause.create(new Object[] { remover }, ), (Entity)event.getEntity()));
/*     */       
/* 552 */       if (event.isCancelled() && remover instanceof Player) {
/* 553 */         playDenyEffect((Player)remover, event.getEntity().getLocation());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onVehicleDestroy(VehicleDestroyEvent event) {
/* 560 */     Events.fireToCancel((Cancellable)event, (Event)new DestroyEntityEvent((Event)event, Cause.create(new Object[] { event.getAttacker() }, ), (Entity)event.getVehicle()));
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onBlockExp(BlockExpEvent event) {
/* 565 */     if (event.getExpToDrop() > 0 && 
/* 566 */       Events.fireAndTestCancel((Event)new SpawnEntityEvent((Event)event, Cause.create(new Object[] { event.getBlock() }, ), event.getBlock().getLocation(), EntityType.EXPERIENCE_ORB))) {
/* 567 */       event.setExpToDrop(0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onPlayerFish(PlayerFishEvent event) {
/* 574 */     if (Events.fireAndTestCancel((Event)new SpawnEntityEvent((Event)event, Cause.create(new Object[] { event.getPlayer(), event.getHook() }, ), event.getHook().getLocation(), EntityType.EXPERIENCE_ORB))) {
/* 575 */       event.setExpToDrop(0);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onExpBottle(ExpBottleEvent event) {
/* 581 */     if (Events.fireAndTestCancel((Event)new SpawnEntityEvent((Event)event, Cause.create(new Object[] { event.getEntity() }, ), event.getEntity().getLocation(), EntityType.EXPERIENCE_ORB))) {
/* 582 */       event.setExperience(0);
/*     */ 
/*     */       
/* 585 */       ProjectileSource shooter = event.getEntity().getShooter();
/* 586 */       if (shooter instanceof Player) {
/* 587 */         Player player = (Player)shooter;
/* 588 */         if (player.getGameMode() != GameMode.CREATIVE) {
/* 589 */           player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.EXP_BOTTLE, 1) });
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onEntityDeath(EntityDeathEvent event) {
/* 597 */     if (event.getDroppedExp() > 0 && 
/* 598 */       Events.fireAndTestCancel((Event)new SpawnEntityEvent((Event)event, Cause.create(new Object[] { event.getEntity() }, ), event.getEntity().getLocation(), EntityType.EXPERIENCE_ORB))) {
/* 599 */       event.setDroppedExp(0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
/* 610 */     Player player = event.getPlayer();
/* 611 */     World world = player.getWorld();
/* 612 */     ItemStack item = player.getItemInHand();
/* 613 */     Entity entity = event.getRightClicked();
/*     */     
/* 615 */     Events.fireToCancel((Cancellable)event, (Event)new UseItemEvent((Event)event, Cause.create(new Object[] { player }, ), world, item));
/* 616 */     Events.fireToCancel((Cancellable)event, (Event)new UseEntityEvent((Event)event, Cause.create(new Object[] { player }, ), entity));
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onEntityDamage(EntityDamageEvent event) {
/* 621 */     if (event instanceof EntityDamageByBlockEvent) {
/* 622 */       Events.fireToCancel((Cancellable)event, (Event)new DamageEntityEvent((Event)event, Cause.create(new Object[] { ((EntityDamageByBlockEvent)event).getDamager() }, ), event.getEntity()));
/*     */     }
/* 624 */     else if (event instanceof EntityDamageByEntityEvent) {
/* 625 */       EntityDamageByEntityEvent entityEvent = (EntityDamageByEntityEvent)event;
/* 626 */       Entity damager = entityEvent.getDamager();
/* 627 */       Events.fireToCancel((Cancellable)event, (Event)new DamageEntityEvent((Event)event, Cause.create(new Object[] { damager }, ), event.getEntity()));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 632 */       if (damager instanceof Player) {
/* 633 */         ItemStack item = ((Player)damager).getItemInHand();
/*     */         
/* 635 */         if (item != null) {
/* 636 */           Events.fireToCancel((Cancellable)event, (Event)new UseItemEvent((Event)event, Cause.create(new Object[] { damager }, ), event.getEntity().getWorld(), item));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onEntityCombust(EntityCombustEvent event) {
/* 644 */     if (event instanceof EntityCombustByBlockEvent) {
/* 645 */       Events.fireToCancel((Cancellable)event, (Event)new DamageEntityEvent((Event)event, Cause.create(new Object[] { ((EntityCombustByBlockEvent)event).getCombuster() }, ), event.getEntity()));
/*     */     }
/* 647 */     else if (event instanceof EntityCombustByEntityEvent) {
/* 648 */       Events.fireToCancel((Cancellable)event, (Event)new DamageEntityEvent((Event)event, Cause.create(new Object[] { ((EntityCombustByEntityEvent)event).getCombuster() }, ), event.getEntity()));
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onEntityUnleash(EntityUnleashEvent event) {
/* 654 */     if (event instanceof PlayerUnleashEntityEvent) {
/* 655 */       PlayerUnleashEntityEvent playerEvent = (PlayerUnleashEntityEvent)event;
/* 656 */       Events.fireToCancel((Cancellable)playerEvent, (Event)new UseEntityEvent((Event)playerEvent, Cause.create(new Object[] { playerEvent.getPlayer() }, ), event.getEntity()));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onEntityTame(EntityTameEvent event) {
/* 664 */     Events.fireToCancel((Cancellable)event, (Event)new UseEntityEvent((Event)event, Cause.create(new Object[] { event.getOwner() }, ), (Entity)event.getEntity()));
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onPlayerShearEntity(PlayerShearEntityEvent event) {
/* 669 */     Events.fireToCancel((Cancellable)event, (Event)new UseEntityEvent((Event)event, Cause.create(new Object[] { event.getPlayer() }, ), event.getEntity()));
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onPlayerPickupItem(PlayerPickupItemEvent event) {
/* 674 */     Item item = event.getItem();
/* 675 */     this.pickupDebounce.debounce((Entity)event.getPlayer(), (Entity)item, (Cancellable)event, (Event)new DestroyEntityEvent((Event)event, Cause.create(new Object[] { event.getPlayer() }, ), (Entity)event.getItem()));
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onPlayerDropItem(PlayerDropItemEvent event) {
/* 680 */     Events.fireToCancel((Cancellable)event, (Event)new SpawnEntityEvent((Event)event, Cause.create(new Object[] { event.getPlayer() }, ), (Entity)event.getItemDrop()));
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onVehicleDamage(VehicleDamageEvent event) {
/* 685 */     Entity attacker = event.getAttacker();
/* 686 */     Events.fireToCancel((Cancellable)event, (Event)new DestroyEntityEvent((Event)event, Cause.create(new Object[] { attacker }, ), (Entity)event.getVehicle()));
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onVehicleEnter(VehicleDamageEvent event) {
/* 691 */     Events.fireToCancel((Cancellable)event, (Event)new UseEntityEvent((Event)event, Cause.create(new Object[] { event.getAttacker() }, ), (Entity)event.getVehicle()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
/* 700 */     Events.fireToCancel((Cancellable)event, (Event)new UseItemEvent((Event)event, Cause.create(new Object[] { event.getPlayer() }, ), event.getPlayer().getWorld(), event.getItem()));
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onInventoryOpen(InventoryOpenEvent event) {
/* 705 */     InventoryHolder holder = event.getInventory().getHolder();
/* 706 */     if (holder instanceof BlockState) {
/* 707 */       Events.fireToCancel((Cancellable)event, (Event)new UseBlockEvent((Event)event, Cause.create(new Object[] { event.getPlayer() }, ), ((BlockState)holder).getBlock()));
/* 708 */     } else if (holder instanceof Entity && 
/* 709 */       !(holder instanceof Player)) {
/* 710 */       Events.fireToCancel((Cancellable)event, (Event)new UseEntityEvent((Event)event, Cause.create(new Object[] { event.getPlayer() }, ), (Entity)holder));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onInventoryMoveItem(InventoryMoveItemEvent event) {
/* 717 */     final InventoryHolder causeHolder = event.getInitiator().getHolder();
/* 718 */     InventoryHolder sourceHolder = event.getSource().getHolder();
/* 719 */     InventoryHolder targetHolder = event.getDestination().getHolder();
/*     */     
/*     */     AbstractEventDebounce.Entry entry;
/*     */     
/* 723 */     if ((entry = this.moveItemDebounce.tryDebounce(event)) != null) {
/*     */       Cause cause;
/*     */       
/* 726 */       if (causeHolder instanceof Entity) {
/* 727 */         cause = Cause.create(new Object[] { causeHolder });
/* 728 */       } else if (causeHolder instanceof BlockState) {
/* 729 */         cause = Cause.create(new Object[] { ((BlockState)causeHolder).getBlock() });
/*     */       } else {
/* 731 */         cause = Cause.unknown();
/*     */       } 
/*     */       
/* 734 */       if (!causeHolder.equals(sourceHolder)) {
/* 735 */         handleInventoryHolderUse(event, cause, sourceHolder);
/*     */       }
/*     */       
/* 738 */       handleInventoryHolderUse(event, cause, targetHolder);
/*     */       
/* 740 */       if (event.isCancelled() && causeHolder instanceof Hopper) {
/* 741 */         Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)getPlugin(), new Runnable()
/*     */             {
/*     */               public void run() {
/* 744 */                 ((Hopper)causeHolder).getBlock().breakNaturally();
/*     */               }
/*     */             });
/*     */       } else {
/* 748 */         entry.setCancelled(event.isCancelled());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onPotionSplash(PotionSplashEvent event) {
/* 755 */     ThrownPotion thrownPotion1 = event.getEntity();
/* 756 */     ThrownPotion potion = event.getPotion();
/* 757 */     World world = thrownPotion1.getWorld();
/* 758 */     Cause cause = Cause.create(new Object[] { potion });
/*     */ 
/*     */     
/* 761 */     Events.fireToCancel((Cancellable)event, (Event)new UseItemEvent((Event)event, cause, world, potion.getItem()));
/*     */ 
/*     */     
/* 764 */     if (!event.isCancelled()) {
/* 765 */       int blocked = 0;
/* 766 */       boolean hasDamageEffect = Materials.hasDamageEffect(potion.getEffects());
/*     */       
/* 768 */       for (LivingEntity affected : event.getAffectedEntities()) {
/* 769 */         DelegateEvent delegate = hasDamageEffect ? (DelegateEvent)new DamageEntityEvent((Event)event, cause, (Entity)affected) : (DelegateEvent)new UseEntityEvent((Event)event, cause, (Entity)affected);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 774 */         delegate.getRelevantFlags().add(DefaultFlag.POTION_SPLASH);
/*     */         
/* 776 */         if (Events.fireAndTestCancel((Event)delegate)) {
/* 777 */           event.setIntensity(affected, 0.0D);
/* 778 */           blocked++;
/*     */         } 
/*     */       } 
/*     */       
/* 782 */       if (blocked == event.getAffectedEntities().size()) {
/* 783 */         event.setCancelled(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onBlockDispense(BlockDispenseEvent event) {
/* 790 */     Cause cause = Cause.create(new Object[] { event.getBlock() });
/* 791 */     Block dispenserBlock = event.getBlock();
/* 792 */     ItemStack item = event.getItem();
/* 793 */     MaterialData materialData = dispenserBlock.getState().getData();
/*     */     
/* 795 */     Events.fireToCancel((Cancellable)event, (Event)new UseItemEvent((Event)event, cause, dispenserBlock.getWorld(), item));
/*     */ 
/*     */     
/* 798 */     if (materialData instanceof Dispenser) {
/* 799 */       Dispenser dispenser = (Dispenser)materialData;
/* 800 */       Block placed = dispenserBlock.getRelative(dispenser.getFacing());
/* 801 */       Block clicked = placed.getRelative(dispenser.getFacing());
/* 802 */       handleBlockRightClick(event, cause, item, clicked, dispenser.getFacing().getOppositeFace(), placed);
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
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T extends Event & Cancellable> void handleBlockRightClick(T event, Cause cause, @Nullable ItemStack item, Block clicked, BlockFace faceClicked, Block placed) {
/* 818 */     if (item != null && item.getType() == Material.TNT)
/*     */     {
/*     */ 
/*     */       
/* 822 */       Events.fireToCancel((Cancellable)event, (Event)new UseBlockEvent((Event)event, cause, clicked.getLocation(), Material.TNT));
/*     */     }
/*     */ 
/*     */     
/* 826 */     if (item != null && Materials.isMinecart(item.getType()))
/*     */     {
/* 828 */       Events.fireToCancel((Cancellable)event, (Event)new SpawnEntityEvent((Event)event, cause, placed.getLocation().add(0.5D, 0.0D, 0.5D), EntityType.MINECART));
/*     */     }
/*     */ 
/*     */     
/* 832 */     if (item != null && item.getType() == Material.BOAT) {
/* 833 */       Events.fireToCancel((Cancellable)event, (Event)new SpawnEntityEvent((Event)event, cause, placed.getLocation().add(0.5D, 0.0D, 0.5D), EntityType.BOAT));
/*     */     }
/*     */ 
/*     */     
/* 837 */     if (item != null && item.getType() == Material.MONSTER_EGG) {
/* 838 */       MaterialData data = item.getData();
/* 839 */       if (data instanceof SpawnEgg) {
/* 840 */         EntityType type = ((SpawnEgg)data).getSpawnedType();
/* 841 */         if (type == null) {
/* 842 */           type = EntityType.SHEEP;
/*     */         }
/* 844 */         Events.fireToCancel((Cancellable)event, (Event)new SpawnEntityEvent((Event)event, cause, placed.getLocation().add(0.5D, 0.0D, 0.5D), type));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 849 */     if (item != null && item.getType() == Material.INK_SACK && Materials.isDyeColor(item.getData(), DyeColor.BROWN))
/*     */     {
/* 851 */       if (faceClicked != BlockFace.DOWN && faceClicked != BlockFace.UP) {
/* 852 */         Events.fireToCancel((Cancellable)event, (Event)new PlaceBlockEvent((Event)event, cause, placed.getLocation(), Material.COCOA));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 857 */     if (item != null && item.getType() == Material.TNT) {
/* 858 */       Events.fireToCancel((Cancellable)event, (Event)new PlaceBlockEvent((Event)event, cause, placed.getLocation(), Material.TNT));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T extends Event & Cancellable> void handleInventoryHolderUse(T originalEvent, Cause cause, InventoryHolder holder) {
/* 864 */     if (((Cancellable)originalEvent).isCancelled()) {
/*     */       return;
/*     */     }
/*     */     
/* 868 */     if (holder instanceof Entity) {
/* 869 */       Events.fireToCancel((Cancellable)originalEvent, (Event)new UseEntityEvent((Event)originalEvent, cause, (Entity)holder));
/* 870 */     } else if (holder instanceof BlockState) {
/* 871 */       Events.fireToCancel((Cancellable)originalEvent, (Event)new UseBlockEvent((Event)originalEvent, cause, ((BlockState)holder).getBlock()));
/* 872 */     } else if (holder instanceof DoubleChest) {
/* 873 */       Events.fireToCancel((Cancellable)originalEvent, (Event)new UseBlockEvent((Event)originalEvent, cause, ((BlockState)((DoubleChest)holder).getLeftSide()).getBlock()));
/* 874 */       Events.fireToCancel((Cancellable)originalEvent, (Event)new UseBlockEvent((Event)originalEvent, cause, ((BlockState)((DoubleChest)holder).getRightSide()).getBlock()));
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean hasInteractBypass(Block block) {
/* 879 */     return (getWorldConfig(block.getWorld())).allowAllInteract.test(block);
/*     */   }
/*     */   
/*     */   private boolean hasInteractBypass(World world, ItemStack item) {
/* 883 */     return (getWorldConfig(world)).allowAllInteract.test(item);
/*     */   }
/*     */   
/*     */   private boolean isBlockModifiedOnClick(Block block, boolean rightClick) {
/* 887 */     return (Materials.isBlockModifiedOnClick(block.getType(), rightClick) && !hasInteractBypass(block));
/*     */   }
/*     */   
/*     */   private boolean isItemAppliedToBlock(ItemStack item, Block clicked) {
/* 891 */     return (Materials.isItemAppliedToBlock(item.getType(), clicked.getType()) && 
/* 892 */       !hasInteractBypass(clicked) && 
/* 893 */       !hasInteractBypass(clicked.getWorld(), item));
/*     */   }
/*     */ 
/*     */   
/*     */   private void playDenyEffect(Player player, Location location) {
/* 898 */     player.playEffect(location, Effect.SMOKE, BlockFace.UP);
/*     */   }
/*     */   
/*     */   private void playDenyEffect(Location location) {
/* 902 */     location.getWorld().playEffect(location, Effect.SMOKE, BlockFace.UP);
/*     */   }
/*     */   
/*     */   public class SpigotCompatListener implements Listener {
/*     */     @EventHandler(ignoreCancelled = true)
/*     */     public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
/* 908 */       EventAbstractionListener.this.onPlayerInteractEntity((PlayerInteractEntityEvent)event);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\EventAbstractionListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */