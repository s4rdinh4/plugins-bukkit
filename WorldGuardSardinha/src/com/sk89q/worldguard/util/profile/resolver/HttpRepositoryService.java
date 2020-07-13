/*     */ package com.sk89q.worldguard.util.profile.resolver;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import com.sk89q.worldguard.util.profile.Profile;
/*     */ import com.sk89q.worldguard.util.profile.util.HttpRequest;
/*     */ import com.sk89q.worldguard.util.profile.util.UUIDs;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class HttpRepositoryService
/*     */   implements ProfileService
/*     */ {
/*     */   public static final String MINECRAFT_AGENT = "Minecraft";
/*  51 */   private static final Logger log = Logger.getLogger(HttpRepositoryService.class.getCanonicalName());
/*     */   
/*     */   private static final int MAX_NAMES_PER_REQUEST = 100;
/*     */   private final URL profilesURL;
/*  55 */   private int maxRetries = 5;
/*  56 */   private long retryDelay = 50L;
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
/*     */   public HttpRepositoryService(String agent) {
/*  69 */     Preconditions.checkNotNull(agent);
/*  70 */     this.profilesURL = HttpRequest.url("https://api.mojang.com/profiles/" + agent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxRetries() {
/*  79 */     return this.maxRetries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxRetries(int maxRetries) {
/*  88 */     Preconditions.checkArgument((maxRetries > 0), "maxRetries must be >= 0");
/*  89 */     this.maxRetries = maxRetries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getRetryDelay() {
/*  99 */     return this.retryDelay;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRetryDelay(long retryDelay) {
/* 109 */     this.retryDelay = retryDelay;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIdealRequestLimit() {
/* 114 */     return 100;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Profile findByName(String name) throws IOException, InterruptedException {
/* 120 */     ImmutableList<Profile> profiles = findAllByName(Arrays.asList(new String[] { name }));
/* 121 */     if (!profiles.isEmpty()) {
/* 122 */       return (Profile)profiles.get(0);
/*     */     }
/* 124 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void findAllByName(Iterable<String> names, Predicate<Profile> consumer) throws IOException, InterruptedException {
/* 130 */     for (List<String> partition : (Iterable<List<String>>)Iterables.partition(names, 100)) {
/* 131 */       for (UnmodifiableIterator<Profile> unmodifiableIterator = query(partition).iterator(); unmodifiableIterator.hasNext(); ) { Profile profile = unmodifiableIterator.next();
/* 132 */         consumer.apply(profile); }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ImmutableList<Profile> findAllByName(Iterable<String> names) throws IOException, InterruptedException {
/* 139 */     ImmutableList.Builder<Profile> builder = ImmutableList.builder();
/* 140 */     for (List<String> partition : (Iterable<List<String>>)Iterables.partition(names, 100)) {
/* 141 */       builder.addAll((Iterable)query(partition));
/*     */     }
/* 143 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ImmutableList<Profile> query(Iterable<String> names) throws IOException, InterruptedException {
/*     */     Object result;
/* 155 */     List<Profile> profiles = new ArrayList<Profile>();
/*     */ 
/*     */ 
/*     */     
/* 159 */     int retriesLeft = this.maxRetries;
/* 160 */     long retryDelay = this.retryDelay;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/*     */       try {
/* 169 */         result = HttpRequest.post(this.profilesURL).bodyJson(names).execute().returnContent().asJson();
/*     */         break;
/* 171 */       } catch (IOException e) {
/* 172 */         if (retriesLeft == 0) {
/* 173 */           throw e;
/*     */         }
/*     */         
/* 176 */         log.log(Level.WARNING, "Failed to query profile service -- retrying...", e);
/* 177 */         Thread.sleep(retryDelay);
/*     */ 
/*     */         
/* 180 */         retryDelay *= 2L;
/* 181 */         retriesLeft--;
/*     */       } 
/*     */     } 
/* 184 */     if (result instanceof Iterable) {
/* 185 */       for (Object entry : result) {
/* 186 */         Profile profile = decodeResult(entry);
/* 187 */         if (profile != null) {
/* 188 */           profiles.add(profile);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 193 */     return ImmutableList.copyOf(profiles);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static Profile decodeResult(Object entry) {
/*     */     try {
/* 200 */       if (entry instanceof Map) {
/* 201 */         Map<Object, Object> mapEntry = (Map<Object, Object>)entry;
/* 202 */         Object rawUuid = mapEntry.get("id");
/* 203 */         Object rawName = mapEntry.get("name");
/*     */         
/* 205 */         if (rawUuid != null && rawName != null) {
/* 206 */           UUID uuid = UUID.fromString(UUIDs.addDashes(String.valueOf(rawUuid)));
/* 207 */           String name = String.valueOf(rawName);
/* 208 */           return new Profile(uuid, name);
/*     */         } 
/*     */       } 
/* 211 */     } catch (ClassCastException e) {
/* 212 */       log.log(Level.WARNING, "Got invalid value from UUID lookup service", e);
/* 213 */     } catch (IllegalArgumentException e) {
/* 214 */       log.log(Level.WARNING, "Got invalid value from UUID lookup service", e);
/*     */     } 
/*     */     
/* 217 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ProfileService forMinecraft() {
/* 226 */     return new HttpRepositoryService("Minecraft");
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\profile\resolver\HttpRepositoryService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */