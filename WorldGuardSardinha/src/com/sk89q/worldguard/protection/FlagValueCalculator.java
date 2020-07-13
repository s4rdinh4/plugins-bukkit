/*     */ package com.sk89q.worldguard.protection;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.sk89q.worldguard.domains.Association;
/*     */ import com.sk89q.worldguard.protection.association.RegionAssociable;
/*     */ import com.sk89q.worldguard.protection.flags.DefaultFlag;
/*     */ import com.sk89q.worldguard.protection.flags.Flag;
/*     */ import com.sk89q.worldguard.protection.flags.RegionGroup;
/*     */ import com.sk89q.worldguard.protection.flags.StateFlag;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
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
/*     */ public class FlagValueCalculator
/*     */ {
/*     */   private final List<ProtectedRegion> regions;
/*     */   @Nullable
/*     */   private final ProtectedRegion globalRegion;
/*     */   private final Iterable<ProtectedRegion> applicable;
/*     */   
/*     */   public FlagValueCalculator(List<ProtectedRegion> regions, @Nullable ProtectedRegion globalRegion) {
/*  64 */     Preconditions.checkNotNull(regions);
/*     */     
/*  66 */     this.regions = regions;
/*  67 */     this.globalRegion = globalRegion;
/*     */     
/*  69 */     if (globalRegion != null) {
/*  70 */       this.applicable = Iterables.concat(regions, Arrays.asList(new ProtectedRegion[] { globalRegion }));
/*     */     } else {
/*  72 */       this.applicable = regions;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Iterable<ProtectedRegion> getApplicable() {
/*  83 */     return this.applicable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Result getMembership(RegionAssociable subject) {
/* 104 */     Preconditions.checkNotNull(subject);
/*     */     
/* 106 */     int minimumPriority = Integer.MIN_VALUE;
/* 107 */     Result result = Result.NO_REGIONS;
/*     */     
/* 109 */     Set<ProtectedRegion> ignoredRegions = Sets.newHashSet();
/*     */     
/* 111 */     for (ProtectedRegion region : getApplicable()) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 116 */       if (getPriority(region) < minimumPriority) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 121 */       if (getEffectiveFlag(region, (Flag<StateFlag.State>)DefaultFlag.PASSTHROUGH, subject) == StateFlag.State.ALLOW) {
/*     */         continue;
/*     */       }
/*     */       
/* 125 */       if (ignoredRegions.contains(region)) {
/*     */         continue;
/*     */       }
/*     */       
/* 129 */       minimumPriority = getPriority(region);
/*     */       
/* 131 */       boolean member = RegionGroup.MEMBERS.contains(subject.getAssociation(Arrays.asList(new ProtectedRegion[] { region })));
/*     */       
/* 133 */       if (member) {
/* 134 */         result = Result.SUCCESS;
/* 135 */         addParents(ignoredRegions, region); continue;
/*     */       } 
/* 137 */       return Result.FAIL;
/*     */     } 
/*     */ 
/*     */     
/* 141 */     return result;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public StateFlag.State queryState(@Nullable RegionAssociable subject, StateFlag... flags) {
/* 164 */     StateFlag.State value = null;
/*     */     
/* 166 */     for (StateFlag flag : flags) {
/* 167 */       value = StateFlag.combine(new StateFlag.State[] { value, queryValue(subject, (Flag<StateFlag.State>)flag) });
/* 168 */       if (value == StateFlag.State.DENY) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 173 */     return value;
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
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public StateFlag.State queryState(@Nullable RegionAssociable subject, StateFlag flag) {
/* 190 */     return queryValue(subject, (Flag<StateFlag.State>)flag);
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
/*     */   @Nullable
/*     */   public <V> V queryValue(@Nullable RegionAssociable subject, Flag<V> flag) {
/* 220 */     Collection<V> values = queryAllValues(subject, flag, true);
/* 221 */     return (V)flag.chooseValue(values);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <V> Collection<V> queryAllValues(@Nullable RegionAssociable subject, Flag<V> flag) {
/* 242 */     return queryAllValues(subject, flag, false);
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
/*     */   private <V> Collection<V> queryAllValues(@Nullable RegionAssociable subject, Flag<V> flag, boolean acceptOne) {
/* 265 */     Preconditions.checkNotNull(flag);
/*     */ 
/*     */     
/* 268 */     if (acceptOne && flag.hasConflictStrategy()) {
/* 269 */       acceptOne = false;
/*     */     }
/*     */ 
/*     */     
/* 273 */     if (flag.requiresSubject() && subject == null) {
/* 274 */       throw new NullPointerException("The " + flag.getName() + " flag is handled in a special fashion and requires a non-null subject parameter");
/*     */     }
/*     */     
/* 277 */     int minimumPriority = Integer.MIN_VALUE;
/*     */     
/* 279 */     Map<ProtectedRegion, V> consideredValues = new HashMap<ProtectedRegion, V>();
/* 280 */     Set<ProtectedRegion> ignoredParents = new HashSet<ProtectedRegion>();
/*     */     
/* 282 */     for (ProtectedRegion region : getApplicable()) {
/* 283 */       if (getPriority(region) < minimumPriority) {
/*     */         break;
/*     */       }
/*     */       
/* 287 */       if (ignoredParents.contains(region)) {
/*     */         continue;
/*     */       }
/*     */       
/* 291 */       V value = getEffectiveFlag(region, flag, subject);
/* 292 */       int priority = getPriority(region);
/*     */       
/* 294 */       if (value != null) {
/* 295 */         minimumPriority = priority;
/*     */         
/* 297 */         if (acceptOne) {
/* 298 */           return Arrays.asList((V[])new Object[] { value });
/*     */         }
/* 300 */         consideredValues.put(region, value);
/*     */       } 
/*     */ 
/*     */       
/* 304 */       addParents(ignoredParents, region);
/*     */ 
/*     */ 
/*     */       
/* 308 */       if (priority != minimumPriority && flag.implicitlySetWithMembership() && 
/* 309 */         getEffectiveFlag(region, (Flag<StateFlag.State>)DefaultFlag.PASSTHROUGH, subject) != StateFlag.State.ALLOW) {
/* 310 */         minimumPriority = getPriority(region);
/*     */       }
/*     */     } 
/*     */     
/* 314 */     if (flag.usesMembershipAsDefault() && consideredValues.isEmpty()) {
/* 315 */       switch (getMembership(subject)) {
/*     */         case FAIL:
/* 317 */           return (Collection<V>)ImmutableList.of();
/*     */         case SUCCESS:
/* 319 */           return (Collection<V>)ImmutableList.of(StateFlag.State.ALLOW);
/*     */       } 
/*     */     
/*     */     }
/* 323 */     if (consideredValues.isEmpty()) {
/* 324 */       V fallback = (V)flag.getDefault();
/* 325 */       return (fallback != null) ? (Collection<V>)ImmutableList.of(fallback) : (Collection<V>)ImmutableList.of();
/*     */     } 
/*     */     
/* 328 */     return consideredValues.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPriority(ProtectedRegion region) {
/* 339 */     if (region == this.globalRegion) {
/* 340 */       return Integer.MIN_VALUE;
/*     */     }
/* 342 */     return region.getPriority();
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
/*     */ 
/*     */   
/*     */   public <V> V getEffectiveFlag(ProtectedRegion region, Flag<V> flag, @Nullable RegionAssociable subject) {
/* 357 */     if (region == this.globalRegion) {
/* 358 */       if (flag == DefaultFlag.PASSTHROUGH) {
/*     */ 
/*     */         
/* 361 */         if (region.hasMembersOrOwners() || region.getFlag((Flag)DefaultFlag.PASSTHROUGH) == StateFlag.State.DENY) {
/* 362 */           return null;
/*     */         }
/* 364 */         return (V)StateFlag.State.ALLOW;
/*     */       } 
/*     */       
/* 367 */       if (flag instanceof StateFlag && ((StateFlag)flag).preventsAllowOnGlobal()) {
/*     */ 
/*     */         
/* 370 */         StateFlag.State value = (StateFlag.State)region.getFlag(flag);
/* 371 */         return (value != StateFlag.State.ALLOW) ? (V)value : null;
/*     */       } 
/*     */     } 
/*     */     
/* 375 */     ProtectedRegion current = region;
/*     */     
/* 377 */     List<ProtectedRegion> seen = new ArrayList<ProtectedRegion>();
/*     */     
/* 379 */     while (current != null) {
/* 380 */       seen.add(current);
/*     */       
/* 382 */       V value = (V)current.getFlag(flag);
/*     */       
/* 384 */       if (value != null) {
/* 385 */         boolean use = true;
/*     */         
/* 387 */         if (flag.getRegionGroupFlag() != null) {
/* 388 */           RegionGroup group = (RegionGroup)current.getFlag((Flag)flag.getRegionGroupFlag());
/* 389 */           if (group == null) {
/* 390 */             group = flag.getRegionGroupFlag().getDefault();
/*     */           }
/*     */           
/* 393 */           if (group == null) {
/* 394 */             use = false;
/* 395 */           } else if (subject == null) {
/* 396 */             use = group.contains(Association.NON_MEMBER);
/* 397 */           } else if (!group.contains(subject.getAssociation(seen))) {
/* 398 */             use = false;
/*     */           } 
/*     */         } 
/*     */         
/* 402 */         if (use) {
/* 403 */           return value;
/*     */         }
/*     */       } 
/*     */       
/* 407 */       current = current.getParent();
/*     */     } 
/*     */     
/* 410 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addParents(Set<ProtectedRegion> ignored, ProtectedRegion region) {
/* 420 */     ProtectedRegion parent = region.getParent();
/*     */     
/* 422 */     while (parent != null) {
/* 423 */       ignored.add(parent);
/* 424 */       parent = parent.getParent();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Result
/*     */   {
/* 437 */     NO_REGIONS,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 443 */     FAIL,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 449 */     SUCCESS;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\FlagValueCalculator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */