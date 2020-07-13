/*     */ package com.sk89q.worldguard.protection.flags;
/*     */ 
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import org.bukkit.command.CommandSender;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnumFlag<T extends Enum<T>>
/*     */   extends Flag<T>
/*     */ {
/*     */   private Class<T> enumClass;
/*     */   
/*     */   public EnumFlag(String name, Class<T> enumClass, RegionGroup defaultGroup) {
/*  34 */     super(name, defaultGroup);
/*  35 */     this.enumClass = enumClass;
/*     */   }
/*     */   
/*     */   public EnumFlag(String name, Class<T> enumClass) {
/*  39 */     super(name);
/*  40 */     this.enumClass = enumClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<T> getEnumClass() {
/*  49 */     return this.enumClass;
/*     */   }
/*     */   
/*     */   private T findValue(String input) throws IllegalArgumentException {
/*  53 */     if (input != null) {
/*  54 */       input = input.toUpperCase();
/*     */     }
/*     */     
/*     */     try {
/*  58 */       return Enum.valueOf(this.enumClass, input);
/*  59 */     } catch (IllegalArgumentException e) {
/*  60 */       T val = detectValue(input);
/*     */       
/*  62 */       if (val != null) {
/*  63 */         return val;
/*     */       }
/*     */       
/*  66 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T detectValue(String input) {
/*  77 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public T parseInput(WorldGuardPlugin plugin, CommandSender sender, String input) throws InvalidFlagFormat {
/*     */     try {
/*  83 */       return findValue(input);
/*  84 */     } catch (IllegalArgumentException e) {
/*  85 */       throw new InvalidFlagFormat("Unknown value '" + input + "' in " + this.enumClass
/*  86 */           .getName());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public T unmarshal(Object o) {
/*     */     try {
/*  93 */       return Enum.valueOf(this.enumClass, String.valueOf(o));
/*  94 */     } catch (IllegalArgumentException e) {
/*  95 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object marshal(T o) {
/* 101 */     return o.name();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\flags\EnumFlag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */