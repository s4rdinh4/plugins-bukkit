/*    */ package com.sk89q.worldguard.bukkit.util.logging;
/*    */ 
/*    */ import java.util.logging.Handler;
/*    */ import java.util.logging.LogRecord;
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
/*    */ public class LoggerToChatHandler
/*    */   extends Handler
/*    */ {
/*    */   private CommandSender player;
/*    */   
/*    */   public LoggerToChatHandler(CommandSender player) {
/* 43 */     this.player = player;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void flush() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void publish(LogRecord record) {
/* 65 */     this.player.sendMessage(ChatColor.GRAY + record.getLevel().getName() + ": " + ChatColor.WHITE + record
/* 66 */         .getMessage());
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukki\\util\logging\LoggerToChatHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */