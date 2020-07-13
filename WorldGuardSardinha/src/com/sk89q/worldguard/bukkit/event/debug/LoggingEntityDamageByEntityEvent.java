/*    */ package com.sk89q.worldguard.bukkit.event.debug;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*    */ import org.bukkit.event.entity.EntityDamageEvent;
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
/*    */ public class LoggingEntityDamageByEntityEvent
/*    */   extends EntityDamageByEntityEvent
/*    */   implements CancelLogging
/*    */ {
/* 29 */   private final CancelLogger logger = new CancelLogger();
/*    */   
/*    */   public LoggingEntityDamageByEntityEvent(Entity damager, Entity damagee, EntityDamageEvent.DamageCause cause, double damage) {
/* 32 */     super(damager, damagee, cause, damage);
/*    */   }
/*    */   
/*    */   public List<CancelAttempt> getCancels() {
/* 36 */     return this.logger.getCancels();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 41 */     this.logger.log(isCancelled(), cancel, (new Exception()).getStackTrace());
/* 42 */     super.setCancelled(cancel);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\event\debug\LoggingEntityDamageByEntityEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */