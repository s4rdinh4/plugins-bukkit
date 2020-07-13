/*     */ package net.eq2online.macros.gui.dialogs;
/*     */ 
/*     */ import bug;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
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
/*     */ public class GuiDialogBoxConfirm<T>
/*     */   extends GuiDialogBox
/*     */ {
/*     */   private String messageText1;
/*     */   private String messageText2;
/*     */   private T metaData;
/*     */   
/*     */   public GuiDialogBoxConfirm(GuiScreenEx parentScreen, String windowTitle, String line1, String line2, T metaData) {
/*  37 */     this(parentScreen, windowTitle, line1, line2);
/*  38 */     this.metaData = metaData;
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
/*     */   public GuiDialogBoxConfirm(GuiScreenEx parentScreen, String windowTitle, String line1, String line2) {
/*  51 */     super(parentScreen, 320, 80, windowTitle);
/*     */ 
/*     */     
/*  54 */     this.messageText1 = line1;
/*  55 */     this.messageText2 = line2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getMetaData() {
/*  65 */     return this.metaData;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initDialog() {
/*  71 */     this.btnOk.j = LocalisationProvider.getLocalisedString("gui.yes");
/*  72 */     this.btnCancel.j = LocalisationProvider.getLocalisedString("gui.no");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSubmit() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validateDialog() {
/*  83 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawDialog(int mouseX, int mouseY, float f) {
/*  90 */     a(this.q, this.messageText1, this.dialogX + this.dialogWidth / 2, this.dialogY + 18, -22016);
/*  91 */     a(this.q, this.messageText2, this.dialogX + this.dialogWidth / 2, this.dialogY + 32, -22016);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogKeyTyped(char keyChar, int keyCode) {
/* 100 */     if (keyChar == 'y' || keyChar == 'Y') a((bug)this.btnOk); 
/* 101 */     if (keyChar == 'n' || keyChar == 'N') a((bug)this.btnCancel); 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\dialogs\GuiDialogBoxConfirm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */