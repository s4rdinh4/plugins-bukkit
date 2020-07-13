/*    */ package com.sk89q.worldguard.protection.events;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
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
/*    */ public class DisallowedPVPEvent
/*    */   extends Event
/*    */   implements Cancellable
/*    */ {
/* 34 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   private boolean cancelled = false;
/*    */   private final Player attacker;
/*    */   private final Player defender;
/*    */   private final Event event;
/*    */   
/*    */   public DisallowedPVPEvent(Player attacker, Player defender, Event event) {
/* 42 */     this.attacker = attacker;
/* 43 */     this.defender = defender;
/* 44 */     this.event = event;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 48 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancelled) {
/* 52 */     this.cancelled = cancelled;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Player getAttacker() {
/* 59 */     return this.attacker;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Player getDefender() {
/* 66 */     return this.defender;
/*    */   }
/*    */   
/*    */   public Event getCause() {
/* 70 */     return this.event;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 75 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 79 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\events\DisallowedPVPEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */