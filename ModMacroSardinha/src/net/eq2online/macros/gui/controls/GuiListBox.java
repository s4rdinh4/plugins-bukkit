/*      */ package net.eq2online.macros.gui.controls;
/*      */ 
/*      */ import amj;
/*      */ import bsu;
/*      */ import bty;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.security.InvalidParameterException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Map;
/*      */ import java.util.TreeMap;
/*      */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*      */ import net.eq2online.macros.gui.list.ListObjectGeneric;
/*      */ import net.eq2online.macros.gui.shared.GuiControlEx;
/*      */ import net.eq2online.macros.interfaces.IDragDrop;
/*      */ import net.eq2online.macros.interfaces.IListObject;
/*      */ import oa;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GuiListBox
/*      */   extends GuiControlEx
/*      */   implements IDragDrop
/*      */ {
/*   33 */   public static int defaultRowHeight = 20;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean iconsEnabled;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isDragSource;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isDragTarget;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   53 */   protected ArrayList<IDragDrop> dragTargets = new ArrayList<IDragDrop>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   58 */   protected IListObject dragItem = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   63 */   protected Point dragOffset = new Point();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   68 */   protected Point mouseDownLocation = new Point();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   73 */   protected Boolean mouseDelta = Boolean.valueOf(false);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int rowSize;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   83 */   protected int itemsPerRow = 1;
/*      */ 
/*      */   
/*      */   protected int textOffset;
/*      */ 
/*      */   
/*      */   protected int iconOffset;
/*      */ 
/*      */   
/*      */   protected int iconSpacing;
/*      */ 
/*      */   
/*      */   protected int displayRowCount;
/*      */ 
/*      */   
/*   98 */   protected int selectedItem = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  103 */   protected int editInPlaceItem = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  108 */   protected int timer = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected GuiScrollBar scrollBar;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  118 */   protected ArrayList<IListObject> items = new ArrayList<IListObject>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected IListObject mouseDownObject;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  128 */   protected int newItemIndex = 0;
/*      */   
/*  130 */   public int backColour = -2146562560;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean sortable = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GuiListBox(bsu minecraft, int controlId, int xPos, int yPos, int controlWidth, int controlHeight, int rowHeight, boolean showIcons, boolean dragSource, boolean dragTarget) {
/*  150 */     super(minecraft, controlId, xPos, yPos, Math.max(controlWidth, 60), Math.max(controlHeight, 8), "");
/*      */ 
/*      */ 
/*      */     
/*  154 */     this.rowSize = Math.max(rowHeight, 8);
/*  155 */     this.textOffset = (this.rowSize - 8) / 2;
/*  156 */     this.iconOffset = (this.rowSize - 16) / 2;
/*  157 */     this.iconSpacing = showIcons ? this.rowSize : 0;
/*  158 */     this.displayRowCount = getHeight() / this.rowSize;
/*      */     
/*  160 */     this.iconsEnabled = (showIcons && rowHeight >= 16);
/*      */     
/*  162 */     this.isDragSource = dragSource;
/*  163 */     this.isDragTarget = dragTarget;
/*      */     
/*  165 */     if (dragSource && dragTarget)
/*      */     {
/*  167 */       this.dragTargets.add(this);
/*      */     }
/*      */     
/*  170 */     this.scrollBar = new GuiScrollBar(minecraft, controlId, xPos + controlWidth - 20, yPos, 20, getHeight(), 0, 0, GuiScrollBar.ScrollBarOrientation.Vertical);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GuiListBox(bsu minecraft, int controlId, boolean showIcons, boolean dragSource, boolean dragTarget) {
/*  183 */     this(minecraft, controlId, 0, 0, 100, 20, defaultRowHeight, showIcons, dragSource, dragTarget);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSortable() {
/*  191 */     return this.sortable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GuiListBox setSortable(boolean sortable) {
/*  199 */     this.sortable = sortable;
/*  200 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSize(int controlWidth, int controlHeight) {
/*  211 */     setSize(controlWidth, controlHeight, this.rowSize, this.iconsEnabled);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSize(int controlWidth, int controlHeight, int rowHeight, boolean showIcons) {
/*  224 */     a(Math.max(controlWidth, 60));
/*  225 */     getHeight(Math.max(controlHeight, 40));
/*      */     
/*  227 */     this.rowSize = Math.max(rowHeight, 8);
/*  228 */     this.textOffset = (this.rowSize - 8) / 2;
/*  229 */     this.iconOffset = (this.rowSize - 16) / 2;
/*  230 */     this.iconSpacing = showIcons ? this.rowSize : 0;
/*      */     
/*  232 */     this.displayRowCount = getHeight() / this.rowSize;
/*  233 */     this.iconsEnabled = (showIcons && rowHeight >= 16);
/*      */     
/*  235 */     this.scrollBar.setSizeAndPosition(getXPosition() + controlWidth - 20, getYPosition(), 20, controlHeight);
/*  236 */     updateScrollBar();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSizeAndPosition(int left, int top, int controlWidth, int controlHeight, int rowHeight, boolean showIcons) {
/*  251 */     setPosition(left, top);
/*  252 */     setSize(controlWidth, controlHeight, rowHeight, showIcons);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  260 */     endEditInPlace();
/*  261 */     this.scrollBar.setValue(0);
/*  262 */     this.scrollBar.setMax(0);
/*  263 */     this.items.clear();
/*  264 */     this.mouseDownObject = null;
/*  265 */     this.selectedItem = 0;
/*  266 */     this.timer = this.updateCounter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addItem(IListObject newItem) {
/*  276 */     this.items.add(newItem);
/*  277 */     updateScrollBar();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addItemAt(IListObject newItem, int itemIndex) {
/*  288 */     if (itemIndex < 0) itemIndex = 0;
/*      */     
/*  290 */     if (itemIndex >= this.items.size()) {
/*  291 */       this.items.add(newItem);
/*      */     } else {
/*  293 */       this.items.add(itemIndex, newItem);
/*      */     } 
/*  295 */     updateScrollBar();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListObject removeItemAt(int itemIndex) {
/*  305 */     IListObject removedObject = null;
/*      */     
/*  307 */     if (itemIndex >= 0 && itemIndex < this.items.size()) {
/*      */       
/*  309 */       removedObject = this.items.remove(itemIndex);
/*  310 */       updateScrollBar();
/*  311 */       updateScrollPosition();
/*      */     } 
/*      */     
/*  314 */     return removedObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeItem(IListObject item) {
/*  324 */     for (int i = 0; i < this.items.size(); i++) {
/*      */       
/*  326 */       if (this.items.get(i) == item) {
/*      */         
/*  328 */         removeItemAt(i);
/*      */         return;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean up() {
/*  339 */     if (isEnabled()) {
/*      */       
/*  341 */       this.selectedItem -= this.itemsPerRow;
/*  342 */       endEditInPlace();
/*  343 */       updateScrollPosition();
/*      */     } 
/*      */     
/*  346 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean down() {
/*  354 */     if (isEnabled()) {
/*      */       
/*  356 */       this.selectedItem += this.itemsPerRow;
/*  357 */       endEditInPlace();
/*  358 */       updateScrollPosition();
/*      */     } 
/*      */     
/*  361 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean left() {
/*  371 */     if (isEnabled()) {
/*      */       
/*  373 */       this.selectedItem--;
/*  374 */       endEditInPlace();
/*  375 */       updateScrollPosition();
/*      */     } 
/*      */     
/*  378 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean right() {
/*  387 */     if (isEnabled()) {
/*      */       
/*  389 */       this.selectedItem++;
/*  390 */       endEditInPlace();
/*  391 */       updateScrollPosition();
/*      */     } 
/*      */     
/*  394 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean pageUp() {
/*  402 */     this.selectedItem -= this.displayRowCount * this.itemsPerRow;
/*  403 */     endEditInPlace();
/*  404 */     updateScrollPosition();
/*      */     
/*  406 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean pageDown() {
/*  414 */     this.selectedItem += this.displayRowCount * this.itemsPerRow;
/*  415 */     endEditInPlace();
/*  416 */     updateScrollPosition();
/*      */     
/*  418 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSelectedItemIndex(int itemIndex) {
/*  428 */     this.selectedItem = itemIndex;
/*  429 */     if (itemIndex != this.editInPlaceItem) endEditInPlace(); 
/*  430 */     updateScrollPosition();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void scrollToCentre() {
/*  438 */     int startRow = Math.max(0, this.selectedItem / this.itemsPerRow - this.displayRowCount / 2);
/*  439 */     this.scrollBar.setValue(startRow);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void selectId(int id) {
/*  449 */     for (int i = 0; i < this.items.size(); i++) {
/*      */       
/*  451 */       if (((IListObject)this.items.get(i)).getId() == id) {
/*      */         
/*  453 */         setSelectedItemIndex(i);
/*      */         return;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void selectData(Object data) {
/*  466 */     for (int i = 0; i < this.items.size(); i++) {
/*      */       
/*  468 */       if (((IListObject)this.items.get(i)).getData() != null && ((IListObject)this.items.get(i)).getData().equals(data)) {
/*      */         
/*  470 */         setSelectedItemIndex(i);
/*      */         return;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean selectIdWithData(int ID, Object data) {
/*  484 */     for (int i = 0; i < this.items.size(); i++) {
/*      */       
/*  486 */       if (((IListObject)this.items.get(i)).getId() == ID && ((((IListObject)this.items.get(i)).getData() == null && data == null) || data == null || ((IListObject)this.items.get(i)).getData().equals(data))) {
/*      */         
/*  488 */         setSelectedItemIndex(i);
/*  489 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/*  493 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean selectIdentifier(String identifier) {
/*  498 */     for (int i = 0; i < this.items.size(); i++) {
/*      */       
/*  500 */       if (((IListObject)this.items.get(i)).getIdentifier().equals(identifier)) {
/*      */         
/*  502 */         setSelectedItemIndex(i);
/*  503 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/*  507 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean selectIdentifierWithData(String identifier, Object data) {
/*  512 */     for (int i = 0; i < this.items.size(); i++) {
/*      */       
/*  514 */       if (((IListObject)this.items.get(i)).getIdentifier().equals(identifier) && ((((IListObject)this.items.get(i)).getData() == null && data == null) || data == null || ((IListObject)this.items.get(i)).getData().equals(data))) {
/*      */         
/*  516 */         setSelectedItemIndex(i);
/*  517 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/*  521 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void selectItem(IListObject item) {
/*  531 */     for (int i = 0; i < this.items.size(); i++) {
/*      */       
/*  533 */       if (this.items.get(i) == item) {
/*      */         
/*  535 */         setSelectedItemIndex(i);
/*      */         return;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemCount() {
/*  548 */     return this.items.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSelectedItemIndex() {
/*  558 */     return this.selectedItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListObject getSelectedItem() {
/*  568 */     return (this.items.size() > this.selectedItem && this.selectedItem > -1) ? this.items.get(this.selectedItem) : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListObject removeSelectedItem() {
/*  578 */     if (this.items.size() > this.selectedItem && this.selectedItem > -1) {
/*      */       
/*  580 */       IListObject removed = this.items.remove(this.selectedItem);
/*  581 */       updateScrollPosition();
/*  582 */       return removed;
/*      */     } 
/*      */     
/*  585 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListObject getItemById(int ID) {
/*  596 */     for (int i = 0; i < this.items.size(); i++) {
/*      */       
/*  598 */       if (((IListObject)this.items.get(i)).getId() == ID)
/*      */       {
/*  600 */         return this.items.get(i);
/*      */       }
/*      */     } 
/*      */     
/*  604 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsItem(Object itemData) {
/*  615 */     for (int i = 0; i < this.items.size(); i++) {
/*      */       
/*  617 */       if (((IListObject)this.items.get(i)).getData().equals(itemData)) {
/*  618 */         return true;
/*      */       }
/*      */     } 
/*  621 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sort() {
/*  629 */     if (!isSortable())
/*      */       return; 
/*  631 */     IListObject selectedItem = getSelectedItem();
/*      */     
/*  633 */     TreeMap<String, IListObject> sortList = new TreeMap<String, IListObject>();
/*      */     
/*  635 */     while (this.items.size() > 0) {
/*  636 */       sortList.put(((IListObject)this.items.get(0)).getText().toLowerCase(), this.items.remove(0));
/*      */     }
/*  638 */     for (Map.Entry<String, IListObject> item : sortList.entrySet()) {
/*  639 */       this.items.add(item.getValue());
/*      */     }
/*  641 */     if (selectedItem != null) {
/*  642 */       selectItem(selectedItem);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Rectangle getItemBoundingBox(int itemId) {
/*  653 */     int itemWidth = (this.itemsPerRow > 1) ? this.rowSize : (getWidth() - 20);
/*      */     
/*  655 */     return new Rectangle(
/*  656 */         getXPosition() + itemId % this.itemsPerRow * itemWidth, 
/*  657 */         getYPosition() + this.rowSize * (itemId / this.itemsPerRow - this.scrollBar.getValue()), itemWidth, this.rowSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getTotalRowCount() {
/*  670 */     int rowCount = this.items.size() / this.itemsPerRow;
/*  671 */     if (this.items.size() % this.itemsPerRow > 0) rowCount++; 
/*  672 */     return rowCount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateScrollBar() {
/*  680 */     this.scrollBar.setMax(Math.max(0, getTotalRowCount() - this.displayRowCount));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateScrollPosition() {
/*  689 */     updateScrollPosition(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateScrollPosition(boolean noEndEdit) {
/*  698 */     if (this.selectedItem < 0) this.selectedItem = 0; 
/*  699 */     if (this.selectedItem >= this.items.size()) this.selectedItem = this.items.size() - 1;
/*      */     
/*  701 */     int selectedItemRow = this.selectedItem / this.itemsPerRow;
/*      */     
/*  703 */     if (selectedItemRow < this.scrollBar.getValue()) this.scrollBar.setValue(selectedItemRow); 
/*  704 */     if (selectedItemRow > this.scrollBar.getValue() + this.displayRowCount - 1) this.scrollBar.setValue(selectedItemRow - this.displayRowCount + 1);
/*      */     
/*  706 */     if (!noEndEdit && this.editInPlaceItem > -1 && this.editInPlaceItem != this.selectedItem) {
/*  707 */       endEditInPlace();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void beginEditInPlace() {
/*  715 */     beginEditInPlace(this.selectedItem);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void beginEditInPlace(int editInPlaceIndex) {
/*  725 */     if (editInPlaceIndex == this.editInPlaceItem)
/*      */       return; 
/*  727 */     endEditInPlace();
/*      */     
/*  729 */     if (editInPlaceIndex > -1 && editInPlaceIndex < this.items.size() && ((IListObject)this.items.get(editInPlaceIndex)).getCanEditInPlace()) {
/*      */       
/*  731 */       this.editInPlaceItem = editInPlaceIndex;
/*  732 */       ((IListObject)this.items.get(this.editInPlaceItem)).beginEditInPlace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endEditInPlace() {
/*  741 */     if (getEditingInPlaceObject() != null) {
/*      */       
/*  743 */       ((IListObject)this.items.get(this.editInPlaceItem)).endEditInPlace();
/*      */       
/*  745 */       if (((IListObject)this.items.get(this.editInPlaceItem)).getText() == "") {
/*  746 */         removeItemAt(this.editInPlaceItem);
/*      */       }
/*  748 */       updateScrollPosition(true);
/*  749 */       save();
/*      */     } 
/*      */     
/*  752 */     this.editInPlaceItem = -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListObject getEditingInPlaceObject() {
/*  762 */     if (this.editInPlaceItem > -1 && this.editInPlaceItem < this.items.size() && ((IListObject)this.items.get(this.editInPlaceItem)).isEditingInPlace())
/*      */     {
/*  764 */       return this.items.get(this.editInPlaceItem);
/*      */     }
/*      */     
/*  767 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final GuiControlEx.KeyHandledState listBoxKeyTyped(char keyChar, int keyCode) {
/*  779 */     IListObject currentItem = getSelectedItem();
/*      */     
/*  781 */     this.doubleClicked = false;
/*  782 */     this.actionPerformed = false;
/*      */     
/*  784 */     if (currentItem != null) {
/*      */       
/*  786 */       if (currentItem.getCanEditInPlace() && currentItem.isEditingInPlace()) {
/*      */         
/*  788 */         if (this.editInPlaceItem < this.scrollBar.getValue() || this.editInPlaceItem >= this.scrollBar.getValue() + this.displayRowCount) {
/*  789 */           scrollToCentre();
/*      */         }
/*  791 */         if (!currentItem.editInPlaceKeyTyped(keyChar, keyCode)) {
/*      */           
/*  793 */           endEditInPlace();
/*      */           
/*  795 */           if (keyCode == 1) {
/*  796 */             return GuiControlEx.KeyHandledState.Handled;
/*      */           }
/*  798 */           if (keyCode == 200) up(); 
/*  799 */           if (keyCode == 208) down(); 
/*  800 */           if (keyCode == 203) left(); 
/*  801 */           if (keyCode == 205) right(); 
/*  802 */           if (keyCode == 201) pageUp(); 
/*  803 */           if (keyCode == 209) pageDown();
/*      */           
/*  805 */           return GuiControlEx.KeyHandledState.ActionPerformed;
/*      */         } 
/*  807 */         return GuiControlEx.KeyHandledState.Handled;
/*      */       } 
/*      */       
/*  810 */       if (keyCode == 200 && up()) return GuiControlEx.KeyHandledState.ActionPerformed; 
/*  811 */       if (keyCode == 208 && down()) return GuiControlEx.KeyHandledState.ActionPerformed; 
/*  812 */       if (keyCode == 203 && left()) return GuiControlEx.KeyHandledState.ActionPerformed; 
/*  813 */       if (keyCode == 205 && right()) return GuiControlEx.KeyHandledState.ActionPerformed; 
/*  814 */       if (keyCode == 201 && pageUp()) return GuiControlEx.KeyHandledState.ActionPerformed; 
/*  815 */       if (keyCode == 209 && pageDown()) return GuiControlEx.KeyHandledState.ActionPerformed;
/*      */       
/*  817 */       return keyTyped(keyChar, keyCode);
/*      */     } 
/*      */     
/*  820 */     return GuiControlEx.KeyHandledState.None;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected GuiControlEx.KeyHandledState keyTyped(char keyChar, int keyCode) {
/*  832 */     return GuiControlEx.KeyHandledState.None;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isValidDragTarget() {
/*  844 */     return this.isDragTarget;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isValidDragSource() {
/*  855 */     return this.isDragSource;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addDragTarget(IDragDrop target) {
/*  866 */     addDragTarget(target, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addDragTarget(IDragDrop target, boolean mutual) {
/*  878 */     if (!this.isDragSource)
/*      */     {
/*  880 */       throw new InvalidParameterException("This listbox does not support drag/drop source functions");
/*      */     }
/*      */     
/*  883 */     if (target != null && target.isValidDragTarget()) {
/*      */       
/*  885 */       if (!this.dragTargets.contains(target))
/*      */       {
/*  887 */         this.dragTargets.add(target);
/*      */       }
/*      */       
/*  890 */       if (mutual && this.isDragTarget && target.isValidDragSource())
/*      */       {
/*  892 */         target.addDragTarget(this, false);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  897 */       throw new InvalidParameterException("Target listbox is not a valid drag target");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeDragTarget(IDragDrop target) {
/*  909 */     removeDragTarget(target, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeDragTarget(IDragDrop target, boolean mutual) {
/*  921 */     if (!this.isDragSource) {
/*  922 */       throw new InvalidParameterException("This listbox does not support drag/drop source functions");
/*      */     }
/*  924 */     if (this.dragTargets.contains(target)) {
/*  925 */       this.dragTargets.remove(target);
/*      */     }
/*  927 */     if (mutual && this.isDragTarget) {
/*  928 */       target.removeDragTarget(this, false);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean dragDrop(IDragDrop source, IListObject object, int mouseX, int mouseY) {
/*  943 */     if (this.isDragTarget && isEnabled() && mouseX >= getXPosition() && mouseY >= getYPosition() && mouseX < getXPosition() + getWidth() && mouseY < getYPosition() + getHeight()) {
/*      */       
/*  945 */       int targetIndex = this.scrollBar.getValue() + (mouseY - getYPosition() + this.rowSize / 2) / this.rowSize;
/*      */       
/*  947 */       if (source == this) {
/*      */         
/*  949 */         int sourceIndex = this.items.indexOf(object);
/*      */         
/*  951 */         if (targetIndex == sourceIndex) {
/*  952 */           return true;
/*      */         }
/*  954 */         removeItemAt(sourceIndex);
/*      */       } 
/*      */       
/*  957 */       addItemAt(object, targetIndex);
/*  958 */       save();
/*  959 */       return true;
/*      */     } 
/*      */ 
/*      */     
/*  963 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawControl(bsu minecraft, int mouseX, int mouseY) {
/*  976 */     if (!isVisible()) {
/*      */       return;
/*      */     }
/*  979 */     a(getXPosition(), getYPosition(), getXPosition() + getWidth(), getYPosition() + getHeight(), this.backColour);
/*      */ 
/*      */     
/*  982 */     this.scrollBar.setEnabled(isEnabled());
/*      */ 
/*      */     
/*  985 */     this.scrollBar.updateCounter = this.updateCounter;
/*  986 */     this.scrollBar.a(minecraft, mouseX, mouseY);
/*      */     
/*  988 */     int itemTextColour = isEnabled() ? -1 : 1143087650;
/*      */     
/*  990 */     drawItems(minecraft, mouseX, mouseY, itemTextColour);
/*      */ 
/*      */     
/*  993 */     if (!this.mouseDelta.booleanValue() && (mouseX != this.mouseDownLocation.x || mouseY != this.mouseDownLocation.y))
/*      */     {
/*  995 */       this.mouseDelta = Boolean.valueOf(true);
/*      */     }
/*      */ 
/*      */     
/*  999 */     if (this.dragItem != null && this.mouseDelta.booleanValue()) {
/*      */       
/* 1001 */       a(mouseX + this.dragOffset.x + 1, mouseY + this.dragOffset.y, mouseX + this.dragOffset.x + getWidth() - 21, mouseY + this.dragOffset.y + this.rowSize, 1610612736);
/* 1002 */       drawItem(this.dragItem, minecraft, mouseX, mouseY, mouseX + this.dragOffset.x, mouseY + this.dragOffset.y, getWidth() - 20, this.rowSize, itemTextColour);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawItems(bsu minecraft, int mouseX, int mouseY, int itemTextColour) {
/* 1016 */     int startRow = this.scrollBar.getValue();
/* 1017 */     int itemWidth = (this.itemsPerRow > 1) ? this.rowSize : (getWidth() - 20);
/*      */ 
/*      */     
/* 1020 */     for (int itemNumber = startRow * this.itemsPerRow; itemNumber < startRow * this.itemsPerRow + this.displayRowCount * this.itemsPerRow && itemNumber < this.items.size(); itemNumber++) {
/*      */       
/* 1022 */       int rowNumber = itemNumber / this.itemsPerRow;
/* 1023 */       int colNumber = itemNumber % this.itemsPerRow;
/*      */       
/* 1025 */       IListObject item = this.items.get(itemNumber);
/*      */       
/* 1027 */       int itemY = getYPosition() + this.rowSize * (rowNumber - startRow);
/* 1028 */       int itemX = getXPosition() + colNumber * itemWidth;
/*      */ 
/*      */       
/* 1031 */       if (itemNumber == this.selectedItem)
/*      */       {
/* 1033 */         a(itemX, itemY, itemX + itemWidth, itemY + this.rowSize, 1895825407);
/*      */       }
/*      */ 
/*      */       
/* 1037 */       if (itemNumber == this.editInPlaceItem) {
/*      */         
/* 1039 */         if (item.getCanEditInPlace()) {
/*      */           
/* 1041 */           if (item.isEditingInPlace())
/*      */           {
/* 1043 */             item.editInPlaceDraw(this.iconsEnabled, minecraft, mouseX, mouseY, itemX, itemY, itemWidth, this.rowSize, this.updateCounter);
/*      */           }
/*      */           else
/*      */           {
/* 1047 */             endEditInPlace();
/* 1048 */             drawItem(item, minecraft, mouseX, mouseY, itemX, itemY, itemWidth, this.rowSize, itemTextColour);
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 1053 */           drawItem(item, minecraft, mouseX, mouseY, itemX, itemY, itemWidth, this.rowSize, itemTextColour);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1058 */         drawItem(item, minecraft, mouseX, mouseY, itemX, itemY, itemWidth, this.rowSize, itemTextColour);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawItem(IListObject item, bsu minecraft, int mouseX, int mouseY, int itemX, int itemY, int itemWidth, int itemHeight, int itemTextColour) {
/* 1078 */     bty fontrenderer = AbstractionLayer.getFontRenderer();
/*      */ 
/*      */     
/* 1081 */     if (item.getCustomDrawBehaviour() == IListObject.CustomDrawBehaviour.FullCustomDraw) {
/*      */       
/* 1083 */       item.drawCustom(this.iconsEnabled, minecraft, mouseX, mouseY, itemX, itemY, itemWidth, itemHeight, this.updateCounter);
/*      */     }
/*      */     else {
/*      */       
/* 1087 */       if (this.iconsEnabled)
/*      */       {
/* 1089 */         if (!item.renderIcon(minecraft, itemX + this.iconOffset, itemY + this.iconOffset) && item.hasIcon() && item.getIcon() != null) {
/*      */           
/* 1091 */           a(itemX + this.iconOffset, itemY + this.iconOffset, itemX + this.iconOffset + 16, itemY + this.iconOffset + 16, 1090519039);
/* 1092 */           drawIcon(item.getIconTexture(), item.getIcon(), itemX + this.iconOffset, itemY + this.iconOffset, itemX + this.iconOffset + 16, itemY + this.iconOffset + 16);
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/* 1097 */       drawStringWithEllipsis(fontrenderer, "" + item.getDisplayName(), itemX + this.iconSpacing + 4, itemY + this.textOffset, getWidth() - 32 - this.iconSpacing, itemTextColour);
/*      */ 
/*      */       
/* 1100 */       if (item.getCustomDrawBehaviour() == IListObject.CustomDrawBehaviour.CustomDrawExtra)
/*      */       {
/* 1102 */         item.drawCustom(this.iconsEnabled, minecraft, mouseX, mouseY, itemX, itemY, itemWidth, itemHeight, this.updateCounter);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void b(bsu minecraft, int mouseX, int mouseY) {
/* 1118 */     this.scrollBar.b(minecraft, mouseX, mouseY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void a(int mouseX, int mouseY) {
/* 1130 */     if (this.dragItem != null) {
/*      */       
/* 1132 */       if (this.mouseDelta.booleanValue())
/*      */       {
/* 1134 */         for (int targetId = 0; targetId < this.dragTargets.size(); targetId++) {
/*      */           
/* 1136 */           IDragDrop target = this.dragTargets.get(targetId);
/*      */           
/* 1138 */           if (target.dragDrop(this, this.dragItem, mouseX, mouseY)) {
/*      */             
/* 1140 */             if (target != this) {
/*      */               
/* 1142 */               removeItem(this.dragItem);
/* 1143 */               save();
/*      */             } 
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 1151 */       this.dragItem = null;
/*      */     } 
/*      */     
/* 1154 */     if (this.mouseDownObject != null) {
/*      */       
/* 1156 */       this.mouseDownObject.mouseReleased(mouseX, mouseY);
/* 1157 */       this.mouseDownObject = null;
/*      */     } 
/*      */     
/* 1160 */     this.scrollBar.a(mouseX, mouseY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getItemIndexAt(int mouseX, int mouseY) {
/* 1172 */     int row = this.scrollBar.getValue() + (mouseY - getYPosition()) / this.rowSize;
/*      */     
/* 1174 */     if (this.itemsPerRow > 1) {
/*      */       
/* 1176 */       if (!isMouseOver(null, mouseX, mouseY)) return -1;
/*      */       
/* 1178 */       int itemWidth = (this.itemsPerRow > 1) ? this.rowSize : (getWidth() - 20);
/* 1179 */       int col = (mouseX - getXPosition()) / itemWidth;
/*      */       
/* 1181 */       if (col < this.itemsPerRow) {
/* 1182 */         return col + row * this.itemsPerRow;
/*      */       }
/* 1184 */       return -1;
/*      */     } 
/*      */     
/* 1187 */     return row;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListObject getItemAtPosition(int mouseX, int mouseY) {
/* 1199 */     int index = getItemIndexAt(mouseX, mouseY);
/*      */     
/* 1201 */     if (index > -1 && index < this.items.size())
/*      */     {
/* 1203 */       return this.items.get(index);
/*      */     }
/*      */     
/* 1206 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean c(bsu minecraft, int mouseX, int mouseY) {
/* 1220 */     this.actionPerformed = false;
/* 1221 */     this.doubleClicked = false;
/*      */     
/* 1223 */     IListObject editInPlaceObject = getEditingInPlaceObject();
/*      */     
/* 1225 */     if (editInPlaceObject != null) {
/*      */       
/* 1227 */       Rectangle editInPlaceItemRect = getItemBoundingBox(this.editInPlaceItem);
/*      */       
/* 1229 */       if (editInPlaceObject.editInPlaceMousePressed(this.iconsEnabled, minecraft, mouseX, mouseY, editInPlaceItemRect.x, editInPlaceItemRect.y, editInPlaceItemRect.width, editInPlaceItemRect.height)) {
/*      */         
/* 1231 */         this.mouseDownObject = editInPlaceObject;
/* 1232 */         this.actionPerformed = false;
/* 1233 */         this.doubleClicked = false;
/* 1234 */         return true;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1239 */     if (this.scrollBar.c(minecraft, mouseX, mouseY)) return true;
/*      */     
/* 1241 */     if (super.c(minecraft, mouseX, mouseY)) {
/*      */ 
/*      */       
/* 1244 */       if (mouseX < getXPosition() + getWidth() - 20) {
/*      */ 
/*      */         
/* 1247 */         int newSelectedItem = getItemIndexAt(mouseX, mouseY);
/*      */ 
/*      */         
/* 1250 */         if (newSelectedItem > -1 && newSelectedItem < this.items.size()) {
/*      */ 
/*      */           
/* 1253 */           IListObject item = this.items.get(newSelectedItem);
/* 1254 */           Rectangle itemRect = getItemBoundingBox(newSelectedItem);
/*      */ 
/*      */           
/* 1257 */           if (item.getCustomDrawBehaviour() != IListObject.CustomDrawBehaviour.NoCustomDraw) {
/*      */             
/* 1259 */             this.mouseDownObject = item;
/* 1260 */             this.actionPerformed = item.mousePressed(this.iconsEnabled, minecraft, mouseX, mouseY, itemRect.x, itemRect.y, itemRect.width, itemRect.height);
/*      */           } 
/*      */           
/* 1263 */           if (newSelectedItem != this.selectedItem) {
/*      */             
/* 1265 */             this.actionPerformed = true;
/* 1266 */             this.selectedItem = newSelectedItem;
/* 1267 */             updateScrollPosition();
/* 1268 */             this.timer = this.updateCounter;
/*      */           }
/*      */           else {
/*      */             
/* 1272 */             if (this.updateCounter - this.timer < 9) {
/*      */               
/* 1274 */               this.actionPerformed = true;
/* 1275 */               this.doubleClicked = true;
/*      */             } 
/*      */             
/* 1278 */             this.timer = this.updateCounter;
/*      */           } 
/*      */ 
/*      */           
/* 1282 */           if (!this.doubleClicked && this.isDragSource && this.dragTargets.size() > 0 && item.isDraggable()) {
/*      */             
/* 1284 */             this.dragItem = item;
/* 1285 */             this.dragOffset = new Point(itemRect.x - mouseX, itemRect.y - mouseY);
/* 1286 */             this.mouseDownLocation.x = mouseX;
/* 1287 */             this.mouseDownLocation.y = mouseY;
/* 1288 */             this.mouseDelta = Boolean.valueOf(false);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1293 */       return true;
/*      */     } 
/*      */     
/* 1296 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void save() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListObject createObject(String text) {
/* 1315 */     return createObject(text, -1, (oa)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListObject createObject(String text, int iconId, oa iconTexture) {
/* 1328 */     return (IListObject)new ListObjectGeneric(this.newItemIndex++, text, null, (this.iconsEnabled && iconId > -1), iconTexture, iconId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListObject createObject(String text, amj displayItem) {
/* 1337 */     return (IListObject)new ListObjectGeneric(this.newItemIndex++, text, null, displayItem);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListObject createObject(String text, amj displayItem, Object data) {
/* 1345 */     return (IListObject)new ListObjectGeneric(this.newItemIndex++, text, data, displayItem);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListObject createObject(String text, int iconId, oa iconTexture, Object data) {
/* 1359 */     return (IListObject)new ListObjectGeneric(this.newItemIndex++, text, data, (this.iconsEnabled && iconId > -1), iconTexture, iconId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListObject createObject(String text, int iconId) {
/* 1371 */     return createObject(text, iconId, "/gui/items.png");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IListObject createObject(String text, int iconId, Object data) {
/* 1383 */     return createObject(text, iconId);
/*      */   }
/*      */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\GuiListBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */