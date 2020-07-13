/*     */ package com.sk89q.worldguard.protection.regions;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.sk89q.worldedit.BlockVector;
/*     */ import com.sk89q.worldedit.BlockVector2D;
/*     */ import com.sk89q.worldedit.Vector;
/*     */ import com.sk89q.worldguard.util.MathUtils;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.geom.Area;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProtectedCuboidRegion
/*     */   extends ProtectedRegion
/*     */ {
/*     */   public ProtectedCuboidRegion(String id, BlockVector pt1, BlockVector pt2) {
/*  49 */     super(id);
/*  50 */     setMinMaxPoints(pt1, pt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setMinMaxPoints(BlockVector position1, BlockVector position2) {
/*  60 */     Preconditions.checkNotNull(position1);
/*  61 */     Preconditions.checkNotNull(position2);
/*     */     
/*  63 */     List<Vector> points = new ArrayList<Vector>();
/*  64 */     points.add(position1);
/*  65 */     points.add(position2);
/*  66 */     setMinMaxPoints(points);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinimumPoint(BlockVector position) {
/*  75 */     setMinMaxPoints(position, this.max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaximumPoint(BlockVector position) {
/*  84 */     setMinMaxPoints(this.min, position);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPhysicalArea() {
/*  89 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BlockVector2D> getPoints() {
/*  94 */     List<BlockVector2D> pts = new ArrayList<BlockVector2D>();
/*  95 */     int x1 = this.min.getBlockX();
/*  96 */     int x2 = this.max.getBlockX();
/*  97 */     int z1 = this.min.getBlockZ();
/*  98 */     int z2 = this.max.getBlockZ();
/*     */     
/* 100 */     pts.add(new BlockVector2D(x1, z1));
/* 101 */     pts.add(new BlockVector2D(x2, z1));
/* 102 */     pts.add(new BlockVector2D(x2, z2));
/* 103 */     pts.add(new BlockVector2D(x1, z2));
/*     */     
/* 105 */     return pts;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Vector pt) {
/* 110 */     double x = pt.getX();
/* 111 */     double y = pt.getY();
/* 112 */     double z = pt.getZ();
/* 113 */     return (x >= this.min.getBlockX() && x < (this.max.getBlockX() + 1) && y >= this.min
/* 114 */       .getBlockY() && y < (this.max.getBlockY() + 1) && z >= this.min
/* 115 */       .getBlockZ() && z < (this.max.getBlockZ() + 1));
/*     */   }
/*     */ 
/*     */   
/*     */   public RegionType getType() {
/* 120 */     return RegionType.CUBOID;
/*     */   }
/*     */ 
/*     */   
/*     */   Area toArea() {
/* 125 */     int x = getMinimumPoint().getBlockX();
/* 126 */     int z = getMinimumPoint().getBlockZ();
/* 127 */     int width = getMaximumPoint().getBlockX() - x;
/* 128 */     int height = getMaximumPoint().getBlockZ() - z;
/* 129 */     return new Area(new Rectangle(x, z, width, height));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean intersects(ProtectedRegion region, Area thisArea) {
/* 134 */     if (region instanceof ProtectedCuboidRegion) {
/* 135 */       return intersectsBoundingBox(region);
/*     */     }
/* 137 */     return super.intersects(region, thisArea);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int volume() {
/* 143 */     int xLength = this.max.getBlockX() - this.min.getBlockX() + 1;
/* 144 */     int yLength = this.max.getBlockY() - this.min.getBlockY() + 1;
/* 145 */     int zLength = this.max.getBlockZ() - this.min.getBlockZ() + 1;
/*     */     
/*     */     try {
/* 148 */       long v = MathUtils.checkedMultiply(xLength, yLength);
/* 149 */       v = MathUtils.checkedMultiply(v, zLength);
/* 150 */       if (v > 2147483647L) {
/* 151 */         return Integer.MAX_VALUE;
/*     */       }
/* 153 */       return (int)v;
/*     */     }
/* 155 */     catch (ArithmeticException e) {
/* 156 */       return Integer.MAX_VALUE;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\regions\ProtectedCuboidRegion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */