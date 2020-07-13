/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner.classpath;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.UrlUtils;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.Log;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.LogFactory;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import java.net.URLDecoder;
/*    */ import java.util.Set;
/*    */ import java.util.TreeSet;
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
/*    */ public class FileSystemClassPathLocationScanner
/*    */   implements ClassPathLocationScanner
/*    */ {
/* 33 */   private static final Log LOG = LogFactory.getLog(FileSystemClassPathLocationScanner.class);
/*    */   
/*    */   public Set<String> findResourceNames(String location, URL locationUrl) throws IOException {
/* 36 */     String filePath = UrlUtils.toFilePath(locationUrl);
/* 37 */     File folder = new File(filePath);
/* 38 */     if (!folder.isDirectory()) {
/* 39 */       LOG.debug("Skipping path as it is not a directory: " + filePath);
/* 40 */       return new TreeSet<String>();
/*    */     } 
/*    */     
/* 43 */     String classPathRootOnDisk = filePath.substring(0, filePath.length() - location.length());
/* 44 */     if (!classPathRootOnDisk.endsWith("/")) {
/* 45 */       classPathRootOnDisk = classPathRootOnDisk + "/";
/*    */     }
/* 47 */     LOG.debug("Scanning starting at classpath root in filesystem: " + classPathRootOnDisk);
/* 48 */     return findResourceNamesFromFileSystem(classPathRootOnDisk, location, folder);
/*    */   }
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
/*    */   Set<String> findResourceNamesFromFileSystem(String classPathRootOnDisk, String scanRootLocation, File folder) throws IOException {
/* 63 */     LOG.debug("Scanning for resources in path: " + folder.getPath() + " (" + scanRootLocation + ")");
/*    */     
/* 65 */     Set<String> resourceNames = new TreeSet<String>();
/*    */     
/* 67 */     File[] files = folder.listFiles();
/* 68 */     for (File file : files) {
/* 69 */       if (file.canRead()) {
/* 70 */         if (file.isDirectory()) {
/* 71 */           resourceNames.addAll(findResourceNamesFromFileSystem(classPathRootOnDisk, scanRootLocation, file));
/*    */         } else {
/* 73 */           resourceNames.add(toResourceNameOnClasspath(classPathRootOnDisk, file));
/*    */         } 
/*    */       }
/*    */     } 
/*    */     
/* 78 */     return resourceNames;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private String toResourceNameOnClasspath(String classPathRootOnDisk, File file) throws IOException {
/* 90 */     String fileName = URLDecoder.decode(file.toURI().toURL().getFile(), "UTF-8");
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 95 */     return fileName.substring(classPathRootOnDisk.length());
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\interna\\util\scanner\classpath\FileSystemClassPathLocationScanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */