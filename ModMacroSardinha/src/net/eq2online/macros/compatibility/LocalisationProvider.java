/*    */ package net.eq2online.macros.compatibility;
/*    */ 
/*    */ import net.eq2online.macros.interfaces.ILocalisationProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LocalisationProvider
/*    */ {
/*    */   private static ILocalisationProvider activeProvider;
/*    */   
/*    */   public static void setProvider(ILocalisationProvider provider) {
/* 15 */     activeProvider = provider;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String getRawLocalisedString(String key) {
/* 20 */     return (activeProvider != null) ? activeProvider.getRawLocalisedString(key) : key;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String getLocalisedString(String key) {
/* 25 */     return (activeProvider != null) ? activeProvider.getLocalisedString(key) : key;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String getLocalisedString(String key, Object... params) {
/* 30 */     return (activeProvider != null) ? activeProvider.getLocalisedString(key, params) : key;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\compatibility\LocalisationProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */