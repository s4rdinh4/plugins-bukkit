package net.eq2online.macros.scripting.api;

import com.mumfrey.liteloader.util.render.Icon;

public interface IMacroEvent {
  String getName();
  
  IMacroEventProvider getProvider();
  
  void setVariableProviderClass(Class<? extends IMacroEventVariableProvider> paramClass);
  
  IMacroEventVariableProvider getVariableProvider(String[] paramArrayOfString);
  
  void onDispatch();
  
  boolean isPermissible();
  
  String getPermissionGroup();
  
  String getPermissionName();
  
  Icon getIcon();
  
  void setIcon(Icon paramIcon);
  
  String getHelpLine(int paramInt1, int paramInt2);
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\api\IMacroEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */