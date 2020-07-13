/*     */ package com.sk89q.worldguard.domains;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import com.sk89q.worldguard.util.ChangeTracked;
/*     */ import com.sk89q.worldguard.util.profile.Profile;
/*     */ import com.sk89q.worldguard.util.profile.cache.ProfileCache;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class DefaultDomain
/*     */   implements Domain, ChangeTracked
/*     */ {
/*  43 */   private PlayerDomain playerDomain = new PlayerDomain();
/*  44 */   private GroupDomain groupDomain = new GroupDomain();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultDomain() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultDomain(DefaultDomain existing) {
/*  58 */     setPlayerDomain(existing.getPlayerDomain());
/*  59 */     setGroupDomain(existing.getGroupDomain());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerDomain getPlayerDomain() {
/*  68 */     return this.playerDomain;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerDomain(PlayerDomain playerDomain) {
/*  77 */     Preconditions.checkNotNull(playerDomain);
/*  78 */     this.playerDomain = new PlayerDomain(playerDomain);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GroupDomain getGroupDomain() {
/*  87 */     return this.groupDomain;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGroupDomain(GroupDomain groupDomain) {
/*  96 */     Preconditions.checkNotNull(groupDomain);
/*  97 */     this.groupDomain = new GroupDomain(groupDomain);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPlayer(String name) {
/* 106 */     this.playerDomain.addPlayer(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePlayer(String name) {
/* 115 */     this.playerDomain.removePlayer(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePlayer(UUID uuid) {
/* 124 */     this.playerDomain.removePlayer(uuid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPlayer(UUID uniqueId) {
/* 133 */     this.playerDomain.addPlayer(uniqueId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePlayer(LocalPlayer player) {
/* 143 */     this.playerDomain.removePlayer(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPlayer(LocalPlayer player) {
/* 152 */     this.playerDomain.addPlayer(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAll(DefaultDomain other) {
/* 161 */     Preconditions.checkNotNull(other);
/* 162 */     for (String player : other.getPlayers()) {
/* 163 */       addPlayer(player);
/*     */     }
/* 165 */     for (UUID uuid : other.getUniqueIds()) {
/* 166 */       addPlayer(uuid);
/*     */     }
/* 168 */     for (String group : other.getGroups()) {
/* 169 */       addGroup(group);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAll(DefaultDomain other) {
/* 179 */     Preconditions.checkNotNull(other);
/* 180 */     for (String player : other.getPlayers()) {
/* 181 */       removePlayer(player);
/*     */     }
/* 183 */     for (UUID uuid : other.getUniqueIds()) {
/* 184 */       removePlayer(uuid);
/*     */     }
/* 186 */     for (String group : other.getGroups()) {
/* 187 */       removeGroup(group);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getPlayers() {
/* 197 */     return this.playerDomain.getPlayers();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<UUID> getUniqueIds() {
/* 206 */     return this.playerDomain.getUniqueIds();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addGroup(String name) {
/* 215 */     this.groupDomain.addGroup(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeGroup(String name) {
/* 224 */     this.groupDomain.removeGroup(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getGroups() {
/* 233 */     return this.groupDomain.getGroups();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(LocalPlayer player) {
/* 238 */     return (this.playerDomain.contains(player) || this.groupDomain.contains(player));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(UUID uniqueId) {
/* 243 */     return this.playerDomain.contains(uniqueId);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(String playerName) {
/* 248 */     return this.playerDomain.contains(playerName);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 253 */     return this.groupDomain.size() + this.playerDomain.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 258 */     this.playerDomain.clear();
/* 259 */     this.groupDomain.clear();
/*     */   }
/*     */   
/*     */   public void removeAll() {
/* 263 */     clear();
/*     */   }
/*     */   
/*     */   public String toPlayersString() {
/* 267 */     return toPlayersString(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toPlayersString(@Nullable ProfileCache cache) {
/* 272 */     StringBuilder str = new StringBuilder();
/* 273 */     List<String> output = new ArrayList<String>();
/*     */     
/* 275 */     for (String name : this.playerDomain.getPlayers()) {
/* 276 */       output.add("name:" + name);
/*     */     }
/*     */     
/* 279 */     if (cache != null) {
/* 280 */       ImmutableMap<UUID, Profile> results = cache.getAllPresent(this.playerDomain.getUniqueIds());
/* 281 */       for (UUID uuid : this.playerDomain.getUniqueIds()) {
/* 282 */         Profile profile = (Profile)results.get(uuid);
/* 283 */         if (profile != null) {
/* 284 */           output.add(profile.getName() + "*"); continue;
/*     */         } 
/* 286 */         output.add("uuid:" + uuid);
/*     */       } 
/*     */     } else {
/*     */       
/* 290 */       for (UUID uuid : this.playerDomain.getUniqueIds()) {
/* 291 */         output.add("uuid:" + uuid);
/*     */       }
/*     */     } 
/*     */     
/* 295 */     Collections.sort(output, String.CASE_INSENSITIVE_ORDER);
/* 296 */     for (Iterator<String> it = output.iterator(); it.hasNext(); ) {
/* 297 */       str.append(it.next());
/* 298 */       if (it.hasNext()) {
/* 299 */         str.append(", ");
/*     */       }
/*     */     } 
/* 302 */     return str.toString();
/*     */   }
/*     */   
/*     */   public String toGroupsString() {
/* 306 */     StringBuilder str = new StringBuilder();
/* 307 */     for (Iterator<String> it = this.groupDomain.getGroups().iterator(); it.hasNext(); ) {
/* 308 */       str.append("*");
/* 309 */       str.append(it.next());
/* 310 */       if (it.hasNext()) {
/* 311 */         str.append(", ");
/*     */       }
/*     */     } 
/* 314 */     return str.toString();
/*     */   }
/*     */   
/*     */   public String toUserFriendlyString() {
/* 318 */     StringBuilder str = new StringBuilder();
/*     */     
/* 320 */     if (this.playerDomain.size() > 0) {
/* 321 */       str.append(toPlayersString());
/*     */     }
/*     */     
/* 324 */     if (this.groupDomain.size() > 0) {
/* 325 */       if (str.length() > 0) {
/* 326 */         str.append("; ");
/*     */       }
/*     */       
/* 329 */       str.append(toGroupsString());
/*     */     } 
/*     */     
/* 332 */     return str.toString();
/*     */   }
/*     */   
/*     */   public String toUserFriendlyString(ProfileCache cache) {
/* 336 */     StringBuilder str = new StringBuilder();
/*     */     
/* 338 */     if (this.playerDomain.size() > 0) {
/* 339 */       str.append(toPlayersString(cache));
/*     */     }
/*     */     
/* 342 */     if (this.groupDomain.size() > 0) {
/* 343 */       if (str.length() > 0) {
/* 344 */         str.append("; ");
/*     */       }
/*     */       
/* 347 */       str.append(toGroupsString());
/*     */     } 
/*     */     
/* 350 */     return str.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirty() {
/* 355 */     return (this.playerDomain.isDirty() || this.groupDomain.isDirty());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDirty(boolean dirty) {
/* 360 */     this.playerDomain.setDirty(dirty);
/* 361 */     this.groupDomain.setDirty(dirty);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 366 */     return "{players=" + this.playerDomain + ", groups=" + this.groupDomain + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\domains\DefaultDomain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */