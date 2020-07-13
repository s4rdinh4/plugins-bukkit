/*     */ package com.sk89q.worldguard.util.task;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import com.sk89q.worldguard.util.task.progress.Progress;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FutureForwardingTask<V>
/*     */   extends AbstractTask<V>
/*     */ {
/*     */   private final ListenableFuture<V> future;
/*     */   
/*     */   private FutureForwardingTask(ListenableFuture<V> future, String name, @Nullable Object owner) {
/*  50 */     super(name, owner);
/*  51 */     Preconditions.checkNotNull(future);
/*  52 */     this.future = future;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addListener(Runnable listener, Executor executor) {
/*  57 */     this.future.addListener(listener, executor);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean cancel(boolean mayInterruptIfRunning) {
/*  62 */     return this.future.cancel(mayInterruptIfRunning);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCancelled() {
/*  67 */     return this.future.isCancelled();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDone() {
/*  72 */     return this.future.isDone();
/*     */   }
/*     */ 
/*     */   
/*     */   public V get() throws InterruptedException, ExecutionException {
/*  77 */     return (V)this.future.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
/*  82 */     return (V)this.future.get(timeout, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public Task.State getState() {
/*  87 */     if (isCancelled())
/*  88 */       return Task.State.CANCELLED; 
/*  89 */     if (isDone()) {
/*     */       try {
/*  91 */         get();
/*  92 */         return Task.State.SUCCEEDED;
/*  93 */       } catch (InterruptedException e) {
/*  94 */         return Task.State.CANCELLED;
/*  95 */       } catch (ExecutionException e) {
/*  96 */         return Task.State.FAILED;
/*     */       } 
/*     */     }
/*  99 */     return Task.State.RUNNING;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Progress getProgress() {
/* 105 */     return Progress.indeterminate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> FutureForwardingTask<V> create(ListenableFuture<V> future, String name, @Nullable Object owner) {
/* 118 */     return new FutureForwardingTask<V>(future, name, owner);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\task\FutureForwardingTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */