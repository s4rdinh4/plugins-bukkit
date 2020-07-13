package org.khelekore.prtree;

import java.util.List;

interface NodeGetter<N> {
  N getNextNode(int paramInt);
  
  boolean hasMoreNodes();
  
  boolean hasMoreData();
  
  List<? extends NodeGetter<N>> split(int paramInt1, int paramInt2);
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\org\khelekore\prtree\NodeGetter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */