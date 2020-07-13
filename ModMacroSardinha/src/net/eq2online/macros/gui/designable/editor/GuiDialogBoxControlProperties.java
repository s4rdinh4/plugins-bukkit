/*     */ package net.eq2online.macros.gui.designable.editor;
/*     */ 
/*     */ import bub;
/*     */ import bug;
/*     */ import bul;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import net.eq2online.macros.compatibility.GuiControl;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*     */ import net.eq2online.macros.gui.controls.GuiColourButton;
/*     */ import net.eq2online.macros.gui.controls.GuiDropDownList;
/*     */ import net.eq2online.macros.gui.controls.GuiLabel;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiTextFieldWithHighlight;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*     */ import net.eq2online.macros.gui.shared.GuiDialogBox;
/*     */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*     */ import net.eq2online.macros.interfaces.IHighlighter;
/*     */ import net.eq2online.util.Colour;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiDialogBoxControlProperties
/*     */   extends GuiDialogBox
/*     */ {
/*     */   protected DesignableGuiControl control;
/*  35 */   protected LinkedList<bul> textFields = new LinkedList<bul>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   protected HashMap<String, bul> propertyFields = new HashMap<String, bul>();
/*  41 */   protected HashMap<String, bul> numericpropertyFields = new HashMap<String, bul>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   protected HashMap<String, GuiCheckBox> checkBoxes = new HashMap<String, GuiCheckBox>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   protected HashMap<String, GuiColourButton> colourButtons = new HashMap<String, GuiColourButton>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   protected HashMap<String, GuiDropDownList.GuiDropDownListControl> dropDowns = new HashMap<String, GuiDropDownList.GuiDropDownListControl>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   private int controlTop = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   private int nextControlId = 100;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected bul txtName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDialogBoxControlProperties(GuiScreenEx parentScreen, DesignableGuiControl control) {
/*  79 */     super(parentScreen, 210, 170, LocalisationProvider.getLocalisedString("control.properties.title", new Object[] { control.getName() }));
/*     */     
/*  81 */     this.control = control;
/*  82 */     this.centreTitle = false;
/*     */     
/*  84 */     this.movable = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initDialog() {
/*  93 */     super.initDialog();
/*     */     
/*  95 */     if (!this.dragging) {
/*     */       
/*  97 */       this.dialogX = 30;
/*  98 */       this.dialogY = 42;
/*     */     } 
/*     */     
/* 101 */     this.btnOk.setXPosition(this.dialogX + this.dialogWidth - 62); this.btnOk.setYPosition(this.dialogY + this.dialogHeight - 22);
/* 102 */     this.btnCancel.setXPosition(this.dialogX + this.dialogWidth - 124); this.btnCancel.setYPosition(this.dialogY + this.dialogHeight - 22);
/*     */     
/* 104 */     this.textFields.clear();
/* 105 */     this.propertyFields.clear();
/* 106 */     this.numericpropertyFields.clear();
/* 107 */     this.checkBoxes.clear();
/* 108 */     this.colourButtons.clear();
/* 109 */     this.dropDowns.clear();
/* 110 */     this.controlTop = 8;
/* 111 */     this.nextControlId = 100;
/*     */     
/* 113 */     this.txtName = addTextField(LocalisationProvider.getLocalisedString("control.properties.name"), "name", false);
/* 114 */     this.txtName.b(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/* 123 */     super.e();
/*     */     
/* 125 */     for (bul textField : this.textFields) {
/* 126 */       textField.a();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawDialog(int mouseX, int mouseY, float f) {
/* 135 */     super.drawDialog(mouseX, mouseY, f);
/*     */     
/* 137 */     for (bul textField : this.textFields) {
/* 138 */       textField.g();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postRender(int mouseX, int mouseY, float f) {
/* 147 */     for (GuiDropDownList.GuiDropDownListControl dropDown : this.dropDowns.values()) {
/* 148 */       dropDown.a(this.j, mouseX, mouseY);
/*     */     }
/* 150 */     for (GuiColourButton colourButton : this.colourButtons.values()) {
/* 151 */       colourButton.drawPicker(this.j, mouseX, mouseY);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogKeyTyped(char keyChar, int keyCode) {
/* 160 */     boolean handled = false;
/*     */     
/* 162 */     for (GuiColourButton colourButton : this.colourButtons.values()) {
/* 163 */       handled |= colourButton.textBoxKeyTyped(keyChar, keyCode);
/*     */     }
/* 165 */     if (!handled) {
/*     */       
/* 167 */       for (bul textField : this.textFields) {
/*     */         
/* 169 */         if (!this.numericpropertyFields.containsValue(textField) || "-0123456789.".indexOf(keyChar) > -1 || keyCode == 203 || keyCode == 205 || keyCode == 199 || keyCode == 207 || keyCode == 211 || keyCode == 14)
/*     */         {
/* 171 */           textField.a(keyChar, keyCode);
/*     */         }
/*     */       } 
/*     */       
/* 175 */       if (this.textFields.size() > 0 && keyChar == '\t') {
/*     */         
/* 177 */         int focusedId = 0;
/*     */         
/* 179 */         for (int i = 0; i < this.textFields.size(); i++) {
/*     */           
/* 181 */           if (((bul)this.textFields.get(i)).m()) {
/*     */             
/* 183 */             focusedId = i + 1;
/* 184 */             ((bul)this.textFields.get(i)).b(false);
/*     */           } 
/*     */         } 
/*     */         
/* 188 */         if (focusedId >= this.textFields.size()) {
/* 189 */           focusedId = 0;
/*     */         }
/* 191 */         ((bul)this.textFields.get(focusedId)).b(true);
/*     */         return;
/*     */       } 
/*     */     } 
/* 195 */     super.dialogKeyTyped(keyChar, keyCode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogMouseClicked(int mouseX, int mouseY, int button) throws IOException {
/* 204 */     for (bul textField : this.textFields) {
/* 205 */       textField.a(mouseX, mouseY, button);
/*     */     }
/* 207 */     for (GuiColourButton colourButton : this.colourButtons.values()) {
/*     */       
/* 209 */       if (colourButton.c(this.j, mouseX, mouseY)) {
/*     */         
/* 211 */         this.h = (bug)colourButton;
/* 212 */         a((bug)colourButton);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 217 */     for (GuiDropDownList.GuiDropDownListControl dropDown : this.dropDowns.values()) {
/*     */       
/* 219 */       if (dropDown.c(this.j, mouseX, mouseY)) {
/*     */         
/* 221 */         this.h = (bug)dropDown;
/* 222 */         a((bug)dropDown);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 227 */     super.dialogMouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSubmit() {
/* 233 */     for (Map.Entry<String, bul> propertyField : this.propertyFields.entrySet())
/*     */     {
/* 235 */       this.control.setProperty(propertyField.getKey(), ((bul)propertyField.getValue()).b());
/*     */     }
/*     */     
/* 238 */     for (Map.Entry<String, bul> propertyField : this.numericpropertyFields.entrySet())
/*     */     {
/* 240 */       this.control.setProperty(propertyField.getKey(), tryParseInt(((bul)propertyField.getValue()).b(), 0));
/*     */     }
/*     */     
/* 243 */     for (Map.Entry<String, GuiCheckBox> checkBox : this.checkBoxes.entrySet())
/*     */     {
/* 245 */       this.control.setProperty(checkBox.getKey(), ((GuiCheckBox)checkBox.getValue()).checked);
/*     */     }
/*     */     
/* 248 */     for (Map.Entry<String, GuiColourButton> colourButton : this.colourButtons.entrySet())
/*     */     {
/* 250 */       this.control.setProperty(colourButton.getKey(), Colour.getHexColour(((GuiColourButton)colourButton.getValue()).getColour()));
/*     */     }
/*     */     
/* 253 */     for (Map.Entry<String, GuiDropDownList.GuiDropDownListControl> dropDown : this.dropDowns.entrySet()) {
/*     */       
/* 255 */       if (((GuiDropDownList.GuiDropDownListControl)dropDown.getValue()).getSelectedItemTag() != null)
/*     */       {
/* 257 */         this.control.setProperty(dropDown.getKey(), ((GuiDropDownList.GuiDropDownListControl)dropDown.getValue()).getSelectedItemTag());
/*     */       }
/*     */     } 
/*     */     
/* 261 */     this.control.applyChanges(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validateDialog() {
/* 267 */     return true;
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
/*     */   public bul addTextFieldWithCheckBox(String label, String textBoxPropertyName, String checkBoxLabel, String checkBoxPropertyName, boolean numeric) {
/* 279 */     bul textField = new bul(0, this.j.k, this.dialogX + 85, this.dialogY + this.controlTop, 70, 16);
/* 280 */     GuiCheckBox checkBox = new GuiCheckBox(this.nextControlId++, this.dialogX + 100 + textField.p(), this.dialogY + this.controlTop - 2, checkBoxLabel, this.control.getProperty(checkBoxPropertyName, false));
/* 281 */     checkBox.textColour = -22016;
/* 282 */     this.checkBoxes.put(checkBoxPropertyName, checkBox);
/* 283 */     addControl((GuiControl)checkBox);
/*     */     
/* 285 */     return addTextField(label, textBoxPropertyName, textField, numeric);
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
/*     */   public bul addTextField(String label, String propertyName, boolean numeric) {
/* 297 */     bul textField = new bul(0, this.j.k, this.dialogX + 85, this.dialogY + this.controlTop, 115, 16);
/* 298 */     return addTextField(label, propertyName, textField, numeric);
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
/*     */   public bul addTextField(String label, String propertyName, IHighlighter highlighter) {
/* 310 */     GuiTextFieldWithHighlight textField = new GuiTextFieldWithHighlight(0, this.j.k, this.dialogX + 85, this.dialogY + this.controlTop, 115, 16, "");
/* 311 */     textField.setHighlighter(highlighter);
/* 312 */     return addTextField(label, propertyName, (bul)textField, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected bul addTextField(String label, String propertyName, bul textField, boolean numeric) {
/* 323 */     textField.f(255);
/* 324 */     addControl(label, (bub)textField, false);
/*     */     
/* 326 */     if (numeric) {
/*     */       
/* 328 */       textField.a(String.valueOf(this.control.getProperty(propertyName, 0)));
/* 329 */       this.numericpropertyFields.put(propertyName, textField);
/*     */     }
/*     */     else {
/*     */       
/* 333 */       textField.a(this.control.getProperty(propertyName, ""));
/* 334 */       this.propertyFields.put(propertyName, textField);
/*     */     } 
/*     */     
/* 337 */     this.controlTop += 20;
/* 338 */     return textField;
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
/*     */   public GuiCheckBox addCheckBox(String label, String propertyName) {
/* 350 */     GuiCheckBox checkBox = new GuiCheckBox(this.nextControlId++, this.dialogX + 10, this.dialogY + this.controlTop, label, this.control.getProperty(propertyName, false));
/* 351 */     checkBox.textColour = -22016;
/* 352 */     this.checkBoxes.put(propertyName, checkBox);
/* 353 */     addControl((GuiControl)checkBox);
/* 354 */     this.controlTop += 20;
/* 355 */     return checkBox;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiColourButton addColourButton(String label, String propertyName, int defaultColour) {
/* 366 */     GuiColourButton colourButton = new GuiColourButton(this.nextControlId++, this.dialogX + 160, this.dialogY + this.controlTop, 40, 16, "", Colour.parseColour(this.control.getProperty(propertyName, ""), defaultColour));
/* 367 */     addControl(label, (bub)colourButton, true);
/* 368 */     this.colourButtons.put(propertyName, colourButton);
/* 369 */     this.controlTop += 20;
/* 370 */     return colourButton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDropDownList.GuiDropDownListControl addDropDown(String label, String propertyName, String emptyText) {
/* 381 */     GuiDropDownList.GuiDropDownListControl dropDown = new GuiDropDownList.GuiDropDownListControl(this.j, this.nextControlId++, this.dialogX + 85, this.dialogY + this.controlTop, 115, 16, 12, emptyText);
/* 382 */     addControl(label, (bub)dropDown, false);
/* 383 */     this.dropDowns.put(propertyName, dropDown);
/* 384 */     this.controlTop += 20;
/* 385 */     return dropDown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addControl(String label, bub control, boolean addToControlList) {
/* 395 */     if (control instanceof GuiControl) {
/*     */       
/* 397 */       GuiControl button = (GuiControl)control;
/* 398 */       if (label.length() > 0) addControl((GuiControl)new GuiLabel(-999, this.dialogX + 10, this.dialogY + this.controlTop + (button.getHeight() - 8) / 2, label, -22016)); 
/* 399 */       if (addToControlList) addControl(button);
/*     */     
/* 401 */     } else if (control instanceof bul) {
/*     */       
/* 403 */       bul textField = (bul)control;
/* 404 */       if (label.length() > 0) addControl((GuiControl)new GuiLabel(-999, this.dialogX + 10, this.dialogY + this.controlTop + 4, label, -22016)); 
/* 405 */       this.textFields.add(textField);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getControlTop() {
/* 414 */     return this.controlTop;
/*     */   }
/*     */   
/*     */   private static int tryParseInt(String value, int defaultValue) {
/*     */     
/* 419 */     try { return Integer.parseInt(value.trim().replaceAll(",", "")); } catch (NumberFormatException numberFormatException) { return defaultValue; }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\designable\editor\GuiDialogBoxControlProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */