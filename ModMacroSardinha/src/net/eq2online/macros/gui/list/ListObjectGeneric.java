/*     */ package net.eq2online.macros.gui.list;
/*     */ 
/*     */ import amj;
/*     */ import bsu;
/*     */ import bub;
/*     */ import com.mumfrey.liteloader.util.render.Icon;
/*     */ import cqh;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.IconTiled;
/*     */ import net.eq2online.macros.interfaces.IListObject;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import oa;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ListObjectGeneric
/*     */   extends bub
/*     */   implements IListObject
/*     */ {
/*     */   protected int id;
/*     */   protected boolean hasIcon;
/*     */   protected oa iconTexture;
/*     */   protected Icon icon;
/*     */   protected String text;
/*     */   protected String displayName;
/*     */   protected Object data;
/*  62 */   protected Boolean draggable = Boolean.valueOf(false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected amj displayItem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ListObjectGeneric(int id, String text, Object data, boolean hasIcon, oa iconTexture, int iconID) {
/*  78 */     this.id = id;
/*  79 */     this.text = text;
/*  80 */     this.data = data;
/*  81 */     this.hasIcon = hasIcon;
/*  82 */     initIcon(iconTexture, iconID);
/*     */   }
/*     */ 
/*     */   
/*     */   public ListObjectGeneric(int id, String text, Object data, oa iconTexture, Icon icon) {
/*  87 */     this.id = id;
/*  88 */     this.text = text;
/*  89 */     this.data = data;
/*  90 */     this.hasIcon = (icon != null);
/*  91 */     this.icon = icon;
/*  92 */     this.iconTexture = iconTexture;
/*     */   }
/*     */ 
/*     */   
/*     */   public ListObjectGeneric(int id, String text, Object data, Icon icon) {
/*  97 */     this(id, text, data, (icon instanceof IconTiled) ? ((IconTiled)icon).getTextureResource() : ResourceLocations.ITEMS, icon);
/*     */   }
/*     */ 
/*     */   
/*     */   public ListObjectGeneric(int id, String text, Icon icon) {
/* 102 */     this(id, text, null, (icon instanceof IconTiled) ? ((IconTiled)icon).getTextureResource() : ResourceLocations.ITEMS, icon);
/*     */   }
/*     */ 
/*     */   
/*     */   public ListObjectGeneric(int id, String text, Object data, amj displayItem) {
/* 107 */     this.id = id;
/* 108 */     this.text = text;
/* 109 */     this.data = data;
/* 110 */     this.hasIcon = false;
/* 111 */     this.icon = null;
/* 112 */     this.iconTexture = null;
/* 113 */     this.displayItem = displayItem;
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
/*     */   public ListObjectGeneric(int id, String text, Object data) {
/* 125 */     this.id = id;
/* 126 */     this.text = text;
/* 127 */     this.hasIcon = false;
/* 128 */     this.data = data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ListObjectGeneric(int id, String text) {
/* 139 */     this.id = id;
/* 140 */     this.text = text;
/* 141 */     this.hasIcon = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initIcon(oa iconTexture, int iconID) {
/* 150 */     iconID %= 256;
/* 151 */     this.icon = (iconTexture != null) ? (Icon)new IconTiled(iconTexture, iconID) : null;
/* 152 */     this.iconTexture = iconTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDraggable(Boolean newDraggableState) {
/* 162 */     this.draggable = newDraggableState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasIcon() {
/* 171 */     return this.hasIcon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public oa getIconTexture() {
/* 180 */     return this.iconTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void bindIconTexture() {
/* 186 */     Icon icon = getIcon();
/* 187 */     if (icon != null) {
/*     */       
/* 189 */       if (icon instanceof IconTiled)
/*     */       {
/* 191 */         AbstractionLayer.bindTexture(((IconTiled)icon).getTextureResource());
/*     */       }
/*     */       
/* 194 */       AbstractionLayer.bindTexture(this.iconTexture);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Icon getIcon() {
/* 205 */     return this.icon;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public amj getDisplayItem() {
/* 211 */     return this.displayItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 220 */     return this.text;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayName() {
/* 226 */     return getText();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/* 235 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIdentifier() {
/* 241 */     return String.valueOf(this.id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getData() {
/* 250 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanEditInPlace() {
/* 256 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEditingInPlace() {
/* 262 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void beginEditInPlace() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endEditInPlace() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean editInPlaceKeyTyped(char keyChar, int keyCode) {
/* 278 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean editInPlaceMousePressed(boolean iconEnabled, bsu minecraft, int mouseX, int mouseY, int xPosition, int yPosition, int width, int height) {
/* 286 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void editInPlaceDraw(boolean iconEnabled, bsu minecraft, int mouseX, int mouseY, int xPosition, int yPosition, int width, int height, int updateCounter) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IListObject.CustomDrawBehaviour getCustomDrawBehaviour() {
/* 302 */     return IListObject.CustomDrawBehaviour.NoCustomDraw;
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
/*     */   public void drawCustom(boolean iconEnabled, bsu minecraft, int mouseX, int mouseY, int xPosition, int yPosition, int width, int height, int updateCounter) {}
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
/*     */   public boolean mousePressed(boolean iconEnabled, bsu minecraft, int mouseX, int mouseY, int xPosition, int yPosition, int width, int height) {
/* 336 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCustomAction(boolean bClear) {
/* 354 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDraggable() {
/* 360 */     return this.draggable.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIconId(int newIconId) {
/* 366 */     if (this.icon != null && this.icon instanceof IconTiled) {
/*     */       
/* 368 */       newIconId %= 256;
/* 369 */       ((IconTiled)this.icon).setIconID(newIconId);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String newText) {
/* 376 */     this.text = newText;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisplayName(String newDisplayName) {
/* 382 */     if (newDisplayName.equals(getText())) {
/* 383 */       newDisplayName = "";
/*     */     }
/* 385 */     this.displayName = newDisplayName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(int newId) {
/* 391 */     this.id = newId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setData(Object newData) {
/* 397 */     this.data = newData;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String serialise() {
/* 403 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean renderIcon(bsu minecraft, int xPosition, int yPosition) {
/* 409 */     if (this.displayItem != null) {
/*     */       
/* 411 */       cqh render = minecraft.af();
/* 412 */       render.a(this.displayItem, xPosition, yPosition);
/* 413 */       return true;
/*     */     } 
/*     */     
/* 416 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\list\ListObjectGeneric.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */