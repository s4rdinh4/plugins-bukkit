/*     */ package com.sk89q.worldguard.bukkit.listener;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.sk89q.worldedit.Vector;
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import com.sk89q.worldguard.blacklist.event.BlacklistEvent;
/*     */ import com.sk89q.worldguard.blacklist.event.BlockBreakBlacklistEvent;
/*     */ import com.sk89q.worldguard.blacklist.event.BlockDispenseBlacklistEvent;
/*     */ import com.sk89q.worldguard.blacklist.event.BlockInteractBlacklistEvent;
/*     */ import com.sk89q.worldguard.blacklist.event.BlockPlaceBlacklistEvent;
/*     */ import com.sk89q.worldguard.blacklist.event.ItemAcquireBlacklistEvent;
/*     */ import com.sk89q.worldguard.blacklist.event.ItemDestroyWithBlacklistEvent;
/*     */ import com.sk89q.worldguard.blacklist.event.ItemDropBlacklistEvent;
/*     */ import com.sk89q.worldguard.blacklist.event.ItemUseBlacklistEvent;
/*     */ import com.sk89q.worldguard.bukkit.BukkitUtil;
/*     */ import com.sk89q.worldguard.bukkit.ConfigurationManager;
/*     */ import com.sk89q.worldguard.bukkit.WorldConfiguration;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.bukkit.event.block.BreakBlockEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.block.PlaceBlockEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.block.UseBlockEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.entity.DestroyEntityEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.entity.SpawnEntityEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.inventory.UseItemEvent;
/*     */ import com.sk89q.worldguard.bukkit.util.Materials;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.entity.Item;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.block.BlockDispenseEvent;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryCreativeEvent;
/*     */ import org.bukkit.event.player.PlayerDropItemEvent;
/*     */ import org.bukkit.event.player.PlayerItemHeldEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlacklistListener
/*     */   extends AbstractListener
/*     */ {
/*     */   public BlacklistListener(WorldGuardPlugin plugin) {
/*  64 */     super(plugin);
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onBreakBlock(final BreakBlockEvent event) {
/*  69 */     final Player player = event.getCause().getFirstPlayer();
/*     */     
/*  71 */     if (player == null) {
/*     */       return;
/*     */     }
/*     */     
/*  75 */     final LocalPlayer localPlayer = getPlugin().wrapPlayer(player);
/*  76 */     final WorldConfiguration wcfg = getWorldConfig(player);
/*     */ 
/*     */     
/*  79 */     if (wcfg.getBlacklist() == null) {
/*     */       return;
/*     */     }
/*     */     
/*  83 */     event.filter(new Predicate<Location>()
/*     */         {
/*     */           public boolean apply(Location target) {
/*  86 */             if (!wcfg.getBlacklist().check((BlacklistEvent)new BlockBreakBlacklistEvent(localPlayer, 
/*  87 */                   BukkitUtil.toVector(target), BukkitUtil.createTarget(target.getBlock(), event.getEffectiveMaterial())), false, false))
/*  88 */               return false; 
/*  89 */             if (!wcfg.getBlacklist().check((BlacklistEvent)new ItemDestroyWithBlacklistEvent(localPlayer, 
/*  90 */                   BukkitUtil.toVector(target), BukkitUtil.createTarget(player.getItemInHand())), false, false)) {
/*  91 */               return false;
/*     */             }
/*     */             
/*  94 */             return true;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onPlaceBlock(final PlaceBlockEvent event) {
/* 101 */     Player player = event.getCause().getFirstPlayer();
/*     */     
/* 103 */     if (player == null) {
/*     */       return;
/*     */     }
/*     */     
/* 107 */     final LocalPlayer localPlayer = getPlugin().wrapPlayer(player);
/* 108 */     final WorldConfiguration wcfg = getWorldConfig(player);
/*     */ 
/*     */     
/* 111 */     if (wcfg.getBlacklist() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 115 */     event.filter(new Predicate<Location>()
/*     */         {
/*     */           public boolean apply(Location target) {
/* 118 */             return wcfg.getBlacklist().check((BlacklistEvent)new BlockPlaceBlacklistEvent(localPlayer, 
/* 119 */                   BukkitUtil.toVector(target), BukkitUtil.createTarget(target.getBlock(), event.getEffectiveMaterial())), false, false);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onUseBlock(final UseBlockEvent event) {
/* 127 */     Player player = event.getCause().getFirstPlayer();
/*     */     
/* 129 */     if (player == null) {
/*     */       return;
/*     */     }
/*     */     
/* 133 */     final LocalPlayer localPlayer = getPlugin().wrapPlayer(player);
/* 134 */     final WorldConfiguration wcfg = getWorldConfig(player);
/*     */ 
/*     */     
/* 137 */     if (wcfg.getBlacklist() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 141 */     event.filter(new Predicate<Location>()
/*     */         {
/*     */           public boolean apply(Location target) {
/* 144 */             return wcfg.getBlacklist().check((BlacklistEvent)new BlockInteractBlacklistEvent(localPlayer, 
/* 145 */                   BukkitUtil.toVector(target), BukkitUtil.createTarget(target.getBlock(), event.getEffectiveMaterial())), false, false);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onSpawnEntity(SpawnEntityEvent event) {
/* 152 */     Player player = event.getCause().getFirstPlayer();
/*     */     
/* 154 */     if (player == null) {
/*     */       return;
/*     */     }
/*     */     
/* 158 */     LocalPlayer localPlayer = getPlugin().wrapPlayer(player);
/* 159 */     WorldConfiguration wcfg = getWorldConfig(player);
/*     */ 
/*     */     
/* 162 */     if (wcfg.getBlacklist() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 166 */     Material material = Materials.getRelatedMaterial(event.getEffectiveType());
/* 167 */     if (material != null && 
/* 168 */       !wcfg.getBlacklist().check((BlacklistEvent)new ItemUseBlacklistEvent(localPlayer, BukkitUtil.toVector(event.getTarget()), BukkitUtil.createTarget(material)), false, false)) {
/* 169 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onDestroyEntity(DestroyEntityEvent event) {
/* 176 */     Player player = event.getCause().getFirstPlayer();
/*     */     
/* 178 */     if (player == null) {
/*     */       return;
/*     */     }
/*     */     
/* 182 */     LocalPlayer localPlayer = getPlugin().wrapPlayer(player);
/* 183 */     Entity target = event.getEntity();
/* 184 */     WorldConfiguration wcfg = getWorldConfig(player);
/*     */ 
/*     */     
/* 187 */     if (wcfg.getBlacklist() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 191 */     if (target instanceof Item) {
/* 192 */       Item item = (Item)target;
/* 193 */       if (!wcfg.getBlacklist().check((BlacklistEvent)new ItemAcquireBlacklistEvent(localPlayer, 
/*     */             
/* 195 */             BukkitUtil.toVector(target.getLocation()), BukkitUtil.createTarget(item.getItemStack())), false, true)) {
/* 196 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 201 */     Material material = Materials.getRelatedMaterial(target.getType());
/* 202 */     if (material != null)
/*     */     {
/* 204 */       if (!wcfg.getBlacklist().check((BlacklistEvent)new BlockBreakBlacklistEvent(localPlayer, BukkitUtil.toVector(event.getTarget()), BukkitUtil.createTarget(material)), false, false)) {
/* 205 */         event.setCancelled(true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onUseItem(UseItemEvent event) {
/* 212 */     Player player = event.getCause().getFirstPlayer();
/*     */     
/* 214 */     if (player == null) {
/*     */       return;
/*     */     }
/*     */     
/* 218 */     LocalPlayer localPlayer = getPlugin().wrapPlayer(player);
/* 219 */     ItemStack target = event.getItemStack();
/* 220 */     WorldConfiguration wcfg = getWorldConfig(player);
/*     */ 
/*     */     
/* 223 */     if (wcfg.getBlacklist() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 227 */     if (!wcfg.getBlacklist().check((BlacklistEvent)new ItemUseBlacklistEvent(localPlayer, BukkitUtil.toVector(player.getLocation()), BukkitUtil.createTarget(target)), false, false)) {
/* 228 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onPlayerDropItem(PlayerDropItemEvent event) {
/* 234 */     ConfigurationManager cfg = getPlugin().getGlobalStateManager();
/* 235 */     WorldConfiguration wcfg = cfg.get(event.getPlayer().getWorld());
/*     */     
/* 237 */     if (wcfg.getBlacklist() != null) {
/* 238 */       Item ci = event.getItemDrop();
/*     */       
/* 240 */       if (!wcfg.getBlacklist().check((BlacklistEvent)new ItemDropBlacklistEvent(
/* 241 */             getPlugin().wrapPlayer(event.getPlayer()), 
/* 242 */             BukkitUtil.toVector(ci.getLocation()), BukkitUtil.createTarget(ci.getItemStack())), false, false)) {
/* 243 */         event.setCancelled(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onBlockDispense(BlockDispenseEvent event) {
/* 250 */     ConfigurationManager cfg = getPlugin().getGlobalStateManager();
/* 251 */     WorldConfiguration wcfg = cfg.get(event.getBlock().getWorld());
/*     */     
/* 253 */     if (wcfg.getBlacklist() != null && 
/* 254 */       !wcfg.getBlacklist().check((BlacklistEvent)new BlockDispenseBlacklistEvent(null, (Vector)BukkitUtil.toVector(event.getBlock()), BukkitUtil.createTarget(event.getItem())), false, false)) {
/* 255 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onInventoryClick(InventoryClickEvent event) {
/* 262 */     HumanEntity entity = event.getWhoClicked();
/* 263 */     Inventory inventory = event.getInventory();
/* 264 */     ItemStack item = event.getCurrentItem();
/*     */     
/* 266 */     if (item != null && entity instanceof Player) {
/* 267 */       Player player = (Player)entity;
/* 268 */       ConfigurationManager cfg = getPlugin().getGlobalStateManager();
/* 269 */       WorldConfiguration wcfg = cfg.get(entity.getWorld());
/* 270 */       LocalPlayer localPlayer = getPlugin().wrapPlayer(player);
/*     */       
/* 272 */       if (wcfg.getBlacklist() != null) if (!wcfg.getBlacklist().check((BlacklistEvent)new ItemAcquireBlacklistEvent(localPlayer, 
/* 273 */               BukkitUtil.toVector(entity.getLocation()), BukkitUtil.createTarget(item)), false, false)) {
/* 274 */           event.setCancelled(true);
/*     */           
/* 276 */           if (inventory.getHolder().equals(player)) {
/* 277 */             event.setCurrentItem(null);
/*     */           }
/*     */         }  
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onInventoryCreative(InventoryCreativeEvent event) {
/* 285 */     HumanEntity entity = event.getWhoClicked();
/* 286 */     ItemStack item = event.getCursor();
/*     */     
/* 288 */     if (item != null && entity instanceof Player) {
/* 289 */       Player player = (Player)entity;
/* 290 */       ConfigurationManager cfg = getPlugin().getGlobalStateManager();
/* 291 */       WorldConfiguration wcfg = cfg.get(entity.getWorld());
/* 292 */       LocalPlayer localPlayer = getPlugin().wrapPlayer(player);
/*     */       
/* 294 */       if (wcfg.getBlacklist() != null) if (!wcfg.getBlacklist().check((BlacklistEvent)new ItemAcquireBlacklistEvent(localPlayer, 
/* 295 */               BukkitUtil.toVector(entity.getLocation()), BukkitUtil.createTarget(item)), false, false)) {
/* 296 */           event.setCancelled(true);
/* 297 */           event.setCursor(null);
/*     */         }  
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onPlayerItemHeld(PlayerItemHeldEvent event) {
/* 304 */     Player player = event.getPlayer();
/* 305 */     PlayerInventory playerInventory = player.getInventory();
/* 306 */     ItemStack item = playerInventory.getItem(event.getNewSlot());
/*     */     
/* 308 */     if (item != null) {
/* 309 */       ConfigurationManager cfg = getPlugin().getGlobalStateManager();
/* 310 */       WorldConfiguration wcfg = cfg.get(player.getWorld());
/* 311 */       LocalPlayer localPlayer = getPlugin().wrapPlayer(player);
/*     */       
/* 313 */       if (wcfg.getBlacklist() != null) if (!wcfg.getBlacklist().check((BlacklistEvent)new ItemAcquireBlacklistEvent(localPlayer, 
/* 314 */               BukkitUtil.toVector(player.getLocation()), BukkitUtil.createTarget(item)), false, false))
/* 315 */           playerInventory.setItem(event.getNewSlot(), null);  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\BlacklistListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */