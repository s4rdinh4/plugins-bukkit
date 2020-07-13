/*    */ package com.sk89q.worldguard.blacklist.event;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.sk89q.worldedit.Vector;
/*    */ import com.sk89q.worldguard.LocalPlayer;
/*    */ import com.sk89q.worldguard.blacklist.target.Target;
/*    */ import javax.annotation.Nullable;
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
/*    */ 
/*    */ abstract class AbstractBlacklistEvent
/*    */   implements BlacklistEvent
/*    */ {
/*    */   @Nullable
/*    */   private final LocalPlayer player;
/*    */   private final Vector position;
/*    */   private final Target target;
/*    */   
/*    */   AbstractBlacklistEvent(@Nullable LocalPlayer player, Vector position, Target target) {
/* 45 */     Preconditions.checkNotNull(position);
/* 46 */     Preconditions.checkNotNull(target);
/* 47 */     this.player = player;
/* 48 */     this.position = position;
/* 49 */     this.target = target;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public LocalPlayer getPlayer() {
/* 55 */     return this.player;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getCauseName() {
/* 60 */     return (this.player != null) ? this.player.getName() : this.position.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public Vector getPosition() {
/* 65 */     return this.position;
/*    */   }
/*    */ 
/*    */   
/*    */   public Target getTarget() {
/* 70 */     return this.target;
/*    */   }
/*    */   
/*    */   protected String getPlayerName() {
/* 74 */     return (this.player == null) ? "(unknown)" : this.player.getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\event\AbstractBlacklistEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */