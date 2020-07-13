/*     */ package com.sk89q.worldguard.protection.managers;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Supplier;
/*     */ import com.sk89q.worldguard.protection.managers.index.ChunkHashTable;
/*     */ import com.sk89q.worldguard.protection.managers.index.ConcurrentRegionIndex;
/*     */ import com.sk89q.worldguard.protection.managers.index.PriorityRTreeIndex;
/*     */ import com.sk89q.worldguard.protection.managers.storage.RegionDatabase;
/*     */ import com.sk89q.worldguard.protection.managers.storage.RegionDriver;
/*     */ import com.sk89q.worldguard.protection.managers.storage.StorageException;
/*     */ import com.sk89q.worldguard.util.Normal;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RegionContainerImpl
/*     */ {
/*  56 */   private static final Logger log = Logger.getLogger(RegionContainerImpl.class.getCanonicalName());
/*     */   
/*     */   private static final int LOAD_ATTEMPT_INTERVAL = 30000;
/*     */   private static final int SAVE_INTERVAL = 30000;
/*  60 */   private final ConcurrentMap<Normal, RegionManager> mapping = new ConcurrentHashMap<Normal, RegionManager>();
/*  61 */   private final Object lock = new Object();
/*     */   private final RegionDriver driver;
/*  63 */   private final Supplier<? extends ConcurrentRegionIndex> indexFactory = (Supplier<? extends ConcurrentRegionIndex>)new ChunkHashTable.Factory((Supplier)new PriorityRTreeIndex.Factory());
/*  64 */   private final Timer timer = new Timer();
/*     */   
/*  66 */   private final Set<Normal> failingLoads = new HashSet<Normal>();
/*  67 */   private final Set<RegionManager> failingSaves = Collections.synchronizedSet(
/*  68 */       Collections.newSetFromMap(new WeakHashMap<RegionManager, Boolean>()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegionContainerImpl(RegionDriver driver) {
/*  76 */     Preconditions.checkNotNull(driver);
/*  77 */     this.driver = driver;
/*  78 */     this.timer.schedule(new BackgroundLoader(), 30000L, 30000L);
/*  79 */     this.timer.schedule(new BackgroundSaver(), 30000L, 30000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegionDriver getDriver() {
/*  88 */     return this.driver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public RegionManager load(String name) {
/* 100 */     Preconditions.checkNotNull(name);
/*     */     
/* 102 */     Normal normal = Normal.normal(name);
/*     */     
/* 104 */     synchronized (this.lock) {
/* 105 */       RegionManager manager = this.mapping.get(normal);
/* 106 */       if (manager != null) {
/* 107 */         return manager;
/*     */       }
/*     */       try {
/* 110 */         manager = createAndLoad(name);
/* 111 */         this.mapping.put(normal, manager);
/* 112 */         this.failingLoads.remove(normal);
/* 113 */         return manager;
/* 114 */       } catch (StorageException e) {
/* 115 */         log.log(Level.WARNING, "Failed to load the region data for '" + name + "' (periodic attempts will be made to load the data until success)", (Throwable)e);
/* 116 */         this.failingLoads.add(normal);
/* 117 */         return null;
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
/*     */   private RegionManager createAndLoad(String name) throws StorageException {
/* 131 */     RegionDatabase store = this.driver.get(name);
/* 132 */     RegionManager manager = new RegionManager(store, this.indexFactory);
/* 133 */     manager.load();
/* 134 */     return manager;
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
/*     */   public void unload(String name) {
/* 146 */     Preconditions.checkNotNull(name);
/*     */     
/* 148 */     Normal normal = Normal.normal(name);
/*     */     
/* 150 */     synchronized (this.lock) {
/* 151 */       RegionManager manager = this.mapping.get(normal);
/* 152 */       if (manager != null) {
/*     */         try {
/* 154 */           manager.save();
/* 155 */         } catch (StorageException e) {
/* 156 */           log.log(Level.WARNING, "Failed to save the region data for '" + name + "'", (Throwable)e);
/*     */         } 
/*     */         
/* 159 */         this.mapping.remove(normal);
/* 160 */         this.failingSaves.remove(manager);
/*     */       } 
/*     */       
/* 163 */       this.failingLoads.remove(normal);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unloadAll() {
/* 172 */     synchronized (this.lock) {
/* 173 */       for (Map.Entry<Normal, RegionManager> entry : this.mapping.entrySet()) {
/* 174 */         String name = ((Normal)entry.getKey()).toString();
/* 175 */         RegionManager manager = entry.getValue();
/*     */         try {
/* 177 */           manager.saveChanges();
/* 178 */         } catch (StorageException e) {
/* 179 */           log.log(Level.WARNING, "Failed to save the region data for '" + name + "' while unloading the data for all worlds", (Throwable)e);
/*     */         } 
/*     */       } 
/*     */       
/* 183 */       this.mapping.clear();
/* 184 */       this.failingLoads.clear();
/* 185 */       this.failingSaves.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public RegionManager get(String name) {
/* 197 */     Preconditions.checkNotNull(name);
/* 198 */     return this.mapping.get(Normal.normal(name));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<RegionManager> getLoaded() {
/* 207 */     return Collections.unmodifiableList(new ArrayList<RegionManager>(this.mapping.values()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<RegionManager> getSaveFailures() {
/* 216 */     return new HashSet<RegionManager>(this.failingSaves);
/*     */   }
/*     */   
/*     */   private class BackgroundSaver
/*     */     extends TimerTask
/*     */   {
/*     */     private BackgroundSaver() {}
/*     */     
/*     */     public void run() {
/* 225 */       synchronized (RegionContainerImpl.this.lock) {
/*     */ 
/*     */         
/* 228 */         for (Map.Entry<Normal, RegionManager> entry : (Iterable<Map.Entry<Normal, RegionManager>>)RegionContainerImpl.this.mapping.entrySet()) {
/* 229 */           String name = ((Normal)entry.getKey()).toString();
/* 230 */           RegionManager manager = entry.getValue();
/*     */           try {
/* 232 */             if (manager.saveChanges()) {
/* 233 */               RegionContainerImpl.log.info("Region data changes made in '" + name + "' have been background saved");
/*     */             }
/* 235 */             RegionContainerImpl.this.failingSaves.remove(manager);
/* 236 */           } catch (StorageException e) {
/* 237 */             RegionContainerImpl.this.failingSaves.add(manager);
/* 238 */             RegionContainerImpl.log.log(Level.WARNING, "Failed to save the region data for '" + name + "' during a periodical save", (Throwable)e);
/* 239 */           } catch (Exception e) {
/* 240 */             RegionContainerImpl.this.failingSaves.add(manager);
/* 241 */             RegionContainerImpl.log.log(Level.WARNING, "An expected error occurred during a periodical save", e);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private class BackgroundLoader
/*     */     extends TimerTask
/*     */   {
/*     */     private BackgroundLoader() {}
/*     */     
/*     */     public void run() {
/* 255 */       synchronized (RegionContainerImpl.this.lock) {
/* 256 */         if (!RegionContainerImpl.this.failingLoads.isEmpty()) {
/* 257 */           RegionContainerImpl.log.info("Attempting to load region data that has previously failed to load...");
/*     */           
/* 259 */           Iterator<Normal> it = RegionContainerImpl.this.failingLoads.iterator();
/* 260 */           while (it.hasNext()) {
/* 261 */             Normal normal = it.next();
/*     */             try {
/* 263 */               RegionManager manager = RegionContainerImpl.this.createAndLoad(normal.toString());
/* 264 */               RegionContainerImpl.this.mapping.put(normal, manager);
/* 265 */               it.remove();
/* 266 */               RegionContainerImpl.log.info("Successfully loaded region data for '" + normal.toString() + "'");
/* 267 */             } catch (StorageException e) {
/* 268 */               RegionContainerImpl.log.log(Level.WARNING, "Region data is still failing to load, at least for the world named '" + normal.toString() + "'", (Throwable)e);
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\RegionContainerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */