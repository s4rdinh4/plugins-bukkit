/*     */ package br.com.sgcraft.arenax1.invite;
import br.com.sgcraft.arenax1.ArenaConfig;
import br.com.sgcraft.arenax1.executor.ArenaExecutor;
import br.com.sgcraft.arenax1.language.Language;

/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;

import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InviteManager
/*     */   extends BukkitRunnable
/*     */ {
/*     */   private final ArenaExecutor arenaExecutor;
/*     */   private final Language language;
/*     */   private final int defaultTime;
/*     */   private final int acceptedWait;
/*     */   private final List<Invite> invites;
/*     */   
/*     */   public InviteManager(ArenaExecutor arenaExecutor, ArenaConfig config, Language language) {
/*  27 */     this.arenaExecutor = arenaExecutor;
/*  28 */     this.defaultTime = config.getDefaultExpireTime();
/*  29 */     this.acceptedWait = config.getDefaultAcceptedWait();
/*  30 */     this.language = language;
/*  31 */     this.invites = new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInviteRelation(Player author, Player target) {
/*  45 */     int value = 0;
/*  46 */     Iterator<Invite> i = this.invites.iterator();
/*  47 */     while (i.hasNext()) {
/*     */       
/*  49 */       Invite invite = i.next();
/*     */       
/*  51 */       if (invite.getTarget().getName().equalsIgnoreCase(author.getName()) && invite.getAuthor().getName().equalsIgnoreCase(target.getName())) {
/*     */ 
/*     */         
/*  54 */         value = 1; continue;
/*  55 */       }  if (invite.getAuthor().getName().equalsIgnoreCase(author.getName()) && invite.getTarget().getName().equalsIgnoreCase(target.getName()))
/*     */       {
/*     */         
/*  58 */         value = 2;
/*     */       }
/*     */     } 
/*  61 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public Invite getPendentInvite(Player target, Player author) {
/*  66 */     Iterator<Invite> i = this.invites.iterator();
/*  67 */     while (i.hasNext()) {
/*     */       
/*  69 */       Invite invite = i.next();
/*     */       
/*  71 */       if (invite.getAuthor().getName().equalsIgnoreCase(author.getName()) && invite.getTarget().getName().equalsIgnoreCase(target.getName()))
/*     */       {
/*     */         
/*  74 */         return invite;
/*     */       }
/*     */     } 
/*  77 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean createInvite(Player author, Player target) {
/*  82 */     if (getPendentInvite(author, target) != null)
/*     */     {
/*  84 */       return false; } 
/*  85 */     if (getPendentInvite(target, author) != null)
/*     */     {
/*  87 */       return false;
/*     */     }
/*     */     
/*  90 */     this.invites.add(new Invite(author, target, this.defaultTime, this.acceptedWait));
/*  91 */     target.sendMessage(this.language.getMessage("InviteMessageTarget", new Object[] { author.getName() }));
/*  92 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void inviteAccepted(Invite invite) {
/*  97 */     clearPlayerInvites(invite);
/*  98 */     invite.setAccepted(true);
/*  99 */     invite.getAuthor().sendMessage(this.language.getMessage("InviteAcceptedAuthor", new Object[0]));
/*     */   }
/*     */ 
/*     */   
/*     */   public void inviteRejected(Invite invite) {
/* 104 */     this.invites.remove(invite);
/* 105 */     invite.getAuthor().sendMessage(this.language.getMessage("InviteRejectedAuthor", new Object[] { invite.getTarget().getName() }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearPlayerInvites(Invite invite) {
/* 112 */     Iterator<Invite> i = this.invites.iterator();
/*     */     
/* 114 */     String author = invite.getAuthor().getName();
/* 115 */     String target = invite.getTarget().getName();
/*     */     
/* 117 */     while (i.hasNext()) {
/*     */       
/* 119 */       Invite invi = i.next();
/*     */       
/* 121 */       if (!invi.equals(invite)) {
/*     */         
/* 123 */         String authori = invi.getAuthor().getName();
/* 124 */         String targeti = invi.getTarget().getName();
/*     */         
/* 126 */         if (author.equals(authori) || author.equals(targeti) || target.equals(authori) || target.equals(targeti))
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 131 */           i.remove();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/* 140 */     Iterator<Invite> i = this.invites.iterator();
/*     */     
/* 142 */     while (i.hasNext()) {
/*     */       
/* 144 */       Invite invite = i.next();
/*     */       
/* 146 */       if (!invite.getAuthor().isOnline()) {
/*     */         
/* 148 */         invite.getTarget().sendMessage(this.language.getMessage("InviteCancelledExit", new Object[0]));
/*     */         
/* 150 */         i.remove(); continue;
/*     */       } 
/* 152 */       if (!invite.getTarget().isOnline()) {
/*     */         
/* 154 */         invite.getAuthor().sendMessage(this.language.getMessage("InviteCancelledExit", new Object[0]));
/*     */         
/* 156 */         i.remove();
/*     */         
/*     */         continue;
/*     */       } 
/* 160 */       if (invite.isEnded() && !invite.isAccepted()) {
/*     */         
/* 162 */         invite.getAuthor().sendMessage(this.language.getMessage("InviteExpiredAuthor", new Object[] { invite.getTarget().getName() }));
/*     */ 
/*     */         
/* 165 */         invite.getTarget().sendMessage(this.language.getMessage("InviteExpiredTarget", new Object[] { invite.getAuthor().getName() }));
/*     */ 
/*     */         
/* 168 */         i.remove(); continue;
/* 169 */       }  if (invite.isAccepted()) {
/*     */         
/* 171 */         if (invite.isAcceptedWait()) {
/*     */           
/* 173 */           if (this.arenaExecutor.createX1(invite.getAuthor(), invite.getTarget())) {
/*     */             
/* 175 */             invite.getAuthor().sendMessage(this.language.getMessage("InviteAcceptedStarting", new Object[0]));
/*     */             
/* 177 */             invite.getTarget().sendMessage(this.language.getMessage("InviteAcceptedStarting", new Object[0]));
						
/*     */           }
/*     */           else {
/*     */             
/* 181 */             invite.getAuthor().sendMessage(this.language.getMessage("InviteAcceptedNoArena", new Object[0]));
/*     */             
/* 183 */             invite.getTarget().sendMessage(this.language.getMessage("InviteAcceptedNoArena", new Object[0]));
/*     */           } 
/*     */ 
/*     */           
/* 187 */           i.remove();
/*     */           continue;
/*     */         } 
/* 190 */         invite.getAuthor().sendMessage(this.language.getMessage("InviteAcceptedWaiting", new Object[] { Integer.valueOf(invite.getAcceptedWaitTime()) }));
/*     */ 
/*     */         
/* 193 */         invite.getTarget().sendMessage(this.language.getMessage("InviteAcceptedWaiting", new Object[] { Integer.valueOf(invite.getAcceptedWaitTime()) }));	
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\ArenaX1-0.3.2.jar!\br\com\tinycraft\arenax1\invite\InviteManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */