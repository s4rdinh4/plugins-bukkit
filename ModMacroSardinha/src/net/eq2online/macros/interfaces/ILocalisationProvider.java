package net.eq2online.macros.interfaces;

public interface ILocalisationProvider {
  String getRawLocalisedString(String paramString);
  
  String getLocalisedString(String paramString);
  
  String getLocalisedString(String paramString, Object... paramVarArgs);
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\interfaces\ILocalisationProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */