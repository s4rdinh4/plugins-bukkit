/*     */ package com.sk89q.worldguard.util.profile.util;
/*     */ 
/*     */ import com.sk89q.worldguard.util.jsonsimple.JSONValue;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.Closeable;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpRequest
/*     */   implements Closeable
/*     */ {
/*  57 */   private static final Logger log = Logger.getLogger(HttpRequest.class.getCanonicalName());
/*     */   
/*     */   private static final int READ_TIMEOUT = 600000;
/*     */   private static final int READ_BUFFER_SIZE = 8192;
/*  61 */   private final Map<String, String> headers = new HashMap<String, String>();
/*     */   
/*     */   private final String method;
/*     */   
/*     */   private final URL url;
/*     */   
/*     */   private String contentType;
/*     */   
/*     */   private byte[] body;
/*     */   
/*     */   private HttpURLConnection conn;
/*     */   
/*     */   private InputStream inputStream;
/*     */   
/*     */   private HttpRequest(String method, URL url) {
/*  76 */     this.method = method;
/*  77 */     this.url = url;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpRequest bodyJson(Object object) throws IOException {
/*  88 */     this.contentType = "application/json";
/*  89 */     this.body = JSONValue.toJSONString(object).getBytes();
/*  90 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpRequest bodyForm(Form form) {
/* 100 */     this.contentType = "application/x-www-form-urlencoded";
/* 101 */     this.body = form.toString().getBytes();
/* 102 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpRequest header(String key, String value) {
/* 113 */     this.headers.put(key, value);
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpRequest execute() throws IOException {
/* 126 */     boolean successful = false;
/*     */     
/*     */     try {
/* 129 */       if (this.conn != null) {
/* 130 */         throw new IllegalArgumentException("Connection already executed");
/*     */       }
/*     */       
/* 133 */       this.conn = (HttpURLConnection)reformat(this.url).openConnection();
/*     */       
/* 135 */       if (this.body != null) {
/* 136 */         this.conn.setRequestProperty("Content-Type", this.contentType);
/* 137 */         this.conn.setRequestProperty("Content-Length", Integer.toString(this.body.length));
/* 138 */         this.conn.setDoInput(true);
/*     */       } 
/*     */       
/* 141 */       for (Map.Entry<String, String> entry : this.headers.entrySet()) {
/* 142 */         this.conn.setRequestProperty(entry.getKey(), entry.getValue());
/*     */       }
/*     */       
/* 145 */       this.conn.setRequestMethod(this.method);
/* 146 */       this.conn.setUseCaches(false);
/* 147 */       this.conn.setDoOutput(true);
/* 148 */       this.conn.setReadTimeout(600000);
/*     */       
/* 150 */       this.conn.connect();
/*     */       
/* 152 */       if (this.body != null) {
/* 153 */         DataOutputStream out = new DataOutputStream(this.conn.getOutputStream());
/* 154 */         out.write(this.body);
/* 155 */         out.flush();
/* 156 */         out.close();
/*     */       } 
/*     */       
/* 159 */       this
/* 160 */         .inputStream = (this.conn.getResponseCode() == 200) ? this.conn.getInputStream() : this.conn.getErrorStream();
/*     */       
/* 162 */       successful = true;
/*     */     } finally {
/* 164 */       if (!successful) {
/* 165 */         close();
/*     */       }
/*     */     } 
/*     */     
/* 169 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpRequest expectResponseCode(int... codes) throws IOException {
/* 180 */     int responseCode = getResponseCode();
/*     */     
/* 182 */     for (int code : codes) {
/* 183 */       if (code == responseCode) {
/* 184 */         return this;
/*     */       }
/*     */     } 
/*     */     
/* 188 */     close();
/* 189 */     throw new IOException("Did not get expected response code, got " + responseCode + " for " + this.url);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getResponseCode() throws IOException {
/* 199 */     if (this.conn == null) {
/* 200 */       throw new IllegalArgumentException("No connection has been made");
/*     */     }
/*     */     
/* 203 */     return this.conn.getResponseCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getInputStream() {
/* 212 */     return this.inputStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferedResponse returnContent() throws IOException, InterruptedException {
/* 223 */     if (this.inputStream == null) {
/* 224 */       throw new IllegalArgumentException("No input stream available");
/*     */     }
/*     */     
/*     */     try {
/* 228 */       ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*     */       int b;
/* 230 */       while ((b = this.inputStream.read()) != -1) {
/* 231 */         checkInterrupted();
/* 232 */         bos.write(b);
/*     */       } 
/* 234 */       return new BufferedResponse(bos.toByteArray());
/*     */     } finally {
/* 236 */       close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpRequest saveContent(File file) throws IOException, InterruptedException {
/* 249 */     FileOutputStream fos = null;
/* 250 */     BufferedOutputStream bos = null;
/*     */     
/*     */     try {
/* 253 */       fos = new FileOutputStream(file);
/* 254 */       bos = new BufferedOutputStream(fos);
/*     */       
/* 256 */       saveContent(bos);
/*     */     } finally {
/* 258 */       closeQuietly(bos);
/* 259 */       closeQuietly(fos);
/*     */     } 
/*     */     
/* 262 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpRequest saveContent(OutputStream out) throws IOException, InterruptedException {
/*     */     try {
/* 277 */       BufferedInputStream bis = new BufferedInputStream(this.inputStream);
/*     */       
/* 279 */       byte[] data = new byte[8192];
/*     */       int len;
/* 281 */       while ((len = bis.read(data, 0, 8192)) >= 0) {
/* 282 */         out.write(data, 0, len);
/* 283 */         checkInterrupted();
/*     */       } 
/*     */     } finally {
/* 286 */       close();
/*     */     } 
/*     */     
/* 289 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 294 */     if (this.conn != null) this.conn.disconnect();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HttpRequest get(URL url) {
/* 304 */     return request("GET", url);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HttpRequest post(URL url) {
/* 314 */     return request("POST", url);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HttpRequest request(String method, URL url) {
/* 325 */     return new HttpRequest(method, url);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static URL url(String url) {
/*     */     try {
/* 338 */       return new URL(url);
/* 339 */     } catch (MalformedURLException e) {
/* 340 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static URL reformat(URL existing) {
/*     */     try {
/* 352 */       URL url = new URL(existing.toString());
/*     */ 
/*     */       
/* 355 */       URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
/* 356 */       url = uri.toURL();
/* 357 */       return url;
/* 358 */     } catch (MalformedURLException e) {
/* 359 */       return existing;
/* 360 */     } catch (URISyntaxException e) {
/* 361 */       return existing;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Form
/*     */   {
/* 369 */     public final List<String> elements = new ArrayList<String>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Form add(String key, String value) {
/*     */       try {
/* 383 */         this.elements.add(URLEncoder.encode(key, "UTF-8") + "=" + 
/* 384 */             URLEncoder.encode(value, "UTF-8"));
/* 385 */         return this;
/* 386 */       } catch (UnsupportedEncodingException e) {
/* 387 */         throw new RuntimeException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 393 */       StringBuilder builder = new StringBuilder();
/* 394 */       boolean first = true;
/* 395 */       for (String element : this.elements) {
/* 396 */         if (first) {
/* 397 */           first = false;
/*     */         } else {
/* 399 */           builder.append("&");
/*     */         } 
/* 401 */         builder.append(element);
/*     */       } 
/* 403 */       return builder.toString();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Form form() {
/* 412 */       return new Form();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public class BufferedResponse
/*     */   {
/*     */     private final byte[] data;
/*     */ 
/*     */     
/*     */     private BufferedResponse(byte[] data) {
/* 423 */       this.data = data;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] asBytes() {
/* 432 */       return this.data;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String asString(String encoding) throws IOException {
/* 443 */       return new String(this.data, encoding);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object asJson() throws IOException {
/* 454 */       return JSONValue.parse(asString("UTF-8"));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <T> T asXml(Class<T> cls) throws IOException {
/*     */       try {
/* 467 */         JAXBContext context = JAXBContext.newInstance(new Class[] { cls });
/* 468 */         Unmarshaller um = context.createUnmarshaller();
/* 469 */         return (T)um.unmarshal(new ByteArrayInputStream(this.data));
/* 470 */       } catch (JAXBException e) {
/* 471 */         throw new IOException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BufferedResponse saveContent(File file) throws IOException, InterruptedException {
/* 484 */       FileOutputStream fos = null;
/* 485 */       BufferedOutputStream bos = null;
/*     */       
/* 487 */       file.getParentFile().mkdirs();
/*     */       
/*     */       try {
/* 490 */         fos = new FileOutputStream(file);
/* 491 */         bos = new BufferedOutputStream(fos);
/*     */         
/* 493 */         saveContent(bos);
/*     */       } finally {
/* 495 */         HttpRequest.closeQuietly(bos);
/* 496 */         HttpRequest.closeQuietly(fos);
/*     */       } 
/*     */       
/* 499 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BufferedResponse saveContent(OutputStream out) throws IOException, InterruptedException {
/* 511 */       out.write(this.data);
/*     */       
/* 513 */       return this;
/*     */     }
/*     */   }
/*     */   
/*     */   private static void checkInterrupted() throws InterruptedException {
/* 518 */     if (Thread.currentThread().isInterrupted()) {
/* 519 */       throw new InterruptedException();
/*     */     }
/*     */   }
/*     */   
/*     */   private static void closeQuietly(Closeable closeable) {
/*     */     try {
/* 525 */       closeable.close();
/* 526 */     } catch (IOException ignored) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\profil\\util\HttpRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */