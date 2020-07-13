/*     */ package net.eq2online.macros.gui.list;
/*     */ 
/*     */ import amj;
/*     */ import bsu;
/*     */ import com.mumfrey.liteloader.util.render.Icon;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.interfaces.IListObject;
/*     */ import net.eq2online.macros.rendering.FontRendererLegacy;
/*     */ import oa;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ListObjectEditable
/*     */   extends ListObjectGeneric
/*     */ {
/*  17 */   protected String customAction = "";
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean allowEdit = true;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean allowDelete = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ListObjectEditable(int id, String text, Object data, boolean allowEdit, boolean allowDelete) {
/*  31 */     super(id, text, data);
/*     */     
/*  33 */     this.allowEdit = allowEdit;
/*  34 */     this.allowDelete = allowDelete;
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
/*     */   public ListObjectEditable(int id, int iconID, String text, oa iconTexture) {
/*  46 */     super(id, text, text, true, iconTexture, iconID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ListObjectEditable(int id, amj displayItem, String text) {
/*  57 */     super(id, text, text, displayItem);
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
/*     */   public ListObjectEditable(int id, int iconID, String text, oa iconTexture, Object data) {
/*  69 */     super(id, text, data, true, iconTexture, iconID);
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
/*     */   public ListObjectEditable(int id, amj displayItem, String text, Object data) {
/*  81 */     super(id, text, data, displayItem);
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
/*     */   public ListObjectEditable(int id, String text, Object data, oa iconTexture, Icon icon) {
/*  93 */     super(id, text, data, iconTexture, icon);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayName() {
/*  99 */     return (this.displayName != null && this.displayName.length() > 0) ? this.displayName : getText();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IListObject.CustomDrawBehaviour getCustomDrawBehaviour() {
/* 108 */     return IListObject.CustomDrawBehaviour.CustomDrawExtra;
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
/*     */   public void drawCustom(boolean iconEnabled, bsu minecraft, int mouseX, int mouseY, int xPosition, int yPosition, int width, int height, int updateCounter) {
/* 125 */     FontRendererLegacy fontRendererLegacy = MacroModCore.getInstance().getLegacyFontRenderer();
/*     */     
/* 127 */     if (this.allowDelete)
/*     */     {
/* 129 */       fontRendererLegacy.a("x", xPosition + width - 8, yPosition, -65536);
/*     */     }
/*     */     
/* 132 */     if (this.allowEdit) {
/*     */       
/* 134 */       fontRendererLegacy.a("...", xPosition + width - 8, yPosition + 11, -6250336);
/* 135 */       fontRendererLegacy.a("/", xPosition + width - 8, yPosition + 10, -4145152);
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
/*     */ 
/*     */   
/*     */   public boolean mousePressed(boolean iconEnabled, bsu minecraft, int mouseX, int mouseY, int xPosition, int yPosition, int width, int height) {
/* 154 */     if (mouseX > xPosition + width - 8 && mouseX < xPosition + width) {
/*     */       
/* 156 */       if (mouseY > yPosition && mouseY < yPosition + 8 && this.allowDelete) {
/*     */         
/* 158 */         this.customAction = "delete";
/* 159 */         return true;
/*     */       } 
/* 161 */       if (mouseY > yPosition + 10 && mouseY < yPosition + 18 && this.allowEdit) {
/*     */         
/* 163 */         this.customAction = "edit";
/* 164 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 168 */     this.customAction = "";
/* 169 */     return false;
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
/*     */   public void mouseReleased(int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCustomAction(boolean bClear) {
/* 192 */     String c = this.customAction;
/* 193 */     if (bClear) this.customAction = ""; 
/* 194 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDraggable() {
/* 200 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\list\ListObjectEditable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */