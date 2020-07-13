/*    */ package com.sk89q.worldguard.session.handler;
/*    */ 
/*    */ import com.sk89q.commandbook.CommandBook;
/*    */ import com.sk89q.commandbook.GodComponent;
/*    */ import com.sk89q.worldguard.protection.flags.StateFlag;
/*    */ import com.sk89q.worldguard.session.Session;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.entity.Player;
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
/*    */ public class GodMode
/*    */   extends Handler
/*    */ {
/*    */   private boolean godMode;
/*    */   
/*    */   public GodMode(Session session) {
/* 35 */     super(session);
/*    */   }
/*    */   
/*    */   public boolean hasGodMode(Player player) {
/* 39 */     if (getPlugin().getGlobalStateManager().hasCommandBookGodMode()) {
/* 40 */       GodComponent god = (GodComponent)CommandBook.inst().getComponentManager().getComponent(GodComponent.class);
/* 41 */       if (god != null) {
/* 42 */         return god.hasGodMode(player);
/*    */       }
/*    */     } 
/*    */     
/* 46 */     return this.godMode;
/*    */   }
/*    */   
/*    */   public void setGodMode(Player player, boolean godMode) {
/* 50 */     if (getPlugin().getGlobalStateManager().hasCommandBookGodMode()) {
/* 51 */       GodComponent god = (GodComponent)CommandBook.inst().getComponentManager().getComponent(GodComponent.class);
/* 52 */       if (god != null) {
/* 53 */         god.enableGodMode(player);
/*    */       }
/*    */     } 
/*    */     
/* 57 */     this.godMode = godMode;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public StateFlag.State getInvincibility(Player player) {
/* 63 */     return hasGodMode(player) ? StateFlag.State.ALLOW : null;
/*    */   }
/*    */   
/*    */   public static boolean set(Player player, Session session, boolean value) {
/* 67 */     GodMode godMode = (GodMode)session.getHandler(GodMode.class);
/* 68 */     if (godMode != null) {
/* 69 */       godMode.setGodMode(player, value);
/* 70 */       return true;
/*    */     } 
/* 72 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\session\handler\GodMode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */