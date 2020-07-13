package net.eq2online.macros.scripting.api;

import net.eq2online.macros.scripting.exceptions.ScriptExceptionStackOverflow;

public interface IMacroAction {
  boolean canExecuteNow(IMacroActionContext paramIMacroActionContext, IMacro paramIMacro);
  
  boolean isClocked();
  
  boolean execute(IMacroActionContext paramIMacroActionContext, IMacro paramIMacro, boolean paramBoolean1, boolean paramBoolean2) throws ScriptExceptionStackOverflow;
  
  boolean executeStackPop(IMacroActionProcessor paramIMacroActionProcessor, IMacroActionContext paramIMacroActionContext, IMacro paramIMacro, IMacroAction paramIMacroAction);
  
  boolean canBreak(IMacroActionProcessor paramIMacroActionProcessor, IScriptActionProvider paramIScriptActionProvider, IMacro paramIMacro, IMacroAction paramIMacroAction);
  
  IScriptAction getAction();
  
  <T> T getState();
  
  void setState(Object paramObject);
  
  String[] getParams();
  
  String getRawParams();
  
  IMacroActionProcessor getActionProcessor();
  
  void refreshPermissions(IScriptParser paramIScriptParser);
  
  void onStopped(IMacroActionProcessor paramIMacroActionProcessor, IMacroActionContext paramIMacroActionContext, IMacro paramIMacro);
  
  boolean hasOutVar();
  
  String getOutVarName();
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\api\IMacroAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */