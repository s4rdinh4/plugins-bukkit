package net.eq2online.macros.scripting.api;

public interface IExpressionEvaluator extends IVariableListener {
  void dumpVariables();
  
  int addStringLiteral(String paramString);
  
  int evaluate();
  
  int getResult();
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\api\IExpressionEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */