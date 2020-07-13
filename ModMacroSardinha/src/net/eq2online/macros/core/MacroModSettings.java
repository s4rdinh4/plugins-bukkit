/*     */ package net.eq2online.macros.core;
/*     */ 
/*     */ import ahg;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.gui.layout.LayoutPanel;
/*     */ import net.eq2online.macros.input.InputHandler;
/*     */ import net.eq2online.macros.interfaces.ISettingsProvider;
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
/*     */ public class MacroModSettings
/*     */ {
/*     */   public static final int MAX_CHAT_LENGTH = 100;
/*  28 */   private static String macrosDirName = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   public static String compilerFlags = "";
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean compatibilityMode = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean disableDeepInjection = false;
/*     */ 
/*     */ 
/*     */   
/*  45 */   public static InputHandler.BindingComboMode bindingMode = InputHandler.BindingComboMode.Normal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean configsForFriends = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean configsForHomes = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean configsForTowns = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean configsForWarps = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean configsForPlaces = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean configsForPresets = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean enableAutoDiscovery = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean enableAutoConfigSwitch = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean enableOverride = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean enableOverrideChat = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean enableStatus = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean enableDebug = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean debugEnvironment = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean debugEnvKeys = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean simpleGui = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean alwaysGoBack = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean defaultToOptions = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean enableConfigOverlay = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   public static String colourCodeFormat = "&%s";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 154 */   public static String coordsFormat = "%1$s %2$s %3$s";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 159 */   protected static int colourCodeHelperKey = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 164 */   public static int trimCharsUserListStart = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 169 */   public static int trimCharsUserListEnd = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean includeSelf = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean enableGuiAnimation = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean rememberBindPage = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean chatHistory = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 194 */   public static Set<Integer> reservedKeys = new HashSet<Integer>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 199 */   public static String editorMinimisePromptAction = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean showSlotInfo = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean drawLargeEditorButtons = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean autoScaleKeyboard = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean showCraftingStatus = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean showChatInParamScreen = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean spamFilterEnabled = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 239 */   public static SpamFilter.FilterStyle spamFilterStyle = SpamFilter.FilterStyle.Queue;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 244 */   public static int spamFilterQueueSize = 32;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean spamFilterIgnoreCommands = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean simpleOverridePopup = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean generatePermissionsWarnings = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean scriptTrace = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean showTextEditorHelp = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean showTextEditorSyntax = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean enableButtonsOnChatGui = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean enableHighlightTextFields = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean configNameLinksToSettings = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 294 */   public static int maxInstructionsPerTick = 100;
/*     */   
/* 296 */   public static int maxExecutionTime = 100;
/*     */   
/*     */   public static boolean templateDebounceEnabled = true;
/*     */   
/* 300 */   public static int templateDebounceTicks = 10;
/*     */   
/*     */   public static boolean showAutoCraftingFailedMessage = true;
/*     */   
/*     */   public static boolean loadLayoutBindings = true;
/*     */   
/* 306 */   public static int maxIncludes = 10;
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean showFilterableChat = false;
/*     */ 
/*     */   
/*     */   public static boolean intelligentChatSplitter = false;
/*     */ 
/*     */   
/*     */   public static boolean stripDefaultNamespace = true;
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getColourCodeHelperKey() {
/* 321 */     return (colourCodeHelperKey < 0) ? InputHandler.getOverrideKeyCode() : colourCodeHelperKey;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getCompilerFlagItem() {
/* 327 */     return compilerFlags.contains("i");
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean getCompilerFlagTown() {
/* 332 */     return compilerFlags.contains("t");
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean getCompilerFlagWarp() {
/* 337 */     return compilerFlags.contains("w");
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean getCompilerFlagHome() {
/* 342 */     return compilerFlags.contains("h");
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean getCompilerFlagUser() {
/* 347 */     return compilerFlags.contains("u");
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean getCompilerFlagFriend() {
/* 352 */     return compilerFlags.contains("f");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getMacrosDirName() {
/* 359 */     return (macrosDirName == null) ? "/liteconfig/common/macros/" : macrosDirName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void notifySettingsCleared() {
/* 367 */     compilerFlags = "";
/* 368 */     compatibilityMode = false;
/* 369 */     disableDeepInjection = false;
/* 370 */     bindingMode = InputHandler.BindingComboMode.Normal;
/* 371 */     enableAutoDiscovery = true;
/*     */     
/* 373 */     enableOverride = true;
/* 374 */     enableOverrideChat = true;
/* 375 */     enableStatus = true;
/* 376 */     simpleGui = false;
/* 377 */     alwaysGoBack = false;
/* 378 */     defaultToOptions = false;
/* 379 */     colourCodeFormat = "&%s";
/* 380 */     coordsFormat = "%1$s %2$s %3$s";
/* 381 */     colourCodeHelperKey = 0;
/* 382 */     configsForFriends = false;
/* 383 */     configsForHomes = false;
/* 384 */     configsForTowns = false;
/* 385 */     configsForWarps = false;
/* 386 */     configsForPlaces = false;
/* 387 */     configsForPresets = false;
/* 388 */     trimCharsUserListStart = 0;
/* 389 */     trimCharsUserListEnd = 0;
/* 390 */     includeSelf = true;
/* 391 */     enableDebug = false;
/* 392 */     enableConfigOverlay = true;
/* 393 */     enableGuiAnimation = true;
/* 394 */     rememberBindPage = true;
/* 395 */     chatHistory = false;
/* 396 */     editorMinimisePromptAction = "";
/* 397 */     showSlotInfo = false;
/* 398 */     drawLargeEditorButtons = false;
/* 399 */     autoScaleKeyboard = false;
/* 400 */     showCraftingStatus = true;
/* 401 */     showChatInParamScreen = true;
/*     */     
/* 403 */     spamFilterEnabled = true;
/* 404 */     spamFilterQueueSize = 32;
/* 405 */     spamFilterStyle = SpamFilter.FilterStyle.Queue;
/* 406 */     spamFilterIgnoreCommands = false;
/* 407 */     simpleOverridePopup = true;
/* 408 */     generatePermissionsWarnings = false;
/* 409 */     scriptTrace = false;
/* 410 */     showTextEditorHelp = true;
/* 411 */     showTextEditorSyntax = true;
/* 412 */     enableButtonsOnChatGui = true;
/* 413 */     enableHighlightTextFields = true;
/* 414 */     configNameLinksToSettings = false;
/* 415 */     maxInstructionsPerTick = 100;
/* 416 */     maxExecutionTime = 100;
/* 417 */     templateDebounceEnabled = true;
/* 418 */     templateDebounceTicks = 4;
/* 419 */     showAutoCraftingFailedMessage = true;
/* 420 */     loadLayoutBindings = true;
/* 421 */     maxIncludes = 10;
/* 422 */     showFilterableChat = false;
/*     */     
/* 424 */     debugEnvironment = false;
/* 425 */     debugEnvKeys = false;
/*     */     
/* 427 */     reservedKeys.clear();
/*     */     
/* 429 */     reservedKeys.add(Integer.valueOf(59));
/*     */     
/* 431 */     reservedKeys.add(Integer.valueOf(61));
/*     */ 
/*     */     
/* 434 */     reservedKeys.add(Integer.valueOf(68));
/* 435 */     reservedKeys.add(Integer.valueOf(87));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void notifySettingsLoaded(ISettingsProvider settings) {
/* 444 */     compilerFlags = settings.getSetting("compiler.flags", "");
/* 445 */     compatibilityMode = settings.getSetting("input.compatibilitymode.enabled", false);
/* 446 */     disableDeepInjection = settings.getSetting("input.deepinjection.disabled", false);
/* 447 */     bindingMode = (InputHandler.BindingComboMode)settings.getSetting("gui.bindingmode", (Enum)InputHandler.BindingComboMode.Normal);
/* 448 */     enableAutoDiscovery = settings.getSetting("discover.enabled", true);
/* 449 */     enableOverride = settings.getSetting("override.enabled", true);
/* 450 */     enableOverrideChat = settings.getSetting("override.chatgui.enabled", true);
/* 451 */     enableStatus = settings.getSetting("runstatus.enabled", true);
/* 452 */     simpleGui = settings.getSetting("gui.simple", false);
/*     */     
/* 454 */     colourCodeFormat = settings.getSetting("colourcodeformat", "&%s");
/* 455 */     colourCodeHelperKey = settings.getSetting("colourcodehelperkey", 0, 0, 255);
/* 456 */     coordsFormat = settings.getSetting("place.coordsformat", "%1$s %2$s %3$s");
/* 457 */     alwaysGoBack = settings.getSetting("gui.bindafteredit", false);
/* 458 */     defaultToOptions = settings.getSetting("gui.optionsfirst", false);
/* 459 */     configsForFriends = settings.getSetting("configs.enable.friends", false);
/* 460 */     configsForHomes = settings.getSetting("configs.enable.homes", false);
/* 461 */     configsForTowns = settings.getSetting("configs.enable.towns", false);
/* 462 */     configsForWarps = settings.getSetting("configs.enable.warps", false);
/* 463 */     configsForPlaces = settings.getSetting("configs.enable.places", false);
/* 464 */     configsForPresets = settings.getSetting("configs.enable.presets", false);
/* 465 */     trimCharsUserListStart = settings.getSetting("onlineuser.trimchars.start", 0);
/* 466 */     trimCharsUserListEnd = settings.getSetting("onlineuser.trimchars.end", 0);
/* 467 */     includeSelf = settings.getSetting("onlineuser.includeself", true);
/* 468 */     enableDebug = settings.getSetting("debug.enabled", false);
/* 469 */     enableConfigOverlay = settings.getSetting("overlay.configpopup.enabled", true);
/* 470 */     enableGuiAnimation = settings.getSetting("gui.animation", true);
/* 471 */     rememberBindPage = settings.getSetting("gui.rememberpage", true);
/* 472 */     chatHistory = settings.getSetting("output.enablehistory", false);
/* 473 */     editorMinimisePromptAction = settings.getSetting("editor.minimiseprompt", "");
/* 474 */     showSlotInfo = settings.getSetting("gui.showslotids", false);
/* 475 */     drawLargeEditorButtons = settings.getSetting("gui.showlargebuttons", false);
/* 476 */     autoScaleKeyboard = settings.getSetting("gui.autoscale", false);
/* 477 */     showCraftingStatus = settings.getSetting("overlay.craftingstatus.enabled", true);
/* 478 */     showChatInParamScreen = settings.getSetting("guiparams.chathistory", true);
/*     */     
/* 480 */     spamFilterEnabled = settings.getSetting("floodprotection.enabled", true);
/* 481 */     spamFilterQueueSize = settings.getSetting("floodprotection.queuesize", 32);
/* 482 */     spamFilterStyle = (SpamFilter.FilterStyle)settings.getSetting("floodprotection.style", SpamFilter.FilterStyle.Queue);
/* 483 */     spamFilterIgnoreCommands = settings.getSetting("floodprotection.ignorecommands", false);
/* 484 */     simpleOverridePopup = settings.getSetting("override.simplepopup", true);
/* 485 */     generatePermissionsWarnings = settings.getSetting("script.warn.permissions.enabled", false);
/* 486 */     scriptTrace = settings.getSetting("script.trace", false);
/* 487 */     showTextEditorHelp = settings.getSetting("editor.help.enabled", true);
/* 488 */     showTextEditorSyntax = settings.getSetting("editor.syntax.enabled", true);
/* 489 */     enableButtonsOnChatGui = settings.getSetting("chatgui.enabled", true);
/* 490 */     enableHighlightTextFields = settings.getSetting("gui.textboxhighlight.enabled", true);
/* 491 */     configNameLinksToSettings = settings.getSetting("gui.configname.linkstosettings", false);
/* 492 */     maxInstructionsPerTick = settings.getSetting("script.run.limitpertick", 100, 0, 32000);
/* 493 */     maxExecutionTime = settings.getSetting("script.run.maxtimesharems", 100);
/* 494 */     templateDebounceEnabled = settings.getSetting("input.debounce.enabled", true);
/* 495 */     templateDebounceTicks = settings.getSetting("input.debounce.ticks", 4);
/* 496 */     showAutoCraftingFailedMessage = settings.getSetting("autocraft.failed.popup", true);
/* 497 */     loadLayoutBindings = settings.getSetting("layout.bindings.loadatstartup", true);
/* 498 */     maxIncludes = settings.getSetting("compiler.maxincludes", 10, 1, 1024);
/* 499 */     showFilterableChat = settings.getSetting("filterablechat.enabled", false);
/*     */     
/* 501 */     stripDefaultNamespace = settings.getSetting("script.stripdefaultnamespace", true);
/* 502 */     debugEnvironment = settings.getSetting("debug.environment.enabled", false);
/* 503 */     debugEnvKeys = settings.getSetting("debug.environment.keys", false);
/*     */     
/* 505 */     if (settings.getSetting("chathidden", false))
/*     */     {
/* 507 */       (AbstractionLayer.getGameSettings()).l = ahg.c;
/*     */     }
/*     */     
/* 510 */     if (macrosDirName == null) {
/*     */       
/* 512 */       macrosDirName = settings.getSetting("macrosdirectory", "/liteconfig/common/macros/");
/* 513 */       Log.info("Using config dir {0}", new Object[] { macrosDirName });
/*     */     } 
/*     */     
/* 516 */     if (settings.getSetting("input.keys.reserved", "*") != "*") {
/*     */       
/* 518 */       reservedKeys.clear();
/* 519 */       String[] strReservedKeys = settings.getSetting("input.keys.reserved", "59,61,68,87").split(",");
/*     */       
/* 521 */       for (String strReservedKey : strReservedKeys) {
/*     */ 
/*     */         
/*     */         try {
/* 525 */           int keyCode = Integer.parseInt(strReservedKey.trim());
/* 526 */           if (!reservedKeys.contains(Integer.valueOf(keyCode))) {
/* 527 */             reservedKeys.add(Integer.valueOf(keyCode));
/*     */           }
/* 529 */         } catch (NumberFormatException numberFormatException) {}
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
/*     */   public static void saveSettings(ISettingsProvider settings) {
/* 541 */     String serializedReserved = "";
/*     */     
/* 543 */     String separator = "";
/* 544 */     for (Integer reservedKey : reservedKeys) {
/*     */       
/* 546 */       serializedReserved = serializedReserved + separator + String.valueOf(reservedKey);
/* 547 */       separator = ",";
/*     */     } 
/*     */     
/* 550 */     settings.setSetting("macrosdirectory", getMacrosDirName());
/* 551 */     settings.setSetting("compiler.flags", compilerFlags);
/* 552 */     settings.setSetting("input.compatibilitymode.enabled", compatibilityMode);
/* 553 */     settings.setSetting("input.deepinjection.disabled", disableDeepInjection);
/* 554 */     settings.setSetting("gui.bindingmode", (Enum)bindingMode);
/* 555 */     settings.setSetting("input.keys.reserved", serializedReserved);
/* 556 */     settings.setSetting("discover.enabled", enableAutoDiscovery);
/* 557 */     settings.setSetting("override.enabled", enableOverride);
/* 558 */     settings.setSetting("override.chatgui.enabled", enableOverrideChat);
/* 559 */     settings.setSetting("runstatus.enabled", enableStatus);
/* 560 */     settings.setSetting("gui.simple", simpleGui);
/*     */     
/* 562 */     settings.setSetting("colourcodeformat", colourCodeFormat);
/* 563 */     settings.setSetting("place.coordsformat", coordsFormat);
/* 564 */     settings.setSetting("gui.bindafteredit", alwaysGoBack);
/* 565 */     settings.setSetting("gui.optionsfirst", defaultToOptions);
/* 566 */     settings.setSetting("colourcodehelperkey", colourCodeHelperKey);
/* 567 */     settings.setSetting("configs.enable.friends", configsForFriends);
/* 568 */     settings.setSetting("configs.enable.homes", configsForHomes);
/* 569 */     settings.setSetting("configs.enable.towns", configsForTowns);
/* 570 */     settings.setSetting("configs.enable.warps", configsForWarps);
/* 571 */     settings.setSetting("configs.enable.places", configsForPlaces);
/* 572 */     settings.setSetting("configs.enable.presets", configsForPresets);
/* 573 */     settings.setSetting("onlineuser.trimchars.start", trimCharsUserListStart);
/* 574 */     settings.setSetting("onlineuser.trimchars.end", trimCharsUserListEnd);
/* 575 */     settings.setSetting("onlineuser.includeself", includeSelf);
/* 576 */     settings.setSetting("debug.enabled", enableDebug);
/* 577 */     settings.setSetting("overlay.configpopup.enabled", enableConfigOverlay);
/* 578 */     settings.setSetting("gui.animation", enableGuiAnimation);
/* 579 */     settings.setSetting("gui.rememberpage", rememberBindPage);
/* 580 */     settings.setSetting("output.enablehistory", chatHistory);
/* 581 */     settings.setSetting("editor.minimiseprompt", editorMinimisePromptAction);
/* 582 */     settings.setSetting("gui.showslotids", showSlotInfo);
/* 583 */     settings.setSetting("gui.showlargebuttons", drawLargeEditorButtons);
/* 584 */     settings.setSetting("gui.autoscale", autoScaleKeyboard);
/* 585 */     settings.setSetting("overlay.craftingstatus.enabled", showCraftingStatus);
/* 586 */     settings.setSetting("guiparams.chathistory", showChatInParamScreen);
/*     */     
/* 588 */     settings.setSetting("floodprotection.enabled", spamFilterEnabled);
/* 589 */     settings.setSetting("floodprotection.queuesize", spamFilterQueueSize);
/* 590 */     settings.setSetting("floodprotection.style", spamFilterStyle);
/* 591 */     settings.setSetting("floodprotection.ignorecommands", spamFilterIgnoreCommands);
/* 592 */     settings.setSetting("override.simplepopup", simpleOverridePopup);
/* 593 */     settings.setSetting("script.warn.permissions.enabled", generatePermissionsWarnings);
/* 594 */     settings.setSetting("script.trace", scriptTrace);
/* 595 */     settings.setSetting("editor.help.enabled", showTextEditorHelp);
/* 596 */     settings.setSetting("editor.syntax.enabled", showTextEditorSyntax);
/* 597 */     settings.setSetting("chatgui.enabled", enableButtonsOnChatGui);
/* 598 */     settings.setSetting("gui.textboxhighlight.enabled", enableHighlightTextFields);
/* 599 */     settings.setSetting("gui.configname.linkstosettings", configNameLinksToSettings);
/* 600 */     settings.setSetting("script.run.limitpertick", maxInstructionsPerTick);
/* 601 */     settings.setSetting("script.run.maxtimesharems", maxExecutionTime);
/* 602 */     settings.setSetting("input.debounce.enabled", templateDebounceEnabled);
/* 603 */     settings.setSetting("input.debounce.ticks", templateDebounceTicks);
/* 604 */     settings.setSetting("autocraft.failed.popup", showAutoCraftingFailedMessage);
/* 605 */     settings.setSetting("layout.bindings.loadatstartup", loadLayoutBindings);
/* 606 */     settings.setSetting("compiler.maxincludes", maxIncludes);
/* 607 */     settings.setSetting("filterablechat.enabled", showFilterableChat);
/*     */     
/* 609 */     settings.setSetting("script.stripdefaultnamespace", stripDefaultNamespace);
/* 610 */     settings.setSetting("debug.environment.enabled", debugEnvironment);
/* 611 */     settings.setSetting("debug.environment.keys", debugEnvKeys);
/*     */ 
/*     */ 
/*     */     
/* 615 */     settings.setSettingComment("debug.environment.enabled", "Displays all environment variables on the HUD when debugging is enabled and the minecraft debug info is visible. WARNING THIS CAN DESTROY YOUR FRAMERATE, USE WITH CAUTION");
/* 616 */     settings.setSettingComment("debug.environment.keys", "Includes all KEY_ environment variables in the environment variable display");
/* 617 */     settings.setSettingComment("script.stripdefaultnamespace", "If enabled, items and blocks in the default \"minecraft\" namespace will be have the minecraft: prefix stripped from their names when returned by script commands or environment variables");
/* 618 */     settings.setSettingComment("filterablechat.enabled", "True to enable the filterable chat functionality");
/* 619 */     settings.setSettingComment("compiler.maxincludes", "Maximum number of file includes which can be inserted during a single compiler iteration");
/* 620 */     settings.setSettingComment("layout.bindings.loadatstartup", "If true, then the custom GUI -> slot mappings are loaded from the config at startup, otherwise the defaults are always applied");
/* 621 */     settings.setSettingComment("input.debounce.ticks", "Minimum number of game ticks that must elapse before a template can be retriggered");
/* 622 */     settings.setSettingComment("script.run.limitpertick", "Limit the number of instructions that can be processed per macro per tick");
/* 623 */     settings.setSettingComment("gui.configname.linkstosettings", "By default the config name banner now shows the configs dropdown, enable this setting to use the old behaviour");
/* 624 */     settings.setSettingComment("chatgui.enabled", "True to enable custom (designable) GUI in the chat screen");
/* 625 */     settings.setSettingComment("editor.help.enabled", "True if the text editor should display context-sensitive help popups");
/* 626 */     settings.setSettingComment("editor.syntax.enabled", "True if the text editor should enable syntax highlighting");
/* 627 */     settings.setSettingComment("script.warn.permissions.enabled", "True if the script engine should generate warnings for blocked script actions");
/* 628 */     settings.setSettingComment("override.simplepopup", "True to show only the \"simple\" MACRO OVERRIDE popup instead of the full prompt bar");
/* 629 */     settings.setSettingComment("chathidden", "Hide the minecraft chat");
/* 630 */     settings.setSettingComment("guiparams.chathistory", "Show the chat history in the 'Macro Parameter' screens");
/* 631 */     settings.setSettingComment("overlay.craftingstatus.enabled", "Enable the auto-crafting status display");
/* 632 */     settings.setSettingComment("gui.autoscale", "True if the keyboard (and event) layouts should be scaled to fit the screen rather than remaining a fixed size");
/* 633 */     settings.setSettingComment("gui.showlargebuttons", "True to display the old style large copy, move and delete buttons in the binding screen");
/* 634 */     settings.setSettingComment("gui.showslotids", "Show the slot ID's when hovering over container slots. Useful if you need to figure out which slots are which");
/* 635 */     settings.setSettingComment("editor.minimiseprompt", "Set which action the editor will take when the minimise button is clicked. Valid options are \"save\", \"nosave\" or blank to always prompt");
/* 636 */     settings.setSettingComment("output.enablehistory", "Saves outbound chat messages generated by the mod to the local chat history. Defaults to false");
/* 637 */     settings.setSettingComment("gui.rememberpage", "Remember which binding page was used last when opening the binding GUI, otherwise always starts with the KEYS screen");
/* 638 */     settings.setSettingComment("gui.animation", "Enable the slide animation in the macro binding GUI");
/* 639 */     settings.setSettingComment("overlay.configpopup.enabled", "Enable the config-switched popup when changing configs and imports");
/* 640 */     settings.setSettingComment("debug.enabled", "Enables on-screen debugging information to help diagnose problems with the mod");
/* 641 */     settings.setSettingComment("input.compatibilitymode.enabled", "Enable the compatibility mode key interception. Use this if the enhanced key capture mode is causing issues with other mods.");
/* 642 */     settings.setSettingComment("input.deepinjection.disabled", "Disable the deep injection proxy. This proxy enables more reliable key event injection but can cause the game to crash with certain mods.");
/* 643 */     settings.setSettingComment("input.pollextramousebuttons", "True if mouse3 and mouse4 buttons should be polled using jinput");
/* 644 */     settings.setSettingComment("input.mousebutton3identifier", "Identifier string for mouse button 3 with jinput. Usually \"3\" but change if the 3rd mouse button is not detected");
/* 645 */     settings.setSettingComment("input.mousebutton4identifier", "Identifier string for mouse button 4 with jinput. Usually \"4\" but change if the 4th mouse button is not detected");
/* 646 */     settings.setSettingComment("directmode.enabled", "Change UI mode to invert the SNEAK modifier");
/* 647 */     settings.setSettingComment("macrosdirectory", "Base directory for ancilliary macro config files, relative to the Minecraft directory. Use . to use the Minecraft directory (old behaviour)");
/* 648 */     settings.setSettingComment("input.keys.reserved", "Keys which will require MACRO OVERRIDE to function as macro keys");
/* 649 */     settings.setSettingComment("onlineuser.includeself", "Include self in the 'online users' list ($$u)");
/* 650 */     settings.setSettingComment("configs.enable.friends", "Enable per-config friends list, setting this to 0 uses a single list for all configurations");
/* 651 */     settings.setSettingComment("configs.enable.homes", "Enable per-config homes list, setting this to 0 uses a single list for all configurations");
/* 652 */     settings.setSettingComment("configs.enable.towns", "Enable per-config towns list, setting this to 0 uses a single list for all configurations");
/* 653 */     settings.setSettingComment("configs.enable.warps", "Enable per-config warps list, setting this to 0 uses a single list for all configurations");
/* 654 */     settings.setSettingComment("configs.enable.places", "Enable per-config places list, setting this to 0 uses a single list for all configurations");
/* 655 */     settings.setSettingComment("configs.enable.presets", "Enable per-config presets list, setting this to 0 uses a single list for all configurations");
/* 656 */     settings.setSettingComment("onlineuser.trimchars.start", "Number of characters to trim from the START of online user names");
/* 657 */     settings.setSettingComment("onlineuser.trimchars.end", "Number of characters to trim from the END of online user names");
/* 658 */     settings.setSettingComment("compiler.flags", "Compiler flags, CASE SENSITIVE. Only change this if you know what you're doing");
/* 659 */     settings.setSettingComment("runstatus.enabled", "Enable the 'running macros' display");
/* 660 */     settings.setSettingComment("override.enabled", "Enable the MACRO OVERRIDE function");
/* 661 */     settings.setSettingComment("override.chatgui.enabled", "Enable the override function when in the chat GUI, disable if using CTRL");
/* 662 */     settings.setSettingComment("customcontrolsgui.enabled", "Enable the custom 'Controls' GUI screen, disable if conflicts");
/* 663 */     settings.setSettingComment("gui.bindafteredit", "Pressing ENTER or ESC in the MACRO EDIT screen will return to the MACRO BIND screen");
/* 664 */     settings.setSettingComment("colourcodeformat", "Set this to the colour code format used by your server");
/* 665 */     settings.setSettingComment("disallowtextfiles", "Text files which CAN NOT be included in macros");
/* 666 */     settings.setSettingComment("singleplayerenable", "Set this value to 1 to enable the mod in single player");
/* 667 */     settings.setSettingComment("place.coordsformat", "Uses standard java formatting string, param 1 is X, 2 is Y and 3 is Z");
/* 668 */     settings.setSettingComment("colourcodehelperkey", "Use 0 to disable or -1 to use the MACRO OVERRIDE key");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void save() {
/* 673 */     MacroModCore.getMacroManager().save();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T getSetting(String settingName) {
/* 679 */     if (settingName.equals("requireHomeCount")) {
/* 680 */       return (T)Boolean.valueOf((MacroModCore.getMacroManager().getAutoDiscoveryAgent()).requireHomeCount);
/*     */     }
/* 682 */     if (settingName.equals("keyboardLayoutEditable")) {
/* 683 */       return (T)Boolean.valueOf(LayoutPanel.isEditable());
/*     */     }
/*     */     
/*     */     try {
/* 687 */       Field settingField = MacroModSettings.class.getDeclaredField(settingName);
/* 688 */       return (T)settingField.get(null);
/*     */     }
/* 690 */     catch (Exception ex) {
/*     */       
/* 692 */       Log.printStackTrace(ex);
/* 693 */       return null;
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
/*     */   
/*     */   public static <T> void setSetting(String settingName, T settingValue) {
/* 706 */     if (settingName.equals("requireHomeCount")) {
/*     */       
/* 708 */       (MacroModCore.getMacroManager().getAutoDiscoveryAgent()).requireHomeCount = ((Boolean)settingValue).booleanValue();
/*     */       
/*     */       return;
/*     */     } 
/* 712 */     if (settingName.equals("keyboardLayoutEditable")) {
/*     */       
/* 714 */       LayoutPanel.setEditable(((Boolean)settingValue).booleanValue());
/*     */       
/*     */       return;
/*     */     } 
/* 718 */     MacroModCore.getMacroManager().getEventManager().reloadEvents();
/*     */ 
/*     */     
/*     */     try {
/* 722 */       Field settingField = MacroModSettings.class.getDeclaredField(settingName);
/* 723 */       settingField.set(null, settingValue);
/*     */     }
/* 725 */     catch (Exception ex) {
/*     */       
/* 727 */       Log.printStackTrace(ex);
/* 728 */       throw new IllegalArgumentException("Invalid setting name " + settingName);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setReservedKeyState(int key, boolean newState) {
/* 734 */     if (newState) {
/* 735 */       reservedKeys.add(Integer.valueOf(key));
/*     */     } else {
/* 737 */       reservedKeys.remove(Integer.valueOf(key));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void toggleReservedKeyState(int key) {
/* 742 */     if (reservedKeys.contains(Integer.valueOf(key))) {
/* 743 */       reservedKeys.remove(Integer.valueOf(key));
/*     */     } else {
/* 745 */       reservedKeys.add(Integer.valueOf(key));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isReservedKey(int key) {
/* 750 */     return reservedKeys.contains(Integer.valueOf(key));
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\MacroModSettings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */