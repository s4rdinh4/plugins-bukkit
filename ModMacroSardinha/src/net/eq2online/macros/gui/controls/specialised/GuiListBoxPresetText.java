/*     */ package net.eq2online.macros.gui.controls.specialised;
/*     */ 
/*     */ import bsu;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.gui.controls.GuiListBoxFilebound;
/*     */ import net.eq2online.macros.gui.list.ListObjectEditInPlace;
/*     */ import net.eq2online.macros.gui.list.ListObjectGeneric;
/*     */ import net.eq2online.macros.gui.shared.GuiControlEx;
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
/*     */ public class GuiListBoxPresetText
/*     */   extends GuiListBoxFilebound
/*     */ {
/*     */   public GuiListBoxPresetText(bsu minecraft, int controlId, String sourceFileName) {
/*  29 */     super(minecraft, controlId, false, true, true, sourceFileName);
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
/*  47 */     if (MacroModSettings.configsForPresets) {
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
/*  58 */     ListObjectGeneric newPreset = new ListObjectGeneric(-1, LocalisationProvider.getLocalisedString("list.new.preset"), Integer.valueOf(-1));
/*     */     
/*  60 */     for (Map.Entry<String, ArrayList<IListObject>> config : (Iterable<Map.Entry<String, ArrayList<IListObject>>>)this.configs.entrySet()) {
/*  61 */       ((ArrayList<ListObjectGeneric>)config.getValue()).add(newPreset);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadItem(String line, Matcher linePatternMatcher, ArrayList<IListObject> items, String currentConfigName) {
/*  67 */     if (linePatternMatcher.matches())
/*     */     {
/*  69 */       items.add(new ListObjectEditInPlace(this.newItemIndex++, 0, linePatternMatcher.group(1), ResourceLocations.ITEMS, ""));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void notifyNewConfig(ArrayList<IListObject> newConfig) {
/*  76 */     newConfig.add(new ListObjectGeneric(-1, LocalisationProvider.getLocalisedString("list.new.preset"), Integer.valueOf(-1)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean left() {
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean right() {
/*  94 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected GuiControlEx.KeyHandledState keyTyped(char keyChar, int keyCode) {
/* 100 */     if (keyCode == 205) { beginEditInPlace(); return GuiControlEx.KeyHandledState.Handled; }
/*     */     
/* 102 */     return super.keyTyped(keyChar, keyCode);
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
/* 115 */     return createObject(text, 0, (oa)null);
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
/* 129 */     return (IListObject)new ListObjectEditInPlace(this.newItemIndex++, iconId, text, iconTexture, LocalisationProvider.getLocalisedString("list.prompt.preset"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addItemAt(IListObject newItem, int itemIndex) {
/* 135 */     if (itemIndex >= this.items.size() - 1) itemIndex = this.items.size() - 1; 
/* 136 */     super.addItemAt(newItem, itemIndex);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\specialised\GuiListBoxPresetText.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */