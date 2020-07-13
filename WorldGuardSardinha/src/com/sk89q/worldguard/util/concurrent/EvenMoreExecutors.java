/*    */ package com.sk89q.worldguard.util.concurrent;
/*    */ 
/*    */ import java.util.concurrent.ExecutorService;
/*    */ import java.util.concurrent.LinkedBlockingDeque;
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
/*    */ public final class EvenMoreExecutors
/*    */ {
/*    */   public static ExecutorService newBoundedCachedThreadPool(int minThreads, int maxThreads, int queueSize) {
/* 46 */     ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(minThreads, maxThreads, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(queueSize));
/*    */ 
/*    */ 
/*    */     
/* 50 */     threadPoolExecutor.allowCoreThreadTimeOut(true);
/* 51 */     return threadPoolExecutor;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\concurrent\EvenMoreExecutors.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */