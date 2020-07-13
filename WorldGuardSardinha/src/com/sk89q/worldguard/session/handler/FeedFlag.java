/*    */ package com.sk89q.worldguard.session.handler;
/*    */ 
/*    */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*    */ import com.sk89q.worldguard.protection.flags.DefaultFlag;
/*    */ import com.sk89q.worldguard.protection.flags.Flag;
/*    */ import com.sk89q.worldguard.session.Session;
/*    */ import org.bukkit.GameMode;
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
/*    */ public class FeedFlag
/*    */   extends Handler
/*    */ {
/* 30 */   private long lastFeed = 0L;
/*    */   
/*    */   public FeedFlag(Session session) {
/* 33 */     super(session);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(Player player, ApplicableRegionSet set) {
/* 38 */     if (!getSession().isInvincible(player) && player.getGameMode() != GameMode.CREATIVE) {
/* 39 */       long now = System.currentTimeMillis();
/*    */       
/* 41 */       Integer feedAmount = (Integer)set.getFlag((Flag)DefaultFlag.FEED_AMOUNT);
/* 42 */       Integer feedDelay = (Integer)set.getFlag((Flag)DefaultFlag.FEED_DELAY);
/* 43 */       Integer minHunger = (Integer)set.getFlag((Flag)DefaultFlag.MIN_FOOD);
/* 44 */       Integer maxHunger = (Integer)set.getFlag((Flag)DefaultFlag.MAX_FOOD);
/*    */       
/* 46 */       if (feedAmount == null || feedDelay == null || feedAmount.intValue() == 0 || feedDelay.intValue() < 0) {
/*    */         return;
/*    */       }
/* 49 */       if (minHunger == null) {
/* 50 */         minHunger = Integer.valueOf(0);
/*    */       }
/* 52 */       if (maxHunger == null) {
/* 53 */         maxHunger = Integer.valueOf(20);
/*    */       }
/*    */ 
/*    */       
/* 57 */       minHunger = Integer.valueOf(Math.min(20, minHunger.intValue()));
/* 58 */       maxHunger = Integer.valueOf(Math.min(20, maxHunger.intValue()));
/*    */       
/* 60 */       if (player.getFoodLevel() >= maxHunger.intValue() && feedAmount.intValue() > 0) {
/*    */         return;
/*    */       }
/*    */       
/* 64 */       if (feedDelay.intValue() <= 0) {
/* 65 */         player.setFoodLevel(((feedAmount.intValue() > 0) ? maxHunger : minHunger).intValue());
/* 66 */         player.setSaturation(player.getFoodLevel());
/* 67 */         this.lastFeed = now;
/* 68 */       } else if (now - this.lastFeed > (feedDelay.intValue() * 1000)) {
/*    */         
/* 70 */         player.setFoodLevel(Math.min(maxHunger.intValue(), Math.max(minHunger.intValue(), player.getFoodLevel() + feedAmount.intValue())));
/* 71 */         player.setSaturation(player.getFoodLevel());
/* 72 */         this.lastFeed = now;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\session\handler\FeedFlag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */