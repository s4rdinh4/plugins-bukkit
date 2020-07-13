/*     */ package com.sk89q.worldguard.bukkit.event.entity;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.sk89q.worldguard.bukkit.cause.Cause;
/*     */ import com.sk89q.worldguard.bukkit.event.DelegateEvent;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Event;
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
/*     */ abstract class AbstractEntityEvent
/*     */   extends DelegateEvent
/*     */ {
/*     */   private final Location target;
/*     */   @Nullable
/*     */   private final Entity entity;
/*     */   
/*     */   protected AbstractEntityEvent(@Nullable Event originalEvent, Cause cause, Entity entity) {
/*  45 */     super(originalEvent, cause);
/*  46 */     Preconditions.checkNotNull(entity);
/*  47 */     this.target = entity.getLocation();
/*  48 */     this.entity = entity;
/*     */   }
/*     */   
/*     */   protected AbstractEntityEvent(@Nullable Event originalEvent, Cause cause, Location target) {
/*  52 */     super(originalEvent, cause);
/*  53 */     Preconditions.checkNotNull(target);
/*  54 */     this.target = target;
/*  55 */     this.entity = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld() {
/*  64 */     return this.target.getWorld();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Location getTarget() {
/*  73 */     return this.target;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getEntity() {
/*  84 */     return this.entity;
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
/*     */   public boolean filter(Predicate<Location> predicate, boolean cancelEventOnFalse) {
/*  97 */     if (!isCancelled() && 
/*  98 */       !predicate.apply(getTarget())) {
/*  99 */       setCancelled(true);
/*     */     }
/*     */ 
/*     */     
/* 103 */     return isCancelled();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\event\entity\AbstractEntityEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */