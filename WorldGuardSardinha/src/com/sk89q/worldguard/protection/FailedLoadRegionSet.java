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
/*     */ import org.bukkit.ChatColor;
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
/*     */ public class FailedLoadRegionSet
/*     */   extends AbstractRegionSet
/*     */ {
/*  45 */   private static final FailedLoadRegionSet INSTANCE = new FailedLoadRegionSet();
/*     */   
/*  47 */   private final String denyMessage = ChatColor.RED + "Region data for WorldGuard failed to load for this world, so " + "everything has been protected as a precaution. Please inform a server administrator.";
/*     */   
/*  49 */   private final Collection<String> denyMessageCollection = (Collection<String>)ImmutableList.of(this.denyMessage);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVirtual() {
/*  56 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <V> V queryValue(@Nullable RegionAssociable subject, Flag<V> flag) {
/*  63 */     if (flag == DefaultFlag.BUILD)
/*  64 */       return (V)StateFlag.State.DENY; 
/*  65 */     if (flag == DefaultFlag.DENY_MESSAGE) {
/*  66 */       return (V)this.denyMessage;
/*     */     }
/*  68 */     return (V)flag.getDefault();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <V> Collection<V> queryAllValues(@Nullable RegionAssociable subject, Flag<V> flag) {
/*  74 */     if (flag == DefaultFlag.BUILD)
/*  75 */       return (Collection<V>)ImmutableList.of(StateFlag.State.DENY); 
/*  76 */     if (flag == DefaultFlag.DENY_MESSAGE) {
/*  77 */       return (Collection)this.denyMessageCollection;
/*     */     }
/*  79 */     V fallback = (V)flag.getDefault();
/*  80 */     return (fallback != null) ? (Collection<V>)ImmutableList.of(fallback) : (Collection<V>)ImmutableList.of();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnerOfAll(LocalPlayer player) {
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMemberOfAll(LocalPlayer player) {
/*  90 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  95 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<ProtectedRegion> getRegions() {
/* 100 */     return Collections.emptySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<ProtectedRegion> iterator() {
/* 105 */     return (Iterator<ProtectedRegion>)Iterators.emptyIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FailedLoadRegionSet getInstance() {
/* 114 */     return INSTANCE;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\FailedLoadRegionSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */