/*     */ package com.sk89q.worldguard.util.net;
/*     */ 
/*     */ import com.sk89q.worldguard.util.io.Closer;
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
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpRequest
/*     */   implements Closeable
/*     */ {
/*     */   private static final int CONNECT_TIMEOUT = 5000;
/*     */   private static final int READ_TIMEOUT = 5000;
/*     */   private static final int READ_BUFFER_SIZE = 8192;
/*  40 */   private final Map<String, String> headers = new HashMap<String, String>();
/*     */   
/*     */   private final String method;
/*     */   private final URL url;
/*     */   private String contentType;
/*     */   private byte[] body;
/*     */   private HttpURLConnection conn;
/*     */   private InputStream inputStream;
/*  48 */   private long contentLength = -1L;
/*  49 */   private long readBytes = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private HttpRequest(String method, URL url) {
/*  58 */     this.method = method;
/*  59 */     this.url = url;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpRequest body(String data) {
/*  68 */     this.body = data.getBytes();
/*  69 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpRequest bodyForm(Form form) {
/*  79 */     this.contentType = "application/x-www-form-urlencoded";
/*  80 */     this.body = form.toString().getBytes();
/*  81 */     return this;
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
/*  92 */     if (key.equalsIgnoreCase("Content-Type")) {
/*  93 */       this.contentType = value;
/*     */     } else {
/*  95 */       this.headers.put(key, value);
/*     */     } 
/*  97 */     return this;
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
/* 109 */     boolean successful = false;
/*     */     
/*     */     try {
/* 112 */       if (this.conn != null) {
/* 113 */         throw new IllegalArgumentException("Connection already executed");
/*     */       }
/*     */       
/* 116 */       this.conn = (HttpURLConnection)reformat(this.url).openConnection();
/* 117 */       this.conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Java)");
/*     */       
/* 119 */       if (this.body != null) {
/* 120 */         this.conn.setRequestProperty("Content-Type", this.contentType);
/* 121 */         this.conn.setRequestProperty("Content-Length", Integer.toString(this.body.length));
/* 122 */         this.conn.setDoInput(true);
/*     */       } 
/*     */       
/* 125 */       for (Map.Entry<String, String> entry : this.headers.entrySet()) {
/* 126 */         this.conn.setRequestProperty(entry.getKey(), entry.getValue());
/*     */       }
/*     */       
/* 129 */       this.conn.setRequestMethod(this.method);
/* 130 */       this.conn.setUseCaches(false);
/* 131 */       this.conn.setDoOutput(true);
/* 132 */       this.conn.setConnectTimeout(5000);
/* 133 */       this.conn.setReadTimeout(5000);
/*     */       
/* 135 */       this.conn.connect();
/*     */       
/* 137 */       if (this.body != null) {
/* 138 */         DataOutputStream out = new DataOutputStream(this.conn.getOutputStream());
/* 139 */         out.write(this.body);
/* 140 */         out.flush();
/* 141 */         out.close();
/*     */       } 
/*     */       
/* 144 */       this
/* 145 */         .inputStream = (this.conn.getResponseCode() == 200) ? this.conn.getInputStream() : this.conn.getErrorStream();
/*     */       
/* 147 */       successful = true;
/*     */     } finally {
/* 149 */       if (!successful) {
/* 150 */         close();
/*     */       }
/*     */     } 
/*     */     
/* 154 */     return this;
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
/* 165 */     int responseCode = getResponseCode();
/*     */     
/* 167 */     for (int code : codes) {
/* 168 */       if (code == responseCode) {
/* 169 */         return this;
/*     */       }
/*     */     } 
/*     */     
/* 173 */     close();
/* 174 */     throw new IOException("Did not get expected response code, got " + responseCode + " for " + this.url);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getResponseCode() throws IOException {
/* 184 */     if (this.conn == null) {
/* 185 */       throw new IllegalArgumentException("No connection has been made");
/*     */     }
/*     */     
/* 188 */     return this.conn.getResponseCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getInputStream() {
/* 197 */     return this.inputStream;
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
/* 208 */     if (this.inputStream == null) {
/* 209 */       throw new IllegalArgumentException("No input stream available");
/*     */     }
/*     */     
/*     */     try {
/* 213 */       ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 214 */       int b = 0;
/* 215 */       while ((b = this.inputStream.read()) != -1) {
/* 216 */         bos.write(b);
/*     */       }
/* 218 */       return new BufferedResponse(bos.toByteArray());
/*     */     } finally {
/* 220 */       close();
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
/* 233 */     Closer closer = Closer.create();
/*     */     
/*     */     try {
/* 236 */       FileOutputStream fos = (FileOutputStream)closer.register(new FileOutputStream(file));
/* 237 */       BufferedOutputStream bos = (BufferedOutputStream)closer.register(new BufferedOutputStream(fos));
/*     */       
/* 239 */       saveContent(bos);
/*     */     } finally {
/* 241 */       closer.close();
/*     */     } 
/*     */     
/* 244 */     return this;
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
/* 259 */       String field = this.conn.getHeaderField("Content-Length");
/* 260 */       if (field != null) {
/* 261 */         long len = Long.parseLong(field);
/* 262 */         if (len >= 0L) {
/* 263 */           this.contentLength = len;
/*     */         }
/*     */       } 
/* 266 */     } catch (NumberFormatException ignored) {}
/*     */ 
/*     */     
/*     */     try {
/* 270 */       BufferedInputStream bis = new BufferedInputStream(this.inputStream);
/*     */       
/* 272 */       byte[] data = new byte[8192];
/* 273 */       int len = 0;
/* 274 */       while ((len = bis.read(data, 0, 8192)) >= 0) {
/* 275 */         out.write(data, 0, len);
/* 276 */         this.readBytes += len;
/*     */       } 
/*     */     } finally {
/* 279 */       close();
/*     */     } 
/*     */     
/* 282 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 287 */     if (this.conn != null) this.conn.disconnect();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HttpRequest get(URL url) {
/* 297 */     return request("GET", url);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HttpRequest post(URL url) {
/* 307 */     return request("POST", url);
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
/* 318 */     return new HttpRequest(method, url);
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
/* 331 */       return new URL(url);
/* 332 */     } catch (MalformedURLException e) {
/* 333 */       throw new RuntimeException(e);
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
/* 345 */       URL url = new URL(existing.toString());
/*     */ 
/*     */       
/* 348 */       URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
/* 349 */       url = uri.toURL();
/* 350 */       return url;
/* 351 */     } catch (MalformedURLException e) {
/* 352 */       return existing;
/* 353 */     } catch (URISyntaxException e) {
/* 354 */       return existing;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Form
/*     */   {
/* 362 */     public final List<String> elements = new ArrayList<String>();
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
/* 376 */         this.elements.add(URLEncoder.encode(key, "UTF-8") + "=" + 
/* 377 */             URLEncoder.encode(value, "UTF-8"));
/* 378 */         return this;
/* 379 */       } catch (UnsupportedEncodingException e) {
/* 380 */         throw new RuntimeException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 386 */       StringBuilder builder = new StringBuilder();
/* 387 */       boolean first = true;
/* 388 */       for (String element : this.elements) {
/* 389 */         if (first) {
/* 390 */           first = false;
/*     */         } else {
/* 392 */           builder.append("&");
/*     */         } 
/* 394 */         builder.append(element);
/*     */       } 
/* 396 */       return builder.toString();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Form create() {
/* 405 */       return new Form();
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
/* 416 */       this.data = data;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] asBytes() {
/* 425 */       return this.data;
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
/* 436 */       return new String(this.data, encoding);
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
/* 449 */         JAXBContext context = JAXBContext.newInstance(new Class[] { cls });
/* 450 */         Unmarshaller um = context.createUnmarshaller();
/* 451 */         return (T)um.unmarshal(new ByteArrayInputStream(this.data));
/* 452 */       } catch (JAXBException e) {
/* 453 */         throw new IOException(e);
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
/* 466 */       Closer closer = Closer.create();
/* 467 */       file.getParentFile().mkdirs();
/*     */       
/*     */       try {
/* 470 */         FileOutputStream fos = (FileOutputStream)closer.register(new FileOutputStream(file));
/* 471 */         BufferedOutputStream bos = (BufferedOutputStream)closer.register(new BufferedOutputStream(fos));
/*     */         
/* 473 */         saveContent(bos);
/*     */       } finally {
/* 475 */         closer.close();
/*     */       } 
/*     */       
/* 478 */       return this;
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
/* 490 */       out.write(this.data);
/*     */       
/* 492 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\net\HttpRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */