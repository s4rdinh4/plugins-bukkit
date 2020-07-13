/*    */ package com.sk89q.worldguard.bukkit.event.inventory;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.sk89q.worldguard.bukkit.cause.Cause;
/*    */ import com.sk89q.worldguard.bukkit.event.DelegateEvent;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.inventory.ItemStack;
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
/*    */ 
/*    */ 
/*    */ public class UseItemEvent
/*    */   extends DelegateEvent
/*    */ {
/* 41 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final World world;
/*    */   private final ItemStack itemStack;
/*    */   
/*    */   public UseItemEvent(@Nullable Event originalEvent, Cause cause, World world, ItemStack itemStack) {
/* 46 */     super(originalEvent, cause);
/* 47 */     Preconditions.checkNotNull(world);
/* 48 */     Preconditions.checkNotNull(itemStack);
/* 49 */     this.world = world;
/* 50 */     this.itemStack = itemStack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public World getWorld() {
/* 59 */     return this.world;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getItemStack() {
/* 68 */     return this.itemStack;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 73 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 77 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\event\inventory\UseItemEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */