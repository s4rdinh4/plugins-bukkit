/*     */ package net.eq2online.macros.gui.controls.specialised;
/*     */ 
/*     */ import bsu;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.gui.controls.GuiListBoxFilebound;
/*     */ import net.eq2online.macros.gui.list.ListObjectFriend;
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
/*     */ public class GuiListBoxFriends
/*     */   extends GuiListBoxFilebound
/*     */ {
/*     */   private Pattern oldPattern;
/*     */   
/*     */   public GuiListBoxFriends(bsu minecraft, int controlId) {
/*  40 */     super(minecraft, controlId, true, true, true, ".friends.txt");
/*     */     
/*  42 */     this.saveTrimTailSize = 1;
/*     */     
/*  44 */     this.linePattern = Pattern.compile("^([0-9]{1,3}):([a-zA-Z0-9_]{2,16})", 2);
/*  45 */     this.oldPattern = Pattern.compile("^([a-zA-Z0-9_]{2,16}),(\\d{1,3})", 2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sort() {
/*  51 */     IListObject newEntry = getItemById(-1);
/*  52 */     removeItemAt(this.items.size() - 1);
/*  53 */     super.sort();
/*  54 */     addItem(newEntry);
/*  55 */     save();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyChangeConfiguration() {
/*  61 */     if (MacroModSettings.configsForFriends) {
/*  62 */       super.notifyChangeConfiguration();
/*     */     } else {
/*  64 */       this.items = (ArrayList)this.configs.get("");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void load() {
/*  70 */     super.load();
/*     */     
/*  72 */     ListObjectGeneric newFriend = new ListObjectGeneric(-1, LocalisationProvider.getLocalisedString("list.new.friend"), Integer.valueOf(-1));
/*     */     
/*  74 */     for (Map.Entry<String, ArrayList<IListObject>> config : (Iterable<Map.Entry<String, ArrayList<IListObject>>>)this.configs.entrySet()) {
/*  75 */       ((ArrayList<ListObjectGeneric>)config.getValue()).add(newFriend);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadItem(String line, Matcher linePatternMatcher, ArrayList<IListObject> items, String currentConfigName) {
/*  81 */     if (linePatternMatcher.matches()) {
/*     */       
/*  83 */       items.add(new ListObjectFriend(this.newItemIndex++, Integer.parseInt(linePatternMatcher.group(1)), linePatternMatcher.group(2)));
/*     */     }
/*     */     else {
/*     */       
/*  87 */       Matcher friendPatternMatcher = this.oldPattern.matcher(line);
/*     */       
/*  89 */       if (friendPatternMatcher.matches())
/*     */       {
/*  91 */         items.add(new ListObjectFriend(this.newItemIndex++, Integer.parseInt(friendPatternMatcher.group(2)), friendPatternMatcher.group(1)));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void notifyNewConfig(ArrayList<IListObject> newConfig) {
/*  99 */     newConfig.add(new ListObjectGeneric(-1, LocalisationProvider.getLocalisedString("list.new.friend"), Integer.valueOf(-1)));
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
/* 112 */     return createObject(text, iconId, ResourceLocations.FRIENDS);
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
/* 126 */     return (IListObject)new ListObjectFriend(this.newItemIndex++, iconId, text);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addItemAt(IListObject newItem, int itemIndex) {
/* 132 */     if (itemIndex >= this.items.size() - 1) itemIndex = this.items.size() - 1; 
/* 133 */     super.addItemAt(newItem, itemIndex);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\specialised\GuiListBoxFriends.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */