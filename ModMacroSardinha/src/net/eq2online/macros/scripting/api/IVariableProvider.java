package net.eq2online.macros.scripting.api;

import java.util.Set;

public interface IVariableProvider extends IMacrosAPIModule {
  void updateVariables(boolean paramBoolean);
  
  Object getVariable(String paramString);
  
  Set<String> getVariables();
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\api\IVariableProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */