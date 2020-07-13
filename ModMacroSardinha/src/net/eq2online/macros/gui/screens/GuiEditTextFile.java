/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import alq;
/*     */ import amj;
/*     */ import amk;
/*     */ import bug;
/*     */ import bul;
/*     */ import bxf;
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.GuiControl;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.Macro;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.core.MacroPlaybackType;
/*     */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.controls.GuiTextEditor;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxConfirm;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxConfirmWithCheckbox;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxRenameItem;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxYesNoCancel;
/*     */ import net.eq2online.macros.gui.list.ListObjectEditable;
/*     */ import net.eq2online.macros.gui.shared.GuiDialogBox;
/*     */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*     */ import net.eq2online.macros.interfaces.IHighlighter;
/*     */ import net.eq2online.macros.interfaces.IListObject;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import org.lwjgl.input.Keyboard;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiEditTextFile
/*     */   extends GuiEditText
/*     */   implements FilenameFilter, IHighlighter
/*     */ {
/*     */   protected GuiListBox fileListBox;
/*     */   protected bul newFileTextBox;
/*     */   protected File file;
/*  60 */   protected String fileSuggestion = null;
/*     */ 
/*     */   
/*     */   protected boolean closeMe = false;
/*     */ 
/*     */   
/*     */   protected ScriptContext context;
/*     */ 
/*     */   
/*  69 */   protected static String lastFile = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiEditTextFile(GuiScreenEx parent, File file, ScriptContext context) {
/*  79 */     super(parent);
/*  80 */     this.file = file;
/*  81 */     this.context = context;
/*  82 */     this.screenTitle = LocalisationProvider.getLocalisedString("editor.title");
/*  83 */     this.screenBackgroundSpaceBottom = 28;
/*  84 */     this.screenBanner = (this.file == null) ? "" : LocalisationProvider.getLocalisedString("editor.editing", new Object[] { this.file.getName() });
/*     */     
/*  86 */     this.screenMenu.addItem("mini", LocalisationProvider.getLocalisedString("editor.menu.minimise"))
/*  87 */       .addItem("edit", LocalisationProvider.getLocalisedString("editor.menu.editother"))
/*  88 */       .addItem("help", LocalisationProvider.getLocalisedString("editor.menu.help"))
/*  89 */       .addSeparator()
/*  90 */       .addItem("close", LocalisationProvider.getLocalisedString("gui.exit"));
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiEditTextFile(GuiScreenEx parent, ScriptContext context) {
/*  95 */     this(parent, (File)null, context);
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiEditTextFile(GuiScreenEx parent, String suggestion, ScriptContext context) {
/* 100 */     this(parent, context);
/*     */     
/* 102 */     this.fileSuggestion = suggestion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/* 111 */     Keyboard.enableRepeatEvents(true);
/*     */     
/* 113 */     clearControlList();
/*     */     
/* 115 */     if (this.file != null) {
/*     */       
/* 117 */       lastFile = this.file.getName();
/*     */ 
/*     */       
/* 120 */       if (this.textEditor == null) {
/*     */         
/* 122 */         this.textEditor = new GuiTextEditor(this.j, this, 0, 7, 27, this.l - 12, this.m - 60, MacroModSettings.showTextEditorSyntax, MacroModSettings.showTextEditorHelp, this.context);
/* 123 */         this.textEditor.load(this.file);
/*     */       }
/*     */       else {
/*     */         
/* 127 */         this.textEditor.setSizeAndPosition(7, 27, this.l - 12, this.m - 60);
/*     */       } 
/*     */       
/* 130 */       addControl((GuiControl)this.textEditor);
/* 131 */       addControl(new GuiControl(1, this.l - 64, this.m - 24, 60, 20, LocalisationProvider.getLocalisedString("gui.save")));
/* 132 */       addControl(new GuiControl(2, this.l - 128, this.m - 24, 60, 20, LocalisationProvider.getLocalisedString("gui.cancel")));
/* 133 */       addControl(new GuiControl(10, 5, this.m - 24, 30, 20, "..."));
/*     */       
/* 135 */       addControl((GuiControl)(this.chkShowHelp = new GuiCheckBox(55, 40, this.m - 24, LocalisationProvider.getLocalisedString("editor.option.help"), MacroModSettings.showTextEditorHelp)));
/*     */       
/* 137 */       this.screenDrawMinButton = (this.parent instanceof GuiMacroBind || this.parent instanceof GuiMacroPlayback);
/* 138 */       this.screenDrawMenuButton = true;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 143 */       if (this.fileListBox == null) {
/*     */         
/* 145 */         this.fileListBox = new GuiListBox(this.j, 5, 4, 40, 174, this.m - 70, 20, true, false, false);
/* 146 */         this.newFileTextBox = new bul(0, AbstractionLayer.getFontRenderer(), 199, 40, this.l - 225, 16);
/* 147 */         this.newFileTextBox.f(255);
/* 148 */         this.newFileTextBox.b(true);
/*     */         
/* 150 */         populateFileList();
/*     */       }
/*     */       else {
/*     */         
/* 154 */         this.fileListBox.setSizeAndPosition(4, 40, 174, this.m - 70, GuiListBox.defaultRowHeight, true);
/*     */       } 
/*     */       
/* 157 */       addControl((GuiControl)this.fileListBox);
/* 158 */       addControl(new GuiControl(3, this.l - 64, this.m - 24, 60, 20, LocalisationProvider.getLocalisedString("gui.ok")));
/* 159 */       addControl(new GuiControl(4, this.l - 128, this.m - 24, 60, 20, LocalisationProvider.getLocalisedString("gui.cancel")));
/* 160 */       addControl(new GuiControl(6, this.l - 106, 60, 80, 20, LocalisationProvider.getLocalisedString("gui.create")));
/*     */       
/* 162 */       this.screenDrawMinButton = false;
/* 163 */       this.screenDrawMenuButton = false;
/*     */     } 
/*     */     
/* 166 */     super.b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMenuItemClicked(String menuItem) {
/* 172 */     if ("mini".equals(menuItem)) {
/*     */       
/* 174 */       onMinimiseClick();
/*     */     }
/* 176 */     else if ("edit".equals(menuItem)) {
/*     */       
/* 178 */       onBackToMenuClick();
/*     */     }
/* 180 */     else if ("help".equals(menuItem)) {
/*     */       
/* 182 */       AbstractionLayer.displayGuiScreen((bxf)new GuiCommandReference((bxf)this));
/*     */     }
/* 184 */     else if ("close".equals(menuItem)) {
/*     */       
/* 186 */       onCloseClick();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(bug guibutton) {
/* 198 */     if (guibutton != null) {
/*     */ 
/*     */       
/* 201 */       if (guibutton.k == 1) {
/*     */         
/* 203 */         this.textEditor.save();
/* 204 */         close();
/*     */       } 
/*     */ 
/*     */       
/* 208 */       if (guibutton.k == 1 || guibutton.k == 2 || guibutton.k == 4)
/*     */       {
/* 210 */         close();
/*     */       }
/*     */ 
/*     */       
/* 214 */       if (guibutton.k == 3 || (guibutton.k == 5 && this.fileListBox.isDoubleClicked(true))) {
/*     */         
/* 216 */         if (this.newFileTextBox.b().length() > 0 && guibutton.k == 3)
/*     */         {
/* 218 */           createFile();
/*     */         }
/*     */         else
/*     */         {
/* 222 */           if (this.fileListBox.getSelectedItem() != null) {
/*     */             
/* 224 */             this.file = (File)this.fileListBox.getSelectedItem().getData();
/*     */           }
/*     */           else {
/*     */             
/* 228 */             this.file = new File(MacroModCore.getMacrosDirectory(), "New Text File.txt");
/*     */           } 
/*     */           
/* 231 */           this.screenBanner = LocalisationProvider.getLocalisedString("editor.editing", new Object[] { this.file.getName() });
/* 232 */           b();
/*     */         }
/*     */       
/* 235 */       } else if (guibutton.k == 5) {
/*     */         
/* 237 */         if (this.fileListBox.getSelectedItem() != null) {
/*     */           
/* 239 */           String customAction = this.fileListBox.getSelectedItem().getCustomAction(true);
/*     */           
/* 241 */           if (customAction == "edit") {
/*     */             
/* 243 */             displayDialog((GuiDialogBox)new GuiDialogBoxRenameItem(this, (File)this.fileListBox.getSelectedItem().getData()));
/*     */           }
/* 245 */           else if (customAction == "delete") {
/*     */             
/* 247 */             displayDialog((GuiDialogBox)new GuiDialogBoxConfirm(this, LocalisationProvider.getLocalisedString("gui.delete"), LocalisationProvider.getLocalisedString("param.action.confirmdeletefile"), this.fileListBox.getSelectedItem().getText()));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 252 */       if (guibutton.k == 6)
/*     */       {
/* 254 */         createFile();
/*     */       }
/*     */       
/* 257 */       if (guibutton.k == 10)
/*     */       {
/* 259 */         onBackToMenuClick();
/*     */       }
/*     */       
/* 262 */       if (guibutton.k == 55) {
/*     */         
/* 264 */         MacroModSettings.showTextEditorHelp = this.chkShowHelp.checked;
/* 265 */         if (this.textEditor != null) this.textEditor.showCommandHelp = this.chkShowHelp.checked;
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onBackToMenuClick() {
/* 275 */     if (this.textEditor.isChanged()) {
/*     */       
/* 277 */       this.closeMe = false;
/* 278 */       displayDialog((GuiDialogBox)new GuiDialogBoxYesNoCancel(this, LocalisationProvider.getLocalisedString("gui.save"), LocalisationProvider.getLocalisedString("editor.savechanges"), this.file.getName()));
/*     */     }
/*     */     else {
/*     */       
/* 282 */       this.textEditor.clear();
/* 283 */       this.j.a((bxf)new GuiEditTextFile(this.parent, this.context));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) {
/* 293 */     if (keyCode == 1) {
/*     */       
/* 295 */       onCloseClick();
/*     */       
/*     */       return;
/*     */     } 
/* 299 */     if (this.textEditor != null) {
/*     */       
/* 301 */       if (keyCode == 15 && (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157))) {
/*     */         
/* 303 */         onMinimiseClick();
/*     */         
/*     */         return;
/*     */       } 
/* 307 */       this.textEditor.keyTyped(keyChar, keyCode);
/*     */       
/* 309 */       if (keyCode == 59)
/*     */       {
/* 311 */         AbstractionLayer.displayGuiScreen((bxf)new GuiCommandReference((bxf)this));
/*     */       }
/*     */     }
/* 314 */     else if (this.newFileTextBox != null && this.newFileTextBox.m()) {
/*     */       
/* 316 */       if (keyCode == 14 || "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890_-. ".indexOf(keyChar) >= 0)
/*     */       {
/* 318 */         this.newFileTextBox.a(keyChar, keyCode);
/*     */       }
/* 320 */       else if (keyCode == 28 || keyCode == 156)
/*     */       {
/* 322 */         createFile();
/*     */       }
/*     */     
/* 325 */     } else if (this.fileListBox != null) {
/*     */       
/* 327 */       if (keyCode == 200) this.fileListBox.up(); 
/* 328 */       if (keyCode == 208) this.fileListBox.down(); 
/* 329 */       if (keyCode == 201) this.fileListBox.pageUp(); 
/* 330 */       if (keyCode == 209) this.fileListBox.pageDown(); 
/* 331 */       if (keyCode == 28 || keyCode == 156) {
/*     */         
/* 333 */         this.file = (File)this.fileListBox.getSelectedItem().getData();
/* 334 */         this.screenBanner = LocalisationProvider.getLocalisedString("editor.editing", new Object[] { this.file.getName() });
/* 335 */         b();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/* 343 */     if (this.newFileTextBox != null) {
/* 344 */       this.newFileTextBox.a();
/*     */     }
/* 346 */     super.e();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float f) {
/* 355 */     if (AbstractionLayer.getWorld() == null) {
/* 356 */       c();
/*     */     }
/* 358 */     drawScreenWithEnabledState(mouseX, mouseY, f, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreenWithEnabledState(int mouseX, int mouseY, float f, boolean enabled) {
/* 364 */     a(2, this.m - 26, this.l - 2, this.m - 2, this.screenBackColour);
/*     */     
/* 366 */     if (this.fileListBox != null) this.fileListBox.setEnabled((this.newFileTextBox.b().length() == 0));
/*     */     
/* 368 */     super.a(mouseX, mouseY, f);
/*     */     
/* 370 */     if (this.file == null) {
/*     */       
/* 372 */       c(AbstractionLayer.getFontRenderer(), LocalisationProvider.getLocalisedString("editor.choosefile"), 10, 26, 16777215);
/* 373 */       c(AbstractionLayer.getFontRenderer(), LocalisationProvider.getLocalisedString("editor.createnewfile"), 205, 26, 16777215);
/* 374 */       this.screenBanner = "";
/* 375 */       this.newFileTextBox.g();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClickedEx(int mouseX, int mouseY, int button) {
/* 383 */     if (this.newFileTextBox != null) {
/* 384 */       this.newFileTextBox.a(mouseX, mouseY, button);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseWheelScrolled(int mouseWheelDelta) {
/* 393 */     if (this.textEditor != null) {
/*     */       
/* 395 */       mouseWheelDelta /= 120;
/*     */       
/* 397 */       while (mouseWheelDelta > 0) {
/*     */         
/* 399 */         this.textEditor.scroll(-1);
/* 400 */         mouseWheelDelta--;
/*     */       } 
/*     */       
/* 403 */       while (mouseWheelDelta < 0) {
/*     */         
/* 405 */         this.textEditor.scroll(1);
/* 406 */         mouseWheelDelta++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean accept(File dir, String name) {
/* 414 */     if (name.startsWith(".")) {
/* 415 */       return false;
/*     */     }
/*     */     
/* 418 */     if (name.matches("^[A-Za-z0-9\\x20_\\-\\.]+\\.txt$")) {
/* 419 */       return true;
/*     */     }
/* 421 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void populateFileList() {
/* 426 */     this.fileListBox.clear();
/*     */     
/* 428 */     if (this.fileSuggestion == null) {
/* 429 */       this.fileSuggestion = lastFile;
/*     */     }
/* 431 */     File[] files = MacroModCore.getMacrosDirectory().listFiles(this);
/* 432 */     File selectFile = null;
/*     */     
/* 434 */     if (files != null)
/*     */     {
/* 436 */       for (int fileId = 0; fileId < files.length; fileId++) {
/*     */ 
/*     */         
/* 439 */         amj icon = (files[fileId].getName().startsWith(".") || MacroModCore.checkDisallowedTextFileName(files[fileId].getName())) ? new amj((alq)amk.bV) : new amj(amk.aK);
/* 440 */         this.fileListBox.addItem((IListObject)new ListObjectEditable(fileId, icon, files[fileId].getName(), files[fileId]));
/*     */         
/* 442 */         if (this.fileSuggestion != null && files[fileId].getName().equals(this.fileSuggestion))
/*     */         {
/* 444 */           selectFile = files[fileId];
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 449 */     if (selectFile != null)
/*     */     {
/* 451 */       this.fileListBox.selectData(selectFile);
/*     */     }
/*     */     
/* 454 */     this.fileSuggestion = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMinimiseClick() {
/* 463 */     if (!this.screenDrawMinButton)
/*     */       return; 
/* 465 */     if (MacroModSettings.editorMinimisePromptAction.equals("save")) {
/*     */       
/* 467 */       this.textEditor.save();
/* 468 */       minimise();
/*     */     }
/* 470 */     else if (MacroModSettings.editorMinimisePromptAction.equals("nosave")) {
/*     */       
/* 472 */       minimise();
/*     */ 
/*     */     
/*     */     }
/* 476 */     else if (this.textEditor.isChanged()) {
/*     */       
/* 478 */       AbstractionLayer.displayGuiScreen((bxf)new GuiDialogBoxConfirmWithCheckbox(this, LocalisationProvider.getLocalisedString("editor.title.minimise"), LocalisationProvider.getLocalisedString("editor.prompt.minimise"), LocalisationProvider.getLocalisedString("editor.prompt.saveonmin"), LocalisationProvider.getLocalisedString("editor.prompt.remember")));
/*     */     }
/*     */     else {
/*     */       
/* 482 */       minimise();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void minimise() {
/* 489 */     if (this.parent instanceof GuiMacroBind) {
/*     */       
/* 491 */       GuiMacroBind.minimisedEditor = this;
/* 492 */       AbstractionLayer.displayGuiScreen(null);
/*     */     }
/* 494 */     else if (this.parent instanceof GuiMacroPlayback) {
/*     */       
/* 496 */       GuiMacroPlayback.minimisedEditor = this;
/* 497 */       AbstractionLayer.displayGuiScreen(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCloseClick() {
/* 507 */     if (this.file != null && this.textEditor.isChanged()) {
/*     */       
/* 509 */       this.closeMe = true;
/* 510 */       displayDialog((GuiDialogBox)new GuiDialogBoxYesNoCancel(this, LocalisationProvider.getLocalisedString("gui.save"), LocalisationProvider.getLocalisedString("editor.savechanges"), this.file.getName()));
/*     */     }
/*     */     else {
/*     */       
/* 514 */       close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void close() {
/* 524 */     if (this.textEditor != null) {
/*     */       
/* 526 */       MacroModSettings.showTextEditorSyntax = this.textEditor.highlight;
/* 527 */       MacroModSettings.showTextEditorHelp = this.textEditor.showCommandHelp;
/*     */     } 
/*     */     
/* 530 */     if (this.parent != null) {
/* 531 */       this.parent.onFinishEditingTextFile(this, this.file);
/*     */     } else {
/* 533 */       AbstractionLayer.displayGuiScreen(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDialogClosed(GuiDialogBox dialog) {
/* 539 */     if (dialog instanceof GuiDialogBoxConfirmWithCheckbox) {
/*     */       
/* 541 */       if (((GuiDialogBoxConfirmWithCheckbox)dialog).getChecked()) {
/*     */         
/* 543 */         if (dialog.dialogResult == GuiDialogBox.DialogResult.OK) MacroModSettings.editorMinimisePromptAction = "save"; 
/* 544 */         if (dialog.dialogResult == GuiDialogBox.DialogResult.Cancel) MacroModSettings.editorMinimisePromptAction = "nosave"; 
/* 545 */         MacroModCore.getMacroManager().save();
/*     */       } 
/*     */       
/* 548 */       if (dialog.dialogResult == GuiDialogBox.DialogResult.OK)
/*     */       {
/* 550 */         this.textEditor.save();
/*     */       }
/*     */       
/* 553 */       minimise();
/*     */       
/*     */       return;
/*     */     } 
/* 557 */     if (dialog instanceof GuiDialogBoxYesNoCancel) {
/*     */       
/* 559 */       if (dialog.dialogResult == GuiDialogBox.DialogResult.Yes) {
/*     */         
/* 561 */         this.textEditor.save();
/*     */         
/* 563 */         if (this.closeMe) {
/* 564 */           close();
/*     */         } else {
/* 566 */           AbstractionLayer.displayGuiScreen((bxf)new GuiEditTextFile(this.parent, this.context));
/*     */         } 
/* 568 */       } else if (dialog.dialogResult == GuiDialogBox.DialogResult.No) {
/*     */         
/* 570 */         if (this.closeMe)
/*     */         {
/* 572 */           close();
/*     */         }
/*     */         else
/*     */         {
/* 576 */           this.textEditor.clear();
/* 577 */           AbstractionLayer.displayGuiScreen((bxf)new GuiEditTextFile(this.parent, this.context));
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 582 */         AbstractionLayer.displayGuiScreen((bxf)this);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 587 */     if (dialog.dialogResult == GuiDialogBox.DialogResult.OK)
/*     */     {
/* 589 */       if (dialog instanceof GuiDialogBoxRenameItem) {
/*     */         
/* 591 */         String fileName = MacroModCore.sanitiseFileName(((GuiDialogBoxRenameItem)dialog).getNewItemName());
/* 592 */         if (fileName != null) {
/*     */           
/* 594 */           File targetFile = new File(MacroModCore.getMacrosDirectory(), fileName);
/*     */           
/* 596 */           if (!targetFile.exists()) {
/*     */             try
/*     */             {
/*     */               
/* 600 */               ((GuiDialogBoxRenameItem)dialog).file.renameTo(targetFile);
/* 601 */               populateFileList();
/*     */             }
/* 603 */             catch (Exception ex)
/*     */             {
/* 605 */               Log.printStackTrace(ex);
/*     */             }
/*     */           
/*     */           }
/*     */         } 
/* 610 */       } else if (dialog instanceof GuiDialogBoxConfirm) {
/*     */         
/* 612 */         File deleteFile = (File)this.fileListBox.getSelectedItem().getData();
/*     */         
/* 614 */         if (deleteFile.exists()) {
/*     */           
/*     */           try {
/*     */             
/* 618 */             deleteFile.delete();
/* 619 */             populateFileList();
/*     */           }
/* 621 */           catch (Exception ex) {
/*     */             
/* 623 */             Log.printStackTrace(ex);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 629 */     super.onDialogClosed(dialog);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createFile() {
/* 637 */     if (this.newFileTextBox != null) {
/*     */       
/* 639 */       String fileName = MacroModCore.sanitiseFileName(this.newFileTextBox.b());
/*     */       
/* 641 */       if (fileName != null) {
/*     */         
/* 643 */         this.file = new File(MacroModCore.getMacrosDirectory(), fileName);
/* 644 */         this.screenBanner = LocalisationProvider.getLocalisedString("editor.editing", new Object[] { this.file.getName() });
/* 645 */         b();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String generateHighlightMask(String text) {
/* 653 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String highlight(String text) {
/* 659 */     if (!text.trim().startsWith("//")) {
/*     */       
/* 661 */       text = Macro.highlightParams(text, MacroPlaybackType.OneShot, "￻", "￺");
/* 662 */       text = this.context.getCore().highlight(text);
/*     */     } 
/*     */     
/* 665 */     return text;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\screens\GuiEditTextFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */