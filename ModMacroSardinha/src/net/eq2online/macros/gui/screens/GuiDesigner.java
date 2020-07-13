/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bxf;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiLayout;
/*     */ import net.eq2online.macros.gui.designable.LayoutManager;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxRenameItem;
/*     */ import net.eq2online.macros.gui.layout.LayoutPanel;
/*     */ import net.eq2online.macros.gui.layout.LayoutPanelEditMode;
/*     */ import net.eq2online.macros.gui.shared.GuiDialogBox;
/*     */ import net.eq2online.macros.interfaces.ILayoutPanel;
/*     */ import net.eq2online.macros.interfaces.ILayoutWidget;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiDesigner
/*     */   extends GuiDesignerBase
/*     */ {
/*     */   protected boolean allowBinding = false;
/*     */   
/*     */   public GuiDesigner(String guiSlotName, bxf parentScreen, boolean allowBinding) {
/*  32 */     super(0, 0);
/*     */     
/*  34 */     this.lastScreen = parentScreen;
/*  35 */     this.screenDrawMenuButton = true;
/*  36 */     this.screenCentreBanner = false;
/*  37 */     this.allowBinding = allowBinding;
/*     */     
/*  39 */     this.layout = LayoutManager.getBoundLayout(guiSlotName, false);
/*     */     
/*  41 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDesigner(DesignableGuiLayout layout, bxf parentScreen, boolean allowBinding) {
/*  50 */     super(0, 0);
/*     */     
/*  52 */     this.lastScreen = parentScreen;
/*  53 */     this.screenDrawMenuButton = true;
/*  54 */     this.screenCentreBanner = false;
/*  55 */     this.allowBinding = allowBinding;
/*     */     
/*  57 */     this.layout = layout;
/*     */     
/*  59 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init() {
/*  65 */     super.init();
/*     */     
/*  67 */     this.screenMenu.addItem("rename", LocalisationProvider.getLocalisedString("layout.editor.menu.rename"))
/*  68 */       .addSeparator()
/*  69 */       .addItem("back", LocalisationProvider.getLocalisedString("gui.exit"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/*  75 */     super.e();
/*     */     
/*  77 */     if (this.layout != null)
/*     */     {
/*  79 */       this.layout.onTick();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleWidgetClick(ILayoutPanel<? extends ILayoutWidget> source, int keyCode, boolean bindable) {
/*  90 */     if (bindable && this.allowBinding) {
/*     */       
/*  92 */       this.macros.save();
/*  93 */       AbstractionLayer.displayGuiScreen((bxf)new GuiMacroEdit(keyCode, (bxf)this));
/*     */     }
/*     */     else {
/*     */       
/*  97 */       LayoutPanel.setGlobalMode(LayoutPanelEditMode.EditButtonsOnly);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setTitles(int currentPage) {
/* 107 */     this.screenTitle = LocalisationProvider.getLocalisedString("editor.editing", new Object[] { '"' + this.layout.displayName + '"' });
/* 108 */     this.prompt = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMenuItemClicked(String menuItem) {
/* 117 */     if (menuItem.equals("back")) {
/* 118 */       onCloseClick();
/*     */     }
/* 120 */     if (menuItem.equals("rename")) {
/* 121 */       onTitleClick();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onTitleClick() {
/* 127 */     displayDialog((GuiDialogBox)new GuiDialogBoxRenameItem(this, this.layout));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDialogClosed(GuiDialogBox dialog) {
/* 133 */     super.onDialogClosed(dialog);
/*     */     
/* 135 */     if (dialog.dialogResult == GuiDialogBox.DialogResult.OK)
/*     */     {
/* 137 */       if (dialog instanceof GuiDialogBoxRenameItem) {
/*     */         
/* 139 */         GuiDialogBoxRenameItem renameDialog = (GuiDialogBoxRenameItem)dialog;
/* 140 */         renameDialog.layout.displayName = renameDialog.getNewItemName();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\screens\GuiDesigner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */