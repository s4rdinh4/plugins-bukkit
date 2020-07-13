/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bsu;
/*     */ import bug;
/*     */ import bxf;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.GuiControl;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.list.ListObjectGeneric;
/*     */ import net.eq2online.macros.gui.shared.GuiControlEx;
/*     */ import net.eq2online.macros.interfaces.IListObject;
/*     */ import net.eq2online.macros.scripting.Documentor;
/*     */ import net.eq2online.macros.scripting.IDocumentationEntry;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiCommandReference
/*     */   extends GuiScreenWithHeader
/*     */ {
/*     */   private bxf parentScreen;
/*     */   private GuiListBox commandList;
/*     */   private GuiControl btnOk;
/*     */   private IDocumentationEntry selectedEntry;
/*     */   
/*     */   public GuiCommandReference(bxf parentScreen) {
/*  32 */     super(0, 0);
/*     */     
/*  34 */     this.parentScreen = parentScreen;
/*  35 */     this.screenDrawBackground = false;
/*  36 */     this.screenCentreBanner = false;
/*  37 */     this.screenBannerColour = 4259648;
/*  38 */     this.screenBanner = "";
/*  39 */     this.screenTitle = LocalisationProvider.getLocalisedString("reference.title");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/*  45 */     clearControlList();
/*     */     
/*  47 */     addControl((GuiControl)(this.commandList = new GuiListBox(bsu.z(), 0, 4, 40, 174, this.m - 70, 16, false, false, false)));
/*  48 */     addControl(this.btnOk = new GuiControl(1, this.l - 64, this.m - 24, 60, 20, LocalisationProvider.getLocalisedString("gui.ok")));
/*     */     
/*  50 */     Documentor documentor = (Documentor)ScriptContext.MAIN.getDocumentor();
/*  51 */     int id = 0;
/*  52 */     for (String entry : documentor.getEntries()) {
/*     */       
/*  54 */       IDocumentationEntry documentation = documentor.getDocumentation(entry);
/*  55 */       if (documentation != null && !documentation.isHidden()) {
/*  56 */         this.commandList.addItem((IListObject)new ListObjectGeneric(id++, entry.toUpperCase(), documentation));
/*     */       }
/*     */     } 
/*     */     
/*     */     try {
/*  61 */       a((bug)this.commandList);
/*     */     }
/*  63 */     catch (IOException iOException) {}
/*  64 */     Keyboard.enableRepeatEvents(true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void m() {
/*  70 */     super.m();
/*     */     
/*  72 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseWheelScrolled(int mouseWheelDelta) throws IOException {
/*  79 */     mouseWheelDelta /= 120;
/*     */     
/*  81 */     while (mouseWheelDelta > 0) {
/*     */       
/*  83 */       this.commandList.up();
/*  84 */       a((bug)this.commandList);
/*  85 */       mouseWheelDelta--;
/*     */     } 
/*     */     
/*  88 */     while (mouseWheelDelta < 0) {
/*     */       
/*  90 */       this.commandList.down();
/*  91 */       a((bug)this.commandList);
/*  92 */       mouseWheelDelta++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float partialTick) {
/*  99 */     if (AbstractionLayer.getWorld() == null) {
/* 100 */       c();
/*     */     }
/* 102 */     a(2, 22, 180, 38, -1607257293);
/* 103 */     a(2, 38, 180, this.m - 28, -1342177280);
/* 104 */     a(182, 22, this.l - 2, 38, -1607257293);
/* 105 */     a(182, 38, this.l - 2, 58, -1342177280);
/* 106 */     a(182, 60, this.l - 2, 76, -1607257293);
/* 107 */     a(182, 76, this.l - 2, this.m - 72, -1342177280);
/* 108 */     a(182, this.m - 70, this.l - 2, this.m - 54, -1607257293);
/* 109 */     a(182, this.m - 54, this.l - 2, this.m - 28, -1342177280);
/* 110 */     a(2, this.m - 26, this.l - 2, this.m - 2, -1342177280);
/*     */     
/* 112 */     c(this.q, LocalisationProvider.getLocalisedString("reference.cmdlist"), 8, 26, -256);
/* 113 */     c(this.q, LocalisationProvider.getLocalisedString("reference.syntax"), 190, 26, -256);
/* 114 */     c(this.q, LocalisationProvider.getLocalisedString("reference.desc"), 190, 64, -256);
/* 115 */     c(this.q, LocalisationProvider.getLocalisedString("reference.return"), 190, this.m - 66, -256);
/*     */     
/* 117 */     if (this.selectedEntry != null) {
/*     */       
/* 119 */       c(this.q, formatUsage(), 190, 44, -43691);
/* 120 */       this.q.a(this.selectedEntry.getDescription(), 190, 82, this.l - 210, -22016);
/* 121 */       this.q.a(this.selectedEntry.getReturnType(), 190, this.m - 46, this.l - 210, -22016);
/*     */     } 
/*     */     
/* 124 */     super.a(mouseX, mouseY, partialTick);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String formatUsage() {
/* 132 */     String formattedUsage = this.selectedEntry.getUsage();
/*     */     
/* 134 */     if (formattedUsage.indexOf('(') > -1)
/*     */     {
/* 136 */       formattedUsage = "§c" + this.selectedEntry.getName().toUpperCase() + "§6" + formattedUsage.substring(formattedUsage.indexOf('('));
/*     */     }
/*     */     
/* 139 */     return formattedUsage;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) throws IOException {
/* 145 */     GuiControlEx.KeyHandledState listBoxAction = this.commandList.listBoxKeyTyped(keyChar, keyCode);
/* 146 */     if (listBoxAction == GuiControlEx.KeyHandledState.ActionPerformed) {
/* 147 */       a((bug)this.commandList);
/* 148 */     } else if (listBoxAction == GuiControlEx.KeyHandledState.Handled) {
/*     */       return;
/* 150 */     }  if (keyCode == 1) {
/* 151 */       onCloseClick();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onCloseClick() {
/* 157 */     AbstractionLayer.displayGuiScreen(this.parentScreen);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(bug button) throws IOException {
/* 163 */     super.a(button);
/*     */ 
/*     */     
/*     */     try {
/* 167 */       this.selectedEntry = (IDocumentationEntry)this.commandList.getSelectedItem().getData();
/*     */     }
/* 169 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 173 */     if (button != null && button.k == this.btnOk.k)
/* 174 */       onCloseClick(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\screens\GuiCommandReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */