/*     */ package com.sk89q.worldguard.bukkit.util;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class InteropUtils
/*     */ {
/*     */   @Nullable
/*     */   private static final Class<?> forgeFakePlayerClass;
/*  37 */   private static final UUID forgeFakePlayerUuid = UUID.fromString("41c82c87-7afb-4024-ba57-13d2c99cae77");
/*     */   private static final PlayerHandleFunction playerHandleFunction;
/*     */   
/*     */   static {
/*  41 */     forgeFakePlayerClass = findClass("net.minecraftforge.common.util.FakePlayer");
/*     */ 
/*     */     
/*     */     try {
/*  45 */       playerHandleFunction = new PlayerHandleFunction();
/*  46 */     } catch (Exception e) {
/*  47 */       playerHandleFunction = null;
/*     */     } 
/*  49 */     playerHandleFunction = playerHandleFunction;
/*     */   } static {
/*     */     PlayerHandleFunction playerHandleFunction;
/*     */   } @Nullable
/*     */   private static Class<?> findClass(String name) {
/*     */     try {
/*  55 */       return Class.forName(name);
/*  56 */     } catch (Throwable ignored) {
/*  57 */       return null;
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
/*     */   public static boolean isFakePlayer(Player player) {
/*  71 */     UUID uuid = player.getUniqueId();
/*  72 */     String name = player.getName();
/*     */     
/*  74 */     if (uuid.equals(forgeFakePlayerUuid)) {
/*  75 */       return true;
/*     */     }
/*     */     
/*  78 */     if (forgeFakePlayerClass != null && playerHandleFunction != null) {
/*  79 */       Object handle = playerHandleFunction.apply(player);
/*  80 */       if (handle != null && 
/*  81 */         forgeFakePlayerClass.isAssignableFrom(handle.getClass())) {
/*  82 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  87 */     if (name.length() >= 3 && name.charAt(0) == '[' && name.charAt(name.length() - 1) == ']') {
/*  88 */       return true;
/*     */     }
/*     */     
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class PlayerHandleFunction
/*     */     implements Function<Object, Object>
/*     */   {
/*  99 */     private final Class<?> craftPlayerClass = Class.forName(Bukkit.getServer().getClass().getCanonicalName().replaceAll("CraftServer$", "entity.CraftPlayer"));
/* 100 */     private final Method getHandleMethod = this.craftPlayerClass.getMethod("getHandle", new Class[0]);
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public Object apply(Object o) {
/* 106 */       if (this.craftPlayerClass.isAssignableFrom(o.getClass())) {
/*     */         try {
/* 108 */           return this.getHandleMethod.invoke(o, new Object[0]);
/* 109 */         } catch (Throwable ignored) {}
/*     */       }
/*     */ 
/*     */       
/* 113 */       return null;
/*     */     }
/*     */     
/*     */     private PlayerHandleFunction() throws NoSuchMethodException, ClassNotFoundException {}
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukki\\util\InteropUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */