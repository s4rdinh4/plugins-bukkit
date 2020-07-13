/*    */ package com.sk89q.worldguard.util.profile.resolver;
/*    */ 
/*    */ import com.sk89q.worldguard.util.profile.Profile;
/*    */ import java.io.IOException;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.Bukkit;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BukkitPlayerService
/*    */   extends SingleRequestService
/*    */ {
/* 34 */   private static final BukkitPlayerService INSTANCE = new BukkitPlayerService();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getIdealRequestLimit() {
/* 41 */     return Integer.MAX_VALUE;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Profile findByName(String name) throws IOException, InterruptedException {
/* 47 */     Player player = Bukkit.getServer().getPlayerExact(name);
/* 48 */     if (player != null) {
/* 49 */       return new Profile(player.getUniqueId(), player.getName());
/*    */     }
/* 51 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static BukkitPlayerService getInstance() {
/* 56 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\profile\resolver\BukkitPlayerService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */