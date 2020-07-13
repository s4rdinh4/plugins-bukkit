/*     */ package com.sk89q.worldguard.bukkit;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.sk89q.worldedit.Vector2D;
/*     */ import com.sk89q.worldguard.protection.managers.RegionContainerImpl;
/*     */ import com.sk89q.worldguard.protection.managers.RegionManager;
/*     */ import com.sk89q.worldguard.protection.managers.migration.Migration;
/*     */ import com.sk89q.worldguard.protection.managers.migration.MigrationException;
/*     */ import com.sk89q.worldguard.protection.managers.migration.UUIDMigration;
/*     */ import com.sk89q.worldguard.protection.managers.storage.RegionDriver;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Chunk;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.world.ChunkLoadEvent;
/*     */ import org.bukkit.event.world.ChunkUnloadEvent;
/*     */ import org.bukkit.event.world.WorldLoadEvent;
/*     */ import org.bukkit.event.world.WorldUnloadEvent;
/*     */ import org.bukkit.plugin.Plugin;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RegionContainer
/*     */ {
/*  62 */   private static final Logger log = Logger.getLogger(RegionContainer.class.getCanonicalName());
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int CACHE_INVALIDATION_INTERVAL = 2;
/*     */ 
/*     */   
/*  69 */   private final Object lock = new Object();
/*     */   private final WorldGuardPlugin plugin;
/*  71 */   private final QueryCache cache = new QueryCache();
/*     */ 
/*     */ 
/*     */   
/*     */   private RegionContainerImpl container;
/*     */ 
/*     */ 
/*     */   
/*     */   RegionContainer(WorldGuardPlugin plugin) {
/*  80 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void initialize() {
/*  87 */     ConfigurationManager config = this.plugin.getGlobalStateManager();
/*  88 */     this.container = new RegionContainerImpl(config.selectedRegionStoreDriver);
/*     */ 
/*     */     
/*  91 */     autoMigrate();
/*     */     
/*  93 */     loadWorlds();
/*     */     
/*  95 */     Bukkit.getPluginManager().registerEvents(new Listener() {
/*     */           @EventHandler
/*     */           public void onWorldLoad(WorldLoadEvent event) {
/*  98 */             RegionContainer.this.load(event.getWorld());
/*     */           }
/*     */           
/*     */           @EventHandler
/*     */           public void onWorldUnload(WorldUnloadEvent event) {
/* 103 */             RegionContainer.this.unload(event.getWorld());
/*     */           }
/*     */           
/*     */           @EventHandler
/*     */           public void onChunkLoad(ChunkLoadEvent event) {
/* 108 */             RegionManager manager = RegionContainer.this.get(event.getWorld());
/* 109 */             if (manager != null) {
/* 110 */               Chunk chunk = event.getChunk();
/* 111 */               manager.loadChunk(new Vector2D(chunk.getX(), chunk.getZ()));
/*     */             } 
/*     */           }
/*     */           
/*     */           @EventHandler
/*     */           public void onChunkUnload(ChunkUnloadEvent event) {
/* 117 */             RegionManager manager = RegionContainer.this.get(event.getWorld());
/* 118 */             if (manager != null) {
/* 119 */               Chunk chunk = event.getChunk();
/* 120 */               manager.unloadChunk(new Vector2D(chunk.getX(), chunk.getZ()));
/*     */             } 
/*     */           }
/*     */         }(Plugin)this.plugin);
/*     */     
/* 125 */     Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable()
/*     */         {
/*     */           public void run() {
/* 128 */             RegionContainer.this.cache.invalidateAll();
/*     */           }
/*     */         },  2L, 2L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void unload() {
/* 137 */     synchronized (this.lock) {
/* 138 */       this.container.unloadAll();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegionDriver getDriver() {
/* 148 */     return this.container.getDriver();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadWorlds() {
/* 155 */     synchronized (this.lock) {
/* 156 */       for (World world : Bukkit.getServer().getWorlds()) {
/* 157 */         load(world);
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
/*     */   public void reload() {
/* 169 */     synchronized (this.lock) {
/* 170 */       unload();
/* 171 */       loadWorlds();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private RegionManager load(World world) {
/*     */     RegionManager manager;
/* 183 */     Preconditions.checkNotNull(world);
/*     */     
/* 185 */     WorldConfiguration config = this.plugin.getGlobalStateManager().get(world);
/* 186 */     if (!config.useRegions) {
/* 187 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 192 */     synchronized (this.lock) {
/* 193 */       manager = this.container.load(world.getName());
/*     */       
/* 195 */       if (manager != null) {
/*     */         
/* 197 */         List<Vector2D> positions = new ArrayList<Vector2D>();
/* 198 */         for (Chunk chunk : world.getLoadedChunks()) {
/* 199 */           positions.add(new Vector2D(chunk.getX(), chunk.getZ()));
/*     */         }
/* 201 */         manager.loadChunks(positions);
/*     */       } 
/*     */     } 
/*     */     
/* 205 */     return manager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void unload(World world) {
/* 214 */     Preconditions.checkNotNull(world);
/*     */     
/* 216 */     synchronized (this.lock) {
/* 217 */       this.container.unload(world.getName());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public RegionManager get(World world) {
/* 236 */     return this.container.get(world.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<RegionManager> getLoaded() {
/* 245 */     return Collections.unmodifiableList(this.container.getLoaded());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<RegionManager> getSaveFailures() {
/* 254 */     return this.container.getSaveFailures();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegionQuery createQuery() {
/* 263 */     return new RegionQuery(this.plugin, this.cache);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void migrate(Migration migration) throws MigrationException {
/* 274 */     Preconditions.checkNotNull(migration);
/*     */     
/* 276 */     synchronized (this.lock) {
/*     */       try {
/* 278 */         log.info("Unloading and saving region data that is currently loaded...");
/* 279 */         unload();
/* 280 */         migration.migrate();
/*     */       } finally {
/* 282 */         log.info("Loading region data for loaded worlds...");
/* 283 */         loadWorlds();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void autoMigrate() {
/* 292 */     ConfigurationManager config = this.plugin.getGlobalStateManager();
/*     */     
/* 294 */     if (config.migrateRegionsToUuid) {
/* 295 */       RegionDriver driver = getDriver();
/* 296 */       UUIDMigration migrator = new UUIDMigration(driver, this.plugin.getProfileService());
/* 297 */       migrator.setKeepUnresolvedNames(config.keepUnresolvedNames);
/*     */       try {
/* 299 */         migrate((Migration)migrator);
/*     */         
/* 301 */         log.info("Regions saved after UUID migration! This won't happen again unless you change the relevant configuration option in WorldGuard's config.");
/*     */ 
/*     */         
/* 304 */         config.disableUuidMigration();
/* 305 */       } catch (MigrationException e) {
/* 306 */         log.log(Level.WARNING, "Failed to execute the migration", (Throwable)e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\RegionContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */