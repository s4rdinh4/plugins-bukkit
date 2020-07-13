/*     */ package com.sk89q.worldguard.protection.regions;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.sk89q.worldedit.BlockVector;
/*     */ import com.sk89q.worldedit.BlockVector2D;
/*     */ import com.sk89q.worldedit.Vector;
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import com.sk89q.worldguard.domains.DefaultDomain;
/*     */ import com.sk89q.worldguard.protection.flags.Flag;
/*     */ import com.sk89q.worldguard.util.ChangeTracked;
/*     */ import com.sk89q.worldguard.util.Normal;
/*     */ import java.awt.geom.Area;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nullable;
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
/*     */ public abstract class ProtectedRegion
/*     */   implements ChangeTracked, Comparable<ProtectedRegion>
/*     */ {
/*     */   public static final String GLOBAL_REGION = "__global__";
/*  53 */   private static final Pattern VALID_ID_PATTERN = Pattern.compile("^[A-Za-z0-9_,'\\-\\+/]{1,}$");
/*     */   
/*     */   protected BlockVector min;
/*     */   
/*     */   protected BlockVector max;
/*     */   private final String id;
/*  59 */   private int priority = 0;
/*     */   private ProtectedRegion parent;
/*  61 */   private DefaultDomain owners = new DefaultDomain();
/*  62 */   private DefaultDomain members = new DefaultDomain();
/*  63 */   private ConcurrentMap<Flag<?>, Object> flags = new ConcurrentHashMap<Flag<?>, Object>();
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean dirty = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ProtectedRegion(String id) {
/*  73 */     Preconditions.checkNotNull(id);
/*     */     
/*  75 */     if (!isValidId(id)) {
/*  76 */       throw new IllegalArgumentException("Invalid region ID: " + id);
/*     */     }
/*     */     
/*  79 */     this.id = Normal.normalize(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setMinMaxPoints(List<Vector> points) {
/*  88 */     int minX = ((Vector)points.get(0)).getBlockX();
/*  89 */     int minY = ((Vector)points.get(0)).getBlockY();
/*  90 */     int minZ = ((Vector)points.get(0)).getBlockZ();
/*  91 */     int maxX = minX;
/*  92 */     int maxY = minY;
/*  93 */     int maxZ = minZ;
/*     */     
/*  95 */     for (Vector v : points) {
/*  96 */       int x = v.getBlockX();
/*  97 */       int y = v.getBlockY();
/*  98 */       int z = v.getBlockZ();
/*     */       
/* 100 */       if (x < minX) minX = x; 
/* 101 */       if (y < minY) minY = y; 
/* 102 */       if (z < minZ) minZ = z;
/*     */       
/* 104 */       if (x > maxX) maxX = x; 
/* 105 */       if (y > maxY) maxY = y; 
/* 106 */       if (z > maxZ) maxZ = z;
/*     */     
/*     */     } 
/* 109 */     setDirty(true);
/* 110 */     this.min = new BlockVector(minX, minY, minZ);
/* 111 */     this.max = new BlockVector(maxX, maxY, maxZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 120 */     return this.id;
/*     */   }
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
/*     */   public BlockVector getMinimumPoint() {
/* 137 */     return this.min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockVector getMaximumPoint() {
/* 147 */     return this.max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPriority() {
/* 157 */     return this.priority;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPriority(int priority) {
/* 167 */     setDirty(true);
/* 168 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ProtectedRegion getParent() {
/* 178 */     return this.parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParent(@Nullable ProtectedRegion parent) throws CircularInheritanceException {
/* 189 */     setDirty(true);
/*     */     
/* 191 */     if (parent == null) {
/* 192 */       this.parent = null;
/*     */       
/*     */       return;
/*     */     } 
/* 196 */     if (parent == this) {
/* 197 */       throw new CircularInheritanceException();
/*     */     }
/*     */     
/* 200 */     ProtectedRegion p = parent.getParent();
/* 201 */     while (p != null) {
/* 202 */       if (p == this) {
/* 203 */         throw new CircularInheritanceException();
/*     */       }
/* 205 */       p = p.getParent();
/*     */     } 
/*     */     
/* 208 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearParent() {
/* 215 */     setDirty(true);
/* 216 */     this.parent = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultDomain getOwners() {
/* 225 */     return this.owners;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOwners(DefaultDomain owners) {
/* 234 */     Preconditions.checkNotNull(owners);
/* 235 */     setDirty(true);
/* 236 */     this.owners = new DefaultDomain(owners);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultDomain getMembers() {
/* 246 */     return this.members;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMembers(DefaultDomain members) {
/* 255 */     Preconditions.checkNotNull(members);
/* 256 */     setDirty(true);
/* 257 */     this.members = new DefaultDomain(members);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasMembersOrOwners() {
/* 266 */     return (this.owners.size() > 0 || this.members.size() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOwner(LocalPlayer player) {
/* 276 */     Preconditions.checkNotNull(player);
/*     */     
/* 278 */     if (this.owners.contains(player)) {
/* 279 */       return true;
/*     */     }
/*     */     
/* 282 */     ProtectedRegion curParent = getParent();
/* 283 */     while (curParent != null) {
/* 284 */       if (curParent.getOwners().contains(player)) {
/* 285 */         return true;
/*     */       }
/*     */       
/* 288 */       curParent = curParent.getParent();
/*     */     } 
/*     */     
/* 291 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean isOwner(String playerName) {
/* 303 */     Preconditions.checkNotNull(playerName);
/*     */     
/* 305 */     if (this.owners.contains(playerName)) {
/* 306 */       return true;
/*     */     }
/*     */     
/* 309 */     ProtectedRegion curParent = getParent();
/* 310 */     while (curParent != null) {
/* 311 */       if (curParent.getOwners().contains(playerName)) {
/* 312 */         return true;
/*     */       }
/*     */       
/* 315 */       curParent = curParent.getParent();
/*     */     } 
/*     */     
/* 318 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMember(LocalPlayer player) {
/* 329 */     Preconditions.checkNotNull(player);
/*     */     
/* 331 */     if (isOwner(player)) {
/* 332 */       return true;
/*     */     }
/*     */     
/* 335 */     if (this.members.contains(player)) {
/* 336 */       return true;
/*     */     }
/*     */     
/* 339 */     ProtectedRegion curParent = getParent();
/* 340 */     while (curParent != null) {
/* 341 */       if (curParent.getMembers().contains(player)) {
/* 342 */         return true;
/*     */       }
/*     */       
/* 345 */       curParent = curParent.getParent();
/*     */     } 
/*     */     
/* 348 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean isMember(String playerName) {
/* 361 */     Preconditions.checkNotNull(playerName);
/*     */     
/* 363 */     if (isOwner(playerName)) {
/* 364 */       return true;
/*     */     }
/*     */     
/* 367 */     if (this.members.contains(playerName)) {
/* 368 */       return true;
/*     */     }
/*     */     
/* 371 */     ProtectedRegion curParent = getParent();
/* 372 */     while (curParent != null) {
/* 373 */       if (curParent.getMembers().contains(playerName)) {
/* 374 */         return true;
/*     */       }
/*     */       
/* 377 */       curParent = curParent.getParent();
/*     */     } 
/*     */     
/* 380 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMemberOnly(LocalPlayer player) {
/* 390 */     Preconditions.checkNotNull(player);
/*     */     
/* 392 */     if (this.members.contains(player)) {
/* 393 */       return true;
/*     */     }
/*     */     
/* 396 */     ProtectedRegion curParent = getParent();
/* 397 */     while (curParent != null) {
/* 398 */       if (curParent.getMembers().contains(player)) {
/* 399 */         return true;
/*     */       }
/*     */       
/* 402 */       curParent = curParent.getParent();
/*     */     } 
/*     */     
/* 405 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends Flag<V>, V> V getFlag(T flag) {
/*     */     V val;
/* 419 */     Preconditions.checkNotNull(flag);
/*     */     
/* 421 */     Object obj = this.flags.get(flag);
/*     */ 
/*     */     
/* 424 */     if (obj != null) {
/* 425 */       val = (V)obj;
/*     */     } else {
/* 427 */       return null;
/*     */     } 
/*     */     
/* 430 */     return val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Flag<V>, V> void setFlag(T flag, @Nullable V val) {
/* 442 */     Preconditions.checkNotNull(flag);
/* 443 */     setDirty(true);
/*     */     
/* 445 */     if (val == null) {
/* 446 */       this.flags.remove(flag);
/*     */     } else {
/* 448 */       this.flags.put((Flag<?>)flag, val);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<Flag<?>, Object> getFlags() {
/* 458 */     return this.flags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlags(Map<Flag<?>, Object> flags) {
/* 469 */     Preconditions.checkNotNull(flags);
/*     */     
/* 471 */     setDirty(true);
/* 472 */     this.flags = new ConcurrentHashMap<Flag<?>, Object>(flags);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void copyFrom(ProtectedRegion other) {
/* 481 */     Preconditions.checkNotNull(other);
/* 482 */     setMembers(other.getMembers());
/* 483 */     setOwners(other.getOwners());
/* 484 */     setFlags(other.getFlags());
/* 485 */     setPriority(other.getPriority());
/*     */     try {
/* 487 */       setParent(other.getParent());
/* 488 */     } catch (CircularInheritanceException ignore) {}
/*     */   }
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
/*     */   public boolean contains(BlockVector2D position) {
/* 522 */     Preconditions.checkNotNull(position);
/* 523 */     return contains(new Vector(position.getBlockX(), this.min.getBlockY(), position.getBlockZ()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(int x, int y, int z) {
/* 535 */     return contains(new Vector(x, y, z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsAny(List<BlockVector2D> positions) {
/* 546 */     Preconditions.checkNotNull(positions);
/*     */     
/* 548 */     for (BlockVector2D pt : positions) {
/* 549 */       if (contains(pt)) {
/* 550 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 554 */     return false;
/*     */   }
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
/*     */   @Deprecated
/*     */   public final String getTypeName() {
/* 572 */     return getType().getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ProtectedRegion> getIntersectingRegions(Collection<ProtectedRegion> regions) {
/* 583 */     Preconditions.checkNotNull(regions, "regions");
/*     */     
/* 585 */     List<ProtectedRegion> intersecting = Lists.newArrayList();
/* 586 */     Area thisArea = toArea();
/*     */     
/* 588 */     for (ProtectedRegion region : regions) {
/* 589 */       if (!region.isPhysicalArea())
/*     */         continue; 
/* 591 */       if (intersects(region, thisArea)) {
/* 592 */         intersecting.add(region);
/*     */       }
/*     */     } 
/*     */     
/* 596 */     return intersecting;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean intersects(ProtectedRegion region, Area thisArea) {
/* 607 */     if (intersectsBoundingBox(region)) {
/* 608 */       Area testArea = region.toArea();
/* 609 */       testArea.intersect(thisArea);
/* 610 */       return !testArea.isEmpty();
/*     */     } 
/* 612 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean intersectsBoundingBox(ProtectedRegion region) {
/* 624 */     BlockVector rMaxPoint = region.getMaximumPoint();
/* 625 */     BlockVector min = getMinimumPoint();
/*     */     
/* 627 */     if (rMaxPoint.getBlockX() < min.getBlockX()) return false; 
/* 628 */     if (rMaxPoint.getBlockY() < min.getBlockY()) return false; 
/* 629 */     if (rMaxPoint.getBlockZ() < min.getBlockZ()) return false;
/*     */     
/* 631 */     BlockVector rMinPoint = region.getMinimumPoint();
/* 632 */     BlockVector max = getMaximumPoint();
/*     */     
/* 634 */     if (rMinPoint.getBlockX() > max.getBlockX()) return false; 
/* 635 */     if (rMinPoint.getBlockY() > max.getBlockY()) return false; 
/* 636 */     if (rMinPoint.getBlockZ() > max.getBlockZ()) return false;
/*     */     
/* 638 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean intersectsEdges(ProtectedRegion region) {
/* 648 */     List<BlockVector2D> pts1 = getPoints();
/* 649 */     List<BlockVector2D> pts2 = region.getPoints();
/* 650 */     BlockVector2D lastPt1 = pts1.get(pts1.size() - 1);
/* 651 */     BlockVector2D lastPt2 = pts2.get(pts2.size() - 1);
/* 652 */     for (BlockVector2D aPts1 : pts1) {
/* 653 */       for (BlockVector2D aPts2 : pts2) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 659 */         Line2D line1 = new Line2D.Double(lastPt1.getBlockX(), lastPt1.getBlockZ(), aPts1.getBlockX(), aPts1.getBlockZ());
/*     */         
/* 661 */         if (line1.intersectsLine(lastPt2
/* 662 */             .getBlockX(), lastPt2
/* 663 */             .getBlockZ(), aPts2
/* 664 */             .getBlockX(), aPts2
/* 665 */             .getBlockZ())) {
/* 666 */           return true;
/*     */         }
/* 668 */         lastPt2 = aPts2;
/*     */       } 
/* 670 */       lastPt1 = aPts1;
/*     */     } 
/* 672 */     return false;
/*     */   }
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
/*     */   public boolean isDirty() {
/* 685 */     return (this.dirty || this.owners.isDirty() || this.members.isDirty());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDirty(boolean dirty) {
/* 690 */     this.dirty = dirty;
/* 691 */     this.owners.setDirty(dirty);
/* 692 */     this.members.setDirty(dirty);
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(ProtectedRegion other) {
/* 697 */     if (getPriority() > other.getPriority())
/* 698 */       return -1; 
/* 699 */     if (getPriority() < other.getPriority()) {
/* 700 */       return 1;
/*     */     }
/*     */     
/* 703 */     return getId().compareTo(other.getId());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 708 */     return this.id.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 713 */     if (!(obj instanceof ProtectedRegion)) {
/* 714 */       return false;
/*     */     }
/*     */     
/* 717 */     ProtectedRegion other = (ProtectedRegion)obj;
/* 718 */     return other.getId().equals(getId());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 723 */     return "ProtectedRegion{id='" + this.id + "', " + "type='" + 
/*     */       
/* 725 */       getType() + '\'' + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isValidId(String id) {
/* 736 */     Preconditions.checkNotNull(id);
/* 737 */     return VALID_ID_PATTERN.matcher(id).matches();
/*     */   }
/*     */   
/*     */   public abstract boolean isPhysicalArea();
/*     */   
/*     */   public abstract List<BlockVector2D> getPoints();
/*     */   
/*     */   public abstract int volume();
/*     */   
/*     */   public abstract boolean contains(Vector paramVector);
/*     */   
/*     */   public abstract RegionType getType();
/*     */   
/*     */   abstract Area toArea();
/*     */   
/*     */   public static class CircularInheritanceException extends Exception {}
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\regions\ProtectedRegion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */