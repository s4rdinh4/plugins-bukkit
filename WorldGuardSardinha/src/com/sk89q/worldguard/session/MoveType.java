/*    */ package com.sk89q.worldguard.session;
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
/*    */ 
/*    */ 
/*    */ public enum MoveType
/*    */ {
/* 31 */   RESPAWN(false),
/* 32 */   EMBARK(true),
/* 33 */   MOVE(true),
/* 34 */   TELEPORT(true),
/* 35 */   RIDE(true),
/* 36 */   OTHER_NON_CANCELLABLE(false),
/* 37 */   OTHER_CANCELLABLE(true);
/*    */   
/*    */   private final boolean cancellable;
/*    */   
/*    */   MoveType(boolean cancellable) {
/* 42 */     this.cancellable = cancellable;
/*    */   }
/*    */   
/*    */   public boolean isCancellable() {
/* 46 */     return this.cancellable;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\session\MoveType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */