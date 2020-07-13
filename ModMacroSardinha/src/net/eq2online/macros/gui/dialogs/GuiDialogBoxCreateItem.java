/*     */ package net.eq2online.macros.gui.dialogs;
/*     */ 
/*     */ import bul;
/*     */ import java.io.IOException;
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
/*     */ public class GuiDialogBoxCreateItem
/*     */   extends GuiDialogBox
/*     */ {
/*     */   private bul txtNewItemName;
/*     */   private bul txtNewItemDisplayName;
/*     */   private String prompt;
/*  24 */   private String newItemName = "", newItemDisplayName = "";
/*     */   
/*  26 */   private String allowedcharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890_-. ";
/*     */ 
/*     */   
/*     */   public GuiDialogBoxCreateItem(GuiScreenEx parentScreen, String windowTitle, String prompt) {
/*  30 */     super(parentScreen, 320, 110, windowTitle);
/*  31 */     this.prompt = prompt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/*  37 */     this.txtNewItemName.a();
/*  38 */     this.txtNewItemDisplayName.a();
/*     */     
/*  40 */     super.e();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSubmit() {
/*  46 */     this.newItemName = this.txtNewItemName.b();
/*  47 */     this.newItemDisplayName = this.txtNewItemDisplayName.b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogMouseClicked(int mouseX, int mouseY, int button) throws IOException {
/*  53 */     this.txtNewItemName.a(mouseX, mouseY, button);
/*  54 */     this.txtNewItemDisplayName.a(mouseX, mouseY, button);
/*  55 */     super.dialogMouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initDialog() {
/*  61 */     this.txtNewItemName = new bul(0, this.q, this.dialogX + 80, this.dialogY + 30, this.dialogWidth - 90, 16);
/*  62 */     this.txtNewItemName.f(255);
/*  63 */     this.txtNewItemName.a(this.newItemName);
/*  64 */     this.txtNewItemName.b(true);
/*     */     
/*  66 */     this.txtNewItemDisplayName = new bul(1, this.q, this.dialogX + 80, this.dialogY + 54, this.dialogWidth - 90, 16);
/*  67 */     this.txtNewItemDisplayName.f(255);
/*  68 */     this.txtNewItemDisplayName.a(this.newItemDisplayName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogKeyTyped(char keyChar, int keyCode) {
/*  74 */     if (keyCode == 14 || keyCode == 211 || keyCode == 199 || keyCode == 207 || keyCode == 203 || keyCode == 205 || this.allowedcharacters
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  80 */       .indexOf(keyChar) >= 0) {
/*     */       
/*  82 */       String oldName = this.txtNewItemName.b();
/*     */       
/*  84 */       this.txtNewItemName.a(keyChar, keyCode);
/*  85 */       this.txtNewItemDisplayName.a(keyChar, keyCode);
/*     */       
/*  87 */       this.txtNewItemName.g(14737632);
/*     */       
/*  89 */       if (this.txtNewItemName.b() != oldName)
/*     */       {
/*  91 */         this.txtNewItemDisplayName.a(this.txtNewItemName.b());
/*     */       }
/*     */     }
/*  94 */     else if (keyCode == 15) {
/*     */       
/*  96 */       if (this.txtNewItemName.m()) {
/*     */         
/*  98 */         this.txtNewItemName.b(false);
/*  99 */         this.txtNewItemDisplayName.b(true);
/*     */       }
/*     */       else {
/*     */         
/* 103 */         this.txtNewItemName.b(true);
/* 104 */         this.txtNewItemDisplayName.b(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDialogSubmissionFailed(String reason) {
/* 112 */     this.txtNewItemName.g(16733525);
/* 113 */     this.txtNewItemName.b(true);
/* 114 */     this.txtNewItemDisplayName.b(false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validateDialog() {
/* 120 */     return (this.txtNewItemName.b().length() > 0 && this.txtNewItemDisplayName.b().length() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawDialog(int mouseX, int mouseY, float f) {
/* 127 */     c(this.q, this.prompt, this.dialogX + 10, this.dialogY + 10, -22016);
/*     */     
/* 129 */     c(this.q, "Name", this.dialogX + 10, this.dialogY + 36, -256);
/* 130 */     this.txtNewItemName.g();
/*     */     
/* 132 */     c(this.q, "Display Name", this.dialogX + 10, this.dialogY + 60, -256);
/* 133 */     this.txtNewItemDisplayName.g();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNewItemName() {
/* 141 */     return this.newItemName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNewItemDisplayName() {
/* 146 */     return this.newItemDisplayName;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\dialogs\GuiDialogBoxCreateItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */