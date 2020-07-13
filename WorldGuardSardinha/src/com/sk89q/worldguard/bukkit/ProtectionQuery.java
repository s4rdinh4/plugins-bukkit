/*     */ package com.sk89q.worldguard.bukkit;
/*     */ 
/*     */ import com.sk89q.worldguard.bukkit.cause.Cause;
/*     */ import com.sk89q.worldguard.bukkit.event.DelegateEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.DelegateEvents;
/*     */ import com.sk89q.worldguard.bukkit.event.block.BreakBlockEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.block.PlaceBlockEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.block.UseBlockEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.entity.DamageEntityEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.entity.SpawnEntityEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.entity.UseEntityEvent;
/*     */ import com.sk89q.worldguard.bukkit.util.Events;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
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
/*     */ public class ProtectionQuery
/*     */ {
/*     */   public boolean testBlockPlace(@Nullable Object cause, Location location, Material newMaterial) {
/*  59 */     return !Events.fireAndTestCancel((Event)DelegateEvents.setSilent((DelegateEvent)new PlaceBlockEvent(null, Cause.create(new Object[] { cause }, ), location, newMaterial)));
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
/*     */   public boolean testBlockBreak(@Nullable Object cause, Block block) {
/*  74 */     return !Events.fireAndTestCancel((Event)DelegateEvents.setSilent((DelegateEvent)new BreakBlockEvent(null, Cause.create(new Object[] { cause }, ), block)));
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
/*     */   public boolean testBlockInteract(@Nullable Object cause, Block block) {
/*  89 */     return !Events.fireAndTestCancel((Event)DelegateEvents.setSilent((DelegateEvent)new UseBlockEvent(null, Cause.create(new Object[] { cause }, ), block)));
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
/*     */   public boolean testEntityPlace(@Nullable Object cause, Location location, EntityType type) {
/* 105 */     return !Events.fireAndTestCancel((Event)DelegateEvents.setSilent((DelegateEvent)new SpawnEntityEvent(null, Cause.create(new Object[] { cause }, ), location, type)));
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
/*     */   public boolean testEntityDestroy(@Nullable Object cause, Entity entity) {
/* 120 */     return !Events.fireAndTestCancel((Event)DelegateEvents.setSilent((DelegateEvent)new SpawnEntityEvent(null, Cause.create(new Object[] { cause }, ), entity)));
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
/*     */   public boolean testEntityInteract(@Nullable Object cause, Entity entity) {
/* 135 */     return !Events.fireAndTestCancel((Event)DelegateEvents.setSilent((DelegateEvent)new UseEntityEvent(null, Cause.create(new Object[] { cause }, ), entity)));
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
/*     */   public boolean testEntityDamage(@Nullable Object cause, Entity entity) {
/* 150 */     return !Events.fireAndTestCancel((Event)DelegateEvents.setSilent((DelegateEvent)new DamageEntityEvent(null, Cause.create(new Object[] { cause }, ), entity)));
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\ProtectionQuery.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */