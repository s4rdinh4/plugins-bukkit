/*    */ package com.sk89q.worldguard.util.profile.cache;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.sk89q.worldguard.util.profile.Profile;
/*    */ import java.util.Arrays;
/*    */ import java.util.UUID;
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
/*    */ abstract class AbstractProfileCache
/*    */   implements ProfileCache
/*    */ {
/*    */   public void put(Profile profile) {
/* 36 */     putAll((Iterable<Profile>)ImmutableList.builder().add(profile).build());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Profile getIfPresent(UUID uuid) {
/* 42 */     return (Profile)getAllPresent(Arrays.asList(new UUID[] { uuid })).get(uuid);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\profile\cache\AbstractProfileCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */