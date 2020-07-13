/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bxf;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.controls.GuiDropDownMenu;
/*     */ import net.eq2online.macros.gui.controls.GuiMiniToolbarButton;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiLayout;
/*     */ import net.eq2online.macros.gui.designable.LayoutManager;
/*     */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import org.lwjgl.input.Mouse;
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
/*     */ public class GuiCustomGui
/*     */   extends GuiScreenEx
/*     */ {
/*     */   protected static boolean haveMouseCoords = false;
/*     */   protected static int lastMouseX;
/*     */   protected static int lastMouseY;
/*     */   protected Macros macros;
/*  41 */   protected bxf parentScreen = null;
/*     */   
/*     */   protected String prompt;
/*     */   
/*     */   protected GuiMiniToolbarButton btnGui;
/*     */   
/*     */   protected GuiMiniToolbarButton btnBind;
/*     */   
/*     */   protected GuiMiniToolbarButton btnEditFile;
/*     */   protected GuiMiniToolbarButton btnOptions;
/*  51 */   protected ArrayList<GuiMiniToolbarButton> miniButtons = new ArrayList<GuiMiniToolbarButton>();
/*     */   
/*     */   protected DesignableGuiLayout layout;
/*     */   
/*     */   protected DesignableGuiLayout backLayout;
/*     */   protected Rectangle boundingBox;
/*  57 */   protected GuiDropDownMenu contextMenu = new GuiDropDownMenu(true);
/*     */   
/*  59 */   protected Point contextMenuLocation = new Point();
/*     */   
/*  61 */   protected DesignableGuiControl clickedControl = null;
/*     */ 
/*     */   
/*     */   public GuiCustomGui(String layout, String backLayout) {
/*  65 */     this(LayoutManager.getLayout(layout), (bxf)null);
/*     */     
/*  67 */     if (backLayout != null)
/*     */     {
/*  69 */       this.backLayout = LayoutManager.getLayout(backLayout);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiCustomGui(DesignableGuiLayout layout, bxf parentScreen) {
/*  75 */     this.macros = MacroModCore.getMacroManager();
/*  76 */     this.parentScreen = parentScreen;
/*  77 */     this.layout = layout;
/*     */     
/*  79 */     this.contextMenu.addItem("execute", LocalisationProvider.getLocalisedString("layout.contextmenu.execute"));
/*  80 */     this.contextMenu.addItem("edit", LocalisationProvider.getLocalisedString("layout.contextmenu.edit"));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPrompt() {
/*  85 */     return this.prompt;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPrompt(String prompt) {
/*  90 */     this.prompt = prompt;
/*     */   }
/*     */ 
/*     */   
/*     */   public DesignableGuiLayout getLayout() {
/*  95 */     return this.layout;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/* 101 */     super.b();
/*     */     
/* 103 */     if (this.layout != null)
/*     */     {
/* 105 */       this.prompt = this.layout.displayName;
/*     */     }
/*     */     
/* 108 */     this.miniButtons.clear();
/* 109 */     this.miniButtons.add(this.btnGui = new GuiMiniToolbarButton(this.j, 4, 104, 64));
/*     */     
/* 111 */     this.boundingBox = new Rectangle(0, 0, this.l, this.m - 14);
/*     */     
/* 113 */     if (haveMouseCoords) {
/* 114 */       Mouse.setCursorPosition(lastMouseX, lastMouseY);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) {
/* 123 */     if (this.updateCounter < 1)
/*     */       return; 
/* 125 */     if (keyCode == 1)
/*     */     {
/* 127 */       if (this.backLayout != null) {
/*     */         
/* 129 */         AbstractionLayer.displayGuiScreen((bxf)new GuiCustomGui(this.backLayout, null));
/*     */       }
/*     */       else {
/*     */         
/* 133 */         AbstractionLayer.displayGuiScreen(this.parentScreen);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/* 141 */     if (this.layout != null)
/*     */     {
/* 143 */       this.layout.onTick();
/*     */     }
/*     */     
/* 146 */     super.e();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float partialTicks) {
/* 155 */     preDrawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 157 */     GL.glDisableLighting();
/* 158 */     GL.glDisableDepthTest();
/*     */     
/* 160 */     drawControls(mouseX, mouseY);
/* 161 */     postDrawControls(mouseX, mouseY, partialTicks);
/*     */     
/* 163 */     int overMiniButton = drawMiniButtons(mouseX, mouseY, -1342177280);
/* 164 */     super.a(mouseX, mouseY, partialTicks);
/* 165 */     postDrawScreen(mouseX, mouseY, partialTicks);
/* 166 */     drawMiniButtonToolTips(mouseX, mouseY, overMiniButton, -1342177280);
/* 167 */     this.contextMenu.drawControlAt(this.contextMenuLocation, mouseX, mouseY);
/*     */     
/* 169 */     haveMouseCoords = true;
/* 170 */     lastMouseX = Mouse.getX();
/* 171 */     lastMouseY = Mouse.getY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void preDrawScreen(int mouseX, int mouseY, float partialTicks) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postDrawControls(int mouseX, int mouseY, float partialTicks) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postDrawScreen(int mouseX, int mouseY, float partialTicks) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int drawMiniButtons(int mouseX, int mouseY, int backColour) {
/* 197 */     int overMiniButton = 0;
/* 198 */     int rightMargin = 2;
/*     */     
/* 200 */     if (this.btnOptions != null) {
/*     */       
/* 202 */       if (this.btnOptions.drawControlAt(this.j, mouseX, mouseY, this.l - rightMargin - 18, this.m - 14, -16716288, backColour)) overMiniButton = 3; 
/* 203 */       rightMargin += 20;
/*     */     } 
/*     */     
/* 206 */     if (this.btnEditFile != null) {
/*     */       
/* 208 */       if (this.btnEditFile.drawControlAt(this.j, mouseX, mouseY, this.l - rightMargin - 18, this.m - 14, -16716288, backColour)) overMiniButton = 2; 
/* 209 */       rightMargin += 20;
/*     */     } 
/*     */     
/* 212 */     if (this.btnBind != null) {
/*     */       
/* 214 */       if (this.btnBind.drawControlAt(this.j, mouseX, mouseY, this.l - rightMargin - 18, this.m - 14, -16716288, backColour)) overMiniButton = 1; 
/* 215 */       rightMargin += 20;
/*     */     } 
/*     */     
/* 218 */     if (this.btnGui != null) {
/*     */       
/* 220 */       if (this.btnGui.drawControlAt(this.j, mouseX, mouseY, this.l - rightMargin - 18, this.m - 14, -16716288, backColour)) overMiniButton = 4; 
/* 221 */       rightMargin += 20;
/*     */     } 
/*     */     
/* 224 */     a(2, this.m - 14, this.l - rightMargin, this.m - 2, backColour);
/* 225 */     c(this.q, this.prompt, 4, this.m - 12, 60928);
/* 226 */     return overMiniButton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawMiniButtonToolTips(int mouseX, int mouseY, int overMiniButton, int backColour) {
/* 237 */     if (overMiniButton == 4) drawTooltip(LocalisationProvider.getLocalisedString("tooltip.guiedit"), mouseX, mouseY, -1118482, backColour); 
/* 238 */     if (overMiniButton == 1) drawTooltip(LocalisationProvider.getLocalisedString("tooltip.bind"), mouseX, mouseY, -1118482, backColour); 
/* 239 */     if (overMiniButton == 2) drawTooltip(LocalisationProvider.getLocalisedString("tooltip.editfile"), mouseX, mouseY, -1118482, backColour); 
/* 240 */     if (overMiniButton == 3) drawTooltip(LocalisationProvider.getLocalisedString("tooltip.options"), mouseX, mouseY, -1118482, backColour);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawControls(int mouseX, int mouseY) {
/* 249 */     if (this.layout != null && this.boundingBox != null)
/*     */     {
/* 251 */       this.layout.draw(this.boundingBox, mouseX, mouseY);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(int mouseX, int mouseY, int button) throws IOException {
/* 261 */     if (contextMenuClicked(mouseX, mouseY, button))
/* 262 */       return;  if (miniToolbarClicked(mouseX, mouseY))
/* 263 */       return;  if (controlClicked(mouseX, mouseY, button))
/* 264 */       return;  super.a(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean contextMenuClicked(int mouseX, int mouseY, int button) {
/* 274 */     String menuItem = this.contextMenu.mousePressed(mouseX, mouseY);
/* 275 */     if (menuItem != null && this.clickedControl != null) {
/*     */       
/* 277 */       if (menuItem.equals("execute")) {
/*     */         
/* 279 */         if (this.clickedControl.getWidgetIsBindable())
/*     */         {
/* 281 */           onControlClicked(this.clickedControl);
/*     */         }
/*     */       }
/* 284 */       else if (menuItem.equals("edit")) {
/*     */         
/* 286 */         if (this.clickedControl.getWidgetIsBindable())
/*     */         {
/* 288 */           AbstractionLayer.displayGuiScreen((bxf)new GuiMacroEdit(this.clickedControl.id, (bxf)this));
/*     */         }
/*     */       }
/* 291 */       else if (menuItem.equals("design")) {
/*     */         
/* 293 */         AbstractionLayer.displayGuiScreen((bxf)new GuiMacroBind(2, (bxf)this));
/*     */       } 
/*     */       
/* 296 */       return true;
/*     */     } 
/*     */     
/* 299 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean miniToolbarClicked(int mouseX, int mouseY) {
/* 309 */     for (GuiMiniToolbarButton miniButton : this.miniButtons) {
/*     */       
/* 311 */       if (miniButton.c(this.j, mouseX, mouseY)) {
/*     */         
/* 313 */         onMiniButtonClicked(miniButton);
/* 314 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 318 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean controlClicked(int mouseX, int mouseY, int button) {
/* 328 */     if (this.layout != null && this.boundingBox != null) {
/*     */       
/* 330 */       this.clickedControl = this.layout.getControlAt(this.boundingBox, mouseX, mouseY, -1);
/*     */       
/* 332 */       if (this.clickedControl != null && !this.clickedControl.isVisible()) {
/* 333 */         this.clickedControl = null;
/*     */       }
/* 335 */       if (this.clickedControl != null) {
/*     */         
/* 337 */         if (button == 0) {
/*     */           
/* 339 */           onControlClicked(this.clickedControl);
/*     */         }
/* 341 */         else if (button == 1) {
/*     */           
/* 343 */           this.contextMenu.showDropDown();
/* 344 */           Dimension contextMenuSize = this.contextMenu.getSize();
/* 345 */           this.contextMenuLocation = new Point(Math.min(mouseX, this.l - contextMenuSize.width), Math.min(mouseY - 8, this.m - contextMenuSize.height));
/*     */         } 
/*     */ 
/*     */         
/* 349 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 353 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onControlClicked(DesignableGuiControl control) {
/* 358 */     if (control.getCloseGuiOnClick()) AbstractionLayer.displayGuiScreen(null); 
/* 359 */     this.macros.playMacro(control.id, false, ScriptContext.MAIN, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onUnhandledMouseClick(int mouseX, int mouseY, int button) throws IOException {
/* 369 */     super.a(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onMiniButtonClicked(GuiMiniToolbarButton button) {
/* 374 */     if (this.btnGui != null && button.k == this.btnGui.k) {
/* 375 */       AbstractionLayer.displayGuiScreen((bxf)new GuiDesigner(this.layout, (bxf)this, true));
/*     */     }
/* 377 */     if (this.btnBind != null && button.k == this.btnBind.k) {
/* 378 */       AbstractionLayer.displayGuiScreen((bxf)new GuiMacroBind(true));
/*     */     }
/* 380 */     if (this.btnEditFile != null && button.k == this.btnEditFile.k) {
/* 381 */       AbstractionLayer.displayGuiScreen((bxf)new GuiEditTextFile(this, ScriptContext.MAIN));
/*     */     }
/* 383 */     if (this.btnOptions != null && button.k == this.btnOptions.k)
/* 384 */       AbstractionLayer.displayGuiScreen((bxf)new GuiMacroConfig(this, false)); 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\screens\GuiCustomGui.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */