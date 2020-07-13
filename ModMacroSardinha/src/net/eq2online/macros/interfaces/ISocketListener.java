package net.eq2online.macros.interfaces;

import net.eq2online.macros.gui.controls.GuiListItemSocket;

public interface ISocketListener {
  void onSocketChanged(GuiListItemSocket paramGuiListItemSocket, IListObject paramIListObject);
  
  void onSocketCleared(GuiListItemSocket paramGuiListItemSocket);
  
  void onSocketClicked(GuiListItemSocket paramGuiListItemSocket, boolean paramBoolean);
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\interfaces\ISocketListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */