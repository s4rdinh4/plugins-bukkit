/*    */ package com.sk89q.worldguard.util.task;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.google.common.util.concurrent.AbstractFuture;
/*    */ import java.util.Date;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nullable;
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
/*    */ public abstract class AbstractTask<V>
/*    */   extends AbstractFuture<V>
/*    */   implements Task<V>
/*    */ {
/* 37 */   private final UUID uniqueId = UUID.randomUUID();
/*    */   private final String name;
/*    */   private final Object owner;
/* 40 */   private final Date creationDate = new Date();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractTask(String name, @Nullable Object owner) {
/* 49 */     Preconditions.checkNotNull(name);
/* 50 */     this.name = name;
/* 51 */     this.owner = owner;
/*    */   }
/*    */ 
/*    */   
/*    */   public UUID getUniqueId() {
/* 56 */     return this.uniqueId;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 61 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Object getOwner() {
/* 67 */     return this.owner;
/*    */   }
/*    */ 
/*    */   
/*    */   public Date getCreationDate() {
/* 72 */     return this.creationDate;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\task\AbstractTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */