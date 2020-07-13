/*     */ package com.sk89q.worldguard.domains;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import com.sk89q.worldguard.util.ChangeTracked;
/*     */ import java.util.Collections;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CopyOnWriteArraySet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerDomain
/*     */   implements Domain, ChangeTracked
/*     */ {
/*  37 */   private final Set<UUID> uniqueIds = new CopyOnWriteArraySet<UUID>();
/*  38 */   private final Set<String> names = new CopyOnWriteArraySet<String>();
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean dirty = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerDomain() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerDomain(PlayerDomain domain) {
/*  53 */     Preconditions.checkNotNull(domain, "domain");
/*  54 */     this.uniqueIds.addAll(domain.getUniqueIds());
/*  55 */     this.names.addAll(domain.getPlayers());
/*  56 */     this.dirty = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public PlayerDomain(String[] names) {
/*  67 */     for (String name : names) {
/*  68 */       addPlayer(name);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPlayer(String name) {
/*  78 */     Preconditions.checkNotNull(name);
/*  79 */     if (!name.trim().isEmpty()) {
/*  80 */       setDirty(true);
/*  81 */       this.names.add(name.trim().toLowerCase());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPlayer(UUID uniqueId) {
/*  94 */     Preconditions.checkNotNull(uniqueId);
/*  95 */     setDirty(true);
/*  96 */     this.uniqueIds.add(uniqueId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPlayer(LocalPlayer player) {
/* 105 */     Preconditions.checkNotNull(player);
/* 106 */     setDirty(true);
/* 107 */     addPlayer(player.getUniqueId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePlayer(String name) {
/* 116 */     Preconditions.checkNotNull(name);
/* 117 */     setDirty(true);
/* 118 */     this.names.remove(name.trim().toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePlayer(UUID uuid) {
/* 127 */     Preconditions.checkNotNull(uuid);
/* 128 */     this.uniqueIds.remove(uuid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePlayer(LocalPlayer player) {
/* 138 */     Preconditions.checkNotNull(player);
/* 139 */     removePlayer(player.getName());
/* 140 */     removePlayer(player.getUniqueId());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(LocalPlayer player) {
/* 145 */     Preconditions.checkNotNull(player);
/* 146 */     return (contains(player.getName()) || contains(player.getUniqueId()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getPlayers() {
/* 155 */     return Collections.unmodifiableSet(this.names);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<UUID> getUniqueIds() {
/* 164 */     return Collections.unmodifiableSet(this.uniqueIds);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(UUID uniqueId) {
/* 169 */     Preconditions.checkNotNull(uniqueId);
/* 170 */     return this.uniqueIds.contains(uniqueId);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(String playerName) {
/* 175 */     Preconditions.checkNotNull(playerName);
/* 176 */     return this.names.contains(playerName.trim().toLowerCase());
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 181 */     return this.names.size() + this.uniqueIds.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 186 */     this.uniqueIds.clear();
/* 187 */     this.names.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirty() {
/* 192 */     return this.dirty;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDirty(boolean dirty) {
/* 197 */     this.dirty = dirty;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 202 */     return "{uuids=" + this.uniqueIds + ", names=" + this.names + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\domains\PlayerDomain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */