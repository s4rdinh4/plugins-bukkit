/*    */ package com.sk89q.worldguard.bukkit.event.block;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import com.sk89q.worldguard.bukkit.cause.Cause;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
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
/*    */ 
/*    */ public class PlaceBlockEvent
/*    */   extends AbstractBlockEvent
/*    */ {
/* 41 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   public PlaceBlockEvent(@Nullable Event originalEvent, Cause cause, World world, List<Block> blocks, Material effectiveMaterial) {
/* 44 */     super(originalEvent, cause, world, blocks, effectiveMaterial);
/*    */   }
/*    */   
/*    */   public PlaceBlockEvent(@Nullable Event originalEvent, Cause cause, Block block) {
/* 48 */     super(originalEvent, cause, block);
/*    */   }
/*    */   
/*    */   public PlaceBlockEvent(@Nullable Event originalEvent, Cause cause, Location target, Material effectiveMaterial) {
/* 52 */     super(originalEvent, cause, target, effectiveMaterial);
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 57 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 61 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\event\block\PlaceBlockEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */