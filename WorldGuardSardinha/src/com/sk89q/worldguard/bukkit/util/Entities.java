/*     */ package com.sk89q.worldguard.bukkit.util;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Projectile;
/*     */ import org.bukkit.entity.Tameable;
/*     */ import org.bukkit.projectiles.ProjectileSource;
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
/*     */ public final class Entities
/*     */ {
/*     */   public static boolean isTamed(@Nullable Entity entity) {
/*  40 */     return (entity instanceof Tameable && ((Tameable)entity).isTamed());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isTNTBased(Entity entity) {
/*  50 */     return (entity instanceof org.bukkit.entity.TNTPrimed || entity instanceof org.bukkit.entity.minecart.ExplosiveMinecart);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFireball(EntityType type) {
/*  61 */     return (type == EntityType.FIREBALL || type == EntityType.SMALL_FIREBALL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRiddenOnUse(Entity entity) {
/*  71 */     return entity instanceof org.bukkit.entity.Vehicle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isVehicle(EntityType type) {
/*  81 */     return (type == EntityType.BOAT || 
/*  82 */       isMinecart(type));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isMinecart(EntityType type) {
/*  92 */     return (type == EntityType.MINECART || type == EntityType.MINECART_CHEST || type == EntityType.MINECART_COMMAND || type == EntityType.MINECART_FURNACE || type == EntityType.MINECART_HOPPER || type == EntityType.MINECART_MOB_SPAWNER || type == EntityType.MINECART_TNT);
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
/*     */   public static Entity getShooter(Entity entity) {
/* 109 */     while (entity instanceof Projectile) {
/* 110 */       Projectile projectile = (Projectile)entity;
/* 111 */       ProjectileSource remover = projectile.getShooter();
/* 112 */       if (remover instanceof Entity && remover != entity) {
/* 113 */         entity = (Entity)remover; continue;
/*     */       } 
/* 115 */       return entity;
/*     */     } 
/*     */ 
/*     */     
/* 119 */     return entity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isHostile(Entity entity) {
/* 129 */     return (entity instanceof org.bukkit.entity.Monster || entity instanceof org.bukkit.entity.Slime || entity instanceof org.bukkit.entity.Flying || entity instanceof org.bukkit.entity.EnderDragon);
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
/*     */   public static boolean isAmbient(Entity entity) {
/* 142 */     return entity instanceof org.bukkit.entity.Ambient;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isNPC(Entity entity) {
/* 152 */     return entity instanceof org.bukkit.entity.NPC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isNonPlayerCreature(Entity entity) {
/* 163 */     return (entity instanceof org.bukkit.entity.LivingEntity && !(entity instanceof org.bukkit.entity.Player));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isConsideredBuildingIfUsed(Entity entity) {
/* 174 */     return entity instanceof org.bukkit.entity.Hanging;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukki\\util\Entities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */