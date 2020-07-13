/*    */ package com.sk89q.worldguard.bukkit.commands;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.util.TimerTask;
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
/*    */ 
/*    */ public class MessageTimerTask
/*    */   extends TimerTask
/*    */ {
/*    */   private final CommandSender sender;
/*    */   private final String message;
/*    */   
/*    */   MessageTimerTask(CommandSender sender, String message) {
/* 34 */     Preconditions.checkNotNull(sender);
/* 35 */     Preconditions.checkNotNull(message);
/*    */     
/* 37 */     this.sender = sender;
/* 38 */     this.message = message;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 43 */     this.sender.sendMessage(this.message);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\commands\MessageTimerTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */