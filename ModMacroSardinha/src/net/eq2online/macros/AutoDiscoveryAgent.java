/*     */ package net.eq2online.macros;
/*     */ 
/*     */ import bxf;
/*     */ import cee;
/*     */ import ces;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.core.SpamFilter;
/*     */ import net.eq2online.macros.core.params.MacroParam;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.list.ListObjectFriend;
/*     */ import net.eq2online.macros.gui.list.ListObjectOnlineUser;
/*     */ import net.eq2online.macros.gui.screens.GuiAutoDiscoverStatus;
/*     */ import net.eq2online.macros.interfaces.IListObject;
/*     */ import net.eq2online.macros.interfaces.ISaveSettings;
/*     */ import net.eq2online.macros.interfaces.ISettingsProvider;
/*     */ import vb;
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
/*     */ public class AutoDiscoveryAgent
/*     */   implements ISaveSettings
/*     */ {
/*     */   enum AutoDiscoveryState
/*     */   {
/*  44 */     None,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  49 */     EvaluateLocalPlayers,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  54 */     ExecuteList,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     WaitingForList,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     ExecuteWho,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     WaitingForWho,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     ExecuteListTowns,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     WaitingForTowns,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     ExecuteListHomes,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  89 */     WaitingForHomes,
/*     */     
/*  91 */     ExecuteListWarps,
/*     */     
/*  93 */     WaitingForWarps,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     Finished,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     Failed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   protected AutoDiscoveryState state = AutoDiscoveryState.None;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   protected int timer = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MacroParam param;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GuiAutoDiscoverStatus status;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean active;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   protected ArrayList<String> items = new ArrayList<String>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 144 */   protected int responseTime = 60;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean requireHomeCount;
/*     */ 
/*     */ 
/*     */   
/*     */   public String cmdTownList;
/*     */ 
/*     */ 
/*     */   
/*     */   public String cmdHomeList;
/*     */ 
/*     */ 
/*     */   
/*     */   public String cmdWarps;
/*     */ 
/*     */ 
/*     */   
/*     */   public String cmdWarpMorePages;
/*     */ 
/*     */ 
/*     */   
/* 169 */   protected String[] ignore = new String[0];
/*     */ 
/*     */ 
/*     */   
/*     */   protected String townRegex;
/*     */ 
/*     */   
/*     */   protected String homeCountRegex;
/*     */ 
/*     */   
/* 179 */   protected int homeCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean gotHomeCount = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 189 */   protected String homeList = "";
/*     */   
/* 191 */   protected String warpList = "";
/*     */   
/*     */   protected String warpPageRegex;
/*     */   protected String warpIgnoreRegex;
/* 195 */   protected int warpsPages = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean warpPagesQueried = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private SpamFilter spamFilter;
/*     */ 
/*     */ 
/*     */   
/*     */   public AutoDiscoveryAgent() {
/* 208 */     notifySettingsCleared();
/* 209 */     MacroModCore.registerSettingsProvider(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSpamFilter(SpamFilter spamFilter) {
/* 214 */     this.spamFilter = spamFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void activate(MacroParam param) {
/* 225 */     if (this.active) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 230 */     this.active = true;
/* 231 */     this.param = param;
/*     */     
/* 233 */     this.items.clear();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 241 */     this.status = new GuiAutoDiscoverStatus(this);
/* 242 */     AbstractionLayer.displayGuiScreen((bxf)this.status);
/*     */     
/* 244 */     gotoState(AutoDiscoveryState.None);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 249 */     return this.active;
/*     */   }
/*     */ 
/*     */   
/*     */   public void retry() {
/* 254 */     cancel();
/* 255 */     activate(this.param);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancel() {
/* 263 */     if (this.active) {
/*     */       
/* 265 */       this.active = false;
/*     */ 
/*     */       
/* 268 */       gotoState(AutoDiscoveryState.None);
/* 269 */       this.param.autoPopulateCancelled();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 278 */     this.param.autoPopulateComplete(this.items);
/* 279 */     cancel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finish() {
/* 287 */     if (this.active)
/*     */     {
/* 289 */       if (this.items.size() == 0) {
/*     */         
/* 291 */         gotoState(AutoDiscoveryState.Failed);
/* 292 */         this.status.notifyFailed();
/*     */       }
/*     */       else {
/*     */         
/* 296 */         close();
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
/*     */   protected void gotoState(AutoDiscoveryState state) {
/* 308 */     this.timer = 0;
/* 309 */     this.state = state;
/*     */     
/* 311 */     if (state == AutoDiscoveryState.None)
/*     */     {
/* 313 */       this.items.clear();
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
/*     */   public void onTick() {
/* 336 */     if (isActive()) {
/*     */       
/* 338 */       this.timer++;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 343 */       if (this.param.isType(MacroParam.Type.Town)) {
/*     */         
/* 345 */         switch (this.state)
/*     */         { case None:
/* 347 */             gotoState(AutoDiscoveryState.ExecuteListTowns);
/* 348 */           case ExecuteListTowns: executeListTowns();
/* 349 */           case WaitingForTowns: if (this.timer > this.responseTime) gotoState(AutoDiscoveryState.Finished); 
/* 350 */           case Finished: finish();
/*     */           case Failed:
/* 352 */             return; }  cancel();
/*     */       } 
/*     */       
/* 355 */       if (this.param.isType(MacroParam.Type.Warp)) {
/*     */         String[] warps; int i;
/* 357 */         switch (this.state) {
/*     */           case None:
/* 359 */             gotoState(AutoDiscoveryState.ExecuteListWarps);
/* 360 */           case ExecuteListWarps: executeListWarps();
/* 361 */           case WaitingForWarps: if (this.timer > this.responseTime) gotoState(AutoDiscoveryState.Finished); 
/*     */           case Finished:
/* 363 */             warps = this.warpList.split(", ");
/*     */             
/* 365 */             for (i = 0; i < warps.length; i++) {
/*     */               
/* 367 */               if (!warps[i].equals("")) addItem(warps[i]); 
/*     */             } 
/* 369 */             finish();
/*     */           case Failed:
/*     */           
/*     */         } 
/* 373 */         cancel();
/*     */       } 
/*     */       
/* 376 */       if (this.param.isType(MacroParam.Type.Home)) {
/*     */         String[] homes; int i;
/* 378 */         switch (this.state) {
/*     */           
/*     */           case None:
/* 381 */             gotoState(AutoDiscoveryState.ExecuteListHomes);
/*     */ 
/*     */           
/*     */           case ExecuteListHomes:
/* 385 */             executeListHomes();
/*     */ 
/*     */           
/*     */           case WaitingForHomes:
/* 389 */             if (this.timer > this.responseTime || (this.gotHomeCount && this.requireHomeCount && this.homeCount < 1))
/*     */             {
/* 391 */               gotoState(AutoDiscoveryState.Finished);
/*     */             }
/*     */ 
/*     */           
/*     */           case Finished:
/* 396 */             homes = this.homeList.split(", ");
/*     */             
/* 398 */             for (i = 0; i < homes.length; i++) {
/*     */               
/* 400 */               if (!homes[i].equals("")) addItem(homes[i]); 
/*     */             } 
/* 402 */             finish();
/*     */ 
/*     */           
/*     */           case Failed:
/*     */           
/*     */         } 
/*     */         
/* 409 */         cancel();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 415 */       finish();
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
/*     */   protected void addItem(String item) {
/* 427 */     for (int i = 0; i < this.ignore.length; i++) {
/* 428 */       if (this.ignore[i].equalsIgnoreCase(item))
/*     */         return; 
/* 430 */     }  if (!this.items.contains(item)) {
/* 431 */       this.items.add(item);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void executeListTowns() {
/* 440 */     this.spamFilter.sendChatPacket(this.cmdTownList);
/* 441 */     gotoState(AutoDiscoveryState.WaitingForTowns);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void executeListWarps() {
/* 446 */     this.warpList = "";
/* 447 */     this.warpsPages = 1;
/* 448 */     this.warpPagesQueried = false;
/*     */     
/* 450 */     this.spamFilter.sendChatPacket(this.cmdWarps);
/* 451 */     gotoState(AutoDiscoveryState.WaitingForWarps);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void executeListWarpPage() {
/* 456 */     if (!this.warpPagesQueried) {
/*     */       
/* 458 */       this.warpPagesQueried = true;
/*     */       
/* 460 */       for (int warpPage = 2; warpPage <= this.warpsPages; warpPage++) {
/*     */         
/* 462 */         this.spamFilter.sendChatPacket(String.format(this.cmdWarpMorePages, new Object[] { Integer.valueOf(warpPage++) }));
/*     */       } 
/*     */       
/* 465 */       gotoState(AutoDiscoveryState.WaitingForWarps);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void executeListHomes() {
/* 474 */     this.homeCount = 0;
/* 475 */     this.gotHomeCount = !this.requireHomeCount;
/* 476 */     this.homeList = "";
/*     */     
/* 478 */     this.spamFilter.sendChatPacket(this.cmdHomeList);
/* 479 */     gotoState(AutoDiscoveryState.WaitingForHomes);
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
/*     */   public void onChat(String chatMessage, String chatMessageNoColours) {
/* 491 */     if (isActive())
/*     */     {
/* 493 */       switch (this.state) {
/*     */         case WaitingForTowns:
/* 495 */           processChatMessageAsTownList(chatMessage, chatMessageNoColours); break;
/* 496 */         case WaitingForHomes: processChatMessageAsHomeList(chatMessage, chatMessageNoColours); break;
/* 497 */         case WaitingForWarps: processChatMessageAsWarpList(chatMessage, chatMessageNoColours);
/*     */           break;
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
/*     */   protected void processChatMessageAsTownList(String chatMessage, String chatMessageNoColours) {
/*     */     try {
/* 529 */       Matcher townPatternMatcher = Pattern.compile(this.townRegex).matcher(chatMessage);
/* 530 */       for (; townPatternMatcher.find(); addItem(townPatternMatcher.group(1)));
/* 531 */     } catch (Exception exception) {}
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
/*     */   protected void processChatMessageAsHomeList(String chatMessage, String chatMessageNoColours) {
/*     */     try {
/* 544 */       if (this.gotHomeCount) {
/*     */         
/* 546 */         this.homeList += chatMessageNoColours;
/* 547 */         if (!this.requireHomeCount && (this.homeList.split(", ")).length >= this.homeCount)
/*     */         {
/* 549 */           gotoState(AutoDiscoveryState.Finished);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 554 */         Matcher homeCountPatternMatcher = Pattern.compile(this.homeCountRegex).matcher(chatMessage);
/*     */         
/* 556 */         if (homeCountPatternMatcher.matches())
/*     */         {
/* 558 */           this.homeCount = Integer.parseInt(homeCountPatternMatcher.group(1));
/* 559 */           this.gotHomeCount = true;
/*     */         }
/*     */       
/*     */       } 
/* 563 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processChatMessageAsWarpList(String chatMessage, String chatMessageNoColours) {
/* 569 */     Matcher warpListIgnorePatternMatcher = Pattern.compile(this.warpIgnoreRegex, 2).matcher(chatMessage);
/*     */     
/* 571 */     if (!warpListIgnorePatternMatcher.find()) {
/*     */       
/* 573 */       Matcher warpListPagePatternMatcher = Pattern.compile(this.warpPageRegex, 2).matcher(chatMessage);
/*     */       
/* 575 */       if (warpListPagePatternMatcher.find()) {
/*     */         
/* 577 */         this.warpsPages = Integer.parseInt(warpListPagePatternMatcher.group(1));
/* 578 */         this.warpList += ", ";
/* 579 */         executeListWarpPage();
/*     */       }
/*     */       else {
/*     */         
/* 583 */         this.warpList += chatMessageNoColours;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void populateUserListBox(GuiListBox userListBox, boolean friends) {
/* 591 */     cee netclienthandler = (AbstractionLayer.getPlayer()).a;
/* 592 */     Collection<ces> playerList = netclienthandler.d();
/* 593 */     int playerId = 0;
/*     */     
/* 595 */     Pattern namePattern = Pattern.compile("[a-zA-Z0-9_]{2,16}$");
/*     */     
/* 597 */     for (ces playerEntry : playerList) {
/*     */       
/* 599 */       String trimmedPlayerName = vb.a(playerEntry.a().getName());
/* 600 */       if (trimmedPlayerName.length() > MacroModSettings.trimCharsUserListStart) trimmedPlayerName = trimmedPlayerName.substring(MacroModSettings.trimCharsUserListStart); 
/* 601 */       if (trimmedPlayerName.length() > MacroModSettings.trimCharsUserListEnd) trimmedPlayerName = trimmedPlayerName.substring(0, trimmedPlayerName.length() - MacroModSettings.trimCharsUserListEnd);
/*     */       
/* 603 */       Matcher namePatternMatcher = namePattern.matcher(trimmedPlayerName);
/*     */       
/* 605 */       if (namePatternMatcher.find()) {
/*     */         
/* 607 */         String filteredPlayerName = namePatternMatcher.group();
/*     */         
/* 609 */         if (MacroModSettings.includeSelf || !filteredPlayerName.equalsIgnoreCase(AbstractionLayer.getPlayer().d_())) {
/*     */           
/* 611 */           if (friends) {
/* 612 */             userListBox.addItem((IListObject)new ListObjectFriend(playerId++, 0, filteredPlayerName)); continue;
/*     */           } 
/* 614 */           userListBox.addItem((IListObject)new ListObjectOnlineUser(playerId++, filteredPlayerName));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 619 */     userListBox.setSortable(true).sort();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifySettingsCleared() {
/* 625 */     this.responseTime = 60;
/*     */     
/* 627 */     this.requireHomeCount = true;
/*     */     
/* 629 */     this.cmdTownList = "/town list";
/* 630 */     this.cmdHomeList = "/listhomes";
/* 631 */     this.cmdWarps = "/warp";
/* 632 */     this.cmdWarpMorePages = "/warp %s";
/*     */     
/* 634 */     this.townRegex = "\\xa7[0-9a-f]([a-zA-Z0-9\\.\\_\\-]+)\\xa7[0-9a-f] \\[[0-9]+\\]\\xa7[0-9a-f]";
/* 635 */     this.homeCountRegex = "^\\xa7[0-9a-f]Home\\(s\\) : \\xa7[0-9a-f]([0-9]+)";
/*     */     
/* 637 */     this.warpPageRegex = "^\\xa77.+?page [0-9]+ of ([0-9]+)";
/* 638 */     this.warpIgnoreRegex = "^\\xa7c";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifySettingsLoaded(ISettingsProvider settings) {
/* 644 */     this.responseTime = settings.getSetting("discover.responsetimeticks", 60);
/*     */     
/* 646 */     this.requireHomeCount = settings.getSetting("discover.waitforhomecount", true);
/*     */     
/* 648 */     this.cmdTownList = settings.getSetting("discover.commandtownlist", "/town list");
/* 649 */     this.cmdHomeList = settings.getSetting("discover.commandhomelist", "/listhomes");
/* 650 */     this.cmdWarps = settings.getSetting("discover.commandwarplist", "/warp");
/* 651 */     this.cmdWarpMorePages = settings.getSetting("discover.commandwarplistpage", "/warp %s");
/*     */     
/* 653 */     this.townRegex = settings.getSetting("discover.townregex", "\\xa7[0-9a-f]([a-zA-Z0-9\\.\\_\\-]+)\\xa7[0-9a-f] \\[[0-9]+\\]\\xa7[0-9a-f]");
/* 654 */     this.homeCountRegex = settings.getSetting("discover.homecountregex", "^\\xa7[0-9a-f]Home\\(s\\) : \\xa7[0-9a-f]([0-9]+)");
/*     */     
/* 656 */     this.warpPageRegex = settings.getSetting("discover.warppageregex", "^\\xa77.+?page [0-9]+ of ([0-9]+)");
/* 657 */     this.warpIgnoreRegex = settings.getSetting("discover.warpignoreregex", "^\\xa7c");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveSettings(ISettingsProvider settings) {
/* 663 */     settings.setSetting("discover.responsetimeticks", this.responseTime);
/*     */     
/* 665 */     settings.setSetting("discover.waitforhomecount", this.requireHomeCount);
/*     */     
/* 667 */     settings.setSetting("discover.commandtownlist", this.cmdTownList);
/* 668 */     settings.setSetting("discover.commandhomelist", this.cmdHomeList);
/* 669 */     settings.setSetting("discover.commandwarplist", this.cmdWarps);
/* 670 */     settings.setSetting("discover.commandwarplistpage", this.cmdWarpMorePages);
/*     */     
/* 672 */     settings.setSetting("discover.townregex", this.townRegex);
/* 673 */     settings.setSetting("discover.homecountregex", this.homeCountRegex);
/*     */     
/* 675 */     settings.setSetting("discover.warppageregex", this.warpPageRegex);
/* 676 */     settings.setSetting("discover.warpignoreregex", this.warpIgnoreRegex);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\AutoDiscoveryAgent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */