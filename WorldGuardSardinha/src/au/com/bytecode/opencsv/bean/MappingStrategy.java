package au.com.bytecode.opencsv.bean;

import au.com.bytecode.opencsv.CSVReader;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;

public interface MappingStrategy<T> {
  PropertyDescriptor findDescriptor(int paramInt) throws IntrospectionException;
  
  T createBean() throws InstantiationException, IllegalAccessException;
  
  void captureHeader(CSVReader paramCSVReader) throws IOException;
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\au\com\bytecode\opencsv\bean\MappingStrategy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */