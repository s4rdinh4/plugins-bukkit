/*    */ package net.eq2online.macros.gui.designable.editor;
/*    */ 
/*    */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*    */ import net.eq2online.macros.gui.controls.GuiDropDownList;
/*    */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*    */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*    */ 
/*    */ public class GuiDialogBoxProgressBarProperties
/*    */   extends GuiDialogBoxControlProperties
/*    */ {
/*    */   public GuiDialogBoxProgressBarProperties(GuiScreenEx parentScreen, DesignableGuiControl control) {
/* 12 */     super(parentScreen, control);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initDialog() {
/* 21 */     super.initDialog();
/*    */     
/* 23 */     addTextField(LocalisationProvider.getLocalisedString("control.properties.bar.expression"), "expression", false).b(true);
/* 24 */     addTextFieldWithCheckBox(LocalisationProvider.getLocalisedString("control.properties.bar.min"), "min", LocalisationProvider.getLocalisedString("control.properties.bar.expression"), "calcmin", false);
/* 25 */     addTextFieldWithCheckBox(LocalisationProvider.getLocalisedString("control.properties.bar.max"), "max", LocalisationProvider.getLocalisedString("control.properties.bar.expression"), "calcmax", false);
/* 26 */     addColourButton(LocalisationProvider.getLocalisedString("control.properties.forecolour"), "colour", -16711936);
/* 27 */     addColourButton(LocalisationProvider.getLocalisedString("control.properties.backcolour"), "background", -1442840576);
/*    */     
/* 29 */     GuiDropDownList.GuiDropDownListControl alignment = addDropDown(LocalisationProvider.getLocalisedString("control.properties.bar.style"), "style", "Style");
/*    */     
/* 31 */     alignment.addItem("horizontal", LocalisationProvider.getLocalisedString("control.properties.bar.style.horizontal"));
/* 32 */     alignment.addItem("vertical", LocalisationProvider.getLocalisedString("control.properties.bar.style.vertical"));
/*    */     
/* 34 */     alignment.selectItemByTag(this.control.getProperty("style", "horizontal").toLowerCase());
/*    */     
/* 36 */     this.txtName.b(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\designable\editor\GuiDialogBoxProgressBarProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */