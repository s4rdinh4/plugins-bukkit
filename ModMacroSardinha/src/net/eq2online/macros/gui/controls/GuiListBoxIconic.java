/*     */ package net.eq2online.macros.gui.controls;
/*     */ 
/*     */ import bsu;
/*     */ import bty;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Rectangle;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.interfaces.IListObject;
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
/*     */ public class GuiListBoxIconic
/*     */   extends GuiListBox
/*     */ {
/*  22 */   private static int fadeTicks = 40;
/*     */ 
/*     */   
/*     */   private int lastSelectedItem;
/*     */   
/*     */   private int lastSelectedItemCounter;
/*     */ 
/*     */   
/*     */   public GuiListBoxIconic(bsu minecraft, int controlId) {
/*  31 */     super(minecraft, controlId, true, false, false);
/*     */     
/*  33 */     this.itemsPerRow = (getWidth() - 20) / this.rowSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiListBoxIconic(bsu minecraft, int controlId, int xPos, int yPos, int controlWidth, int controlHeight, int rowHeight, boolean showIcons, boolean dragSource, boolean dragTarget) {
/*  38 */     super(minecraft, controlId, xPos, yPos, controlWidth, controlHeight, rowHeight, showIcons, dragSource, dragTarget);
/*     */     
/*  40 */     this.itemsPerRow = (getWidth() - 20) / this.rowSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSize(int controlWidth, int controlHeight, int rowHeight, boolean showIcons) {
/*  49 */     super.setSize(controlWidth, controlHeight, rowHeight, showIcons);
/*     */     
/*  51 */     this.itemsPerRow = (getWidth() - 20) / this.rowSize;
/*  52 */     updateScrollBar();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void selectId(int ID) {
/*  61 */     super.selectId(ID);
/*  62 */     this.lastSelectedItemCounter = this.updateCounter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawItems(bsu minecraft, int mouseX, int mouseY, int itemTextColour) {
/*  72 */     super.drawItems(minecraft, mouseX, mouseY, itemTextColour);
/*     */     
/*  74 */     GL.glClear(256);
/*     */     
/*  76 */     int mouseOverItemIndex = getItemIndexAt(mouseX, mouseY);
/*     */     
/*  78 */     if (this.items.size() > 0 && this.selectedItem < this.items.size()) {
/*     */       
/*  80 */       if (this.selectedItem != this.lastSelectedItem) {
/*     */         
/*  82 */         this.lastSelectedItem = this.selectedItem;
/*  83 */         this.lastSelectedItemCounter = this.updateCounter;
/*     */       } 
/*     */       
/*  86 */       float fadeCounter = (fadeTicks - this.updateCounter - this.lastSelectedItemCounter);
/*     */       
/*  88 */       if (fadeCounter > 0.0F && this.selectedItem != mouseOverItemIndex) {
/*     */         
/*  90 */         int opacity = ((int)(fadeCounter / fadeTicks * 215.0F) & 0xFF) << 24;
/*     */         
/*  92 */         Rectangle itm = getItemBoundingBox(this.selectedItem);
/*  93 */         drawTooltip(AbstractionLayer.getFontRenderer(), ((IListObject)this.items.get(this.selectedItem)).getText(), itm.x + 8 + itm.width, itm.y + 18 + this.iconOffset, 999, 999, opacity | 0xFFFFFF, opacity | 0x0);
/*     */       } 
/*     */     } 
/*     */     
/*  97 */     if (mouseOverItemIndex > -1 && mouseOverItemIndex < this.items.size())
/*     */     {
/*  99 */       drawTooltip(AbstractionLayer.getFontRenderer(), ((IListObject)this.items.get(mouseOverItemIndex)).getText(), getItemBoundingBox(mouseOverItemIndex), 999, 999, itemTextColour, -805306368);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawTooltip(bty fontRenderer, String tooltipText, Rectangle itemBoundingBox, int screenWidth, int screenHeight, int colour, int backgroundColour) {
/* 116 */     int textSize = fontRenderer.a(tooltipText) + this.iconOffset + this.iconOffset + 6;
/* 117 */     a(itemBoundingBox.x, itemBoundingBox.y, itemBoundingBox.x + itemBoundingBox.width + textSize, itemBoundingBox.y + itemBoundingBox.height, 1082163328);
/* 118 */     drawTooltip(fontRenderer, tooltipText, itemBoundingBox.x + 8 + itemBoundingBox.width, itemBoundingBox.y + 18 + this.iconOffset, screenWidth, screenHeight, colour, backgroundColour);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawItem(IListObject item, bsu minecraft, int mouseX, int mouseY, int itemX, int itemY, int itemWidth, int itemHeight, int itemTextColour) {
/* 128 */     if (item.getCustomDrawBehaviour() == IListObject.CustomDrawBehaviour.FullCustomDraw) {
/*     */       
/* 130 */       item.drawCustom(this.iconsEnabled, minecraft, mouseX, mouseY, itemX, itemY, itemWidth, itemHeight, this.updateCounter);
/*     */     }
/*     */     else {
/*     */       
/* 134 */       if (!item.renderIcon(minecraft, itemX + this.iconOffset, itemY + this.iconOffset) && item.hasIcon() && item.getIcon() != null) {
/*     */         
/* 136 */         a(itemX + this.iconOffset, itemY + this.iconOffset, itemX + this.iconOffset + 16, itemY + this.iconOffset + 16, 1090519039);
/* 137 */         drawIcon(item.getIconTexture(), item.getIcon(), itemX + this.iconOffset, itemY + this.iconOffset, itemX + this.iconOffset + 16, itemY + this.iconOffset + 16);
/*     */       } 
/*     */ 
/*     */       
/* 141 */       if (item.getCustomDrawBehaviour() == IListObject.CustomDrawBehaviour.CustomDrawExtra)
/*     */       {
/* 143 */         item.drawCustom(this.iconsEnabled, minecraft, mouseX, mouseY, itemX, itemY, itemWidth, itemHeight, this.updateCounter);
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\GuiListBoxIconic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */