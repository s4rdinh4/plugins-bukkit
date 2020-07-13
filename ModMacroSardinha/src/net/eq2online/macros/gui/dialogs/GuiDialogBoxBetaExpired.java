/*    */ package net.eq2online.macros.gui.dialogs;
/*    */ 
/*    */ import bxf;
/*    */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*    */ import net.eq2online.macros.core.MacroModCore;
/*    */ import net.eq2online.macros.gui.shared.GuiDialogBox;
/*    */ 
/*    */ public class GuiDialogBoxBetaExpired
/*    */   extends GuiDialogBox
/*    */ {
/*    */   public GuiDialogBoxBetaExpired(bxf parentScreen) {
/* 12 */     super(parentScreen, 340, 130, LocalisationProvider.getLocalisedString("betaexpired.title"));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initDialog() {
/* 18 */     this.btnCancel.m = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onSubmit() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean validateDialog() {
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawDialog(int mouseX, int mouseY, float f) {
/* 38 */     this.rowPos = this.dialogY + 9;
/*    */     
/* 40 */     for (int i = 1; !LocalisationProvider.getLocalisedString("betaexpired.line" + i).startsWith("betaexpired."); i++)
/*    */     {
/* 42 */       drawSpacedString(LocalisationProvider.getLocalisedString("betaexpired.line" + i), this.dialogX + 9, -22016);
/*    */     }
/*    */     
/* 45 */     c(this.q, "v" + MacroModCore.version(), this.dialogX + 10, this.dialogY + this.dialogHeight - 16, -10461088);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\dialogs\GuiDialogBoxBetaExpired.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */