/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner.classpath;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import java.util.Enumeration;
/*    */ import java.util.Set;
/*    */ import java.util.TreeSet;
/*    */ import org.osgi.framework.Bundle;
/*    */ import org.osgi.framework.FrameworkUtil;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OsgiClassPathLocationScanner
/*    */   implements ClassPathLocationScanner
/*    */ {
/*    */   public Set<String> findResourceNames(String location, URL locationUrl) throws IOException {
/* 39 */     Set<String> resourceNames = new TreeSet<String>();
/*    */     
/* 41 */     Bundle bundle = FrameworkUtil.getBundle(getClass());
/*    */     
/* 43 */     Enumeration<URL> entries = bundle.findEntries(locationUrl.getPath(), "*", true);
/*    */     
/* 45 */     if (entries != null) {
/* 46 */       while (entries.hasMoreElements()) {
/* 47 */         URL entry = entries.nextElement();
/* 48 */         String resourceName = getPathWithoutLeadingSlash(entry);
/*    */         
/* 50 */         resourceNames.add(resourceName);
/*    */       } 
/*    */     }
/*    */     
/* 54 */     return resourceNames;
/*    */   }
/*    */   
/*    */   private String getPathWithoutLeadingSlash(URL entry) {
/* 58 */     String path = entry.getPath();
/*    */     
/* 60 */     return path.startsWith("/") ? path.substring(1) : path;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\interna\\util\scanner\classpath\OsgiClassPathLocationScanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */