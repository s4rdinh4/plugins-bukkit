/*    */ package com.sk89q.worldguard.bukkit.commands;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.google.common.util.concurrent.ListenableFuture;
/*    */ import com.google.common.util.concurrent.MoreExecutors;
/*    */ import java.util.Timer;
/*    */ import java.util.concurrent.Executor;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FutureProgressListener
/*    */   implements Runnable
/*    */ {
/* 33 */   private static final Timer timer = new Timer();
/*    */   
/*    */   private static final int MESSAGE_DELAY = 1000;
/*    */   private final MessageTimerTask task;
/*    */   
/*    */   public FutureProgressListener(CommandSender sender, String message) {
/* 39 */     Preconditions.checkNotNull(sender);
/* 40 */     Preconditions.checkNotNull(message);
/*    */     
/* 42 */     this.task = new MessageTimerTask(sender, ChatColor.GRAY + message);
/* 43 */     timer.schedule(this.task, 1000L);
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 48 */     this.task.cancel();
/*    */   }
/*    */   
/*    */   public static void addProgressListener(ListenableFuture<?> future, CommandSender sender, String message) {
/* 52 */     future.addListener(new FutureProgressListener(sender, message), (Executor)MoreExecutors.sameThreadExecutor());
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\commands\FutureProgressListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */