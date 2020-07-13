/*    */ package br.com.sgcraft.arenax1;
import br.com.sgcraft.arenax1.arena.ArenaManager;
import br.com.sgcraft.arenax1.commands.CommandManager;
import br.com.sgcraft.arenax1.data.Data;
import br.com.sgcraft.arenax1.data.FlatFileData;
import br.com.sgcraft.arenax1.executor.ArenaExecutor;
import br.com.sgcraft.arenax1.gui.GUI;
import br.com.sgcraft.arenax1.invite.InviteManager;
import br.com.sgcraft.arenax1.language.Language;
import br.com.sgcraft.arenax1.listener.PlayerListener;

/*    */ import java.io.IOException;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArenaX1
/*    */   extends JavaPlugin
/*    */ {
/*    */   private ArenaManager arenaManager;
/*    */   private ArenaExecutor arenaExecutor;
/*    */   private InviteManager inviteManager;
/*    */   private GUI gui;
/*    */   private Data data;
/*    */   private ArenaConfig config;
/*    */   private Language language;
/*    */   private CommandManager commandManager;
/*    */   
/*    */   public void onEnable() {
/* 38 */     getLogger().info("ArenaX1 by SgCraft");
/*    */     
/* 40 */     this.config = new ArenaConfig(this);
/* 41 */     this.data = (Data)new FlatFileData(getDataFolder());
/* 42 */     this.language = new Language(this, this.config);
/* 43 */     this.arenaManager = new ArenaManager(this, this.data.loadAllArena(), this.data.loadLobby());
/* 44 */     this.arenaExecutor = new ArenaExecutor(this, this.arenaManager, this.config, this.language);
/* 45 */     this.inviteManager = new InviteManager(this.arenaExecutor, this.config, this.language);
/* 46 */     this.gui = new GUI(this.arenaManager, this.inviteManager, this.arenaExecutor);
/* 47 */     this.commandManager = new CommandManager(this.inviteManager, this.arenaManager, this.gui, this.language);
/*    */     
/* 49 */     getServer().getScheduler().runTaskTimer((Plugin)this, (Runnable)this.arenaExecutor, 20L, 20L);
/* 50 */     getServer().getScheduler().runTaskTimer((Plugin)this, (Runnable)this.inviteManager, 20L, 20L);
/*    */     
/* 52 */     getServer().getPluginManager().registerEvents((Listener)new PlayerListener(this.arenaExecutor, this.config, this.gui, this.language), (Plugin)this);
/* 53 */     getServer().getPluginManager().registerEvents((Listener)this.gui, (Plugin)this);
/*    */     
/* 55 */     getCommand("arenax1").setExecutor((CommandExecutor)this.commandManager);
/* 56 */     getCommand("arenax1adm").setExecutor((CommandExecutor)this.commandManager);
/*    */ 
/*    */     }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 71 */     getLogger().info("ArenaX1 by SgCraft");
/* 72 */     this.data.saveAllArena(this.arenaManager.getArenas());
/* 73 */     this.data.saveLobby(this.arenaManager.getArenaLobby());
/*    */     
/*    */     try {
/* 76 */       this.data.saveToBase();
/* 77 */     } catch (Exception ex) {
/*    */       
/* 79 */       Logger.getLogger(ArenaX1.class.getName()).log(Level.SEVERE, (String)null, ex);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public ArenaManager getArenaManager() {
/* 85 */     return this.arenaManager;
/*    */   }
/*    */ 
/*    */   
/*    */   public InviteManager inviteManager() {
/* 90 */     return this.inviteManager;
/*    */   }
/*    */ }