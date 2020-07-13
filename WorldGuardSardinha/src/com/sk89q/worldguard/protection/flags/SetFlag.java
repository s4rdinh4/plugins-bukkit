/*    */ package com.sk89q.worldguard.protection.flags;
/*    */ 
/*    */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import org.bukkit.command.CommandSender;
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
/*    */ public class SetFlag<T>
/*    */   extends Flag<Set<T>>
/*    */ {
/*    */   private Flag<T> subFlag;
/*    */   
/*    */   public SetFlag(String name, RegionGroup defaultGroup, Flag<T> subFlag) {
/* 39 */     super(name, defaultGroup);
/* 40 */     this.subFlag = subFlag;
/*    */   }
/*    */   
/*    */   public SetFlag(String name, Flag<T> subFlag) {
/* 44 */     super(name);
/* 45 */     this.subFlag = subFlag;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Flag<T> getType() {
/* 54 */     return this.subFlag;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<T> parseInput(WorldGuardPlugin plugin, CommandSender sender, String input) throws InvalidFlagFormat {
/* 59 */     Set<T> items = new HashSet<T>();
/*    */     
/* 61 */     for (String str : input.split(",")) {
/* 62 */       items.add(this.subFlag.parseInput(plugin, sender, str.trim()));
/*    */     }
/*    */     
/* 65 */     return new HashSet<T>(items);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<T> unmarshal(Object o) {
/* 70 */     if (o instanceof Collection) {
/* 71 */       Collection<?> collection = (Collection)o;
/* 72 */       Set<T> items = new HashSet<T>();
/*    */       
/* 74 */       for (Object sub : collection) {
/* 75 */         T item = this.subFlag.unmarshal(sub);
/* 76 */         if (item != null) {
/* 77 */           items.add(item);
/*    */         }
/*    */       } 
/*    */       
/* 81 */       return items;
/*    */     } 
/* 83 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object marshal(Set<T> o) {
/* 89 */     List<Object> list = new ArrayList();
/* 90 */     for (T item : o) {
/* 91 */       list.add(this.subFlag.marshal(item));
/*    */     }
/*    */     
/* 94 */     return list;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\flags\SetFlag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */