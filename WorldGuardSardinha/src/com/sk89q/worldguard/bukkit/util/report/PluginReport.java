/*    */ package com.sk89q.worldguard.bukkit.util.report;
/*    */ 
/*    */ import com.sk89q.worldguard.util.report.DataReport;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.plugin.Plugin;
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
/*    */ public class PluginReport
/*    */   extends DataReport
/*    */ {
/*    */   public PluginReport() {
/* 29 */     super("Plugins");
/*    */     
/* 31 */     Plugin[] plugins = Bukkit.getServer().getPluginManager().getPlugins();
/*    */     
/* 33 */     append("Plugin Count", plugins.length);
/*    */     
/* 35 */     for (Plugin plugin : plugins) {
/* 36 */       DataReport report = new DataReport("Plugin: " + plugin.getName());
/* 37 */       report.append("Enabled?", plugin.isEnabled());
/* 38 */       report.append("Full Name", plugin.getDescription().getFullName());
/* 39 */       report.append("Version", plugin.getDescription().getVersion());
/* 40 */       report.append("Website", plugin.getDescription().getWebsite());
/* 41 */       report.append("Description", plugin.getDescription().getDescription());
/* 42 */       report.append("Authors", plugin.getDescription().getAuthors());
/* 43 */       report.append("Load Before", plugin.getDescription().getLoadBefore());
/* 44 */       report.append("Dependencies", plugin.getDescription().getDepend());
/* 45 */       report.append("Soft Dependencies", plugin.getDescription().getSoftDepend());
/* 46 */       report.append("Folder", plugin.getDataFolder().getAbsoluteFile());
/* 47 */       report.append("Entry Point", plugin.getDescription().getMain());
/* 48 */       report.append("Class", plugin.getClass().getName());
/* 49 */       report.append("Class Source", plugin.getClass().getProtectionDomain().getCodeSource().getLocation());
/* 50 */       append(report.getTitle(), report);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukki\\util\report\PluginReport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */