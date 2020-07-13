/*     */ package br.com.sgcraft.arenax1.gui;
import br.com.sgcraft.arenax1.arena.ArenaManager;
import br.com.sgcraft.arenax1.executor.ArenaExecutor;
import br.com.sgcraft.arenax1.invite.InviteManager;

/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.SkullMeta;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GUI
/*     */   implements Listener
/*     */ {
/*     */   private final ArenaManager arenaManager;
/*     */   private final InviteManager inviteManager;
/*     */   private final ArenaExecutor arenaExecutor;
/*     */   
/*     */   public GUI(ArenaManager arenaManager, InviteManager inviteManager, ArenaExecutor arenaExecutor) {
/*  32 */     this.arenaExecutor = arenaExecutor;
/*  33 */     this.arenaManager = arenaManager;
/*  34 */     this.inviteManager = inviteManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public void openGui(Player author) {
/*  39 */     Inventory inventory = Bukkit.createInventory((InventoryHolder)author, 54, "§eArenaX1 GUI");
/*     */     
/*  41 */     int m = 0;
/*  42 */     for (Player player : Bukkit.getOnlinePlayers()) {
/*     */       
/*  44 */       if (!author.getName().equals(player.getName()))
/*     */       {
/*     */ 
/*     */         
/*  48 */         if (this.arenaExecutor.getPlayerArena(player, false) == null) {
/*     */ 
/*     */ 
/*     */           
/*  52 */           ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
/*  53 */           SkullMeta skullMeta = (SkullMeta)skull.getItemMeta();
/*  54 */           skullMeta.setOwner(player.getName());
/*  55 */           skullMeta.setDisplayName("§aDuel with " + player.getName());
/*     */           
/*  57 */           int inviteRelation = this.inviteManager.getInviteRelation(author, player);
/*     */           
/*  59 */           if (inviteRelation == 1) {
/*     */             
/*  61 */             skullMeta.setDisplayName("§a" + player.getName() + " invite you. Click to accept!");
/*  62 */             skull.setDurability((short)1);
/*  63 */           } else if (inviteRelation == 2) {
/*     */             
/*  65 */             skullMeta.setDisplayName("§a" + player.getName() + " invite sended!");
/*  66 */             skull.setDurability((short)1);
/*     */           } 
/*  68 */           skull.setItemMeta((ItemMeta)skullMeta);
/*  69 */           inventory.setItem(m, skull);
/*  70 */           m++;
/*     */           
/*  72 */           if (m > 54) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*  78 */     author.openInventory(inventory);
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onInventoryClickEvent(InventoryClickEvent e) {
/*  84 */     if (!e.getInventory().getName().equals("§eArenaX1 GUI")) {
/*     */       return;
/*     */     }
/*     */     
/*  88 */     e.setCancelled(true);
/*     */     
/*  90 */     if (e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.SKULL_ITEM) {
/*     */       
/*  92 */       SkullMeta meta = (SkullMeta)e.getCurrentItem().getItemMeta();
/*  93 */       Player author = (Player)e.getWhoClicked();
/*  94 */       Player target = Bukkit.getPlayer(meta.getOwner());
/*     */       
/*  96 */       if (target.isOnline()) {
/*     */         
/*  98 */         int relation = this.inviteManager.getInviteRelation(author, target);
/*  99 */         if (relation == 1) {
/*     */           
/* 101 */           author.playSound(author.getLocation(), Sound.NOTE_PLING, 20.0F, 1.0F);
/* 102 */           this.inviteManager.getPendentInvite(author, target).setAccepted(true);
/*     */           return;
/*     */         } 
/* 105 */         if (this.inviteManager.createInvite(author, target)) {
/*     */           
/* 107 */           author.playSound(author.getLocation(), Sound.NOTE_PLING, 20.0F, 1.0F);
/* 108 */           e.getCurrentItem().setDurability((short)1);
/* 109 */           meta.setDisplayName("§a" + target.getName() + " invite sended!");
/* 110 */           e.getCurrentItem().setItemMeta((ItemMeta)meta);
/*     */         } else {
/*     */           
/* 113 */           author.playSound(author.getLocation(), Sound.ANVIL_LAND, 20.0F, 1.0F);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\ArenaX1-0.3.2.jar!\br\com\tinycraft\arenax1\gui\GUI.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */