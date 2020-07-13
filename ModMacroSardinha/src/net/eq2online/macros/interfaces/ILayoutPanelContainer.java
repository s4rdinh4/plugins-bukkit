package net.eq2online.macros.interfaces;

public interface ILayoutPanelContainer {
  void captureWidgetAt(int paramInt1, int paramInt2);
  
  ILayoutWidget getCapturedWidget();
  
  void handleWidgetClick(ILayoutPanel<? extends ILayoutWidget> paramILayoutPanel, int paramInt, boolean paramBoolean);
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\interfaces\ILayoutPanelContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */