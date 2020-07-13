/*     */ package com.sk89q.worldguard.protection;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import com.sk89q.worldguard.protection.association.RegionAssociable;
/*     */ import com.sk89q.worldguard.protection.flags.DefaultFlag;
/*     */ import com.sk89q.worldguard.protection.flags.Flag;
/*     */ import com.sk89q.worldguard.protection.flags.StateFlag;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
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
/*     */ public class PermissiveRegionSet
/*     */   extends AbstractRegionSet
/*     */ {
/*  44 */   private static final PermissiveRegionSet INSTANCE = new PermissiveRegionSet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVirtual() {
/*  51 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <V> V queryValue(@Nullable RegionAssociable subject, Flag<V> flag) {
/*  58 */     if (flag == DefaultFlag.BUILD) {
/*  59 */       return (V)StateFlag.State.DENY;
/*     */     }
/*  61 */     return (V)flag.getDefault();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <V> Collection<V> queryAllValues(@Nullable RegionAssociable subject, Flag<V> flag) {
/*  67 */     if (flag == DefaultFlag.BUILD) {
/*  68 */       return (Collection<V>)ImmutableList.of(StateFlag.State.DENY);
/*     */     }
/*  70 */     V fallback = (V)flag.getDefault();
/*  71 */     return (fallback != null) ? (Collection<V>)ImmutableList.of(fallback) : (Collection<V>)ImmutableList.of();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnerOfAll(LocalPlayer player) {
/*  76 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMemberOfAll(LocalPlayer player) {
/*  81 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  86 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<ProtectedRegion> getRegions() {
/*  91 */     return Collections.emptySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<ProtectedRegion> iterator() {
/*  96 */     return (Iterator<ProtectedRegion>)Iterators.emptyIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PermissiveRegionSet getInstance() {
/* 105 */     return INSTANCE;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\PermissiveRegionSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */