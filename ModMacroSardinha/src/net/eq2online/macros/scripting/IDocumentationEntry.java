package net.eq2online.macros.scripting;

import bty;

public interface IDocumentationEntry {
  boolean isHidden();
  
  String getName();
  
  String getUsage();
  
  String getDescription();
  
  String getReturnType();
  
  void drawAt(bty parambty, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\IDocumentationEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */