/*    */ package com.sk89q.worldguard.bukkit.util.report;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.sk89q.worldguard.util.report.DataReport;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.entity.Entity;
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
/*    */ public class PerformanceReport
/*    */   extends DataReport
/*    */ {
/*    */   public PerformanceReport() {
/* 34 */     super("Performance");
/*    */     
/* 36 */     List<World> worlds = Bukkit.getServer().getWorlds();
/*    */     
/* 38 */     append("World Count", worlds.size());
/*    */     
/* 40 */     for (World world : worlds) {
/* 41 */       int loadedChunkCount = (world.getLoadedChunks()).length;
/*    */       
/* 43 */       DataReport report = new DataReport("World: " + world.getName());
/* 44 */       report.append("Keep in Memory?", world.getKeepSpawnInMemory());
/* 45 */       report.append("Entity Count", world.getEntities().size());
/* 46 */       report.append("Chunk Count", loadedChunkCount);
/*    */       
/* 48 */       Map<Class<? extends Entity>, Integer> entityCounts = Maps.newHashMap();
/*    */ 
/*    */       
/* 51 */       for (Entity entity : world.getEntities()) {
/* 52 */         Class<? extends Entity> cls = (Class)entity.getClass();
/*    */         
/* 54 */         if (entityCounts.containsKey(cls)) {
/* 55 */           entityCounts.put(cls, Integer.valueOf(((Integer)entityCounts.get(cls)).intValue() + 1)); continue;
/*    */         } 
/* 57 */         entityCounts.put(cls, Integer.valueOf(1));
/*    */       } 
/*    */ 
/*    */ 
/*    */       
/* 62 */       DataReport entities = new DataReport("Entity Distribution");
/* 63 */       for (Map.Entry<Class<? extends Entity>, Integer> entry : entityCounts.entrySet()) {
/* 64 */         entities.append(((Class)entry.getKey()).getSimpleName(), "%d [%f/chunk]", new Object[] { entry
/* 65 */               .getValue(), 
/* 66 */               Float.valueOf((float)(((Integer)entry.getValue()).intValue() / loadedChunkCount)) });
/*    */       } 
/* 68 */       report.append(entities.getTitle(), entities);
/*    */       
/* 70 */       append(report.getTitle(), report);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukki\\util\report\PerformanceReport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */