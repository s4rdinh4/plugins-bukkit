/*    */ package com.sk89q.worldguard.protection.flags;
/*    */ 
/*    */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
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
/*    */ public class CommandStringFlag
/*    */   extends Flag<String>
/*    */ {
/*    */   public CommandStringFlag(String name, RegionGroup defaultGroup) {
/* 32 */     super(name, defaultGroup);
/*    */   }
/*    */   
/*    */   public CommandStringFlag(String name) {
/* 36 */     super(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public String parseInput(WorldGuardPlugin plugin, CommandSender sender, String input) throws InvalidFlagFormat {
/* 41 */     input = input.trim();
/* 42 */     if (!input.startsWith("/")) {
/* 43 */       input = "/" + input;
/*    */     }
/* 45 */     return input.toLowerCase();
/*    */   }
/*    */ 
/*    */   
/*    */   public String unmarshal(Object o) {
/* 50 */     if (o instanceof String) {
/* 51 */       return (String)o;
/*    */     }
/* 53 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object marshal(String o) {
/* 59 */     return o;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\flags\CommandStringFlag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */