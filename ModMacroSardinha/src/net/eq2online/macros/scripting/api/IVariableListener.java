package net.eq2online.macros.scripting.api;

import java.util.Map;

public interface IVariableListener {
  void setVariable(String paramString, boolean paramBoolean);
  
  void setVariable(String paramString, int paramInt);
  
  void setVariable(String paramString1, String paramString2);
  
  void setVariables(Map<String, Object> paramMap);
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\api\IVariableListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */