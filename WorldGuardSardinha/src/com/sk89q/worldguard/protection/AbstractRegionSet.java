/*     */ package com.sk89q.worldguard.protection;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import com.sk89q.worldguard.protection.association.RegionAssociable;
/*     */ import com.sk89q.worldguard.protection.flags.DefaultFlag;
/*     */ import com.sk89q.worldguard.protection.flags.Flag;
/*     */ import com.sk89q.worldguard.protection.flags.RegionGroup;
/*     */ import com.sk89q.worldguard.protection.flags.RegionGroupFlag;
/*     */ import com.sk89q.worldguard.protection.flags.StateFlag;
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
/*     */ public abstract class AbstractRegionSet
/*     */   implements ApplicableRegionSet
/*     */ {
/*     */   @Deprecated
/*     */   public boolean canBuild(LocalPlayer player) {
/*  41 */     Preconditions.checkNotNull(player);
/*  42 */     return StateFlag.test(new StateFlag.State[] { queryState((RegionAssociable)player, new StateFlag[] { DefaultFlag.BUILD }) });
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean canConstruct(LocalPlayer player) {
/*  48 */     Preconditions.checkNotNull(player);
/*  49 */     RegionGroup flag = getFlag(DefaultFlag.CONSTRUCT, player);
/*  50 */     return RegionGroupFlag.isMember(this, flag, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean testState(@Nullable RegionAssociable subject, StateFlag... flags) {
/*  55 */     return StateFlag.test(new StateFlag.State[] { queryState(subject, flags) });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public StateFlag.State queryState(@Nullable RegionAssociable subject, StateFlag... flags) {
/*  61 */     StateFlag.State value = null;
/*     */     
/*  63 */     for (StateFlag flag : flags) {
/*  64 */       value = StateFlag.combine(new StateFlag.State[] { value, queryValue(subject, (Flag<StateFlag.State>)flag) });
/*  65 */       if (value == StateFlag.State.DENY) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/*  70 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean allows(StateFlag flag) {
/*  76 */     Preconditions.checkNotNull(flag);
/*     */     
/*  78 */     if (flag == DefaultFlag.BUILD) {
/*  79 */       throw new IllegalArgumentException("Can't use build flag with allows()");
/*     */     }
/*     */     
/*  82 */     return StateFlag.test(new StateFlag.State[] { queryState((RegionAssociable)null, new StateFlag[] { flag }) });
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean allows(StateFlag flag, @Nullable LocalPlayer player) {
/*  88 */     Preconditions.checkNotNull(flag);
/*     */     
/*  90 */     if (flag == DefaultFlag.BUILD) {
/*  91 */       throw new IllegalArgumentException("Can't use build flag with allows()");
/*     */     }
/*     */     
/*  94 */     return StateFlag.test(new StateFlag.State[] { queryState((RegionAssociable)player, new StateFlag[] { flag }) });
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @Nullable
/*     */   public <T extends Flag<V>, V> V getFlag(T flag, @Nullable LocalPlayer groupPlayer) {
/* 101 */     return queryValue((RegionAssociable)groupPlayer, (Flag<V>)flag);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @Nullable
/*     */   public <T extends Flag<V>, V> V getFlag(T flag) {
/* 108 */     return getFlag(flag, (LocalPlayer)null);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\AbstractRegionSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */