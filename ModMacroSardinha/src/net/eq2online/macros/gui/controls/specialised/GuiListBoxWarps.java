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
/*     */ public class GuiListBoxWarps
/*     */   extends GuiListBoxFilebound
/*     */ {
/*     */   public GuiListBoxWarps(bsu minecraft, int controlId) {
/*  25 */     super(minecraft, controlId, true, true, true, ".warps.txt");
/*     */     
/*  27 */     this.iconTexture = ResourceLocations.DYNAMIC_TOWNS;
/*  28 */     this.editable = true;
/*     */     
/*  30 */     this.saveTrimTailSize = 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sort() {
/*  36 */     IListObject newEntry = getItemById(-1);
/*  37 */     removeItemAt(this.items.size() - 1);
/*  38 */     super.sort();
/*  39 */     addItem(newEntry);
/*  40 */     save();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyChangeConfiguration() {
/*  46 */     if (MacroModSettings.configsForWarps) {
/*  47 */       super.notifyChangeConfiguration();
/*     */     } else {
/*  49 */       this.items = (ArrayList)this.configs.get("");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void load() {
/*  55 */     super.load();
/*     */     
/*  57 */     ListObjectGeneric newTown = new ListObjectGeneric(-1, LocalisationProvider.getLocalisedString("list.new.warp"), Integer.valueOf(-1));
/*     */     
/*  59 */     for (Map.Entry<String, ArrayList<IListObject>> config : (Iterable<Map.Entry<String, ArrayList<IListObject>>>)this.configs.entrySet()) {
/*  60 */       ((ArrayList<ListObjectGeneric>)config.getValue()).add(newTown);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void notifyNewConfig(ArrayList<IListObject> newConfig) {
/*  66 */     newConfig.add(new ListObjectGeneric(-1, LocalisationProvider.getLocalisedString("list.new.warp"), Integer.valueOf(-1)));
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
/*  79 */     return createObject(text, iconId, ResourceLocations.DYNAMIC_TOWNS);
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
/*  93 */     return (IListObject)new ListObjectEditable(this.newItemIndex++, iconId, text, iconTexture);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addItemAt(IListObject newItem, int itemIndex) {
/*  99 */     if (itemIndex >= this.items.size() - 1) itemIndex = this.items.size() - 1; 
/* 100 */     super.addItemAt(newItem, itemIndex);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\specialised\GuiListBoxWarps.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */