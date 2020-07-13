/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner.classpath;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.FileCopyUtils;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner.Resource;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URL;
/*     */ import java.net.URLDecoder;
/*     */ import java.nio.charset.Charset;
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
/*     */ public class ClassPathResource
/*     */   implements Comparable<ClassPathResource>, Resource
/*     */ {
/*     */   private String location;
/*     */   private ClassLoader classLoader;
/*     */   
/*     */   public ClassPathResource(String location, ClassLoader classLoader) {
/*  52 */     this.location = location;
/*  53 */     this.classLoader = classLoader;
/*     */   }
/*     */   
/*     */   public String getLocation() {
/*  57 */     return this.location;
/*     */   }
/*     */   
/*     */   public String getLocationOnDisk() {
/*  61 */     URL url = getUrl();
/*  62 */     if (url == null) {
/*  63 */       throw new FlywayException("Unable to location resource on disk: " + this.location);
/*     */     }
/*     */     try {
/*  66 */       return URLDecoder.decode(url.getPath(), "UTF-8");
/*  67 */     } catch (UnsupportedEncodingException e) {
/*  68 */       throw new FlywayException("Unknown encoding: UTF-8", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private URL getUrl() {
/*  76 */     return this.classLoader.getResource(this.location);
/*     */   }
/*     */   
/*     */   public String loadAsString(String encoding) {
/*     */     try {
/*  81 */       InputStream inputStream = this.classLoader.getResourceAsStream(this.location);
/*  82 */       if (inputStream == null) {
/*  83 */         throw new FlywayException("Unable to obtain inputstream for resource: " + this.location);
/*     */       }
/*  85 */       Reader reader = new InputStreamReader(inputStream, Charset.forName(encoding));
/*     */       
/*  87 */       return FileCopyUtils.copyToString(reader);
/*  88 */     } catch (IOException e) {
/*  89 */       throw new FlywayException("Unable to load resource: " + this.location + " (encoding: " + encoding + ")", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] loadAsBytes() {
/*     */     try {
/*  95 */       InputStream inputStream = this.classLoader.getResourceAsStream(this.location);
/*  96 */       if (inputStream == null) {
/*  97 */         throw new FlywayException("Unable to obtain inputstream for resource: " + this.location);
/*     */       }
/*  99 */       return FileCopyUtils.copyToByteArray(inputStream);
/* 100 */     } catch (IOException e) {
/* 101 */       throw new FlywayException("Unable to load resource: " + this.location, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getFilename() {
/* 106 */     return this.location.substring(this.location.lastIndexOf("/") + 1);
/*     */   }
/*     */   
/*     */   public boolean exists() {
/* 110 */     return (getUrl() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 116 */     if (this == o) return true; 
/* 117 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 119 */     ClassPathResource that = (ClassPathResource)o;
/*     */     
/* 121 */     if (!this.location.equals(that.location)) return false;
/*     */     
/* 123 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 128 */     return this.location.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(ClassPathResource o) {
/* 133 */     return this.location.compareTo(o.location);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\interna\\util\scanner\classpath\ClassPathResource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */