/*     */ package net.eq2online.macros.gui.controls.specialised;
/*     */ 
/*     */ import alq;
/*     */ import amj;
/*     */ import amk;
/*     */ import bsu;
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.list.ListObjectEditable;
/*     */ import net.eq2online.macros.gui.list.ListObjectGeneric;
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
/*     */ public class GuiListBoxFile
/*     */   extends GuiListBox
/*     */   implements FilenameFilter
/*     */ {
/*     */   protected File directory;
/*     */   protected String filterExtension;
/*     */   
/*     */   public GuiListBoxFile(bsu minecraft, int controlId, boolean showIcons, File directory, String filterExtension) {
/*  45 */     super(minecraft, controlId, showIcons, false, false);
/*     */     
/*  47 */     this.directory = directory;
/*  48 */     this.filterExtension = filterExtension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean accept(File dir, String name) {
/*  55 */     if (name.startsWith(".")) {
/*  56 */       return false;
/*     */     }
/*     */     
/*  59 */     if (name.matches("^[A-Za-z0-9\\x20_\\-\\.]+\\." + this.filterExtension + "$")) {
/*  60 */       return true;
/*     */     }
/*  62 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refresh() {
/*  70 */     File[] files = this.directory.listFiles(this);
/*  71 */     this.items.clear();
/*     */     
/*  73 */     if (files != null)
/*     */     {
/*  75 */       for (int fileId = 0; fileId < files.length; fileId++) {
/*     */         
/*  77 */         amj icon = MacroModCore.checkDisallowedTextFileName(files[fileId].getName()) ? new amj((alq)amk.bV) : new amj(amk.aK);
/*  78 */         this.items.add(new ListObjectEditable(fileId, icon, files[fileId].getName(), files[fileId].getName()));
/*     */       } 
/*     */     }
/*     */     
/*  82 */     this.items.add(new ListObjectGeneric(-1, LocalisationProvider.getLocalisedString("list.new.file"), Integer.valueOf(-1)));
/*     */     
/*  84 */     this.scrollBar.setMax(Math.max(0, this.items.size() - this.displayRowCount));
/*  85 */     updateScrollPosition();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawItem(IListObject item, bsu minecraft, int mouseX, int mouseY, int itemX, int itemY, int itemWidth, int itemHeight, int itemTextColour) {
/*  94 */     if (isEnabled() && MacroModCore.checkDisallowedTextFileName(item.getText())) {
/*  95 */       itemTextColour = -6710887;
/*     */     }
/*  97 */     super.drawItem(item, minecraft, mouseX, mouseY, itemX, itemY, itemWidth, itemHeight, itemTextColour);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IListObject removeSelectedItem() {
/* 108 */     if (this.items.size() > this.selectedItem) {
/*     */       
/* 110 */       IListObject selected = getSelectedItem();
/*     */ 
/*     */       
/* 113 */       if (selected != null && MacroModCore.checkDisallowedTextFileName((String)selected.getData()))
/*     */       {
/* 115 */         return null;
/*     */       }
/*     */       
/* 118 */       IListObject removed = this.items.remove(this.selectedItem);
/* 119 */       File deleteFile = new File(this.directory, (String)removed.getData());
/*     */ 
/*     */       
/*     */       try {
/* 123 */         if (deleteFile.exists()) deleteFile.delete(); 
/*     */       } catch (Exception ex) {
/* 125 */         Log.printStackTrace(ex);
/*     */       } 
/* 127 */       refresh();
/* 128 */       updateScrollPosition();
/* 129 */       return removed;
/*     */     } 
/*     */     
/* 132 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\specialised\GuiListBoxFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */