/*     */ package com.sk89q.worldguard.bukkit;
/*     */ 
/*     */ import com.sk89q.util.yaml.YAMLFormat;
/*     */ import com.sk89q.util.yaml.YAMLProcessor;
/*     */ import com.sk89q.worldguard.blacklist.Blacklist;
/*     */ import com.sk89q.worldguard.blacklist.BlacklistLoggerHandler;
/*     */ import com.sk89q.worldguard.blacklist.logger.ConsoleHandler;
/*     */ import com.sk89q.worldguard.blacklist.logger.DatabaseHandler;
/*     */ import com.sk89q.worldguard.blacklist.logger.FileHandler;
/*     */ import com.sk89q.worldguard.blacklist.logger.LoggerHandler;
/*     */ import com.sk89q.worldguard.blacklist.target.TargetMatcherParseException;
/*     */ import com.sk89q.worldguard.blacklist.target.TargetMatcherParser;
/*     */ import com.sk89q.worldguard.bukkit.commands.CommandUtils;
/*     */ import com.sk89q.worldguard.bukkit.internal.BukkitBlacklist;
/*     */ import com.sk89q.worldguard.bukkit.internal.TargetMatcherSet;
/*     */ import com.sk89q.worldguard.chest.ChestProtection;
/*     */ import com.sk89q.worldguard.chest.SignChestProtection;
/*     */ import com.sk89q.worldguard.util.report.Unreported;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.yaml.snakeyaml.parser.ParserException;
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
/*     */ public class WorldConfiguration
/*     */ {
/*  58 */   private static final Logger log = Logger.getLogger(WorldConfiguration.class.getCanonicalName());
/*  59 */   private static final TargetMatcherParser matcherParser = new TargetMatcherParser();
/*     */   
/*     */   public static final String CONFIG_HEADER = "#\r\n# WorldGuard's world configuration file\r\n#\r\n# This is a world configuration file. Anything placed into here will only\r\n# affect this world. If you don't put anything in this file, then the\r\n# settings will be inherited from the main configuration file.\r\n#\r\n# If you see {} below, that means that there are NO entries in this file.\r\n# Remove the {} and add your own entries.\r\n#\r\n";
/*     */   
/*     */   @Unreported
/*     */   private WorldGuardPlugin plugin;
/*     */   
/*     */   @Unreported
/*     */   private String worldName;
/*     */   
/*     */   @Unreported
/*     */   private YAMLProcessor parentConfig;
/*     */   
/*     */   @Unreported
/*     */   private YAMLProcessor config;
/*     */   
/*     */   private File blacklistFile;
/*     */   
/*     */   @Unreported
/*     */   private Blacklist blacklist;
/*     */   @Unreported
/*  80 */   private ChestProtection chestProtection = (ChestProtection)new SignChestProtection();
/*     */   
/*     */   public boolean summaryOnStart;
/*     */   
/*     */   public boolean opPermissions;
/*     */   public boolean buildPermissions;
/*  86 */   public String buildPermissionDenyMessage = "";
/*     */   
/*     */   public boolean fireSpreadDisableToggle;
/*     */   
/*     */   public boolean itemDurability;
/*     */   
/*     */   public boolean simulateSponge;
/*     */   
/*     */   public int spongeRadius;
/*     */   
/*     */   public boolean disableExpDrops;
/*     */   
/*     */   public Set<PotionEffectType> blockPotions;
/*     */   
/*     */   public boolean blockPotionsAlways;
/*     */   
/*     */   public boolean pumpkinScuba;
/*     */   
/*     */   public boolean redstoneSponges;
/*     */   
/*     */   public boolean noPhysicsGravel;
/*     */   
/*     */   public boolean noPhysicsSand;
/*     */   
/*     */   public boolean ropeLadders;
/*     */   
/*     */   public boolean allowPortalAnywhere;
/*     */   public Set<Integer> preventWaterDamage;
/*     */   public boolean blockLighter;
/*     */   public boolean disableFireSpread;
/*     */   public Set<Integer> disableFireSpreadBlocks;
/*     */   public boolean preventLavaFire;
/*     */   public Set<Integer> allowedLavaSpreadOver;
/*     */   public boolean blockTNTExplosions;
/*     */   public boolean blockTNTBlockDamage;
/*     */   public boolean blockCreeperExplosions;
/*     */   public boolean blockCreeperBlockDamage;
/*     */   public boolean blockWitherExplosions;
/*     */   public boolean blockWitherBlockDamage;
/*     */   public boolean blockWitherSkullExplosions;
/*     */   public boolean blockWitherSkullBlockDamage;
/*     */   public boolean blockEnderDragonBlockDamage;
/*     */   public boolean blockEnderDragonPortalCreation;
/*     */   public boolean blockFireballExplosions;
/*     */   public boolean blockFireballBlockDamage;
/*     */   public boolean blockOtherExplosions;
/*     */   public boolean blockEntityPaintingDestroy;
/*     */   public boolean blockEntityItemFrameDestroy;
/*     */   public boolean blockPluginSpawning;
/*     */   public boolean blockGroundSlimes;
/*     */   public boolean blockZombieDoorDestruction;
/*     */   public boolean disableContactDamage;
/*     */   public boolean disableFallDamage;
/*     */   public boolean disableLavaDamage;
/*     */   public boolean disableFireDamage;
/*     */   public boolean disableLightningDamage;
/*     */   public boolean disableDrowningDamage;
/*     */   public boolean disableSuffocationDamage;
/*     */   public boolean teleportOnSuffocation;
/*     */   public boolean disableVoidDamage;
/*     */   public boolean teleportOnVoid;
/*     */   public boolean disableExplosionDamage;
/*     */   public boolean disableMobDamage;
/*     */   public boolean useRegions;
/*     */   public boolean highFreqFlags;
/*     */   public boolean checkLiquidFlow;
/*     */   public int regionWand;
/*     */   public Set<EntityType> blockCreatureSpawn;
/*     */   public boolean allowTamedSpawns;
/*     */   public int maxClaimVolume;
/*     */   public boolean claimOnlyInsideExistingRegions;
/*     */   public int maxRegionCountPerPlayer;
/*     */   public boolean antiWolfDumbness;
/*     */   public boolean signChestProtection;
/*     */   public boolean disableSignChestProtectionCheck;
/*     */   public boolean removeInfiniteStacks;
/*     */   public boolean disableCreatureCropTrampling;
/*     */   public boolean disablePlayerCropTrampling;
/*     */   public boolean preventLightningFire;
/*     */   public Set<Integer> disallowedLightningBlocks;
/*     */   public boolean disableThunder;
/*     */   public boolean disableWeather;
/*     */   public boolean alwaysRaining;
/*     */   public boolean alwaysThundering;
/*     */   public boolean disablePigZap;
/*     */   public boolean disableCreeperPower;
/*     */   public boolean disableHealthRegain;
/*     */   public boolean disableMushroomSpread;
/*     */   public boolean disableIceMelting;
/*     */   public boolean disableSnowMelting;
/*     */   public boolean disableSnowFormation;
/*     */   public boolean disableIceFormation;
/*     */   public boolean disableLeafDecay;
/*     */   public boolean disableGrassGrowth;
/*     */   public boolean disableMyceliumSpread;
/*     */   public boolean disableVineGrowth;
/*     */   public boolean disableEndermanGriefing;
/*     */   public boolean disableSnowmanTrails;
/*     */   public boolean disableSoilDehydration;
/*     */   public Set<Integer> allowedSnowFallOver;
/*     */   public boolean regionInvinciblityRemovesMobs;
/*     */   public boolean fakePlayerBuildOverride;
/*     */   public boolean explosionFlagCancellation;
/*     */   public boolean disableDeathMessages;
/*     */   public boolean disableObsidianGenerators;
/*     */   public boolean strictEntitySpawn;
/*     */   public TargetMatcherSet allowAllInteract;
/*     */   public TargetMatcherSet blockUseAtFeet;
/*     */   private Map<String, Integer> maxRegionCounts;
/*     */   
/*     */   public WorldConfiguration(WorldGuardPlugin plugin, String worldName, YAMLProcessor parentConfig) {
/* 197 */     File baseFolder = new File(plugin.getDataFolder(), "worlds/" + worldName);
/* 198 */     File configFile = new File(baseFolder, "config.yml");
/* 199 */     this.blacklistFile = new File(baseFolder, "blacklist.txt");
/*     */     
/* 201 */     this.plugin = plugin;
/* 202 */     this.worldName = worldName;
/* 203 */     this.parentConfig = parentConfig;
/*     */     
/* 205 */     plugin.createDefaultConfiguration(configFile, "config_world.yml");
/* 206 */     plugin.createDefaultConfiguration(this.blacklistFile, "blacklist.txt");
/*     */     
/* 208 */     this.config = new YAMLProcessor(configFile, true, YAMLFormat.EXTENDED);
/* 209 */     loadConfiguration();
/*     */     
/* 211 */     if (this.summaryOnStart) {
/* 212 */       log.info("Loaded configuration for world '" + worldName + "'");
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean getBoolean(String node, boolean def) {
/* 217 */     boolean val = this.parentConfig.getBoolean(node, def);
/*     */     
/* 219 */     if (this.config.getProperty(node) != null) {
/* 220 */       return this.config.getBoolean(node, def);
/*     */     }
/* 222 */     return val;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getString(String node, String def) {
/* 227 */     String val = this.parentConfig.getString(node, def);
/*     */     
/* 229 */     if (this.config.getProperty(node) != null) {
/* 230 */       return this.config.getString(node, def);
/*     */     }
/* 232 */     return val;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getInt(String node, int def) {
/* 237 */     int val = this.parentConfig.getInt(node, def);
/*     */     
/* 239 */     if (this.config.getProperty(node) != null) {
/* 240 */       return this.config.getInt(node, def);
/*     */     }
/* 242 */     return val;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private double getDouble(String node, double def) {
/* 248 */     double val = this.parentConfig.getDouble(node, def);
/*     */     
/* 250 */     if (this.config.getProperty(node) != null) {
/* 251 */       return this.config.getDouble(node, def);
/*     */     }
/* 253 */     return val;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Integer> getIntList(String node, List<Integer> def) {
/* 258 */     List<Integer> res = this.parentConfig.getIntList(node, def);
/*     */     
/* 260 */     if (res == null || res.size() == 0) {
/* 261 */       this.parentConfig.setProperty(node, new ArrayList());
/*     */     }
/*     */     
/* 264 */     if (this.config.getProperty(node) != null) {
/* 265 */       res = this.config.getIntList(node, def);
/*     */     }
/*     */     
/* 268 */     return res;
/*     */   }
/*     */   
/*     */   private TargetMatcherSet getTargetMatchers(String node) {
/* 272 */     TargetMatcherSet set = new TargetMatcherSet();
/* 273 */     List<String> inputs = this.parentConfig.getStringList(node, null);
/*     */     
/* 275 */     if (inputs == null || inputs.size() == 0) {
/* 276 */       this.parentConfig.setProperty(node, new ArrayList());
/* 277 */       return set;
/*     */     } 
/*     */     
/* 280 */     for (String input : inputs) {
/*     */       try {
/* 282 */         set.add(matcherParser.fromInput(input));
/* 283 */       } catch (TargetMatcherParseException e) {
/* 284 */         log.warning("Failed to parse the block / item type specified as '" + input + "'");
/*     */       } 
/*     */     } 
/*     */     
/* 288 */     return set;
/*     */   }
/*     */   
/*     */   private List<String> getStringList(String node, List<String> def) {
/* 292 */     List<String> res = this.parentConfig.getStringList(node, def);
/*     */     
/* 294 */     if (res == null || res.size() == 0) {
/* 295 */       this.parentConfig.setProperty(node, new ArrayList());
/*     */     }
/*     */     
/* 298 */     if (this.config.getProperty(node) != null) {
/* 299 */       res = this.config.getStringList(node, def);
/*     */     }
/*     */     
/* 302 */     return res;
/*     */   }
/*     */   
/*     */   private List<String> getKeys(String node) {
/* 306 */     List<String> res = this.parentConfig.getKeys(node);
/*     */     
/* 308 */     if (res == null || res.size() == 0) {
/* 309 */       res = this.config.getKeys(node);
/*     */     }
/* 311 */     if (res == null) {
/* 312 */       res = new ArrayList<String>();
/*     */     }
/*     */     
/* 315 */     return res;
/*     */   }
/*     */   
/*     */   private Object getProperty(String node) {
/* 319 */     Object res = this.parentConfig.getProperty(node);
/*     */     
/* 321 */     if (this.config.getProperty(node) != null) {
/* 322 */       res = this.config.getProperty(node);
/*     */     }
/*     */     
/* 325 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadConfiguration() {
/*     */     try {
/* 333 */       this.config.load();
/* 334 */     } catch (IOException e) {
/* 335 */       log.severe("Error reading configuration for world " + this.worldName + ": ");
/* 336 */       e.printStackTrace();
/* 337 */     } catch (ParserException e) {
/* 338 */       log.severe("Error parsing configuration for world " + this.worldName + ". ");
/* 339 */       throw e;
/*     */     } 
/*     */     
/* 342 */     this.summaryOnStart = getBoolean("summary-on-start", true);
/* 343 */     this.opPermissions = getBoolean("op-permissions", true);
/*     */     
/* 345 */     this.buildPermissions = getBoolean("build-permission-nodes.enable", false);
/* 346 */     this.buildPermissionDenyMessage = CommandUtils.replaceColorMacros(
/* 347 */         getString("build-permission-nodes.deny-message", "&eSorry, but you are not permitted to do that here."));
/*     */     
/* 349 */     this.strictEntitySpawn = getBoolean("event-handling.block-entity-spawns-with-untraceable-cause", false);
/* 350 */     this.allowAllInteract = getTargetMatchers("event-handling.interaction-whitelist");
/* 351 */     this.blockUseAtFeet = getTargetMatchers("event-handling.emit-block-use-at-feet");
/*     */     
/* 353 */     this.itemDurability = getBoolean("protection.item-durability", true);
/* 354 */     this.removeInfiniteStacks = getBoolean("protection.remove-infinite-stacks", false);
/* 355 */     this.disableExpDrops = getBoolean("protection.disable-xp-orb-drops", false);
/* 356 */     this.disableObsidianGenerators = getBoolean("protection.disable-obsidian-generators", false);
/*     */     
/* 358 */     this.blockPotions = new HashSet<PotionEffectType>();
/* 359 */     for (String potionName : getStringList("gameplay.block-potions", null)) {
/* 360 */       PotionEffectType effect = PotionEffectType.getByName(potionName);
/*     */       
/* 362 */       if (effect == null) {
/* 363 */         log.warning("Unknown potion effect type '" + potionName + "'"); continue;
/*     */       } 
/* 365 */       this.blockPotions.add(effect);
/*     */     } 
/*     */     
/* 368 */     this.blockPotionsAlways = getBoolean("gameplay.block-potions-overly-reliably", false);
/*     */     
/* 370 */     this.simulateSponge = getBoolean("simulation.sponge.enable", false);
/* 371 */     this.spongeRadius = Math.max(1, getInt("simulation.sponge.radius", 3)) - 1;
/* 372 */     this.redstoneSponges = getBoolean("simulation.sponge.redstone", false);
/*     */     
/* 374 */     this.pumpkinScuba = getBoolean("default.pumpkin-scuba", false);
/* 375 */     this.disableHealthRegain = getBoolean("default.disable-health-regain", false);
/*     */     
/* 377 */     this.noPhysicsGravel = getBoolean("physics.no-physics-gravel", false);
/* 378 */     this.noPhysicsSand = getBoolean("physics.no-physics-sand", false);
/* 379 */     this.ropeLadders = getBoolean("physics.vine-like-rope-ladders", false);
/* 380 */     this.allowPortalAnywhere = getBoolean("physics.allow-portal-anywhere", false);
/* 381 */     this.preventWaterDamage = new HashSet<Integer>(getIntList("physics.disable-water-damage-blocks", null));
/*     */     
/* 383 */     this.blockTNTExplosions = getBoolean("ignition.block-tnt", false);
/* 384 */     this.blockTNTBlockDamage = getBoolean("ignition.block-tnt-block-damage", false);
/* 385 */     this.blockLighter = getBoolean("ignition.block-lighter", false);
/*     */     
/* 387 */     this.preventLavaFire = getBoolean("fire.disable-lava-fire-spread", true);
/* 388 */     this.disableFireSpread = getBoolean("fire.disable-all-fire-spread", false);
/* 389 */     this.disableFireSpreadBlocks = new HashSet<Integer>(getIntList("fire.disable-fire-spread-blocks", null));
/* 390 */     this.allowedLavaSpreadOver = new HashSet<Integer>(getIntList("fire.lava-spread-blocks", null));
/*     */     
/* 392 */     this.blockCreeperExplosions = getBoolean("mobs.block-creeper-explosions", false);
/* 393 */     this.blockCreeperBlockDamage = getBoolean("mobs.block-creeper-block-damage", false);
/* 394 */     this.blockWitherExplosions = getBoolean("mobs.block-wither-explosions", false);
/* 395 */     this.blockWitherBlockDamage = getBoolean("mobs.block-wither-block-damage", false);
/* 396 */     this.blockWitherSkullExplosions = getBoolean("mobs.block-wither-skull-explosions", false);
/* 397 */     this.blockWitherSkullBlockDamage = getBoolean("mobs.block-wither-skull-block-damage", false);
/* 398 */     this.blockEnderDragonBlockDamage = getBoolean("mobs.block-enderdragon-block-damage", false);
/* 399 */     this.blockEnderDragonPortalCreation = getBoolean("mobs.block-enderdragon-portal-creation", false);
/* 400 */     this.blockFireballExplosions = getBoolean("mobs.block-fireball-explosions", false);
/* 401 */     this.blockFireballBlockDamage = getBoolean("mobs.block-fireball-block-damage", false);
/* 402 */     this.antiWolfDumbness = getBoolean("mobs.anti-wolf-dumbness", false);
/* 403 */     this.allowTamedSpawns = getBoolean("mobs.allow-tamed-spawns", true);
/* 404 */     this.disableEndermanGriefing = getBoolean("mobs.disable-enderman-griefing", false);
/* 405 */     this.disableSnowmanTrails = getBoolean("mobs.disable-snowman-trails", false);
/* 406 */     this.blockEntityPaintingDestroy = getBoolean("mobs.block-painting-destroy", false);
/* 407 */     this.blockEntityItemFrameDestroy = getBoolean("mobs.block-item-frame-destroy", false);
/* 408 */     this.blockPluginSpawning = getBoolean("mobs.block-plugin-spawning", true);
/* 409 */     this.blockGroundSlimes = getBoolean("mobs.block-above-ground-slimes", false);
/* 410 */     this.blockOtherExplosions = getBoolean("mobs.block-other-explosions", false);
/* 411 */     this.blockZombieDoorDestruction = getBoolean("mobs.block-zombie-door-destruction", false);
/*     */     
/* 413 */     this.disableFallDamage = getBoolean("player-damage.disable-fall-damage", false);
/* 414 */     this.disableLavaDamage = getBoolean("player-damage.disable-lava-damage", false);
/* 415 */     this.disableFireDamage = getBoolean("player-damage.disable-fire-damage", false);
/* 416 */     this.disableLightningDamage = getBoolean("player-damage.disable-lightning-damage", false);
/* 417 */     this.disableDrowningDamage = getBoolean("player-damage.disable-drowning-damage", false);
/* 418 */     this.disableSuffocationDamage = getBoolean("player-damage.disable-suffocation-damage", false);
/* 419 */     this.disableContactDamage = getBoolean("player-damage.disable-contact-damage", false);
/* 420 */     this.teleportOnSuffocation = getBoolean("player-damage.teleport-on-suffocation", false);
/* 421 */     this.disableVoidDamage = getBoolean("player-damage.disable-void-damage", false);
/* 422 */     this.teleportOnVoid = getBoolean("player-damage.teleport-on-void-falling", false);
/* 423 */     this.disableExplosionDamage = getBoolean("player-damage.disable-explosion-damage", false);
/* 424 */     this.disableMobDamage = getBoolean("player-damage.disable-mob-damage", false);
/* 425 */     this.disableDeathMessages = getBoolean("player-damage.disable-death-messages", false);
/*     */     
/* 427 */     this.signChestProtection = getBoolean("chest-protection.enable", false);
/* 428 */     this.disableSignChestProtectionCheck = getBoolean("chest-protection.disable-off-check", false);
/*     */     
/* 430 */     this.disableCreatureCropTrampling = getBoolean("crops.disable-creature-trampling", false);
/* 431 */     this.disablePlayerCropTrampling = getBoolean("crops.disable-player-trampling", false);
/*     */     
/* 433 */     this.disallowedLightningBlocks = new HashSet<Integer>(getIntList("weather.prevent-lightning-strike-blocks", null));
/* 434 */     this.preventLightningFire = getBoolean("weather.disable-lightning-strike-fire", false);
/* 435 */     this.disableThunder = getBoolean("weather.disable-thunderstorm", false);
/* 436 */     this.disableWeather = getBoolean("weather.disable-weather", false);
/* 437 */     this.disablePigZap = getBoolean("weather.disable-pig-zombification", false);
/* 438 */     this.disableCreeperPower = getBoolean("weather.disable-powered-creepers", false);
/* 439 */     this.alwaysRaining = getBoolean("weather.always-raining", false);
/* 440 */     this.alwaysThundering = getBoolean("weather.always-thundering", false);
/*     */     
/* 442 */     this.disableMushroomSpread = getBoolean("dynamics.disable-mushroom-spread", false);
/* 443 */     this.disableIceMelting = getBoolean("dynamics.disable-ice-melting", false);
/* 444 */     this.disableSnowMelting = getBoolean("dynamics.disable-snow-melting", false);
/* 445 */     this.disableSnowFormation = getBoolean("dynamics.disable-snow-formation", false);
/* 446 */     this.disableIceFormation = getBoolean("dynamics.disable-ice-formation", false);
/* 447 */     this.disableLeafDecay = getBoolean("dynamics.disable-leaf-decay", false);
/* 448 */     this.disableGrassGrowth = getBoolean("dynamics.disable-grass-growth", false);
/* 449 */     this.disableMyceliumSpread = getBoolean("dynamics.disable-mycelium-spread", false);
/* 450 */     this.disableVineGrowth = getBoolean("dynamics.disable-vine-growth", false);
/* 451 */     this.disableSoilDehydration = getBoolean("dynamics.disable-soil-dehydration", false);
/* 452 */     this.allowedSnowFallOver = new HashSet<Integer>(getIntList("dynamics.snow-fall-blocks", null));
/*     */     
/* 454 */     this.useRegions = getBoolean("regions.enable", true);
/* 455 */     this.regionInvinciblityRemovesMobs = getBoolean("regions.invincibility-removes-mobs", false);
/* 456 */     this.fakePlayerBuildOverride = getBoolean("regions.fake-player-build-override", true);
/* 457 */     this.explosionFlagCancellation = getBoolean("regions.explosion-flags-block-entity-damage", true);
/* 458 */     this.highFreqFlags = getBoolean("regions.high-frequency-flags", false);
/* 459 */     this.checkLiquidFlow = getBoolean("regions.protect-against-liquid-flow", false);
/* 460 */     this.regionWand = getInt("regions.wand", 334);
/* 461 */     this.maxClaimVolume = getInt("regions.max-claim-volume", 30000);
/* 462 */     this.claimOnlyInsideExistingRegions = getBoolean("regions.claim-only-inside-existing-regions", false);
/*     */     
/* 464 */     this.maxRegionCountPerPlayer = getInt("regions.max-region-count-per-player.default", 7);
/* 465 */     this.maxRegionCounts = new HashMap<String, Integer>();
/* 466 */     this.maxRegionCounts.put(null, Integer.valueOf(this.maxRegionCountPerPlayer));
/*     */     
/* 468 */     for (String key : getKeys("regions.max-region-count-per-player")) {
/* 469 */       if (!key.equalsIgnoreCase("default")) {
/* 470 */         Object val = getProperty("regions.max-region-count-per-player." + key);
/* 471 */         if (val != null && val instanceof Number) {
/* 472 */           this.maxRegionCounts.put(key, Integer.valueOf(((Number)val).intValue()));
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 481 */     this.blockCreatureSpawn = new HashSet<EntityType>();
/* 482 */     for (String creatureName : getStringList("mobs.block-creature-spawn", null)) {
/* 483 */       EntityType creature = EntityType.fromName(creatureName);
/*     */       
/* 485 */       if (creature == null) {
/* 486 */         log.warning("Unknown mob type '" + creatureName + "'"); continue;
/* 487 */       }  if (!creature.isAlive()) {
/* 488 */         log.warning("Entity type '" + creatureName + "' is not a creature"); continue;
/*     */       } 
/* 490 */       this.blockCreatureSpawn.add(creature);
/*     */     } 
/*     */ 
/*     */     
/* 494 */     boolean useBlacklistAsWhitelist = getBoolean("blacklist.use-as-whitelist", false);
/*     */ 
/*     */     
/* 497 */     boolean logConsole = getBoolean("blacklist.logging.console.enable", true);
/*     */ 
/*     */     
/* 500 */     boolean logDatabase = getBoolean("blacklist.logging.database.enable", false);
/* 501 */     String dsn = getString("blacklist.logging.database.dsn", "jdbc:mysql://localhost:3306/minecraft");
/* 502 */     String user = getString("blacklist.logging.database.user", "root");
/* 503 */     String pass = getString("blacklist.logging.database.pass", "");
/* 504 */     String table = getString("blacklist.logging.database.table", "blacklist_events");
/*     */ 
/*     */     
/* 507 */     boolean logFile = getBoolean("blacklist.logging.file.enable", false);
/* 508 */     String logFilePattern = getString("blacklist.logging.file.path", "worldguard/logs/%Y-%m-%d.log");
/* 509 */     int logFileCacheSize = Math.max(1, getInt("blacklist.logging.file.open-files", 10));
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 514 */       if (this.blacklist != null) {
/* 515 */         this.blacklist.getLogger().close();
/*     */       }
/*     */ 
/*     */       
/* 519 */       BukkitBlacklist bukkitBlacklist = new BukkitBlacklist(Boolean.valueOf(useBlacklistAsWhitelist), this.plugin);
/* 520 */       bukkitBlacklist.load(this.blacklistFile);
/*     */ 
/*     */ 
/*     */       
/* 524 */       if (bukkitBlacklist.isEmpty()) {
/* 525 */         this.blacklist = null;
/*     */       } else {
/* 527 */         this.blacklist = (Blacklist)bukkitBlacklist;
/* 528 */         if (this.summaryOnStart) {
/* 529 */           log.log(Level.INFO, "Blacklist loaded.");
/*     */         }
/*     */         
/* 532 */         BlacklistLoggerHandler blacklistLogger = bukkitBlacklist.getLogger();
/*     */         
/* 534 */         if (logDatabase) {
/* 535 */           blacklistLogger.addHandler((LoggerHandler)new DatabaseHandler(dsn, user, pass, table, this.worldName, log));
/*     */         }
/*     */         
/* 538 */         if (logConsole) {
/* 539 */           blacklistLogger.addHandler((LoggerHandler)new ConsoleHandler(this.worldName, log));
/*     */         }
/*     */         
/* 542 */         if (logFile) {
/* 543 */           FileHandler handler = new FileHandler(logFilePattern, logFileCacheSize, this.worldName, log);
/*     */           
/* 545 */           blacklistLogger.addHandler((LoggerHandler)handler);
/*     */         } 
/*     */       } 
/* 548 */     } catch (FileNotFoundException e) {
/* 549 */       log.log(Level.WARNING, "WorldGuard blacklist does not exist.");
/* 550 */     } catch (IOException e) {
/* 551 */       log.log(Level.WARNING, "Could not load WorldGuard blacklist: " + e
/* 552 */           .getMessage());
/*     */     } 
/*     */ 
/*     */     
/* 556 */     if (this.summaryOnStart) {
/* 557 */       log.log(Level.INFO, this.blockTNTExplosions ? ("(" + this.worldName + ") TNT ignition is blocked.") : ("(" + this.worldName + ") TNT ignition is PERMITTED."));
/*     */ 
/*     */       
/* 560 */       log.log(Level.INFO, this.blockLighter ? ("(" + this.worldName + ") Lighters are blocked.") : ("(" + this.worldName + ") Lighters are PERMITTED."));
/*     */ 
/*     */       
/* 563 */       log.log(Level.INFO, this.preventLavaFire ? ("(" + this.worldName + ") Lava fire is blocked.") : ("(" + this.worldName + ") Lava fire is PERMITTED."));
/*     */ 
/*     */ 
/*     */       
/* 567 */       if (this.disableFireSpread) {
/* 568 */         log.log(Level.INFO, "(" + this.worldName + ") All fire spread is disabled.");
/*     */       }
/* 570 */       else if (this.disableFireSpreadBlocks.size() > 0) {
/* 571 */         log.log(Level.INFO, "(" + this.worldName + ") Fire spread is limited to " + this.disableFireSpreadBlocks
/*     */             
/* 573 */             .size() + " block types.");
/*     */       } else {
/* 575 */         log.log(Level.INFO, "(" + this.worldName + ") Fire spread is UNRESTRICTED.");
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 581 */     this.config.setHeader("#\r\n# WorldGuard's world configuration file\r\n#\r\n# This is a world configuration file. Anything placed into here will only\r\n# affect this world. If you don't put anything in this file, then the\r\n# settings will be inherited from the main configuration file.\r\n#\r\n# If you see {} below, that means that there are NO entries in this file.\r\n# Remove the {} and add your own entries.\r\n#\r\n");
/*     */     
/* 583 */     this.config.save();
/*     */   }
/*     */   
/*     */   public Blacklist getBlacklist() {
/* 587 */     return this.blacklist;
/*     */   }
/*     */   
/*     */   public String getWorldName() {
/* 591 */     return this.worldName;
/*     */   }
/*     */   
/*     */   public boolean isChestProtected(Block block, Player player) {
/* 595 */     if (!this.signChestProtection) {
/* 596 */       return false;
/*     */     }
/* 598 */     if (this.plugin.hasPermission((CommandSender)player, "worldguard.chest-protection.override") || this.plugin
/* 599 */       .hasPermission((CommandSender)player, "worldguard.override.chest-protection")) {
/* 600 */       return false;
/*     */     }
/* 602 */     return this.chestProtection.isProtected(block, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isChestProtected(Block block) {
/* 607 */     return (this.signChestProtection && this.chestProtection.isProtected(block, null));
/*     */   }
/*     */   
/*     */   public boolean isChestProtectedPlacement(Block block, Player player) {
/* 611 */     if (!this.signChestProtection) {
/* 612 */       return false;
/*     */     }
/* 614 */     if (this.plugin.hasPermission((CommandSender)player, "worldguard.chest-protection.override") || this.plugin
/* 615 */       .hasPermission((CommandSender)player, "worldguard.override.chest-protection")) {
/* 616 */       return false;
/*     */     }
/* 618 */     return this.chestProtection.isProtectedPlacement(block, player);
/*     */   }
/*     */   
/*     */   public boolean isAdjacentChestProtected(Block block, Player player) {
/* 622 */     if (!this.signChestProtection) {
/* 623 */       return false;
/*     */     }
/* 625 */     if (this.plugin.hasPermission((CommandSender)player, "worldguard.chest-protection.override") || this.plugin
/* 626 */       .hasPermission((CommandSender)player, "worldguard.override.chest-protection")) {
/* 627 */       return false;
/*     */     }
/* 629 */     return this.chestProtection.isAdjacentChestProtected(block, player);
/*     */   }
/*     */   
/*     */   public ChestProtection getChestProtection() {
/* 633 */     return this.chestProtection;
/*     */   }
/*     */   
/*     */   public int getMaxRegionCount(Player player) {
/* 637 */     int max = -1;
/* 638 */     for (String group : this.plugin.getGroups(player)) {
/* 639 */       if (this.maxRegionCounts.containsKey(group)) {
/* 640 */         int groupMax = ((Integer)this.maxRegionCounts.get(group)).intValue();
/* 641 */         if (max < groupMax) {
/* 642 */           max = groupMax;
/*     */         }
/*     */       } 
/*     */     } 
/* 646 */     if (max <= -1) {
/* 647 */       max = this.maxRegionCountPerPlayer;
/*     */     }
/* 649 */     return max;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\WorldConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */