package net.eq2online.macros.interfaces;

public interface IDragDrop {
  boolean isValidDragTarget();
  
  boolean isValidDragSource();
  
  void addDragTarget(IDragDrop paramIDragDrop);
  
  void addDragTarget(IDragDrop paramIDragDrop, boolean paramBoolean);
  
  void removeDragTarget(IDragDrop paramIDragDrop);
  
  void removeDragTarget(IDragDrop paramIDragDrop, boolean paramBoolean);
  
  boolean dragDrop(IDragDrop paramIDragDrop, IListObject paramIListObject, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\interfaces\IDragDrop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */