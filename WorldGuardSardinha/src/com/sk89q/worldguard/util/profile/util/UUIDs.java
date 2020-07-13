/*    */ package com.sk89q.worldguard.util.profile.util;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
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
/*    */ public final class UUIDs
/*    */ {
/* 30 */   private static final Pattern DASHLESS_PATTERN = Pattern.compile("^([A-Fa-f0-9]{8})([A-Fa-f0-9]{4})([A-Fa-f0-9]{4})([A-Fa-f0-9]{4})([A-Fa-f0-9]{12})$");
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
/*    */   public static String addDashes(String uuid) {
/* 45 */     uuid = uuid.replace("-", "");
/* 46 */     Matcher matcher = DASHLESS_PATTERN.matcher(uuid);
/* 47 */     if (!matcher.matches()) {
/* 48 */       throw new IllegalArgumentException("Invalid UUID format");
/*    */     }
/* 50 */     return matcher.replaceAll("$1-$2-$3-$4-$5");
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\profil\\util\UUIDs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */