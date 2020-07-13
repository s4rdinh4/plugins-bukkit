/*     */ package com.sk89q.worldguard.internal.guava.cache;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Beta
/*     */ public final class CacheBuilderSpec
/*     */ {
/*  91 */   private static final Splitter KEYS_SPLITTER = Splitter.on(',').trimResults();
/*     */ 
/*     */   
/*  94 */   private static final Splitter KEY_VALUE_SPLITTER = Splitter.on('=').trimResults();
/*     */ 
/*     */ 
/*     */   
/*  98 */   private static final ImmutableMap<String, ValueParser> VALUE_PARSERS = ImmutableMap.builder()
/*  99 */     .put("initialCapacity", new InitialCapacityParser())
/* 100 */     .put("maximumSize", new MaximumSizeParser())
/* 101 */     .put("maximumWeight", new MaximumWeightParser())
/* 102 */     .put("concurrencyLevel", new ConcurrencyLevelParser())
/* 103 */     .put("weakKeys", new KeyStrengthParser(LocalCache.Strength.WEAK))
/* 104 */     .put("softValues", new ValueStrengthParser(LocalCache.Strength.SOFT))
/* 105 */     .put("weakValues", new ValueStrengthParser(LocalCache.Strength.WEAK))
/* 106 */     .put("recordStats", new RecordStatsParser())
/* 107 */     .put("expireAfterAccess", new AccessDurationParser())
/* 108 */     .put("expireAfterWrite", new WriteDurationParser())
/* 109 */     .put("refreshAfterWrite", new RefreshDurationParser())
/* 110 */     .put("refreshInterval", new RefreshDurationParser())
/* 111 */     .build();
/*     */   
/*     */   @VisibleForTesting
/*     */   Integer initialCapacity;
/*     */   
/*     */   @VisibleForTesting
/*     */   Long maximumSize;
/*     */   @VisibleForTesting
/*     */   Long maximumWeight;
/*     */   @VisibleForTesting
/*     */   Integer concurrencyLevel;
/*     */   @VisibleForTesting
/*     */   LocalCache.Strength keyStrength;
/*     */   @VisibleForTesting
/*     */   LocalCache.Strength valueStrength;
/*     */   @VisibleForTesting
/*     */   Boolean recordStats;
/*     */   @VisibleForTesting
/*     */   long writeExpirationDuration;
/*     */   @VisibleForTesting
/*     */   TimeUnit writeExpirationTimeUnit;
/*     */   @VisibleForTesting
/*     */   long accessExpirationDuration;
/*     */   @VisibleForTesting
/*     */   TimeUnit accessExpirationTimeUnit;
/*     */   @VisibleForTesting
/*     */   long refreshDuration;
/*     */   @VisibleForTesting
/*     */   TimeUnit refreshTimeUnit;
/*     */   private final String specification;
/*     */   
/*     */   private CacheBuilderSpec(String specification) {
/* 143 */     this.specification = specification;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CacheBuilderSpec parse(String cacheBuilderSpecification) {
/* 152 */     CacheBuilderSpec spec = new CacheBuilderSpec(cacheBuilderSpecification);
/* 153 */     if (!cacheBuilderSpecification.isEmpty()) {
/* 154 */       for (String keyValuePair : KEYS_SPLITTER.split(cacheBuilderSpecification)) {
/* 155 */         ImmutableList<String> immutableList = ImmutableList.copyOf(KEY_VALUE_SPLITTER.split(keyValuePair));
/* 156 */         Preconditions.checkArgument(!immutableList.isEmpty(), "blank key-value pair");
/* 157 */         Preconditions.checkArgument((immutableList.size() <= 2), "key-value pair %s with more than one equals sign", new Object[] { keyValuePair });
/*     */ 
/*     */ 
/*     */         
/* 161 */         String key = immutableList.get(0);
/* 162 */         ValueParser valueParser = (ValueParser)VALUE_PARSERS.get(key);
/* 163 */         Preconditions.checkArgument((valueParser != null), "unknown key %s", new Object[] { key });
/*     */         
/* 165 */         String value = (immutableList.size() == 1) ? null : immutableList.get(1);
/* 166 */         valueParser.parse(spec, key, value);
/*     */       } 
/*     */     }
/*     */     
/* 170 */     return spec;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CacheBuilderSpec disableCaching() {
/* 178 */     return parse("maximumSize=0");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CacheBuilder<Object, Object> toCacheBuilder() {
/* 185 */     CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder();
/* 186 */     if (this.initialCapacity != null) {
/* 187 */       builder.initialCapacity(this.initialCapacity.intValue());
/*     */     }
/* 189 */     if (this.maximumSize != null) {
/* 190 */       builder.maximumSize(this.maximumSize.longValue());
/*     */     }
/* 192 */     if (this.maximumWeight != null) {
/* 193 */       builder.maximumWeight(this.maximumWeight.longValue());
/*     */     }
/* 195 */     if (this.concurrencyLevel != null) {
/* 196 */       builder.concurrencyLevel(this.concurrencyLevel.intValue());
/*     */     }
/* 198 */     if (this.keyStrength != null) {
/* 199 */       switch (this.keyStrength) {
/*     */         case WEAK:
/* 201 */           builder.weakKeys();
/*     */           break;
/*     */         default:
/* 204 */           throw new AssertionError();
/*     */       } 
/*     */     }
/* 207 */     if (this.valueStrength != null) {
/* 208 */       switch (this.valueStrength) {
/*     */         case SOFT:
/* 210 */           builder.softValues();
/*     */           break;
/*     */         case WEAK:
/* 213 */           builder.weakValues();
/*     */           break;
/*     */         default:
/* 216 */           throw new AssertionError();
/*     */       } 
/*     */     }
/* 219 */     if (this.recordStats != null && this.recordStats.booleanValue()) {
/* 220 */       builder.recordStats();
/*     */     }
/* 222 */     if (this.writeExpirationTimeUnit != null) {
/* 223 */       builder.expireAfterWrite(this.writeExpirationDuration, this.writeExpirationTimeUnit);
/*     */     }
/* 225 */     if (this.accessExpirationTimeUnit != null) {
/* 226 */       builder.expireAfterAccess(this.accessExpirationDuration, this.accessExpirationTimeUnit);
/*     */     }
/* 228 */     if (this.refreshTimeUnit != null) {
/* 229 */       builder.refreshAfterWrite(this.refreshDuration, this.refreshTimeUnit);
/*     */     }
/*     */     
/* 232 */     return builder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toParsableString() {
/* 242 */     return this.specification;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 251 */     return MoreObjects.toStringHelper(this).addValue(toParsableString()).toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 256 */     return Objects.hashCode(new Object[] { this.initialCapacity, this.maximumSize, this.maximumWeight, this.concurrencyLevel, this.keyStrength, this.valueStrength, this.recordStats, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 264 */           durationInNanos(this.writeExpirationDuration, this.writeExpirationTimeUnit), 
/* 265 */           durationInNanos(this.accessExpirationDuration, this.accessExpirationTimeUnit), 
/* 266 */           durationInNanos(this.refreshDuration, this.refreshTimeUnit) });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object obj) {
/* 271 */     if (this == obj) {
/* 272 */       return true;
/*     */     }
/* 274 */     if (!(obj instanceof CacheBuilderSpec)) {
/* 275 */       return false;
/*     */     }
/* 277 */     CacheBuilderSpec that = (CacheBuilderSpec)obj;
/* 278 */     return (Objects.equal(this.initialCapacity, that.initialCapacity) && 
/* 279 */       Objects.equal(this.maximumSize, that.maximumSize) && 
/* 280 */       Objects.equal(this.maximumWeight, that.maximumWeight) && 
/* 281 */       Objects.equal(this.concurrencyLevel, that.concurrencyLevel) && 
/* 282 */       Objects.equal(this.keyStrength, that.keyStrength) && 
/* 283 */       Objects.equal(this.valueStrength, that.valueStrength) && 
/* 284 */       Objects.equal(this.recordStats, that.recordStats) && 
/* 285 */       Objects.equal(durationInNanos(this.writeExpirationDuration, this.writeExpirationTimeUnit), 
/* 286 */         durationInNanos(that.writeExpirationDuration, that.writeExpirationTimeUnit)) && 
/* 287 */       Objects.equal(durationInNanos(this.accessExpirationDuration, this.accessExpirationTimeUnit), 
/* 288 */         durationInNanos(that.accessExpirationDuration, that.accessExpirationTimeUnit)) && 
/* 289 */       Objects.equal(durationInNanos(this.refreshDuration, this.refreshTimeUnit), 
/* 290 */         durationInNanos(that.refreshDuration, that.refreshTimeUnit)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static Long durationInNanos(long duration, @Nullable TimeUnit unit) {
/* 299 */     return (unit == null) ? null : Long.valueOf(unit.toNanos(duration));
/*     */   }
/*     */   
/*     */   static abstract class IntegerParser
/*     */     implements ValueParser
/*     */   {
/*     */     protected abstract void parseInteger(CacheBuilderSpec param1CacheBuilderSpec, int param1Int);
/*     */     
/*     */     public void parse(CacheBuilderSpec spec, String key, String value) {
/* 308 */       Preconditions.checkArgument((value != null && !value.isEmpty()), "value of key %s omitted", new Object[] { key });
/*     */       try {
/* 310 */         parseInteger(spec, Integer.parseInt(value));
/* 311 */       } catch (NumberFormatException e) {
/* 312 */         throw new IllegalArgumentException(
/* 313 */             String.format("key %s value set to %s, must be integer", new Object[] { key, value }), e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static abstract class LongParser
/*     */     implements ValueParser
/*     */   {
/*     */     protected abstract void parseLong(CacheBuilderSpec param1CacheBuilderSpec, long param1Long);
/*     */     
/*     */     public void parse(CacheBuilderSpec spec, String key, String value) {
/* 324 */       Preconditions.checkArgument((value != null && !value.isEmpty()), "value of key %s omitted", new Object[] { key });
/*     */       try {
/* 326 */         parseLong(spec, Long.parseLong(value));
/* 327 */       } catch (NumberFormatException e) {
/* 328 */         throw new IllegalArgumentException(
/* 329 */             String.format("key %s value set to %s, must be integer", new Object[] { key, value }), e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class InitialCapacityParser
/*     */     extends IntegerParser
/*     */   {
/*     */     protected void parseInteger(CacheBuilderSpec spec, int value) {
/* 338 */       Preconditions.checkArgument((spec.initialCapacity == null), "initial capacity was already set to ", new Object[] { spec.initialCapacity });
/*     */       
/* 340 */       spec.initialCapacity = Integer.valueOf(value);
/*     */     }
/*     */   }
/*     */   
/*     */   static class MaximumSizeParser
/*     */     extends LongParser
/*     */   {
/*     */     protected void parseLong(CacheBuilderSpec spec, long value) {
/* 348 */       Preconditions.checkArgument((spec.maximumSize == null), "maximum size was already set to ", new Object[] { spec.maximumSize });
/*     */       
/* 350 */       Preconditions.checkArgument((spec.maximumWeight == null), "maximum weight was already set to ", new Object[] { spec.maximumWeight });
/*     */       
/* 352 */       spec.maximumSize = Long.valueOf(value);
/*     */     }
/*     */   }
/*     */   
/*     */   static class MaximumWeightParser
/*     */     extends LongParser
/*     */   {
/*     */     protected void parseLong(CacheBuilderSpec spec, long value) {
/* 360 */       Preconditions.checkArgument((spec.maximumWeight == null), "maximum weight was already set to ", new Object[] { spec.maximumWeight });
/*     */       
/* 362 */       Preconditions.checkArgument((spec.maximumSize == null), "maximum size was already set to ", new Object[] { spec.maximumSize });
/*     */       
/* 364 */       spec.maximumWeight = Long.valueOf(value);
/*     */     }
/*     */   }
/*     */   
/*     */   static class ConcurrencyLevelParser
/*     */     extends IntegerParser
/*     */   {
/*     */     protected void parseInteger(CacheBuilderSpec spec, int value) {
/* 372 */       Preconditions.checkArgument((spec.concurrencyLevel == null), "concurrency level was already set to ", new Object[] { spec.concurrencyLevel });
/*     */       
/* 374 */       spec.concurrencyLevel = Integer.valueOf(value);
/*     */     }
/*     */   }
/*     */   
/*     */   static class KeyStrengthParser
/*     */     implements ValueParser {
/*     */     private final LocalCache.Strength strength;
/*     */     
/*     */     public KeyStrengthParser(LocalCache.Strength strength) {
/* 383 */       this.strength = strength;
/*     */     }
/*     */ 
/*     */     
/*     */     public void parse(CacheBuilderSpec spec, String key, @Nullable String value) {
/* 388 */       Preconditions.checkArgument((value == null), "key %s does not take values", new Object[] { key });
/* 389 */       Preconditions.checkArgument((spec.keyStrength == null), "%s was already set to %s", new Object[] { key, spec.keyStrength });
/* 390 */       spec.keyStrength = this.strength;
/*     */     }
/*     */   }
/*     */   
/*     */   static class ValueStrengthParser
/*     */     implements ValueParser {
/*     */     private final LocalCache.Strength strength;
/*     */     
/*     */     public ValueStrengthParser(LocalCache.Strength strength) {
/* 399 */       this.strength = strength;
/*     */     }
/*     */ 
/*     */     
/*     */     public void parse(CacheBuilderSpec spec, String key, @Nullable String value) {
/* 404 */       Preconditions.checkArgument((value == null), "key %s does not take values", new Object[] { key });
/* 405 */       Preconditions.checkArgument((spec.valueStrength == null), "%s was already set to %s", new Object[] { key, spec.valueStrength });
/*     */ 
/*     */       
/* 408 */       spec.valueStrength = this.strength;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class RecordStatsParser
/*     */     implements ValueParser
/*     */   {
/*     */     public void parse(CacheBuilderSpec spec, String key, @Nullable String value) {
/* 417 */       Preconditions.checkArgument((value == null), "recordStats does not take values");
/* 418 */       Preconditions.checkArgument((spec.recordStats == null), "recordStats already set");
/* 419 */       spec.recordStats = Boolean.valueOf(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static abstract class DurationParser
/*     */     implements ValueParser
/*     */   {
/*     */     protected abstract void parseDuration(CacheBuilderSpec param1CacheBuilderSpec, long param1Long, TimeUnit param1TimeUnit);
/*     */ 
/*     */     
/*     */     public void parse(CacheBuilderSpec spec, String key, String value) {
/* 432 */       Preconditions.checkArgument((value != null && !value.isEmpty()), "value of key %s omitted", new Object[] { key }); try {
/*     */         TimeUnit timeUnit;
/* 434 */         char lastChar = value.charAt(value.length() - 1);
/*     */         
/* 436 */         switch (lastChar) {
/*     */           case 'd':
/* 438 */             timeUnit = TimeUnit.DAYS;
/*     */             break;
/*     */           case 'h':
/* 441 */             timeUnit = TimeUnit.HOURS;
/*     */             break;
/*     */           case 'm':
/* 444 */             timeUnit = TimeUnit.MINUTES;
/*     */             break;
/*     */           case 's':
/* 447 */             timeUnit = TimeUnit.SECONDS;
/*     */             break;
/*     */           default:
/* 450 */             throw new IllegalArgumentException(
/* 451 */                 String.format("key %s invalid format.  was %s, must end with one of [dDhHmMsS]", new Object[] { key, value }));
/*     */         } 
/*     */ 
/*     */         
/* 455 */         long duration = Long.parseLong(value.substring(0, value.length() - 1));
/* 456 */         parseDuration(spec, duration, timeUnit);
/* 457 */       } catch (NumberFormatException e) {
/* 458 */         throw new IllegalArgumentException(
/* 459 */             String.format("key %s value set to %s, must be integer", new Object[] { key, value }));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class AccessDurationParser
/*     */     extends DurationParser {
/*     */     protected void parseDuration(CacheBuilderSpec spec, long duration, TimeUnit unit) {
/* 467 */       Preconditions.checkArgument((spec.accessExpirationTimeUnit == null), "expireAfterAccess already set");
/* 468 */       spec.accessExpirationDuration = duration;
/* 469 */       spec.accessExpirationTimeUnit = unit;
/*     */     }
/*     */   }
/*     */   
/*     */   static class WriteDurationParser
/*     */     extends DurationParser {
/*     */     protected void parseDuration(CacheBuilderSpec spec, long duration, TimeUnit unit) {
/* 476 */       Preconditions.checkArgument((spec.writeExpirationTimeUnit == null), "expireAfterWrite already set");
/* 477 */       spec.writeExpirationDuration = duration;
/* 478 */       spec.writeExpirationTimeUnit = unit;
/*     */     }
/*     */   }
/*     */   
/*     */   static class RefreshDurationParser
/*     */     extends DurationParser {
/*     */     protected void parseDuration(CacheBuilderSpec spec, long duration, TimeUnit unit) {
/* 485 */       Preconditions.checkArgument((spec.refreshTimeUnit == null), "refreshAfterWrite already set");
/* 486 */       spec.refreshDuration = duration;
/* 487 */       spec.refreshTimeUnit = unit;
/*     */     }
/*     */   }
/*     */   
/*     */   private static interface ValueParser {
/*     */     void parse(CacheBuilderSpec param1CacheBuilderSpec, String param1String1, @Nullable String param1String2);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\guava\cache\CacheBuilderSpec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */