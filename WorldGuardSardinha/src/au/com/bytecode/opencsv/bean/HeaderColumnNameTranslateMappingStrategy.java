/*    */ package au.com.bytecode.opencsv.bean;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public class HeaderColumnNameTranslateMappingStrategy<T>
/*    */   extends HeaderColumnNameMappingStrategy<T>
/*    */ {
/* 22 */   private Map<String, String> columnMapping = new HashMap<String, String>();
/*    */   protected String getColumnName(int col) {
/* 24 */     return getColumnMapping().get(this.header[col]);
/*    */   }
/*    */   public Map<String, String> getColumnMapping() {
/* 27 */     return this.columnMapping;
/*    */   }
/*    */   public void setColumnMapping(Map<String, String> columnMapping) {
/* 30 */     this.columnMapping = columnMapping;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\au\com\bytecode\opencsv\bean\HeaderColumnNameTranslateMappingStrategy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */