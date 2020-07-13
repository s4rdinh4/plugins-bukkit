/*    */ package br.com.sgcraft.arenax1.commands;
import br.com.sgcraft.arenax1.arena.ArenaManager;
import br.com.sgcraft.arenax1.gui.GUI;
import br.com.sgcraft.arenax1.invite.InviteManager;
import br.com.sgcraft.arenax1.language.Language;
import br.com.sgcraft.commands.annotation.CommandArena;

/*    */ import java.lang.reflect.Method;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandManager
/*    */   implements CommandExecutor
/*    */ {
/*    */   private final InviteManager inviteManager;
/*    */   private final ArenaManager arenaManager;
/*    */   private final GUI gui;
/*    */   private final Language language;
/*    */   private final CommandX1 commandX1;
/*    */   private final List<Method> methods;
/*    */   
/*    */   public CommandManager(InviteManager inviteManager, ArenaManager arenaManager, GUI gui, Language language) {
/* 32 */     this.inviteManager = inviteManager;
/* 33 */     this.arenaManager = arenaManager;
/* 34 */     this.gui = gui;
/* 35 */     this.language = language;
/* 36 */     this.methods = new ArrayList<>();
/* 37 */     this.commandX1 = new CommandX1(inviteManager, language, gui, arenaManager);
/*    */     
/* 39 */     for (Method method : this.commandX1.getClass().getDeclaredMethods()) {
/*    */       
/* 41 */       if (method.isAnnotationPresent((Class)CommandArena.class))
/*    */       {
/* 43 */         this.methods.add(method);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
/* 51 */     if (!(cs instanceof Player)) {
/*    */       
/* 53 */       cs.sendMessage(this.language.getMessage("ErrorCommandPlayerOnly", (Object[])new String[0]));
/* 54 */       return true;
/*    */     } 
/*    */     
/* 57 */     Player player = (Player)cs;
/*    */     
/* 59 */     if (args.length == 0)
/*    */     {
/* 61 */       return false;
/*    */     }
/*    */     
/* 64 */     String command = cmnd.getName().toLowerCase();
/*    */     
/* 66 */     for (Method method : this.methods) {
/*    */       
/* 68 */       CommandArena annotation = method.<CommandArena>getAnnotation(CommandArena.class);
/*    */       
/* 70 */       if (!annotation.superCommand().equals(command)) {
/*    */         continue;
/*    */       }
/*    */       
/* 74 */       if (!annotation.command().equalsIgnoreCase(args[0])) {
/*    */         continue;
/*    */       }
/*    */       
/* 78 */       if (!player.hasPermission(annotation.permission())) {
/*    */         
/* 80 */         player.sendMessage(annotation.permissionMessage());
/* 81 */         return true;
/*    */       } 
/* 83 */       if (args.length != annotation.args()) {
/*    */         
/* 85 */         player.sendMessage("§cUse: " + annotation.usage());
/* 86 */         return true;
/*    */       } 
/*    */       
/*    */       try {
/* 90 */         method.invoke(this.commandX1, new Object[] { player, args });
/* 91 */         return true;
/* 92 */       } catch (Exception e) {
/*    */         
/* 94 */         e.printStackTrace();
/*    */       } 
/*    */     } 
/* 97 */     return false;
/*    */   }
/*    */ }

