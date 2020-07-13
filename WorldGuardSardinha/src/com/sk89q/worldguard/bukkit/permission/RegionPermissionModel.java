/*     */ package com.sk89q.worldguard.bukkit.permission;
/*     */ 
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.protection.flags.Flag;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RegionPermissionModel
/*     */   extends AbstractPermissionModel
/*     */ {
/*     */   public RegionPermissionModel(WorldGuardPlugin plugin, CommandSender sender) {
/*  38 */     super(plugin, sender);
/*     */   }
/*     */   
/*     */   public boolean mayIgnoreRegionProtection(World world) {
/*  42 */     return hasPluginPermission("region.bypass." + world.getName());
/*     */   }
/*     */   
/*     */   public boolean mayForceLoadRegions() {
/*  46 */     return hasPluginPermission("region.load");
/*     */   }
/*     */   
/*     */   public boolean mayForceSaveRegions() {
/*  50 */     return hasPluginPermission("region.save");
/*     */   }
/*     */   
/*     */   public boolean mayMigrateRegionStore() {
/*  54 */     return hasPluginPermission("region.migratedb");
/*     */   }
/*     */   
/*     */   public boolean mayMigrateRegionNames() {
/*  58 */     return hasPluginPermission("region.migrateuuid");
/*     */   }
/*     */   
/*     */   public boolean mayDefine() {
/*  62 */     return hasPluginPermission("region.define");
/*     */   }
/*     */   
/*     */   public boolean mayRedefine(ProtectedRegion region) {
/*  66 */     return hasPatternPermission("redefine", region);
/*     */   }
/*     */   
/*     */   public boolean mayClaim() {
/*  70 */     return hasPluginPermission("region.claim");
/*     */   }
/*     */   
/*     */   public boolean mayClaimRegionsUnbounded() {
/*  74 */     return hasPluginPermission("region.unlimited");
/*     */   }
/*     */   
/*     */   public boolean mayDelete(ProtectedRegion region) {
/*  78 */     return hasPatternPermission("remove", region);
/*     */   }
/*     */   
/*     */   public boolean maySetPriority(ProtectedRegion region) {
/*  82 */     return hasPatternPermission("setpriority", region);
/*     */   }
/*     */   
/*     */   public boolean maySetParent(ProtectedRegion child, ProtectedRegion parent) {
/*  86 */     return (hasPatternPermission("setparent", child) && (parent == null || 
/*     */       
/*  88 */       hasPatternPermission("setparent", parent)));
/*     */   }
/*     */   
/*     */   public boolean maySelect(ProtectedRegion region) {
/*  92 */     return hasPatternPermission("select", region);
/*     */   }
/*     */   
/*     */   public boolean mayLookup(ProtectedRegion region) {
/*  96 */     return hasPatternPermission("info", region);
/*     */   }
/*     */   
/*     */   public boolean mayTeleportTo(ProtectedRegion region) {
/* 100 */     return hasPatternPermission("teleport", region);
/*     */   }
/*     */   
/*     */   public boolean mayList() {
/* 104 */     return hasPluginPermission("region.list");
/*     */   }
/*     */   
/*     */   public boolean mayList(String targetPlayer) {
/* 108 */     if (targetPlayer == null) {
/* 109 */       return mayList();
/*     */     }
/*     */     
/* 112 */     if (targetPlayer.equalsIgnoreCase(getSender().getName())) {
/* 113 */       return hasPluginPermission("region.list.own");
/*     */     }
/* 115 */     return mayList();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean maySetFlag(ProtectedRegion region) {
/* 120 */     return hasPatternPermission("flag.regions", region);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean maySetFlag(ProtectedRegion region, Flag<?> flag) {
/* 125 */     return hasPatternPermission("flag.flags." + flag
/* 126 */         .getName().toLowerCase(), region);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean maySetFlag(ProtectedRegion region, Flag<?> flag, @Nullable String value) {
/*     */     String sanitizedValue;
/* 132 */     if (value != null) {
/* 133 */       sanitizedValue = value.trim().toLowerCase().replaceAll("[^a-z0-9]", "");
/* 134 */       if (sanitizedValue.length() > 20) {
/* 135 */         sanitizedValue = sanitizedValue.substring(0, 20);
/*     */       }
/*     */     } else {
/* 138 */       sanitizedValue = "unset";
/*     */     } 
/*     */ 
/*     */     
/* 142 */     return hasPatternPermission("flag.flags." + flag
/* 143 */         .getName().toLowerCase() + "." + sanitizedValue, region);
/*     */   }
/*     */   
/*     */   public boolean mayAddMembers(ProtectedRegion region) {
/* 147 */     return hasPatternPermission("addmember", region);
/*     */   }
/*     */   
/*     */   public boolean mayAddOwners(ProtectedRegion region) {
/* 151 */     return hasPatternPermission("addowner", region);
/*     */   }
/*     */   
/*     */   public boolean mayRemoveMembers(ProtectedRegion region) {
/* 155 */     return hasPatternPermission("removemember", region);
/*     */   }
/*     */   
/*     */   public boolean mayRemoveOwners(ProtectedRegion region) {
/* 159 */     return hasPatternPermission("removeowner", region);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasPatternPermission(String perm, ProtectedRegion region) {
/* 170 */     if (!(getSender() instanceof Player)) {
/* 171 */       return true;
/*     */     }
/*     */     
/* 174 */     LocalPlayer localPlayer = getPlugin().wrapPlayer((Player)getSender());
/* 175 */     String idLower = region.getId().toLowerCase();
/*     */ 
/*     */     
/* 178 */     if (region.isOwner(localPlayer))
/* 179 */       return (hasPluginPermission("region." + perm + ".own." + idLower) || 
/* 180 */         hasPluginPermission("region." + perm + ".member." + idLower)); 
/* 181 */     if (region.isMember(localPlayer)) {
/* 182 */       return hasPluginPermission("region." + perm + ".member." + idLower);
/*     */     }
/* 184 */     String effectivePerm = "region." + perm + "." + idLower;
/*     */ 
/*     */     
/* 187 */     return hasPluginPermission(effectivePerm);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\permission\RegionPermissionModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */