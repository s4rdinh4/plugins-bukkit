package org.khelekore.prtree;

import java.util.Comparator;

interface NodeComparators<T> {
  Comparator<T> getMinComparator(int paramInt);
  
  Comparator<T> getMaxComparator(int paramInt);
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\org\khelekore\prtree\NodeComparators.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */