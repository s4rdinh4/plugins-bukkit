/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner.filesystem;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.Log;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.LogFactory;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner.Resource;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
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
/*     */ public class FileSystemScanner
/*     */ {
/*  32 */   private static final Log LOG = LogFactory.getLog(FileSystemScanner.class);
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
/*     */   public Resource[] scanForResources(String path, String prefix, String suffix) throws IOException {
/*  45 */     LOG.debug("Scanning for filesystem resources at '" + path + "' (Prefix: '" + prefix + "', Suffix: '" + suffix + "')");
/*     */     
/*  47 */     if (!(new File(path)).isDirectory()) {
/*  48 */       throw new FlywayException("Invalid filesystem path: " + path);
/*     */     }
/*     */     
/*  51 */     Set<Resource> resources = new TreeSet<Resource>();
/*     */     
/*  53 */     Set<String> resourceNames = findResourceNames(path, prefix, suffix);
/*  54 */     for (String resourceName : resourceNames) {
/*  55 */       resources.add(new FileSystemResource(resourceName));
/*  56 */       LOG.debug("Found filesystem resource: " + resourceName);
/*     */     } 
/*     */     
/*  59 */     return resources.<Resource>toArray(new Resource[resources.size()]);
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
/*     */ 
/*     */   
/*     */   private Set<String> findResourceNames(String path, String prefix, String suffix) throws IOException {
/*  73 */     Set<String> resourceNames = findResourceNamesFromFileSystem(path, new File(path));
/*  74 */     return filterResourceNames(resourceNames, prefix, suffix);
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
/*     */   
/*     */   private Set<String> findResourceNamesFromFileSystem(String scanRootLocation, File folder) throws IOException {
/*  87 */     LOG.debug("Scanning for resources in path: " + folder.getPath() + " (" + scanRootLocation + ")");
/*     */     
/*  89 */     Set<String> resourceNames = new TreeSet<String>();
/*     */     
/*  91 */     File[] files = folder.listFiles();
/*  92 */     for (File file : files) {
/*  93 */       if (file.canRead()) {
/*  94 */         if (file.isDirectory()) {
/*  95 */           resourceNames.addAll(findResourceNamesFromFileSystem(scanRootLocation, file));
/*     */         } else {
/*  97 */           resourceNames.add(file.getPath());
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 102 */     return resourceNames;
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
/*     */   private Set<String> filterResourceNames(Set<String> resourceNames, String prefix, String suffix) {
/* 114 */     Set<String> filteredResourceNames = new TreeSet<String>();
/* 115 */     for (String resourceName : resourceNames) {
/* 116 */       String fileName = resourceName.substring(resourceName.lastIndexOf(File.separator) + 1);
/* 117 */       if (fileName.startsWith(prefix) && fileName.endsWith(suffix) && fileName.length() > (prefix + suffix).length()) {
/*     */         
/* 119 */         filteredResourceNames.add(resourceName); continue;
/*     */       } 
/* 121 */       LOG.debug("Filtering out resource: " + resourceName + " (filename: " + fileName + ")");
/*     */     } 
/*     */     
/* 124 */     return filteredResourceNames;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\interna\\util\scanner\filesystem\FileSystemScanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */