/*     */ package com.sk89q.worldguard.session;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.sk89q.worldguard.bukkit.RegionQuery;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.bukkit.util.Locations;
/*     */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*     */ import com.sk89q.worldguard.protection.flags.StateFlag;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import com.sk89q.worldguard.session.handler.Handler;
/*     */ import java.util.HashMap;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import javax.annotation.Nullable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Session
/*     */ {
/*     */   private final SessionManager manager;
/*  47 */   private final HashMap<Class<?>, Handler> handlers = Maps.newLinkedHashMap();
/*     */   private Location lastValid;
/*     */   private Set<ProtectedRegion> lastRegionSet;
/*  50 */   private final AtomicBoolean needRefresh = new AtomicBoolean(false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Session(SessionManager manager) {
/*  58 */     Preconditions.checkNotNull(manager, "manager");
/*  59 */     this.manager = manager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void register(Handler handler) {
/*  68 */     this.handlers.put(handler.getClass(), handler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldGuardPlugin getPlugin() {
/*  77 */     return getManager().getPlugin();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SessionManager getManager() {
/*  86 */     return this.manager;
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
/*     */   @Nullable
/*     */   public <T extends Handler> T getHandler(Class<T> type) {
/*  99 */     return (T)this.handlers.get(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void initialize(Player player) {
/* 108 */     RegionQuery query = getManager().getPlugin().getRegionContainer().createQuery();
/* 109 */     Location location = player.getLocation();
/* 110 */     ApplicableRegionSet set = query.getApplicableRegions(location);
/*     */     
/* 112 */     this.lastValid = location;
/* 113 */     this.lastRegionSet = set.getRegions();
/*     */     
/* 115 */     for (Handler handler : this.handlers.values()) {
/* 116 */       handler.initialize(player, location, set);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void tick(Player player) {
/* 126 */     RegionQuery query = getManager().getPlugin().getRegionContainer().createQuery();
/* 127 */     Location location = player.getLocation();
/* 128 */     ApplicableRegionSet set = query.getApplicableRegions(location);
/*     */     
/* 130 */     for (Handler handler : this.handlers.values()) {
/* 131 */       handler.tick(player, set);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void resetState(Player player) {
/* 141 */     initialize(player);
/* 142 */     this.needRefresh.set(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInvincible(Player player) {
/* 151 */     boolean invincible = false;
/*     */     
/* 153 */     for (Handler handler : this.handlers.values()) {
/* 154 */       StateFlag.State state = handler.getInvincibility(player);
/* 155 */       if (state != null) {
/* 156 */         switch (state) { case DENY:
/* 157 */             return false;
/* 158 */           case ALLOW: invincible = true; }
/*     */ 
/*     */       
/*     */       }
/*     */     } 
/* 163 */     return invincible;
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
/*     */   
/*     */   @Nullable
/*     */   public Location testMoveTo(Player player, Location to, MoveType moveType) {
/* 177 */     return testMoveTo(player, to, moveType, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Location testMoveTo(Player player, Location to, MoveType moveType, boolean forced) {
/* 198 */     RegionQuery query = getManager().getPlugin().getRegionContainer().createQuery();
/*     */     
/* 200 */     if (!forced && this.needRefresh.getAndSet(false)) {
/* 201 */       forced = true;
/*     */     }
/*     */     
/* 204 */     if (forced || Locations.isDifferentBlock(this.lastValid, to)) {
/* 205 */       ApplicableRegionSet toSet = query.getApplicableRegions(to);
/*     */       
/* 207 */       for (Handler handler : this.handlers.values()) {
/* 208 */         if (!handler.testMoveTo(player, this.lastValid, to, toSet, moveType) && moveType.isCancellable()) {
/* 209 */           return this.lastValid;
/*     */         }
/*     */       } 
/*     */       
/* 213 */       Sets.SetView setView1 = Sets.difference(toSet.getRegions(), this.lastRegionSet);
/* 214 */       Sets.SetView setView2 = Sets.difference(this.lastRegionSet, toSet.getRegions());
/*     */       
/* 216 */       for (Handler handler : this.handlers.values()) {
/* 217 */         if (!handler.onCrossBoundary(player, this.lastValid, to, toSet, (Set)setView1, (Set)setView2, moveType) && moveType.isCancellable()) {
/* 218 */           return this.lastValid;
/*     */         }
/*     */       } 
/*     */       
/* 222 */       this.lastValid = to;
/* 223 */       this.lastRegionSet = toSet.getRegions();
/*     */     } 
/*     */     
/* 226 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\session\Session.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */