/*     */ package com.sk89q.worldguard.bukkit.commands;
/*     */ 
/*     */ import com.sk89q.minecraft.util.commands.Command;
/*     */ import com.sk89q.minecraft.util.commands.CommandContext;
/*     */ import com.sk89q.minecraft.util.commands.CommandException;
/*     */ import com.sk89q.minecraft.util.commands.CommandPermissions;
/*     */ import com.sk89q.worldguard.bukkit.BukkitUtil;
/*     */ import com.sk89q.worldguard.bukkit.ConfigurationManager;
/*     */ import com.sk89q.worldguard.bukkit.WorldConfiguration;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Entity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ToggleCommands
/*     */ {
/*     */   private final WorldGuardPlugin plugin;
/*     */   
/*     */   public ToggleCommands(WorldGuardPlugin plugin) {
/*  41 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Command(aliases = {"stopfire"}, usage = "[<world>]", desc = "Disables all fire spread temporarily", max = 1)
/*     */   @CommandPermissions({"worldguard.fire-toggle.stop"})
/*     */   public void stopFire(CommandContext args, CommandSender sender) throws CommandException {
/*     */     World world;
/*  51 */     if (args.argsLength() == 0) {
/*  52 */       world = this.plugin.checkPlayer(sender).getWorld();
/*     */     } else {
/*  54 */       world = this.plugin.matchWorld(sender, args.getString(0));
/*     */     } 
/*     */     
/*  57 */     WorldConfiguration wcfg = this.plugin.getGlobalStateManager().get(world);
/*     */     
/*  59 */     if (!wcfg.fireSpreadDisableToggle) {
/*  60 */       this.plugin.getServer().broadcastMessage(ChatColor.YELLOW + "Fire spread has been globally disabled for '" + world
/*     */           
/*  62 */           .getName() + "' by " + this.plugin
/*  63 */           .toName(sender) + ".");
/*     */     } else {
/*  65 */       sender.sendMessage(ChatColor.YELLOW + "Fire spread was already globally disabled.");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  70 */     wcfg.fireSpreadDisableToggle = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Command(aliases = {"allowfire"}, usage = "[<world>]", desc = "Allows all fire spread temporarily", max = 1)
/*     */   @CommandPermissions({"worldguard.fire-toggle.stop"})
/*     */   public void allowFire(CommandContext args, CommandSender sender) throws CommandException {
/*     */     World world;
/*  80 */     if (args.argsLength() == 0) {
/*  81 */       world = this.plugin.checkPlayer(sender).getWorld();
/*     */     } else {
/*  83 */       world = this.plugin.matchWorld(sender, args.getString(0));
/*     */     } 
/*     */     
/*  86 */     WorldConfiguration wcfg = this.plugin.getGlobalStateManager().get(world);
/*     */     
/*  88 */     if (wcfg.fireSpreadDisableToggle) {
/*  89 */       this.plugin.getServer().broadcastMessage(ChatColor.YELLOW + "Fire spread has been globally for '" + world
/*  90 */           .getName() + "' re-enabled by " + this.plugin
/*  91 */           .toName(sender) + ".");
/*     */     } else {
/*  93 */       sender.sendMessage(ChatColor.YELLOW + "Fire spread was already globally enabled.");
/*     */     } 
/*     */ 
/*     */     
/*  97 */     wcfg.fireSpreadDisableToggle = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Command(aliases = {"halt-activity", "stoplag", "haltactivity"}, desc = "Attempts to cease as much activity in order to stop lag", flags = "cis", max = 0)
/*     */   @CommandPermissions({"worldguard.halt-activity"})
/*     */   public void stopLag(CommandContext args, CommandSender sender) throws CommandException {
/* 105 */     ConfigurationManager configManager = this.plugin.getGlobalStateManager();
/*     */     
/* 107 */     if (args.hasFlag('i')) {
/* 108 */       if (configManager.activityHaltToggle) {
/* 109 */         sender.sendMessage(ChatColor.YELLOW + "ALL intensive server activity is not allowed.");
/*     */       } else {
/*     */         
/* 112 */         sender.sendMessage(ChatColor.YELLOW + "ALL intensive server activity is allowed.");
/*     */       } 
/*     */     } else {
/*     */       
/* 116 */       configManager.activityHaltToggle = !args.hasFlag('c');
/*     */       
/* 118 */       if (configManager.activityHaltToggle) {
/* 119 */         if (!(sender instanceof org.bukkit.entity.Player)) {
/* 120 */           sender.sendMessage(ChatColor.YELLOW + "ALL intensive server activity halted.");
/*     */         }
/*     */ 
/*     */         
/* 124 */         if (!args.hasFlag('s')) {
/* 125 */           this.plugin.getServer().broadcastMessage(ChatColor.YELLOW + "ALL intensive server activity halted by " + this.plugin
/*     */               
/* 127 */               .toName(sender) + ".");
/*     */         } else {
/* 129 */           sender.sendMessage(ChatColor.YELLOW + "(Silent) ALL intensive server activity halted by " + this.plugin
/*     */               
/* 131 */               .toName(sender) + ".");
/*     */         } 
/*     */         
/* 134 */         for (World world : this.plugin.getServer().getWorlds()) {
/* 135 */           int removed = 0;
/*     */           
/* 137 */           for (Entity entity : world.getEntities()) {
/* 138 */             if (BukkitUtil.isIntensiveEntity(entity)) {
/* 139 */               entity.remove();
/* 140 */               removed++;
/*     */             } 
/*     */           } 
/*     */           
/* 144 */           if (removed > 10) {
/* 145 */             sender.sendMessage("" + removed + " entities (>10) auto-removed from " + world
/* 146 */                 .getName());
/*     */           }
/*     */         }
/*     */       
/* 150 */       } else if (!args.hasFlag('s')) {
/* 151 */         this.plugin.getServer().broadcastMessage(ChatColor.YELLOW + "ALL intensive server activity is now allowed.");
/*     */ 
/*     */         
/* 154 */         if (!(sender instanceof org.bukkit.entity.Player)) {
/* 155 */           sender.sendMessage(ChatColor.YELLOW + "ALL intensive server activity is now allowed.");
/*     */         }
/*     */       } else {
/*     */         
/* 159 */         sender.sendMessage(ChatColor.YELLOW + "(Silent) ALL intensive server activity is now allowed.");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\commands\ToggleCommands.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */