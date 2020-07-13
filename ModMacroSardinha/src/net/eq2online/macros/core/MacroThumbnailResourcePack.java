/*     */ package net.eq2online.macros.core;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import cvm;
/*     */ import cwi;
/*     */ import cwk;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import oa;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MacroThumbnailResourcePack
/*     */   implements cvm
/*     */ {
/*  29 */   public static final Set<String> availableNamespaces = new HashSet<String>(Arrays.asList(new String[] { "macros" }));
/*     */   
/*  31 */   private final Map<oa, File> fileResources = Maps.newHashMap();
/*     */   
/*     */   private final File baseDir;
/*     */   
/*     */   public MacroThumbnailResourcePack(File dirPath) {
/*  36 */     this.baseDir = dirPath;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream a(oa location) throws IOException {
/*  42 */     File file = this.fileResources.get(location);
/*  43 */     if (file != null && file.exists()) {
/*  44 */       return new FileInputStream(file);
/*     */     }
/*  46 */     InputStream jarInputStream = getResourceAsStreamFromJar(location);
/*  47 */     if (jarInputStream != null) {
/*  48 */       return jarInputStream;
/*     */     }
/*  50 */     throw new FileNotFoundException(location.a());
/*     */   }
/*     */ 
/*     */   
/*     */   private InputStream getResourceAsStreamFromJar(oa location) {
/*  55 */     return MacroThumbnailResourcePack.class.getResourceAsStream("/dynamicassets/" + location.b() + "/" + location.a());
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFileFor(oa location) {
/*  60 */     String path = location.a();
/*  61 */     path = path.substring(path.indexOf('/') + 1);
/*     */     
/*  63 */     addFileResource(location, new File(this.baseDir, path));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFileResource(oa location, File file) {
/*  68 */     this.fileResources.put(location, file);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean b(oa par1ResourceLocation) {
/*  74 */     return (getResourceAsStreamFromJar(par1ResourceLocation) != null || this.fileResources.containsKey(par1ResourceLocation.toString()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> c() {
/*  80 */     return availableNamespaces;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public cwi a(cwk par1MetadataSerializer, String par2Str) throws IOException {
/*  86 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferedImage a() throws IOException {
/*  92 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String b() {
/*  98 */     return "Macros Icon Thumbnails";
/*     */   }
/*     */ 
/*     */   
/*     */   protected static String getRelativePath(File left, File right) {
/* 103 */     return left.toURI().relativize(right.toURI()).getPath();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\MacroThumbnailResourcePack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */