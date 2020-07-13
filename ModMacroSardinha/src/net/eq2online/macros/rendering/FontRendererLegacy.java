/*     */ package net.eq2online.macros.rendering;
/*     */ 
/*     */ import bst;
/*     */ import bto;
/*     */ import bty;
/*     */ import civ;
/*     */ import ckx;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import cug;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Random;
/*     */ import net.eq2online.macros.compatibility.ChatAllowedCharacters;
/*     */ import oa;
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
/*     */ public class FontRendererLegacy
/*     */   extends bty
/*     */ {
/*  28 */   protected int[] d = new int[] { 1, 9, 9, 8, 8, 8, 8, 7, 9, 8, 9, 9, 8, 9, 9, 9, 8, 8, 8, 8, 9, 9, 8, 9, 8, 8, 8, 8, 8, 9, 9, 9, 4, 2, 5, 6, 6, 6, 6, 3, 5, 5, 5, 6, 2, 6, 2, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 2, 2, 5, 6, 5, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 4, 6, 6, 3, 6, 6, 6, 6, 6, 5, 6, 6, 2, 6, 5, 3, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6, 5, 2, 5, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 3, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 3, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 2, 6, 6, 8, 9, 9, 6, 6, 6, 8, 8, 6, 8, 8, 8, 8, 8, 6, 6, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 6, 9, 9, 9, 5, 9, 9, 8, 7, 7, 8, 7, 8, 8, 8, 7, 8, 8, 7, 9, 9, 6, 7, 7, 7, 7, 7, 9, 6, 7, 8, 7, 6, 6, 9, 7, 6, 7, 1 };
/*     */ 
/*     */   
/*     */   public int lineHeight;
/*     */   
/*     */   protected int fontDisplayLists;
/*     */   
/*     */   protected IntBuffer buffer;
/*     */   
/*     */   public Random rng;
/*     */   
/*     */   private cug renderEngine;
/*     */   
/*     */   private oa fontTexture;
/*     */ 
/*     */   
/*     */   public FontRendererLegacy(bto gamesettings, oa fontTexture, cug renderEngine) {
/*  45 */     super(gamesettings, fontTexture, renderEngine, false);
/*     */     
/*  47 */     this.fontTexture = fontTexture;
/*  48 */     this.renderEngine = renderEngine;
/*  49 */     this.lineHeight = 8;
/*  50 */     this.buffer = bst.f(1024);
/*  51 */     this.rng = new Random();
/*     */     
/*  53 */     this.fontDisplayLists = bst.a(288);
/*     */     
/*  55 */     ckx tessellator = ckx.a();
/*  56 */     civ worldRender = tessellator.c();
/*     */     
/*  58 */     for (int index = 0; index < 256; index++) {
/*     */       
/*  60 */       GL.glNewList(this.fontDisplayLists + index, 4864);
/*  61 */       GL.glEnableBlend();
/*  62 */       GL.glBlendFunc(770, 771);
/*     */       
/*  64 */       worldRender.b();
/*     */       
/*  66 */       int xPos = index % 16 * 8;
/*  67 */       int yPos = index / 16 * 8;
/*     */       
/*  69 */       float f = 7.99F;
/*  70 */       float f1 = 0.0F;
/*  71 */       float f2 = 0.0F;
/*     */       
/*  73 */       worldRender.a(0.0D, (0.0F + f), 0.0D, (xPos / 128.0F + f1), ((yPos + f) / 128.0F + f2));
/*  74 */       worldRender.a((0.0F + f), (0.0F + f), 0.0D, ((xPos + f) / 128.0F + f1), ((yPos + f) / 128.0F + f2));
/*  75 */       worldRender.a((0.0F + f), 0.0D, 0.0D, ((xPos + f) / 128.0F + f1), (yPos / 128.0F + f2));
/*  76 */       worldRender.a(0.0D, 0.0D, 0.0D, (xPos / 128.0F + f1), (yPos / 128.0F + f2));
/*  77 */       tessellator.b();
/*     */       
/*  79 */       GL.glTranslatef(this.d[index], 0.0F, 0.0F);
/*  80 */       GL.glDisableBlend();
/*  81 */       GL.glEndList();
/*     */     } 
/*     */     
/*  84 */     for (int colourCode = 0; colourCode < 32; colourCode++) {
/*     */       
/*  86 */       int i2 = (colourCode >> 3 & 0x1) * 85;
/*  87 */       int red = (colourCode >> 2 & 0x1) * 170 + i2;
/*  88 */       int green = (colourCode >> 1 & 0x1) * 170 + i2;
/*  89 */       int blue = (colourCode >> 0 & 0x1) * 170 + i2;
/*     */       
/*  91 */       if (colourCode == 6)
/*     */       {
/*  93 */         red += 85;
/*     */       }
/*     */       
/*  96 */       boolean bright = (colourCode >= 16);
/*     */       
/*  98 */       if (bright) {
/*     */         
/* 100 */         red /= 4;
/* 101 */         green /= 4;
/* 102 */         blue /= 4;
/*     */       } 
/*     */       
/* 105 */       GL.glNewList(this.fontDisplayLists + 256 + colourCode, 4864);
/* 106 */       GL.glColor3f(red / 255.0F, green / 255.0F, blue / 255.0F);
/* 107 */       GL.glEndList();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int a(String string, float xPos, float yPos, int colour) {
/* 114 */     b(string, xPos + 1.0F, yPos + 1.0F, colour, true);
/* 115 */     a(string, xPos, yPos, colour, false);
/* 116 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int a(String string, float xPos, float yPos, int colour, boolean shadow) {
/* 122 */     return b(string, xPos, yPos, colour, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int b(String string, float xPos, float yPos, int colour, boolean shadow) {
/* 128 */     if (string == null)
/*     */     {
/* 130 */       return 0;
/*     */     }
/* 132 */     boolean flag1 = false;
/* 133 */     if (shadow) {
/*     */       
/* 135 */       int l = colour & 0xFF000000;
/* 136 */       colour = (colour & 0xFCFCFC) >> 2;
/* 137 */       colour += l;
/*     */     } 
/* 139 */     this.renderEngine.a(this.fontTexture);
/* 140 */     float f = (colour >> 16 & 0xFF) / 255.0F;
/* 141 */     float f1 = (colour >> 8 & 0xFF) / 255.0F;
/* 142 */     float f2 = (colour & 0xFF) / 255.0F;
/* 143 */     float f3 = (colour >> 24 & 0xFF) / 255.0F;
/* 144 */     if (f3 == 0.0F)
/*     */     {
/* 146 */       f3 = 1.0F;
/*     */     }
/*     */     
/* 149 */     GL.glColor4f(f, f1, f2, f3);
/*     */     
/* 151 */     this.buffer.clear();
/*     */     
/* 153 */     GL.glPushMatrix();
/* 154 */     GL.glTranslatef(xPos, yPos, 0.0F);
/*     */     
/* 156 */     for (int i1 = 0; i1 < string.length(); i1++) {
/*     */       
/* 158 */       for (; string.length() > i1 + 1 && string.charAt(i1) == '§'; i1 += 2) {
/*     */         
/* 160 */         char c = string.toLowerCase().charAt(i1 + 1);
/* 161 */         if (c == 'k') {
/*     */           
/* 163 */           flag1 = true;
/*     */         } else {
/*     */           
/* 166 */           flag1 = false;
/* 167 */           int k1 = "0123456789abcdef".indexOf(c);
/* 168 */           if (k1 < 0 || k1 > 15)
/*     */           {
/* 170 */             k1 = 15;
/*     */           }
/* 172 */           this.buffer.put(this.fontDisplayLists + 256 + k1 + (shadow ? 16 : 0));
/* 173 */           if (this.buffer.remaining() == 0) {
/*     */             
/* 175 */             this.buffer.flip();
/* 176 */             GL.glCallLists(this.buffer);
/* 177 */             this.buffer.clear();
/*     */           } 
/*     */         } 
/*     */       } 
/* 181 */       if (i1 < string.length()) {
/*     */         
/* 183 */         int j1 = ChatAllowedCharacters.allowedCharacters.indexOf(string.charAt(i1));
/* 184 */         if (j1 >= 0)
/*     */         {
/* 186 */           if (flag1)
/*     */           
/* 188 */           { int l1 = 0;
/*     */             
/*     */             while (true) {
/* 191 */               l1 = this.rng.nextInt(ChatAllowedCharacters.allowedCharacters.length());
/* 192 */               if (this.d[j1 + 32] == this.d[l1 + 32]) {
/* 193 */                 this.buffer.put(this.fontDisplayLists + 256 + this.rng.nextInt(2) + 8 + (shadow ? 16 : 0));
/* 194 */                 this.buffer.put(this.fontDisplayLists + l1 + 32); break;
/*     */               } 
/*     */             }  }
/* 197 */           else { this.buffer.put(this.fontDisplayLists + j1 + 32); }
/*     */         
/*     */         }
/*     */       } 
/* 201 */       if (this.buffer.remaining() == 0) {
/*     */         
/* 203 */         this.buffer.flip();
/* 204 */         GL.glCallLists(this.buffer);
/* 205 */         this.buffer.clear();
/*     */       } 
/*     */     } 
/*     */     
/* 209 */     this.buffer.flip();
/* 210 */     GL.glCallLists(this.buffer);
/* 211 */     GL.glPopMatrix();
/*     */     
/* 213 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int a(String s) {
/* 219 */     if (s == null)
/*     */     {
/* 221 */       return 0;
/*     */     }
/* 223 */     int i = 0;
/* 224 */     for (int j = 0; j < s.length(); j++) {
/*     */       
/* 226 */       if (s.charAt(j) == '§') {
/*     */         
/* 228 */         j++;
/*     */       } else {
/*     */         
/* 231 */         int k = ChatAllowedCharacters.allowedCharacters.indexOf(s.charAt(j));
/* 232 */         if (k >= 0)
/*     */         {
/* 234 */           i += this.d[k + 32];
/*     */         }
/*     */       } 
/*     */     } 
/* 238 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\rendering\FontRendererLegacy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */