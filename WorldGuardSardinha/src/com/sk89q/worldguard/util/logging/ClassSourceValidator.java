/*     */ package com.sk89q.worldguard.util.logging;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.security.CodeSource;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bukkit.plugin.Plugin;
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
/*     */ public class ClassSourceValidator
/*     */ {
/*  41 */   private static final Logger log = Logger.getLogger(ClassSourceValidator.class.getCanonicalName());
/*  42 */   private static final String separatorLine = Strings.repeat("*", 46);
/*     */ 
/*     */   
/*     */   private final Plugin plugin;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private final CodeSource expectedCodeSource;
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassSourceValidator(Plugin plugin) {
/*  54 */     Preconditions.checkNotNull(plugin, "plugin");
/*  55 */     this.plugin = plugin;
/*  56 */     this.expectedCodeSource = plugin.getClass().getProtectionDomain().getCodeSource();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<Class<?>, CodeSource> findMismatches(List<Class<?>> classes) {
/*  66 */     Preconditions.checkNotNull(classes, "classes");
/*     */     
/*  68 */     Map<Class<?>, CodeSource> mismatches = Maps.newHashMap();
/*     */     
/*  70 */     if (this.expectedCodeSource != null) {
/*  71 */       for (Class<?> testClass : classes) {
/*  72 */         CodeSource testSource = testClass.getProtectionDomain().getCodeSource();
/*  73 */         if (!this.expectedCodeSource.equals(testSource)) {
/*  74 */           mismatches.put(testClass, testSource);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  79 */     return mismatches;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reportMismatches(List<Class<?>> classes) {
/*  90 */     Map<Class<?>, CodeSource> mismatches = findMismatches(classes);
/*     */     
/*  92 */     if (!mismatches.isEmpty()) {
/*  93 */       StringBuilder builder = new StringBuilder("\n");
/*     */       
/*  95 */       builder.append(separatorLine).append("\n");
/*  96 */       builder.append("** /!\\    SEVERE WARNING    /!\\\n");
/*  97 */       builder.append("** \n");
/*  98 */       builder.append("** A plugin developer has copied and pasted a portion of \n");
/*  99 */       builder.append("** ").append(this.plugin.getName()).append(" into their own plugin, so rather than using\n");
/* 100 */       builder.append("** the version of ").append(this.plugin.getName()).append(" that you downloaded, you\n");
/* 101 */       builder.append("** will be using a broken mix of old ").append(this.plugin.getName()).append(" (that came\n");
/* 102 */       builder.append("** with the plugin) and your downloaded version. THIS MAY\n");
/* 103 */       builder.append("** SEVERELY BREAK ").append(this.plugin.getName().toUpperCase()).append(" AND ALL OF ITS FEATURES.\n");
/* 104 */       builder.append("**\n");
/* 105 */       builder.append("** This may have happened because the developer is using\n");
/* 106 */       builder.append("** the ").append(this.plugin.getName()).append(" API and thinks that including\n");
/* 107 */       builder.append("** ").append(this.plugin.getName()).append(" is necessary. However, it is not!\n");
/* 108 */       builder.append("**\n");
/* 109 */       builder.append("** Here are some files that have been overridden:\n");
/* 110 */       builder.append("** \n");
/* 111 */       for (Map.Entry<Class<?>, CodeSource> entry : mismatches.entrySet()) {
/* 112 */         CodeSource codeSource = entry.getValue();
/* 113 */         String url = (codeSource != null) ? codeSource.getLocation().toExternalForm() : "(unknown)";
/* 114 */         builder.append("** '").append(((Class)entry.getKey()).getSimpleName()).append("' came from '").append(url).append("'\n");
/*     */       } 
/* 116 */       builder.append("**\n");
/* 117 */       builder.append("** Please report this to the plugins' developers.\n");
/* 118 */       builder.append(separatorLine).append("\n");
/*     */       
/* 120 */       log.log(Level.SEVERE, builder.toString());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\logging\ClassSourceValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */