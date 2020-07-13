/*     */ package com.sk89q.worldguard.bukkit.commands;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import com.google.common.io.Files;
/*     */ import com.google.common.util.concurrent.FutureCallback;
/*     */ import com.google.common.util.concurrent.Futures;
/*     */ import com.google.common.util.concurrent.MoreExecutors;
/*     */ import com.sk89q.minecraft.util.commands.Command;
/*     */ import com.sk89q.minecraft.util.commands.CommandContext;
/*     */ import com.sk89q.minecraft.util.commands.CommandException;
/*     */ import com.sk89q.minecraft.util.commands.CommandPermissions;
/*     */ import com.sk89q.minecraft.util.commands.NestedCommand;
/*     */ import com.sk89q.worldguard.bukkit.ConfigurationManager;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.bukkit.util.logging.LoggerToChatHandler;
/*     */ import com.sk89q.worldguard.bukkit.util.report.ConfigReport;
/*     */ import com.sk89q.worldguard.bukkit.util.report.PerformanceReport;
/*     */ import com.sk89q.worldguard.bukkit.util.report.PluginReport;
/*     */ import com.sk89q.worldguard.bukkit.util.report.SchedulerReport;
/*     */ import com.sk89q.worldguard.bukkit.util.report.ServerReport;
/*     */ import com.sk89q.worldguard.bukkit.util.report.ServicesReport;
/*     */ import com.sk89q.worldguard.bukkit.util.report.WorldReport;
/*     */ import com.sk89q.worldguard.util.profiler.SamplerBuilder;
/*     */ import com.sk89q.worldguard.util.profiler.ThreadIdFilter;
/*     */ import com.sk89q.worldguard.util.profiler.ThreadNameFilter;
/*     */ import com.sk89q.worldguard.util.report.Report;
/*     */ import com.sk89q.worldguard.util.report.ReportList;
/*     */ import com.sk89q.worldguard.util.report.SystemInfoReport;
/*     */ import com.sk89q.worldguard.util.task.Task;
/*     */ import com.sk89q.worldguard.util.task.TaskStateComparator;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.lang.management.ThreadInfo;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.logging.Handler;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bukkit.Bukkit;
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
/*     */ public class WorldGuardCommands
/*     */ {
/*  60 */   private static final Logger log = Logger.getLogger(WorldGuardCommands.class.getCanonicalName());
/*     */   
/*     */   private final WorldGuardPlugin plugin;
/*     */   @Nullable
/*     */   private SamplerBuilder.Sampler activeSampler;
/*     */   
/*     */   public WorldGuardCommands(WorldGuardPlugin plugin) {
/*  67 */     this.plugin = plugin;
/*     */   }
/*     */   
/*     */   @Command(aliases = {"version"}, desc = "Get the WorldGuard version", max = 0)
/*     */   public void version(CommandContext args, CommandSender sender) throws CommandException {
/*  72 */     sender.sendMessage(ChatColor.YELLOW + "WorldGuard " + this.plugin
/*  73 */         .getDescription().getVersion());
/*  74 */     sender.sendMessage(ChatColor.YELLOW + "http://www.sk89q.com");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Command(aliases = {"reload"}, desc = "Reload WorldGuard configuration", max = 0)
/*     */   @CommandPermissions({"worldguard.reload"})
/*     */   public void reload(CommandContext args, CommandSender sender) throws CommandException {
/*  82 */     List<Task<?>> tasks = this.plugin.getSupervisor().getTasks();
/*  83 */     if (!tasks.isEmpty()) {
/*  84 */       throw new CommandException("There are currently pending tasks. Use /wg running to monitor these tasks first.");
/*     */     }
/*     */     
/*  87 */     LoggerToChatHandler handler = null;
/*  88 */     Logger minecraftLogger = null;
/*     */     
/*  90 */     if (sender instanceof Player) {
/*  91 */       handler = new LoggerToChatHandler(sender);
/*  92 */       handler.setLevel(Level.ALL);
/*  93 */       minecraftLogger = Logger.getLogger("com.sk89q.worldguard");
/*  94 */       minecraftLogger.addHandler((Handler)handler);
/*     */     } 
/*     */     
/*     */     try {
/*  98 */       ConfigurationManager config = this.plugin.getGlobalStateManager();
/*  99 */       config.unload();
/* 100 */       config.load();
/* 101 */       for (World world : Bukkit.getServer().getWorlds()) {
/* 102 */         config.get(world);
/*     */       }
/* 104 */       this.plugin.getRegionContainer().reload();
/*     */       
/* 106 */       sender.sendMessage("WorldGuard configuration reloaded.");
/* 107 */     } catch (Throwable t) {
/* 108 */       sender.sendMessage("Error while reloading: " + t
/* 109 */           .getMessage());
/*     */     } finally {
/* 111 */       if (minecraftLogger != null) {
/* 112 */         minecraftLogger.removeHandler((Handler)handler);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @Command(aliases = {"report"}, desc = "Writes a report on WorldGuard", flags = "p", max = 0)
/*     */   @CommandPermissions({"worldguard.report"})
/*     */   public void report(CommandContext args, CommandSender sender) throws CommandException {
/* 120 */     ReportList report = new ReportList("Report");
/* 121 */     report.add((Report)new SystemInfoReport());
/* 122 */     report.add((Report)new ServerReport());
/* 123 */     report.add((Report)new PluginReport());
/* 124 */     report.add((Report)new SchedulerReport());
/* 125 */     report.add((Report)new ServicesReport());
/* 126 */     report.add((Report)new WorldReport());
/* 127 */     report.add((Report)new PerformanceReport());
/* 128 */     report.add((Report)new ConfigReport(this.plugin));
/* 129 */     String result = report.toString();
/*     */     
/*     */     try {
/* 132 */       File dest = new File(this.plugin.getDataFolder(), "report.txt");
/* 133 */       Files.write(result, dest, Charset.forName("UTF-8"));
/* 134 */       sender.sendMessage(ChatColor.YELLOW + "WorldGuard report written to " + dest.getAbsolutePath());
/* 135 */     } catch (IOException e) {
/* 136 */       throw new CommandException("Failed to write report: " + e.getMessage());
/*     */     } 
/*     */     
/* 139 */     if (args.hasFlag('p')) {
/* 140 */       this.plugin.checkPermission(sender, "worldguard.report.pastebin");
/* 141 */       CommandUtils.pastebin(this.plugin, sender, result, "WorldGuard report: %s.report");
/*     */     } 
/*     */   }
/*     */   @Command(aliases = {"profile"}, usage = "[<minutes>]", desc = "Profile the CPU usage of the server", min = 0, max = 1, flags = "t:p")
/*     */   @CommandPermissions({"worldguard.profile"})
/*     */   public void profile(CommandContext args, final CommandSender sender) throws CommandException {
/*     */     final boolean pastebin;
/*     */     ThreadNameFilter threadNameFilter;
/*     */     int minutes;
/*     */     SamplerBuilder.Sampler sampler;
/* 151 */     String threadName = args.getFlag('t');
/*     */ 
/*     */     
/* 154 */     if (args.hasFlag('p')) {
/* 155 */       this.plugin.checkPermission(sender, "worldguard.report.pastebin");
/* 156 */       pastebin = true;
/*     */     } else {
/* 158 */       pastebin = false;
/*     */     } 
/*     */     
/* 161 */     if (threadName == null) {
/* 162 */       ThreadIdFilter threadIdFilter = new ThreadIdFilter(Thread.currentThread().getId());
/* 163 */     } else if (threadName.equals("*")) {
/* 164 */       Predicate<ThreadInfo> threadFilter = Predicates.alwaysTrue();
/*     */     } else {
/* 166 */       threadNameFilter = new ThreadNameFilter(threadName);
/*     */     } 
/*     */ 
/*     */     
/* 170 */     if (args.argsLength() == 0) {
/* 171 */       minutes = 5;
/*     */     } else {
/* 173 */       minutes = args.getInteger(0);
/* 174 */       if (minutes < 1)
/* 175 */         throw new CommandException("You must run the profile for at least 1 minute."); 
/* 176 */       if (minutes > 10) {
/* 177 */         throw new CommandException("You can profile for, at maximum, 10 minutes.");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 183 */     synchronized (this) {
/* 184 */       if (this.activeSampler != null) {
/* 185 */         throw new CommandException("A profile is currently in progress! Please use /wg stopprofile to stop the current profile.");
/*     */       }
/*     */       
/* 188 */       SamplerBuilder builder = new SamplerBuilder();
/* 189 */       builder.setThreadFilter((Predicate)threadNameFilter);
/* 190 */       builder.setRunTime(minutes, TimeUnit.MINUTES);
/* 191 */       sampler = this.activeSampler = builder.start();
/*     */     } 
/*     */     
/* 194 */     AsyncCommandHelper.wrap(sampler.getFuture(), this.plugin, sender)
/* 195 */       .formatUsing(new Object[] { Integer.valueOf(minutes)
/* 196 */         }).registerWithSupervisor("Running CPU profiler for %d minute(s)...")
/* 197 */       .sendMessageAfterDelay("(Please wait... profiling for %d minute(s)...)")
/* 198 */       .thenTellErrorsOnly("CPU profiling failed.");
/*     */     
/* 200 */     sampler.getFuture().addListener(new Runnable()
/*     */         {
/*     */           public void run() {
/* 203 */             synchronized (WorldGuardCommands.this) {
/* 204 */               WorldGuardCommands.this.activeSampler = null;
/*     */             } 
/*     */           }
/* 207 */         },  (Executor)MoreExecutors.sameThreadExecutor());
/*     */     
/* 209 */     Futures.addCallback(sampler.getFuture(), new FutureCallback<SamplerBuilder.Sampler>()
/*     */         {
/*     */           public void onSuccess(SamplerBuilder.Sampler result) {
/* 212 */             String output = result.toString();
/*     */             
/*     */             try {
/* 215 */               File dest = new File(WorldGuardCommands.this.plugin.getDataFolder(), "profile.txt");
/* 216 */               Files.write(output, dest, Charset.forName("UTF-8"));
/* 217 */               sender.sendMessage(ChatColor.YELLOW + "CPU profiling data written to " + dest.getAbsolutePath());
/* 218 */             } catch (IOException e) {
/* 219 */               sender.sendMessage(ChatColor.RED + "Failed to write CPU profiling data: " + e.getMessage());
/*     */             } 
/*     */             
/* 222 */             if (pastebin) {
/* 223 */               CommandUtils.pastebin(WorldGuardCommands.this.plugin, sender, output, "Profile result: %s.profile");
/*     */             }
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Throwable throwable) {}
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   @Command(aliases = {"stopprofile"}, usage = "", desc = "Stop a running profile", min = 0, max = 0)
/*     */   @CommandPermissions({"worldguard.profile"})
/*     */   public void stopProfile(CommandContext args, CommandSender sender) throws CommandException {
/* 236 */     synchronized (this) {
/* 237 */       if (this.activeSampler == null) {
/* 238 */         throw new CommandException("No CPU profile is currently running.");
/*     */       }
/*     */       
/* 241 */       this.activeSampler.cancel();
/* 242 */       this.activeSampler = null;
/*     */     } 
/*     */     
/* 245 */     sender.sendMessage("The running CPU profile has been stopped.");
/*     */   }
/*     */ 
/*     */   
/*     */   @Command(aliases = {"flushstates", "clearstates"}, usage = "[player]", desc = "Flush the state manager", max = 1)
/*     */   @CommandPermissions({"worldguard.flushstates"})
/*     */   public void flushStates(CommandContext args, CommandSender sender) throws CommandException {
/* 252 */     if (args.argsLength() == 0) {
/* 253 */       this.plugin.getSessionManager().resetAllStates();
/* 254 */       sender.sendMessage("Cleared all states.");
/*     */     } else {
/* 256 */       Player player = this.plugin.getServer().getPlayer(args.getString(0));
/* 257 */       if (player != null) {
/* 258 */         this.plugin.getSessionManager().resetState(player);
/* 259 */         sender.sendMessage("Cleared states for player \"" + player.getName() + "\".");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Command(aliases = {"running", "queue"}, desc = "List running tasks", max = 0)
/*     */   @CommandPermissions({"worldguard.running"})
/*     */   public void listRunningTasks(CommandContext args, CommandSender sender) throws CommandException {
/* 267 */     List<Task<?>> tasks = this.plugin.getSupervisor().getTasks();
/*     */     
/* 269 */     if (!tasks.isEmpty()) {
/* 270 */       Collections.sort(tasks, (Comparator<? super Task<?>>)new TaskStateComparator());
/* 271 */       StringBuilder builder = new StringBuilder();
/* 272 */       builder.append(ChatColor.GRAY);
/* 273 */       builder.append("═══════════");
/* 274 */       builder.append(" Running tasks ");
/* 275 */       builder.append("═══════════");
/* 276 */       builder.append("\n").append(ChatColor.GRAY).append("Note: Some 'running' tasks may be waiting to be start.");
/* 277 */       for (Task<?> task : tasks) {
/* 278 */         builder.append("\n");
/* 279 */         builder.append(ChatColor.BLUE).append("(").append(task.getState().name()).append(") ");
/* 280 */         builder.append(ChatColor.YELLOW);
/* 281 */         builder.append(CommandUtils.getOwnerName(task.getOwner()));
/* 282 */         builder.append(": ");
/* 283 */         builder.append(ChatColor.WHITE);
/* 284 */         builder.append(task.getName());
/*     */       } 
/* 286 */       sender.sendMessage(builder.toString());
/*     */     } else {
/* 288 */       sender.sendMessage(ChatColor.YELLOW + "There are currently no running tasks.");
/*     */     } 
/*     */   }
/*     */   
/*     */   @Command(aliases = {"debug"}, desc = "Debugging commands")
/*     */   @NestedCommand({DebuggingCommands.class})
/*     */   public void debug(CommandContext args, CommandSender sender) {}
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\commands\WorldGuardCommands.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */