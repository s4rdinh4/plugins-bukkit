package net.eq2online.macros.scripting.api;

import net.eq2online.macros.scripting.parser.ScriptContext;

public interface IScriptAction extends IMacrosAPIModule {
  ScriptContext getContext();
  
  String getName();
  
  boolean isThreadSafe();
  
  boolean isStackPushOperator();
  
  boolean isStackPopOperator();
  
  boolean canBePoppedBy(IScriptAction paramIScriptAction);
  
  boolean executeStackPush(IScriptActionProvider paramIScriptActionProvider, IMacro paramIMacro, IMacroAction paramIMacroAction, String paramString, String[] paramArrayOfString);
  
  boolean executeStackPop(IScriptActionProvider paramIScriptActionProvider, IMacro paramIMacro, IMacroAction paramIMacroAction1, String paramString, String[] paramArrayOfString, IMacroAction paramIMacroAction2);
  
  boolean canBreak(IMacroActionProcessor paramIMacroActionProcessor, IScriptActionProvider paramIScriptActionProvider, IMacro paramIMacro, IMacroAction paramIMacroAction1, IMacroAction paramIMacroAction2);
  
  boolean isConditionalOperator();
  
  boolean isConditionalElseOperator(IScriptAction paramIScriptAction);
  
  boolean matchesConditionalOperator(IScriptAction paramIScriptAction);
  
  boolean executeConditional(IScriptActionProvider paramIScriptActionProvider, IMacro paramIMacro, IMacroAction paramIMacroAction, String paramString, String[] paramArrayOfString);
  
  void executeConditionalElse(IScriptActionProvider paramIScriptActionProvider, IMacro paramIMacro, IMacroAction paramIMacroAction, String paramString, String[] paramArrayOfString, IMacroActionStackEntry paramIMacroActionStackEntry);
  
  IReturnValue execute(IScriptActionProvider paramIScriptActionProvider, IMacro paramIMacro, IMacroAction paramIMacroAction, String paramString, String[] paramArrayOfString);
  
  boolean canExecuteNow(IScriptActionProvider paramIScriptActionProvider, IMacro paramIMacro, IMacroAction paramIMacroAction, String paramString, String[] paramArrayOfString);
  
  int onTick(IScriptActionProvider paramIScriptActionProvider);
  
  boolean isClocked();
  
  boolean isPermissable();
  
  String getPermissionGroup();
  
  void registerPermissions(String paramString1, String paramString2);
  
  boolean checkExecutePermission();
  
  boolean checkPermission(String paramString1, String paramString2);
  
  void onStopped(IScriptActionProvider paramIScriptActionProvider, IMacro paramIMacro, IMacroAction paramIMacroAction);
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\api\IScriptAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */