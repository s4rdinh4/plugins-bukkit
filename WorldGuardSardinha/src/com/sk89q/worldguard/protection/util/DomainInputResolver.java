/*     */ package com.sk89q.worldguard.protection.util;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import com.sk89q.worldguard.domains.DefaultDomain;
/*     */ import com.sk89q.worldguard.util.profile.Profile;
/*     */ import com.sk89q.worldguard.util.profile.resolver.ProfileService;
/*     */ import com.sk89q.worldguard.util.profile.util.UUIDs;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class DomainInputResolver
/*     */   implements Callable<DefaultDomain>
/*     */ {
/*  45 */   private static final Pattern GROUP_PATTERN = Pattern.compile("(?i)^[G]:(.+)$");
/*     */   private final ProfileService profileService;
/*     */   private final String[] input;
/*     */   
/*     */   public enum UserLocatorPolicy
/*     */   {
/*  51 */     UUID_ONLY,
/*  52 */     NAME_ONLY,
/*  53 */     UUID_AND_NAME;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  58 */   private UserLocatorPolicy locatorPolicy = UserLocatorPolicy.UUID_ONLY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DomainInputResolver(ProfileService profileService, String[] input) {
/*  67 */     Preconditions.checkNotNull(profileService);
/*  68 */     Preconditions.checkNotNull(input);
/*  69 */     this.profileService = profileService;
/*  70 */     this.input = input;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserLocatorPolicy getLocatorPolicy() {
/*  79 */     return this.locatorPolicy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocatorPolicy(UserLocatorPolicy locatorPolicy) {
/*  88 */     Preconditions.checkNotNull(locatorPolicy);
/*  89 */     this.locatorPolicy = locatorPolicy;
/*     */   }
/*     */ 
/*     */   
/*     */   public DefaultDomain call() throws UnresolvedNamesException {
/*  94 */     DefaultDomain domain = new DefaultDomain();
/*  95 */     List<String> namesToQuery = new ArrayList<String>();
/*     */     
/*  97 */     for (String s : this.input) {
/*  98 */       Matcher m = GROUP_PATTERN.matcher(s);
/*  99 */       if (m.matches()) {
/* 100 */         domain.addGroup(m.group(1));
/*     */       } else {
/* 102 */         UUID uuid = parseUUID(s);
/* 103 */         if (uuid != null) {
/*     */           
/* 105 */           domain.addPlayer(UUID.fromString(UUIDs.addDashes(s.replaceAll("^uuid:", ""))));
/*     */         } else {
/* 107 */           switch (this.locatorPolicy) {
/*     */             case NAME_ONLY:
/* 109 */               domain.addPlayer(s);
/*     */               break;
/*     */             case UUID_ONLY:
/* 112 */               namesToQuery.add(s.toLowerCase());
/*     */               break;
/*     */             case UUID_AND_NAME:
/* 115 */               domain.addPlayer(s);
/* 116 */               namesToQuery.add(s.toLowerCase());
/*     */               break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 122 */     if (!namesToQuery.isEmpty()) {
/*     */       try {
/* 124 */         for (UnmodifiableIterator<Profile> unmodifiableIterator = this.profileService.findAllByName(namesToQuery).iterator(); unmodifiableIterator.hasNext(); ) { Profile profile = unmodifiableIterator.next();
/* 125 */           namesToQuery.remove(profile.getName().toLowerCase());
/* 126 */           domain.addPlayer(profile.getUniqueId()); }
/*     */       
/* 128 */       } catch (IOException e) {
/* 129 */         throw new UnresolvedNamesException("The UUID lookup service failed so the names entered could not be turned into UUIDs");
/* 130 */       } catch (InterruptedException e) {
/* 131 */         throw new UnresolvedNamesException("UUID lookup was interrupted");
/*     */       } 
/*     */     }
/*     */     
/* 135 */     if (!namesToQuery.isEmpty()) {
/* 136 */       throw new UnresolvedNamesException("Unable to resolve the names " + Joiner.on(", ").join(namesToQuery));
/*     */     }
/*     */     
/* 139 */     return domain;
/*     */   }
/*     */   
/*     */   public Function<DefaultDomain, DefaultDomain> createAddAllFunction(final DefaultDomain target) {
/* 143 */     return new Function<DefaultDomain, DefaultDomain>()
/*     */       {
/*     */         public DefaultDomain apply(@Nullable DefaultDomain domain) {
/* 146 */           target.addAll(domain);
/* 147 */           return domain;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public Function<DefaultDomain, DefaultDomain> createRemoveAllFunction(final DefaultDomain target) {
/* 153 */     return new Function<DefaultDomain, DefaultDomain>()
/*     */       {
/*     */         public DefaultDomain apply(@Nullable DefaultDomain domain) {
/* 156 */           target.removeAll(domain);
/* 157 */           return domain;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static UUID parseUUID(String input) {
/* 170 */     Preconditions.checkNotNull(input);
/*     */     
/*     */     try {
/* 173 */       return UUID.fromString(UUIDs.addDashes(input.replaceAll("^uuid:", "")));
/* 174 */     } catch (IllegalArgumentException e) {
/* 175 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protectio\\util\DomainInputResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */