/*     */ package com.sk89q.worldguard.bukkit.commands;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.util.concurrent.Futures;
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.util.task.FutureForwardingTask;
/*     */ import com.sk89q.worldguard.util.task.Task;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bukkit.World;
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
/*     */ public class AsyncCommandHelper
/*     */ {
/*     */   private final ListenableFuture<?> future;
/*     */   private final WorldGuardPlugin plugin;
/*     */   private final CommandSender sender;
/*     */   @Nullable
/*     */   private Object[] formatArgs;
/*     */   
/*     */   private AsyncCommandHelper(ListenableFuture<?> future, WorldGuardPlugin plugin, CommandSender sender) {
/*  42 */     Preconditions.checkNotNull(future);
/*  43 */     Preconditions.checkNotNull(plugin);
/*  44 */     Preconditions.checkNotNull(sender);
/*     */     
/*  46 */     this.future = future;
/*  47 */     this.plugin = plugin;
/*  48 */     this.sender = sender;
/*     */   }
/*     */   
/*     */   public AsyncCommandHelper formatUsing(Object... args) {
/*  52 */     this.formatArgs = args;
/*  53 */     return this;
/*     */   }
/*     */   
/*     */   private String format(String message) {
/*  57 */     if (this.formatArgs != null) {
/*  58 */       return String.format(message, this.formatArgs);
/*     */     }
/*  60 */     return message;
/*     */   }
/*     */ 
/*     */   
/*     */   public AsyncCommandHelper registerWithSupervisor(String description) {
/*  65 */     this.plugin.getSupervisor().monitor(
/*  66 */         (Task)FutureForwardingTask.create(this.future, 
/*  67 */           format(description), this.sender));
/*  68 */     return this;
/*     */   }
/*     */   
/*     */   public AsyncCommandHelper sendMessageAfterDelay(String message) {
/*  72 */     FutureProgressListener.addProgressListener(this.future, this.sender, format(message));
/*  73 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public AsyncCommandHelper thenRespondWith(String success, String failure) {
/*  78 */     Futures.addCallback(this.future, (new MessageFutureCallback.Builder(this.plugin, this.sender))
/*     */ 
/*     */         
/*  81 */         .onSuccess(format(success))
/*  82 */         .onFailure(format(failure))
/*  83 */         .build());
/*  84 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public AsyncCommandHelper thenTellErrorsOnly(String failure) {
/*  89 */     Futures.addCallback(this.future, (new MessageFutureCallback.Builder(this.plugin, this.sender))
/*     */ 
/*     */         
/*  92 */         .onFailure(format(failure))
/*  93 */         .build());
/*  94 */     return this;
/*     */   }
/*     */   
/*     */   public AsyncCommandHelper forRegionDataLoad(World world, boolean silent) {
/*  98 */     Preconditions.checkNotNull(world);
/*     */     
/* 100 */     formatUsing(new Object[] { world.getName() });
/* 101 */     registerWithSupervisor("Loading region data for '%s'");
/* 102 */     if (silent) {
/* 103 */       thenTellErrorsOnly("Failed to load regions '%s'");
/*     */     } else {
/* 105 */       sendMessageAfterDelay("(Please wait... loading the region data for '%s')");
/* 106 */       thenRespondWith("Loaded region data for '%s'", "Failed to load regions '%s'");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 111 */     return this;
/*     */   }
/*     */   
/*     */   public AsyncCommandHelper forRegionDataSave(World world, boolean silent) {
/* 115 */     Preconditions.checkNotNull(world);
/*     */     
/* 117 */     formatUsing(new Object[] { world.getName() });
/* 118 */     registerWithSupervisor("Saving region data for '%s'");
/* 119 */     if (silent) {
/* 120 */       thenTellErrorsOnly("Failed to save regions '%s'");
/*     */     } else {
/* 122 */       sendMessageAfterDelay("(Please wait... saving the region data for '%s')");
/* 123 */       thenRespondWith("Saved region data for '%s'", "Failed to load regions '%s'");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 128 */     return this;
/*     */   }
/*     */   
/*     */   public static AsyncCommandHelper wrap(ListenableFuture<?> future, WorldGuardPlugin plugin, CommandSender sender) {
/* 132 */     return new AsyncCommandHelper(future, plugin, sender);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\commands\AsyncCommandHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */