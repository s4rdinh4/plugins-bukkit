/*    */ package br.com.sgcraft.arenax1.invite;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Invite
/*    */ {
/*    */   private final Player author;
/*    */   private final Player target;
/*    */   private int remainingTime;
/*    */   private boolean accepted;
/*    */   private int acceptedWait;
/*    */   
/*    */   public Invite(Player author, Player target, int remainingTime, int acceptedWait) {
/* 19 */     this.author = author;
/* 20 */     this.target = target;
/* 21 */     this.remainingTime = remainingTime;
/* 22 */     this.accepted = false;
/* 23 */     this.acceptedWait = acceptedWait;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setAccepted(boolean value) {
/* 28 */     this.accepted = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEnded() {
/* 33 */     this.remainingTime--;
/* 34 */     return (this.remainingTime <= 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAccepted() {
/* 39 */     return this.accepted;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAcceptedWait() {
/* 44 */     this.acceptedWait--;
/* 45 */     return (this.acceptedWait <= 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAcceptedWaitTime() {
/* 50 */     return this.acceptedWait;
/*    */   }
/*    */ 
/*    */   
/*    */   public Player getAuthor() {
/* 55 */     return this.author;
/*    */   }
/*    */ 
/*    */   
/*    */   public Player getTarget() {
/* 60 */     return this.target;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\ArenaX1-0.3.2.jar!\br\com\tinycraft\arenax1\invite\Invite.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */