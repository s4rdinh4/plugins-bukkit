/*    */ package com.sk89q.worldguard.util;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import javax.annotation.Nullable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Enums
/*    */ {
/*    */   @Nullable
/*    */   public static <T extends Enum<T>> T findByValue(Class<T> enumType, String... values) {
/* 45 */     Preconditions.checkNotNull(enumType);
/* 46 */     Preconditions.checkNotNull(values);
/* 47 */     for (String val : values) {
/*    */       try {
/* 49 */         return Enum.valueOf(enumType, val);
/* 50 */       } catch (IllegalArgumentException ignored) {}
/*    */     } 
/* 52 */     return null;
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
/*    */   @Nullable
/*    */   public static <T extends Enum<T>> T findFuzzyByValue(Class<T> enumType, String... values) {
/* 68 */     Preconditions.checkNotNull(enumType);
/* 69 */     Preconditions.checkNotNull(values);
/* 70 */     for (String test : values) {
/* 71 */       test = test.replace("_", "");
/* 72 */       for (Enum enum_ : (Enum[])enumType.getEnumConstants()) {
/* 73 */         if (enum_.name().replace("_", "").equalsIgnoreCase(test)) {
/* 74 */           return (T)enum_;
/*    */         }
/*    */       } 
/*    */     } 
/* 78 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\Enums.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */