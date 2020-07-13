/*     */ package com.sk89q.worldguard.util.command;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandFilter
/*     */   implements Predicate<String>
/*     */ {
/*     */   @Nullable
/*     */   private final Collection<String> permitted;
/*     */   @Nullable
/*     */   private final Collection<String> denied;
/*     */   
/*     */   public CommandFilter(@Nullable Collection<String> permitted, @Nullable Collection<String> denied) {
/*  60 */     this.permitted = permitted;
/*  61 */     this.denied = denied;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean apply(String command) {
/*  67 */     command = command.toLowerCase().replaceAll("^/([^:]*:)?", "/");
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
/*  82 */     String result = "";
/*  83 */     String[] usedParts = command.split("\\s+");
/*  84 */     if (this.denied != null)
/*     */     {
/*  86 */       for (String deniedCommand : this.denied) {
/*  87 */         String[] deniedParts = deniedCommand.split("\\s+");
/*  88 */         for (int i = 0; i < deniedParts.length && i < usedParts.length && 
/*  89 */           deniedParts[i].equalsIgnoreCase(usedParts[i]); i++) {
/*     */           
/*  91 */           if (i + 1 == deniedParts.length) {
/*     */             
/*  93 */             result = deniedCommand;
/*     */             
/*     */             // Byte code: goto -> 141
/*     */           } 
/*  97 */           if (i + 1 == usedParts.length) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
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
/* 113 */     if (this.permitted != null)
/*     */     {
/* 115 */       for (String permittedCommand : this.permitted) {
/* 116 */         String[] permittedParts = permittedCommand.split("\\s+");
/* 117 */         for (int i = 0; i < permittedParts.length && i < usedParts.length; i++) {
/* 118 */           if (permittedParts[i].equalsIgnoreCase(usedParts[i])) {
/*     */             
/* 120 */             if (i + 1 == permittedParts.length) {
/*     */ 
/*     */               
/* 123 */               result = "";
/*     */               
/*     */               // Byte code: goto -> 267
/*     */             } 
/* 127 */             if (i + 1 == usedParts.length) {
/*     */ 
/*     */               
/* 130 */               result = command;
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               break;
/*     */             } 
/*     */           } else {
/* 138 */             result = command;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 145 */     return result.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     private Set<String> permitted;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Set<String> denied;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder permit(String... rules) {
/* 172 */       Preconditions.checkNotNull(rules);
/* 173 */       if (this.permitted == null) {
/* 174 */         this.permitted = new HashSet<String>();
/*     */       }
/* 176 */       this.permitted.addAll(Arrays.asList(rules));
/* 177 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder deny(String... rules) {
/* 187 */       Preconditions.checkNotNull(rules);
/* 188 */       if (this.denied == null) {
/* 189 */         this.denied = new HashSet<String>();
/*     */       }
/* 191 */       this.denied.addAll(Arrays.asList(rules));
/* 192 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CommandFilter build() {
/* 201 */       return new CommandFilter((this.permitted != null) ? new HashSet<String>(this.permitted) : null, (this.denied != null) ? new HashSet<String>(this.denied) : null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\command\CommandFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */