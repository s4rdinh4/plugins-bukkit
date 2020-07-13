/*     */ package com.sk89q.worldguard.bukkit.event;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.sk89q.worldguard.bukkit.cause.Cause;
/*     */ import com.sk89q.worldguard.protection.flags.StateFlag;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.Event;
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
/*     */ public abstract class DelegateEvent
/*     */   extends Event
/*     */   implements Cancellable, Handleable
/*     */ {
/*     */   @Nullable
/*     */   private final Event originalEvent;
/*     */   private final Cause cause;
/*  42 */   private final List<StateFlag> relevantFlags = Lists.newArrayList();
/*  43 */   private Event.Result result = Event.Result.DEFAULT;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean silent;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DelegateEvent(@Nullable Event originalEvent, Cause cause) {
/*  53 */     Preconditions.checkNotNull(cause);
/*  54 */     this.originalEvent = originalEvent;
/*  55 */     this.cause = cause;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Event getOriginalEvent() {
/*  65 */     return this.originalEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cause getCause() {
/*  74 */     return this.cause;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<StateFlag> getRelevantFlags() {
/*  83 */     return this.relevantFlags;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCancelled() {
/*  88 */     return (getResult() == Event.Result.DENY);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCancelled(boolean cancel) {
/*  93 */     if (cancel) {
/*  94 */       setResult(Event.Result.DENY);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Event.Result getResult() {
/* 100 */     return this.result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setResult(Event.Result result) {
/* 105 */     this.result = result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSilent() {
/* 114 */     return this.silent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DelegateEvent setSilent(boolean silent) {
/* 124 */     this.silent = silent;
/* 125 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DelegateEvent setAllowed(boolean allowed) {
/* 135 */     if (allowed) {
/* 136 */       setResult(Event.Result.ALLOW);
/*     */     }
/*     */     
/* 139 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\event\DelegateEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */