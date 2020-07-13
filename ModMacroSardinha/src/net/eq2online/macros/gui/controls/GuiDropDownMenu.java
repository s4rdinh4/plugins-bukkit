/*     */ package net.eq2online.macros.gui.controls;
/*     */ 
/*     */ import bty;
/*     */ import bub;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Point;
/*     */ import java.util.ArrayList;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.gui.shared.GuiScreenEx;
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
/*     */ public class GuiDropDownMenu
/*     */   extends bub
/*     */ {
/*  25 */   protected ArrayList<String> listItems = new ArrayList<String>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  30 */   protected ArrayList<String> listItemsTags = new ArrayList<String>();
/*     */   
/*  32 */   protected ArrayList<Point> iconCoords = new ArrayList<Point>();
/*     */   
/*     */   protected boolean hasIcons = false;
/*     */   
/*  36 */   protected int separators = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int width;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int height;
/*     */ 
/*     */   
/*     */   protected int itemHeight;
/*     */ 
/*     */   
/*     */   protected int xPos;
/*     */ 
/*     */   
/*     */   protected int yPos;
/*     */ 
/*     */   
/*     */   protected boolean dropDownVisible = false;
/*     */ 
/*     */   
/*     */   protected bty fontRenderer;
/*     */ 
/*     */   
/*     */   protected boolean dropDown = true;
/*     */ 
/*     */   
/*     */   protected boolean autoSize = true;
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDropDownMenu() {
/*  70 */     this(10, 16, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDropDownMenu(boolean dropDown) {
/*  80 */     this(10, 16, dropDown);
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
/*     */   public GuiDropDownMenu(int width, int itemHeight, boolean dropDown) {
/*  92 */     this.width = width;
/*  93 */     this.itemHeight = itemHeight;
/*  94 */     this.dropDown = dropDown;
/*  95 */     this.fontRenderer = AbstractionLayer.getFontRenderer();
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
/*     */   public GuiDropDownMenu addItem(String itemKey, String itemName) {
/* 107 */     this.listItemsTags.add(itemKey);
/* 108 */     this.listItems.add(itemName);
/* 109 */     this.iconCoords.add(null);
/* 110 */     this.height = this.itemHeight * this.listItems.size() + this.separators + 8;
/* 111 */     if (this.autoSize) this.width = Math.max(this.width, this.fontRenderer.a(itemName) + 20); 
/* 112 */     return this;
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
/*     */   public GuiDropDownMenu addItem(String itemKey, String itemName, int u, int v) {
/* 124 */     this.listItemsTags.add(itemKey);
/* 125 */     this.listItems.add(itemName);
/* 126 */     this.iconCoords.add(new Point(u, v));
/* 127 */     this.hasIcons = true;
/* 128 */     this.height = this.itemHeight * this.listItems.size() + this.separators + 8;
/* 129 */     if (this.autoSize) this.width = Math.max(this.width, this.fontRenderer.a(itemName) + 20); 
/* 130 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiDropDownMenu addSeparator() {
/* 135 */     this.listItemsTags.add("-");
/* 136 */     this.listItems.add("-");
/* 137 */     this.iconCoords.add(null);
/* 138 */     this.separators += -this.itemHeight + 4;
/* 139 */     this.height = this.itemHeight * this.listItems.size() + this.separators + 8;
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void showDropDown() {
/* 148 */     this.dropDownVisible = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDropDownVisible() {
/* 158 */     return this.dropDownVisible;
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
/*     */   public void drawControlAt(Point position, int mouseX, int mouseY) {
/* 170 */     drawControlAt(position.x, position.y, mouseX, mouseY);
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
/*     */   public void drawControlAt(int x, int y, int mouseX, int mouseY) {
/* 184 */     this.xPos = x;
/* 185 */     this.yPos = y;
/*     */     
/* 187 */     if (!this.dropDownVisible)
/* 188 */       return;  if (!this.dropDown) y -= this.height; 
/* 189 */     mouseX -= x; mouseY -= y;
/*     */     
/* 191 */     GL.glPushMatrix();
/* 192 */     GL.glTranslatef(x, y, 0.0F);
/*     */     
/* 194 */     int fontColour = -1118482;
/* 195 */     int displayWidth = this.width + (this.hasIcons ? 16 : 0);
/*     */     
/* 197 */     a(-1, -1, displayWidth + 1, this.height + 1, fontColour);
/* 198 */     a(0, 0, displayWidth, this.height, -16777216);
/*     */     
/* 200 */     int yPos = 4;
/* 201 */     int index = 0;
/*     */     
/* 203 */     for (String menuItem : this.listItems) {
/*     */       
/* 205 */       if (menuItem.equals("-")) {
/*     */         
/* 207 */         a(4, yPos + 1, displayWidth - 4, yPos + 3, 1728053247);
/* 208 */         yPos += 4;
/*     */       }
/*     */       else {
/*     */         
/* 212 */         if (mouseX > 0 && mouseX < displayWidth && mouseY > yPos && mouseY < yPos + this.itemHeight) {
/*     */           
/* 214 */           a(2, yPos, displayWidth - 2, yPos + this.itemHeight, -1325400065);
/* 215 */           fontColour = -16777216;
/*     */         } 
/*     */         
/* 218 */         int itemXPos = 6;
/* 219 */         int itemYPos = yPos + (this.itemHeight - 8) / 2;
/*     */         
/* 221 */         if (this.hasIcons) {
/*     */           
/* 223 */           Point iconUV = this.iconCoords.get(index);
/*     */           
/* 225 */           if (iconUV != null) {
/*     */             
/* 227 */             float alpha = (fontColour >> 24 & 0xFF) / 255.0F;
/* 228 */             float red = (fontColour >> 16 & 0xFF) / 255.0F;
/* 229 */             float green = (fontColour >> 8 & 0xFF) / 255.0F;
/* 230 */             float blue = (fontColour & 0xFF) / 255.0F;
/* 231 */             GL.glColor4f(red, green, blue, alpha);
/*     */             
/* 233 */             AbstractionLayer.bindTexture(ResourceLocations.MAIN);
/* 234 */             GuiScreenEx.drawTexturedModalRectEx(itemXPos, itemYPos, itemXPos + 12, itemYPos + 8, iconUV.x, iconUV.y, iconUV.x + 6, iconUV.y + 4, 0);
/*     */           } 
/*     */           
/* 237 */           itemXPos += 16;
/*     */         } 
/*     */         
/* 240 */         this.fontRenderer.a(menuItem, itemXPos, itemYPos, fontColour);
/* 241 */         yPos += this.itemHeight;
/* 242 */         fontColour = -1118482;
/*     */       } 
/*     */       
/* 245 */       index++;
/*     */     } 
/*     */     
/* 248 */     GL.glPopMatrix();
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
/*     */   public String mousePressed(int mouseX, int mouseY) {
/* 260 */     if (!this.dropDownVisible) return null;
/*     */     
/* 262 */     this.dropDownVisible = false;
/*     */     
/* 264 */     if (!this.dropDown) mouseY += this.height; 
/* 265 */     mouseX -= this.xPos; mouseY -= this.yPos;
/*     */     
/* 267 */     int displayWidth = this.width + (this.hasIcons ? 16 : 0);
/*     */     
/* 269 */     int yPos = 4;
/*     */     
/* 271 */     for (String menuItemTag : this.listItemsTags) {
/*     */       
/* 273 */       if (menuItemTag.equals("-")) {
/*     */         
/* 275 */         yPos += 4;
/*     */         
/*     */         continue;
/*     */       } 
/* 279 */       if (mouseX > 0 && mouseX < displayWidth && mouseY > yPos && mouseY < yPos + this.itemHeight) {
/* 280 */         return menuItemTag;
/*     */       }
/* 282 */       yPos += this.itemHeight;
/*     */     } 
/*     */ 
/*     */     
/* 286 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Dimension getSize() {
/* 291 */     return new Dimension(this.width, this.height);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\GuiDropDownMenu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */