/*     */ package com.sk89q.worldguard.bukkit.util;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.sk89q.worldguard.bukkit.event.BulkEvent;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
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
/*     */ public final class Events
/*     */ {
/*     */   public static void fire(Event event) {
/*  48 */     Preconditions.checkNotNull(event);
/*  49 */     Bukkit.getServer().getPluginManager().callEvent(event);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends Event & Cancellable> boolean fireAndTestCancel(T eventToFire) {
/*  60 */     Bukkit.getServer().getPluginManager().callEvent((Event)eventToFire);
/*  61 */     return ((Cancellable)eventToFire).isCancelled();
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
/*     */   public static <T extends Event & Cancellable> boolean fireToCancel(Cancellable original, T eventToFire) {
/*  74 */     Bukkit.getServer().getPluginManager().callEvent((Event)eventToFire);
/*  75 */     if (((Cancellable)eventToFire).isCancelled()) {
/*  76 */       original.setCancelled(true);
/*  77 */       return true;
/*     */     } 
/*     */     
/*  80 */     return false;
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
/*     */   public static <T extends Event & Cancellable> boolean fireItemEventToCancel(PlayerInteractEvent original, T eventToFire) {
/*  93 */     Bukkit.getServer().getPluginManager().callEvent((Event)eventToFire);
/*  94 */     if (((Cancellable)eventToFire).isCancelled()) {
/*  95 */       original.setUseItemInHand(Event.Result.DENY);
/*  96 */       return true;
/*     */     } 
/*     */     
/*  99 */     return false;
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
/*     */   public static <T extends Event & Cancellable & BulkEvent> boolean fireBulkEventToCancel(Cancellable original, T eventToFire) {
/* 112 */     Bukkit.getServer().getPluginManager().callEvent((Event)eventToFire);
/* 113 */     if (((BulkEvent)eventToFire).getExplicitResult() == Event.Result.DENY) {
/* 114 */       original.setCancelled(true);
/* 115 */       return true;
/*     */     } 
/*     */     
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFireCause(EntityDamageEvent.DamageCause cause) {
/* 128 */     return (cause == EntityDamageEvent.DamageCause.FIRE || cause == EntityDamageEvent.DamageCause.FIRE_TICK);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isExplosionCause(EntityDamageEvent.DamageCause cause) {
/* 138 */     return (cause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || cause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION);
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
/*     */   public static void restoreStatistic(Entity entity, EntityDamageEvent.DamageCause cause) {
/* 150 */     if (cause == EntityDamageEvent.DamageCause.DROWNING && entity instanceof LivingEntity) {
/* 151 */       LivingEntity living = (LivingEntity)entity;
/* 152 */       living.setRemainingAir(living.getMaximumAir());
/*     */     } 
/*     */     
/* 155 */     if (isFireCause(cause)) {
/* 156 */       entity.setFireTicks(0);
/*     */     }
/*     */     
/* 159 */     if (cause == EntityDamageEvent.DamageCause.LAVA)
/* 160 */       entity.setFireTicks(0); 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukki\\util\Events.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */