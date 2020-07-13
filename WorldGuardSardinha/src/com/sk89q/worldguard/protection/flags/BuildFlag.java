/*    */ package com.sk89q.worldguard.protection.flags;
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
/*    */ class BuildFlag
/*    */   extends StateFlag
/*    */ {
/*    */   public BuildFlag(String name, boolean def) {
/* 29 */     super(name, def);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean implicitlySetWithMembership() {
/* 34 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean usesMembershipAsDefault() {
/* 39 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean preventsAllowOnGlobal() {
/* 44 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean requiresSubject() {
/* 49 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\flags\BuildFlag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */