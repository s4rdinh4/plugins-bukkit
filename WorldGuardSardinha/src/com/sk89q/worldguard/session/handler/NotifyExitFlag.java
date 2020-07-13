/*    */ package com.sk89q.worldguard.session.handler;
/*    */ 
/*    */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*    */ import com.sk89q.worldguard.protection.flags.DefaultFlag;
/*    */ import com.sk89q.worldguard.protection.flags.Flag;
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
/*    */ public class NotifyExitFlag
/*    */   extends FlagValueChangeHandler<Boolean>
/*    */ {
/* 32 */   private Boolean notifiedForLeave = Boolean.valueOf(false);
/*    */   
/*    */   public NotifyExitFlag(Session session) {
/* 35 */     super(session, (Flag<Boolean>)DefaultFlag.NOTIFY_LEAVE);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onInitialValue(Player player, ApplicableRegionSet set, Boolean value) {}
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean onSetValue(Player player, Location from, Location to, ApplicableRegionSet toSet, Boolean currentValue, Boolean lastValue, MoveType moveType) {
/* 45 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean onAbsentValue(Player player, Location from, Location to, ApplicableRegionSet toSet, Boolean lastValue, MoveType moveType) {
/* 50 */     getPlugin().broadcastNotification(ChatColor.GRAY + "WG: " + ChatColor.LIGHT_PURPLE + player
/* 51 */         .getName() + ChatColor.GOLD + " left NOTIFY region");
/*    */     
/* 53 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\session\handler\NotifyExitFlag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */