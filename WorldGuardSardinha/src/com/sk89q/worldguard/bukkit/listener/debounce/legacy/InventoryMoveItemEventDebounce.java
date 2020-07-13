/*     */ package com.sk89q.worldguard.bukkit.listener.debounce.legacy;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.block.DoubleChest;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.inventory.InventoryMoveItemEvent;
/*     */ import org.bukkit.inventory.InventoryHolder;
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
/*     */ public class InventoryMoveItemEventDebounce
/*     */   extends AbstractEventDebounce<InventoryMoveItemEventDebounce.Key>
/*     */ {
/*     */   public InventoryMoveItemEventDebounce(int debounceTime) {
/*  33 */     super(debounceTime);
/*     */   }
/*     */   
/*     */   public AbstractEventDebounce.Entry tryDebounce(InventoryMoveItemEvent event) {
/*  37 */     return getEntry(new Key(event), (Cancellable)event);
/*     */   }
/*     */   
/*     */   protected static class Key {
/*     */     private final Object cause;
/*     */     private final Object source;
/*     */     private final Object target;
/*     */     
/*     */     public Key(InventoryMoveItemEvent event) {
/*  46 */       this.cause = transform(event.getInitiator().getHolder());
/*  47 */       this.source = transform(event.getSource().getHolder());
/*  48 */       this.target = transform(event.getDestination().getHolder());
/*     */     }
/*     */     
/*     */     private Object transform(InventoryHolder holder) {
/*  52 */       if (holder instanceof BlockState)
/*  53 */         return new InventoryMoveItemEventDebounce.BlockMaterialKey(((BlockState)holder).getBlock()); 
/*  54 */       if (holder instanceof DoubleChest) {
/*  55 */         return new InventoryMoveItemEventDebounce.BlockMaterialKey(((BlockState)((DoubleChest)holder).getLeftSide()).getBlock());
/*     */       }
/*  57 */       return holder;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  63 */       if (this == o) return true; 
/*  64 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/*  66 */       Key key = (Key)o;
/*     */       
/*  68 */       if ((this.cause != null) ? !this.cause.equals(key.cause) : (key.cause != null))
/*  69 */         return false; 
/*  70 */       if ((this.source != null) ? !this.source.equals(key.source) : (key.source != null))
/*  71 */         return false; 
/*  72 */       if ((this.target != null) ? !this.target.equals(key.target) : (key.target != null)) {
/*  73 */         return false;
/*     */       }
/*  75 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/*  80 */       int result = (this.cause != null) ? this.cause.hashCode() : 0;
/*  81 */       result = 31 * result + ((this.source != null) ? this.source.hashCode() : 0);
/*  82 */       result = 31 * result + ((this.target != null) ? this.target.hashCode() : 0);
/*  83 */       return result;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class BlockMaterialKey {
/*     */     private final Block block;
/*     */     private final Material material;
/*     */     
/*     */     private BlockMaterialKey(Block block) {
/*  92 */       this.block = block;
/*  93 */       this.material = block.getType();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  98 */       if (this == o) return true; 
/*  99 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/* 101 */       BlockMaterialKey that = (BlockMaterialKey)o;
/*     */       
/* 103 */       if (!this.block.equals(that.block)) return false; 
/* 104 */       if (this.material != that.material) return false;
/*     */       
/* 106 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 111 */       int result = this.block.hashCode();
/* 112 */       result = 31 * result + this.material.hashCode();
/* 113 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\debounce\legacy\InventoryMoveItemEventDebounce.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */