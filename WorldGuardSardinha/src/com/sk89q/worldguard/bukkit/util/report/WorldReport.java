/*    */ package com.sk89q.worldguard.bukkit.util.report;
/*    */ 
/*    */ import com.sk89q.worldguard.util.report.DataReport;
/*    */ import java.util.List;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.generator.ChunkGenerator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldReport
/*    */   extends DataReport
/*    */ {
/*    */   public WorldReport() {
/* 32 */     super("Worlds");
/*    */     
/* 34 */     List<World> worlds = Bukkit.getServer().getWorlds();
/*    */     
/* 36 */     append("World Count", worlds.size());
/*    */     
/* 38 */     for (World world : worlds) {
/* 39 */       DataReport report = new DataReport("World: " + world.getName());
/* 40 */       report.append("UUID", world.getUID());
/* 41 */       report.append("World Type", world.getWorldType());
/* 42 */       report.append("Environment", world.getEnvironment());
/* 43 */       ChunkGenerator generator = world.getGenerator();
/* 44 */       report.append("Chunk Generator", (generator != null) ? generator.getClass().getName() : "<Default>");
/*    */       
/* 46 */       DataReport spawning = new DataReport("Spawning");
/* 47 */       spawning.append("Animals?", world.getAllowAnimals());
/* 48 */       spawning.append("Monsters?", world.getAllowMonsters());
/* 49 */       spawning.append("Ambient Spawn Limit", world.getAmbientSpawnLimit());
/* 50 */       spawning.append("Animal Spawn Limit", world.getAnimalSpawnLimit());
/* 51 */       spawning.append("Monster Spawn Limit", world.getMonsterSpawnLimit());
/* 52 */       spawning.append("Water Creature Spawn Limit", world.getWaterAnimalSpawnLimit());
/* 53 */       report.append(spawning.getTitle(), spawning);
/*    */       
/* 55 */       DataReport config = new DataReport("Configuration");
/* 56 */       config.append("Difficulty", world.getDifficulty());
/* 57 */       config.append("Max Height", world.getMaxHeight());
/* 58 */       config.append("Sea Level", world.getSeaLevel());
/* 59 */       report.append(config.getTitle(), config);
/*    */       
/* 61 */       DataReport state = new DataReport("State");
/* 62 */       state.append("Spawn Location", world.getSpawnLocation());
/* 63 */       state.append("Full Time", world.getFullTime());
/* 64 */       state.append("Weather Duration", world.getWeatherDuration());
/* 65 */       state.append("Thunder Duration", world.getThunderDuration());
/* 66 */       report.append(state.getTitle(), state);
/*    */       
/* 68 */       DataReport protection = new DataReport("Protection");
/* 69 */       protection.append("PVP?", world.getPVP());
/* 70 */       protection.append("Game Rules", world.getGameRules());
/* 71 */       report.append(protection.getTitle(), protection);
/*    */       
/* 73 */       append(report.getTitle(), report);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukki\\util\report\WorldReport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */