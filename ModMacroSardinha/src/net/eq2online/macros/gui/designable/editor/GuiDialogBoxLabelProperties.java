/*    */ package net.eq2online.macros.gui.designable.editor;
/*    */ 
/*    */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*    */ import net.eq2online.macros.core.MacroModCore;
/*    */ import net.eq2online.macros.gui.controls.GuiDropDownList;
/*    */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*    */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*    */ import net.eq2online.macros.interfaces.IHighlighter;
/*    */ 
/*    */ public class GuiDialogBoxLabelProperties
/*    */   extends GuiDialogBoxControlProperties
/*    */   implements IHighlighter {
/*    */   public GuiDialogBoxLabelProperties(GuiScreenEx parentScreen, DesignableGuiControl control) {
/* 14 */     super(parentScreen, control);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initDialog() {
/* 23 */     super.initDialog();
/*    */     
/* 25 */     addTextField(LocalisationProvider.getLocalisedString("control.properties.label.text"), "text", this).b(true);
/* 26 */     addTextField(LocalisationProvider.getLocalisedString("control.properties.label.binding"), "binding", false);
/* 27 */     addColourButton(LocalisationProvider.getLocalisedString("control.properties.forecolour"), "colour", -16711936);
/* 28 */     addColourButton(LocalisationProvider.getLocalisedString("control.properties.backcolour"), "background", -1442840576);
/*    */     
/* 30 */     GuiDropDownList.GuiDropDownListControl alignment = addDropDown(LocalisationProvider.getLocalisedString("control.properties.label.align"), "align", "Select");
/*    */     
/* 32 */     alignment.addItem("top left", LocalisationProvider.getLocalisedString("control.properties.label.align.topleft"));
/* 33 */     alignment.addItem("top centre", LocalisationProvider.getLocalisedString("control.properties.label.align.topcentre"));
/* 34 */     alignment.addItem("top right", LocalisationProvider.getLocalisedString("control.properties.label.align.topright"));
/* 35 */     alignment.addItem("middle left", LocalisationProvider.getLocalisedString("control.properties.label.align.middleleft"));
/* 36 */     alignment.addItem("middle centre", LocalisationProvider.getLocalisedString("control.properties.label.align.middlecentre"));
/* 37 */     alignment.addItem("middle right", LocalisationProvider.getLocalisedString("control.properties.label.align.middleright"));
/* 38 */     alignment.addItem("bottom left", LocalisationProvider.getLocalisedString("control.properties.label.align.bottomleft"));
/* 39 */     alignment.addItem("bottom centre", LocalisationProvider.getLocalisedString("control.properties.label.align.bottomcentre"));
/* 40 */     alignment.addItem("bottom right", LocalisationProvider.getLocalisedString("control.properties.label.align.bottomright"));
/*    */     
/* 42 */     alignment.selectItemByTag(this.control.getProperty("align", "top left"));
/*    */     
/* 44 */     addCheckBox(LocalisationProvider.getLocalisedString("control.properties.text.shadow"), "shadow");
/*    */     
/* 46 */     this.txtName.b(false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String generateHighlightMask(String text) {
/* 52 */     return MacroModCore.stringToHighlightMask(highlight(text));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String highlight(String text) {
/* 58 */     return text.replaceAll("(?<!d)%%", "§d%§d%§f");
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\designable\editor\GuiDialogBoxLabelProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */