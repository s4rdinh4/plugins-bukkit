/*     */ package com.sk89q.worldguard.util;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.text.Normalizer;
/*     */ import javax.annotation.Nullable;
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
/*     */ public final class Normal
/*     */ {
/*     */   private final String name;
/*     */   @Nullable
/*     */   private final String normal;
/*     */   
/*     */   private Normal(String name) {
/*  44 */     Preconditions.checkNotNull(name);
/*     */     
/*  46 */     this.name = name;
/*  47 */     String normal = normalize(name);
/*  48 */     if (!normal.equals(name)) {
/*  49 */       this.normal = normal;
/*     */     } else {
/*  51 */       this.normal = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  61 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNormal() {
/*  70 */     return (this.normal != null) ? this.normal : this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String normalize(String name) {
/*  80 */     return Normalizer.normalize(name.toLowerCase(), Normalizer.Form.NFC);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Normal normal(String name) {
/*  90 */     return new Normal(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  95 */     if (this == o) return true; 
/*  96 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/*  98 */     Normal that = (Normal)o;
/*     */     
/* 100 */     return getNormal().equals(that.getNormal());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 106 */     return getNormal().hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 116 */     return this.name;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\Normal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */