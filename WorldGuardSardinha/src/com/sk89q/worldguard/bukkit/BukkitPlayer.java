/*     */ package com.sk89q.worldguard.bukkit;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.sk89q.worldedit.Vector;
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.command.CommandSender;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BukkitPlayer
/*     */   extends LocalPlayer
/*     */ {
/*     */   private final WorldGuardPlugin plugin;
/*     */   private final Player player;
/*     */   private final String name;
/*     */   private final boolean silenced;
/*     */   
/*     */   public BukkitPlayer(WorldGuardPlugin plugin, Player player) {
/*  40 */     this(plugin, player, false);
/*     */   }
/*     */   
/*     */   BukkitPlayer(WorldGuardPlugin plugin, Player player, boolean silenced) {
/*  44 */     Preconditions.checkNotNull(plugin);
/*  45 */     Preconditions.checkNotNull(player);
/*     */     
/*  47 */     this.plugin = plugin;
/*  48 */     this.player = player;
/*     */     
/*  50 */     this.name = player.getName();
/*  51 */     this.silenced = silenced;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  56 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public UUID getUniqueId() {
/*  61 */     return this.player.getUniqueId();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasGroup(String group) {
/*  66 */     return this.plugin.inGroup(this.player, group);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector getPosition() {
/*  71 */     Location loc = this.player.getLocation();
/*  72 */     return new Vector(loc.getX(), loc.getY(), loc.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public void kick(String msg) {
/*  77 */     if (!this.silenced) {
/*  78 */       this.player.kickPlayer(msg);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void ban(String msg) {
/*  84 */     if (!this.silenced) {
/*  85 */       this.player.setBanned(true);
/*  86 */       this.player.kickPlayer(msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getGroups() {
/*  92 */     return this.plugin.getGroups(this.player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void printRaw(String msg) {
/*  97 */     if (!this.silenced) {
/*  98 */       this.player.sendMessage(msg);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPermission(String perm) {
/* 104 */     return this.plugin.hasPermission((CommandSender)this.player, perm);
/*     */   }
/*     */   
/*     */   public Player getPlayer() {
/* 108 */     return this.player;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\BukkitPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */