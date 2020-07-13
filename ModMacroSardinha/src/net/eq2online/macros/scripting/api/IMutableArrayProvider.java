package net.eq2online.macros.scripting.api;

public interface IMutableArrayProvider extends IArrayProvider {
  public static final int MISSING = -1;
  
  boolean push(String paramString1, String paramString2);
  
  String pop(String paramString);
  
  boolean put(String paramString1, String paramString2);
  
  void delete(String paramString, int paramInt);
  
  void clear(String paramString);
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\api\IMutableArrayProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */