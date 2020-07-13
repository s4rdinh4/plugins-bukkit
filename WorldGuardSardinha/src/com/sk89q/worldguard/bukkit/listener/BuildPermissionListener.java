/*     */ package com.sk89q.worldguard.bukkit.listener;
/*     */ 
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.bukkit.event.block.BreakBlockEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.block.PlaceBlockEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.block.UseBlockEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.entity.DamageEntityEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.entity.DestroyEntityEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.entity.SpawnEntityEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.entity.UseEntityEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.inventory.UseItemEvent;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
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
/*     */ public class BuildPermissionListener
/*     */   extends AbstractListener
/*     */ {
/*     */   public BuildPermissionListener(WorldGuardPlugin plugin) {
/*  46 */     super(plugin);
/*     */   }
/*     */   
/*     */   private boolean hasBuildPermission(CommandSender sender, String perm) {
/*  50 */     return getPlugin().hasPermission(sender, "worldguard.build." + perm);
/*     */   }
/*     */   
/*     */   private void tellErrorMessage(CommandSender sender, World world) {
/*  54 */     String message = (getWorldConfig(world)).buildPermissionDenyMessage;
/*  55 */     if (!message.isEmpty()) {
/*  56 */       sender.sendMessage(message);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onPlaceBlock(PlaceBlockEvent event) {
/*  62 */     if (!(getWorldConfig(event.getWorld())).buildPermissions)
/*     */       return; 
/*  64 */     Object rootCause = event.getCause().getRootCause();
/*     */ 
/*     */     
/*  67 */     Player player = (Player)rootCause;
/*  68 */     Material material = event.getEffectiveMaterial();
/*     */     
/*  70 */     if (rootCause instanceof Player && !hasBuildPermission((CommandSender)player, "block." + material.name().toLowerCase() + ".place") && 
/*  71 */       !hasBuildPermission((CommandSender)player, "block.place." + material.name().toLowerCase())) {
/*  72 */       tellErrorMessage((CommandSender)player, event.getWorld());
/*  73 */       event.setCancelled(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onBreakBlock(BreakBlockEvent event) {
/*  80 */     if (!(getWorldConfig(event.getWorld())).buildPermissions)
/*     */       return; 
/*  82 */     Object rootCause = event.getCause().getRootCause();
/*     */ 
/*     */     
/*  85 */     Player player = (Player)rootCause;
/*  86 */     Material material = event.getEffectiveMaterial();
/*     */     
/*  88 */     if (rootCause instanceof Player && !hasBuildPermission((CommandSender)player, "block." + material.name().toLowerCase() + ".remove") && 
/*  89 */       !hasBuildPermission((CommandSender)player, "block.remove." + material.name().toLowerCase())) {
/*  90 */       tellErrorMessage((CommandSender)player, event.getWorld());
/*  91 */       event.setCancelled(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onUseBlock(UseBlockEvent event) {
/*  98 */     if (!(getWorldConfig(event.getWorld())).buildPermissions)
/*     */       return; 
/* 100 */     Object rootCause = event.getCause().getRootCause();
/*     */ 
/*     */     
/* 103 */     Player player = (Player)rootCause;
/* 104 */     Material material = event.getEffectiveMaterial();
/*     */     
/* 106 */     if (rootCause instanceof Player && !hasBuildPermission((CommandSender)player, "block." + material.name().toLowerCase() + ".interact") && 
/* 107 */       !hasBuildPermission((CommandSender)player, "block.interact." + material.name().toLowerCase())) {
/* 108 */       tellErrorMessage((CommandSender)player, event.getWorld());
/* 109 */       event.setCancelled(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onSpawnEntity(SpawnEntityEvent event) {
/* 116 */     if (!(getWorldConfig(event.getWorld())).buildPermissions)
/*     */       return; 
/* 118 */     Object rootCause = event.getCause().getRootCause();
/*     */ 
/*     */     
/* 121 */     Player player = (Player)rootCause;
/* 122 */     EntityType type = event.getEffectiveType();
/*     */     
/* 124 */     if (rootCause instanceof Player && !hasBuildPermission((CommandSender)player, "entity." + type.name().toLowerCase() + ".place") && 
/* 125 */       !hasBuildPermission((CommandSender)player, "entity.place." + type.name().toLowerCase())) {
/* 126 */       tellErrorMessage((CommandSender)player, event.getWorld());
/* 127 */       event.setCancelled(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onDestroyEntity(DestroyEntityEvent event) {
/* 134 */     if (!(getWorldConfig(event.getWorld())).buildPermissions)
/*     */       return; 
/* 136 */     Object rootCause = event.getCause().getRootCause();
/*     */ 
/*     */     
/* 139 */     Player player = (Player)rootCause;
/* 140 */     EntityType type = event.getEntity().getType();
/*     */     
/* 142 */     if (rootCause instanceof Player && !hasBuildPermission((CommandSender)player, "entity." + type.name().toLowerCase() + ".remove") && 
/* 143 */       !hasBuildPermission((CommandSender)player, "entity.remove." + type.name().toLowerCase())) {
/* 144 */       tellErrorMessage((CommandSender)player, event.getWorld());
/* 145 */       event.setCancelled(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onUseEntity(UseEntityEvent event) {
/* 152 */     if (!(getWorldConfig(event.getWorld())).buildPermissions)
/*     */       return; 
/* 154 */     Object rootCause = event.getCause().getRootCause();
/*     */ 
/*     */     
/* 157 */     Player player = (Player)rootCause;
/* 158 */     EntityType type = event.getEntity().getType();
/*     */     
/* 160 */     if (rootCause instanceof Player && !hasBuildPermission((CommandSender)player, "entity." + type.name().toLowerCase() + ".interact") && 
/* 161 */       !hasBuildPermission((CommandSender)player, "entity.interact." + type.name().toLowerCase())) {
/* 162 */       tellErrorMessage((CommandSender)player, event.getWorld());
/* 163 */       event.setCancelled(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onDamageEntity(DamageEntityEvent event) {
/* 170 */     if (!(getWorldConfig(event.getWorld())).buildPermissions)
/*     */       return; 
/* 172 */     Object rootCause = event.getCause().getRootCause();
/*     */ 
/*     */     
/* 175 */     Player player = (Player)rootCause;
/* 176 */     EntityType type = event.getEntity().getType();
/*     */     
/* 178 */     if (rootCause instanceof Player && !hasBuildPermission((CommandSender)player, "entity." + type.name().toLowerCase() + ".damage") && 
/* 179 */       !hasBuildPermission((CommandSender)player, "entity.damage." + type.name().toLowerCase())) {
/* 180 */       tellErrorMessage((CommandSender)player, event.getWorld());
/* 181 */       event.setCancelled(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onUseItem(UseItemEvent event) {
/* 188 */     if (!(getWorldConfig(event.getWorld())).buildPermissions)
/*     */       return; 
/* 190 */     Object rootCause = event.getCause().getRootCause();
/*     */     
/* 192 */     if (rootCause instanceof Player) {
/* 193 */       Player player = (Player)rootCause;
/* 194 */       Material material = event.getItemStack().getType();
/*     */       
/* 196 */       if (material.isBlock()) {
/*     */         return;
/*     */       }
/*     */       
/* 200 */       if (!hasBuildPermission((CommandSender)player, "item." + material.name().toLowerCase() + ".use") && 
/* 201 */         !hasBuildPermission((CommandSender)player, "item.use." + material.name().toLowerCase())) {
/* 202 */         tellErrorMessage((CommandSender)player, event.getWorld());
/* 203 */         event.setCancelled(true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\BuildPermissionListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */