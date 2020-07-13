/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner.classpath;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.ClassUtils;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.FeatureDetector;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.UrlUtils;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.Log;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.LogFactory;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner.Resource;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner.classpath.jboss.JBossVFSv2UrlResolver;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner.classpath.jboss.JBossVFSv3ClassPathLocationScanner;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.net.URL;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
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
/*     */ public class ClassPathScanner
/*     */ {
/*  42 */   private static final Log LOG = LogFactory.getLog(ClassPathScanner.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ClassLoader classLoader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassPathScanner(ClassLoader classLoader) {
/*  55 */     this.classLoader = classLoader;
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
/*     */   public Resource[] scanForResources(String path, String prefix, String suffix) throws IOException {
/*  69 */     LOG.debug("Scanning for classpath resources at '" + path + "' (Prefix: '" + prefix + "', Suffix: '" + suffix + "')");
/*     */     
/*  71 */     Set<Resource> resources = new TreeSet<Resource>();
/*     */     
/*  73 */     Set<String> resourceNames = findResourceNames(path, prefix, suffix);
/*  74 */     for (String resourceName : resourceNames) {
/*  75 */       resources.add(new ClassPathResource(resourceName, this.classLoader));
/*  76 */       LOG.debug("Found resource: " + resourceName);
/*     */     } 
/*     */     
/*  79 */     return resources.<Resource>toArray(new Resource[resources.size()]);
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
/*     */   public Class<?>[] scanForClasses(String location, Class<?> implementedInterface) throws Exception {
/*  93 */     LOG.debug("Scanning for classes at '" + location + "' (Implementing: '" + implementedInterface.getName() + "')");
/*     */     
/*  95 */     List<Class<?>> classes = new ArrayList<Class<?>>();
/*     */     
/*  97 */     Set<String> resourceNames = findResourceNames(location, "", ".class");
/*  98 */     for (String resourceName : resourceNames) {
/*  99 */       String className = toClassName(resourceName);
/* 100 */       Class<?> clazz = this.classLoader.loadClass(className);
/*     */       
/* 102 */       if (Modifier.isAbstract(clazz.getModifiers())) {
/* 103 */         LOG.debug("Skipping abstract class: " + className);
/*     */         
/*     */         continue;
/*     */       } 
/* 107 */       if (!implementedInterface.isAssignableFrom(clazz)) {
/*     */         continue;
/*     */       }
/*     */       
/*     */       try {
/* 112 */         ClassUtils.instantiate(className, this.classLoader);
/* 113 */       } catch (Exception e) {
/* 114 */         throw new FlywayException("Unable to instantiate class: " + className);
/*     */       } 
/*     */       
/* 117 */       classes.add(clazz);
/* 118 */       LOG.debug("Found class: " + className);
/*     */     } 
/*     */     
/* 121 */     return (Class[])classes.<Class<?>[]>toArray((Class<?>[][])new Class[classes.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String toClassName(String resourceName) {
/* 131 */     String nameWithDots = resourceName.replace("/", ".");
/* 132 */     return nameWithDots.substring(0, nameWithDots.length() - ".class".length());
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
/* 146 */     Set<String> resourceNames = new TreeSet<String>();
/*     */     
/* 148 */     List<URL> locationsUrls = getLocationUrlsForPath(path);
/* 149 */     for (URL locationUrl : locationsUrls) {
/* 150 */       LOG.debug("Scanning URL: " + locationUrl.toExternalForm());
/*     */       
/* 152 */       UrlResolver urlResolver = createUrlResolver(locationUrl.getProtocol());
/* 153 */       URL resolvedUrl = urlResolver.toStandardJavaUrl(locationUrl);
/*     */       
/* 155 */       String protocol = resolvedUrl.getProtocol();
/* 156 */       ClassPathLocationScanner classPathLocationScanner = createLocationScanner(protocol);
/* 157 */       if (classPathLocationScanner == null) {
/* 158 */         String scanRoot = UrlUtils.toFilePath(resolvedUrl);
/* 159 */         LOG.warn("Unable to scan location: " + scanRoot + " (unsupported protocol: " + protocol + ")"); continue;
/*     */       } 
/* 161 */       resourceNames.addAll(classPathLocationScanner.findResourceNames(path, resolvedUrl));
/*     */     } 
/*     */ 
/*     */     
/* 165 */     return filterResourceNames(resourceNames, prefix, suffix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<URL> getLocationUrlsForPath(String path) throws IOException {
/* 176 */     List<URL> locationUrls = new ArrayList<URL>();
/*     */     
/* 178 */     if (this.classLoader.getClass().getName().startsWith("com.ibm")) {
/*     */       
/* 180 */       Enumeration<URL> urls = this.classLoader.getResources(path + "/flyway.location");
/* 181 */       if (!urls.hasMoreElements()) {
/* 182 */         throw new FlywayException("Unable to determine URL for classpath location: " + path + " (ClassLoader: " + this.classLoader + ")" + " On WebSphere an empty file named flyway.location must be present on the classpath location for WebSphere to find it!");
/*     */       }
/*     */       
/* 185 */       while (urls.hasMoreElements()) {
/* 186 */         URL url = urls.nextElement();
/* 187 */         locationUrls.add(new URL(URLDecoder.decode(url.toExternalForm(), "UTF-8").replace("/flyway.location", "")));
/*     */       } 
/*     */     } else {
/* 190 */       Enumeration<URL> urls = this.classLoader.getResources(path);
/* 191 */       if (!urls.hasMoreElements()) {
/* 192 */         throw new FlywayException("Unable to determine URL for classpath location: " + path + " (ClassLoader: " + this.classLoader + ")");
/*     */       }
/*     */       
/* 195 */       while (urls.hasMoreElements()) {
/* 196 */         locationUrls.add(urls.nextElement());
/*     */       }
/*     */     } 
/*     */     
/* 200 */     return locationUrls;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private UrlResolver createUrlResolver(String protocol) {
/* 210 */     if ((new FeatureDetector(this.classLoader)).isJBossVFSv2Available() && protocol.startsWith("vfs")) {
/* 211 */       return (UrlResolver)new JBossVFSv2UrlResolver();
/*     */     }
/*     */     
/* 214 */     return new DefaultUrlResolver();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClassPathLocationScanner createLocationScanner(String protocol) {
/* 224 */     if ("file".equals(protocol)) {
/* 225 */       return new FileSystemClassPathLocationScanner();
/*     */     }
/*     */     
/* 228 */     if ("jar".equals(protocol) || "zip".equals(protocol) || "wsjar".equals(protocol))
/*     */     {
/*     */ 
/*     */       
/* 232 */       return new JarFileClassPathLocationScanner();
/*     */     }
/*     */     
/* 235 */     FeatureDetector featureDetector = new FeatureDetector(this.classLoader);
/* 236 */     if (featureDetector.isJBossVFSv3Available() && "vfs".equals(protocol)) {
/* 237 */       return (ClassPathLocationScanner)new JBossVFSv3ClassPathLocationScanner();
/*     */     }
/* 239 */     if (featureDetector.isOsgiFrameworkAvailable() && ("bundle".equals(protocol) || "bundleresource".equals(protocol)))
/*     */     {
/*     */ 
/*     */       
/* 243 */       return new OsgiClassPathLocationScanner();
/*     */     }
/*     */     
/* 246 */     return null;
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
/* 258 */     Set<String> filteredResourceNames = new TreeSet<String>();
/* 259 */     for (String resourceName : resourceNames) {
/* 260 */       String fileName = resourceName.substring(resourceName.lastIndexOf("/") + 1);
/* 261 */       if (fileName.startsWith(prefix) && fileName.endsWith(suffix) && fileName.length() > (prefix + suffix).length()) {
/*     */         
/* 263 */         filteredResourceNames.add(resourceName); continue;
/*     */       } 
/* 265 */       LOG.debug("Filtering out resource: " + resourceName + " (filename: " + fileName + ")");
/*     */     } 
/*     */     
/* 268 */     return filteredResourceNames;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\interna\\util\scanner\classpath\ClassPathScanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */