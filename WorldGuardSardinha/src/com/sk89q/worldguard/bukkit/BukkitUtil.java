/*     */ package com.sk89q.worldguard.bukkit;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.sk89q.worldedit.BlockVector;
/*     */ import com.sk89q.worldedit.Vector;
/*     */ import com.sk89q.worldedit.blocks.BlockType;
/*     */ import com.sk89q.worldguard.blacklist.target.MaterialTarget;
/*     */ import com.sk89q.worldguard.blacklist.target.Target;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Chunk;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.util.Vector;
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
/*     */ public class BukkitUtil
/*     */ {
/*     */   private static Method ONLINE_PLAYERS_METHOD;
/*     */   
/*     */   public static BlockVector toVector(Block block) {
/*  58 */     return new BlockVector(block.getX(), block.getY(), block.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector toVector(Location loc) {
/*  68 */     return new Vector(loc.getX(), loc.getY(), loc.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector toVector(Vector vector) {
/*  78 */     return new Vector(vector.getX(), vector.getY(), vector.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Location toLocation(World world, Vector vec) {
/*  89 */     return new Location(world, vec.getX(), vec.getY(), vec.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ProtectedRegion toRegion(Chunk chunk) {
/*  99 */     World world = chunk.getWorld();
/*     */     
/* 101 */     int minX = chunk.getX() << 4;
/* 102 */     int minZ = chunk.getZ() << 4;
/* 103 */     return (ProtectedRegion)new ProtectedCuboidRegion("_", new BlockVector(minX, 0, minZ), new BlockVector(minX + 15, world.getMaxHeight(), minZ + 15));
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
/*     */   @Deprecated
/*     */   public static Player matchSinglePlayer(Server server, String name) {
/* 116 */     List<Player> players = server.matchPlayer(name);
/* 117 */     if (players.size() == 0) {
/* 118 */       return null;
/*     */     }
/* 120 */     return players.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void dropSign(Block block) {
/* 131 */     block.setTypeId(0);
/* 132 */     block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(323, 1));
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
/*     */   public static void setBlockToWater(World world, int ox, int oy, int oz) {
/* 146 */     Block block = world.getBlockAt(ox, oy, oz);
/* 147 */     int id = block.getTypeId();
/* 148 */     if (id == 0) {
/* 149 */       block.setTypeId(8);
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
/*     */   public static boolean isBlockWater(World world, int ox, int oy, int oz) {
/* 163 */     Block block = world.getBlockAt(ox, oy, oz);
/* 164 */     int id = block.getTypeId();
/* 165 */     return (id == 8 || id == 9);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isWaterPotion(ItemStack item) {
/* 175 */     return ((item.getDurability() & 0x3F) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getPotionEffectBits(ItemStack item) {
/* 186 */     return item.getDurability() & 0x3F;
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
/*     */   public static void findFreePosition(Player player) {
/* 198 */     Location loc = player.getLocation();
/* 199 */     int x = loc.getBlockX();
/* 200 */     int y = Math.max(0, loc.getBlockY());
/* 201 */     int origY = y;
/* 202 */     int z = loc.getBlockZ();
/* 203 */     World world = player.getWorld();
/*     */     
/* 205 */     byte free = 0;
/*     */     
/* 207 */     while (y <= world.getMaxHeight() + 1) {
/* 208 */       if (BlockType.canPassThrough(world.getBlockTypeIdAt(x, y, z))) {
/* 209 */         free = (byte)(free + 1);
/*     */       } else {
/* 211 */         free = 0;
/*     */       } 
/*     */       
/* 214 */       if (free == 2) {
/* 215 */         if (y - 1 != origY || y == 1) {
/* 216 */           loc.setX(x + 0.5D);
/* 217 */           loc.setY(y);
/* 218 */           loc.setZ(z + 0.5D);
/* 219 */           if (y <= 2 && world.getBlockAt(x, 0, z).getTypeId() == 0) {
/* 220 */             world.getBlockAt(x, 0, z).setTypeId(20);
/* 221 */             loc.setY(2.0D);
/*     */           } 
/* 223 */           player.setFallDistance(0.0F);
/* 224 */           player.teleport(loc);
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/* 229 */       y++;
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
/*     */ 
/*     */   
/*     */   public static String replaceColorMacros(String str) {
/* 245 */     str = str.replace("&r", ChatColor.RED.toString());
/* 246 */     str = str.replace("&R", ChatColor.DARK_RED.toString());
/*     */     
/* 248 */     str = str.replace("&y", ChatColor.YELLOW.toString());
/* 249 */     str = str.replace("&Y", ChatColor.GOLD.toString());
/*     */     
/* 251 */     str = str.replace("&g", ChatColor.GREEN.toString());
/* 252 */     str = str.replace("&G", ChatColor.DARK_GREEN.toString());
/*     */     
/* 254 */     str = str.replace("&c", ChatColor.AQUA.toString());
/* 255 */     str = str.replace("&C", ChatColor.DARK_AQUA.toString());
/*     */     
/* 257 */     str = str.replace("&b", ChatColor.BLUE.toString());
/* 258 */     str = str.replace("&B", ChatColor.DARK_BLUE.toString());
/*     */     
/* 260 */     str = str.replace("&p", ChatColor.LIGHT_PURPLE.toString());
/* 261 */     str = str.replace("&P", ChatColor.DARK_PURPLE.toString());
/*     */     
/* 263 */     str = str.replace("&0", ChatColor.BLACK.toString());
/* 264 */     str = str.replace("&1", ChatColor.DARK_GRAY.toString());
/* 265 */     str = str.replace("&2", ChatColor.GRAY.toString());
/* 266 */     str = str.replace("&w", ChatColor.WHITE.toString());
/*     */     
/* 268 */     str = str.replace("&k", ChatColor.MAGIC.toString());
/* 269 */     str = str.replace("&l", ChatColor.BOLD.toString());
/* 270 */     str = str.replace("&m", ChatColor.STRIKETHROUGH.toString());
/* 271 */     str = str.replace("&n", ChatColor.UNDERLINE.toString());
/* 272 */     str = str.replace("&o", ChatColor.ITALIC.toString());
/*     */     
/* 274 */     str = str.replace("&x", ChatColor.RESET.toString());
/*     */     
/* 276 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isIntensiveEntity(Entity entity) {
/* 286 */     return (entity instanceof org.bukkit.entity.Item || entity instanceof org.bukkit.entity.TNTPrimed || entity instanceof org.bukkit.entity.ExperienceOrb || entity instanceof org.bukkit.entity.FallingBlock || (entity instanceof org.bukkit.entity.LivingEntity && !(entity instanceof org.bukkit.entity.Tameable) && !(entity instanceof Player)));
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
/*     */   public static <T extends Enum<T>> T tryEnum(Class<T> enumType, String... values) {
/* 304 */     for (String val : values) {
/*     */       try {
/* 306 */         return Enum.valueOf(enumType, val);
/* 307 */       } catch (IllegalArgumentException e) {}
/*     */     } 
/*     */ 
/*     */     
/* 311 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Target createTarget(Block block) {
/* 321 */     Preconditions.checkNotNull(block);
/* 322 */     return (Target)new MaterialTarget(block.getTypeId(), (short)block.getData());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Target createTarget(Block block, Material material) {
/* 333 */     Preconditions.checkNotNull(material);
/* 334 */     if (block.getType() == material) {
/* 335 */       return (Target)new MaterialTarget(block.getTypeId(), (short)block.getData());
/*     */     }
/* 337 */     return (Target)new MaterialTarget(material.getId(), (short)0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Target createTarget(ItemStack item) {
/* 348 */     Preconditions.checkNotNull(item);
/* 349 */     return (Target)new MaterialTarget(item.getTypeId(), item.getDurability());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Target createTarget(Material material) {
/* 359 */     Preconditions.checkNotNull(material);
/* 360 */     return (Target)new MaterialTarget(material.getId(), (short)0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Collection<? extends Player> getOnlinePlayers() {
/*     */     try {
/* 371 */       return Bukkit.getServer().getOnlinePlayers();
/* 372 */     } catch (NoSuchMethodError ignored) {
/*     */ 
/*     */       
/*     */       try {
/* 376 */         if (ONLINE_PLAYERS_METHOD == null) {
/* 377 */           ONLINE_PLAYERS_METHOD = getOnlinePlayersMethod();
/*     */         }
/*     */         
/* 380 */         Object result = ONLINE_PLAYERS_METHOD.invoke(Bukkit.getServer(), new Object[0]);
/* 381 */         if (result instanceof Player[])
/* 382 */           return (Collection<? extends Player>)ImmutableList.copyOf((Object[])result); 
/* 383 */         if (result instanceof Collection) {
/* 384 */           return (Collection<? extends Player>)result;
/*     */         }
/* 386 */         throw new RuntimeException("Result of getOnlinePlayers() call was not a known data type");
/*     */       }
/* 388 */       catch (Exception e) {
/* 389 */         throw new RuntimeException("WorldGuard is not compatible with this version of Bukkit", e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private static Method getOnlinePlayersMethod() throws NoSuchMethodException {
/*     */     try {
/* 395 */       return Server.class.getMethod("getOnlinePlayers", new Class[0]);
/* 396 */     } catch (NoSuchMethodException e1) {
/* 397 */       return Server.class.getMethod("_INVALID_getOnlinePlayers", new Class[0]);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\BukkitUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */