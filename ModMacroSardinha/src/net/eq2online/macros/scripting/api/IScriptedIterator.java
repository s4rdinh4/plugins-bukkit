package net.eq2online.macros.scripting.api;

public interface IScriptedIterator extends IVariableProvider {
  boolean continueLooping();
  
  void increment();
  
  void reset();
  
  void breakLoop();
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\api\IScriptedIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */