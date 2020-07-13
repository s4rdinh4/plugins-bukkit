/*    */ package com.sk89q.worldguard.util.paste;
/*    */ 
/*    */ import com.google.common.util.concurrent.ListeningExecutorService;
/*    */ import com.google.common.util.concurrent.MoreExecutors;
/*    */ import java.util.concurrent.SynchronousQueue;
/*    */ import java.util.concurrent.ThreadPoolExecutor;
/*    */ import java.util.concurrent.TimeUnit;
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
/*    */ final class Pasters
/*    */ {
/* 32 */   private static final ListeningExecutorService executor = MoreExecutors.listeningDecorator(new ThreadPoolExecutor(0, 2147483647, 2L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>()));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static ListeningExecutorService getExecutor() {
/* 41 */     return executor;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\paste\Pasters.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */