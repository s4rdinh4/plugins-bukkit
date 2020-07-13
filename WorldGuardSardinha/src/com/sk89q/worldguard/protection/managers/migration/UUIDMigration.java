/*     */ package com.sk89q.worldguard.protection.managers.migration;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.sk89q.worldguard.domains.DefaultDomain;
/*     */ import com.sk89q.worldguard.domains.PlayerDomain;
/*     */ import com.sk89q.worldguard.protection.managers.storage.RegionDatabase;
/*     */ import com.sk89q.worldguard.protection.managers.storage.RegionDriver;
/*     */ import com.sk89q.worldguard.protection.managers.storage.StorageException;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import com.sk89q.worldguard.util.profile.Profile;
/*     */ import com.sk89q.worldguard.util.profile.resolver.ProfileService;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class UUIDMigration
/*     */   extends AbstractMigration
/*     */ {
/*  51 */   private static final Logger log = Logger.getLogger(UUIDMigration.class.getCanonicalName());
/*     */   
/*     */   private static final int LOG_DELAY = 5000;
/*  54 */   private final Timer timer = new Timer();
/*     */   private final ProfileService profileService;
/*  56 */   private final ConcurrentMap<String, UUID> resolvedNames = new ConcurrentHashMap<String, UUID>();
/*  57 */   private final Set<String> unresolvedNames = new HashSet<String>();
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean keepUnresolvedNames = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UUIDMigration(RegionDriver driver, ProfileService profileService) {
/*  67 */     super(driver);
/*  68 */     Preconditions.checkNotNull(profileService);
/*  69 */     this.profileService = profileService;
/*     */   }
/*     */   
/*     */   protected void migrate(RegionDatabase store) throws MigrationException {
/*     */     Set<ProtectedRegion> regions;
/*  74 */     log.log(Level.INFO, "Migrating regions in '" + store.getName() + "' to convert names -> UUIDs...");
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  79 */       regions = store.loadAll();
/*  80 */     } catch (StorageException e) {
/*  81 */       throw new MigrationException("Failed to load region data for the world '" + store.getName() + "'", e);
/*     */     } 
/*     */     
/*  84 */     migrate(regions);
/*     */     
/*     */     try {
/*  87 */       store.saveAll(regions);
/*  88 */     } catch (StorageException e) {
/*  89 */       throw new MigrationException("Failed to save region data after migration of the world '" + store.getName() + "'", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean migrate(Collection<ProtectedRegion> regions) throws MigrationException {
/*  95 */     Set<String> names = getNames(regions);
/*     */     
/*  97 */     if (!names.isEmpty()) {
/*     */ 
/*     */       
/* 100 */       TimerTask task = new ResolvedNamesTimerTask();
/*     */       
/*     */       try {
/* 103 */         this.timer.schedule(task, 5000L, 5000L);
/*     */         
/* 105 */         log.log(Level.INFO, "Resolving " + names.size() + " name(s) into UUIDs... this may take a while.");
/*     */ 
/*     */ 
/*     */         
/* 109 */         Set<String> lookupNames = new HashSet<String>(names);
/* 110 */         lookupNames.removeAll(this.resolvedNames.keySet());
/*     */ 
/*     */         
/* 113 */         this.profileService.findAllByName(lookupNames, new Predicate<Profile>()
/*     */             {
/*     */               public boolean apply(Profile profile) {
/* 116 */                 UUIDMigration.this.resolvedNames.put(profile.getName().toLowerCase(), profile.getUniqueId());
/* 117 */                 return true;
/*     */               }
/*     */             });
/* 120 */       } catch (IOException e) {
/* 121 */         throw new MigrationException("The name -> UUID service failed", e);
/* 122 */       } catch (InterruptedException e) {
/* 123 */         throw new MigrationException("The migration was interrupted");
/*     */       } finally {
/*     */         
/* 126 */         task.cancel();
/*     */       } 
/*     */ 
/*     */       
/* 130 */       log.log(Level.INFO, "UUIDs resolved... now migrating all regions to UUIDs where possible...");
/* 131 */       convert(regions);
/*     */       
/* 133 */       return true;
/*     */     } 
/* 135 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postMigration() {
/* 141 */     if (!this.unresolvedNames.isEmpty()) {
/* 142 */       if (this.keepUnresolvedNames) {
/* 143 */         log.log(Level.WARNING, "Some member and owner names do not seem to exist or own Minecraft so they could not be converted into UUIDs. They have been left as names, but the conversion can be re-run with 'keep-names-that-lack-uuids' set to false in the configuration in order to remove these names. Leaving the names means that someone can register with one of these names in the future and become that player.");
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 150 */         log.log(Level.WARNING, "Some member and owner names do not seem to exist or own Minecraft so they could not be converted into UUIDs. These names have been removed.");
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
/*     */ 
/*     */   
/*     */   private static Set<String> getNames(Collection<ProtectedRegion> regions) {
/* 164 */     Set<String> names = new HashSet<String>();
/* 165 */     for (ProtectedRegion region : regions) {
/*     */       
/* 167 */       names.addAll(region.getOwners().getPlayers());
/* 168 */       names.addAll(region.getMembers().getPlayers());
/*     */     } 
/* 170 */     return names;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void convert(Collection<ProtectedRegion> regions) {
/* 179 */     for (ProtectedRegion region : regions) {
/* 180 */       convert(region.getOwners());
/* 181 */       convert(region.getMembers());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void convert(DefaultDomain domain) {
/* 191 */     PlayerDomain playerDomain = new PlayerDomain();
/* 192 */     for (UUID uuid : domain.getUniqueIds()) {
/* 193 */       playerDomain.addPlayer(uuid);
/*     */     }
/*     */     
/* 196 */     for (String name : domain.getPlayers()) {
/* 197 */       UUID uuid = this.resolvedNames.get(name.toLowerCase());
/* 198 */       if (uuid != null) {
/* 199 */         playerDomain.addPlayer(uuid); continue;
/*     */       } 
/* 201 */       if (this.keepUnresolvedNames) {
/* 202 */         playerDomain.addPlayer(name);
/*     */       }
/* 204 */       this.unresolvedNames.add(name);
/*     */     } 
/*     */ 
/*     */     
/* 208 */     domain.setPlayerDomain(playerDomain);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getKeepUnresolvedNames() {
/* 218 */     return this.keepUnresolvedNames;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKeepUnresolvedNames(boolean keepUnresolvedNames) {
/* 228 */     this.keepUnresolvedNames = keepUnresolvedNames;
/*     */   }
/*     */   
/*     */   private class ResolvedNamesTimerTask
/*     */     extends TimerTask
/*     */   {
/*     */     private ResolvedNamesTimerTask() {}
/*     */     
/*     */     public void run() {
/* 237 */       UUIDMigration.log.info("UUIDs have been found for " + UUIDMigration.this.resolvedNames.size() + " name(s)...");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\migration\UUIDMigration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */