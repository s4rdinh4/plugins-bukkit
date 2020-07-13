/*    */ package com.sk89q.worldguard.session.handler;
/*    */ 
/*    */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*    */ import com.sk89q.worldguard.protection.flags.DefaultFlag;
/*    */ import com.sk89q.worldguard.protection.flags.Flag;
/*    */ import com.sk89q.worldguard.session.MoveType;
/*    */ import com.sk89q.worldguard.session.Session;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.GameMode;
/*    */ import org.bukkit.Location;
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
/*    */ public class GameModeFlag
/*    */   extends FlagValueChangeHandler<GameMode>
/*    */ {
/*    */   private GameMode originalGameMode;
/*    */   private GameMode setGameMode;
/*    */   
/*    */   public GameModeFlag(Session session) {
/* 39 */     super(session, (Flag<GameMode>)DefaultFlag.GAME_MODE);
/*    */   }
/*    */   
/*    */   public GameMode getOriginalGameMode() {
/* 43 */     return this.originalGameMode;
/*    */   }
/*    */   
/*    */   public GameMode getSetGameMode() {
/* 47 */     return this.setGameMode;
/*    */   }
/*    */   
/*    */   private void updateGameMode(Player player, @Nullable GameMode newValue, World world) {
/* 51 */     if (!getSession().getManager().hasBypass(player, world) && newValue != null) {
/* 52 */       if (player.getGameMode() != newValue) {
/* 53 */         this.originalGameMode = player.getGameMode();
/* 54 */         player.setGameMode(newValue);
/* 55 */       } else if (this.originalGameMode == null) {
/* 56 */         this.originalGameMode = player.getServer().getDefaultGameMode();
/*    */       }
/*    */     
/* 59 */     } else if (this.originalGameMode != null) {
/* 60 */       GameMode mode = this.originalGameMode;
/* 61 */       this.originalGameMode = null;
/* 62 */       player.setGameMode(mode);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onInitialValue(Player player, ApplicableRegionSet set, GameMode value) {
/* 69 */     updateGameMode(player, value, player.getWorld());
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean onSetValue(Player player, Location from, Location to, ApplicableRegionSet toSet, GameMode currentValue, GameMode lastValue, MoveType moveType) {
/* 74 */     updateGameMode(player, currentValue, to.getWorld());
/* 75 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean onAbsentValue(Player player, Location from, Location to, ApplicableRegionSet toSet, GameMode lastValue, MoveType moveType) {
/* 80 */     updateGameMode(player, null, player.getWorld());
/* 81 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\session\handler\GameModeFlag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */