/*     */ package net.eq2online.macros.modules.chatfilter.gui;
/*     */ 
/*     */ import bug;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*     */ import net.eq2online.macros.gui.screens.GuiEditTextString;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroBind;
/*     */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*     */ import net.eq2online.macros.modules.chatfilter.ChatFilterManager;
/*     */ import net.eq2online.macros.modules.chatfilter.ChatFilterTemplate;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ 
/*     */ 
/*     */ public class GuiEditChatFilter
/*     */   extends GuiEditTextString
/*     */ {
/*     */   private ChatFilterTemplate template;
/*     */   private boolean global;
/*     */   private GuiCheckBox chkGlobal;
/*     */   
/*     */   public GuiEditChatFilter(GuiMacroBind parent) {
/*  23 */     super((GuiScreenEx)parent, getTemplateText(), "Chat Filter", ScriptContext.CHATFILTER);
/*     */     
/*  25 */     this.template = ChatFilterManager.getInstance().getTemplate(MacroModCore.getMacroManager().getActiveConfig(), true);
/*  26 */     this.global = this.template.global;
/*  27 */     this.screenTitle = LocalisationProvider.getLocalisedString("editor.editing", new Object[] { "<Chat Filter>" });
/*  28 */     this.screenBannerColour = 4259648;
/*  29 */     this.screenCentreBanner = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/*  38 */     super.b();
/*     */     
/*  40 */     getControlList().add(this.chkGlobal = new GuiCheckBox(33, 6, this.m - 24, LocalisationProvider.getLocalisedString("macro.option.global"), this.global));
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getTemplateText() {
/*  45 */     ChatFilterTemplate template = ChatFilterManager.getInstance().getTemplate(MacroModCore.getMacroManager().getActiveConfig(), true);
/*  46 */     return unfoldTemplate(template.getKeyDownMacro());
/*     */   }
/*     */ 
/*     */   
/*     */   private static String unfoldTemplate(String template) {
/*  51 */     return template.replaceAll("\\x82", "\r\n");
/*     */   }
/*     */ 
/*     */   
/*     */   private static String foldTemplate(String template) {
/*  56 */     return template.replaceAll("\\r?\\n", "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(bug guibutton) {
/*  65 */     if (guibutton != null) {
/*     */       
/*  67 */       if (guibutton.k == 1) {
/*     */         
/*  69 */         String newText = this.textEditor.getText();
/*  70 */         this.template.setKeyDownMacro(foldTemplate(newText));
/*  71 */         this.template.global = this.global;
/*  72 */         ChatFilterManager.getInstance().save();
/*     */       } 
/*     */       
/*  75 */       if (guibutton.k == this.chkGlobal.k)
/*     */       {
/*  77 */         this.global = this.chkGlobal.checked;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  82 */     super.a(guibutton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseWheelScrolled(int mouseWheelDelta) {
/*  91 */     if (this.textEditor != null) {
/*     */       
/*  93 */       mouseWheelDelta /= 120;
/*     */       
/*  95 */       while (mouseWheelDelta > 0) {
/*     */         
/*  97 */         this.textEditor.scroll(-1);
/*  98 */         mouseWheelDelta--;
/*     */       } 
/*     */       
/* 101 */       while (mouseWheelDelta < 0) {
/*     */         
/* 103 */         this.textEditor.scroll(1);
/* 104 */         mouseWheelDelta++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float f) {
/* 115 */     this.screenBanner = LocalisationProvider.getLocalisedString("macro.currentconfig", new Object[] { this.global ? ("§e" + LocalisationProvider.getLocalisedString("macro.config.global")) : MacroModCore.getMacroManager().getActiveConfigName() });
/*     */     
/* 117 */     super.a(mouseX, mouseY, f);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onHeaderClick() {
/* 123 */     this.global = !this.global;
/* 124 */     this.chkGlobal.checked = this.global;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\modules\chatfilter\gui\GuiEditChatFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */