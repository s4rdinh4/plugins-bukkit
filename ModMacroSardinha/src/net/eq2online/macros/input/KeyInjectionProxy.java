/*    */ package net.eq2online.macros.input;
/*    */ 
/*    */ import aqu;
/*    */ import bsu;
/*    */ import cio;
/*    */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*    */ 
/*    */ 
/*    */ public class KeyInjectionProxy
/*    */   extends cio
/*    */ {
/*    */   private InputHandler handler;
/*    */   private bsu minecraft;
/*    */   private cio oldPlayer;
/*    */   
/*    */   public KeyInjectionProxy(InputHandler handler, bsu minecraft) {
/* 17 */     super(minecraft, (aqu)minecraft.f, minecraft.h.a, minecraft.h.x());
/*    */     
/* 19 */     this.handler = handler;
/* 20 */     this.minecraft = minecraft;
/* 21 */     this.oldPlayer = AbstractionLayer.getPlayer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean aj() {
/* 30 */     this.minecraft.h = this.oldPlayer;
/* 31 */     this.oldPlayer = null;
/* 32 */     J();
/*    */     
/* 34 */     this.handler.processBuffers(this.minecraft, true, true, false);
/* 35 */     return AbstractionLayer.getPlayer().aj();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void s_() {
/* 44 */     if (this.oldPlayer != null)
/*    */     {
/* 46 */       this.oldPlayer.s_();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\input\KeyInjectionProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */