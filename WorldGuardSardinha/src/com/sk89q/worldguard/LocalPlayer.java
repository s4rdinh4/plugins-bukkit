/*     */ package com.sk89q.worldguard;
/*     */ 
/*     */ import com.sk89q.worldedit.Vector;
/*     */ import com.sk89q.worldguard.domains.Association;
/*     */ import com.sk89q.worldguard.protection.association.RegionAssociable;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
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
/*     */ public abstract class LocalPlayer
/*     */   implements RegionAssociable
/*     */ {
/*     */   public abstract String getName();
/*     */   
/*     */   public abstract UUID getUniqueId();
/*     */   
/*     */   public abstract boolean hasGroup(String paramString);
/*     */   
/*     */   public abstract Vector getPosition();
/*     */   
/*     */   public abstract void kick(String paramString);
/*     */   
/*     */   public abstract void ban(String paramString);
/*     */   
/*     */   public abstract void printRaw(String paramString);
/*     */   
/*     */   public abstract String[] getGroups();
/*     */   
/*     */   public abstract boolean hasPermission(String paramString);
/*     */   
/*     */   public Association getAssociation(List<ProtectedRegion> regions) {
/*  99 */     boolean member = false;
/*     */     
/* 101 */     for (ProtectedRegion region : regions) {
/* 102 */       if (region.isOwner(this))
/* 103 */         return Association.OWNER; 
/* 104 */       if (!member && region.isMember(this)) {
/* 105 */         member = true;
/*     */       }
/*     */     } 
/*     */     
/* 109 */     return member ? Association.MEMBER : Association.NON_MEMBER;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 114 */     return (obj instanceof LocalPlayer && ((LocalPlayer)obj).getName().equals(getName()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 119 */     return getName().hashCode();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\LocalPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */