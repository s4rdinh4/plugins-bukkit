/*     */ package com.sk89q.worldguard.util.profile.resolver;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.sk89q.worldguard.util.profile.Profile;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.CompletionService;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.ExecutorCompletionService;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
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
/*     */ public class ParallelProfileService
/*     */   implements ProfileService
/*     */ {
/*     */   private final ProfileService resolver;
/*     */   private final ExecutorService executorService;
/*  48 */   private int profilesPerJob = 100;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParallelProfileService(ProfileService resolver, ExecutorService executorService) {
/*  57 */     Preconditions.checkNotNull(resolver);
/*  58 */     Preconditions.checkNotNull(executorService);
/*     */     
/*  60 */     this.resolver = resolver;
/*  61 */     this.executorService = executorService;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParallelProfileService(ProfileService resolver, int nThreads) {
/*  71 */     this(resolver, Executors.newFixedThreadPool(nThreads));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getProfilesPerJob() {
/*  80 */     return this.profilesPerJob;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProfilesPerJob(int profilesPerJob) {
/*  89 */     Preconditions.checkArgument((profilesPerJob >= 1), "profilesPerJob must be >= 1");
/*  90 */     this.profilesPerJob = profilesPerJob;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIdealRequestLimit() {
/*  95 */     return this.resolver.getIdealRequestLimit();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getEffectiveProfilesPerJob() {
/* 104 */     return Math.min(this.profilesPerJob, this.resolver.getIdealRequestLimit());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Profile findByName(String name) throws IOException, InterruptedException {
/* 110 */     return this.resolver.findByName(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public ImmutableList<Profile> findAllByName(Iterable<String> names) throws IOException, InterruptedException {
/* 115 */     CompletionService<List<Profile>> completion = new ExecutorCompletionService<List<Profile>>(this.executorService);
/* 116 */     int count = 0;
/* 117 */     for (List<String> partition : (Iterable<List<String>>)Iterables.partition(names, getEffectiveProfilesPerJob())) {
/* 118 */       count++;
/* 119 */       completion.submit(new Callable<List<Profile>>()
/*     */           {
/*     */             public List<Profile> call() throws Exception {
/* 122 */               return (List<Profile>)ParallelProfileService.this.resolver.findAllByName(partition);
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 127 */     ImmutableList.Builder<Profile> builder = ImmutableList.builder();
/* 128 */     for (int i = 0; i < count; i++) {
/*     */       try {
/* 130 */         builder.addAll(completion.take().get());
/* 131 */       } catch (ExecutionException e) {
/* 132 */         if (e.getCause() instanceof IOException) {
/* 133 */           throw (IOException)e.getCause();
/*     */         }
/* 135 */         throw new RuntimeException("Error occurred during the operation", e);
/*     */       } 
/*     */     } 
/*     */     
/* 139 */     return builder.build();
/*     */   }
/*     */ 
/*     */   
/*     */   public void findAllByName(Iterable<String> names, final Predicate<Profile> consumer) throws IOException, InterruptedException {
/* 144 */     CompletionService<Object> completion = new ExecutorCompletionService(this.executorService);
/* 145 */     int count = 0;
/* 146 */     for (List<String> partition : (Iterable<List<String>>)Iterables.partition(names, getEffectiveProfilesPerJob())) {
/* 147 */       count++;
/* 148 */       completion.submit(new Callable()
/*     */           {
/*     */             public Object call() throws Exception {
/* 151 */               ParallelProfileService.this.resolver.findAllByName(partition, consumer);
/* 152 */               return null;
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 157 */     Throwable throwable = null;
/* 158 */     for (int i = 0; i < count; i++) {
/*     */       try {
/* 160 */         completion.take().get();
/* 161 */       } catch (ExecutionException e) {
/* 162 */         throwable = e.getCause();
/*     */       } 
/*     */     } 
/*     */     
/* 166 */     if (throwable != null) {
/* 167 */       if (throwable instanceof IOException) {
/* 168 */         throw (IOException)throwable;
/*     */       }
/* 170 */       throw new RuntimeException("Error occurred during the operation", throwable);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\profile\resolver\ParallelProfileService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */