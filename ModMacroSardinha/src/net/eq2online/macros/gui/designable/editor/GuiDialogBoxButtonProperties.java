/*    */ package net.eq2online.macros.gui.designable.editor;
/*    */ 
/*    */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*    */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*    */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*    */ 
/*    */ public class GuiDialogBoxButtonProperties
/*    */   extends GuiDialogBoxControlProperties
/*    */ {
/*    */   public GuiDialogBoxButtonProperties(GuiScreenEx parentScreen, DesignableGuiControl control) {
/* 11 */     super(parentScreen, control);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initDialog() {
/* 20 */     super.initDialog();
/*    */     
/* 22 */     addTextField(LocalisationProvider.getLocalisedString("control.properties.button.text"), "text", false).b(true);
/* 23 */     addCheckBox(LocalisationProvider.getLocalisedString("control.properties.button.hide"), "hide");
/* 24 */     addCheckBox(LocalisationProvider.getLocalisedString("control.properties.button.sticky"), "sticky");
/* 25 */     addColourButton(LocalisationProvider.getLocalisedString("control.properties.forecolour"), "colour", -16711936);
/* 26 */     addColourButton(LocalisationProvider.getLocalisedString("control.properties.backcolour"), "background", -1442840576);
/*    */     
/* 28 */     this.txtName.b(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\designable\editor\GuiDialogBoxButtonProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */