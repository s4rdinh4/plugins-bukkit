/*    */ package com.sk89q.worldguard.bukkit.internal;
/*    */ 
/*    */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.metadata.FixedMetadataValue;
/*    */ import org.bukkit.metadata.MetadataValue;
/*    */ import org.bukkit.metadata.Metadatable;
/*    */ import org.bukkit.plugin.Plugin;
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
/*    */ public final class WGMetadata
/*    */ {
/*    */   public static void put(Metadatable target, String key, Object value) {
/* 48 */     target.setMetadata(key, (MetadataValue)new FixedMetadataValue((Plugin)WorldGuardPlugin.inst(), value));
/*    */   }
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
/*    */   @Nullable
/*    */   public static <T> T getIfPresent(Metadatable target, String key, Class<T> expected) {
/* 64 */     List<MetadataValue> values = target.getMetadata(key);
/* 65 */     WorldGuardPlugin owner = WorldGuardPlugin.inst();
/* 66 */     for (MetadataValue value : values) {
/* 67 */       if (value.getOwningPlugin() == owner) {
/* 68 */         Object v = value.value();
/* 69 */         if (expected.isInstance(v)) {
/* 70 */           return (T)v;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 75 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\internal\WGMetadata.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */