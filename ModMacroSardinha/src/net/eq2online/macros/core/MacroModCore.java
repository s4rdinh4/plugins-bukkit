/*      */ package net.eq2online.macros.core;
/*      */ 
/*      */ import ahd;
/*      */ import amj;
/*      */ import bsr;
/*      */ import bsu;
/*      */ import bty;
/*      */ import buf;
/*      */ import buv;
/*      */ import bvx;
/*      */ import bxf;
/*      */ import byf;
/*      */ import byj;
/*      */ import cee;
/*      */ import com.mumfrey.liteloader.common.Resources;
/*      */ import com.mumfrey.liteloader.core.LiteLoader;
/*      */ import com.mumfrey.liteloader.transformers.event.EventInfo;
/*      */ import com.mumfrey.liteloader.util.log.LiteLoaderLogger;
/*      */ import cvi;
/*      */ import cvk;
/*      */ import cvl;
/*      */ import cvm;
/*      */ import cwc;
/*      */ import cwd;
/*      */ import cwf;
/*      */ import hg;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.net.InetSocketAddress;
/*      */ import java.net.SocketAddress;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.LinkedList;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import net.eq2online.console.Log;
/*      */ import net.eq2online.macros.AutoDiscoveryAgent;
/*      */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*      */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*      */ import net.eq2online.macros.compatibility.PrivateFields;
/*      */ import net.eq2online.macros.gui.designable.LayoutManager;
/*      */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxErrorList;
/*      */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxFirstRunPrompt;
/*      */ import net.eq2online.macros.gui.ext.BindScreenOptionEntry;
/*      */ import net.eq2online.macros.gui.ext.BindingButtonEntry;
/*      */ import net.eq2online.macros.gui.ext.GuiChatAdapter;
/*      */ import net.eq2online.macros.gui.ext.OverrideKeyEntry;
/*      */ import net.eq2online.macros.gui.helpers.HelperThumbnailImage;
/*      */ import net.eq2online.macros.gui.helpers.HelperUserSkinDownload;
/*      */ import net.eq2online.macros.gui.helpers.ListProvider;
/*      */ import net.eq2online.macros.gui.layout.LayoutPanelEvents;
/*      */ import net.eq2online.macros.gui.layout.LayoutPanelKeys;
/*      */ import net.eq2online.macros.gui.screens.GuiEditThumbnails;
/*      */ import net.eq2online.macros.gui.screens.GuiMacroModOverlay;
/*      */ import net.eq2online.macros.gui.screens.GuiPermissions;
/*      */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*      */ import net.eq2online.macros.input.IInputHandlerModule;
/*      */ import net.eq2online.macros.input.InputHandler;
/*      */ import net.eq2online.macros.input.InputHandlerInjector;
/*      */ import net.eq2online.macros.input.InputHandlerModuleJInput;
/*      */ import net.eq2online.macros.interfaces.IChatEventListener;
/*      */ import net.eq2online.macros.interfaces.ILocalisationProvider;
/*      */ import net.eq2online.macros.interfaces.IMultipleConfigurations;
/*      */ import net.eq2online.macros.interfaces.ISaveSettings;
/*      */ import net.eq2online.macros.interfaces.ISettingsProvider;
/*      */ import net.eq2online.macros.rendering.FontRendererLegacy;
/*      */ import net.eq2online.macros.res.ResourceLocations;
/*      */ import net.eq2online.macros.struct.ItemStackInfo;
/*      */ import oa;
/*      */ import org.lwjgl.input.Mouse;
/*      */ import vb;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MacroModCore
/*      */   implements ILocalisationProvider, cvl
/*      */ {
/*      */   private static MacroModCore instance;
/*   99 */   public static int VERSION = 1130;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String version = "0.11.3";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean haveAutoSwitched = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  114 */   private String lastServerName = "";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Macros macros;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected InputHandler inputHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  129 */   protected HashMap<oa, HelperThumbnailImage> thumbnailManagers = new HashMap<oa, HelperThumbnailImage>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected HelperThumbnailImage activeThumbnailManager;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected GuiEditThumbnails activeThumbnailGui;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean oldHideGUI;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  149 */   private static ArrayList<ISaveSettings> settingsProviders = new ArrayList<ISaveSettings>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  154 */   private static ArrayList<IMultipleConfigurations> configObjects = new ArrayList<IMultipleConfigurations>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  159 */   private static ArrayList<IChatEventListener> chatListeners = new ArrayList<IChatEventListener>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected LayoutPanelKeys keyboardLayout;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected LayoutPanelEvents eventLayout;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AutoDiscoveryAgent autoDiscoveryAgent;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int displayWidth;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int displayHeight;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int guiScale;
/*      */ 
/*      */ 
/*      */   
/*      */   protected GuiMacroModOverlay overlay;
/*      */ 
/*      */ 
/*      */   
/*      */   protected HelperUserSkinDownload userSkinManager;
/*      */ 
/*      */ 
/*      */   
/*      */   protected FontRendererLegacy legacyFontRenderer;
/*      */ 
/*      */ 
/*      */   
/*  205 */   protected Properties localisationTable = new Properties();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  210 */   protected String currentLanguageCode = "en_GB";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean firstRun = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  220 */   private static ArrayList<String> startupErrors = new ArrayList<String>();
/*      */ 
/*      */   
/*      */   private static boolean inputInitDone = false;
/*      */ 
/*      */   
/*      */   private static boolean displayedStartupErrors = false;
/*      */ 
/*      */   
/*      */   private ListProvider listProvider;
/*      */ 
/*      */   
/*      */   private GuiChatAdapter chatAdapter;
/*      */ 
/*      */   
/*      */   private MacroThumbnailResourcePack thumbnailResourceProvider;
/*      */ 
/*      */   
/*      */   private boolean displayBetaMessage;
/*      */ 
/*      */   
/*      */   private byj lastTransformedScreen;
/*      */ 
/*      */   
/*      */   private boolean scrollToButtons;
/*      */ 
/*      */   
/*  247 */   private long globalTickCounter = 0L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MacroModCore getInstance() {
/*  255 */     if (instance == null)
/*      */     {
/*  257 */       instance = new MacroModCore();
/*      */     }
/*      */     
/*  260 */     return instance;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Macros getMacroManager() {
/*  265 */     if (instance == null) {
/*  266 */       throw new RuntimeException("Instance was not set");
/*      */     }
/*  268 */     if (instance.macros == null) {
/*  269 */       throw new RuntimeException("Macros was not set");
/*      */     }
/*  271 */     return (instance != null) ? instance.macros : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MacroModCore() {
/*  282 */     instance = this;
/*  283 */     bsu minecraft = bsu.z();
/*      */     
/*  285 */     this.listProvider = new ListProvider();
/*      */     
/*  287 */     initialiseLocalisationTable("en_GB");
/*  288 */     LocalisationProvider.setProvider(this);
/*      */ 
/*      */     
/*  291 */     this.autoDiscoveryAgent = new AutoDiscoveryAgent();
/*      */ 
/*      */     
/*  294 */     this.inputHandler = new InputHandler(this);
/*      */ 
/*      */     
/*  297 */     this.macros = new Macros(minecraft, this);
/*      */     
/*  299 */     this.chatAdapter = new GuiChatAdapter(this.macros);
/*      */     
/*  301 */     ((cvi)minecraft.O()).a(this);
/*      */     
/*  303 */     this.displayBetaMessage = (version().contains("beta") || version().contains("build"));
/*      */     
/*  305 */     this.thumbnailResourceProvider = new MacroThumbnailResourcePack(getMacrosDirectory());
/*  306 */     Resources<?, cvm> resources = LiteLoader.getGameEngine().getResources();
/*  307 */     resources.registerResourcePack(this.thumbnailResourceProvider);
/*      */   }
/*      */ 
/*      */   
/*      */   public void init(bsu minecraft) {
/*  312 */     this.macros.init();
/*      */     
/*  314 */     this.legacyFontRenderer = new FontRendererLegacy(AbstractionLayer.getGameSettings(), ResourceLocations.DEFAULTFONT, minecraft.N());
/*      */ 
/*      */     
/*  317 */     this.keyboardLayout = new LayoutPanelKeys(minecraft, 9);
/*  318 */     this.eventLayout = new LayoutPanelEvents(minecraft, 10);
/*      */     
/*  320 */     this.keyboardLayout.notifySettingsCleared();
/*  321 */     this.eventLayout.notifySettingsCleared();
/*      */ 
/*      */     
/*  324 */     createThumbnailManagers(minecraft);
/*      */     
/*  326 */     this.listProvider.init(minecraft);
/*      */ 
/*      */     
/*  329 */     InputHandler.keybindSneak = minecraft.t.Z;
/*      */ 
/*      */     
/*  332 */     LiteLoader.getInput().registerKeyBinding(InputHandler.keybindActivate);
/*  333 */     LiteLoader.getInput().registerKeyBinding(InputHandler.keybindOverride);
/*      */     
/*  335 */     if (!MacroModSettings.enableOverride) {
/*      */       
/*  337 */       InputHandler.keybindOverride.b(0);
/*  338 */       bsr.b();
/*      */     } 
/*      */ 
/*      */     
/*  342 */     if (this.userSkinManager == null)
/*      */     {
/*  344 */       this.userSkinManager = new HelperUserSkinDownload(minecraft, ResourceLocations.PLAYERS);
/*      */     }
/*      */ 
/*      */     
/*  348 */     this.overlay = new GuiMacroModOverlay(this.macros);
/*      */     
/*  350 */     notifySettingsLoaded(this.macros);
/*      */ 
/*      */     
/*  353 */     this.macros.save();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initInputHandler(bsu minecraft) {
/*  363 */     if (!MacroModSettings.compatibilityMode)
/*      */     {
/*  365 */       this.inputHandler.addModule((IInputHandlerModule)new InputHandlerModuleJInput());
/*      */     }
/*      */ 
/*      */     
/*  369 */     this.inputHandler.register(minecraft, this.macros);
/*      */   }
/*      */ 
/*      */   
/*      */   public InputHandler getInputHandler() {
/*  374 */     return this.inputHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String version() {
/*  384 */     return "0.11.3";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static File getMacrosDirectory() {
/*  394 */     File macrosDir = new File(LiteLoader.getGameDirectory(), MacroModSettings.getMacrosDirName());
/*      */ 
/*      */     
/*      */     try {
/*  398 */       if (!macrosDir.exists()) {
/*  399 */         macrosDir.mkdirs();
/*      */       }
/*  401 */       File soundsDir = new File(macrosDir, "sounds");
/*      */       
/*  403 */       if (!soundsDir.exists()) {
/*  404 */         soundsDir.mkdirs();
/*      */       }
/*  406 */     } catch (Exception exception) {}
/*      */     
/*  408 */     return macrosDir.exists() ? macrosDir : LiteLoader.getGameDirectory();
/*      */   }
/*      */ 
/*      */   
/*      */   public ListProvider getListProvider() {
/*  413 */     return this.listProvider;
/*      */   }
/*      */ 
/*      */   
/*      */   public HelperUserSkinDownload getUserSkinManager() {
/*  418 */     return this.userSkinManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public FontRendererLegacy getLegacyFontRenderer() {
/*  423 */     return this.legacyFontRenderer;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getLastServerName() {
/*  428 */     return this.lastServerName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createThumbnailManagers(bsu minecraft) {
/*  438 */     ResourceLocations.DYNAMIC_TOWNS = createThumbnailManager(minecraft, ResourceLocations.TOWNS);
/*  439 */     ResourceLocations.DYNAMIC_HOMES = createThumbnailManager(minecraft, ResourceLocations.HOMES);
/*  440 */     ResourceLocations.DYNAMIC_FRIENDS = createThumbnailManager(minecraft, ResourceLocations.FRIENDS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected oa createThumbnailManager(bsu minecraft, oa imageResource) {
/*      */     try {
/*  452 */       this.thumbnailResourceProvider.addFileFor(imageResource);
/*  453 */       String dynamicResourceName = "icon_" + imageResource.a().substring(imageResource.a().lastIndexOf('/') + 1, imageResource.a().lastIndexOf('.'));
/*  454 */       HelperThumbnailImage thumbnailManager = new HelperThumbnailImage(minecraft, dynamicResourceName, imageResource, 16);
/*  455 */       this.thumbnailManagers.put(imageResource, thumbnailManager);
/*  456 */       return thumbnailManager.getDynamicResource();
/*      */     }
/*  458 */     catch (Exception exception) {
/*      */       
/*  460 */       return imageResource;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HelperThumbnailImage getThumbnailManager(oa imageResource) {
/*  471 */     return this.thumbnailManagers.get(imageResource);
/*      */   }
/*      */ 
/*      */   
/*      */   public HelperThumbnailImage getActiveThumbnailManager() {
/*  476 */     return this.activeThumbnailManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isCapturingThumbnail() {
/*  481 */     return (this.activeThumbnailManager != null);
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
/*      */   public void beginThumbnailCapture(GuiEditThumbnails gui, oa imageResource, int iconIndex) {
/*  493 */     if (this.thumbnailManagers.containsKey(imageResource)) {
/*      */       
/*  495 */       this.oldHideGUI = (AbstractionLayer.getGameSettings()).aw;
/*  496 */       this.activeThumbnailManager = this.thumbnailManagers.get(imageResource);
/*  497 */       this.activeThumbnailManager.prepareCapture(iconIndex);
/*  498 */       this.activeThumbnailGui = gui;
/*      */     } 
/*      */     
/*  501 */     AbstractionLayer.displayGuiScreen(null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void captureThumbnail() {
/*  509 */     if (this.activeThumbnailManager != null) {
/*      */ 
/*      */       
/*  512 */       this.activeThumbnailManager.captureNow(this.displayWidth, this.displayHeight);
/*  513 */       this.activeThumbnailManager = null;
/*      */ 
/*      */       
/*  516 */       AbstractionLayer.displayGuiScreen((bxf)this.activeThumbnailGui);
/*  517 */       this.activeThumbnailGui = null;
/*      */ 
/*      */       
/*  520 */       (AbstractionLayer.getGameSettings()).aw = this.oldHideGUI;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cancelCaptureThumbnail(boolean showOldGui) {
/*  531 */     if (this.activeThumbnailManager != null) {
/*      */ 
/*      */       
/*  534 */       this.activeThumbnailManager.prepareCapture(-1);
/*  535 */       this.activeThumbnailManager = null;
/*      */ 
/*      */       
/*  538 */       if (showOldGui) AbstractionLayer.displayGuiScreen((bxf)this.activeThumbnailGui); 
/*  539 */       this.activeThumbnailGui = null;
/*      */ 
/*      */       
/*  542 */       (AbstractionLayer.getGameSettings()).aw = this.oldHideGUI;
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
/*      */   public static boolean checkDisallowedTextFileName(String textFileName) {
/*  554 */     return textFileName.startsWith(".");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void registerSettingsProvider(ISaveSettings provider) {
/*  564 */     if (settingsProviders.contains(provider))
/*  565 */       return;  settingsProviders.add(provider);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void notifyClearSettings() {
/*  575 */     MacroModSettings.notifySettingsCleared();
/*  576 */     ItemStackInfo.notifySettingsCleared();
/*  577 */     SpamFilter.notifyClearSettings();
/*      */     
/*  579 */     for (int i = 0; i < settingsProviders.size(); i++) {
/*      */       
/*  581 */       if (settingsProviders.get(i) != null) {
/*  582 */         ((ISaveSettings)settingsProviders.get(i)).notifySettingsCleared();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void notifySettingsLoaded(ISettingsProvider settings) {
/*  593 */     MacroModSettings.notifySettingsLoaded(settings);
/*  594 */     ItemStackInfo.notifySettingsLoaded(settings);
/*  595 */     LayoutManager.notifySettingsLoaded(settings);
/*  596 */     SpamFilter.notifySettingsLoaded(settings);
/*      */     
/*  598 */     (getMacroManager()).singlePlayerConfigName = settings.getSetting("singleplayerconfig", "");
/*      */     
/*  600 */     for (int i = 0; i < settingsProviders.size(); i++) {
/*      */       
/*  602 */       if (settingsProviders.get(i) != null) {
/*  603 */         ((ISaveSettings)settingsProviders.get(i)).notifySettingsLoaded(settings);
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
/*      */   public static void saveSettings(Macros macros) {
/*  615 */     if (macros.singlePlayerConfigName != null && macros.singlePlayerConfigName.length() > 0) {
/*  616 */       macros.setSetting("singleplayerconfig", macros.singlePlayerConfigName);
/*      */     }
/*  618 */     MacroModSettings.saveSettings(macros);
/*  619 */     ItemStackInfo.saveSettings(macros);
/*  620 */     LayoutManager.saveSettings(macros);
/*  621 */     SpamFilter.saveSettings(macros);
/*      */     
/*  623 */     for (int i = 0; i < settingsProviders.size(); i++) {
/*      */       
/*  625 */       if (settingsProviders.get(i) != null) {
/*  626 */         ((ISaveSettings)settingsProviders.get(i)).saveSettings(macros);
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
/*      */   public static void registerMultipleConfigurationObject(IMultipleConfigurations configObject) {
/*  638 */     if (configObjects.contains(configObject))
/*  639 */       return;  configObjects.add(configObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void notifyChangeConfiguration() {
/*  648 */     for (IMultipleConfigurations configObject : configObjects)
/*      */     {
/*  650 */       configObject.notifyChangeConfiguration();
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
/*      */   public void notifyAddConfiguration(String configName, boolean copy) {
/*  663 */     for (IMultipleConfigurations configObject : configObjects)
/*      */     {
/*  665 */       configObject.addConfiguration(configName, copy);
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
/*      */   public void notifyRemoveConfiguration(String configName) {
/*  677 */     for (IMultipleConfigurations configObject : configObjects)
/*      */     {
/*  679 */       configObject.removeConfiguration(configName);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AutoDiscoveryAgent getAutoDiscoveryAgent() {
/*  690 */     return this.autoDiscoveryAgent;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void registerChatListener(IChatEventListener listener) {
/*  695 */     if (!chatListeners.contains(listener)) {
/*  696 */       chatListeners.add(listener);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onChat(String chatMessage) {
/*  706 */     String chatMessageNoColours = vb.a(chatMessage);
/*      */     
/*  708 */     this.autoDiscoveryAgent.onChat(chatMessage, chatMessageNoColours);
/*      */     
/*  710 */     for (IChatEventListener listener : chatListeners)
/*      */     {
/*  712 */       listener.receiveChatPacket(chatMessage, chatMessageNoColours);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean onSendChatMessage(String message) {
/*  718 */     boolean pass = true;
/*      */     
/*  720 */     for (IChatEventListener listener : chatListeners)
/*      */     {
/*  722 */       pass &= listener.onSendChatMessage(message);
/*      */     }
/*      */     
/*  725 */     return pass;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onRender() {
/*  730 */     bsu minecraft = bsu.z();
/*  731 */     if (minecraft != null && minecraft.m != null) {
/*      */       
/*  733 */       bxf guiscreen = minecraft.m;
/*      */       
/*  735 */       if (guiscreen instanceof byj) {
/*      */         
/*  737 */         if (guiscreen != this.lastTransformedScreen)
/*      */         {
/*  739 */           this.lastTransformedScreen = (byj)guiscreen;
/*  740 */           transformKeyBindings(minecraft);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  745 */         this.lastTransformedScreen = null;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  750 */       this.lastTransformedScreen = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void enableScrollToButtons() {
/*  756 */     this.scrollToButtons = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void transformKeyBindings(bsu minecraft) {
/*      */     try {
/*  766 */       byf keyBindingList = (byf)PrivateFields.keyBindingList.get(this.lastTransformedScreen);
/*  767 */       buv[] keyBindingEntries = (buv[])PrivateFields.keyBindingEntries.get(keyBindingList);
/*  768 */       LinkedList<buv> newList = new LinkedList<buv>();
/*      */       
/*  770 */       int maxDescriptionWidth = calcMaxDescriptionWidth(minecraft);
/*  771 */       int bindButtonIndex = 0;
/*      */       
/*  773 */       for (int bindIndex = 0; bindIndex < keyBindingEntries.length; bindIndex++) {
/*      */         
/*  775 */         buv listEntry = keyBindingEntries[bindIndex];
/*  776 */         newList.add(listEntry);
/*      */         
/*  778 */         if (listEntry instanceof byi) {
/*      */           
/*  780 */           bsr binding = (bsr)PrivateFields.keyEntryBinding.get(listEntry);
/*  781 */           if (binding == InputHandler.keybindOverride) {
/*      */             
/*  783 */             newList.removeLast();
/*  784 */             bindButtonIndex = bindIndex;
/*      */ 
/*      */             
/*  787 */             newList.add(new OverrideKeyEntry(minecraft, this.lastTransformedScreen, maxDescriptionWidth, binding));
/*  788 */             newList.add(new BindScreenOptionEntry(minecraft, this.lastTransformedScreen, maxDescriptionWidth));
/*  789 */             newList.add(new BindingButtonEntry(minecraft, this.lastTransformedScreen));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  794 */       PrivateFields.keyBindingEntries.set(keyBindingList, newList.toArray(new buv[0]));
/*      */       
/*  796 */       if (this.scrollToButtons)
/*      */       {
/*  798 */         this.scrollToButtons = false;
/*  799 */         keyBindingList.g(-2147483648);
/*  800 */         keyBindingList.g(bindButtonIndex * 20 - 40);
/*      */       }
/*      */     
/*  803 */     } catch (Exception ex) {
/*      */       
/*  805 */       ex.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private int calcMaxDescriptionWidth(bsu minecraft) {
/*  811 */     int maxEntryWidth = 0;
/*  812 */     bty fontRenderer = minecraft.k;
/*      */     
/*  814 */     for (bsr binding : minecraft.t.at) {
/*      */       
/*  816 */       int entryWidth = fontRenderer.a(cwc.a(binding.g(), new Object[0]));
/*  817 */       if (entryWidth > maxEntryWidth) maxEntryWidth = entryWidth;
/*      */     
/*      */     } 
/*  820 */     return maxEntryWidth;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onTimerUpdate(bsu minecraft) {
/*  825 */     this.inputHandler.onTimerUpdate(minecraft);
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
/*      */   public void onTickInGUI(float partialTick, bsu minecraft, boolean clock, bxf guiscreen) {
/*  837 */     if (guiscreen != null && this.activeThumbnailManager != null)
/*      */     {
/*  839 */       cancelCaptureThumbnail(false);
/*      */     }
/*      */     
/*  842 */     if (this.userSkinManager != null)
/*      */     {
/*      */       
/*  845 */       this.userSkinManager.onTick();
/*      */     }
/*      */     
/*  848 */     if (!inputInitDone) {
/*      */       
/*  850 */       inputInitDone = true;
/*  851 */       initInputHandler(minecraft);
/*      */     } 
/*      */     
/*  854 */     if (guiscreen instanceof bwo)
/*      */     {
/*  856 */       onDisconnected();
/*      */     }
/*      */     
/*  859 */     if (guiscreen instanceof bxq) {
/*      */       
/*  861 */       if (firstRun) {
/*      */         
/*  863 */         AbstractionLayer.displayGuiScreen((bxf)new GuiDialogBoxFirstRunPrompt(guiscreen));
/*  864 */         firstRun = false;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  872 */       if (!displayedStartupErrors && startupErrors.size() > 0) {
/*      */         
/*  874 */         displayedStartupErrors = true;
/*  875 */         AbstractionLayer.displayGuiScreen((bxf)new GuiDialogBoxErrorList(guiscreen, startupErrors, LocalisationProvider.getLocalisedString("startuperrors.line1"), LocalisationProvider.getLocalisedString("startuperrors.title")));
/*  876 */         startupErrors.clear();
/*      */       } 
/*      */       
/*  879 */       onDisconnected();
/*      */       
/*  881 */       if (this.displayBetaMessage) {
/*      */         
/*  883 */         int top = guiscreen.getClass().getSimpleName().contains("Voxel") ? 14 : 2;
/*  884 */         minecraft.k.a("Macros version " + version(), 2.0F, top, -43691);
/*      */       } 
/*      */ 
/*      */       
/*  888 */       cwd currentLanguage = minecraft.Q().c();
/*  889 */       if (currentLanguage != null && !currentLanguage.a().equals(this.currentLanguageCode)) {
/*      */         
/*  891 */         this.currentLanguageCode = currentLanguage.a();
/*  892 */         initialiseLocalisationTable(this.currentLanguageCode);
/*      */       } 
/*      */     } 
/*      */     
/*  896 */     if (this.displayWidth != minecraft.d || this.displayHeight != minecraft.e || this.guiScale != (AbstractionLayer.getGameSettings()).aG) {
/*      */       
/*  898 */       this.lastTransformedScreen = null;
/*  899 */       this.displayWidth = minecraft.d;
/*  900 */       this.displayHeight = minecraft.e;
/*  901 */       this.guiScale = (AbstractionLayer.getGameSettings()).aG;
/*      */       
/*  903 */       buf scaledresolution = new buf(minecraft, this.displayWidth, this.displayHeight);
/*  904 */       GuiScreenEx.guiScaleFactor = scaledresolution.e();
/*      */       
/*  906 */       if (this.overlay != null)
/*      */       {
/*  908 */         this.overlay.a(minecraft, scaledresolution.a(), scaledresolution.b());
/*      */       }
/*      */       
/*  911 */       this.inputHandler.getBuffers();
/*      */     } 
/*      */     
/*  914 */     if (minecraft.f == null)
/*      */     {
/*  916 */       this.inputHandler.processBuffers(minecraft, false, true, false);
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
/*      */   public void onTickInGame(float partialTick, bsu minecraft, boolean clock) {
/*  946 */     if (this.userSkinManager != null) {
/*      */       
/*  948 */       minecraft.y.a("skinmanager");
/*  949 */       this.userSkinManager.onTick();
/*  950 */       minecraft.y.b();
/*      */     } 
/*      */ 
/*      */     
/*  954 */     cwd currentLanguage = minecraft.Q().c();
/*  955 */     if (currentLanguage != null && !currentLanguage.a().equals(this.currentLanguageCode)) {
/*      */       
/*  957 */       this.currentLanguageCode = currentLanguage.a();
/*  958 */       initialiseLocalisationTable(this.currentLanguageCode);
/*      */     } 
/*      */ 
/*      */     
/*  962 */     if (this.displayWidth != minecraft.d || this.displayHeight != minecraft.e || this.guiScale != (AbstractionLayer.getGameSettings()).aG) {
/*      */       
/*  964 */       this.displayWidth = minecraft.d;
/*  965 */       this.displayHeight = minecraft.e;
/*  966 */       this.guiScale = (AbstractionLayer.getGameSettings()).aG;
/*      */       
/*  968 */       if (this.overlay != null) {
/*      */         
/*  970 */         buf scaledresolution = new buf(minecraft, this.displayWidth, this.displayHeight);
/*  971 */         GuiScreenEx.guiScaleFactor = scaledresolution.e();
/*  972 */         this.overlay.a(minecraft, scaledresolution.a(), scaledresolution.b());
/*      */       } 
/*      */       
/*  975 */       this.inputHandler.getBuffers();
/*      */     } 
/*      */ 
/*      */     
/*  979 */     if (this.overlay != null && AbstractionLayer.getWorld() != null) {
/*      */       
/*  981 */       minecraft.y.a("overlay");
/*  982 */       buf scaledresolution = new buf(minecraft, minecraft.d, minecraft.e);
/*      */       
/*  984 */       int mouseX = Mouse.getX() * scaledresolution.a() / minecraft.d;
/*  985 */       int mouseY = scaledresolution.b() - Mouse.getY() * scaledresolution.b() / minecraft.e - 1;
/*      */       
/*  987 */       this.overlay.drawOverlay(minecraft, this, AbstractionLayer.getWorld().K(), mouseX, mouseY, partialTick, clock);
/*      */       
/*  989 */       minecraft.y.b();
/*      */     } 
/*      */     
/*  992 */     this.inputHandler.processBuffers(minecraft, true, true, false);
/*      */ 
/*      */     
/*  995 */     this.macros.onTick(minecraft, clock);
/*      */     
/*  997 */     if (clock) {
/*      */       
/*  999 */       this.globalTickCounter++;
/* 1000 */       if (this.globalTickCounter % 20L == 0L) this.inputHandler.getBuffers();
/*      */ 
/*      */       
/* 1003 */       updateServer();
/*      */       
/* 1005 */       this.inputHandler.onTick(partialTick, minecraft);
/*      */     } 
/*      */     
/* 1008 */     this.autoDiscoveryAgent.onTick();
/* 1009 */     this.userSkinManager.__displayTexture();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void preChatGuiEvent(EventInfo<bvx> e, int mouseX, int mouseY, float partialTicks) {
/* 1014 */     instance.onDrawChat((bvx)e.getSource(), mouseX, mouseY, partialTicks);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void onChatGuiEvent(EventInfo<bvx> e, int mouseX, int mouseY, float partialTicks) {
/* 1019 */     instance.onDrawChatField((bvx)e.getSource(), mouseX, mouseY, partialTicks);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void onChatGuiEvent(EventInfo<bvx> e) {
/* 1024 */     instance.onUpdateChatField((bvx)e.getSource());
/*      */   }
/*      */ 
/*      */   
/*      */   public static void onChatGuiEvent(EventInfo<bvx> e, int mouseX, int mouseY, int mouseButton) {
/* 1029 */     if (instance.onChatFieldMouseClicked((bvx)e.getSource(), mouseX, mouseY, mouseButton))
/*      */     {
/* 1031 */       e.cancel();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void onDrawChat(bvx source, int mouseX, int mouseY, float partialTicks) {
/* 1037 */     if (MacroModSettings.enableButtonsOnChatGui && this.chatAdapter != null)
/*      */     {
/* 1039 */       this.chatAdapter.onTick(source);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void onDrawChatField(bvx source, int mouseX, int mouseY, float partialTicks) {
/* 1045 */     if (MacroModSettings.enableButtonsOnChatGui && this.chatAdapter != null)
/*      */     {
/* 1047 */       this.chatAdapter.a(mouseX, mouseY, partialTicks);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void onUpdateChatField(bvx source) {
/* 1053 */     if (MacroModSettings.enableButtonsOnChatGui)
/*      */     {
/* 1055 */       this.chatAdapter.e();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean onChatFieldMouseClicked(bvx source, int mouseX, int mouseY, int mouseButton) {
/* 1061 */     if (MacroModSettings.enableButtonsOnChatGui)
/*      */     {
/* 1063 */       return this.chatAdapter.handleMouseClicked(mouseX, mouseY, mouseButton);
/*      */     }
/*      */     
/* 1066 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateServer() {
/* 1074 */     if (!this.haveAutoSwitched)
/*      */     {
/* 1076 */       if (bsu.z().E()) {
/*      */         
/* 1078 */         this.haveAutoSwitched = true;
/*      */         
/* 1080 */         if (MacroModSettings.enableAutoConfigSwitch) {
/*      */           
/* 1082 */           this.macros.setActiveConfig(this.macros.singlePlayerConfigName);
/* 1083 */           AbstractionLayer.addChatMessage(LocalisationProvider.getLocalisedString("message.autosp", new Object[] { this.macros.getActiveConfigName() }));
/*      */         } 
/*      */         
/* 1086 */         this.macros.getBuiltinEventDispatcher().initSinglePlayer();
/* 1087 */         this.lastServerName = "SP";
/* 1088 */         this.macros.onJoinGame("SP");
/*      */       } else {
/*      */ 
/*      */         
/*      */         try {
/*      */           
/* 1094 */           cee sendQueue = (AbstractionLayer.getPlayer()).a;
/* 1095 */           SocketAddress socketAddress = sendQueue.a().b();
/*      */           
/* 1097 */           if (socketAddress instanceof InetSocketAddress) {
/*      */             
/* 1099 */             InetSocketAddress inetAddr = (InetSocketAddress)socketAddress;
/*      */             
/* 1101 */             String serverName = inetAddr.getHostName();
/* 1102 */             int serverPort = inetAddr.getPort();
/*      */             
/* 1104 */             this.haveAutoSwitched = true;
/*      */             
/* 1106 */             if (serverName != null && !serverName.equalsIgnoreCase(this.lastServerName))
/*      */             {
/* 1108 */               onConnectToServer(serverName, serverPort);
/* 1109 */               this.lastServerName = serverName;
/*      */             }
/*      */           
/*      */           } 
/* 1113 */         } catch (Exception exception) {}
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onDisconnected() {
/* 1123 */     this.haveAutoSwitched = false;
/* 1124 */     this.lastServerName = "";
/* 1125 */     this.macros.terminateActiveMacros();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onConnectToServer(String serverName, int serverPort) {
/* 1136 */     String serverNameAndPort = serverName + ":" + serverPort;
/*      */     
/* 1138 */     Log.info("onConnectToServer: {0}", new Object[] { serverNameAndPort });
/*      */     
/* 1140 */     if (MacroModSettings.enableAutoConfigSwitch)
/*      */     {
/* 1142 */       if (this.macros.hasConfig(serverNameAndPort)) {
/*      */         
/* 1144 */         this.macros.setActiveConfig(serverNameAndPort);
/* 1145 */         this.overlay.setNextConfigTimer(160L);
/*      */       }
/* 1147 */       else if (this.macros.hasConfig(serverName)) {
/*      */         
/* 1149 */         this.macros.setActiveConfig(serverName);
/* 1150 */         this.overlay.setNextConfigTimer(160L);
/*      */       } 
/*      */     }
/*      */     
/* 1154 */     this.macros.onJoinGame(serverName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onItemPickup(ahd entityPlayer, amj itemStack) {
/* 1165 */     if (entityPlayer == AbstractionLayer.getPlayer())
/*      */     {
/* 1167 */       this.macros.getBuiltinEventDispatcher().onItemPickup(itemStack);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onServerConnect(hg netclienthandler) {
/* 1178 */     this.macros.onServerConnect(netclienthandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPermissionsChanged() {
/* 1186 */     this.macros.refreshPermissions();
/*      */     
/* 1188 */     bsu minecraft = bsu.z();
/*      */     
/* 1190 */     if (minecraft.m != null && minecraft.m instanceof GuiPermissions)
/*      */     {
/* 1192 */       ((GuiPermissions)minecraft.m).refreshPermissions();
/*      */     }
/*      */     
/* 1195 */     if (!InputHandlerInjector.b()) this.listProvider = null;
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initialiseLocalisationTable(String currentLanguage) {
/* 1203 */     this.localisationTable.clear();
/*      */     
/* 1205 */     InputStream langResource = MacroModCore.class.getResourceAsStream("/lang/macros/en_GB.lang");
/* 1206 */     InputStream langResourceEvents = MacroModCore.class.getResourceAsStream("/lang/macros/events/en_GB.lang");
/*      */     
/* 1208 */     if (langResource != null) {
/*      */       
/* 1210 */       try { this.localisationTable.load(langResource); } catch (Exception exception) {}
/*      */       
/* 1212 */       if (langResourceEvents != null) {
/*      */         
/* 1214 */         try { this.localisationTable.load(langResourceEvents); } catch (Exception exception) {}
/*      */       }
/*      */     } 
/*      */     
/* 1218 */     if (!currentLanguage.equals("en_GB")) {
/*      */       
/* 1220 */       InputStream localLangResource = MacroModCore.class.getResourceAsStream("/lang/macros/" + currentLanguage + ".lang");
/*      */       
/* 1222 */       if (localLangResource != null) {
/*      */         
/* 1224 */         Log.info("loading localisations for {0}", new Object[] { currentLanguage });
/*      */ 
/*      */         
/*      */         try {
/* 1228 */           String encoding = "ru_RU".equals(currentLanguage) ? "Cp1251" : "UTF-8";
/* 1229 */           BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(localLangResource, encoding));
/* 1230 */           for (String s = bufferedreader.readLine(); s != null; s = bufferedreader.readLine())
/*      */           {
/* 1232 */             String[] as = s.split("=");
/* 1233 */             if (as != null && as.length == 2)
/*      */             {
/* 1235 */               this.localisationTable.put(as[0], as[1]);
/*      */             }
/*      */           }
/*      */         
/* 1239 */         } catch (Exception exception) {}
/*      */       } 
/*      */     } 
/*      */     
/* 1243 */     this.listProvider.updateItemNames();
/*      */     
/* 1245 */     cwf currentLocale = (cwf)PrivateFields.StaticFields.locale.get();
/* 1246 */     Map<String, String> translateTable = (Map<String, String>)PrivateFields.translateTable.get(currentLocale);
/*      */     
/* 1248 */     if (translateTable != null) {
/*      */       
/* 1250 */       setTranslation(translateTable, "key.categories.macros");
/* 1251 */       setTranslation(translateTable, "key.macros");
/* 1252 */       setTranslation(translateTable, "key.macro_override");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setTranslation(Map<String, String> translateTable, String key) {
/* 1262 */     translateTable.put(key, getLocalisedString(key));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void a(cvk var1) {
/*      */     try {
/* 1270 */       this.currentLanguageCode = bsu.z().Q().c().a();
/* 1271 */       initialiseLocalisationTable(this.currentLanguageCode);
/*      */     }
/* 1273 */     catch (Exception ex) {
/*      */       
/* 1275 */       LiteLoaderLogger.warning("An error occurred updating the Macros localisation data", new Object[0]);
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
/*      */   public static String stringToHighlightMask(String highlighted) {
/* 1287 */     StringBuilder mask = new StringBuilder();
/* 1288 */     char colour = 'f';
/*      */     
/* 1290 */     for (int i = 0; i < highlighted.length(); i++) {
/*      */       
/* 1292 */       char c = highlighted.charAt(i);
/*      */       
/* 1294 */       if (c == '§' && i < highlighted.length() - 1) {
/*      */         
/* 1296 */         colour = highlighted.charAt(i + 1);
/* 1297 */         i++;
/*      */       }
/*      */       else {
/*      */         
/* 1301 */         mask.append(colour);
/*      */       } 
/*      */     } 
/* 1304 */     return mask.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String convertAmpCodes(String text) {
/* 1315 */     return text.replaceAll("(?<!&)&([0-9a-fklmnor])", "§$1").replaceAll("&&", "&");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRawLocalisedString(String key) {
/* 1324 */     return this.localisationTable.getProperty(key, key);
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
/*      */   public String getLocalisedString(String key) {
/* 1336 */     String s = this.localisationTable.getProperty(key, key);
/* 1337 */     return convertAmpCodes(s);
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
/*      */   public String getLocalisedString(String key, Object... params) {
/* 1351 */     return String.format(getLocalisedString(key), params);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String sanitiseFileName(String fileName) {
/* 1362 */     if (fileName.length() > 0) {
/*      */       
/* 1364 */       if (!fileName.toLowerCase().endsWith(".txt")) {
/*      */         
/* 1366 */         if (fileName.lastIndexOf('.') > 0) {
/* 1367 */           fileName = fileName.substring(0, fileName.lastIndexOf('.'));
/*      */         }
/* 1369 */         fileName = fileName + ".txt";
/*      */       } 
/*      */ 
/*      */       
/* 1373 */       if (fileName.startsWith(".")) {
/* 1374 */         fileName = fileName.substring(1);
/*      */       }
/*      */       
/* 1377 */       if (fileName.length() > 4)
/*      */       {
/* 1379 */         return fileName;
/*      */       }
/*      */     } 
/*      */     
/* 1383 */     return null;
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
/*      */   public static int parseInt(String value, int defaultValue) {
/* 1395 */     if (value == null || value == "") return 0;
/*      */     
/* 1397 */     Matcher numeric = Pattern.compile("^(\\d+)").matcher(value);
/* 1398 */     if (numeric.find()) value = numeric.group(1);
/*      */ 
/*      */     
/*      */     try {
/* 1402 */       int i = Integer.parseInt(value);
/* 1403 */       return i;
/*      */     }
/* 1405 */     catch (Exception ex) {
/*      */       
/* 1407 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void logStartupError(String error) {
/* 1418 */     if (!displayedStartupErrors)
/*      */     {
/* 1420 */       startupErrors.add(error);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sendEvent(String eventName, int priority, String... eventArgs) {
/* 1431 */     if (instance != null && instance.macros != null)
/*      */     {
/* 1433 */       instance.macros.sendEvent(eventName, priority, eventArgs);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\MacroModCore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */