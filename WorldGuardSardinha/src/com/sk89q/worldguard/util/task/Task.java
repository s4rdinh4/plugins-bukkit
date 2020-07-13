/*    */ package com.sk89q.worldguard.util.task;
/*    */ 
/*    */ import com.google.common.util.concurrent.ListenableFuture;
/*    */ import com.sk89q.worldguard.util.task.progress.ProgressObservable;
/*    */ import java.util.Date;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nullable;
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
/*    */ 
/*    */ 
/*    */ public interface Task<V>
/*    */   extends ListenableFuture<V>, ProgressObservable
/*    */ {
/*    */   UUID getUniqueId();
/*    */   
/*    */   String getName();
/*    */   
/*    */   @Nullable
/*    */   Object getOwner();
/*    */   
/*    */   State getState();
/*    */   
/*    */   Date getCreationDate();
/*    */   
/*    */   public enum State
/*    */   {
/* 78 */     SCHEDULED,
/*    */ 
/*    */ 
/*    */     
/* 82 */     CANCELLED,
/*    */ 
/*    */ 
/*    */     
/* 86 */     RUNNING,
/*    */ 
/*    */ 
/*    */     
/* 90 */     FAILED,
/*    */ 
/*    */ 
/*    */     
/* 94 */     SUCCEEDED;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\task\Task.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */