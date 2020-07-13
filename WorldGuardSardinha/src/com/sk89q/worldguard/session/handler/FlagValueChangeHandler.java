/*    */ package com.sk89q.worldguard.session.handler;
/*    */ 
/*    */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*    */ import com.sk89q.worldguard.protection.association.RegionAssociable;
/*    */ import com.sk89q.worldguard.protection.flags.Flag;
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
/*    */ 
/*    */ 
/*    */ public abstract class FlagValueChangeHandler<T>
/*    */   extends Handler
/*    */ {
/*    */   private final Flag<T> flag;
/*    */   private T lastValue;
/*    */   
/*    */   protected FlagValueChangeHandler(Session session, Flag<T> flag) {
/* 38 */     super(session);
/* 39 */     this.flag = flag;
/*    */   }
/*    */ 
/*    */   
/*    */   public final void initialize(Player player, Location current, ApplicableRegionSet set) {
/* 44 */     this.lastValue = (T)set.queryValue((RegionAssociable)getPlugin().wrapPlayer(player), this.flag);
/* 45 */     onInitialValue(player, set, this.lastValue);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCrossBoundary(Player player, Location from, Location to, ApplicableRegionSet toSet, Set<ProtectedRegion> entered, Set<ProtectedRegion> exited, MoveType moveType) {
/* 50 */     T currentValue = (T)toSet.queryValue((RegionAssociable)getPlugin().wrapPlayer(player), this.flag);
/* 51 */     boolean allowed = true;
/*    */     
/* 53 */     if (currentValue == null && this.lastValue != null) {
/* 54 */       allowed = onAbsentValue(player, from, to, toSet, this.lastValue, moveType);
/* 55 */     } else if (currentValue != null && currentValue != this.lastValue) {
/* 56 */       allowed = onSetValue(player, from, to, toSet, currentValue, this.lastValue, moveType);
/*    */     } 
/*    */     
/* 59 */     if (allowed) {
/* 60 */       this.lastValue = currentValue;
/*    */     }
/*    */     
/* 63 */     return allowed;
/*    */   }
/*    */   
/*    */   protected abstract void onInitialValue(Player paramPlayer, ApplicableRegionSet paramApplicableRegionSet, T paramT);
/*    */   
/*    */   protected abstract boolean onSetValue(Player paramPlayer, Location paramLocation1, Location paramLocation2, ApplicableRegionSet paramApplicableRegionSet, T paramT1, T paramT2, MoveType paramMoveType);
/*    */   
/*    */   protected abstract boolean onAbsentValue(Player paramPlayer, Location paramLocation1, Location paramLocation2, ApplicableRegionSet paramApplicableRegionSet, T paramT, MoveType paramMoveType);
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\session\handler\FlagValueChangeHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */