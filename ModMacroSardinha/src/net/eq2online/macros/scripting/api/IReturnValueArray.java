package net.eq2online.macros.scripting.api;

import java.util.List;

public interface IReturnValueArray extends IReturnValue {
  int size();
  
  boolean shouldAppend();
  
  List<Boolean> getBooleans();
  
  List<Integer> getIntegers();
  
  List<String> getStrings();
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\api\IReturnValueArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */