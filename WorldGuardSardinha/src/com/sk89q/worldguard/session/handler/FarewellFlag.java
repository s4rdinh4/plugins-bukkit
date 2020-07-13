/*    */ package com.sk89q.worldguard.session.handler;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import com.sk89q.worldguard.bukkit.commands.CommandUtils;
/*    */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*    */ import com.sk89q.worldguard.protection.association.RegionAssociable;
/*    */ import com.sk89q.worldguard.protection.flags.DefaultFlag;
/*    */ import com.sk89q.worldguard.protection.flags.Flag;
/*    */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*    */ import com.sk89q.worldguard.session.MoveType;
/*    */ import com.sk89q.worldguard.session.Session;
/*    */ import java.util.Collections;
/*    */ import java.util.Set;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.command.CommandSender;
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
/*    */ public class FarewellFlag
/*    */   extends Handler
/*    */ {
/* 37 */   private Set<String> lastMessageStack = Collections.emptySet();
/*    */   
/*    */   public FarewellFlag(Session session) {
/* 40 */     super(session);
/*    */   }
/*    */   
/*    */   private Set<String> getMessages(Player player, ApplicableRegionSet set) {
/* 44 */     return Sets.newLinkedHashSet(set.queryAllValues((RegionAssociable)getPlugin().wrapPlayer(player), (Flag)DefaultFlag.FAREWELL_MESSAGE));
/*    */   }
/*    */ 
/*    */   
/*    */   public void initialize(Player player, Location current, ApplicableRegionSet set) {
/* 49 */     this.lastMessageStack = getMessages(player, set);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCrossBoundary(Player player, Location from, Location to, ApplicableRegionSet toSet, Set<ProtectedRegion> entered, Set<ProtectedRegion> exited, MoveType moveType) {
/* 54 */     Set<String> messages = getMessages(player, toSet);
/*    */     
/* 56 */     if (!messages.isEmpty())
/*    */     {
/*    */       
/* 59 */       for (ProtectedRegion region : toSet) {
/* 60 */         String message = (String)region.getFlag((Flag)DefaultFlag.FAREWELL_MESSAGE);
/* 61 */         if (message != null) {
/* 62 */           messages.add(message);
/*    */         }
/*    */       } 
/*    */     }
/*    */     
/* 67 */     for (String message : this.lastMessageStack) {
/* 68 */       if (!messages.contains(message)) {
/* 69 */         String effective = CommandUtils.replaceColorMacros(message);
/* 70 */         effective = getPlugin().replaceMacros((CommandSender)player, effective);
/* 71 */         player.sendMessage(effective.replaceAll("\\\\n", "\n").split("\\n"));
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/* 76 */     this.lastMessageStack = messages;
/*    */     
/* 78 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\session\handler\FarewellFlag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */