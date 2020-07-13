/*    */ package com.sk89q.worldguard.session.handler;
/*    */ 
/*    */ import com.sk89q.worldguard.LocalPlayer;
/*    */ import com.sk89q.worldguard.bukkit.commands.CommandUtils;
/*    */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*    */ import com.sk89q.worldguard.protection.association.RegionAssociable;
/*    */ import com.sk89q.worldguard.protection.flags.DefaultFlag;
/*    */ import com.sk89q.worldguard.protection.flags.Flag;
/*    */ import com.sk89q.worldguard.protection.flags.StateFlag;
/*    */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*    */ import com.sk89q.worldguard.session.MoveType;
/*    */ import com.sk89q.worldguard.session.Session;
/*    */ import java.util.Set;
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
/*    */ public class EntryFlag
/*    */   extends Handler
/*    */ {
/*    */   private static final long MESSAGE_THRESHOLD = 2000L;
/*    */   private long lastMessage;
/*    */   
/*    */   public EntryFlag(Session session) {
/* 40 */     super(session);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCrossBoundary(Player player, Location from, Location to, ApplicableRegionSet toSet, Set<ProtectedRegion> entered, Set<ProtectedRegion> exited, MoveType moveType) {
/* 45 */     LocalPlayer localPlayer = getPlugin().wrapPlayer(player);
/* 46 */     boolean allowed = toSet.testState((RegionAssociable)localPlayer, new StateFlag[] { DefaultFlag.ENTRY });
/*    */     
/* 48 */     if (!getSession().getManager().hasBypass(player, to.getWorld()) && !allowed && moveType.isCancellable()) {
/* 49 */       String message = (String)toSet.queryValue((RegionAssociable)localPlayer, (Flag)DefaultFlag.ENTRY_DENY_MESSAGE);
/* 50 */       long now = System.currentTimeMillis();
/*    */       
/* 52 */       if (now - this.lastMessage > 2000L && message != null && !message.isEmpty()) {
/* 53 */         player.sendMessage(CommandUtils.replaceColorMacros(message));
/* 54 */         this.lastMessage = now;
/*    */       } 
/*    */       
/* 57 */       return false;
/*    */     } 
/* 59 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\session\handler\EntryFlag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */