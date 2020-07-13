/*    */ package com.sk89q.worldguard.bukkit.listener.debounce.legacy;
/*    */ 
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
/*    */ public class EntityEntityEventDebounce
/*    */   extends AbstractEventDebounce<EntityEntityEventDebounce.Key>
/*    */ {
/*    */   public EntityEntityEventDebounce(int debounceTime) {
/* 30 */     super(debounceTime);
/*    */   }
/*    */   
/*    */   public <T extends org.bukkit.event.Event & Cancellable> void debounce(Entity source, Entity target, Cancellable originalEvent, T firedEvent) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: new com/sk89q/worldguard/bukkit/listener/debounce/legacy/EntityEntityEventDebounce$Key
/*    */     //   4: dup
/*    */     //   5: aload_1
/*    */     //   6: aload_2
/*    */     //   7: invokespecial <init> : (Lorg/bukkit/entity/Entity;Lorg/bukkit/entity/Entity;)V
/*    */     //   10: aload_3
/*    */     //   11: aload #4
/*    */     //   13: invokespecial debounce : (Ljava/lang/Object;Lorg/bukkit/event/Cancellable;Lorg/bukkit/event/Event;)V
/*    */     //   16: return
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #34	-> 0
/*    */     //   #35	-> 16
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	17	0	this	Lcom/sk89q/worldguard/bukkit/listener/debounce/legacy/EntityEntityEventDebounce;
/*    */     //   0	17	1	source	Lorg/bukkit/entity/Entity;
/*    */     //   0	17	2	target	Lorg/bukkit/entity/Entity;
/*    */     //   0	17	3	originalEvent	Lorg/bukkit/event/Cancellable;
/*    */     //   0	17	4	firedEvent	Lorg/bukkit/event/Event;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	17	4	firedEvent	TT;
/*    */   }
/*    */   
/*    */   protected static class Key {
/*    */     private final Entity source;
/*    */     private final Entity target;
/*    */     
/*    */     public Key(Entity source, Entity target) {
/* 42 */       this.source = source;
/* 43 */       this.target = target;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean equals(Object o) {
/* 48 */       if (this == o) return true; 
/* 49 */       if (o == null || getClass() != o.getClass()) return false;
/*    */       
/* 51 */       Key key = (Key)o;
/*    */       
/* 53 */       if (!this.source.equals(key.source)) return false; 
/* 54 */       if (!this.target.equals(key.target)) return false;
/*    */       
/* 56 */       return true;
/*    */     }
/*    */ 
/*    */     
/*    */     public int hashCode() {
/* 61 */       int result = this.source.hashCode();
/* 62 */       result = 31 * result + this.target.hashCode();
/* 63 */       return result;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\listener\debounce\legacy\EntityEntityEventDebounce.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */