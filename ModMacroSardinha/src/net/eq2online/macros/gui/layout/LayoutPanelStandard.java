/*     */ package net.eq2online.macros.gui.layout;
/*     */ 
/*     */ import bsu;
/*     */ import bty;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.GuiControl;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.core.MacroType;
/*     */ import net.eq2online.macros.interfaces.ILayoutWidget;
/*     */ import net.eq2online.macros.interfaces.ISaveSettings;
/*     */ import net.eq2online.macros.interfaces.ISettingsProvider;
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
/*     */ public class LayoutPanelStandard
/*     */   extends LayoutPanel<LayoutWidget>
/*     */   implements ISaveSettings
/*     */ {
/*     */   protected GuiControl btnCopy;
/*     */   protected GuiControl btnMove;
/*     */   protected GuiControl btnDelete;
/*     */   protected GuiControl btnEdit;
/*  41 */   protected Pattern layoutPattern = Pattern.compile("\\{([0-9]+),([0-9]+),([0-9]+)\\}");
/*     */ 
/*     */ 
/*     */   
/*     */   protected int lastMouseX;
/*     */ 
/*     */   
/*     */   protected int lastMouseY;
/*     */ 
/*     */   
/*  51 */   protected String layoutSettingName = "keyboard.layout";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   protected String defaultLayout = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   protected String layoutSettingDescription = "Serialised version of the keyboard layout, each param is {MappingID,X,Y}";
/*     */   
/*  63 */   protected float scaleFactor = 1.0F;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int drawX;
/*     */ 
/*     */   
/*     */   protected int drawY;
/*     */ 
/*     */ 
/*     */   
/*     */   protected LayoutPanelStandard(bsu minecraft, int controlId, String layoutPanelSettingName, MacroType type) {
/*  75 */     super(minecraft, controlId, type);
/*     */     
/*  77 */     this.layoutSettingName = layoutPanelSettingName;
/*  78 */     this.layoutSettingDescription = "Serialised version of the keyboard layout, each param is {MappingID,X,Y}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init() {
/*  87 */     this.btnCopy = new GuiControl(1, getXPosition() + 4, getYPosition() + getHeight() - 24, 60, 20, LocalisationProvider.getLocalisedString("gui.copy"), 0);
/*  88 */     this.btnMove = new GuiControl(2, getXPosition() + 68, getYPosition() + getHeight() - 24, 60, 20, LocalisationProvider.getLocalisedString("gui.move"), 2);
/*  89 */     this.btnDelete = new GuiControl(3, getXPosition() + 132, getYPosition() + getHeight() - 24, 60, 20, LocalisationProvider.getLocalisedString("gui.delete"), 1);
/*  90 */     this.btnEdit = new GuiControl(4, getXPosition() + 196, getYPosition() + getHeight() - 24, 60, 20, LocalisationProvider.getLocalisedString("gui.edit"), 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSizeAndPosition(int left, int top, int controlWidth, int controlHeight) {
/* 100 */     if (MacroModSettings.autoScaleKeyboard) {
/*     */       
/* 102 */       this.scaleFactor = Math.min(controlWidth / 427.0F, controlHeight / 202.0F);
/*     */       
/* 104 */       this.drawX = left;
/* 105 */       this.drawY = top;
/*     */       
/* 107 */       controlWidth = 427;
/* 108 */       controlHeight = 202;
/* 109 */       left = 0;
/* 110 */       top = 0;
/*     */     } 
/*     */     
/* 113 */     super.setSizeAndPosition(left, top, controlWidth, controlHeight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ILayoutWidget getWidgetAt(int mouseX, int mouseY) {
/* 123 */     if (MacroModSettings.autoScaleKeyboard) {
/*     */       
/* 125 */       mouseX = adjustMouse(this.drawX, mouseX);
/* 126 */       mouseY = adjustMouse(this.drawY, mouseY);
/*     */     } 
/*     */     
/* 129 */     return getWidgetAtAdjustedPosition(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ILayoutWidget getWidgetAtAdjustedPosition(int mouseX, int mouseY) {
/* 139 */     if (mouseX < getXPosition() || mouseY < getYPosition() || mouseX > getXPosition() + getWidth() || mouseY > getYPosition() + getWidth()) {
/* 140 */       return null;
/*     */     }
/* 142 */     ILayoutWidget topMost = null;
/*     */     
/* 144 */     for (int i = this.macroType.getMinId(); i <= this.macroType.getAbsoluteMaxId(); i++) {
/*     */       
/* 146 */       if (this.widgets[i] != null && this.widgets[i].mouseOver(null, mouseX - getXPosition(), mouseY - getYPosition(), false))
/*     */       {
/* 148 */         if (topMost == null || this.widgets[i].getWidgetZIndex() > topMost.getWidgetZIndex()) {
/* 149 */           topMost = this.widgets[i];
/*     */         }
/*     */       }
/*     */     } 
/* 153 */     return topMost;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ILayoutWidget createWidget(int widgetId) {
/* 163 */     return new LayoutButton((bty)MacroModCore.getInstance().getLegacyFontRenderer(), widgetId, this.macroType.getName(widgetId), this.widgetWidth, this.widgetHeight, this.centreAlign);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadPanelLayout(String panelLayout) {
/* 174 */     Matcher layoutPatternMatcher = this.layoutPattern.matcher(panelLayout);
/*     */     
/* 176 */     this.widgets = new ILayoutWidget[10000];
/*     */     
/* 178 */     while (layoutPatternMatcher.find()) {
/*     */       
/* 180 */       int widgetId = Integer.parseInt(layoutPatternMatcher.group(1));
/* 181 */       int xCoord = Integer.parseInt(layoutPatternMatcher.group(2));
/* 182 */       int yCoord = Integer.parseInt(layoutPatternMatcher.group(3));
/* 183 */       ILayoutWidget<LayoutPanelStandard> key = getWidget(widgetId, true);
/*     */       
/* 185 */       if (key != null)
/*     */       {
/* 187 */         key.setWidgetPosition(this, xCoord, yCoord);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String savePanelLayout() {
/* 197 */     String panelLayout = "";
/*     */     
/* 199 */     for (int i = this.macroType.getMinId(); i <= this.macroType.getAbsoluteMaxId(); i++) {
/*     */       
/* 201 */       if (this.widgets[i] != null)
/*     */       {
/* 203 */         panelLayout = panelLayout + this.widgets[i].toString();
/*     */       }
/*     */     } 
/*     */     
/* 207 */     return panelLayout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean keyPressed(char keyChar, int keyCode) {
/* 217 */     if (super.keyPressed(keyChar, keyCode)) return true;
/*     */     
/* 219 */     if (mode == LayoutPanelEditMode.EditAll && this.macroType.supportsId(keyCode)) {
/*     */       
/* 221 */       if (this.widgets[keyCode] == null) {
/*     */         
/* 223 */         this.widgets[keyCode] = new LayoutButton((bty)MacroModCore.getInstance().getLegacyFontRenderer(), keyCode, this.macroType.getName(keyCode), this.widgetWidth, this.widgetHeight, this.centreAlign);
/* 224 */         this.widgets[keyCode].setWidgetPositionSnapped(this, this.lastMouseX, this.lastMouseY - 7);
/*     */       }
/* 226 */       else if (keyCode == 211 && this.selectedWidgetIndex > -1) {
/*     */         
/* 228 */         this.widgets[this.selectedWidgetIndex] = null;
/* 229 */         this.selectedWidgetIndex = -1;
/*     */       }
/*     */       else {
/*     */         
/* 233 */         this.selectedWidgetIndex = keyCode;
/*     */       } 
/*     */       
/* 236 */       return true;
/*     */     } 
/*     */     
/* 239 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int adjustMouse(int drawOffset, int mousePosition) {
/* 245 */     return (int)((mousePosition - drawOffset) / this.scaleFactor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean c(bsu minecraft, int mouseX, int mouseY) {
/* 254 */     if (MacroModSettings.autoScaleKeyboard) {
/*     */       
/* 256 */       mouseX = adjustMouse(this.drawX, mouseX);
/* 257 */       mouseY = adjustMouse(this.drawY, mouseY);
/*     */     } 
/*     */     
/* 260 */     if (MacroModSettings.drawLargeEditorButtons) {
/*     */       
/* 262 */       if (this.btnCopy != null && this.btnCopy.c(minecraft, mouseX, mouseY)) {
/*     */         
/* 264 */         setGlobalMode(LayoutPanelEditMode.Copy);
/* 265 */         return false;
/*     */       } 
/*     */       
/* 268 */       if (this.btnMove != null && this.btnMove.c(minecraft, mouseX, mouseY)) {
/*     */         
/* 270 */         setGlobalMode(LayoutPanelEditMode.Move);
/* 271 */         return false;
/*     */       } 
/*     */       
/* 274 */       if (this.btnDelete != null && this.btnDelete.c(minecraft, mouseX, mouseY)) {
/*     */         
/* 276 */         setGlobalMode(LayoutPanelEditMode.Delete);
/* 277 */         return false;
/*     */       } 
/*     */       
/* 280 */       if (this.btnEdit != null && this.btnEdit.c(minecraft, mouseX, mouseY)) {
/*     */         
/* 282 */         setGlobalMode(LayoutPanelEditMode.EditAll);
/* 283 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 287 */     return super.c(minecraft, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseRelease(int mouseX, int mouseY) {
/* 296 */     if (MacroModSettings.autoScaleKeyboard) {
/*     */       
/* 298 */       mouseX = adjustMouse(this.drawX, mouseX);
/* 299 */       mouseY = adjustMouse(this.drawY, mouseY);
/*     */     } 
/*     */     
/* 302 */     super.handleMouseRelease(mouseX, mouseY);
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
/*     */   protected void drawControl(bsu minecraft, int mouseX, int mouseY) {
/* 315 */     GL.glPushMatrix();
/*     */     
/* 317 */     if (MacroModSettings.autoScaleKeyboard) {
/*     */       
/* 319 */       GL.glTranslatef(this.drawX, this.drawY, 0.0F);
/* 320 */       GL.glScalef(this.scaleFactor, this.scaleFactor, 1.0F);
/*     */       
/* 322 */       mouseX = adjustMouse(this.drawX, mouseX);
/* 323 */       mouseY = adjustMouse(this.drawY, mouseY);
/*     */     } 
/*     */     
/* 326 */     drawCustomStuff(minecraft, mouseX, mouseY);
/*     */     
/* 328 */     if (mode == LayoutPanelEditMode.EditAll || mode == LayoutPanelEditMode.Reserve) {
/*     */       
/* 330 */       this.hoverWidget = null;
/* 331 */       this.hoverWidgetTime = panelUpdateCounter;
/*     */     }
/*     */     else {
/*     */       
/* 335 */       calcHover(mouseX, mouseY);
/*     */     } 
/*     */ 
/*     */     
/* 339 */     drawLargeButtons(minecraft, mouseX, mouseY);
/*     */ 
/*     */     
/* 342 */     drawWidgets(mouseX, mouseY);
/*     */ 
/*     */     
/* 345 */     drawHover(mouseX, mouseY, AbstractionLayer.getFontRenderer());
/*     */     
/* 347 */     b(minecraft, mouseX, mouseY);
/*     */ 
/*     */     
/* 350 */     this.lastMouseX = mouseX - getXPosition();
/* 351 */     this.lastMouseY = mouseY - getYPosition();
/*     */     
/* 353 */     GL.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postRender(bsu minecraft, int mouseX, int mouseY) {
/* 362 */     GL.glPushMatrix();
/*     */     
/* 364 */     if (MacroModSettings.autoScaleKeyboard) {
/*     */       
/* 366 */       GL.glTranslatef(this.drawX, this.drawY, 0.0F);
/* 367 */       GL.glScalef(this.scaleFactor, this.scaleFactor, 1.0F);
/*     */       
/* 369 */       mouseX = adjustMouse(this.drawX, mouseX);
/* 370 */       mouseY = adjustMouse(this.drawY, mouseY);
/*     */     } 
/*     */     
/* 373 */     super.postRender(minecraft, mouseX, mouseY);
/*     */     
/* 375 */     GL.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawCustomStuff(bsu minecraft, int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawLargeButtons(bsu minecraft, int mouseX, int mouseY) {
/* 390 */     if (MacroModSettings.drawLargeEditorButtons) {
/*     */       
/* 392 */       int widgetY = getYPosition() + getHeight() - 24;
/* 393 */       this.btnCopy.setYPosition(widgetY); this.btnMove.setYPosition(widgetY); this.btnDelete.setYPosition(widgetY); this.btnEdit.setYPosition(widgetY);
/* 394 */       this.btnCopy.setXPosition(getXPosition() + 4); this.btnMove.setXPosition(getXPosition() + 68); this.btnDelete.setXPosition(getXPosition() + 132); this.btnEdit.setXPosition(getXPosition() + 196);
/*     */ 
/*     */       
/* 397 */       switch (mode) {
/*     */         case Copy:
/* 399 */           a(this.btnCopy.getXPosition() - 1, this.btnCopy.getYPosition() - 1, this.btnCopy.getXPosition() + 61, this.btnCopy.getYPosition() + 21, -16711936); break;
/* 400 */         case Move: a(this.btnMove.getXPosition() - 1, this.btnMove.getYPosition() - 1, this.btnMove.getXPosition() + 61, this.btnMove.getYPosition() + 21, -16711681); break;
/* 401 */         case Delete: a(this.btnDelete.getXPosition() - 1, this.btnDelete.getYPosition() - 1, this.btnDelete.getXPosition() + 61, this.btnDelete.getYPosition() + 21, -65536); break;
/* 402 */         case EditAll: a(this.btnEdit.getXPosition() - 1, this.btnEdit.getYPosition() - 1, this.btnEdit.getXPosition() + 61, this.btnEdit.getYPosition() + 21, -256);
/*     */           break;
/*     */       } 
/* 405 */       if (mode != LayoutPanelEditMode.Reserve) {
/*     */ 
/*     */         
/* 408 */         this.btnCopy.a(minecraft, mouseX, mouseY);
/* 409 */         this.btnMove.a(minecraft, mouseX, mouseY);
/* 410 */         this.btnDelete.a(minecraft, mouseX, mouseY);
/* 411 */         if (isEditable()) {
/* 412 */           this.btnEdit.a(minecraft, mouseX, mouseY);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifySettingsCleared() {
/* 423 */     loadPanelLayout(this.defaultLayout);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifySettingsLoaded(ISettingsProvider settings) {
/* 434 */     LayoutButton.notifySettingsLoaded(settings);
/*     */     
/* 436 */     setEditable(settings.getSetting("keyboard.editable", false));
/*     */     
/* 438 */     if (this.layoutSettingName.length() > 0) {
/*     */       
/* 440 */       String keyboardLayout = settings.getSetting(this.layoutSettingName, "");
/*     */       
/* 442 */       if (keyboardLayout.length() > 0) {
/* 443 */         loadPanelLayout(keyboardLayout);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveSettings(ISettingsProvider settings) {
/* 455 */     if (this.layoutSettingName.length() > 0) {
/*     */       
/* 457 */       settings.setSetting(this.layoutSettingName, savePanelLayout());
/* 458 */       settings.setSettingComment(this.layoutSettingName, this.layoutSettingDescription);
/*     */     } 
/* 460 */     settings.setSetting("keyboard.editable", isEditable());
/* 461 */     settings.setSettingComment("keyboard.editable", "0 to disable layout editing, 1 to enable the 'edit' button");
/*     */     
/* 463 */     LayoutButton.saveSettings(settings);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\layout\LayoutPanelStandard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */