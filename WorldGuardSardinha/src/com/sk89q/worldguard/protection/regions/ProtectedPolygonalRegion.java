/*     */ package com.sk89q.worldguard.protection.regions;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.sk89q.worldedit.BlockVector2D;
/*     */ import com.sk89q.worldedit.Vector;
/*     */ import java.awt.Polygon;
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
/*     */ public class ProtectedPolygonalRegion
/*     */   extends ProtectedRegion
/*     */ {
/*     */   private final ImmutableList<BlockVector2D> points;
/*     */   private final int minY;
/*     */   private final int maxY;
/*     */   
/*     */   public ProtectedPolygonalRegion(String id, List<BlockVector2D> points, int minY, int maxY) {
/*  40 */     super(id);
/*  41 */     ImmutableList<BlockVector2D> immutablePoints = ImmutableList.copyOf(points);
/*  42 */     setMinMaxPoints((List<BlockVector2D>)immutablePoints, minY, maxY);
/*  43 */     this.points = immutablePoints;
/*  44 */     this.minY = this.min.getBlockY();
/*  45 */     this.maxY = this.max.getBlockY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setMinMaxPoints(List<BlockVector2D> points2D, int minY, int maxY) {
/*  56 */     Preconditions.checkNotNull(points2D);
/*     */     
/*  58 */     List<Vector> points = new ArrayList<Vector>();
/*  59 */     int y = minY;
/*  60 */     for (BlockVector2D point2D : points2D) {
/*  61 */       points.add(new Vector(point2D.getBlockX(), y, point2D.getBlockZ()));
/*  62 */       y = maxY;
/*     */     } 
/*  64 */     setMinMaxPoints(points);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPhysicalArea() {
/*  69 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BlockVector2D> getPoints() {
/*  74 */     return (List<BlockVector2D>)this.points;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Vector position) {
/*  79 */     Preconditions.checkNotNull(position);
/*     */     
/*  81 */     int targetX = position.getBlockX();
/*  82 */     int targetY = position.getBlockY();
/*  83 */     int targetZ = position.getBlockZ();
/*     */     
/*  85 */     if (targetY < this.minY || targetY > this.maxY) {
/*  86 */       return false;
/*     */     }
/*     */     
/*  89 */     if (targetX < this.min.getBlockX() || targetX > this.max.getBlockX() || targetZ < this.min.getBlockZ() || targetZ > this.max.getBlockZ()) {
/*  90 */       return false;
/*     */     }
/*  92 */     boolean inside = false;
/*  93 */     int npoints = this.points.size();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     int xOld = ((BlockVector2D)this.points.get(npoints - 1)).getBlockX();
/* 102 */     int zOld = ((BlockVector2D)this.points.get(npoints - 1)).getBlockZ();
/*     */     
/* 104 */     for (int i = 0; i < npoints; i++) {
/* 105 */       int x1, x2, z1, z2, xNew = ((BlockVector2D)this.points.get(i)).getBlockX();
/* 106 */       int zNew = ((BlockVector2D)this.points.get(i)).getBlockZ();
/*     */       
/* 108 */       if (xNew == targetX && zNew == targetZ) {
/* 109 */         return true;
/*     */       }
/* 111 */       if (xNew > xOld) {
/* 112 */         x1 = xOld;
/* 113 */         x2 = xNew;
/* 114 */         z1 = zOld;
/* 115 */         z2 = zNew;
/*     */       } else {
/* 117 */         x1 = xNew;
/* 118 */         x2 = xOld;
/* 119 */         z1 = zNew;
/* 120 */         z2 = zOld;
/*     */       } 
/* 122 */       if (x1 <= targetX && targetX <= x2) {
/* 123 */         long crossproduct = (targetZ - z1) * (x2 - x1) - (z2 - z1) * (targetX - x1);
/*     */         
/* 125 */         if (crossproduct == 0L) {
/* 126 */           if (((z1 <= targetZ) ? true : false) == ((targetZ <= z2) ? true : false)) return true; 
/* 127 */         } else if (crossproduct < 0L && x1 != targetX) {
/* 128 */           inside = !inside;
/*     */         } 
/*     */       } 
/* 131 */       xOld = xNew;
/* 132 */       zOld = zNew;
/*     */     } 
/*     */     
/* 135 */     return inside;
/*     */   }
/*     */ 
/*     */   
/*     */   public RegionType getType() {
/* 140 */     return RegionType.POLYGON;
/*     */   }
/*     */ 
/*     */   
/*     */   Area toArea() {
/* 145 */     List<BlockVector2D> points = getPoints();
/* 146 */     int numPoints = points.size();
/* 147 */     int[] xCoords = new int[numPoints];
/* 148 */     int[] yCoords = new int[numPoints];
/*     */     
/* 150 */     int i = 0;
/* 151 */     for (BlockVector2D point : points) {
/* 152 */       xCoords[i] = point.getBlockX();
/* 153 */       yCoords[i] = point.getBlockZ();
/* 154 */       i++;
/*     */     } 
/*     */     
/* 157 */     Polygon polygon = new Polygon(xCoords, yCoords, numPoints);
/* 158 */     return new Area(polygon);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int volume() {
/* 164 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\regions\ProtectedPolygonalRegion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */