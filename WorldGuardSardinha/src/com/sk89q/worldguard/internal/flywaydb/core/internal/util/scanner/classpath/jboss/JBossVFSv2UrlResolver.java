/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner.classpath.jboss;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner.classpath.UrlResolver;
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.net.URL;
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
/*    */ public class JBossVFSv2UrlResolver
/*    */   implements UrlResolver
/*    */ {
/*    */   public URL toStandardJavaUrl(URL url) throws IOException {
/*    */     try {
/* 31 */       Class<?> vfsClass = Class.forName("org.jboss.virtual.VFS");
/* 32 */       Class<?> vfsUtilsClass = Class.forName("org.jboss.virtual.VFSUtils");
/* 33 */       Class<?> virtualFileClass = Class.forName("org.jboss.virtual.VirtualFile");
/*    */       
/* 35 */       Method getRootMethod = vfsClass.getMethod("getRoot", new Class[] { URL.class });
/* 36 */       Method getRealURLMethod = vfsUtilsClass.getMethod("getRealURL", new Class[] { virtualFileClass });
/*    */       
/* 38 */       Object root = getRootMethod.invoke(null, new Object[] { url });
/* 39 */       return (URL)getRealURLMethod.invoke(null, new Object[] { root });
/* 40 */     } catch (Exception e) {
/* 41 */       throw new FlywayException("JBoss VFS v2 call failed", e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\interna\\util\scanner\classpath\jboss\JBossVFSv2UrlResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */