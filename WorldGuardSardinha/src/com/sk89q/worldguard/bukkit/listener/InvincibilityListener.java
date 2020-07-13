/*     */ package com.sk89q.worldguard.bukkit.listener;
/*     */ 
/*     */ import com.sk89q.worldguard.bukkit.WorldConfiguration;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Tameable;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.entity.EntityCombustEvent;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.FoodLevelChangeEvent;
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
/*     */ public class InvincibilityListener
/*     */   extends AbstractListener
/*     */ {
/*     */   public InvincibilityListener(WorldGuardPlugin plugin) {
/*  42 */     super(plugin);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isInvincible(Player player) {
/*  52 */     return getPlugin().getSessionManager().get(player).isInvincible(player);
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onEntityDamage(EntityDamageEvent event) {
/*  57 */     Entity victim = event.getEntity();
/*  58 */     WorldConfiguration worldConfig = getPlugin().getGlobalStateManager().get(victim.getWorld());
/*     */     
/*  60 */     if (victim instanceof Player) {
/*  61 */       Player player = (Player)victim;
/*     */       
/*  63 */       if (isInvincible(player)) {
/*  64 */         player.setFireTicks(0);
/*  65 */         event.setCancelled(true);
/*     */         
/*  67 */         if (event instanceof EntityDamageByEntityEvent) {
/*  68 */           EntityDamageByEntityEvent byEntityEvent = (EntityDamageByEntityEvent)event;
/*  69 */           Entity attacker = byEntityEvent.getDamager();
/*     */           
/*  71 */           if (worldConfig.regionInvinciblityRemovesMobs && attacker instanceof org.bukkit.entity.LivingEntity && !(attacker instanceof Player) && (!(attacker instanceof Tameable) || 
/*     */             
/*  73 */             !((Tameable)attacker).isTamed())) {
/*  74 */             attacker.remove();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onEntityCombust(EntityCombustEvent event) {
/*  83 */     Entity entity = event.getEntity();
/*     */     
/*  85 */     if (entity instanceof Player) {
/*  86 */       Player player = (Player)entity;
/*     */       
/*  88 */       if (isInvincible(player)) {
/*  89 */         event.setCancelled(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onFoodLevelChange(FoodLevelChangeEvent event) {
/*  96 */     if (event.getEntity() instanceof Player) {
/*  97 */       Player player = (Player)event.getEntity();
/*     */       
/*  99 */       if (event.getFoodLevel() < player.getFoodLevel() && isInvincible(player))
/* 100 */         event.setCancelled(true); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\InvincibilityListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */