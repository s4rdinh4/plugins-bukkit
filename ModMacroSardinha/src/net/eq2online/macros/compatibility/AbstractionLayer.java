/*     */ package net.eq2online.macros.compatibility;
/*     */ 
/*     */ import ahd;
/*     */ import bsu;
/*     */ import bto;
/*     */ import bty;
/*     */ import btz;
/*     */ import bxf;
/*     */ import cem;
/*     */ import cen;
/*     */ import cio;
/*     */ import ckx;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import cug;
/*     */ import cye;
/*     */ import cyk;
/*     */ import ho;
/*     */ import hy;
/*     */ import net.eq2online.macros.scripting.SoundEffect;
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
/*     */ public final class AbstractionLayer
/*     */ {
/*     */   public static bty getFontRenderer() {
/*  34 */     return (bsu.z()).k;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static cug getTextureManager() {
/*  42 */     return bsu.z().N();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static bto getGameSettings() {
/*  50 */     return (bsu.z()).t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void bindTexture(oa textureName) {
/*  60 */     getTextureManager().a(textureName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void bindTexture(int glTextureName) {
/*  70 */     GL.glBindTexture2D(glTextureName);
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
/*     */   public static void addChatMessage(String message) {
/*  82 */     (bsu.z()).q.d().a((ho)new hy(message));
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
/*     */   public static void playSoundFX(oa sound, float soundVolume, float soundPitch) {
/*  96 */     bsu.z().U().a((cye)new SoundEffect(sound, soundVolume, soundPitch));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void displayGuiScreen(bxf guiscreen) {
/* 106 */     bsu.z().a(guiscreen);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getChatAllowedCharacters() {
/* 116 */     return ChatAllowedCharacters.allowedCharacters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ckx getTessellator() {
/* 124 */     return ckx.a();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static cio getPlayer() {
/* 134 */     return (bsu.z()).h;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ahd getPlayerMP() {
/* 139 */     bsu mc = bsu.z();
/* 140 */     cyk server = mc.F();
/*     */     
/* 142 */     if (server != null)
/*     */     {
/* 144 */       return (ahd)server.an().a(server.R());
/*     */     }
/*     */     
/* 147 */     return (ahd)getPlayer();
/*     */   }
/*     */ 
/*     */   
/*     */   public static cem getPlayerController() {
/* 152 */     return (bsu.z()).c;
/*     */   }
/*     */ 
/*     */   
/*     */   public static cen getWorld() {
/* 157 */     return (bsu.z()).f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static btz getIngameGui() {
/* 165 */     return (bsu.z()).q;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\compatibility\AbstractionLayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */