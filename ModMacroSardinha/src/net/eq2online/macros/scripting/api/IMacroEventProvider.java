package net.eq2online.macros.scripting.api;

public interface IMacroEventProvider extends IMacrosAPIModule {
  IMacroEventDispatcher getDispatcher();
  
  void registerEvents(IMacroEventManager paramIMacroEventManager);
  
  String getHelp(IMacroEvent paramIMacroEvent, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\api\IMacroEventProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */