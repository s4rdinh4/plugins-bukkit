/*     */ package net.eq2online.macros.gui.dialogs;
/*     */ 
/*     */ import bul;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiLayout;
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
/*     */ public class GuiDialogBoxRenameItem
/*     */   extends GuiDialogBox
/*     */ {
/*     */   private bul txtNewItemName;
/*     */   private String prompt;
/*     */   private String originalName;
/*     */   private String newItemName;
/*     */   private String allowedcharacters;
/*     */   public File file;
/*     */   public DesignableGuiLayout layout;
/*     */   
/*     */   public GuiDialogBoxRenameItem(GuiScreenEx parentScreen, File file) {
/*  39 */     super(parentScreen, 320, 100, LocalisationProvider.getLocalisedString("editor.rename.file"));
/*     */     
/*  41 */     this.file = file;
/*  42 */     this.prompt = LocalisationProvider.getLocalisedString("editor.rename.file.prompt");
/*  43 */     this.originalName = this.file.getName();
/*  44 */     this.allowedcharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890_-. ";
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiDialogBoxRenameItem(GuiScreenEx parentScreen, DesignableGuiLayout layout) {
/*  49 */     super(parentScreen, 320, 100, LocalisationProvider.getLocalisedString("editor.rename.gui"));
/*     */     
/*  51 */     this.layout = layout;
/*  52 */     this.prompt = LocalisationProvider.getLocalisedString("editor.rename.gui.prompt", new Object[] { layout.name });
/*  53 */     this.originalName = this.layout.displayName;
/*  54 */     this.allowedcharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890_-. ";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/*  61 */     this.txtNewItemName.a();
/*     */     
/*  63 */     super.e();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSubmit() {
/*  70 */     this.newItemName = this.txtNewItemName.b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogMouseClicked(int mouseX, int mouseY, int button) throws IOException {
/*  76 */     this.txtNewItemName.a(mouseX, mouseY, button);
/*  77 */     super.dialogMouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initDialog() {
/*  83 */     this.txtNewItemName = new bul(0, this.q, this.dialogX + 10, this.dialogY + 36, this.dialogWidth - 20, 16);
/*  84 */     this.txtNewItemName.f(255);
/*  85 */     this.txtNewItemName.a(this.originalName);
/*  86 */     this.txtNewItemName.b(true);
/*  87 */     this.txtNewItemName.d(false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogKeyTyped(char keyChar, int keyCode) {
/*  93 */     if (keyCode == 14 || keyCode == 211 || keyCode == 199 || keyCode == 207 || keyCode == 203 || keyCode == 205 || this.allowedcharacters
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  99 */       .indexOf(keyChar) >= 0) {
/* 100 */       this.txtNewItemName.a(keyChar, keyCode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validateDialog() {
/* 107 */     return (this.txtNewItemName.b().length() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawDialog(int mouseX, int mouseY, float f) {
/* 114 */     c(this.q, this.prompt, this.dialogX + 10, this.dialogY + 16, -22016);
/* 115 */     this.txtNewItemName.g();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNewItemName() {
/* 123 */     return this.newItemName;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\dialogs\GuiDialogBoxRenameItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */