/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bug;
/*     */ import bxf;
/*     */ import java.io.IOException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.GuiControl;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*     */ import net.eq2online.macros.permissions.MacroModPermissions;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
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
/*     */ public class GuiPermissions
/*     */   extends GuiScreenWithHeader
/*     */ {
/*  30 */   protected static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected bxf parentScreen;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   protected int refreshTimer = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GuiControl btnRefresh;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GuiCheckBox chkWarnings;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ArrayList<String> deniedActionsList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiPermissions(bxf parentScreen) {
/*  64 */     super(0, 0);
/*     */     
/*  66 */     this.parentScreen = parentScreen;
/*  67 */     this.screenTitle = LocalisationProvider.getLocalisedString("macro.permissions.title");
/*  68 */     this.screenBackgroundSpaceBottom = 28;
/*     */     
/*  70 */     refreshPermissions();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refreshPermissions() {
/*  78 */     long lastUpdateTime = MacroModPermissions.getLastUpdateTime();
/*  79 */     String updated = (lastUpdateTime > 0L) ? dateFormat.format(Long.valueOf(lastUpdateTime)) : LocalisationProvider.getLocalisedString("time.never");
/*  80 */     this.screenBanner = LocalisationProvider.getLocalisedString("macro.permissions.banner", new Object[] { updated });
/*     */     
/*  82 */     this.deniedActionsList = ScriptAction.getDeniedActionList(ScriptContext.MAIN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/*  91 */     clearControlList();
/*     */     
/*  93 */     super.b();
/*     */     
/*  95 */     addControl(new GuiControl(0, this.l - 64, this.m - 24, 60, 20, LocalisationProvider.getLocalisedString("gui.ok"), 7));
/*  96 */     addControl(this.btnRefresh = new GuiControl(1, 4, this.m - 24, 120, 20, LocalisationProvider.getLocalisedString("macro.permissions.gui.refresh"), 11));
/*  97 */     addControl((GuiControl)(this.chkWarnings = new GuiCheckBox(2, 6, this.m - 50, LocalisationProvider.getLocalisedString("macro.permissions.option.scriptwarnings"), MacroModSettings.generatePermissionsWarnings)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/* 106 */     super.e();
/*     */     
/* 108 */     if (this.refreshTimer > 0) this.refreshTimer--;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float partialTick) {
/* 117 */     if (AbstractionLayer.getWorld() == null) {
/* 118 */       c();
/*     */     }
/* 120 */     a(2, this.m - 26, this.l - 2, this.m - 2, this.screenBackColour);
/*     */     
/* 122 */     this.btnRefresh.setEnabled((this.refreshTimer == 0));
/*     */     
/* 124 */     super.a(mouseX, mouseY, partialTick);
/*     */     
/* 126 */     int left = 6;
/*     */     
/* 128 */     this.q.a(LocalisationProvider.getLocalisedString("macro.permissions.script.title"), left, 26.0F, -22016);
/* 129 */     this.q.a(LocalisationProvider.getLocalisedString("macro.permissions.script.info1"), left, 38.0F, -1);
/*     */     
/* 131 */     int xPos = left + 4, yPos = 54;
/* 132 */     int spacing = 80;
/*     */     
/* 134 */     for (int i = 0; i < this.deniedActionsList.size(); i++) {
/*     */       
/* 136 */       if (xPos + spacing > this.l) {
/*     */         
/* 138 */         xPos = left + 4;
/* 139 */         yPos += 12;
/*     */       } 
/*     */       
/* 142 */       this.q.a(this.deniedActionsList.get(i), xPos, yPos, -1166541);
/* 143 */       xPos += spacing;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(bug guiButton) {
/* 153 */     if (guiButton.k == 0) {
/*     */       
/* 155 */       MacroModSettings.generatePermissionsWarnings = this.chkWarnings.checked;
/*     */       
/* 157 */       onCloseClick();
/*     */       
/*     */       return;
/*     */     } 
/* 161 */     if (guiButton.k == this.btnRefresh.k) {
/*     */       
/* 163 */       MacroModPermissions.refreshPermissions(this.j);
/* 164 */       this.refreshTimer = 300;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) throws IOException {
/* 174 */     if (keyCode == 1) {
/*     */       
/* 176 */       onCloseClick();
/*     */       
/*     */       return;
/*     */     } 
/* 180 */     super.a(keyChar, keyCode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCloseClick() {
/* 189 */     AbstractionLayer.displayGuiScreen(this.parentScreen);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\screens\GuiPermissions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */