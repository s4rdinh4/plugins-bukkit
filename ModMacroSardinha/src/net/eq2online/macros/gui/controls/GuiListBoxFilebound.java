/*     */ package net.eq2online.macros.gui.controls;
/*     */ 
/*     */ import bsu;
/*     */ import com.mumfrey.liteloader.util.render.Icon;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.compatibility.IconTiled;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.gui.list.ListObjectEditable;
/*     */ import net.eq2online.macros.gui.list.ListObjectGeneric;
/*     */ import net.eq2online.macros.interfaces.IListObject;
/*     */ import net.eq2online.macros.interfaces.IMultipleConfigurations;
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
/*     */ public abstract class GuiListBoxFilebound
/*     */   extends GuiListBox
/*     */   implements IMultipleConfigurations
/*     */ {
/*  36 */   private static Pattern beginConfigPattern = Pattern.compile("^DIRECTIVE BEGINCONFIG\\(\\) (.+)$");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   protected static Pattern displayNamePattern = Pattern.compile("\\[Display=(.+)\\]$");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean editable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected File file;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Pattern linePattern;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected oa iconTexture;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   protected int saveTrimTailSize = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   protected HashMap<String, ArrayList<IListObject>> configs = new HashMap<String, ArrayList<IListObject>>();
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
/*     */   public GuiListBoxFilebound(bsu minecraft, int controlId, boolean showIcons, boolean dragSource, boolean dragTarget, String sourceFileName) {
/*  87 */     super(minecraft, controlId, showIcons, dragSource, dragTarget);
/*     */     
/*  89 */     this.editable = false;
/*     */     
/*  91 */     if (showIcons) {
/*  92 */       this.linePattern = Pattern.compile("^([0-9]{1,3}):(.+)$", 2);
/*     */     } else {
/*  94 */       this.linePattern = Pattern.compile("^(.+)$");
/*     */     } 
/*  96 */     this.file = new File(MacroModCore.getMacrosDirectory(), sourceFileName);
/*     */     
/*  98 */     MacroModCore.registerMultipleConfigurationObject(this);
/*     */     
/* 100 */     setSortable(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getFile() {
/* 110 */     return this.file;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load() {
/* 118 */     this.items.clear();
/* 119 */     this.configs.clear();
/* 120 */     this.configs.put("", new ArrayList<IListObject>());
/*     */     
/* 122 */     if (this.file.exists()) {
/*     */       
/*     */       try {
/*     */         
/* 126 */         BufferedReader bufferedreader = new BufferedReader(new FileReader(this.file));
/* 127 */         String currentConfigName = "";
/*     */         
/* 129 */         for (String line = ""; (line = bufferedreader.readLine()) != null; ) {
/*     */           
/* 131 */           Matcher beginConfigPatternMatcher = beginConfigPattern.matcher(line);
/*     */           
/* 133 */           if (beginConfigPatternMatcher.matches()) {
/*     */             
/* 135 */             currentConfigName = beginConfigPatternMatcher.group(1);
/*     */             
/* 137 */             if (!this.configs.containsKey(currentConfigName)) {
/* 138 */               this.configs.put(currentConfigName, new ArrayList<IListObject>());
/*     */             }
/*     */             continue;
/*     */           } 
/* 142 */           Matcher linePatternMatcher = this.linePattern.matcher(line);
/* 143 */           loadItem(line, linePatternMatcher, this.configs.get(currentConfigName), currentConfigName);
/*     */         } 
/*     */ 
/*     */         
/* 147 */         bufferedreader.close();
/*     */       }
/* 149 */       catch (Exception ex) {
/*     */         
/* 151 */         Log.info("Error loading data for list box in {0}", new Object[] { this.file.getName() });
/* 152 */         Log.printStackTrace(ex);
/*     */       } 
/*     */     }
/*     */     
/* 156 */     notifyChangeConfiguration();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ArrayList<IListObject> getConfig(String configName) {
/* 167 */     if (!this.configs.containsKey(configName)) {
/*     */       
/* 169 */       ArrayList<IListObject> newConfig = new ArrayList<IListObject>();
/* 170 */       this.configs.put(configName, newConfig);
/* 171 */       notifyNewConfig(newConfig);
/*     */     } 
/*     */     
/* 174 */     return this.configs.get(configName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void notifyNewConfig(ArrayList<IListObject> paramArrayList);
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyChangeConfiguration() {
/* 185 */     this.items = getConfig(MacroModCore.getMacroManager().getActiveConfig());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addConfiguration(String configName, boolean copy) {
/* 194 */     if (!this.configs.containsKey(configName))
/*     */     {
/* 196 */       if (copy) {
/*     */         
/* 198 */         this.configs.put(configName, this.items);
/* 199 */         save();
/* 200 */         load();
/*     */       }
/*     */       else {
/*     */         
/* 204 */         ArrayList<IListObject> newConfig = new ArrayList<IListObject>();
/* 205 */         this.configs.put(configName, newConfig);
/* 206 */         notifyNewConfig(newConfig);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeConfiguration(String configName) {
/* 217 */     if (this.configs.containsKey(configName)) {
/*     */       
/* 219 */       this.configs.remove(configName);
/* 220 */       save();
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
/*     */   protected void loadItem(String line, Matcher linePatternMatcher, ArrayList<IListObject> items, String currentConfigName) {
/* 232 */     if (linePatternMatcher.matches()) {
/*     */       
/* 234 */       if (this.iconsEnabled) line = linePatternMatcher.group(2);
/*     */       
/* 236 */       String displayName = null;
/* 237 */       Matcher displayNamePatternMatcher = displayNamePattern.matcher(line);
/*     */       
/* 239 */       if (displayNamePatternMatcher.find()) {
/*     */         
/* 241 */         displayName = displayNamePatternMatcher.group(1);
/* 242 */         line = line.substring(0, displayNamePatternMatcher.start());
/*     */       } 
/*     */       
/* 245 */       ListObjectGeneric newItem = null;
/*     */       
/* 247 */       if (this.iconsEnabled) {
/*     */         
/* 249 */         int iconIndex = Integer.parseInt(linePatternMatcher.group(1));
/*     */         
/* 251 */         if (this.editable) {
/* 252 */           ListObjectEditable listObjectEditable = new ListObjectEditable(this.newItemIndex++, iconIndex, line, this.iconTexture);
/*     */         } else {
/* 254 */           newItem = new ListObjectGeneric(this.newItemIndex++, line, null, (iconIndex >= 0), this.iconTexture, iconIndex);
/*     */         } 
/*     */       } else {
/*     */         
/* 258 */         newItem = new ListObjectGeneric(this.newItemIndex++, line);
/*     */       } 
/*     */       
/* 261 */       items.add(newItem);
/*     */       
/* 263 */       if (displayName != null)
/*     */       {
/* 265 */         newItem.setDisplayName(displayName);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void save() {
/*     */     try {
/* 278 */       PrintWriter printwriter = new PrintWriter(new FileWriter(this.file));
/*     */       
/* 280 */       for (Map.Entry<String, ArrayList<IListObject>> config : this.configs.entrySet()) {
/*     */         
/* 282 */         if (!((String)config.getKey()).equals("")) {
/* 283 */           printwriter.println("\nDIRECTIVE BEGINCONFIG() " + (String)config.getKey() + "\n");
/*     */         }
/* 285 */         for (int i = 0; i < ((ArrayList)config.getValue()).size() - this.saveTrimTailSize; i++) {
/*     */           
/* 287 */           IListObject item = ((ArrayList<IListObject>)config.getValue()).get(i);
/* 288 */           String itemSerialised = item.serialise();
/*     */           
/* 290 */           if (itemSerialised == null) {
/*     */             
/* 292 */             Icon icon = item.getIcon();
/* 293 */             int iconIndex = (icon != null && icon instanceof IconTiled) ? ((IconTiled)icon).getIconID() : 0;
/* 294 */             itemSerialised = (this.iconsEnabled ? (iconIndex + ":") : "") + item.getText();
/* 295 */             if (!item.getDisplayName().equals(item.getText()) && item.getDisplayName().length() > 0) itemSerialised = itemSerialised + "[Display=" + item.getDisplayName() + "]";
/*     */           
/*     */           } 
/* 298 */           printwriter.println(itemSerialised);
/*     */         } 
/*     */       } 
/*     */       
/* 302 */       printwriter.close();
/*     */     }
/* 304 */     catch (Exception ex) {
/*     */       
/* 306 */       Log.printStackTrace(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\GuiListBoxFilebound.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */