/*    */ package com.sk89q.worldguard.blacklist.target;
/*    */ 
/*    */ import com.sk89q.worldedit.blocks.ItemType;
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
/*    */ public class MaterialTarget
/*    */   implements Target
/*    */ {
/*    */   private int id;
/*    */   private short data;
/*    */   
/*    */   public MaterialTarget(int id, short data) {
/* 30 */     this.id = id;
/* 31 */     this.data = data;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTypeId() {
/* 36 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getData() {
/* 41 */     return this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getFriendlyName() {
/* 46 */     ItemType type = ItemType.fromID(this.id);
/* 47 */     if (type != null) {
/* 48 */       return type.getName() + " (#" + this.id + ":" + this.data + ")";
/*    */     }
/* 50 */     return "#" + this.id + ":" + this.data;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\target\MaterialTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */