/*     */ package com.sk89q.worldguard.bukkit.listener;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.sk89q.worldguard.bukkit.WorldConfiguration;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.bukkit.event.DelegateEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.block.BreakBlockEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.block.PlaceBlockEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.block.UseBlockEvent;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.block.SignChangeEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChestProtectionListener
/*     */   extends AbstractListener
/*     */ {
/*     */   public ChestProtectionListener(WorldGuardPlugin plugin) {
/*  47 */     super(plugin);
/*     */   }
/*     */   
/*     */   private void sendMessage(DelegateEvent event, Player player, String message) {
/*  51 */     if (!event.isSilent()) {
/*  52 */       player.sendMessage(message);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onPlaceBlock(final PlaceBlockEvent event) {
/*  58 */     final Player player = event.getCause().getFirstPlayer();
/*     */     
/*  60 */     if (player != null) {
/*  61 */       final WorldConfiguration wcfg = getWorldConfig(player);
/*     */ 
/*     */       
/*  64 */       if (!wcfg.signChestProtection) {
/*     */         return;
/*     */       }
/*     */       
/*  68 */       event.filter(new Predicate<Location>()
/*     */           {
/*     */             public boolean apply(Location target) {
/*  71 */               if (wcfg.getChestProtection().isChest(event.getEffectiveMaterial().getId()) && wcfg.isChestProtected(target.getBlock(), player)) {
/*  72 */                 ChestProtectionListener.this.sendMessage((DelegateEvent)event, player, ChatColor.DARK_RED + "This spot is for a chest that you don't have permission for.");
/*  73 */                 return false;
/*     */               } 
/*     */               
/*  76 */               return true;
/*     */             }
/*     */           }true);
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onBreakBlock(final BreakBlockEvent event) {
/*  84 */     final Player player = event.getCause().getFirstPlayer();
/*     */     
/*  86 */     final WorldConfiguration wcfg = getWorldConfig(event.getWorld());
/*     */ 
/*     */     
/*  89 */     if (!wcfg.signChestProtection) {
/*     */       return;
/*     */     }
/*     */     
/*  93 */     if (player != null) {
/*  94 */       event.filter(new Predicate<Location>()
/*     */           {
/*     */             public boolean apply(Location target) {
/*  97 */               if (wcfg.isChestProtected(target.getBlock(), player)) {
/*  98 */                 ChestProtectionListener.this.sendMessage((DelegateEvent)event, player, ChatColor.DARK_RED + "This chest is protected.");
/*  99 */                 return false;
/*     */               } 
/*     */               
/* 102 */               return true;
/*     */             }
/*     */           }true);
/*     */     } else {
/* 106 */       event.filter(new Predicate<Location>()
/*     */           {
/*     */             public boolean apply(Location target) {
/* 109 */               return !wcfg.isChestProtected(target.getBlock());
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onUseBlock(final UseBlockEvent event) {
/* 118 */     final Player player = event.getCause().getFirstPlayer();
/*     */     
/* 120 */     final WorldConfiguration wcfg = getWorldConfig(event.getWorld());
/*     */ 
/*     */     
/* 123 */     if (!wcfg.signChestProtection) {
/*     */       return;
/*     */     }
/*     */     
/* 127 */     if (player != null) {
/* 128 */       event.filter(new Predicate<Location>()
/*     */           {
/*     */             public boolean apply(Location target) {
/* 131 */               if (wcfg.isChestProtected(target.getBlock(), player)) {
/* 132 */                 ChestProtectionListener.this.sendMessage((DelegateEvent)event, player, ChatColor.DARK_RED + "This chest is protected.");
/* 133 */                 return false;
/*     */               } 
/*     */               
/* 136 */               return true;
/*     */             }
/*     */           }true);
/*     */     } else {
/* 140 */       event.filter(new Predicate<Location>()
/*     */           {
/*     */             public boolean apply(Location target) {
/* 143 */               return !wcfg.isChestProtected(target.getBlock());
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onSignChange(SignChangeEvent event) {
/* 152 */     Player player = event.getPlayer();
/* 153 */     WorldConfiguration wcfg = getWorldConfig(player);
/*     */     
/* 155 */     if (wcfg.signChestProtection) {
/* 156 */       if (event.getLine(0).equalsIgnoreCase("[Lock]")) {
/* 157 */         if (wcfg.isChestProtectedPlacement(event.getBlock(), player)) {
/* 158 */           player.sendMessage(ChatColor.DARK_RED + "You do not own the adjacent chest.");
/* 159 */           event.getBlock().breakNaturally();
/* 160 */           event.setCancelled(true);
/*     */           
/*     */           return;
/*     */         } 
/* 164 */         if (event.getBlock().getTypeId() != 63) {
/* 165 */           player.sendMessage(ChatColor.RED + "The [Lock] sign must be a sign post, not a wall sign.");
/*     */ 
/*     */           
/* 168 */           event.getBlock().breakNaturally();
/* 169 */           event.setCancelled(true);
/*     */           
/*     */           return;
/*     */         } 
/* 173 */         if (!event.getLine(1).equalsIgnoreCase(player.getName())) {
/* 174 */           player.sendMessage(ChatColor.RED + "The first owner line must be your name.");
/*     */ 
/*     */           
/* 177 */           event.getBlock().breakNaturally();
/* 178 */           event.setCancelled(true);
/*     */           
/*     */           return;
/*     */         } 
/* 182 */         int below = event.getBlock().getRelative(0, -1, 0).getTypeId();
/*     */         
/* 184 */         if (below == 46 || below == 12 || below == 13 || below == 63) {
/*     */           
/* 186 */           player.sendMessage(ChatColor.RED + "That is not a safe block that you're putting this sign on.");
/*     */ 
/*     */           
/* 189 */           event.getBlock().breakNaturally();
/* 190 */           event.setCancelled(true);
/*     */           
/*     */           return;
/*     */         } 
/* 194 */         event.setLine(0, "[Lock]");
/* 195 */         player.sendMessage(ChatColor.YELLOW + "A chest or double chest above is now protected.");
/*     */       }
/*     */     
/* 198 */     } else if (!wcfg.disableSignChestProtectionCheck && 
/* 199 */       event.getLine(0).equalsIgnoreCase("[Lock]")) {
/* 200 */       player.sendMessage(ChatColor.RED + "WorldGuard's sign chest protection is disabled.");
/*     */ 
/*     */       
/* 203 */       event.getBlock().breakNaturally();
/* 204 */       event.setCancelled(true);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\ChestProtectionListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */