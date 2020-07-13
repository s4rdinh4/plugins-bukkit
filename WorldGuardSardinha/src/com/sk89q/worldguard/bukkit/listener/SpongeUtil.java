/*     */ package com.sk89q.worldguard.bukkit.listener;
/*     */ 
/*     */ import com.sk89q.worldguard.bukkit.BukkitUtil;
/*     */ import com.sk89q.worldguard.bukkit.ConfigurationManager;
/*     */ import com.sk89q.worldguard.bukkit.WorldConfiguration;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import org.bukkit.World;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SpongeUtil
/*     */ {
/*     */   public static void clearSpongeWater(WorldGuardPlugin plugin, World world, int ox, int oy, int oz) {
/*  45 */     ConfigurationManager cfg = plugin.getGlobalStateManager();
/*  46 */     WorldConfiguration wcfg = cfg.get(world);
/*     */     
/*  48 */     for (int cx = -wcfg.spongeRadius; cx <= wcfg.spongeRadius; cx++) {
/*  49 */       for (int cy = -wcfg.spongeRadius; cy <= wcfg.spongeRadius; cy++) {
/*  50 */         for (int cz = -wcfg.spongeRadius; cz <= wcfg.spongeRadius; cz++) {
/*  51 */           if (BukkitUtil.isBlockWater(world, ox + cx, oy + cy, oz + cz)) {
/*  52 */             world.getBlockAt(ox + cx, oy + cy, oz + cz).setTypeId(0);
/*     */           }
/*     */         } 
/*     */       } 
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
/*     */   public static void addSpongeWater(WorldGuardPlugin plugin, World world, int ox, int oy, int oz) {
/*  69 */     ConfigurationManager cfg = plugin.getGlobalStateManager();
/*  70 */     WorldConfiguration wcfg = cfg.get(world);
/*     */ 
/*     */     
/*  73 */     int cx = ox - wcfg.spongeRadius - 1; int cy;
/*  74 */     for (cy = oy - wcfg.spongeRadius - 1; cy <= oy + wcfg.spongeRadius + 1; cy++) {
/*  75 */       for (int i = oz - wcfg.spongeRadius - 1; i <= oz + wcfg.spongeRadius + 1; i++) {
/*  76 */         if (BukkitUtil.isBlockWater(world, cx, cy, i)) {
/*  77 */           BukkitUtil.setBlockToWater(world, cx + 1, cy, i);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  83 */     cx = ox + wcfg.spongeRadius + 1;
/*  84 */     for (cy = oy - wcfg.spongeRadius - 1; cy <= oy + wcfg.spongeRadius + 1; cy++) {
/*  85 */       for (int i = oz - wcfg.spongeRadius - 1; i <= oz + wcfg.spongeRadius + 1; i++) {
/*  86 */         if (BukkitUtil.isBlockWater(world, cx, cy, i)) {
/*  87 */           BukkitUtil.setBlockToWater(world, cx - 1, cy, i);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  93 */     cy = oy - wcfg.spongeRadius - 1;
/*  94 */     for (cx = ox - wcfg.spongeRadius - 1; cx <= ox + wcfg.spongeRadius + 1; cx++) {
/*  95 */       for (int i = oz - wcfg.spongeRadius - 1; i <= oz + wcfg.spongeRadius + 1; i++) {
/*  96 */         if (BukkitUtil.isBlockWater(world, cx, cy, i)) {
/*  97 */           BukkitUtil.setBlockToWater(world, cx, cy + 1, i);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 103 */     cy = oy + wcfg.spongeRadius + 1;
/* 104 */     for (cx = ox - wcfg.spongeRadius - 1; cx <= ox + wcfg.spongeRadius + 1; cx++) {
/* 105 */       for (int i = oz - wcfg.spongeRadius - 1; i <= oz + wcfg.spongeRadius + 1; i++) {
/* 106 */         if (BukkitUtil.isBlockWater(world, cx, cy, i)) {
/* 107 */           BukkitUtil.setBlockToWater(world, cx, cy - 1, i);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 113 */     int cz = oz - wcfg.spongeRadius - 1;
/* 114 */     for (cx = ox - wcfg.spongeRadius - 1; cx <= ox + wcfg.spongeRadius + 1; cx++) {
/* 115 */       for (cy = oy - wcfg.spongeRadius - 1; cy <= oy + wcfg.spongeRadius + 1; cy++) {
/* 116 */         if (BukkitUtil.isBlockWater(world, cx, cy, cz)) {
/* 117 */           BukkitUtil.setBlockToWater(world, cx, cy, cz + 1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 123 */     cz = oz + wcfg.spongeRadius + 1;
/* 124 */     for (cx = ox - wcfg.spongeRadius - 1; cx <= ox + wcfg.spongeRadius + 1; cx++) {
/* 125 */       for (cy = oy - wcfg.spongeRadius - 1; cy <= oy + wcfg.spongeRadius + 1; cy++) {
/* 126 */         if (BukkitUtil.isBlockWater(world, cx, cy, cz))
/* 127 */           BukkitUtil.setBlockToWater(world, cx, cy, cz - 1); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\SpongeUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */