/*    */ package net.eq2online.macros.gui.dialogs;
/*    */ 
/*    */ import bxf;
/*    */ import java.util.ArrayList;
/*    */ import net.eq2online.macros.gui.shared.GuiDialogBox;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiDialogBoxErrorList
/*    */   extends GuiDialogBox
/*    */ {
/*    */   protected String[] errorList;
/*    */   protected String line1;
/*    */   
/*    */   public GuiDialogBoxErrorList(bxf parentScreen, ArrayList<String> errorList, String line, String windowTitle) {
/* 33 */     super(parentScreen, 370, 55 + errorList.size() * 10, windowTitle);
/*    */     
/* 35 */     this.errorList = errorList.<String>toArray(new String[0]);
/* 36 */     this.line1 = line;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initDialog() {
/* 45 */     this.btnCancel.setVisible(false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onSubmit() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean validateDialog() {
/* 60 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawDialog(int mouseX, int mouseY, float f) {
/* 69 */     this.rowPos = this.dialogY + 9;
/* 70 */     drawSpacedString(this.line1, this.dialogX + 9, -22016);
/*    */     
/* 72 */     for (int i = 0; i < this.errorList.length; i++)
/* 73 */       drawSpacedString(this.errorList[i], this.dialogX + 15, -43691); 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\dialogs\GuiDialogBoxErrorList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */