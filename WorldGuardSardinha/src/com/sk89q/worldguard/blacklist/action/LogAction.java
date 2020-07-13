/*    */ package com.sk89q.worldguard.blacklist.action;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.sk89q.worldguard.blacklist.Blacklist;
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
/*    */ public class LogAction
/*    */   extends RepeatGuardedAction
/*    */ {
/*    */   private final Blacklist blacklist;
/*    */   private final BlacklistEntry entry;
/*    */   
/*    */   public LogAction(Blacklist blacklist, BlacklistEntry entry) {
/* 34 */     Preconditions.checkNotNull(blacklist);
/* 35 */     Preconditions.checkNotNull(entry);
/* 36 */     this.blacklist = blacklist;
/* 37 */     this.entry = entry;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ActionResult applyNonRepeated(BlacklistEvent event, boolean silent) {
/* 42 */     if (silent) {
/* 43 */       return ActionResult.INHERIT;
/*    */     }
/*    */     
/* 46 */     this.blacklist.getLogger().logEvent(event, this.entry.getComment());
/*    */     
/* 48 */     return ActionResult.INHERIT;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\action\LogAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */