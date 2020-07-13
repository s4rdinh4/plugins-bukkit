/*    */ package com.sk89q.worldguard.blacklist.action;
/*    */ 
/*    */ import com.sk89q.worldguard.blacklist.Blacklist;
/*    */ import com.sk89q.worldguard.blacklist.BlacklistEntry;
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
/*    */ public enum ActionType
/*    */ {
/* 27 */   ALLOW("allow")
/*    */   {
/*    */     public Action parseInput(Blacklist blacklist, BlacklistEntry entry) {
/* 30 */       return AllowAction.getInstance();
/*    */     }
/*    */   },
/* 33 */   DENY("deny")
/*    */   {
/*    */     public Action parseInput(Blacklist blacklist, BlacklistEntry entry) {
/* 36 */       return DenyAction.getInstance();
/*    */     }
/*    */   },
/* 39 */   BAN("ban")
/*    */   {
/*    */     public Action parseInput(Blacklist blacklist, BlacklistEntry entry) {
/* 42 */       return new BanAction(entry);
/*    */     }
/*    */   },
/* 45 */   KICK("kick")
/*    */   {
/*    */     public Action parseInput(Blacklist blacklist, BlacklistEntry entry) {
/* 48 */       return new KickAction(entry);
/*    */     }
/*    */   },
/* 51 */   LOG("log")
/*    */   {
/*    */     public Action parseInput(Blacklist blacklist, BlacklistEntry entry) {
/* 54 */       return new LogAction(blacklist, entry);
/*    */     }
/*    */   },
/* 57 */   NOTIFY("notify")
/*    */   {
/*    */     public Action parseInput(Blacklist blacklist, BlacklistEntry entry) {
/* 60 */       return new NotifyAction(blacklist, entry);
/*    */     }
/*    */   },
/* 63 */   TELL("tell")
/*    */   {
/*    */     public Action parseInput(Blacklist blacklist, BlacklistEntry entry) {
/* 66 */       return new TellAction(entry);
/*    */     }
/*    */   };
/*    */   
/*    */   private final String actionName;
/*    */   
/*    */   ActionType(String actionName) {
/* 73 */     this.actionName = actionName;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getActionName() {
/* 79 */     return this.actionName;
/*    */   }
/*    */   
/*    */   public abstract Action parseInput(Blacklist paramBlacklist, BlacklistEntry paramBlacklistEntry);
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\action\ActionType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */