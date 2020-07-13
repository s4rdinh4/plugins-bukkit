/*     */ package net.eq2online.macros.gui.controls;
/*     */ 
/*     */ import bsu;
/*     */ import civ;
/*     */ import ckx;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.gui.shared.GuiControlEx;
/*     */ import net.eq2online.macros.gui.shared.GuiDialogBox;
/*     */ import net.eq2online.macros.res.ResourceLocations;
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
/*     */ public class GuiDropDownList
/*     */   extends GuiDropDownMenu
/*     */ {
/*     */   protected int lastX;
/*     */   protected int lastY;
/*     */   
/*     */   public static class GuiDropDownListControl
/*     */     extends GuiControlEx
/*     */   {
/*     */     private GuiDropDownList dropDownList;
/*     */     
/*     */     public GuiDropDownListControl(bsu minecraft, int controlId, int xPos, int yPos, int controlWidth, int controlHeight, int itemHeight, String emptyText) {
/*  40 */       super(minecraft, controlId, xPos, yPos, controlWidth, controlHeight, emptyText);
/*  41 */       this.dropDownList = new GuiDropDownList(controlWidth, controlHeight, itemHeight);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void drawControl(bsu minecraft, int mouseX, int mouseY) {
/*  50 */       this.dropDownList.drawControlAt(this.h, this.i, mouseX, mouseY);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void drawControlAt(bsu minecraft, int mouseX, int mouseY, int yPosition) {
/*  61 */       this.dropDownList.drawControlAt(this.h, yPosition, mouseX, mouseY);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean c(bsu minecraft, int mouseX, int mouseY) {
/*  70 */       return (this.dropDownList.mousePressed(mouseX, mouseY) != null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public GuiDropDownMenu addItem(String itemKey, String itemName) {
/*  82 */       return this.dropDownList.addItem(itemKey, itemName);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void selectItem(String item) {
/*  92 */       this.dropDownList.selectItem(item);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void selectItemByTag(String itemTag) {
/* 102 */       this.dropDownList.selectItemByTag(itemTag);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void selectItem(int itemIndex) {
/* 112 */       this.dropDownList.selectItem(itemIndex);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getSelectedItem() {
/* 120 */       return this.dropDownList.getSelectedItem();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getSelectedItemTag() {
/* 128 */       return this.dropDownList.getSelectedItemTag();
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
/*     */   
/* 140 */   protected int selectedItemIndex = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 145 */   protected int fieldHeight = 16;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String emptyText;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDropDownList() {
/* 157 */     super(200, 16, true);
/* 158 */     this.autoSize = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDropDownList(int width, int height, int itemHeight) {
/* 168 */     super(width, itemHeight, true);
/* 169 */     this.autoSize = false;
/* 170 */     this.fieldHeight = height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDropDownMenu addItem(String itemKey, String itemName) {
/* 179 */     super.addItem(itemKey, itemName);
/*     */     
/* 181 */     if (this.selectedItemIndex == -1) {
/* 182 */       this.selectedItemIndex = 0;
/*     */     }
/* 184 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDropDownMenu addSeparator() {
/* 193 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void selectItem(String item) {
/* 201 */     if (item == null)
/*     */       return; 
/* 203 */     for (int itemIndex = 0; itemIndex < this.listItems.size(); itemIndex++) {
/*     */       
/* 205 */       if (item.equals(this.listItems.get(itemIndex))) {
/*     */         
/* 207 */         this.selectedItemIndex = itemIndex;
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void selectItemByTag(String itemTag) {
/* 218 */     if (itemTag == null)
/*     */       return; 
/* 220 */     for (int itemIndex = 0; itemIndex < this.listItemsTags.size(); itemIndex++) {
/*     */       
/* 222 */       if (itemTag.equals(this.listItemsTags.get(itemIndex))) {
/*     */         
/* 224 */         this.selectedItemIndex = itemIndex;
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void selectItem(int itemIndex) {
/* 235 */     if (itemIndex > -2 && itemIndex < this.listItems.size())
/*     */     {
/* 237 */       this.selectedItemIndex = itemIndex;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSelectedItem() {
/* 246 */     if (this.selectedItemIndex > -1 && this.selectedItemIndex < this.listItems.size())
/*     */     {
/* 248 */       return this.listItems.get(this.selectedItemIndex);
/*     */     }
/*     */     
/* 251 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSelectedItemTag() {
/* 259 */     if (this.selectedItemIndex > -1 && this.selectedItemIndex < this.listItemsTags.size())
/*     */     {
/* 261 */       return this.listItemsTags.get(this.selectedItemIndex);
/*     */     }
/*     */     
/* 264 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawControlAt(int x, int y, int mouseX, int mouseY) {
/* 273 */     this.lastX = x;
/* 274 */     this.lastY = y;
/*     */     
/* 276 */     a(x - 1, y - 1, x + this.width + 1, y + this.fieldHeight + 1, -6250336);
/* 277 */     a(x, y, x + this.width, y + this.fieldHeight, -16777216);
/*     */     
/* 279 */     String selectedItem = getSelectedItem();
/*     */     
/* 281 */     int top = y + (this.fieldHeight - 8) / 2;
/*     */     
/* 283 */     if (selectedItem != null) {
/*     */       
/* 285 */       this.fontRenderer.a(selectedItem, x + 4, top, -1118482);
/*     */     }
/*     */     else {
/*     */       
/* 289 */       this.fontRenderer.a(this.emptyText, x + 4, top, -8355712);
/*     */     } 
/*     */     
/* 292 */     AbstractionLayer.bindTexture(ResourceLocations.FIXEDWIDTHFONT);
/*     */     
/* 294 */     a(x + this.width - 16, y, x + this.width, y + this.fieldHeight, -16777216);
/*     */     
/* 296 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 297 */     drawTexturedModalRect(x + this.width - 12, top, x + this.width - 4, top + 8, 240, 16, 256, 32);
/*     */     
/* 299 */     super.drawControlAt(x, Math.min(y + this.fieldHeight, GuiDialogBox.lastScreenHeight - this.height), mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String mousePressed(int mouseX, int mouseY) {
/* 308 */     if (this.dropDownVisible) {
/*     */       
/* 310 */       String clickedTag = super.mousePressed(mouseX, mouseY);
/*     */       
/* 312 */       if (clickedTag != null) {
/*     */         
/* 314 */         this.selectedItemIndex = this.listItemsTags.indexOf(clickedTag);
/* 315 */         return "";
/*     */       } 
/*     */     } 
/*     */     
/* 319 */     if (mouseX > this.lastX && mouseX < this.lastX + this.width && mouseY > this.lastY && mouseY < this.lastY + this.fieldHeight) {
/*     */       
/* 321 */       this.dropDownVisible = !this.dropDownVisible;
/* 322 */       return "";
/*     */     } 
/*     */     
/* 325 */     return null;
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
/*     */   public void drawTexturedModalRect(int x, int y, int x2, int y2, int u, int v, int u2, int v2) {
/* 342 */     ckx tessellator = AbstractionLayer.getTessellator();
/* 343 */     civ worldRender = tessellator.c();
/* 344 */     worldRender.b();
/* 345 */     worldRender.a(x, y2, 0.0D, (u * 0.00390625F), (v2 * 0.00390625F));
/* 346 */     worldRender.a(x2, y2, 0.0D, (u2 * 0.00390625F), (v2 * 0.00390625F));
/* 347 */     worldRender.a(x2, y, 0.0D, (u2 * 0.00390625F), (v * 0.00390625F));
/* 348 */     worldRender.a(x, y, 0.0D, (u * 0.00390625F), (v * 0.00390625F));
/* 349 */     tessellator.b();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\GuiDropDownList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */