/*    */ package com.sk89q.worldguard.protection.regions;
/*    */ 
/*    */ import com.sk89q.worldedit.BlockVector;
/*    */ import com.sk89q.worldedit.BlockVector2D;
/*    */ import com.sk89q.worldedit.Vector;
/*    */ import java.awt.geom.Area;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
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
/*    */ public class GlobalProtectedRegion
/*    */   extends ProtectedRegion
/*    */ {
/*    */   public GlobalProtectedRegion(String id) {
/* 47 */     super(id);
/* 48 */     this.min = new BlockVector(0, 0, 0);
/* 49 */     this.max = new BlockVector(0, 0, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPhysicalArea() {
/* 54 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<BlockVector2D> getPoints() {
/* 60 */     List<BlockVector2D> pts = new ArrayList<BlockVector2D>();
/* 61 */     pts.add(new BlockVector2D(this.min.getBlockX(), this.min.getBlockZ()));
/* 62 */     return pts;
/*    */   }
/*    */ 
/*    */   
/*    */   public int volume() {
/* 67 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean contains(Vector pt) {
/* 73 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public RegionType getType() {
/* 78 */     return RegionType.GLOBAL;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<ProtectedRegion> getIntersectingRegions(Collection<ProtectedRegion> regions) {
/* 84 */     return Collections.emptyList();
/*    */   }
/*    */ 
/*    */   
/*    */   Area toArea() {
/* 89 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\regions\GlobalProtectedRegion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */