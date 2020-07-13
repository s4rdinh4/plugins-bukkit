/*    */ package com.sk89q.worldguard.blacklist.action;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.sk89q.worldguard.blacklist.BlacklistEntry;
/*    */ import com.sk89q.worldguard.blacklist.event.BlacklistEvent;
/*    */ import org.bukkit.ChatColor;
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
/*    */ public class TellAction
/*    */   extends RepeatGuardedAction
/*    */ {
/*    */   private final BlacklistEntry entry;
/*    */   
/*    */   public TellAction(BlacklistEntry entry) {
/* 33 */     Preconditions.checkNotNull(entry);
/* 34 */     this.entry = entry;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ActionResult applyNonRepeated(BlacklistEvent event, boolean silent) {
/* 39 */     if (silent) {
/* 40 */       return ActionResult.INHERIT;
/*    */     }
/*    */     
/* 43 */     String message = this.entry.getMessage();
/*    */     
/* 45 */     if (event.getPlayer() != null) {
/* 46 */       if (message != null) {
/* 47 */         event.getPlayer().printRaw(ChatColor.YELLOW + String.format(message, new Object[] { event.getTarget().getFriendlyName() }) + ".");
/*    */       } else {
/* 49 */         event.getPlayer().printRaw(ChatColor.YELLOW + "You're not allowed to " + event.getDescription() + " " + event
/* 50 */             .getTarget().getFriendlyName() + ".");
/*    */       } 
/*    */     }
/*    */     
/* 54 */     return ActionResult.INHERIT;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\action\TellAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */