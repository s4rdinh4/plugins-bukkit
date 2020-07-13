/*    */ package com.sk89q.worldguard.util.profile.cache;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.sk89q.worldguard.util.profile.Profile;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.concurrent.ConcurrentMap;
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
/*    */ public class HashMapCache
/*    */   extends AbstractProfileCache
/*    */ {
/* 38 */   private final ConcurrentMap<UUID, String> cache = new ConcurrentHashMap<UUID, String>();
/*    */ 
/*    */   
/*    */   public void putAll(Iterable<Profile> profiles) {
/* 42 */     for (Profile profile : profiles) {
/* 43 */       this.cache.put(profile.getUniqueId(), profile.getName());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public ImmutableMap<UUID, Profile> getAllPresent(Iterable<UUID> uuids) {
/* 49 */     Map<UUID, Profile> results = new HashMap<UUID, Profile>();
/* 50 */     for (UUID uuid : uuids) {
/* 51 */       String name = this.cache.get(uuid);
/* 52 */       if (name != null) {
/* 53 */         results.put(uuid, new Profile(uuid, name));
/*    */       }
/*    */     } 
/* 56 */     return ImmutableMap.copyOf(results);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\profile\cache\HashMapCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */