/*    */ package com.sk89q.worldguard.util.paste;
/*    */ 
/*    */ import com.google.common.util.concurrent.ListenableFuture;
/*    */ import com.sk89q.worldguard.util.jsonsimple.JSONValue;
/*    */ import com.sk89q.worldguard.util.net.HttpRequest;
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.Callable;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EngineHubPaste
/*    */   implements Paster
/*    */ {
/* 36 */   private static final Pattern URL_PATTERN = Pattern.compile("https?://.+$");
/*    */ 
/*    */   
/*    */   public ListenableFuture<URL> paste(String content) {
/* 40 */     return Pasters.getExecutor().submit(new PasteTask(content));
/*    */   }
/*    */   
/*    */   private final class PasteTask implements Callable<URL> {
/*    */     private final String content;
/*    */     
/*    */     private PasteTask(String content) {
/* 47 */       this.content = content;
/*    */     }
/*    */ 
/*    */     
/*    */     public URL call() throws IOException, InterruptedException {
/* 52 */       HttpRequest.Form form = HttpRequest.Form.create();
/* 53 */       form.add("content", this.content);
/* 54 */       form.add("from", "worldguard");
/*    */       
/* 56 */       URL url = HttpRequest.url("http://paste.enginehub.org/paste");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 62 */       String result = HttpRequest.post(url).bodyForm(form).execute().expectResponseCode(new int[] { 200 }).returnContent().asString("UTF-8").trim();
/*    */       
/* 64 */       Object object = JSONValue.parse(result);
/* 65 */       if (object instanceof Map) {
/*    */         
/* 67 */         String urlString = String.valueOf(((Map)object).get("url"));
/* 68 */         Matcher m = EngineHubPaste.URL_PATTERN.matcher(urlString);
/*    */         
/* 70 */         if (m.matches()) {
/* 71 */           return new URL(urlString);
/*    */         }
/*    */       } 
/*    */       
/* 75 */       throw new IOException("Failed to save paste; instead, got: " + result);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\paste\EngineHubPaste.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */