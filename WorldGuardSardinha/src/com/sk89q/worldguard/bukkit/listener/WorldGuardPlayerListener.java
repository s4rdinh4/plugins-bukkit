/*     */ package com.sk89q.worldguard.bukkit.listener;
/*     */ 
/*     */ import com.sk89q.worldedit.Location;
/*     */ import com.sk89q.worldedit.bukkit.BukkitUtil;
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import com.sk89q.worldguard.bukkit.BukkitUtil;
/*     */ import com.sk89q.worldguard.bukkit.ConfigurationManager;
/*     */ import com.sk89q.worldguard.bukkit.WorldConfiguration;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.bukkit.event.player.ProcessPlayerEvent;
/*     */ import com.sk89q.worldguard.bukkit.util.Events;
/*     */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*     */ import com.sk89q.worldguard.protection.flags.DefaultFlag;
/*     */ import com.sk89q.worldguard.protection.flags.Flag;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import com.sk89q.worldguard.session.MoveType;
/*     */ import com.sk89q.worldguard.session.handler.GameModeFlag;
/*     */ import com.sk89q.worldguard.util.command.CommandFilter;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Logger;
/*     */ import java.util.regex.Pattern;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.player.AsyncPlayerChatEvent;
/*     */ import org.bukkit.event.player.PlayerCommandPreprocessEvent;
/*     */ import org.bukkit.event.player.PlayerGameModeChangeEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerItemHeldEvent;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerLoginEvent;
/*     */ import org.bukkit.event.player.PlayerRespawnEvent;
/*     */ import org.bukkit.event.player.PlayerTeleportEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGuardPlayerListener
/*     */   implements Listener
/*     */ {
/*  62 */   private static final Logger log = Logger.getLogger(WorldGuardPlayerListener.class.getCanonicalName());
/*  63 */   private static final Pattern opPattern = Pattern.compile("^/(?:bukkit:)?op(?:\\s.*)?$", 2);
/*     */ 
/*     */ 
/*     */   
/*     */   private WorldGuardPlugin plugin;
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldGuardPlayerListener(WorldGuardPlugin plugin) {
/*  72 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerEvents() {
/*  79 */     PluginManager pm = this.plugin.getServer().getPluginManager();
/*  80 */     pm.registerEvents(this, (Plugin)this.plugin);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
/*  85 */     Player player = event.getPlayer();
/*  86 */     WorldConfiguration wcfg = this.plugin.getGlobalStateManager().get(player.getWorld());
/*  87 */     GameModeFlag handler = (GameModeFlag)this.plugin.getSessionManager().get(player).getHandler(GameModeFlag.class);
/*  88 */     if (handler != null && wcfg.useRegions && !this.plugin.getGlobalRegionManager().hasBypass(player, player.getWorld())) {
/*  89 */       GameMode expected = handler.getSetGameMode();
/*  90 */       if (handler.getOriginalGameMode() != null && expected != null && expected != event.getNewGameMode()) {
/*  91 */         log.info("Game mode change on " + player.getName() + " has been blocked due to the region GAMEMODE flag");
/*  92 */         event.setCancelled(true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerJoin(PlayerJoinEvent event) {
/*  99 */     Player player = event.getPlayer();
/* 100 */     World world = player.getWorld();
/*     */     
/* 102 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 103 */     WorldConfiguration wcfg = cfg.get(world);
/*     */     
/* 105 */     if (cfg.activityHaltToggle) {
/* 106 */       player.sendMessage(ChatColor.YELLOW + "Intensive server activity has been HALTED.");
/*     */ 
/*     */       
/* 109 */       int removed = 0;
/*     */       
/* 111 */       for (Entity entity : world.getEntities()) {
/* 112 */         if (BukkitUtil.isIntensiveEntity(entity)) {
/* 113 */           entity.remove();
/* 114 */           removed++;
/*     */         } 
/*     */       } 
/*     */       
/* 118 */       if (removed > 10) {
/* 119 */         log.info("Halt-Act: " + removed + " entities (>10) auto-removed from " + player
/* 120 */             .getWorld().toString());
/*     */       }
/*     */     } 
/*     */     
/* 124 */     if (wcfg.fireSpreadDisableToggle) {
/* 125 */       player.sendMessage(ChatColor.YELLOW + "Fire spread is currently globally disabled for this world.");
/*     */     }
/*     */ 
/*     */     
/* 129 */     Events.fire((Event)new ProcessPlayerEvent(player));
/*     */     
/* 131 */     this.plugin.getSessionManager().get(player);
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onPlayerChat(AsyncPlayerChatEvent event) {
/* 136 */     Player player = event.getPlayer();
/* 137 */     WorldConfiguration wcfg = this.plugin.getGlobalStateManager().get(player.getWorld());
/* 138 */     if (wcfg.useRegions) {
/* 139 */       if (!this.plugin.getGlobalRegionManager().allows(DefaultFlag.SEND_CHAT, player.getLocation())) {
/* 140 */         player.sendMessage(ChatColor.RED + "You don't have permission to chat in this region!");
/* 141 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/* 145 */       for (Iterator<Player> i = event.getRecipients().iterator(); i.hasNext();) {
/* 146 */         if (!this.plugin.getGlobalRegionManager().allows(DefaultFlag.RECEIVE_CHAT, ((Player)i.next()).getLocation())) {
/* 147 */           i.remove();
/*     */         }
/*     */       } 
/* 150 */       if (event.getRecipients().size() == 0) {
/* 151 */         event.setCancelled(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onPlayerLogin(PlayerLoginEvent event) {
/* 158 */     Player player = event.getPlayer();
/* 159 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/*     */     
/* 161 */     String hostKey = (String)cfg.hostKeys.get(player.getName().toLowerCase());
/* 162 */     if (hostKey != null) {
/* 163 */       String hostname = event.getHostname();
/* 164 */       int colonIndex = hostname.indexOf(':');
/* 165 */       if (colonIndex != -1) {
/* 166 */         hostname = hostname.substring(0, colonIndex);
/*     */       }
/*     */       
/* 169 */       if (!hostname.equals(hostKey)) {
/* 170 */         event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You did not join with the valid host key!");
/*     */         
/* 172 */         log.warning("WorldGuard host key check: " + player
/* 173 */             .getName() + " joined with '" + hostname + "' but '" + hostKey + "' was expected. Kicked!");
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 179 */     if (cfg.deopOnJoin) {
/* 180 */       player.setOp(false);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH)
/*     */   public void onPlayerInteract(PlayerInteractEvent event) {
/* 186 */     Player player = event.getPlayer();
/* 187 */     World world = player.getWorld();
/*     */     
/* 189 */     if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
/* 190 */       handleBlockRightClick(event);
/* 191 */     } else if (event.getAction() == Action.PHYSICAL) {
/* 192 */       handlePhysicalInteract(event);
/*     */     } 
/*     */     
/* 195 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 196 */     WorldConfiguration wcfg = cfg.get(world);
/*     */     
/* 198 */     if (wcfg.removeInfiniteStacks && 
/* 199 */       !this.plugin.hasPermission((CommandSender)player, "worldguard.override.infinite-stack")) {
/* 200 */       int slot = player.getInventory().getHeldItemSlot();
/* 201 */       ItemStack heldItem = player.getInventory().getItem(slot);
/* 202 */       if (heldItem != null && heldItem.getAmount() < 0) {
/* 203 */         player.getInventory().setItem(slot, null);
/* 204 */         player.sendMessage(ChatColor.RED + "Infinite stack removed.");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleBlockRightClick(PlayerInteractEvent event) {
/* 215 */     if (event.isCancelled()) {
/*     */       return;
/*     */     }
/*     */     
/* 219 */     Block block = event.getClickedBlock();
/* 220 */     World world = block.getWorld();
/* 221 */     int type = block.getTypeId();
/* 222 */     Player player = event.getPlayer();
/* 223 */     ItemStack item = player.getItemInHand();
/*     */     
/* 225 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 226 */     WorldConfiguration wcfg = cfg.get(world);
/*     */ 
/*     */     
/* 229 */     if ((type == 54 || type == 84 || type == 23 || type == 61 || type == 62 || type == 117 || type == 116) && wcfg.removeInfiniteStacks && 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 237 */       !this.plugin.hasPermission((CommandSender)player, "worldguard.override.infinite-stack")) {
/* 238 */       for (int slot = 0; slot < 40; slot++) {
/* 239 */         ItemStack heldItem = player.getInventory().getItem(slot);
/* 240 */         if (heldItem != null && heldItem.getAmount() < 0) {
/* 241 */           player.getInventory().setItem(slot, null);
/* 242 */           player.sendMessage(ChatColor.RED + "Infinite stack in slot #" + slot + " removed.");
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 247 */     if (wcfg.useRegions) {
/* 248 */       Block placedIn = block.getRelative(event.getBlockFace());
/* 249 */       ApplicableRegionSet set = this.plugin.getRegionContainer().createQuery().getApplicableRegions(block.getLocation());
/* 250 */       ApplicableRegionSet placedInSet = this.plugin.getRegionContainer().createQuery().getApplicableRegions(placedIn.getLocation());
/* 251 */       LocalPlayer localPlayer = this.plugin.wrapPlayer(player);
/*     */       
/* 253 */       if (item.getTypeId() == wcfg.regionWand && this.plugin.hasPermission((CommandSender)player, "worldguard.region.wand")) {
/* 254 */         if (set.size() > 0) {
/* 255 */           player.sendMessage(ChatColor.YELLOW + "Can you build? " + (
/* 256 */               set.canBuild(localPlayer) ? "Yes" : "No"));
/*     */           
/* 258 */           StringBuilder str = new StringBuilder();
/* 259 */           for (Iterator<ProtectedRegion> it = set.iterator(); it.hasNext(); ) {
/* 260 */             str.append(((ProtectedRegion)it.next()).getId());
/* 261 */             if (it.hasNext()) {
/* 262 */               str.append(", ");
/*     */             }
/*     */           } 
/*     */           
/* 266 */           player.sendMessage(ChatColor.YELLOW + "Applicable regions: " + str.toString());
/*     */         } else {
/* 268 */           player.sendMessage(ChatColor.YELLOW + "WorldGuard: No defined regions here!");
/*     */         } 
/*     */         
/* 271 */         event.setCancelled(true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handlePhysicalInteract(PlayerInteractEvent event) {
/* 282 */     if (event.isCancelled())
/*     */       return; 
/* 284 */     Player player = event.getPlayer();
/* 285 */     Block block = event.getClickedBlock();
/* 286 */     int type = block.getTypeId();
/* 287 */     World world = player.getWorld();
/*     */     
/* 289 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 290 */     WorldConfiguration wcfg = cfg.get(world);
/*     */     
/* 292 */     if (block.getTypeId() == 60 && wcfg.disablePlayerCropTrampling) {
/* 293 */       event.setCancelled(true);
/*     */       return;
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST)
/*     */   public void onPlayerRespawn(PlayerRespawnEvent event) {
/* 300 */     Player player = event.getPlayer();
/* 301 */     Location location = player.getLocation();
/*     */     
/* 303 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 304 */     WorldConfiguration wcfg = cfg.get(player.getWorld());
/*     */     
/* 306 */     if (wcfg.useRegions) {
/* 307 */       ApplicableRegionSet set = this.plugin.getRegionContainer().createQuery().getApplicableRegions(location);
/*     */       
/* 309 */       LocalPlayer localPlayer = this.plugin.wrapPlayer(player);
/* 310 */       Location spawn = (Location)set.getFlag((Flag)DefaultFlag.SPAWN_LOC, localPlayer);
/*     */       
/* 312 */       if (spawn != null) {
/* 313 */         event.setRespawnLocation(BukkitUtil.toLocation(spawn));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGH)
/*     */   public void onItemHeldChange(PlayerItemHeldEvent event) {
/* 320 */     Player player = event.getPlayer();
/*     */     
/* 322 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 323 */     WorldConfiguration wcfg = cfg.get(player.getWorld());
/*     */     
/* 325 */     if (wcfg.removeInfiniteStacks && 
/* 326 */       !this.plugin.hasPermission((CommandSender)player, "worldguard.override.infinite-stack")) {
/* 327 */       int newSlot = event.getNewSlot();
/* 328 */       ItemStack heldItem = player.getInventory().getItem(newSlot);
/* 329 */       if (heldItem != null && heldItem.getAmount() < 0) {
/* 330 */         player.getInventory().setItem(newSlot, null);
/* 331 */         player.sendMessage(ChatColor.RED + "Infinite stack removed.");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
/*     */   public void onPlayerTeleport(PlayerTeleportEvent event) {
/* 338 */     World world = event.getFrom().getWorld();
/* 339 */     Player player = event.getPlayer();
/* 340 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 341 */     WorldConfiguration wcfg = cfg.get(world);
/*     */     
/* 343 */     if (wcfg.useRegions) {
/* 344 */       ApplicableRegionSet set = this.plugin.getRegionContainer().createQuery().getApplicableRegions(event.getTo());
/* 345 */       ApplicableRegionSet setFrom = this.plugin.getRegionContainer().createQuery().getApplicableRegions(event.getFrom());
/* 346 */       LocalPlayer localPlayer = this.plugin.wrapPlayer(player);
/*     */       
/* 348 */       if (cfg.usePlayerTeleports && 
/* 349 */         null != this.plugin.getSessionManager().get(player).testMoveTo(player, event.getTo(), MoveType.TELEPORT)) {
/* 350 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 355 */       if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL && 
/* 356 */         !this.plugin.getGlobalRegionManager().hasBypass(localPlayer, world) && (
/* 357 */         !set.allows(DefaultFlag.ENDERPEARL, localPlayer) || 
/* 358 */         !setFrom.allows(DefaultFlag.ENDERPEARL, localPlayer))) {
/* 359 */         player.sendMessage(ChatColor.DARK_RED + "You're not allowed to go there.");
/* 360 */         event.setCancelled(true);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
/*     */   public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
/* 369 */     Player player = event.getPlayer();
/* 370 */     LocalPlayer localPlayer = this.plugin.wrapPlayer(player);
/* 371 */     World world = player.getWorld();
/* 372 */     ConfigurationManager cfg = this.plugin.getGlobalStateManager();
/* 373 */     WorldConfiguration wcfg = cfg.get(world);
/*     */     
/* 375 */     if (wcfg.useRegions && !this.plugin.getGlobalRegionManager().hasBypass(player, world)) {
/* 376 */       ApplicableRegionSet set = this.plugin.getRegionContainer().createQuery().getApplicableRegions(player.getLocation());
/*     */       
/* 378 */       Set<String> allowedCommands = (Set<String>)set.getFlag((Flag)DefaultFlag.ALLOWED_CMDS, localPlayer);
/* 379 */       Set<String> blockedCommands = (Set<String>)set.getFlag((Flag)DefaultFlag.BLOCKED_CMDS, localPlayer);
/* 380 */       CommandFilter test = new CommandFilter(allowedCommands, blockedCommands);
/*     */       
/* 382 */       if (!test.apply(event.getMessage())) {
/* 383 */         player.sendMessage(ChatColor.RED + event.getMessage() + " is not allowed in this area.");
/* 384 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 389 */     if (cfg.blockInGameOp && 
/* 390 */       opPattern.matcher(event.getMessage()).matches()) {
/* 391 */       player.sendMessage(ChatColor.RED + "/op can only be used in console (as set by a WG setting).");
/* 392 */       event.setCancelled(true);
/*     */       return;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\WorldGuardPlayerListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */