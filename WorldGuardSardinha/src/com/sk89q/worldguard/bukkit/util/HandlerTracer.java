/*    */ package com.sk89q.worldguard.bukkit.util;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.RegisteredListener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HandlerTracer
/*    */ {
/*    */   private final List<Handler> handlers;
/*    */   
/*    */   public HandlerTracer(Event event) {
/* 38 */     this.handlers = getHandlers(event);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Plugin detectPlugin(StackTraceElement[] elements) {
/* 49 */     for (int i = elements.length - 1; i >= 0; i--) {
/* 50 */       StackTraceElement element = elements[i];
/*    */       
/* 52 */       for (Handler handler : this.handlers) {
/* 53 */         if (element.getClassName().equals(handler.className)) {
/* 54 */           return handler.plugin;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 59 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static List<Handler> getHandlers(Event event) {
/* 69 */     List<Handler> handlers = Lists.newArrayList();
/*    */     
/* 71 */     for (RegisteredListener listener : event.getHandlers().getRegisteredListeners()) {
/* 72 */       handlers.add(new Handler(listener.getListener().getClass().getName(), listener.getPlugin()));
/*    */     }
/*    */     
/* 75 */     return handlers;
/*    */   }
/*    */   
/*    */   private static class Handler {
/*    */     private final String className;
/*    */     private final Plugin plugin;
/*    */     
/*    */     private Handler(String className, Plugin plugin) {
/* 83 */       this.className = className;
/* 84 */       this.plugin = plugin;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukki\\util\HandlerTracer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */