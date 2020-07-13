/*     */ package org.bouncycastle.util.encoders;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public class Base64Encoder
/*     */   implements Encoder {
/*   8 */   protected final byte[] encodingTable = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
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
/*  24 */   protected byte padding = 61;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  29 */   protected final byte[] decodingTable = new byte[128];
/*     */   
/*     */   protected void initialiseDecodingTable() {
/*     */     int i;
/*  33 */     for (i = 0; i < this.decodingTable.length; i++)
/*     */     {
/*  35 */       this.decodingTable[i] = -1;
/*     */     }
/*     */     
/*  38 */     for (i = 0; i < this.encodingTable.length; i++)
/*     */     {
/*  40 */       this.decodingTable[this.encodingTable[i]] = (byte)i;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Base64Encoder() {
/*  46 */     initialiseDecodingTable();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int encode(byte[] data, int off, int length, OutputStream out) throws IOException {
/*  57 */     int b1, b2, b3, d1, d2, modulus = length % 3;
/*  58 */     int dataLength = length - modulus;
/*     */ 
/*     */     
/*  61 */     for (int i = off; i < off + dataLength; i += 3) {
/*     */       
/*  63 */       int a1 = data[i] & 0xFF;
/*  64 */       int a2 = data[i + 1] & 0xFF;
/*  65 */       int a3 = data[i + 2] & 0xFF;
/*     */       
/*  67 */       out.write(this.encodingTable[a1 >>> 2 & 0x3F]);
/*  68 */       out.write(this.encodingTable[(a1 << 4 | a2 >>> 4) & 0x3F]);
/*  69 */       out.write(this.encodingTable[(a2 << 2 | a3 >>> 6) & 0x3F]);
/*  70 */       out.write(this.encodingTable[a3 & 0x3F]);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     switch (modulus) {
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/*  84 */         d1 = data[off + dataLength] & 0xFF;
/*  85 */         b1 = d1 >>> 2 & 0x3F;
/*  86 */         b2 = d1 << 4 & 0x3F;
/*     */         
/*  88 */         out.write(this.encodingTable[b1]);
/*  89 */         out.write(this.encodingTable[b2]);
/*  90 */         out.write(this.padding);
/*  91 */         out.write(this.padding);
/*     */         break;
/*     */       case 2:
/*  94 */         d1 = data[off + dataLength] & 0xFF;
/*  95 */         d2 = data[off + dataLength + 1] & 0xFF;
/*     */         
/*  97 */         b1 = d1 >>> 2 & 0x3F;
/*  98 */         b2 = (d1 << 4 | d2 >>> 4) & 0x3F;
/*  99 */         b3 = d2 << 2 & 0x3F;
/*     */         
/* 101 */         out.write(this.encodingTable[b1]);
/* 102 */         out.write(this.encodingTable[b2]);
/* 103 */         out.write(this.encodingTable[b3]);
/* 104 */         out.write(this.padding);
/*     */         break;
/*     */     } 
/*     */     
/* 108 */     return dataLength / 3 * 4 + ((modulus == 0) ? 0 : 4);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean ignore(char c) {
/* 113 */     return (c == '\n' || c == '\r' || c == '\t' || c == ' ');
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
/*     */   public int decode(byte[] data, int off, int length, OutputStream out) throws IOException {
/* 126 */     int outLen = 0;
/*     */     
/* 128 */     int end = off + length;
/*     */     
/* 130 */     while (end > off) {
/*     */       
/* 132 */       if (!ignore((char)data[end - 1])) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 137 */       end--;
/*     */     } 
/*     */     
/* 140 */     int i = off;
/* 141 */     int finish = end - 4;
/*     */     
/* 143 */     i = nextI(data, i, finish);
/*     */     
/* 145 */     while (i < finish) {
/*     */       
/* 147 */       byte b1 = this.decodingTable[data[i++]];
/*     */       
/* 149 */       i = nextI(data, i, finish);
/*     */       
/* 151 */       byte b2 = this.decodingTable[data[i++]];
/*     */       
/* 153 */       i = nextI(data, i, finish);
/*     */       
/* 155 */       byte b3 = this.decodingTable[data[i++]];
/*     */       
/* 157 */       i = nextI(data, i, finish);
/*     */       
/* 159 */       byte b4 = this.decodingTable[data[i++]];
/*     */       
/* 161 */       if ((b1 | b2 | b3 | b4) < 0)
/*     */       {
/* 163 */         throw new IOException("invalid characters encountered in base64 data");
/*     */       }
/*     */       
/* 166 */       out.write(b1 << 2 | b2 >> 4);
/* 167 */       out.write(b2 << 4 | b3 >> 2);
/* 168 */       out.write(b3 << 6 | b4);
/*     */       
/* 170 */       outLen += 3;
/*     */       
/* 172 */       i = nextI(data, i, finish);
/*     */     } 
/*     */     
/* 175 */     outLen += decodeLastBlock(out, (char)data[end - 4], (char)data[end - 3], (char)data[end - 2], (char)data[end - 1]);
/*     */     
/* 177 */     return outLen;
/*     */   }
/*     */ 
/*     */   
/*     */   private int nextI(byte[] data, int i, int finish) {
/* 182 */     while (i < finish && ignore((char)data[i]))
/*     */     {
/* 184 */       i++;
/*     */     }
/* 186 */     return i;
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
/*     */   public int decode(String data, OutputStream out) throws IOException {
/* 199 */     int length = 0;
/*     */     
/* 201 */     int end = data.length();
/*     */     
/* 203 */     while (end > 0) {
/*     */       
/* 205 */       if (!ignore(data.charAt(end - 1))) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 210 */       end--;
/*     */     } 
/*     */     
/* 213 */     int i = 0;
/* 214 */     int finish = end - 4;
/*     */     
/* 216 */     i = nextI(data, i, finish);
/*     */     
/* 218 */     while (i < finish) {
/*     */       
/* 220 */       byte b1 = this.decodingTable[data.charAt(i++)];
/*     */       
/* 222 */       i = nextI(data, i, finish);
/*     */       
/* 224 */       byte b2 = this.decodingTable[data.charAt(i++)];
/*     */       
/* 226 */       i = nextI(data, i, finish);
/*     */       
/* 228 */       byte b3 = this.decodingTable[data.charAt(i++)];
/*     */       
/* 230 */       i = nextI(data, i, finish);
/*     */       
/* 232 */       byte b4 = this.decodingTable[data.charAt(i++)];
/*     */       
/* 234 */       if ((b1 | b2 | b3 | b4) < 0)
/*     */       {
/* 236 */         throw new IOException("invalid characters encountered in base64 data");
/*     */       }
/*     */       
/* 239 */       out.write(b1 << 2 | b2 >> 4);
/* 240 */       out.write(b2 << 4 | b3 >> 2);
/* 241 */       out.write(b3 << 6 | b4);
/*     */       
/* 243 */       length += 3;
/*     */       
/* 245 */       i = nextI(data, i, finish);
/*     */     } 
/*     */     
/* 248 */     length += decodeLastBlock(out, data.charAt(end - 4), data.charAt(end - 3), data.charAt(end - 2), data.charAt(end - 1));
/*     */     
/* 250 */     return length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int decodeLastBlock(OutputStream out, char c1, char c2, char c3, char c4) throws IOException {
/* 257 */     if (c3 == this.padding) {
/*     */       
/* 259 */       byte b5 = this.decodingTable[c1];
/* 260 */       byte b6 = this.decodingTable[c2];
/*     */       
/* 262 */       if ((b5 | b6) < 0)
/*     */       {
/* 264 */         throw new IOException("invalid characters encountered at end of base64 data");
/*     */       }
/*     */       
/* 267 */       out.write(b5 << 2 | b6 >> 4);
/*     */       
/* 269 */       return 1;
/*     */     } 
/* 271 */     if (c4 == this.padding) {
/*     */       
/* 273 */       byte b5 = this.decodingTable[c1];
/* 274 */       byte b6 = this.decodingTable[c2];
/* 275 */       byte b7 = this.decodingTable[c3];
/*     */       
/* 277 */       if ((b5 | b6 | b7) < 0)
/*     */       {
/* 279 */         throw new IOException("invalid characters encountered at end of base64 data");
/*     */       }
/*     */       
/* 282 */       out.write(b5 << 2 | b6 >> 4);
/* 283 */       out.write(b6 << 4 | b7 >> 2);
/*     */       
/* 285 */       return 2;
/*     */     } 
/*     */ 
/*     */     
/* 289 */     byte b1 = this.decodingTable[c1];
/* 290 */     byte b2 = this.decodingTable[c2];
/* 291 */     byte b3 = this.decodingTable[c3];
/* 292 */     byte b4 = this.decodingTable[c4];
/*     */     
/* 294 */     if ((b1 | b2 | b3 | b4) < 0)
/*     */     {
/* 296 */       throw new IOException("invalid characters encountered at end of base64 data");
/*     */     }
/*     */     
/* 299 */     out.write(b1 << 2 | b2 >> 4);
/* 300 */     out.write(b2 << 4 | b3 >> 2);
/* 301 */     out.write(b3 << 6 | b4);
/*     */     
/* 303 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int nextI(String data, int i, int finish) {
/* 309 */     while (i < finish && ignore(data.charAt(i)))
/*     */     {
/* 311 */       i++;
/*     */     }
/* 313 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\org\bouncycastl\\util\encoders\Base64Encoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */