/*    */ package com.sk89q.worldguard.bukkit.util.report;
/*    */ 
/*    */ import com.sk89q.worldguard.util.report.DataReport;
/*    */ import java.util.Collection;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.plugin.ServicesManager;
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
/*    */ 
/*    */ 
/*    */ public class ServicesReport
/*    */   extends DataReport
/*    */ {
/*    */   public ServicesReport() {
/* 33 */     super("Services");
/*    */     
/* 35 */     ServicesManager manager = Bukkit.getServer().getServicesManager();
/* 36 */     Collection<Class<?>> services = manager.getKnownServices();
/*    */     
/* 38 */     for (Class<?> service : services) {
/* 39 */       Object provider = manager.load(service);
/* 40 */       if (provider != null)
/* 41 */         append(service.getName(), provider); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukki\\util\report\ServicesReport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */