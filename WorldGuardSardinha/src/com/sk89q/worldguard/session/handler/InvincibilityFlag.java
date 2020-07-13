/*    */ package com.sk89q.worldguard.session.handler;
/*    */ 
/*    */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*    */ import com.sk89q.worldguard.protection.flags.DefaultFlag;
/*    */ import com.sk89q.worldguard.protection.flags.Flag;
/*    */ import com.sk89q.worldguard.protection.flags.StateFlag;
/*    */ import com.sk89q.worldguard.session.MoveType;
/*    */ import com.sk89q.worldguard.session.Session;
/*    */ import javax.annotation.Nullable;
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
/*    */ 
/*    */ public class InvincibilityFlag
/*    */   extends FlagValueChangeHandler<StateFlag.State>
/*    */ {
/*    */   @Nullable
/*    */   private StateFlag.State invincibility;
/*    */   
/*    */   public InvincibilityFlag(Session session) {
/* 38 */     super(session, (Flag<StateFlag.State>)DefaultFlag.INVINCIBILITY);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onInitialValue(Player player, ApplicableRegionSet set, StateFlag.State value) {
/* 43 */     this.invincibility = value;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean onSetValue(Player player, Location from, Location to, ApplicableRegionSet toSet, StateFlag.State currentValue, StateFlag.State lastValue, MoveType moveType) {
/* 48 */     this.invincibility = currentValue;
/* 49 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean onAbsentValue(Player player, Location from, Location to, ApplicableRegionSet toSet, StateFlag.State lastValue, MoveType moveType) {
/* 54 */     this.invincibility = null;
/* 55 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public StateFlag.State getInvincibility(Player player) {
/* 61 */     if (this.invincibility == StateFlag.State.DENY && getPlugin().hasPermission((CommandSender)player, "worldguard.god.override-regions")) {
/* 62 */       return null;
/*    */     }
/*    */     
/* 65 */     return this.invincibility;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\session\handler\InvincibilityFlag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */