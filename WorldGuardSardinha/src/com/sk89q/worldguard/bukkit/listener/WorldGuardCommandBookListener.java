/*    */ package com.sk89q.worldguard.bukkit.listener;
/*    */ 
/*    */ import com.sk89q.commandbook.InfoComponent;
/*    */ import com.sk89q.worldguard.LocalPlayer;
/*    */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*    */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*    */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
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
/*    */ public class WorldGuardCommandBookListener
/*    */   implements Listener
/*    */ {
/*    */   private final WorldGuardPlugin plugin;
/*    */   
/*    */   public WorldGuardCommandBookListener(WorldGuardPlugin plugin) {
/* 38 */     this.plugin = plugin;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPlayerWhois(InfoComponent.PlayerWhoisEvent event) {
/* 43 */     if (event.getPlayer() instanceof Player) {
/* 44 */       Player player = (Player)event.getPlayer();
/* 45 */       LocalPlayer localPlayer = this.plugin.wrapPlayer(player);
/* 46 */       if ((this.plugin.getGlobalStateManager().get(player.getWorld())).useRegions) {
/* 47 */         ApplicableRegionSet regions = this.plugin.getRegionContainer().createQuery().getApplicableRegions(player.getLocation());
/*    */ 
/*    */         
/* 50 */         StringBuilder regionStr = new StringBuilder();
/* 51 */         boolean first = true;
/*    */         
/* 53 */         for (ProtectedRegion region : regions) {
/* 54 */           if (!first) {
/* 55 */             regionStr.append(", ");
/*    */           }
/*    */           
/* 58 */           if (region.isOwner(localPlayer)) {
/* 59 */             regionStr.append("+");
/* 60 */           } else if (region.isMemberOnly(localPlayer)) {
/* 61 */             regionStr.append("-");
/*    */           } 
/*    */           
/* 64 */           regionStr.append(region.getId());
/*    */           
/* 66 */           first = false;
/*    */         } 
/*    */         
/* 69 */         if (regions.size() > 0) {
/* 70 */           event.addWhoisInformation("Current Regions", regionStr);
/*    */         }
/* 72 */         event.addWhoisInformation("Can build", Boolean.valueOf(regions.canBuild(localPlayer)));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\WorldGuardCommandBookListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */