package net.eq2online.macros.scripting.api;

import java.util.HashMap;

public interface IMacro extends IVariableProvider, IVariableListener {
  int getID();
  
  String getDisplayName();
  
  void setSynchronous(boolean paramBoolean);
  
  boolean isSynchronous();
  
  IFlagProvider getFlagProvider();
  
  ICounterProvider getCounterProvider();
  
  IStringProvider getStringProvider();
  
  IMutableArrayProvider getArrayProvider();
  
  IMacroActionContext getContext();
  
  HashMap<String, Object> getStateData();
  
  Object getState(String paramString);
  
  void setState(String paramString, Object paramObject);
  
  void markDirty();
  
  boolean isDirty();
  
  void kill();
  
  boolean isDead();
  
  void registerVariableProvider(IVariableProvider paramIVariableProvider);
  
  void unregisterVariableProvider(IVariableProvider paramIVariableProvider);
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\api\IMacro.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */