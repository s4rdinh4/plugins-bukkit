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
/*    */ import java.util.Collection;
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
/*    */ public class GreetingFlag
/*    */   extends Handler
/*    */ {
/* 38 */   private Set<String> lastMessageStack = Collections.emptySet();
/*    */   
/*    */   public GreetingFlag(Session session) {
/* 41 */     super(session);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCrossBoundary(Player player, Location from, Location to, ApplicableRegionSet toSet, Set<ProtectedRegion> entered, Set<ProtectedRegion> exited, MoveType moveType) {
/* 46 */     Collection<String> messages = toSet.queryAllValues((RegionAssociable)getPlugin().wrapPlayer(player), (Flag)DefaultFlag.GREET_MESSAGE);
/*    */     
/* 48 */     for (String message : messages) {
/* 49 */       if (!this.lastMessageStack.contains(message)) {
/* 50 */         String effective = CommandUtils.replaceColorMacros(message);
/* 51 */         effective = getPlugin().replaceMacros((CommandSender)player, effective);
/* 52 */         player.sendMessage(effective.replaceAll("\\\\n", "\n").split("\\n"));
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/* 57 */     this.lastMessageStack = Sets.newHashSet(messages);
/*    */     
/* 59 */     if (!this.lastMessageStack.isEmpty())
/*    */     {
/*    */       
/* 62 */       for (ProtectedRegion region : toSet) {
/* 63 */         String message = (String)region.getFlag((Flag)DefaultFlag.GREET_MESSAGE);
/* 64 */         if (message != null) {
/* 65 */           this.lastMessageStack.add(message);
/*    */         }
/*    */       } 
/*    */     }
/*    */     
/* 70 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\session\handler\GreetingFlag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */