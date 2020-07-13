package net.eq2online.macros.interfaces;

import net.eq2online.macros.core.MacroParamProvider;
import net.eq2online.macros.core.MacroType;
import net.eq2online.macros.core.params.MacroParam;

public interface IMacroParamStorage {
  MacroType getMacroType();
  
  String getStoredParam(MacroParamProvider paramMacroParamProvider);
  
  void setStoredParam(MacroParam.Type paramType, String paramString);
  
  void setStoredParam(MacroParam.Type paramType, int paramInt, String paramString);
  
  void setStoredParam(MacroParam.Type paramType, int paramInt, String paramString1, String paramString2);
  
  void setReplaceFirstOccurrenceOnly(MacroParam.Type paramType, boolean paramBoolean);
  
  boolean shouldReplaceFirstOccurrenceOnly(MacroParam.Type paramType);
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\interfaces\IMacroParamStorage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */