/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.util;
/*    */ 
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.net.URL;
/*    */ import java.net.URLDecoder;
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
/*    */ 
/*    */ public class UrlUtils
/*    */ {
/*    */   public static String toFilePath(URL url) {
/*    */     String filePath;
/*    */     try {
/* 43 */       filePath = URLDecoder.decode(url.getPath(), "UTF-8");
/* 44 */     } catch (UnsupportedEncodingException e) {
/* 45 */       throw new IllegalStateException("Can never happen", e);
/*    */     } 
/*    */     
/* 48 */     if (filePath.endsWith("/")) {
/* 49 */       filePath = filePath.substring(0, filePath.length() - 1);
/*    */     }
/*    */     
/* 52 */     return filePath;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\interna\\util\UrlUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */