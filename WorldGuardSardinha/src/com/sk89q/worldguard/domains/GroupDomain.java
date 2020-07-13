/*     */ package com.sk89q.worldguard.domains;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import com.sk89q.worldguard.util.ChangeTracked;
/*     */ import java.util.Collections;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CopyOnWriteArraySet;
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
/*     */ public class GroupDomain
/*     */   implements Domain, ChangeTracked
/*     */ {
/*  37 */   private final Set<String> groups = new CopyOnWriteArraySet<String>();
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean dirty = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GroupDomain() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GroupDomain(GroupDomain domain) {
/*  52 */     Preconditions.checkNotNull(domain, "domain");
/*  53 */     this.groups.addAll(domain.getGroups());
/*  54 */     this.dirty = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GroupDomain(String[] groups) {
/*  63 */     Preconditions.checkNotNull(groups);
/*  64 */     for (String group : groups) {
/*  65 */       addGroup(group);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addGroup(String name) {
/*  75 */     Preconditions.checkNotNull(name);
/*  76 */     if (!name.trim().isEmpty()) {
/*  77 */       setDirty(true);
/*  78 */       this.groups.add(name.trim().toLowerCase());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeGroup(String name) {
/*  88 */     Preconditions.checkNotNull(name);
/*  89 */     setDirty(true);
/*  90 */     this.groups.remove(name.trim().toLowerCase());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(LocalPlayer player) {
/*  95 */     Preconditions.checkNotNull(player);
/*  96 */     for (String group : this.groups) {
/*  97 */       if (player.hasGroup(group)) {
/*  98 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getGroups() {
/* 111 */     return Collections.unmodifiableSet(this.groups);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(UUID uniqueId) {
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(String playerName) {
/* 121 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 126 */     return this.groups.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 131 */     this.groups.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirty() {
/* 136 */     return this.dirty;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDirty(boolean dirty) {
/* 141 */     this.dirty = dirty;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 146 */     return "{names=" + this.groups + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\domains\GroupDomain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */