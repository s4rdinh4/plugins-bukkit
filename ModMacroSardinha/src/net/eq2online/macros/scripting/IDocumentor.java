package net.eq2online.macros.scripting;

import java.io.File;
import net.eq2online.macros.scripting.api.IScriptAction;

public interface IDocumentor {
  IDocumentor loadXml(String paramString);
  
  void writeXml(File paramFile);
  
  IDocumentationEntry getDocumentation(String paramString);
  
  IDocumentationEntry getDocumentation(IScriptAction paramIScriptAction);
  
  void setDocumentation(IScriptAction paramIScriptAction);
  
  void appendScriptActionNode(IScriptAction paramIScriptAction);
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\IDocumentor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */