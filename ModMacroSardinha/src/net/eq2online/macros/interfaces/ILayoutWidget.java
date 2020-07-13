package net.eq2online.macros.interfaces;

import bsu;
import java.awt.Point;
import java.awt.Rectangle;
import net.eq2online.macros.gui.layout.LayoutPanelEditMode;

public interface ILayoutWidget<TParent> {
  boolean setWidgetPosition(TParent paramTParent, int paramInt1, int paramInt2);
  
  void setWidgetPositionSnapped(TParent paramTParent, int paramInt1, int paramInt2);
  
  Point getWidgetPosition(TParent paramTParent);
  
  int getWidgetId();
  
  int getWidgetWidth(TParent paramTParent);
  
  boolean getWidgetIsBound();
  
  boolean getWidgetIsBindable();
  
  boolean getWidgetIsDenied();
  
  int getWidgetZIndex();
  
  String getWidgetDisplayText();
  
  String getWidgetDeniedText();
  
  void toggleReservedState();
  
  void drawWidget(TParent paramTParent, Rectangle paramRectangle, int paramInt1, int paramInt2, LayoutPanelEditMode paramLayoutPanelEditMode, boolean paramBoolean1, boolean paramBoolean2);
  
  void mouseDragged(bsu parambsu, int paramInt1, int paramInt2);
  
  void mouseReleased(int paramInt1, int paramInt2);
  
  boolean mousePressed(bsu parambsu, int paramInt1, int paramInt2);
  
  boolean mouseOver(Rectangle paramRectangle, int paramInt1, int paramInt2, boolean paramBoolean);
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\interfaces\ILayoutWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */