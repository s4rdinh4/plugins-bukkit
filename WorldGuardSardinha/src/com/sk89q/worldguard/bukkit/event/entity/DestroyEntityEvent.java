/*    */ package com.sk89q.worldguard.bukkit.event.entity;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.google.common.base.Predicate;
/*    */ import com.sk89q.worldguard.bukkit.cause.Cause;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.entity.Entity;
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
/*    */ public class DestroyEntityEvent
/*    */   extends AbstractEntityEvent
/*    */ {
/* 40 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   public DestroyEntityEvent(@Nullable Event originalEvent, Cause cause, Entity target) {
/* 43 */     super(originalEvent, cause, (Entity)Preconditions.checkNotNull(target));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Entity getEntity() {
/* 49 */     return super.getEntity();
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 54 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 58 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\event\entity\DestroyEntityEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */