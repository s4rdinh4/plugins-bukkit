/*     */ package net.eq2online.macros.gui.controls.specialised;
/*     */ 
/*     */ import bsu;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.gui.controls.GuiListBoxFilebound;
/*     */ import net.eq2online.macros.gui.list.ListObjectGeneric;
/*     */ import net.eq2online.macros.gui.list.ListObjectPlace;
/*     */ import net.eq2online.macros.interfaces.IListObject;
/*     */ import net.eq2online.macros.struct.Place;
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
/*     */ public class GuiListBoxPlaces
/*     */   extends GuiListBoxFilebound
/*     */ {
/*     */   public GuiListBoxPlaces(bsu minecraft, int controlId) {
/*  33 */     super(minecraft, controlId, true, true, true, ".places.txt");
/*     */     
/*  35 */     this.linePattern = Place.placePattern;
/*  36 */     this.saveTrimTailSize = 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sort() {
/*  42 */     IListObject newEntry = getItemById(-1);
/*  43 */     removeItemAt(this.items.size() - 1);
/*  44 */     super.sort();
/*  45 */     addItem(newEntry);
/*  46 */     save();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyChangeConfiguration() {
/*  55 */     if (MacroModSettings.configsForPlaces) {
/*  56 */       super.notifyChangeConfiguration();
/*     */     } else {
/*  58 */       this.items = (ArrayList)this.configs.get("");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load() {
/*  67 */     super.load();
/*     */     
/*  69 */     ListObjectGeneric newPlace = new ListObjectGeneric(-1, LocalisationProvider.getLocalisedString("list.new.place"), Integer.valueOf(-1));
/*     */     
/*  71 */     for (Map.Entry<String, ArrayList<IListObject>> config : (Iterable<Map.Entry<String, ArrayList<IListObject>>>)this.configs.entrySet()) {
/*  72 */       ((ArrayList<ListObjectGeneric>)config.getValue()).add(newPlace);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void loadItem(String line, Matcher linePatternMatcher, ArrayList<IListObject> items, String currentConfigName) {
/*  81 */     if (linePatternMatcher.matches()) {
/*     */       
/*  83 */       Place newPlace = Place.parsePlace(line);
/*  84 */       if (newPlace != null) {
/*     */         
/*  86 */         items.add(new ListObjectPlace(this.newItemIndex++, newPlace));
/*     */       }
/*     */       else {
/*     */         
/*  90 */         Log.info("Parsing place failed: {0}", new Object[] { line });
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void notifyNewConfig(ArrayList<IListObject> newConfig) {
/* 101 */     newConfig.add(new ListObjectGeneric(-1, LocalisationProvider.getLocalisedString("list.new.place"), Integer.valueOf(-1)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addItemAt(IListObject newItem, int itemIndex) {
/* 110 */     if (itemIndex >= this.items.size() - 1) itemIndex = this.items.size() - 1; 
/* 111 */     super.addItemAt(newItem, itemIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IListObject createObject(String text, int iconId, Object data) {
/* 120 */     return (IListObject)new ListObjectPlace(this.newItemIndex++, (Place)data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsPlace(String placeName) {
/* 131 */     for (int i = 0; i < this.items.size(); i++) {
/*     */       
/* 133 */       if (((IListObject)this.items.get(i)).getText().equals(placeName)) {
/* 134 */         return true;
/*     */       }
/*     */     } 
/* 137 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Place getPlace(String placeName) {
/* 148 */     for (int i = 0; i < this.items.size(); i++) {
/*     */       
/* 150 */       if (((IListObject)this.items.get(i)).getText().equals(placeName)) {
/* 151 */         return (Place)((IListObject)this.items.get(i)).getData();
/*     */       }
/*     */     } 
/* 154 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\specialised\GuiListBoxPlaces.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */