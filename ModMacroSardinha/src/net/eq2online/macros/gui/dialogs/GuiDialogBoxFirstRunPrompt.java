/*    */ package net.eq2online.macros.gui.dialogs;
/*    */ 
/*    */ import bwv;
/*    */ import bxf;
/*    */ import byj;
/*    */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*    */ import net.eq2online.macros.core.MacroModCore;
/*    */ import net.eq2online.macros.gui.shared.GuiDialogBox;
/*    */ 
/*    */ public class GuiDialogBoxFirstRunPrompt
/*    */   extends GuiDialogBox
/*    */ {
/*    */   public GuiDialogBoxFirstRunPrompt(bxf parentScreen) {
/* 14 */     super(parentScreen, 340, 150, LocalisationProvider.getLocalisedString("firstrun.title"));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initDialog() {
/* 20 */     this.btnOk.j = LocalisationProvider.getLocalisedString("firstrun.yes");
/* 21 */     this.btnCancel.j = LocalisationProvider.getLocalisedString("firstrun.no");
/*    */     
/* 23 */     this.btnCancel.setXPosition(this.dialogX + this.dialogWidth - 62);
/* 24 */     this.btnOk.setXPosition(this.dialogX + this.dialogWidth - 186);
/* 25 */     this.btnOk.a(120);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onSubmit() {
/* 31 */     MacroModCore.getInstance().enableScrollToButtons();
/* 32 */     this.legacyParent = (bxf)new byj((bxf)new bwv(this.legacyParent, this.j.t), this.j.t);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean validateDialog() {
/* 38 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawDialog(int mouseX, int mouseY, float f) {
/* 47 */     this.rowPos = this.dialogY + 9;
/*    */     
/* 49 */     for (int i = 1; !LocalisationProvider.getLocalisedString("firstrun.line" + i).startsWith("firstrun."); i++)
/*    */     {
/* 51 */       drawSpacedString(LocalisationProvider.getLocalisedString("firstrun.line" + i), this.dialogX + 9, -22016);
/*    */     }
/*    */     
/* 54 */     c(this.q, "v" + MacroModCore.version(), this.dialogX + 10, this.dialogY + this.dialogHeight - 16, -10461088);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\dialogs\GuiDialogBoxFirstRunPrompt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */