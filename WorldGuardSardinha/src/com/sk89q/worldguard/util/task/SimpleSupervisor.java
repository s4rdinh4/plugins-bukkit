/*    */ package com.sk89q.worldguard.util.task;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.google.common.util.concurrent.MoreExecutors;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.Executor;
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
/*    */ public class SimpleSupervisor
/*    */   implements Supervisor
/*    */ {
/* 34 */   private final List<Task<?>> monitored = new ArrayList<Task<?>>();
/* 35 */   private final Object lock = new Object();
/*    */ 
/*    */   
/*    */   public List<Task<?>> getTasks() {
/* 39 */     synchronized (this.lock) {
/* 40 */       return new ArrayList<Task<?>>(this.monitored);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void monitor(final Task<?> task) {
/* 46 */     Preconditions.checkNotNull(task);
/*    */     
/* 48 */     synchronized (this.lock) {
/* 49 */       this.monitored.add(task);
/*    */     } 
/*    */     
/* 52 */     task.addListener(new Runnable()
/*    */         {
/*    */           public void run() {
/* 55 */             synchronized (SimpleSupervisor.this.lock) {
/* 56 */               SimpleSupervisor.this.monitored.remove(task);
/*    */             } 
/*    */           }
/* 59 */         },  (Executor)MoreExecutors.sameThreadExecutor());
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\task\SimpleSupervisor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */