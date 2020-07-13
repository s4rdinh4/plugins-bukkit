/*     */ package com.sk89q.worldguard.bukkit.event.block;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.sk89q.worldguard.bukkit.cause.Cause;
/*     */ import com.sk89q.worldguard.bukkit.event.BulkEvent;
/*     */ import com.sk89q.worldguard.bukkit.event.DelegateEvent;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
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
/*     */ abstract class AbstractBlockEvent
/*     */   extends DelegateEvent
/*     */   implements BulkEvent
/*     */ {
/*     */   private final World world;
/*     */   private final List<Block> blocks;
/*     */   private final Material effectiveMaterial;
/*     */   
/*     */   protected AbstractBlockEvent(@Nullable Event originalEvent, Cause cause, World world, List<Block> blocks, Material effectiveMaterial) {
/*  50 */     super(originalEvent, cause);
/*  51 */     Preconditions.checkNotNull(world);
/*  52 */     Preconditions.checkNotNull(blocks);
/*  53 */     Preconditions.checkNotNull(effectiveMaterial);
/*  54 */     this.world = world;
/*  55 */     this.blocks = blocks;
/*  56 */     this.effectiveMaterial = effectiveMaterial;
/*     */   }
/*     */   
/*     */   protected AbstractBlockEvent(@Nullable Event originalEvent, Cause cause, Block block) {
/*  60 */     this(originalEvent, cause, block.getWorld(), createList((Block)Preconditions.checkNotNull(block)), block.getType());
/*     */   }
/*     */   
/*     */   protected AbstractBlockEvent(@Nullable Event originalEvent, Cause cause, Location target, Material effectiveMaterial) {
/*  64 */     this(originalEvent, cause, target.getWorld(), createList(target.getBlock()), effectiveMaterial);
/*     */   }
/*     */   
/*     */   private static List<Block> createList(Block block) {
/*  68 */     List<Block> blocks = new ArrayList<Block>();
/*  69 */     blocks.add(block);
/*  70 */     return blocks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld() {
/*  79 */     return this.world;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Block> getBlocks() {
/*  88 */     return this.blocks;
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
/*     */   public boolean filter(Predicate<Location> predicate, boolean cancelEventOnFalse) {
/* 101 */     boolean hasRemoval = false;
/*     */     
/* 103 */     Iterator<Block> it = this.blocks.iterator();
/* 104 */     while (it.hasNext()) {
/* 105 */       if (!predicate.apply(((Block)it.next()).getLocation())) {
/* 106 */         hasRemoval = true;
/*     */         
/* 108 */         if (cancelEventOnFalse) {
/* 109 */           getBlocks().clear();
/* 110 */           setCancelled(true);
/*     */           break;
/*     */         } 
/* 113 */         it.remove();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 118 */     return hasRemoval;
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
/*     */   public boolean filter(Predicate<Location> predicate) {
/* 134 */     return filter(predicate, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Material getEffectiveMaterial() {
/* 144 */     return this.effectiveMaterial;
/*     */   }
/*     */ 
/*     */   
/*     */   public Event.Result getResult() {
/* 149 */     if (this.blocks.isEmpty()) {
/* 150 */       return Event.Result.DENY;
/*     */     }
/* 152 */     return super.getResult();
/*     */   }
/*     */ 
/*     */   
/*     */   public Event.Result getExplicitResult() {
/* 157 */     return super.getResult();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\event\block\AbstractBlockEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */