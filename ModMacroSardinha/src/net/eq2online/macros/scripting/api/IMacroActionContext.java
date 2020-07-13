package net.eq2online.macros.scripting.api;

import net.eq2online.macros.scripting.parser.ScriptContext;

public interface IMacroActionContext {
  ScriptContext getScriptContext();
  
  IScriptActionProvider getProvider();
  
  IVariableProvider getVariableProvider();
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\api\IMacroActionContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */