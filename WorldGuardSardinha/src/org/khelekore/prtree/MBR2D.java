package org.khelekore.prtree;

public interface MBR2D {
  double getMinX();
  
  double getMinY();
  
  double getMaxX();
  
  double getMaxY();
  
  MBR2D union(MBR2D paramMBR2D);
  
  boolean intersects(MBR2D paramMBR2D);
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\org\khelekore\prtree\MBR2D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */