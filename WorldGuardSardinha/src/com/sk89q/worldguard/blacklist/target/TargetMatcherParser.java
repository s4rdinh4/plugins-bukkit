/*     */ package com.sk89q.worldguard.blacklist.target;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import com.sk89q.worldedit.blocks.ItemType;
/*     */ import com.sk89q.worldguard.internal.guava.collect.Range;
/*     */ import com.sk89q.worldguard.util.Enums;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.bukkit.Material;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TargetMatcherParser
/*     */ {
/*  36 */   private static final Pattern DATA_VALUE_PATTERN = Pattern.compile("^([^:]+):([^:]+)$");
/*  37 */   private static final Pattern LESS_THAN_PATTERN = Pattern.compile("^<=\\s*([0-9]+)$");
/*  38 */   private static final Pattern GREATER_THAN_PATTERN = Pattern.compile("^>=\\s*([0-9]+)$");
/*  39 */   private static final Pattern RANGE_PATTERN = Pattern.compile("^([0-9]+)\\s*-\\s*([0-9]+)$");
/*     */   
/*     */   public TargetMatcher fromInput(String input) throws TargetMatcherParseException {
/*  42 */     Matcher matcher = DATA_VALUE_PATTERN.matcher(input.trim());
/*  43 */     if (matcher.matches()) {
/*  44 */       return new DataValueRangeMatcher(parseType(matcher.group(1)), parseDataValueRanges(matcher.group(2)));
/*     */     }
/*  46 */     return new WildcardDataMatcher(parseType(input));
/*     */   }
/*     */ 
/*     */   
/*     */   private int parseType(String input) throws TargetMatcherParseException {
/*  51 */     input = input.trim();
/*     */     
/*     */     try {
/*  54 */       return Integer.parseInt(input);
/*  55 */     } catch (NumberFormatException e) {
/*  56 */       int id = getItemID(input);
/*  57 */       if (id > 0) {
/*  58 */         return id;
/*     */       }
/*     */       
/*  61 */       Material material = (Material)Enums.findFuzzyByValue(Material.class, new String[] { input });
/*  62 */       if (material != null) {
/*  63 */         return material.getId();
/*     */       }
/*     */       
/*  66 */       throw new TargetMatcherParseException("Unknown block or item name: " + input);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Predicate<Short> parseDataValueRanges(String input) throws TargetMatcherParseException {
/*  71 */     List<Predicate<Short>> predicates = new ArrayList<Predicate<Short>>();
/*     */     
/*  73 */     for (String part : input.split(";")) {
/*  74 */       predicates.add(parseRange(part));
/*     */     }
/*     */     
/*  77 */     return Predicates.or(predicates);
/*     */   }
/*     */   
/*     */   private Predicate<Short> parseRange(String input) throws TargetMatcherParseException {
/*  81 */     input = input.trim();
/*     */ 
/*     */ 
/*     */     
/*  85 */     Matcher matcher = LESS_THAN_PATTERN.matcher(input);
/*  86 */     if (matcher.matches()) {
/*  87 */       return (Predicate<Short>)Range.atMost(Short.valueOf(Short.parseShort(matcher.group(1))));
/*     */     }
/*     */     
/*  90 */     matcher = GREATER_THAN_PATTERN.matcher(input);
/*  91 */     if (matcher.matches()) {
/*  92 */       return (Predicate<Short>)Range.atLeast(Short.valueOf(Short.parseShort(matcher.group(1))));
/*     */     }
/*     */     
/*  95 */     matcher = RANGE_PATTERN.matcher(input);
/*  96 */     if (matcher.matches()) {
/*  97 */       return (Predicate<Short>)Range.closed(Short.valueOf(Short.parseShort(matcher.group(1))), Short.valueOf(Short.parseShort(matcher.group(2))));
/*     */     }
/*     */     
/*     */     try {
/* 101 */       short s = Short.parseShort(input);
/* 102 */       return (Predicate<Short>)Range.closed(Short.valueOf(s), Short.valueOf(s));
/* 103 */     } catch (NumberFormatException e) {
/* 104 */       throw new TargetMatcherParseException("Unknown data value range: " + input);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getItemID(String name) {
/* 115 */     ItemType type = ItemType.lookup(name);
/* 116 */     if (type != null) {
/* 117 */       return type.getID();
/*     */     }
/* 119 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\target\TargetMatcherParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */