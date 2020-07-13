/*     */ package net.eq2online.macros.gui.shared;
/*     */ 
/*     */ import bsu;
/*     */ import bug;
/*     */ import bxf;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Point;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.GuiControl;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ public abstract class GuiDialogBox
/*     */   extends GuiScreenEx
/*     */ {
/*     */   public static int lastScreenWidth;
/*     */   public static int lastScreenHeight;
/*     */   protected bxf legacyParent;
/*     */   protected GuiScreenEx parentScreen;
/*     */   protected GuiControl btnOk;
/*     */   protected GuiControl btnCancel;
/*     */   protected int dialogX;
/*     */   protected int dialogY;
/*     */   protected int dialogWidth;
/*     */   protected int dialogHeight;
/*     */   protected String dialogTitle;
/*     */   
/*     */   public enum DialogResult
/*     */   {
/*  33 */     None,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  38 */     OK,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  43 */     Cancel,
/*     */     
/*  45 */     Yes,
/*     */     
/*  47 */     No;
/*     */   }
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
/*     */   protected boolean centreTitle = true;
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
/*  80 */   protected int dialogTitleColour = -256;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public DialogResult dialogResult = DialogResult.None;
/*     */ 
/*     */   
/*     */   protected boolean movable = false;
/*     */ 
/*     */   
/*     */   protected boolean dragging = false;
/*     */ 
/*     */   
/*  94 */   protected Point dragOffset = new Point(0, 0);
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
/*     */   public GuiDialogBox(GuiScreenEx parentScreen, int width, int height, String windowTitle) {
/* 106 */     this.parentScreen = parentScreen;
/* 107 */     this.legacyParent = parentScreen;
/* 108 */     this.dialogWidth = width;
/* 109 */     this.dialogHeight = height;
/* 110 */     this.dialogTitle = windowTitle;
/*     */     
/* 112 */     this.generateMouseDragEvents = true;
/* 113 */     this.e = 500.0F;
/*     */   }
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
/*     */   public GuiDialogBox(bxf parentScreen, int width, int height, String windowTitle) {
/* 126 */     this.legacyParent = parentScreen;
/* 127 */     this.dialogWidth = width;
/* 128 */     this.dialogHeight = height;
/* 129 */     this.dialogTitle = windowTitle;
/*     */     
/* 131 */     this.generateMouseDragEvents = true;
/* 132 */     this.e = 500.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void closeDialog() {
/* 140 */     if (this.parentScreen != null) {
/*     */       
/* 142 */       this.parentScreen.onDialogClosed(this);
/*     */     }
/* 144 */     else if (this.legacyParent != null) {
/*     */       
/* 146 */       AbstractionLayer.displayGuiScreen(this.legacyParent);
/*     */     }
/*     */     else {
/*     */       
/* 150 */       AbstractionLayer.displayGuiScreen(null);
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
/* 162 */     if (guibutton.k == this.btnCancel.k) {
/*     */       
/* 164 */       this.dialogResult = DialogResult.Cancel;
/* 165 */       closeDialog();
/*     */     } 
/* 167 */     if (guibutton.k == this.btnOk.k)
/*     */     {
/* 169 */       if (validateDialog()) {
/*     */         
/* 171 */         this.dialogResult = DialogResult.OK;
/* 172 */         onSubmit();
/* 173 */         closeDialog();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void a(char keyChar, int keyCode) {
/* 184 */     if (keyCode == 1) {
/*     */       
/* 186 */       a((bug)this.btnCancel);
/*     */     }
/* 188 */     else if (keyCode == 28 || keyCode == 156) {
/*     */       
/* 190 */       a((bug)this.btnOk);
/*     */     }
/*     */     else {
/*     */       
/* 194 */       dialogKeyTyped(keyChar, keyCode);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void a(int mouseX, int mouseY, int button) throws IOException {
/* 204 */     if (button == 0 && this.movable && mouseX > this.dialogX && mouseX < this.dialogX + this.dialogWidth && mouseY > this.dialogY - 18 && mouseY < this.dialogY) {
/*     */       
/* 206 */       this.dragOffset = new Point(mouseX - this.dialogX, mouseY - this.dialogY);
/* 207 */       this.dragging = true;
/*     */     }
/*     */     else {
/*     */       
/* 211 */       dialogMouseClicked(mouseX, mouseY, button);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogMouseClicked(int mouseX, int mouseY, int button) throws IOException {
/* 222 */     super.a(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void b(int mouseX, int mouseY, int button) {
/* 231 */     if (this.dragging) {
/*     */       
/* 233 */       this.dialogX = mouseX - this.dragOffset.x;
/* 234 */       this.dialogY = mouseY - this.dragOffset.y;
/* 235 */       b();
/*     */     } 
/*     */     
/* 238 */     if (button == 0 && this.dragging) {
/*     */       
/* 240 */       if (this.dialogX < 0) this.dialogX = 0; 
/* 241 */       if (this.dialogX > this.l) this.dialogX = this.l - this.dialogWidth; 
/* 242 */       if (this.dialogY < 9) this.dialogY = 18; 
/* 243 */       if (this.dialogY > this.m) this.dialogY = this.m - this.dialogHeight; 
/* 244 */       b();
/* 245 */       this.dragging = false;
/*     */       
/*     */       return;
/*     */     } 
/* 249 */     super.b(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void b() {
/* 258 */     super.b();
/*     */     
/* 260 */     Keyboard.enableRepeatEvents(true);
/*     */ 
/*     */     
/* 263 */     if (this.legacyParent != null) {
/* 264 */       this.legacyParent.b();
/*     */     }
/* 266 */     if (!this.dragging) {
/*     */ 
/*     */       
/* 269 */       this.dialogX = (this.l - this.dialogWidth) / 2;
/* 270 */       this.dialogY = (this.m - this.dialogHeight) / 2;
/*     */     } 
/*     */     
/* 273 */     this.btnOk = new GuiControl(-1, this.dialogX + this.dialogWidth - 62, this.dialogY + this.dialogHeight - 22, 60, 20, LocalisationProvider.getLocalisedString("gui.ok"));
/* 274 */     this.btnCancel = new GuiControl(-2, this.dialogX + this.dialogWidth - 124, this.dialogY + this.dialogHeight - 22, 60, 20, LocalisationProvider.getLocalisedString("gui.cancel"));
/*     */     
/* 276 */     clearControlList();
/* 277 */     addControl(this.btnOk);
/* 278 */     addControl(this.btnCancel);
/*     */     
/* 280 */     lastScreenWidth = this.l;
/* 281 */     lastScreenHeight = this.m;
/*     */     
/* 283 */     initDialog();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void m() {
/* 289 */     Keyboard.enableRepeatEvents(false);
/*     */     
/* 291 */     onDialogClosed();
/*     */     
/* 293 */     super.m();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(bsu minecraft, int width, int height) {
/* 299 */     super.a(minecraft, width, height);
/*     */     
/* 301 */     if (this.legacyParent != null)
/*     */     {
/* 303 */       this.legacyParent.a(minecraft, width, height);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void a(int mouseX, int mouseY, float partialTick) {
/* 310 */     drawParentScreen(mouseX, mouseY, partialTick);
/*     */     
/* 312 */     GL.glClear(256);
/*     */     
/* 314 */     GL.glPushMatrix();
/* 315 */     GL.glTranslatef(0.0F, 0.0F, 20.0F);
/* 316 */     GL.glEnableDepthTest();
/*     */     
/* 318 */     int backColour = -1442840576;
/* 319 */     int backColour2 = -869059789;
/*     */ 
/*     */     
/* 322 */     a(this.dialogX, this.dialogY - 18, this.dialogX + this.dialogWidth, this.dialogY, backColour2);
/*     */     
/* 324 */     if (this.centreTitle) {
/* 325 */       a(this.q, this.dialogTitle, this.dialogX + this.dialogWidth / 2, this.dialogY - 13, this.dialogTitleColour);
/*     */     } else {
/* 327 */       c(this.q, this.dialogTitle, this.dialogX + 5, this.dialogY - 13, this.dialogTitleColour);
/*     */     } 
/*     */     
/* 330 */     a(this.dialogX, this.dialogY, this.dialogX + this.dialogWidth, this.dialogY + this.dialogHeight, backColour);
/*     */ 
/*     */     
/* 333 */     drawDialog(mouseX, mouseY, partialTick);
/*     */ 
/*     */     
/* 336 */     super.a(mouseX, mouseY, partialTick);
/*     */     
/* 338 */     postRender(mouseX, mouseY, partialTick);
/*     */     
/* 340 */     renderShadow();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderShadow() {
/* 348 */     GL.glPopMatrix();
/*     */     
/* 350 */     int alphaFunc = GL.glGetInteger(3009);
/* 351 */     float alphaRef = GL.glGetFloat(3010);
/* 352 */     GL.glEnableDepthTest();
/* 353 */     GL.glDepthFunc(515);
/* 354 */     GL.glAlphaFunc(516, 0.0F);
/* 355 */     GL.glEnableBlend();
/* 356 */     this.e = 0.0F;
/* 357 */     drawTessellatedModalBorderRect(ResourceLocations.EXT, 128, this.dialogX - 6, this.dialogY - 24, this.dialogX + this.dialogWidth + 6, this.dialogY + this.dialogHeight + 6, 96, 80, 128, 112, 16);
/* 358 */     this.e = 500.0F;
/* 359 */     setTexMapSize(256);
/*     */     
/* 361 */     GL.glAlphaFunc(alphaFunc, alphaRef);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawParentScreen(int mouseX, int mouseY, float partialTick) {
/* 369 */     if (this.parentScreen != null) {
/*     */ 
/*     */       
/* 372 */       this.parentScreen.drawScreenWithEnabledState(0, 0, partialTick, false);
/*     */     }
/* 374 */     else if (this.legacyParent != null) {
/*     */       
/* 376 */       this.legacyParent.a(0, 0, partialTick);
/* 377 */       a(0, 0, this.l, this.m, -1442840576);
/*     */     }
/* 379 */     else if (AbstractionLayer.getWorld() == null) {
/*     */       
/* 381 */       c();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void drawDialog(int mouseX, int mouseY, float f) {}
/*     */   
/*     */   protected void postRender(int mouseX, int mouseY, float f) {}
/*     */   
/*     */   public abstract void onSubmit();
/*     */   
/*     */   public void onDialogSubmissionFailed(String reason) {}
/*     */   
/*     */   public abstract boolean validateDialog();
/*     */   
/*     */   protected void dialogKeyTyped(char keyChar, int keyCode) {}
/*     */   
/*     */   protected void initDialog() {}
/*     */   
/*     */   protected void onDialogClosed() {}
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\shared\GuiDialogBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */