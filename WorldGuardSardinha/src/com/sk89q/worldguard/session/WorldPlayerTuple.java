/*    */ package com.sk89q.worldguard.session;
/*    */ 
/*    */ import org.bukkit.World;
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
/*    */ class WorldPlayerTuple
/*    */ {
/*    */   final World world;
/*    */   final Player player;
/*    */   
/*    */   WorldPlayerTuple(World world, Player player) {
/* 31 */     this.world = world;
/* 32 */     this.player = player;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 37 */     if (this == o) return true; 
/* 38 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 40 */     WorldPlayerTuple that = (WorldPlayerTuple)o;
/*    */     
/* 42 */     if (!this.player.equals(that.player)) return false; 
/* 43 */     if (!this.world.equals(that.world)) return false;
/*    */     
/* 45 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 50 */     int result = this.world.hashCode();
/* 51 */     result = 31 * result + this.player.hashCode();
/* 52 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\session\WorldPlayerTuple.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */