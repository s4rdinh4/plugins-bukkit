/*     */ package com.sk89q.worldguard.util.io;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Throwables;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public final class Closer
/*     */   implements Closeable
/*     */ {
/*  41 */   private static final Logger logger = Logger.getLogger(Closer.class.getCanonicalName());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   private static final Suppressor SUPPRESSOR = SuppressingSuppressor.isAvailable() ? SuppressingSuppressor.INSTANCE : LoggingSuppressor.INSTANCE;
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   final Suppressor suppressor;
/*     */ 
/*     */   
/*     */   public static Closer create() {
/*  54 */     return new Closer(SUPPRESSOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   private final Deque<Closeable> stack = new ArrayDeque<Closeable>(4);
/*     */   private Throwable thrown;
/*     */   
/*     */   @VisibleForTesting
/*     */   Closer(Suppressor suppressor) {
/*  66 */     this.suppressor = (Suppressor)Preconditions.checkNotNull(suppressor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <C extends Closeable> C register(C closeable) {
/*  77 */     this.stack.push((Closeable)closeable);
/*  78 */     return closeable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <C extends Connection> C register(final C connection) {
/*  88 */     register(new Closeable()
/*     */         {
/*     */           public void close() throws IOException {
/*     */             try {
/*  92 */               connection.close();
/*  93 */             } catch (SQLException e) {
/*  94 */               throw new IOException("Failed to close", e);
/*     */             } 
/*     */           }
/*     */         });
/*  98 */     return connection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <C extends Statement> C register(final C statement) {
/* 108 */     register(new Closeable()
/*     */         {
/*     */           public void close() throws IOException {
/*     */             try {
/* 112 */               statement.close();
/* 113 */             } catch (SQLException e) {
/* 114 */               throw new IOException("Failed to close", e);
/*     */             } 
/*     */           }
/*     */         });
/* 118 */     return statement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <C extends ResultSet> C register(final C resultSet) {
/* 128 */     register(new Closeable()
/*     */         {
/*     */           public void close() throws IOException {
/*     */             try {
/* 132 */               resultSet.close();
/* 133 */             } catch (SQLException e) {
/* 134 */               throw new IOException("Failed to close", e);
/*     */             } 
/*     */           }
/*     */         });
/* 138 */     return resultSet;
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
/*     */   public RuntimeException rethrow(Throwable e) throws IOException {
/* 155 */     this.thrown = e;
/* 156 */     Throwables.propagateIfPossible(e, IOException.class);
/* 157 */     throw Throwables.propagate(e);
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
/*     */   public <X extends Exception> RuntimeException rethrow(Throwable e, Class<X> declaredType) throws IOException, X {
/* 176 */     this.thrown = e;
/* 177 */     Throwables.propagateIfPossible(e, IOException.class);
/* 178 */     Throwables.propagateIfPossible(e, declaredType);
/* 179 */     throw Throwables.propagate(e);
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
/*     */   public <X1 extends Exception, X2 extends Exception> RuntimeException rethrow(Throwable e, Class<X1> declaredType1, Class<X2> declaredType2) throws IOException, X1, X2 {
/* 199 */     this.thrown = e;
/* 200 */     Throwables.propagateIfPossible(e, IOException.class);
/* 201 */     Throwables.propagateIfPossible(e, declaredType1, declaredType2);
/* 202 */     throw Throwables.propagate(e);
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
/*     */   public void close() throws IOException {
/* 214 */     Throwable throwable = this.thrown;
/*     */ 
/*     */     
/* 217 */     while (!this.stack.isEmpty()) {
/* 218 */       Closeable closeable = this.stack.pop();
/*     */       try {
/* 220 */         closeable.close();
/* 221 */       } catch (Throwable e) {
/* 222 */         if (throwable == null) {
/* 223 */           throwable = e; continue;
/*     */         } 
/* 225 */         this.suppressor.suppress(closeable, throwable, e);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 230 */     if (this.thrown == null && throwable != null) {
/* 231 */       Throwables.propagateIfPossible(throwable, IOException.class);
/* 232 */       throw new AssertionError(throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeQuietly() {
/*     */     try {
/* 241 */       close();
/* 242 */     } catch (IOException ignored) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static interface Suppressor
/*     */   {
/*     */     void suppress(Closeable param1Closeable, Throwable param1Throwable1, Throwable param1Throwable2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static final class LoggingSuppressor
/*     */     implements Suppressor
/*     */   {
/* 263 */     static final LoggingSuppressor INSTANCE = new LoggingSuppressor();
/*     */ 
/*     */ 
/*     */     
/*     */     public void suppress(Closeable closeable, Throwable thrown, Throwable suppressed) {
/* 268 */       Closer.logger.log(Level.WARNING, "Suppressing exception thrown when closing " + closeable, suppressed);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static final class SuppressingSuppressor
/*     */     implements Suppressor
/*     */   {
/* 278 */     static final SuppressingSuppressor INSTANCE = new SuppressingSuppressor();
/*     */     
/*     */     static boolean isAvailable() {
/* 281 */       return (addSuppressed != null);
/*     */     }
/*     */     
/* 284 */     static final Method addSuppressed = getAddSuppressed();
/*     */     
/*     */     private static Method getAddSuppressed() {
/*     */       try {
/* 288 */         return Throwable.class.getMethod("addSuppressed", new Class[] { Throwable.class });
/* 289 */       } catch (Throwable e) {
/* 290 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void suppress(Closeable closeable, Throwable thrown, Throwable suppressed) {
/* 297 */       if (thrown == suppressed) {
/*     */         return;
/*     */       }
/*     */       try {
/* 301 */         addSuppressed.invoke(thrown, new Object[] { suppressed });
/* 302 */       } catch (Throwable e) {
/*     */         
/* 304 */         Closer.LoggingSuppressor.INSTANCE.suppress(closeable, thrown, suppressed);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\io\Closer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */