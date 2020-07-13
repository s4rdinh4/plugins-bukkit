/*    */ package com.sk89q.worldguard.bukkit.listener;
/*    */ 
/*    */ import com.sk89q.worldguard.bukkit.BukkitUtil;
/*    */ import com.sk89q.worldguard.bukkit.ConfigurationManager;
/*    */ import com.sk89q.worldguard.bukkit.WorldConfiguration;
/*    */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*    */ import com.sk89q.worldguard.bukkit.event.inventory.UseItemEvent;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.potion.Potion;
/*    */ import org.bukkit.potion.PotionEffect;
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
/*    */ public class BlockedPotionsListener
/*    */   extends AbstractListener
/*    */ {
/*    */   public BlockedPotionsListener(WorldGuardPlugin plugin) {
/* 46 */     super(plugin);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onItemInteract(UseItemEvent event) {
/* 51 */     ConfigurationManager cfg = getPlugin().getGlobalStateManager();
/* 52 */     WorldConfiguration wcfg = cfg.get(event.getWorld());
/* 53 */     ItemStack item = event.getItemStack();
/*    */ 
/*    */     
/* 56 */     if (item.getType() != Material.POTION || BukkitUtil.isWaterPotion(item)) {
/*    */       return;
/*    */     }
/*    */     
/* 60 */     if (!wcfg.blockPotions.isEmpty()) {
/* 61 */       PotionEffect blockedEffect = null;
/*    */       
/* 63 */       Potion potion = Potion.fromDamage(BukkitUtil.getPotionEffectBits(item));
/*    */ 
/*    */       
/* 66 */       for (PotionEffect effect : potion.getEffects()) {
/* 67 */         if (wcfg.blockPotions.contains(effect.getType())) {
/* 68 */           blockedEffect = effect;
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/* 73 */       if (blockedEffect != null) {
/* 74 */         Player player = event.getCause().getFirstPlayer();
/*    */         
/* 76 */         if (player != null) {
/* 77 */           if (getPlugin().hasPermission((CommandSender)player, "worldguard.override.potions")) {
/* 78 */             if (potion.isSplash() && wcfg.blockPotionsAlways) {
/* 79 */               player.sendMessage(ChatColor.RED + "Sorry, potions with " + blockedEffect
/* 80 */                   .getType().getName() + " can't be thrown, " + "even if you have a permission to bypass it, " + "due to limitations (and because overly-reliable potion blocking is on).");
/*    */ 
/*    */               
/* 83 */               event.setCancelled(true);
/*    */             } 
/*    */           } else {
/* 86 */             player.sendMessage(ChatColor.RED + "Sorry, potions with " + blockedEffect
/* 87 */                 .getType().getName() + " are presently disabled.");
/* 88 */             event.setCancelled(true);
/*    */           } 
/*    */         } else {
/* 91 */           event.setCancelled(true);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\BlockedPotionsListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */