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
/*    */ public class BooleanFlag
/*    */   extends Flag<Boolean>
/*    */ {
/*    */   public BooleanFlag(String name, RegionGroup defaultGroup) {
/* 32 */     super(name, defaultGroup);
/*    */   }
/*    */   
/*    */   public BooleanFlag(String name) {
/* 36 */     super(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public Boolean parseInput(WorldGuardPlugin plugin, CommandSender sender, String input) throws InvalidFlagFormat {
/* 41 */     input = input.trim();
/*    */     
/* 43 */     if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("yes") || input
/* 44 */       .equalsIgnoreCase("on") || input
/* 45 */       .equalsIgnoreCase("1"))
/* 46 */       return Boolean.valueOf(true); 
/* 47 */     if (input.equalsIgnoreCase("false") || input.equalsIgnoreCase("no") || input
/* 48 */       .equalsIgnoreCase("off") || input
/* 49 */       .equalsIgnoreCase("0")) {
/* 50 */       return Boolean.valueOf(false);
/*    */     }
/* 52 */     throw new InvalidFlagFormat("Not a yes/no value: " + input);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Boolean unmarshal(Object o) {
/* 58 */     if (o instanceof Boolean) {
/* 59 */       return (Boolean)o;
/*    */     }
/* 61 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object marshal(Boolean o) {
/* 67 */     return o;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\flags\BooleanFlag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */