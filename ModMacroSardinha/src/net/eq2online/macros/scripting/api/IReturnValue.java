package net.eq2online.macros.scripting.api;

public interface IReturnValue {
  boolean isVoid();
  
  boolean getBoolean();
  
  int getInteger();
  
  String getString();
  
  String getLocalMessage();
  
  String getRemoteMessage();
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\api\IReturnValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */