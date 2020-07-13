/*    */ package com.sk89q.worldguard.bukkit.commands;
/*    */ 
/*    */ import com.sk89q.minecraft.util.commands.Command;
/*    */ import com.sk89q.minecraft.util.commands.CommandContext;
/*    */ import com.sk89q.minecraft.util.commands.NestedCommand;
/*    */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*    */ import com.sk89q.worldguard.bukkit.commands.region.MemberCommands;
/*    */ import com.sk89q.worldguard.bukkit.commands.region.RegionCommands;
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
/*    */ public class ProtectionCommands
/*    */ {
/*    */   private final WorldGuardPlugin plugin;
/*    */   
/*    */   public ProtectionCommands(WorldGuardPlugin plugin) {
/* 36 */     this.plugin = plugin;
/*    */   }
/*    */   
/*    */   @Command(aliases = {"region", "regions", "rg"}, desc = "Region management commands")
/*    */   @NestedCommand({RegionCommands.class, MemberCommands.class})
/*    */   public void region(CommandContext args, CommandSender sender) {}
/*    */   
/*    */   @Command(aliases = {"worldguard", "wg"}, desc = "WorldGuard commands")
/*    */   @NestedCommand({WorldGuardCommands.class})
/*    */   public void worldGuard(CommandContext args, CommandSender sender) {}
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\commands\ProtectionCommands.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */