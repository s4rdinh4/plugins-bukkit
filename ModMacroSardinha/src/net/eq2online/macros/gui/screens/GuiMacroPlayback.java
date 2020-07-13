/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bxf;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.gui.controls.GuiMiniToolbarButton;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*     */ import net.eq2online.macros.gui.designable.LayoutManager;
/*     */ import net.eq2online.macros.input.InputHandler;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
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
/*     */ public class GuiMacroPlayback
/*     */   extends GuiCustomGui
/*     */ {
/*     */   protected boolean activeOverride;
/*     */   public static GuiEditTextFile minimisedEditor;
/*     */   
/*     */   public GuiMacroPlayback() {
/*  32 */     this(false, (bxf)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiMacroPlayback(boolean handleOverride, bxf parentScreen) {
/*  37 */     super(LayoutManager.getBoundLayout("playback", false), parentScreen);
/*     */     
/*  39 */     this.activeOverride = handleOverride;
/*  40 */     this.p = handleOverride;
/*  41 */     this.prompt = LocalisationProvider.getLocalisedString("macro.prompt.execute");
/*     */     
/*  43 */     this.contextMenu.addItem("design", LocalisationProvider.getLocalisedString("layout.contextmenu.design"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/*  52 */     super.b();
/*     */     
/*  54 */     this.prompt = LocalisationProvider.getLocalisedString("macro.prompt.execute");
/*     */     
/*  56 */     this.miniButtons.add(this.btnBind = new GuiMiniToolbarButton(this.j, 0, 104, 48));
/*  57 */     this.miniButtons.add(this.btnEditFile = new GuiMiniToolbarButton(this.j, 1, 104, 16));
/*  58 */     this.miniButtons.add(this.btnOptions = new GuiMiniToolbarButton(this.j, 2, 104, 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) {
/*  67 */     if (this.updateCounter < 1)
/*     */       return; 
/*  69 */     if (keyCode == 1) {
/*     */       
/*  71 */       AbstractionLayer.displayGuiScreen(this.activeOverride ? this.parentScreen : null);
/*     */       
/*     */       return;
/*     */     } 
/*  75 */     if (MacroModSettings.enableOverride && keyCode == InputHandler.getOverrideKeyCode()) {
/*     */       
/*  77 */       AbstractionLayer.displayGuiScreen(this.activeOverride ? this.parentScreen : null);
/*     */       
/*     */       return;
/*     */     } 
/*  81 */     if (keyCode > 0) {
/*     */       
/*  83 */       MacroModCore.getInstance().getInputHandler().notifyKeyDown(keyCode);
/*  84 */       if (!this.activeOverride) AbstractionLayer.displayGuiScreen(null); 
/*  85 */       this.macros.playMacro(keyCode, this.activeOverride, ScriptContext.MAIN, null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void l() throws IOException {
/*  96 */     if (this.activeOverride && !InputHandler.isKeyDown(InputHandler.getOverrideKeyCode())) {
/*     */       
/*  98 */       AbstractionLayer.displayGuiScreen(this.parentScreen);
/*     */       
/*     */       return;
/*     */     } 
/* 102 */     super.l();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/* 111 */     if (!this.activeOverride && minimisedEditor != null) {
/*     */       
/* 113 */       AbstractionLayer.displayGuiScreen((bxf)minimisedEditor);
/* 114 */       minimisedEditor = null;
/*     */       
/*     */       return;
/*     */     } 
/* 118 */     this.layout = LayoutManager.getBoundLayout("playback", false);
/*     */     
/* 120 */     super.e();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postDrawControls(int mouseX, int mouseY, float partialTicks) {
/* 130 */     if (!this.activeOverride || MacroModSettings.enableStatus)
/*     */     {
/* 132 */       this.macros.drawPlaybackStatus(this.q, 4, 10, this.l, this.m, mouseX, mouseY, true);
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
/*     */   protected void preDrawScreen(int mouseX, int mouseY, float partialTicks) {
/* 144 */     if (this.activeOverride && this.parentScreen != null)
/*     */     {
/* 146 */       this.parentScreen.a(mouseX, mouseY, partialTicks);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(int mouseX, int mouseY, int button) throws IOException {
/* 156 */     if (contextMenuClicked(mouseX, mouseY, button))
/* 157 */       return;  if (miniToolbarClicked(mouseX, mouseY))
/* 158 */       return;  if (handleSpecialClick(mouseX, mouseY, button))
/* 159 */       return;  if (controlClicked(mouseX, mouseY, button))
/*     */       return; 
/* 161 */     a(' ', button + 250);
/* 162 */     onUnhandledMouseClick(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleSpecialClick(int mouseX, int mouseY, int button) {
/* 173 */     if ((!this.activeOverride || MacroModSettings.enableStatus) && button == 0)
/*     */     {
/* 175 */       return this.macros.stopActiveMacroAt(mouseX, mouseY);
/*     */     }
/*     */     
/* 178 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onControlClicked(DesignableGuiControl control) {
/* 187 */     if (!this.activeOverride && control.getCloseGuiOnClick()) AbstractionLayer.displayGuiScreen(null); 
/* 188 */     this.macros.playMacro(control.id, this.activeOverride, ScriptContext.MAIN, null);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\screens\GuiMacroPlayback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */