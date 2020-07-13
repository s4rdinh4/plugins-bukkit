/*     */ package net.eq2online.macros.scripting;
/*     */ 
/*     */ import ahd;
/*     */ import amj;
/*     */ import bru;
/*     */ import brv;
/*     */ import bsr;
/*     */ import bsu;
/*     */ import bto;
/*     */ import bul;
/*     */ import bvx;
/*     */ import bwv;
/*     */ import bwy;
/*     */ import bxf;
/*     */ import bxr;
/*     */ import byj;
/*     */ import bzj;
/*     */ import cio;
/*     */ import com.mumfrey.liteloader.common.Resources;
/*     */ import com.mumfrey.liteloader.core.LiteLoader;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import com.mumfrey.liteloader.util.ModUtilities;
/*     */ import cvm;
/*     */ import cvo;
/*     */ import cvs;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.DelegateDisconnect;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.compatibility.PrivateFields;
/*     */ import net.eq2online.macros.core.Macro;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.core.MacroParams;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.params.MacroParam;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiControlLabel;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiControlTextArea;
/*     */ import net.eq2online.macros.gui.designable.LayoutManager;
/*     */ import net.eq2online.macros.gui.helpers.HelperContainerSlots;
/*     */ import net.eq2online.macros.gui.screens.GuiChatFilterable;
/*     */ import net.eq2online.macros.gui.screens.GuiCustomGui;
/*     */ import net.eq2online.macros.gui.screens.GuiEditTextFile;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroBind;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroConfig;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroPlayback;
/*     */ import net.eq2online.macros.interfaces.IListObject;
/*     */ import net.eq2online.macros.scripting.api.IExpressionEvaluator;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ import net.eq2online.macros.scripting.api.IVariableProviderShared;
/*     */ import net.eq2online.macros.scripting.crafting.AutoCraftingManager;
/*     */ import net.eq2online.macros.scripting.crafting.AutoCraftingToken;
/*     */ import net.eq2online.macros.scripting.crafting.IAutoCraftingInitiator;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ import net.eq2online.macros.scripting.variable.ItemID;
/*     */ import net.eq2online.macros.scripting.variable.VariableManager;
/*     */ import net.eq2online.macros.scripting.variable.providers.VariableProviderInput;
/*     */ import net.eq2online.macros.scripting.variable.providers.VariableProviderPlayer;
/*     */ import net.eq2online.macros.scripting.variable.providers.VariableProviderSettings;
/*     */ import net.eq2online.macros.scripting.variable.providers.VariableProviderShared;
/*     */ import net.eq2online.macros.scripting.variable.providers.VariableProviderWorld;
/*     */ import wp;
/*     */ import wv;
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
/*     */ public class ScriptActionProvider
/*     */   extends VariableManager
/*     */ {
/*     */   protected bsu minecraft;
/*     */   protected Macros macros;
/*  91 */   private int pendingResChange = 0;
/*     */   
/*     */   private int pendingResWidth;
/*     */   
/*     */   private int pendingResHeight;
/*     */   
/*     */   private PlaySoundResourcePack playSoundResourcePack;
/*     */   
/*  99 */   private String soundResourceNamespace = "macros";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScriptActionProvider(bsu minecraft, Macros macros) {
/* 109 */     this.minecraft = minecraft;
/* 110 */     this.macros = macros;
/*     */     
/* 112 */     this.sharedVariableProvider = (IVariableProviderShared)new VariableProviderShared((IScriptActionProvider)this);
/*     */     
/* 114 */     registerVariableProvider((IVariableProvider)new VariableProviderInput());
/* 115 */     registerVariableProvider((IVariableProvider)new VariableProviderSettings());
/* 116 */     registerVariableProvider((IVariableProvider)new VariableProviderPlayer());
/* 117 */     registerVariableProvider((IVariableProvider)new VariableProviderWorld());
/* 118 */     registerVariableProvider((IVariableProvider)this.sharedVariableProvider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 125 */     this.playSoundResourcePack = new PlaySoundResourcePack(this.soundResourceNamespace, new File(MacroModCore.getMacrosDirectory(), "sounds"));
/* 126 */     Resources<?, cvm> resources = LiteLoader.getGameEngine().getResources();
/* 127 */     resources.registerResourcePack(this.playSoundResourcePack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSoundResourceNamespace() {
/* 133 */     return this.soundResourceNamespace;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTick() {
/* 139 */     super.onTick();
/*     */     
/* 141 */     AutoCraftingManager.getInstance().onTick((IScriptActionProvider)this);
/*     */     
/* 143 */     if (this.pendingResChange > 0) {
/*     */       
/* 145 */       this.pendingResChange--;
/*     */       
/* 147 */       if (this.pendingResChange == 0) {
/*     */         
/*     */         try {
/*     */           
/* 151 */           ModUtilities.setWindowSize(this.pendingResWidth, this.pendingResHeight);
/*     */         }
/* 153 */         catch (Exception ex) {
/*     */           
/* 155 */           ex.printStackTrace();
/*     */         } 
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
/*     */   public static bsu getMinecraft() {
/* 168 */     return bsu.z();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static cio getPlayer() {
/* 178 */     return AbstractionLayer.getPlayer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExpressionEvaluator getExpressionEvaluator(IMacro macro, String expression) {
/* 187 */     ExpressionEvaluator evaluator = new ExpressionEvaluator(expression, (IScriptActionProvider)this, macro);
/*     */     
/* 189 */     return evaluator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionSendChatMessage(IMacro macro, IMacroAction instance, String message) {
/* 198 */     if (message != null && message.length() > 0) {
/*     */       
/* 200 */       if (instance != null && instance.getActionProcessor() != null)
/*     */       {
/* 202 */         if (instance.getActionProcessor().isUnsafe() && !MacroModSettings.spamFilterEnabled) {
/*     */           return;
/*     */         }
/*     */       }
/* 206 */       String[] messages = message.replaceAll(MacroParams.escapeCharacter + "\\x7C", Macro.pipeReplacement).split("[\\x7C\\x82]");
/*     */       
/* 208 */       for (String msg : messages) {
/*     */         
/* 210 */         msg = msg.replaceAll(Macro.pipeReplacement, "\\|").trim();
/* 211 */         this.macros.dispatchChatMessage(msg, macro.getContext().getScriptContext());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionAddChatMessage(String message) {
/* 222 */     AbstractionLayer.addChatMessage(message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionDisconnect() {
/* 232 */     GL.glClear(256);
/*     */ 
/*     */     
/* 235 */     GL.glDisableLighting();
/* 236 */     GL.glDisableDepthTest();
/*     */     
/* 238 */     Log.info("Disconnect");
/* 239 */     AbstractionLayer.displayGuiScreen((bxf)new DelegateDisconnect());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionDisplayGuiScreen(String guiScreenName, ScriptContext context) {
/* 248 */     if (guiScreenName == null || guiScreenName.length() == 0) {
/*     */       
/* 250 */       if (this.minecraft.m != null) {
/*     */         
/* 252 */         if (this.minecraft.m instanceof bvx) AbstractionLayer.displayGuiScreen(null); 
/* 253 */         if (this.minecraft.m instanceof bwy) AbstractionLayer.displayGuiScreen(null); 
/* 254 */         if (this.minecraft.m instanceof bzj) AbstractionLayer.displayGuiScreen(null); 
/* 255 */         if (this.minecraft.m instanceof bwv) AbstractionLayer.displayGuiScreen(null); 
/* 256 */         if (this.minecraft.m instanceof bxr) AbstractionLayer.displayGuiScreen(null); 
/* 257 */         if (this.minecraft.m instanceof byj) AbstractionLayer.displayGuiScreen(null); 
/* 258 */         if (this.minecraft.m instanceof GuiMacroBind) AbstractionLayer.displayGuiScreen(null); 
/* 259 */         if (this.minecraft.m instanceof GuiMacroConfig) AbstractionLayer.displayGuiScreen(null); 
/* 260 */         if (this.minecraft.m instanceof byl) AbstractionLayer.displayGuiScreen(null); 
/* 261 */         if (HelperContainerSlots.currentScreenIsContainer(this.minecraft)) AbstractionLayer.displayGuiScreen(null); 
/* 262 */         if (HelperContainerSlots.currentScreenIsInventory(this.minecraft)) AbstractionLayer.displayGuiScreen(null);
/*     */       
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 268 */     if (guiScreenName.equalsIgnoreCase("chat")) {
/*     */       
/* 270 */       AbstractionLayer.displayGuiScreen((bxf)new bvx());
/*     */     }
/* 272 */     else if (guiScreenName.equalsIgnoreCase("filterablechat")) {
/*     */       
/* 274 */       if (MacroModSettings.showFilterableChat)
/*     */       {
/* 276 */         AbstractionLayer.displayGuiScreen((bxf)new GuiChatFilterable());
/*     */       }
/*     */     }
/* 279 */     else if (guiScreenName.equalsIgnoreCase("menu")) {
/*     */       
/* 281 */       AbstractionLayer.displayGuiScreen((bxf)new bwy());
/*     */     }
/* 283 */     else if (guiScreenName.equalsIgnoreCase("inventory") || guiScreenName.equalsIgnoreCase("inv")) {
/*     */       
/* 285 */       AbstractionLayer.displayGuiScreen((bxf)new bzj((ahd)AbstractionLayer.getPlayer()));
/*     */     }
/* 287 */     else if (guiScreenName.equalsIgnoreCase("options")) {
/*     */       
/* 289 */       AbstractionLayer.displayGuiScreen((bxf)new bwv(null, AbstractionLayer.getGameSettings()));
/*     */     }
/* 291 */     else if (guiScreenName.equalsIgnoreCase("video")) {
/*     */       
/* 293 */       AbstractionLayer.displayGuiScreen((bxf)new bxr(null, AbstractionLayer.getGameSettings()));
/*     */     }
/* 295 */     else if (guiScreenName.equalsIgnoreCase("controls")) {
/*     */       
/* 297 */       AbstractionLayer.displayGuiScreen((bxf)new byj(null, AbstractionLayer.getGameSettings()));
/*     */     }
/* 299 */     else if (guiScreenName.equalsIgnoreCase("macrobind")) {
/*     */       
/* 301 */       AbstractionLayer.displayGuiScreen((bxf)new GuiMacroBind(true));
/*     */     }
/* 303 */     else if (guiScreenName.equalsIgnoreCase("macroplayback")) {
/*     */       
/* 305 */       AbstractionLayer.displayGuiScreen((bxf)new GuiMacroPlayback());
/*     */     }
/* 307 */     else if (guiScreenName.equalsIgnoreCase("macroconfig")) {
/*     */       
/* 309 */       AbstractionLayer.displayGuiScreen((bxf)new GuiMacroConfig(null, false));
/*     */     }
/* 311 */     else if (guiScreenName.equalsIgnoreCase("texteditor")) {
/*     */       
/* 313 */       AbstractionLayer.displayGuiScreen((bxf)new GuiEditTextFile(null, context));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionDisplayCustomScreen(String screenName, String backScreenName) {
/* 320 */     if (LayoutManager.layoutExists(screenName)) {
/*     */       
/* 322 */       if (!LayoutManager.layoutExists(backScreenName)) backScreenName = null; 
/* 323 */       AbstractionLayer.displayGuiScreen((bxf)new GuiCustomGui(screenName, backScreenName));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionBindScreenToSlot(String slotName, String screenName) {
/* 330 */     LayoutManager.setBinding(slotName, screenName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String actionSwitchConfig(String configName, boolean verbose) {
/* 339 */     String oldConfig = this.macros.getActiveConfig();
/*     */     
/* 341 */     if (this.macros.hasConfig(configName)) {
/*     */       
/* 343 */       Log.info("Switching to config {0}", new Object[] { configName });
/* 344 */       this.macros.setActiveConfig(configName);
/*     */       
/* 346 */       if (verbose)
/*     */       {
/* 348 */         AbstractionLayer.addChatMessage(LocalisationProvider.getLocalisedString("message.config", new Object[] { this.macros.getActiveConfigName() }));
/*     */       }
/*     */     } 
/*     */     
/* 352 */     return oldConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String actionOverlayConfig(String configName, boolean toggle, boolean verbose) {
/* 361 */     String oldConfig = this.macros.getOverlayConfig();
/*     */     
/* 363 */     if (toggle && oldConfig != null && oldConfig.equals(configName)) {
/*     */       
/* 365 */       Log.info("Stripping overlaid config");
/* 366 */       this.macros.setOverlayConfig(null);
/*     */       
/* 368 */       if (verbose) {
/* 369 */         AbstractionLayer.addChatMessage(LocalisationProvider.getLocalisedString("message.overlay.remove", new Object[] { this.macros.getOverlayConfigName("§c") }));
/*     */       }
/* 371 */     } else if (configName == null || this.macros.hasConfig(configName)) {
/*     */       
/* 373 */       Log.info("Overlaying config {0}", new Object[] { configName });
/* 374 */       this.macros.setOverlayConfig(configName);
/*     */       
/* 376 */       if (verbose) {
/* 377 */         AbstractionLayer.addChatMessage(LocalisationProvider.getLocalisedString((configName == null) ? "message.overlay.remove" : "message.overlay.add", new Object[] { this.macros.getOverlayConfigName("§c") }));
/*     */       }
/*     */     } 
/* 380 */     return oldConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionRenderDistance() {
/* 389 */     bto gameSettings = AbstractionLayer.getGameSettings();
/* 390 */     int newDistance = 8;
/* 391 */     if (gameSettings.c < 9) newDistance = 4; 
/* 392 */     if (gameSettings.c < 5) newDistance = 2; 
/* 393 */     if (gameSettings.c < 3) newDistance = 16; 
/* 394 */     gameSettings.c = newDistance;
/* 395 */     gameSettings.b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionSetRenderDistance(String distance) {
/* 404 */     int newDistance = 0;
/* 405 */     if (distance.equalsIgnoreCase("far")) { newDistance = 16; }
/* 406 */     else if (distance.equalsIgnoreCase("normal")) { newDistance = 8; }
/* 407 */     else if (distance.equalsIgnoreCase("short")) { newDistance = 4; }
/* 408 */     else if (distance.equalsIgnoreCase("tiny")) { newDistance = 2; }
/*     */     else
/*     */     
/* 411 */     { newDistance = Math.min(16, ScriptCore.tryParseInt(distance, 0)); }
/*     */ 
/*     */     
/* 414 */     if (newDistance > 0) {
/*     */       
/* 416 */       bto gameSettings = AbstractionLayer.getGameSettings();
/* 417 */       gameSettings.c = newDistance;
/* 418 */       gameSettings.b();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean actionInventoryPick(String itemIdString, int itemDamage) {
/* 428 */     ItemID itemId = new ItemID(itemIdString, itemDamage);
/* 429 */     cio thePlayer = AbstractionLayer.getPlayer();
/*     */     
/* 431 */     if (thePlayer != null) {
/*     */       
/* 433 */       thePlayer.bg.a(itemId.item, itemDamage, (itemDamage > 0), false);
/* 434 */       amj currentItem = thePlayer.bg.h();
/* 435 */       return (currentItem != null && itemId.equals(currentItem));
/*     */     } 
/*     */     
/* 438 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionInventorySlot(int slotId) {
/* 447 */     cio thePlayer = AbstractionLayer.getPlayer();
/*     */     
/* 449 */     if (slotId > 0 && slotId < 10) {
/* 450 */       thePlayer.bg.c = slotId - 1;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionInventoryMove(int offset) {
/* 459 */     cio thePlayer = AbstractionLayer.getPlayer();
/* 460 */     thePlayer.bg.d(offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionSetSprinting(boolean sprint) {
/* 469 */     cio thePlayer = AbstractionLayer.getPlayer();
/*     */     
/* 471 */     if (sprint) {
/*     */       
/* 473 */       if (thePlayer.C && !thePlayer.ax() && thePlayer.ck().a() > 6.0F && !thePlayer.bR() && !thePlayer.a(wp.q)) {
/* 474 */         thePlayer.d(true);
/*     */       }
/*     */     } else {
/*     */       
/* 478 */       thePlayer.d(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionStopMacros() {
/* 488 */     this.macros.terminateActiveMacros();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionStopMacros(IMacro macro, int keyCode) {
/* 497 */     if (macro.getID() == keyCode)
/*     */     {
/* 499 */       macro.kill();
/*     */     }
/*     */     
/* 502 */     ScriptContext context = macro.getContext().getScriptContext();
/*     */     
/* 504 */     this.macros.terminateActiveMacros(context, keyCode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteArrayElement(IMacro macro, String arrayName, int offset) {
/* 510 */     arrayName = sanitiseArrayVariableName(macro, arrayName);
/*     */     
/* 512 */     if (arrayName != null)
/*     */     {
/* 514 */       if (arrayName.startsWith("@")) {
/*     */         
/* 516 */         this.sharedVariableProvider.delete(arrayName.substring(1), offset);
/*     */       }
/*     */       else {
/*     */         
/* 520 */         macro.markDirty();
/* 521 */         macro.getArrayProvider().delete(arrayName, offset);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionPumpCharacters(String chars) {
/* 532 */     if (chars == null)
/*     */       return; 
/* 534 */     bxf currentScreen = (bsu.z()).m;
/* 535 */     if (currentScreen instanceof bvx) {
/*     */       
/* 537 */       bul textField = (bul)PrivateFields.chatTextField.get(currentScreen);
/* 538 */       if (textField != null) {
/*     */         
/* 540 */         textField.b(chars);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 545 */     MacroModCore.getInstance().getInputHandler().pumpKeyChars(chars, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionPumpKeyPress(int keyCode, boolean deep) {
/* 554 */     MacroModCore.getInstance().getInputHandler().pumpKeyCode(keyCode, deep);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionSelectResourcePacks(String[] resourcePackNames) {
/* 564 */     cvo resourcePackRepository = this.minecraft.P();
/* 565 */     resourcePackRepository.a();
/*     */     
/* 567 */     List<cvs> resourcePackList = resourcePackRepository.b();
/* 568 */     List<cvs> selectedResourcePacks = new ArrayList<cvs>();
/*     */     
/* 570 */     for (int pos = 0; pos < resourcePackNames.length; pos++) {
/*     */       
/* 572 */       int packIndex = getIndexOfResourcePack(resourcePackList, resourcePackNames[pos]);
/* 573 */       if (packIndex > -1) {
/*     */         
/* 575 */         cvs pack = resourcePackList.get(packIndex);
/* 576 */         if (!selectedResourcePacks.contains(pack)) {
/* 577 */           selectedResourcePacks.add(pack);
/*     */         }
/*     */       } 
/*     */     } 
/* 581 */     this.minecraft.t.k.clear();
/*     */ 
/*     */     
/*     */     try {
/* 585 */       this.minecraft.P().a(selectedResourcePacks);
/*     */       
/* 587 */       for (cvs var14 : selectedResourcePacks)
/*     */       {
/* 589 */         this.minecraft.t.k.add(var14.d());
/*     */       }
/*     */     }
/* 592 */     catch (Exception exception) {
/*     */       
/* 594 */       resourcePackRepository.a(new ArrayList());
/*     */     } 
/*     */     
/* 597 */     this.minecraft.t.b();
/* 598 */     this.minecraft.e();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionUseItem(bsu minecraft, cio thePlayer, amj itemstack, int slotID) {
/* 607 */     int oldItem = thePlayer.bg.c;
/* 608 */     thePlayer.bg.c = slotID;
/*     */     
/* 610 */     bru objectMouseOver = minecraft.s;
/* 611 */     if (objectMouseOver != null && objectMouseOver.a == brv.b) {
/*     */       
/* 613 */       AbstractionLayer.getPlayerController().a(thePlayer, minecraft.f, itemstack, objectMouseOver.a(), objectMouseOver.b, objectMouseOver.c);
/*     */       
/* 615 */       if (itemstack != null && itemstack.b == 0)
/*     */       {
/* 617 */         thePlayer.bg.a[slotID] = null;
/*     */       }
/*     */     } 
/*     */     
/* 621 */     thePlayer.bg.c = oldItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionBindKey(int keyBindId, int keyCode) {
/*     */     try {
/* 632 */       AbstractionLayer.getGameSettings().a(this.minecraft.t.at[keyBindId], keyCode);
/* 633 */       bsr.b();
/*     */     }
/* 635 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionSetEntityDirection(wv entity, float yaw, float pitch) {
/* 641 */     if (entity != null) {
/*     */       
/* 643 */       entity.y = yaw % 360.0F;
/*     */       
/* 645 */       pitch %= 360.0F;
/*     */       
/* 647 */       if (pitch <= 90.0F) {
/* 648 */         entity.z = pitch;
/* 649 */       } else if (pitch >= 270.0F) {
/* 650 */         entity.z = pitch - 360.0F;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void actionRespawnPlayer() {
/* 657 */     AbstractionLayer.getPlayer().ca();
/* 658 */     AbstractionLayer.displayGuiScreen(null);
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
/*     */   public IListObject actionAddItemToList(GuiListBox listBox, MacroParam.Type itemType, String newItem, String displayName, int iconID) {
/* 671 */     return actionAddItemToList(listBox, itemType, newItem, displayName, iconID, (Object)null);
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
/*     */   public IListObject actionAddItemToList(GuiListBox listBox, MacroParam.Type itemType, String newItem, String displayName, int iconID, Object newItemData) {
/* 684 */     IListObject newListEntry = listBox.createObject(newItem, iconID, newItemData);
/* 685 */     newListEntry.setDisplayName(displayName);
/* 686 */     listBox.addItemAt(newListEntry, listBox.getItemCount() - 1);
/* 687 */     listBox.save();
/* 688 */     return newListEntry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionAddLogMessage(String targetName, String logMessage) {
/*     */     try {
/* 696 */       DesignableGuiControl textArea = DesignableGuiControl.getControl(targetName);
/*     */       
/* 698 */       if (textArea != null && textArea instanceof DesignableGuiControlTextArea)
/*     */       {
/* 700 */         ((DesignableGuiControlTextArea)textArea).addMessage(logMessage);
/*     */       }
/*     */     }
/* 703 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionSetLabel(String targetName, String text, String binding) {
/*     */     try {
/* 711 */       DesignableGuiControl label = DesignableGuiControl.getControl(targetName);
/*     */       
/* 713 */       if (label != null && label instanceof DesignableGuiControlLabel)
/*     */       {
/* 715 */         if (text != null)
/*     */         {
/* 717 */           ((DesignableGuiControlLabel)label).setProperty("text", text);
/*     */         }
/*     */         
/* 720 */         if (binding != null)
/*     */         {
/* 722 */           ((DesignableGuiControlLabel)label).setProperty("binding", binding);
/*     */         }
/*     */       }
/*     */     
/* 726 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AutoCraftingToken actionCraft(IAutoCraftingInitiator initiator, cio thePlayer, String itemId, int damageValue, int amount, boolean shouldThrowResult, boolean verbose) {
/* 732 */     return AutoCraftingManager.getInstance().craft((IScriptActionProvider)this, initiator, thePlayer, itemId, damageValue, amount, shouldThrowResult, verbose);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionBreakLoop(IMacro macro, IMacroAction breakAction) {
/* 738 */     if (breakAction != null) {
/*     */       
/* 740 */       IMacroActionProcessor actionProcessor = breakAction.getActionProcessor();
/* 741 */       actionProcessor.breakLoop((IScriptActionProvider)this, macro, breakAction);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionBeginUnsafeBlock(IMacro macro, IMacroAction instance, int maxActions) {
/* 748 */     if (instance != null) {
/*     */       
/* 750 */       IMacroActionProcessor actionProcessor = instance.getActionProcessor();
/* 751 */       actionProcessor.beginUnsafeBlock((IScriptActionProvider)this, macro, instance, maxActions);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionEndUnsafeBlock(IMacro macro, IMacroAction instance) {
/* 758 */     if (instance != null) {
/*     */       
/* 760 */       IMacroActionProcessor actionProcessor = instance.getActionProcessor();
/* 761 */       actionProcessor.endUnsafeBlock((IScriptActionProvider)this, macro, instance);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionScheduleResChange(int width, int height) {
/* 768 */     if (this.pendingResChange == 0) this.pendingResChange = 5; 
/* 769 */     this.pendingResWidth = width;
/* 770 */     this.pendingResHeight = height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getIndexOfResourcePack(List<cvs> resourcePackList, String resourcePackName) {
/* 780 */     if ("default".equalsIgnoreCase(resourcePackName)) return -1;
/*     */     
/* 782 */     int resourcePackIndex = 0;
/*     */     
/* 784 */     for (cvs resourcePack : resourcePackList) {
/*     */       
/* 786 */       if (resourcePack.d().equalsIgnoreCase(resourcePackName)) {
/* 787 */         return resourcePackIndex;
/*     */       }
/* 789 */       resourcePackIndex++;
/*     */     } 
/*     */     
/* 792 */     resourcePackIndex = 0;
/* 793 */     resourcePackName = resourcePackName.toLowerCase();
/*     */     
/* 795 */     for (cvs resourcePack : resourcePackList) {
/*     */       
/* 797 */       if (resourcePack.d().toLowerCase().contains(resourcePackName)) {
/* 798 */         return resourcePackIndex;
/*     */       }
/* 800 */       resourcePackIndex++;
/*     */     } 
/*     */     
/* 803 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\ScriptActionProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */