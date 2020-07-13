package org.khelekore.prtree;

public interface MBRConverter<T> {
  int getDimensions();
  
  double getMin(int paramInt, T paramT);
  
  double getMax(int paramInt, T paramT);
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\org\khelekore\prtree\MBRConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */