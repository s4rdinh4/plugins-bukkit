/*    */ package com.sk89q.worldguard.domains;
/*    */ 
/*    */ import com.sk89q.worldguard.LocalPlayer;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
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
/*    */ @Deprecated
/*    */ public class DomainCollection
/*    */   implements Domain
/*    */ {
/* 37 */   private Set<Domain> domains = new LinkedHashSet<Domain>();
/*    */ 
/*    */   
/*    */   public void add(Domain domain) {
/* 41 */     this.domains.add(domain);
/*    */   }
/*    */   
/*    */   public void remove(Domain domain) {
/* 45 */     this.domains.remove(domain);
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 50 */     return this.domains.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 55 */     this.domains.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean contains(LocalPlayer player) {
/* 60 */     for (Domain domain : this.domains) {
/* 61 */       if (domain.contains(player)) {
/* 62 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 66 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean contains(UUID uniqueId) {
/* 71 */     for (Domain domain : this.domains) {
/* 72 */       if (domain.contains(uniqueId)) {
/* 73 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 77 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean contains(String playerName) {
/* 82 */     for (Domain domain : this.domains) {
/* 83 */       if (domain.contains(playerName)) {
/* 84 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 88 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\domains\DomainCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */