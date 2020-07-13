/*     */ package com.sk89q.worldguard.bukkit.commands.task;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.sk89q.minecraft.util.commands.CommandException;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.domains.DefaultDomain;
/*     */ import com.sk89q.worldguard.protection.managers.RegionManager;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import com.sk89q.worldguard.util.profile.Profile;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.ChatColor;
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
/*     */ public class RegionLister
/*     */   implements Callable<Integer>
/*     */ {
/*  46 */   private static final Logger log = Logger.getLogger(RegionLister.class.getCanonicalName());
/*     */   
/*     */   private final WorldGuardPlugin plugin;
/*     */   private final CommandSender sender;
/*     */   private final RegionManager manager;
/*     */   private OwnerMatcher ownerMatcher;
/*     */   private int page;
/*     */   
/*     */   public RegionLister(WorldGuardPlugin plugin, RegionManager manager, CommandSender sender) {
/*  55 */     Preconditions.checkNotNull(plugin);
/*  56 */     Preconditions.checkNotNull(manager);
/*  57 */     Preconditions.checkNotNull(sender);
/*     */     
/*  59 */     this.plugin = plugin;
/*  60 */     this.manager = manager;
/*  61 */     this.sender = sender;
/*     */   }
/*     */   
/*     */   public int getPage() {
/*  65 */     return this.page;
/*     */   }
/*     */   
/*     */   public void setPage(int page) {
/*  69 */     this.page = page;
/*     */   }
/*     */   
/*     */   public void filterOwnedByPlayer(final Player player) {
/*  73 */     this.ownerMatcher = new OwnerMatcher()
/*     */       {
/*     */         public String getName() {
/*  76 */           return player.getName();
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isContainedWithin(DefaultDomain domain) throws CommandException {
/*  81 */           return domain.contains(player.getUniqueId());
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public void filterOwnedByName(String name, boolean nameOnly) {
/*  87 */     if (nameOnly) {
/*  88 */       filterOwnedByName(name);
/*     */     } else {
/*  90 */       filterOwnedByProfile(name);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void filterOwnedByName(final String name) {
/*  95 */     this.ownerMatcher = new OwnerMatcher()
/*     */       {
/*     */         public String getName() {
/*  98 */           return name;
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isContainedWithin(DefaultDomain domain) throws CommandException {
/* 103 */           return domain.contains(name);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private void filterOwnedByProfile(final String name) {
/* 109 */     this.ownerMatcher = new OwnerMatcher()
/*     */       {
/*     */         private UUID uniqueId;
/*     */         
/*     */         public String getName() {
/* 114 */           return name;
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isContainedWithin(DefaultDomain domain) throws CommandException {
/* 119 */           if (domain.contains(name)) {
/* 120 */             return true;
/*     */           }
/*     */           
/* 123 */           if (this.uniqueId == null) {
/*     */             Profile profile;
/*     */             
/*     */             try {
/* 127 */               profile = RegionLister.this.plugin.getProfileService().findByName(name);
/* 128 */             } catch (IOException e) {
/* 129 */               RegionLister.log.log(Level.WARNING, "Failed UUID lookup of '" + name + "'", e);
/* 130 */               throw new CommandException("Failed to lookup the UUID of '" + name + "'");
/* 131 */             } catch (InterruptedException e) {
/* 132 */               RegionLister.log.log(Level.WARNING, "Failed UUID lookup of '" + name + "'", e);
/* 133 */               throw new CommandException("The lookup the UUID of '" + name + "' was interrupted");
/*     */             } 
/*     */             
/* 136 */             if (profile == null) {
/* 137 */               throw new CommandException("A user by the name of '" + name + "' does not seem to exist.");
/*     */             }
/*     */             
/* 140 */             this.uniqueId = profile.getUniqueId();
/*     */           } 
/*     */           
/* 143 */           return domain.contains(this.uniqueId);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer call() throws Exception {
/* 150 */     Map<String, ProtectedRegion> regions = this.manager.getRegions();
/*     */ 
/*     */     
/* 153 */     List<RegionListEntry> entries = new ArrayList<RegionListEntry>();
/*     */     
/* 155 */     int index = 0;
/* 156 */     for (String id : regions.keySet()) {
/* 157 */       RegionListEntry entry = new RegionListEntry(id, index++);
/*     */ 
/*     */       
/* 160 */       ProtectedRegion region = regions.get(id);
/* 161 */       if (this.ownerMatcher != null) {
/* 162 */         entry.isOwner = this.ownerMatcher.isContainedWithin(region.getOwners());
/* 163 */         entry.isMember = this.ownerMatcher.isContainedWithin(region.getMembers());
/*     */         
/* 165 */         if (!entry.isOwner && !entry.isMember) {
/*     */           continue;
/*     */         }
/*     */       } 
/*     */       
/* 170 */       entries.add(entry);
/*     */     } 
/*     */     
/* 173 */     Collections.sort(entries);
/*     */     
/* 175 */     int totalSize = entries.size();
/* 176 */     int pageSize = 10;
/* 177 */     int pages = (int)Math.ceil((totalSize / 10.0F));
/*     */     
/* 179 */     this.sender.sendMessage(ChatColor.RED + ((this.ownerMatcher == null) ? "Regions (page " : ("Regions for " + this.ownerMatcher
/* 180 */         .getName() + " (page ")) + (this.page + 1) + " of " + pages + "):");
/*     */ 
/*     */     
/* 183 */     if (this.page < pages)
/*     */     {
/* 185 */       for (int i = this.page * 10; i < this.page * 10 + 10 && 
/* 186 */         i < totalSize; i++)
/*     */       {
/*     */ 
/*     */         
/* 190 */         this.sender.sendMessage(ChatColor.YELLOW.toString() + entries.get(i));
/*     */       }
/*     */     }
/*     */     
/* 194 */     return Integer.valueOf(this.page);
/*     */   }
/*     */   
/*     */   private static interface OwnerMatcher {
/*     */     String getName();
/*     */     
/*     */     boolean isContainedWithin(DefaultDomain param1DefaultDomain) throws CommandException;
/*     */   }
/*     */   
/*     */   private class RegionListEntry implements Comparable<RegionListEntry> {
/*     */     private final String id;
/*     */     private final int index;
/*     */     boolean isOwner;
/*     */     boolean isMember;
/*     */     
/*     */     private RegionListEntry(String id, int index) {
/* 210 */       this.id = id;
/* 211 */       this.index = index;
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(RegionListEntry o) {
/* 216 */       if (this.isOwner != o.isOwner) {
/* 217 */         return this.isOwner ? 1 : -1;
/*     */       }
/* 219 */       if (this.isMember != o.isMember) {
/* 220 */         return this.isMember ? 1 : -1;
/*     */       }
/* 222 */       return this.id.compareTo(o.id);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 227 */       if (this.isOwner)
/* 228 */         return (this.index + 1) + ". +" + this.id; 
/* 229 */       if (this.isMember) {
/* 230 */         return (this.index + 1) + ". -" + this.id;
/*     */       }
/* 232 */       return (this.index + 1) + ". " + this.id;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\commands\task\RegionLister.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */