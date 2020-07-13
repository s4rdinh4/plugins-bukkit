/*     */ package com.sk89q.worldguard.bukkit.commands.region;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.sk89q.minecraft.util.commands.CommandContext;
/*     */ import com.sk89q.minecraft.util.commands.CommandException;
/*     */ import com.sk89q.worldedit.BlockVector;
/*     */ import com.sk89q.worldedit.Vector;
/*     */ import com.sk89q.worldedit.bukkit.WorldEditPlugin;
/*     */ import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
/*     */ import com.sk89q.worldedit.bukkit.selections.Polygonal2DSelection;
/*     */ import com.sk89q.worldedit.bukkit.selections.Selection;
/*     */ import com.sk89q.worldguard.bukkit.RegionContainer;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.bukkit.permission.RegionPermissionModel;
/*     */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*     */ import com.sk89q.worldguard.protection.flags.Flag;
/*     */ import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
/*     */ import com.sk89q.worldguard.protection.managers.RegionManager;
/*     */ import com.sk89q.worldguard.protection.regions.GlobalProtectedRegion;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedPolygonalRegion;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import java.util.Set;
/*     */ import org.bukkit.ChatColor;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class RegionCommandsBase
/*     */ {
/*     */   protected static RegionPermissionModel getPermissionModel(CommandSender sender) {
/*  63 */     return new RegionPermissionModel(WorldGuardPlugin.inst(), sender);
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
/*     */   protected static World checkWorld(CommandContext args, CommandSender sender, char flag) throws CommandException {
/*  77 */     if (args.hasFlag(flag)) {
/*  78 */       return WorldGuardPlugin.inst().matchWorld(sender, args.getFlag(flag));
/*     */     }
/*  80 */     if (sender instanceof Player) {
/*  81 */       return WorldGuardPlugin.inst().checkPlayer(sender).getWorld();
/*     */     }
/*  83 */     throw new CommandException("Please specify the world with -" + flag + " world_name.");
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
/*     */   protected static String checkRegionId(String id, boolean allowGlobal) throws CommandException {
/*  97 */     if (!ProtectedRegion.isValidId(id)) {
/*  98 */       throw new CommandException("The region name of '" + id + "' contains characters that are not allowed.");
/*     */     }
/*     */ 
/*     */     
/* 102 */     if (!allowGlobal && id.equalsIgnoreCase("__global__")) {
/* 103 */       throw new CommandException("Sorry, you can't use __global__ here.");
/*     */     }
/*     */ 
/*     */     
/* 107 */     return id;
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
/*     */   protected static ProtectedRegion checkExistingRegion(RegionManager regionManager, String id, boolean allowGlobal) throws CommandException {
/*     */     GlobalProtectedRegion globalProtectedRegion;
/* 123 */     checkRegionId(id, allowGlobal);
/*     */     
/* 125 */     ProtectedRegion region = regionManager.getRegion(id);
/*     */ 
/*     */     
/* 128 */     if (region == null) {
/*     */       
/* 130 */       if (id.equalsIgnoreCase("__global__")) {
/* 131 */         globalProtectedRegion = new GlobalProtectedRegion(id);
/* 132 */         regionManager.addRegion((ProtectedRegion)globalProtectedRegion);
/* 133 */         return (ProtectedRegion)globalProtectedRegion;
/*     */       } 
/*     */       
/* 136 */       throw new CommandException("No region could be found with the name of '" + id + "'.");
/*     */     } 
/*     */ 
/*     */     
/* 140 */     return (ProtectedRegion)globalProtectedRegion;
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
/*     */   protected static ProtectedRegion checkRegionStandingIn(RegionManager regionManager, Player player) throws CommandException {
/* 156 */     return checkRegionStandingIn(regionManager, player, false);
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
/*     */   protected static ProtectedRegion checkRegionStandingIn(RegionManager regionManager, Player player, boolean allowGlobal) throws CommandException {
/* 175 */     ApplicableRegionSet set = regionManager.getApplicableRegions(player.getLocation());
/*     */     
/* 177 */     if (set.size() == 0) {
/* 178 */       if (allowGlobal) {
/* 179 */         ProtectedRegion global = checkExistingRegion(regionManager, "__global__", true);
/* 180 */         player.sendMessage(ChatColor.GRAY + "You're not standing in any " + "regions. Using the global region for this world instead.");
/*     */         
/* 182 */         return global;
/*     */       } 
/* 184 */       throw new CommandException("You're not standing in a region.Specify an ID if you want to select a specific region.");
/*     */     } 
/*     */     
/* 187 */     if (set.size() > 1) {
/* 188 */       StringBuilder builder = new StringBuilder();
/* 189 */       boolean first = true;
/*     */       
/* 191 */       for (ProtectedRegion region : set) {
/* 192 */         if (!first) {
/* 193 */           builder.append(", ");
/*     */         }
/* 195 */         first = false;
/* 196 */         builder.append(region.getId());
/*     */       } 
/*     */       
/* 199 */       throw new CommandException("You're standing in several regions (please pick one).\nYou're in: " + builder
/* 200 */           .toString());
/*     */     } 
/*     */     
/* 203 */     return set.iterator().next();
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
/*     */   protected static Selection checkSelection(Player player) throws CommandException {
/* 215 */     WorldEditPlugin worldEdit = WorldGuardPlugin.inst().getWorldEdit();
/* 216 */     Selection selection = worldEdit.getSelection(player);
/*     */     
/* 218 */     if (selection == null) {
/* 219 */       throw new CommandException("Please select an area first. Use WorldEdit to make a selection! (wiki: http://wiki.sk89q.com/wiki/WorldEdit).");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 225 */     return selection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void checkRegionDoesNotExist(RegionManager manager, String id, boolean mayRedefine) throws CommandException {
/* 236 */     if (manager.hasRegion(id)) {
/* 237 */       throw new CommandException("A region with that name already exists. Please choose another name." + (mayRedefine ? (" To change the shape, use /region redefine " + id + ".") : ""));
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
/*     */   
/*     */   protected static RegionManager checkRegionManager(WorldGuardPlugin plugin, World world) throws CommandException {
/* 250 */     if (!(plugin.getGlobalStateManager().get(world)).useRegions) {
/* 251 */       throw new CommandException("Region support is disabled in the target world. It can be enabled per-world in WorldGuard's configuration files. However, you may need to restart your server afterwards.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 256 */     RegionManager manager = plugin.getRegionContainer().get(world);
/* 257 */     if (manager == null) {
/* 258 */       throw new CommandException("Region data failed to load for this world. Please ask a server administrator to read the logs to identify the reason.");
/*     */     }
/*     */     
/* 261 */     return manager;
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
/*     */   protected static ProtectedRegion checkRegionFromSelection(Player player, String id) throws CommandException {
/* 273 */     Selection selection = checkSelection(player);
/*     */ 
/*     */     
/* 276 */     if (selection instanceof Polygonal2DSelection) {
/* 277 */       Polygonal2DSelection polySel = (Polygonal2DSelection)selection;
/* 278 */       int minY = polySel.getNativeMinimumPoint().getBlockY();
/* 279 */       int maxY = polySel.getNativeMaximumPoint().getBlockY();
/* 280 */       return (ProtectedRegion)new ProtectedPolygonalRegion(id, polySel.getNativePoints(), minY, maxY);
/* 281 */     }  if (selection instanceof CuboidSelection) {
/* 282 */       BlockVector min = selection.getNativeMinimumPoint().toBlockVector();
/* 283 */       BlockVector max = selection.getNativeMaximumPoint().toBlockVector();
/* 284 */       return (ProtectedRegion)new ProtectedCuboidRegion(id, min, max);
/*     */     } 
/* 286 */     throw new CommandException("Sorry, you can only use cuboids and polygons for WorldGuard regions.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void warnAboutSaveFailures(CommandSender sender) {
/* 296 */     RegionContainer container = WorldGuardPlugin.inst().getRegionContainer();
/* 297 */     Set<RegionManager> failures = container.getSaveFailures();
/*     */     
/* 299 */     if (failures.size() > 0) {
/* 300 */       String failingList = Joiner.on(", ").join(Iterables.transform(failures, new Function<RegionManager, String>()
/*     */             {
/*     */               public String apply(RegionManager regionManager) {
/* 303 */                 return "'" + regionManager.getName() + "'";
/*     */               }
/*     */             }));
/*     */       
/* 307 */       sender.sendMessage(ChatColor.GOLD + "(Warning: The background saving of region data is failing for these worlds: " + failingList + ". " + "Your changes are getting lost. See the server log for more information.)");
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
/*     */   
/*     */   protected static void warnAboutDimensions(CommandSender sender, ProtectedRegion region) {
/* 320 */     int height = region.getMaximumPoint().getBlockY() - region.getMinimumPoint().getBlockY();
/* 321 */     if (height <= 2) {
/* 322 */       sender.sendMessage(ChatColor.GRAY + "(Warning: The height of the region was " + (height + 1) + " block(s).)");
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
/*     */   protected static void informNewUser(CommandSender sender, RegionManager manager, ProtectedRegion region) {
/* 334 */     if (manager.getRegions().size() <= 2) {
/* 335 */       sender.sendMessage(ChatColor.GRAY + "(This region is NOW PROTECTED from modification from others. " + "Don't want that? Use " + ChatColor.AQUA + "/rg flag " + region
/*     */ 
/*     */           
/* 338 */           .getId() + " passthrough allow" + ChatColor.GRAY + ")");
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
/*     */   
/*     */   protected static void setPlayerSelection(Player player, ProtectedRegion region) throws CommandException {
/* 351 */     WorldEditPlugin worldEdit = WorldGuardPlugin.inst().getWorldEdit();
/*     */     
/* 353 */     World world = player.getWorld();
/*     */ 
/*     */     
/* 356 */     if (region instanceof ProtectedCuboidRegion) {
/* 357 */       ProtectedCuboidRegion cuboid = (ProtectedCuboidRegion)region;
/* 358 */       BlockVector blockVector1 = cuboid.getMinimumPoint();
/* 359 */       BlockVector blockVector2 = cuboid.getMaximumPoint();
/* 360 */       CuboidSelection selection = new CuboidSelection(world, (Vector)blockVector1, (Vector)blockVector2);
/* 361 */       worldEdit.setSelection(player, (Selection)selection);
/* 362 */       player.sendMessage(ChatColor.YELLOW + "Region selected as a cuboid.");
/*     */     }
/* 364 */     else if (region instanceof ProtectedPolygonalRegion) {
/* 365 */       ProtectedPolygonalRegion poly2d = (ProtectedPolygonalRegion)region;
/*     */ 
/*     */ 
/*     */       
/* 369 */       Polygonal2DSelection selection = new Polygonal2DSelection(world, poly2d.getPoints(), poly2d.getMinimumPoint().getBlockY(), poly2d.getMaximumPoint().getBlockY());
/* 370 */       worldEdit.setSelection(player, (Selection)selection);
/* 371 */       player.sendMessage(ChatColor.YELLOW + "Region selected as a polygon.");
/*     */     } else {
/* 373 */       if (region instanceof GlobalProtectedRegion) {
/* 374 */         throw new CommandException("Can't select global regions! That would cover the entire world.");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 379 */       throw new CommandException("Unknown region type: " + region
/* 380 */           .getClass().getCanonicalName());
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
/*     */ 
/*     */   
/*     */   protected static <V> void setFlag(ProtectedRegion region, Flag<V> flag, CommandSender sender, String value) throws InvalidFlagFormat {
/* 394 */     region.setFlag(flag, flag.parseInput(WorldGuardPlugin.inst(), sender, value));
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\commands\region\RegionCommandsBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */