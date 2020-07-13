package net.eq2online.macros.scripting;

import net.eq2online.macros.scripting.api.IScriptAction;
import net.eq2online.macros.scripting.parser.ScriptContext;
import net.eq2online.macros.scripting.parser.ScriptCore;

public interface IActionFilter {
  boolean pass(ScriptContext paramScriptContext, ScriptCore paramScriptCore, IScriptAction paramIScriptAction);
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\IActionFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */