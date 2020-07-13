/*    */ package au.com.bytecode.opencsv.bean;
/*    */ 
/*    */ import au.com.bytecode.opencsv.CSVReader;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ColumnPositionMappingStrategy<T>
/*    */   extends HeaderColumnNameMappingStrategy<T>
/*    */ {
/* 23 */   protected String[] columnMapping = new String[0];
/*    */   
/*    */   public void captureHeader(CSVReader reader) throws IOException {}
/*    */   
/*    */   protected String getColumnName(int col) {
/* 28 */     return (null != this.columnMapping && col < this.columnMapping.length) ? this.columnMapping[col] : null;
/*    */   }
/*    */   public String[] getColumnMapping() {
/* 31 */     return this.columnMapping;
/*    */   }
/*    */   public void setColumnMapping(String[] columnMapping) {
/* 34 */     this.columnMapping = columnMapping;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\au\com\bytecode\opencsv\bean\ColumnPositionMappingStrategy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */