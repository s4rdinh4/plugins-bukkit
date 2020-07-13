/*    */ package net.eq2online.macros.gui.designable.editor;
/*    */ 
/*    */ import bul;
/*    */ import net.eq2online.macros.compatibility.GuiControl;
/*    */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*    */ import net.eq2online.macros.gui.controls.GuiLabel;
/*    */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*    */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*    */ 
/*    */ public class GuiDialogBoxTextAreaProperties
/*    */   extends GuiDialogBoxControlProperties {
/*    */   public GuiDialogBoxTextAreaProperties(GuiScreenEx parentScreen, DesignableGuiControl control) {
/* 13 */     super(parentScreen, control);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initDialog() {
/* 22 */     super.initDialog();
/*    */     
/* 24 */     addColourButton(LocalisationProvider.getLocalisedString("control.properties.forecolour"), "colour", -16711936);
/*    */     
/* 26 */     bul textField = new bul(0, this.j.k, this.dialogX + 85, this.dialogY + getControlTop(), 32, 16);
/* 27 */     addControl((GuiControl)new GuiLabel(-999, this.dialogX + 122, this.dialogY + getControlTop() + 4, "ticks", -22016));
/* 28 */     addTextField(LocalisationProvider.getLocalisedString("control.properties.textarea.lifespan"), "lifespan", textField, true);
/*    */     
/* 30 */     this.txtName.b(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\designable\editor\GuiDialogBoxTextAreaProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */