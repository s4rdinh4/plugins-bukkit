/*    */ package net.eq2online.macros.scripting.crafting;
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
/*    */ public class AutoCraftingToken
/*    */ {
/* 14 */   private static int nextId = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final int id;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private IAutoCraftingInitiator initiator;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 29 */   private String reason = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AutoCraftingToken(IAutoCraftingInitiator initiator) {
/* 36 */     this.id = nextId++;
/* 37 */     this.initiator = initiator;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AutoCraftingToken(IAutoCraftingInitiator initiator, String reason) {
/* 46 */     this.id = nextId++;
/* 47 */     this.initiator = initiator;
/* 48 */     notifyCompleted(reason);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AutoCraftingToken notifyCompleted(String reason) {
/* 59 */     this.reason = reason;
/*    */     
/* 61 */     if (this.initiator != null)
/*    */     {
/* 63 */       this.initiator.notifyTokenProcessed(this, reason);
/*    */     }
/*    */     
/* 66 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isCompleted() {
/* 76 */     return (this.reason != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getReason() {
/* 81 */     return this.reason;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 90 */     if (obj == null) return false; 
/* 91 */     if (!(obj instanceof AutoCraftingToken)) return false; 
/* 92 */     return (((AutoCraftingToken)obj).id == this.id);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 98 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\crafting\AutoCraftingToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */