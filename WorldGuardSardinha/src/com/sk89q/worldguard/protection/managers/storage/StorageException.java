/*    */ package com.sk89q.worldguard.protection.managers.storage;
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
/*    */ public class StorageException
/*    */   extends Exception
/*    */ {
/*    */   public StorageException() {}
/*    */   
/*    */   public StorageException(String message) {
/* 31 */     super(message);
/*    */   }
/*    */   
/*    */   public StorageException(String message, Throwable cause) {
/* 35 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public StorageException(Throwable cause) {
/* 39 */     super(cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\storage\StorageException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */