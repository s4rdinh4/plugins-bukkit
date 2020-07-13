/*     */ package com.sk89q.worldguard.util.profile;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.UUID;
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
/*     */ public final class Profile
/*     */ {
/*     */   private final UUID uniqueId;
/*     */   private final String name;
/*     */   
/*     */   public Profile(UUID uniqueId, String name) {
/*  43 */     Preconditions.checkNotNull(uniqueId);
/*  44 */     Preconditions.checkNotNull(name);
/*     */     
/*  46 */     this.uniqueId = uniqueId;
/*  47 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UUID getUniqueId() {
/*  56 */     return this.uniqueId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Profile setUniqueId(UUID uniqueId) {
/*  66 */     return new Profile(uniqueId, this.name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  75 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Profile setName(String name) {
/*  85 */     return new Profile(this.uniqueId, name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  90 */     if (this == o) return true; 
/*  91 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/*  93 */     Profile profile = (Profile)o;
/*     */     
/*  95 */     if (!this.uniqueId.equals(profile.uniqueId)) return false;
/*     */     
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 102 */     return this.uniqueId.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 107 */     return "Profile{uniqueId=" + this.uniqueId + ", name='" + this.name + '\'' + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\profile\Profile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */