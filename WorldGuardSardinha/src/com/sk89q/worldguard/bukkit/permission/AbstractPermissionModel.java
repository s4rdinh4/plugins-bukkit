/*    */ package com.sk89q.worldguard.bukkit.permission;
/*    */ 
/*    */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*    */ import com.sk89q.worldguard.internal.PermissionModel;
/*    */ import org.bukkit.command.CommandSender;
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
/*    */ abstract class AbstractPermissionModel
/*    */   implements PermissionModel
/*    */ {
/*    */   private final WorldGuardPlugin plugin;
/*    */   private final CommandSender sender;
/*    */   
/*    */   public AbstractPermissionModel(WorldGuardPlugin plugin, CommandSender sender) {
/* 32 */     this.plugin = plugin;
/* 33 */     this.sender = sender;
/*    */   }
/*    */   
/*    */   protected WorldGuardPlugin getPlugin() {
/* 37 */     return this.plugin;
/*    */   }
/*    */   
/*    */   public CommandSender getSender() {
/* 41 */     return this.sender;
/*    */   }
/*    */   
/*    */   protected boolean hasPluginPermission(String permission) {
/* 45 */     return this.plugin.hasPermission(getSender(), "worldguard." + permission);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\permission\AbstractPermissionModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */