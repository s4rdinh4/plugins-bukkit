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
/*    */ public final class AllowAction
/*    */   implements Action
/*    */ {
/* 26 */   private static final AllowAction INSTANCE = new AllowAction();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ActionResult apply(BlacklistEvent event, boolean silent, boolean repeating, boolean forceRepeat) {
/* 33 */     if (silent) {
/* 34 */       return ActionResult.ALLOW_OVERRIDE;
/*    */     }
/*    */     
/* 37 */     return ActionResult.ALLOW;
/*    */   }
/*    */   
/*    */   public static AllowAction getInstance() {
/* 41 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\action\AllowAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */