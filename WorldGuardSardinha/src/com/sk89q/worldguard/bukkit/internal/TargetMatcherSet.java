/*    */ package com.sk89q.worldguard.bukkit.internal;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.google.common.collect.HashMultimap;
/*    */ import com.google.common.collect.Multimap;
/*    */ import com.sk89q.worldguard.blacklist.target.MaterialTarget;
/*    */ import com.sk89q.worldguard.blacklist.target.Target;
/*    */ import com.sk89q.worldguard.blacklist.target.TargetMatcher;
/*    */ import java.util.Collection;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockState;
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
/*    */ public class TargetMatcherSet
/*    */ {
/* 38 */   private final Multimap<Integer, TargetMatcher> entries = (Multimap<Integer, TargetMatcher>)HashMultimap.create();
/*    */   
/*    */   public boolean add(TargetMatcher matcher) {
/* 41 */     Preconditions.checkNotNull(matcher);
/* 42 */     return this.entries.put(Integer.valueOf(matcher.getMatchedTypeId()), matcher);
/*    */   }
/*    */   
/*    */   public boolean test(Target target) {
/* 46 */     Collection<TargetMatcher> matchers = this.entries.get(Integer.valueOf(target.getTypeId()));
/*    */     
/* 48 */     for (TargetMatcher matcher : matchers) {
/* 49 */       if (matcher.test(target)) {
/* 50 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 54 */     return false;
/*    */   }
/*    */   
/*    */   public boolean test(Material material) {
/* 58 */     return test((Target)new MaterialTarget(material.getId(), (short)0));
/*    */   }
/*    */   
/*    */   public boolean test(Block block) {
/* 62 */     return test((Target)new MaterialTarget(block.getTypeId(), (short)block.getData()));
/*    */   }
/*    */   
/*    */   public boolean test(BlockState state) {
/* 66 */     return test((Target)new MaterialTarget(state.getTypeId(), (short)state.getRawData()));
/*    */   }
/*    */   
/*    */   public boolean test(ItemStack itemStack) {
/* 70 */     return test((Target)new MaterialTarget(itemStack.getTypeId(), itemStack.getDurability()));
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 75 */     return this.entries.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\internal\TargetMatcherSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */