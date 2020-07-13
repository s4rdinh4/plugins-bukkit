/*     */ package com.sk89q.worldguard.bukkit.commands;
/*     */ 
/*     */ import com.sk89q.minecraft.util.commands.Command;
/*     */ import com.sk89q.minecraft.util.commands.CommandContext;
/*     */ import com.sk89q.minecraft.util.commands.CommandException;
/*     */ import com.sk89q.minecraft.util.commands.CommandPermissions;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.bukkit.event.debug.CancelLogging;
/*     */ import com.sk89q.worldguard.bukkit.event.debug.LoggingBlockBreakEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.debug.LoggingBlockPlaceEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.debug.LoggingEntityDamageByEntityEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.debug.LoggingPlayerInteractEvent;
/*     */ import com.sk89q.worldguard.util.report.CancelReport;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.util.BlockIterator;
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
/*     */ public class DebuggingCommands
/*     */ {
/*  47 */   private static final Logger log = Logger.getLogger(DebuggingCommands.class.getCanonicalName());
/*     */ 
/*     */   
/*     */   private static final int MAX_TRACE_DISTANCE = 20;
/*     */ 
/*     */   
/*     */   private final WorldGuardPlugin plugin;
/*     */ 
/*     */ 
/*     */   
/*     */   public DebuggingCommands(WorldGuardPlugin plugin) {
/*  58 */     this.plugin = plugin;
/*     */   }
/*     */   
/*     */   @Command(aliases = {"testbreak"}, usage = "[player]", desc = "Simulate a block break", min = 1, max = 1, flags = "t")
/*     */   @CommandPermissions({"worldguard.debug.event"})
/*     */   public void fireBreakEvent(CommandContext args, CommandSender sender) throws CommandException {
/*  64 */     Player target = this.plugin.matchSinglePlayer(sender, args.getString(0));
/*  65 */     Block block = traceBlock(sender, target, args.hasFlag('t'));
/*  66 */     sender.sendMessage(ChatColor.AQUA + "Testing BLOCK BREAK at " + ChatColor.DARK_AQUA + block);
/*  67 */     LoggingBlockBreakEvent event = new LoggingBlockBreakEvent(block, target);
/*  68 */     testEvent(sender, target, event);
/*     */   }
/*     */ 
/*     */   
/*     */   @Command(aliases = {"testplace"}, usage = "[player]", desc = "Simulate a block place", min = 1, max = 1, flags = "t")
/*     */   @CommandPermissions({"worldguard.debug.event"})
/*     */   public void firePlaceEvent(CommandContext args, CommandSender sender) throws CommandException {
/*  75 */     Player target = this.plugin.matchSinglePlayer(sender, args.getString(0));
/*  76 */     Block block = traceBlock(sender, target, args.hasFlag('t'));
/*  77 */     sender.sendMessage(ChatColor.AQUA + "Testing BLOCK PLACE at " + ChatColor.DARK_AQUA + block);
/*  78 */     LoggingBlockPlaceEvent event = new LoggingBlockPlaceEvent(block, block.getState(), block.getRelative(BlockFace.DOWN), target.getItemInHand(), target, true);
/*  79 */     testEvent(sender, target, event);
/*     */   }
/*     */   
/*     */   @Command(aliases = {"testinteract"}, usage = "[player]", desc = "Simulate a block interact", min = 1, max = 1, flags = "t")
/*     */   @CommandPermissions({"worldguard.debug.event"})
/*     */   public void fireInteractEvent(CommandContext args, CommandSender sender) throws CommandException {
/*  85 */     Player target = this.plugin.matchSinglePlayer(sender, args.getString(0));
/*  86 */     Block block = traceBlock(sender, target, args.hasFlag('t'));
/*  87 */     sender.sendMessage(ChatColor.AQUA + "Testing BLOCK INTERACT at " + ChatColor.DARK_AQUA + block);
/*  88 */     LoggingPlayerInteractEvent event = new LoggingPlayerInteractEvent(target, Action.RIGHT_CLICK_BLOCK, target.getItemInHand(), block, BlockFace.SOUTH);
/*  89 */     testEvent(sender, target, event);
/*     */   }
/*     */   
/*     */   @Command(aliases = {"testdamage"}, usage = "[player]", desc = "Simulate an entity damage", min = 1, max = 1, flags = "t")
/*     */   @CommandPermissions({"worldguard.debug.event"})
/*     */   public void fireDamageEvent(CommandContext args, CommandSender sender) throws CommandException {
/*  95 */     Player target = this.plugin.matchSinglePlayer(sender, args.getString(0));
/*  96 */     Entity entity = traceEntity(sender, target, args.hasFlag('t'));
/*  97 */     sender.sendMessage(ChatColor.AQUA + "Testing ENTITY DAMAGE on " + ChatColor.DARK_AQUA + entity);
/*  98 */     LoggingEntityDamageByEntityEvent event = new LoggingEntityDamageByEntityEvent((Entity)target, entity, EntityDamageEvent.DamageCause.ENTITY_ATTACK, 1.0D);
/*  99 */     testEvent(sender, target, event);
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
/*     */   private <T extends Event & CancelLogging> void testEvent(CommandSender receiver, Player target, T event) {
/* 111 */     boolean isConsole = receiver instanceof org.bukkit.command.ConsoleCommandSender;
/*     */     
/* 113 */     if (!receiver.equals(target)) {
/* 114 */       if (!isConsole) {
/* 115 */         log.info(receiver.getName() + " is simulating an event on " + target.getName());
/*     */       }
/*     */       
/* 118 */       target.sendMessage(ChatColor.RED + "(Please ignore any messages that may immediately follow.)");
/*     */     } 
/*     */ 
/*     */     
/* 122 */     Bukkit.getPluginManager().callEvent((Event)event);
/* 123 */     int start = ((new Exception()).getStackTrace()).length;
/* 124 */     CancelReport report = new CancelReport((Event)event, ((CancelLogging)event).getCancels(), start);
/* 125 */     String result = report.toString();
/* 126 */     receiver.sendMessage(result.replaceAll("(?m)^", ChatColor.AQUA.toString()));
/*     */     
/* 128 */     if (result.length() >= 500 && !isConsole) {
/* 129 */       receiver.sendMessage(ChatColor.GRAY + "The report was also printed to console.");
/* 130 */       log.info("Event report for " + receiver.getName() + ":\n\n" + result);
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
/*     */   private Player getSource(CommandSender sender, Player target, boolean fromTarget) throws CommandException {
/* 144 */     if (fromTarget) {
/* 145 */       return target;
/*     */     }
/* 147 */     if (sender instanceof Player) {
/* 148 */       return (Player)sender;
/*     */     }
/* 150 */     throw new CommandException("If this command is not to be used in-game, use -t to run the test from the viewpoint of the given player rather than yourself.");
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
/*     */   private Block traceBlock(CommandSender sender, Player target, boolean fromTarget) throws CommandException {
/* 166 */     Player source = getSource(sender, target, fromTarget);
/*     */     
/* 168 */     BlockIterator it = new BlockIterator((LivingEntity)source);
/* 169 */     int i = 0;
/* 170 */     while (it.hasNext() && i < 20) {
/* 171 */       Block block = it.next();
/* 172 */       if (block.getType() != Material.AIR) {
/* 173 */         return block;
/*     */       }
/* 175 */       i++;
/*     */     } 
/*     */     
/* 178 */     throw new CommandException("Not currently looking at a block that is close enough.");
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
/*     */   private Entity traceEntity(CommandSender sender, Player target, boolean fromTarget) throws CommandException {
/* 191 */     Player source = getSource(sender, target, fromTarget);
/*     */     
/* 193 */     BlockIterator it = new BlockIterator((LivingEntity)source);
/* 194 */     int i = 0;
/* 195 */     while (it.hasNext() && i < 20) {
/* 196 */       Block block = it.next();
/*     */ 
/*     */       
/* 199 */       Entity[] entities = block.getChunk().getEntities();
/* 200 */       for (Entity entity : entities) {
/* 201 */         if (!entity.equals(target) && entity.getLocation().distanceSquared(block.getLocation()) < 10.0D) {
/* 202 */           return entity;
/*     */         }
/*     */       } 
/*     */       
/* 206 */       i++;
/*     */     } 
/*     */     
/* 209 */     throw new CommandException("Not currently looking at an entity that is close enough.");
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\commands\DebuggingCommands.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */