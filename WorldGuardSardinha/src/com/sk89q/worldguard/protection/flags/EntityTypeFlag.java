/*    */ package com.sk89q.worldguard.protection.flags;
/*    */ 
/*    */ import org.bukkit.entity.EntityType;
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
/*    */ public class EntityTypeFlag
/*    */   extends EnumFlag<EntityType>
/*    */ {
/*    */   public EntityTypeFlag(String name, RegionGroup defaultGroup) {
/* 30 */     super(name, EntityType.class, defaultGroup);
/*    */   }
/*    */   
/*    */   public EntityTypeFlag(String name) {
/* 34 */     super(name, EntityType.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityType detectValue(String input) {
/* 39 */     EntityType lowMatch = null;
/*    */     
/* 41 */     for (EntityType type : EntityType.values()) {
/* 42 */       if (type.name().equalsIgnoreCase(input.trim())) {
/* 43 */         return type;
/*    */       }
/*    */       
/* 46 */       if (type.name().toLowerCase().startsWith(input.toLowerCase().trim())) {
/* 47 */         lowMatch = type;
/*    */       }
/*    */     } 
/*    */     
/* 51 */     return lowMatch;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\flags\EntityTypeFlag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */