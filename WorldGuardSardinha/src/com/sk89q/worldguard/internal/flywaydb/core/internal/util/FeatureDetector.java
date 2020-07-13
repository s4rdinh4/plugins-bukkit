/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.util;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.Log;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.LogFactory;
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
/*     */ public final class FeatureDetector
/*     */ {
/*  25 */   private static final Log LOG = LogFactory.getLog(FeatureDetector.class);
/*     */   
/*     */   private ClassLoader classLoader;
/*     */   
/*     */   private Boolean apacheCommonsLoggingAvailable;
/*     */   
/*     */   private Boolean springJdbcAvailable;
/*     */   private Boolean jbossVFSv2Available;
/*     */   private Boolean jbossVFSv3Available;
/*     */   private Boolean osgiFrameworkAvailable;
/*     */   private Boolean androidAvailable;
/*     */   
/*     */   public FeatureDetector(ClassLoader classLoader) {
/*  38 */     this.classLoader = classLoader;
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
/*     */   public boolean isApacheCommonsLoggingAvailable() {
/*  77 */     if (this.apacheCommonsLoggingAvailable == null) {
/*  78 */       this.apacheCommonsLoggingAvailable = Boolean.valueOf(ClassUtils.isPresent("org.apache.commons.logging.Log", this.classLoader));
/*     */     }
/*     */     
/*  81 */     return this.apacheCommonsLoggingAvailable.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpringJdbcAvailable() {
/*  90 */     if (this.springJdbcAvailable == null) {
/*  91 */       this.springJdbcAvailable = Boolean.valueOf(ClassUtils.isPresent("org.springframework.jdbc.core.JdbcTemplate", this.classLoader));
/*  92 */       LOG.debug("Spring Jdbc available: " + this.springJdbcAvailable);
/*     */     } 
/*     */     
/*  95 */     return this.springJdbcAvailable.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isJBossVFSv2Available() {
/* 104 */     if (this.jbossVFSv2Available == null) {
/* 105 */       this.jbossVFSv2Available = Boolean.valueOf(ClassUtils.isPresent("org.jboss.virtual.VFS", this.classLoader));
/* 106 */       LOG.debug("JBoss VFS v2 available: " + this.jbossVFSv2Available);
/*     */     } 
/*     */     
/* 109 */     return this.jbossVFSv2Available.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isJBossVFSv3Available() {
/* 118 */     if (this.jbossVFSv3Available == null) {
/* 119 */       this.jbossVFSv3Available = Boolean.valueOf(ClassUtils.isPresent("org.jboss.vfs.VFS", this.classLoader));
/* 120 */       LOG.debug("JBoss VFS v3 available: " + this.jbossVFSv3Available);
/*     */     } 
/*     */     
/* 123 */     return this.jbossVFSv3Available.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOsgiFrameworkAvailable() {
/* 132 */     if (this.osgiFrameworkAvailable == null) {
/* 133 */       this.osgiFrameworkAvailable = Boolean.valueOf(ClassUtils.isPresent("org.osgi.framework.Bundle", this.classLoader));
/* 134 */       LOG.debug("OSGi framework available: " + this.osgiFrameworkAvailable);
/*     */     } 
/*     */     
/* 137 */     return this.osgiFrameworkAvailable.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAndroidAvailable() {
/* 146 */     if (this.androidAvailable == null) {
/* 147 */       this.androidAvailable = Boolean.valueOf("Android Runtime".equals(System.getProperty("java.runtime.name")));
/*     */     }
/*     */     
/* 150 */     return this.androidAvailable.booleanValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\interna\\util\FeatureDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */