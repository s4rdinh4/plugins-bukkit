/*     */ package com.sk89q.worldguard.util.profile.resolver;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import com.sk89q.worldguard.util.profile.Profile;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ public class CombinedProfileService
/*     */   implements ProfileService
/*     */ {
/*     */   private final List<ProfileService> services;
/*     */   
/*     */   public CombinedProfileService(List<ProfileService> services) {
/*  49 */     Preconditions.checkNotNull(services);
/*  50 */     this.services = (List<ProfileService>)ImmutableList.copyOf(services);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CombinedProfileService(ProfileService... services) {
/*  59 */     Preconditions.checkNotNull(services);
/*  60 */     this.services = (List<ProfileService>)ImmutableList.copyOf((Object[])services);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIdealRequestLimit() {
/*  65 */     int ideal = Integer.MAX_VALUE;
/*  66 */     for (ProfileService service : this.services) {
/*  67 */       ideal = Math.min(service.getIdealRequestLimit(), ideal);
/*     */     }
/*  69 */     return ideal;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Profile findByName(String name) throws IOException, InterruptedException {
/*  75 */     for (ProfileService service : this.services) {
/*  76 */       Profile profile = service.findByName(name);
/*  77 */       if (profile != null) {
/*  78 */         return profile;
/*     */       }
/*     */     } 
/*  81 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ImmutableList<Profile> findAllByName(Iterable<String> names) throws IOException, InterruptedException {
/*  86 */     List<String> missing = new ArrayList<String>();
/*  87 */     List<Profile> totalResults = new ArrayList<Profile>();
/*     */     
/*  89 */     for (String name : names) {
/*  90 */       missing.add(name.toLowerCase());
/*     */     }
/*     */     
/*  93 */     for (ProfileService service : this.services) {
/*  94 */       ImmutableList<Profile> results = service.findAllByName(missing);
/*     */       
/*  96 */       for (UnmodifiableIterator<Profile> unmodifiableIterator = results.iterator(); unmodifiableIterator.hasNext(); ) { Profile profile = unmodifiableIterator.next();
/*  97 */         String nameLower = profile.getName().toLowerCase();
/*  98 */         if (missing.contains(nameLower)) {
/*  99 */           missing.remove(nameLower);
/*     */         }
/* 101 */         totalResults.add(profile); }
/*     */ 
/*     */       
/* 104 */       if (missing.isEmpty()) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 109 */     return ImmutableList.copyOf(totalResults);
/*     */   }
/*     */ 
/*     */   
/*     */   public void findAllByName(Iterable<String> names, final Predicate<Profile> consumer) throws IOException, InterruptedException {
/* 114 */     final List<String> missing = Collections.synchronizedList(new ArrayList<String>());
/*     */     
/* 116 */     Predicate<Profile> forwardingConsumer = new Predicate<Profile>()
/*     */       {
/*     */         public boolean apply(Profile profile) {
/* 119 */           missing.remove(profile.getName().toLowerCase());
/* 120 */           return consumer.apply(profile);
/*     */         }
/*     */       };
/*     */     
/* 124 */     for (String name : names) {
/* 125 */       missing.add(name.toLowerCase());
/*     */     }
/*     */     
/* 128 */     for (ProfileService service : this.services) {
/* 129 */       service.findAllByName(new ArrayList<String>(missing), forwardingConsumer);
/*     */       
/* 131 */       if (missing.isEmpty())
/*     */         break; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\profile\resolver\CombinedProfileService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */