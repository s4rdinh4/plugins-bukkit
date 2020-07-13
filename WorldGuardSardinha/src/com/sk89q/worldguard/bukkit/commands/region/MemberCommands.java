/*     */ package com.sk89q.worldguard.bukkit.commands.region;
/*     */ 
/*     */ import com.google.common.util.concurrent.Futures;
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import com.sk89q.minecraft.util.commands.Command;
/*     */ import com.sk89q.minecraft.util.commands.CommandContext;
/*     */ import com.sk89q.minecraft.util.commands.CommandException;
/*     */ import com.sk89q.minecraft.util.commands.CommandPermissionsException;
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.bukkit.commands.AsyncCommandHelper;
/*     */ import com.sk89q.worldguard.domains.DefaultDomain;
/*     */ import com.sk89q.worldguard.protection.flags.DefaultFlag;
/*     */ import com.sk89q.worldguard.protection.flags.Flag;
/*     */ import com.sk89q.worldguard.protection.managers.RegionManager;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import com.sk89q.worldguard.protection.util.DomainInputResolver;
/*     */ import java.util.concurrent.Callable;
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
/*     */ public class MemberCommands
/*     */   extends RegionCommandsBase
/*     */ {
/*     */   private final WorldGuardPlugin plugin;
/*     */   
/*     */   public MemberCommands(WorldGuardPlugin plugin) {
/*  46 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Command(aliases = {"addmember", "addmember", "addmem", "am"}, usage = "<id> <members...>", flags = "nw:", desc = "Add a member to a region", min = 2)
/*     */   public void addMember(CommandContext args, CommandSender sender) throws CommandException {
/*  55 */     warnAboutSaveFailures(sender);
/*     */     
/*  57 */     World world = checkWorld(args, sender, 'w');
/*  58 */     String id = args.getString(0);
/*  59 */     RegionManager manager = checkRegionManager(this.plugin, world);
/*  60 */     ProtectedRegion region = checkExistingRegion(manager, id, true);
/*     */     
/*  62 */     id = region.getId();
/*     */ 
/*     */     
/*  65 */     if (!getPermissionModel(sender).mayAddMembers(region)) {
/*  66 */       throw new CommandPermissionsException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  71 */     DomainInputResolver resolver = new DomainInputResolver(this.plugin.getProfileService(), args.getParsedPaddedSlice(1, 0));
/*  72 */     resolver.setLocatorPolicy(args.hasFlag('n') ? DomainInputResolver.UserLocatorPolicy.NAME_ONLY : DomainInputResolver.UserLocatorPolicy.UUID_ONLY);
/*     */ 
/*     */     
/*  75 */     ListenableFuture<DefaultDomain> future = Futures.transform(this.plugin
/*  76 */         .getExecutorService().submit((Callable)resolver), resolver
/*  77 */         .createAddAllFunction(region.getMembers()));
/*     */     
/*  79 */     AsyncCommandHelper.wrap(future, this.plugin, sender)
/*  80 */       .formatUsing(new Object[] { region.getId(), world.getName()
/*  81 */         }).registerWithSupervisor("Adding members to the region '%s' on '%s'")
/*  82 */       .sendMessageAfterDelay("(Please wait... querying player names...)")
/*  83 */       .thenRespondWith("Region '%s' updated with new members.", "Failed to add new members");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Command(aliases = {"addowner", "addowner", "ao"}, usage = "<id> <owners...>", flags = "nw:", desc = "Add an owner to a region", min = 2)
/*     */   public void addOwner(CommandContext args, CommandSender sender) throws CommandException {
/*  92 */     warnAboutSaveFailures(sender);
/*     */     
/*  94 */     World world = checkWorld(args, sender, 'w');
/*     */     
/*  96 */     Player player = null;
/*  97 */     LocalPlayer localPlayer = null;
/*  98 */     if (sender instanceof Player) {
/*  99 */       player = (Player)sender;
/* 100 */       localPlayer = this.plugin.wrapPlayer(player);
/*     */     } 
/*     */     
/* 103 */     String id = args.getString(0);
/*     */     
/* 105 */     RegionManager manager = checkRegionManager(this.plugin, world);
/* 106 */     ProtectedRegion region = checkExistingRegion(manager, id, true);
/*     */     
/* 108 */     id = region.getId();
/*     */     
/* 110 */     Boolean flag = (Boolean)region.getFlag((Flag)DefaultFlag.BUYABLE);
/* 111 */     DefaultDomain owners = region.getOwners();
/*     */     
/* 113 */     if (localPlayer != null) {
/* 114 */       if (flag != null && flag.booleanValue() && owners != null && owners.size() == 0) {
/*     */         
/* 116 */         if (!this.plugin.hasPermission((CommandSender)player, "worldguard.region.unlimited")) {
/* 117 */           int maxRegionCount = this.plugin.getGlobalStateManager().get(world).getMaxRegionCount(player);
/* 118 */           if (maxRegionCount >= 0 && manager.getRegionCountOfPlayer(localPlayer) >= maxRegionCount)
/*     */           {
/* 120 */             throw new CommandException("You already own the maximum allowed amount of regions.");
/*     */           }
/*     */         } 
/* 123 */         this.plugin.checkPermission(sender, "worldguard.region.addowner.unclaimed." + id.toLowerCase());
/*     */       
/*     */       }
/* 126 */       else if (!getPermissionModel(sender).mayAddOwners(region)) {
/* 127 */         throw new CommandPermissionsException();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     DomainInputResolver resolver = new DomainInputResolver(this.plugin.getProfileService(), args.getParsedPaddedSlice(1, 0));
/* 135 */     resolver.setLocatorPolicy(args.hasFlag('n') ? DomainInputResolver.UserLocatorPolicy.NAME_ONLY : DomainInputResolver.UserLocatorPolicy.UUID_ONLY);
/*     */ 
/*     */     
/* 138 */     ListenableFuture<DefaultDomain> future = Futures.transform(this.plugin
/* 139 */         .getExecutorService().submit((Callable)resolver), resolver
/* 140 */         .createAddAllFunction(region.getOwners()));
/*     */     
/* 142 */     AsyncCommandHelper.wrap(future, this.plugin, sender)
/* 143 */       .formatUsing(new Object[] { region.getId(), world.getName()
/* 144 */         }).registerWithSupervisor("Adding owners to the region '%s' on '%s'")
/* 145 */       .sendMessageAfterDelay("(Please wait... querying player names...)")
/* 146 */       .thenRespondWith("Region '%s' updated with new owners.", "Failed to add new owners");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Command(aliases = {"removemember", "remmember", "removemem", "remmem", "rm"}, usage = "<id> <owners...>", flags = "naw:", desc = "Remove an owner to a region", min = 1)
/*     */   public void removeMember(CommandContext args, CommandSender sender) throws CommandException {
/*     */     ListenableFuture<?> future;
/* 155 */     warnAboutSaveFailures(sender);
/*     */     
/* 157 */     World world = checkWorld(args, sender, 'w');
/* 158 */     String id = args.getString(0);
/* 159 */     RegionManager manager = checkRegionManager(this.plugin, world);
/* 160 */     ProtectedRegion region = checkExistingRegion(manager, id, true);
/*     */ 
/*     */     
/* 163 */     if (!getPermissionModel(sender).mayRemoveMembers(region)) {
/* 164 */       throw new CommandPermissionsException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 169 */     if (args.hasFlag('a')) {
/* 170 */       region.getMembers().removeAll();
/*     */       
/* 172 */       future = Futures.immediateFuture(null);
/*     */     } else {
/* 174 */       if (args.argsLength() < 2) {
/* 175 */         throw new CommandException("List some names to remove, or use -a to remove all.");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 180 */       DomainInputResolver resolver = new DomainInputResolver(this.plugin.getProfileService(), args.getParsedPaddedSlice(1, 0));
/* 181 */       resolver.setLocatorPolicy(args.hasFlag('n') ? DomainInputResolver.UserLocatorPolicy.NAME_ONLY : DomainInputResolver.UserLocatorPolicy.UUID_AND_NAME);
/*     */ 
/*     */       
/* 184 */       future = Futures.transform(this.plugin
/* 185 */           .getExecutorService().submit((Callable)resolver), resolver
/* 186 */           .createRemoveAllFunction(region.getMembers()));
/*     */     } 
/*     */     
/* 189 */     AsyncCommandHelper.wrap(future, this.plugin, sender)
/* 190 */       .formatUsing(new Object[] { region.getId(), world.getName()
/* 191 */         }).registerWithSupervisor("Removing members from the region '%s' on '%s'")
/* 192 */       .sendMessageAfterDelay("(Please wait... querying player names...)")
/* 193 */       .thenRespondWith("Region '%s' updated with members removed.", "Failed to remove members");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Command(aliases = {"removeowner", "remowner", "ro"}, usage = "<id> <owners...>", flags = "naw:", desc = "Remove an owner to a region", min = 1)
/*     */   public void removeOwner(CommandContext args, CommandSender sender) throws CommandException {
/*     */     ListenableFuture<?> future;
/* 202 */     warnAboutSaveFailures(sender);
/*     */     
/* 204 */     World world = checkWorld(args, sender, 'w');
/* 205 */     String id = args.getString(0);
/* 206 */     RegionManager manager = checkRegionManager(this.plugin, world);
/* 207 */     ProtectedRegion region = checkExistingRegion(manager, id, true);
/*     */ 
/*     */     
/* 210 */     if (!getPermissionModel(sender).mayRemoveOwners(region)) {
/* 211 */       throw new CommandPermissionsException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 216 */     if (args.hasFlag('a')) {
/* 217 */       region.getOwners().removeAll();
/*     */       
/* 219 */       future = Futures.immediateFuture(null);
/*     */     } else {
/* 221 */       if (args.argsLength() < 2) {
/* 222 */         throw new CommandException("List some names to remove, or use -a to remove all.");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 227 */       DomainInputResolver resolver = new DomainInputResolver(this.plugin.getProfileService(), args.getParsedPaddedSlice(1, 0));
/* 228 */       resolver.setLocatorPolicy(args.hasFlag('n') ? DomainInputResolver.UserLocatorPolicy.NAME_ONLY : DomainInputResolver.UserLocatorPolicy.UUID_AND_NAME);
/*     */ 
/*     */       
/* 231 */       future = Futures.transform(this.plugin
/* 232 */           .getExecutorService().submit((Callable)resolver), resolver
/* 233 */           .createRemoveAllFunction(region.getOwners()));
/*     */     } 
/*     */     
/* 236 */     AsyncCommandHelper.wrap(future, this.plugin, sender)
/* 237 */       .formatUsing(new Object[] { region.getId(), world.getName()
/* 238 */         }).registerWithSupervisor("Removing owners from the region '%s' on '%s'")
/* 239 */       .sendMessageAfterDelay("(Please wait... querying player names...)")
/* 240 */       .thenRespondWith("Region '%s' updated with owners removed.", "Failed to remove owners");
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\commands\region\MemberCommands.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */