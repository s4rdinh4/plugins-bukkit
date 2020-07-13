/*      */ package com.sk89q.worldguard.bukkit.commands.region;
/*      */ 
/*      */ import com.google.common.base.Preconditions;
/*      */ import com.google.common.util.concurrent.FutureCallback;
/*      */ import com.google.common.util.concurrent.Futures;
/*      */ import com.google.common.util.concurrent.ListenableFuture;
/*      */ import com.sk89q.minecraft.util.commands.Command;
/*      */ import com.sk89q.minecraft.util.commands.CommandContext;
/*      */ import com.sk89q.minecraft.util.commands.CommandException;
/*      */ import com.sk89q.minecraft.util.commands.CommandPermissionsException;
/*      */ import com.sk89q.worldedit.Location;
/*      */ import com.sk89q.worldedit.bukkit.BukkitUtil;
/*      */ import com.sk89q.worldguard.LocalPlayer;
/*      */ import com.sk89q.worldguard.bukkit.ConfigurationManager;
/*      */ import com.sk89q.worldguard.bukkit.RegionContainer;
/*      */ import com.sk89q.worldguard.bukkit.WorldConfiguration;
/*      */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*      */ import com.sk89q.worldguard.bukkit.commands.AsyncCommandHelper;
/*      */ import com.sk89q.worldguard.bukkit.commands.CommandUtils;
/*      */ import com.sk89q.worldguard.bukkit.commands.FutureProgressListener;
/*      */ import com.sk89q.worldguard.bukkit.commands.MessageFutureCallback;
/*      */ import com.sk89q.worldguard.bukkit.commands.task.RegionAdder;
/*      */ import com.sk89q.worldguard.bukkit.commands.task.RegionLister;
/*      */ import com.sk89q.worldguard.bukkit.commands.task.RegionManagerReloader;
/*      */ import com.sk89q.worldguard.bukkit.commands.task.RegionManagerSaver;
/*      */ import com.sk89q.worldguard.bukkit.commands.task.RegionRemover;
/*      */ import com.sk89q.worldguard.bukkit.permission.RegionPermissionModel;
/*      */ import com.sk89q.worldguard.bukkit.util.logging.LoggerToChatHandler;
/*      */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*      */ import com.sk89q.worldguard.protection.flags.DefaultFlag;
/*      */ import com.sk89q.worldguard.protection.flags.Flag;
/*      */ import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
/*      */ import com.sk89q.worldguard.protection.flags.RegionGroup;
/*      */ import com.sk89q.worldguard.protection.flags.RegionGroupFlag;
/*      */ import com.sk89q.worldguard.protection.managers.RegionManager;
/*      */ import com.sk89q.worldguard.protection.managers.RemovalStrategy;
/*      */ import com.sk89q.worldguard.protection.managers.migration.DriverMigration;
/*      */ import com.sk89q.worldguard.protection.managers.migration.Migration;
/*      */ import com.sk89q.worldguard.protection.managers.migration.MigrationException;
/*      */ import com.sk89q.worldguard.protection.managers.migration.UUIDMigration;
/*      */ import com.sk89q.worldguard.protection.managers.storage.DriverType;
/*      */ import com.sk89q.worldguard.protection.managers.storage.RegionDriver;
/*      */ import com.sk89q.worldguard.protection.regions.GlobalProtectedRegion;
/*      */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*      */ import com.sk89q.worldguard.util.Enums;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.logging.Handler;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.ChatColor;
/*      */ import org.bukkit.World;
/*      */ import org.bukkit.command.CommandSender;
/*      */ import org.bukkit.entity.Player;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class RegionCommands
/*      */   extends RegionCommandsBase
/*      */ {
/*   82 */   private static final Logger log = Logger.getLogger(RegionCommands.class.getCanonicalName());
/*      */   private final WorldGuardPlugin plugin;
/*      */   
/*      */   public RegionCommands(WorldGuardPlugin plugin) {
/*   86 */     Preconditions.checkNotNull(plugin);
/*   87 */     this.plugin = plugin;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Command(aliases = {"define", "def", "d", "create"}, usage = "<id> [<owner1> [<owner2> [<owners...>]]]", flags = "ng", desc = "Defines a region", min = 1)
/*      */   public void define(CommandContext args, CommandSender sender) throws CommandException {
/*      */     ProtectedRegion region;
/*  103 */     warnAboutSaveFailures(sender);
/*  104 */     Player player = this.plugin.checkPlayer(sender);
/*      */ 
/*      */     
/*  107 */     if (!getPermissionModel(sender).mayDefine()) {
/*  108 */       throw new CommandPermissionsException();
/*      */     }
/*      */     
/*  111 */     String id = checkRegionId(args.getString(0), false);
/*      */     
/*  113 */     RegionManager manager = checkRegionManager(this.plugin, player.getWorld());
/*      */     
/*  115 */     checkRegionDoesNotExist(manager, id, true);
/*      */ 
/*      */ 
/*      */     
/*  119 */     if (args.hasFlag('g')) {
/*  120 */       GlobalProtectedRegion globalProtectedRegion = new GlobalProtectedRegion(id);
/*      */     } else {
/*  122 */       region = checkRegionFromSelection(player, id);
/*  123 */       warnAboutDimensions((CommandSender)player, region);
/*  124 */       informNewUser((CommandSender)player, manager, region);
/*      */     } 
/*      */     
/*  127 */     RegionAdder task = new RegionAdder(this.plugin, manager, region);
/*  128 */     task.addOwnersFromCommand(args, 2);
/*  129 */     ListenableFuture<?> future = this.plugin.getExecutorService().submit((Callable)task);
/*      */     
/*  131 */     AsyncCommandHelper.wrap(future, this.plugin, (CommandSender)player)
/*  132 */       .formatUsing(new Object[] { id
/*  133 */         }).registerWithSupervisor("Adding the region '%s'...")
/*  134 */       .sendMessageAfterDelay("(Please wait... adding '%s'...)")
/*  135 */       .thenRespondWith("A new region has been made named '%s'.", "Failed to add the region '%s'");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Command(aliases = {"redefine", "update", "move"}, usage = "<id>", desc = "Re-defines the shape of a region", flags = "g", min = 1, max = 1)
/*      */   public void redefine(CommandContext args, CommandSender sender) throws CommandException {
/*      */     ProtectedRegion region;
/*  153 */     warnAboutSaveFailures(sender);
/*      */     
/*  155 */     Player player = this.plugin.checkPlayer(sender);
/*  156 */     World world = player.getWorld();
/*      */     
/*  158 */     String id = checkRegionId(args.getString(0), false);
/*      */     
/*  160 */     RegionManager manager = checkRegionManager(this.plugin, world);
/*      */     
/*  162 */     ProtectedRegion existing = checkExistingRegion(manager, id, false);
/*      */ 
/*      */     
/*  165 */     if (!getPermissionModel(sender).mayRedefine(existing)) {
/*  166 */       throw new CommandPermissionsException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  171 */     if (args.hasFlag('g')) {
/*  172 */       GlobalProtectedRegion globalProtectedRegion = new GlobalProtectedRegion(id);
/*      */     } else {
/*  174 */       region = checkRegionFromSelection(player, id);
/*  175 */       warnAboutDimensions((CommandSender)player, region);
/*  176 */       informNewUser((CommandSender)player, manager, region);
/*      */     } 
/*      */     
/*  179 */     region.copyFrom(existing);
/*      */     
/*  181 */     RegionAdder task = new RegionAdder(this.plugin, manager, region);
/*  182 */     ListenableFuture<?> future = this.plugin.getExecutorService().submit((Callable)task);
/*      */     
/*  184 */     AsyncCommandHelper.wrap(future, this.plugin, (CommandSender)player)
/*  185 */       .formatUsing(new Object[] { id
/*  186 */         }).registerWithSupervisor("Updating the region '%s'...")
/*  187 */       .sendMessageAfterDelay("(Please wait... updating '%s'...)")
/*  188 */       .thenRespondWith("Region '%s' has been updated with a new area.", "Failed to update the region '%s'");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Command(aliases = {"claim"}, usage = "<id> [<owner1> [<owner2> [<owners...>]]]", desc = "Claim a region", min = 1)
/*      */   public void claim(CommandContext args, CommandSender sender) throws CommandException {
/*  208 */     warnAboutSaveFailures(sender);
/*      */     
/*  210 */     Player player = this.plugin.checkPlayer(sender);
/*  211 */     LocalPlayer localPlayer = this.plugin.wrapPlayer(player);
/*  212 */     RegionPermissionModel permModel = getPermissionModel(sender);
/*      */ 
/*      */     
/*  215 */     if (!permModel.mayClaim()) {
/*  216 */       throw new CommandPermissionsException();
/*      */     }
/*      */     
/*  219 */     String id = checkRegionId(args.getString(0), false);
/*      */     
/*  221 */     RegionManager manager = checkRegionManager(this.plugin, player.getWorld());
/*      */     
/*  223 */     checkRegionDoesNotExist(manager, id, false);
/*  224 */     ProtectedRegion region = checkRegionFromSelection(player, id);
/*      */     
/*  226 */     WorldConfiguration wcfg = this.plugin.getGlobalStateManager().get(player.getWorld());
/*      */ 
/*      */     
/*  229 */     if (!permModel.mayClaimRegionsUnbounded()) {
/*  230 */       int maxRegionCount = wcfg.getMaxRegionCount(player);
/*  231 */       if (maxRegionCount >= 0 && manager
/*  232 */         .getRegionCountOfPlayer(localPlayer) >= maxRegionCount) {
/*  233 */         throw new CommandException("You own too many regions, delete one first to claim a new one.");
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  238 */     ProtectedRegion existing = manager.getRegion(id);
/*      */ 
/*      */     
/*  241 */     if (existing != null && 
/*  242 */       !existing.getOwners().contains(localPlayer)) {
/*  243 */       throw new CommandException("This region already exists and you don't own it.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  249 */     ApplicableRegionSet regions = manager.getApplicableRegions(region);
/*      */ 
/*      */     
/*  252 */     if (regions.size() > 0) {
/*  253 */       if (!regions.isOwnerOfAll(localPlayer)) {
/*  254 */         throw new CommandException("This region overlaps with someone else's region.");
/*      */       }
/*      */     }
/*  257 */     else if (wcfg.claimOnlyInsideExistingRegions) {
/*  258 */       throw new CommandException("You may only claim regions inside existing regions that you or your group own.");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  263 */     if (wcfg.maxClaimVolume >= Integer.MAX_VALUE) {
/*  264 */       throw new CommandException("The maximum claim volume get in the configuration is higher than is supported. Currently, it must be 2147483647 or smaller. Please contact a server administrator.");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  269 */     if (!permModel.mayClaimRegionsUnbounded()) {
/*  270 */       if (region instanceof com.sk89q.worldguard.protection.regions.ProtectedPolygonalRegion) {
/*  271 */         throw new CommandException("Polygons are currently not supported for /rg claim.");
/*      */       }
/*      */       
/*  274 */       if (region.volume() > wcfg.maxClaimVolume) {
/*  275 */         player.sendMessage(ChatColor.RED + "This region is too large to claim.");
/*  276 */         player.sendMessage(ChatColor.RED + "Max. volume: " + wcfg.maxClaimVolume + ", your volume: " + region
/*  277 */             .volume());
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  282 */     region.getOwners().addPlayer(player.getName());
/*      */     
/*  284 */     RegionAdder task = new RegionAdder(this.plugin, manager, region);
/*  285 */     ListenableFuture<?> future = this.plugin.getExecutorService().submit((Callable)task);
/*      */     
/*  287 */     AsyncCommandHelper.wrap(future, this.plugin, (CommandSender)player)
/*  288 */       .formatUsing(new Object[] { id
/*  289 */         }).registerWithSupervisor("Claiming the region '%s'...")
/*  290 */       .sendMessageAfterDelay("(Please wait... claiming '%s'...)")
/*  291 */       .thenRespondWith("A new region has been claimed named '%s'.", "Failed to claim the region '%s'");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Command(aliases = {"select", "sel", "s"}, usage = "[id]", desc = "Load a region as a WorldEdit selection", min = 0, max = 1)
/*      */   public void select(CommandContext args, CommandSender sender) throws CommandException {
/*      */     ProtectedRegion existing;
/*  308 */     Player player = this.plugin.checkPlayer(sender);
/*  309 */     World world = player.getWorld();
/*  310 */     RegionManager manager = checkRegionManager(this.plugin, world);
/*      */ 
/*      */ 
/*      */     
/*  314 */     if (args.argsLength() == 0) {
/*  315 */       existing = checkRegionStandingIn(manager, player);
/*      */     } else {
/*  317 */       existing = checkExistingRegion(manager, args.getString(0), false);
/*      */     } 
/*      */ 
/*      */     
/*  321 */     if (!getPermissionModel(sender).maySelect(existing)) {
/*  322 */       throw new CommandPermissionsException();
/*      */     }
/*      */ 
/*      */     
/*  326 */     setPlayerSelection(player, existing);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Command(aliases = {"info", "i"}, usage = "[id]", flags = "usw:", desc = "Get information about a region", min = 0, max = 1)
/*      */   public void info(CommandContext args, CommandSender sender) throws CommandException {
/*      */     ProtectedRegion existing;
/*  342 */     warnAboutSaveFailures(sender);
/*      */     
/*  344 */     World world = checkWorld(args, sender, 'w');
/*  345 */     RegionPermissionModel permModel = getPermissionModel(sender);
/*      */ 
/*      */     
/*  348 */     RegionManager manager = checkRegionManager(this.plugin, world);
/*      */ 
/*      */     
/*  351 */     if (args.argsLength() == 0) {
/*  352 */       if (!(sender instanceof Player)) {
/*  353 */         throw new CommandException("Please specify the region with /region info -w world_name region_name.");
/*      */       }
/*      */ 
/*      */       
/*  357 */       existing = checkRegionStandingIn(manager, (Player)sender, true);
/*      */     } else {
/*  359 */       existing = checkExistingRegion(manager, args.getString(0), true);
/*      */     } 
/*      */ 
/*      */     
/*  363 */     if (!permModel.mayLookup(existing)) {
/*  364 */       throw new CommandPermissionsException();
/*      */     }
/*      */ 
/*      */     
/*  368 */     if (args.hasFlag('s')) {
/*      */       
/*  370 */       if (!permModel.maySelect(existing)) {
/*  371 */         throw new CommandPermissionsException();
/*      */       }
/*      */       
/*  374 */       setPlayerSelection(this.plugin.checkPlayer(sender), existing);
/*      */     } 
/*      */ 
/*      */     
/*  378 */     RegionPrintoutBuilder printout = new RegionPrintoutBuilder(existing, args.hasFlag('u') ? null : this.plugin.getProfileCache());
/*  379 */     ListenableFuture<?> future = Futures.transform(this.plugin
/*  380 */         .getExecutorService().submit(printout), 
/*  381 */         CommandUtils.messageFunction(sender));
/*      */ 
/*      */     
/*  384 */     FutureProgressListener.addProgressListener(future, sender, "(Please wait... fetching region information...)");
/*      */ 
/*      */ 
/*      */     
/*  388 */     Futures.addCallback(future, (FutureCallback)(new MessageFutureCallback.Builder(this.plugin, sender))
/*      */         
/*  390 */         .onFailure("Failed to fetch region information")
/*  391 */         .build());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Command(aliases = {"list"}, usage = "[page]", desc = "Get a list of regions", flags = "np:w:", max = 1)
/*      */   public void list(CommandContext args, CommandSender sender) throws CommandException {
/*      */     String ownedBy;
/*  407 */     warnAboutSaveFailures(sender);
/*      */     
/*  409 */     World world = checkWorld(args, sender, 'w');
/*      */ 
/*      */ 
/*      */     
/*  413 */     int page = args.getInteger(0, 1) - 1;
/*  414 */     if (page < 0) {
/*  415 */       page = 0;
/*      */     }
/*      */ 
/*      */     
/*  419 */     if (args.hasFlag('p')) {
/*  420 */       ownedBy = args.getFlag('p');
/*      */     } else {
/*  422 */       ownedBy = null;
/*      */     } 
/*      */ 
/*      */     
/*  426 */     if (!getPermissionModel(sender).mayList(ownedBy)) {
/*  427 */       ownedBy = sender.getName();
/*  428 */       if (!getPermissionModel(sender).mayList(ownedBy)) {
/*  429 */         throw new CommandPermissionsException();
/*      */       }
/*      */     } 
/*      */     
/*  433 */     RegionManager manager = checkRegionManager(this.plugin, world);
/*      */     
/*  435 */     RegionLister task = new RegionLister(this.plugin, manager, sender);
/*  436 */     task.setPage(page);
/*  437 */     if (ownedBy != null) {
/*  438 */       task.filterOwnedByName(ownedBy, args.hasFlag('n'));
/*      */     }
/*      */     
/*  441 */     ListenableFuture<?> future = this.plugin.getExecutorService().submit((Callable)task);
/*      */     
/*  443 */     AsyncCommandHelper.wrap(future, this.plugin, sender)
/*  444 */       .registerWithSupervisor("Getting list of regions...")
/*  445 */       .sendMessageAfterDelay("(Please wait... fetching region list...)")
/*  446 */       .thenTellErrorsOnly("Failed to fetch region list");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Command(aliases = {"flag", "f"}, usage = "<id> <flag> [-w world] [-g group] [value]", flags = "g:w:e", desc = "Set flags", min = 2)
/*      */   public void flag(CommandContext args, CommandSender sender) throws CommandException {
/*  462 */     warnAboutSaveFailures(sender);
/*      */     
/*  464 */     World world = checkWorld(args, sender, 'w');
/*  465 */     String flagName = args.getString(1);
/*  466 */     String value = (args.argsLength() >= 3) ? args.getJoinedStrings(2) : null;
/*  467 */     RegionGroup groupValue = null;
/*  468 */     RegionPermissionModel permModel = getPermissionModel(sender);
/*      */     
/*  470 */     if (args.hasFlag('e')) {
/*  471 */       if (value != null) {
/*  472 */         throw new CommandException("You cannot use -e(mpty) with a flag value.");
/*      */       }
/*      */       
/*  475 */       value = "";
/*      */     } 
/*      */ 
/*      */     
/*  479 */     if (value != null) {
/*  480 */       value = CommandUtils.replaceColorMacros(value);
/*      */     }
/*      */ 
/*      */     
/*  484 */     RegionManager manager = checkRegionManager(this.plugin, world);
/*  485 */     ProtectedRegion existing = checkExistingRegion(manager, args.getString(0), true);
/*      */ 
/*      */     
/*  488 */     if (!permModel.maySetFlag(existing)) {
/*  489 */       throw new CommandPermissionsException();
/*      */     }
/*      */     
/*  492 */     Flag<?> foundFlag = DefaultFlag.fuzzyMatchFlag(flagName);
/*      */ 
/*      */ 
/*      */     
/*  496 */     if (foundFlag == null) {
/*  497 */       StringBuilder list = new StringBuilder();
/*      */ 
/*      */       
/*  500 */       for (Flag<?> flag : DefaultFlag.getFlags()) {
/*      */         
/*  502 */         if (permModel.maySetFlag(existing, flag)) {
/*      */ 
/*      */ 
/*      */           
/*  506 */           if (list.length() > 0) {
/*  507 */             list.append(", ");
/*      */           }
/*      */           
/*  510 */           list.append(flag.getName());
/*      */         } 
/*      */       } 
/*  513 */       sender.sendMessage(ChatColor.RED + "Unknown flag specified: " + flagName);
/*  514 */       sender.sendMessage(ChatColor.RED + "Available " + "flags: " + list);
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  522 */     if (!permModel.maySetFlag(existing, foundFlag, value)) {
/*  523 */       throw new CommandPermissionsException();
/*      */     }
/*      */ 
/*      */     
/*  527 */     if (args.hasFlag('g')) {
/*  528 */       String group = args.getFlag('g');
/*  529 */       RegionGroupFlag groupFlag = foundFlag.getRegionGroupFlag();
/*      */       
/*  531 */       if (groupFlag == null) {
/*  532 */         throw new CommandException("Region flag '" + foundFlag.getName() + "' does not have a group flag!");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  539 */         groupValue = (RegionGroup)groupFlag.parseInput(this.plugin, sender, group);
/*  540 */       } catch (InvalidFlagFormat e) {
/*  541 */         throw new CommandException(e.getMessage());
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  547 */     if (value != null) {
/*      */       
/*      */       try {
/*  550 */         setFlag(existing, foundFlag, sender, value);
/*  551 */       } catch (InvalidFlagFormat e) {
/*  552 */         throw new CommandException(e.getMessage());
/*      */       } 
/*      */       
/*  555 */       sender.sendMessage(ChatColor.YELLOW + "Region flag " + foundFlag
/*  556 */           .getName() + " set on '" + existing
/*  557 */           .getId() + "' to '" + ChatColor.stripColor(value) + "'.");
/*      */     
/*      */     }
/*  560 */     else if (!args.hasFlag('g')) {
/*      */       
/*  562 */       existing.setFlag(foundFlag, null);
/*      */ 
/*      */       
/*  565 */       RegionGroupFlag groupFlag = foundFlag.getRegionGroupFlag();
/*  566 */       if (groupFlag != null) {
/*  567 */         existing.setFlag((Flag)groupFlag, null);
/*      */       }
/*      */       
/*  570 */       sender.sendMessage(ChatColor.YELLOW + "Region flag " + foundFlag
/*  571 */           .getName() + " removed from '" + existing
/*  572 */           .getId() + "'. (Any -g(roups) were also removed.)");
/*      */     } 
/*      */ 
/*      */     
/*  576 */     if (groupValue != null) {
/*  577 */       RegionGroupFlag groupFlag = foundFlag.getRegionGroupFlag();
/*      */ 
/*      */       
/*  580 */       if (groupValue == groupFlag.getDefault()) {
/*  581 */         existing.setFlag((Flag)groupFlag, null);
/*  582 */         sender.sendMessage(ChatColor.YELLOW + "Region group flag for '" + foundFlag
/*  583 */             .getName() + "' reset to " + "default.");
/*      */       } else {
/*      */         
/*  586 */         existing.setFlag((Flag)groupFlag, groupValue);
/*  587 */         sender.sendMessage(ChatColor.YELLOW + "Region group flag for '" + foundFlag
/*  588 */             .getName() + "' set.");
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  593 */     RegionPrintoutBuilder printout = new RegionPrintoutBuilder(existing, null);
/*  594 */     printout.append(ChatColor.GRAY);
/*  595 */     printout.append("(Current flags: ");
/*  596 */     printout.appendFlagsList(false);
/*  597 */     printout.append(")");
/*  598 */     printout.send(sender);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Command(aliases = {"setpriority", "priority", "pri"}, usage = "<id> <priority>", flags = "w:", desc = "Set the priority of a region", min = 2, max = 2)
/*      */   public void setPriority(CommandContext args, CommandSender sender) throws CommandException {
/*  614 */     warnAboutSaveFailures(sender);
/*      */     
/*  616 */     World world = checkWorld(args, sender, 'w');
/*  617 */     int priority = args.getInteger(1);
/*      */ 
/*      */     
/*  620 */     RegionManager manager = checkRegionManager(this.plugin, world);
/*  621 */     ProtectedRegion existing = checkExistingRegion(manager, args.getString(0), false);
/*      */ 
/*      */     
/*  624 */     if (!getPermissionModel(sender).maySetPriority(existing)) {
/*  625 */       throw new CommandPermissionsException();
/*      */     }
/*      */     
/*  628 */     existing.setPriority(priority);
/*      */     
/*  630 */     sender.sendMessage(ChatColor.YELLOW + "Priority of '" + existing
/*  631 */         .getId() + "' set to " + priority + " (higher numbers override).");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Command(aliases = {"setparent", "parent", "par"}, usage = "<id> [parent-id]", flags = "w:", desc = "Set the parent of a region", min = 1, max = 2)
/*      */   public void setParent(CommandContext args, CommandSender sender) throws CommandException {
/*      */     ProtectedRegion parent;
/*  648 */     warnAboutSaveFailures(sender);
/*      */     
/*  650 */     World world = checkWorld(args, sender, 'w');
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  655 */     RegionManager manager = checkRegionManager(this.plugin, world);
/*      */ 
/*      */     
/*  658 */     ProtectedRegion child = checkExistingRegion(manager, args.getString(0), false);
/*  659 */     if (args.argsLength() == 2) {
/*  660 */       parent = checkExistingRegion(manager, args.getString(1), false);
/*      */     } else {
/*  662 */       parent = null;
/*      */     } 
/*      */ 
/*      */     
/*  666 */     if (!getPermissionModel(sender).maySetParent(child, parent)) {
/*  667 */       throw new CommandPermissionsException();
/*      */     }
/*      */     
/*      */     try {
/*  671 */       child.setParent(parent);
/*  672 */     } catch (com.sk89q.worldguard.protection.regions.ProtectedRegion.CircularInheritanceException e) {
/*      */       
/*  674 */       RegionPrintoutBuilder regionPrintoutBuilder = new RegionPrintoutBuilder(parent, null);
/*  675 */       regionPrintoutBuilder.append(ChatColor.RED);
/*  676 */       assert parent != null;
/*  677 */       regionPrintoutBuilder.append("Uh oh! Setting '" + parent.getId() + "' to be the parent " + "of '" + child
/*  678 */           .getId() + "' would cause circular inheritance.\n");
/*  679 */       regionPrintoutBuilder.append(ChatColor.GRAY);
/*  680 */       regionPrintoutBuilder.append("(Current inheritance on '" + parent.getId() + "':\n");
/*  681 */       regionPrintoutBuilder.appendParentTree(true);
/*  682 */       regionPrintoutBuilder.append(ChatColor.GRAY);
/*  683 */       regionPrintoutBuilder.append(")");
/*  684 */       regionPrintoutBuilder.send(sender);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  689 */     RegionPrintoutBuilder printout = new RegionPrintoutBuilder(child, null);
/*  690 */     printout.append(ChatColor.YELLOW);
/*  691 */     printout.append("Inheritance set for region '" + child.getId() + "'.\n");
/*  692 */     if (parent != null) {
/*  693 */       printout.append(ChatColor.GRAY);
/*  694 */       printout.append("(Current inheritance:\n");
/*  695 */       printout.appendParentTree(true);
/*  696 */       printout.append(ChatColor.GRAY);
/*  697 */       printout.append(")");
/*      */     } 
/*  699 */     printout.send(sender);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Command(aliases = {"remove", "delete", "del", "rem"}, usage = "<id>", flags = "fuw:", desc = "Remove a region", min = 1, max = 1)
/*      */   public void remove(CommandContext args, CommandSender sender) throws CommandException {
/*  715 */     warnAboutSaveFailures(sender);
/*      */     
/*  717 */     World world = checkWorld(args, sender, 'w');
/*  718 */     boolean removeChildren = args.hasFlag('f');
/*  719 */     boolean unsetParent = args.hasFlag('u');
/*      */ 
/*      */     
/*  722 */     RegionManager manager = checkRegionManager(this.plugin, world);
/*  723 */     ProtectedRegion existing = checkExistingRegion(manager, args.getString(0), true);
/*      */ 
/*      */     
/*  726 */     if (!getPermissionModel(sender).mayDelete(existing)) {
/*  727 */       throw new CommandPermissionsException();
/*      */     }
/*      */     
/*  730 */     RegionRemover task = new RegionRemover(manager, existing);
/*      */     
/*  732 */     if (removeChildren && unsetParent)
/*  733 */       throw new CommandException("You cannot use both -u (unset parent) and -f (remove children) together."); 
/*  734 */     if (removeChildren) {
/*  735 */       task.setRemovalStrategy(RemovalStrategy.REMOVE_CHILDREN);
/*  736 */     } else if (unsetParent) {
/*  737 */       task.setRemovalStrategy(RemovalStrategy.UNSET_PARENT_IN_CHILDREN);
/*      */     } 
/*      */     
/*  740 */     AsyncCommandHelper.wrap(this.plugin.getExecutorService().submit((Callable)task), this.plugin, sender)
/*  741 */       .formatUsing(new Object[] { existing.getId()
/*  742 */         }).registerWithSupervisor("Removing the region '%s'...")
/*  743 */       .sendMessageAfterDelay("(Please wait... removing '%s'...)")
/*  744 */       .thenRespondWith("The region named '%s' has been removed.", "Failed to remove the region '%s'");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Command(aliases = {"load", "reload"}, usage = "[world]", desc = "Reload regions from file", flags = "w:")
/*      */   public void load(CommandContext args, CommandSender sender) throws CommandException {
/*  761 */     warnAboutSaveFailures(sender);
/*      */     
/*  763 */     World world = null;
/*      */     try {
/*  765 */       world = checkWorld(args, sender, 'w');
/*  766 */     } catch (CommandException e) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  771 */     if (!getPermissionModel(sender).mayForceLoadRegions()) {
/*  772 */       throw new CommandPermissionsException();
/*      */     }
/*      */     
/*  775 */     if (world != null) {
/*  776 */       RegionManager manager = checkRegionManager(this.plugin, world);
/*      */       
/*  778 */       if (manager == null) {
/*  779 */         throw new CommandException("No region manager exists for world '" + world.getName() + "'.");
/*      */       }
/*      */       
/*  782 */       ListenableFuture<?> future = this.plugin.getExecutorService().submit((Callable)new RegionManagerReloader(new RegionManager[] { manager }));
/*      */       
/*  784 */       AsyncCommandHelper.wrap(future, this.plugin, sender)
/*  785 */         .forRegionDataLoad(world, false);
/*      */     } else {
/*      */       
/*  788 */       List<RegionManager> managers = new ArrayList<RegionManager>();
/*      */       
/*  790 */       for (World w : Bukkit.getServer().getWorlds()) {
/*  791 */         RegionManager manager = this.plugin.getRegionContainer().get(w);
/*  792 */         if (manager != null) {
/*  793 */           managers.add(manager);
/*      */         }
/*      */       } 
/*      */       
/*  797 */       ListenableFuture<?> future = this.plugin.getExecutorService().submit((Callable)new RegionManagerReloader(managers));
/*      */       
/*  799 */       AsyncCommandHelper.wrap(future, this.plugin, sender)
/*  800 */         .registerWithSupervisor("Loading regions for all worlds")
/*  801 */         .sendMessageAfterDelay("(Please wait... loading region data for all worlds...)")
/*  802 */         .thenRespondWith("Successfully load the region data for all worlds.", "Failed to load regions for all worlds");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Command(aliases = {"save", "write"}, usage = "[world]", desc = "Re-save regions to file", flags = "w:")
/*      */   public void save(CommandContext args, CommandSender sender) throws CommandException {
/*  820 */     warnAboutSaveFailures(sender);
/*      */     
/*  822 */     World world = null;
/*      */     try {
/*  824 */       world = checkWorld(args, sender, 'w');
/*  825 */     } catch (CommandException e) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  830 */     if (!getPermissionModel(sender).mayForceSaveRegions()) {
/*  831 */       throw new CommandPermissionsException();
/*      */     }
/*      */     
/*  834 */     if (world != null) {
/*  835 */       RegionManager manager = checkRegionManager(this.plugin, world);
/*      */       
/*  837 */       if (manager == null) {
/*  838 */         throw new CommandException("No region manager exists for world '" + world.getName() + "'.");
/*      */       }
/*      */       
/*  841 */       ListenableFuture<?> future = this.plugin.getExecutorService().submit((Callable)new RegionManagerSaver(new RegionManager[] { manager }));
/*      */       
/*  843 */       AsyncCommandHelper.wrap(future, this.plugin, sender)
/*  844 */         .forRegionDataSave(world, false);
/*      */     } else {
/*      */       
/*  847 */       List<RegionManager> managers = new ArrayList<RegionManager>();
/*      */       
/*  849 */       for (World w : Bukkit.getServer().getWorlds()) {
/*  850 */         RegionManager manager = this.plugin.getRegionContainer().get(w);
/*  851 */         if (manager != null) {
/*  852 */           managers.add(manager);
/*      */         }
/*      */       } 
/*      */       
/*  856 */       ListenableFuture<?> future = this.plugin.getExecutorService().submit((Callable)new RegionManagerSaver(managers));
/*      */       
/*  858 */       AsyncCommandHelper.wrap(future, this.plugin, sender)
/*  859 */         .registerWithSupervisor("Saving regions for all worlds")
/*  860 */         .sendMessageAfterDelay("(Please wait... saving region data for all worlds...)")
/*  861 */         .thenRespondWith("Successfully saved the region data for all worlds.", "Failed to save regions for all worlds");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Command(aliases = {"migratedb"}, usage = "<from> <to>", flags = "y", desc = "Migrate from one Protection Database to another.", min = 2, max = 2)
/*      */   public void migrateDB(CommandContext args, CommandSender sender) throws CommandException {
/*  879 */     if (!getPermissionModel(sender).mayMigrateRegionStore()) {
/*  880 */       throw new CommandPermissionsException();
/*      */     }
/*      */     
/*  883 */     DriverType from = (DriverType)Enums.findFuzzyByValue(DriverType.class, new String[] { args.getString(0) });
/*  884 */     DriverType to = (DriverType)Enums.findFuzzyByValue(DriverType.class, new String[] { args.getString(1) });
/*      */     
/*  886 */     if (from == null) {
/*  887 */       throw new CommandException("The value of 'from' is not a recognized type of region data database.");
/*      */     }
/*      */     
/*  890 */     if (to == null) {
/*  891 */       throw new CommandException("The value of 'to' is not a recognized type of region region data database.");
/*      */     }
/*      */     
/*  894 */     if (from.equals(to)) {
/*  895 */       throw new CommandException("It is not possible to migrate between the same types of region data databases.");
/*      */     }
/*      */     
/*  898 */     if (!args.hasFlag('y')) {
/*  899 */       throw new CommandException("This command is potentially dangerous.\nPlease ensure you have made a backup of your data, and then re-enter the command with -y tacked on at the end to proceed.");
/*      */     }
/*      */ 
/*      */     
/*  903 */     ConfigurationManager config = this.plugin.getGlobalStateManager();
/*  904 */     RegionDriver fromDriver = (RegionDriver)config.regionStoreDriverMap.get(from);
/*  905 */     RegionDriver toDriver = (RegionDriver)config.regionStoreDriverMap.get(to);
/*      */     
/*  907 */     if (fromDriver == null) {
/*  908 */       throw new CommandException("The driver specified as 'from' does not seem to be supported in your version of WorldGuard.");
/*      */     }
/*      */     
/*  911 */     if (toDriver == null) {
/*  912 */       throw new CommandException("The driver specified as 'to' does not seem to be supported in your version of WorldGuard.");
/*      */     }
/*      */     
/*  915 */     DriverMigration migration = new DriverMigration(fromDriver, toDriver);
/*      */     
/*  917 */     LoggerToChatHandler handler = null;
/*  918 */     Logger minecraftLogger = null;
/*      */     
/*  920 */     if (sender instanceof Player) {
/*  921 */       handler = new LoggerToChatHandler(sender);
/*  922 */       handler.setLevel(Level.ALL);
/*  923 */       minecraftLogger = Logger.getLogger("com.sk89q.worldguard");
/*  924 */       minecraftLogger.addHandler((Handler)handler);
/*      */     } 
/*      */     
/*      */     try {
/*  928 */       RegionContainer container = this.plugin.getRegionContainer();
/*  929 */       sender.sendMessage(ChatColor.YELLOW + "Now performing migration... this may take a while.");
/*  930 */       container.migrate((Migration)migration);
/*  931 */       sender.sendMessage(ChatColor.YELLOW + "Migration complete! This only migrated the data. If you already changed your settings to use " + "the target driver, then WorldGuard is now using the new data. If not, you have to adjust your " + "configuration to use the new driver and then restart your server.");
/*      */ 
/*      */     
/*      */     }
/*  935 */     catch (MigrationException e) {
/*  936 */       log.log(Level.WARNING, "Failed to migrate", (Throwable)e);
/*  937 */       throw new CommandException("Error encountered while migrating: " + e.getMessage());
/*      */     } finally {
/*  939 */       if (minecraftLogger != null) {
/*  940 */         minecraftLogger.removeHandler((Handler)handler);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Command(aliases = {"migrateuuid"}, desc = "Migrate loaded databases to use UUIDs", max = 0)
/*      */   public void migrateUuid(CommandContext args, CommandSender sender) throws CommandException {
/*  956 */     if (!getPermissionModel(sender).mayMigrateRegionNames()) {
/*  957 */       throw new CommandPermissionsException();
/*      */     }
/*      */     
/*  960 */     LoggerToChatHandler handler = null;
/*  961 */     Logger minecraftLogger = null;
/*      */     
/*  963 */     if (sender instanceof Player) {
/*  964 */       handler = new LoggerToChatHandler(sender);
/*  965 */       handler.setLevel(Level.ALL);
/*  966 */       minecraftLogger = Logger.getLogger("com.sk89q.worldguard");
/*  967 */       minecraftLogger.addHandler((Handler)handler);
/*      */     } 
/*      */     
/*      */     try {
/*  971 */       ConfigurationManager config = this.plugin.getGlobalStateManager();
/*  972 */       RegionContainer container = this.plugin.getRegionContainer();
/*  973 */       RegionDriver driver = container.getDriver();
/*  974 */       UUIDMigration migration = new UUIDMigration(driver, this.plugin.getProfileService());
/*  975 */       migration.setKeepUnresolvedNames(config.keepUnresolvedNames);
/*  976 */       sender.sendMessage(ChatColor.YELLOW + "Now performing migration... this may take a while.");
/*  977 */       container.migrate((Migration)migration);
/*  978 */       sender.sendMessage(ChatColor.YELLOW + "Migration complete!");
/*  979 */     } catch (MigrationException e) {
/*  980 */       log.log(Level.WARNING, "Failed to migrate", (Throwable)e);
/*  981 */       throw new CommandException("Error encountered while migrating: " + e.getMessage());
/*      */     } finally {
/*  983 */       if (minecraftLogger != null) {
/*  984 */         minecraftLogger.removeHandler((Handler)handler);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Command(aliases = {"teleport", "tp"}, usage = "<id>", flags = "s", desc = "Teleports you to the location associated with the region.", min = 1, max = 1)
/*      */   public void teleport(CommandContext args, CommandSender sender) throws CommandException {
/*      */     Location teleportLocation;
/* 1002 */     Player player = this.plugin.checkPlayer(sender);
/*      */ 
/*      */ 
/*      */     
/* 1006 */     RegionManager regionManager = checkRegionManager(this.plugin, player.getWorld());
/* 1007 */     ProtectedRegion existing = checkExistingRegion(regionManager, args.getString(0), false);
/*      */ 
/*      */     
/* 1010 */     if (!getPermissionModel(sender).mayTeleportTo(existing)) {
/* 1011 */       throw new CommandPermissionsException();
/*      */     }
/*      */ 
/*      */     
/* 1015 */     if (args.hasFlag('s')) {
/* 1016 */       teleportLocation = (Location)existing.getFlag((Flag)DefaultFlag.SPAWN_LOC);
/*      */       
/* 1018 */       if (teleportLocation == null) {
/* 1019 */         throw new CommandException("The region has no spawn point associated.");
/*      */       }
/*      */     } else {
/*      */       
/* 1023 */       teleportLocation = (Location)existing.getFlag((Flag)DefaultFlag.TELE_LOC);
/*      */       
/* 1025 */       if (teleportLocation == null) {
/* 1026 */         throw new CommandException("The region has no teleport point associated.");
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1031 */     player.teleport(BukkitUtil.toLocation(teleportLocation));
/* 1032 */     sender.sendMessage("Teleported you to the region '" + existing.getId() + "'.");
/*      */   }
/*      */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\commands\region\RegionCommands.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */