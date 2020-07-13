package net.eq2online.macros.scripting.api;

import java.util.List;

public interface IMacroEventManager {
  void registerEventProvider(IMacroEventProvider paramIMacroEventProvider);
  
  IMacroEvent registerEvent(IMacroEventProvider paramIMacroEventProvider, String paramString);
  
  IMacroEvent registerEvent(IMacroEventProvider paramIMacroEventProvider, String paramString1, String paramString2);
  
  IMacroEvent registerEvent(IMacroEvent paramIMacroEvent);
  
  IMacroEvent getEvent(int paramInt);
  
  IMacroEvent getEvent(String paramString);
  
  List<IMacroEvent> getEvents();
  
  int getEventID(String paramString);
  
  int getEventID(IMacroEvent paramIMacroEvent);
  
  void sendEvent(IMacroEvent paramIMacroEvent, String... paramVarArgs);
  
  void sendEvent(String paramString, int paramInt, String... paramVarArgs);
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\api\IMacroEventManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */