/*    */ package com.sk89q.worldguard.session.handler;
/*    */ 
/*    */ import com.sk89q.worldguard.LocalPlayer;
/*    */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*    */ import com.sk89q.worldguard.protection.association.RegionAssociable;
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
/*    */ public class HealFlag
/*    */   extends Handler
/*    */ {
/* 31 */   private long lastHeal = 0L;
/*    */   
/*    */   public HealFlag(Session session) {
/* 34 */     super(session);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(Player player, ApplicableRegionSet set) {
/* 39 */     LocalPlayer localPlayer = getPlugin().wrapPlayer(player);
/*    */     
/* 41 */     if (!getSession().isInvincible(player) && player.getGameMode() != GameMode.CREATIVE) {
/* 42 */       if (player.getHealth() <= 0.0D) {
/*    */         return;
/*    */       }
/*    */       
/* 46 */       long now = System.currentTimeMillis();
/*    */       
/* 48 */       Integer healAmount = (Integer)set.queryValue((RegionAssociable)localPlayer, (Flag)DefaultFlag.HEAL_AMOUNT);
/* 49 */       Integer healDelay = (Integer)set.queryValue((RegionAssociable)localPlayer, (Flag)DefaultFlag.HEAL_DELAY);
/* 50 */       Double minHealth = (Double)set.queryValue((RegionAssociable)localPlayer, (Flag)DefaultFlag.MIN_HEAL);
/* 51 */       Double maxHealth = (Double)set.queryValue((RegionAssociable)localPlayer, (Flag)DefaultFlag.MAX_HEAL);
/*    */       
/* 53 */       if (healAmount == null || healDelay == null || healAmount.intValue() == 0 || healDelay.intValue() < 0) {
/*    */         return;
/*    */       }
/*    */       
/* 57 */       if (minHealth == null) {
/* 58 */         minHealth = Double.valueOf(0.0D);
/*    */       }
/*    */       
/* 61 */       if (maxHealth == null) {
/* 62 */         maxHealth = Double.valueOf(player.getMaxHealth());
/*    */       }
/*    */ 
/*    */       
/* 66 */       minHealth = Double.valueOf(Math.min(player.getMaxHealth(), minHealth.doubleValue()));
/* 67 */       maxHealth = Double.valueOf(Math.min(player.getMaxHealth(), maxHealth.doubleValue()));
/*    */       
/* 69 */       if (player.getHealth() >= maxHealth.doubleValue() && healAmount.intValue() > 0) {
/*    */         return;
/*    */       }
/*    */       
/* 73 */       if (healDelay.intValue() <= 0) {
/* 74 */         player.setHealth(((healAmount.intValue() > 0) ? maxHealth : minHealth).doubleValue());
/* 75 */         this.lastHeal = now;
/* 76 */       } else if (now - this.lastHeal > (healDelay.intValue() * 1000)) {
/*    */         
/* 78 */         player.setHealth(Math.min(maxHealth.doubleValue(), Math.max(minHealth.doubleValue(), player.getHealth() + healAmount.intValue())));
/* 79 */         this.lastHeal = now;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\session\handler\HealFlag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */