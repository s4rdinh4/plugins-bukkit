/*    */ package net.eq2online.macros.gui.list;
/*    */ 
/*    */ import bsu;
/*    */ import civ;
/*    */ import ckx;
/*    */ import com.mumfrey.liteloader.gl.GL;
/*    */ import ctp;
/*    */ import cug;
/*    */ import cvo;
/*    */ import cvs;
/*    */ import java.io.IOException;
/*    */ import oa;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ListObjectTexturePack
/*    */   extends ListObjectGeneric
/*    */ {
/*    */   private oa defaultTexturePackIcon;
/*    */   private cvo resourcePackRepository;
/*    */   private cvs texturePack;
/*    */   
/*    */   public ListObjectTexturePack(int id, String text, cvs texturePack) {
/* 25 */     super(id, text, texturePack);
/*    */     
/* 27 */     this.texturePack = texturePack;
/* 28 */     this.resourcePackRepository = bsu.z().P();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean renderIcon(bsu minecraft, int xPosition, int yPosition) {
/* 34 */     cug textureManager = minecraft.N();
/*    */     
/* 36 */     if (this.texturePack == null) {
/*    */       
/* 38 */       if (this.defaultTexturePackIcon == null) {
/*    */         
/*    */         try {
/*    */           
/* 42 */           this.defaultTexturePackIcon = textureManager.a("texturepackicon", new ctp(this.resourcePackRepository.a.a()));
/*    */         }
/* 44 */         catch (IOException iOException) {}
/*    */       }
/*    */       
/* 47 */       textureManager.a(this.defaultTexturePackIcon);
/*    */     }
/*    */     else {
/*    */       
/* 51 */       this.texturePack.a(textureManager);
/*    */     } 
/*    */     
/* 54 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 55 */     ckx tessellator = ckx.a();
/* 56 */     civ worldRender = tessellator.c();
/* 57 */     worldRender.b();
/* 58 */     worldRender.c(16777215);
/* 59 */     worldRender.a(xPosition, (yPosition + 16), 0.0D, 0.0D, 1.0D);
/* 60 */     worldRender.a((xPosition + 16), (yPosition + 16), 0.0D, 1.0D, 1.0D);
/* 61 */     worldRender.a((xPosition + 16), yPosition, 0.0D, 1.0D, 0.0D);
/* 62 */     worldRender.a(xPosition, yPosition, 0.0D, 0.0D, 0.0D);
/* 63 */     tessellator.b();
/*    */     
/* 65 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\list\ListObjectTexturePack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */