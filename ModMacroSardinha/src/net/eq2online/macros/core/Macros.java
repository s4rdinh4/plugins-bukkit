/*      */ package net.eq2online.macros.core;
/*      */ 
/*      */ import alq;
/*      */ import atr;
/*      */ import bsr;
/*      */ import bsu;
/*      */ import bty;
/*      */ import bxf;
/*      */ import com.google.common.io.Files;
/*      */ import com.mumfrey.liteloader.LiteMod;
/*      */ import com.mumfrey.liteloader.core.LiteLoader;
/*      */ import hg;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileReader;
/*      */ import java.io.FileWriter;
/*      */ import java.io.PrintWriter;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.ArrayList;
/*      */ import java.util.ConcurrentModificationException;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.TreeMap;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import net.eq2online.console.Log;
/*      */ import net.eq2online.macros.AutoDiscoveryAgent;
/*      */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*      */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*      */ import net.eq2online.macros.core.params.MacroParam;
/*      */ import net.eq2online.macros.event.MacroEventDispatcherBuiltin;
/*      */ import net.eq2online.macros.event.MacroEventManager;
/*      */ import net.eq2online.macros.event.MacroEventProviderBuiltin;
/*      */ import net.eq2online.macros.gui.designable.LayoutManager;
/*      */ import net.eq2online.macros.gui.layout.LayoutPanelEvents;
/*      */ import net.eq2online.macros.gui.layout.LayoutPanelKeys;
/*      */ import net.eq2online.macros.gui.screens.GuiMacroModOverlay;
/*      */ import net.eq2online.macros.gui.screens.GuiMacroParam;
/*      */ import net.eq2online.macros.input.InputHandler;
/*      */ import net.eq2online.macros.interfaces.ISettingsProvider;
/*      */ import net.eq2online.macros.scripting.ActionParser;
/*      */ import net.eq2online.macros.scripting.Documentor;
/*      */ import net.eq2online.macros.scripting.IActionFilter;
/*      */ import net.eq2online.macros.scripting.IDocumentor;
/*      */ import net.eq2online.macros.scripting.IErrorLogger;
/*      */ import net.eq2online.macros.scripting.ModuleLoader;
/*      */ import net.eq2online.macros.scripting.ScriptActionProvider;
/*      */ import net.eq2online.macros.scripting.api.IMacroEventManager;
/*      */ import net.eq2online.macros.scripting.api.IMacroEventProvider;
/*      */ import net.eq2online.macros.scripting.api.IMacrosAPIModule;
/*      */ import net.eq2online.macros.scripting.api.IScriptAction;
/*      */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*      */ import net.eq2online.macros.scripting.api.IScriptParser;
/*      */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*      */ import net.eq2online.macros.scripting.exceptions.ScriptException;
/*      */ import net.eq2online.macros.scripting.parser.ActionParserAction;
/*      */ import net.eq2online.macros.scripting.parser.ActionParserAssignment;
/*      */ import net.eq2online.macros.scripting.parser.ActionParserDirective;
/*      */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*      */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*      */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*      */ import net.eq2online.macros.scripting.parser.ScriptParser;
/*      */ import net.eq2online.macros.scripting.variable.ItemID;
/*      */ import net.eq2online.xml.Xml;
/*      */ import oa;
/*      */ import org.apache.commons.io.IOUtils;
/*      */ import org.lwjgl.input.Mouse;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Node;
/*      */ import v;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Macros
/*      */   implements IErrorLogger, ISettingsProvider
/*      */ {
/*   85 */   private MacroTemplate[] baseTemplates = new MacroTemplate[10000];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   90 */   private ArrayList<Macro> executingMacros = new ArrayList<Macro>();
/*      */   
/*   92 */   private LinkedList<Macro> pendingAdditions = new LinkedList<Macro>();
/*      */   
/*   94 */   private LinkedList<Macro> pendingRemovals = new LinkedList<Macro>();
/*      */   
/*   96 */   private Object executingMacrosLock = new Object();
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean insideRunLoop = false;
/*      */ 
/*      */   
/*  103 */   private String activeConfig = "";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  108 */   private String overlayConfig = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  113 */   public String singlePlayerConfigName = "";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  118 */   private HashMap<String, MacroTemplate[]> configs = (HashMap)new HashMap<String, MacroTemplate>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private File macrosFile;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private File oldLegacyMacrosFile;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private File newLegacyMacrosFile;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private File variablesFile;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MacroModCore mod;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SpamFilter spamFilter;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  153 */   private TreeMap<String, String> settingsComments = new TreeMap<String, String>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  158 */   private TreeMap<String, String> settings = new TreeMap<String, String>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected MacroEventManager eventManager;
/*      */ 
/*      */ 
/*      */   
/*      */   protected MacroEventProviderBuiltin builtinEventProvider;
/*      */ 
/*      */ 
/*      */   
/*      */   protected MacroEventDispatcherBuiltin builtinEventDispatcher;
/*      */ 
/*      */ 
/*      */   
/*  175 */   private static Pattern beginConfigPattern = Pattern.compile("^DIRECTIVE BEGINCONFIG\\(\\) (.+)$");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  180 */   private static Pattern macroPattern = Pattern.compile("^Macro\\[([0-9]{1,4})\\]\\.([a-z0-9_\\-\\[\\]]+)=(.+)$", 2);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  185 */   private static Pattern settingPattern = Pattern.compile("^([a-zA-Z\\.]+)=(.*)$", 2);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  190 */   private static Pattern oldMacroPattern = Pattern.compile("^(|@|#|&)([0-9]{1,3}):(.+)$");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean dirty = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  200 */   private int dirtyCounter = 0;
/*      */   
/*  202 */   private Set<Integer> thirdPartyReservedKeys = new HashSet<Integer>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Macros(bsu minecraft, MacroModCore mod) {
/*  211 */     this.mod = mod;
/*  212 */     this.mod.macros = this;
/*      */     
/*  214 */     this.macrosFile = new File(MacroModCore.getMacrosDirectory(), ".macros.txt");
/*  215 */     this.variablesFile = new File(MacroModCore.getMacrosDirectory(), ".vars.xml");
/*  216 */     this.newLegacyMacrosFile = new File(LiteLoader.getGameDirectory(), "/mods/macros/.macros.txt");
/*  217 */     this.oldLegacyMacrosFile = new File(LiteLoader.getGameDirectory(), "macros.txt");
/*      */     
/*  219 */     this.eventManager = new MacroEventManager(this, minecraft);
/*  220 */     this.builtinEventProvider = new MacroEventProviderBuiltin(this, minecraft);
/*  221 */     this.builtinEventDispatcher = (MacroEventDispatcherBuiltin)this.builtinEventProvider.getDispatcher();
/*      */     
/*  223 */     this.eventManager.registerEventProvider((IMacroEventProvider)this.builtinEventProvider);
/*      */ 
/*      */     
/*  226 */     LayoutManager.init();
/*      */     
/*  228 */     ScriptParser scriptParser = new ScriptParser(ScriptContext.MAIN);
/*  229 */     scriptParser.addActionParser((ActionParser)new ActionParserAction(ScriptContext.MAIN));
/*  230 */     scriptParser.addActionParser((ActionParser)new ActionParserDirective(ScriptContext.MAIN));
/*  231 */     scriptParser.addActionParser((ActionParser)new ActionParserAssignment(ScriptContext.MAIN));
/*      */ 
/*      */     
/*  234 */     IDocumentor documentorInstance = Documentor.getInstance().loadXml("en_GB");
/*  235 */     ScriptActionProvider scriptActionProviderInstance = new ScriptActionProvider(minecraft, this);
/*      */     
/*  237 */     ScriptContext.MAIN.create((IScriptActionProvider)scriptActionProviderInstance, (IMacroEventManager)this.eventManager, (IScriptParser)scriptParser, this, documentorInstance, new IActionFilter()
/*      */         {
/*      */           
/*      */           public boolean pass(ScriptContext context, ScriptCore scriptCore, IScriptAction action)
/*      */           {
/*  242 */             return true;
/*      */           }
/*      */         },  MacroActionContext.class);
/*      */ 
/*      */     
/*  247 */     Class<?> chatFilterManager = null;
/*      */ 
/*      */     
/*      */     try {
/*  251 */       chatFilterManager = Class.forName("net.eq2online.macros.modules.chatfilter.ChatFilterManager");
/*      */     }
/*  253 */     catch (Exception ex) {
/*      */       
/*  255 */       ex.printStackTrace();
/*      */     } 
/*      */     
/*  258 */     if (chatFilterManager != null) {
/*      */       
/*  260 */       ScriptParser scriptParser1 = new ScriptParser(ScriptContext.CHATFILTER);
/*  261 */       scriptParser1.addActionParser((ActionParser)new ActionParserAction(ScriptContext.CHATFILTER));
/*  262 */       scriptParser1.addActionParser((ActionParser)new ActionParserDirective(ScriptContext.CHATFILTER));
/*  263 */       scriptParser1.addActionParser((ActionParser)new ActionParserAssignment(ScriptContext.CHATFILTER));
/*      */       
/*  265 */       ScriptContext.CHATFILTER.create((IScriptActionProvider)scriptActionProviderInstance, (IMacroEventManager)this.eventManager, (IScriptParser)scriptParser1, this, documentorInstance, new IActionFilter()
/*      */           {
/*      */             
/*      */             public boolean pass(ScriptContext context, ScriptCore scriptCore, IScriptAction action)
/*      */             {
/*  270 */               return action.isThreadSafe();
/*      */             }
/*      */           },  MacroActionContext.class);
/*      */     } 
/*      */ 
/*      */     
/*  276 */     ModuleLoader moduleLoader = new ModuleLoader(MacroModCore.getMacrosDirectory());
/*  277 */     moduleLoader.loadModules(this);
/*      */ 
/*      */     
/*  280 */     load();
/*      */     
/*  282 */     scriptActionProviderInstance.init();
/*  283 */     ScriptContext.MAIN.initActions();
/*      */     
/*  285 */     if (chatFilterManager != null)
/*      */     {
/*  287 */       ScriptContext.CHATFILTER.initActions();
/*      */     }
/*      */     
/*  290 */     if (chatFilterManager != null) {
/*      */       
/*      */       try {
/*      */         
/*  294 */         Method getInstance = chatFilterManager.getDeclaredMethod("getInstance", new Class[0]);
/*  295 */         getInstance.invoke(null, new Object[0]);
/*      */       }
/*  297 */       catch (Exception ex) {
/*      */         
/*  299 */         ex.printStackTrace();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void init() {
/*  306 */     detectThirdPartyModKeys();
/*      */   }
/*      */ 
/*      */   
/*      */   public void detectThirdPartyModKeys() {
/*  311 */     this.thirdPartyReservedKeys.clear();
/*      */ 
/*      */     
/*      */     try {
/*  315 */       detectReiMiniMapKeys();
/*      */     }
/*  317 */     catch (Throwable throwable) {}
/*      */ 
/*      */     
/*      */     try {
/*  321 */       detectVoxelFlightKeys();
/*      */     }
/*  323 */     catch (Throwable throwable) {}
/*      */ 
/*      */     
/*      */     try {
/*  327 */       detectVoxelPlayerKeys();
/*      */     }
/*  329 */     catch (Throwable throwable) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void detectReiMiniMapKeys() throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
/*  343 */     Class<? extends Enum<?>> reiKeyInput = (Class)Class.forName("reifnsk.minimap.KeyInput");
/*  344 */     if (reiKeyInput != null) {
/*      */       
/*  346 */       Method mGetKey = reiKeyInput.getDeclaredMethod("getKey", new Class[0]);
/*  347 */       Method mValues = reiKeyInput.getDeclaredMethod("values", new Class[0]);
/*  348 */       Enum[] arrayOfEnum = (Enum[])mValues.invoke(null, new Object[0]);
/*      */       
/*  350 */       for (Enum<?> enumValue : arrayOfEnum) {
/*      */         
/*  352 */         int keyIndex = ((Integer)mGetKey.invoke(enumValue, new Object[0])).intValue();
/*  353 */         if (keyIndex > 0) this.thirdPartyReservedKeys.add(Integer.valueOf(keyIndex));
/*      */       
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void detectVoxelFlightKeys() throws ClassNotFoundException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
/*  361 */     Class<? extends LiteMod> voxelFlight = (Class)Class.forName("com.thevoxelbox.voxelflight.LiteModVoxelFlight");
/*  362 */     if (voxelFlight != null) {
/*      */       
/*  364 */       Field config = voxelFlight.getDeclaredField("config");
/*  365 */       config.setAccessible(true);
/*  366 */       Object configuration = config.get(LiteLoader.getInstance().getMod(voxelFlight));
/*      */       
/*  368 */       if (configuration != null) {
/*      */         
/*  370 */         Method getIntProperty = configuration.getClass().getSuperclass().getDeclaredMethod("getIntProperty", new Class[] { String.class });
/*      */         
/*  372 */         int keyFly = ((Integer)getIntProperty.invoke(configuration, new Object[] { "flyKey" })).intValue();
/*  373 */         int keyNoClip = ((Integer)getIntProperty.invoke(configuration, new Object[] { "noclipKey" })).intValue();
/*  374 */         int keyUp = ((Integer)getIntProperty.invoke(configuration, new Object[] { "upKey" })).intValue();
/*  375 */         int keyDown = ((Integer)getIntProperty.invoke(configuration, new Object[] { "downKey" })).intValue();
/*  376 */         int keySpeed = ((Integer)getIntProperty.invoke(configuration, new Object[] { "speedKey" })).intValue();
/*  377 */         int keyCine = ((Integer)getIntProperty.invoke(configuration, new Object[] { "cineKey" })).intValue();
/*      */         
/*  379 */         if (keyFly > 0) this.thirdPartyReservedKeys.add(Integer.valueOf(keyFly)); 
/*  380 */         if (keyNoClip > 0) this.thirdPartyReservedKeys.add(Integer.valueOf(keyNoClip)); 
/*  381 */         if (keyUp > 0) this.thirdPartyReservedKeys.add(Integer.valueOf(keyUp)); 
/*  382 */         if (keyDown > 0) this.thirdPartyReservedKeys.add(Integer.valueOf(keyDown)); 
/*  383 */         if (keySpeed > 0) this.thirdPartyReservedKeys.add(Integer.valueOf(keySpeed)); 
/*  384 */         if (keyCine > 0) this.thirdPartyReservedKeys.add(Integer.valueOf(keyCine));
/*      */         
/*  386 */         this.thirdPartyReservedKeys.add(Integer.valueOf(65));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void detectVoxelPlayerKeys() throws ClassNotFoundException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
/*  394 */     Class<? extends LiteMod> voxelPlayer = (Class)Class.forName("com.thevoxelbox.voxelplayer.LiteModVoxelPlayer");
/*  395 */     if (voxelPlayer != null) {
/*      */       
/*  397 */       Field guiKeybinding = voxelPlayer.getDeclaredField("guiKeybinding");
/*  398 */       bsr guiKey = (bsr)guiKeybinding.get((Object)null);
/*  399 */       if (guiKey.i() > 0) this.thirdPartyReservedKeys.add(Integer.valueOf(guiKey.i()));
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void logError(String errorMessage) {
/*  406 */     MacroModCore.logStartupError(errorMessage);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getConfigNames() {
/*  416 */     return (String[])this.configs.keySet().toArray((Object[])new String[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getConfigDisplayName(String configName) {
/*  428 */     return !configName.equals("") ? configName : LocalisationProvider.getLocalisedString("options.defaultconfig");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getActiveConfigName() {
/*  438 */     return getConfigDisplayName(this.activeConfig);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getOverlayConfigName(String noneColourCode) {
/*  449 */     return (this.overlayConfig != null) ? getConfigDisplayName(this.overlayConfig) : (noneColourCode + "None");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getActiveConfig() {
/*  458 */     return this.activeConfig;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getOverlayConfig() {
/*  467 */     return this.overlayConfig;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasConfig(String configName) {
/*  478 */     return (configName.equals("") || this.configs.containsKey(configName));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setActiveConfig(String configName) {
/*  489 */     this.activeConfig = configName;
/*  490 */     this.overlayConfig = null;
/*      */     
/*  492 */     if (this.mod != null) {
/*  493 */       this.mod.notifyChangeConfiguration();
/*      */     }
/*  495 */     if (bsu.z().E())
/*      */     {
/*  497 */       this.singlePlayerConfigName = configName;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void setOverlayConfig(String configName) {
/*  503 */     this.overlayConfig = configName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMod(MacroModCore parent) {
/*  513 */     this.mod = parent;
/*      */   }
/*      */ 
/*      */   
/*      */   public MacroEventManager getEventManager() {
/*  518 */     return this.eventManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public MacroEventDispatcherBuiltin getBuiltinEventDispatcher() {
/*  523 */     return this.builtinEventDispatcher;
/*      */   }
/*      */ 
/*      */   
/*      */   public SpamFilter getSpamFilter() {
/*  528 */     return this.spamFilter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LayoutPanelKeys getKeyboardLayout() {
/*  537 */     return this.mod.keyboardLayout;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LayoutPanelEvents getEventLayout() {
/*  546 */     return this.mod.eventLayout;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AutoDiscoveryAgent getAutoDiscoveryAgent() {
/*  556 */     return (this.mod != null) ? this.mod.getAutoDiscoveryAgent() : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSetting(String settingName, String settingValue) {
/*  565 */     if (this.settings.containsKey(settingName)) {
/*  566 */       this.settings.remove(settingName);
/*      */     }
/*  568 */     if (settingValue != null) {
/*  569 */       this.settings.put(settingName, settingValue);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSettingComment(String settingName, String settingComment) {
/*  578 */     if (this.settingsComments.containsKey(settingName)) {
/*  579 */       this.settingsComments.remove(settingName);
/*      */     }
/*  581 */     if (settingComment != null) {
/*  582 */       this.settingsComments.put(settingName, settingComment);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSetting(String settingName, boolean settingValue) {
/*  591 */     setSetting(settingName, settingValue ? "1" : "0");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends Enum> void setSetting(String settingName, T settingValue) {
/*  601 */     if (settingValue == null) {
/*      */       
/*  603 */       setSetting(settingName, (String)null);
/*      */     }
/*      */     else {
/*      */       
/*  607 */       setSetting(settingName, settingValue.toString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSetting(String settingName, int settingValue) {
/*  617 */     setSetting(settingName, "" + settingValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSetting(String settingName, String defaultValue) {
/*  626 */     return this.settings.containsKey(settingName) ? this.settings.get(settingName) : defaultValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSetting(String settingName, int defaultValue) {
/*  635 */     String sValue = getSetting(settingName, "" + defaultValue);
/*      */ 
/*      */     
/*      */     try {
/*  639 */       int result = Integer.parseInt(sValue);
/*  640 */       return result;
/*      */     }
/*  642 */     catch (NumberFormatException ex) {
/*      */       
/*  644 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSetting(String settingName, int defaultValue, int minValue, int maxValue) {
/*  651 */     return Math.min(Math.max(getSetting(settingName, defaultValue), minValue), maxValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getSetting(String settingName, boolean defaultValue) {
/*  660 */     String sValue = getSetting(settingName, "*").toLowerCase();
/*  661 */     if (sValue.equals("*")) return defaultValue;
/*      */     
/*  663 */     return (sValue.equals("1") || sValue.equals("true") || sValue.equals("yes"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends Enum> T getSetting(String settingName, T defaultValue) {
/*  673 */     Class<? extends T> enumClass = defaultValue.getDeclaringClass();
/*      */     
/*  675 */     String sValue = getSetting(settingName, "*");
/*  676 */     if (sValue.equals("*")) return defaultValue;
/*      */ 
/*      */     
/*      */     try {
/*  680 */       return Enum.valueOf(enumClass, sValue);
/*      */     }
/*  682 */     catch (IllegalArgumentException ex) {
/*      */       
/*  684 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MacroTemplate[] getConfig(String configName) {
/*  696 */     if (configName.equals("")) return this.baseTemplates;
/*      */     
/*  698 */     if (!this.configs.containsKey(configName)) {
/*  699 */       this.configs.put(configName, new MacroTemplate[10000]);
/*      */     }
/*  701 */     return this.configs.get(configName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addConfig(String configName, boolean copy) {
/*  711 */     if (configName.equals(this.activeConfig) || this.configs.containsKey(configName))
/*      */       return; 
/*  713 */     if (!this.configs.containsKey(configName))
/*      */     {
/*  715 */       this.configs.put(configName, new MacroTemplate[10000]);
/*      */     }
/*      */ 
/*      */     
/*  719 */     if (copy) {
/*      */       
/*  721 */       MacroTemplate[] currentConfig = getConfig(this.activeConfig);
/*  722 */       MacroTemplate[] newConfig = getConfig(configName);
/*      */       
/*  724 */       for (int i = 0; i < 10000; i++) {
/*      */         
/*  726 */         if (currentConfig[i] != null)
/*      */         {
/*  728 */           newConfig[i] = new MacroTemplate(i, currentConfig[i]);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  733 */     this.mod.notifyAddConfiguration(configName, copy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteConfig(String configName) {
/*  743 */     if (configName.equals("") || !this.configs.containsKey(configName))
/*      */       return; 
/*  745 */     this.configs.remove(configName);
/*  746 */     this.mod.notifyRemoveConfiguration(configName);
/*  747 */     save();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MacroTemplate getMacroTemplate(int key, boolean useOverlay) {
/*  759 */     return useOverlay ? getMacroTemplateWithOverlay(key) : getMacroTemplate(key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MacroTemplate getMacroTemplate(int key) {
/*  770 */     return getMacroTemplate(this.activeConfig, key, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public MacroTemplate getMacroTemplateWithOverlay(int key) {
/*  775 */     if (this.overlayConfig != null && !this.overlayConfig.equals(this.activeConfig) && hasConfig(this.overlayConfig)) {
/*      */       
/*  777 */       MacroTemplate tpl = getMacroTemplate(this.overlayConfig, key, false);
/*  778 */       if (tpl != null && !tpl.isEmpty()) return tpl;
/*      */     
/*      */     } 
/*  781 */     return getMacroTemplate(this.activeConfig, key, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MacroTemplate getMacroTemplate(String configName, int key, boolean createIfNotFound) {
/*  793 */     if (key > -1 && key < 10000) {
/*      */       
/*  795 */       if (this.baseTemplates[key] != null && (this.baseTemplates[key]).global) {
/*  796 */         return this.baseTemplates[key];
/*      */       }
/*  798 */       MacroTemplate[] templates = getConfig(configName);
/*      */       
/*  800 */       if (templates[key] == null && createIfNotFound) {
/*  801 */         templates[key] = new MacroTemplate(this, key);
/*      */       }
/*  803 */       return templates[key];
/*      */     } 
/*      */     
/*  806 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMacroTemplate(String configName, int key, MacroTemplate template) {
/*  818 */     if (key > -1 && key < 10000) {
/*      */       
/*  820 */       MacroTemplate[] templates = getConfig(configName);
/*  821 */       templates[key] = template;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteMacroTemplate(int key) {
/*  832 */     deleteMacroTemplate(this.activeConfig, key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteMacroTemplate(String configName, int key) {
/*  843 */     if (key > -1 && key < 10000) {
/*      */       
/*  845 */       MacroTemplate delTemplate = getMacroTemplate(configName, key, true);
/*      */       
/*  847 */       if (delTemplate.global) {
/*      */         
/*  849 */         this.baseTemplates[key] = null;
/*      */       }
/*      */       else {
/*      */         
/*  853 */         MacroTemplate[] templates = getConfig(configName);
/*  854 */         templates[key] = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteMacroTemplateFromAllConfigurations(int key) {
/*  866 */     this.baseTemplates[key] = null;
/*      */     
/*  868 */     for (MacroTemplate[] templates : this.configs.values()) {
/*  869 */       templates[key] = null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyMacroTemplate(int source, int dest) {
/*  880 */     copyMacroTemplate(this.activeConfig, source, dest);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyMacroTemplate(String configName, int source, int dest) {
/*  892 */     MacroTemplate sourceTemplate = getMacroTemplate(configName, source, true);
/*  893 */     MacroTemplate destTemplate = getMacroTemplate(configName, dest, false);
/*      */     
/*  895 */     if (sourceTemplate != null && dest > -1 && dest < 10000) {
/*      */       
/*  897 */       if (sourceTemplate.global || (destTemplate != null && destTemplate.global))
/*      */       {
/*  899 */         deleteMacroTemplateFromAllConfigurations(dest);
/*      */       }
/*      */       
/*  902 */       if (sourceTemplate.global) {
/*      */         
/*  904 */         this.baseTemplates[dest] = new MacroTemplate(dest, sourceTemplate);
/*      */       }
/*      */       else {
/*      */         
/*  908 */         MacroTemplate[] templates = getConfig(configName);
/*  909 */         templates[dest] = new MacroTemplate(dest, sourceTemplate);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveMacroTemplate(int source, int dest) {
/*  922 */     moveMacroTemplate(this.activeConfig, source, dest);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveMacroTemplate(String configName, int source, int dest) {
/*  934 */     MacroTemplate sourceTemplate = getMacroTemplate(configName, source, true);
/*      */     
/*  936 */     if (sourceTemplate != null && dest > -1 && dest < 10000)
/*      */     {
/*  938 */       if (sourceTemplate.global) {
/*      */         
/*  940 */         deleteMacroTemplateFromAllConfigurations(dest);
/*  941 */         this.baseTemplates[dest] = sourceTemplate;
/*  942 */         this.baseTemplates[source] = null;
/*  943 */         sourceTemplate.setID(dest);
/*      */       }
/*      */       else {
/*      */         
/*  947 */         MacroTemplate[] templates = getConfig(configName);
/*  948 */         templates[dest] = templates[source];
/*  949 */         templates[dest].setID(dest);
/*  950 */         templates[source] = null;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateMacroTemplate(int key, MacroTemplate template, MacroPlaybackType playbackType, String repeatRate, String keyDownMacro, String keyHeldMacro, String keyUpMacro, String condition, boolean requireControl, boolean requireAlt, boolean requireShift, boolean inhibitParamLoad, boolean global, boolean alwaysOverride) {
/*  968 */     template.setPlaybackType(playbackType);
/*  969 */     template.setKeyDownMacro(keyDownMacro);
/*  970 */     template.setKeyHeldMacro(keyHeldMacro);
/*  971 */     template.setKeyUpMacro(keyUpMacro);
/*  972 */     template.setMacroCondition(condition);
/*      */     
/*  974 */     template.requireControl = requireControl;
/*  975 */     template.requireAlt = requireAlt;
/*  976 */     template.requireShift = requireShift;
/*  977 */     template.inhibitParamLoad = inhibitParamLoad;
/*  978 */     template.repeatRate = MacroTemplate.tryParse(repeatRate, template.repeatRate);
/*  979 */     template.alwaysOverride = alwaysOverride;
/*      */ 
/*      */     
/*  982 */     if (template.global != global)
/*      */     {
/*  984 */       if (template.global) {
/*      */         
/*  986 */         deleteMacroTemplate("", key);
/*  987 */         setMacroTemplate(this.activeConfig, key, template);
/*      */       }
/*      */       else {
/*      */         
/*  991 */         deleteMacroTemplateFromAllConfigurations(key);
/*  992 */         setMacroTemplate("", key, template);
/*      */       } 
/*      */     }
/*      */     
/*  996 */     template.global = global;
/*      */     
/*  998 */     save();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MacroTemplate createLooseTemplate(String macro, String macroName) {
/* 1009 */     int macroId = MacroType.None.getNextFreeIndex(macroName);
/*      */     
/* 1011 */     if (macroId < 10000) {
/*      */       
/* 1013 */       MacroTemplate template = new MacroTemplate(this, macroId);
/*      */       
/* 1015 */       template.setKeyDownMacro(macro);
/* 1016 */       setMacroTemplate("", macroId, template);
/*      */       
/* 1018 */       return template;
/*      */     } 
/*      */     
/* 1021 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void load() {
/* 1030 */     BufferedReader bufferedreader = null;
/*      */ 
/*      */     
/*      */     try {
/* 1034 */       Log.info("Loading macro templates...");
/*      */ 
/*      */       
/* 1037 */       this.baseTemplates = new MacroTemplate[10000];
/* 1038 */       this.configs.clear();
/* 1039 */       this.settings.clear();
/* 1040 */       MacroModCore.notifyClearSettings();
/* 1041 */       this.singlePlayerConfigName = "";
/*      */       
/* 1043 */       if (!this.macrosFile.exists())
/*      */       {
/* 1045 */         if (this.oldLegacyMacrosFile.exists()) {
/*      */           
/* 1047 */           this.macrosFile = this.oldLegacyMacrosFile;
/*      */ 
/*      */         
/*      */         }
/* 1051 */         else if (this.newLegacyMacrosFile.exists()) {
/*      */           
/* 1053 */           this.macrosFile = this.newLegacyMacrosFile;
/*      */         }
/*      */         else {
/*      */           
/* 1057 */           MacroModCore.firstRun = true;
/*      */           
/*      */           return;
/*      */         } 
/*      */       }
/*      */       
/* 1063 */       String currentConfigName = "";
/*      */       
/* 1065 */       bufferedreader = new BufferedReader(new FileReader(this.macrosFile));
/*      */       
/* 1067 */       for (String configLine = ""; (configLine = bufferedreader.readLine()) != null;) {
/*      */ 
/*      */         
/*      */         try {
/* 1071 */           Matcher macroPatternMatcher = macroPattern.matcher(configLine);
/* 1072 */           Matcher settingPatternMatcher = settingPattern.matcher(configLine);
/* 1073 */           Matcher beginConfigPatternMatcher = beginConfigPattern.matcher(configLine);
/*      */           
/* 1075 */           if (macroPatternMatcher.matches()) {
/*      */             
/* 1077 */             int key = Integer.parseInt(macroPatternMatcher.group(1));
/*      */             
/* 1079 */             if (key > -1 && key < 10000) {
/*      */               
/* 1081 */               MacroTemplate tpl = getMacroTemplate(currentConfigName, key, true);
/* 1082 */               tpl.loadFrom(configLine, macroPatternMatcher.group(2), macroPatternMatcher.group(3));
/*      */ 
/*      */               
/* 1085 */               if (tpl.global && currentConfigName.length() > 0)
/*      */               {
/* 1087 */                 deleteMacroTemplate(currentConfigName, key); } 
/*      */             } 
/*      */             continue;
/*      */           } 
/* 1091 */           if (settingPatternMatcher.matches()) {
/*      */             
/* 1093 */             if (currentConfigName.equals("")) {
/*      */               
/* 1095 */               if (this.settings.containsKey(settingPatternMatcher.group(1).toLowerCase())) {
/*      */                 
/* 1097 */                 Log.info("WARNING! Duplicate directive '{0}' found in file", new Object[] { settingPatternMatcher.group(1) });
/* 1098 */                 this.settings.remove(settingPatternMatcher.group(1));
/*      */               } 
/*      */               
/* 1101 */               this.settings.put(settingPatternMatcher.group(1).toLowerCase(), settingPatternMatcher.group(2));
/*      */             }  continue;
/*      */           } 
/* 1104 */           if (beginConfigPatternMatcher.matches()) {
/*      */             
/* 1106 */             currentConfigName = beginConfigPatternMatcher.group(1);
/* 1107 */             Log.info("Loading additional config: {0}", new Object[] { currentConfigName });
/*      */             
/*      */             continue;
/*      */           } 
/* 1111 */           Matcher oldMacroPatternMatcher = oldMacroPattern.matcher(configLine);
/*      */           
/* 1113 */           if (oldMacroPatternMatcher.matches()) {
/*      */             
/* 1115 */             int key = Integer.parseInt(oldMacroPatternMatcher.group(2));
/*      */             
/* 1117 */             if (key > -1 && key < 10000)
/*      */             {
/* 1119 */               MacroTemplate tpl = getMacroTemplate(currentConfigName, key, true);
/*      */               
/* 1121 */               if (oldMacroPatternMatcher.group(1).equals("")) { tpl.setKeyDownMacro(oldMacroPatternMatcher.group(3)); continue; }
/* 1122 */                if (oldMacroPatternMatcher.group(1).equals("@")) { tpl.setStoredParam(MacroParam.Type.Normal, 0, oldMacroPatternMatcher.group(3)); continue; }
/* 1123 */                if (oldMacroPatternMatcher.group(1).equals("#")) { tpl.setStoredParam(MacroParam.Type.Item, 0, oldMacroPatternMatcher.group(3)); continue; }
/* 1124 */                if (oldMacroPatternMatcher.group(1).equals("&")) tpl.setStoredParam(MacroParam.Type.Friend, 0, oldMacroPatternMatcher.group(3));
/*      */             
/*      */             }
/*      */           
/*      */           } 
/* 1129 */         } catch (Exception ex) {
/*      */           
/* 1131 */           Log.info("Skipping bad macro/setting: {0}", new Object[] { configLine });
/*      */         } 
/*      */       } 
/*      */       
/* 1135 */       bufferedreader.close();
/* 1136 */       bufferedreader = null;
/*      */       
/* 1138 */       if (getSetting("version", 0) < MacroModCore.VERSION) {
/*      */         
/* 1140 */         handleVersionUpgrade(getSetting("version", 0));
/*      */         
/* 1142 */         File backupFile = new File(this.macrosFile.getParentFile(), ".macros.backup." + getSetting("version", 0) + ".txt");
/* 1143 */         Log.info("Creating backup of existing config at {0}", new Object[] { backupFile.getAbsoluteFile() });
/* 1144 */         Files.copy(this.macrosFile, backupFile);
/*      */       } 
/*      */       
/* 1147 */       loadVariables();
/*      */       
/* 1149 */       MacroModCore.notifySettingsLoaded(this);
/*      */     }
/* 1151 */     catch (Exception ex) {
/*      */       
/* 1153 */       Log.info("Failed to load macro templates:");
/* 1154 */       Log.printStackTrace(ex);
/*      */     }
/*      */     finally {
/*      */       
/* 1158 */       if (bufferedreader != null)
/*      */       {
/* 1160 */         IOUtils.closeQuietly(bufferedreader);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void loadVariables() {
/* 1167 */     Xml.clearNS();
/*      */ 
/*      */     
/*      */     try {
/* 1171 */       if (this.variablesFile.exists()) {
/*      */         
/* 1173 */         Document xml = Xml.getDocument(this.variablesFile);
/* 1174 */         if (xml != null) loadVariablesFromXml(xml);
/*      */       
/*      */       } 
/* 1177 */     } catch (Exception ex) {
/*      */       
/* 1179 */       Log.printStackTrace(ex);
/*      */     } 
/*      */     
/* 1182 */     Xml.clearNS();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadVariablesFromXml(Document xml) {
/* 1188 */     for (Node configNode : Xml.queryAsArray(xml.getFirstChild(), "config")) {
/*      */       
/* 1190 */       String configName = Xml.getAttributeValue(configNode, "name", "");
/* 1191 */       if (configName.length() < 1)
/* 1192 */         continue;  if (configName.equalsIgnoreCase("@default")) configName = "";
/*      */       
/* 1194 */       for (Node templateNode : Xml.queryAsArray(configNode, "template")) {
/*      */         
/* 1196 */         int templateId = Xml.getAttributeValue(templateNode, "id", 0);
/* 1197 */         if (templateId < 1)
/*      */           continue; 
/* 1199 */         MacroTemplate tpl = getMacroTemplate(configName, templateId, false);
/* 1200 */         if (tpl != null)
/*      */         {
/* 1202 */           tpl.loadVariables(templateNode);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void save() {
/*      */     try {
/* 1216 */       this.settings.clear();
/* 1217 */       MacroModCore.saveSettings(this);
/*      */ 
/*      */       
/*      */       try {
/* 1221 */         this.macrosFile.getParentFile().mkdirs();
/*      */       }
/* 1223 */       catch (Exception exception) {}
/*      */       
/* 1225 */       PrintWriter printwriter = new PrintWriter(new FileWriter(this.macrosFile));
/*      */       
/* 1227 */       printwriter.println("#");
/* 1228 */       printwriter.println("# macros.txt");
/* 1229 */       printwriter.println("# This file stores the macro definitions, parameters and options for mod_macros");
/* 1230 */       printwriter.println("#\n");
/*      */       
/* 1232 */       printwriter.println("# Version number of the mod which saved this file. ***Do not alter this value!***");
/* 1233 */       printwriter.println("version=" + MacroModCore.VERSION + "\n");
/*      */       
/* 1235 */       printwriter.println("#\n# Settings\n#\n");
/*      */       
/* 1237 */       String nextBreak = "";
/*      */       
/* 1239 */       for (Map.Entry<String, String> setting : this.settings.entrySet()) {
/*      */         
/* 1241 */         printwriter.print(nextBreak);
/*      */         
/* 1243 */         if (this.settingsComments.containsKey(setting.getKey())) {
/*      */           
/* 1245 */           if (nextBreak.length() == 0) printwriter.println(); 
/* 1246 */           printwriter.println("# " + (String)this.settingsComments.get(setting.getKey()));
/* 1247 */           nextBreak = "\n";
/*      */         }
/*      */         else {
/*      */           
/* 1251 */           nextBreak = "";
/*      */         } 
/*      */         
/* 1254 */         printwriter.println((String)setting.getKey() + "=" + (String)setting.getValue());
/*      */       } 
/*      */       
/* 1257 */       printwriter.println("\n#\n# Macros\n#\n");
/*      */       
/* 1259 */       for (int index = 0; index < 10000; index++) {
/*      */         
/* 1261 */         if (this.baseTemplates[index] != null && MacroType.getMacroShouldBeSaved(index)) {
/*      */           
/*      */           try {
/*      */             
/* 1265 */             this.baseTemplates[index].saveTemplate(printwriter);
/*      */           }
/* 1267 */           catch (Exception exception) {}
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1272 */       if (this.configs.size() > 0)
/*      */       {
/* 1274 */         for (Map.Entry<String, MacroTemplate[]> config : this.configs.entrySet()) {
/*      */           
/* 1276 */           printwriter.println("\nDIRECTIVE BEGINCONFIG() " + (String)config.getKey() + "\n");
/*      */           
/* 1278 */           MacroTemplate[] configTemplates = config.getValue();
/*      */           
/* 1280 */           for (int i = 0; i < 10000; i++) {
/*      */             
/* 1282 */             if (configTemplates[i] != null && !(configTemplates[i]).global && MacroType.getMacroShouldBeSaved(i)) {
/*      */               
/*      */               try {
/*      */                 
/* 1286 */                 configTemplates[i].saveTemplate(printwriter);
/*      */               }
/* 1288 */               catch (Exception exception) {}
/*      */             }
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 1294 */       printwriter.close();
/*      */     }
/* 1296 */     catch (Exception ex) {
/*      */       
/* 1298 */       Log.info("Failed to save .macros.txt");
/* 1299 */       Log.printStackTrace(ex);
/*      */     } 
/*      */     
/* 1302 */     saveVariables();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveVariables() {
/*      */     try {
/* 1309 */       Document xml = Xml.createDocument();
/*      */       
/* 1311 */       Element root = xml.createElement("variables");
/* 1312 */       xml.appendChild(root);
/*      */       
/* 1314 */       Element baseConfigNode = xml.createElement("config");
/* 1315 */       baseConfigNode.setAttribute("name", "@default");
/*      */       
/* 1317 */       for (int index = 0; index < 10000; index++) {
/*      */         
/* 1319 */         if (this.baseTemplates[index] != null && MacroType.getMacroShouldBeSaved(index)) {
/*      */           
/* 1321 */           Element templateNode = xml.createElement("template");
/* 1322 */           templateNode.setAttribute("id", String.valueOf(index));
/*      */ 
/*      */           
/*      */           try {
/* 1326 */             this.baseTemplates[index].saveVariables(xml, templateNode);
/*      */           }
/* 1328 */           catch (Exception exception) {}
/*      */           
/* 1330 */           if (templateNode.hasChildNodes()) {
/*      */             
/* 1332 */             baseConfigNode.appendChild(xml.createComment(" " + MacroType.getMacroNameWithPrefix(index) + " "));
/* 1333 */             baseConfigNode.appendChild(templateNode);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1338 */       root.appendChild(baseConfigNode);
/*      */ 
/*      */       
/* 1341 */       if (this.configs.size() > 0)
/*      */       {
/* 1343 */         for (Map.Entry<String, MacroTemplate[]> config : this.configs.entrySet()) {
/*      */           
/* 1345 */           Element configNode = xml.createElement("config");
/* 1346 */           configNode.setAttribute("name", config.getKey());
/*      */           
/* 1348 */           MacroTemplate[] configTemplates = config.getValue();
/*      */           
/* 1350 */           for (int i = 0; i < 10000; i++) {
/*      */             
/* 1352 */             if (configTemplates[i] != null && !(configTemplates[i]).global && MacroType.getMacroShouldBeSaved(i)) {
/*      */               
/* 1354 */               Element templateNode = xml.createElement("template");
/* 1355 */               templateNode.setAttribute("id", String.valueOf(i));
/*      */ 
/*      */               
/*      */               try {
/* 1359 */                 configTemplates[i].saveVariables(xml, templateNode);
/*      */               }
/* 1361 */               catch (Exception exception) {}
/*      */               
/* 1363 */               if (templateNode.hasChildNodes()) {
/*      */                 
/* 1365 */                 configNode.appendChild(xml.createComment(" " + MacroType.getMacroNameWithPrefix(i) + " "));
/* 1366 */                 configNode.appendChild(templateNode);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           
/* 1371 */           if (configNode.hasChildNodes()) {
/*      */             
/* 1373 */             root.appendChild(xml.createComment(" ================================================================================ "));
/* 1374 */             root.appendChild(configNode);
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 1379 */       Xml.saveDocument(this.variablesFile, xml);
/*      */     }
/* 1381 */     catch (Exception ex) {
/*      */       
/* 1383 */       Log.info("Failed to save .vars.xml");
/* 1384 */       Log.printStackTrace(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void handleVersionUpgrade(int oldVersion) {
/* 1396 */     Log.info("Handling version upgrade: old version is {0}, current version is {1}", new Object[] { Double.valueOf(oldVersion * 0.001D), Double.valueOf(MacroModCore.VERSION * 0.001D) });
/*      */ 
/*      */     
/* 1399 */     if (oldVersion < 630) {
/*      */       
/* 1401 */       File macrosDir = MacroModCore.getMacrosDirectory();
/*      */       
/* 1403 */       if (macrosDir.exists()) {
/*      */         
/* 1405 */         String[] moveFiles = { "friends", "homes", "places", "warps", "towns", "places", "presettext0", "presettext1", "presettext2", "presettext3", "presettext4", "presettext5", "presettext6", "presettext7", "presettext8", "presettext9" };
/*      */         
/* 1407 */         for (String moveFile : moveFiles) {
/*      */           
/* 1409 */           File sourceFile = new File(LiteLoader.getGameDirectory(), "." + moveFile + ".txt");
/* 1410 */           File destFile = new File(macrosDir, "." + moveFile + ".txt");
/*      */           
/* 1412 */           if (sourceFile.exists()) {
/*      */             
/* 1414 */             Log.info("Moving {0} to {1}", new Object[] { sourceFile.getName(), destFile.getAbsolutePath() });
/*      */ 
/*      */             
/*      */             try {
/* 1418 */               sourceFile.renameTo(destFile);
/*      */             }
/* 1420 */             catch (Exception ex) {
/*      */               
/* 1422 */               Log.printStackTrace(ex);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1430 */     if (oldVersion < 750) {
/*      */       
/* 1432 */       String keyboardLayout = getSetting("keyboard.layout", "");
/*      */       
/* 1434 */       if (keyboardLayout.length() > 0) {
/*      */         
/* 1436 */         keyboardLayout = keyboardLayout + "{250,26,108}{251,164,108}{252,168,152}{253,26,152}{254,26,136}";
/* 1437 */         setSetting("keyboard.layout", keyboardLayout);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1442 */     if (oldVersion < 870)
/*      */     {
/* 1444 */       setSetting("events.layout", "{1000,6,4}{1001,6,24}{1002,6,44}{1003,6,64}{1004,6,84}{1005,6,104}{1006,6,124}{1007,6,144}{1008,160,4}{1009,160,24}{1010,160,44}{1011,160,64}{1012,160,84}{1013,160,104}{1014,160,124}");
/*      */     }
/*      */     
/* 1447 */     if (oldVersion < 880) {
/*      */       
/* 1449 */       if (getSetting("colourcodehelperkey", 0) == -1)
/*      */       {
/* 1451 */         setSetting("colourcodehelperkey", 0);
/*      */       }
/*      */       
/* 1454 */       String eventsLayout = getSetting("events.layout", "");
/*      */       
/* 1456 */       if (eventsLayout.length() > 0) {
/*      */         
/* 1458 */         eventsLayout = eventsLayout + "{1015,160,144}";
/* 1459 */         setSetting("events.layout", eventsLayout);
/*      */       } 
/*      */     } 
/*      */     
/* 1463 */     if (oldVersion < 902) {
/*      */       
/* 1465 */       String eventsLayout = getSetting("events.layout", "");
/*      */       
/* 1467 */       if (eventsLayout.length() > 0) {
/*      */         
/* 1469 */         eventsLayout = eventsLayout + "{1016,6,164}{1017,160,164}";
/* 1470 */         setSetting("events.layout", eventsLayout);
/*      */       } 
/*      */     } 
/*      */     
/* 1474 */     if (oldVersion < 903)
/*      */     {
/* 1476 */       MacroModCore.firstRun = true;
/*      */     }
/*      */     
/* 1479 */     if (oldVersion < 952) {
/*      */       
/* 1481 */       String symbolOverrides = getSetting("keyboard.symbols", "");
/*      */       
/* 1483 */       if (symbolOverrides.length() > 0) {
/*      */         
/* 1485 */         symbolOverrides = symbolOverrides + "{248,24}{249,25}";
/* 1486 */         setSetting("keyboard.symbols", symbolOverrides);
/*      */       } 
/*      */       
/* 1489 */       String keyboardLayout = getSetting("keyboard.layout", "");
/*      */       
/* 1491 */       if (keyboardLayout.length() > 0) {
/*      */         
/* 1493 */         keyboardLayout = keyboardLayout + "{248,92,116}{249,92,132}";
/* 1494 */         setSetting("keyboard.layout", keyboardLayout);
/*      */       } 
/*      */     } 
/*      */     
/* 1498 */     if (oldVersion < 980) {
/*      */       
/* 1500 */       String eventsLayout = "{1000,6,4}{1001,6,24}{1002,6,44}{1003,6,64}{1004,6,84}{1005,6,104}{1006,6,124}{1007,6,144}{1016,6,164}{1008,145,4}{1009,145,24}{1010,145,44}{1011,145,64}{1012,145,84}{1013,145,104}{1014,145,124}{1015,145,144}{1017,145,164}{1018,284,4}";
/* 1501 */       setSetting("events.layout", eventsLayout);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void autoPlayMacro(int key, boolean overrideKeyDown) {
/* 1513 */     if (canPlayMacro(key, overrideKeyDown)) playMacro(key, true, ScriptContext.MAIN, (IVariableProvider)null);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canPlayMacro(int key, boolean overrideKeyDown) {
/* 1524 */     if (!overrideKeyDown) {
/*      */       
/* 1526 */       MacroTemplate tpl = getMacroTemplateWithOverlay(key);
/*      */ 
/*      */       
/* 1529 */       if (tpl.isEmpty()) return false;
/*      */       
/* 1531 */       if (tpl.alwaysOverride && !InputHandler.fallbackMode) return true;
/*      */       
/* 1533 */       if (isReservedKey(key)) return false;
/*      */     
/*      */     } 
/* 1536 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMacroBound(int key, boolean useOverlay) {
/* 1547 */     MacroTemplate tpl = getMacroTemplate(key, useOverlay);
/* 1548 */     return (tpl != null && !tpl.isEmpty());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMacroGlobal(int key, boolean useOverlay) {
/* 1559 */     MacroTemplate tpl = getMacroTemplate(key, useOverlay);
/* 1560 */     return (tpl != null && tpl.global);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isKeyAlwaysOverridden(int key, boolean useOverlay, boolean check) {
/* 1571 */     MacroTemplate tpl = getMacroTemplate(key, useOverlay);
/* 1572 */     return (tpl != null && tpl.alwaysOverride && (check || (bsu.z()).m == null));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isKeyOverlaid(int key) {
/* 1583 */     if (this.overlayConfig != null && !this.overlayConfig.equals(this.activeConfig) && hasConfig(this.overlayConfig)) {
/*      */       
/* 1585 */       MacroTemplate tpl = getMacroTemplate(this.overlayConfig, key, false);
/* 1586 */       if (tpl != null && !tpl.global && !tpl.isEmpty()) return true;
/*      */     
/*      */     } 
/* 1589 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isReservedKey(int key) {
/* 1601 */     if (key > 254) return false;
/*      */ 
/*      */     
/* 1604 */     if (key == InputHandler.keybindActivate.i()) return true;
/*      */ 
/*      */     
/* 1607 */     if (key > 0 && key < 11) return true;
/*      */ 
/*      */     
/* 1610 */     if (MacroModSettings.isReservedKey(key)) return true;
/*      */ 
/*      */     
/* 1613 */     if (this.thirdPartyReservedKeys.contains(Integer.valueOf(key))) return true;
/*      */ 
/*      */     
/* 1616 */     if (key > 249 && key < 253) key -= 350;
/*      */ 
/*      */     
/* 1619 */     for (bsr bind : (AbstractionLayer.getGameSettings()).at) {
/* 1620 */       if (bind.i() == key) return true;
/*      */     
/*      */     } 
/* 1623 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playMacro(int key, boolean checkModifiers, ScriptContext scriptContext, IVariableProvider contextProvider) {
/* 1636 */     playMacro(key, checkModifiers, scriptContext, contextProvider, false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void playMacro(int key, boolean checkModifiers, ScriptContext scriptContext, IVariableProvider contextProvider, boolean synchronous) {
/* 1642 */     MacroTemplate tpl = getMacroTemplate(key, true);
/*      */     
/* 1644 */     if (tpl == null || tpl.isEmpty() || key == InputHandler.getOverrideKeyCode()) {
/*      */       return;
/*      */     }
/*      */     
/* 1648 */     Macro macro = tpl.createInstance(checkModifiers, scriptContext.createActionContext(contextProvider));
/*      */ 
/*      */     
/* 1651 */     if (macro == null)
/*      */       return; 
/* 1653 */     playMacro(macro, synchronous);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playMacro(MacroTemplate template, boolean checkModifiers, ScriptContext scriptContext, IVariableProvider contextProvider) {
/* 1665 */     playMacro(template, checkModifiers, scriptContext, contextProvider, false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void playMacro(MacroTemplate template, boolean checkModifiers, ScriptContext scriptContext, IVariableProvider contextProvider, boolean synchronous) {
/* 1671 */     Macro macro = template.createInstance(checkModifiers, scriptContext.createActionContext(contextProvider));
/*      */ 
/*      */     
/* 1674 */     if (macro == null)
/*      */       return; 
/* 1676 */     playMacro(macro, synchronous);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void playMacro(Macro macro, boolean synchronous) {
/* 1688 */     if (macro.hasRemainingParams()) {
/*      */       
/* 1690 */       AbstractionLayer.displayGuiScreen((bxf)new GuiMacroParam(macro));
/*      */       
/*      */       return;
/*      */     } 
/* 1694 */     macro.setSynchronous(synchronous);
/*      */ 
/*      */     
/*      */     try {
/* 1698 */       if (macro.playMacro(true, true))
/*      */       {
/* 1700 */         addRunningMacro(macro);
/*      */       }
/* 1702 */       else if (macro.dirty)
/*      */       {
/*      */         
/* 1705 */         saveVariables();
/*      */       }
/*      */     
/* 1708 */     } catch (ScriptException ex) {
/*      */       
/* 1710 */       if (ex.getMessage() != null) AbstractionLayer.addChatMessage("§4Unhandled Exception " + ex.getMessage()); 
/* 1711 */       Log.printStackTrace((Throwable)ex);
/*      */     }
/* 1713 */     catch (Exception ex) {
/*      */       
/* 1715 */       if (ex.getMessage() != null) AbstractionLayer.addChatMessage("§4Unhandled Exception " + ex.getMessage()); 
/* 1716 */       Log.printStackTrace(ex);
/*      */       
/* 1718 */       removeRunningMacro(macro);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addRunningMacro(Macro macro) {
/* 1727 */     synchronized (this.executingMacrosLock) {
/*      */ 
/*      */       
/* 1730 */       if (this.insideRunLoop) {
/*      */         
/* 1732 */         this.pendingAdditions.add(macro);
/*      */       }
/*      */       else {
/*      */         
/* 1736 */         this.executingMacros.add(macro);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeRunningMacro(Macro macro) {
/* 1746 */     synchronized (this.executingMacrosLock) {
/*      */       
/* 1748 */       if (this.insideRunLoop) {
/*      */         
/* 1750 */         this.pendingRemovals.add(macro);
/*      */       }
/*      */       else {
/*      */         
/* 1754 */         this.executingMacros.remove(macro);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void terminateActiveMacros() {
/* 1764 */     synchronized (this.executingMacrosLock) {
/*      */       
/* 1766 */       if (this.executingMacros.size() > 0) {
/*      */         
/* 1768 */         Log.info("Terminating {0} active macro(s)", new Object[] { Integer.valueOf(this.executingMacros.size()) });
/* 1769 */         this.executingMacros.clear();
/* 1770 */         this.pendingAdditions.clear();
/* 1771 */         this.pendingRemovals.clear();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void terminateActiveMacros(ScriptContext context, int keyCode) {
/* 1783 */     synchronized (this.executingMacrosLock) {
/*      */       
/* 1785 */       for (Macro macro : this.executingMacros) {
/*      */         
/* 1787 */         if (macro.getContext().getScriptContext().equals(context) && macro.getID() == keyCode)
/*      */         {
/* 1789 */           macro.kill();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendEvent(String eventName, int priority, String... eventArgs) {
/* 1804 */     this.eventManager.sendEvent(eventName, priority, eventArgs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearEvents() {
/* 1812 */     this.eventManager.purgeQueue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onTick(bsu minecraft, boolean clock) {
/* 1821 */     minecraft.y.a("executive");
/*      */     
/* 1823 */     minecraft.y.a("variables");
/* 1824 */     IScriptActionProvider scriptActionProvider = ScriptContext.MAIN.getScriptActionProvider();
/* 1825 */     scriptActionProvider.updateVariableProviders(clock);
/* 1826 */     minecraft.y.b();
/*      */     
/* 1828 */     if (clock) {
/*      */ 
/*      */       
/* 1831 */       minecraft.y.a("latent");
/* 1832 */       ScriptAction.onTickInGame(scriptActionProvider);
/* 1833 */       minecraft.y.b();
/*      */       
/* 1835 */       minecraft.y.a("templates");
/* 1836 */       for (int index = MacroType.Key.getMinId(); index <= MacroType.Key.getMaxId(); index++) {
/*      */         
/* 1838 */         if (this.baseTemplates[index] != null) {
/* 1839 */           this.baseTemplates[index].onTick();
/*      */         }
/* 1841 */         if (this.configs.size() > 0)
/*      */         {
/* 1843 */           for (MacroTemplate[] configTemplates : this.configs.values()) {
/*      */             
/* 1845 */             if (configTemplates[index] != null)
/* 1846 */               configTemplates[index].onTick(); 
/*      */           } 
/*      */         }
/*      */       } 
/* 1850 */       minecraft.y.b();
/*      */ 
/*      */       
/* 1853 */       minecraft.y.a("dispatcher");
/* 1854 */       this.eventManager.onTick(minecraft);
/* 1855 */       minecraft.y.b();
/*      */ 
/*      */       
/* 1858 */       minecraft.y.a("spamfilter");
/* 1859 */       if (this.spamFilter != null) this.spamFilter.onTick(); 
/* 1860 */       minecraft.y.b();
/*      */       
/* 1862 */       if (this.dirtyCounter > 0) this.dirtyCounter--;
/*      */     
/*      */     } 
/* 1865 */     minecraft.y.a("execute");
/*      */ 
/*      */     
/*      */     try {
/* 1869 */       synchronized (this.executingMacrosLock) {
/*      */         
/* 1871 */         this.insideRunLoop = true;
/*      */         
/* 1873 */         if (this.executingMacros.size() > 0)
/*      */         {
/* 1875 */           for (Iterator<Macro> executingMacrosIterator = this.executingMacros.iterator(); executingMacrosIterator.hasNext(); ) {
/*      */             
/* 1877 */             Macro macro = executingMacrosIterator.next();
/*      */             
/* 1879 */             if (macro.killed) {
/*      */               
/* 1881 */               this.pendingRemovals.add(macro);
/*      */               
/*      */               continue;
/*      */             } 
/*      */             
/*      */             try {
/* 1887 */               if (macro.playMacro(isTriggerActive(macro.getID()), clock)) {
/*      */                 
/* 1889 */                 this.dirty |= macro.dirty;
/*      */                 
/*      */                 continue;
/*      */               } 
/* 1893 */               this.pendingRemovals.add(macro);
/*      */             
/*      */             }
/* 1896 */             catch (ScriptException ex) {
/*      */               
/* 1898 */               Log.printStackTrace((Throwable)ex);
/* 1899 */               AbstractionLayer.addChatMessage("" + ex.getMessage());
/*      */             }
/* 1901 */             catch (Exception ex) {
/*      */               
/* 1903 */               Log.printStackTrace(ex);
/* 1904 */               AbstractionLayer.addChatMessage((ex.getMessage() != null) ? ("" + ex.getMessage()) : ("" + ex.getClass().getSimpleName()));
/* 1905 */               this.pendingRemovals.add(macro);
/*      */             } 
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/* 1911 */         this.insideRunLoop = false;
/*      */         
/* 1913 */         for (Iterator<Macro> pendingRemovalsIterator = this.pendingRemovals.iterator(); pendingRemovalsIterator.hasNext(); ) {
/*      */           
/* 1915 */           this.executingMacros.remove(pendingRemovalsIterator.next());
/* 1916 */           pendingRemovalsIterator.remove();
/*      */         } 
/*      */         
/* 1919 */         for (Iterator<Macro> pendingAdditionsIterator = this.pendingAdditions.iterator(); pendingAdditionsIterator.hasNext(); )
/*      */         {
/* 1921 */           this.executingMacros.add(pendingAdditionsIterator.next());
/* 1922 */           pendingAdditionsIterator.remove();
/*      */         }
/*      */       
/*      */       } 
/* 1926 */     } catch (ConcurrentModificationException ex) {
/*      */       
/* 1928 */       Log.printStackTrace(ex);
/*      */     } 
/* 1930 */     minecraft.y.b();
/*      */ 
/*      */     
/* 1933 */     if (this.dirty && this.dirtyCounter == 0) {
/*      */       
/* 1935 */       saveVariables();
/* 1936 */       this.dirty = false;
/*      */ 
/*      */       
/* 1939 */       this.dirtyCounter = 100;
/*      */     } 
/*      */     
/* 1942 */     minecraft.y.b();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onJoinGame(String serverName) {
/* 1952 */     this.spamFilter = new SpamFilter();
/*      */     
/* 1954 */     this.mod.getAutoDiscoveryAgent().setSpamFilter(this.spamFilter);
/* 1955 */     this.builtinEventDispatcher.onJoinGame(serverName, this.spamFilter);
/*      */     
/* 1957 */     detectThirdPartyModKeys();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onServerConnect(hg netclienthandler) {
/* 1965 */     this.spamFilter = new SpamFilter();
/*      */     
/* 1967 */     this.mod.getAutoDiscoveryAgent().setSpamFilter(this.spamFilter);
/* 1968 */     this.builtinEventDispatcher.onServerConnect(netclienthandler, this.spamFilter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean stopActiveMacroAt(int mouseX, int mouseY) {
/* 1979 */     Macro removeMacro = null;
/* 1980 */     int left = 4;
/* 1981 */     int top = 10;
/*      */     
/* 1983 */     if (this.spamFilter != null && this.spamFilter.getHasQueue()) {
/*      */       
/* 1985 */       if (this.spamFilter.mousePressed(left, top, mouseX, mouseY)) return true; 
/* 1986 */       top += 10;
/*      */     } 
/*      */     
/* 1989 */     synchronized (this.executingMacrosLock) {
/*      */       
/* 1991 */       for (Macro macro : this.executingMacros) {
/*      */         
/* 1993 */         if (mouseX > left && mouseX < left + 12 && mouseY > top - 1 && mouseY < top + 9) {
/*      */           
/* 1995 */           removeMacro = macro;
/*      */           break;
/*      */         } 
/* 1998 */         top += 10;
/*      */       } 
/*      */     } 
/*      */     
/* 2002 */     if (removeMacro != null) {
/*      */       
/* 2004 */       removeRunningMacro(removeMacro);
/*      */       
/* 2006 */       if (removeMacro.dirty) saveVariables(); 
/* 2007 */       return true;
/*      */     } 
/*      */     
/* 2010 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawPlaybackStatus(bty fontRenderer, int left, int top, int width, int height, int mouseX, int mouseY, boolean drawStopButtons) {
/* 2022 */     if (this.spamFilter != null) top = this.spamFilter.drawQueueStatus(fontRenderer, left, top, width, height, mouseX, mouseY, drawStopButtons);
/*      */     
/* 2024 */     synchronized (this.executingMacrosLock) {
/*      */       
/* 2026 */       for (IMacrosAPIModule macro : this.executingMacros)
/*      */       {
/* 2028 */         top = GuiMacroModOverlay.drawStatusLine(fontRenderer, macro.toString(), left, top, mouseX, mouseY, drawStopButtons);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getExecutingMacroCount() {
/* 2040 */     synchronized (this.executingMacrosLock) {
/*      */       
/* 2042 */       return this.executingMacros.size();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void refreshPermissions() {
/* 2048 */     synchronized (this.executingMacrosLock) {
/*      */       
/* 2050 */       for (Macro macro : this.executingMacros)
/*      */       {
/* 2052 */         macro.refreshPermissions();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dispatchChatMessage(String message, ScriptContext context) {
/*      */     try {
/* 2067 */       int cutoff = 90;
/*      */       
/* 2069 */       while (message.length() > 100) {
/*      */ 
/*      */         
/* 2072 */         int pos = message.substring(cutoff, 100).indexOf(" ") + cutoff;
/* 2073 */         if (pos == cutoff - 1) pos = 100;
/*      */         
/* 2075 */         this.spamFilter.sendChatMessage(replaceInvalidChars(message.substring(0, pos)), context);
/* 2076 */         message = message.substring(pos).trim();
/*      */       } 
/*      */       
/* 2079 */       this.spamFilter.sendChatMessage(replaceInvalidChars(message), context);
/*      */     }
/* 2081 */     catch (NullPointerException nullPointerException) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String replaceInvalidChars(String message) {
/* 2092 */     if (message == null) return "";
/*      */     
/* 2094 */     StringBuilder sb = new StringBuilder();
/* 2095 */     char[] chars = message.toCharArray();
/* 2096 */     for (int pos = 0; pos < chars.length; pos++) {
/*      */       
/* 2098 */       char charAt = chars[pos];
/* 2099 */       sb.append(v.a(charAt) ? charAt : 63);
/*      */     } 
/*      */     
/* 2102 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isTriggerActive(int mappingId) {
/* 2114 */     if (mappingId < 255) {
/*      */       
/* 2116 */       if (mappingId == 250) return Mouse.isButtonDown(0); 
/* 2117 */       if (mappingId == 251) return Mouse.isButtonDown(1); 
/* 2118 */       if (mappingId == 252) return Mouse.isButtonDown(2); 
/* 2119 */       return InputHandler.isKeyReallyDown(mappingId);
/*      */     } 
/*      */     
/* 2122 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static alq getItem(oa name) {
/* 2127 */     if ("air".equals(name))
/*      */     {
/* 2129 */       return ItemID.ITEM_AIR_VIRTUAL;
/*      */     }
/*      */     
/* 2132 */     return (alq)alq.e.a(name);
/*      */   }
/*      */ 
/*      */   
/*      */   public static atr getBlock(oa name) {
/* 2137 */     return (atr)atr.c.a(name);
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getItemName(alq item) {
/* 2142 */     if (item == ItemID.ITEM_AIR_VIRTUAL) {
/* 2143 */       return "air";
/*      */     }
/* 2145 */     oa itemName = (oa)alq.e.c(item);
/* 2146 */     return (itemName == null) ? "air" : stripNamespace(itemName.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getBlockName(atr block) {
/* 2151 */     oa blockName = (oa)atr.c.c(block);
/* 2152 */     return (blockName == null) ? "air" : stripNamespace(blockName.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String stripNamespace(String itemName) {
/* 2161 */     return (MacroModSettings.stripDefaultNamespace && itemName.startsWith("minecraft:")) ? itemName.substring(10) : itemName;
/*      */   }
/*      */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\Macros.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */