package org.khelekore.prtree;

import java.util.List;
import java.util.PriorityQueue;

interface Node<T> {
  int size();
  
  MBR getMBR(MBRConverter<T> paramMBRConverter);
  
  void expand(MBR paramMBR, MBRConverter<T> paramMBRConverter, List<T> paramList, List<Node<T>> paramList1);
  
  void find(MBR paramMBR, MBRConverter<T> paramMBRConverter, List<T> paramList);
  
  void nnExpand(DistanceCalculator<T> paramDistanceCalculator, NodeFilter<T> paramNodeFilter, List<DistanceResult<T>> paramList, int paramInt, PriorityQueue<Node<T>> paramPriorityQueue, MinDistComparator<T, Node<T>> paramMinDistComparator);
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\org\khelekore\prtree\Node.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */