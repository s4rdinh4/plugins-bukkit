/*     */ package br.com.sgcraft.arenax1;
/*     */ 
/*     */ import java.io.File;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ArenaConfig
/*     */ {
/*     */   private final FileConfiguration config;
/*     */   private final int startWaitTime;
/*     */   private final int endindTime;
/*     */   private final int defaultRemainingTime;
/*     */   private final int defaultAcceptedWait;
/*     */   private final int defaultExpireTime;
/*     */   private final String language;
/*  22 */   private final double _VERSION = 1.1D;
/*     */   
/*     */   private final int guiItem;
/*     */   
/*     */   public FileConfiguration getConfig() {
/*  27 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public ArenaConfig(ArenaX1 plugin) {
/*  32 */     plugin.saveDefaultConfig();
/*  33 */     this.config = plugin.getConfig();
/*  34 */     if (this.config.getDouble("Config.VERSION") != 1.1D) {
/*     */       
/*  36 */       File file = new File(plugin.getDataFolder(), "config.yml");
/*  37 */       file.delete();
/*  38 */       plugin.saveDefaultConfig();
/*  39 */       Bukkit.getLogger().info("---------------------------------");
/*  40 */       Bukkit.getLogger().info("A NEW CONFIG FILE HAS GENERATED!");
/*  41 */       Bukkit.getLogger().info("---------------------------------");
/*     */     } 
/*     */     
/*  44 */     this.startWaitTime = getInt(5, "Config.START_WAIT_TIME");
/*  45 */     this.endindTime = getInt(15, "Config.ENDING_TIME");
/*  46 */     this.defaultRemainingTime = getInt(500, "Config.DEFAULT_REMAINING_TIME");
/*  47 */     this.defaultAcceptedWait = getInt(5, "Config.DEFAULT_ACCEPTED_WAIT");
/*  48 */     this.defaultExpireTime = getInt(60, "Config.DEFAULT_EXPIRE_TIME");
/*  49 */     this.language = getString("EN-US", "Config.LANGUAGE");
/*  50 */     this.guiItem = getInt(283, "Config.GUI_ITEM");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(int defaultValue, String path) {
/*  55 */     if (this.config.contains(path))
/*     */     {
/*  57 */       return this.config.getInt(path);
/*     */     }
/*     */     
/*  60 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(String defaultValue, String path) {
/*  66 */     if (this.config.contains(path))
/*     */     {
/*  68 */       return this.config.getString(path);
/*     */     }
/*     */     
/*  71 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStartWaitTime() {
/*  77 */     return this.startWaitTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEndindTime() {
/*  82 */     return this.endindTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDefaultRemainingTime() {
/*  87 */     return this.defaultRemainingTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDefaultAcceptedWait() {
/*  92 */     return this.defaultAcceptedWait;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDefaultExpireTime() {
/*  97 */     return this.defaultExpireTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLanguage() {
/* 102 */     return this.language;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGuiItem() {
/* 107 */     return this.guiItem;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\ArenaX1-0.3.2.jar!\br\com\tinycraft\arenax1\ArenaConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */