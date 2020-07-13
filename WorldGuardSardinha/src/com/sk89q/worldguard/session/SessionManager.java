/*     */ package com.sk89q.worldguard.session;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.sk89q.worldguard.bukkit.BukkitUtil;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.internal.guava.cache.CacheBuilder;
/*     */ import com.sk89q.worldguard.internal.guava.cache.CacheLoader;
/*     */ import com.sk89q.worldguard.internal.guava.cache.LoadingCache;
/*     */ import com.sk89q.worldguard.session.handler.EntryFlag;
/*     */ import com.sk89q.worldguard.session.handler.ExitFlag;
/*     */ import com.sk89q.worldguard.session.handler.FarewellFlag;
/*     */ import com.sk89q.worldguard.session.handler.FeedFlag;
/*     */ import com.sk89q.worldguard.session.handler.GameModeFlag;
/*     */ import com.sk89q.worldguard.session.handler.GodMode;
/*     */ import com.sk89q.worldguard.session.handler.GreetingFlag;
/*     */ import com.sk89q.worldguard.session.handler.Handler;
/*     */ import com.sk89q.worldguard.session.handler.HealFlag;
/*     */ import com.sk89q.worldguard.session.handler.InvincibilityFlag;
/*     */ import com.sk89q.worldguard.session.handler.NotifyEntryFlag;
/*     */ import com.sk89q.worldguard.session.handler.NotifyExitFlag;
/*     */ import com.sk89q.worldguard.session.handler.WaterBreathing;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Collection;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
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
/*     */ public class SessionManager
/*     */   implements Runnable, Listener
/*     */ {
/*     */   public static final int RUN_DELAY = 20;
/*     */   public static final long SESSION_LIFETIME = 10L;
/*     */   private final WorldGuardPlugin plugin;
/*     */   
/*  53 */   private final LoadingCache<WorldPlayerTuple, Boolean> bypassCache = CacheBuilder.newBuilder()
/*  54 */     .maximumSize(1000L)
/*  55 */     .expireAfterAccess(2L, TimeUnit.SECONDS)
/*  56 */     .build(new CacheLoader<WorldPlayerTuple, Boolean>()
/*     */       {
/*     */         public Boolean load(WorldPlayerTuple tuple) throws Exception {
/*  59 */           return Boolean.valueOf(SessionManager.this.plugin.getGlobalRegionManager().hasBypass(tuple.player, tuple.world));
/*     */         }
/*     */       });
/*     */   
/*  63 */   private final LoadingCache<CacheKey, Session> sessions = CacheBuilder.newBuilder()
/*  64 */     .expireAfterAccess(10L, TimeUnit.MINUTES)
/*  65 */     .build(new CacheLoader<CacheKey, Session>()
/*     */       {
/*     */         public Session load(SessionManager.CacheKey key) throws Exception {
/*  68 */           return SessionManager.this.createSession(key.playerRef.get());
/*     */         }
/*     */       });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SessionManager(WorldGuardPlugin plugin) {
/*  78 */     Preconditions.checkNotNull(plugin, "plugin");
/*  79 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldGuardPlugin getPlugin() {
/*  88 */     return this.plugin;
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
/*     */   public boolean hasBypass(Player player, World world) {
/* 101 */     return ((Boolean)this.bypassCache.getUnchecked(new WorldPlayerTuple(world, player))).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetAllStates() {
/* 109 */     Collection<? extends Player> players = BukkitUtil.getOnlinePlayers();
/* 110 */     for (Player player : players) {
/* 111 */       Session session = (Session)this.sessions.getIfPresent(new CacheKey(player));
/* 112 */       if (session != null) {
/* 113 */         session.resetState(player);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetState(Player player) {
/* 125 */     Preconditions.checkNotNull(player, "player");
/* 126 */     Session session = (Session)this.sessions.getIfPresent(new CacheKey(player));
/* 127 */     if (session != null) {
/* 128 */       session.resetState(player);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Session createSession(Player player) {
/* 139 */     Session session = new Session(this);
/* 140 */     session.register((Handler)new HealFlag(session));
/* 141 */     session.register((Handler)new FeedFlag(session));
/* 142 */     session.register((Handler)new NotifyEntryFlag(session));
/* 143 */     session.register((Handler)new NotifyExitFlag(session));
/* 144 */     session.register((Handler)new EntryFlag(session));
/* 145 */     session.register((Handler)new ExitFlag(session));
/* 146 */     session.register((Handler)new GreetingFlag(session));
/* 147 */     session.register((Handler)new FarewellFlag(session));
/* 148 */     session.register((Handler)new GameModeFlag(session));
/* 149 */     session.register((Handler)new InvincibilityFlag(session));
/* 150 */     session.register((Handler)new GodMode(session));
/* 151 */     session.register((Handler)new WaterBreathing(session));
/* 152 */     session.initialize(player);
/* 153 */     return session;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Session getIfPresent(Player player) {
/* 164 */     return (Session)this.sessions.getIfPresent(player);
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
/*     */   public Session get(Player player) {
/* 179 */     return (Session)this.sessions.getUnchecked(new CacheKey(player));
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerJoin(PlayerJoinEvent event) {
/* 185 */     get(event.getPlayer());
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/* 190 */     for (Player player : BukkitUtil.getOnlinePlayers())
/* 191 */       get(player).tick(player); 
/*     */   }
/*     */   
/*     */   private static final class CacheKey
/*     */   {
/*     */     private final WeakReference<Player> playerRef;
/*     */     private final UUID uuid;
/*     */     
/*     */     private CacheKey(Player player) {
/* 200 */       this.playerRef = new WeakReference<Player>(player);
/* 201 */       this.uuid = player.getUniqueId();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 206 */       if (this == o) return true; 
/* 207 */       if (o == null || getClass() != o.getClass()) return false; 
/* 208 */       CacheKey cacheKey = (CacheKey)o;
/* 209 */       return this.uuid.equals(cacheKey.uuid);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 215 */       return this.uuid.hashCode();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\session\SessionManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */