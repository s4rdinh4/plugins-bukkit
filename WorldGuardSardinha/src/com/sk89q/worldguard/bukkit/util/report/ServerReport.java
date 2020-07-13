/*    */ package com.sk89q.worldguard.bukkit.util.report;
/*    */ 
/*    */ import com.sk89q.worldguard.bukkit.BukkitUtil;
/*    */ import com.sk89q.worldguard.util.report.DataReport;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Server;
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
/*    */ public class ServerReport
/*    */   extends DataReport
/*    */ {
/*    */   public ServerReport() {
/* 30 */     super("Server Information");
/*    */     
/* 32 */     Server server = Bukkit.getServer();
/*    */     
/* 34 */     append("Server ID", server.getServerId());
/* 35 */     append("Server Name", server.getServerName());
/* 36 */     append("Bukkit Version", server.getBukkitVersion());
/* 37 */     append("Implementation", server.getVersion());
/* 38 */     append("Player Count", "%d/%d", new Object[] { Integer.valueOf(BukkitUtil.getOnlinePlayers().size()), Integer.valueOf(server.getMaxPlayers()) });
/*    */     
/* 40 */     append("Server Class Source", server.getClass().getProtectionDomain().getCodeSource().getLocation());
/*    */     
/* 42 */     DataReport spawning = new DataReport("Spawning");
/* 43 */     spawning.append("Ambient Spawn Limit", server.getAmbientSpawnLimit());
/* 44 */     spawning.append("Animal Spawn Limit", server.getAnimalSpawnLimit());
/* 45 */     spawning.append("Monster Spawn Limit", server.getMonsterSpawnLimit());
/* 46 */     spawning.append("Ticks per Animal Spawn", server.getTicksPerAnimalSpawns());
/* 47 */     spawning.append("Ticks per Monster Spawn", server.getTicksPerMonsterSpawns());
/* 48 */     append(spawning.getTitle(), spawning);
/*    */     
/* 50 */     DataReport config = new DataReport("Configuration");
/* 51 */     config.append("Nether Enabled?", server.getAllowNether());
/* 52 */     config.append("The End Enabled?", server.getAllowEnd());
/* 53 */     config.append("Generate Structures?", server.getGenerateStructures());
/* 54 */     config.append("Flight Allowed?", server.getAllowFlight());
/* 55 */     config.append("Connection Throttle", server.getConnectionThrottle());
/* 56 */     config.append("Idle Timeout", server.getIdleTimeout());
/* 57 */     config.append("Shutdown Message", server.getShutdownMessage());
/* 58 */     config.append("Default Game Mode", server.getDefaultGameMode());
/* 59 */     config.append("Main World Type", server.getWorldType());
/* 60 */     config.append("View Distance", server.getViewDistance());
/* 61 */     append(config.getTitle(), config);
/*    */     
/* 63 */     DataReport protection = new DataReport("Protection");
/* 64 */     protection.append("Spawn Radius", server.getSpawnRadius());
/* 65 */     append(protection.getTitle(), protection);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukki\\util\report\ServerReport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */