/*     */ package com.sk89q.worldguard.bukkit.commands.task;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.sk89q.minecraft.util.commands.CommandContext;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.domains.DefaultDomain;
/*     */ import com.sk89q.worldguard.protection.managers.RegionManager;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import com.sk89q.worldguard.protection.util.DomainInputResolver;
/*     */ import java.util.concurrent.Callable;
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
/*     */ public class RegionAdder
/*     */   implements Callable<ProtectedRegion>
/*     */ {
/*     */   private final WorldGuardPlugin plugin;
/*     */   private final RegionManager manager;
/*     */   private final ProtectedRegion region;
/*     */   @Nullable
/*     */   private String[] ownersInput;
/*  45 */   private DomainInputResolver.UserLocatorPolicy locatorPolicy = DomainInputResolver.UserLocatorPolicy.UUID_ONLY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegionAdder(WorldGuardPlugin plugin, RegionManager manager, ProtectedRegion region) {
/*  55 */     Preconditions.checkNotNull(plugin);
/*  56 */     Preconditions.checkNotNull(manager);
/*  57 */     Preconditions.checkNotNull(region);
/*     */     
/*  59 */     this.plugin = plugin;
/*  60 */     this.manager = manager;
/*  61 */     this.region = region;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addOwnersFromCommand(CommandContext args, int namesIndex) {
/*  71 */     if (args.argsLength() >= namesIndex) {
/*  72 */       setLocatorPolicy(args.hasFlag('n') ? DomainInputResolver.UserLocatorPolicy.NAME_ONLY : DomainInputResolver.UserLocatorPolicy.UUID_ONLY);
/*  73 */       setOwnersInput(args.getSlice(namesIndex));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ProtectedRegion call() throws Exception {
/*  79 */     if (this.ownersInput != null) {
/*  80 */       DomainInputResolver resolver = new DomainInputResolver(this.plugin.getProfileService(), this.ownersInput);
/*  81 */       resolver.setLocatorPolicy(this.locatorPolicy);
/*  82 */       DefaultDomain domain = resolver.call();
/*  83 */       this.region.getOwners().addAll(domain);
/*     */     } 
/*     */     
/*  86 */     this.manager.addRegion(this.region);
/*     */     
/*  88 */     return this.region;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String[] getOwnersInput() {
/*  93 */     return this.ownersInput;
/*     */   }
/*     */   
/*     */   public void setOwnersInput(@Nullable String[] ownersInput) {
/*  97 */     this.ownersInput = ownersInput;
/*     */   }
/*     */   
/*     */   public DomainInputResolver.UserLocatorPolicy getLocatorPolicy() {
/* 101 */     return this.locatorPolicy;
/*     */   }
/*     */   
/*     */   public void setLocatorPolicy(DomainInputResolver.UserLocatorPolicy locatorPolicy) {
/* 105 */     this.locatorPolicy = locatorPolicy;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\commands\task\RegionAdder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */