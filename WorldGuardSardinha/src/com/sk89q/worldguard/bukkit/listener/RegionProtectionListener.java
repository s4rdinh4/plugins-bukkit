/*     */ package com.sk89q.worldguard.bukkit.listener;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.sk89q.worldguard.bukkit.RegionQuery;
/*     */ import com.sk89q.worldguard.bukkit.WorldConfiguration;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.bukkit.cause.Cause;
/*     */ import com.sk89q.worldguard.bukkit.event.DelegateEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.block.BreakBlockEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.block.PlaceBlockEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.block.UseBlockEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.entity.DamageEntityEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.entity.DestroyEntityEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.entity.SpawnEntityEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.entity.UseEntityEvent;
/*     */ import com.sk89q.worldguard.bukkit.internal.WGMetadata;
/*     */ import com.sk89q.worldguard.bukkit.permission.RegionPermissionModel;
/*     */ import com.sk89q.worldguard.bukkit.protection.DelayedRegionOverlapAssociation;
/*     */ import com.sk89q.worldguard.bukkit.util.Entities;
/*     */ import com.sk89q.worldguard.bukkit.util.Events;
/*     */ import com.sk89q.worldguard.bukkit.util.InteropUtils;
/*     */ import com.sk89q.worldguard.bukkit.util.Materials;
/*     */ import com.sk89q.worldguard.domains.Association;
/*     */ import com.sk89q.worldguard.protection.association.Associables;
/*     */ import com.sk89q.worldguard.protection.association.RegionAssociable;
/*     */ import com.sk89q.worldguard.protection.events.DisallowedPVPEvent;
/*     */ import com.sk89q.worldguard.protection.flags.DefaultFlag;
/*     */ import com.sk89q.worldguard.protection.flags.Flag;
/*     */ import com.sk89q.worldguard.protection.flags.StateFlag;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Vehicle;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.vehicle.VehicleExitEvent;
/*     */ import org.bukkit.metadata.Metadatable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RegionProtectionListener
/*     */   extends AbstractListener
/*     */ {
/*     */   private static final String DENY_MESSAGE_KEY = "worldguard.region.lastMessage";
/*     */   private static final String DISEMBARK_MESSAGE_KEY = "worldguard.region.disembarkMessage";
/*     */   private static final int LAST_MESSAGE_DELAY = 500;
/*     */   
/*     */   public RegionProtectionListener(WorldGuardPlugin plugin) {
/*  74 */     super(plugin);
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
/*     */   private void tellErrorMessage(DelegateEvent event, Cause cause, Location location, String what) {
/*  86 */     if (event.isSilent() || cause.isIndirect()) {
/*     */       return;
/*     */     }
/*     */     
/*  90 */     Object rootCause = cause.getRootCause();
/*     */     
/*  92 */     if (rootCause instanceof Player) {
/*  93 */       Player player = (Player)rootCause;
/*     */       
/*  95 */       long now = System.currentTimeMillis();
/*  96 */       Long lastTime = (Long)WGMetadata.getIfPresent((Metadatable)player, "worldguard.region.lastMessage", Long.class);
/*  97 */       if (lastTime == null || now - lastTime.longValue() >= 500L) {
/*  98 */         RegionQuery query = getPlugin().getRegionContainer().createQuery();
/*  99 */         String message = (String)query.queryValue(location, player, (Flag)DefaultFlag.DENY_MESSAGE);
/* 100 */         if (message != null && !message.isEmpty()) {
/* 101 */           player.sendMessage(message.replace("%what%", what));
/*     */         }
/* 103 */         WGMetadata.put((Metadatable)player, "worldguard.region.lastMessage", Long.valueOf(now));
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
/*     */   private boolean isWhitelisted(Cause cause, World world, boolean pvp) {
/* 117 */     Object rootCause = cause.getRootCause();
/*     */     
/* 119 */     if (rootCause instanceof Block) {
/* 120 */       Material type = ((Block)rootCause).getType();
/* 121 */       return (type == Material.HOPPER || type == Material.DROPPER);
/* 122 */     }  if (rootCause instanceof Player) {
/* 123 */       Player player = (Player)rootCause;
/* 124 */       WorldConfiguration config = getWorldConfig(world);
/*     */       
/* 126 */       if (config.fakePlayerBuildOverride && InteropUtils.isFakePlayer(player)) {
/* 127 */         return true;
/*     */       }
/*     */       
/* 130 */       return (!pvp && (new RegionPermissionModel(getPlugin(), (CommandSender)player)).mayIgnoreRegionProtection(world));
/*     */     } 
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private RegionAssociable createRegionAssociable(Cause cause) {
/* 137 */     Object rootCause = cause.getRootCause();
/*     */     
/* 139 */     if (!cause.isKnown())
/* 140 */       return Associables.constant(Association.NON_MEMBER); 
/* 141 */     if (rootCause instanceof Player)
/* 142 */       return (RegionAssociable)getPlugin().wrapPlayer((Player)rootCause); 
/* 143 */     if (rootCause instanceof OfflinePlayer)
/* 144 */       return (RegionAssociable)getPlugin().wrapOfflinePlayer((OfflinePlayer)rootCause); 
/* 145 */     if (rootCause instanceof Entity) {
/* 146 */       RegionQuery query = getPlugin().getRegionContainer().createQuery();
/* 147 */       return (RegionAssociable)new DelayedRegionOverlapAssociation(query, ((Entity)rootCause).getLocation());
/* 148 */     }  if (rootCause instanceof Block) {
/* 149 */       RegionQuery query = getPlugin().getRegionContainer().createQuery();
/* 150 */       return (RegionAssociable)new DelayedRegionOverlapAssociation(query, ((Block)rootCause).getLocation());
/*     */     } 
/* 152 */     return Associables.constant(Association.NON_MEMBER);
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onPlaceBlock(final PlaceBlockEvent event) {
/* 158 */     if (event.getResult() == Event.Result.ALLOW)
/* 159 */       return;  if (!isRegionSupportEnabled(event.getWorld()))
/* 160 */       return;  if (isWhitelisted(event.getCause(), event.getWorld(), false))
/*     */       return; 
/* 162 */     final Material type = event.getEffectiveMaterial();
/* 163 */     final RegionQuery query = getPlugin().getRegionContainer().createQuery();
/* 164 */     final RegionAssociable associable = createRegionAssociable(event.getCause());
/*     */ 
/*     */     
/* 167 */     if (event.getCause().getRootCause() instanceof Block && 
/* 168 */       Materials.isLiquid(type) && 
/* 169 */       !(getWorldConfig(event.getWorld())).checkLiquidFlow) {
/*     */       return;
/*     */     }
/*     */     
/* 173 */     event.filter(new Predicate<Location>()
/*     */         {
/*     */           public boolean apply(Location target)
/*     */           {
/*     */             boolean canPlace;
/*     */             
/*     */             String what;
/* 180 */             if (type == Material.FIRE) {
/* 181 */               canPlace = query.testBuild(target, associable, RegionProtectionListener.combine((DelegateEvent)event, new StateFlag[] { DefaultFlag.BLOCK_PLACE, DefaultFlag.LIGHTER }));
/* 182 */               what = "place fire";
/*     */             }
/*     */             else {
/*     */               
/* 186 */               canPlace = query.testBuild(target, associable, RegionProtectionListener.combine((DelegateEvent)event, new StateFlag[] { DefaultFlag.BLOCK_PLACE }));
/* 187 */               what = "place that block";
/*     */             } 
/*     */             
/* 190 */             if (!canPlace) {
/* 191 */               RegionProtectionListener.this.tellErrorMessage((DelegateEvent)event, event.getCause(), target, what);
/* 192 */               return false;
/*     */             } 
/*     */             
/* 195 */             return true;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onBreakBlock(final BreakBlockEvent event) {
/* 202 */     if (event.getResult() == Event.Result.ALLOW)
/* 203 */       return;  if (!isRegionSupportEnabled(event.getWorld()))
/* 204 */       return;  if (isWhitelisted(event.getCause(), event.getWorld(), false))
/*     */       return; 
/* 206 */     final RegionQuery query = getPlugin().getRegionContainer().createQuery();
/*     */     
/* 208 */     if (!event.isCancelled()) {
/* 209 */       final RegionAssociable associable = createRegionAssociable(event.getCause());
/*     */       
/* 211 */       event.filter(new Predicate<Location>()
/*     */           {
/*     */             public boolean apply(Location target)
/*     */             {
/*     */               boolean canBreak;
/*     */               
/*     */               String what;
/* 218 */               if (event.getCause().find(new EntityType[] { EntityType.PRIMED_TNT, EntityType.PRIMED_TNT }) != null) {
/* 219 */                 canBreak = query.testBuild(target, associable, RegionProtectionListener.combine((DelegateEvent)event, new StateFlag[] { DefaultFlag.BLOCK_BREAK, DefaultFlag.TNT }));
/* 220 */                 what = "dynamite blocks";
/*     */               }
/*     */               else {
/*     */                 
/* 224 */                 canBreak = query.testBuild(target, associable, RegionProtectionListener.combine((DelegateEvent)event, new StateFlag[] { DefaultFlag.BLOCK_BREAK }));
/* 225 */                 what = "break that block";
/*     */               } 
/*     */               
/* 228 */               if (!canBreak) {
/* 229 */                 RegionProtectionListener.this.tellErrorMessage((DelegateEvent)event, event.getCause(), target, what);
/* 230 */                 return false;
/*     */               } 
/*     */               
/* 233 */               return true;
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onUseBlock(final UseBlockEvent event) {
/* 241 */     if (event.getResult() == Event.Result.ALLOW)
/* 242 */       return;  if (!isRegionSupportEnabled(event.getWorld()))
/* 243 */       return;  if (isWhitelisted(event.getCause(), event.getWorld(), false))
/*     */       return; 
/* 245 */     final Material type = event.getEffectiveMaterial();
/* 246 */     final RegionQuery query = getPlugin().getRegionContainer().createQuery();
/* 247 */     final RegionAssociable associable = createRegionAssociable(event.getCause());
/*     */     
/* 249 */     event.filter(new Predicate<Location>()
/*     */         {
/*     */           public boolean apply(Location target)
/*     */           {
/*     */             boolean canUse;
/*     */             
/*     */             String what;
/* 256 */             if (Materials.isConsideredBuildingIfUsed(type)) {
/* 257 */               canUse = query.testBuild(target, associable, RegionProtectionListener.combine((DelegateEvent)event, new StateFlag[0]));
/* 258 */               what = "use that";
/*     */             
/*     */             }
/* 261 */             else if (Materials.isInventoryBlock(type)) {
/* 262 */               canUse = query.testBuild(target, associable, RegionProtectionListener.combine((DelegateEvent)event, new StateFlag[] { DefaultFlag.INTERACT, DefaultFlag.CHEST_ACCESS }));
/* 263 */               what = "open that";
/*     */             
/*     */             }
/* 266 */             else if (type == Material.BED_BLOCK) {
/* 267 */               canUse = query.testBuild(target, associable, RegionProtectionListener.combine((DelegateEvent)event, new StateFlag[] { DefaultFlag.INTERACT, DefaultFlag.SLEEP }));
/* 268 */               what = "sleep";
/*     */             
/*     */             }
/* 271 */             else if (type == Material.TNT) {
/* 272 */               canUse = query.testBuild(target, associable, RegionProtectionListener.combine((DelegateEvent)event, new StateFlag[] { DefaultFlag.INTERACT, DefaultFlag.TNT }));
/* 273 */               what = "use explosives";
/*     */             
/*     */             }
/* 276 */             else if (Materials.isUseFlagApplicable(type)) {
/* 277 */               canUse = query.testBuild(target, associable, RegionProtectionListener.combine((DelegateEvent)event, new StateFlag[] { DefaultFlag.INTERACT, DefaultFlag.USE }));
/* 278 */               what = "use that";
/*     */             }
/*     */             else {
/*     */               
/* 282 */               canUse = query.testBuild(target, associable, RegionProtectionListener.combine((DelegateEvent)event, new StateFlag[] { DefaultFlag.INTERACT }));
/* 283 */               what = "use that";
/*     */             } 
/*     */             
/* 286 */             if (!canUse) {
/* 287 */               RegionProtectionListener.this.tellErrorMessage((DelegateEvent)event, event.getCause(), target, what);
/* 288 */               return false;
/*     */             } 
/*     */             
/* 291 */             return true;
/*     */           }
/*     */         });
/*     */   } @EventHandler(ignoreCancelled = true)
/*     */   public void onSpawnEntity(SpawnEntityEvent event) {
/*     */     boolean canSpawn;
/*     */     String what;
/* 298 */     if (event.getResult() == Event.Result.ALLOW)
/* 299 */       return;  if (!isRegionSupportEnabled(event.getWorld()))
/* 300 */       return;  if (isWhitelisted(event.getCause(), event.getWorld(), false))
/*     */       return; 
/* 302 */     Location target = event.getTarget();
/* 303 */     EntityType type = event.getEffectiveType();
/*     */     
/* 305 */     RegionQuery query = getPlugin().getRegionContainer().createQuery();
/* 306 */     RegionAssociable associable = createRegionAssociable(event.getCause());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 312 */     if (Entities.isVehicle(type)) {
/* 313 */       canSpawn = query.testBuild(target, associable, combine((DelegateEvent)event, new StateFlag[] { DefaultFlag.PLACE_VEHICLE }));
/* 314 */       what = "place vehicles";
/*     */     
/*     */     }
/* 317 */     else if (event.getEntity() instanceof org.bukkit.entity.Item) {
/* 318 */       canSpawn = query.testBuild(target, associable, combine((DelegateEvent)event, new StateFlag[] { DefaultFlag.ITEM_DROP }));
/* 319 */       what = "drop items";
/*     */     
/*     */     }
/* 322 */     else if (event.getEffectiveType() == EntityType.EXPERIENCE_ORB) {
/* 323 */       canSpawn = query.testBuild(target, associable, combine((DelegateEvent)event, new StateFlag[] { DefaultFlag.EXP_DROPS }));
/* 324 */       what = "drop XP";
/*     */     }
/*     */     else {
/*     */       
/* 328 */       canSpawn = query.testBuild(target, associable, combine((DelegateEvent)event, new StateFlag[0]));
/*     */       
/* 330 */       if (event.getEntity() instanceof org.bukkit.entity.Item) {
/* 331 */         what = "drop items";
/*     */       } else {
/* 333 */         what = "place things";
/*     */       } 
/*     */     } 
/*     */     
/* 337 */     if (!canSpawn) {
/* 338 */       tellErrorMessage((DelegateEvent)event, event.getCause(), target, what);
/* 339 */       event.setCancelled(true);
/*     */     } 
/*     */   } @EventHandler(ignoreCancelled = true)
/*     */   public void onDestroyEntity(DestroyEntityEvent event) {
/*     */     boolean canDestroy;
/*     */     String what;
/* 345 */     if (event.getResult() == Event.Result.ALLOW)
/* 346 */       return;  if (!isRegionSupportEnabled(event.getWorld()))
/* 347 */       return;  if (isWhitelisted(event.getCause(), event.getWorld(), false))
/*     */       return; 
/* 349 */     Location target = event.getTarget();
/* 350 */     EntityType type = event.getEntity().getType();
/* 351 */     RegionAssociable associable = createRegionAssociable(event.getCause());
/*     */     
/* 353 */     RegionQuery query = getPlugin().getRegionContainer().createQuery();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 358 */     if (Entities.isVehicle(type)) {
/* 359 */       canDestroy = query.testBuild(target, associable, combine((DelegateEvent)event, new StateFlag[] { DefaultFlag.DESTROY_VEHICLE }));
/* 360 */       what = "break vehicles";
/*     */     
/*     */     }
/* 363 */     else if (event.getEntity() instanceof org.bukkit.entity.Item || event.getEntity() instanceof org.bukkit.entity.ExperienceOrb) {
/* 364 */       canDestroy = query.testBuild(target, associable, combine((DelegateEvent)event, new StateFlag[] { DefaultFlag.ITEM_PICKUP }));
/* 365 */       what = "pick up items";
/*     */     }
/*     */     else {
/*     */       
/* 369 */       canDestroy = query.testBuild(target, associable, combine((DelegateEvent)event, new StateFlag[0]));
/* 370 */       what = "break things";
/*     */     } 
/*     */     
/* 373 */     if (!canDestroy) {
/* 374 */       tellErrorMessage((DelegateEvent)event, event.getCause(), target, what);
/* 375 */       event.setCancelled(true);
/*     */     } 
/*     */   } @EventHandler(ignoreCancelled = true)
/*     */   public void onUseEntity(UseEntityEvent event) {
/*     */     boolean canUse;
/*     */     String what;
/* 381 */     if (event.getResult() == Event.Result.ALLOW)
/* 382 */       return;  if (!isRegionSupportEnabled(event.getWorld()))
/* 383 */       return;  if (isWhitelisted(event.getCause(), event.getWorld(), false))
/*     */       return; 
/* 385 */     Location target = event.getTarget();
/* 386 */     RegionAssociable associable = createRegionAssociable(event.getCause());
/*     */     
/* 388 */     RegionQuery query = getPlugin().getRegionContainer().createQuery();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 393 */     if (Entities.isHostile(event.getEntity()) || Entities.isAmbient(event.getEntity()) || Entities.isNPC(event.getEntity())) {
/* 394 */       canUse = (event.getRelevantFlags().isEmpty() || query.queryState(target, associable, combine((DelegateEvent)event, new StateFlag[0])) != StateFlag.State.DENY);
/* 395 */       what = "use that";
/*     */     
/*     */     }
/* 398 */     else if (Entities.isConsideredBuildingIfUsed(event.getEntity())) {
/* 399 */       canUse = query.testBuild(target, associable, combine((DelegateEvent)event, new StateFlag[0]));
/* 400 */       what = "change that";
/*     */     
/*     */     }
/* 403 */     else if (Entities.isRiddenOnUse(event.getEntity())) {
/* 404 */       canUse = query.testBuild(target, associable, combine((DelegateEvent)event, new StateFlag[] { DefaultFlag.RIDE, DefaultFlag.INTERACT }));
/* 405 */       what = "ride that";
/*     */     }
/*     */     else {
/*     */       
/* 409 */       canUse = query.testBuild(target, associable, combine((DelegateEvent)event, new StateFlag[] { DefaultFlag.INTERACT }));
/* 410 */       what = "use that";
/*     */     } 
/*     */     
/* 413 */     if (!canUse) {
/* 414 */       tellErrorMessage((DelegateEvent)event, event.getCause(), target, what);
/* 415 */       event.setCancelled(true);
/*     */     } 
/*     */   } @EventHandler(ignoreCancelled = true)
/*     */   public void onDamageEntity(DamageEntityEvent event) {
/*     */     boolean canDamage;
/*     */     String what;
/* 421 */     if (event.getResult() == Event.Result.ALLOW)
/* 422 */       return;  if (!isRegionSupportEnabled(event.getWorld())) {
/*     */       return;
/*     */     }
/* 425 */     Location target = event.getTarget();
/* 426 */     RegionAssociable associable = createRegionAssociable(event.getCause());
/*     */     
/* 428 */     RegionQuery query = getPlugin().getRegionContainer().createQuery();
/* 429 */     Player playerAttacker = event.getCause().getFirstPlayer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 436 */     boolean pvp = (event.getEntity() instanceof Player && playerAttacker != null && !playerAttacker.equals(event.getEntity()));
/* 437 */     if (isWhitelisted(event.getCause(), event.getWorld(), pvp)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 442 */     if (Entities.isHostile(event.getEntity()) || Entities.isAmbient(event.getEntity())) {
/* 443 */       canDamage = (event.getRelevantFlags().isEmpty() || query.queryState(target, associable, combine((DelegateEvent)event, new StateFlag[0])) != StateFlag.State.DENY);
/* 444 */       what = "hit that";
/*     */     
/*     */     }
/* 447 */     else if (Entities.isConsideredBuildingIfUsed(event.getEntity())) {
/* 448 */       canDamage = query.testBuild(target, associable, combine((DelegateEvent)event, new StateFlag[0]));
/* 449 */       what = "change that";
/*     */     
/*     */     }
/* 452 */     else if (pvp) {
/* 453 */       Player defender = (Player)event.getEntity();
/*     */ 
/*     */       
/* 456 */       canDamage = (query.testBuild(target, associable, combine((DelegateEvent)event, new StateFlag[] { DefaultFlag.PVP })) && query.queryState(playerAttacker.getLocation(), playerAttacker, combine((DelegateEvent)event, new StateFlag[] { DefaultFlag.PVP })) != StateFlag.State.DENY);
/*     */ 
/*     */       
/* 459 */       if (!canDamage && Events.fireAndTestCancel((Event)new DisallowedPVPEvent(playerAttacker, defender, event.getOriginalEvent()))) {
/* 460 */         canDamage = true;
/*     */       }
/*     */       
/* 463 */       what = "PvP";
/*     */     
/*     */     }
/* 466 */     else if (event.getEntity() instanceof Player) {
/* 467 */       canDamage = (event.getRelevantFlags().isEmpty() || query.queryState(target, associable, combine((DelegateEvent)event, new StateFlag[0])) != StateFlag.State.DENY);
/* 468 */       what = "damage that";
/*     */     }
/*     */     else {
/*     */       
/* 472 */       canDamage = query.testBuild(target, associable, combine((DelegateEvent)event, new StateFlag[] { DefaultFlag.INTERACT }));
/* 473 */       what = "hit that";
/*     */     } 
/*     */     
/* 476 */     if (!canDamage) {
/* 477 */       tellErrorMessage((DelegateEvent)event, event.getCause(), target, what);
/* 478 */       event.setCancelled(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onVehicleExit(VehicleExitEvent event) {
/* 484 */     Vehicle vehicle = event.getVehicle();
/* 485 */     LivingEntity livingEntity = event.getExited();
/*     */ 
/*     */     
/* 488 */     Player player = (Player)livingEntity;
/*     */     
/* 490 */     RegionQuery query = getPlugin().getRegionContainer().createQuery();
/* 491 */     Location location = vehicle.getLocation();
/* 492 */     if (vehicle instanceof org.bukkit.entity.Tameable && livingEntity instanceof Player && !isWhitelisted(Cause.create(new Object[] { player }, ), vehicle.getWorld(), false) && !query.testBuild(location, player, new StateFlag[] { DefaultFlag.RIDE, DefaultFlag.INTERACT })) {
/* 493 */       long now = System.currentTimeMillis();
/* 494 */       Long lastTime = (Long)WGMetadata.getIfPresent((Metadatable)player, "worldguard.region.disembarkMessage", Long.class);
/* 495 */       if (lastTime == null || now - lastTime.longValue() >= 500L) {
/* 496 */         player.sendMessage("" + ChatColor.GOLD + "Don't disembark here!" + ChatColor.GRAY + " You can't get back on.");
/* 497 */         WGMetadata.put((Metadatable)player, "worldguard.region.disembarkMessage", Long.valueOf(now));
/*     */       } 
/*     */       
/* 500 */       event.setCancelled(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isWhitelistedEntity(Entity entity) {
/* 507 */     return Entities.isNonPlayerCreature(entity);
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
/*     */   private static StateFlag[] combine(DelegateEvent event, StateFlag... flag) {
/* 520 */     List<StateFlag> extra = event.getRelevantFlags();
/* 521 */     StateFlag[] flags = Arrays.<StateFlag>copyOf(flag, flag.length + extra.size());
/* 522 */     for (int i = 0; i < extra.size(); i++) {
/* 523 */       flags[flag.length + i] = extra.get(i);
/*     */     }
/* 525 */     return flags;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\RegionProtectionListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */