/*    */ package com.sk89q.worldguard.util.profile.resolver;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.UnmodifiableIterator;
/*    */ import com.sk89q.worldguard.util.profile.Profile;
/*    */ import com.sk89q.worldguard.util.profile.cache.ProfileCache;
/*    */ import java.io.IOException;
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
/*    */ public class CacheForwardingService
/*    */   implements ProfileService
/*    */ {
/*    */   private final ProfileService resolver;
/*    */   private final ProfileCache cache;
/*    */   
/*    */   public CacheForwardingService(ProfileService resolver, ProfileCache cache) {
/* 47 */     Preconditions.checkNotNull(resolver);
/* 48 */     Preconditions.checkNotNull(cache);
/*    */     
/* 50 */     this.resolver = resolver;
/* 51 */     this.cache = cache;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getIdealRequestLimit() {
/* 56 */     return this.resolver.getIdealRequestLimit();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Profile findByName(String name) throws IOException, InterruptedException {
/* 62 */     Profile profile = this.resolver.findByName(name);
/* 63 */     if (profile != null) {
/* 64 */       this.cache.put(profile);
/*    */     }
/* 66 */     return profile;
/*    */   }
/*    */ 
/*    */   
/*    */   public ImmutableList<Profile> findAllByName(Iterable<String> names) throws IOException, InterruptedException {
/* 71 */     ImmutableList<Profile> profiles = this.resolver.findAllByName(names);
/* 72 */     for (UnmodifiableIterator<Profile> unmodifiableIterator = profiles.iterator(); unmodifiableIterator.hasNext(); ) { Profile profile = unmodifiableIterator.next();
/* 73 */       this.cache.put(profile); }
/*    */     
/* 75 */     return profiles;
/*    */   }
/*    */ 
/*    */   
/*    */   public void findAllByName(Iterable<String> names, final Predicate<Profile> consumer) throws IOException, InterruptedException {
/* 80 */     this.resolver.findAllByName(names, new Predicate<Profile>()
/*    */         {
/*    */           public boolean apply(@Nullable Profile input) {
/* 83 */             CacheForwardingService.this.cache.put(input);
/* 84 */             return consumer.apply(input);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\profile\resolver\CacheForwardingService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */