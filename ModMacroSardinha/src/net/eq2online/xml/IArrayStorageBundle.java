package net.eq2online.xml;

import java.util.Map;
import java.util.Set;

public interface IArrayStorageBundle {
  Set<String> getStorageTypes();
  
  Map<String, Map<Integer, ?>> getStorage(String paramString);
  
  void preDeserialise();
  
  void preSerialise();
  
  void postDeserialise();
  
  void postSerialise();
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\xml\IArrayStorageBundle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */