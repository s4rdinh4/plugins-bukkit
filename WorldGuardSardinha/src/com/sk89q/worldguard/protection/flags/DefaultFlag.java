/*     */ package com.sk89q.worldguard.protection.flags;
/*     */ 
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.entity.EntityType;
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
/*     */ public final class DefaultFlag
/*     */ {
/*  32 */   public static final StateFlag PASSTHROUGH = new StateFlag("passthrough", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  39 */   public static final RegionGroupFlag CONSTRUCT = new RegionGroupFlag("construct", RegionGroup.MEMBERS);
/*     */ 
/*     */   
/*  42 */   public static final StateFlag BUILD = new BuildFlag("build", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   public static final StateFlag BLOCK_BREAK = new StateFlag("block-break", false);
/*  48 */   public static final StateFlag BLOCK_PLACE = new StateFlag("block-place", false);
/*  49 */   public static final StateFlag USE = new StateFlag("use", false);
/*  50 */   public static final StateFlag INTERACT = new StateFlag("interact", false);
/*  51 */   public static final StateFlag PVP = new StateFlag("pvp", false);
/*  52 */   public static final StateFlag SLEEP = new StateFlag("sleep", false);
/*  53 */   public static final StateFlag TNT = new StateFlag("tnt", false);
/*  54 */   public static final StateFlag CHEST_ACCESS = new StateFlag("chest-access", false);
/*  55 */   public static final StateFlag PLACE_VEHICLE = new StateFlag("vehicle-place", false);
/*  56 */   public static final StateFlag DESTROY_VEHICLE = new StateFlag("vehicle-destroy", false);
/*  57 */   public static final StateFlag LIGHTER = new StateFlag("lighter", false);
/*  58 */   public static final StateFlag RIDE = new StateFlag("ride", false);
/*  59 */   public static final StateFlag POTION_SPLASH = new StateFlag("potion-splash", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   public static final StateFlag ITEM_PICKUP = new StateFlag("item-pickup", true);
/*  67 */   public static final StateFlag ITEM_DROP = new StateFlag("item-drop", true);
/*  68 */   public static final StateFlag EXP_DROPS = new StateFlag("exp-drops", true);
/*     */ 
/*     */ 
/*     */   
/*  72 */   public static final StateFlag MOB_DAMAGE = new StateFlag("mob-damage", true);
/*  73 */   public static final StateFlag MOB_SPAWNING = new StateFlag("mob-spawning", true);
/*  74 */   public static final StateFlag CREEPER_EXPLOSION = new StateFlag("creeper-explosion", true);
/*  75 */   public static final StateFlag ENDERDRAGON_BLOCK_DAMAGE = new StateFlag("enderdragon-block-damage", true);
/*  76 */   public static final StateFlag GHAST_FIREBALL = new StateFlag("ghast-fireball", true);
/*  77 */   public static final StateFlag OTHER_EXPLOSION = new StateFlag("other-explosion", true);
/*  78 */   public static final StateFlag FIRE_SPREAD = new StateFlag("fire-spread", true);
/*  79 */   public static final StateFlag LAVA_FIRE = new StateFlag("lava-fire", true);
/*  80 */   public static final StateFlag LIGHTNING = new StateFlag("lightning", true);
/*  81 */   public static final StateFlag WATER_FLOW = new StateFlag("water-flow", true);
/*  82 */   public static final StateFlag LAVA_FLOW = new StateFlag("lava-flow", true);
/*  83 */   public static final StateFlag PISTONS = new StateFlag("pistons", true);
/*  84 */   public static final StateFlag SNOW_FALL = new StateFlag("snow-fall", true);
/*  85 */   public static final StateFlag SNOW_MELT = new StateFlag("snow-melt", true);
/*  86 */   public static final StateFlag ICE_FORM = new StateFlag("ice-form", true);
/*  87 */   public static final StateFlag ICE_MELT = new StateFlag("ice-melt", true);
/*  88 */   public static final StateFlag MUSHROOMS = new StateFlag("mushroom-growth", true);
/*  89 */   public static final StateFlag LEAF_DECAY = new StateFlag("leaf-decay", true);
/*  90 */   public static final StateFlag GRASS_SPREAD = new StateFlag("grass-growth", true);
/*  91 */   public static final StateFlag MYCELIUM_SPREAD = new StateFlag("mycelium-spread", true);
/*  92 */   public static final StateFlag VINE_GROWTH = new StateFlag("vine-growth", true);
/*  93 */   public static final StateFlag SOIL_DRY = new StateFlag("soil-dry", true);
/*  94 */   public static final StateFlag ENDER_BUILD = new StateFlag("enderman-grief", true);
/*  95 */   public static final StateFlag INVINCIBILITY = new StateFlag("invincible", false);
/*  96 */   public static final StateFlag SEND_CHAT = new StateFlag("send-chat", true);
/*  97 */   public static final StateFlag RECEIVE_CHAT = new StateFlag("receive-chat", true);
/*  98 */   public static final StateFlag ENTRY = new StateFlag("entry", true, RegionGroup.NON_MEMBERS);
/*  99 */   public static final StateFlag EXIT = new StateFlag("exit", true, RegionGroup.NON_MEMBERS);
/* 100 */   public static final StateFlag ENDERPEARL = new StateFlag("enderpearl", true);
/* 101 */   public static final StateFlag ENTITY_PAINTING_DESTROY = new StateFlag("entity-painting-destroy", true);
/* 102 */   public static final StateFlag ENTITY_ITEM_FRAME_DESTROY = new StateFlag("entity-item-frame-destroy", true);
/*     */ 
/*     */   
/* 105 */   public static final StringFlag DENY_MESSAGE = new StringFlag("deny-message", "" + ChatColor.RED + ChatColor.BOLD + "Hey!" + ChatColor.GRAY + " Sorry, but you can't %what% here.");
/*     */   
/* 107 */   public static final StringFlag ENTRY_DENY_MESSAGE = new StringFlag("entry-deny-message", "" + ChatColor.RED + ChatColor.BOLD + "Hey!" + ChatColor.GRAY + " You are not permitted to enter this area.");
/*     */   
/* 109 */   public static final StringFlag EXIT_DENY_MESSAGE = new StringFlag("exit-deny-message", "" + ChatColor.RED + ChatColor.BOLD + "Hey!" + ChatColor.GRAY + " You are not permitted to leave this area.");
/*     */   
/* 111 */   public static final BooleanFlag EXIT_OVERRIDE = new BooleanFlag("exit-override");
/* 112 */   public static final StringFlag GREET_MESSAGE = new StringFlag("greeting");
/* 113 */   public static final StringFlag FAREWELL_MESSAGE = new StringFlag("farewell");
/* 114 */   public static final BooleanFlag NOTIFY_ENTER = new BooleanFlag("notify-enter");
/* 115 */   public static final BooleanFlag NOTIFY_LEAVE = new BooleanFlag("notify-leave");
/* 116 */   public static final SetFlag<EntityType> DENY_SPAWN = new SetFlag<EntityType>("deny-spawn", new EntityTypeFlag(null));
/* 117 */   public static final EnumFlag<GameMode> GAME_MODE = new EnumFlag<GameMode>("game-mode", GameMode.class);
/* 118 */   public static final IntegerFlag HEAL_DELAY = new IntegerFlag("heal-delay");
/* 119 */   public static final IntegerFlag HEAL_AMOUNT = new IntegerFlag("heal-amount");
/* 120 */   public static final DoubleFlag MIN_HEAL = new DoubleFlag("heal-min-health");
/* 121 */   public static final DoubleFlag MAX_HEAL = new DoubleFlag("heal-max-health");
/* 122 */   public static final IntegerFlag FEED_DELAY = new IntegerFlag("feed-delay");
/* 123 */   public static final IntegerFlag FEED_AMOUNT = new IntegerFlag("feed-amount");
/* 124 */   public static final IntegerFlag MIN_FOOD = new IntegerFlag("feed-min-hunger");
/* 125 */   public static final IntegerFlag MAX_FOOD = new IntegerFlag("feed-max-hunger");
/*     */ 
/*     */   
/* 128 */   public static final LocationFlag TELE_LOC = new LocationFlag("teleport", RegionGroup.MEMBERS);
/* 129 */   public static final LocationFlag SPAWN_LOC = new LocationFlag("spawn", RegionGroup.MEMBERS);
/* 130 */   public static final StateFlag ENABLE_SHOP = new StateFlag("allow-shop", false);
/* 131 */   public static final BooleanFlag BUYABLE = new BooleanFlag("buyable");
/* 132 */   public static final DoubleFlag PRICE = new DoubleFlag("price");
/* 133 */   public static final SetFlag<String> BLOCKED_CMDS = new SetFlag<String>("blocked-cmds", new CommandStringFlag(null));
/* 134 */   public static final SetFlag<String> ALLOWED_CMDS = new SetFlag<String>("allowed-cmds", new CommandStringFlag(null));
/*     */   
/* 136 */   public static final Flag<?>[] flagsList = new Flag[] { PASSTHROUGH, BUILD, CONSTRUCT, BLOCK_BREAK, BLOCK_PLACE, PVP, CHEST_ACCESS, PISTONS, TNT, LIGHTER, RIDE, USE, INTERACT, PLACE_VEHICLE, DESTROY_VEHICLE, SLEEP, MOB_DAMAGE, MOB_SPAWNING, DENY_SPAWN, INVINCIBILITY, EXP_DROPS, CREEPER_EXPLOSION, OTHER_EXPLOSION, ENDERDRAGON_BLOCK_DAMAGE, GHAST_FIREBALL, ENDER_BUILD, DENY_MESSAGE, ENTRY_DENY_MESSAGE, EXIT_DENY_MESSAGE, EXIT_OVERRIDE, GREET_MESSAGE, FAREWELL_MESSAGE, NOTIFY_ENTER, NOTIFY_LEAVE, EXIT, ENTRY, LIGHTNING, ENTITY_PAINTING_DESTROY, ENDERPEARL, ENTITY_ITEM_FRAME_DESTROY, ITEM_PICKUP, ITEM_DROP, HEAL_AMOUNT, HEAL_DELAY, MIN_HEAL, MAX_HEAL, FEED_DELAY, FEED_AMOUNT, MIN_FOOD, MAX_FOOD, SNOW_FALL, SNOW_MELT, ICE_FORM, ICE_MELT, SOIL_DRY, GAME_MODE, MUSHROOMS, LEAF_DECAY, GRASS_SPREAD, MYCELIUM_SPREAD, VINE_GROWTH, SEND_CHAT, RECEIVE_CHAT, FIRE_SPREAD, LAVA_FIRE, LAVA_FLOW, WATER_FLOW, TELE_LOC, SPAWN_LOC, POTION_SPLASH, BLOCKED_CMDS, ALLOWED_CMDS, PRICE, BUYABLE, ENABLE_SHOP };
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
/*     */   public static Flag<?>[] getFlags() {
/* 158 */     return flagsList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Flag<?> fuzzyMatchFlag(String id) {
/* 168 */     for (Flag<?> flag : getFlags()) {
/* 169 */       if (flag.getName().replace("-", "").equalsIgnoreCase(id.replace("-", ""))) {
/* 170 */         return flag;
/*     */       }
/*     */     } 
/*     */     
/* 174 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\flags\DefaultFlag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */