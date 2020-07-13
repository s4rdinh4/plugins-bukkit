/*    */ package com.sk89q.worldguard.protection.managers.storage.file;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.sk89q.worldguard.protection.managers.storage.RegionDatabase;
/*    */ import com.sk89q.worldguard.protection.managers.storage.RegionDriver;
/*    */ import com.sk89q.worldguard.protection.managers.storage.StorageException;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DirectoryYamlDriver
/*    */   implements RegionDriver
/*    */ {
/*    */   private final File rootDir;
/*    */   private final String filename;
/*    */   
/*    */   public DirectoryYamlDriver(File rootDir, String filename) {
/* 49 */     Preconditions.checkNotNull(rootDir);
/* 50 */     Preconditions.checkNotNull(filename);
/* 51 */     this.rootDir = rootDir;
/* 52 */     this.filename = filename;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private File getPath(String id) {
/* 62 */     Preconditions.checkNotNull(id);
/*    */     
/* 64 */     File f = new File(this.rootDir, id + File.separator + this.filename);
/*    */     try {
/* 66 */       f.getCanonicalPath();
/* 67 */       return f;
/* 68 */     } catch (IOException e) {
/* 69 */       throw new IllegalArgumentException("Invalid file path for the world's regions file");
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public RegionDatabase get(String id) {
/* 75 */     Preconditions.checkNotNull(id);
/*    */     
/* 77 */     File file = getPath(id);
/*    */     
/* 79 */     return new YamlRegionFile(id, file);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<RegionDatabase> getAll() throws StorageException {
/* 84 */     List<RegionDatabase> stores = new ArrayList<RegionDatabase>();
/*    */     
/* 86 */     File[] files = this.rootDir.listFiles();
/* 87 */     if (files != null) {
/* 88 */       for (File dir : files) {
/* 89 */         if (dir.isDirectory() && (new File(dir, "regions.yml")).isFile()) {
/* 90 */           stores.add(new YamlRegionFile(dir.getName(), getPath(dir.getName())));
/*    */         }
/*    */       } 
/*    */     }
/*    */     
/* 95 */     return stores;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\storage\file\DirectoryYamlDriver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */