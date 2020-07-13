/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.util;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import java.util.regex.Matcher;
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
/*     */ public class PlaceholderReplacer
/*     */ {
/*  34 */   public static final PlaceholderReplacer NO_PLACEHOLDERS = new PlaceholderReplacer(new HashMap<String, String>(), "", "");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Map<String, String> placeholders;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String placeholderPrefix;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String placeholderSuffix;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlaceholderReplacer(Map<String, String> placeholders, String placeholderPrefix, String placeholderSuffix) {
/*  59 */     this.placeholders = placeholders;
/*  60 */     this.placeholderPrefix = placeholderPrefix;
/*  61 */     this.placeholderSuffix = placeholderSuffix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String replacePlaceholders(String input) {
/*  71 */     String noPlaceholders = input;
/*     */     
/*  73 */     for (String placeholder : this.placeholders.keySet()) {
/*  74 */       String searchTerm = this.placeholderPrefix + placeholder + this.placeholderSuffix;
/*  75 */       String value = this.placeholders.get(placeholder);
/*  76 */       noPlaceholders = StringUtils.replaceAll(noPlaceholders, searchTerm, (value == null) ? "" : value);
/*     */     } 
/*  78 */     checkForUnmatchedPlaceholderExpression(noPlaceholders);
/*     */     
/*  80 */     return noPlaceholders;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkForUnmatchedPlaceholderExpression(String input) {
/*  91 */     String regex = Pattern.quote(this.placeholderPrefix) + "(.+?)" + Pattern.quote(this.placeholderSuffix);
/*  92 */     Matcher matcher = Pattern.compile(regex).matcher(input);
/*     */     
/*  94 */     Set<String> unmatchedPlaceHolderExpressions = new TreeSet<String>();
/*  95 */     while (matcher.find()) {
/*  96 */       unmatchedPlaceHolderExpressions.add(matcher.group());
/*     */     }
/*     */     
/*  99 */     if (!unmatchedPlaceHolderExpressions.isEmpty())
/* 100 */       throw new FlywayException("No value provided for placeholder expressions: " + StringUtils.collectionToCommaDelimitedString(unmatchedPlaceHolderExpressions) + ".  Check your configuration!"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\interna\\util\PlaceholderReplacer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */