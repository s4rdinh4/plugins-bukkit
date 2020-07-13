/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.FeatureDetector;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.Location;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner.android.AndroidScanner;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner.classpath.ClassPathScanner;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner.filesystem.FileSystemScanner;
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
/*    */ public class Scanner
/*    */ {
/*    */   private final ClassLoader classLoader;
/*    */   
/*    */   public Scanner(ClassLoader classLoader) {
/* 31 */     this.classLoader = classLoader;
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
/*    */   public Resource[] scanForResources(Location location, String prefix, String suffix) throws Exception {
/* 44 */     if (location.isFileSystem()) {
/* 45 */       return (new FileSystemScanner()).scanForResources(location.getPath(), prefix, suffix);
/*    */     }
/*    */     
/* 48 */     if ((new FeatureDetector(this.classLoader)).isAndroidAvailable()) {
/* 49 */       return (new AndroidScanner(this.classLoader)).scanForResources(location.getPath(), prefix, suffix);
/*    */     }
/*    */     
/* 52 */     return (new ClassPathScanner(this.classLoader)).scanForResources(location.getPath(), prefix, suffix);
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
/*    */   public Class<?>[] scanForClasses(Location location, Class<?> implementedInterface) throws Exception {
/* 67 */     if ((new FeatureDetector(this.classLoader)).isAndroidAvailable()) {
/* 68 */       return (new AndroidScanner(this.classLoader)).scanForClasses(location.getPath(), implementedInterface);
/*    */     }
/*    */     
/* 71 */     return (new ClassPathScanner(this.classLoader)).scanForClasses(location.getPath(), implementedInterface);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\interna\\util\scanner\Scanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */