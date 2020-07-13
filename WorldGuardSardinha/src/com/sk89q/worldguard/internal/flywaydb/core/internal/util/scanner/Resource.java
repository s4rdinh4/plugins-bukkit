package com.sk89q.worldguard.internal.flywaydb.core.internal.util.scanner;

public interface Resource {
  String getLocation();
  
  String getLocationOnDisk();
  
  String loadAsString(String paramString);
  
  byte[] loadAsBytes();
  
  String getFilename();
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\interna\\util\scanner\Resource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */