/*     */ package net.eq2online.macros.gui.designable.editor;
/*     */ 
/*     */ import bty;
/*     */ import bug;
/*     */ import bul;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.GuiControl;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.gui.controls.GuiLabel;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiLayout;
/*     */ import net.eq2online.macros.gui.shared.GuiDialogBox;
/*     */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiDialogBoxSetGridSize
/*     */   extends GuiDialogBox
/*     */ {
/*     */   protected DesignableGuiLayout layout;
/*     */   protected bul rows;
/*     */   protected bul cols;
/*     */   protected bul colWidth;
/*     */   protected GuiLabel colWidthLabel;
/*     */   
/*     */   public GuiDialogBoxSetGridSize(GuiScreenEx parentScreen, DesignableGuiLayout layout) {
/*  29 */     super(parentScreen, 240, 115, LocalisationProvider.getLocalisedString("grid.properties.title"));
/*     */     
/*  31 */     this.layout = layout;
/*  32 */     this.movable = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initDialog() {
/*  41 */     addControl((GuiControl)new GuiLabel(26, this.dialogX + 33, this.dialogY + 18, LocalisationProvider.getLocalisedString("grid.properties.rows"), -22016));
/*  42 */     addControl((GuiControl)new GuiLabel(27, this.dialogX + 33, this.dialogY + 42, LocalisationProvider.getLocalisedString("grid.properties.cols"), -22016));
/*  43 */     addControl((GuiControl)(this.colWidthLabel = new GuiLabel(27, this.dialogX + 33, this.dialogY + 66, LocalisationProvider.getLocalisedString("grid.properties.colWidth"), -22016)));
/*     */     
/*  45 */     addControl(new GuiControl(20, this.dialogX + 103, this.dialogY + 12, 22, 20, "", 8));
/*  46 */     addControl(new GuiControl(21, this.dialogX + 185, this.dialogY + 12, 22, 20, "", 9));
/*     */     
/*  48 */     addControl(new GuiControl(22, this.dialogX + 103, this.dialogY + 36, 22, 20, "", 8));
/*  49 */     addControl(new GuiControl(23, this.dialogX + 185, this.dialogY + 36, 22, 20, "", 9));
/*     */     
/*  51 */     removeControl(this.btnCancel);
/*     */     
/*  53 */     bty fontRenderer = AbstractionLayer.getFontRenderer();
/*  54 */     this.rows = new bul(0, fontRenderer, this.dialogX + 135, this.dialogY + 14, 40, 16);
/*  55 */     this.cols = new bul(1, fontRenderer, this.dialogX + 135, this.dialogY + 38, 40, 16);
/*  56 */     this.colWidth = new bul(2, fontRenderer, this.dialogX + 135, this.dialogY + 62, 40, 16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawDialog(int mouseX, int mouseY, float f) {
/*  65 */     this.rows.a(String.valueOf(this.layout.getRows()));
/*  66 */     this.rows.g();
/*     */     
/*  68 */     this.cols.a(String.valueOf(this.layout.getColumns()));
/*  69 */     this.cols.g();
/*     */     
/*  71 */     int editingColumn = this.layout.getSelectedColumn();
/*  72 */     if (editingColumn < 0) this.colWidth.b(false); 
/*  73 */     this.colWidthLabel.drawColour = (editingColumn > -1) ? -22016 : -8355712;
/*     */     
/*  75 */     this.colWidth.a(this.layout.getSelectedColumnWidthText());
/*  76 */     this.colWidth.g();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawParentScreen(int mouseX, int mouseY, float partialTick) {
/*  85 */     if (this.parentScreen != null) {
/*     */ 
/*     */       
/*  88 */       this.parentScreen.drawScreenWithEnabledState(mouseX, mouseY, partialTick, false);
/*     */     }
/*     */     else {
/*     */       
/*  92 */       super.drawParentScreen(mouseX, mouseY, partialTick);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(bug guibutton) {
/* 102 */     if (guibutton.k == 20) this.layout.removeRow(); 
/* 103 */     if (guibutton.k == 21) this.layout.addRow(); 
/* 104 */     if (guibutton.k == 22) this.layout.removeColumn(); 
/* 105 */     if (guibutton.k == 23) this.layout.addColumn();
/*     */     
/* 107 */     super.a(guibutton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogMouseClicked(int mouseX, int mouseY, int button) throws IOException {
/* 116 */     super.dialogMouseClicked(mouseX, mouseY, button);
/*     */     
/* 118 */     this.colWidth.a(mouseX, mouseY, button);
/*     */     
/* 120 */     if (button == 0 && (mouseX < this.dialogX || mouseY < this.dialogY || mouseX > this.dialogX + this.dialogWidth || mouseY > this.dialogY + this.dialogHeight)) {
/*     */       
/* 122 */       this.layout.selectColumn();
/* 123 */       this.colWidth.b(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogKeyTyped(char keyChar, int keyCode) {
/* 133 */     super.dialogKeyTyped(keyChar, keyCode);
/*     */     
/* 135 */     if (this.colWidth.m())
/*     */     {
/* 137 */       if (keyCode == 14 || "0123456789".indexOf(keyChar) > -1) {
/*     */         
/* 139 */         if (this.colWidth.b().equals("§8Auto")) {
/* 140 */           this.colWidth.a("");
/*     */         }
/* 142 */         this.colWidth.a(keyChar, keyCode);
/* 143 */         this.layout.setSelectedColumnWidth(MacroModCore.parseInt(this.colWidth.b(), 0));
/*     */         
/* 145 */         if (this.colWidth.b().equals("")) {
/* 146 */           this.colWidth.a("§8Auto");
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSubmit() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validateDialog() {
/* 159 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\designable\editor\GuiDialogBoxSetGridSize.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */