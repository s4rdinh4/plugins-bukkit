/*     */ package com.sk89q.worldguard.util.task.progress;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
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
/*     */ public abstract class Progress
/*     */ {
/*     */   private Progress() {}
/*     */   
/*     */   public static Progress indeterminate() {
/*  65 */     return INDETERMINATE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Progress completed() {
/*  74 */     return COMPLETED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Progress of(double value) {
/*  84 */     if (value < 0.0D) {
/*  85 */       value = 0.0D;
/*  86 */     } else if (value > 1.0D) {
/*  87 */       value = 1.0D;
/*     */     } 
/*     */     
/*  90 */     final double finalValue = value;
/*  91 */     return new Progress()
/*     */       {
/*     */         public boolean isIndeterminate() {
/*  94 */           return false;
/*     */         }
/*     */ 
/*     */         
/*     */         public double getProgress() {
/*  99 */           return finalValue;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Progress split(Progress... objects) {
/* 112 */     return split(Arrays.asList(objects));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Progress split(Collection<Progress> progress) {
/* 123 */     int count = 0;
/* 124 */     double total = 0.0D;
/*     */     
/* 126 */     for (Progress p : progress) {
/* 127 */       if (p.isIndeterminate()) {
/* 128 */         return indeterminate();
/*     */       }
/* 130 */       total += p.getProgress();
/*     */     } 
/*     */     
/* 133 */     return of(total / count);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Progress splitObservables(ProgressObservable... observables) {
/* 144 */     return splitObservables(Arrays.asList(observables));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Progress splitObservables(Collection<? extends ProgressObservable> observables) {
/* 155 */     int count = 0;
/* 156 */     double total = 0.0D;
/*     */     
/* 158 */     for (ProgressObservable observable : observables) {
/* 159 */       Progress p = observable.getProgress();
/* 160 */       if (p.isIndeterminate()) {
/* 161 */         return indeterminate();
/*     */       }
/* 163 */       total += p.getProgress();
/*     */     } 
/*     */     
/* 166 */     return of(total / count);
/*     */   }
/*     */   
/* 169 */   private static final Progress COMPLETED = of(1.0D);
/*     */   
/* 171 */   private static final Progress INDETERMINATE = new Progress()
/*     */     {
/*     */       public boolean isIndeterminate() {
/* 174 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       public double getProgress() {
/* 179 */         return 0.0D;
/*     */       }
/*     */     };
/*     */   
/*     */   public abstract boolean isIndeterminate();
/*     */   
/*     */   public abstract double getProgress();
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\task\progress\Progress.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */