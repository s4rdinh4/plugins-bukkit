/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner.classpath;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.JarURLConnection;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarFile;
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
/*     */ public class JarFileClassPathLocationScanner
/*     */   implements ClassPathLocationScanner
/*     */ {
/*     */   public Set<String> findResourceNames(String location, URL locationUrl) throws IOException {
/*  34 */     JarFile jarFile = getJarFromUrl(locationUrl);
/*     */     
/*     */     try {
/*  37 */       return findResourceNamesFromJarFile(jarFile, location);
/*     */     } finally {
/*  39 */       jarFile.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JarFile getJarFromUrl(URL locationUrl) throws IOException {
/*  51 */     URLConnection con = locationUrl.openConnection();
/*  52 */     if (con instanceof JarURLConnection) {
/*     */       
/*  54 */       JarURLConnection jarCon = (JarURLConnection)con;
/*  55 */       jarCon.setUseCaches(false);
/*  56 */       return jarCon.getJarFile();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  63 */     String urlFile = locationUrl.getFile();
/*     */     
/*  65 */     int separatorIndex = urlFile.indexOf("!/");
/*  66 */     if (separatorIndex != -1) {
/*  67 */       String jarFileUrl = urlFile.substring(0, separatorIndex);
/*  68 */       if (jarFileUrl.startsWith("file:")) {
/*     */         try {
/*  70 */           return new JarFile((new URL(jarFileUrl)).toURI().getSchemeSpecificPart());
/*  71 */         } catch (URISyntaxException ex) {
/*     */           
/*  73 */           return new JarFile(jarFileUrl.substring("file:".length()));
/*     */         } 
/*     */       }
/*  76 */       return new JarFile(jarFileUrl);
/*     */     } 
/*     */     
/*  79 */     return new JarFile(urlFile);
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
/*     */   private Set<String> findResourceNamesFromJarFile(JarFile jarFile, String location) throws IOException {
/*  91 */     location = location + (location.endsWith("/") ? "" : "/");
/*  92 */     Set<String> resourceNames = new TreeSet<String>();
/*     */     
/*  94 */     Enumeration<JarEntry> entries = jarFile.entries();
/*  95 */     while (entries.hasMoreElements()) {
/*  96 */       String entryName = ((JarEntry)entries.nextElement()).getName();
/*  97 */       if (entryName.startsWith(location)) {
/*  98 */         resourceNames.add(entryName);
/*     */       }
/*     */     } 
/*     */     
/* 102 */     return resourceNames;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\interna\\util\scanner\classpath\JarFileClassPathLocationScanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */