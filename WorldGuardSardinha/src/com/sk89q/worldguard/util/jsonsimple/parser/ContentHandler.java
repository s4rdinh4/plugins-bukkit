package com.sk89q.worldguard.util.jsonsimple.parser;

import java.io.IOException;

public interface ContentHandler {
  void startJSON() throws ParseException, IOException;
  
  void endJSON() throws ParseException, IOException;
  
  boolean startObject() throws ParseException, IOException;
  
  boolean endObject() throws ParseException, IOException;
  
  boolean startObjectEntry(String paramString) throws ParseException, IOException;
  
  boolean endObjectEntry() throws ParseException, IOException;
  
  boolean startArray() throws ParseException, IOException;
  
  boolean endArray() throws ParseException, IOException;
  
  boolean primitive(Object paramObject) throws ParseException, IOException;
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\jsonsimple\parser\ContentHandler.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */