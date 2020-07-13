/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bug;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.GuiControl;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.Macro;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.core.MacroPlaybackType;
/*     */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*     */ import net.eq2online.macros.gui.controls.GuiTextEditor;
/*     */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*     */ import net.eq2online.macros.interfaces.IHighlighter;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiEditTextString
/*     */   extends GuiEditText
/*     */   implements IHighlighter
/*     */ {
/*     */   protected String string;
/*     */   private ScriptContext context;
/*     */   
/*     */   public GuiEditTextString(GuiScreenEx parent, String string, String macroName, ScriptContext context) {
/*  25 */     super(parent);
/*  26 */     this.string = string;
/*  27 */     this.screenTitle = LocalisationProvider.getLocalisedString("macro.edit.title");
/*  28 */     this.screenBackgroundSpaceBottom = 28;
/*  29 */     this.screenBanner = LocalisationProvider.getLocalisedString("editor.editing", new Object[] { "<" + macroName + ">" });
/*  30 */     this.context = context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/*  39 */     Keyboard.enableRepeatEvents(true);
/*     */     
/*  41 */     clearControlList();
/*     */ 
/*     */     
/*  44 */     if (this.textEditor == null) {
/*     */       
/*  46 */       this.textEditor = new GuiTextEditor(this.j, this, 0, 7, 27, this.l - 12, this.m - 60, MacroModSettings.showTextEditorSyntax, MacroModSettings.showTextEditorHelp, this.context);
/*  47 */       this.textEditor.setText(this.string);
/*     */     }
/*     */     else {
/*     */       
/*  51 */       this.textEditor.setSizeAndPosition(7, 27, this.l - 12, this.m - 60);
/*     */     } 
/*     */     
/*  54 */     addControl((GuiControl)this.textEditor);
/*  55 */     addControl(new GuiControl(1, this.l - 64, this.m - 24, 60, 20, LocalisationProvider.getLocalisedString("gui.ok")));
/*  56 */     addControl(new GuiControl(2, this.l - 128, this.m - 24, 60, 20, LocalisationProvider.getLocalisedString("gui.cancel")));
/*     */     
/*  58 */     addControl((GuiControl)(this.chkShowHelp = new GuiCheckBox(55, 175, this.m - 24, LocalisationProvider.getLocalisedString("editor.option.help"), MacroModSettings.showTextEditorHelp)));
/*     */     
/*  60 */     super.b();
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
/*  71 */     if (guibutton != null) {
/*     */ 
/*     */       
/*  74 */       if (guibutton.k == 1)
/*     */       {
/*     */         
/*  77 */         close();
/*     */       }
/*     */ 
/*     */       
/*  81 */       if (guibutton.k == 1 || guibutton.k == 2)
/*     */       {
/*  83 */         close();
/*     */       }
/*     */       
/*  86 */       if (guibutton.k == 55) {
/*     */         
/*  88 */         MacroModSettings.showTextEditorHelp = this.chkShowHelp.checked;
/*  89 */         if (this.textEditor != null) this.textEditor.showCommandHelp = this.chkShowHelp.checked;
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) {
/* 100 */     if (keyCode == 1) {
/*     */       
/* 102 */       close();
/*     */       
/*     */       return;
/*     */     } 
/* 106 */     if (this.textEditor != null)
/*     */     {
/* 108 */       this.textEditor.keyTyped(keyChar, keyCode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float f) {
/* 118 */     if (AbstractionLayer.getWorld() == null) {
/* 119 */       c();
/*     */     }
/* 121 */     drawScreenWithEnabledState(mouseX, mouseY, f, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreenWithEnabledState(int mouseX, int mouseY, float f, boolean enabled) {
/* 127 */     a(2, this.m - 26, this.l - 2, this.m - 2, this.screenBackColour);
/*     */     
/* 129 */     super.a(mouseX, mouseY, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void close() {
/* 138 */     if (this.textEditor != null) {
/*     */       
/* 140 */       MacroModSettings.showTextEditorSyntax = this.textEditor.highlight;
/* 141 */       MacroModSettings.showTextEditorHelp = this.textEditor.showCommandHelp;
/*     */     } 
/*     */     
/* 144 */     if (this.parent != null) {
/* 145 */       this.parent.onFinishEditingTextFile(this, null);
/*     */     } else {
/* 147 */       AbstractionLayer.displayGuiScreen(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String generateHighlightMask(String text) {
/* 153 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String highlight(String text) {
/* 159 */     if (!text.trim().startsWith("//")) {
/*     */       
/* 161 */       text = Macro.highlightParams(text, MacroPlaybackType.OneShot, "￻", "￺");
/* 162 */       text = this.context.getCore().highlight(text);
/*     */     } 
/*     */     
/* 165 */     return text;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\screens\GuiEditTextString.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */