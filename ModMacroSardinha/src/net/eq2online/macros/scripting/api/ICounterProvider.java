package net.eq2online.macros.scripting.api;

public interface ICounterProvider {
  public static final int EMPTY = 0;
  
  public static final String KEY = "int";
  
  int getCounter(String paramString);
  
  int getCounter(String paramString, int paramInt);
  
  void setCounter(String paramString, int paramInt);
  
  void setCounter(String paramString, int paramInt1, int paramInt2);
  
  void unsetCounter(String paramString);
  
  void unsetCounter(String paramString, int paramInt);
  
  void incrementCounter(String paramString, int paramInt);
  
  void incrementCounter(String paramString, int paramInt1, int paramInt2);
  
  void decrementCounter(String paramString, int paramInt);
  
  void decrementCounter(String paramString, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\api\ICounterProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */