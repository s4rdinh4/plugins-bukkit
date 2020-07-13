/*     */ package com.sk89q.worldguard.protection.flags;
/*     */ 
/*     */ import com.sk89q.minecraft.util.commands.CommandException;
/*     */ import com.sk89q.worldedit.Vector;
/*     */ import com.sk89q.worldguard.bukkit.BukkitUtil;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.bukkit.command.CommandSender;
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
/*     */ public class VectorFlag
/*     */   extends Flag<Vector>
/*     */ {
/*     */   public VectorFlag(String name, RegionGroup defaultGroup) {
/*  37 */     super(name, defaultGroup);
/*     */   }
/*     */   
/*     */   public VectorFlag(String name) {
/*  41 */     super(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector parseInput(WorldGuardPlugin plugin, CommandSender sender, String input) throws InvalidFlagFormat {
/*  46 */     input = input.trim();
/*     */     
/*  48 */     if ("here".equalsIgnoreCase(input)) {
/*     */       try {
/*  50 */         return BukkitUtil.toVector(plugin.checkPlayer(sender).getLocation());
/*  51 */       } catch (CommandException e) {
/*  52 */         throw new InvalidFlagFormat(e.getMessage());
/*     */       } 
/*     */     }
/*  55 */     String[] split = input.split(",");
/*  56 */     if (split.length == 3) {
/*     */       try {
/*  58 */         return new Vector(
/*  59 */             Double.parseDouble(split[0]), 
/*  60 */             Double.parseDouble(split[1]), 
/*  61 */             Double.parseDouble(split[2]));
/*     */       }
/*  63 */       catch (NumberFormatException ignored) {}
/*     */     }
/*     */ 
/*     */     
/*  67 */     throw new InvalidFlagFormat("Expected 'here' or x,y,z.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector unmarshal(Object o) {
/*  73 */     if (o instanceof Map) {
/*  74 */       Map<?, ?> map = (Map<?, ?>)o;
/*     */       
/*  76 */       Object rawX = map.get("x");
/*  77 */       Object rawY = map.get("y");
/*  78 */       Object rawZ = map.get("z");
/*     */       
/*  80 */       if (rawX == null || rawY == null || rawZ == null) {
/*  81 */         return null;
/*     */       }
/*     */       
/*  84 */       return new Vector(toNumber(rawX), toNumber(rawY), toNumber(rawZ));
/*     */     } 
/*     */     
/*  87 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object marshal(Vector o) {
/*  92 */     Map<String, Object> vec = new HashMap<String, Object>();
/*  93 */     vec.put("x", Double.valueOf(o.getX()));
/*  94 */     vec.put("y", Double.valueOf(o.getY()));
/*  95 */     vec.put("z", Double.valueOf(o.getZ()));
/*  96 */     return vec;
/*     */   }
/*     */   
/*     */   private double toNumber(Object o) {
/* 100 */     if (o instanceof Integer)
/* 101 */       return ((Integer)o).intValue(); 
/* 102 */     if (o instanceof Long)
/* 103 */       return ((Long)o).longValue(); 
/* 104 */     if (o instanceof Float)
/* 105 */       return ((Float)o).floatValue(); 
/* 106 */     if (o instanceof Double) {
/* 107 */       return ((Double)o).doubleValue();
/*     */     }
/* 109 */     return 0.0D;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\flags\VectorFlag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */