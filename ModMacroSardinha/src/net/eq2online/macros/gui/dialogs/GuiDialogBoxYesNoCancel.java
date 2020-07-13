/*    */ package net.eq2online.macros.gui.dialogs;
/*    */ 
/*    */ import bug;
/*    */ import net.eq2online.macros.compatibility.GuiControl;
/*    */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*    */ import net.eq2online.macros.gui.shared.GuiDialogBox;
/*    */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*    */ 
/*    */ public class GuiDialogBoxYesNoCancel
/*    */   extends GuiDialogBoxConfirm<Object> {
/*    */   protected GuiControl btnNo;
/*    */   
/*    */   public GuiDialogBoxYesNoCancel(GuiScreenEx parentScreen, String windowTitle, String line1, String line2, Object metaData) {
/* 14 */     super(parentScreen, windowTitle, line1, line2, metaData);
/*    */   }
/*    */ 
/*    */   
/*    */   public GuiDialogBoxYesNoCancel(GuiScreenEx parentScreen, String windowTitle, String line1, String line2) {
/* 19 */     super(parentScreen, windowTitle, line1, line2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initDialog() {
/* 28 */     this.btnCancel.setXPosition(this.dialogX + 2);
/* 29 */     this.btnNo = new GuiControl(-3, this.dialogX + this.dialogWidth - 124, this.dialogY + this.dialogHeight - 22, 60, 20, LocalisationProvider.getLocalisedString("gui.no"));
/* 30 */     addControl(this.btnNo);
/* 31 */     this.btnOk.j = LocalisationProvider.getLocalisedString("gui.yes");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void a(bug guibutton) {
/* 40 */     if (guibutton.k == this.btnOk.k) {
/*    */       
/* 42 */       this.dialogResult = GuiDialogBox.DialogResult.Yes;
/* 43 */       closeDialog();
/*    */     } 
/* 45 */     if (guibutton.k == this.btnNo.k) {
/*    */       
/* 47 */       this.dialogResult = GuiDialogBox.DialogResult.No;
/* 48 */       closeDialog();
/*    */     } 
/* 50 */     if (guibutton.k == this.btnCancel.k) {
/*    */       
/* 52 */       this.dialogResult = GuiDialogBox.DialogResult.Cancel;
/* 53 */       closeDialog();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void dialogKeyTyped(char keyChar, int keyCode) {
/* 63 */     if (keyChar == 'y' || keyChar == 'Y') a((bug)this.btnOk); 
/* 64 */     if (keyChar == 'n' || keyChar == 'N') a((bug)this.btnNo); 
/* 65 */     if (keyChar == 'c' || keyChar == 'C') a((bug)this.btnCancel); 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\dialogs\GuiDialogBoxYesNoCancel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */