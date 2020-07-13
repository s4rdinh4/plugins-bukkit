/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.util;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.StringReader;
/*     */ import java.util.Properties;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PropertiesUtils
/*     */ {
/*     */   public static Properties loadPropertiesFromString(String propertiesData) throws IOException {
/*  44 */     Properties properties = new Properties();
/*     */     
/*  46 */     BufferedReader in = new BufferedReader(new StringReader(propertiesData));
/*     */     while (true) {
/*  48 */       String line = in.readLine();
/*  49 */       if (line == null) {
/*  50 */         return properties;
/*     */       }
/*  52 */       line = StringUtils.trimLeadingWhitespace(line);
/*  53 */       if (line.length() > 0) {
/*  54 */         char firstChar = line.charAt(0);
/*  55 */         if (firstChar != '#' && firstChar != '!') {
/*  56 */           while (endsWithContinuationMarker(line)) {
/*  57 */             String nextLine = in.readLine();
/*  58 */             line = line.substring(0, line.length() - 1);
/*  59 */             if (nextLine != null) {
/*  60 */               line = line + StringUtils.trimLeadingWhitespace(nextLine);
/*     */             }
/*     */           } 
/*  63 */           int separatorIndex = line.indexOf("=");
/*  64 */           if (separatorIndex == -1) {
/*  65 */             separatorIndex = line.indexOf(":");
/*     */           }
/*  67 */           String key = (separatorIndex != -1) ? line.substring(0, separatorIndex) : line;
/*  68 */           String value = (separatorIndex != -1) ? line.substring(separatorIndex + 1) : "";
/*  69 */           key = StringUtils.trimTrailingWhitespace(key);
/*  70 */           value = StringUtils.trimLeadingWhitespace(value);
/*  71 */           properties.put(unescape(key), unescape(value));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean endsWithContinuationMarker(String line) {
/*  78 */     boolean evenSlashCount = true;
/*  79 */     int index = line.length() - 1;
/*  80 */     while (index >= 0 && line.charAt(index) == '\\') {
/*  81 */       evenSlashCount = !evenSlashCount;
/*  82 */       index--;
/*     */     } 
/*  84 */     return !evenSlashCount;
/*     */   }
/*     */   
/*     */   private static String unescape(String str) {
/*  88 */     StringBuilder outBuffer = new StringBuilder(str.length());
/*  89 */     for (int index = 0; index < str.length(); ) {
/*  90 */       char c = str.charAt(index++);
/*  91 */       if (c == '\\') {
/*  92 */         c = str.charAt(index++);
/*  93 */         if (c == 't') {
/*  94 */           c = '\t';
/*  95 */         } else if (c == 'r') {
/*  96 */           c = '\r';
/*  97 */         } else if (c == 'n') {
/*  98 */           c = '\n';
/*  99 */         } else if (c == 'f') {
/* 100 */           c = '\f';
/*     */         } 
/*     */       } 
/* 103 */       outBuffer.append(c);
/*     */     } 
/* 105 */     return outBuffer.toString();
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
/*     */   public static int getIntProperty(Properties properties, String key, int defaultValue) {
/* 117 */     String value = properties.getProperty(key);
/* 118 */     if (StringUtils.hasText(value)) {
/*     */       try {
/* 120 */         return Integer.parseInt(value);
/* 121 */       } catch (NumberFormatException e) {
/* 122 */         return defaultValue;
/*     */       } 
/*     */     }
/* 125 */     return defaultValue;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\interna\\util\PropertiesUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */