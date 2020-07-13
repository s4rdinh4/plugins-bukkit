/*     */ package com.sk89q.worldguard.bukkit.commands;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.util.concurrent.FutureCallback;
/*     */ import com.google.common.util.concurrent.Futures;
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.util.paste.EngineHubPaste;
/*     */ import java.net.URL;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.BlockCommandSender;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
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
/*     */ public final class CommandUtils
/*     */ {
/*  44 */   private static final Logger log = Logger.getLogger(CommandUtils.class.getCanonicalName());
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
/*     */   public static String replaceColorMacros(String str) {
/*  58 */     str = str.replace("`r", ChatColor.RED.toString());
/*  59 */     str = str.replace("`R", ChatColor.DARK_RED.toString());
/*     */     
/*  61 */     str = str.replace("`y", ChatColor.YELLOW.toString());
/*  62 */     str = str.replace("`Y", ChatColor.GOLD.toString());
/*     */     
/*  64 */     str = str.replace("`g", ChatColor.GREEN.toString());
/*  65 */     str = str.replace("`G", ChatColor.DARK_GREEN.toString());
/*     */     
/*  67 */     str = str.replace("`c", ChatColor.AQUA.toString());
/*  68 */     str = str.replace("`C", ChatColor.DARK_AQUA.toString());
/*     */     
/*  70 */     str = str.replace("`b", ChatColor.BLUE.toString());
/*  71 */     str = str.replace("`B", ChatColor.DARK_BLUE.toString());
/*     */     
/*  73 */     str = str.replace("`p", ChatColor.LIGHT_PURPLE.toString());
/*  74 */     str = str.replace("`P", ChatColor.DARK_PURPLE.toString());
/*     */     
/*  76 */     str = str.replace("`0", ChatColor.BLACK.toString());
/*  77 */     str = str.replace("`1", ChatColor.DARK_GRAY.toString());
/*  78 */     str = str.replace("`2", ChatColor.GRAY.toString());
/*  79 */     str = str.replace("`w", ChatColor.WHITE.toString());
/*     */     
/*  81 */     str = str.replace("`k", ChatColor.MAGIC.toString());
/*     */     
/*  83 */     str = str.replace("`l", ChatColor.BOLD.toString());
/*  84 */     str = str.replace("`m", ChatColor.STRIKETHROUGH.toString());
/*  85 */     str = str.replace("`n", ChatColor.UNDERLINE.toString());
/*  86 */     str = str.replace("`o", ChatColor.ITALIC.toString());
/*     */     
/*  88 */     str = str.replace("`x", ChatColor.RESET.toString());
/*     */ 
/*     */ 
/*     */     
/*  92 */     str = str.replace("&c", ChatColor.RED.toString());
/*  93 */     str = str.replace("&4", ChatColor.DARK_RED.toString());
/*     */     
/*  95 */     str = str.replace("&e", ChatColor.YELLOW.toString());
/*  96 */     str = str.replace("&6", ChatColor.GOLD.toString());
/*     */     
/*  98 */     str = str.replace("&a", ChatColor.GREEN.toString());
/*  99 */     str = str.replace("&2", ChatColor.DARK_GREEN.toString());
/*     */     
/* 101 */     str = str.replace("&b", ChatColor.AQUA.toString());
/* 102 */     str = str.replace("&3", ChatColor.DARK_AQUA.toString());
/*     */     
/* 104 */     str = str.replace("&9", ChatColor.BLUE.toString());
/* 105 */     str = str.replace("&1", ChatColor.DARK_BLUE.toString());
/*     */     
/* 107 */     str = str.replace("&d", ChatColor.LIGHT_PURPLE.toString());
/* 108 */     str = str.replace("&5", ChatColor.DARK_PURPLE.toString());
/*     */     
/* 110 */     str = str.replace("&0", ChatColor.BLACK.toString());
/* 111 */     str = str.replace("&8", ChatColor.DARK_GRAY.toString());
/* 112 */     str = str.replace("&7", ChatColor.GRAY.toString());
/* 113 */     str = str.replace("&f", ChatColor.WHITE.toString());
/*     */     
/* 115 */     str = str.replace("&k", ChatColor.MAGIC.toString());
/*     */     
/* 117 */     str = str.replace("&l", ChatColor.BOLD.toString());
/* 118 */     str = str.replace("&m", ChatColor.STRIKETHROUGH.toString());
/* 119 */     str = str.replace("&n", ChatColor.UNDERLINE.toString());
/* 120 */     str = str.replace("&o", ChatColor.ITALIC.toString());
/*     */     
/* 122 */     str = str.replace("&x", ChatColor.RESET.toString());
/* 123 */     str = str.replace("&r", ChatColor.RESET.toString());
/*     */     
/* 125 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getOwnerName(@Nullable Object owner) {
/* 136 */     if (owner == null)
/* 137 */       return "?"; 
/* 138 */     if (owner instanceof Player)
/* 139 */       return ((Player)owner).getName(); 
/* 140 */     if (owner instanceof org.bukkit.command.ConsoleCommandSender)
/* 141 */       return "*CONSOLE*"; 
/* 142 */     if (owner instanceof BlockCommandSender) {
/* 143 */       return ((BlockCommandSender)owner).getBlock().getLocation().toString();
/*     */     }
/* 145 */     return "?";
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
/*     */   public static Function<String, ?> messageFunction(final CommandSender sender) {
/* 157 */     return new Function<String, Object>()
/*     */       {
/*     */         public Object apply(@Nullable String s) {
/* 160 */           sender.sendMessage(s);
/* 161 */           return null;
/*     */         }
/*     */       };
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
/*     */   public static void pastebin(WorldGuardPlugin plugin, final CommandSender sender, String content, final String successMessage) {
/* 176 */     ListenableFuture<URL> future = (new EngineHubPaste()).paste(content);
/*     */     
/* 178 */     AsyncCommandHelper.wrap(future, plugin, sender)
/* 179 */       .registerWithSupervisor("Submitting content to a pastebin service...")
/* 180 */       .sendMessageAfterDelay("(Please wait... sending output to pastebin...)");
/*     */     
/* 182 */     Futures.addCallback(future, new FutureCallback<URL>()
/*     */         {
/*     */           public void onSuccess(URL url) {
/* 185 */             sender.sendMessage(ChatColor.YELLOW + String.format(successMessage, new Object[] { url }));
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Throwable throwable) {
/* 190 */             CommandUtils.log.log(Level.WARNING, "Failed to submit pastebin", throwable);
/* 191 */             sender.sendMessage(ChatColor.RED + "Failed to submit to a pastebin. Please see console for the error.");
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\commands\CommandUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */