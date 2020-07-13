/*    */ package net.eq2online.macros.compatibility;
/*    */ 
/*    */ import bsu;
/*    */ import cvj;
/*    */ import cvk;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStreamReader;
/*    */ import net.eq2online.macros.res.ResourceLocations;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatAllowedCharacters
/*    */ {
/* 16 */   public static final String allowedCharacters = getAllowedCharacters();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 21 */   public static final char[] allowedCharactersArray = new char[] { '/', '\n', '\r', '\t', Character.MIN_VALUE, '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':' };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static String getAllowedCharacters() {
/* 29 */     String var0 = "";
/*    */ 
/*    */     
/*    */     try {
/* 33 */       cvk resourceManager = bsu.z().O();
/* 34 */       cvj fontResource = resourceManager.a(ResourceLocations.FONT);
/*    */       
/* 36 */       BufferedReader var1 = new BufferedReader(new InputStreamReader(fontResource.b()));
/* 37 */       String var2 = "";
/*    */       
/* 39 */       while ((var2 = var1.readLine()) != null) {
/*    */         
/* 41 */         if (!var2.startsWith("#"))
/*    */         {
/* 43 */           var0 = var0 + var2;
/*    */         }
/*    */       } 
/*    */       
/* 47 */       var1.close();
/*    */     }
/* 49 */     catch (Exception exception) {}
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 54 */     return var0;
/*    */   }
/*    */ 
/*    */   
/*    */   public static final boolean isAllowedCharacter(char par0) {
/* 59 */     return (par0 != '§' && (allowedCharacters.indexOf(par0) >= 0 || par0 > ' '));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String filerAllowedCharacters(String par0Str) {
/* 67 */     StringBuilder var1 = new StringBuilder();
/* 68 */     char[] var2 = par0Str.toCharArray();
/* 69 */     int var3 = var2.length;
/*    */     
/* 71 */     for (int var4 = 0; var4 < var3; var4++) {
/*    */       
/* 73 */       char var5 = var2[var4];
/*    */       
/* 75 */       if (isAllowedCharacter(var5))
/*    */       {
/* 77 */         var1.append(var5);
/*    */       }
/*    */     } 
/*    */     
/* 81 */     return var1.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\compatibility\ChatAllowedCharacters.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */