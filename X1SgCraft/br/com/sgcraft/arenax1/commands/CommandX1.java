/*     */ package br.com.sgcraft.arenax1.commands;
import br.com.sgcraft.arenax1.arena.Arena;
import br.com.sgcraft.arenax1.arena.ArenaManager;
import br.com.sgcraft.arenax1.gui.GUI;
import br.com.sgcraft.arenax1.invite.Invite;
import br.com.sgcraft.arenax1.invite.InviteManager;
import br.com.sgcraft.arenax1.language.Language;
import br.com.sgcraft.commands.annotation.CommandArena;

/*     */ import java.util.Iterator;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandX1
/*     */ {
/*     */   private final InviteManager inviteManager;
/*     */   private final Language language;
/*     */   private final GUI gui;
/*     */   private final ArenaManager arenaManager;
/*     */   
/*     */   public CommandX1(InviteManager inviteManager, Language language, GUI gui, ArenaManager arenaManager) {
/*  28 */     this.inviteManager = inviteManager;
/*  29 */     this.language = language;
/*  30 */     this.gui = gui;
/*  31 */     this.arenaManager = arenaManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CommandArena(command = "desafiar", superCommand = "arenax1", args = 2, usage = "§a/x1 desafiar [nick] - desafia um jogador")
/*     */   public void duel(Player author, String[] args) {
/*  40 */     Player target = Bukkit.getPlayer(args[1]);
/*     */     
/*  42 */     if (checkPlayers(author, target)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  47 */     if (this.inviteManager.createInvite(author, target)) {
/*     */       author.sendMessage("");
/*  49 */       author.sendMessage(this.language.getMessage("InviteMessageAuthor", new Object[0]));
				author.sendMessage("");
/*     */     } else {
/*     */       author.sendMessage("");
/*  52 */       author.sendMessage(this.language.getMessage("ErrorPlayerAlreadyHasInvite", new Object[0]));
				author.sendMessage("");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CommandArena(command = "aceitar", superCommand = "arenax1", args = 2, usage = "§a/x1 aceitar [nick] - Aceitar um Desafio.")
/*     */   public void accept(Player author, String[] args) {
/*  62 */     Player target = Bukkit.getPlayer(args[1]);
/*     */     
/*  64 */     if (checkPlayers(author, target)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  69 */     Invite invite = this.inviteManager.getPendentInvite(author, target);
/*  70 */     if (invite == null) {
/*     */       
/*  72 */       author.sendMessage(this.language.getMessage("ErrorNoInviteFound", new Object[0]));
/*     */     } else {
/*     */       
/*  75 */       this.inviteManager.inviteAccepted(invite);
/*  76 */       author.sendMessage(this.language.getMessage("InviteAcceptedTarget", new Object[0]));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CommandArena(command = "negar", superCommand = "arenax1", args = 2, usage = "§a/x1 negar [nick] - Negar um desafio.")
/*     */   public void reject(Player author, String[] args) {
/*  86 */     Player target = Bukkit.getPlayer(args[1]);
/*     */     
/*  88 */     if (checkPlayers(author, target)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  93 */     if (target == null) {
/*     */       
/*  95 */       author.sendMessage(this.language.getMessage("ErrorNoInviteFound", new Object[0]));
/*     */       
/*     */       return;
/*     */     } 
/*  99 */     Invite invite = this.inviteManager.getPendentInvite(author, target);
/* 100 */     if (invite == null) {
/*     */       
/* 102 */       author.sendMessage(this.language.getMessage("ErrorNoInviteFound", new Object[0]));
/*     */     } else {
/*     */       
/* 105 */       this.inviteManager.inviteRejected(invite);
/* 106 */       author.sendMessage(this.language.getMessage("InviteRejectedTarget", new Object[0]));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CommandArena(command = "gui", superCommand = "arenax1", args = 1, usage = "§a/x1 guit")
/*     */   public void gui(Player author, String[] args) {
/* 116 */     this.gui.openGui(author);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CommandArena(command = "criar", superCommand = "arenax1adm", permission = "arenax1.adm", args = 2, usage = "§a/x1adm criar [nome]")
/*     */   public void create(Player author, String[] args) {
/* 126 */     if (this.arenaManager.createArena(args[1], author.getWorld().getName())) {
/*     */       
/* 128 */       author.sendMessage("§aArena criada com sucesso!");
/*     */     } else {
/*     */       
/* 131 */       author.sendMessage("§cErro: Arena já existe!");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CommandArena(command = "excluir", superCommand = "arenax1adm", permission = "arenax1.adm", args = 2, usage = "§a/x1adm excluir [nome]")
/*     */   public void remove(Player author, String[] args) {
/* 142 */     if (this.arenaManager.removeArena(args[1])) {
/*     */       
/* 144 */       author.sendMessage("§aArena excluir com sucesso!");
/*     */     } else {
/*     */       
/* 147 */       author.sendMessage("Â§cError! Arena já existe");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CommandArena(command = "pos1", superCommand = "arenax1adm", permission = "arenax1.adm", args = 2, usage = "§a/x1adm pos1 [nome]")
/*     */   public void pos1(Player author, String[] args) {
/* 158 */     Arena arena = this.arenaManager.getArena(args[1]);
/*     */     
/* 160 */     if (arena == null) {
/*     */       
/* 162 */       author.sendMessage("§cErro. Arena nao localizada.");
/*     */     } else {
/*     */       
/* 165 */       author.sendMessage("§aPosicao 1 definida!");
/* 166 */       if (arena.setPos1(author.getLocation())) {
/*     */         
/* 168 */         author.sendMessage("§a" + arena.getName() + " esta pronta para uso!");
/*     */       } else {
/*     */         
/* 171 */         author.sendMessage("§cAgora defina a segunda posicao!");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CommandArena(command = "pos2", superCommand = "arenax1adm", permission = "arenax1.adm", args = 2, usage = "§a/x1adm pos2 [nome]")
/*     */   public void pos2(Player author, String[] args) {
/* 183 */     Arena arena = this.arenaManager.getArena(args[1]);
/*     */     
/* 185 */     if (arena == null) {
/*     */       
/* 187 */       author.sendMessage("§cErro. Arena nao localizada.");
/*     */     } else {
/*     */       
/* 190 */       author.sendMessage("§aPosicao 2 definida!");
/* 191 */       if (arena.setPos2(author.getLocation())) {
/*     */         
/* 193 */         author.sendMessage("§a" + arena.getName() + " esta pronta para uso!");
/*     */       } else {
/*     */         
/* 196 */         author.sendMessage("§cAgora defina a primeira posicao!");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CommandArena(command = "setlobby", superCommand = "arenax1adm", permission = "arenax1.adm", args = 1, usage = "§a/x1adm setlobby")
/*     */   public void setlobby(Player author, String[] args) {
/* 208 */     author.sendMessage("§aLobby das arenas definido!");
/* 209 */     this.arenaManager.setArenaLobby(author.getLocation());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CommandArena(command = "list", superCommand = "arenax1adm", permission = "arenax1.adm", args = 1, usage = "§a/x1adm list")
/*     */   public void list(Player author, String[] args) {
/* 219 */     author.sendMessage("§a -> Arenas:");
/* 220 */     Iterator<Arena> i = this.arenaManager.getArenas().iterator();
/*     */     
/* 222 */     while (i.hasNext()) {
/*     */       
/* 224 */       Arena arena = i.next();
/* 225 */       author.sendMessage("§a " + arena.getName() + (arena.isCompleted() ? " ativada." : " §csem posicao de spawn."));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkPlayers(Player author, Player target) {
/* 231 */     if (target == null) {
/*     */       
/* 233 */       author.sendMessage(this.language.getMessage("ErrorCommandPlayerNotFound", new Object[0]));
/* 234 */       return true;
/* 235 */     }  if (author.getName().equalsIgnoreCase(target.getName())) {
/*     */       
/* 237 */       author.sendMessage(this.language.getMessage("ErrorCommandCantChallengeYourself", new Object[0]));
/* 238 */       return true;
/*     */     } 
/* 240 */     return false;
/*     */   }
/*     */ }
