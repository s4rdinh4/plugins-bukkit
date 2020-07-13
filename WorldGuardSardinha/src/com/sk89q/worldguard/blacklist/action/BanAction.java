/*    */ package com.sk89q.worldguard.blacklist.action;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.sk89q.worldguard.blacklist.BlacklistEntry;
/*    */ import com.sk89q.worldguard.blacklist.event.BlacklistEvent;
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
/*    */ public class BanAction
/*    */   implements Action
/*    */ {
/*    */   private final BlacklistEntry entry;
/*    */   
/*    */   public BanAction(BlacklistEntry entry) {
/* 32 */     Preconditions.checkNotNull(entry);
/* 33 */     this.entry = entry;
/*    */   }
/*    */ 
/*    */   
/*    */   public ActionResult apply(BlacklistEvent event, boolean silent, boolean repeating, boolean forceRepeat) {
/* 38 */     if (silent) {
/* 39 */       return ActionResult.INHERIT;
/*    */     }
/*    */     
/* 42 */     if (event.getPlayer() != null) {
/* 43 */       String message = this.entry.getMessage();
/*    */       
/* 45 */       if (message != null) {
/* 46 */         event.getPlayer().ban("Banned: " + String.format(message, new Object[] { event.getTarget().getFriendlyName() }));
/*    */       } else {
/* 48 */         event.getPlayer().ban("Banned: You can't " + event.getDescription() + " " + event.getTarget().getFriendlyName());
/*    */       } 
/*    */     } 
/*    */     
/* 52 */     return ActionResult.INHERIT;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\action\BanAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */