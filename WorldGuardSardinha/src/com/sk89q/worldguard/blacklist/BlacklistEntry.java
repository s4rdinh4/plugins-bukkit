/*     */ package com.sk89q.worldguard.blacklist;
/*     */ 
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import com.sk89q.worldguard.blacklist.action.Action;
/*     */ import com.sk89q.worldguard.blacklist.action.ActionResult;
/*     */ import com.sk89q.worldguard.blacklist.event.BlacklistEvent;
/*     */ import com.sk89q.worldguard.internal.guava.cache.LoadingCache;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class BlacklistEntry
/*     */ {
/*     */   private Blacklist blacklist;
/*     */   private Set<String> ignoreGroups;
/*     */   private Set<String> ignorePermissions;
/*  36 */   private Map<Class<? extends BlacklistEvent>, List<Action>> actions = new HashMap<Class<? extends BlacklistEvent>, List<Action>>();
/*     */ 
/*     */   
/*     */   private String message;
/*     */ 
/*     */   
/*     */   private String comment;
/*     */ 
/*     */ 
/*     */   
/*     */   public BlacklistEntry(Blacklist blacklist) {
/*  47 */     this.blacklist = blacklist;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getIgnoreGroups() {
/*  54 */     return this.ignoreGroups.<String>toArray(new String[this.ignoreGroups.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getIgnorePermissions() {
/*  61 */     return this.ignorePermissions.<String>toArray(new String[this.ignorePermissions.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIgnoreGroups(String[] ignoreGroups) {
/*  68 */     Set<String> ignoreGroupsSet = new HashSet<String>();
/*  69 */     for (String group : ignoreGroups) {
/*  70 */       ignoreGroupsSet.add(group.toLowerCase());
/*     */     }
/*  72 */     this.ignoreGroups = ignoreGroupsSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIgnorePermissions(String[] ignorePermissions) {
/*  79 */     Set<String> ignorePermissionsSet = new HashSet<String>();
/*  80 */     Collections.addAll(ignorePermissionsSet, ignorePermissions);
/*  81 */     this.ignorePermissions = ignorePermissionsSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage() {
/*  88 */     return this.message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMessage(String message) {
/*  95 */     this.message = message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getComment() {
/* 102 */     return this.comment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setComment(String comment) {
/* 109 */     this.comment = comment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldIgnore(@Nullable LocalPlayer player) {
/* 119 */     if (player == null) {
/* 120 */       return false;
/*     */     }
/*     */     
/* 123 */     if (this.ignoreGroups != null) {
/* 124 */       for (String group : player.getGroups()) {
/* 125 */         if (this.ignoreGroups.contains(group.toLowerCase())) {
/* 126 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 131 */     if (this.ignorePermissions != null) {
/* 132 */       for (String perm : this.ignorePermissions) {
/* 133 */         if (player.hasPermission(perm)) {
/* 134 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 139 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Action> getActions(Class<? extends BlacklistEvent> eventCls) {
/* 149 */     List<Action> ret = this.actions.get(eventCls);
/* 150 */     if (ret == null) {
/* 151 */       ret = new ArrayList<Action>();
/* 152 */       this.actions.put(eventCls, ret);
/*     */     } 
/* 154 */     return ret;
/*     */   }
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
/*     */   public boolean check(boolean useAsWhitelist, BlacklistEvent event, boolean forceRepeat, boolean silent) {
/* 167 */     LocalPlayer player = event.getPlayer();
/*     */     
/* 169 */     if (shouldIgnore(player)) {
/* 170 */       return true;
/*     */     }
/*     */     
/* 173 */     boolean repeating = false;
/* 174 */     String eventCacheKey = event.getCauseName();
/* 175 */     LoadingCache<String, TrackedEvent> repeatingEventCache = this.blacklist.getRepeatingEventCache();
/*     */ 
/*     */     
/* 178 */     TrackedEvent tracked = (TrackedEvent)repeatingEventCache.getUnchecked(eventCacheKey);
/* 179 */     if (tracked.matches(event)) {
/* 180 */       repeating = true;
/*     */     } else {
/* 182 */       tracked.setLastEvent(event);
/* 183 */       tracked.resetTimer();
/*     */     } 
/*     */     
/* 186 */     List<Action> actions = getActions((Class)event.getClass());
/*     */     
/* 188 */     boolean ret = !useAsWhitelist;
/*     */ 
/*     */     
/* 191 */     if (actions == null) {
/* 192 */       return ret;
/*     */     }
/*     */     
/* 195 */     for (Action action : actions) {
/* 196 */       ActionResult result = action.apply(event, silent, repeating, forceRepeat);
/* 197 */       switch (result) {
/*     */ 
/*     */         
/*     */         case ALLOW:
/* 201 */           ret = true;
/*     */         
/*     */         case DENY:
/* 204 */           ret = false;
/*     */         
/*     */         case ALLOW_OVERRIDE:
/* 207 */           return true;
/*     */         case DENY_OVERRIDE:
/* 209 */           return false;
/*     */       } 
/*     */     
/*     */     } 
/* 213 */     return ret;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\BlacklistEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */