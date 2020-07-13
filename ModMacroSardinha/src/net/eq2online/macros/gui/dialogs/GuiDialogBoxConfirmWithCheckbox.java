/*    */ package net.eq2online.macros.gui.dialogs;
/*    */ 
/*    */ import bug;
/*    */ import net.eq2online.macros.compatibility.GuiControl;
/*    */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*    */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*    */ 
/*    */ 
/*    */ public class GuiDialogBoxConfirmWithCheckbox
/*    */   extends GuiDialogBoxConfirm<Object>
/*    */ {
/*    */   protected String checkboxText;
/*    */   protected GuiCheckBox checkbox;
/*    */   
/*    */   public GuiDialogBoxConfirmWithCheckbox(GuiScreenEx parentScreen, String windowTitle, String line1, String line2, String checkboxText, Object metaData) {
/* 16 */     super(parentScreen, windowTitle, line1, line2, metaData);
/*    */     
/* 18 */     this.checkboxText = checkboxText;
/*    */   }
/*    */ 
/*    */   
/*    */   public GuiDialogBoxConfirmWithCheckbox(GuiScreenEx parentScreen, String windowTitle, String line1, String line2, String checkboxText) {
/* 23 */     super(parentScreen, windowTitle, line1, line2);
/*    */     
/* 25 */     this.checkboxText = checkboxText;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initDialog() {
/* 34 */     super.initDialog();
/*    */     
/* 36 */     this.checkbox = new GuiCheckBox(-4, this.dialogX + 8, this.dialogY + this.dialogHeight - 22, this.checkboxText, false);
/* 37 */     addControl((GuiControl)this.checkbox);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getChecked() {
/* 42 */     return this.checkbox.checked;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void dialogKeyTyped(char keyChar, int keyCode) {
/* 51 */     if (keyChar == 'r' || keyChar == 'R' || keyCode == 57) a((bug)this.btnOk);
/*    */     
/* 53 */     super.dialogKeyTyped(keyChar, keyCode);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\dialogs\GuiDialogBoxConfirmWithCheckbox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */