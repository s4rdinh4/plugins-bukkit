/*     */ package com.sk89q.worldguard.internal.guava.cache;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.io.Serializable;
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
/*     */ @GwtCompatible
/*     */ abstract class Equivalence<T>
/*     */ {
/*     */   public final boolean equivalent(@Nullable T a, @Nullable T b) {
/*  69 */     if (a == b) {
/*  70 */       return true;
/*     */     }
/*  72 */     if (a == null || b == null) {
/*  73 */       return false;
/*     */     }
/*  75 */     return doEquivalent(a, b);
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
/*     */   protected abstract boolean doEquivalent(T paramT1, T paramT2);
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
/*     */   public final int hash(@Nullable T t) {
/* 105 */     if (t == null) {
/* 106 */       return 0;
/*     */     }
/* 108 */     return doHash(t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract int doHash(T paramT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <S extends T> Wrapper<S> wrap(@Nullable S reference) {
/* 128 */     return new Wrapper<S>(this, reference);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Wrapper<T>
/*     */     implements Serializable
/*     */   {
/*     */     private final Equivalence<? super T> equivalence;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private final T reference;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final long serialVersionUID = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Wrapper(Equivalence<? super T> equivalence, @Nullable T reference) {
/* 155 */       this.equivalence = (Equivalence<? super T>)Preconditions.checkNotNull(equivalence);
/* 156 */       this.reference = reference;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public T get() {
/* 162 */       return this.reference;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object obj) {
/* 171 */       if (obj == this) {
/* 172 */         return true;
/*     */       }
/* 174 */       if (obj instanceof Wrapper) {
/* 175 */         Wrapper<?> that = (Wrapper)obj;
/*     */         
/* 177 */         if (this.equivalence.equals(that.equivalence)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 183 */           Equivalence<Object> equivalence = (Equivalence)this.equivalence;
/* 184 */           return equivalence.equivalent(this.reference, that.reference);
/*     */         } 
/*     */       } 
/* 187 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 194 */       return this.equivalence.hash(this.reference);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 202 */       return this.equivalence + ".wrap(" + this.reference + ")";
/*     */     }
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
/*     */   @Beta
/*     */   public final Predicate<T> equivalentTo(@Nullable T target) {
/* 216 */     return new EquivalentToPredicate<T>(this, target);
/*     */   }
/*     */   
/*     */   private static final class EquivalentToPredicate<T> implements Predicate<T>, Serializable {
/*     */     private final Equivalence<T> equivalence;
/*     */     @Nullable
/*     */     private final T target;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     EquivalentToPredicate(Equivalence<T> equivalence, @Nullable T target) {
/* 226 */       this.equivalence = (Equivalence<T>)Preconditions.checkNotNull(equivalence);
/* 227 */       this.target = target;
/*     */     }
/*     */     
/*     */     public boolean apply(@Nullable T input) {
/* 231 */       return this.equivalence.equivalent(input, this.target);
/*     */     }
/*     */     
/*     */     public boolean equals(@Nullable Object obj) {
/* 235 */       if (this == obj) {
/* 236 */         return true;
/*     */       }
/* 238 */       if (obj instanceof EquivalentToPredicate) {
/* 239 */         EquivalentToPredicate<?> that = (EquivalentToPredicate)obj;
/* 240 */         return (this.equivalence.equals(that.equivalence) && 
/* 241 */           Objects.equal(this.target, that.target));
/*     */       } 
/* 243 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 247 */       return Objects.hashCode(new Object[] { this.equivalence, this.target });
/*     */     }
/*     */     
/*     */     public String toString() {
/* 251 */       return this.equivalence + ".equivalentTo(" + this.target + ")";
/*     */     }
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
/*     */   public static Equivalence<Object> equals() {
/* 268 */     return Equals.INSTANCE;
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
/*     */   public static Equivalence<Object> identity() {
/* 280 */     return Identity.INSTANCE;
/*     */   }
/*     */   
/*     */   static final class Equals
/*     */     extends Equivalence<Object> implements Serializable {
/*     */     private static final long serialVersionUID = 1L;
/* 286 */     static final Equals INSTANCE = new Equals();
/*     */     
/*     */     protected boolean doEquivalent(Object a, Object b) {
/* 289 */       return a.equals(b);
/*     */     }
/*     */     protected int doHash(Object o) {
/* 292 */       return o.hashCode();
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/* 296 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class Identity
/*     */     extends Equivalence<Object>
/*     */     implements Serializable
/*     */   {
/* 304 */     static final Identity INSTANCE = new Identity(); private static final long serialVersionUID = 1L;
/*     */     
/*     */     protected boolean doEquivalent(Object a, Object b) {
/* 307 */       return false;
/*     */     }
/*     */     
/*     */     protected int doHash(Object o) {
/* 311 */       return System.identityHashCode(o);
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/* 315 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\guava\cache\Equivalence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */