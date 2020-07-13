/*     */ package net.eq2online.macros.gui.dialogs;
/*     */ 
/*     */ import bug;
/*     */ import bul;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.GuiControl;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*     */ import net.eq2online.macros.gui.shared.GuiDialogBox;
/*     */ import net.eq2online.macros.gui.shared.GuiScreenEx;
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
/*     */ public class GuiDialogBoxAddConfig
/*     */   extends GuiDialogBox
/*     */ {
/*     */   private bul txtNewConfigName;
/*     */   private GuiCheckBox chkCopySettings;
/*     */   public String newConfigName;
/*     */   public boolean copySettings;
/*     */   
/*     */   public GuiDialogBoxAddConfig(GuiScreenEx parentScreen) {
/*  52 */     super(parentScreen, 320, 142, LocalisationProvider.getLocalisedString("options.newconfig.title"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/*  58 */     this.txtNewConfigName.a();
/*     */     
/*  60 */     super.e();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(bug guibutton) {
/*  66 */     if (guibutton.k == 0) {
/*     */       
/*  68 */       if (this.j.E()) {
/*     */ 
/*     */         
/*  71 */         this.txtNewConfigName.a("Single Player");
/*     */       
/*     */       }
/*     */       else {
/*     */         
/*  76 */         this.txtNewConfigName.a(MacroModCore.getInstance().getLastServerName());
/*     */       } 
/*     */       
/*  79 */       this.txtNewConfigName.b(true);
/*     */     } 
/*     */     
/*  82 */     super.a(guibutton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSubmit() {
/*  89 */     this.newConfigName = this.txtNewConfigName.b();
/*  90 */     this.copySettings = this.chkCopySettings.checked;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogMouseClicked(int mouseX, int mouseY, int button) throws IOException {
/*  96 */     this.txtNewConfigName.a(mouseX, mouseY, button);
/*  97 */     super.dialogMouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initDialog() {
/* 103 */     this.txtNewConfigName = new bul(0, this.q, this.dialogX + 10, this.dialogY + 36, this.dialogWidth - 20, 16);
/* 104 */     this.txtNewConfigName.f(32);
/* 105 */     this.txtNewConfigName.b(true);
/*     */     
/* 107 */     this.chkCopySettings = new GuiCheckBox(1, this.dialogX + 8, this.dialogY + 90, LocalisationProvider.getLocalisedString("options.newconfig.option"), false);
/*     */     
/* 109 */     addControl(new GuiControl(0, this.dialogX + 8, this.dialogY + 60, 160, 20, LocalisationProvider.getLocalisedString("options.newconfig.prompt2")));
/* 110 */     addControl((GuiControl)this.chkCopySettings);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogKeyTyped(char keyChar, int keyCode) {
/* 116 */     if (keyCode == 14 || AbstractionLayer.getChatAllowedCharacters().indexOf(keyChar) >= 0) {
/* 117 */       this.txtNewConfigName.a(keyChar, keyCode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validateDialog() {
/* 124 */     return (this.txtNewConfigName.b().length() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawDialog(int mouseX, int mouseY, float f) {
/* 131 */     c(this.q, LocalisationProvider.getLocalisedString("options.newconfig.prompt"), this.dialogX + 10, this.dialogY + 16, -22016);
/* 132 */     this.txtNewConfigName.g();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\dialogs\GuiDialogBoxAddConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */