/*    */ package com.sk89q.worldguard.util.profile.resolver;
/*    */ 
/*    */ import com.sk89q.worldguard.util.profile.Profile;
/*    */ import java.io.IOException;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import java.util.concurrent.ConcurrentHashMap;
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
/*    */ public class HashMapService
/*    */   extends SingleRequestService
/*    */ {
/* 36 */   private final ConcurrentHashMap<String, UUID> map = new ConcurrentHashMap<String, UUID>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HashMapService() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HashMapService(Map<String, UUID> map) {
/* 51 */     for (Map.Entry<String, UUID> entry : map.entrySet()) {
/* 52 */       this.map.put(((String)entry.getKey()).toLowerCase(), entry.getValue());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void put(Profile profile) {
/* 62 */     this.map.put(profile.getName().toLowerCase(), profile.getUniqueId());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void putAll(Collection<Profile> profiles) {
/* 71 */     for (Profile profile : profiles) {
/* 72 */       put(profile);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getIdealRequestLimit() {
/* 78 */     return Integer.MAX_VALUE;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Profile findByName(String name) throws IOException, InterruptedException {
/* 84 */     UUID uuid = this.map.get(name.toLowerCase());
/* 85 */     if (uuid != null) {
/* 86 */       return new Profile(uuid, name);
/*    */     }
/* 88 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\profile\resolver\HashMapService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */