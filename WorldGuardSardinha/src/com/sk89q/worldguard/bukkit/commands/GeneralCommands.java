/*     */ package com.sk89q.worldguard.bukkit.commands;
/*     */ 
/*     */ import com.sk89q.minecraft.util.commands.Command;
/*     */ import com.sk89q.minecraft.util.commands.CommandContext;
/*     */ import com.sk89q.minecraft.util.commands.CommandException;
/*     */ import com.sk89q.minecraft.util.commands.CommandPermissions;
/*     */ import com.sk89q.worldedit.blocks.ItemType;
/*     */ import com.sk89q.worldguard.bukkit.ConfigurationManager;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.session.Session;
/*     */ import com.sk89q.worldguard.session.handler.GodMode;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GeneralCommands
/*     */ {
/*     */   private final WorldGuardPlugin plugin;
/*     */   
/*     */   public GeneralCommands(WorldGuardPlugin plugin) {
/*  40 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Command(aliases = {"god"}, usage = "[player]", desc = "Enable godmode on a player", flags = "s", max = 1)
/*     */   public void god(CommandContext args, CommandSender sender) throws CommandException {
/*  47 */     ConfigurationManager config = this.plugin.getGlobalStateManager();
/*     */     
/*  49 */     Iterable<? extends Player> targets = null;
/*  50 */     boolean included = false;
/*     */ 
/*     */     
/*  53 */     if (args.argsLength() == 0) {
/*  54 */       targets = this.plugin.matchPlayers(this.plugin.checkPlayer(sender));
/*     */ 
/*     */       
/*  57 */       this.plugin.checkPermission(sender, "worldguard.god");
/*     */     } else {
/*  59 */       targets = this.plugin.matchPlayers(sender, args.getString(0));
/*     */ 
/*     */       
/*  62 */       this.plugin.checkPermission(sender, "worldguard.god.other");
/*     */     } 
/*     */     
/*  65 */     for (Player player : targets) {
/*  66 */       Session session = this.plugin.getSessionManager().get(player);
/*     */       
/*  68 */       if (GodMode.set(player, session, true)) {
/*  69 */         player.setFireTicks(0);
/*     */ 
/*     */         
/*  72 */         if (player.equals(sender)) {
/*  73 */           player.sendMessage(ChatColor.YELLOW + "God mode enabled! Use /ungod to disable.");
/*     */ 
/*     */           
/*  76 */           included = true; continue;
/*     */         } 
/*  78 */         player.sendMessage(ChatColor.YELLOW + "God enabled by " + this.plugin
/*  79 */             .toName(sender) + ".");
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     if (!included && args.hasFlag('s')) {
/*  88 */       sender.sendMessage(ChatColor.YELLOW + "Players now have god mode.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Command(aliases = {"ungod"}, usage = "[player]", desc = "Disable godmode on a player", flags = "s", max = 1)
/*     */   public void ungod(CommandContext args, CommandSender sender) throws CommandException {
/*  96 */     ConfigurationManager config = this.plugin.getGlobalStateManager();
/*     */     
/*  98 */     Iterable<? extends Player> targets = null;
/*  99 */     boolean included = false;
/*     */ 
/*     */     
/* 102 */     if (args.argsLength() == 0) {
/* 103 */       targets = this.plugin.matchPlayers(this.plugin.checkPlayer(sender));
/*     */ 
/*     */       
/* 106 */       this.plugin.checkPermission(sender, "worldguard.god");
/*     */     } else {
/* 108 */       targets = this.plugin.matchPlayers(sender, args.getString(0));
/*     */ 
/*     */       
/* 111 */       this.plugin.checkPermission(sender, "worldguard.god.other");
/*     */     } 
/*     */     
/* 114 */     for (Player player : targets) {
/* 115 */       Session session = this.plugin.getSessionManager().get(player);
/*     */       
/* 117 */       if (GodMode.set(player, session, false)) {
/*     */         
/* 119 */         if (player.equals(sender)) {
/* 120 */           player.sendMessage(ChatColor.YELLOW + "God mode disabled!");
/*     */ 
/*     */           
/* 123 */           included = true; continue;
/*     */         } 
/* 125 */         player.sendMessage(ChatColor.YELLOW + "God disabled by " + this.plugin
/* 126 */             .toName(sender) + ".");
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     if (!included && args.hasFlag('s')) {
/* 135 */       sender.sendMessage(ChatColor.YELLOW + "Players no longer have god mode.");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Command(aliases = {"heal"}, usage = "[player]", desc = "Heal a player", flags = "s", max = 1)
/*     */   public void heal(CommandContext args, CommandSender sender) throws CommandException {
/* 142 */     Iterable<? extends Player> targets = null;
/* 143 */     boolean included = false;
/*     */ 
/*     */     
/* 146 */     if (args.argsLength() == 0) {
/* 147 */       targets = this.plugin.matchPlayers(this.plugin.checkPlayer(sender));
/*     */ 
/*     */       
/* 150 */       this.plugin.checkPermission(sender, "worldguard.heal");
/* 151 */     } else if (args.argsLength() == 1) {
/* 152 */       targets = this.plugin.matchPlayers(sender, args.getString(0));
/*     */ 
/*     */       
/* 155 */       this.plugin.checkPermission(sender, "worldguard.heal.other");
/*     */     } 
/*     */     
/* 158 */     for (Player player : targets) {
/* 159 */       player.setHealth(player.getMaxHealth());
/* 160 */       player.setFoodLevel(20);
/*     */ 
/*     */       
/* 163 */       if (player.equals(sender)) {
/* 164 */         player.sendMessage(ChatColor.YELLOW + "Healed!");
/*     */ 
/*     */         
/* 167 */         included = true; continue;
/*     */       } 
/* 169 */       player.sendMessage(ChatColor.YELLOW + "Healed by " + this.plugin
/* 170 */           .toName(sender) + ".");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 177 */     if (!included && args.hasFlag('s')) {
/* 178 */       sender.sendMessage(ChatColor.YELLOW.toString() + "Players healed.");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Command(aliases = {"slay"}, usage = "[player]", desc = "Slay a player", flags = "s", max = 1)
/*     */   public void slay(CommandContext args, CommandSender sender) throws CommandException {
/* 185 */     Iterable<? extends Player> targets = null;
/* 186 */     boolean included = false;
/*     */ 
/*     */     
/* 189 */     if (args.argsLength() == 0) {
/* 190 */       targets = this.plugin.matchPlayers(this.plugin.checkPlayer(sender));
/*     */ 
/*     */       
/* 193 */       this.plugin.checkPermission(sender, "worldguard.slay");
/* 194 */     } else if (args.argsLength() == 1) {
/* 195 */       targets = this.plugin.matchPlayers(sender, args.getString(0));
/*     */ 
/*     */       
/* 198 */       this.plugin.checkPermission(sender, "worldguard.slay.other");
/*     */     } 
/*     */     
/* 201 */     for (Player player : targets) {
/* 202 */       player.setHealth(0.0D);
/*     */ 
/*     */       
/* 205 */       if (player.equals(sender)) {
/* 206 */         player.sendMessage(ChatColor.YELLOW + "Slain!");
/*     */ 
/*     */         
/* 209 */         included = true; continue;
/*     */       } 
/* 211 */       player.sendMessage(ChatColor.YELLOW + "Slain by " + this.plugin
/* 212 */           .toName(sender) + ".");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 219 */     if (!included && args.hasFlag('s')) {
/* 220 */       sender.sendMessage(ChatColor.YELLOW.toString() + "Players slain.");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Command(aliases = {"locate"}, usage = "[player]", desc = "Locate a player", max = 1)
/*     */   @CommandPermissions({"worldguard.locate"})
/*     */   public void locate(CommandContext args, CommandSender sender) throws CommandException {
/* 228 */     Player player = this.plugin.checkPlayer(sender);
/*     */     
/* 230 */     if (args.argsLength() == 0) {
/* 231 */       player.setCompassTarget(player.getWorld().getSpawnLocation());
/*     */       
/* 233 */       sender.sendMessage(ChatColor.YELLOW.toString() + "Compass reset to spawn.");
/*     */     } else {
/* 235 */       Player target = this.plugin.matchSinglePlayer(sender, args.getString(0));
/* 236 */       player.setCompassTarget(target.getLocation());
/*     */       
/* 238 */       sender.sendMessage(ChatColor.YELLOW.toString() + "Compass repointed.");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Command(aliases = {"stack", ";"}, usage = "", desc = "Stack items", max = 0)
/*     */   @CommandPermissions({"worldguard.stack"})
/*     */   public void stack(CommandContext args, CommandSender sender) throws CommandException {
/* 246 */     Player player = this.plugin.checkPlayer(sender);
/* 247 */     boolean ignoreMax = this.plugin.hasPermission((CommandSender)player, "worldguard.stack.illegitimate");
/* 248 */     boolean ignoreDamaged = this.plugin.hasPermission((CommandSender)player, "worldguard.stack.damaged");
/*     */     
/* 250 */     ItemStack[] items = player.getInventory().getContents();
/* 251 */     int len = items.length;
/*     */     
/* 253 */     int affected = 0;
/*     */     
/* 255 */     for (int i = 0; i < len; i++) {
/* 256 */       ItemStack item = items[i];
/*     */ 
/*     */       
/* 259 */       if (item != null && item.getAmount() > 0 && (ignoreMax || item
/* 260 */         .getMaxStackSize() != 1)) {
/*     */ 
/*     */ 
/*     */         
/* 264 */         int max = ignoreMax ? 64 : item.getMaxStackSize();
/*     */         
/* 266 */         if (item.getAmount() < max) {
/* 267 */           int needed = max - item.getAmount();
/*     */ 
/*     */           
/* 270 */           for (int j = i + 1; j < len; j++) {
/* 271 */             ItemStack item2 = items[j];
/*     */ 
/*     */             
/* 274 */             if (item2 != null && item2.getAmount() > 0 && (ignoreMax || item
/* 275 */               .getMaxStackSize() != 1))
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 281 */               if (item2.getTypeId() == item.getTypeId() && ((
/* 282 */                 !ItemType.usesDamageValue(item.getTypeId()) && ignoreDamaged) || item
/* 283 */                 .getDurability() == item2.getDurability()) && ((item
/* 284 */                 .getItemMeta() == null && item2.getItemMeta() == null) || (item
/* 285 */                 .getItemMeta() != null && item
/* 286 */                 .getItemMeta().equals(item2.getItemMeta())))) {
/*     */                 
/* 288 */                 if (item2.getAmount() > needed) {
/* 289 */                   item.setAmount(max);
/* 290 */                   item2.setAmount(item2.getAmount() - needed);
/*     */                   
/*     */                   break;
/*     */                 } 
/* 294 */                 items[j] = null;
/* 295 */                 item.setAmount(item.getAmount() + item2.getAmount());
/* 296 */                 needed = max - item.getAmount();
/*     */ 
/*     */                 
/* 299 */                 affected++;
/*     */               }  } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 305 */     if (affected > 0) {
/* 306 */       player.getInventory().setContents(items);
/*     */     }
/*     */     
/* 309 */     player.sendMessage(ChatColor.YELLOW + "Items compacted into stacks!");
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\commands\GeneralCommands.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */