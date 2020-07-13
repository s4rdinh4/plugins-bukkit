/*     */ package br.com.sgcraft.arenax1.arena;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.Location;

import br.com.sgcraft.arenax1.ArenaX1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArenaManager
/*     */ {
/*     */   private final ArenaX1 plugin;
/*     */   private final List<Arena> arenas;
/*     */   private Location arenaLobby;
/*     */   
/*     */   public ArenaManager(ArenaX1 plugin, List<Arena> arenas, Location lobby) {
/*  22 */     this.plugin = plugin;
/*  23 */     this.arenas = arenas;
/*  24 */     this.arenaLobby = lobby;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsArena(String name) {
/*  29 */     Iterator<Arena> i = this.arenas.iterator();
/*     */     
/*  31 */     while (i.hasNext()) {
/*     */       
/*  33 */       Arena arena = i.next();
/*     */       
/*  35 */       if (arena.getName().equalsIgnoreCase(name))
/*     */       {
/*  37 */         return true;
/*     */       }
/*     */     } 
/*  40 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Location getArenaLobby() {
/*  45 */     return this.arenaLobby;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setArenaLobby(Location lobby) {
/*  50 */     this.arenaLobby = lobby;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Arena> getArenas() {
/*  55 */     return this.arenas;
/*     */   }
/*     */ 
/*     */   
/*     */   public Arena getArena(String name) {
/*  60 */     Iterator<Arena> i = this.arenas.iterator();
/*     */     
/*  62 */     while (i.hasNext()) {
/*     */       
/*  64 */       Arena arena = i.next();
/*     */       
/*  66 */       if (arena.getName().equalsIgnoreCase(name))
/*     */       {
/*  68 */         return arena;
/*     */       }
/*     */     } 
/*  71 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Arena getAvailableArena() {
/*  76 */     Iterator<Arena> i = this.arenas.iterator();
/*     */     
/*  78 */     while (i.hasNext()) {
/*     */       
/*  80 */       Arena arena = i.next();
/*     */       
/*  82 */       if (!arena.isOcurring() && arena.isCompleted())
/*     */       {
/*  84 */         return arena;
/*     */       }
/*     */     } 
/*     */     
/*  88 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean createArena(String name, String world) {
/*  93 */     if (containsArena(name))
/*     */     {
/*  95 */       return false;
/*     */     }
/*     */     
/*  98 */     this.arenas.add(new Arena(name, world, null, null));
/*  99 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeArena(String name) {
/* 105 */     Iterator<Arena> i = this.arenas.iterator();
/*     */     
/* 107 */     while (i.hasNext()) {
/*     */       
/* 109 */       Arena arena = i.next();
/*     */       
/* 111 */       if (arena.getName().equalsIgnoreCase(name)) {
/*     */         
/* 113 */         i.remove();
/* 114 */         return true;
/*     */       } 
/*     */     } 
/* 117 */     return false;
/*     */   }
/*     */ }