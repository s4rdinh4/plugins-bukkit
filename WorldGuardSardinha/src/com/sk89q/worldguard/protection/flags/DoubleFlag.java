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
/*    */ public class DoubleFlag
/*    */   extends Flag<Double>
/*    */ {
/*    */   public DoubleFlag(String name, RegionGroup defaultGroup) {
/* 32 */     super(name, defaultGroup);
/*    */   }
/*    */   
/*    */   public DoubleFlag(String name) {
/* 36 */     super(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public Double parseInput(WorldGuardPlugin plugin, CommandSender sender, String input) throws InvalidFlagFormat {
/* 41 */     input = input.trim();
/*    */     
/*    */     try {
/* 44 */       return Double.valueOf(Double.parseDouble(input));
/* 45 */     } catch (NumberFormatException e) {
/* 46 */       throw new InvalidFlagFormat("Not a number: " + input);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Double unmarshal(Object o) {
/* 52 */     if (o instanceof Double)
/* 53 */       return (Double)o; 
/* 54 */     if (o instanceof Number) {
/* 55 */       return Double.valueOf(((Number)o).doubleValue());
/*    */     }
/* 57 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object marshal(Double o) {
/* 63 */     return o;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\flags\DoubleFlag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */