/*    */ package com.sk89q.worldguard.blacklist.action;
/*    */ 
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
/*    */ public abstract class RepeatGuardedAction
/*    */   implements Action
/*    */ {
/*    */   public final ActionResult apply(BlacklistEvent event, boolean silent, boolean repeating, boolean forceRepeat) {
/* 28 */     if (!repeating || forceRepeat) {
/* 29 */       return applyNonRepeated(event, silent);
/*    */     }
/*    */     
/* 32 */     return ActionResult.INHERIT;
/*    */   }
/*    */   
/*    */   protected abstract ActionResult applyNonRepeated(BlacklistEvent paramBlacklistEvent, boolean paramBoolean);
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\action\RepeatGuardedAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */