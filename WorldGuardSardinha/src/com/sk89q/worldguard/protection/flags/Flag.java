/*     */ package com.sk89q.worldguard.protection.flags;
/*     */ 
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import java.util.Collection;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bukkit.command.CommandSender;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Flag<T>
/*     */ {
/*     */   private final String name;
/*     */   private final RegionGroupFlag regionGroup;
/*     */   
/*     */   public Flag(String name, @Nullable RegionGroup defaultGroup) {
/*  44 */     this.name = name;
/*  45 */     this.regionGroup = (defaultGroup != null) ? new RegionGroupFlag(name + "-group", defaultGroup) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Flag(String name) {
/*  54 */     this(name, RegionGroup.ALL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  63 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T getDefault() {
/*  73 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public T chooseValue(Collection<T> values) {
/*  78 */     if (!values.isEmpty()) {
/*  79 */       return values.iterator().next();
/*     */     }
/*  81 */     return null;
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
/*     */   public boolean hasConflictStrategy() {
/*  98 */     return false;
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
/*     */   public boolean implicitlySetWithMembership() {
/* 111 */     return false;
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
/*     */   public boolean usesMembershipAsDefault() {
/* 124 */     return false;
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
/*     */   public boolean requiresSubject() {
/* 137 */     return false;
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
/*     */   public RegionGroupFlag getRegionGroupFlag() {
/* 149 */     return this.regionGroup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract T parseInput(WorldGuardPlugin paramWorldGuardPlugin, CommandSender paramCommandSender, String paramString) throws InvalidFlagFormat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract T unmarshal(@Nullable Object paramObject);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Object marshal(T paramT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 183 */     return getClass().getSimpleName() + "{" + "name='" + this.name + '\'' + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\flags\Flag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */