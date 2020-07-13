/*     */ package com.sk89q.worldguard.bukkit.commands;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.util.concurrent.FutureCallback;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bukkit.ChatColor;
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
/*     */ public class MessageFutureCallback<V>
/*     */   implements FutureCallback<V>
/*     */ {
/*     */   private final WorldGuardPlugin plugin;
/*     */   private final CommandSender sender;
/*     */   @Nullable
/*     */   private final String success;
/*     */   @Nullable
/*     */   private final String failure;
/*     */   
/*     */   private MessageFutureCallback(WorldGuardPlugin plugin, CommandSender sender, @Nullable String success, @Nullable String failure) {
/*  41 */     this.plugin = plugin;
/*  42 */     this.sender = sender;
/*  43 */     this.success = success;
/*  44 */     this.failure = failure;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSuccess(@Nullable V v) {
/*  49 */     if (this.success != null) {
/*  50 */       this.sender.sendMessage(ChatColor.YELLOW + this.success);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onFailure(@Nullable Throwable throwable) {
/*  56 */     String failure = (this.failure != null) ? this.failure : "An error occurred";
/*  57 */     this.sender.sendMessage(ChatColor.RED + failure + ": " + this.plugin.convertThrowable(throwable));
/*     */   }
/*     */   
/*     */   public static class Builder {
/*     */     private final WorldGuardPlugin plugin;
/*     */     private final CommandSender sender;
/*     */     @Nullable
/*     */     private String success;
/*     */     @Nullable
/*     */     private String failure;
/*     */     
/*     */     public Builder(WorldGuardPlugin plugin, CommandSender sender) {
/*  69 */       Preconditions.checkNotNull(plugin);
/*  70 */       Preconditions.checkNotNull(sender);
/*     */       
/*  72 */       this.plugin = plugin;
/*  73 */       this.sender = sender;
/*     */     }
/*     */     
/*     */     public Builder onSuccess(@Nullable String message) {
/*  77 */       this.success = message;
/*  78 */       return this;
/*     */     }
/*     */     
/*     */     public Builder onFailure(@Nullable String message) {
/*  82 */       this.failure = message;
/*  83 */       return this;
/*     */     }
/*     */     
/*     */     public <V> MessageFutureCallback<V> build() {
/*  87 */       return new MessageFutureCallback<V>(this.plugin, this.sender, this.success, this.failure);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> MessageFutureCallback<V> createRegionLoadCallback(WorldGuardPlugin plugin, CommandSender sender) {
/*  94 */     return (new Builder(plugin, sender)).onSuccess("Successfully load the region data.").build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> MessageFutureCallback<V> createRegionSaveCallback(WorldGuardPlugin plugin, CommandSender sender) {
/* 100 */     return (new Builder(plugin, sender)).onSuccess("Successfully saved the region data.").build();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\commands\MessageFutureCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */