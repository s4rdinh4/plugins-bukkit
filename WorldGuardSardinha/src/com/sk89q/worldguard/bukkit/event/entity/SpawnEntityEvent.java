/*    */ package com.sk89q.worldguard.bukkit.event.entity;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.google.common.base.Predicate;
/*    */ import com.sk89q.worldguard.bukkit.cause.Cause;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SpawnEntityEvent
/*    */   extends AbstractEntityEvent
/*    */ {
/* 41 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final EntityType effectiveType;
/*    */   
/*    */   public SpawnEntityEvent(@Nullable Event originalEvent, Cause cause, Entity target) {
/* 45 */     super(originalEvent, cause, (Entity)Preconditions.checkNotNull(target));
/* 46 */     this.effectiveType = target.getType();
/*    */   }
/*    */   
/*    */   public SpawnEntityEvent(@Nullable Event originalEvent, Cause cause, Location location, EntityType type) {
/* 50 */     super(originalEvent, cause, location);
/* 51 */     Preconditions.checkNotNull(type);
/* 52 */     this.effectiveType = type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityType getEffectiveType() {
/* 61 */     return this.effectiveType;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 66 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 70 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\event\entity\SpawnEntityEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */