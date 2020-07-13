/*    */ package com.sk89q.worldguard.bukkit.listener;
/*    */ 
/*    */ import com.sk89q.worldguard.bukkit.ConfigurationManager;
/*    */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*    */ import com.sk89q.worldguard.bukkit.event.player.ProcessPlayerEvent;
/*    */ import com.sk89q.worldguard.session.Session;
/*    */ import com.sk89q.worldguard.session.handler.GodMode;
/*    */ import com.sk89q.worldguard.session.handler.WaterBreathing;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
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
/*    */ public class PlayerModesListener
/*    */   extends AbstractListener
/*    */ {
/* 36 */   private static final Logger log = Logger.getLogger(PlayerModesListener.class.getCanonicalName());
/*    */ 
/*    */   
/*    */   private static final String INVINCIBLE_PERMISSION = "worldguard.auto-invincible";
/*    */ 
/*    */   
/*    */   private static final String INVINCIBLE_GROUP = "wg-invincible";
/*    */   
/*    */   private static final String AMPHIBIOUS_GROUP = "wg-amphibious";
/*    */ 
/*    */   
/*    */   public PlayerModesListener(WorldGuardPlugin plugin) {
/* 48 */     super(plugin);
/*    */   }
/*    */   
/*    */   private boolean hasGodModeGroup(Player player) {
/* 52 */     return ((getConfig()).useGodGroup && getPlugin().inGroup(player, "wg-invincible"));
/*    */   }
/*    */   
/*    */   private boolean hasGodModePermission(Player player) {
/* 56 */     return ((getConfig()).useGodPermission && getPlugin().hasPermission((CommandSender)player, "worldguard.auto-invincible"));
/*    */   }
/*    */   
/*    */   private boolean hasAmphibiousGroup(Player player) {
/* 60 */     return ((getConfig()).useAmphibiousGroup && getPlugin().inGroup(player, "wg-amphibious"));
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onProcessPlayer(ProcessPlayerEvent event) {
/* 65 */     ConfigurationManager config = getConfig();
/* 66 */     Player player = event.getPlayer();
/* 67 */     Session session = getPlugin().getSessionManager().get(player);
/*    */     
/* 69 */     if ((hasGodModeGroup(player) || hasGodModePermission(player)) && 
/* 70 */       GodMode.set(player, session, true)) {
/* 71 */       log.log(Level.INFO, "Enabled auto-god mode for " + player.getName());
/*    */     }
/*    */ 
/*    */     
/* 75 */     if (hasAmphibiousGroup(player) && 
/* 76 */       WaterBreathing.set(player, session, true))
/* 77 */       log.log(Level.INFO, "Enabled water breathing mode for " + player.getName() + " (player is in group 'wg-amphibious')"); 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\PlayerModesListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */