/*     */ package com.sk89q.worldguard.bukkit.util;
/*     */ 
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.collect.HashBiMap;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bukkit.DyeColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.material.Dye;
/*     */ import org.bukkit.material.MaterialData;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
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
/*     */ public final class Materials
/*     */ {
/*     */   private static final int MODIFIED_ON_RIGHT = 1;
/*     */   private static final int MODIFIED_ON_LEFT = 2;
/*     */   private static final int MODIFIES_BLOCKS = 4;
/*  45 */   private static final BiMap<EntityType, Material> ENTITY_ITEMS = (BiMap<EntityType, Material>)HashBiMap.create();
/*  46 */   private static final Map<Material, Integer> MATERIAL_FLAGS = new HashMap<Material, Integer>();
/*  47 */   private static final Set<PotionEffectType> DAMAGE_EFFECTS = new HashSet<PotionEffectType>();
/*     */   
/*     */   static {
/*  50 */     ENTITY_ITEMS.put(EntityType.PAINTING, Material.PAINTING);
/*  51 */     ENTITY_ITEMS.put(EntityType.ARROW, Material.ARROW);
/*  52 */     ENTITY_ITEMS.put(EntityType.SNOWBALL, Material.SNOW_BALL);
/*  53 */     ENTITY_ITEMS.put(EntityType.FIREBALL, Material.FIREBALL);
/*  54 */     ENTITY_ITEMS.put(EntityType.SMALL_FIREBALL, Material.FIREWORK_CHARGE);
/*  55 */     ENTITY_ITEMS.put(EntityType.ENDER_PEARL, Material.ENDER_PEARL);
/*  56 */     ENTITY_ITEMS.put(EntityType.THROWN_EXP_BOTTLE, Material.EXP_BOTTLE);
/*  57 */     ENTITY_ITEMS.put(EntityType.ITEM_FRAME, Material.ITEM_FRAME);
/*  58 */     ENTITY_ITEMS.put(EntityType.PRIMED_TNT, Material.TNT);
/*  59 */     ENTITY_ITEMS.put(EntityType.FIREWORK, Material.FIREWORK);
/*  60 */     ENTITY_ITEMS.put(EntityType.MINECART_COMMAND, Material.COMMAND_MINECART);
/*  61 */     ENTITY_ITEMS.put(EntityType.BOAT, Material.BOAT);
/*  62 */     ENTITY_ITEMS.put(EntityType.MINECART, Material.MINECART);
/*  63 */     ENTITY_ITEMS.put(EntityType.MINECART_CHEST, Material.STORAGE_MINECART);
/*  64 */     ENTITY_ITEMS.put(EntityType.MINECART_FURNACE, Material.POWERED_MINECART);
/*  65 */     ENTITY_ITEMS.put(EntityType.MINECART_TNT, Material.EXPLOSIVE_MINECART);
/*  66 */     ENTITY_ITEMS.put(EntityType.MINECART_HOPPER, Material.HOPPER_MINECART);
/*  67 */     ENTITY_ITEMS.put(EntityType.SPLASH_POTION, Material.POTION);
/*  68 */     ENTITY_ITEMS.put(EntityType.EGG, Material.EGG);
/*     */     
/*  70 */     MATERIAL_FLAGS.put(Material.AIR, Integer.valueOf(0));
/*  71 */     MATERIAL_FLAGS.put(Material.STONE, Integer.valueOf(0));
/*  72 */     MATERIAL_FLAGS.put(Material.GRASS, Integer.valueOf(0));
/*  73 */     MATERIAL_FLAGS.put(Material.DIRT, Integer.valueOf(0));
/*  74 */     MATERIAL_FLAGS.put(Material.COBBLESTONE, Integer.valueOf(0));
/*  75 */     MATERIAL_FLAGS.put(Material.WOOD, Integer.valueOf(0));
/*  76 */     MATERIAL_FLAGS.put(Material.SAPLING, Integer.valueOf(0));
/*  77 */     MATERIAL_FLAGS.put(Material.BEDROCK, Integer.valueOf(0));
/*  78 */     MATERIAL_FLAGS.put(Material.WATER, Integer.valueOf(0));
/*  79 */     MATERIAL_FLAGS.put(Material.STATIONARY_WATER, Integer.valueOf(0));
/*  80 */     MATERIAL_FLAGS.put(Material.LAVA, Integer.valueOf(0));
/*  81 */     MATERIAL_FLAGS.put(Material.STATIONARY_LAVA, Integer.valueOf(0));
/*  82 */     MATERIAL_FLAGS.put(Material.SAND, Integer.valueOf(0));
/*  83 */     MATERIAL_FLAGS.put(Material.GRAVEL, Integer.valueOf(0));
/*  84 */     MATERIAL_FLAGS.put(Material.GOLD_ORE, Integer.valueOf(0));
/*  85 */     MATERIAL_FLAGS.put(Material.IRON_ORE, Integer.valueOf(0));
/*  86 */     MATERIAL_FLAGS.put(Material.COAL_ORE, Integer.valueOf(0));
/*  87 */     MATERIAL_FLAGS.put(Material.LOG, Integer.valueOf(0));
/*  88 */     MATERIAL_FLAGS.put(Material.LEAVES, Integer.valueOf(0));
/*  89 */     MATERIAL_FLAGS.put(Material.SPONGE, Integer.valueOf(0));
/*  90 */     MATERIAL_FLAGS.put(Material.GLASS, Integer.valueOf(0));
/*  91 */     MATERIAL_FLAGS.put(Material.LAPIS_ORE, Integer.valueOf(0));
/*  92 */     MATERIAL_FLAGS.put(Material.LAPIS_BLOCK, Integer.valueOf(0));
/*  93 */     MATERIAL_FLAGS.put(Material.DISPENSER, Integer.valueOf(1));
/*  94 */     MATERIAL_FLAGS.put(Material.SANDSTONE, Integer.valueOf(0));
/*  95 */     MATERIAL_FLAGS.put(Material.NOTE_BLOCK, Integer.valueOf(1));
/*  96 */     MATERIAL_FLAGS.put(Material.BED_BLOCK, Integer.valueOf(1));
/*  97 */     MATERIAL_FLAGS.put(Material.POWERED_RAIL, Integer.valueOf(0));
/*  98 */     MATERIAL_FLAGS.put(Material.DETECTOR_RAIL, Integer.valueOf(0));
/*  99 */     MATERIAL_FLAGS.put(Material.PISTON_STICKY_BASE, Integer.valueOf(0));
/* 100 */     MATERIAL_FLAGS.put(Material.WEB, Integer.valueOf(0));
/* 101 */     MATERIAL_FLAGS.put(Material.LONG_GRASS, Integer.valueOf(0));
/* 102 */     MATERIAL_FLAGS.put(Material.DEAD_BUSH, Integer.valueOf(0));
/* 103 */     MATERIAL_FLAGS.put(Material.PISTON_BASE, Integer.valueOf(0));
/* 104 */     MATERIAL_FLAGS.put(Material.PISTON_EXTENSION, Integer.valueOf(0));
/* 105 */     MATERIAL_FLAGS.put(Material.WOOL, Integer.valueOf(0));
/* 106 */     MATERIAL_FLAGS.put(Material.PISTON_MOVING_PIECE, Integer.valueOf(0));
/* 107 */     MATERIAL_FLAGS.put(Material.YELLOW_FLOWER, Integer.valueOf(0));
/* 108 */     MATERIAL_FLAGS.put(Material.RED_ROSE, Integer.valueOf(0));
/* 109 */     MATERIAL_FLAGS.put(Material.BROWN_MUSHROOM, Integer.valueOf(0));
/* 110 */     MATERIAL_FLAGS.put(Material.RED_MUSHROOM, Integer.valueOf(0));
/* 111 */     MATERIAL_FLAGS.put(Material.GOLD_BLOCK, Integer.valueOf(0));
/* 112 */     MATERIAL_FLAGS.put(Material.IRON_BLOCK, Integer.valueOf(0));
/* 113 */     MATERIAL_FLAGS.put(Material.DOUBLE_STEP, Integer.valueOf(0));
/* 114 */     MATERIAL_FLAGS.put(Material.STEP, Integer.valueOf(0));
/* 115 */     MATERIAL_FLAGS.put(Material.BRICK, Integer.valueOf(0));
/* 116 */     MATERIAL_FLAGS.put(Material.TNT, Integer.valueOf(1));
/* 117 */     MATERIAL_FLAGS.put(Material.BOOKSHELF, Integer.valueOf(0));
/* 118 */     MATERIAL_FLAGS.put(Material.MOSSY_COBBLESTONE, Integer.valueOf(0));
/* 119 */     MATERIAL_FLAGS.put(Material.OBSIDIAN, Integer.valueOf(0));
/* 120 */     MATERIAL_FLAGS.put(Material.TORCH, Integer.valueOf(0));
/* 121 */     MATERIAL_FLAGS.put(Material.FIRE, Integer.valueOf(0));
/* 122 */     MATERIAL_FLAGS.put(Material.MOB_SPAWNER, Integer.valueOf(0));
/* 123 */     MATERIAL_FLAGS.put(Material.WOOD_STAIRS, Integer.valueOf(0));
/* 124 */     MATERIAL_FLAGS.put(Material.CHEST, Integer.valueOf(1));
/* 125 */     MATERIAL_FLAGS.put(Material.REDSTONE_WIRE, Integer.valueOf(0));
/* 126 */     MATERIAL_FLAGS.put(Material.DIAMOND_ORE, Integer.valueOf(0));
/* 127 */     MATERIAL_FLAGS.put(Material.DIAMOND_BLOCK, Integer.valueOf(0));
/* 128 */     MATERIAL_FLAGS.put(Material.WORKBENCH, Integer.valueOf(1));
/* 129 */     MATERIAL_FLAGS.put(Material.CROPS, Integer.valueOf(0));
/* 130 */     MATERIAL_FLAGS.put(Material.SOIL, Integer.valueOf(0));
/* 131 */     MATERIAL_FLAGS.put(Material.FURNACE, Integer.valueOf(1));
/* 132 */     MATERIAL_FLAGS.put(Material.BURNING_FURNACE, Integer.valueOf(1));
/* 133 */     MATERIAL_FLAGS.put(Material.SIGN_POST, Integer.valueOf(0));
/* 134 */     MATERIAL_FLAGS.put(Material.WOODEN_DOOR, Integer.valueOf(1));
/* 135 */     MATERIAL_FLAGS.put(Material.LADDER, Integer.valueOf(0));
/* 136 */     MATERIAL_FLAGS.put(Material.RAILS, Integer.valueOf(0));
/* 137 */     MATERIAL_FLAGS.put(Material.COBBLESTONE_STAIRS, Integer.valueOf(0));
/* 138 */     MATERIAL_FLAGS.put(Material.WALL_SIGN, Integer.valueOf(0));
/* 139 */     MATERIAL_FLAGS.put(Material.LEVER, Integer.valueOf(1));
/* 140 */     MATERIAL_FLAGS.put(Material.STONE_PLATE, Integer.valueOf(0));
/* 141 */     MATERIAL_FLAGS.put(Material.IRON_DOOR_BLOCK, Integer.valueOf(0));
/* 142 */     MATERIAL_FLAGS.put(Material.WOOD_PLATE, Integer.valueOf(0));
/* 143 */     MATERIAL_FLAGS.put(Material.REDSTONE_ORE, Integer.valueOf(0));
/* 144 */     MATERIAL_FLAGS.put(Material.GLOWING_REDSTONE_ORE, Integer.valueOf(0));
/* 145 */     MATERIAL_FLAGS.put(Material.REDSTONE_TORCH_OFF, Integer.valueOf(0));
/* 146 */     MATERIAL_FLAGS.put(Material.REDSTONE_TORCH_ON, Integer.valueOf(0));
/* 147 */     MATERIAL_FLAGS.put(Material.STONE_BUTTON, Integer.valueOf(1));
/* 148 */     MATERIAL_FLAGS.put(Material.SNOW, Integer.valueOf(0));
/* 149 */     MATERIAL_FLAGS.put(Material.ICE, Integer.valueOf(0));
/* 150 */     MATERIAL_FLAGS.put(Material.SNOW_BLOCK, Integer.valueOf(0));
/* 151 */     MATERIAL_FLAGS.put(Material.CACTUS, Integer.valueOf(0));
/* 152 */     MATERIAL_FLAGS.put(Material.CLAY, Integer.valueOf(0));
/* 153 */     MATERIAL_FLAGS.put(Material.SUGAR_CANE_BLOCK, Integer.valueOf(0));
/* 154 */     MATERIAL_FLAGS.put(Material.JUKEBOX, Integer.valueOf(1));
/* 155 */     MATERIAL_FLAGS.put(Material.FENCE, Integer.valueOf(0));
/* 156 */     MATERIAL_FLAGS.put(Material.PUMPKIN, Integer.valueOf(0));
/* 157 */     MATERIAL_FLAGS.put(Material.NETHERRACK, Integer.valueOf(0));
/* 158 */     MATERIAL_FLAGS.put(Material.SOUL_SAND, Integer.valueOf(0));
/* 159 */     MATERIAL_FLAGS.put(Material.GLOWSTONE, Integer.valueOf(0));
/* 160 */     MATERIAL_FLAGS.put(Material.PORTAL, Integer.valueOf(0));
/* 161 */     MATERIAL_FLAGS.put(Material.JACK_O_LANTERN, Integer.valueOf(0));
/* 162 */     MATERIAL_FLAGS.put(Material.CAKE_BLOCK, Integer.valueOf(1));
/* 163 */     MATERIAL_FLAGS.put(Material.DIODE_BLOCK_OFF, Integer.valueOf(1));
/* 164 */     MATERIAL_FLAGS.put(Material.DIODE_BLOCK_ON, Integer.valueOf(1));
/* 165 */     MATERIAL_FLAGS.put(Material.STAINED_GLASS, Integer.valueOf(0));
/* 166 */     MATERIAL_FLAGS.put(Material.TRAP_DOOR, Integer.valueOf(1));
/* 167 */     MATERIAL_FLAGS.put(Material.MONSTER_EGGS, Integer.valueOf(0));
/* 168 */     MATERIAL_FLAGS.put(Material.SMOOTH_BRICK, Integer.valueOf(0));
/* 169 */     MATERIAL_FLAGS.put(Material.HUGE_MUSHROOM_1, Integer.valueOf(0));
/* 170 */     MATERIAL_FLAGS.put(Material.HUGE_MUSHROOM_2, Integer.valueOf(0));
/* 171 */     MATERIAL_FLAGS.put(Material.IRON_FENCE, Integer.valueOf(0));
/* 172 */     MATERIAL_FLAGS.put(Material.THIN_GLASS, Integer.valueOf(0));
/* 173 */     MATERIAL_FLAGS.put(Material.MELON_BLOCK, Integer.valueOf(0));
/* 174 */     MATERIAL_FLAGS.put(Material.PUMPKIN_STEM, Integer.valueOf(0));
/* 175 */     MATERIAL_FLAGS.put(Material.MELON_STEM, Integer.valueOf(0));
/* 176 */     MATERIAL_FLAGS.put(Material.VINE, Integer.valueOf(0));
/* 177 */     MATERIAL_FLAGS.put(Material.FENCE_GATE, Integer.valueOf(1));
/* 178 */     MATERIAL_FLAGS.put(Material.BRICK_STAIRS, Integer.valueOf(0));
/* 179 */     MATERIAL_FLAGS.put(Material.SMOOTH_STAIRS, Integer.valueOf(0));
/* 180 */     MATERIAL_FLAGS.put(Material.MYCEL, Integer.valueOf(0));
/* 181 */     MATERIAL_FLAGS.put(Material.WATER_LILY, Integer.valueOf(0));
/* 182 */     MATERIAL_FLAGS.put(Material.NETHER_BRICK, Integer.valueOf(0));
/* 183 */     MATERIAL_FLAGS.put(Material.NETHER_FENCE, Integer.valueOf(0));
/* 184 */     MATERIAL_FLAGS.put(Material.NETHER_BRICK_STAIRS, Integer.valueOf(0));
/* 185 */     MATERIAL_FLAGS.put(Material.NETHER_WARTS, Integer.valueOf(0));
/* 186 */     MATERIAL_FLAGS.put(Material.ENCHANTMENT_TABLE, Integer.valueOf(1));
/* 187 */     MATERIAL_FLAGS.put(Material.BREWING_STAND, Integer.valueOf(1));
/* 188 */     MATERIAL_FLAGS.put(Material.CAULDRON, Integer.valueOf(1));
/* 189 */     MATERIAL_FLAGS.put(Material.ENDER_PORTAL, Integer.valueOf(0));
/* 190 */     MATERIAL_FLAGS.put(Material.ENDER_PORTAL_FRAME, Integer.valueOf(0));
/* 191 */     MATERIAL_FLAGS.put(Material.ENDER_STONE, Integer.valueOf(0));
/* 192 */     MATERIAL_FLAGS.put(Material.DRAGON_EGG, Integer.valueOf(3));
/* 193 */     MATERIAL_FLAGS.put(Material.REDSTONE_LAMP_OFF, Integer.valueOf(0));
/* 194 */     MATERIAL_FLAGS.put(Material.REDSTONE_LAMP_ON, Integer.valueOf(0));
/* 195 */     MATERIAL_FLAGS.put(Material.WOOD_DOUBLE_STEP, Integer.valueOf(0));
/* 196 */     MATERIAL_FLAGS.put(Material.WOOD_STEP, Integer.valueOf(0));
/* 197 */     MATERIAL_FLAGS.put(Material.COCOA, Integer.valueOf(0));
/* 198 */     MATERIAL_FLAGS.put(Material.SANDSTONE_STAIRS, Integer.valueOf(0));
/* 199 */     MATERIAL_FLAGS.put(Material.EMERALD_ORE, Integer.valueOf(0));
/* 200 */     MATERIAL_FLAGS.put(Material.ENDER_CHEST, Integer.valueOf(1));
/* 201 */     MATERIAL_FLAGS.put(Material.TRIPWIRE_HOOK, Integer.valueOf(0));
/* 202 */     MATERIAL_FLAGS.put(Material.TRIPWIRE, Integer.valueOf(0));
/* 203 */     MATERIAL_FLAGS.put(Material.EMERALD_BLOCK, Integer.valueOf(0));
/* 204 */     MATERIAL_FLAGS.put(Material.SPRUCE_WOOD_STAIRS, Integer.valueOf(0));
/* 205 */     MATERIAL_FLAGS.put(Material.BIRCH_WOOD_STAIRS, Integer.valueOf(0));
/* 206 */     MATERIAL_FLAGS.put(Material.JUNGLE_WOOD_STAIRS, Integer.valueOf(0));
/* 207 */     MATERIAL_FLAGS.put(Material.COMMAND, Integer.valueOf(1));
/* 208 */     MATERIAL_FLAGS.put(Material.BEACON, Integer.valueOf(1));
/* 209 */     MATERIAL_FLAGS.put(Material.COBBLE_WALL, Integer.valueOf(0));
/* 210 */     MATERIAL_FLAGS.put(Material.FLOWER_POT, Integer.valueOf(1));
/* 211 */     MATERIAL_FLAGS.put(Material.CARROT, Integer.valueOf(0));
/* 212 */     MATERIAL_FLAGS.put(Material.POTATO, Integer.valueOf(0));
/* 213 */     MATERIAL_FLAGS.put(Material.WOOD_BUTTON, Integer.valueOf(1));
/* 214 */     MATERIAL_FLAGS.put(Material.SKULL, Integer.valueOf(0));
/* 215 */     MATERIAL_FLAGS.put(Material.ANVIL, Integer.valueOf(1));
/* 216 */     MATERIAL_FLAGS.put(Material.TRAPPED_CHEST, Integer.valueOf(1));
/* 217 */     MATERIAL_FLAGS.put(Material.GOLD_PLATE, Integer.valueOf(0));
/* 218 */     MATERIAL_FLAGS.put(Material.IRON_PLATE, Integer.valueOf(0));
/* 219 */     MATERIAL_FLAGS.put(Material.REDSTONE_COMPARATOR_OFF, Integer.valueOf(1));
/* 220 */     MATERIAL_FLAGS.put(Material.REDSTONE_COMPARATOR_ON, Integer.valueOf(1));
/* 221 */     MATERIAL_FLAGS.put(Material.DAYLIGHT_DETECTOR, Integer.valueOf(1));
/* 222 */     MATERIAL_FLAGS.put(Material.REDSTONE_BLOCK, Integer.valueOf(0));
/* 223 */     MATERIAL_FLAGS.put(Material.QUARTZ_ORE, Integer.valueOf(0));
/* 224 */     MATERIAL_FLAGS.put(Material.HOPPER, Integer.valueOf(1));
/* 225 */     MATERIAL_FLAGS.put(Material.QUARTZ_BLOCK, Integer.valueOf(0));
/* 226 */     MATERIAL_FLAGS.put(Material.QUARTZ_STAIRS, Integer.valueOf(0));
/* 227 */     MATERIAL_FLAGS.put(Material.ACTIVATOR_RAIL, Integer.valueOf(0));
/* 228 */     MATERIAL_FLAGS.put(Material.DROPPER, Integer.valueOf(1));
/* 229 */     MATERIAL_FLAGS.put(Material.STAINED_CLAY, Integer.valueOf(0));
/* 230 */     MATERIAL_FLAGS.put(Material.STAINED_GLASS_PANE, Integer.valueOf(0));
/* 231 */     MATERIAL_FLAGS.put(Material.LEAVES_2, Integer.valueOf(0));
/* 232 */     MATERIAL_FLAGS.put(Material.LOG_2, Integer.valueOf(0));
/* 233 */     MATERIAL_FLAGS.put(Material.ACACIA_STAIRS, Integer.valueOf(0));
/* 234 */     MATERIAL_FLAGS.put(Material.DARK_OAK_STAIRS, Integer.valueOf(0));
/* 235 */     MATERIAL_FLAGS.put(Material.HAY_BLOCK, Integer.valueOf(0));
/* 236 */     MATERIAL_FLAGS.put(Material.CARPET, Integer.valueOf(0));
/* 237 */     MATERIAL_FLAGS.put(Material.HARD_CLAY, Integer.valueOf(0));
/* 238 */     MATERIAL_FLAGS.put(Material.COAL_BLOCK, Integer.valueOf(0));
/* 239 */     MATERIAL_FLAGS.put(Material.PACKED_ICE, Integer.valueOf(0));
/* 240 */     MATERIAL_FLAGS.put(Material.DOUBLE_PLANT, Integer.valueOf(0));
/*     */     try {
/* 242 */       MATERIAL_FLAGS.put(Material.STANDING_BANNER, Integer.valueOf(0));
/* 243 */       MATERIAL_FLAGS.put(Material.WALL_BANNER, Integer.valueOf(0));
/* 244 */       MATERIAL_FLAGS.put(Material.DAYLIGHT_DETECTOR_INVERTED, Integer.valueOf(0));
/* 245 */       MATERIAL_FLAGS.put(Material.RED_SANDSTONE, Integer.valueOf(0));
/* 246 */       MATERIAL_FLAGS.put(Material.RED_SANDSTONE_STAIRS, Integer.valueOf(0));
/* 247 */       MATERIAL_FLAGS.put(Material.DOUBLE_STONE_SLAB2, Integer.valueOf(0));
/* 248 */       MATERIAL_FLAGS.put(Material.STONE_SLAB2, Integer.valueOf(0));
/* 249 */       MATERIAL_FLAGS.put(Material.SPRUCE_FENCE_GATE, Integer.valueOf(1));
/* 250 */       MATERIAL_FLAGS.put(Material.BIRCH_FENCE_GATE, Integer.valueOf(1));
/* 251 */       MATERIAL_FLAGS.put(Material.JUNGLE_FENCE_GATE, Integer.valueOf(1));
/* 252 */       MATERIAL_FLAGS.put(Material.DARK_OAK_FENCE_GATE, Integer.valueOf(1));
/* 253 */       MATERIAL_FLAGS.put(Material.ACACIA_FENCE_GATE, Integer.valueOf(1));
/* 254 */       MATERIAL_FLAGS.put(Material.SPRUCE_FENCE, Integer.valueOf(0));
/* 255 */       MATERIAL_FLAGS.put(Material.BIRCH_FENCE, Integer.valueOf(0));
/* 256 */       MATERIAL_FLAGS.put(Material.JUNGLE_FENCE, Integer.valueOf(0));
/* 257 */       MATERIAL_FLAGS.put(Material.DARK_OAK_FENCE, Integer.valueOf(0));
/* 258 */       MATERIAL_FLAGS.put(Material.ACACIA_FENCE, Integer.valueOf(0));
/* 259 */       MATERIAL_FLAGS.put(Material.SPRUCE_DOOR, Integer.valueOf(1));
/* 260 */       MATERIAL_FLAGS.put(Material.BIRCH_DOOR, Integer.valueOf(1));
/* 261 */       MATERIAL_FLAGS.put(Material.JUNGLE_DOOR, Integer.valueOf(1));
/* 262 */       MATERIAL_FLAGS.put(Material.ACACIA_DOOR, Integer.valueOf(1));
/* 263 */       MATERIAL_FLAGS.put(Material.DARK_OAK_DOOR, Integer.valueOf(1));
/* 264 */     } catch (NoSuchFieldError ignored) {}
/*     */ 
/*     */     
/* 267 */     MATERIAL_FLAGS.put(Material.IRON_SPADE, Integer.valueOf(0));
/* 268 */     MATERIAL_FLAGS.put(Material.IRON_PICKAXE, Integer.valueOf(0));
/* 269 */     MATERIAL_FLAGS.put(Material.IRON_AXE, Integer.valueOf(0));
/* 270 */     MATERIAL_FLAGS.put(Material.FLINT_AND_STEEL, Integer.valueOf(0));
/* 271 */     MATERIAL_FLAGS.put(Material.APPLE, Integer.valueOf(0));
/* 272 */     MATERIAL_FLAGS.put(Material.BOW, Integer.valueOf(0));
/* 273 */     MATERIAL_FLAGS.put(Material.ARROW, Integer.valueOf(0));
/* 274 */     MATERIAL_FLAGS.put(Material.COAL, Integer.valueOf(0));
/* 275 */     MATERIAL_FLAGS.put(Material.DIAMOND, Integer.valueOf(0));
/* 276 */     MATERIAL_FLAGS.put(Material.IRON_INGOT, Integer.valueOf(0));
/* 277 */     MATERIAL_FLAGS.put(Material.GOLD_INGOT, Integer.valueOf(0));
/* 278 */     MATERIAL_FLAGS.put(Material.IRON_SWORD, Integer.valueOf(0));
/* 279 */     MATERIAL_FLAGS.put(Material.WOOD_SWORD, Integer.valueOf(0));
/* 280 */     MATERIAL_FLAGS.put(Material.WOOD_SPADE, Integer.valueOf(0));
/* 281 */     MATERIAL_FLAGS.put(Material.WOOD_PICKAXE, Integer.valueOf(0));
/* 282 */     MATERIAL_FLAGS.put(Material.WOOD_AXE, Integer.valueOf(0));
/* 283 */     MATERIAL_FLAGS.put(Material.STONE_SWORD, Integer.valueOf(0));
/* 284 */     MATERIAL_FLAGS.put(Material.STONE_SPADE, Integer.valueOf(0));
/* 285 */     MATERIAL_FLAGS.put(Material.STONE_PICKAXE, Integer.valueOf(0));
/* 286 */     MATERIAL_FLAGS.put(Material.STONE_AXE, Integer.valueOf(0));
/* 287 */     MATERIAL_FLAGS.put(Material.DIAMOND_SWORD, Integer.valueOf(0));
/* 288 */     MATERIAL_FLAGS.put(Material.DIAMOND_SPADE, Integer.valueOf(0));
/* 289 */     MATERIAL_FLAGS.put(Material.DIAMOND_PICKAXE, Integer.valueOf(0));
/* 290 */     MATERIAL_FLAGS.put(Material.DIAMOND_AXE, Integer.valueOf(0));
/* 291 */     MATERIAL_FLAGS.put(Material.STICK, Integer.valueOf(0));
/* 292 */     MATERIAL_FLAGS.put(Material.BOWL, Integer.valueOf(0));
/* 293 */     MATERIAL_FLAGS.put(Material.MUSHROOM_SOUP, Integer.valueOf(0));
/* 294 */     MATERIAL_FLAGS.put(Material.GOLD_SWORD, Integer.valueOf(0));
/* 295 */     MATERIAL_FLAGS.put(Material.GOLD_SPADE, Integer.valueOf(0));
/* 296 */     MATERIAL_FLAGS.put(Material.GOLD_PICKAXE, Integer.valueOf(0));
/* 297 */     MATERIAL_FLAGS.put(Material.GOLD_AXE, Integer.valueOf(0));
/* 298 */     MATERIAL_FLAGS.put(Material.STRING, Integer.valueOf(0));
/* 299 */     MATERIAL_FLAGS.put(Material.FEATHER, Integer.valueOf(0));
/* 300 */     MATERIAL_FLAGS.put(Material.SULPHUR, Integer.valueOf(0));
/* 301 */     MATERIAL_FLAGS.put(Material.WOOD_HOE, Integer.valueOf(4));
/* 302 */     MATERIAL_FLAGS.put(Material.STONE_HOE, Integer.valueOf(4));
/* 303 */     MATERIAL_FLAGS.put(Material.IRON_HOE, Integer.valueOf(4));
/* 304 */     MATERIAL_FLAGS.put(Material.DIAMOND_HOE, Integer.valueOf(4));
/* 305 */     MATERIAL_FLAGS.put(Material.GOLD_HOE, Integer.valueOf(4));
/* 306 */     MATERIAL_FLAGS.put(Material.SEEDS, Integer.valueOf(4));
/* 307 */     MATERIAL_FLAGS.put(Material.WHEAT, Integer.valueOf(0));
/* 308 */     MATERIAL_FLAGS.put(Material.BREAD, Integer.valueOf(0));
/* 309 */     MATERIAL_FLAGS.put(Material.LEATHER_HELMET, Integer.valueOf(0));
/* 310 */     MATERIAL_FLAGS.put(Material.LEATHER_CHESTPLATE, Integer.valueOf(0));
/* 311 */     MATERIAL_FLAGS.put(Material.LEATHER_LEGGINGS, Integer.valueOf(0));
/* 312 */     MATERIAL_FLAGS.put(Material.LEATHER_BOOTS, Integer.valueOf(0));
/* 313 */     MATERIAL_FLAGS.put(Material.CHAINMAIL_HELMET, Integer.valueOf(0));
/* 314 */     MATERIAL_FLAGS.put(Material.CHAINMAIL_CHESTPLATE, Integer.valueOf(0));
/* 315 */     MATERIAL_FLAGS.put(Material.CHAINMAIL_LEGGINGS, Integer.valueOf(0));
/* 316 */     MATERIAL_FLAGS.put(Material.CHAINMAIL_BOOTS, Integer.valueOf(0));
/* 317 */     MATERIAL_FLAGS.put(Material.IRON_HELMET, Integer.valueOf(0));
/* 318 */     MATERIAL_FLAGS.put(Material.IRON_CHESTPLATE, Integer.valueOf(0));
/* 319 */     MATERIAL_FLAGS.put(Material.IRON_LEGGINGS, Integer.valueOf(0));
/* 320 */     MATERIAL_FLAGS.put(Material.IRON_BOOTS, Integer.valueOf(0));
/* 321 */     MATERIAL_FLAGS.put(Material.DIAMOND_HELMET, Integer.valueOf(0));
/* 322 */     MATERIAL_FLAGS.put(Material.DIAMOND_CHESTPLATE, Integer.valueOf(0));
/* 323 */     MATERIAL_FLAGS.put(Material.DIAMOND_LEGGINGS, Integer.valueOf(0));
/* 324 */     MATERIAL_FLAGS.put(Material.DIAMOND_BOOTS, Integer.valueOf(0));
/* 325 */     MATERIAL_FLAGS.put(Material.GOLD_HELMET, Integer.valueOf(0));
/* 326 */     MATERIAL_FLAGS.put(Material.GOLD_CHESTPLATE, Integer.valueOf(0));
/* 327 */     MATERIAL_FLAGS.put(Material.GOLD_LEGGINGS, Integer.valueOf(0));
/* 328 */     MATERIAL_FLAGS.put(Material.GOLD_BOOTS, Integer.valueOf(0));
/* 329 */     MATERIAL_FLAGS.put(Material.FLINT, Integer.valueOf(0));
/* 330 */     MATERIAL_FLAGS.put(Material.PORK, Integer.valueOf(0));
/* 331 */     MATERIAL_FLAGS.put(Material.GRILLED_PORK, Integer.valueOf(0));
/* 332 */     MATERIAL_FLAGS.put(Material.PAINTING, Integer.valueOf(0));
/* 333 */     MATERIAL_FLAGS.put(Material.GOLDEN_APPLE, Integer.valueOf(0));
/* 334 */     MATERIAL_FLAGS.put(Material.SIGN, Integer.valueOf(0));
/* 335 */     MATERIAL_FLAGS.put(Material.WOOD_DOOR, Integer.valueOf(0));
/* 336 */     MATERIAL_FLAGS.put(Material.BUCKET, Integer.valueOf(0));
/* 337 */     MATERIAL_FLAGS.put(Material.WATER_BUCKET, Integer.valueOf(0));
/* 338 */     MATERIAL_FLAGS.put(Material.LAVA_BUCKET, Integer.valueOf(0));
/* 339 */     MATERIAL_FLAGS.put(Material.MINECART, Integer.valueOf(0));
/* 340 */     MATERIAL_FLAGS.put(Material.SADDLE, Integer.valueOf(0));
/* 341 */     MATERIAL_FLAGS.put(Material.IRON_DOOR, Integer.valueOf(0));
/* 342 */     MATERIAL_FLAGS.put(Material.REDSTONE, Integer.valueOf(0));
/* 343 */     MATERIAL_FLAGS.put(Material.SNOW_BALL, Integer.valueOf(0));
/* 344 */     MATERIAL_FLAGS.put(Material.BOAT, Integer.valueOf(0));
/* 345 */     MATERIAL_FLAGS.put(Material.LEATHER, Integer.valueOf(0));
/* 346 */     MATERIAL_FLAGS.put(Material.MILK_BUCKET, Integer.valueOf(0));
/* 347 */     MATERIAL_FLAGS.put(Material.CLAY_BRICK, Integer.valueOf(0));
/* 348 */     MATERIAL_FLAGS.put(Material.CLAY_BALL, Integer.valueOf(0));
/* 349 */     MATERIAL_FLAGS.put(Material.SUGAR_CANE, Integer.valueOf(0));
/* 350 */     MATERIAL_FLAGS.put(Material.PAPER, Integer.valueOf(0));
/* 351 */     MATERIAL_FLAGS.put(Material.BOOK, Integer.valueOf(0));
/* 352 */     MATERIAL_FLAGS.put(Material.SLIME_BALL, Integer.valueOf(0));
/* 353 */     MATERIAL_FLAGS.put(Material.STORAGE_MINECART, Integer.valueOf(0));
/* 354 */     MATERIAL_FLAGS.put(Material.POWERED_MINECART, Integer.valueOf(0));
/* 355 */     MATERIAL_FLAGS.put(Material.EGG, Integer.valueOf(0));
/* 356 */     MATERIAL_FLAGS.put(Material.COMPASS, Integer.valueOf(0));
/* 357 */     MATERIAL_FLAGS.put(Material.FISHING_ROD, Integer.valueOf(0));
/* 358 */     MATERIAL_FLAGS.put(Material.WATCH, Integer.valueOf(0));
/* 359 */     MATERIAL_FLAGS.put(Material.GLOWSTONE_DUST, Integer.valueOf(0));
/* 360 */     MATERIAL_FLAGS.put(Material.RAW_FISH, Integer.valueOf(0));
/* 361 */     MATERIAL_FLAGS.put(Material.COOKED_FISH, Integer.valueOf(0));
/* 362 */     MATERIAL_FLAGS.put(Material.INK_SACK, Integer.valueOf(4));
/* 363 */     MATERIAL_FLAGS.put(Material.BONE, Integer.valueOf(0));
/* 364 */     MATERIAL_FLAGS.put(Material.SUGAR, Integer.valueOf(0));
/* 365 */     MATERIAL_FLAGS.put(Material.CAKE, Integer.valueOf(0));
/* 366 */     MATERIAL_FLAGS.put(Material.BED, Integer.valueOf(0));
/* 367 */     MATERIAL_FLAGS.put(Material.DIODE, Integer.valueOf(0));
/* 368 */     MATERIAL_FLAGS.put(Material.COOKIE, Integer.valueOf(0));
/* 369 */     MATERIAL_FLAGS.put(Material.MAP, Integer.valueOf(0));
/* 370 */     MATERIAL_FLAGS.put(Material.SHEARS, Integer.valueOf(4));
/* 371 */     MATERIAL_FLAGS.put(Material.MELON, Integer.valueOf(0));
/* 372 */     MATERIAL_FLAGS.put(Material.PUMPKIN_SEEDS, Integer.valueOf(0));
/* 373 */     MATERIAL_FLAGS.put(Material.MELON_SEEDS, Integer.valueOf(0));
/* 374 */     MATERIAL_FLAGS.put(Material.RAW_BEEF, Integer.valueOf(0));
/* 375 */     MATERIAL_FLAGS.put(Material.COOKED_BEEF, Integer.valueOf(0));
/* 376 */     MATERIAL_FLAGS.put(Material.RAW_CHICKEN, Integer.valueOf(0));
/* 377 */     MATERIAL_FLAGS.put(Material.COOKED_CHICKEN, Integer.valueOf(0));
/* 378 */     MATERIAL_FLAGS.put(Material.ROTTEN_FLESH, Integer.valueOf(0));
/* 379 */     MATERIAL_FLAGS.put(Material.ENDER_PEARL, Integer.valueOf(0));
/* 380 */     MATERIAL_FLAGS.put(Material.BLAZE_ROD, Integer.valueOf(0));
/* 381 */     MATERIAL_FLAGS.put(Material.GHAST_TEAR, Integer.valueOf(0));
/* 382 */     MATERIAL_FLAGS.put(Material.GOLD_NUGGET, Integer.valueOf(0));
/* 383 */     MATERIAL_FLAGS.put(Material.NETHER_STALK, Integer.valueOf(0));
/* 384 */     MATERIAL_FLAGS.put(Material.POTION, Integer.valueOf(0));
/* 385 */     MATERIAL_FLAGS.put(Material.GLASS_BOTTLE, Integer.valueOf(0));
/* 386 */     MATERIAL_FLAGS.put(Material.SPIDER_EYE, Integer.valueOf(0));
/* 387 */     MATERIAL_FLAGS.put(Material.FERMENTED_SPIDER_EYE, Integer.valueOf(0));
/* 388 */     MATERIAL_FLAGS.put(Material.BLAZE_POWDER, Integer.valueOf(0));
/* 389 */     MATERIAL_FLAGS.put(Material.MAGMA_CREAM, Integer.valueOf(0));
/* 390 */     MATERIAL_FLAGS.put(Material.BREWING_STAND_ITEM, Integer.valueOf(0));
/* 391 */     MATERIAL_FLAGS.put(Material.CAULDRON_ITEM, Integer.valueOf(0));
/* 392 */     MATERIAL_FLAGS.put(Material.EYE_OF_ENDER, Integer.valueOf(0));
/* 393 */     MATERIAL_FLAGS.put(Material.SPECKLED_MELON, Integer.valueOf(0));
/* 394 */     MATERIAL_FLAGS.put(Material.MONSTER_EGG, Integer.valueOf(0));
/* 395 */     MATERIAL_FLAGS.put(Material.EXP_BOTTLE, Integer.valueOf(0));
/* 396 */     MATERIAL_FLAGS.put(Material.FIREBALL, Integer.valueOf(0));
/* 397 */     MATERIAL_FLAGS.put(Material.BOOK_AND_QUILL, Integer.valueOf(0));
/* 398 */     MATERIAL_FLAGS.put(Material.WRITTEN_BOOK, Integer.valueOf(0));
/* 399 */     MATERIAL_FLAGS.put(Material.EMERALD, Integer.valueOf(0));
/* 400 */     MATERIAL_FLAGS.put(Material.ITEM_FRAME, Integer.valueOf(0));
/* 401 */     MATERIAL_FLAGS.put(Material.FLOWER_POT_ITEM, Integer.valueOf(0));
/* 402 */     MATERIAL_FLAGS.put(Material.CARROT_ITEM, Integer.valueOf(0));
/* 403 */     MATERIAL_FLAGS.put(Material.POTATO_ITEM, Integer.valueOf(0));
/* 404 */     MATERIAL_FLAGS.put(Material.BAKED_POTATO, Integer.valueOf(0));
/* 405 */     MATERIAL_FLAGS.put(Material.POISONOUS_POTATO, Integer.valueOf(0));
/* 406 */     MATERIAL_FLAGS.put(Material.EMPTY_MAP, Integer.valueOf(0));
/* 407 */     MATERIAL_FLAGS.put(Material.GOLDEN_CARROT, Integer.valueOf(0));
/* 408 */     MATERIAL_FLAGS.put(Material.SKULL_ITEM, Integer.valueOf(0));
/* 409 */     MATERIAL_FLAGS.put(Material.CARROT_STICK, Integer.valueOf(0));
/* 410 */     MATERIAL_FLAGS.put(Material.NETHER_STAR, Integer.valueOf(0));
/* 411 */     MATERIAL_FLAGS.put(Material.PUMPKIN_PIE, Integer.valueOf(0));
/* 412 */     MATERIAL_FLAGS.put(Material.FIREWORK, Integer.valueOf(0));
/* 413 */     MATERIAL_FLAGS.put(Material.FIREWORK_CHARGE, Integer.valueOf(0));
/* 414 */     MATERIAL_FLAGS.put(Material.ENCHANTED_BOOK, Integer.valueOf(0));
/* 415 */     MATERIAL_FLAGS.put(Material.REDSTONE_COMPARATOR, Integer.valueOf(0));
/* 416 */     MATERIAL_FLAGS.put(Material.NETHER_BRICK_ITEM, Integer.valueOf(0));
/* 417 */     MATERIAL_FLAGS.put(Material.QUARTZ, Integer.valueOf(0));
/* 418 */     MATERIAL_FLAGS.put(Material.EXPLOSIVE_MINECART, Integer.valueOf(0));
/* 419 */     MATERIAL_FLAGS.put(Material.HOPPER_MINECART, Integer.valueOf(0));
/* 420 */     MATERIAL_FLAGS.put(Material.IRON_BARDING, Integer.valueOf(0));
/* 421 */     MATERIAL_FLAGS.put(Material.GOLD_BARDING, Integer.valueOf(0));
/* 422 */     MATERIAL_FLAGS.put(Material.DIAMOND_BARDING, Integer.valueOf(0));
/* 423 */     MATERIAL_FLAGS.put(Material.LEASH, Integer.valueOf(0));
/* 424 */     MATERIAL_FLAGS.put(Material.NAME_TAG, Integer.valueOf(0));
/* 425 */     MATERIAL_FLAGS.put(Material.COMMAND_MINECART, Integer.valueOf(0));
/* 426 */     MATERIAL_FLAGS.put(Material.GOLD_RECORD, Integer.valueOf(0));
/* 427 */     MATERIAL_FLAGS.put(Material.GREEN_RECORD, Integer.valueOf(0));
/* 428 */     MATERIAL_FLAGS.put(Material.RECORD_3, Integer.valueOf(0));
/* 429 */     MATERIAL_FLAGS.put(Material.RECORD_4, Integer.valueOf(0));
/* 430 */     MATERIAL_FLAGS.put(Material.RECORD_5, Integer.valueOf(0));
/* 431 */     MATERIAL_FLAGS.put(Material.RECORD_6, Integer.valueOf(0));
/* 432 */     MATERIAL_FLAGS.put(Material.RECORD_7, Integer.valueOf(0));
/* 433 */     MATERIAL_FLAGS.put(Material.RECORD_8, Integer.valueOf(0));
/* 434 */     MATERIAL_FLAGS.put(Material.RECORD_9, Integer.valueOf(0));
/* 435 */     MATERIAL_FLAGS.put(Material.RECORD_10, Integer.valueOf(0));
/* 436 */     MATERIAL_FLAGS.put(Material.RECORD_11, Integer.valueOf(0));
/* 437 */     MATERIAL_FLAGS.put(Material.RECORD_12, Integer.valueOf(0));
/*     */ 
/*     */     
/* 440 */     DAMAGE_EFFECTS.add(PotionEffectType.BLINDNESS);
/* 441 */     DAMAGE_EFFECTS.add(PotionEffectType.CONFUSION);
/*     */ 
/*     */ 
/*     */     
/* 445 */     DAMAGE_EFFECTS.add(PotionEffectType.HARM);
/*     */ 
/*     */     
/* 448 */     DAMAGE_EFFECTS.add(PotionEffectType.HUNGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 453 */     DAMAGE_EFFECTS.add(PotionEffectType.POISON);
/*     */ 
/*     */     
/* 456 */     DAMAGE_EFFECTS.add(PotionEffectType.SLOW);
/* 457 */     DAMAGE_EFFECTS.add(PotionEffectType.SLOW_DIGGING);
/*     */ 
/*     */     
/* 460 */     DAMAGE_EFFECTS.add(PotionEffectType.WEAKNESS);
/* 461 */     DAMAGE_EFFECTS.add(PotionEffectType.WITHER);
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
/*     */   @Nullable
/*     */   public static Material getRelatedMaterial(EntityType type) {
/* 475 */     return (Material)ENTITY_ITEMS.get(type);
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
/*     */   public static Material getBucketBlockMaterial(Material type) {
/* 492 */     switch (type) {
/*     */       case LAVA_BUCKET:
/* 494 */         return Material.LAVA;
/*     */       case MILK_BUCKET:
/* 496 */         return Material.WATER;
/*     */     } 
/* 498 */     return Material.WATER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isMushroom(Material material) {
/* 509 */     return (material == Material.RED_MUSHROOM || material == Material.BROWN_MUSHROOM);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isLeaf(Material material) {
/* 519 */     return (material == Material.LEAVES || material == Material.LEAVES_2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isLiquid(Material material) {
/* 529 */     return (isWater(material) || isLava(material));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isWater(Material material) {
/* 539 */     return (material == Material.WATER || material == Material.STATIONARY_WATER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isLava(Material material) {
/* 549 */     return (material == Material.LAVA || material == Material.STATIONARY_LAVA);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isPortal(Material material) {
/* 559 */     return (material == Material.PORTAL || material == Material.ENDER_PORTAL);
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
/*     */   public static boolean isDyeColor(MaterialData data, DyeColor color) {
/* 571 */     return (data instanceof Dye && ((Dye)data).getColor() == color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRailBlock(Material material) {
/* 581 */     return (material == Material.RAILS || material == Material.ACTIVATOR_RAIL || material == Material.DETECTOR_RAIL || material == Material.POWERED_RAIL);
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
/*     */   public static boolean isPistonBlock(Material material) {
/* 595 */     return (material == Material.PISTON_BASE || material == Material.PISTON_STICKY_BASE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isMinecart(Material material) {
/* 606 */     return (material == Material.MINECART || material == Material.COMMAND_MINECART || material == Material.EXPLOSIVE_MINECART || material == Material.HOPPER_MINECART || material == Material.POWERED_MINECART || material == Material.STORAGE_MINECART);
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
/*     */   public static boolean isInventoryBlock(Material material) {
/* 621 */     return (material == Material.CHEST || material == Material.JUKEBOX || material == Material.DISPENSER || material == Material.FURNACE || material == Material.BURNING_FURNACE || material == Material.BREWING_STAND || material == Material.TRAPPED_CHEST || material == Material.HOPPER || material == Material.DROPPER);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isUseFlagApplicable(Material material) {
/* 643 */     switch (material) { case LEVER:
/* 644 */         return true;
/* 645 */       case STONE_BUTTON: return true;
/* 646 */       case WOOD_BUTTON: return true;
/* 647 */       case WOODEN_DOOR: return true;
/* 648 */       case TRAP_DOOR: return true;
/* 649 */       case FENCE_GATE: return true;
/* 650 */       case WORKBENCH: return true;
/* 651 */       case ENCHANTMENT_TABLE: return true;
/* 652 */       case BEACON: return true;
/* 653 */       case ANVIL: return true;
/* 654 */       case WOOD_PLATE: return true;
/* 655 */       case STONE_PLATE: return true;
/* 656 */       case IRON_PLATE: return true;
/* 657 */       case GOLD_PLATE: return true;
/* 658 */       case SPRUCE_FENCE_GATE: return true;
/* 659 */       case BIRCH_FENCE_GATE: return true;
/* 660 */       case JUNGLE_FENCE_GATE: return true;
/* 661 */       case DARK_OAK_FENCE_GATE: return true;
/* 662 */       case ACACIA_FENCE_GATE: return true;
/* 663 */       case SPRUCE_DOOR: return true;
/* 664 */       case BIRCH_DOOR: return true;
/* 665 */       case JUNGLE_DOOR: return true;
/* 666 */       case ACACIA_DOOR: return true;
/* 667 */       case DARK_OAK_DOOR: return true; }
/* 668 */      return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRedstoneOre(Material type) {
/* 679 */     return (type == Material.GLOWING_REDSTONE_ORE || type == Material.REDSTONE_ORE);
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
/*     */   public static boolean isBlockModifiedOnClick(Material material, boolean rightClick) {
/* 694 */     Integer flags = MATERIAL_FLAGS.get(material);
/* 695 */     return (flags == null || (rightClick && (flags
/* 696 */       .intValue() & 0x1) == 1) || (!rightClick && (flags
/* 697 */       .intValue() & 0x2) == 2));
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
/*     */   public static boolean isItemAppliedToBlock(Material item, Material block) {
/* 711 */     Integer flags = MATERIAL_FLAGS.get(item);
/* 712 */     return (flags == null || (flags.intValue() & 0x4) == 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isConsideredBuildingIfUsed(Material type) {
/* 723 */     return (type == Material.SAPLING);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasDamageEffect(Collection<PotionEffect> effects) {
/* 734 */     for (PotionEffect effect : effects) {
/* 735 */       if (DAMAGE_EFFECTS.contains(effect.getType())) {
/* 736 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 740 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukki\\util\Materials.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */