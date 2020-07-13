/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bug;
/*     */ import bxf;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import net.eq2online.macros.AutoDiscoveryAgent;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.GuiControl;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*     */ import net.eq2online.macros.res.ResourceLocations;
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
/*     */ public class GuiAutoDiscoverStatus
/*     */   extends GuiScreenEx
/*     */ {
/*     */   private AutoDiscoveryAgent parent;
/*  30 */   private int throbberIndex = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean failed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiAutoDiscoverStatus(AutoDiscoveryAgent parent) {
/*  45 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyFailed() {
/*  53 */     this.failed = true;
/*  54 */     b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/*  60 */     clearControlList();
/*     */     
/*  62 */     if (this.failed) {
/*     */       
/*  64 */       addControl(new GuiControl(1, this.l / 2 - 40, this.m / 2, 80, 20, "Close"));
/*  65 */       addControl(new GuiControl(2, this.l / 2 - 92, this.m / 2 + 30, 100, 20, LocalisationProvider.getLocalisedString("gui.options")));
/*  66 */       addControl(new GuiControl(3, this.l / 2 + 10, this.m / 2 + 30, 80, 20, LocalisationProvider.getLocalisedString("gui.refresh")));
/*     */     }
/*     */     else {
/*     */       
/*  70 */       addControl(new GuiControl(1, this.l / 2 - 40, this.m / 2, 80, 20, LocalisationProvider.getLocalisedString("gui.cancel")));
/*     */     } 
/*     */     
/*  73 */     super.b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void m() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/*  84 */     this.throbberIndex++;
/*  85 */     if (this.throbberIndex >= 27) this.throbberIndex = 0;
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) {
/*  91 */     if (keyCode == 1)
/*     */     {
/*  93 */       if (this.failed) { this.parent.close(); } else { this.parent.cancel(); }
/*     */     
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(bug guibutton) {
/* 100 */     if (guibutton != null) {
/*     */       
/* 102 */       if (guibutton.k == 1)
/*     */       {
/* 104 */         if (this.failed) { this.parent.close(); } else { this.parent.cancel(); }
/*     */       
/*     */       }
/* 107 */       if (guibutton.k == 2)
/*     */       {
/* 109 */         AbstractionLayer.displayGuiScreen((bxf)new GuiMacroConfig(this, true));
/*     */       }
/*     */       
/* 112 */       if (guibutton.k == 3)
/*     */       {
/* 114 */         this.parent.retry();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float partialTicks) {
/* 123 */     a(0, 0, this.l, this.m, -2147483648);
/* 124 */     GL.glDisableBlend();
/*     */ 
/*     */     
/* 127 */     int t = this.throbberIndex / 3;
/*     */     
/* 129 */     if (this.failed) {
/*     */       
/* 131 */       a(AbstractionLayer.getFontRenderer(), LocalisationProvider.getLocalisedString("query.failed"), this.l / 2, this.m / 2 - 20, -1);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 136 */       drawTexturedModalRect(ResourceLocations.MAIN, this.l / 2 - 25, this.m / 2 - 30, this.l / 2 + 26, this.m / 2 - 25, 0, t * 10, 102, t * 10 + 10);
/*     */       
/* 138 */       String pleaseWait = LocalisationProvider.getLocalisedString("query.wait");
/* 139 */       int stringWidth = AbstractionLayer.getFontRenderer().a(pleaseWait);
/* 140 */       c(AbstractionLayer.getFontRenderer(), pleaseWait, this.l / 2 - stringWidth / 2, this.m / 2 - 20, -1);
/*     */     } 
/*     */ 
/*     */     
/* 144 */     super.a(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\screens\GuiAutoDiscoverStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */