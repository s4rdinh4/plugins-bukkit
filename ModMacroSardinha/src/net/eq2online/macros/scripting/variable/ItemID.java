/*     */ package net.eq2online.macros.scripting.variable;
/*     */ 
/*     */ import alq;
/*     */ import amj;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ import oa;
/*     */ 
/*     */ public class ItemID
/*     */ {
/*  11 */   public static final alq ITEM_AIR_VIRTUAL = (new alq()
/*     */     {
/*     */       public alq init()
/*     */       {
/*  15 */         c("air");
/*  16 */         return this;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public String a() {
/*  22 */         return "Air";
/*     */       }
/*  24 */     }).init();
/*     */ 
/*     */   
/*     */   public final String identifier;
/*     */   
/*     */   public final int damage;
/*     */   
/*     */   public final boolean hasDamage;
/*     */   
/*     */   public final alq item;
/*     */   
/*     */   public final String name;
/*     */   
/*     */   public ItemID(String itemIdString) {
/*  38 */     if (itemIdString.matches("^[a-z0-9_]+:\\d{1,5}$")) {
/*     */       
/*  40 */       String[] idStringParts = itemIdString.split(":");
/*  41 */       this.identifier = idStringParts[0];
/*  42 */       this.damage = ScriptCore.tryParseInt(idStringParts[1], 0);
/*  43 */       this.hasDamage = true;
/*     */     }
/*     */     else {
/*     */       
/*  47 */       this.identifier = itemIdString;
/*  48 */       this.damage = -1;
/*  49 */       this.hasDamage = false;
/*     */     } 
/*     */     
/*  52 */     this.item = Macros.getItem(new oa(this.identifier));
/*  53 */     this.name = Macros.getItemName(this.item);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemID(String itemIdString, int damage) {
/*  58 */     if (itemIdString.contains(":")) throw new RuntimeException("Debug? Why are you here with [" + itemIdString + "] and [" + damage + "]?"); 
/*  59 */     this.identifier = itemIdString;
/*  60 */     this.damage = damage;
/*  61 */     this.hasDamage = (damage > 0);
/*  62 */     this.item = Macros.getItem(new oa(this.identifier));
/*  63 */     this.name = Macros.getItemName(this.item);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/*  68 */     return (this.item != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasValidDamage() {
/*  73 */     return (this.damage >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public amj toItemStack(int stackSize) {
/*  78 */     return new amj(this.item, stackSize, (this.damage > -1) ? this.damage : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDamage() {
/*  83 */     return String.valueOf((this.damage >= 0) ? this.damage : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  89 */     return String.format("%s:%s", new Object[] { (this.identifier == null) ? Macros.getItemName(null) : this.identifier, Integer.valueOf(Math.max(0, this.damage)) });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/*  95 */     if (other == null) return false; 
/*  96 */     if (other instanceof ItemID) {
/*     */       
/*  98 */       ItemID otherItem = (ItemID)other;
/*  99 */       return (otherItem.item == this.item);
/*     */     } 
/* 101 */     if (other instanceof alq)
/*     */     {
/* 103 */       return ((alq)other == this.item);
/*     */     }
/* 105 */     if (other instanceof amj) {
/*     */       
/* 107 */       amj itemStack = (amj)other;
/* 108 */       return amj.b(itemStack, toItemStack(itemStack.b));
/*     */     } 
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 116 */     return super.hashCode();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\variable\ItemID.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */