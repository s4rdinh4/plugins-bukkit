/*     */ package org.bouncycastle.util.encoders;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public class Base64
/*     */ {
/*   9 */   private static final Encoder encoder = new Base64Encoder();
/*     */ 
/*     */   
/*     */   public static String toBase64String(byte[] data) {
/*  13 */     return toBase64String(data, 0, data.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toBase64String(byte[] data, int off, int length) {
/*  18 */     byte[] encoded = encode(data, off, length);
/*  19 */     return new String(asCharArray(encoded));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static char[] asCharArray(byte[] bytes) {
/*  30 */     char[] chars = new char[bytes.length];
/*     */     
/*  32 */     for (int i = 0; i != chars.length; i++)
/*     */     {
/*  34 */       chars[i] = (char)(bytes[i] & 0xFF);
/*     */     }
/*     */     
/*  37 */     return chars;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] encode(byte[] data) {
/*  47 */     return encode(data, 0, data.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] encode(byte[] data, int off, int length) {
/*  57 */     int len = (length + 2) / 3 * 4;
/*  58 */     ByteArrayOutputStream bOut = new ByteArrayOutputStream(len);
/*     */ 
/*     */     
/*     */     try {
/*  62 */       encoder.encode(data, off, length, bOut);
/*     */     }
/*  64 */     catch (Exception e) {
/*     */       
/*  66 */       throw new RuntimeException("exception encoding base64 string: " + e.getMessage(), e);
/*     */     } 
/*     */     
/*  69 */     return bOut.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int encode(byte[] data, OutputStream out) throws IOException {
/*  79 */     return encoder.encode(data, 0, data.length, out);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int encode(byte[] data, int off, int length, OutputStream out) throws IOException {
/*  89 */     return encoder.encode(data, off, length, out);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] decode(byte[] data) {
/*  99 */     int len = data.length / 4 * 3;
/* 100 */     ByteArrayOutputStream bOut = new ByteArrayOutputStream(len);
/*     */ 
/*     */     
/*     */     try {
/* 104 */       encoder.decode(data, 0, data.length, bOut);
/*     */     }
/* 106 */     catch (Exception e) {
/*     */       
/* 108 */       throw new RuntimeException("unable to decode base64 data: " + e.getMessage(), e);
/*     */     } 
/*     */     
/* 111 */     return bOut.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] decode(String data) {
/* 121 */     int len = data.length() / 4 * 3;
/* 122 */     ByteArrayOutputStream bOut = new ByteArrayOutputStream(len);
/*     */ 
/*     */     
/*     */     try {
/* 126 */       encoder.decode(data, bOut);
/*     */     }
/* 128 */     catch (Exception e) {
/*     */       
/* 130 */       throw new RuntimeException("unable to decode base64 string: " + e.getMessage(), e);
/*     */     } 
/*     */     
/* 133 */     return bOut.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int decode(String data, OutputStream out) throws IOException {
/* 144 */     return encoder.decode(data, out);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\org\bouncycastl\\util\encoders\Base64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */