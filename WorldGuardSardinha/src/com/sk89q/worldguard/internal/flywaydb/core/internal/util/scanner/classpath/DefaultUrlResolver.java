/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner.classpath;
/*    */ 
/*    */ import java.io.IOException;
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
/*    */ public class DefaultUrlResolver
/*    */   implements UrlResolver
/*    */ {
/*    */   public URL toStandardJavaUrl(URL url) throws IOException {
/* 26 */     return url;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\interna\\util\scanner\classpath\DefaultUrlResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */