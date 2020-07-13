/*    */ package com.sk89q.worldguard.bukkit.internal;
/*    */ 
/*    */ import com.sk89q.worldguard.blacklist.Blacklist;
/*    */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
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
/*    */ public class BukkitBlacklist
/*    */   extends Blacklist
/*    */ {
/*    */   private WorldGuardPlugin plugin;
/*    */   
/*    */   public BukkitBlacklist(Boolean useAsWhitelist, WorldGuardPlugin plugin) {
/* 30 */     super(useAsWhitelist.booleanValue());
/* 31 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */   
/*    */   public void broadcastNotification(String msg) {
/* 36 */     this.plugin.broadcastNotification(msg);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\internal\BukkitBlacklist.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */