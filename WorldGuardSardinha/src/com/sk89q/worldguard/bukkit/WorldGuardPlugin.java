/*      */ package com.sk89q.worldguard.bukkit;
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.google.common.util.concurrent.Futures;
/*      */ import com.google.common.util.concurrent.ListeningExecutorService;
/*      */ import com.sk89q.bukkit.util.CommandsManagerRegistration;
/*      */ import com.sk89q.minecraft.util.commands.CommandException;
/*      */ import com.sk89q.minecraft.util.commands.CommandPermissionsException;
/*      */ import com.sk89q.minecraft.util.commands.CommandUsageException;
/*      */ import com.sk89q.minecraft.util.commands.CommandsManager;
/*      */ import com.sk89q.minecraft.util.commands.Injector;
/*      */ import com.sk89q.minecraft.util.commands.MissingNestedCommandException;
/*      */ import com.sk89q.minecraft.util.commands.SimpleInjector;
/*      */ import com.sk89q.minecraft.util.commands.WrappedCommandException;
/*      */ import com.sk89q.wepif.PermissionsResolverManager;
/*      */ import com.sk89q.worldedit.bukkit.WorldEditPlugin;
/*      */ import com.sk89q.worldguard.LocalPlayer;
/*      */ import com.sk89q.worldguard.bukkit.commands.GeneralCommands;
/*      */ import com.sk89q.worldguard.bukkit.commands.ProtectionCommands;
/*      */ import com.sk89q.worldguard.bukkit.commands.ToggleCommands;
/*      */ import com.sk89q.worldguard.bukkit.event.player.ProcessPlayerEvent;
/*      */ import com.sk89q.worldguard.bukkit.listener.BlacklistListener;
/*      */ import com.sk89q.worldguard.bukkit.listener.BlockedPotionsListener;
/*      */ import com.sk89q.worldguard.bukkit.listener.BuildPermissionListener;
/*      */ import com.sk89q.worldguard.bukkit.listener.ChestProtectionListener;
/*      */ import com.sk89q.worldguard.bukkit.listener.DebuggingListener;
/*      */ import com.sk89q.worldguard.bukkit.listener.EventAbstractionListener;
/*      */ import com.sk89q.worldguard.bukkit.listener.InvincibilityListener;
/*      */ import com.sk89q.worldguard.bukkit.listener.PlayerMoveListener;
/*      */ import com.sk89q.worldguard.bukkit.listener.RegionFlagsListener;
/*      */ import com.sk89q.worldguard.bukkit.listener.RegionProtectionListener;
/*      */ import com.sk89q.worldguard.bukkit.listener.WorldGuardBlockListener;
/*      */ import com.sk89q.worldguard.bukkit.listener.WorldGuardEntityListener;
/*      */ import com.sk89q.worldguard.bukkit.listener.WorldGuardHangingListener;
/*      */ import com.sk89q.worldguard.bukkit.listener.WorldGuardPlayerListener;
/*      */ import com.sk89q.worldguard.bukkit.listener.WorldGuardServerListener;
/*      */ import com.sk89q.worldguard.bukkit.listener.WorldGuardVehicleListener;
/*      */ import com.sk89q.worldguard.bukkit.listener.WorldGuardWorldListener;
/*      */ import com.sk89q.worldguard.bukkit.util.Events;
/*      */ import com.sk89q.worldguard.protection.GlobalRegionManager;
/*      */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*      */ import com.sk89q.worldguard.session.SessionManager;
/*      */ import com.sk89q.worldguard.util.concurrent.EvenMoreExecutors;
/*      */ import com.sk89q.worldguard.util.logging.ClassSourceValidator;
/*      */ import com.sk89q.worldguard.util.logging.RecordMessagePrefixer;
/*      */ import com.sk89q.worldguard.util.profile.cache.HashMapCache;
/*      */ import com.sk89q.worldguard.util.profile.cache.ProfileCache;
/*      */ import com.sk89q.worldguard.util.profile.cache.SQLiteCache;
/*      */ import com.sk89q.worldguard.util.profile.resolver.BukkitPlayerService;
/*      */ import com.sk89q.worldguard.util.profile.resolver.CacheForwardingService;
/*      */ import com.sk89q.worldguard.util.profile.resolver.CombinedProfileService;
/*      */ import com.sk89q.worldguard.util.profile.resolver.HttpRepositoryService;
/*      */ import com.sk89q.worldguard.util.profile.resolver.ProfileService;
/*      */ import com.sk89q.worldguard.util.task.SimpleSupervisor;
/*      */ import com.sk89q.worldguard.util.task.Supervisor;
/*      */ import com.sk89q.worldguard.util.task.Task;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ExecutionException;
/*      */ import java.util.jar.JarFile;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import java.util.zip.ZipEntry;
/*      */ import javax.annotation.Nullable;
/*      */ import org.bukkit.ChatColor;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.OfflinePlayer;
/*      */ import org.bukkit.World;
/*      */ import org.bukkit.block.Block;
/*      */ import org.bukkit.command.Command;
/*      */ import org.bukkit.command.CommandSender;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.event.Listener;
/*      */ import org.bukkit.permissions.Permissible;
/*      */ import org.bukkit.plugin.Plugin;
/*      */ import org.bukkit.plugin.java.JavaPlugin;
/*      */ import org.bukkit.util.Vector;
/*      */ 
/*      */ public class WorldGuardPlugin extends JavaPlugin {
/*   87 */   private static final Logger log = Logger.getLogger(WorldGuardPlugin.class.getCanonicalName());
/*      */   
/*      */   private static WorldGuardPlugin inst;
/*      */   private final CommandsManager<CommandSender> commands;
/*   91 */   private final ConfigurationManager configuration = new ConfigurationManager(this);
/*   92 */   private final RegionContainer regionContainer = new RegionContainer(this);
/*   93 */   private final GlobalRegionManager globalRegionManager = new GlobalRegionManager(this, this.regionContainer);
/*      */   private SessionManager sessionManager;
/*   95 */   private final Supervisor supervisor = (Supervisor)new SimpleSupervisor();
/*      */   
/*      */   private ListeningExecutorService executorService;
/*      */   
/*      */   private ProfileService profileService;
/*      */   
/*      */   private ProfileCache profileCache;
/*      */   
/*      */   private PlayerMoveListener playerMoveListener;
/*      */   
/*      */   public WorldGuardPlugin() {
/*  106 */     final WorldGuardPlugin plugin = inst = this;
/*  107 */     this.commands = new CommandsManager<CommandSender>()
/*      */       {
/*      */         public boolean hasPermission(CommandSender player, String perm) {
/*  110 */           return plugin.hasPermission(player, perm);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static WorldGuardPlugin inst() {
/*  120 */     return inst;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEnable() {
/*  129 */     configureLogger();
/*      */     
/*  131 */     getDataFolder().mkdirs();
/*      */     
/*  133 */     this.executorService = MoreExecutors.listeningDecorator(EvenMoreExecutors.newBoundedCachedThreadPool(0, 1, 20));
/*      */     
/*  135 */     this.sessionManager = new SessionManager(this);
/*      */ 
/*      */     
/*  138 */     this.commands.setInjector((Injector)new SimpleInjector(new Object[] { this }));
/*      */ 
/*      */ 
/*      */     
/*  142 */     ClassSourceValidator verifier = new ClassSourceValidator((Plugin)this);
/*  143 */     verifier.reportMismatches((List)ImmutableList.of(WGBukkit.class, ProtectedRegion.class, ProtectedCuboidRegion.class, Flag.class));
/*      */ 
/*      */     
/*  146 */     final CommandsManagerRegistration reg = new CommandsManagerRegistration((Plugin)this, this.commands);
/*  147 */     reg.register(ToggleCommands.class);
/*  148 */     reg.register(ProtectionCommands.class);
/*      */     
/*  150 */     getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this, new Runnable()
/*      */         {
/*      */           public void run() {
/*  153 */             if (!WorldGuardPlugin.this.getGlobalStateManager().hasCommandBookGodMode()) {
/*  154 */               reg.register(GeneralCommands.class);
/*      */             }
/*      */           }
/*      */         },  0L);
/*      */     
/*  159 */     File cacheDir = new File(getDataFolder(), "cache");
/*  160 */     cacheDir.mkdirs();
/*      */     try {
/*  162 */       this.profileCache = (ProfileCache)new SQLiteCache(new File(cacheDir, "profiles.sqlite"));
/*  163 */     } catch (IOException e) {
/*  164 */       log.log(Level.WARNING, "Failed to initialize SQLite profile cache");
/*  165 */       this.profileCache = (ProfileCache)new HashMapCache();
/*      */     } 
/*      */     
/*  168 */     this
/*      */ 
/*      */       
/*  171 */       .profileService = (ProfileService)new CacheForwardingService((ProfileService)new CombinedProfileService(new ProfileService[] { (ProfileService)BukkitPlayerService.getInstance(), HttpRepositoryService.forMinecraft() }, ), this.profileCache);
/*      */ 
/*      */     
/*  174 */     PermissionsResolverManager.initialize((Plugin)this);
/*  175 */     this.configuration.load();
/*      */     
/*  177 */     log.info("Loading region data...");
/*  178 */     this.regionContainer.initialize();
/*      */     
/*  180 */     getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this, (Runnable)this.sessionManager, 20L, 20L);
/*      */ 
/*      */     
/*  183 */     getServer().getPluginManager().registerEvents((Listener)this.sessionManager, (Plugin)this);
/*  184 */     (new WorldGuardPlayerListener(this)).registerEvents();
/*  185 */     (new WorldGuardBlockListener(this)).registerEvents();
/*  186 */     (new WorldGuardEntityListener(this)).registerEvents();
/*  187 */     (new WorldGuardWeatherListener(this)).registerEvents();
/*  188 */     (new WorldGuardVehicleListener(this)).registerEvents();
/*  189 */     (new WorldGuardServerListener(this)).registerEvents();
/*  190 */     (new WorldGuardHangingListener(this)).registerEvents();
/*      */ 
/*      */     
/*  193 */     (this.playerMoveListener = new PlayerMoveListener(this)).registerEvents();
/*  194 */     (new BlacklistListener(this)).registerEvents();
/*  195 */     (new ChestProtectionListener(this)).registerEvents();
/*  196 */     (new RegionProtectionListener(this)).registerEvents();
/*  197 */     (new RegionFlagsListener(this)).registerEvents();
/*  198 */     (new BlockedPotionsListener(this)).registerEvents();
/*  199 */     (new EventAbstractionListener(this)).registerEvents();
/*  200 */     (new PlayerModesListener(this)).registerEvents();
/*  201 */     (new BuildPermissionListener(this)).registerEvents();
/*  202 */     (new InvincibilityListener(this)).registerEvents();
/*  203 */     if ("true".equalsIgnoreCase(System.getProperty("worldguard.debug.listener"))) {
/*  204 */       (new DebuggingListener(this, log)).registerEvents();
/*      */     }
/*      */     
/*  207 */     this.configuration.updateCommandBookGodMode();
/*      */     
/*  209 */     if (getServer().getPluginManager().isPluginEnabled("CommandBook")) {
/*  210 */       getServer().getPluginManager().registerEvents((Listener)new WorldGuardCommandBookListener(this), (Plugin)this);
/*      */     }
/*      */ 
/*      */     
/*  214 */     WorldGuardWorldListener worldListener = new WorldGuardWorldListener(this);
/*  215 */     for (World world : getServer().getWorlds()) {
/*  216 */       worldListener.initWorld(world);
/*      */     }
/*  218 */     worldListener.registerEvents();
/*      */     
/*  220 */     for (Player player : BukkitUtil.getOnlinePlayers()) {
/*  221 */       ProcessPlayerEvent event = new ProcessPlayerEvent(player);
/*  222 */       Events.fire((Event)event);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void onDisable() {
/*  228 */     this.executorService.shutdown();
/*      */     
/*      */     try {
/*  231 */       log.log(Level.INFO, "Shutting down executor and waiting for any pending tasks...");
/*      */       
/*  233 */       List<Task<?>> tasks = this.supervisor.getTasks();
/*  234 */       if (!tasks.isEmpty()) {
/*  235 */         StringBuilder builder = new StringBuilder("Known tasks:");
/*  236 */         for (Task<?> task : tasks) {
/*  237 */           builder.append("\n");
/*  238 */           builder.append(task.getName());
/*      */         } 
/*  240 */         log.log(Level.INFO, builder.toString());
/*      */       } 
/*      */       
/*  243 */       Futures.successfulAsList(tasks).get();
/*  244 */       this.executorService.awaitTermination(2147483647L, TimeUnit.DAYS);
/*  245 */     } catch (InterruptedException e) {
/*  246 */       Thread.currentThread().interrupt();
/*  247 */     } catch (ExecutionException e) {
/*  248 */       log.log(Level.WARNING, "Some tasks failed while waiting for remaining tasks to finish", e);
/*      */     } 
/*      */     
/*  251 */     this.regionContainer.unload();
/*  252 */     this.configuration.unload();
/*  253 */     getServer().getScheduler().cancelTasks((Plugin)this);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
/*      */     try {
/*  259 */       this.commands.execute(cmd.getName(), args, sender, new Object[] { sender });
/*  260 */     } catch (CommandPermissionsException e) {
/*  261 */       sender.sendMessage(ChatColor.RED + "You don't have permission.");
/*  262 */     } catch (MissingNestedCommandException e) {
/*  263 */       sender.sendMessage(ChatColor.RED + e.getUsage());
/*  264 */     } catch (CommandUsageException e) {
/*  265 */       sender.sendMessage(ChatColor.RED + e.getMessage());
/*  266 */       sender.sendMessage(ChatColor.RED + e.getUsage());
/*  267 */     } catch (WrappedCommandException e) {
/*  268 */       sender.sendMessage(ChatColor.RED + convertThrowable(e.getCause()));
/*  269 */     } catch (CommandException e) {
/*  270 */       sender.sendMessage(ChatColor.RED + e.getMessage());
/*      */     } 
/*      */     
/*  273 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String convertThrowable(@Nullable Throwable throwable) {
/*  283 */     if (throwable instanceof NumberFormatException)
/*  284 */       return "Number expected, string received instead."; 
/*  285 */     if (throwable instanceof com.sk89q.worldguard.protection.managers.storage.StorageException) {
/*  286 */       log.log(Level.WARNING, "Error loading/saving regions", throwable);
/*  287 */       return "Region data could not be loaded/saved: " + throwable.getMessage();
/*  288 */     }  if (throwable instanceof java.util.concurrent.RejectedExecutionException)
/*  289 */       return "There are currently too many tasks queued to add yours. Use /wg running to list queued and running tasks."; 
/*  290 */     if (throwable instanceof java.util.concurrent.CancellationException)
/*  291 */       return "WorldGuard: Task was cancelled"; 
/*  292 */     if (throwable instanceof InterruptedException)
/*  293 */       return "WorldGuard: Task was interrupted"; 
/*  294 */     if (throwable instanceof com.sk89q.worldguard.protection.util.UnresolvedNamesException)
/*  295 */       return throwable.getMessage(); 
/*  296 */     if (throwable instanceof CommandException) {
/*  297 */       return throwable.getMessage();
/*      */     }
/*  299 */     log.log(Level.WARNING, "WorldGuard encountered an unexpected error", throwable);
/*  300 */     return "WorldGuard: An unexpected error occurred! Please see the server console.";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public GlobalRegionManager getGlobalRegionManager() {
/*  312 */     return this.globalRegionManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RegionContainer getRegionContainer() {
/*  321 */     return this.regionContainer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public ConfigurationManager getGlobalConfiguration() {
/*  332 */     return getGlobalStateManager();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SessionManager getSessionManager() {
/*  341 */     return this.sessionManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ConfigurationManager getGlobalStateManager() {
/*  350 */     return this.configuration;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Supervisor getSupervisor() {
/*  359 */     return this.supervisor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ListeningExecutorService getExecutorService() {
/*  369 */     return this.executorService;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ProfileService getProfileService() {
/*  378 */     return this.profileService;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ProfileCache getProfileCache() {
/*  387 */     return this.profileCache;
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
/*      */   public boolean inGroup(Player player, String group) {
/*      */     try {
/*  400 */       return PermissionsResolverManager.getInstance().inGroup((OfflinePlayer)player, group);
/*  401 */     } catch (Throwable t) {
/*  402 */       t.printStackTrace();
/*  403 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getGroups(Player player) {
/*      */     try {
/*  415 */       return PermissionsResolverManager.getInstance().getGroups((OfflinePlayer)player);
/*  416 */     } catch (Throwable t) {
/*  417 */       t.printStackTrace();
/*  418 */       return new String[0];
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
/*      */   public String toUniqueName(CommandSender sender) {
/*  430 */     if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
/*  431 */       return "*Console*";
/*      */     }
/*  433 */     return sender.getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toName(CommandSender sender) {
/*  444 */     if (sender instanceof org.bukkit.command.ConsoleCommandSender)
/*  445 */       return "*Console*"; 
/*  446 */     if (sender instanceof Player) {
/*  447 */       return ((Player)sender).getDisplayName();
/*      */     }
/*  449 */     return sender.getName();
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
/*      */   public boolean hasPermission(CommandSender sender, String perm) {
/*  461 */     if (sender.isOp()) {
/*  462 */       if (sender instanceof Player) {
/*  463 */         if ((getGlobalStateManager().get(((Player)sender)
/*  464 */             .getWorld())).opPermissions) {
/*  465 */           return true;
/*      */         }
/*      */       } else {
/*  468 */         return true;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  473 */     if (sender instanceof Player) {
/*  474 */       Player player = (Player)sender;
/*  475 */       return PermissionsResolverManager.getInstance().hasPermission(player.getWorld().getName(), (OfflinePlayer)player, perm);
/*      */     } 
/*      */     
/*  478 */     return false;
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
/*      */   public void checkPermission(CommandSender sender, String perm) throws CommandPermissionsException {
/*  490 */     if (!hasPermission(sender, perm)) {
/*  491 */       throw new CommandPermissionsException();
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
/*      */   public Player checkPlayer(CommandSender sender) throws CommandException {
/*  504 */     if (sender instanceof Player) {
/*  505 */       return (Player)sender;
/*      */     }
/*  507 */     throw new CommandException("A player is expected.");
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
/*      */   public List<Player> matchPlayerNames(String filter) {
/*  523 */     Collection<? extends Player> players = BukkitUtil.getOnlinePlayers();
/*      */     
/*  525 */     filter = filter.toLowerCase();
/*      */ 
/*      */     
/*  528 */     if (filter.charAt(0) == '@' && filter.length() >= 2) {
/*  529 */       filter = filter.substring(1);
/*      */       
/*  531 */       for (Player player : players) {
/*  532 */         if (player.getName().equalsIgnoreCase(filter)) {
/*  533 */           List<Player> list1 = new ArrayList<Player>();
/*  534 */           list1.add(player);
/*  535 */           return list1;
/*      */         } 
/*      */       } 
/*      */       
/*  539 */       return new ArrayList<Player>();
/*      */     } 
/*  541 */     if (filter.charAt(0) == '*' && filter.length() >= 2) {
/*  542 */       filter = filter.substring(1);
/*      */       
/*  544 */       List<Player> list1 = new ArrayList<Player>();
/*      */       
/*  546 */       for (Player player : players) {
/*  547 */         if (player.getName().toLowerCase().contains(filter)) {
/*  548 */           list1.add(player);
/*      */         }
/*      */       } 
/*      */       
/*  552 */       return list1;
/*      */     } 
/*      */ 
/*      */     
/*  556 */     List<Player> list = new ArrayList<Player>();
/*      */     
/*  558 */     for (Player player : players) {
/*  559 */       if (player.getName().toLowerCase().startsWith(filter)) {
/*  560 */         list.add(player);
/*      */       }
/*      */     } 
/*      */     
/*  564 */     return list;
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
/*      */   protected Iterable<? extends Player> checkPlayerMatch(List<? extends Player> players) throws CommandException {
/*  579 */     if (players.size() == 0) {
/*  580 */       throw new CommandException("No players matched query.");
/*      */     }
/*      */     
/*  583 */     return players;
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
/*      */ 
/*      */   
/*      */   public Iterable<? extends Player> matchPlayers(CommandSender source, String filter) throws CommandException {
/*  604 */     if (BukkitUtil.getOnlinePlayers().isEmpty()) {
/*  605 */       throw new CommandException("No players matched query.");
/*      */     }
/*      */     
/*  608 */     if (filter.equals("*")) {
/*  609 */       return checkPlayerMatch(Lists.newArrayList(BukkitUtil.getOnlinePlayers()));
/*      */     }
/*      */ 
/*      */     
/*  613 */     if (filter.charAt(0) == '#') {
/*      */ 
/*      */       
/*  616 */       if (filter.equalsIgnoreCase("#world")) {
/*  617 */         List<Player> list = new ArrayList<Player>();
/*  618 */         Player sourcePlayer = checkPlayer(source);
/*  619 */         World sourceWorld = sourcePlayer.getWorld();
/*      */         
/*  621 */         for (Player player : BukkitUtil.getOnlinePlayers()) {
/*  622 */           if (player.getWorld().equals(sourceWorld)) {
/*  623 */             list.add(player);
/*      */           }
/*      */         } 
/*      */         
/*  627 */         return checkPlayerMatch(list);
/*      */       } 
/*      */       
/*  630 */       if (filter.equalsIgnoreCase("#near")) {
/*  631 */         List<Player> list = new ArrayList<Player>();
/*  632 */         Player sourcePlayer = checkPlayer(source);
/*  633 */         World sourceWorld = sourcePlayer.getWorld();
/*      */         
/*  635 */         Vector sourceVector = sourcePlayer.getLocation().toVector();
/*      */         
/*  637 */         for (Player player : BukkitUtil.getOnlinePlayers()) {
/*  638 */           if (player.getWorld().equals(sourceWorld) && player
/*  639 */             .getLocation().toVector().distanceSquared(sourceVector) < 900.0D)
/*      */           {
/*  641 */             list.add(player);
/*      */           }
/*      */         } 
/*      */         
/*  645 */         return checkPlayerMatch(list);
/*      */       } 
/*      */       
/*  648 */       throw new CommandException("Invalid group '" + filter + "'.");
/*      */     } 
/*      */ 
/*      */     
/*  652 */     List<Player> players = matchPlayerNames(filter);
/*      */     
/*  654 */     return checkPlayerMatch(players);
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
/*      */   public Player matchSinglePlayer(CommandSender sender, String filter) throws CommandException {
/*  669 */     Iterator<? extends Player> players = matchPlayers(sender, filter).iterator();
/*      */     
/*  671 */     Player match = players.next();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  676 */     if (players.hasNext()) {
/*  677 */       throw new CommandException("More than one player found! Use @<name> for exact matching.");
/*      */     }
/*      */ 
/*      */     
/*  681 */     return match;
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
/*      */   public CommandSender matchPlayerOrConsole(CommandSender sender, String filter) throws CommandException {
/*  699 */     if (filter.equalsIgnoreCase("#console") || filter
/*  700 */       .equalsIgnoreCase("*console*") || filter
/*  701 */       .equalsIgnoreCase("!")) {
/*  702 */       return (CommandSender)getServer().getConsoleSender();
/*      */     }
/*      */     
/*  705 */     return (CommandSender)matchSinglePlayer(sender, filter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Iterable<Player> matchPlayers(Player player) {
/*  715 */     return Arrays.asList(new Player[] { player });
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
/*      */   public World matchWorld(CommandSender sender, String filter) throws CommandException {
/*  734 */     List<World> worlds = getServer().getWorlds();
/*      */ 
/*      */     
/*  737 */     if (filter.charAt(0) == '#') {
/*      */       
/*  739 */       if (filter.equalsIgnoreCase("#main")) {
/*  740 */         return worlds.get(0);
/*      */       }
/*      */       
/*  743 */       if (filter.equalsIgnoreCase("#normal")) {
/*  744 */         for (World world : worlds) {
/*  745 */           if (world.getEnvironment() == World.Environment.NORMAL) {
/*  746 */             return world;
/*      */           }
/*      */         } 
/*      */         
/*  750 */         throw new CommandException("No normal world found.");
/*      */       } 
/*      */       
/*  753 */       if (filter.equalsIgnoreCase("#nether")) {
/*  754 */         for (World world : worlds) {
/*  755 */           if (world.getEnvironment() == World.Environment.NETHER) {
/*  756 */             return world;
/*      */           }
/*      */         } 
/*      */         
/*  760 */         throw new CommandException("No nether world found.");
/*      */       } 
/*      */       
/*  763 */       if (filter.matches("^#player$")) {
/*  764 */         String[] parts = filter.split(":", 2);
/*      */ 
/*      */         
/*  767 */         if (parts.length == 1) {
/*  768 */           throw new CommandException("Argument expected for #player.");
/*      */         }
/*      */         
/*  771 */         return ((Player)matchPlayers(sender, parts[1]).iterator().next()).getWorld();
/*      */       } 
/*  773 */       throw new CommandException("Invalid identifier '" + filter + "'.");
/*      */     } 
/*      */ 
/*      */     
/*  777 */     for (World world : worlds) {
/*  778 */       if (world.getName().equals(filter)) {
/*  779 */         return world;
/*      */       }
/*      */     } 
/*      */     
/*  783 */     throw new CommandException("No world by that exact name found.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldEditPlugin getWorldEdit() throws CommandException {
/*  793 */     Plugin worldEdit = getServer().getPluginManager().getPlugin("WorldEdit");
/*  794 */     if (worldEdit == null) {
/*  795 */       throw new CommandException("WorldEdit does not appear to be installed.");
/*      */     }
/*      */     
/*  798 */     if (worldEdit instanceof WorldEditPlugin) {
/*  799 */       return (WorldEditPlugin)worldEdit;
/*      */     }
/*  801 */     throw new CommandException("WorldEdit detection failed (report error).");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalPlayer wrapPlayer(Player player) {
/*  812 */     return new BukkitPlayer(this, player);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalPlayer wrapPlayer(Player player, boolean silenced) {
/*  823 */     return new BukkitPlayer(this, player, silenced);
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
/*      */   public LocalPlayer wrapOfflinePlayer(OfflinePlayer player) {
/*  835 */     return new BukkitOfflinePlayer(player);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ProtectionQuery createProtectionQuery() {
/*  846 */     return new ProtectionQuery();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void configureLogger() {
/*  853 */     RecordMessagePrefixer.register(Logger.getLogger("com.sk89q.worldguard"), "[WorldGuard] ");
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
/*      */   public void createDefaultConfiguration(File actual, String defaultName) {
/*  866 */     File parent = actual.getParentFile();
/*  867 */     if (!parent.exists()) {
/*  868 */       parent.mkdirs();
/*      */     }
/*      */     
/*  871 */     if (actual.exists()) {
/*      */       return;
/*      */     }
/*      */     
/*  875 */     InputStream input = null;
/*      */     
/*      */     try {
/*  878 */       JarFile file = new JarFile(getFile());
/*  879 */       ZipEntry copy = file.getEntry("defaults/" + defaultName);
/*  880 */       if (copy == null) throw new FileNotFoundException(); 
/*  881 */       input = file.getInputStream(copy);
/*  882 */     } catch (IOException e) {
/*  883 */       log.severe("Unable to read default configuration: " + defaultName);
/*      */     } 
/*      */     
/*  886 */     if (input != null) {
/*  887 */       FileOutputStream output = null;
/*      */       
/*      */       try {
/*  890 */         output = new FileOutputStream(actual);
/*  891 */         byte[] buf = new byte[8192];
/*  892 */         int length = 0;
/*  893 */         while ((length = input.read(buf)) > 0) {
/*  894 */           output.write(buf, 0, length);
/*      */         }
/*      */         
/*  897 */         log.info("Default configuration file written: " + actual
/*  898 */             .getAbsolutePath());
/*  899 */       } catch (IOException e) {
/*  900 */         e.printStackTrace();
/*      */       } finally {
/*      */         try {
/*  903 */           if (input != null) {
/*  904 */             input.close();
/*      */           }
/*  906 */         } catch (IOException ignore) {}
/*      */ 
/*      */         
/*      */         try {
/*  910 */           if (output != null) {
/*  911 */             output.close();
/*      */           }
/*  913 */         } catch (IOException ignore) {}
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
/*      */   public void broadcastNotification(String msg) {
/*  927 */     getServer().broadcast(msg, "worldguard.notify");
/*  928 */     Set<Permissible> subs = getServer().getPluginManager().getPermissionSubscriptions("worldguard.notify");
/*  929 */     for (Player player : BukkitUtil.getOnlinePlayers()) {
/*  930 */       if ((!subs.contains(player) || !player.hasPermission("worldguard.notify")) && 
/*  931 */         hasPermission((CommandSender)player, "worldguard.notify")) {
/*  932 */         player.sendMessage(msg);
/*      */       }
/*      */     } 
/*  935 */     log.info(msg);
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
/*      */   public boolean canBuild(Player player, Location loc) {
/*  948 */     return getGlobalRegionManager().canBuild(player, loc);
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
/*      */   public boolean canBuild(Player player, Block block) {
/*  961 */     return getGlobalRegionManager().canBuild(player, block);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RegionManager getRegionManager(World world) {
/*  971 */     if (!(getGlobalStateManager().get(world)).useRegions) {
/*  972 */       return null;
/*      */     }
/*      */     
/*  975 */     return getGlobalRegionManager().get(world);
/*      */   }
/*      */   
/*      */   public PlayerMoveListener getPlayerMoveListener() {
/*  979 */     return this.playerMoveListener;
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
/*      */   public String replaceMacros(CommandSender sender, String message) {
/*  998 */     Collection<? extends Player> online = BukkitUtil.getOnlinePlayers();
/*      */     
/* 1000 */     message = message.replace("%name%", toName(sender));
/* 1001 */     message = message.replace("%id%", toUniqueName(sender));
/* 1002 */     message = message.replace("%online%", String.valueOf(online.size()));
/*      */     
/* 1004 */     if (sender instanceof Player) {
/* 1005 */       Player player = (Player)sender;
/* 1006 */       World world = player.getWorld();
/*      */       
/* 1008 */       message = message.replace("%world%", world.getName());
/* 1009 */       message = message.replace("%health%", String.valueOf(player.getHealth()));
/*      */     } 
/*      */     
/* 1012 */     return message;
/*      */   }
/*      */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\WorldGuardPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */