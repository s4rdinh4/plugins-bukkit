/*     */ package br.com.sgcraft.arenax1.listener;
/*     */ 
/*     */ import br.com.sgcraft.arenax1.ArenaConfig;
/*     */ import br.com.sgcraft.arenax1.arena.Arena;
/*     */ import br.com.sgcraft.arenax1.executor.ArenaExecutor;
/*     */ import br.com.sgcraft.arenax1.gui.GUI;
/*     */ import br.com.sgcraft.arenax1.language.Language;
/*     */ import org.bukkit.entity.Arrow;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityShootBowEvent;
/*     */ import org.bukkit.event.entity.PlayerDeathEvent;
/*     */ import org.bukkit.event.player.PlayerCommandPreprocessEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerKickEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerListener
/*     */   implements Listener
/*     */ {
/*     */   private final ArenaExecutor arenaExecutor;
/*     */   private final GUI gui;
/*     */   private final int guiItem;
/*     */   private final Language language;
/*     */   
/*     */   public PlayerListener(ArenaExecutor arenaExecutor, ArenaConfig arenaConfig, GUI gui, Language language) {
/*  35 */     this.arenaExecutor = arenaExecutor;
/*  36 */     this.gui = gui;
/*  37 */     this.guiItem = arenaConfig.getGuiItem();
/*  38 */     this.language = language;
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerKickEvent(PlayerKickEvent e) {
/*  44 */     if (e.isCancelled()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  49 */     this.arenaExecutor.playerDeath(e.getPlayer());
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerDeathEvent(PlayerDeathEvent e) {
/*  55 */     this.arenaExecutor.playerDeath(e.getEntity());
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerQuitEvent(PlayerQuitEvent e) {
/*  61 */     this.arenaExecutor.playerDeath(e.getPlayer());
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST)
/*     */   public void onPlayerDamageEvent(EntityDamageByEntityEvent e) {
/*  67 */     if (e.getEntity() instanceof Player) {
/*     */ 
/*     */       
/*  70 */       Player player = null;
/*  71 */       if (e.getDamager() instanceof Player) {
/*     */         
/*  73 */         player = (Player)e.getDamager();
/*  74 */       } else if (e.getDamager() instanceof Arrow) {
/*     */         
/*  76 */         Arrow arrow = (Arrow)e.getDamager();
/*  77 */         if (arrow.getShooter() instanceof Player)
/*     */         {
/*  79 */           player = (Player)arrow.getShooter();
/*     */         }
/*     */       } else {
/*     */         return;
/*     */       } 
/*     */ 
/*     */       
/*  86 */       Arena arena = this.arenaExecutor.getPlayerArena(player, false);
/*     */       
/*  88 */       if (arena == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  93 */       if (arena.isWaitinigToStart()) {
/*     */         
/*  95 */         player.sendMessage(this.language.getMessage("ErrorWaitDuelStart", (Object[])new String[0]));
/*  96 */         e.setCancelled(true);
/*  97 */       } else if (arena.isOcurring()) {
/*     */         
/*  99 */         e.setCancelled(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST)
/*     */   public void onPlayerShootBowEvent(EntityShootBowEvent e) {
/* 107 */     if (e.getEntity() instanceof Player) {
/*     */       
/* 109 */       Player player = (Player)e.getEntity();
/*     */       
/* 111 */       Arena arena = this.arenaExecutor.getPlayerArena(player, false);
/*     */       
/* 113 */       if (arena == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 118 */       if (arena.isWaitinigToStart()) {
/*     */         
/* 120 */         player.sendMessage(this.language.getMessage("ErrorWaitDuelStart", (Object[])new String[0]));
/* 121 */         e.setCancelled(true);
/* 122 */       } else if (arena.isOcurring()) {
/*     */         
/* 124 */         e.setCancelled(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerInteractEvent(PlayerInteractEvent e) {
/* 132 */     if (e.getPlayer().getItemInHand().getTypeId() == this.guiItem)
/*     */     {
/* 134 */       this.gui.openGui(e.getPlayer());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent e) {
/* 141 */     if (e.getMessage().equalsIgnoreCase("/suicide"))
/*     */     {
/* 143 */       e.getPlayer().setHealth(0.0D);
/*     */     }
/*     */     
/* 146 */     if (this.arenaExecutor.playerCommand(e.getPlayer()) && !e.getPlayer().isOp()) {
/*     */       
/* 148 */       e.getPlayer().sendMessage(this.language.getMessage("ErrorCommandsBlocked", (Object[])new String[0]));
/* 149 */       e.setCancelled(true);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\ArenaX1-0.3.2.jar!\br\com\tinycraft\arenax1\listener\PlayerListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */