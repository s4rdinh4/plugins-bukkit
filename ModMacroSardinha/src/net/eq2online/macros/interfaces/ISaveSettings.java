package net.eq2online.macros.interfaces;

public interface ISaveSettings {
  void notifySettingsCleared();
  
  void notifySettingsLoaded(ISettingsProvider paramISettingsProvider);
  
  void saveSettings(ISettingsProvider paramISettingsProvider);
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\interfaces\ISaveSettings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */