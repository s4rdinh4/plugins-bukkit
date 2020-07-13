package net.eq2online.macros.scripting.api;

public interface IMacroActionStackEntry {
  boolean isStackPushOperator();
  
  boolean canBePoppedBy(IMacroAction paramIMacroAction);
  
  void executeStackPop(IMacroActionProcessor paramIMacroActionProcessor, IMacroActionContext paramIMacroActionContext, IMacro paramIMacro, IMacroAction paramIMacroAction);
  
  boolean isConditionalOperator();
  
  boolean isConditionalElseOperator(IMacroAction paramIMacroAction);
  
  boolean matchesConditionalOperator(IMacroAction paramIMacroAction);
  
  boolean getConditionalFlag();
  
  void setConditionalFlag(boolean paramBoolean);
  
  boolean getIfFlag();
  
  void setIfFlag(boolean paramBoolean);
  
  boolean getElseFlag();
  
  void setElseFlag(boolean paramBoolean);
  
  IMacroAction getAction();
  
  int getStackPointer();
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\api\IMacroActionStackEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */