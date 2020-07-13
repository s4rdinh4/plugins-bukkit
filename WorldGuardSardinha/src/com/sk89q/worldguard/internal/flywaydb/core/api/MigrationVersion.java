/*     */ package com.sk89q.worldguard.internal.flywaydb.core.api;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MigrationVersion
/*     */   implements Comparable<MigrationVersion>
/*     */ {
/*  31 */   public static final MigrationVersion EMPTY = new MigrationVersion(null, "<< Empty Schema >>");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   public static final MigrationVersion LATEST = new MigrationVersion(Long.valueOf(Long.MAX_VALUE), "<< Latest Version >>");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   private static Pattern splitPattern = Pattern.compile("\\.(?=\\d)");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final List<Long> versionParts;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String displayText;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MigrationVersion fromVersion(String version) {
/*  59 */     if (LATEST.getVersion().equals(version)) return LATEST; 
/*  60 */     if (version == null) return EMPTY; 
/*  61 */     return new MigrationVersion(version);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MigrationVersion(String version) {
/*  70 */     String normalizedVersion = version.replace('_', '.');
/*  71 */     this.versionParts = tokenizeToLongs(normalizedVersion);
/*  72 */     this.displayText = normalizedVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MigrationVersion(Long version, String displayText) {
/*  83 */     this.versionParts = new ArrayList<Long>();
/*  84 */     this.versionParts.add(version);
/*  85 */     this.displayText = displayText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  93 */     return this.displayText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 100 */     if (equals(EMPTY)) return null; 
/* 101 */     if (equals(LATEST)) return Long.toString(Long.MAX_VALUE); 
/* 102 */     return this.displayText;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 107 */     if (this == o) return true; 
/* 108 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 110 */     MigrationVersion version1 = (MigrationVersion)o;
/*     */     
/* 112 */     return (compareTo(version1) == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 117 */     return (this.versionParts == null) ? 0 : this.versionParts.hashCode();
/*     */   }
/*     */   
/*     */   public int compareTo(MigrationVersion o) {
/* 121 */     if (o == null) {
/* 122 */       return 1;
/*     */     }
/*     */     
/* 125 */     if (this == EMPTY) {
/* 126 */       return (o == EMPTY) ? 0 : Integer.MIN_VALUE;
/*     */     }
/*     */     
/* 129 */     if (this == LATEST) {
/* 130 */       return (o == LATEST) ? 0 : Integer.MAX_VALUE;
/*     */     }
/*     */     
/* 133 */     if (o == EMPTY) {
/* 134 */       return Integer.MAX_VALUE;
/*     */     }
/*     */     
/* 137 */     if (o == LATEST) {
/* 138 */       return Integer.MIN_VALUE;
/*     */     }
/* 140 */     List<Long> elements1 = this.versionParts;
/* 141 */     List<Long> elements2 = o.versionParts;
/* 142 */     int largestNumberOfElements = Math.max(elements1.size(), elements2.size());
/* 143 */     for (int i = 0; i < largestNumberOfElements; i++) {
/* 144 */       int compared = getOrZero(elements1, i).compareTo(getOrZero(elements2, i));
/* 145 */       if (compared != 0) {
/* 146 */         return compared;
/*     */       }
/*     */     } 
/* 149 */     return 0;
/*     */   }
/*     */   
/*     */   private Long getOrZero(List<Long> elements, int i) {
/* 153 */     return Long.valueOf((i < elements.size()) ? ((Long)elements.get(i)).longValue() : 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Long> tokenizeToLongs(String str) {
/* 163 */     List<Long> numbers = new ArrayList<Long>();
/* 164 */     for (String number : splitPattern.split(str)) {
/*     */       try {
/* 166 */         numbers.add(Long.valueOf(number));
/* 167 */       } catch (NumberFormatException e) {
/* 168 */         throw new FlywayException("Invalid version containing non-numeric characters. Only 0..9 and . are allowed. Invalid version: " + str);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 173 */     for (int i = numbers.size() - 1; i > 0 && (
/* 174 */       (Long)numbers.get(i)).longValue() == 0L; i--) {
/* 175 */       numbers.remove(i);
/*     */     }
/* 177 */     return numbers;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\api\MigrationVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */