/*    */ package com.sk89q.worldguard.protection.flags;
/*    */ 
/*    */ import com.sk89q.worldedit.LocalWorld;
/*    */ import com.sk89q.worldedit.Location;
/*    */ import com.sk89q.worldedit.Vector;
/*    */ import com.sk89q.worldedit.bukkit.BukkitUtil;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.Bukkit;
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
/*    */ class LazyLocation
/*    */   extends Location
/*    */ {
/*    */   private final String worldName;
/*    */   
/*    */   @Nullable
/*    */   private static LocalWorld findWorld(String worldName) {
/* 39 */     return BukkitUtil.getLocalWorld(Bukkit.getServer().getWorld(worldName));
/*    */   }
/*    */   
/*    */   public LazyLocation(String worldName, Vector position, float yaw, float pitch) {
/* 43 */     super(findWorld(worldName), position, yaw, pitch);
/* 44 */     this.worldName = worldName;
/*    */   }
/*    */   
/*    */   public LazyLocation(String worldName, Vector position) {
/* 48 */     super(findWorld(worldName), position);
/* 49 */     this.worldName = worldName;
/*    */   }
/*    */   
/*    */   public String getWorldName() {
/* 53 */     return this.worldName;
/*    */   }
/*    */   
/*    */   public LazyLocation setAngles(float yaw, float pitch) {
/* 57 */     return new LazyLocation(this.worldName, getPosition(), yaw, pitch);
/*    */   }
/*    */   
/*    */   public LazyLocation setPosition(Vector position) {
/* 61 */     return new LazyLocation(this.worldName, position, getYaw(), getPitch());
/*    */   }
/*    */   
/*    */   public LazyLocation add(Vector other) {
/* 65 */     return setPosition(getPosition().add(other));
/*    */   }
/*    */   
/*    */   public LazyLocation add(double x, double y, double z) {
/* 69 */     return setPosition(getPosition().add(x, y, z));
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\flags\LazyLocation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */