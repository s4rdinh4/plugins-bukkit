/*    */ package com.sk89q.worldguard.session.handler;
/*    */ 
/*    */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*    */ import com.sk89q.worldguard.protection.flags.DefaultFlag;
/*    */ import com.sk89q.worldguard.protection.flags.Flag;
/*    */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*    */ import com.sk89q.worldguard.session.MoveType;
/*    */ import com.sk89q.worldguard.session.Session;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Location;
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
/*    */ public class NotifyEntryFlag
/*    */   extends FlagValueChangeHandler<Boolean>
/*    */ {
/*    */   public NotifyEntryFlag(Session session) {
/* 34 */     super(session, (Flag<Boolean>)DefaultFlag.NOTIFY_ENTER);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onInitialValue(Player player, ApplicableRegionSet set, Boolean value) {}
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean onSetValue(Player player, Location from, Location to, ApplicableRegionSet toSet, Boolean currentValue, Boolean lastValue, MoveType moveType) {
/* 44 */     StringBuilder regionList = new StringBuilder();
/*    */     
/* 46 */     for (ProtectedRegion region : toSet) {
/* 47 */       if (regionList.length() != 0) {
/* 48 */         regionList.append(", ");
/*    */       }
/*    */       
/* 51 */       regionList.append(region.getId());
/*    */     } 
/*    */     
/* 54 */     getPlugin().broadcastNotification(ChatColor.GRAY + "WG: " + ChatColor.LIGHT_PURPLE + player
/* 55 */         .getName() + ChatColor.GOLD + " entered NOTIFY region: " + ChatColor.WHITE + regionList);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 60 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean onAbsentValue(Player player, Location from, Location to, ApplicableRegionSet toSet, Boolean lastValue, MoveType moveType) {
/* 65 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\session\handler\NotifyEntryFlag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */