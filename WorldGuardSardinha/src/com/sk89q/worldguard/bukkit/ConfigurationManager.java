/*     */ package com.sk89q.worldguard.bukkit;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.sk89q.util.yaml.YAMLFormat;
/*     */ import com.sk89q.util.yaml.YAMLProcessor;
/*     */ import com.sk89q.worldguard.protection.managers.storage.DriverType;
/*     */ import com.sk89q.worldguard.protection.managers.storage.RegionDriver;
/*     */ import com.sk89q.worldguard.protection.managers.storage.file.DirectoryYamlDriver;
/*     */ import com.sk89q.worldguard.protection.managers.storage.sql.SQLDriver;
/*     */ import com.sk89q.worldguard.session.handler.WaterBreathing;
/*     */ import com.sk89q.worldguard.util.report.Unreported;
/*     */ import com.sk89q.worldguard.util.sql.DataSourceConfig;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.World;
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
/*     */ public class ConfigurationManager
/*     */ {
/*  52 */   private static final Logger log = Logger.getLogger(ConfigurationManager.class.getCanonicalName());
/*     */ 
/*     */   
/*     */   private static final String CONFIG_HEADER = "#\r\n# WorldGuard's main configuration file\r\n#\r\n# This is the global configuration file. Anything placed into here will\r\n# be applied to all worlds. However, each world has its own configuration\r\n# file to allow you to replace most settings in here for that world only.\r\n#\r\n# About editing this file:\r\n# - DO NOT USE TABS. You MUST use spaces or Bukkit will complain. If\r\n#   you use an editor like Notepad++ (recommended for Windows users), you\r\n#   must configure it to \"replace tabs with spaces.\" In Notepad++, this can\r\n#   be changed in Settings > Preferences > Language Menu.\r\n# - Don't get rid of the indents. They are indented so some entries are\r\n#   in categories (like \"enforce-single-session\" is in the \"protection\"\r\n#   category.\r\n# - If you want to check the format of this file before putting it\r\n#   into WorldGuard, paste it into http://yaml-online-parser.appspot.com/\r\n#   and see if it gives \"ERROR:\".\r\n# - Lines starting with # are comments and so they are ignored.\r\n#\r\n";
/*     */ 
/*     */   
/*     */   @Unreported
/*     */   private WorldGuardPlugin plugin;
/*     */ 
/*     */   
/*     */   @Unreported
/*     */   private ConcurrentMap<String, WorldConfiguration> worlds;
/*     */   
/*     */   @Unreported
/*     */   private YAMLProcessor config;
/*     */   
/*     */   private boolean hasCommandBookGodMode = false;
/*     */   
/*     */   public boolean useRegionsCreatureSpawnEvent;
/*     */   
/*     */   public boolean activityHaltToggle = false;
/*     */   
/*     */   public boolean useGodPermission;
/*     */   
/*     */   public boolean useGodGroup;
/*     */   
/*     */   public boolean useAmphibiousGroup;
/*     */   
/*     */   public boolean usePlayerMove;
/*     */   
/*     */   public boolean usePlayerTeleports;
/*     */   
/*     */   public boolean deopOnJoin;
/*     */   
/*     */   public boolean blockInGameOp;
/*     */   
/*     */   public boolean migrateRegionsToUuid;
/*     */   
/*     */   public boolean keepUnresolvedNames;
/*     */   
/*     */   @Unreported
/*  93 */   public Map<String, String> hostKeys = new HashMap<String, String>();
/*     */ 
/*     */ 
/*     */   
/*     */   @Unreported
/*     */   public RegionDriver selectedRegionStoreDriver;
/*     */ 
/*     */   
/*     */   @Unreported
/*     */   public Map<DriverType, RegionDriver> regionStoreDriverMap;
/*     */ 
/*     */ 
/*     */   
/*     */   public ConfigurationManager(WorldGuardPlugin plugin) {
/* 107 */     this.plugin = plugin;
/* 108 */     this.worlds = new ConcurrentHashMap<String, WorldConfiguration>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getDataFolder() {
/* 117 */     return this.plugin.getDataFolder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getWorldsDataFolder() {
/* 127 */     return new File(getDataFolder(), "worlds");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load() {
/* 136 */     this.plugin.createDefaultConfiguration(new File(this.plugin
/* 137 */           .getDataFolder(), "config.yml"), "config.yml");
/*     */     
/* 139 */     this.config = new YAMLProcessor(new File(this.plugin.getDataFolder(), "config.yml"), true, YAMLFormat.EXTENDED);
/*     */     try {
/* 141 */       this.config.load();
/* 142 */     } catch (IOException e) {
/* 143 */       log.severe("Error reading configuration for global config: ");
/* 144 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 147 */     this.config.removeProperty("suppress-tick-sync-warnings");
/* 148 */     this.migrateRegionsToUuid = this.config.getBoolean("regions.uuid-migration.perform-on-next-start", true);
/* 149 */     this.keepUnresolvedNames = this.config.getBoolean("regions.uuid-migration.keep-names-that-lack-uuids", true);
/* 150 */     this.useRegionsCreatureSpawnEvent = this.config.getBoolean("regions.use-creature-spawn-event", true);
/* 151 */     this.useGodPermission = this.config.getBoolean("auto-invincible", this.config.getBoolean("auto-invincible-permission", false));
/* 152 */     this.useGodGroup = this.config.getBoolean("auto-invincible-group", false);
/* 153 */     this.useAmphibiousGroup = this.config.getBoolean("auto-no-drowning-group", false);
/* 154 */     this.config.removeProperty("auto-invincible-permission");
/* 155 */     this.usePlayerMove = this.config.getBoolean("use-player-move-event", true);
/* 156 */     this.usePlayerTeleports = this.config.getBoolean("use-player-teleports", true);
/*     */     
/* 158 */     this.deopOnJoin = this.config.getBoolean("security.deop-everyone-on-join", false);
/* 159 */     this.blockInGameOp = this.config.getBoolean("security.block-in-game-op-command", false);
/*     */     
/* 161 */     this.hostKeys = new HashMap<String, String>();
/* 162 */     Object hostKeysRaw = this.config.getProperty("host-keys");
/* 163 */     if (hostKeysRaw == null || !(hostKeysRaw instanceof Map)) {
/* 164 */       this.config.setProperty("host-keys", new HashMap<Object, Object>());
/*     */     } else {
/* 166 */       for (Map.Entry<Object, Object> entry : (Iterable<Map.Entry<Object, Object>>)((Map)hostKeysRaw).entrySet()) {
/* 167 */         String key = String.valueOf(entry.getKey());
/* 168 */         String value = String.valueOf(entry.getValue());
/* 169 */         this.hostKeys.put(key.toLowerCase(), value);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 177 */     boolean useSqlDatabase = this.config.getBoolean("regions.sql.use", false);
/* 178 */     String sqlDsn = this.config.getString("regions.sql.dsn", "jdbc:mysql://localhost/worldguard");
/* 179 */     String sqlUsername = this.config.getString("regions.sql.username", "worldguard");
/* 180 */     String sqlPassword = this.config.getString("regions.sql.password", "worldguard");
/* 181 */     String sqlTablePrefix = this.config.getString("regions.sql.table-prefix", "");
/*     */     
/* 183 */     DataSourceConfig dataSourceConfig = new DataSourceConfig(sqlDsn, sqlUsername, sqlPassword, sqlTablePrefix);
/* 184 */     SQLDriver sqlDriver = new SQLDriver(dataSourceConfig);
/* 185 */     DirectoryYamlDriver yamlDriver = new DirectoryYamlDriver(getWorldsDataFolder(), "regions.yml");
/*     */     
/* 187 */     this
/*     */ 
/*     */       
/* 190 */       .regionStoreDriverMap = (Map<DriverType, RegionDriver>)ImmutableMap.builder().put(DriverType.MYSQL, sqlDriver).put(DriverType.YAML, yamlDriver).build();
/* 191 */     this.selectedRegionStoreDriver = useSqlDatabase ? (RegionDriver)sqlDriver : (RegionDriver)yamlDriver;
/*     */ 
/*     */     
/* 194 */     for (World world : this.plugin.getServer().getWorlds()) {
/* 195 */       get(world);
/*     */     }
/*     */     
/* 198 */     this.config.setHeader("#\r\n# WorldGuard's main configuration file\r\n#\r\n# This is the global configuration file. Anything placed into here will\r\n# be applied to all worlds. However, each world has its own configuration\r\n# file to allow you to replace most settings in here for that world only.\r\n#\r\n# About editing this file:\r\n# - DO NOT USE TABS. You MUST use spaces or Bukkit will complain. If\r\n#   you use an editor like Notepad++ (recommended for Windows users), you\r\n#   must configure it to \"replace tabs with spaces.\" In Notepad++, this can\r\n#   be changed in Settings > Preferences > Language Menu.\r\n# - Don't get rid of the indents. They are indented so some entries are\r\n#   in categories (like \"enforce-single-session\" is in the \"protection\"\r\n#   category.\r\n# - If you want to check the format of this file before putting it\r\n#   into WorldGuard, paste it into http://yaml-online-parser.appspot.com/\r\n#   and see if it gives \"ERROR:\".\r\n# - Lines starting with # are comments and so they are ignored.\r\n#\r\n");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unload() {
/* 205 */     this.worlds.clear();
/*     */   }
/*     */   
/*     */   public void disableUuidMigration() {
/* 209 */     this.config.setProperty("regions.uuid-migration.perform-on-next-start", Boolean.valueOf(false));
/* 210 */     if (!this.config.save()) {
/* 211 */       log.severe("Error saving configuration!");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldConfiguration get(World world) {
/* 222 */     String worldName = world.getName();
/* 223 */     WorldConfiguration config = this.worlds.get(worldName);
/* 224 */     WorldConfiguration newConfig = null;
/*     */     
/* 226 */     while (config == null) {
/* 227 */       if (newConfig == null) {
/* 228 */         newConfig = new WorldConfiguration(this.plugin, worldName, this.config);
/*     */       }
/* 230 */       this.worlds.putIfAbsent(world.getName(), newConfig);
/* 231 */       config = this.worlds.get(world.getName());
/*     */     } 
/*     */     
/* 234 */     return config;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasGodMode(Player player) {
/* 244 */     return this.plugin.getSessionManager().get(player).isInvincible(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableAmphibiousMode(Player player) {
/* 253 */     WaterBreathing handler = (WaterBreathing)this.plugin.getSessionManager().get(player).getHandler(WaterBreathing.class);
/* 254 */     if (handler != null) {
/* 255 */       handler.setWaterBreathing(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disableAmphibiousMode(Player player) {
/* 265 */     WaterBreathing handler = (WaterBreathing)this.plugin.getSessionManager().get(player).getHandler(WaterBreathing.class);
/* 266 */     if (handler != null) {
/* 267 */       handler.setWaterBreathing(false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasAmphibiousMode(Player player) {
/* 278 */     WaterBreathing handler = (WaterBreathing)this.plugin.getSessionManager().get(player).getHandler(WaterBreathing.class);
/* 279 */     return (handler != null && handler.hasWaterBreathing());
/*     */   }
/*     */   
/*     */   public void updateCommandBookGodMode() {
/*     */     try {
/* 284 */       if (this.plugin.getServer().getPluginManager().isPluginEnabled("CommandBook")) {
/* 285 */         Class.forName("com.sk89q.commandbook.GodComponent");
/* 286 */         this.hasCommandBookGodMode = true;
/*     */         return;
/*     */       } 
/* 289 */     } catch (ClassNotFoundException ignore) {}
/* 290 */     this.hasCommandBookGodMode = false;
/*     */   }
/*     */   
/*     */   public boolean hasCommandBookGodMode() {
/* 294 */     return this.hasCommandBookGodMode;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\ConfigurationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */