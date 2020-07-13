/*     */ package net.eq2online.macros.gui.designable;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeSet;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.interfaces.ISettingsProvider;
/*     */ import net.eq2online.xml.Xml;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
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
/*     */ public final class LayoutManager
/*     */ {
/*     */   private static final String xmlns_gc = "http://eq2online.net/macros/guiconfiguration";
/*     */   private static final String xmlns_gb = "http://eq2online.net/macros/guibinding";
/*  38 */   protected static final HashMap<String, DesignableGuiLayout> layouts = new HashMap<String, DesignableGuiLayout>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   private static final HashMap<String, DesignableGuiLayout> layoutBindings = new HashMap<String, DesignableGuiLayout>();
/*     */   
/*     */   private static boolean initDone = false;
/*     */ 
/*     */   
/*     */   public static void init() {
/*  49 */     if (initDone)
/*  50 */       return;  initDone = true;
/*     */     
/*  52 */     layoutBindings.put("playback", getDefaultLayout("default", false));
/*  53 */     layoutBindings.put("ingame", getDefaultLayout("ingame", false));
/*  54 */     layoutBindings.put("indebug", getDefaultLayout("ingame", false));
/*  55 */     layoutBindings.put("scoreboard", getDefaultLayout("ingame", false));
/*  56 */     layoutBindings.put("inchat", getDefaultLayout("inchat", false));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void addLayout(DesignableGuiLayout layout) {
/*  61 */     layouts.put(layout.name, layout);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final void setBinding(String slotName, String layoutName) {
/*  66 */     if (slotName != null && layoutBindings.containsKey(slotName))
/*     */     {
/*  68 */       layoutBindings.put(slotName, getLayout(layoutName));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final List<String> getSlotNames() {
/*  74 */     List<String> orderedSlotNames = new ArrayList<String>();
/*     */     
/*  76 */     orderedSlotNames.add("playback");
/*  77 */     orderedSlotNames.add("ingame");
/*  78 */     orderedSlotNames.add("indebug");
/*  79 */     orderedSlotNames.add("inchat");
/*  80 */     orderedSlotNames.add("scoreboard");
/*     */     
/*  82 */     for (String slotName : layoutBindings.keySet()) {
/*     */       
/*  84 */       if (!orderedSlotNames.contains(slotName)) {
/*  85 */         orderedSlotNames.add(slotName);
/*     */       }
/*     */     } 
/*  88 */     return orderedSlotNames;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final String getBoundLayoutName(String slotName) {
/*  93 */     if (layoutBindings.get(slotName) != null)
/*     */     {
/*  95 */       return ((DesignableGuiLayout)layoutBindings.get(slotName)).name;
/*     */     }
/*     */     
/*  98 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final DesignableGuiLayout getBoundLayout(String slotName, boolean canBeNull) {
/* 103 */     if (layoutBindings.get(slotName) != null)
/*     */     {
/* 105 */       return layoutBindings.get(slotName);
/*     */     }
/*     */     
/* 108 */     return getDefaultLayout(slotName, canBeNull);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final DesignableGuiLayout getDefaultLayout(String slotName, boolean canBeNull) {
/* 113 */     if (slotName.equals("ingame")) return getLayout("ingame"); 
/* 114 */     if (slotName.equals("indebug")) return getLayout("ingame"); 
/* 115 */     if (slotName.equals("scoreboard")) return getLayout("ingame"); 
/* 116 */     if (slotName.equals("inchat")) return getLayout("inchat"); 
/* 117 */     if (slotName.equals("playback")) return getLayout("default"); 
/* 118 */     return canBeNull ? null : getLayout("default");
/*     */   }
/*     */ 
/*     */   
/*     */   public static final List<String> getLayoutNames() {
/* 123 */     List<String> orderedLayoutNames = new ArrayList<String>();
/*     */     
/* 125 */     orderedLayoutNames.add("default");
/* 126 */     orderedLayoutNames.add("ingame");
/* 127 */     orderedLayoutNames.add("inchat");
/*     */     
/* 129 */     TreeSet<String> sortedLayoutNames = new TreeSet<String>(layouts.keySet());
/*     */     
/* 131 */     for (String layoutName : sortedLayoutNames) {
/*     */       
/* 133 */       if (!orderedLayoutNames.contains(layoutName)) {
/* 134 */         orderedLayoutNames.add(layoutName);
/*     */       }
/*     */     } 
/* 137 */     return orderedLayoutNames;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final boolean isBuiltinLayout(String layoutName) {
/* 142 */     return layoutName.matches("^(ingame|inchat|default)$");
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean layoutExists(String layoutName) {
/* 147 */     if (layoutName == null) return false; 
/* 148 */     return layouts.containsKey(layoutName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final DesignableGuiLayout getLayout(String name) {
/* 159 */     if (!layouts.containsKey(name)) {
/*     */       
/* 161 */       DesignableGuiLayout newLayout = new DesignableGuiLayout(name);
/*     */       
/* 163 */       if (name.equals("ingame")) {
/*     */         
/* 165 */         newLayout.setColumns(2);
/* 166 */         newLayout.setRows(4);
/* 167 */         newLayout.setColumnWidth(0, 324);
/*     */         
/* 169 */         DesignableGuiControlTextArea debug = (DesignableGuiControlTextArea)newLayout.addControl("textarea", 0, 1);
/* 170 */         debug.setName("debug");
/* 171 */         debug.rowSpan = 4;
/*     */       } 
/*     */       
/* 174 */       layouts.put(name, newLayout);
/*     */     } 
/*     */     
/* 177 */     return layouts.get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final boolean deleteLayout(String layoutName) {
/* 182 */     if (layoutName != null && layouts.containsKey(layoutName) && !isBuiltinLayout(layoutName)) {
/*     */       
/* 184 */       layouts.remove(layoutName);
/*     */       
/* 186 */       for (Iterator<Map.Entry<String, DesignableGuiLayout>> iter = layoutBindings.entrySet().iterator(); iter.hasNext(); ) {
/*     */         
/* 188 */         Map.Entry<String, DesignableGuiLayout> entry = iter.next();
/* 189 */         if (((DesignableGuiLayout)entry.getValue()).name.equals(layoutName)) {
/* 190 */           entry.setValue(getDefaultLayout(entry.getKey(), false));
/*     */         }
/*     */       } 
/* 193 */       return true;
/*     */     } 
/*     */     
/* 196 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean checkControlExistsInLayouts(int controlId) {
/* 201 */     for (DesignableGuiLayout layout : layouts.values()) {
/*     */       
/* 203 */       if (layout.getControl(controlId) != null) return true;
/*     */     
/*     */     } 
/* 206 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void notifySettingsLoaded(ISettingsProvider settings) {
/* 216 */     File settingsFile = new File(MacroModCore.getMacrosDirectory(), ".gui.xml");
/*     */     
/* 218 */     Xml.clearNS();
/* 219 */     Xml.addNS("gc", "http://eq2online.net/macros/guiconfiguration");
/* 220 */     Xml.addNS("gb", "http://eq2online.net/macros/guibinding");
/*     */ 
/*     */     
/*     */     try {
/* 224 */       if (settingsFile.exists()) {
/*     */         
/* 226 */         Document xml = Xml.getDocument(settingsFile);
/* 227 */         if (xml != null) loadFromXml(xml);
/*     */       
/*     */       } else {
/*     */         
/* 231 */         InputStream is = DesignableGuiControl.class.getResourceAsStream("/.gui.xml");
/* 232 */         Document xml = Xml.getDocument(is);
/* 233 */         if (xml != null) loadFromXml(xml);
/*     */       
/*     */       } 
/* 236 */     } catch (Exception ex) {
/*     */       
/* 238 */       Log.printStackTrace(ex);
/*     */     } 
/*     */     
/* 241 */     Xml.clearNS();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void saveSettings() {
/*     */     try {
/* 248 */       saveToXml(new File(MacroModCore.getMacrosDirectory(), ".gui.xml"));
/*     */     } catch (Exception ex) {
/* 250 */       Log.printStackTrace(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void saveSettings(ISettingsProvider settings) {
/* 255 */     saveSettings();
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void loadFromXml(Document xml) {
/* 260 */     loadLayouts(xml);
/* 261 */     loadBindings(xml);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void loadLayouts(Document xml) {
/* 269 */     for (Node guiLayoutNode : Xml.queryAsArray(xml, "/gui/gc:guilayout")) {
/*     */       
/* 271 */       DesignableGuiLayout layout = null;
/*     */ 
/*     */       
/*     */       try {
/* 275 */         layout = new DesignableGuiLayout(guiLayoutNode);
/*     */       }
/* 277 */       catch (Exception exception) {}
/*     */       
/* 279 */       if (layout != null)
/*     */       {
/* 281 */         addLayout(layout);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void loadBindings(Document xml) throws DOMException {
/* 292 */     if (MacroModSettings.loadLayoutBindings)
/*     */     {
/* 294 */       for (Node guiBindingNode : Xml.queryAsArray(xml, "/gui/gb:bindings/gb:binding")) {
/*     */ 
/*     */         
/*     */         try {
/* 298 */           String slotName = Xml.getAttributeValue(guiBindingNode, "slot", "&");
/* 299 */           String layoutName = guiBindingNode.getTextContent();
/*     */           
/* 301 */           if (!slotName.equals("&") && layoutBindings.containsKey(slotName) && layouts.containsKey(layoutName))
/*     */           {
/* 303 */             setBinding(slotName, layoutName);
/*     */           }
/*     */         }
/* 306 */         catch (Exception exception) {}
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void saveToXml(File xmlFile) {
/* 313 */     Document xml = Xml.createDocument();
/*     */     
/* 315 */     xml.appendChild(xml.createComment("                                                                                    "));
/* 316 */     xml.appendChild(xml.createComment(" GUI layout configuration for mod_macros. Do not edit this file by hand, unless you "));
/* 317 */     xml.appendChild(xml.createComment(" want to, in which case go ahead. But don't blame me if it formats your hard drive. "));
/* 318 */     xml.appendChild(xml.createComment("                                                                                    "));
/*     */     
/* 320 */     Element rootNode = xml.createElement("gui");
/* 321 */     rootNode.setAttribute("xmlns:gc", "http://eq2online.net/macros/guiconfiguration");
/* 322 */     rootNode.setAttribute("xmlns:gb", "http://eq2online.net/macros/guibinding");
/* 323 */     xml.appendChild(rootNode);
/*     */     
/* 325 */     saveBindings(xml, rootNode);
/* 326 */     saveLayouts(xml, rootNode);
/*     */     
/* 328 */     Xml.saveDocument(xmlFile, xml);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void saveLayouts(Document xml, Element rootNode) throws DOMException {
/* 338 */     for (DesignableGuiLayout layout : layouts.values()) {
/*     */       
/* 340 */       Element layoutNode = xml.createElement("gc:guilayout");
/* 341 */       layout.save(xml, layoutNode);
/*     */       
/* 343 */       rootNode.appendChild(xml.createComment(" Config for layout '" + layout.name + "' "));
/* 344 */       rootNode.appendChild(layoutNode);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void saveBindings(Document xml, Element rootNode) {
/* 354 */     Element bindingsNode = xml.createElement("gb:bindings");
/*     */     
/* 356 */     for (Map.Entry<String, DesignableGuiLayout> binding : layoutBindings.entrySet()) {
/*     */       
/* 358 */       if (binding.getValue() != null) {
/*     */         
/* 360 */         Element bindingNode = xml.createElement("gb:binding");
/* 361 */         bindingNode.setAttribute("slot", binding.getKey());
/* 362 */         bindingNode.setTextContent(((DesignableGuiLayout)binding.getValue()).name);
/* 363 */         bindingsNode.appendChild(bindingNode);
/*     */       } 
/*     */     } 
/*     */     
/* 367 */     rootNode.appendChild(xml.createComment(" GUI slot bindings "));
/* 368 */     rootNode.appendChild(bindingsNode);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\designable\LayoutManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */