package net.eq2online.macros.interfaces;

import net.eq2online.macros.core.params.MacroParam;

public interface IMacroParamTarget {
  String getDisplayName();
  
  String getPromptMessage();
  
  boolean hasRemainingParams();
  
  MacroParam getNextParam();
  
  int getIteration();
  
  String getIterationString();
  
  void compile();
  
  void recompile();
  
  String getTargetString();
  
  void setTargetString(String paramString);
  
  IMacroParamStorage getParamStore();
  
  void handleCompleted();
  
  void handleCancelled();
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\interfaces\IMacroParamTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */