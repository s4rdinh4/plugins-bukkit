/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner.android;
/*    */ 
/*    */ import android.content.Context;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.api.android.ContextHolder;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.ClassUtils;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.Log;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.LogFactory;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner.Resource;
/*    */ import dalvik.system.DexFile;
/*    */ import dalvik.system.PathClassLoader;
/*    */ import java.lang.reflect.Modifier;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Enumeration;
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
/*    */ public class AndroidScanner
/*    */ {
/* 37 */   private static final Log LOG = LogFactory.getLog(AndroidScanner.class);
/*    */   
/*    */   private final Context context;
/*    */   
/*    */   private final PathClassLoader classLoader;
/*    */   
/*    */   public AndroidScanner(ClassLoader classLoader) {
/* 44 */     this.classLoader = (PathClassLoader)classLoader;
/* 45 */     this.context = ContextHolder.getContext();
/* 46 */     if (this.context == null) {
/* 47 */       throw new FlywayException("Unable to scan for Migrations! Context not set. Within an activity you can fix this with org.flywaydb.core.api.android.ContextHolder.setContext(this);");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Resource[] scanForResources(String path, String prefix, String suffix) throws Exception {
/* 54 */     List<Resource> resources = new ArrayList<Resource>();
/*    */     
/* 56 */     for (String asset : this.context.getAssets().list(path)) {
/* 57 */       if (asset.startsWith(prefix) && asset.endsWith(suffix) && asset.length() > (prefix + suffix).length()) {
/*    */         
/* 59 */         resources.add(new AndroidResource(this.context.getAssets(), path, asset));
/*    */       } else {
/* 61 */         LOG.debug("Filtering out asset: " + asset);
/*    */       } 
/*    */     } 
/*    */     
/* 65 */     return resources.<Resource>toArray(new Resource[resources.size()]);
/*    */   }
/*    */   
/*    */   public Class<?>[] scanForClasses(String path, Class<?> implementedInterface) throws Exception {
/* 69 */     String pkg = path.replace("/", ".");
/*    */     
/* 71 */     List<Class<?>> classes = new ArrayList<Class<?>>();
/*    */     
/* 73 */     DexFile dex = new DexFile((this.context.getApplicationInfo()).sourceDir);
/* 74 */     Enumeration<String> entries = dex.entries();
/* 75 */     while (entries.hasMoreElements()) {
/* 76 */       String className = entries.nextElement();
/* 77 */       if (className.startsWith(pkg)) {
/* 78 */         Class<?> clazz = this.classLoader.loadClass(className);
/* 79 */         if (Modifier.isAbstract(clazz.getModifiers())) {
/* 80 */           LOG.debug("Skipping abstract class: " + className);
/*    */           
/*    */           continue;
/*    */         } 
/* 84 */         if (!implementedInterface.isAssignableFrom(clazz)) {
/*    */           continue;
/*    */         }
/*    */         
/*    */         try {
/* 89 */           ClassUtils.instantiate(className, (ClassLoader)this.classLoader);
/* 90 */         } catch (Exception e) {
/* 91 */           throw new FlywayException("Unable to instantiate class: " + className);
/*    */         } 
/*    */         
/* 94 */         classes.add(clazz);
/* 95 */         LOG.debug("Found class: " + className);
/*    */       } 
/*    */     } 
/* 98 */     return (Class[])classes.<Class<?>[]>toArray((Class<?>[][])new Class[classes.size()]);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\interna\\util\scanner\android\AndroidScanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */