/*     */ package net.eq2online.macros.gui.helpers;
/*     */ 
/*     */ import bsu;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import ctp;
/*     */ import cug;
/*     */ import cuj;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.nio.ByteBuffer;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import oa;
/*     */ import org.lwjgl.BufferUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HelperThumbnailImage
/*     */ {
/*  29 */   protected static int BYTES_PER_PIXEL = 3;
/*     */ 
/*     */ 
/*     */   
/*     */   protected oa sourceResource;
/*     */ 
/*     */ 
/*     */   
/*     */   protected oa dynamicResource;
/*     */ 
/*     */ 
/*     */   
/*     */   protected ctp texture;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int thumbnailSize;
/*     */ 
/*     */   
/*  48 */   private int editingIconIndex = -1;
/*     */ 
/*     */ 
/*     */   
/*     */   private bsu minecraft;
/*     */ 
/*     */ 
/*     */   
/*     */   private int[] textureData;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HelperThumbnailImage(bsu minecraft, String dynamicResourceName, oa imageResource, int thumbnailSize) {
/*  62 */     this.minecraft = minecraft;
/*     */     
/*  64 */     this.sourceResource = imageResource;
/*  65 */     this.thumbnailSize = thumbnailSize;
/*     */     
/*  67 */     BufferedImage bufferedImage = null;
/*     */     
/*     */     try {
/*  70 */       bufferedImage = ImageIO.read(this.minecraft.O().a(this.sourceResource).b());
/*     */     } catch (Exception ex) {
/*  72 */       ex.printStackTrace();
/*     */     } 
/*  74 */     this.texture = new ctp(bufferedImage);
/*  75 */     this.textureData = this.texture.e();
/*     */     
/*  77 */     cug textureManager = minecraft.N();
/*  78 */     this.dynamicResource = textureManager.a(dynamicResourceName, this.texture);
/*     */   }
/*     */ 
/*     */   
/*     */   public oa getDynamicResource() {
/*  83 */     return this.dynamicResource;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prepareCapture(int iconIndex) {
/*  93 */     this.editingIconIndex = iconIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void captureNow(int displayWidth, int displayHeight) {
/* 104 */     if (this.editingIconIndex > -1) {
/*     */       
/* 106 */       int frameSize = (int)Math.min(0.75F * displayWidth, 0.75F * displayHeight);
/* 107 */       int x = (displayWidth - frameSize) / 2;
/* 108 */       int y = (displayHeight - frameSize) / 2;
/* 109 */       int width = displayWidth - x * 2;
/* 110 */       int height = displayHeight - y * 2;
/*     */       
/* 112 */       BufferedImage resourceImage = new BufferedImage(256, 256, 1);
/* 113 */       resourceImage.setRGB(0, 0, 256, 256, this.textureData, 0, 256);
/*     */ 
/*     */       
/* 116 */       BufferedImage capturedImage = captureRegion(x, y, width, height);
/*     */       
/* 118 */       if (capturedImage != null) {
/*     */         
/* 120 */         int xOffset = this.editingIconIndex % this.thumbnailSize * this.thumbnailSize;
/* 121 */         int yOffset = this.editingIconIndex / this.thumbnailSize * this.thumbnailSize;
/*     */         
/* 123 */         Graphics2D resourceGraphicsContext = resourceImage.createGraphics();
/* 124 */         resourceGraphicsContext.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/* 125 */         resourceGraphicsContext.drawImage(capturedImage, xOffset, yOffset, this.thumbnailSize, this.thumbnailSize, null);
/* 126 */         resourceGraphicsContext.dispose();
/*     */ 
/*     */         
/*     */         try {
/* 130 */           String path = this.sourceResource.a();
/* 131 */           path = path.substring(path.indexOf('/') + 1);
/*     */           
/* 133 */           File imageFile = new File(MacroModCore.getMacrosDirectory(), path);
/* 134 */           File imageFileDir = new File(imageFile.getParent());
/*     */           
/* 136 */           if (!imageFileDir.exists()) {
/* 137 */             imageFileDir.mkdirs();
/*     */           }
/* 139 */           ImageIO.write(resourceImage, "png", imageFile);
/* 140 */           int[] outData = new int[this.textureData.length];
/* 141 */           resourceImage.getRGB(0, 0, 256, 256, outData, 0, 256);
/*     */           
/* 143 */           for (int i = 0; i < outData.length; i++)
/*     */           {
/* 145 */             this.textureData[i] = outData[i];
/*     */           }
/*     */           
/* 148 */           cuj.a(this.texture.b(), this.textureData, 256, 256);
/*     */         }
/* 150 */         catch (Exception ex) {
/*     */           
/* 152 */           AbstractionLayer.addChatMessage("§cThumbnail image capture failed");
/* 153 */           Log.printStackTrace(ex);
/*     */         } 
/*     */       } 
/*     */       
/* 157 */       this.editingIconIndex = -1;
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
/*     */ 
/*     */   
/*     */   public static BufferedImage captureRegion(int x, int y, int width, int height) {
/* 172 */     ByteBuffer captureBuffer = BufferUtils.createByteBuffer(width * height * BYTES_PER_PIXEL);
/*     */     
/* 174 */     byte[] pixelData = new byte[width * height * 3];
/* 175 */     int[] imageData = new int[width * height];
/*     */ 
/*     */     
/*     */     try {
/* 179 */       GL.glPixelStorei(3333, 1);
/* 180 */       GL.glPixelStorei(3317, 1);
/*     */       
/* 182 */       captureBuffer.clear();
/*     */       
/* 184 */       GL.glReadPixels(x, y, width, height, 6407, 5121, captureBuffer);
/*     */       
/* 186 */       captureBuffer.clear();
/* 187 */       captureBuffer.get(pixelData);
/*     */       
/* 189 */       for (int xPos = 0; xPos < width; xPos++) {
/*     */         
/* 191 */         for (int yPos = 0; yPos < height; yPos++) {
/*     */           
/* 193 */           int pixelIndex = xPos + (height - yPos - 1) * width;
/* 194 */           int R = pixelData[pixelIndex * BYTES_PER_PIXEL + 0] & 0xFF;
/* 195 */           int G = pixelData[pixelIndex * BYTES_PER_PIXEL + 1] & 0xFF;
/* 196 */           int B = pixelData[pixelIndex * BYTES_PER_PIXEL + 2] & 0xFF;
/* 197 */           imageData[xPos + yPos * width] = 0xFF000000 | R << 16 | G << 8 | B;
/*     */         } 
/*     */       } 
/*     */       
/* 201 */       BufferedImage capturedImage = new BufferedImage(width, height, 1);
/* 202 */       capturedImage.setRGB(0, 0, width, height, imageData, 0, width);
/*     */       
/* 204 */       return capturedImage;
/*     */     }
/* 206 */     catch (Exception exception) {
/*     */       
/* 208 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\helpers\HelperThumbnailImage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */