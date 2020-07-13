/*    */ package com.sk89q.worldguard.util.profile.resolver;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.sk89q.worldguard.util.profile.Profile;
/*    */ import java.io.IOException;
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
/*    */ abstract class SingleRequestService
/*    */   implements ProfileService
/*    */ {
/*    */   public final ImmutableList<Profile> findAllByName(Iterable<String> names) throws IOException, InterruptedException {
/* 37 */     ImmutableList.Builder<Profile> builder = ImmutableList.builder();
/* 38 */     for (String name : names) {
/* 39 */       Profile profile = findByName(name);
/* 40 */       if (profile != null) {
/* 41 */         builder.add(profile);
/*    */       }
/*    */     } 
/* 44 */     return builder.build();
/*    */   }
/*    */ 
/*    */   
/*    */   public final void findAllByName(Iterable<String> names, Predicate<Profile> consumer) throws IOException, InterruptedException {
/* 49 */     for (String name : names) {
/* 50 */       Profile profile = findByName(name);
/* 51 */       if (profile != null)
/* 52 */         consumer.apply(profile); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\profile\resolver\SingleRequestService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */