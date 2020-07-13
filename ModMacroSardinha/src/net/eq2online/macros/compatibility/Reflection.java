/*     */ package net.eq2online.macros.compatibility;
/*     */ 
/*     */ import ckx;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.LinkedList;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipInputStream;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.scripting.IErrorLogger;
/*     */ import net.minecraft.client.ClientBrandRetriever;
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
/*     */ public class Reflection
/*     */ {
/*  32 */   private static Field fieldModifiers = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean forgeModLoader = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  42 */     forgeModLoader = ClientBrandRetriever.getClientModName().contains("fml");
/*     */ 
/*     */     
/*     */     try {
/*  46 */       fieldModifiers = Field.class.getDeclaredField("modifiers");
/*  47 */       fieldModifiers.setAccessible(true);
/*     */     }
/*  49 */     catch (Throwable throwable) {}
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
/*     */   public static void setPrivateValue(Class<?> instanceClass, Object instance, String fieldName, String obfuscatedFieldName, String seargeFieldName, Object value) throws IllegalArgumentException, SecurityException, NoSuchFieldException {
/*  62 */     setPrivateValueInternal(instanceClass, instance, getObfuscatedFieldName(fieldName, obfuscatedFieldName, seargeFieldName), value);
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
/*     */   public static void setPrivateValue(Class<?> instanceClass, Object instance, String fieldName, Object value) throws IllegalArgumentException, SecurityException, NoSuchFieldException {
/*  75 */     setPrivateValueInternal(instanceClass, instance, fieldName, value);
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
/*     */   public static <T> T getPrivateValue(Class<?> instanceClass, Object instance, String fieldName, String obfuscatedFieldName, String seargeFieldName) throws IllegalArgumentException, SecurityException, NoSuchFieldException {
/*  89 */     return (T)getPrivateValueInternal(instanceClass, instance, getObfuscatedFieldName(fieldName, obfuscatedFieldName, seargeFieldName));
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
/*     */   public static <T> T getPrivateValue(Class<?> instanceClass, Object instance, String fieldName) throws IllegalArgumentException, SecurityException, NoSuchFieldException {
/* 103 */     return (T)getPrivateValueInternal(instanceClass, instance, fieldName);
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
/*     */   private static String getObfuscatedFieldName(String fieldName, String obfuscatedFieldName, String seargeFieldName) {
/* 115 */     if (forgeModLoader) return seargeFieldName; 
/* 116 */     return !ckx.a().getClass().getSimpleName().equals("Tessellator") ? obfuscatedFieldName : fieldName;
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
/*     */   private static void setPrivateValueInternal(Class<?> instanceClass, Object instance, String fieldName, Object value) throws IllegalArgumentException, SecurityException, NoSuchFieldException {
/*     */     try {
/* 131 */       Field field = instanceClass.getDeclaredField(fieldName);
/* 132 */       int fieldFieldModifiers = fieldModifiers.getInt(field);
/*     */       
/* 134 */       if ((fieldFieldModifiers & 0x10) != 0)
/*     */       {
/* 136 */         fieldModifiers.setInt(field, fieldFieldModifiers & 0xFFFFFFEF);
/*     */       }
/*     */       
/* 139 */       field.setAccessible(true);
/* 140 */       field.set(instance, value);
/*     */     }
/* 142 */     catch (IllegalAccessException illegalAccessException) {}
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
/*     */   public static Object getPrivateValueInternal(Class<?> instanceClass, Object instance, String fieldName) throws IllegalArgumentException, SecurityException, NoSuchFieldException {
/*     */     try {
/* 157 */       Field field = instanceClass.getDeclaredField(fieldName);
/* 158 */       field.setAccessible(true);
/* 159 */       return field.get(instance);
/*     */     }
/* 161 */     catch (IllegalAccessException illegalaccessexception) {
/*     */       
/* 163 */       return null;
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
/*     */   
/*     */   public static File getPackagePath(Class<?> packageClass) throws MalformedURLException, URISyntaxException, UnsupportedEncodingException {
/* 176 */     File packagePath = null;
/*     */     
/* 178 */     URL protectionDomainLocation = packageClass.getProtectionDomain().getCodeSource().getLocation();
/* 179 */     if (protectionDomainLocation != null) {
/*     */       
/* 181 */       if (protectionDomainLocation.toString().indexOf('!') > -1 && protectionDomainLocation.toString().startsWith("jar:"))
/*     */       {
/* 183 */         protectionDomainLocation = new URL(protectionDomainLocation.toString().substring(4, protectionDomainLocation.toString().indexOf('!')));
/*     */       }
/*     */       
/* 186 */       packagePath = new File(protectionDomainLocation.toURI());
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 191 */       String reflectionClassPath = Reflection.class.getResource("/" + Reflection.class.getName().replace('.', '/') + ".class").getPath();
/*     */       
/* 193 */       if (reflectionClassPath.indexOf('!') > -1) {
/*     */         
/* 195 */         reflectionClassPath = URLDecoder.decode(reflectionClassPath, "UTF-8");
/* 196 */         packagePath = new File(reflectionClassPath.substring(5, reflectionClassPath.indexOf('!')));
/*     */       } 
/*     */     } 
/*     */     
/* 200 */     if (packagePath != null && packagePath.isFile() && packagePath.getName().endsWith(".class")) {
/*     */       
/* 202 */       String discoveredPath = "";
/* 203 */       String absolutePath = packagePath.getAbsolutePath();
/* 204 */       String classPath = System.getProperty("java.class.path");
/* 205 */       String classPathSeparator = System.getProperty("path.separator");
/* 206 */       String[] classPathEntries = classPath.split(classPathSeparator);
/*     */       
/* 208 */       for (String classPathEntry : classPathEntries) {
/*     */ 
/*     */         
/*     */         try {
/* 212 */           String classPathPart = (new File(classPathEntry)).getAbsolutePath();
/* 213 */           if (absolutePath.startsWith(classPathPart) && classPathPart.length() > discoveredPath.length()) {
/* 214 */             discoveredPath = classPathPart;
/*     */           }
/* 216 */         } catch (Exception exception) {}
/*     */       } 
/*     */       
/* 219 */       if (discoveredPath.length() > 0)
/*     */       {
/* 221 */         packagePath = new File(discoveredPath);
/*     */       }
/*     */     } 
/*     */     
/* 225 */     return packagePath;
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
/*     */   public static <T> LinkedList<Class<? extends T>> getSubclassesFor(Class<T> superClass, Class<?> packageClass, String prefix, IErrorLogger logger) {
/*     */     try {
/* 238 */       File packagePath = getPackagePath(packageClass);
/*     */       
/* 240 */       if (packagePath != null)
/*     */       {
/* 242 */         LinkedList<Class<? extends T>> classes = new LinkedList<Class<? extends T>>();
/* 243 */         ClassLoader classloader = superClass.getClassLoader();
/*     */         
/* 245 */         if (packagePath.isDirectory()) {
/*     */           
/* 247 */           enumerateDirectory(prefix, superClass, classloader, classes, packagePath, logger);
/*     */         }
/* 249 */         else if (packagePath.isFile() && (packagePath.getName().endsWith(".jar") || packagePath.getName().endsWith(".zip") || packagePath.getName().endsWith(".litemod"))) {
/*     */           
/* 251 */           enumerateCompressedPackage(prefix, superClass, classloader, classes, packagePath, logger);
/*     */         } 
/*     */         
/* 254 */         return classes;
/*     */       }
/*     */     
/* 257 */     } catch (Throwable th) {
/*     */       
/* 259 */       Log.printStackTrace(th);
/* 260 */       if (logger != null && th.getMessage() != null) logger.logError(th.getClass().getSimpleName() + " " + th.getMessage());
/*     */     
/*     */     } 
/* 263 */     return new LinkedList<Class<? extends T>>();
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
/*     */   protected static <T> void enumerateCompressedPackage(String prefix, Class<T> superClass, ClassLoader classloader, LinkedList<Class<? extends T>> classes, File packagePath, IErrorLogger logger) throws FileNotFoundException, IOException {
/* 276 */     FileInputStream fileinputstream = new FileInputStream(packagePath);
/* 277 */     ZipInputStream zipinputstream = new ZipInputStream(fileinputstream);
/*     */     
/* 279 */     ZipEntry zipentry = null;
/*     */ 
/*     */     
/*     */     do {
/* 283 */       zipentry = zipinputstream.getNextEntry();
/*     */       
/* 285 */       if (zipentry == null || !zipentry.getName().endsWith(".class"))
/*     */         continue; 
/* 287 */       String classFileName = zipentry.getName();
/* 288 */       String className = (classFileName.lastIndexOf('/') > -1) ? classFileName.substring(classFileName.lastIndexOf('/') + 1) : classFileName;
/*     */       
/* 290 */       if (prefix != null && !className.startsWith(prefix)) {
/*     */         continue;
/*     */       }
/*     */       try {
/* 294 */         String fullClassName = classFileName.substring(0, classFileName.length() - 6).replaceAll("/", ".");
/* 295 */         checkAndAddClass(classloader, superClass, classes, fullClassName, logger);
/*     */       }
/* 297 */       catch (Exception exception) {}
/*     */ 
/*     */     
/*     */     }
/* 301 */     while (zipentry != null);
/*     */     
/* 303 */     fileinputstream.close();
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
/*     */   private static <T> void enumerateDirectory(String prefix, Class<T> superClass, ClassLoader classloader, LinkedList<Class<? extends T>> classes, File packagePath, IErrorLogger logger) {
/* 317 */     enumerateDirectory(prefix, superClass, classloader, classes, packagePath, "", logger);
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
/*     */   private static <T> void enumerateDirectory(String prefix, Class<T> superClass, ClassLoader classloader, LinkedList<Class<? extends T>> classes, File packagePath, String packageName, IErrorLogger logger) {
/* 331 */     File[] classFiles = packagePath.listFiles();
/*     */     
/* 333 */     for (File classFile : classFiles) {
/*     */       
/* 335 */       if (classFile.isDirectory()) {
/*     */         
/* 337 */         enumerateDirectory(prefix, superClass, classloader, classes, classFile, packageName + classFile.getName() + ".", logger);
/*     */ 
/*     */       
/*     */       }
/* 341 */       else if (classFile.getName().endsWith(".class") && (prefix == null || classFile.getName().startsWith(prefix))) {
/*     */         
/* 343 */         String classFileName = classFile.getName();
/* 344 */         String className = packageName + classFileName.substring(0, classFileName.length() - 6);
/* 345 */         checkAndAddClass(classloader, superClass, classes, className, logger);
/*     */       } 
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
/*     */ 
/*     */   
/*     */   protected static <T> void checkAndAddClass(ClassLoader classloader, Class<T> superClass, LinkedList<Class<? extends T>> classes, String className, IErrorLogger logger) {
/* 360 */     if (className.indexOf('$') > -1) {
/*     */       return;
/*     */     }
/*     */     try {
/* 364 */       Class<?> subClass = classloader.loadClass(className);
/*     */       
/* 366 */       if (subClass != null && !superClass.equals(subClass) && superClass.isAssignableFrom(subClass) && !classes.contains(subClass))
/*     */       {
/* 368 */         classes.add(subClass);
/*     */       }
/*     */     }
/* 371 */     catch (Throwable throwable) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\compatibility\Reflection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */