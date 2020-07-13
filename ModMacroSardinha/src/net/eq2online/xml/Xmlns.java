/*    */ package net.eq2online.xml;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import javax.xml.namespace.NamespaceContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Xmlns
/*    */   implements NamespaceContext
/*    */ {
/* 16 */   private HashMap<String, String> prefixes = new HashMap<String, String>();
/*    */ 
/*    */   
/*    */   public void addPrefix(String prefix, String namespaceURI) {
/* 20 */     this.prefixes.put(prefix, namespaceURI);
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 25 */     this.prefixes.clear();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Iterator<String> getPrefixes(String namespaceURI) {
/* 31 */     return this.prefixes.keySet().iterator();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPrefix(String namespaceURI) {
/* 37 */     for (Map.Entry<String, String> prefix : this.prefixes.entrySet()) {
/*    */       
/* 39 */       if (((String)prefix.getValue()).equals(namespaceURI)) {
/* 40 */         return prefix.getKey();
/*    */       }
/*    */     } 
/* 43 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getNamespaceURI(String prefix) {
/* 49 */     return this.prefixes.get(prefix);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\xml\Xmlns.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */