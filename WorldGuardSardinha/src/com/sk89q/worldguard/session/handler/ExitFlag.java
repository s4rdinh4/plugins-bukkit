/*     */ package com.sk89q.worldguard.session.handler;
/*     */ 
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import com.sk89q.worldguard.bukkit.commands.CommandUtils;
/*     */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*     */ import com.sk89q.worldguard.protection.association.RegionAssociable;
/*     */ import com.sk89q.worldguard.protection.flags.DefaultFlag;
/*     */ import com.sk89q.worldguard.protection.flags.Flag;
/*     */ import com.sk89q.worldguard.protection.flags.StateFlag;
/*     */ import com.sk89q.worldguard.session.MoveType;
/*     */ import com.sk89q.worldguard.session.Session;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExitFlag
/*     */   extends FlagValueChangeHandler<StateFlag.State>
/*     */ {
/*     */   private static final long MESSAGE_THRESHOLD = 2000L;
/*     */   private String storedMessage;
/*     */   private long lastMessage;
/*     */   
/*     */   public ExitFlag(Session session) {
/*  40 */     super(session, (Flag<StateFlag.State>)DefaultFlag.EXIT);
/*     */   }
/*     */   
/*     */   private void update(LocalPlayer localPlayer, ApplicableRegionSet set, boolean allowed) {
/*  44 */     if (!allowed) {
/*  45 */       this.storedMessage = (String)set.queryValue((RegionAssociable)localPlayer, (Flag)DefaultFlag.EXIT_DENY_MESSAGE);
/*     */     }
/*     */   }
/*     */   
/*     */   private void sendMessage(Player player) {
/*  50 */     long now = System.currentTimeMillis();
/*     */     
/*  52 */     if (now - this.lastMessage > 2000L && this.storedMessage != null && !this.storedMessage.isEmpty()) {
/*  53 */       player.sendMessage(CommandUtils.replaceColorMacros(this.storedMessage));
/*  54 */       this.lastMessage = now;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onInitialValue(Player player, ApplicableRegionSet set, StateFlag.State value) {
/*  60 */     update(getPlugin().wrapPlayer(player), set, StateFlag.test(new StateFlag.State[] { value }));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean onSetValue(Player player, Location from, Location to, ApplicableRegionSet toSet, StateFlag.State currentValue, StateFlag.State lastValue, MoveType moveType) {
/*  65 */     if (getSession().getManager().hasBypass(player, from.getWorld())) {
/*  66 */       return true;
/*     */     }
/*     */     
/*  69 */     boolean lastAllowed = StateFlag.test(new StateFlag.State[] { lastValue });
/*  70 */     boolean allowed = StateFlag.test(new StateFlag.State[] { currentValue });
/*     */     
/*  72 */     LocalPlayer localPlayer = getPlugin().wrapPlayer(player);
/*     */     
/*  74 */     if (allowed && !lastAllowed) {
/*  75 */       Boolean override = (Boolean)toSet.queryValue((RegionAssociable)localPlayer, (Flag)DefaultFlag.EXIT_OVERRIDE);
/*  76 */       if (override == null || !override.booleanValue()) {
/*  77 */         sendMessage(player);
/*  78 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/*  82 */     update(localPlayer, toSet, allowed);
/*  83 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean onAbsentValue(Player player, Location from, Location to, ApplicableRegionSet toSet, StateFlag.State lastValue, MoveType moveType) {
/*  88 */     if (getSession().getManager().hasBypass(player, from.getWorld())) {
/*  89 */       return true;
/*     */     }
/*     */     
/*  92 */     boolean lastAllowed = StateFlag.test(new StateFlag.State[] { lastValue });
/*     */     
/*  94 */     if (!lastAllowed) {
/*  95 */       Boolean override = (Boolean)toSet.queryValue((RegionAssociable)getPlugin().wrapPlayer(player), (Flag)DefaultFlag.EXIT_OVERRIDE);
/*  96 */       if (override == null || !override.booleanValue()) {
/*  97 */         sendMessage(player);
/*  98 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\session\handler\ExitFlag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */