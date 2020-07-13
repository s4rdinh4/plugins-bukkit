package org.khelekore.prtree;

public interface MBR {
  int getDimensions();
  
  double getMin(int paramInt);
  
  double getMax(int paramInt);
  
  MBR union(MBR paramMBR);
  
  boolean intersects(MBR paramMBR);
  
  <T> boolean intersects(T paramT, MBRConverter<T> paramMBRConverter);
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\org\khelekore\prtree\MBR.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */