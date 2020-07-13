/*    */ package com.sk89q.worldguard.util.paste;
/*    */ 
/*    */ import com.google.common.util.concurrent.ListenableFuture;
/*    */ import com.sk89q.worldguard.util.net.HttpRequest;
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
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
/*    */ 
/*    */ 
/*    */ public class Pastebin
/*    */   implements Paster
/*    */ {
/* 36 */   private static final Pattern URL_PATTERN = Pattern.compile("https?://pastebin.com/([^/]+)$");
/*    */   
/*    */   private boolean mungingLinks = true;
/*    */   
/*    */   public boolean isMungingLinks() {
/* 41 */     return this.mungingLinks;
/*    */   }
/*    */   
/*    */   public void setMungingLinks(boolean mungingLinks) {
/* 45 */     this.mungingLinks = mungingLinks;
/*    */   }
/*    */ 
/*    */   
/*    */   public ListenableFuture<URL> paste(String content) {
/* 50 */     if (this.mungingLinks) {
/* 51 */       content = content.replaceAll("http://", "http_//");
/*    */     }
/*    */     
/* 54 */     return Pasters.getExecutor().submit(new PasteTask(content));
/*    */   }
/*    */   
/*    */   private final class PasteTask implements Callable<URL> {
/*    */     private final String content;
/*    */     
/*    */     private PasteTask(String content) {
/* 61 */       this.content = content;
/*    */     }
/*    */ 
/*    */     
/*    */     public URL call() throws IOException, InterruptedException {
/* 66 */       HttpRequest.Form form = HttpRequest.Form.create();
/* 67 */       form.add("api_option", "paste");
/* 68 */       form.add("api_dev_key", "4867eae74c6990dbdef07c543cf8f805");
/* 69 */       form.add("api_paste_code", this.content);
/* 70 */       form.add("api_paste_private", "0");
/* 71 */       form.add("api_paste_name", "");
/* 72 */       form.add("api_paste_expire_date", "1W");
/* 73 */       form.add("api_paste_format", "text");
/* 74 */       form.add("api_user_key", "");
/*    */       
/* 76 */       URL url = HttpRequest.url("http://pastebin.com/api/api_post.php");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 82 */       String result = HttpRequest.post(url).bodyForm(form).execute().expectResponseCode(new int[] { 200 }).returnContent().asString("UTF-8").trim();
/*    */       
/* 84 */       Matcher m = Pastebin.URL_PATTERN.matcher(result);
/*    */       
/* 86 */       if (m.matches())
/* 87 */         return new URL("http://pastebin.com/raw.php?i=" + m.group(1)); 
/* 88 */       if (result.matches("^https?://.+")) {
/* 89 */         return new URL(result);
/*    */       }
/* 91 */       throw new IOException("Failed to save paste; instead, got: " + result);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\paste\Pastebin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */