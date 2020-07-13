/*     */ package br.com.sgcraft.arenax1.executor;
import br.com.sgcraft.arenax1.ArenaConfig;
import br.com.sgcraft.arenax1.ArenaX1;
import br.com.sgcraft.arenax1.arena.Arena;
import br.com.sgcraft.arenax1.arena.ArenaManager;
import br.com.sgcraft.arenax1.language.Language;
import br.com.sgcraft.arenax1.invite.InviteManager;

/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArenaExecutor
/*     */   extends BukkitRunnable
/*     */ {
/*     */   private final ArenaX1 plugin;
/*     */   private final ArenaManager arenaManager;
/*     */   private final Language language;
/*     */   private final List<Arena> runningArenas;
/*     */   private final int defaultRemainingTime;
/*     */   private final int startWaitTime;
/*     */   private final int endingTime;
/*     */   
/*     */   public ArenaExecutor(ArenaX1 plugin, ArenaManager arenaManager, ArenaConfig config, Language language) {
/*  33 */     this.plugin = plugin;
/*  34 */     this.arenaManager = arenaManager;
/*  35 */     this.language = language;
/*  36 */     this.defaultRemainingTime = config.getDefaultRemainingTime();
/*  37 */     this.startWaitTime = config.getStartWaitTime();
/*  38 */     this.endingTime = config.getEndindTime();
/*     */     
/*  40 */     this.runningArenas = new ArrayList<>();
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeX1(Arena arena) {
/*  45 */     Location spawn = Bukkit.getWorld(arena.getWorld()).getSpawnLocation();
/*     */     
/*  47 */     Iterator<Player> i = arena.getPlayers().iterator();
/*     */     
/*  49 */     while (i.hasNext()) {
/*     */       
/*  51 */       Player player = i.next();
/*  52 */       player.teleport((this.arenaManager.getArenaLobby() == null) ? spawn : this.arenaManager.getArenaLobby());
/*  53 */       i.remove();
/*     */     } 
/*     */     
/*  56 */     arena.setOcurring(false);
/*  57 */     arena.setWaitinigToStart(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean createX1(Player player, Player player2) {
/*  62 */     Arena duel = this.arenaManager.getAvailableArena();
/*     */     
/*  64 */     if (duel == null)
/*     */     {
/*  66 */       return false;
/*     */     }
/*  68 */     duel.setOcurring(true);
/*  69 */     duel.setWaitinigToStart(true);
/*  70 */     duel.setRemainingTime(this.defaultRemainingTime);
/*     */     
/*  72 */     duel.addPlayer(player);
/*  73 */     duel.addPlayer(player2);
/*     */     
/*  75 */     player.teleport(duel.getPos1());
/*  76 */     player2.teleport(duel.getPos2());
/*     */     
/*  78 */     this.runningArenas.add(duel);

				Bukkit.broadcastMessage("");
				Bukkit.broadcastMessage("  §e§l" + player.getName()+ " §eesta §lX1 §ecom §e§l"+ player2.getName()+".");
				Bukkit.broadcastMessage("  §eDigite §f/warp x1 §epara assistir a luta!");
				Bukkit.broadcastMessage("");	
/*  79 */     return true;

	

/*     */   }
/*     */ 
/*     */   
/*     */   public void sendMessage(Arena arena, String message) {
/*  84 */     Iterator<Player> i = arena.getPlayers().iterator();
/*     */     
/*  86 */     while (i.hasNext())
/*     */     {
/*  88 */       ((Player)i.next()).sendMessage(message);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Arena getPlayerArena(Player player, boolean remove) {
/*  94 */     Iterator<Arena> i = this.runningArenas.iterator();
/*     */     
/*  96 */     while (i.hasNext()) {
/*     */       
/*  98 */       Arena arena = i.next();
/*     */       
/* 100 */       if (arena.isInArena(player, remove))
/*     */       {
/* 102 */         return arena;
/*     */       }
/*     */     } 
/* 105 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void playerDeath(Player player) {
/* 110 */     Arena arena = getPlayerArena(player, true);
/*     */     
/* 112 */     if (arena != null) {
/*     */       
/* 114 */       if (arena.getPlayers().isEmpty())
/*     */       {
/* 116 */         player.teleport(Bukkit.getWorld(arena.getWorld()).getSpawnLocation());
/*     */       }
/*     */       
/* 119 */       arena.setRemainingTime(this.endingTime);
/* 120 */       arena.setLoser(player);
/*     */       
/* 122 */       if (arena.getWinner() != null)
/*     */       {
/* 124 */         arena.getWinner().sendMessage(this.language.getMessage("WinnerMessage", new Object[] { Integer.valueOf(this.endingTime) }));
					Bukkit.broadcastMessage("");
					Bukkit.broadcastMessage(this.language.getMessage("BroadCastMessage", new Object[] { arena.getWinner().getName(), arena.getLoser().getName() }));
					Bukkit.broadcastMessage("");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean playerCommand(Player player) {
/* 131 */     return (getPlayerArena(player, false) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/* 137 */     Iterator<Arena> i = this.runningArenas.iterator();
/*     */     
/* 139 */     while (i.hasNext()) {
/*     */       
/* 141 */       Arena arena = i.next();
/*     */       
/* 143 */       if (arena.isWaitinigToStart()) {
/*     */         
/* 145 */         int waitTime = this.defaultRemainingTime - arena.getRemainingTime();
/* 146 */         if (waitTime >= this.startWaitTime) {
/*     */           
/* 148 */           sendMessage(arena, this.language.getMessage("DuelStartMessage", new Object[0]));
/* 149 */           arena.setWaitinigToStart(false);
					
				
/*     */         } else {
/*     */           
/* 152 */           sendMessage(arena, this.language.getMessage("WaitMessage", new Object[] { Integer.valueOf(this.startWaitTime - waitTime) }));
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 157 */       if (arena.isEnded()) {
/*     */         
/* 159 */         if (arena.getWinner() == null) {
/*     */           
/* 161 */           closeX1(arena);
/* 162 */           i.remove();
/*     */           continue;
/*     */         }

/*     */         
/* 168 */         closeX1(arena);
/* 169 */         i.remove();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\ArenaX1-0.3.2.jar!\br\com\tinycraft\arenax1\executor\ArenaExecutor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */