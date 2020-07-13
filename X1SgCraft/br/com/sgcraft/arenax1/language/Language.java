/*    */ package br.com.sgcraft.arenax1.language;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.text.MessageFormat;
/*    */ import java.util.HashMap;
/*    */ import java.util.Properties;

import br.com.sgcraft.arenax1.ArenaConfig;
import br.com.sgcraft.arenax1.ArenaX1;
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
/*    */ public class Language
/*    */ {
/* 23 */   private HashMap<String, MessageFormat> formats = new HashMap<>();
/* 24 */   private Properties prop = new Properties(); public Language(ArenaX1 plugin, ArenaConfig config) {
/* 25 */     InputStream is = null;
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 30 */       is = plugin.getResource(config.getLanguage() + ".properties");
/*    */       
/* 32 */       if (is.available() == 0)
/*    */       {
/* 34 */         is = plugin.getResource("PT-BR.properties");
/*    */       }
/* 36 */       this.prop.load(is);
/* 37 */     } catch (IOException ex) {
/*    */       
/* 39 */       ex.printStackTrace();
/*    */     } finally {
/*    */ 
/*    */       
/*    */       try {
/* 44 */         if (is != null)
/*    */         {
/* 46 */           is.close();
/*    */         }
/* 48 */       } catch (IOException ex) {
/*    */         
/* 50 */         ex.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMessage(String key, Object... infos) {
/*    */     try {
/* 59 */       if (this.formats.containsKey(key))
/*    */       {
/* 61 */         return ((MessageFormat)this.formats.get(key)).format(infos);
/*    */       }
/*    */       
/* 64 */       MessageFormat mF = new MessageFormat(this.prop.getProperty(key));
/* 65 */       this.formats.put(key, mF);
/* 66 */       return mF.format(infos);
/*    */     }
/* 68 */     catch (Exception e) {
/*    */       
/* 70 */       return "Message not found";
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\ArenaX1-0.3.2.jar!\br\com\tinycraft\arenax1\language\Language.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */