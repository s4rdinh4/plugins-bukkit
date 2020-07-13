/*    */ package com.sk89q.worldguard.bukkit.listener.debounce.legacy;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Cancellable;
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
/*    */ public class BlockEntityEventDebounce
/*    */   extends AbstractEventDebounce<BlockEntityEventDebounce.Key>
/*    */ {
/*    */   public BlockEntityEventDebounce(int debounceTime) {
/* 32 */     super(debounceTime);
/*    */   }
/*    */   
/*    */   public <T extends org.bukkit.event.Event & Cancellable> void debounce(Block block, Entity entity, Cancellable originalEvent, T firedEvent) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: new com/sk89q/worldguard/bukkit/listener/debounce/legacy/BlockEntityEventDebounce$Key
/*    */     //   4: dup
/*    */     //   5: aload_1
/*    */     //   6: aload_2
/*    */     //   7: aconst_null
/*    */     //   8: invokespecial <init> : (Lorg/bukkit/block/Block;Lorg/bukkit/entity/Entity;Lcom/sk89q/worldguard/bukkit/listener/debounce/legacy/BlockEntityEventDebounce$1;)V
/*    */     //   11: aload_3
/*    */     //   12: aload #4
/*    */     //   14: invokespecial debounce : (Ljava/lang/Object;Lorg/bukkit/event/Cancellable;Lorg/bukkit/event/Event;)V
/*    */     //   17: return
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #36	-> 0
/*    */     //   #37	-> 17
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	18	0	this	Lcom/sk89q/worldguard/bukkit/listener/debounce/legacy/BlockEntityEventDebounce;
/*    */     //   0	18	1	block	Lorg/bukkit/block/Block;
/*    */     //   0	18	2	entity	Lorg/bukkit/entity/Entity;
/*    */     //   0	18	3	originalEvent	Lorg/bukkit/event/Cancellable;
/*    */     //   0	18	4	firedEvent	Lorg/bukkit/event/Event;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	18	4	firedEvent	TT;
/*    */   }
/*    */   
/*    */   protected static class Key {
/*    */     private final Block block;
/*    */     private final Material blockMaterial;
/*    */     private final Entity entity;
/*    */     
/*    */     private Key(Block block, Entity entity) {
/* 45 */       this.block = block;
/* 46 */       this.blockMaterial = block.getType();
/* 47 */       this.entity = entity;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean equals(Object o) {
/* 52 */       if (this == o) return true; 
/* 53 */       if (o == null || getClass() != o.getClass()) return false;
/*    */       
/* 55 */       Key key = (Key)o;
/*    */       
/* 57 */       if (!this.block.equals(key.block)) return false; 
/* 58 */       if (this.blockMaterial != key.blockMaterial) return false; 
/* 59 */       if (!this.entity.equals(key.entity)) return false;
/*    */       
/* 61 */       return true;
/*    */     }
/*    */ 
/*    */     
/*    */     public int hashCode() {
/* 66 */       int result = this.block.hashCode();
/* 67 */       result = 31 * result + this.blockMaterial.hashCode();
/* 68 */       result = 31 * result + this.entity.hashCode();
/* 69 */       return result;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\debounce\legacy\BlockEntityEventDebounce.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */