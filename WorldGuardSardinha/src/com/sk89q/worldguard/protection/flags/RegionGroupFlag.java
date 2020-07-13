/*     */ package com.sk89q.worldguard.protection.flags;
/*     */ 
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
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
/*     */ public class RegionGroupFlag
/*     */   extends EnumFlag<RegionGroup>
/*     */ {
/*     */   private RegionGroup def;
/*     */   
/*     */   public RegionGroupFlag(String name, RegionGroup def) {
/*  36 */     super(name, RegionGroup.class, null);
/*  37 */     this.def = def;
/*     */   }
/*     */ 
/*     */   
/*     */   public RegionGroup getDefault() {
/*  42 */     return this.def;
/*     */   }
/*     */ 
/*     */   
/*     */   public RegionGroup detectValue(String input) {
/*  47 */     input = input.trim();
/*     */     
/*  49 */     if (input.equalsIgnoreCase("members") || input.equalsIgnoreCase("member"))
/*  50 */       return RegionGroup.MEMBERS; 
/*  51 */     if (input.equalsIgnoreCase("owners") || input.equalsIgnoreCase("owner"))
/*  52 */       return RegionGroup.OWNERS; 
/*  53 */     if (input.equalsIgnoreCase("nonowners") || input.equalsIgnoreCase("nonowner"))
/*  54 */       return RegionGroup.NON_OWNERS; 
/*  55 */     if (input.equalsIgnoreCase("nonmembers") || input.equalsIgnoreCase("nonmember"))
/*  56 */       return RegionGroup.NON_MEMBERS; 
/*  57 */     if (input.equalsIgnoreCase("everyone") || input.equalsIgnoreCase("anyone") || input.equalsIgnoreCase("all"))
/*  58 */       return RegionGroup.ALL; 
/*  59 */     if (input.equalsIgnoreCase("none") || input.equalsIgnoreCase("noone") || input.equalsIgnoreCase("deny")) {
/*  60 */       return RegionGroup.NONE;
/*     */     }
/*  62 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isMember(ProtectedRegion region, RegionGroup group, @Nullable LocalPlayer player) {
/*  67 */     if (group == null || group == RegionGroup.ALL)
/*  68 */       return true; 
/*  69 */     if (group == RegionGroup.OWNERS) {
/*  70 */       if (player != null && region.isOwner(player)) {
/*  71 */         return true;
/*     */       }
/*  73 */     } else if (group == RegionGroup.MEMBERS) {
/*  74 */       if (player != null && region.isMember(player)) {
/*  75 */         return true;
/*     */       }
/*  77 */     } else if (group == RegionGroup.NON_OWNERS) {
/*  78 */       if (player == null || !region.isOwner(player)) {
/*  79 */         return true;
/*     */       }
/*  81 */     } else if (group == RegionGroup.NON_MEMBERS && (
/*  82 */       player == null || !region.isMember(player))) {
/*  83 */       return true;
/*     */     } 
/*     */ 
/*     */     
/*  87 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isMember(ApplicableRegionSet set, @Nullable RegionGroup group, LocalPlayer player) {
/*  91 */     if (group == null || group == RegionGroup.ALL)
/*  92 */       return true; 
/*  93 */     if (group == RegionGroup.OWNERS) {
/*  94 */       if (set.isOwnerOfAll(player)) {
/*  95 */         return true;
/*     */       }
/*  97 */     } else if (group == RegionGroup.MEMBERS) {
/*  98 */       if (set.isMemberOfAll(player)) {
/*  99 */         return true;
/*     */       }
/* 101 */     } else if (group == RegionGroup.NON_OWNERS) {
/* 102 */       if (!set.isOwnerOfAll(player)) {
/* 103 */         return true;
/*     */       }
/* 105 */     } else if (group == RegionGroup.NON_MEMBERS && 
/* 106 */       !set.isMemberOfAll(player)) {
/* 107 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 111 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\flags\RegionGroupFlag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */