/*     */ package net.eq2online.macros.gui.controls.specialised;
/*     */ 
/*     */ import bsu;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.gui.controls.GuiListBoxFilebound;
/*     */ import net.eq2online.macros.gui.list.ListObjectEditable;
/*     */ import net.eq2online.macros.gui.list.ListObjectGeneric;
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
/*     */ public class GuiListBoxTowns
/*     */   extends GuiListBoxFilebound
/*     */ {
/*     */   public GuiListBoxTowns(bsu minecraft, int controlId) {
/*  26 */     super(minecraft, controlId, true, true, true, ".towns.txt");
/*     */     
/*  28 */     this.iconTexture = ResourceLocations.DYNAMIC_TOWNS;
/*  29 */     this.editable = true;
/*     */     
/*  31 */     this.saveTrimTailSize = 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sort() {
/*  37 */     IListObject newEntry = getItemById(-1);
/*  38 */     removeItemAt(this.items.size() - 1);
/*  39 */     super.sort();
/*  40 */     addItem(newEntry);
/*  41 */     save();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyChangeConfiguration() {
/*  47 */     if (MacroModSettings.configsForTowns) {
/*  48 */       super.notifyChangeConfiguration();
/*     */     } else {
/*  50 */       this.items = (ArrayList)this.configs.get("");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void load() {
/*  56 */     super.load();
/*     */     
/*  58 */     ListObjectGeneric newTown = new ListObjectGeneric(-1, LocalisationProvider.getLocalisedString("list.new.town"), Integer.valueOf(-1));
/*     */     
/*  60 */     for (Map.Entry<String, ArrayList<IListObject>> config : (Iterable<Map.Entry<String, ArrayList<IListObject>>>)this.configs.entrySet()) {
/*  61 */       ((ArrayList<ListObjectGeneric>)config.getValue()).add(newTown);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void notifyNewConfig(ArrayList<IListObject> newConfig) {
/*  67 */     newConfig.add(new ListObjectGeneric(-1, LocalisationProvider.getLocalisedString("list.new.town"), Integer.valueOf(-1)));
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
/*     */   public IListObject createObject(String text, int iconId) {
/*  80 */     return createObject(text, iconId, ResourceLocations.DYNAMIC_TOWNS);
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
/*     */   public IListObject createObject(String text, int iconId, oa iconTexture) {
/*  94 */     return (IListObject)new ListObjectEditable(this.newItemIndex++, iconId, text, iconTexture);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addItemAt(IListObject newItem, int itemIndex) {
/* 100 */     if (itemIndex >= this.items.size() - 1) itemIndex = this.items.size() - 1; 
/* 101 */     super.addItemAt(newItem, itemIndex);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\specialised\GuiListBoxTowns.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */