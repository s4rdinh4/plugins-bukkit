package net.eq2online.macros.interfaces;

import bsu;
import java.awt.Point;

public interface ILayoutPanel<TWidget extends ILayoutWidget> {
  void connect(ILayoutPanelContainer paramILayoutPanelContainer);
  
  void release();
  
  void setSizeAndPosition(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  boolean isDragging();
  
  Point getWidgetPosition(ILayoutWidget paramILayoutWidget);
  
  ILayoutWidget getWidgetAt(int paramInt1, int paramInt2);
  
  boolean keyPressed(char paramChar, int paramInt);
  
  void postRender(bsu parambsu, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\interfaces\ILayoutPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */