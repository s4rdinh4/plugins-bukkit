/*     */ package br.com.sgcraft.arenax1.arena;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class Arena
/*     */ {
/*     */   private final String name;
/*     */   private final String world;
/*     */   private Location pos1;
/*     */   private Location pos2;
/*     */   private List<Player> players;
/*     */   private Player winner;
/*     */   private Player loser;
/*     */   private int remainingTime;
/*     */   private boolean isOcurring;
/*     */   private boolean isWaitingToStart;
/*     */   
/*     */   public Arena(String name, String world, Location pos1, Location pos2) {
/*  30 */     this.name = name;
/*  31 */     this.world = world;
/*  32 */     this.pos1 = pos1;
/*  33 */     this.pos2 = pos2;
/*  34 */     this.remainingTime = 0;
/*  35 */     this.players = new ArrayList<>();
/*  36 */     this.isOcurring = false;
/*  37 */     this.isWaitingToStart = false;
/*  38 */     this.loser = null;
/*  39 */     this.winner = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  44 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public Player getWinner() {
/*  49 */     return this.winner;
/*     */   }
/*     */ 
/*     */   
/*     */   public Player getLoser() {
/*  54 */     return this.loser;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getWorld() {
/*  59 */     return this.world;
/*     */   }
/*     */ 
/*     */   
/*     */   public Location getPos1() {
/*  64 */     return this.pos1;
/*     */   }
/*     */ 
/*     */   
/*     */   public Location getPos2() {
/*  69 */     return this.pos2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRemainingTime() {
/*  74 */     return this.remainingTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnded() {
/*  79 */     this.remainingTime--;
/*  80 */     return (this.remainingTime <= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOcurring() {
/*  85 */     return this.isOcurring;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWaitinigToStart() {
/*  90 */     return this.isWaitingToStart;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInArena(Player player, boolean remove) {
/*  95 */     Iterator<Player> i = this.players.iterator();
/*     */     
/*  97 */     Player winnern = null;
/*  98 */     boolean is = false;
/*     */     
/* 100 */     while (i.hasNext()) {
/*     */       
/* 102 */       Player loop = i.next();
/* 103 */       if (player.equals(loop)) {
/*     */         
/* 105 */         is = true;
/* 106 */         if (remove)
/*     */         {
/* 108 */           i.remove();
/*     */         }
/*     */         continue;
/*     */       } 
/* 112 */       winnern = loop;
/*     */     } 
/*     */ 
/*     */     
/* 116 */     if (remove && is)
/*     */     {
/* 118 */       this.winner = winnern;
/*     */     }
/*     */     
/* 121 */     return is;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInArena(Player player) {
/* 126 */     return isInArena(player, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCompleted() {
/* 131 */     return (this.pos1 != null && this.pos2 != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Player> getPlayers() {
/* 136 */     return this.players;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWinner(Player player) {
/* 141 */     this.winner = player;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLoser(Player player) {
/* 146 */     this.loser = player;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setPos1(Location pos1) {
/* 151 */     this.pos1 = pos1;
/* 152 */     return isCompleted();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setPos2(Location pos2) {
/* 157 */     this.pos2 = pos2;
/* 158 */     return isCompleted();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOcurring(boolean ocurring) {
/* 163 */     this.isOcurring = ocurring;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWaitinigToStart(boolean waiting) {
/* 168 */     this.isWaitingToStart = waiting;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRemainingTime(int time) {
/* 173 */     this.remainingTime = time;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addPlayer(Player player) {
/* 178 */     this.players.add(player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removePlayer(Player player) {
/* 183 */     isInArena(player, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removePlayers() {
/* 188 */     this.players.clear();
/*     */   }
/*     */ }

