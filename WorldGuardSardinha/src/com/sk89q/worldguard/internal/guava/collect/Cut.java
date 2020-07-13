/*     */ package com.sk89q.worldguard.internal.guava.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.BoundType;
/*     */ import com.google.common.collect.DiscreteDomain;
/*     */ import com.google.common.primitives.Booleans;
/*     */ import java.io.Serializable;
/*     */ import java.util.NoSuchElementException;
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
/*     */ @GwtCompatible
/*     */ abstract class Cut<C extends Comparable>
/*     */   implements Comparable<Cut<C>>, Serializable
/*     */ {
/*     */   final C endpoint;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   Cut(@Nullable C endpoint) {
/*  42 */     this.endpoint = endpoint;
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
/*     */   Cut<C> canonical(DiscreteDomain<C> domain) {
/*  64 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(Cut<C> that) {
/*  70 */     if (that == belowAll()) {
/*  71 */       return 1;
/*     */     }
/*  73 */     if (that == aboveAll()) {
/*  74 */       return -1;
/*     */     }
/*  76 */     int result = Range.compareOrThrow((Comparable)this.endpoint, (Comparable)that.endpoint);
/*  77 */     if (result != 0) {
/*  78 */       return result;
/*     */     }
/*     */     
/*  81 */     return Booleans.compare(this instanceof AboveValue, that instanceof AboveValue);
/*     */   }
/*     */ 
/*     */   
/*     */   C endpoint() {
/*  86 */     return this.endpoint;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  91 */     if (obj instanceof Cut) {
/*     */       
/*  93 */       Cut<C> that = (Cut<C>)obj;
/*     */       try {
/*  95 */         int compareResult = compareTo(that);
/*  96 */         return (compareResult == 0);
/*  97 */       } catch (ClassCastException ignored) {}
/*     */     } 
/*     */     
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <C extends Comparable> Cut<C> belowAll() {
/* 109 */     return BelowAll.INSTANCE;
/*     */   }
/*     */   
/*     */   private static final class BelowAll
/*     */     extends Cut<Comparable<?>>
/*     */   {
/* 115 */     private static final BelowAll INSTANCE = new BelowAll(); private static final long serialVersionUID = 0L;
/*     */     
/*     */     private BelowAll() {
/* 118 */       super(null);
/*     */     }
/*     */     Comparable<?> endpoint() {
/* 121 */       throw new IllegalStateException("range unbounded on this side");
/*     */     }
/*     */     boolean isLessThan(Comparable<?> value) {
/* 124 */       return true;
/*     */     }
/*     */     BoundType typeAsLowerBound() {
/* 127 */       throw new IllegalStateException();
/*     */     }
/*     */     BoundType typeAsUpperBound() {
/* 130 */       throw new AssertionError("this statement should be unreachable");
/*     */     }
/*     */     
/*     */     Cut<Comparable<?>> withLowerBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> domain) {
/* 134 */       throw new IllegalStateException();
/*     */     }
/*     */     
/*     */     Cut<Comparable<?>> withUpperBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> domain) {
/* 138 */       throw new AssertionError("this statement should be unreachable");
/*     */     }
/*     */     void describeAsLowerBound(StringBuilder sb) {
/* 141 */       sb.append("(-∞");
/*     */     }
/*     */     void describeAsUpperBound(StringBuilder sb) {
/* 144 */       throw new AssertionError();
/*     */     }
/*     */     
/*     */     Comparable<?> leastValueAbove(DiscreteDomain<Comparable<?>> domain) {
/* 148 */       return domain.minValue();
/*     */     }
/*     */     
/*     */     Comparable<?> greatestValueBelow(DiscreteDomain<Comparable<?>> domain) {
/* 152 */       throw new AssertionError();
/*     */     }
/*     */     
/*     */     Cut<Comparable<?>> canonical(DiscreteDomain<Comparable<?>> domain) {
/*     */       try {
/* 157 */         return Cut.belowValue(domain.minValue());
/* 158 */       } catch (NoSuchElementException e) {
/* 159 */         return this;
/*     */       } 
/*     */     }
/*     */     public int compareTo(Cut<Comparable<?>> o) {
/* 163 */       return (o == this) ? 0 : -1;
/*     */     }
/*     */     public String toString() {
/* 166 */       return "-∞";
/*     */     }
/*     */     private Object readResolve() {
/* 169 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <C extends Comparable> Cut<C> aboveAll() {
/* 180 */     return AboveAll.INSTANCE;
/*     */   }
/*     */   
/*     */   private static final class AboveAll extends Cut<Comparable<?>> {
/* 184 */     private static final AboveAll INSTANCE = new AboveAll(); private static final long serialVersionUID = 0L;
/*     */     
/*     */     private AboveAll() {
/* 187 */       super(null);
/*     */     }
/*     */     Comparable<?> endpoint() {
/* 190 */       throw new IllegalStateException("range unbounded on this side");
/*     */     }
/*     */     boolean isLessThan(Comparable<?> value) {
/* 193 */       return false;
/*     */     }
/*     */     BoundType typeAsLowerBound() {
/* 196 */       throw new AssertionError("this statement should be unreachable");
/*     */     }
/*     */     BoundType typeAsUpperBound() {
/* 199 */       throw new IllegalStateException();
/*     */     }
/*     */     
/*     */     Cut<Comparable<?>> withLowerBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> domain) {
/* 203 */       throw new AssertionError("this statement should be unreachable");
/*     */     }
/*     */     
/*     */     Cut<Comparable<?>> withUpperBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> domain) {
/* 207 */       throw new IllegalStateException();
/*     */     }
/*     */     void describeAsLowerBound(StringBuilder sb) {
/* 210 */       throw new AssertionError();
/*     */     }
/*     */     void describeAsUpperBound(StringBuilder sb) {
/* 213 */       sb.append("+∞)");
/*     */     }
/*     */     
/*     */     Comparable<?> leastValueAbove(DiscreteDomain<Comparable<?>> domain) {
/* 217 */       throw new AssertionError();
/*     */     }
/*     */     
/*     */     Comparable<?> greatestValueBelow(DiscreteDomain<Comparable<?>> domain) {
/* 221 */       return domain.maxValue();
/*     */     }
/*     */     public int compareTo(Cut<Comparable<?>> o) {
/* 224 */       return (o == this) ? 0 : 1;
/*     */     }
/*     */     public String toString() {
/* 227 */       return "+∞";
/*     */     }
/*     */     private Object readResolve() {
/* 230 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static <C extends Comparable> Cut<C> belowValue(C endpoint) {
/* 236 */     return new BelowValue<C>(endpoint);
/*     */   }
/*     */   private static final class BelowValue<C extends Comparable> extends Cut<C> { private static final long serialVersionUID = 0L;
/*     */     
/*     */     BelowValue(C endpoint) {
/* 241 */       super((C)Preconditions.checkNotNull(endpoint));
/*     */     }
/*     */     
/*     */     boolean isLessThan(C value) {
/* 245 */       return (Range.compareOrThrow((Comparable)this.endpoint, (Comparable)value) <= 0);
/*     */     }
/*     */     BoundType typeAsLowerBound() {
/* 248 */       return BoundType.CLOSED;
/*     */     }
/*     */     BoundType typeAsUpperBound() {
/* 251 */       return BoundType.OPEN;
/*     */     } Cut<C> withLowerBoundType(BoundType boundType, DiscreteDomain<C> domain) {
/*     */       Comparable comparable;
/* 254 */       switch (boundType) {
/*     */         case CLOSED:
/* 256 */           return this;
/*     */         case OPEN:
/* 258 */           comparable = domain.previous((Comparable)this.endpoint);
/* 259 */           return (comparable == null) ? Cut.<C>belowAll() : new Cut.AboveValue<C>((C)comparable);
/*     */       } 
/* 261 */       throw new AssertionError();
/*     */     }
/*     */     Cut<C> withUpperBoundType(BoundType boundType, DiscreteDomain<C> domain) {
/*     */       Comparable comparable;
/* 265 */       switch (boundType) {
/*     */         case CLOSED:
/* 267 */           comparable = domain.previous((Comparable)this.endpoint);
/* 268 */           return (comparable == null) ? Cut.<C>aboveAll() : new Cut.AboveValue<C>((C)comparable);
/*     */         case OPEN:
/* 270 */           return this;
/*     */       } 
/* 272 */       throw new AssertionError();
/*     */     }
/*     */     
/*     */     void describeAsLowerBound(StringBuilder sb) {
/* 276 */       sb.append('[').append(this.endpoint);
/*     */     }
/*     */     void describeAsUpperBound(StringBuilder sb) {
/* 279 */       sb.append(this.endpoint).append(')');
/*     */     }
/*     */     C leastValueAbove(DiscreteDomain<C> domain) {
/* 282 */       return this.endpoint;
/*     */     }
/*     */     C greatestValueBelow(DiscreteDomain<C> domain) {
/* 285 */       return (C)domain.previous((Comparable)this.endpoint);
/*     */     }
/*     */     public int hashCode() {
/* 288 */       return this.endpoint.hashCode();
/*     */     }
/*     */     public String toString() {
/* 291 */       return "\\" + this.endpoint + "/";
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   static <C extends Comparable> Cut<C> aboveValue(C endpoint) {
/* 297 */     return new AboveValue<C>(endpoint);
/*     */   } abstract boolean isLessThan(C paramC); abstract BoundType typeAsLowerBound(); abstract BoundType typeAsUpperBound(); abstract Cut<C> withLowerBoundType(BoundType paramBoundType, DiscreteDomain<C> paramDiscreteDomain); abstract Cut<C> withUpperBoundType(BoundType paramBoundType, DiscreteDomain<C> paramDiscreteDomain); abstract void describeAsLowerBound(StringBuilder paramStringBuilder); abstract void describeAsUpperBound(StringBuilder paramStringBuilder);
/*     */   abstract C leastValueAbove(DiscreteDomain<C> paramDiscreteDomain);
/*     */   abstract C greatestValueBelow(DiscreteDomain<C> paramDiscreteDomain);
/*     */   private static final class AboveValue<C extends Comparable> extends Cut<C> { AboveValue(C endpoint) {
/* 302 */       super((C)Preconditions.checkNotNull(endpoint));
/*     */     }
/*     */     private static final long serialVersionUID = 0L;
/*     */     boolean isLessThan(C value) {
/* 306 */       return (Range.compareOrThrow((Comparable)this.endpoint, (Comparable)value) < 0);
/*     */     }
/*     */     BoundType typeAsLowerBound() {
/* 309 */       return BoundType.OPEN;
/*     */     }
/*     */     BoundType typeAsUpperBound() {
/* 312 */       return BoundType.CLOSED;
/*     */     } Cut<C> withLowerBoundType(BoundType boundType, DiscreteDomain<C> domain) {
/*     */       Comparable comparable;
/* 315 */       switch (boundType) {
/*     */         case OPEN:
/* 317 */           return this;
/*     */         case CLOSED:
/* 319 */           comparable = domain.next((Comparable)this.endpoint);
/* 320 */           return (comparable == null) ? Cut.<C>belowAll() : belowValue((C)comparable);
/*     */       } 
/* 322 */       throw new AssertionError();
/*     */     }
/*     */     Cut<C> withUpperBoundType(BoundType boundType, DiscreteDomain<C> domain) {
/*     */       Comparable comparable;
/* 326 */       switch (boundType) {
/*     */         case OPEN:
/* 328 */           comparable = domain.next((Comparable)this.endpoint);
/* 329 */           return (comparable == null) ? Cut.<C>aboveAll() : belowValue((C)comparable);
/*     */         case CLOSED:
/* 331 */           return this;
/*     */       } 
/* 333 */       throw new AssertionError();
/*     */     }
/*     */     
/*     */     void describeAsLowerBound(StringBuilder sb) {
/* 337 */       sb.append('(').append(this.endpoint);
/*     */     }
/*     */     void describeAsUpperBound(StringBuilder sb) {
/* 340 */       sb.append(this.endpoint).append(']');
/*     */     }
/*     */     C leastValueAbove(DiscreteDomain<C> domain) {
/* 343 */       return (C)domain.next((Comparable)this.endpoint);
/*     */     }
/*     */     C greatestValueBelow(DiscreteDomain<C> domain) {
/* 346 */       return this.endpoint;
/*     */     }
/*     */     Cut<C> canonical(DiscreteDomain<C> domain) {
/* 349 */       C next = leastValueAbove(domain);
/* 350 */       return (next != null) ? belowValue(next) : Cut.<C>aboveAll();
/*     */     }
/*     */     public int hashCode() {
/* 353 */       return this.endpoint.hashCode() ^ 0xFFFFFFFF;
/*     */     }
/*     */     public String toString() {
/* 356 */       return "/" + this.endpoint + "\\";
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\guava\collect\Cut.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */