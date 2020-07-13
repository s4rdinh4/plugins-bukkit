/*    */ package com.sk89q.worldguard.bukkit.util.report;
/*    */ 
/*    */ import com.sk89q.worldguard.blacklist.Blacklist;
/*    */ import com.sk89q.worldguard.bukkit.WorldConfiguration;
/*    */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*    */ import com.sk89q.worldguard.protection.managers.RegionManager;
/*    */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*    */ import com.sk89q.worldguard.util.report.DataReport;
/*    */ import com.sk89q.worldguard.util.report.RegionReport;
/*    */ import com.sk89q.worldguard.util.report.ShallowObjectReport;
/*    */ import java.util.List;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.World;
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
/*    */ public class ConfigReport
/*    */   extends DataReport
/*    */ {
/*    */   public ConfigReport(WorldGuardPlugin plugin) {
/* 38 */     super("WorldGuard Configuration");
/*    */     
/* 40 */     List<World> worlds = Bukkit.getServer().getWorlds();
/*    */     
/* 42 */     append("Configuration", new ShallowObjectReport("Configuration", plugin.getGlobalStateManager()));
/*    */     
/* 44 */     for (World world : worlds) {
/* 45 */       WorldConfiguration config = plugin.getGlobalStateManager().get(world);
/*    */       
/* 47 */       DataReport report = new DataReport("World: " + world.getName());
/* 48 */       report.append("UUID", world.getUID());
/* 49 */       report.append("Configuration", new ShallowObjectReport("Configuration", config));
/*    */       
/* 51 */       Blacklist blacklist = config.getBlacklist();
/* 52 */       if (blacklist != null) {
/* 53 */         DataReport section = new DataReport("Blacklist");
/* 54 */         section.append("Rule Count", blacklist.getItemCount());
/* 55 */         section.append("Whitelist Mode?", blacklist.isWhitelist());
/* 56 */         report.append(section.getTitle(), section);
/*    */       } else {
/* 58 */         report.append("Blacklist", "<Disabled>");
/*    */       } 
/*    */       
/* 61 */       RegionManager regions = plugin.getRegionContainer().get(world);
/* 62 */       if (regions != null) {
/* 63 */         DataReport section = new DataReport("Regions");
/* 64 */         section.append("Region Count", regions.size());
/*    */         
/* 66 */         ProtectedRegion global = regions.getRegion("__global__");
/* 67 */         if (global != null) {
/* 68 */           section.append("__global__", new RegionReport(global));
/*    */         } else {
/* 70 */           section.append("__global__", "<Undefined>");
/*    */         } 
/*    */         
/* 73 */         report.append(section.getTitle(), section);
/*    */       } else {
/* 75 */         report.append("Regions", "<Disabled>");
/*    */       } 
/*    */       
/* 78 */       append(report.getTitle(), report);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukki\\util\report\ConfigReport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */