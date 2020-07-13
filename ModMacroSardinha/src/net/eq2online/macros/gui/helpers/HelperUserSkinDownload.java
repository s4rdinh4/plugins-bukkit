/*     */ package net.eq2online.macros.gui.helpers;
/*     */ 
/*     */ import bst;
/*     */ import bsu;
/*     */ import cil;
/*     */ import ctp;
/*     */ import ctq;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.IconTiled;
/*     */ import net.eq2online.macros.compatibility.PrivateFields;
/*     */ import net.eq2online.macros.interfaces.ISaveSettings;
/*     */ import net.eq2online.macros.interfaces.ISettingsProvider;
/*     */ import oa;
/*     */ import org.lwjgl.opengl.GL11;
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
/*     */ public class HelperUserSkinDownload
/*     */   implements ISaveSettings
/*     */ {
/*  44 */   public static int USER_FACE_IMAGE_SIZE = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   protected static int BYTES_PER_PIXEL = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean enableSkinDownload = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private Map<String, ctq> downloadingSkins = new HashMap<String, ctq>();
/*     */   
/*  66 */   private Map<String, oa> skinResources = new HashMap<String, oa>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private String[] users = new String[1024];
/*     */   
/*  73 */   private IconTiled[] icons = new IconTiled[1024];
/*     */ 
/*     */ 
/*     */   
/*     */   private ByteBuffer bufferImageData;
/*     */ 
/*     */ 
/*     */   
/*     */   private oa textureLocation;
/*     */ 
/*     */ 
/*     */   
/*     */   private ctp userFaceTexture;
/*     */ 
/*     */   
/*     */   private bsu minecraft;
/*     */ 
/*     */ 
/*     */   
/*     */   public HelperUserSkinDownload(bsu minecraft, oa baseTexture) {
/*  93 */     this.minecraft = minecraft;
/*     */     
/*  95 */     this.textureLocation = baseTexture;
/*     */ 
/*     */     
/*  98 */     this.bufferImageData = bst.c(USER_FACE_IMAGE_SIZE * USER_FACE_IMAGE_SIZE * BYTES_PER_PIXEL);
/*     */     
/* 100 */     BufferedImage baseImage = null;
/*     */     
/*     */     try {
/* 103 */       baseImage = ImageIO.read(this.minecraft.O().a(this.textureLocation).b());
/*     */     } catch (Exception ex) {
/* 105 */       ex.printStackTrace();
/*     */     } 
/* 107 */     if (baseImage != null) {
/*     */       
/* 109 */       this.userFaceTexture = new ctp(baseImage);
/* 110 */       this.minecraft.N().a("userfaces", this.userFaceTexture);
/*     */       
/* 112 */       int cols = baseImage.getWidth() / USER_FACE_IMAGE_SIZE;
/*     */       
/* 114 */       for (int id = 0; id < this.icons.length; id++) {
/*     */         
/* 116 */         int uCoord = id % cols * USER_FACE_IMAGE_SIZE;
/* 117 */         int vCoord = id / cols * USER_FACE_IMAGE_SIZE;
/* 118 */         this.icons[id] = new IconTiled(this.textureLocation, id, uCoord, vCoord, USER_FACE_IMAGE_SIZE, USER_FACE_IMAGE_SIZE, baseImage.getWidth(), baseImage.getHeight());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTextureName() {
/* 129 */     return this.userFaceTexture.b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTick() {
/* 137 */     for (Iterator<Map.Entry<String, ctq>> downloadingSkinsIterator = this.downloadingSkins.entrySet().iterator(); downloadingSkinsIterator.hasNext(); ) {
/*     */       
/* 139 */       Map.Entry<String, ctq> data = downloadingSkinsIterator.next();
/*     */       
/* 141 */       ctq value = data.getValue();
/* 142 */       if (storeSkin(data.getKey(), value))
/*     */       {
/* 144 */         downloadingSkinsIterator.remove();
/*     */       }
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
/*     */   protected boolean storeSkin(String username, ctq imageDownload) {
/* 158 */     BufferedImage userSkin = (BufferedImage)PrivateFields.downloadedImage.get(imageDownload);
/*     */     
/* 160 */     if (userSkin == null) return false;
/*     */ 
/*     */     
/* 163 */     int freeslot = getFreeSlot();
/*     */ 
/*     */     
/* 166 */     if (freeslot > 0) {
/*     */ 
/*     */       
/* 169 */       this.users[freeslot] = username;
/*     */ 
/*     */       
/* 172 */       int[] playerFaceData = new int[64];
/* 173 */       userSkin.getRGB(8, 8, 8, 8, playerFaceData, 0, 8);
/*     */ 
/*     */       
/* 176 */       int[] playerHelmData = new int[64];
/* 177 */       userSkin.getRGB(40, 8, 8, 8, playerHelmData, 0, 8);
/*     */ 
/*     */       
/* 180 */       this.bufferImageData.clear();
/*     */ 
/*     */       
/* 183 */       for (int pixelIndex = 0; pixelIndex < 64; pixelIndex++) {
/*     */ 
/*     */         
/* 186 */         float hAlpha = (playerHelmData[pixelIndex] >> 24 & 0xFF) / 255.0F;
/* 187 */         float hAlphaD = 1.0F - hAlpha;
/*     */ 
/*     */         
/* 190 */         float R = (playerFaceData[pixelIndex] >> 16 & 0xFF) * hAlphaD + (playerHelmData[pixelIndex] >> 16 & 0xFF) * hAlpha;
/* 191 */         float G = (playerFaceData[pixelIndex] >> 8 & 0xFF) * hAlphaD + (playerHelmData[pixelIndex] >> 8 & 0xFF) * hAlpha;
/* 192 */         float B = (playerFaceData[pixelIndex] >> 0 & 0xFF) * hAlphaD + (playerHelmData[pixelIndex] >> 0 & 0xFF) * hAlpha;
/*     */ 
/*     */         
/* 195 */         this.bufferImageData.put((byte)(int)R);
/* 196 */         this.bufferImageData.put((byte)(int)G);
/* 197 */         this.bufferImageData.put((byte)(int)B);
/* 198 */         this.bufferImageData.put((byte)-1);
/*     */       } 
/*     */ 
/*     */       
/* 202 */       this.bufferImageData.flip();
/*     */ 
/*     */       
/* 205 */       AbstractionLayer.bindTexture(this.textureLocation);
/* 206 */       GL11.glTexSubImage2D(3553, 0, freeslot % 32 * 8, freeslot / 32 * 8, 8, 8, 6408, 5121, this.bufferImageData);
/*     */     } 
/*     */     
/* 209 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void __displayTexture() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFreeSlot() {
/* 227 */     for (int slot = 1; slot < this.users.length; slot++) {
/* 228 */       if (this.users[slot] == null) return slot; 
/*     */     } 
/* 230 */     return 0;
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
/*     */   public IconTiled getIconForSkin(String username) {
/* 243 */     for (int i = 0; i < this.users.length; i++) {
/*     */       
/* 245 */       if (this.users[i] != null && this.users[i].equals(username)) {
/* 246 */         return this.icons[i];
/*     */       }
/*     */     } 
/*     */     
/* 250 */     if (!this.downloadingSkins.containsKey(username))
/*     */     {
/* 252 */       beginDownloadingSkin(username);
/*     */     }
/*     */     
/* 255 */     return this.icons[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasUserSkin(String username) {
/* 266 */     for (int i = 0; i < this.users.length; i++) {
/*     */       
/* 268 */       if (this.users[i] != null && this.users[i].equals(username)) {
/* 269 */         return true;
/*     */       }
/*     */     } 
/* 272 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDownloadingUserSkin(String username) {
/* 283 */     return this.downloadingSkins.containsKey(username);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addUser(String username) {
/* 294 */     if (!hasUserSkin(username) && !isDownloadingUserSkin(username))
/*     */     {
/* 296 */       beginDownloadingSkin(username);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void beginDownloadingSkin(String username) {
/* 307 */     if (enableSkinDownload) {
/*     */       
/* 309 */       oa skinResource = cil.c(username);
/* 310 */       ctq skinTexture = cil.a(skinResource, username);
/*     */       
/* 312 */       if (skinTexture != null) {
/*     */         
/* 314 */         this.skinResources.put(username, skinResource);
/* 315 */         this.downloadingSkins.put(username, skinTexture);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifySettingsCleared() {
/* 323 */     enableSkinDownload = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifySettingsLoaded(ISettingsProvider settings) {
/* 330 */     enableSkinDownload = settings.getSetting("skin.download.enabled", true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveSettings(ISettingsProvider settings) {
/* 337 */     settings.setSetting("skin.download.enabled", enableSkinDownload);
/*     */ 
/*     */     
/* 340 */     settings.setSettingComment("skin.download.enabled", "Enable skin downloads for the online user list. Disable this to give all users the 'Steve' skin in the online user list");
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\helpers\HelperUserSkinDownload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */