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
/*    */ public final class DenyAction
/*    */   implements Action
/*    */ {
/* 26 */   private static final DenyAction INSTANCE = new DenyAction();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ActionResult apply(BlacklistEvent event, boolean silent, boolean repeating, boolean forceRepeat) {
/* 33 */     if (silent) {
/* 34 */       return ActionResult.DENY_OVERRIDE;
/*    */     }
/*    */     
/* 37 */     return ActionResult.DENY;
/*    */   }
/*    */   
/*    */   public static DenyAction getInstance() {
/* 41 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\action\DenyAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */