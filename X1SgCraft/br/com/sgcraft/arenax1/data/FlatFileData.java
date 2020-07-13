/*     */ package br.com.sgcraft.arenax1.data;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;

import br.com.sgcraft.arenax1.arena.Arena;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FlatFileData
/*     */   implements Data
/*     */ {
/*     */   private final FileConfiguration config;
/*     */   private final File arenas;
/*     */   
/*     */   public FlatFileData(File dataFolder) {
/*  26 */     this.arenas = new File(dataFolder, "arenas.yml");
/*  27 */     this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.arenas);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveArena(Arena arena) {
/*  33 */     if (!arena.isCompleted()) {
/*     */       return;
/*     */     }
/*     */     
/*  37 */     this.config.set("Arenas." + arena.getName() + ".world", arena.getWorld());
/*  38 */     Location f = arena.getPos1();
/*  39 */     this.config.set("Arenas." + arena.getName() + ".pos1", Math.round(f.getX()) + " " + Math.round(f.getY()) + " " + Math.round(f.getZ()) + " " + Math.round(f.getYaw()) + " " + Math.round(f.getPitch()));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  44 */     f = arena.getPos2();
/*  45 */     this.config.set("Arenas." + arena.getName() + ".pos2", Math.round(f.getX()) + " " + Math.round(f.getY()) + " " + Math.round(f.getZ()) + " " + Math.round(f.getYaw()) + " " + Math.round(f.getPitch()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveAllArena(List<Arena> arenas) {
/*  55 */     this.config.set("Arenas", null);
/*  56 */     for (Arena arena : arenas)
/*     */     {
/*  58 */       saveArena(arena);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Arena loadArena(String name) {
/*  65 */     String world = this.config.getString("Arenas." + name + ".world");
/*  66 */     String[] pos1 = this.config.getString("Arenas." + name + ".pos1").split(" ");
/*  67 */     String[] pos2 = this.config.getString("Arenas." + name + ".pos2").split(" ");
/*     */     
/*  69 */     Location ps1 = new Location(Bukkit.getWorld(world), Double.parseDouble(pos1[0]), Double.parseDouble(pos1[1]), Double.parseDouble(pos1[2]), Float.parseFloat(pos1[3]), Float.parseFloat(pos1[4]));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     Location ps2 = new Location(Bukkit.getWorld(world), Double.parseDouble(pos2[0]), Double.parseDouble(pos2[1]), Double.parseDouble(pos2[2]), Float.parseFloat(pos2[3]), Float.parseFloat(pos2[4]));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     Arena arena = new Arena(name, world, ps1, ps2);
/*     */     
/*  84 */     return arena;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Arena> loadAllArena() {
/*  90 */     List<Arena> arenas = new ArrayList<>();
/*     */     
/*  92 */     if (this.config.isConfigurationSection("Arenas")) {
/*     */       
/*  94 */       Set<String> names = this.config.getConfigurationSection("Arenas").getKeys(false);
/*     */       
/*  96 */       for (String string : names) {
/*     */ 
/*     */         
/*     */         try {
/* 100 */           arenas.add(loadArena(string));
/* 101 */         } catch (Exception e) {
/*     */           
/* 103 */           Bukkit.getLogger().log(Level.WARNING, "Error on load arena {0}", string);
/*     */         } 
/*     */       } 
/*     */     } 
/* 107 */     return arenas;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveToBase() throws Exception {
/* 113 */     this.config.save(this.arenas);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Location loadLobby() {
/* 119 */     if (this.config.contains("ArenaLobby")) {
/*     */       
/* 121 */       String[] lobby = this.config.getString("ArenaLobby").split(" ");
/* 122 */       return new Location(Bukkit.getWorld(lobby[5]), Double.parseDouble(lobby[0]), Double.parseDouble(lobby[1]), Double.parseDouble(lobby[2]), Float.parseFloat(lobby[3]), Float.parseFloat(lobby[4]));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveLobby(Location lobby) {
/* 137 */     if (lobby != null)
/*     */     {
/* 139 */       this.config.set("ArenaLobby", Math.round(lobby.getX()) + " " + Math.round(lobby.getY()) + " " + Math.round(lobby.getZ()) + " " + Math.round(lobby.getYaw()) + " " + Math.round(lobby.getPitch()) + " " + lobby.getWorld().getName());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\ArenaX1-0.3.2.jar!\br\com\tinycraft\arenax1\data\FlatFileData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */