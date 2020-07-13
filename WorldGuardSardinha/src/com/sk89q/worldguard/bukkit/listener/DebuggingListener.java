/*     */ package com.sk89q.worldguard.bukkit.listener;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.bukkit.event.block.BreakBlockEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.block.PlaceBlockEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.block.UseBlockEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.entity.DestroyEntityEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.entity.SpawnEntityEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.entity.UseEntityEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.inventory.UseItemEvent;
/*     */ import java.util.List;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
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
/*     */ public class DebuggingListener
/*     */   extends AbstractListener
/*     */ {
/*     */   private final Logger logger;
/*     */   
/*     */   public DebuggingListener(WorldGuardPlugin plugin, Logger logger) {
/*  54 */     super(plugin);
/*  55 */     Preconditions.checkNotNull(logger);
/*  56 */     this.logger = logger;
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR)
/*     */   public void onPlaceBlock(PlaceBlockEvent event) {
/*  61 */     StringBuilder builder = new StringBuilder();
/*  62 */     builder.append("PLACE");
/*  63 */     builder.append(" ");
/*  64 */     builder.append("").append(event.getEffectiveMaterial());
/*  65 */     builder.append(" ");
/*  66 */     builder.append("@").append(toBlockString(event.getBlocks()));
/*  67 */     builder.append(" ");
/*  68 */     builder.append("[").append(event.getCause()).append("]");
/*  69 */     builder.append(" ");
/*  70 */     builder.append(":").append(getEventName(event.getOriginalEvent()));
/*  71 */     if (event.getResult() != Event.Result.DEFAULT) {
/*  72 */       builder.append(" [").append(event.getResult()).append("]");
/*     */     }
/*  74 */     this.logger.info(builder.toString());
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR)
/*     */   public void onBreakBlock(BreakBlockEvent event) {
/*  79 */     StringBuilder builder = new StringBuilder();
/*  80 */     builder.append("DIG");
/*  81 */     builder.append(" ");
/*  82 */     builder.append("").append(event.getEffectiveMaterial());
/*  83 */     builder.append(" ");
/*  84 */     builder.append("[").append(event.getCause()).append("]");
/*  85 */     builder.append(" ");
/*  86 */     builder.append("@").append(toBlockString(event.getBlocks()));
/*  87 */     builder.append(" ");
/*  88 */     builder.append(":").append(getEventName(event.getOriginalEvent()));
/*  89 */     if (event.getResult() != Event.Result.DEFAULT) {
/*  90 */       builder.append(" [").append(event.getResult()).append("]");
/*     */     }
/*  92 */     this.logger.info(builder.toString());
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR)
/*     */   public void onUseBlock(UseBlockEvent event) {
/*  97 */     StringBuilder builder = new StringBuilder();
/*  98 */     builder.append("INTERACT");
/*  99 */     builder.append(" ");
/* 100 */     builder.append("").append(event.getEffectiveMaterial());
/* 101 */     builder.append(" ");
/* 102 */     builder.append("[").append(event.getCause()).append("]");
/* 103 */     builder.append(" ");
/* 104 */     builder.append("@").append(toBlockString(event.getBlocks()));
/* 105 */     builder.append(" ");
/* 106 */     builder.append(":").append(getEventName(event.getOriginalEvent()));
/* 107 */     if (event.getResult() != Event.Result.DEFAULT) {
/* 108 */       builder.append(" [").append(event.getResult()).append("]");
/*     */     }
/* 110 */     this.logger.info(builder.toString());
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR)
/*     */   public void onSpawnEntity(SpawnEntityEvent event) {
/* 115 */     StringBuilder builder = new StringBuilder();
/* 116 */     builder.append("SPAWN");
/* 117 */     builder.append(" ");
/* 118 */     builder.append("").append(event.getEffectiveType());
/* 119 */     builder.append(" ");
/* 120 */     builder.append("[").append(event.getCause()).append("]");
/* 121 */     builder.append(" ");
/* 122 */     builder.append("@").append(toBlockString(event.getTarget()));
/* 123 */     builder.append(" ");
/* 124 */     builder.append(":").append(getEventName(event.getOriginalEvent()));
/* 125 */     if (event.getResult() != Event.Result.DEFAULT) {
/* 126 */       builder.append(" [").append(event.getResult()).append("]");
/*     */     }
/* 128 */     this.logger.info(builder.toString());
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR)
/*     */   public void onDestroyEntity(DestroyEntityEvent event) {
/* 133 */     StringBuilder builder = new StringBuilder();
/* 134 */     builder.append("DESTROY");
/* 135 */     builder.append(" ");
/* 136 */     builder.append("").append(event.getEntity().getType());
/* 137 */     builder.append(" ");
/* 138 */     builder.append("[").append(event.getCause()).append("]");
/* 139 */     builder.append(" ");
/* 140 */     builder.append("@").append(toBlockString(event.getTarget()));
/* 141 */     builder.append(" ");
/* 142 */     builder.append(":").append(getEventName(event.getOriginalEvent()));
/* 143 */     if (event.getResult() != Event.Result.DEFAULT) {
/* 144 */       builder.append(" [").append(event.getResult()).append("]");
/*     */     }
/* 146 */     this.logger.info(builder.toString());
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR)
/*     */   public void onUseEntity(UseEntityEvent event) {
/* 151 */     StringBuilder builder = new StringBuilder();
/* 152 */     builder.append("INTERACT");
/* 153 */     builder.append(" ");
/* 154 */     builder.append("").append(event.getEntity().getType());
/* 155 */     builder.append(" ");
/* 156 */     builder.append("[").append(event.getCause()).append("]");
/* 157 */     builder.append(" ");
/* 158 */     builder.append("@").append(toBlockString(event.getTarget()));
/* 159 */     builder.append(" ");
/* 160 */     builder.append(":").append(getEventName(event.getOriginalEvent()));
/* 161 */     if (event.getResult() != Event.Result.DEFAULT) {
/* 162 */       builder.append(" [").append(event.getResult()).append("]");
/*     */     }
/* 164 */     this.logger.info(builder.toString());
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR)
/*     */   public void onUseItem(UseItemEvent event) {
/* 169 */     StringBuilder builder = new StringBuilder();
/* 170 */     builder.append("USE");
/* 171 */     builder.append(" ");
/* 172 */     builder.append("").append(event.getItemStack().getType());
/* 173 */     builder.append(" ");
/* 174 */     builder.append("[").append(event.getCause()).append("]");
/* 175 */     builder.append(" ");
/* 176 */     builder.append("@").append(event.getWorld().getName());
/* 177 */     builder.append(" ");
/* 178 */     builder.append(":").append(getEventName(event.getOriginalEvent()));
/* 179 */     if (event.getResult() != Event.Result.DEFAULT) {
/* 180 */       builder.append(" [").append(event.getResult()).append("]");
/*     */     }
/* 182 */     this.logger.info(builder.toString());
/*     */   }
/*     */   
/*     */   private static String toBlockString(Location location) {
/* 186 */     return location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ();
/*     */   }
/*     */   
/*     */   private static String toBlockString(List<Block> blocks) {
/* 190 */     StringBuilder builder = new StringBuilder();
/* 191 */     boolean first = true;
/* 192 */     for (Block block : blocks) {
/* 193 */       if (!first) {
/* 194 */         builder.append("|");
/*     */       }
/* 196 */       builder.append(block.getX()).append(",").append(block.getY()).append(",").append(block.getZ());
/* 197 */       first = false;
/*     */     } 
/* 199 */     return builder.toString();
/*     */   }
/*     */   
/*     */   private String getEventName(@Nullable Event event) {
/* 203 */     return (event != null) ? event.getEventName() : "?";
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\DebuggingListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */