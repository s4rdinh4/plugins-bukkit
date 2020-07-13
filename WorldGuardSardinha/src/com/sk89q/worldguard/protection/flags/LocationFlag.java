/*     */ package com.sk89q.worldguard.protection.flags;
/*     */ 
/*     */ import com.sk89q.minecraft.util.commands.CommandException;
/*     */ import com.sk89q.worldedit.Location;
/*     */ import com.sk89q.worldedit.Vector;
/*     */ import com.sk89q.worldedit.bukkit.BukkitUtil;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.bukkit.Location;
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
/*     */ public class LocationFlag
/*     */   extends Flag<Location>
/*     */ {
/*     */   public LocationFlag(String name, RegionGroup defaultGroup) {
/*  37 */     super(name, defaultGroup);
/*     */   }
/*     */   
/*     */   public LocationFlag(String name) {
/*  41 */     super(name);
/*     */   }
/*     */   
/*     */   public Location parseInput(WorldGuardPlugin plugin, CommandSender sender, String input) throws InvalidFlagFormat {
/*     */     Player player;
/*  46 */     input = input.trim();
/*     */ 
/*     */     
/*     */     try {
/*  50 */       player = plugin.checkPlayer(sender);
/*  51 */     } catch (CommandException e) {
/*  52 */       throw new InvalidFlagFormat(e.getMessage());
/*     */     } 
/*     */     
/*  55 */     if ("here".equalsIgnoreCase(input))
/*  56 */       return toLazyLocation(player.getLocation()); 
/*  57 */     if ("none".equalsIgnoreCase(input)) {
/*  58 */       return null;
/*     */     }
/*  60 */     String[] split = input.split(",");
/*  61 */     if (split.length >= 3) {
/*     */       try {
/*  63 */         World world = player.getWorld();
/*  64 */         double x = Double.parseDouble(split[0]);
/*  65 */         double y = Double.parseDouble(split[1]);
/*  66 */         double z = Double.parseDouble(split[2]);
/*  67 */         float yaw = (split.length < 4) ? 0.0F : Float.parseFloat(split[3]);
/*  68 */         float pitch = (split.length < 5) ? 0.0F : Float.parseFloat(split[4]);
/*     */         
/*  70 */         return new LazyLocation(world.getName(), new Vector(x, y, z), yaw, pitch);
/*  71 */       } catch (NumberFormatException ignored) {}
/*     */     }
/*     */ 
/*     */     
/*  75 */     throw new InvalidFlagFormat("Expected 'here' or x,y,z.");
/*     */   }
/*     */ 
/*     */   
/*     */   private Location toLazyLocation(Location location) {
/*  80 */     return new LazyLocation(location.getWorld().getName(), BukkitUtil.toVector(location), location.getYaw(), location.getPitch());
/*     */   }
/*     */ 
/*     */   
/*     */   public Location unmarshal(Object o) {
/*  85 */     if (o instanceof Map) {
/*  86 */       Map<?, ?> map = (Map<?, ?>)o;
/*     */       
/*  88 */       Object rawWorld = map.get("world");
/*  89 */       if (rawWorld == null) return null;
/*     */       
/*  91 */       Object rawX = map.get("x");
/*  92 */       if (rawX == null) return null;
/*     */       
/*  94 */       Object rawY = map.get("y");
/*  95 */       if (rawY == null) return null;
/*     */       
/*  97 */       Object rawZ = map.get("z");
/*  98 */       if (rawZ == null) return null;
/*     */       
/* 100 */       Object rawYaw = map.get("yaw");
/* 101 */       if (rawYaw == null) return null;
/*     */       
/* 103 */       Object rawPitch = map.get("pitch");
/* 104 */       if (rawPitch == null) return null;
/*     */       
/* 106 */       Vector position = new Vector(toNumber(rawX), toNumber(rawY), toNumber(rawZ));
/* 107 */       float yaw = (float)toNumber(rawYaw);
/* 108 */       float pitch = (float)toNumber(rawPitch);
/*     */       
/* 110 */       return new LazyLocation(String.valueOf(rawWorld), position, yaw, pitch);
/*     */     } 
/*     */     
/* 113 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object marshal(Location o) {
/* 118 */     Vector position = o.getPosition();
/* 119 */     Map<String, Object> vec = new HashMap<String, Object>();
/* 120 */     if (o instanceof LazyLocation) {
/* 121 */       vec.put("world", ((LazyLocation)o).getWorldName());
/*     */     } else {
/*     */       try {
/* 124 */         vec.put("world", o.getWorld().getName());
/* 125 */       } catch (NullPointerException e) {
/* 126 */         return null;
/*     */       } 
/*     */     } 
/* 129 */     vec.put("x", Double.valueOf(position.getX()));
/* 130 */     vec.put("y", Double.valueOf(position.getY()));
/* 131 */     vec.put("z", Double.valueOf(position.getZ()));
/* 132 */     vec.put("yaw", Float.valueOf(o.getYaw()));
/* 133 */     vec.put("pitch", Float.valueOf(o.getPitch()));
/* 134 */     return vec;
/*     */   }
/*     */   
/*     */   private double toNumber(Object o) {
/* 138 */     if (o instanceof Number) {
/* 139 */       return ((Number)o).doubleValue();
/*     */     }
/* 141 */     return 0.0D;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\flags\LocationFlag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */