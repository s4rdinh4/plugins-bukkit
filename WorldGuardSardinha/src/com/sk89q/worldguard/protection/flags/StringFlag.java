/*    */ package com.sk89q.worldguard.protection.flags;
/*    */ 
/*    */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*    */ import javax.annotation.Nullable;
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
/*    */ public class StringFlag
/*    */   extends Flag<String>
/*    */ {
/*    */   private final String defaultValue;
/*    */   
/*    */   public StringFlag(String name) {
/* 36 */     super(name);
/* 37 */     this.defaultValue = null;
/*    */   }
/*    */   
/*    */   public StringFlag(String name, String defaultValue) {
/* 41 */     super(name);
/* 42 */     this.defaultValue = defaultValue;
/*    */   }
/*    */   
/*    */   public StringFlag(String name, RegionGroup defaultGroup) {
/* 46 */     super(name, defaultGroup);
/* 47 */     this.defaultValue = null;
/*    */   }
/*    */   
/*    */   public StringFlag(String name, RegionGroup defaultGroup, String defaultValue) {
/* 51 */     super(name, defaultGroup);
/* 52 */     this.defaultValue = defaultValue;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public String getDefault() {
/* 58 */     return this.defaultValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public String parseInput(WorldGuardPlugin plugin, CommandSender sender, String input) throws InvalidFlagFormat {
/* 63 */     return input.replaceAll("(?!\\\\)\\\\n", "\n").replaceAll("\\\\\\\\n", "\\n");
/*    */   }
/*    */ 
/*    */   
/*    */   public String unmarshal(Object o) {
/* 68 */     if (o instanceof String) {
/* 69 */       return (String)o;
/*    */     }
/* 71 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object marshal(String o) {
/* 77 */     return o;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\flags\StringFlag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */