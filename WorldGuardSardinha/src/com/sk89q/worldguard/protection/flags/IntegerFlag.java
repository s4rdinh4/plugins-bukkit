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
/*    */ public class IntegerFlag
/*    */   extends Flag<Integer>
/*    */ {
/*    */   public IntegerFlag(String name, RegionGroup defaultGroup) {
/* 32 */     super(name, defaultGroup);
/*    */   }
/*    */   
/*    */   public IntegerFlag(String name) {
/* 36 */     super(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public Integer parseInput(WorldGuardPlugin plugin, CommandSender sender, String input) throws InvalidFlagFormat {
/* 41 */     input = input.trim();
/*    */     
/*    */     try {
/* 44 */       return Integer.valueOf(Integer.parseInt(input));
/* 45 */     } catch (NumberFormatException e) {
/* 46 */       throw new InvalidFlagFormat("Not a number: " + input);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Integer unmarshal(Object o) {
/* 52 */     if (o instanceof Integer)
/* 53 */       return (Integer)o; 
/* 54 */     if (o instanceof Number) {
/* 55 */       return Integer.valueOf(((Number)o).intValue());
/*    */     }
/* 57 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object marshal(Integer o) {
/* 63 */     return o;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\flags\IntegerFlag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */