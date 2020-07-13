/*    */ package com.sk89q.worldguard.bukkit;
/*    */ 
/*    */ import com.sk89q.worldedit.Vector;
/*    */ import com.sk89q.worldguard.LocalPlayer;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.OfflinePlayer;
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
/*    */ class BukkitOfflinePlayer
/*    */   extends LocalPlayer
/*    */ {
/*    */   private final OfflinePlayer player;
/*    */   
/*    */   BukkitOfflinePlayer(OfflinePlayer offlinePlayer) {
/* 33 */     this.player = offlinePlayer;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 38 */     return this.player.getName();
/*    */   }
/*    */ 
/*    */   
/*    */   public UUID getUniqueId() {
/* 43 */     return this.player.getUniqueId();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasGroup(String group) {
/* 48 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public Vector getPosition() {
/* 53 */     return Vector.ZERO;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void kick(String msg) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void ban(String msg) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void printRaw(String msg) {}
/*    */ 
/*    */   
/*    */   public String[] getGroups() {
/* 70 */     return new String[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasPermission(String perm) {
/* 75 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\BukkitOfflinePlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */