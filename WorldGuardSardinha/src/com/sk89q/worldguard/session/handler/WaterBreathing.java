/*    */ package com.sk89q.worldguard.session.handler;
/*    */ 
/*    */ import com.sk89q.worldguard.session.Session;
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
/*    */ public class WaterBreathing
/*    */   extends Handler
/*    */ {
/*    */   public boolean waterBreathing;
/*    */   
/*    */   public WaterBreathing(Session session) {
/* 30 */     super(session);
/*    */   }
/*    */   
/*    */   public boolean hasWaterBreathing() {
/* 34 */     return this.waterBreathing;
/*    */   }
/*    */   
/*    */   public void setWaterBreathing(boolean waterBreathing) {
/* 38 */     this.waterBreathing = waterBreathing;
/*    */   }
/*    */   
/*    */   public static boolean set(Player player, Session session, boolean value) {
/* 42 */     WaterBreathing waterBreathing = (WaterBreathing)session.getHandler(WaterBreathing.class);
/* 43 */     if (waterBreathing != null) {
/* 44 */       waterBreathing.setWaterBreathing(value);
/* 45 */       return true;
/*    */     } 
/* 47 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\session\handler\WaterBreathing.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */